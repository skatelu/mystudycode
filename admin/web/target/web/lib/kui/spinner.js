/**
 * spinner组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/spinner", [], function(require, exports, module) {
    $.fn.spinner = function(options, param) {
        if (typeof options == "string") {
            var method = $.fn.spinner.methods[options], args = [ this ];
            if (method) {
                return method.apply(this, args.concat(Array.prototype.slice.call(arguments, 1)));
            } else {
                return this.validatebox(options, param);
            }
        }
        return this.each(function() {
            options = options || {};
            var state = $.data(this, "spinner");
            if (state) {
                $.extend(state.options, options);
            } else {
                $.data(this, "spinner", {
                    options: $.extend({}, $.fn.spinner.defaults, $.fn.spinner.parseOptions(this), options)
                });
                init(this);
            }
        });
    };
    $.fn.spinner.defaults = $.extend({}, $.fn.validatebox.defaults, {
        width: "auto",
        //组件宽度
        spinnername: "",
        value: "",
        //值
        defaultValue: "",
        //默认值
        editable: true,
        //是否可编辑
        disabled: false,
        //是否禁用
        spin: $.noop,
        //箭头回调事件
        onSpinUp: $.noop,
        //上箭头回调函数
        onSpinDown: $.noop
    });
    $.fn.spinner.parseOptions = function(target) {
        //只获取kui-options配置的属性，其他属性设置不管，配置地方过多，容易混淆。
        return $.extend({}, $.parser.parseOptions(target));
    };
    //外部事件
    $.fn.spinner.methods = {
        //获取组件属性
        options: function(jq) {
            return $.data(jq[0], "spinner").options;
        },
        //销毁组件
        destroy: function(jq) {
            return jq.each(function() {
                var spinner = $.data(this, "spinner").spinner;
                spinner.remove();
                $(this).remove();
            });
        },
        //设置组件宽度
        resize: function(jq, width) {
            return jq.each(function() {
                setSize(this, width);
            });
        },
        //启用组件
        enable: function(jq) {
            return jq.each(function() {
                setDisabled(this, false);
                bindEvents(this);
            });
        },
        //禁用组件
        disable: function(jq) {
            return jq.each(function() {
                setDisabled(this, true);
                bindEvents(this);
            });
        },
        //取值
        getValue: function(jq) {
            return getValue(jq[0]);
        },
        //赋值
        setValue: function(jq, value) {
            return jq.each(function() {
                setValue(this, value);
            });
        },
        //清空
        clear: function(jq) {
            return jq.each(function() {
                clear(this);
            });
        },
        //重置
        reset: function(jq) {
            return jq.each(function() {
                reset(this);
            });
        }
    };
    //初始化控件
    function init(target) {
        var state = $.data(target, "spinner"), opts = state.options, spinner = $('<span class="spinner"></span>'), spinnerText = $("<input class='spinner-text' autocomplete='off' type='text'/>").appendTo(spinner), spinnerArrow = $('<span class="spinner-arrow"><span class="spinner-arrow-up"></span><span class="spinner-arrow-down"></span></span>').appendTo(spinner);
        spinner.insertAfter($(target));
        $(target).attr("spinnername", opts.spinnername).hide();
        $.extend(state, {
            spinner: spinner,
            spinnerText: spinnerText,
            spinnerArrow: spinnerArrow
        });
        spinnerText.validatebox(opts).val(opts.defaultValue || opts.value).attr("readonly", !opts.editable);
        setSize(target);
        setDisabled(target, state.options.disabled);
        bindEvents(target);
    }
    //设置宽度
    function setSize(target, width) {
        var state = $.data(target, "spinner"), opts = state.options, spinner = state.spinner, spinnerArrow = state.spinnerArrow, spinnerText = state.spinnerText;
        if (width) opts.width = width;
        if (isNaN(opts.width)) {
            opts.width = $(target).outerWidth() - 8;
        }
        var arrowWidth = spinnerArrow.outerWidth() || 18;
        width = opts.width - arrowWidth;
        spinnerText.width(width);
    }
    //禁用或者启用
    function setDisabled(target, disabled) {
        var state = $.data(target, "spinner"), opts = state.options, spinnerText = state.spinnerText;
        if (disabled) {
            opts.disabled = true;
            spinnerText.validatebox("disabled");
        } else {
            opts.disabled = false;
            spinnerText.validatebox("enabled");
        }
    }
    //绑定事件
    function bindEvents(target) {
        var state = $.data(target, "spinner"), opts = state.options, spinner = state.spinner, spinnerArrow = state.spinnerArrow;
        spinnerArrow.find(".spinner-arrow-up,.spinner-arrow-down").unbind(".spinner");
        if (!opts.disabled) {
            spinnerArrow.find(".spinner-arrow-up,.spinner-arrow-down").bind("mouseenter.spinner", function() {
                $(this).addClass("spinner-arrow-hover");
            }).bind("mouseleave.spinner", function() {
                $(this).removeClass("spinner-arrow-hover");
            });
            spinnerArrow.find(".spinner-arrow-up").bind("click.spinner", function() {
                opts.spin.call(target, false);
                opts.onSpinUp.call(target);
            });
            spinnerArrow.find(".spinner-arrow-down").bind("click.spinner", function() {
                opts.spin.call(target, true);
                opts.onSpinDown.call(target);
            });
        }
    }
    //取值
    function getValue(target) {
        return $.data(target, "spinner").spinnerText.val();
    }
    //赋值
    function setValue(target, value) {
        $.data(target, "spinner").spinnerText.val(value);
    }
    //清除
    function clear(target) {
        $.data(target, "spinner").spinnerText.val("");
    }
    //重置
    function reset(target) {
        var state = $.data(target, "spinner"), opts = state.options;
        state.spinnerText.val(opts.defaultValue);
    }
});
