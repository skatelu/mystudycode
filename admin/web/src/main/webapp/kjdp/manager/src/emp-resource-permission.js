/**
 * 员工资源权限管理
 * @author chenk
 */
define(function (require, exports, module) {
    var EmpInfoView = require("./emp-info-view"),
        ResourcePermView = require("./resource-perm-comm"),
        resourcePermView,empInfoView;

    function $ready() {
        var $mask = loading("正在加载数据！");
        resourcePermView = new ResourcePermView({
            empSetTitle: "员工",
            onAddButtonClick: onAddButtonClick,
            onDeleteButtonClick: onDeleteButtonClick,
            onBeforeCreateDialog: onBeforeCreateDialog
        })
        resourcePermView.initView().then(function() {
            empInfoView = new EmpInfoView({
                el: "#empInfoPublic",
                expendCallback: resourcePermView.resize, //展开回调函数
                searchEmpInfoCallback: searchEmpInfoCallback,
                resetEmpInfoCallback: resetEmpInfoCallback
            });
        }).always(function() {
            $mask.destroy();
        })
    }

    function onBeforeCreateDialog() {
        var userCode = empInfoView.getUserCode();
        if (!userCode) {
            alert("请先填写员工信息！");
            return false;
        }
    }

    function onDeleteButtonClick(rows) {
        confirm("确定要删除选中的记录吗？", function (flag) {
            if (flag) {
                ajax.post({
                    service: 'P0000054',
                    RES_TYPE: rows[0].RES_TYPE,
                    OBJ_TYPE: "01",
                    AUTH_TYPE: "00",
                    RES_VALUES: _.pluck(rows, "RES_VALUE").join(","),
                    OBJ_CODE: empInfoView.getUserCode()
                }).done(function () {
                    alert("删除成功！");
                    searchEmpInfoCallback(empInfoView.getEmpInfo(), rows.RES_TYPE);
                });
            }
        });
    }

    function onAddButtonClick($form, resType) {
        var mask,
            formData = $form.form("getData"),
            mask = loading("操作中，请稍后！");
        ajax.post({
            service: 'P0000053',
            RES_TYPE: formData.RES_TYPE,
            RES_VALUES: formData.RES_VALUE,
            OBJ_TYPE: "01",
            AUTH_TYPE: "00",
            GRANTOR: top.kjdp.getOpInfo().OP_CODE,
            OBJ_CODE: empInfoView.getUserCode()
        }).done(function () {
            alert("增加成功！");
            searchEmpInfoCallback(empInfoView.getEmpInfo(), formData.PERMISSION_CLS);
        }).fail(function() {
            searchEmpInfoCallback(empInfoView.getEmpInfo(), formData.PERMISSION_CLS);
        }).always(function () {
            mask.destroy();
        });
    }

    function searchEmpInfoCallback(empInfo, resType) {
        $.when(
            //员工权限
            ajax.post({
                service: "P0000052",
                RES_TYPE: resType || "",
                AUTH_TYPE: "00",
                OBJ_TYPE: "01",
                OBJ_CODES: empInfoView.getUserCode()
            })
        ).then(function(result) {
            if(resType) {
                resourcePermView.loadPermData(result.getData()[0] || [], resType);
            } else {
                resourcePermView.loadAllPermData(result.getData()[0] || []);
            }
        })
    }

    function resetEmpInfoCallback() {
        resourcePermView.cleanPermData();
    }

    function saveOpPermissonData() {
        if(onBeforeCreateDialog() !== false) {
            var diffPermData = resourcePermView.getaDiffTreePermData(ResourcePermView.OPER_PERM_RES_TYPE),
            req = [];
            if(diffPermData && diffPermData.addPermData.length > 0) {
                req.push({
                    service: 'P0000053',
                    RES_TYPE: ResourcePermView.OPER_PERM_RES_TYPE,
                    RES_VALUES: _.map(diffPermData.addPermData || [], function(v) {
                        return v.RES_VALUE + ":" + v.RELATE_VALUE;
                    }).join(","),
                    OBJ_TYPE: "01",
                    AUTH_TYPE: "00",
                    GRANTOR: top.kjdp.getOpInfo().OP_CODE,
                    OBJ_CODE: empInfoView.getUserCode()
                })
            }
            if(diffPermData && diffPermData.delPermData.length > 0) {
                req.push({
                    service: 'P0000054',
                    RES_TYPE: ResourcePermView.OPER_PERM_RES_TYPE,
                    RES_VALUES: _.map(diffPermData.delPermData || [], function(v) {
                        return v.RES_VALUE + ":" + v.RELATE_VALUE;
                    }).join(","),
                    OBJ_TYPE: "01",
                    AUTH_TYPE: "00",
                    GRANTOR: top.kjdp.getOpInfo().OP_CODE,
                    OBJ_CODE: empInfoView.getUserCode()
                })
            }
            if(req.length == 0) {
                alert("数据未发生变化，不需提交！");
                return;
            }
            ajax.post(req).then(function() {
                alert("保存授权成功！");
            })
        }
    }

    //暴露函数到外部
    return $.extend({}, {
        $ready: $ready,
        commonAdd: function(e) {
            resourcePermView && resourcePermView.commonAdd(e);
        },
        commonDelete: function(e) {
            resourcePermView && resourcePermView.commonDelete(e);
        },
        opPerFormatter: function() {
            return resourcePermView && resourcePermView.opPerFormatter.apply(this, arguments);
        },
        saveOpPermissonData: saveOpPermissonData
    });

})