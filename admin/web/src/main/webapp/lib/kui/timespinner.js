/**
 * timespinner组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/timespinner", [], function(require, exports, module) {
    $.fn.timespinner = function(options, param) {
        if (typeof options == "string") {
            var method = $.fn.timespinner.methods[options], args = [ this ];
            if (method) {
                return method.apply(this, args.concat(Array.prototype.slice.call(arguments, 1)));
            } else {
                return this.spinner(options, param);
            }
        }
        return this.each(function() {
            options = options || {};
            var state = $.data(this, "timespinner");
            if (state) {
                $.extend(state.options, options);
            } else {
                $.data(this, "timespinner", {
                    options: $.extend({}, $.fn.timespinner.defaults, $.fn.timespinner.parseOptions(this), options)
                });
                init(this);
            }
        });
    };
    $.fn.timespinner.parseOptions = function(target) {
        return $.extend({}, $.parser.parseOptions(target));
    };
    $.fn.timespinner.defaults = $.extend({}, $.fn.spinner.defaults, {
        separator: ":",
        //分隔符
        showMinute: true,
        //是否显示分钟
        showSeconds: true,
        //是否显示秒
        showMillisecond: false,
        //是否显示毫秒
        hourMax: 24,
        //时间模式，24小时制还是12小时制
        increment: 1,
        //每次增减值
        suffixStr: "",
        //取值的后缀，用于特殊情况补齐参数位置。
        valueHasSeparator: true,
        //取值是否含有分隔符，赋值时去除分隔符
        setFormat: setFormat,
        //赋值时的格式化函数，可覆盖
        //以下参数用于组件计算，不用外部覆盖
        spin: function(down) {
            onSpin(this, down);
        },
        maxLength: "2",
        //固定长度
        defaultValue: "00",
        //默认初始值为0
        highlight: 0,
        //增减的标志位，0代表hour，1代表分钟，2代表秒钟，3代表毫秒
        formatLength: 2
    });
    $.fn.timespinner.methods = {
        options: function(jq) {
            return $.data(jq[0], "timespinner").options;
        },
        setValue: function(jq, value) {
            return jq.each(function() {
                setValue(this, value);
            });
        },
        getValue: function(jq) {
            return getValue(jq[0]);
        },
        enable: function(jq) {
            return jq.each(function() {
                setDisabled(this, false);
                bindEvents(this);
            });
        },
        disable: function(jq) {
            return jq.each(function() {
                setDisabled(this, true);
                bindEvents(this);
            });
        },
        getHours: function(jq) {
            return getValue(jq[0], 0);
        },
        getMinutes: function(jq) {
            return getValue(jq[0], 1);
        },
        getSeconds: function(jq) {
            return getValue(jq[0], 2);
        },
        getMillisecond: function(jq) {
            return getValue(jq[0], 3);
        }
    };
    //初始化组件
    function init(target) {
        var state = $.data(target, "timespinner"), opts = state.options;
        if (opts.showMinute) {
            if (opts.defaultValue.length == 2) {
                opts.defaultValue += opts.separator + "00";
            }
            opts.highlight = 1;
            opts.formatLength = 4;
            opts.maxLength = 5;
            if (opts.showSeconds) {
                if (opts.defaultValue.length == 5) {
                    opts.defaultValue += opts.separator + "00";
                }
                opts.highlight = 2;
                opts.formatLength = 6;
                opts.maxLength = 8;
                if (opts.millisecond) {
                    if (opts.defaultValue.length == 8) {
                        opts.defaultValue += opts.separator + "000";
                    }
                    opts.highlight = 3;
                    opts.formatLength = 9;
                    opts.maxLength = 12;
                }
            }
        }
        $(target).spinner($.extend({}, opts, {
            spinnername: target.id || target.name || ""
        }));
        $.extend(state, {
            spinnerText: $.data(target, "spinner").spinnerText
        });
        $(target).val($(target).timespinner("getValue"));
        bindEvents(target);
    }
    /**
     * 绑定事件
     * @param target
     */
    function bindEvents(target) {
        var state = $.data(target, "timespinner"), opts = state.options, spinnerText = state.spinnerText, targetText = spinnerText[0];
        $(targetText).unbind(".timespinner");
        if (!opts.disabled) {
            $(targetText).bind("click.timespinner", function() {
                var start = 0;
                if (this.selectionStart != null) {
                    start = this.selectionStart;
                } else if (this.createTextRange) {
                    var range = target.createTextRange();
                    var s = document.selection.createRange();
                    s.setEndPoint("StartToStart", range);
                    start = s.text.length;
                }
                if (start >= 0 && start <= 2) {
                    opts.highlight = 0;
                } else if (start >= 3 && start <= 5) {
                    opts.highlight = 1;
                } else if (start >= 6 && start <= 8) {
                    opts.highlight = 2;
                }
                highlight(target);
            }).bind("blur.timespinner", function() {
                repairText(target);
                $(target).val($(target).timespinner("getValue"));
            }).bind("keyup.timespinner", function(e) {
                if (e.keyCode == 38) {
                    onSpin(target, false);
                }
                if (e.keyCode == 40) {
                    onSpin(target, true);
                }
            });
        }
    }
    /**
     * 时间变化
     * @param target
     * @param arrow true是向下 false是向上
     */
    function onSpin(target, arrow) {
        var state = $.data(target, "timespinner"), opts = state.options, val = $(target).spinner("getValue"), vv = val.split(opts.separator);
        for (var i = 0; i < vv.length; i++) {
            vv[i] = parseInt(vv[i], 10) || 0;
        }
        if (arrow == false) {
            vv[opts.highlight] += Number(opts.increment);
        } else {
            vv[opts.highlight] -= Number(opts.increment);
        }
        $(target).spinner("setValue", repairVal(vv, opts.hourMax).join(opts.separator));
        highlight(target);
    }
    /**
     * 高亮选中的项
     */
    function highlight(target) {
        var state = $.data(target, "timespinner"), opts = state.options, spinnerText = state.spinnerText, targetText = spinnerText[0];
        var start = 0, end = 0;
        if (opts.highlight == 0) {
            start = 0;
            end = 2;
        } else if (opts.highlight == 1) {
            start = 3;
            end = 5;
        } else if (opts.highlight == 2) {
            start = 6;
            end = 8;
        } else if (opts.highlight == 3) {
            start = 9;
            end = 12;
        }
        if (targetText.selectionStart != null) {
            targetText.setSelectionRange(start, end);
        } else if (targetText.createTextRange) {
            var range = targetText.createTextRange();
            range.collapse();
            range.moveEnd("character", end);
            range.moveStart("character", start);
            range.select();
        }
        $(targetText).focus();
    }
    /**
     * 修正值
     * @param arr
     * @param hourMax
     * @returns {*}
     */
    function repairVal(arr, hourMax) {
        for (var i = arr.length - 1; i >= 0; i--) {
            if (i == 3) {
                if (arr[i] > 999) {
                    arr[i] -= 1e3;
                    arr[i - 1]++;
                }
                if (arr[i] < 0) {
                    arr[i] += 1e3;
                    arr[i - 1]--;
                }
            }
            if (i == 2 || i == 1) {
                if (arr[i] > 59) {
                    arr[i] -= 60;
                    arr[i - 1]++;
                }
                if (arr[i] < 0) {
                    arr[i] += 60;
                    arr[i - 1]--;
                }
            }
            if (i == 0) {
                if (arr[i] > hourMax) {
                    arr[i] -= hourMax;
                }
                if (arr[i] < 0) {
                    arr[i] += hourMax;
                }
            }
        }
        for (var i = 0; i < arr.length; i++) {
            if (i == 3) {
                arr[i] = addZero(arr[i], 3);
            } else {
                arr[i] = addZero(arr[i], 2);
            }
        }
        return arr;
    }
    /**
     * 修正输入框
     * @param target
     */
    function repairText(target) {
        var state = $.data(target, "timespinner"), opts = state.options, spinnerText = state.spinnerText, val = spinnerText.val(), vv = [];
        while (val.length < opts.maxLength) {
            if (val.indexOf(opts.separator) == -1) {
                vv[0] = val.substr(0, 2);
                if (opts.showMinute) {
                    vv[1] = val.substr(2, 2);
                    if (opts.showSeconds) {
                        vv[2] = val.substr(4, 2);
                        if (opts.millisecond) {
                            vv[3] = val.substr(6, 3);
                        }
                    }
                }
            } else {
                vv = val.split(opts.separator);
            }
            for (var i = 0; i < vv.length; i++) {
                if (i == 3) {
                    vv[i] = addZero(vv[i], 3);
                } else {
                    vv[i] = addZero(vv[i], 2);
                }
            }
            val = vv.join(opts.separator);
            if (val.length < opts.maxLength) {
                val += opts.separator;
            }
        }
        spinnerText.val(val);
    }
    /**
     * 字符串补零
     * @param val
     * @param len 补齐长度
     */
    function addZero(val, len) {
        len = len || 2;
        val += "";
        while (val.length < len) {
            val = "0" + val;
        }
        return val;
    }
    /**
     * 启用或者禁用组件
     * @param target
     * @param disabled
     */
    function setDisabled(target, disabled) {
        var state = $.data(target, "timespinner"), opts = state.options;
        if (disabled) {
            opts.disabled = true;
            $(target).spinner("disable");
        } else {
            opts.disabled = false;
            $(target).spinner("enable");
        }
    }
    //赋值
    function setValue(target, value) {
        var state = $.data(target, "timespinner"), opts = state.options, spinnerText = state.spinnerText;
        $(target).val(value);
        if (opts.valueHasSeparator) {
            value = value.split(opts.separator).join("");
        }
        if (opts.suffixStr) {
            value = value.substr(0, value.length - opts.suffixStr.length);
        }
        spinnerText.val(opts.setFormat.call(target, target, value));
    }
    //取值
    function getValue(target, type) {
        var state = $.data(target, "timespinner"), spinnerText = state.spinnerText, opts = state.options, value = spinnerText.val(), vv = value.split(opts.separator);
        if (isNaN(type)) {
            if (opts.valueHasSeparator) {
                return value + opts.suffixStr;
            } else {
                return vv.join("") + opts.suffixStr;
            }
        } else {
            return vv[type] || "";
        }
    }
    //赋值的默认格式化函数
    function setFormat(target, value) {
        var state = $.data(target, "timespinner"), opts = state.options, vv = [], len = opts.formatLength;
        value = addZero(value, len);
        vv[0] = value.substr(0, 2);
        if (opts.showMinute) {
            vv[1] = value.substr(2, 2);
            if (opts.showSeconds) {
                vv[2] = value.substr(4, 2);
                if (opts.millisecond) {
                    vv[3] = value.substr(6, 3);
                }
            }
        }
        return vv.join(opts.separator);
    }
});
