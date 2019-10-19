/**
 * 员工行政关系变更
 * @author zhubc
 */
define(function (require, exports, module) {

    var EmpInfoView = require("./emp-info-view"),
        empInfoView;

    function $ready(){
        empInfoView = new EmpInfoView({
            el : "#empInfoPublic",
            searchEmpInfoCallback : searchEmpInfoCallback,
            resetEmpInfoCallback : function(){
                $("#ORG_CODE").combotree("setValue","");
            }
        });
    }

    /**
     * 员工信息模块查询回调函数
     * @param empInfo
     */
    function searchEmpInfoCallback(empInfo){
        if(empInfo && empInfo.ORG_CODE){
            $("#ORG_CODE").combotree("setValue",empInfo.ORG_CODE);
        }
    }

    /**
     * 员工行政关系变更
     */
    function empOrgChange(){
        var empInfo = empInfoView.getEmpInfo(),
            orgCode = $("#ORG_CODE").combotree("getValue");
        if(!empInfo || $.isEmptyObject(empInfo)){
            alert("请填写员工信息！");
            return;
        }
        if(!orgCode){
            alert("请选择机构代码！");
            return;
        }
        if(empInfo["ORG_CODE"] == orgCode){
            alert("员工机构代码没有修改，不用提交！");
            return;
        }
        empInfo["ORG_CODE"] = orgCode;
        empInfo["REMARK"] = $("#REMARK").textinput("getValue") || empInfo["REMARK"];

        ajax.post({
            service: "P0000051",
            OP_CODE: empInfo.OP_CODE,
            ORG_CODE: orgCode
        }).then(function() {
            alert("修改成功");
            empInfoReset();
        })
    }

    /**
     * 清空页面数据
     */
    function empInfoReset(){
        $("#ORG_CODE").combotree("setValue","");
        empInfoView.resetEmpInfo();
    }

    //暴露函数到外部。
    return {
        $ready : $ready,
        empOrgChange:empOrgChange,
        empInfoReset:empInfoReset
    };
});
