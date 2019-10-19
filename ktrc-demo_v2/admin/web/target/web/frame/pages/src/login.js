/**
 * 登录页面模块
 * @author chenk
 */
define(function(require, exports, module) {
    var loginService = require("../service/login-service"),
        loginRender = artTemplate("loginTpl"),
        IndexView = Backbone.View.extend({
            el: "body",

            initialize: function(args) {
                var that = this;

                that.$el.append(loginRender({
                    winWidth: $(window).width(),
                    winHeight: $(window).height() - 140,
                    showBorder: false
                }));

                that.$userCode = that.$el.find("#USER_CODE").focus();
                that.$password = that.$el.find("#TRD_PWD");
                that.$capslockTip = that.$el.find("#capslockTip");
                that.$validateCode = that.$el.find("#validateCode");
                that.$validateCodeWrap = that.$validateCode.parent();
                that.$validImg = that.$el.find("#validImg");
                that.$loginBtn = that.$el.find("#loginBtn");
                that.$loginMsg = that.$el.find("#loginMsg");
                that.isNeedValidateCode().then(function() {
                    that.reloadValidImg();
                }).always(function() {
                    that.$el.removeClass("hide");
                })
            },

            getLoginData: function() {
                return {
                    OP_CODE: this.$userCode.val().replace(/(^\s*)|(\s*$)/g, ""),
                    OP_PASS: this.$password.val().replace(/(^\s*)|(\s*$)/g, ""),
                    VALIDATE_CODE: this.$validateCode.val()
                }
            },

            showLoginMsg: function(msg) {
                _.isString(msg) && this.$loginMsg.text(msg).attr("title", msg);
            },

            isCapsLockEnabled: function(e, value) {
                var keyCode = e.keyCode,
                    shiftKey = e.shiftKey,
                    charCode;

                if(!value) {
                    return false;
                }

                charCode = value.charCodeAt(value.length - 1);

                if(keyCode >= 65 && keyCode <= 90) {
                    return shiftKey ? charCode !== keyCode : charCode === keyCode;
                }

                return false;
            },
            isNeedValidateCode: function() {
                var that = this;
                return loginService.isNeedValidateCode().then(function(flag) {
                    if(flag === true) {
                        that.$validateCodeWrap.removeClass("hide");
                    } else {
                        that.$validateCodeWrap.addClass("hide");
                    }
                })
            },
            events: {
                "keyup #USER_CODE": function(e) {
                    if(13 !== e.keyCode) {
                        return;
                    }

                    var that = this,
                        loginData = that.getLoginData();

                    if(!loginData.OP_CODE) {
                        return;
                    }

                    that.$password.focus();
                },

                "keyup #TRD_PWD": function(e) {
                    var that = this, loginData;

                    if(13 !== e.keyCode) {
                        if(16 === e.keyCode) {  //shift
                            return;
                        }
                        if(that.isCapsLockEnabled(e, that.$password.val())) {
                            that.$capslockTip.fadeIn("fast");
                        } else {
                            that.$capslockTip.hide();
                        }
                        return;
                    }

                    loginData = that.getLoginData();
                    if(!loginData.OP_PASS) {
                        return;
                    }

                    if(!that.$validateCodeWrap.hasClass("hide")) {
                        that.$validateCode.focus();
                        e.preventDefault();
                        return false;
                    } else {
                        that.$loginBtn.click();
                    }
                },

                "keyup #validateCode": function(e) {
                    if(13 !== e.keyCode) {
                        return;
                    }

                    var that = this,
                        loginData = that.getLoginData();

                    if(!loginData.VALIDATE_CODE) {
                        return;
                    }

                    that.$loginBtn.click();
                },

                "click #loginBtn": function() {
                    var that = this,

                        rUserCode = /^[\da-zA-Z]*$/g,
                        rValidCode = /^[\da-zA-Z]{4}$/g,

                        userData = that.getLoginData();

                    if(!userData["OP_CODE"]) {
                        that.showLoginMsg("用户代码不能为空！");
                        return;
                    }

                    if(!rUserCode.test(userData["OP_CODE"])) {//长城因为有OA账户登录，且OA账号可能有下划线，故不校验。
                        that.showLoginMsg("用户代码格式不正确！");
                        return;
                    }

                    if(!userData["OP_PASS"]) {
                        that.showLoginMsg("密码不能为空！");
                        return;
                    }

                    if(that.$validateCode.is(":visible") &&
                        (!userData["VALIDATE_CODE"] || !rValidCode.test(userData["VALIDATE_CODE"]))) {
                        that.showLoginMsg("验证码格式不正确！");
                        return;
                    }

                    that.$loginBtn.prop("disabled", true).text("登录中...");
                    loginService.login(userData).done(function(loginData) {
                        if (loginService.isLogin(loginData)) {
                            loginService.redirect(loginData);
                        } else {
                            that.isNeedValidateCode();
                            that.showLoginMsg(loginData['LOGIN_MSG']);
                            that.reloadValidImg();
                        }
                    }).fail(function(result) {
                        that.showLoginMsg("登录失败，服务器异常：" + result.getMsgText());
                    }).always(function() {
                        that.$loginBtn.prop("disabled", false).html("登&nbsp;&nbsp;录");
                    });
                },

                "click #validImg": "reloadValidImg"
            },

            reloadValidImg: function() {
                !this.$validateCodeWrap.hasClass("hide") && this.$validImg.attr("src", uri.resolve("kjdp_validator?" + Math.random() + "#"));
            }
        });


    exports.$ready = function() {
        if(loginService.isLogin()) {
            loginService.redirect();
        } else {
            new IndexView();
        }
    }
});