/**
 * 统一参数界面模块
 * @author chenk
 */
define(function(require, exports, module) {
    //选择数据字典，字典项按钮变化
    function btnControlCommonDict(i,row){
        var $subGrid = $("#"+$(this).data("subDatagrid")),
            modifyBtn = _.find($subGrid.datagrid("options").toolbar, function(obj){ return obj.iconCls == "icon-edit"; }),
            delBtn = _.find($subGrid.datagrid("options").toolbar, function(obj){ return obj.iconCls == "icon-remove"; }),
            addBtn =  _.find($subGrid.datagrid("options").toolbar, function(obj){ return obj.iconCls == "icon-add"; });
        modifyBtn = $("#"+modifyBtn.id);
        delBtn = $("#"+delBtn.id);
        addBtn = $("#"+addBtn.id);
        modifyBtn.linkbutton("disable");
        delBtn.linkbutton("disable");
        addBtn.linkbutton("disable");
        if(row["MAINTAIN_FLAG"].indexOf("1") != -1){
            modifyBtn.linkbutton("enable");
        }
        if(row["MAINTAIN_FLAG"].indexOf("2") != -1){
            addBtn.linkbutton("enable");
        }
        if(row["MAINTAIN_FLAG"].indexOf("3") != -1){
            delBtn.linkbutton("enable");
        }
        if(row["MAINTAIN_FLAG"].indexOf("@") != -1){
            modifyBtn.linkbutton("enable");
            addBtn.linkbutton("enable");
            delBtn.linkbutton("enable");
        }
        $subGrid.datagrid("reload", row);
    }

    function refreshDictCache() {
        var mask = loading("正在刷新字典缓存!");
        ajax.post({
            service: "P0000013",
            CACHE_TYPE: "dictCache"
        }).always(function() {
            mask.destroy();
        })
    }

    return {
        btnControlCommonDict: btnControlCommonDict,
        refreshDictCache: refreshDictCache
    }
});