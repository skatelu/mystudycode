/**
 * numberspinner组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/numberspinner", [], function(require, exports, module) {
    $.fn.numberspinner = function(options, param) {
        if (typeof options == "string") {
            var method = $.fn.numberspinner.methods[options];
            if (method) {
                return method(this, param);
            } else {
                return this.spinner(options, param);
            }
        }
        return this.each(function() {
            options = options || {};
            var state = $.data(this, "numberspinner");
            if (state) {
                $.extend(state.options, options);
            } else {
                $.data(this, "numberspinner", {
                    options: $.extend({}, $.fn.numberspinner.defaults, $.fn.numberspinner.parseOptions(this), options)
                });
            }
            init(this);
        });
    };
    $.fn.numberspinner.methods = {
        options: function(jq) {
            return $.data(jq[0], "numberspinner").options;
        },
        setValue: function(jq, value) {
            return jq.each(function() {
                setValue(this, value);
            });
        },
        getValue: function(jq) {
            return getValue(jq[0]);
        }
    };
    $.fn.numberspinner.parseOptions = function(target) {
        return $.extend({}, $.parser.parseOptions(target));
    };
    $.fn.numberspinner.defaults = $.extend({}, $.fn.spinner.defaults, $.fn.numberbox.defaults, {
        spin: function(down) {
            onSpin(this, down);
        },
        increment: 1
    });
    //初始化组件
    function init(target) {
        var state = $.data(target, "numberspinner"), opts = state.options;
        $(target).spinner($.extend({}, opts, {
            spinnername: target.id || target.name || ""
        }));
        $.extend(state, {
            spinnerText: $.data(target, "spinner").spinnerText
        });
        state.spinnerText.numberbox(opts);
    }
    /**
     * 上下箭头事件
     * @param target
     * @param down true代表下
     */
    function onSpin(target, down) {
        var state = $.data(target, "numberspinner"), opts = state.options, spinnerText = state.spinnerText, val = $(target).spinner("getValue"), dotMax = val.indexOf(".") < 0 ? 0 : val.length - 1 - val.indexOf(".");
        val = val.replace(/,/g, "");
        val = parseFloat(val) || 0;
        if (down == false) {
            val += Number(opts.increment);
        } else {
            val -= Number(opts.increment);
        }
        val = parseFloat(val).toFixed(dotMax);
        $(target).val(val);
        spinnerText.val(val).numberbox("fix");
    }
    //赋值
    function setValue(target, value) {
        var state = $.data(target, "numberspinner"), opts = state.options, spinnerText = state.spinnerText;
        $(target).val(value);
        spinnerText.val(value).numberbox("fix");
    }
    //取值
    function getValue(target) {
        //return $(target).val(); //用这种获取方法获取不到手输的值  @xuml
        return $(target).spinner("getValue");
    }
});
