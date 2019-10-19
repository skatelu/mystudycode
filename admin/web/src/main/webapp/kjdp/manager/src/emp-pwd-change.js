/**
 *  员工密码修改
 * @author chenk
 */
define(function (require, exports, module) {
    var $pwdOld = $("#PWD_OLD"),
        $pwd = $("#PWD"),
        $pwdNew = $("#PWD2");

    /**
     * 员工密码变更
     */
    function uemEmpPwdChange(){
        var empInfo,
            pwdOld = $pwdOld.textinput("getValue"),
            pwd = $pwd.textinput("getValue"),
            pwd2 = $pwdNew.textinput("getValue");
            empInfo = top.kjdp.getOpInfo();

        if(!pwdOld){
            $pwdOld.textinput("showTip","原密码不能为空！");
            return;
        }
        if(!pwd){
            $pwd.textinput("showTip","新密码不能为空！");
            return;
        }
        if(!pwd2){
            $pwdNew.textinput("showTip","确认新密码不能为空！");
            return;
        }
        if(pwd == pwdOld){
            $pwd.textinput("showTip","新旧密码一致，请重新输入！");
            return;
        }
        if(pwd != pwd2){
            $pwdNew.textinput("showTip","两次输入密码不一致，请重新输入！");
            return;
        }
        ajax.post({
            service: "P0000045",
            OP_CODE: empInfo.OP_CODE,
            OLD_OP_PASS: utils.encrypt(pwdOld, empInfo.OP_CODE),
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
        $pwdOld.textinput("setValue","");
        $pwd.textinput("setValue","");
        $pwdNew.textinput("setValue","");
    }

    //暴露函数到外部。
    return {
        uemEmpPwdChange:uemEmpPwdChange,
        empInfoReset:empInfoReset
    };
})
