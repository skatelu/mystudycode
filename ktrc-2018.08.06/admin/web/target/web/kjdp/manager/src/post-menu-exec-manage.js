/**
 * 岗位菜单权限设置
 * @author zhubc
 */
define(function (require, exports, module) {

    var MenuTreeTpl = require("./menu-tree-view.js"),
        postcode,
        execMenuTree;

    function $ready(){
        addPostComb();
        initPage();
        $(window).resize(function(){
            resize();
        });
    }

    //绑定浏览器resize事件
    function resize(){
        var width = $(window).width(),
            height = $(window).height() - 90;
        $(".execution-box").panel("resize",{
            width : width,
            height : height
        });
        execMenuTree.initSize();
    }

    //初始化页面
    function initPage(){
        var width = $(window).width(),
            height = $(window).height() - 90;
        $(".execution-box").panel({
            title : "访问权限",
            border : true,
            width : width,
            height : height
        });
        execMenuTree = new MenuTreeTpl({
            el : ".execution-box",
            submit : execSubmit
        });

    }

    //菜单执行权限赋权
    function execSubmit(addNodeArr, deleteNodeArr){
        var onePackageLength = 9999;
        if(!postcode){
            alert("岗位代码不能为空！");
            return;
        }

        $.when(
            submitMenuPerm(addNodeArr, onePackageLength, {
                service: 'P0000053',
                RES_CLS: "00",
                RES_TYPE: "00",
                AUTH_TYPE: "00",
                GRANTOR: top.kjdp.getOpInfo().OP_CODE,
                OBJ_TYPE: "00",
                OBJ_CODE: postcode
            }), submitMenuPerm(deleteNodeArr, 1, {
                service: 'P0000054',
                RES_CLS: "00",
                RES_TYPE: "00",
                AUTH_TYPE: "00",
                GRANTOR: top.kjdp.getOpInfo().OP_CODE,
                OBJ_TYPE: "00",
                OBJ_CODE: postcode
            })
        ).then(function() {
            alert("授权成功");
            searchPostMenu();
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
        return ajax.post(reqArr).then(function(result, results) {
            if(results.isSuccess()){}
        });
    }



    //添加岗位下拉框
    function addPostComb(){
        $("#searchKey").combobox({
            width : 150,
            panelWidth : 150,
            panelHeight : 'auto',
            idField : 'POST_ID',
            valueField :"POST_ID",
            textField : 'POST_ID,POST_NAME',
            autoFill : false,
            required : true,
            req : [{ service:'P0000033'}],
            onSelect : menuIdOnSelect
        });
    }

    //清空页面
    function resetPostMenu(){
        execMenuTree.clear();
        postcode = "";
        $("#searchKey").combobox("setValue","");
    }

    //查询岗位权限
    function searchPostMenu(){
        postcode = $("#searchKey").combo("getValue");
        if(!postcode){
            alert("岗位代码不能为空！");
            return;
        }
        var mask = loading("岗位权限查询中！请稍等！");
        execMenuTree.unCheckedAll();

        ajax.post({
            service: "P0000052",
            RES_TYPE: "00",
            AUTH_TYPE: "00",
            OBJ_TYPE: "00",
            OBJ_CODES: postcode
        }).then(function(result) {
            execMenuTree.checkNode(result.getData()[0] || []);
        }).always(function() {
            mask.destroy();
        })

    }


    //选择岗位
    function menuIdOnSelect(obj){
        execMenuTree.showTabsBox("1");
        execMenuTree.clear();
        searchPostMenu();
    }

    //暴露函数到外部。
    return {
        $ready : $ready,
        resetPostMenu : resetPostMenu,
        searchPostMenu : searchPostMenu
    };
});
