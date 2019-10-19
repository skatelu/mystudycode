/**
 * numberbox组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/numberbox", [], function(require, exports, module) {
    $.fn.numberbox = function(options, param) {
        if (typeof options == "string") {
            var method = $.fn.numberbox.methods[options];
            if (method) {
                return method(this, param);
            } else {
                return this.validatebox(options, param);
            }
        }
        options = options || {};
        return this.each(function() {
            var state = $.data(this, "numberbox");
            if (state) {
                $.extend(state.options, options);
            } else {
                var t = $(this);
                init(this);
                state = $.data(this, "numberbox", {
                    options: $.extend({}, $.fn.numberbox.defaults, $.fn.numberbox.parseOptions(this), options)
                });
                t.removeAttr("disabled");
                $(this).css({
                    imeMode: "disabled"
                });
            }
            setValue(this, state.options.value);
            setDisabled(this, state.options.disabled);
            bindEvents(this);
            validate(this);
        });
    };
    $.fn.numberbox.methods = {
        options: function(jq) {
            var opts = $.data(jq[0], "numberbox").options;
            return $.extend(opts, {
                value: jq.val()
            });
        },
        setValue: function(jq, value) {
            return jq.each(function() {
                setValue(this, value);
            });
        },
        getValue: function(jq, value) {
            var opts = $.data(jq[0], "numberbox").options;
            return opts.moneyUnits === "1" ? $.trim(jq.val().replace(/,/g, "")) * 1e4 : $.trim(jq.val().replace(/,/g, ""));
        },
        reset: function(jq) {
            return jq.each(function() {
                if ($.data(jq[0], "numberbox")) {
                    var opts = $.data(jq[0], "numberbox").options;
                    $(this).val(opts.value || "").numberbox("fix");
                    var $span = $(this).next("span.to-chinese");
                    if ($span) $span.remove();
                    $(this).removeClass("validatebox-invalid");
                }
            });
        },
        fix: function(jq) {
            return jq.each(function() {
                fixValue(this);
            });
        },
        disable: function(jq) {
            return jq.each(function() {
                setDisabled(this, true);
            });
        },
        enable: function(jq) {
            return jq.each(function() {
                setDisabled(this, false);
            });
        }
    };
    $.fn.numberbox.parseOptions = function(target) {
        var t = $(target);
        return $.extend({}, $.fn.validatebox.parseOptions(target), {
            disabled: t.attr("disabled") ? true : undefined,
            min: t.attr("min") == "0" ? 0 : parseFloat(t.attr("min")) || undefined,
            max: t.attr("max") == "0" ? 0 : parseFloat(t.attr("max")) || undefined,
            precision: parseInt(t.attr("precision")) || undefined
        });
    };
    $.fn.numberbox.defaults = {
        disabled: false,
        toChinese: null,
        min: undefined,
        max: undefined,
        precision: 0,
        setThousandChar: true,
        //是否设置千位符
        textPosition: "bottom",
        moneyUnits: "0",
        onChange: function(value) {},
        onKeyup: function() {},
        onBlur: function() {}
    };
    function setValue(target, value) {
        var opts = $.data(target, "numberbox").options;
        if (opts.moneyUnits === "1") value = Number(value) / 1e4;
        $(target).val(value);
        fixValue(target);
        addSeparator(target);
    }
    function toFixedStr(val, prec) {
        return val && prec ? Number(val).toFixed(prec) : val;
    }
    function fixValue(target) {
        var opts = $.data(target, "numberbox").options;
        var num = $(target).val();
        if (num.indexOf(",") > 0) num = num.replace(/,/g, "");
        var val = num;
        if (isNaN(val)) {
            $(target).val("");
            $(target).nextAll("input.numberbox-value").val("");
            return;
        }
        opts.min = opts.min * 1;
        if (opts.min != null && opts.min != undefined && val < opts.min) {
            $(target).val(opts.min.toFixed(opts.precision));
        } else if (opts.max != null && opts.max != undefined && val > opts.max) {
            $(target).val(toFixedStr(num, opts.precision));
        } else if (opts.precision) {
            $(target).val(toFixedStr(num, opts.precision));
        } else {
            $(target).val(String(opts.setThousandChar) == "true" ? formatNumber(num) : num);
        }
        if ($(target).val()) {
            $(target).nextAll("input.numberbox-value").val($(target).val().replace(/,/g, ""));
        }
        opts.onChange.call(target, val);
    }
    function addSeparator(target) {
        var opts = $.data(target, "numberbox").options;
        var num = $(target).val();
        if (num.indexOf(",") > 0) num = num.replace(/,/g, "");
        if (!$.isNumeric(num) || /(^\s+)|(\s+$)/g.test(num)) {
            $(target).val("");
            $(target).nextAll("input.numberbox-value").val("");
            return;
        }
        if (opts.min != null && opts.min != undefined && Number(num) < opts.min) {
            $(target).val(parseFloat(opts.min).toFixed(opts.precision));
        } else if (opts.max != null && opts.max != undefined && Number(num) > opts.max) {
            $(target).val(parseFloat(opts.max).toFixed(opts.precision));
        } else {
            $(target).val(String(opts.setThousandChar) == "true" ? formatNumber(num) : num);
        }
        if ($(target).val()) {
            $(target).nextAll("input.numberbox-value").val($(target).val().replace(/,/g, ""));
        }
        if (opts.toChinese == "number") {
            showMoneyText(target, toChineseNumber(parseFloat(num)));
        } else if (opts.toChinese == "money") {
            if (opts.moneyUnits === "1") num = parseFloat(num) * 1e4;
            showMoneyText(target, toChineseMoney(parseFloat(num)));
        }
        opts.onChange.call(target, num);
    }
    function bindEvents(target) {
        var opts = $.data(target, "numberbox").options;
        $(target).unbind(".numberbox");
        $(target).bind("keypress.numberbox", function(e) {
            if (e.which == 45) {
                //-
                return true;
            }
            if (e.which == 46) {
                //.
                return true;
            } else if (e.which >= 48 && e.which <= 57 && e.ctrlKey == false && e.shiftKey == false || e.which == 0 || e.which == 8) {
                return true;
            } else if (e.ctrlKey == true && (e.which == 99 || e.which == 118)) {
                return true;
            } else if (e.which == 37 || e.which == 39) {
                return true;
            } else {
                return false;
            }
        }).bind("paste.numberbox", function() {
            if (window.clipboardData) {
                var s = clipboardData.getData("text");
                if (!/\D/.test(s)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                var element = this;
                setTimeout(function() {
                    fixValue(target);
                }, 100);
            }
        }).bind("dragenter.numberbox", function() {
            return false;
        }).bind("blur.numberbox", function() {
            fixValue(target);
            "password" !== $(target).attr("type") && $(target).attr("title", $(target).val());
            addSeparator(target);
            // 四舍五入后更新中文
            opts.onBlur.call(target);
        }).bind("keyup.numberbox", function(e) {
            // 可移动光标
            if (e.which >= 48 && e.which <= 57 || e.which == 37 || e.which == 39 || e.which == 189 || e.which == 46 || !(e.ctrlKey == false && e.shiftKey == false) && e.which != 0 || e.which == 8) {
                return false;
            }
            addSeparator(target);
            opts.onKeyup.call(target);
        });
    }
    //添加千位符
    function formatNumber(num) {
        if (!/^(\+|-)?(\d+)(,\d{3})*(\.|\.\d+)?$/.test(num)) {
            return num;
        }
        var a = RegExp.$1, b = RegExp.$2, c = RegExp.$4;
        var re = /(\d)(\d{3})(,|$)/;
        while (re.test(b)) {
            b = b.replace(re, "$1,$2$3");
        }
        return a + "" + b + "" + c;
    }
    function init(target) {
        var target = $(target).addClass("numberbox-text");
        var name = target.attr("name");
        if (name) {
            target.after("<input type='hidden' class='numberbox-value' name='" + name + "'>");
            target.removeAttr("name").attr("numberboxName", name);
        }
    }
    function validate(target) {
        var opts = $.data(target, "numberbox").options;
        $(target).validatebox(opts);
    }
    function setDisabled(target, disabled) {
        var opts = $.data(target, "numberbox").options;
        if (disabled) {
            opts.disabled = true;
            $(target).attr("disabled", true);
            $(target).addClass("form-input-disabled");
        } else {
            opts.disabled = false;
            $(target).removeAttr("disabled");
            $(target).removeClass("form-input-disabled");
        }
    }
    //金额大写转换函数
    function toChineseMoney(n) {
        var s = "负";
        var temp = 0;
        var MAX = 9999999999999.99;
        var MIN = -9999999999999.99;
        if (n > MAX) {
            ret = "数据过大！";
        } else if (n < MIN) {
            ret = "数据过小！";
        } else {
            if (Number(n) < 0) {
                temp = n;
                n = String(n).substring(1, String(n).length);
            }
            var unit = "万仟佰拾亿仟佰拾万仟佰拾元角分", str = "";
            n += "00";
            var p = n.indexOf(".");
            if (p >= 0) n = n.substring(0, p) + n.substr(p + 1, 2);
            unit = unit.substr(unit.length - n.length);
            for (var i = 0; i < n.length; i++) str += "零壹贰叁肆伍陆柒捌玖".charAt(n.charAt(i)) + unit.charAt(i);
            if (str == "零元零角零分") {
                ret = "零元";
            } else {
                var ret = str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万/g, "$1").replace(/^元零?|零分/g, "").replace(/元$/g, "元");
            }
        }
        return ret && Number(temp) < 0 ? s + ret : ret;
    }
    //数字大写转换函数
    function toChineseNumber(n) {
        var s = "负";
        var temp = 0;
        var MAX = 9999999999999.99;
        var MIN = -9999999999999.99;
        if (n > MAX) {
            ret = "数据过大！";
        } else if (n < MIN) {
            ret = "数据过小！";
        } else {
            if (Number(n) < 0) {
                temp = n;
                n = String(n).substring(1, String(n).length);
            }
            var unit = "万仟佰拾亿仟佰拾万仟佰拾点", str = "";
            n += "00";
            var p = n.indexOf(".");
            if (p >= 0) n = n.substring(0, p) + n.substr(p + 1, 2);
            unit = unit.substr(unit.length - n.length + 2);
            for (var i = 0; i < n.length; i++) str += "零壹贰叁肆伍陆柒捌玖".charAt(n.charAt(i)) + unit.charAt(i);
            if (str == "零点零零") {
                ret = "零";
            } else {
                var ret = str.replace(/点零零/g, "").replace(/零(仟|佰|拾)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿)/g, "$1").replace(/(亿)万/g, "$1").replace(/零$/g, "");
            }
        }
        return ret && Number(temp) < 0 ? s + ret : ret;
    }
    //显示金额/数字中文大写文本
    function showMoneyText(target, text) {
        var $target = $(target), opts = $.data(target, "numberbox").options, $span = $target.next("span"), $moneyText = $("<span class='to-chinese'>");
        $target.closest("td").width("auto");
        $span.hasClass("validatebox-must") ? $span.next("span").remove() : $span.remove();
        if (opts.textPosition === "bottom") {
            $moneyText.css("margin-left", $target.prev().width() + 10 || 0);
        } else if (opts.textPosition === "right") {
            $moneyText.addClass("inline");
        }
        $target.parent().find("span.to-chinese").remove();
        $target.after($moneyText.text(text));
    }
});
