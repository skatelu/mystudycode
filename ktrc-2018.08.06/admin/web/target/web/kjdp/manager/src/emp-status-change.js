/**
 * 员工状态变更
 * @author chenk
 */
define(function (require, exports, module) {

    var EmpInfoView = require("./emp-info-view"),
        empInfoView;

    function $ready(){
        empInfoView = new EmpInfoView({
            el : "#empInfoPublic",
            checkFreeze : false,
            searchEmpInfoCallback : searchEmpInfoCallback
        });
    }

    /**
     * 员工信息模块查询回调函数
     * @param empInfo
     */
    function searchEmpInfoCallback(empInfo){
        if(empInfo && empInfo.OP_STATUS){
            $("#OP_STATUS").combobox("setValue",empInfo.OP_STATUS);
        }
    }

    /**
     * 员工状态设置
     */
    function empStatusChange(){
        var empInfo = empInfoView.getEmpInfo(),
            opStatus = $("#OP_STATUS").combobox("getValue");
        if(!empInfo || $.isEmptyObject(empInfo)){
            alert("请填写员工信息！");
            return;
        }
        if(!opStatus){
            alert("请选择员工状态！");
            return;
        }
        if(empInfo["OP_STATUS"] == opStatus){
            alert("员工状态没有修改，不用提交！");
            return;
        }
        empInfo["OP_STATUS"] = opStatus;

        ajax.post({
            service: "P0000044",
            OP_CODE: empInfo.OP_CODE,
            OP_STATUS: opStatus
        }).then(function() {
            alert("修改成功");
            empInfoReset();
        })

    }

    /**
     * 清空页面数据
     */
    function empInfoReset(){
        $("#OP_STATUS").combobox("setValue","");
        $("#REMARK").textinput("setValue","");
        empInfoView.resetEmpInfo();
    }

    //暴露函数到外部。
    return {
        $ready : $ready,
        empStatusChange:empStatusChange,
        empInfoReset:empInfoReset
    };

})
