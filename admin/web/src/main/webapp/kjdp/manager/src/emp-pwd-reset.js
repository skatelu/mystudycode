/**
 * 员工密码重置
 * @author zhubc
 */
define(function (require, exports, module) {

    var EmpInfoView = require("./emp-info-view"),
        empInfoView;

    function $ready(){
        empInfoView = new EmpInfoView({
            el : "#empInfoPublic",
            resetEmpInfoCallback : function(){
                $("#PWD").textinput("setValue","");
                $("#PWD2").textinput("setValue","");
            }
        });
    }

    function uemEmpPwdReset(){
        var empInfo,
            pwd = $("#PWD").textinput("getValue"),
            pwd2 = $("#PWD2").textinput("getValue");
        empInfo = empInfoView.getEmpInfo();
        if(!empInfo || $.isEmptyObject(empInfo)){
            alert("请填写员工信息！");
            return;
        }
        if(!pwd){
            alert("员工密码不能为空！");
            return;
        }
        if(!pwd2){
            alert("确认密码不能为空！");
            return;
        }
        if(pwd != pwd2){
            alert("两次输入密码不一致，请重新输入！");
            return;
        }
        ajax.post({
            service: "P0000046",
            OP_CODE: empInfo.OP_CODE,
            OP_PASS: utils.encrypt(pwd, empInfo.OP_CODE)
        }).then(function() {
            alert("修改成功");
            empInfoReset();
        })

    }

    /**
     * 清空页面数据
     */
    function empInfoReset(){
        $("#PWD").textinput("setValue","");
        $("#PWD2").textinput("setValue","");
        empInfoView && empInfoView.resetEmpInfo();
    }

    //暴露函数到外部。
    return {
        $ready : $ready,
        uemEmpPwdReset:uemEmpPwdReset,
        empInfoReset:empInfoReset
    };
});
