/**
 * 后台service测试页面模块
 * @author liuqing
 */
define(function(require, exports, module) {

    exports.loginBtnClick = function() {
        var mask = loading("正在登录！");
        ajax.login({
            OP_CODE: $("#userCode").val(),
            OP_PASS: $("#password").val(),
            VALIDATE_CODE: $("#validateCode").val()
        }).done(function(loginResult) {
            if(!loginResult.isSuccess()) {
                alert(loginResult.getMsgText() || ("未知错误，错误代码：" + loginResult.getMsgCode()), alert.ERROR);
            }
        }).fail(function() {
            alert("登录失败！", alert.ERROR);
        }).always(function() {
            mask.destroy();
            refreshValidateCode();
        });
    };

    exports.logoutBtnClick = function() {
        ajax.logout();
        refreshValidateCode();

    }

    exports.testBtnClick = function() {
        var serviceId = $("#service").val(),
            paramVal = $("#param").val(),
            paramObj = {},
            mask;

        if(!serviceId) {
            $("#service").focus();
            return;
        }

        localStorage.setItem("service", serviceId);
        localStorage.setItem("param", paramVal);

        try {
            paramVal && $.each(paramVal.split(";"), function(i, v) {
                var spt = v.split("=");
                paramObj[spt[0]] = spt[1];
            });
        } catch(ex) {
            alert("参数不合法，请重新输入！");
            return;
        }

        mask = loading("正在发送请求,请稍候...");

        ajax.post({
            noProcess: true,
            req: $.extend({service: serviceId}, paramObj)
        }).done(function(result, results) {
            $("#head").val(utils.stringify(result.getHead()));
            $("#data").val(utils.stringify(result.getData()));
        }).fail(function() {
            alert("接口调用错误.");
        }).always(function() {
            mask.destroy();
        });
    }

    exports.clearBtnClick = function() {
        $("#head").val("");
        $("#data").val("");
    }

    exports.$ready = function () {
        refreshValidateCode();
        $("#validateImg").click(refreshValidateCode);

        var serviceId = localStorage.getItem("service");
        if(serviceId) {
            $("#service").val(serviceId);
        }

        var paramVal = localStorage.getItem("param");
        if(paramVal) {
            $("#param").val(paramVal);
        }
    }

    function refreshValidateCode() {
        $("#validateImg").attr("src", uri.resolve("kjdp_validator?" + Math.random() + "#"));
    }

});