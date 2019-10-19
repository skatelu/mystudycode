/**
 * validatebox组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/validatebox", [], function(require, exports, module) {
    $.fn.validatebox = function(options, context) {
        if (typeof options == "string") {
            var method = $.fn.validatebox.methods[options], args = [ this ];
            if (method) {
                return method.apply(this, args.concat(Array.prototype.slice.call(arguments, 1)));
            }
        }
        return this.each(function() {
            options = options || {};
            var state = $.data(this, "validatebox");
            if (state) {
                $.extend(state.options, options);
            } else {
                state = $.data(this, "validatebox", {
                    options: $.extend(true, {}, $.fn.validatebox.defaults, $.fn.validatebox.parseOptions(this), options)
                });
                //调用初始化方法，为其加上validatebox-text class 名称
                init(this);
                $(this).removeAttr("disabled");
                if ($(this).attr("maxLength")) $(this).removeAttr("maxLength");
            }
            //执行绑定事件方法
            bindEvents(this);
            setDisabled(this, state.options.disabled);
            setMaxLength(this, state.options.maxLength);
        });
    };
    $.fn.validatebox.methods = {
        destroy: function(jq) {
            return jq.each(function() {
                destroyBox(this);
            });
        },
        validate: function(jq) {
            return jq.each(function() {
                validate(this);
            });
        },
        isValid: function(jq) {
            return validate(jq[0]);
        },
        disabled: function(jq) {
            return jq.each(function() {
                setDisabled(this, "true");
            });
        },
        enabled: function(jq) {
            return jq.each(function() {
                setDisabled(this, "false");
            });
        },
        getValue: function(jq) {
            return $.trim(jq.val());
        },
        setValue: function(jq, value) {
            return jq.each(function() {
                $(this).val(value).attr("title", value);
            });
        },
        clear: function(jq) {
            return jq.each(function() {
                clear(jq);
            });
        },
        moveNext: function(jq) {
            return jq.each(function() {
                moveNext(jq);
            });
        },
        changeValid: function(jq, value) {
            return jq.each(function() {
                changeValid(this, value);
            });
        },
        changeRequired: function(jq, value) {
            return jq.each(function() {
                changeRequired(this, value);
            });
        },
        resize: function(jq, width) {
            return jq.each(function() {
                setSize(this, width);
            });
        },
        showTip: function(jq, msg) {
            return jq.each(function() {
                showTip(this, msg);
            });
        },
        hideTip: function(jq) {
            return jq.each(function() {
                hideTip(this);
            });
        }
    };
    $.fn.validatebox.methods.enable = $.fn.validatebox.methods.enabled;
    $.fn.validatebox.methods.disable = $.fn.validatebox.methods.disabled;
    $.fn.validatebox.parseOptions = function(target) {
        var jq = $(target);
        return $.extend({}, $.parser.parseOptions(target, [ "validType", "missingMessage", "invalidMessage", {
            showMessage: "boolean"
        }, "maxLength" ]), {
            required: "true" == String(jq.attr("required")) ? true : undefined,
            disabled: jq.attr("disabled") == true ? jq.attr("disabled") : undefined
        });
    };
    $.fn.validatebox.defaults = {
        isOnePassValid: false,
        //任意一个规则通过则校验通过，默认false
        removeSingleQuote: true,
        validOnBlur: true,
        required: false,
        validType: null,
        hideValidFlag: false,
        //隐藏需要校验标志，默认为false
        speTxt: null,
        disabled: false,
        showMessage: true,
        missingMessage: "该项为必输项",
        invalidMessage: null,
        isFocusRemoveTip: true,
        setTimeLong: 5e3,
        focus2Next: true,
        rules: {
            email: {
                validator: function(value, param) {
                    function change2star(s) {
                        return s.replace(/[^\x00-\xff]/g, "**");
                    }
                    var len = change2star(value).length;
                    if (param && param.length > 0) {
                        return len <= param[0] && /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(value);
                    } else {
                        return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(value);
                    }
                },
                message: "请输入正确的邮箱地址",
                tipMessage: "emailTipMessage"
            },
            url: {
                validator: function(value) {
                    return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
                },
                message: "请输入正确的路径"
            },
            ip: {
                validator: function(value) {
                    return /^((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])|\*)\.((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])|\*)\.((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])|\*)\.((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])|\*)$/.exec(value);
                },
                message: "请输入正确的IP"
            },
            eqValue: {
                validator: function(value, param) {
                    return value == param;
                },
                message: "两次输入不一致"
            },
            length: {
                validator: function(value, param) {
                    function change2star(s) {
                        return s.replace(/[^\x00-\xff]/g, "**");
                    }
                    var len = change2star(value).length;
                    return len >= param[0] && len <= param[1];
                },
                message: "请输入{0}到{1}个字符(一个中文算两个字符)",
                tipMessage: "输入{0}到{1}个字符(一个中文算两个字符)"
            },
            eqLength: {
                validator: function(value, param) {
                    function change2star(s) {
                        return s.replace(/[^\x00-\xff]/g, "**");
                    }
                    return change2star(value).length === param[0];
                },
                message: "请输入{0}个字符(一个中文算两个字符)",
                tipMessage: "输入{0}个字符(一个中文算两个字符)"
            },
            //手机号码
            mobile: {
                validator: function(value) {
                    //return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/.exec(value);
                    return /^([+]?[\d]{1,3}-|[ ])?((13[0-9]{9})|(18[0-9]{9})|(15[0-9]{9})|(14[0-9]{9})|(17[0-9]{9}))$/.exec(value);
                },
                message: "请输入正确的手机号码"
            },
            //校验普通电话、传真号码:可以“+”开头,除数字外,可含有“-” 
            tel: {
                validator: function(value) {
                    return /^(([0\+]\d{2,3}-?)?(0\d{2,3})-?)?(\d{7,8})(-(\d{1,}))?$/.exec(value);
                },
                message: "请输入正确的电话号码"
            },
            //校验手机号码:必须以数字开头,除数字外,可含有“-” 不能输入空格
            mobileortel: {
                validator: function(value) {
                    var mobileReg = /^([+]?[\d]{1,3}-|[ ])?((13[0-9]{9})|(18[0-9]{9})|(15[0-9]{9})|(14[0-9]{9})|(17[0-9]{9}))$/;
                    var telReg = /^(([0\+]\d{2,3}-?)?(0\d{2,3})-?)?(\d{7,8})(-(\d{1,}))?$/;
                    return mobileReg.test(value) || telReg.test(value);
                },
                message: "请输入正确的手机号码或者电话号码"
            },
            //只含有数字或者英文字母
            numchar: {
                validator: function(value, param) {
                    //校验数字或字符
                    function isnumchar(s) {
                        var regularExpression = /^([\w])+$/;
                        return regularExpression.test(s);
                    }
                    //将字符串中的汉字转化为2个“*”
                    function change2star(s) {
                        return s.replace(/[^\x00-\xff]/g, "**");
                    }
                    if (param.length == 1) {
                        this.message = "请输入{0}个字符,只能输入数字或英文字符";
                    }
                    if (isnumchar(value)) {
                        var len = change2star(value).length;
                        if (param.length == 1) {
                            return len == param[0];
                        } else {
                            return len >= param[0] && len <= param[1];
                        }
                    } else {
                        return false;
                    }
                },
                message: "请输入{0}到{1}个字符,只能输入数字或英文字符"
            },
            //只含有数字或者英文字母和特殊字符'-'
            numCharMinus: {
                validator: function(value, param) {
                    //校验数字或字符或者'-'
                    function isNumcharMinus(s) {
                        var regularExpression = /^([\w\-])+$/;
                        return regularExpression.test(s);
                    }
                    //将字符串中的汉字转化为2个“*”
                    function change2star(s) {
                        return s.replace(/[^\x00-\xff]/g, "**");
                    }
                    if (isNumcharMinus(value)) {
                        var len = change2star(value).length;
                        return len >= param[0] && len <= param[1];
                    } else {
                        return false;
                    }
                },
                message: "请输入{0}到{1}个字符,只能输入数字或英文字符或者'-'"
            },
            //只含有英文字母
            enChar: {
                validator: function(value, param) {
                    //校验数字或字符
                    function isEnglishChar(s) {
                        //                        var regularExpression = /^([\w-])+$/;
                        var regularExpression = /[a-zA-Z]/;
                        return regularExpression.test(s);
                    }
                    //将字符串中的汉字转化为2个“*”
                    function change2star(s) {
                        return s.replace(/[^\x00-\xff]/g, "**");
                    }
                    if (isEnglishChar(value)) {
                        var len = change2star(value).length;
                        return len >= param[0] && len <= param[1];
                    } else {
                        return false;
                    }
                },
                message: "请输入{0}到{1}个字符,只能英文字符"
            },
            //校验开始日期不能大于截止日期,param(起始日期的输入框name)
            endDate: {
                validator: function(value, param, target) {
                    //校验确切位数数字。
                    var parForm = $($(target)[0].form);
                    var beginDataVal = $('input[name="' + param + '"]', parForm).val();
                    var bgCombo = $('input[comboname="' + param + '"]', parForm);
                    if (bgCombo.data("datebox").options.separator) {
                        beginDataVal = beginDataVal.replace(new RegExp("\\" + bgCombo.data("datebox").options.separator, "g"), "");
                    }
                    if ($(target).hasClass("kui-timespinner")) {
                        value = $(target).timespinner("getValue");
                    }
                    if (!value) {
                        if (bgCombo.length > 0) {
                            var c = bgCombo.datebox("calendar");
                            c.calendar("setMaxDate", "max");
                        }
                        return true;
                    } else {
                        if (beginDataVal != "" && value < beginDataVal) {
                            return false;
                        } else {
                            if (bgCombo.length > 0) {
                                var c = bgCombo.datebox("calendar");
                                c.calendar("setMaxDate", value);
                            }
                            return true;
                        }
                    }
                },
                message: "截止日期不能小于起始日期"
            },
            begDate: {
                validator: function(value, param, target) {
                    //校验确切位数数字。
                    var parForm = $($(target)[0].form);
                    var endDateVal = $('input[name="' + param + '"]', parForm).val();
                    var bgCombo = $('input[comboname="' + param + '"]', parForm);
                    if (bgCombo.data("datebox").options.separator) {
                        endDateVal = endDateVal.replace(new RegExp("\\" + bgCombo.data("datebox").options.separator, "g"), "");
                    }
                    if (!value) {
                        if (bgCombo.length > 0) {
                            var c = bgCombo.datebox("calendar");
                            c.calendar("setMinDate", "min");
                        }
                        return true;
                    } else {
                        if (endDateVal != "" && value > endDateVal) {
                            return false;
                        } else {
                            if (bgCombo.length > 0) {
                                var c = bgCombo.datebox("calendar");
                                c.calendar("setMinDate", value);
                            }
                            return true;
                        }
                    }
                },
                message: "起始日期不能大于截止日期"
            },
            begTime: {
                validator: function(value, param, target) {
                    var parForm = $($(target)[0].form);
                    var opts = $('input[spinnername="' + param[0] + '"]', parForm).timespinner("options");
                    var endTimeVal = $('input[spinnername="' + param[0] + '"]', parForm).timespinner("getValue");
                    if (opts.suffixStr) {
                        endTimeVal = endTimeVal.slice(0, endTimeVal.length - 2);
                    }
                    var begTimeVal = value.replace(/:/g, "");
                    if (!endTimeVal) {
                        return true;
                    } else if (endTimeVal != "" && begTimeVal > endTimeVal) {
                        return false;
                    } else {
                        return true;
                    }
                },
                message: "起始时间不能晚于截止时间！"
            },
            endTime: {
                validator: function(value, param, target) {
                    var parForm = $($(target)[0].form);
                    var opts = $('input[spinnername="' + param[0] + '"]', parForm).timespinner("options");
                    var begTimeVal = $('input[spinnername="' + param[0] + '"]', parForm).timespinner("getValue");
                    if (opts.suffixStr) {
                        begTimeVal = begTimeVal.slice(0, begTimeVal.length - 2);
                    }
                    var endTimeVal = value.replace(/:/g, "");
                    if (!begTimeVal) {
                        return true;
                    } else if (begTimeVal != "" && endTimeVal < begTimeVal) {
                        return false;
                    } else {
                        return true;
                    }
                },
                message: "截止时间不能早于起始时间！"
            },
            begNum: {
                validator: function(value, param, target) {
                    var parForm = $($(target)[0].form);
                    var endVal;
                    try {
                        endVal = $('input[numberboxname="' + param[0] + '"]', parForm).numberbox("getValue");
                    } catch (e) {
                        endVal = $('input[name="' + param[0] + '"]', parForm).val();
                    }
                    if (!endVal) {
                        return true;
                    } else if (endVal != "" && parseFloat(value) > parseFloat(endVal)) {
                        return false;
                    } else {
                        return true;
                    }
                },
                message: "起始值不能大于结束值！"
            },
            endNum: {
                validator: function(value, param, target) {
                    var parForm = $($(target)[0].form);
                    var begVal;
                    try {
                        begVal = $('input[numberboxname="' + param[0] + '"]', parForm).numberbox("getValue");
                    } catch (e) {
                        begVal = $('input[name="' + param[0] + '"]', parForm).val();
                    }
                    if (!begVal) {
                        return true;
                    } else if (begVal != "" && parseFloat(value) < parseFloat(begVal)) {
                        return false;
                    } else {
                        return true;
                    }
                },
                message: "结束值不能小于起始值！"
            },
            //验证必须字符开头
            valFirst: {
                validator: function(value, param) {
                    function checkfirstval(t) {
                        return /(^[\u4e00-\u9fa5a-zA-Z]+)([^\n]*)$/.test(value);
                    }
                    function change2star(s) {
                        return s.replace(/[^\x00-\xff]/g, "**");
                    }
                    if (checkfirstval(value)) {
                        var itemValue = change2star(value);
                        var len = change2star(value).length;
                        return len >= param[0] && len <= param[1];
                    } else {
                        return false;
                    }
                },
                message: "请输入{0}到{1}个字符,且必须输入以字符开头"
            },
            //验证只含有数字或者英文字母或者汉字  不能输入除指定字符以外的特殊字符
            val: {
                validator: function(value, param) {
                    //将允许出现的字符转换成1个空格
                    function keepchar(v, k) {
                        $.each(k, function(index, val) {
                            v = v.replace(k[index], " ");
                        });
                        return v;
                    }
                    //验证是否有特殊字符  但是能输入.和-_
                    function checkunval(t) {
                        //var regularExpression = /[^\u4e00-\u9fa5\da-zA-Z\-\_\.]+[ '"]/;
                        //return ! regularExpression.test(t);
                        var regularExpression = /^[^@。，,？?【】《》<>：；\[\]{}!！+=—~|\/\'\\\"#$%&\^\*]+$/;
                        return regularExpression.test(t);
                    }
                    //将字符串中的汉字转化为2个“*”
                    function change2star(s) {
                        return s.replace(/[^\x00-\xff]/g, "**");
                    }
                    var opts = $.data(arguments[2], "validatebox").options;
                    var itemValue = opts.speTxt !== null ? keepchar(value, opts.speTxt) : value;
                    if (checkunval(itemValue)) {
                        var len = change2star(itemValue).length;
                        return len >= param[0] && len <= param[1];
                    } else {
                        return false;
                    }
                },
                message: "请输入{0}到{1}个字符(一个中文算两个字符),且不能输入特殊字符"
            },
            //验证只含有数字或者英文字母或者汉字和 ‘#’、‘-’、‘.’ 、‘（’、 ‘）’特殊字符地址
            address: {
                validator: function(value, param) {
                    //验证是否有特殊字符  但是能输入 ‘#’、‘-’、‘.’
                    function checkunval(t) {
                        var regularExpression = /^[^@。，,？?【】《》<>：；\[\]{}!！+=~|\/\'\\\"$%&\^\*]+$/;
                        return regularExpression.test(t);
                    }
                    //将字符串中的汉字转化为2个“*”
                    function change2star(s) {
                        return s.replace(/[^\x00-\xff]/g, "**");
                    }
                    if (checkunval(value)) {
                        var itemValue = change2star(value);
                        var len = itemValue.length;
                        return len >= param[0] && len <= param[1];
                    } else {
                        return false;
                    }
                },
                message: "请输入{0}到{1}个字符(一个中文算两个字符),且不能输入除（‘#’、‘-’、‘.’、‘（）’）以外的特殊字符"
            },
            //验证英文字母或者汉字  不能输入特殊字符
            en_ch: {
                validator: function(value, param) {
                    //验证是否有特殊字符  但是能输入.和-_
                    function checkunval(t) {
                        var regularExpression = /[^\u4e00-\u9fa5a-zA-Z\-\_\.]+/;
                        return !regularExpression.test(t);
                    }
                    //将字符串中的汉字转化为2个“*”
                    function change2star(s) {
                        return s.replace(/[^\x00-\xff]/g, "**");
                    }
                    if (checkunval(value)) {
                        var itemValue = change2star(value);
                        var len = change2star(value).length;
                        return len >= param[0] && len <= param[1];
                    } else {
                        return false;
                    }
                },
                message: "请输入{0}到{1}个字符(一个中文算两个字符),只能输入中文或英文且不能输入特殊字符"
            },
            //任意数字
            number: {
                validator: function(value, param) {
                    var value = $.trim(value);
                    //校验数字（带小数点）,可带正负号；s为字符串,p1为小数点前位数,p2为小数点后位数
                    function isnumber(s, p1, p2) {
                        var regularExpression = "/^(-)?(\\d){1," + parseFloat(p1) + "}(\\.)?(\\d){0," + parseFloat(p2) + "}$/";
                        return eval(regularExpression).test(s);
                    }
                    //校验数字（不带小数点）,可带正负号；s为字符串,p1为小数点前位数
                    function isnumber2(s, p1) {
                        var regularExpression = "/^(-)?(\\d){1," + parseInt(p1) + "}$/";
                        return eval(regularExpression).test(s);
                    }
                    //zhaozz 2016-04-28
                    if (param && 1 === param.length) {
                        this.message = "请输入正确的数字, 整数位最多{0}位";
                    } else if (param && 2 === param.length) {
                        this.message = "请输入正确的数字, 整数位最多{0}位, 小数位最多{1}位";
                    }
                    var flag = value.indexOf(".") != -1 ? isnumber(value, param[0], param[1]) : isnumber2(value, param[0]);
                    if (!flag) {
                        return false;
                    } else if (value.indexOf(".") == value.length - 1) {
                        return false;
                    } else {
                        return true;
                    }
                },
                message: "请输入正确的数字"
            },
            //任意数字
            numberex: {
                validator: function(value, param) {
                    this.message = getMessage(param);
                    var value = $.trim(value);
                    //校验数字（带小数点）,可带正负号；s为字符串,p0为标志,p1为小数点前位数,p2为小数点后位数
                    function isnumber(s, p0, p1, p2) {
                        if (p0 == "0" || !p0) {
                            //正,负,0
                            var regularExpression = "^(-)?(\\d){1," + parseFloat(p1) + "}(\\.)(\\d){0," + parseFloat(p2) + "}$", regularExpression2 = "^(-)?(\\d){1," + parseFloat(p1) + "}$";
                            return new RegExp(regularExpression).test(s) || new RegExp(regularExpression2).test(s);
                        } else if (p0 == "10") {
                            //大于等于0
                            var regularExpression = "^(\\d){1," + parseFloat(p1) + "}(\\.)(\\d){0," + parseFloat(p2) + "}$", regularExpression2 = "^(\\d){1," + parseFloat(p1) + "}$";
                            return new RegExp(regularExpression).test(s) || new RegExp(regularExpression2).test(s);
                        } else if (p0 == "-10") {
                            //小于等于0
                            var regularExpression = "^(-)(\\d){1," + parseFloat(p1) + "}(\\.)(\\d){0," + parseFloat(p2) + "}$", regularExpression2 = "^(-)(\\d){1," + parseFloat(p1) + "}$";
                            return new RegExp(regularExpression).test(s) || new RegExp(regularExpression2).test(s);
                        } else if (p0 == "1") {
                            //大于0
                            var regularExpression = "^(\\d){1," + parseFloat(p1) + "}(\\.)(\\d){0," + parseFloat(p2) + "}$", regularExpression2 = "^(\\d){1," + parseFloat(p1) + "}$";
                            return (new RegExp(regularExpression).test(s) || new RegExp(regularExpression2).test(s)) && Number(s) > 0;
                        } else if (p0 == "-1") {
                            //小于0
                            var regularExpression = "^(-)(\\d){1," + parseFloat(p1) + "}(\\.)(\\d){1," + parseFloat(p2) + "}$", regularExpression2 = "^(-)(\\d){1," + parseFloat(p1) + "}$";
                            return (new RegExp(regularExpression).test(s) || new RegExp(regularExpression2).test(s)) && Number(s) < 0;
                        } else if (p0 == "2") {
                            //小于1大于等于0
                            if (value < 1 && value >= 0) {
                                var regularExpression = "^(\\d){1," + parseFloat(p1) + "}(\\.)?(\\d){0," + parseFloat(p2) + "}$";
                                return new RegExp(regularExpression).test(s);
                            } else {
                                return false;
                            }
                        } else if (p0 == "3") {
                            //小于等于1大于0
                            if (value <= 1 && value > 0) {
                                var regularExpression = "^(\\d){1," + parseFloat(p1) + "}(\\.)(\\d){0," + parseFloat(p2) + "}$", regularExpression2 = "^(\\d){1," + parseFloat(p1) + "}$";
                                return (new RegExp(regularExpression).test(s) || new RegExp(regularExpression2).test(s)) && Number(s) > 0;
                            } else {
                                return false;
                            }
                        } else if (p0 == "4") {
                            //小于1大于0
                            if (value < 1 && value > 0) {
                                var regularExpression = "^(\\d){1," + parseFloat(p1) + "}(\\.)(\\d){0," + parseFloat(p2) + "}$";
                                return new RegExp(regularExpression).test(s);
                            } else {
                                return false;
                            }
                        } else if (p0 == "5") {
                            //小于等于1大于等于0
                            if (value <= 1 && value >= 0) {
                                var regularExpression = "^(\\d){1," + parseFloat(p1) + "}(\\.)?(\\d){0," + parseFloat(p2) + "}$";
                                return new RegExp(regularExpression).test(s);
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                    //校验数字（不带小数点）,可带正负号；s为字符串,p0为标志,p1为小数点前位数
                    function isnumber2(s, p0, p1) {
                        if (p0 == "0" || !p0) {
                            //可能正负
                            var regularExpression = "/^(-)?(\\d){1," + parseInt(p1) + "}$/";
                            return eval(regularExpression).test(s);
                        } else if (p0 == "10") {
                            //大于等于0
                            var regularExpression = "/^(\\d){1," + parseInt(p1) + "}$/";
                            return eval(regularExpression).test(s);
                        } else if (p0 == "-10") {
                            //小于等于0
                            var regularExpression = "/(^0$)|(^(-)(\\d){1," + parseInt(p1) + "}$)/";
                            return eval(regularExpression).test(s);
                        } else if (p0 == "1") {
                            //大于0
                            var regularExpression = "/^[1-9](\\d){0," + (parseInt(p1) - 1) + "}$/";
                            return eval(regularExpression).test(s);
                        } else if (p0 == "-1") {
                            //小于0
                            var regularExpression = "/^(-)[1-9](\\d){1," + (parseInt(p1) - 1) + "}$/";
                            return eval(regularExpression).test(s);
                        } else if (p0 == "2") {
                            //小于1大于0
                            if (value < 1 && value >= 0) {
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                    //根据校验参数获取校验提示
                    function getMessage(param) {
                        if (2 === param.length) {
                            return "0" == param[0] ? "请输入正确的整数，最多{1}位" : "10" == param[0] ? "请输入大于等于0的整数，最多{1}位" : "-10" == param[0] ? "请输入小于等于0的整数，最多{1}位" : "1" == param[0] ? "请输入正确的正整数，最多{1}位" : "-1" == param[0] ? "请输入正确的负整数，最多{1}位" : "2" == param[0] ? "请输入大于0且小于1的数字" : "请输入正确的数字";
                        } else if (3 === param.length) {
                            return "0" == param[0] ? "请输入正确的数字，小数点前最多{1}位，小数点后最多{2}位" : "10" == param[0] ? "请输入大于等于0的数字，小数点前最多{1}位，小数点后最多{2}位" : "-10" == param[0] ? "请输入小于等于0的数字，小数点前最多{1}位，小数点后最多{2}位" : "1" == param[0] ? "请输入大于0的数字，小数点前最多{1}位，小数点后最多{2}位" : "-1" == param[0] ? "请输入小于0的数字，小数点前最多{1}位，小数点后最多{2}位" : "2" == param[0] ? "请输入大于等于0且小于1的数字，小数点前最多{1}位，小数点后最多{2}位" : "3" == param[0] ? "请输入大于0且小于等于1的数字，小数点前最多{1}位，小数点后最多{2}位" : "4" == param[0] ? "请输入大于0且小于1的数字，小数点前最多{1}位，小数点后最多{2}位" : "5" == param[0] ? "请输入大于等于0且小于等于1的数字，小数点前最多{1}位，小数点后最多{2}位" : "请输入正确的数字";
                        }
                        return "请输入正确的数字";
                    }
                    if (!/^(\+|-)?(\d+)(,\d{3})*(\.\d+)?$/.test(value)) {
                        return false;
                    } else {
                        if (value.indexOf(",") > 0) value = value.replace(/,/g, "");
                    }
                    /*var flag = value.indexOf('.') != -1 ?
                        isnumber(value, param[0], param[1], param[2] || 0) : //小数
                        isnumber2(value, param[0], param[1]); //非小数*/
                    var flag = param.length > 2 ? isnumber(value, param[0], param[1], param[2] || 0) : //小数
                    isnumber2(value, param[0], param[1]);
                    //非小数
                    if (!flag) {
                        return false;
                    } else if (value.indexOf(".") == value.length - 1) {
                        return false;
                    } else {
                        return true;
                    }
                },
                message: "请输入正确的数字"
            },
            //金额
            money: {
                validator: function(value, param) {
                    var value = $.trim(value);
                    //校验数字（带小数点）,可带正负号；s为字符串,p1为小数点前位数,p2为小数点后位数
                    function isnumber(s, p1, p2) {
                        var regularExpression = "/^(-)?(\\d){1," + parseFloat(p1) + "}(\\.)?(\\d){0," + parseFloat(p2) + "}$/";
                        return eval(regularExpression).test(s);
                    }
                    //校验数字（不带小数点）,可带正负号；s为字符串,p1为小数点前位数
                    function isnumber2(s, p1) {
                        var regularExpression = "/^(-)?[1-9](\\d){0," + (parseInt(p1) - 1) + "}$/";
                        return eval(regularExpression).test(s);
                    }
                    var flag = value.indexOf(".") != -1 ? isnumber(value, param[0], param[1]) : isnumber2(value, param[0]);
                    if (!flag) {
                        return false;
                    } else if (value.indexOf(".") == value.length - 1) {
                        return false;
                    } else {
                        return true;
                    }
                },
                message: "请输入正确的金额"
            },
            //整数（含0）
            "int": {
                validator: function(value, param) {
                    if (value.indexOf(",") > 0) value = value.replace(/,/g, "");
                    //校验整数（含0）
                    function isint(s) {
                        var regularExpression = /^(-)?\d*$/;
                        /////^[1-9]\d*$/;
                        return regularExpression.test(s);
                    }
                    if (isint(value)) {
                        var len = value.length;
                        if (len > param[0]) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return false;
                    }
                },
                message: "请输入正确的整数,最大{0}位"
            },
            //正整数（不含0）
            zint: {
                validator: function(value, param) {
                    //校验正整数（不含0）
                    if (value.indexOf(",") > 0) value = value.replace(/,/g, "");
                    function iszint(s) {
                        var regularExpression = /^[1-9]\d*$/;
                        return regularExpression.test(s);
                    }
                    if (iszint(value)) {
                        var len = value.length;
                        if (len > param[0]) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return false;
                    }
                },
                message: "请输入正确的正整数,最大{0}位"
            },
            //正负整数（含0和不含0）
            intex: {
                validator: function(value, param) {
                    //校验非负整数:10,非正整数:-10,正整数::1,负整数:-1
                    function iszint(s, p1) {
                        //非负整数:10
                        if (p1 == "10") {
                            var regularExpression = /^[0-9]\d*$/;
                            return regularExpression.test(s);
                        } else if (p1 == "-10") {
                            //非正整数
                            var regularExpression = /^(-)[0-9]\d*$/;
                            return regularExpression.test(s);
                        } else if (p1 == "1") {
                            //正整数
                            var regularExpression = /^[1-9]\d*$/;
                            return regularExpression.test(s);
                        } else if (p1 == "-1") {
                            //负整数
                            var regularExpression = /^(-)[1-9]\d*$/;
                            return regularExpression.test(s);
                        } else {
                            return false;
                        }
                    }
                    if (!/^(\+|-)?(\d+)(,\d{3})*(\.\d+)?$/.test(value)) {
                        return false;
                    } else {
                        if (value.indexOf(",") > 0) value = value.replace(/,/g, "");
                    }
                    if (iszint(value, param[1])) {
                        var len = value.length;
                        if (len > param[0]) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        return false;
                    }
                },
                message: "请输入正确的整数"
            },
            //确切位数字
            num: {
                validator: function(value, param) {
                    var value = $.trim(value);
                    //校验确切位数数字。
                    function isnum(s, data) {
                        var regularExpression = "/^(\\d){" + parseFloat(data) + "}$/";
                        return eval(regularExpression).test(s);
                    }
                    function isnum1(s, data) {
                        var regularExpression = "/^(\\d){" + parseFloat(param[0]) + "," + parseFloat(param[1]) + "}$/";
                        return eval(regularExpression).test(s);
                    }
                    //将字符串中的汉字转化为2个“*”
                    function change2star(s) {
                        return s.replace(/[^\x00-\xff]/g, "**");
                    }
                    if (param.length == 1) {
                        if (isnum(value, param[0])) {
                            var itemValue = change2star(value);
                            if (itemValue.length >= param[0]) {
                                return true;
                            } else {
                                this.message = "请输入{0}位数字";
                                return false;
                            }
                        } else {
                            this.message = "请输入{0}位数字";
                            return false;
                        }
                    } else {
                        var itemValue = change2star(value);
                        if (isnum1(value, param[0])) {
                            if (itemValue.length >= param[0] && itemValue.length <= param[1]) {
                                return true;
                            } else {
                                this.message = "请输入{0} 到 {1}位数字";
                                return false;
                            }
                        } else {
                            this.message = "请输入{0} 到 {1}位数字";
                            return false;
                        }
                    }
                },
                message: "请输入{0} 到 {1}位数字"
            },
            //非汉字
            unhan: {
                validator: function(value, param) {
                    var value = $.trim(value);
                    //校验非汉字
                    function isunhan(s) {
                        var regularExpression = /[\x00-\xff]/;
                        return regularExpression.test(s);
                    }
                    //将字符串中的汉字转化为2个“*”
                    function change2star(s) {
                        return s.replace(/[^\x00-\xff]/g, "**");
                    }
                    if (isunhan(value) && change2star(value).length <= parseFloat(param)) {
                        return true;
                    } else {
                        return false;
                    }
                },
                message: "请输入{0}个字符(1个中文算2个字符),且不能以汉字开头!"
            },
            //日期格式
            date: {
                validator: function(value) {
                    var value = $.trim(value).replace(/[^0-9]/g, "");
                    //功能; 判断得到的日期字符串的日期格式是否合法,仅yyyyMMdd格式；
                    //参数: String dayString 如:(19980508 yyyyMMdd格式)
                    //1到4位是年份,5到6位是月份,只能是1到12月,7到8位是日,只能是1到31
                    function isdate(dayString) {
                        var digit = "0123456789";
                        var datelist = new Array(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
                        if (dayString.length != 8) {
                            return false;
                        }
                        for (var i = 0; i < 8; i++) {
                            if (digit.indexOf(dayString.charAt(i), 0) == -1) {
                                return false;
                            }
                        }
                        var year = dayString.substr(0, 4);
                        //截取年部分
                        var month = dayString.substr(4, 2);
                        //截取月部分
                        var date = dayString.substr(6, 2);
                        //截取日部分
                        if (year > 3e3 || year < 1900 || month > 12 || month < 1 || date > 31 || date < 1) {
                            return false;
                        }
                        if (date > datelist[month - 1]) {
                            return false;
                        }
                        var yyyy = eval(year);
                        if (month == "02") {
                            if (yyyy % 400 == 0) {
                                if (date > 29) {
                                    return false;
                                }
                            } else if (yyyy % 4 == 0 && yyyy % 100 != 0) {
                                if (date > 29) {
                                    return false;
                                }
                            } else {
                                if (date > 28) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                    if (isdate(value)) {
                        return true;
                    } else {
                        return false;
                    }
                },
                message: "请输入正确的日期",
                tipMessage: "20100101"
            },
            //时间格式 HH:MM:SS
            time: {
                validator: function(value) {
                    var value = $.trim(value);
                    //校验时间格式 HH:MM:SS
                    function istime(timeString) {
                        var regularExpression = /^(((([0-1][0-9])|(2[0-3])):(([0-5][0-9])):(([0-5][0-9])))|((([0-1][0-2])|[0-9]):(([0-5][0-9])):(([0-5][0-9]))\s[ap]m))$/;
                        return regularExpression.test(timeString);
                    }
                    if (istime(value)) {
                        return true;
                    } else {
                        return false;
                    }
                },
                message: "请输入正确的时间"
            },
            //邮政编码
            postcode: {
                validator: function(value) {
                    var value = $.trim(value);
                    //校验邮政编码 
                    function ispostcode(s) {
                        var patrn = /^[0-9]\d{5}$/;
                        if (!patrn.exec(s)) {
                            return false;
                        }
                        return true;
                    }
                    if (ispostcode(value)) {
                        return true;
                    } else {
                        return false;
                    }
                },
                message: "请输入正确的邮政编码"
            },
            //身份证号码
            cardno: {
                validator: function(value, isLen18) {
                    var value = $.trim(value);
                    //校验身份证号码
                    function iscardno(s) {
                        var area = {
                            11: "北京",
                            12: "天津",
                            13: "河北",
                            14: "山西",
                            15: "内蒙古",
                            21: "辽宁",
                            22: "吉林",
                            23: "黑龙江",
                            31: "上海",
                            32: "江苏",
                            33: "浙江",
                            34: "安徽",
                            35: "福建",
                            36: "江西",
                            37: "山东",
                            41: "河南",
                            42: "湖北",
                            43: "湖南",
                            44: "广东",
                            45: "广西",
                            46: "海南",
                            50: "重庆",
                            51: "四川",
                            52: "贵州",
                            53: "云南",
                            54: "西藏",
                            61: "陕西",
                            62: "甘肃",
                            63: "青海",
                            64: "宁夏",
                            65: "新疆",
                            71: "台湾",
                            81: "香港",
                            82: "澳门",
                            91: "国外"
                        };
                        var idcard = s, Y, JYM;
                        var SS, M;
                        var idcard_array = new Array();
                        idcard_array = idcard.split("");
                        //地区检验 
                        if (area[parseInt(idcard.substr(0, 2))] == null) {
                            return false;
                        }
                        //身份号码位数及格式检验 	
                        switch (idcard.length) {
                          case 15:
                            if (isLen18 && isLen18[0]) {
                                return false;
                            }
                            if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || (parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0) {
                                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;
                            } else {
                                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;
                            }
                            if (ereg.test(idcard)) {
                                return true;
                            } else {
                                return false;
                            }
                            break;

                          case 18:
                            //18位身份号码检测
                            //出生日期的合法性检查
                            //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
                            //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
                            if (parseInt(idcard.substr(6, 4)) % 4 == 0 || parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0) {
                                ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;
                            } else {
                                ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;
                            }
                            //测试出生日期的合法性
                            if (ereg.test(idcard)) {
                                //计算校验位
                                SS = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 + parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9]) * 3;
                                Y = SS % 11;
                                M = "F";
                                JYM = "10X98765432";
                                M = JYM.substr(Y, 1);
                                //判断校验位
                                if (M == idcard_array[17]) {
                                    //检测ID的校验位
                                    return true;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                            break;

                          default:
                            return false;
                            break;
                        }
                    }
                    if (iscardno(value)) {
                        return true;
                    } else {
                        return false;
                    }
                },
                message: "请输入正确的身份证号"
            },
            //机构编码
            orgcode: {
                validator: function(value) {
                    var regularExpression = /^[1-9]\d*$/;
                    if (regularExpression.test(value) && value < 32767) {
                        return true;
                    }
                    return false;
                },
                message: ""
            },
            remote: {
                validator: function(params, opts) {
                    var data = {};
                    data[opts[1]] = params;
                    var response = $.ajax({
                        url: opts[0],
                        dataType: "json",
                        data: data,
                        async: false,
                        cache: false,
                        type: "post"
                    }).responseText;
                    return response == "true";
                },
                message: "Please fix this field."
            },
            equalTo: {
                validator: function(value, param) {
                    return $(param[0]).val() == value;
                },
                message: "字段不匹配"
            },
            normalchar: {
                validator: function(value, param) {
                    return !/[\'\"]/.test(value);
                },
                message: "输入字符中包含非法字符"
            },
            //校验只能够输入中文 英文 数字
            ch_en_num: {
                validator: function(value, param) {
                    function checkunval(t) {
                        var regularExpression = /[^\u4e00-\u9fa5a-zA-Z\d]+/;
                        return !regularExpression.test(t);
                    }
                    //将字符串中的汉字转化为2个“*”
                    function change2star(s) {
                        return s.replace(/[^\x00-\xff]/g, "**");
                    }
                    if (checkunval(value)) {
                        var itemValue = change2star(value);
                        var len = change2star(value).length;
                        return len >= param[0] && len <= param[1];
                    } else {
                        return false;
                    }
                },
                message: "请输入{0}到{1}个字符(一个中文算两个字符),只能输入中文、英文和数字且不能输入特殊字符"
            },
            cnchar: {
                validator: function(value) {
                    return /^[\u4e00-\u9fa5]+$/.test(value);
                },
                message: "只能输入中文字符"
            },
            name: {
                validator: function(value) {
                    return /^((([\u4e00-\u9fa5a-zA-Z\-\_\.\s])+)·?)+$/.test(value);
                },
                message: "请输入正确的姓名"
            },
            u_time_valid: {
                validator: function(value) {
                    if (!/^BEG:\d{2}:\d{2}:\d{2},END:\d{2}:\d{2}:\d{2}$/.test(value)) {
                        return false;
                    }
                    var beg = value.substr(4, 8), begArr = beg.split(":"), end = value.substr(17, 8), endArr = end.split(":");
                    if (begArr[0] > 24 || begArr[1] > 59 || begArr[2] > 59 || endArr[0] > 24 || endArr[1] > 59 || endArr[2] > 59 || beg > end) {
                        return false;
                    }
                    return true;
                },
                message: "起止时间格式不正确（时间格式：BEG:00:00:00,END:23:59:59）"
            },
            cnname: {
                validator: function(value) {
                    return /^[\u4e00-\u9fa5]+(·[\u4e00-\u9fa5]+){0,3}[\u4e00-\u9fa5]$/.test(value);
                },
                message: "请输入正确的中文姓名"
            }
        }
    };
    //len和length为同一引用，这样外部参数就不用修改了
    $.fn.validatebox.defaults.rules.len = $.fn.validatebox.defaults.rules.length;
    //初始话，为对象加上一个class
    function init(target) {
        var jq = $(target);
        var opts = $.data(target, "validatebox").options;
        $(target).addClass("validatebox-text");
        if (opts.required && !opts.noWarn) jq.closest("span.combo").length ? jq.closest("span.combo").addClass("combo-validatebox-must") : jq.addClass("validatebox-must");
    }
    //删除验证
    function destroyBox(target) {
        var state = $.data(target, "validatebox");
        if (state) {
            state.validating = false;
            var tip = state.tip;
            if (tip) {
                tip.remove();
            }
            $(target).unbind();
            $(target).remove();
        }
    }
    //绑定事件
    function bindEvents(target) {
        //dom对象转化为jquery对象
        var box = $(target);
        //拿到缓存里的数据
        //if(box.hasClass("combo-text")) return;
        var boxTagName = box[0].tagName;
        var boxTagType = box[0].type;
        var state = $.data(target, "validatebox");
        var opts = $.data(target, "validatebox").options;
        //绑定键盘按下事件，如果是回车事件，让焦点移动到下一个元素，由于该实现有问题，所以重新实现了一次
        state.validating = false;
        //绑定获得焦点和移除焦点事件
        box.unbind(".validatebox").bind("keydown.validatebox", function(e) {
            var dummyInput = $("<input type='hidden' class='dummy-input'>");
            if (e.keyCode !== 13) {
                return;
            }
            if (!opts.focus2Next) {
                dummyInput.insertAfter(box).focus().remove();
                return;
            }
            e.stopPropagation();
            e.preventDefault();
            if (moveNext(target)) {
                return;
            }
            var $nextBtn = $(this).closest("form").find("a.l-btn:first");
            if (!$nextBtn.length) {
                $nextBtn = $(this).closest("div.window").find("a.l-btn:first");
            }
            if (!$nextBtn.length) {
                $nextBtn = $(this).closest("div.panel").find("a.l-btn:first");
            }
            if ($nextBtn.length) {
                if (!$nextBtn.attr("href")) {
                    $nextBtn.attr("href", "javascript:void(0)");
                }
                $nextBtn.focus().click();
            } else {
                dummyInput.insertAfter(box).focus().remove();
            }
        }).bind("focus.validatebox", function() {
            state.validating = false;
            if (boxTagName.toUpperCase() == "TEXTAREA") {
                box.removeClass("varlidataTextarea-invalid ");
            } else {
                box.removeClass("validatebox-invalid validatebox-grp-invalid");
            }
            if (opts.isFocusRemoveTip) {
                hideTip(target);
            }
        }).bind("blur.validatebox", function() {
            if (opts.removeSingleQuote) {
                box.val(box.val().replace(/\'*/g, ""));
            }
            if (boxTagType != "password") {
                box.attr("title", box.val());
            }
            state.validating = true;
            state.value = undefined;
            (function() {
                if (state.validating) {
                    if (state.value != box.val()) {
                        state.value = box.val();
                        if (opts.validOnBlur) {
                            validate(target);
                        }
                    }
                    setTimeout(function() {
                        state.validating = false;
                        hideTip(target);
                    }, opts.setTimeLong);
                }
            })();
        });
        // 给validatebox控制增加chars参数，用于控制输入框可输入的内容的范围，chars是一个字符串，表示允许被输入的字符的范围。
        if (opts.chars && opts.chars.length > 0) {
            try {
                var reg = new RegExp("[^" + opts.chars + "]", "g");
                box.bind("keyup.validate-permit", function(e) {
                    this.value = this.value.replace(reg, "");
                });
            } catch (e) {
                alert("输入项" + opts.field + "的chars参数配置错误，正确样例如：<br>\\\\d<br>\\\\w<br>\\\\d.<br>等");
            }
        }
    }
    function showTip(target, msg) {
        var box = $(target), pos = box.offset(), obj = $.data(target, "validatebox") || {}, tip, tipTop, tipContent, tipPointer;
        if (0 === pos.left && 0 === pos.top || obj && obj.tip) {
            return;
        }
        msg = msg || obj.message || "";
        tip = obj.tip || $('<div class="validatebox-tip"><div class="validatebox-tip-content"></div><div class="validatebox-tip-pointer"></div></div>').appendTo("body");
        obj.tip = obj.tip || tip;
        tipContent = $(".validatebox-tip-content", tip);
        tipContent.html(msg);
        tipTop = pos.top - tip.outerHeight();
        if (tipTop < 0) {
            tipPointer = $(".validatebox-tip-pointer", tip);
            tipContent.insertAfter(tipPointer);
            tipPointer.removeClass().addClass("validatebox-tip-pointer-down");
            tipTop = box.outerHeight() + pos.top;
        }
        tip.css({
            left: pos.left,
            top: tipTop
        }).show();
        box.addClass("validatebox-invalid");
        setTimeout(function() {
            tip.fadeOut();
            box.removeClass("validatebox-invalid");
        }, obj.options.setTimeLong);
    }
    function hideTip(target) {
        var obj = $.data(target, "validatebox");
        if (!obj) return;
        var tip = obj.tip;
        if (tip) {
            tip.remove();
            obj.tip = null;
        }
    }
    //验证函数
    function validate(target, keyflag) {
        var opts = $.data(target, "validatebox").options;
        if (!$(target).is(":visible") && !opts.hideValidFlag || $(target).prop("disabled") || "hidden" === $(target).attr("type")) {
            return true;
        }
        var tip = $.data(target, "validatebox").tip;
        var box = $(target);
        var value = box.val() || "";
        //得到用户输入的值
        value = value.replace(/(^\s*)|(\s*$)/g, "");
        //去掉首尾空格
        var boxTagName = box[0].tagName;
        function setTipMessage(msg) {
            $.data(target, "validatebox").message = msg;
        }
        //是否必须输入
        if ("true" === String(opts.required) && !keyflag) {
            //注释下面语句 否则不显示校验信息
            //if (value == "" && !$(target).hasClass("combo-text")) {
            if (opts.showMessage && value == "") {
                if (boxTagName.toUpperCase() == "TEXTAREA") {
                    box.addClass("varlidataTextarea-invalid");
                } else {
                    box.addClass("validatebox-invalid");
                    setTimeout(function() {
                        box.removeClass("validatebox-invalid");
                    }, opts.setTimeLong);
                }
                setTipMessage(opts.missingMessage);
                showTip(target);
                return false;
            }
            if (!opts.showMessage && value == "") {
                if (boxTagName.toUpperCase() == "TEXTAREA") {
                    box.addClass("varlidataTextarea-invalid");
                } else {
                    box.addClass("validatebox-invalid");
                    setTimeout(function() {
                        box.removeClass("validatebox-invalid");
                    }, opts.setTimeLong);
                }
                return false;
            }
        }
        var isOnePass = false;
        //是否有一个通过校验
        var isAllPass = true;
        //是否全部通过校验
        if (opts.validType) {
            var validCfgs = opts.validType.split(";");
            while (validCfgs[0]) {
                var result = /([a-zA-Z_]+)(.*)/.exec(validCfgs.shift());
                var rule = opts.rules[result[1]];
                if ((value || result[1] == "begDate" || result[1] == "endDate") && rule) {
                    if ((result[1] == "money" || result[1] == "date" || result[1] == "begDate" || result[1] == "endDate") && opts.groupSeparator) {
                        value = value.replace(new RegExp("\\" + opts.groupSeparator, "g"), "");
                    }
                    var param = "eqValue" === result[1] ? $(target).data("kui-eqValue") : eval(result[2]);
                    if (!rule["validator"](value, param, target)) {
                        if (opts.validType == "date") {
                            $(target).parent("span.datebox").addClass("dateboxerror");
                        } else {
                            if (boxTagName.toUpperCase() == "TEXTAREA") {
                                box.addClass("varlidataTextarea-invalid");
                            } else {
                                box.addClass("validatebox-invalid");
                            }
                        }
                        var message = rule["message"];
                        if (param) {
                            for (var i = 0; i < param.length; i++) {
                                message = message.replace(new RegExp("\\{" + i + "\\}", "g"), param[i]);
                            }
                        }
                        if (opts.showMessage) {
                            setTipMessage(opts.invalidMessage || message);
                            showTip(target);
                        }
                        isAllPass = false;
                        continue;
                    }
                    isOnePass = true;
                }
                isOnePass = true;
            }
        }
        var flag = opts.isOnePassValid ? isOnePass : isAllPass;
        if (flag) {
            if (boxTagName.toUpperCase() == "TEXTAREA") {
                box.removeClass("varlidataTextarea-invalid");
            } else {
                box.removeClass("validatebox-invalid");
            }
            hideTip(target);
        }
        return flag;
    }
    //设置disabled
    function setDisabled(target, disabled) {
        var opts = $.data(target, "validatebox").options;
        if (String(disabled) == "true" || disabled == "disabled") {
            opts.disabled = true;
            $(target).attr("disabled", true).addClass("form-input-disabled").removeClass("validatebox-invalid").removeClass("varlidataTextarea-invalid");
            hideTip(target);
        } else {
            opts.disabled = false;
            $(target).removeAttr("disabled").removeClass("form-input-disabled");
        }
    }
    //设置最大可输入的长度
    function setMaxLength(target, maxLength) {
        !isNaN(maxLength) && $(target).attr("maxLength", maxLength);
    }
    //清除数据
    function clear(target) {
        target.val("");
    }
    //移到下一个元素
    function moveNext(target) {
        var $jq = $("input.validatebox-text,textarea,a.l-btn"), $nextInput;
        //解决省市联动中数据加载问题
        setTimeout(function() {
            for (var nxtIdx = $jq.index(target) + 1, len = $jq.length; nxtIdx < len; nxtIdx++) {
                if (($nextInput = $($jq[nxtIdx])).is(":visible") && "a" !== $nextInput[0].tagName.toLowerCase() && !$nextInput.prop("disabled")) {
                    $nextInput.focus();
                    return true;
                }
            }
        }, 100);
    }
    function setValue(target, value) {
        var $target = $(target);
        if (!value) {
            $target.val("").removeAttr("title");
            return;
        }
        $target.val(value);
        "password" !== $target.attr("type") && $target.attr("title", value);
    }
    function changeValid(target, validType) {
        var opts = $.data(target, "validatebox").options;
        opts.validType = validType;
    }
    function changeRequired(target, required) {
        var opts = $.data(target, "validatebox").options;
        var jq = $(target);
        opts.required = Boolean(required);
        if (required && !opts.noWarn) {
            jq.closest("span.combo").length ? jq.closest("span.combo").addClass("combo-validatebox-must") : jq.addClass("validatebox-must");
        } else {
            jq.closest("span.combo").length ? jq.closest("span.combo").removeClass("combo-validatebox-must") : jq.removeClass("validatebox-must");
        }
    }
    function setSize(target, width) {
        $(target)._outerWidth(width);
    }
});
