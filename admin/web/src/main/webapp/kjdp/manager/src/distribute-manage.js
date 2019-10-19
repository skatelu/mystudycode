/**
 * 分布式管理界面模块
 * @author ck
 */
define(function (require, exports, module) {

    /**********************注册服务管理**********************/

    function deleteService() {
        var that = this,
            row = that.target.treegrid("getSelected");
        if(!row || row.PAR_ID) {
            alert("请选择一个服务！");
            return;
        }
        var childRows = row.children;
        if(childRows && childRows.length > 0) {
            alert("有" + childRows.length + "个节点正在提供该服务,不允许删除");
            return;
        }
        confirm("确定要删除服务吗?", function(flag) {
            if(flag === true) {
                var mask = loading("正在删除服务");
                ajax.post({
                    service: "P0000088",
                    SERVICE_CODE: row.SERVICE_CODE
                }).then(function() {
                    alert("删除成功");
                    that.target.treegrid("reload");
                }).always(function() {
                    mask.destroy();
                })
            }
        });
    }

    /**********************服务节点管理**********************/

    function statusStyler(value, row) {
        return "color:" + ("0" === value ? "red" : "green");
    }

    function deleteCluster(e, $grid) {
        var row = $grid.treegrid("getSelected");
        if(!row || !row.STATUS) {
            alert("请选择一个节点！");
            return;
        }
        if(row.STATUS != "0") {
            alert("节点状态非离线，不允许删除");
            return;
        }
        confirm("确定要删除节点吗?", function(flag) {
            if(flag === true) {
                var mask = loading("正在删除节点");
                ajax.post($.extend({
                    service: "P0000089"
                }, row)).then(function() {
                    alert("删除成功");
                    $grid.treegrid("reload");
                }).always(function() {
                    mask.destroy();
                })
            }
        });
    }

    /**********************编排脚本管理**********************/
    var uploadTpl = '<table width="95%" >' +
        '<tr><td></td><td><span style="color:red;">*温馨提示：只能上传一个文件!</span></td></tr>' +
        '<tr>' +
        '<td class="form-label" style="vertical-align: top;padding-top: 10px;">导入脚本：</td>' +
        '<td id="processFileUpload"></td>' +
        '</tr>' +
        '</table>';

    function addScript() {
        var $newModelFormDialog = $('<div><form id="importScriptForm" style="margin-top: 10px;"></form></div>'),
            $form = $newModelFormDialog.find("#importScriptForm"),
            $datagrid = $("#KJDP_SERVICE_SCRIPT"),
            ipt = $('<input name="processFile" id="processFile" type="file" />'),
            formId = "KJDP_SERVICE_SCRIPT_FORM";
        $("body").append($newModelFormDialog);
        parser.getViewConfig(formId).done(function(viewConfig) {
            if(viewConfig[formId]) {
                $form.form(viewConfig[formId]);
            }
            if($datagrid.datagrid("getSelected")) {
                var rowData = $datagrid.datagrid("getSelected");
                $form.form("load", {
                    "SERVICE_CODE": rowData.SERVICE_CODE
                })
            }
            $form.append($(uploadTpl));
            var $processFileUpload = $newModelFormDialog.find("#processFileUpload");
            $processFileUpload.empty();
            $processFileUpload.append(ipt);
            ipt.uploadify({
                dir: 'temp',
                enableHtml5Upload: true,
                fileTypeDesc: '请选择groovy脚本文件',
                fileTypeExts: '*.groovy',
                fileNumLimit: 1,
                uploadUrl: uri.resolve("kjdp_upload#")
            });
            $processFileUpload.show();
            $processFileUpload.find("div.alt").remove();
            $newModelFormDialog.dialog({
                title: '编排脚本新增',
                modal: true,
                draggable: true,
                width: 600,
                height: 300,
                buttons: [
                    {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: function () {
                            if(!$form.form("validate")) {
                                return false;
                            }
                            var fileNum = $("div").children(".uploadify-queue-item");
                            if (fileNum.length > 1) {
                                return;
                            } else if (fileNum.length == 0) {
                                top.alert('请选择需要导入的脚本文件！');
                                return;
                            } else {
                                var filecon = $newModelFormDialog.find("#processFileUpload .uploadify-queue-item:eq(0)").attr("filecon");
                                if(!filecon) {
                                    top.alert('导入文件有误！');
                                    return;
                                }
                                ajax.post($.extend({
                                    service: "P0000110",
                                    FILE_NAME: filecon
                                }, $form.form("getData"))).then(function(result) {
                                    alert("添加成功!");
                                    $("#KJDP_SERVICE_SCRIPT").treegrid("reload");
                                    $newModelFormDialog.dialog('destroy');
                                });
                            }
                        }
                    },
                    {
                        text: '取消',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            $newModelFormDialog.dialog('destroy');
                        }
                    }
                ]
            })
        })
    }

    function onBeforeUpdateScript(formData) {
        formData.SERVICE_CODE = formData.ID;
    }

    function onBeforeLoadUpdateScript(rowObj) {
        rowObj.ID = rowObj.SERVICE_CODE;
    }

    var prettify = require("kjdp/prettify/prettify");

    function viewScript() {
        var rowObj = $("#KJDP_SERVICE_SCRIPT").datagrid("getSelected");
        if(!rowObj) {
            alert("请选择一行");
            return;
        }

        ajax.post({
            "service": "P0000113",
            "SERVICE_VERSION": rowObj.SERVICE_VERSION,
            "SERVICE_CODE": rowObj.SERVICE_CODE
        }).then(function(result) {
            var data = result.getDataRow(),scriptCode;
            if(!data || !data.SCRIPT_CODE) {
                alert("未获取到脚本内容");
                return;
            }
            scriptCode = decodeURIComponent(data.SCRIPT_CODE);
            scriptCode = scriptCode.replace(/\r\n/g, "<br>");
            var $dialog = $("<div></div>").append($("<pre class='prettyprint linenums'></pre>").append(scriptCode)).prependTo("body");
            $dialog.dialog({
                title: '编排脚本查看',
                modal: true,
                draggable: true,
                width: 800,
                height: 400,
                onClose: function() {
                    $dialog.dialog("destroy");
                }
            });
            prettyPrint();
        })
    }

    function clickScriptRow(rowObj) {
        if(!rowObj.SERVICE_VERSION) {
            $("#KJDP_SERVICE_SCRIPT").datagrid("getToolbarButton", 1).linkbutton("disable");
            $("#KJDP_SERVICE_SCRIPT").datagrid("getToolbarButton", 2).linkbutton("disable");
            $("#KJDP_SERVICE_SCRIPT").datagrid("getToolbarButton", 3).linkbutton("disable");
        } else {
            $("#KJDP_SERVICE_SCRIPT").datagrid("getToolbarButton", 1).linkbutton("enable");
            $("#KJDP_SERVICE_SCRIPT").datagrid("getToolbarButton", 2).linkbutton("enable");
            $("#KJDP_SERVICE_SCRIPT").datagrid("getToolbarButton", 3).linkbutton("enable");
        }

    }

    function jsonToStr(value) {
        try {
            return utils.stringify(value);
        } catch (e) {
            return value;
        }
    }

    return {
        deleteService: deleteService,
        deleteCluster: deleteCluster,
        statusStyler: statusStyler,
        onBeforeUpdateScript: onBeforeUpdateScript,
        onBeforeLoadUpdateScript: onBeforeLoadUpdateScript,
        addScript: addScript,
        clickScriptRow: clickScriptRow,
        jsonToStr: jsonToStr,
        viewScript: viewScript
    }

})
