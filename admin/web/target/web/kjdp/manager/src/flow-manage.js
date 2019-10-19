/**
 * 流程定义管理
 * @author chenk
 */
define(function (require, exports, module) {

    var datagridViewId = "KJDP_PROCESS_MODEL_DEF",
        addFormId = "KJDP_PROCESS_MODEL_ADD";
    /**
     * 行点击事件，启用或禁用部署编辑按钮
     */
    exports.checkBtn = function () {
        var rows = $("#" + datagridViewId).datagrid("getSelections");
        if (rows && rows.length == '1') {
            $("#deployBtn").linkbutton("enable");
            $("#editBtn").linkbutton("enable");
        } else if (rows && rows.length > '1') {
            $("#deployBtn").linkbutton("disable");
            $("#editBtn").linkbutton("disable");
        }
    },

    exports.clearBtn = function () {
        $("#deployBtn").linkbutton("enable");
        $("#editBtn").linkbutton("enable");
    }

    //新建流程模型定义
    exports.newModel = function () {
        var dialogId = "newModelFormDialog",
            $newModelFormDialog,
            $form = $("<form>").attr("id", addFormId);

        $("#" + dialogId).remove();

        $newModelFormDialog = $("<div>").attr("id", dialogId);
        $("body").append($newModelFormDialog.append($form));
        parser.getViewConfig(addFormId).done(function (view_config) {
            if (view_config[addFormId] != undefined) {
                $form.form(view_config[addFormId]);
                $newModelFormDialog.dialog({
                    title: '填写流程信息',
                    modal: true,
                    draggable: true,
                    width: 500,
                    height: 300,
                    buttons: [
                        {
                            text: '创建',
                            iconCls: 'icon-save',
                            handler: function () {
                                var paramJson = $form.form("getData");
                                ajax.post({
                                    service: 'P0000106',
                                    MODEL_KEY: paramJson['MODEL_KEY'],
                                    MODEL_NAME: paramJson['MODEL_NAME'],
                                    MODEL_DESCRIPTION: paramJson['MODEL_DESCRIPTION']
                                }).then(function(result) {
                                    alert("新增模型成功！");
                                    $("#"+datagridViewId).datagrid("reload");
                                    $newModelFormDialog.dialog('close');
                                })
                            }
                        },
                        {
                            text: '取消',
                            iconCls: 'icon-cancel',
                            handler: function () {
                                $newModelFormDialog.dialog('close');
                            }
                        }
                    ]
                });
            }
        });
    }

    //部署模型定义
    exports.deployModel = function () {
    }

    //导入模型定义
    exports.importModel = function () {
        var ipt = $('<input name="processFile" id="processFile" type="file" />'),
            $importProcessDialog = $("#importProcessDialog"),
            $processFileUpload = $('#processFileUpload');

        $processFileUpload.empty();
        $processFileUpload.append(ipt);
        ipt.uploadify({
            dir: 'workflow',
            fileTypeDesc: '请选择流程文件',
            fileTypeExts: '*.zip;*.bpmn;*.bar',
            fileNumLimit: 1,
            uploadUrl: uri.resolve("kjdp_upload#")
        });
        $processFileUpload.show();
        $processFileUpload.find("div.alt").remove();
        $importProcessDialog.dialog({
            title: '导入流程',
            modal: true,
            draggable: true,
            width: 600,
            height: 200,
            buttons: [
                {
                    text: '导入',
                    iconCls: 'icon-save',
                    handler: function () {
                        var fileNum = $("div").children(".uploadify-queue-item");
                        if (fileNum.length > 1) {
                            return;
                        } else if (fileNum.length == 0) {
                            top.alert('请选择需要导入的流程包文件！');
                            return;
                        } else {
                            //TODO 将流程文件写入到数据库。
                        }
                    }
                },
                {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        $importProcessDialog.dialog('close');
                    }
                }
            ]
        });

    }

    //修改模型定义
    exports.editModel = function () {

    }

    exports.exportModel = function (e) {

    }

    //模型删除
    exports.removeModel = function () {
        var row = $('#' + datagridViewId).datagrid('getSelected');
        if (row == null) {
            alert('请至少选择一条记录！');
            return;
        } else {
            top.confirm('确认删除吗?', function (isOK) {
                if (isOK) {
                    var mask = loading("删除中，请稍后。。");
                    ajax.post({
                        service: 'P0000108',
                        MODEL_ID: row['MODEL_ID']
                    }).then(function(result) {
                        alert("删除成功");
                        $('#' + datagridViewId).datagrid("reload");
                    }).always(function() {
                        mask.destroy();
                    })
                }
            })
        }
    }
});
