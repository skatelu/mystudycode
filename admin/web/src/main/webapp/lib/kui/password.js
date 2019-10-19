/**
 * password组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/password", [], function(require, exports, module) {
    $.fn.password = function(options, param) {
        if (typeof options == "string") {
            var method = $.fn.password.methods[options];
            if (method) {
                return method(this, param);
            } else {
                return this.validatebox(options, param);
            }
        }
        options = options || {};
        return this.each(function() {
            var state = $.data(this, "password");
            if (state) {
                $.extend(state.options, options);
            } else {
                state = $.data(this, "password", {
                    options: $.extend({}, $.fn.password.defaults, $.fn.password.parseOptions(this), options)
                });
                render(this);
            }
        });
    };
    function render(target) {
        var target = $(target), opts = target.data("password").options, isValid = 1 === Number(opts.isValid) || "true" === String(opts.isValid), vtArr;
        opts.isValid = isValid;
        if (isValid) {
            //如果password组件需要确认输入，加上eqValue规则
            if (opts.validType) {
                vtArr = opts.validType.split(";");
                vtArr.push(opts.eqRule);
                opts.validType = vtArr.join(";");
            } else {
                opts.validType = opts.eqRule;
            }
        }
        target.css("width", opts.width).validatebox($.extend({}, opts, {
            focus2Next: isValid ? false : opts.focus2Next,
            validOnBlur: isValid ? false : true
        })).addClass("password-f");
        if (target.val()) {
            $(target).data(opts.eqKey, $.trim(target.val()));
        }
        if (isValid) {
            bindEvents(target);
        }
    }
    function bindEvents(target) {
        var that = this, target = $(target), type = target.attr("type") || "text", isPassword = "password" === type, opts = target.data("validatebox").options;
        //壳子下切出界面会同时触发两次，增加该标志只允许跑一次。
        that.syncFlag = 0;
        target.unbind(".password").bind("blur.password", function(e) {
            var value = $.trim(target.validatebox("getValue")), confirmValue = $.trim(target.data(opts.eqKey)), opt = target.data("password").options, vtArr = opts.validType.split(";"), idx = $.inArray(opts.eqRule, vtArr);
            if (that.syncFlag == 0) {
                that.syncFlag = 1;
            } else {
                return;
            }
            if (-1 != idx) {
                //先移除eqValue校验规则
                vtArr.splice(idx, 1);
            }
            target.validatebox("changeValid", vtArr.join(";"));
            if (target.validatebox("isValid")) {
                if (value && (!confirmValue || confirmValue !== value)) {
                    target.data(opts.eqKey, "");
                    $.when($.isFunction(opt.afterValid) && opt.afterValid.call(target, value)).then(function(flag) {
                        if (!flag) {
                            return flag;
                        }
                        that.msgBody && that.msgBody.window("destroy");
                        var msgBody = that.msgBody = prompt(function(inputValue) {
                            if (inputValue !== value) {
                                setTimeout(function() {
                                    msgInput.focus();
                                    msgInput.validatebox("showTip", "两次输入不一致，请重新输入！");
                                }, 0);
                                setTimeout(function() {
                                    msgInput.validatebox("hideTip");
                                }, 3e3);
                                return false;
                            }
                            if (inputValue) {
                                target.data(opt.eqKey, inputValue);
                                if (opt.focus2Next) {
                                    target.validatebox("moveNext");
                                } else {
                                    target.focus();
                                }
                                $.isFunction(opt.onConfirm) && opt.onConfirm.call(target, inputValue);
                            } else {
                                if (opt.focusOnCancel) {
                                    target.focus();
                                }
                                if (opt.clearOnCancel) {
                                    target.password("clear");
                                }
                            }
                        }, {
                            title: isPassword ? "确认密码" : "确认输入",
                            msg: "请重新输入"
                        }), $window = msgBody.data("window").window, $mask = msgBody.data("window").mask, prevMsgInput = msgBody.find(".messager-input").hide(), msgInput = $("<input class='messager-input' type='" + type + "'/>").insertAfter(prevMsgInput), msgOkBtn = msgBody.find(".messager-button a:first");
                        $mask.addClass("password-window-mask");
                        $window.addClass("password-window");
                        msgBody.window("resize", {
                            width: 270
                        });
                        $window.css({
                            left: target.offset().left,
                            top: target.outerHeight(true) + target.offset().top + 3
                        });
                        prevMsgInput.remove();
                        if (opts.isManualConfirm) {
                            msgInput.attr({
                                oncontextmenu: "return false",
                                oncopy: "return false",
                                oncut: "return false",
                                onpaste: "return false"
                            });
                        }
                        if (target.attr("maxlength")) {
                            msgInput.attr("maxlength", target.attr("maxlength"));
                        }
                        msgInput.keydown(function(e) {
                            if (13 === e.keyCode) {
                                msgOkBtn.click();
                            } else if (27 === e.keyCode) {
                                msgBody.window("destroy");
                                target.focus();
                            }
                        }).validatebox({
                            required: true,
                            isFocusRemoveTip: false
                        });
                        msgOkBtn.click(function() {
                            if (!msgInput.val()) {
                                msgInput.focus();
                            }
                        });
                        setTimeout(function() {
                            msgInput.focus();
                        }, 50);
                        $.isFunction(opt.onChange) && opt.onChange.call(this, value, confirmValue);
                    });
                    vtArr.push(opts.eqRule);
                    target.validatebox("changeValid", vtArr.join(";"));
                }
                !value && $.isFunction(opt.onChange) && opt.onChange.call(this, value, confirmValue);
            }
            that.syncFlag = 0;
        });
    }
    $.fn.password.methods = {
        options: function(jq) {
            return $.data(jq[0], "password").options;
        },
        clear: function(jq) {
            return jq.each(function() {
                clear(this);
            });
        },
        setValue: function(jq, value) {
            return jq.each(function() {
                setValue(this, $.trim(value));
            });
        }
    };
    $.fn.password.parseOptions = function(target) {
        return $.extend({}, $.fn.validatebox.parseOptions(target), $.parser.parseOptions(target, [ "maxLength" ]), {});
    };
    $.fn.password.defaults = $.extend({
        eqRule: "eqValue",
        eqKey: "kui-eqValue",
        isValid: 1,
        //1-需要确认输入 0-不需要
        width: 120,
        focus2Next: true,
        onChange: function(newValue, oldValue) {},
        isManualConfirm: false
    }, $.fn.validatebox.defaults, {
        value: null,
        name: null,
        hidden: false,
        readonly: false,
        disabled: false,
        maxLength: null,
        focusOnCancel: false,
        clearOnCancel: false,
        onConfirm: $.noop,
        afterValid: function() {
            return true;
        }
    });
    function setValue(target, value) {
        var target = $(target), opts = target.data("password").options;
        target.val(value);
        if (opts.isValid) {
            target.data(opts.eqKey, value);
        }
        var type = target.attr("type") || "text", isPassword = "password" === type;
        if (!isPassword) {
            target.attr("title", value);
        }
    }
    function clear(target) {
        var opts = $(target).data("password").options;
        $(target).val("").removeAttr("title");
        $(target).data(opts.eqKey, "");
    }
});
