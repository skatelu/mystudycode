/**
 * 机构设置
 * @author chenk
 */
define(function (require, exports, module) {

    var orgTypeArr,
        datagridPrefix="KJDP_UPM_ORG_INFO_TYPE_";

    //表格加载完，置灰注销的机构
    function commonLoadSuccess(){
        var $grid = $(this),
            $trArr = $($grid.data("datagrid").dc.body2).find("tr");
        for(var i = 0; i < $trArr.length; i++){
            var $tr = $($trArr[i]);
            if(!$tr.hasClass("treegrid-tr-tree")){
                var orgStatus = $tr.children("td[field='ORG_STATUS']").children(" .datagrid-cell").text().substr(0,1);
                if(orgStatus == "9"){
                    $tr.css({color:"#999"});
                }
            }
        }
    }

    //根据org_type构造页面
    function createTabs(){
        var res = _.sortBy(dict.getDictData("ORG_TYPE"), "DICT_ITEM");
        orgTypeArr = res;
        $("body").empty().append(artTemplate("orgTypeTabsTemplate")({
            ORG_TYPE: res || []
        }));
        parser.parse("body");
    }

    function $ready(){
        createTabs();
    }

    //加载tab页签内容
    function orgTypeSelect(title,i){
        var dictItem = orgTypeArr[i].DICT_ITEM,
            $grid = $("#" + datagridPrefix + dictItem);
        if(dictItem == 0){
            if(!$grid.data("treegrid")) {
                parser.parse($grid.addClass("kui-treegrid"));
            }else {
                $grid.treegrid("reload");
            }
        }else{
            if(!$grid.data("datagrid")){
                parser.parse($grid.addClass("kui-datagrid"));
            }else{
                $grid.datagrid("reload");
            }
        }
    }

    //增加机构
    function commonAdd(e){
        var $datagrid = $(e.data.target),
            btnConf = _.find($datagrid.datagrid("options").toolbar, function(obj){ return obj.iconCls == "icon-add"; });
        parser.createCommonDialog({
            title: btnConf.title || "增加",
            isConfirm: btnConf.isConfirm,
            confirmMsg: btnConf.confirmMsg,
            colNumbers: btnConf.colNumbers,
            $datagrid: $datagrid,
            width: btnConf.panelWidth,
            height: btnConf.panelHeight,
            req: btnConf.req && btnConf.req[0],
            onBeforeOpen: btnConf.onBeforeOpen,
            onFormInitSuccess: btnConf.onFormInitSuccess,
            onBeforeLoad: btnConf.onBeforeLoad,
            onBeforeSave: btnConf.onBeforeSave,
            onSaveSuccess: function(formData) {
                if($datagrid.data("treegrid")){
                    $datagrid.treegrid("reload",null,null,function(){
                        $datagrid.treegrid("selectRow", formData.ORG_CODE);
                        _.isFunction(btnConf.onSaveSuccess) && btnConf.onSaveSuccess.call(this, formData);
                    })
                }else{
                    $datagrid.datagrid("reload", null, function(){
                        var index = $datagrid.datagrid("getRowIndex", formData);
                        index > -1 && $datagrid.datagrid("selectRow", index);
                        _.isFunction(btnConf.onSaveSuccess) && btnConf.onSaveSuccess.call(this, formData);
                    });
                }
            }
        });
    }

    //过滤机构节点数据，为了构造treegrid结构。
    function loadFilterOrg(data){
        if (data[0] && data[0].length > 0) {
            for ( var i = 0, len = data[0].length; i < len; i++) {
                if(data[0][i]['PAR_ORG'] != -1){
                    data[0][i]['pid'] = data[0][i]['PAR_ORG'];
                }
            }
        }
        return {
            total : data[0].length,
            rows : data[0]
        };
    }

    //修改机构
    function commonModify(e){
        var $datagrid,btnConf;
        if(e.target){
            $datagrid = $(e.data.target);
        }else if(e.ORG_TYPE){
            $datagrid = $("#" + datagridPrefix + e.ORG_TYPE);
        }else{
            $datagrid = $("#" + datagridPrefix + arguments[1].ORG_TYPE);
        }
        btnConf = _.find($datagrid.datagrid("options").toolbar, function(obj){ return obj.iconCls == "icon-edit"; });
        var row = $datagrid.datagrid("getSelected");
        if(!row){
            alert("请选择一条数据！");
            return;
        }
        if(row.ORG_STATUS == "9"){
            alert("机构【"+row.ORG_CODE+"】已经注销，不能进行修改");
            return;
        }
        parser.createCommonDialog({
            modify: true,
            title: btnConf.title || "修改",
            isConfirm: btnConf.isConfirm,
            confirmMsg: btnConf.confirmMsg,
            colNumbers: btnConf.colNumbers,
            $datagrid: $datagrid,
            width: btnConf.panelWidth,
            height: btnConf.panelHeight,
            req: btnConf.req && btnConf.req[0],
            onBeforeOpen: btnConf.onBeforeOpen,
            onFormInitSuccess: btnConf.onFormInitSuccess,
            onBeforeLoad: btnConf.onBeforeLoad,
            onBeforeSave: btnConf.onBeforeSave,
            onSaveSuccess: function(formData) {
                if($datagrid.data("treegrid")){
                    $datagrid.treegrid("reload",null,null,function(){
                        $datagrid.treegrid("selectRow", formData.ORG_CODE);
                        _.isFunction(btnConf.onSaveSuccess) && btnConf.onSaveSuccess.call(this, formData);
                    })
                }else{
                    //修改成功后前端更新行数据，不重新请求数据刷新表格
                    var index = $datagrid.datagrid("getRowIndex", $datagrid.datagrid("getSelected"));
                    $datagrid.datagrid("updateRow", index, formData);
                    _.isFunction(btnConf.onSaveSuccess) && btnConf.onSaveSuccess.call(this, formData);
                }
            }
        });
    }

    //注销机构
    function commonDelete(e){
        var $datagrid = $(e.data.target),
            btnConf = _.find($datagrid.datagrid("options").toolbar, function(obj){ return obj.iconCls == "icon-remove"; }),
            row = $datagrid.datagrid("getSelected");
        if(!row){
            alert("请选择一条数据！");
            return;
        }
        if(row.ORG_STATUS == "9"){
            alert("机构【"+row.ORG_CODE+"】已经注销，不用再次注销");
            return;
        }
        confirm(btnConf.confirmMsg || "确定要删除选中的记录吗？", function(flag) {
            if(flag) {
                if(btnConf.req && btnConf.req.length) {
                    ajax.post({
                        req: $.extend({}, btnConf.req && btnConf.req[0], row)
                    }).done(function() {
                        alert("删除成功！");
                        if($datagrid.data("treegrid")){
                            $datagrid.treegrid("reload")
                        }else{
                            $datagrid.datagrid("reload");
                        }
                        _.isFunction(btnConf.onDeleteSuccess) && btnConf.onDeleteSuccess(row);
                    });
                }
            }
        }, {
          title: btnConf.title || "提示"
        });
    }

    return {
        $ready: $ready,
        orgTypeSelect: orgTypeSelect,
        commonAdd: commonAdd,
        loadFilterOrg : loadFilterOrg,
        commonDelete: commonDelete,
        commonModify: commonModify,
        commonLoadSuccess: commonLoadSuccess
    };
});


