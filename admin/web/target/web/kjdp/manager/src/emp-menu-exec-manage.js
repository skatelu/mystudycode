/**
 * 员工菜单访问权限设置
 * @author zhubc
 */
define(function (require, exports, module) {

    var EmpInfoView = require("./emp-info-view"),
        empInfoView,
        MenuTreeTpl = require("./menu-tree-view.js"),
        execMenuTree,menuPermNode=[],
        execMenuAllTree;

    function $ready(){
        initPage();
        $(window).resize(function(){
            resize();
        });
    }

    //绑定浏览器resize事件
    function resize(){
        var width = $(window).width(),
            height = $(window).height() - $("#empInfoTpl").height(),
            execBox = $(".execution-box");
        execBox.panel("resize",{
            width : width,
            height : height
        });
        execBox.tabs("resize",{
            width : width - 2,
            height : height-31
        });
        if(execMenuAllTree){
            execMenuAllTree.initSize();
        }
        if(execMenuTree){
            execMenuTree.initSize();
        }
    }

    //初始化页面
    function initPage(){
        empInfoView = new EmpInfoView({
            el : "#empMenu",
            closable : false,
            searchEmpInfoCallback : function(){
                searchEmpMenu();
            },
            resetEmpInfoCallback : function(){
                resetPostMenu();
            },
            closeCallback : function(){
                resize();
            },
            expendCallback : function(){
                resize();
            }
        });
        var width = $(window).width(),
            height = $(window).height() - $("#empInfoTpl").height(),
            execBox = $(".execution-box");
        execBox.panel({
            title : "访问权限",
            border : true,
            width : width,
            height : height
        });
        execBox.tabs({
            border : false,
            onSelect : function(title,i){
                if(i == 0 && execMenuTree){
                    execMenuTree.initSize();
                }
                if(i == 1 && execMenuAllTree){
                    execMenuAllTree.initSize();
                }
            }
        });
        execMenuTree = new MenuTreeTpl({
            el : ".exec-private-box",
            submit : execSubmit
        });
        execMenuAllTree = new MenuTreeTpl({
            el : ".exec-all-box",
            showBottomBar : false
        });
    }

    //员工菜单执行权限赋权
    function execSubmit(addNodeArr, deleteNodeArr){
        var userCode = empInfoView.getUserCode(),onePackageLength = 9999;
        if(!userCode){
            alert("员工代码不能为空！");
            return;
        }
        $.when(
            submitMenuPerm(addNodeArr, onePackageLength, {
                service: 'P0000053',
                RES_CLS: "00",
                RES_TYPE: "00",
                AUTH_TYPE: "00",
                OBJ_TYPE: "01",
                GRANTOR: top.kjdp.getOpInfo().OP_CODE,
                OBJ_CODE: userCode
            }), submitMenuPerm(deleteNodeArr, 1, {
                service: 'P0000054',
                RES_CLS: "00",
                RES_TYPE: "00",
                AUTH_TYPE: "00",
                GRANTOR: top.kjdp.getOpInfo().OP_CODE,
                OBJ_TYPE: "01",
                OBJ_CODE: userCode
            })
        ).then(function() {
            alert("授权成功");
            searchEmpMenu();
        })
    }

    function submitMenuPerm(valueArr, packageLength, params) {
        var reqArr = [];
        for(var i = 0; i < valueArr.length; i = i + packageLength) {
            reqArr.push($.extend({}, params || {}, {
                RES_VALUES: valueArr.slice(i, Math.min(i+packageLength, valueArr.length)).join(","),
            }));
        }
        if(reqArr.length == 0) {
            return $.Deferred().resolve();
        }
        return ajax.post(reqArr);
    }

    //员工菜单执行查询
    function searchEmpMenu(){
        var userCode = empInfoView.getUserCode(),tabArr = [];
        if(!userCode){
            alert("员工代码不能为空！");
            return;
        }
        menuPermNode = [];
        tabArr.push("1");
        execMenuTree.showTabsBox(tabArr);
        execMenuAllTree.showTabsBox(tabArr);
        var mask = loading("员工执行权限查询中，请稍后！");
        var postInfo = empInfoView.getPostArr();

        ajax.post([{
            service: "P0000052",
            RES_TYPE: "00",
            AUTH_TYPE: "00",
            OBJ_TYPE: "01",
            OBJ_CODES: userCode
        },{
            service: "P0000052",
            RES_TYPE: "00",
            AUTH_TYPE: "00",
            OBJ_TYPE: "00",
            OBJ_CODES: _.pluck(postInfo, "POST_ID").join(",")
        }]).then(function(result, results) {
            execMenuTree.checkNode(results.get(0).getData()[0] || []);
            execMenuAllTree.checkNode((results.get(0).getData()[0] || []).concat(results.get(1).getData()[0] || []));
        }).always(function() {
            mask.destroy();
        })
    }


    //清空页面
    function resetPostMenu(){
        execMenuTree.clear();
        execMenuAllTree.clear();
    }

    //暴露函数到外部。
    return {
        $ready : $ready
    };
});
