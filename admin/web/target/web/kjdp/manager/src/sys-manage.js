/**
 * 统一参数界面模块
 * @author chenk
 */
define(function(require, exports, module) {

    //选择数据字典，字典项按钮变化
    exports.btnControlCommonDict = function (i,row){
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

    exports.refreshDictCache = function () {
        var mask = loading("正在刷新字典缓存...");
        dict.refreshCache().done(function() {
            alert("刷新数据字典缓存成功");
        }).always(function() {
            mask.destroy();
        });
    }

    exports.refreshSysParamCache = function () {
        var mask = loading("正在刷新公共参数缓存...");
        param.refreshCache().done(function() {
            alert("刷新公共参数缓存成功");
        }).always(function() {
            mask.destroy();
        });
    }

    function refreshCache(cacheType) {
        return ajax.post({
            service: "P0000061",
            CACHE_TYPE: cacheType
        })
    }

    exports.refreshSysNodeCache = function () {
        var mask = loading("正在刷新子系统缓存...");
        return refreshCache("sysInfoCache").done(function() {
            alert("刷新子系统缓存成功");
        }).always(function() {
            mask.destroy();
        });
    }

    exports.refreshServiceCache = function () {
        var mask = loading("正在刷新服务缓存...");
        return refreshCache("serviceCache").done(function() {
            alert("刷新服务缓存成功");
        }).always(function() {
            mask.destroy();
        });
    }

    exports.refreshVirtualOpInfoCache = function () {
        var mask = loading("正在刷新虚拟柜员缓存...");
        return refreshCache("virtualOpInfoCache").done(function() {
            alert("刷新虚拟柜员缓存成功");
        }).always(function() {
            mask.destroy();
        });
    }

    exports.refreshOEMInfoCache = function () {
        var mask = loading("正在刷新OEM信息缓存...");
        return refreshCache("OEMCache").done(function() {
            alert("刷新OEM信息缓存成功");
        }).always(function() {
            mask.destroy();
        });
    }

    exports.beforeModifyVirtualOpInfo = function () {
        var oldOpCode = $("#KJDP_VIRTUAL_OP_SET").datagrid("getSelected").OP_CODE,
            nowOpCode = formData && formData.OP_CODE;
        if(nowOpCode && nowOpCode != oldOpCode) {
            formData.CREATE_FLAG = "1";
        } else {
            formData.CREATE_FLAG = "0";
        }
    }

    exports.viewServiceLogParamDetail = function(row) {
        var row = $("#KJDP_UPM_SERVICE_LOG").datagrid("getSelected");
        if(_.isEmpty(row)) {
            alert("请选择表格中的一行数据！");
            return;
        }

        alert("{}" !== row.PARAM_DATA ? row.PARAM_DATA : "没有获取到详细信息！", {
            width: 500
        });
    }

    exports.viewServiceLogMsgDetail = function(row) {
        var row = $("#KJDP_UPM_SERVICE_LOG").datagrid("getSelected");
        if(_.isEmpty(row)) {
            alert("请选择表格中的一行数据！");
            return;
        }

        alert(row.ERROR_MSG || "没有获取到详细信息！", {
            width: 500
        });
    }

    exports.viewClientLogDetail = function(row) {
        var row = $("#KJDP_UPM_CLIENT_LOG").datagrid("getSelected");
        if(_.isEmpty(row)) {
            alert("请选择表格中的一行数据！");
            return;
        }

        alert(row.STACK || row.MESSAGE || "没有获取到详细信息！", {
            width: 500
        });
    }

    exports.sysServiceExport = function() {
        var $formDialog = $('<div><form id="KJDP_DOCS_EXPORT_FORM" class="kui-form" style="margin-top: 10px;"></form></div>').appendTo("body");
        var $form = $formDialog.find("#KJDP_DOCS_EXPORT_FORM");
        parser.director($form);
        $formDialog.dialog({
            title: '导出文档',
            modal: true,
            draggable: true,
            width: 600,
            height: 200,
            buttons: [{
                text: '导出',
                iconCls: 'icon-save',
                handler: function () {
                    if(!$form.form("validate")) {
                        return;
                    }
                    var formData = $form.form("getData");
                    window.location.href = resolve("kjdp_docs?DOC_TITLE=" + formData.DOC_TITLE + "&DOC_VERSION=" + formData.DOC_VERSION);
                    $formDialog.dialog('destroy');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $formDialog.dialog('destroy');
                }
            }]
        });
    }

});