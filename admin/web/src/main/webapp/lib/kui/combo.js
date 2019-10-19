/**
 *  combo组件
 *  Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/combo", [], function(require, exports, module) {
    $.fn.combo = function(options, context) {
        if (typeof options == "string") {
            //return $.fn.combo.methods[options](this, context);
            var args = [ this ];
            return $.fn.combo.methods[options].apply(this, args.concat(Array.prototype.slice.call(arguments, 1)));
        }
        options = options || {};
        return this.each(function() {
            options = options || {};
            var state = $.data(this, "combo");
            if (state) {
                $.extend(state.options, options);
            } else {
                state = $.extend({}, $.fn.combo.defaults, $.fn.combo.parseOptions(this), options);
                state = $.data(this, "combo", $.extend({
                    options: state,
                    previousValue: null
                }, init(this, state)));
            }
            setDisabled(this, state.options.disabled);
            setSize(this, state.options.width != "auto" ? state.options.width : $.browser.chrome ? 120 : 122);
            bindEvents(this);
            if (!getValues(this).length) {
                select(this);
            }
        });
    };
    $.fn.combo.methods = {
        options: function(jq) {
            return $.data(jq[0], "combo").options;
        },
        //获取panel
        panel: function(jq) {
            return $.data(jq[0], "combo").panel;
        },
        //获取文本框对象
        textbox: function(jq) {
            return $.data(jq[0], "combo").combo.find("input.combo-text");
        },
        destroy: function(jq) {
            return jq.each(function() {
                destroy(this);
            });
        },
        resize: function(jq, width) {
            return jq.each(function() {
                setSize(this, width);
            });
        },
        //显示下拉面板
        showPanel: function(jq) {
            return jq.each(function() {
                showPanel(this);
            });
        },
        //隐藏下拉面板
        hidePanel: function(jq) {
            return jq.each(function() {
                hidePanel(this);
            });
        },
        //禁用组合框组件
        disable: function(jq) {
            return jq.each(function() {
                setDisabled(this, true);
                bindEvents(this);
            });
        },
        //启用组合框组件
        enable: function(jq) {
            return jq.each(function() {
                setDisabled(this, false);
                bindEvents(this);
            });
        },
        changeRequired: function(jq, value) {
            return jq.each(function() {
                var opts = $(this).data("combo").options, $comboText = $(this).data("combo").comboText;
                opts.required = !!value;
                $comboText.validatebox("changeRequired", value);
            });
        },
        validate: function(jq) {
            return jq.each(function() {
                validate(this, true);
            });
        },
        isValid: function(jq) {
            var input = $.data(jq[0], "combo").combo.find("input.combo-text");
            return input.validatebox("isValid");
        },
        clear: function(jq) {
            return jq.each(function() {
                clear(this);
            });
        },
        reset: function(jq) {
            return jq.each(function() {
                reset(this);
            });
        },
        getText: function(jq) {
            return getText(jq[0]);
        },
        setText: function(jq, text) {
            return jq.each(function() {
                setText(this, text);
            });
        },
        setTexts: function(jq, text) {
            return jq.each(function() {
                setTexts(this, text);
            });
        },
        getValues: function(jq) {
            return getValues(jq[0]);
        },
        setValues: function(jq, values, remainText) {
            return jq.each(function() {
                setValues(this, values, remainText);
            });
        },
        getValue: function(jq) {
            return getValue(jq[0]);
        },
        setValue: function(jq, value) {
            return jq.each(function() {
                setValue(this, value);
            });
        },
        getName: function(jq) {
            return getName(jq[0]);
        },
        getNames: function(jq) {
            return getNames(jq[0]);
        },
        showTip: function(jq, msg, global) {
            return jq.each(function() {
                showTip(this, msg, global);
            });
        }
    };
    $.fn.combo.parseOptions = function(target) {
        var t = $(target);
        return $.extend({}, $.fn.validatebox.parseOptions(target), $.parser.parseOptions(target, [ "width", "separator", {
            panelWidth: "number",
            editable: "boolean",
            hasDownArrow: "boolean",
            isClear: "boolean",
            delay: "number"
        } ]), {
            panelHeight: t.attr("panelHeight") == "auto" ? "auto" : parseInt(t.attr("panelHeight")) || undefined,
            multiple: "true" === String(t.attr("multiple")) ? true : undefined,
            disabled: t.attr("disabled") ? true : undefined,
            isClear: t.attr("true") ? true : undefined,
            value: t.val() || undefined
        });
    };
    $.fn.combo.defaults = $.extend({}, $.fn.validatebox.defaults, {
        width: "auto",
        panelWidth: null,
        panelHeight: "auto",
        multiple: false,
        separator: ",",
        editable: false,
        acceptInput: false,
        disabled: false,
        isClear: true,
        isShowSelectPanel: true,
        hasDownArrow: true,
        value: "",
        defaultValue: "",
        closePanel: true,
        delay: 200,
        keyHandler: {
            up: function() {},
            down: function() {},
            enter: function() {},
            query: function(q) {}
        },
        onShowPanel: function() {},
        onHidePanel: function() {},
        onChange: function(newValue, oldValue) {},
        onClear: function() {},
        onBlur: $.noop
    });
    function setSize(target, width) {
        var opts = $.data(target, "combo").options;
        var combo = $.data(target, "combo").combo;
        var panel = $.data(target, "combo").panel;
        var panelWidth;
        if (width) {
            opts.width = width;
        }
        combo.appendTo("body");
        if (isNaN(opts.width)) {
            opts.width = combo.find("input.combo-text").outerWidth();
        }
        var arrowWidth = 0;
        if (opts.hasDownArrow) {
            arrowWidth = combo.find(".combo-arrow").outerWidth();
        }
        //设置宽度，原来为width(),在combo有padding的情况下将导致combo-text计算错误而换行 @chenkang 2016-09-19
        combo.outerWidth(opts.width);
        if (!opts.hasDownArrow) {
            combo.find("input.combo-text").width(combo.width() - arrowWidth);
        } else {
            combo.find("input.combo-text").width(combo.width() - arrowWidth);
        }
        //全选反选按钮宽度加宽后，需要增加panelWidth @zhaozz 2016-01-06
        panelWidth = opts.multiple && combo.outerWidth() < 140 ? combo.outerWidth() + 20 : combo.outerWidth();
        panel.panel("resize", {
            width: opts.panelWidth ? opts.panelWidth : panelWidth,
            height: opts.panelHeight
        });
        combo.insertAfter(target);
    }
    function init(target, options) {
        var target = $(target).addClass("combo-f").removeAttr("disabled").hide(), comboSpan = $("<span class='combo'></span>").insertAfter(target), comboArrow = $("<span class='combo-arrow'></span>").appendTo(comboSpan)[options.hasDownArrow ? "show" : "hide"](), comboText = $("<input class='combo-text' autocomplete='off' type='" + (target.inputType || "text") + "'/>").appendTo(comboSpan), comboClear = (options.isClear ? $("<span class='clear' title='清空'></span>") : $()).appendTo(comboSpan), comboPanel = $('<div class="combo-panel combo-none-panel"></div>').attr("panel-name", target.attr("name") || target.attr("id")).appendTo("body").panel({
            doSize: false,
            closed: true,
            cls: "combo-panel",
            style: {
                position: "absolute",
                zIndex: 10
            },
            onOpen: function() {
                $(this).panel("resize");
            }
        });
        if (!options.hasDownArrow) {
            comboClear.css("right", "0px");
        }
        comboText.attr("readonly", !options.editable).validatebox(options);
        comboSpan.append("<input type='hidden' class='combo-value'>");
        //默认有一个combo-value
        comboPanel.before($('<div class="combo-selectAll"><span class="select-bg" >全选(F2)</span><span class="select-bg">反选(F3)</span></div>'));
        var name = target.attr("name") || target.attr("id");
        if (name) {
            comboSpan.find("input.combo-value").attr("name", name);
            target.removeAttr("name").attr("comboname", name);
        }
        return {
            combo: comboSpan,
            panel: comboPanel,
            comboArrow: comboArrow,
            comboText: comboText,
            comboClear: comboClear
        };
    }
    function destroy(target) {
        var input = $.data(target, "combo").combo.find("input.combo-text");
        input.validatebox("destroy");
        $.data(target, "combo").panel.panel("destroy");
        $.data(target, "combo").combo.remove();
        $(target).remove();
    }
    function bindEvents(target) {
        var state = $.data(target, "combo"), opts = $.data(target, "combo").options, combo = $.data(target, "combo").combo, panel = $.data(target, "combo").panel, input = $.data(target, "combo").comboText, arrow = $.data(target, "combo").comboArrow, clearVal = $.data(target, "combo").comboClear;
        $.each([ combo, panel, input, arrow, clearVal ], function() {
            this.unbind(".combo");
        });
        if (opts.disabled) {
            return;
        }
        input.bind("mousedown.combo", function(e) {
            e.stopPropagation();
        }).bind("click.combo", function(e) {
            if (panel.is(":visible") && !opts.justFocused) {
                hidePanel(target);
            } else {
                if (opts.closePanel) {
                    $("body>div.panel>div.combo-panel").panel("close");
                }
                if (panel.find("div").length > 0) {
                    panel.show();
                    showPanel(target);
                }
            }
            opts.justFocused = false;
        }).bind("keyup.combo", function(e) {
            switch (e.keyCode) {
              case 8:
                //backspace 解决在IE下输入框设置为readonly后按回退键导致页面回退的问题
                if (!opts.editable) {
                    return false;
                }
                break;
            }
        }).bind("keydown.combo", function(e) {
            switch (e.keyCode) {
              case 37:
                //arrow left
                if (!panel.is(":visible")) {
                    panel.show();
                    showPanel(target);
                }
                if (!opts.multiple && opts.keyHandler.left) {
                    opts.keyHandler.left.call(target);
                }
                break;

              case 38:
                //arrow up
                if (!panel.is(":visible")) {
                    panel.show();
                    showPanel(target);
                }
                if (!opts.multiple) {
                    opts.keyHandler.up.call(target);
                }
                break;

              case 39:
                //arrow right
                if (!panel.is(":visible")) {
                    panel.show();
                    showPanel(target);
                }
                if (!opts.multiple && opts.keyHandler.left) {
                    opts.keyHandler.right.call(target);
                }
                break;

              case 40:
                //arrow down
                if (!panel.is(":visible")) {
                    panel.show();
                    showPanel(target);
                }
                if (!opts.multiple) {
                    opts.keyHandler.down.call(target);
                }
                break;

              case 13:
                //enter
                opts.keyHandler.enter.call(target);
                hidePanel(target);
                e.preventDefault();
                break;

              case 9:
                //tab
                hidePanel(target);
                // tab切换时，收回下拉框
                break;

              case 27:
                //esc
                hidePanel(target);
                break;

              //begin 2016-01-05 @zhaozz   增加 全选、反选、清空的快捷键支持
                case 113:
                //全选
                e.stopPropagation();
                e.preventDefault();
                $(target).data("combobox") && $(target).combobox("selectAll");
                $(target).data("combotree") && $(target).combotree("selectAll");
                $(target).data("combogrid") && $(target).combogrid("selectAll");
                break;

              case 114:
                //反选
                e.stopPropagation();
                e.preventDefault();
                $(target).data("combobox") && $(target).combobox("unSelectAll");
                $(target).data("combotree") && $(target).combotree("unSelectAll");
                $(target).data("combogrid") && $(target).combogrid("unSelectAll");
                break;

              case 115:
                //清空
                e.stopPropagation();
                e.preventDefault();
                $(target).data("combobox") && $(target).combobox("clear");
                $(target).data("combotree") && $(target).combotree("clear");
                $(target).data("combogrid") && $(target).combogrid("clear");
                break;

              //end 2016-01-05 @zhaozz   增加 全选、反选、清空的快捷键支持
                default:
                if (opts.editable) {
                    if (state.timer) {
                        clearTimeout(state.timer);
                    }
                    state.timer = setTimeout(function() {
                        var q = input.val();
                        if (state.previousValue != q) {
                            state.previousValue = q;
                            showPanel(target);
                            opts.keyHandler.query.call(target, input.val());
                            validate(target, true);
                        }
                    }, opts.delay);
                }
            }
        }).bind("focus.combo", function() {
            if (!panel.is(":visible")) {
                showPanel(target);
            }
            opts.justFocused = true;
        }).bind("blur.combo", function(e) {
            combo.removeClass("combo-focus");
            if (opts.editable && opts.acceptInput) {
                setValues(target, [ input.val() ]);
            }
            opts.onBlur.call(target, e);
        });
        combo.bind("mouseenter.combo", function() {
            if ($(target).combo("getValues").length || input.val()) {
                clearVal.show();
            }
        }).bind("mouseleave.combo", function() {
            clearVal.hide();
        });
        clearVal.bind("mousedown.combo", function() {
            clear(target);
            clearVal.hide();
            opts.onClear.call(target);
        });
        arrow.bind("click.combo", function() {
            if (panel.is(":visible")) {
                hidePanel(target);
            } else {
                if (opts.closePanel) $("body>div.panel>div.combo-panel").panel("close");
                removeComboFocus();
                if (panel.find("div").length > 0) {
                    panel.show();
                    showPanel(target);
                    input.focus();
                }
            }
            opts.justFocused = false;
        }).bind("mouseenter.combo", function() {
            $(this).addClass("combo-arrow-hover");
        }).bind("mouseleave.combo", function() {
            $(this).removeClass("combo-arrow-hover");
        }).bind("mousedown.combo", function() {
            return false;
        });
        panel.bind("mousedown.combo", function() {
            var validateOpts = input.data("validatebox").options;
            if (panel.is(":visible")) {
                input.data("validOnBlur", validateOpts.validOnBlur);
                validateOpts.validOnBlur = false;
            }
        }).bind("mouseup.combo", function() {
            input.data("validatebox").options.validOnBlur = input.data("validOnBlur");
        });
    }
    function showPanel(target) {
        var opts = $.data(target, "combo").options;
        var combo = $.data(target, "combo").combo;
        var panel = $.data(target, "combo").panel;
        //如果下拉框里没有数据，则不打开面板
        if (panel.find("div").length === 0) {
            return;
        }
        if ($.fn.window) {
            panel.panel("panel").css("z-index", $.fn.window.defaults.zIndex++);
        }
        if (!opts.multiple || !opts.isShowSelectPanel) {
            $(".combo-selectAll").hide();
        } else {
            $(".combo-selectAll").show();
        }
        combo.addClass("combo-focus");
        panel.panel("open");
        opts.onShowPanel.call(target);
        (function() {
            if (panel.is(":visible")) {
                var top = combo.offset().top + combo.outerHeight(), left = combo.offset().left;
                if (top + panel.parent().outerHeight() > $(window).height() + $(document).scrollTop()) {
                    top = combo.offset().top - panel.parent().outerHeight();
                }
                if (top < $(document).scrollTop()) {
                    top = combo.offset().top + combo.outerHeight();
                }
                if (left + panel.outerWidth() > $(window).outerWidth()) {
                    left = $(window).outerWidth() - panel.parent().outerWidth();
                }
                panel.panel("move", {
                    left: left,
                    top: top
                });
                setTimeout(arguments.callee, 200);
            }
        })();
        function getLeft() {
            var left = combo.offset().left;
            if (left + panel.outerWidth() > $(window).width() + $(document).scrollLeft()) {
                left = $(window).width() + $(document).scrollLeft() - panel.outerWidth();
            }
            if (left < 0) {
                left = 0;
            }
            return left;
        }
        function getTop() {
            var top = combo.offset().top + combo.outerHeight();
            if (top + panel.outerHeight() > $(window).height() + $(document).scrollTop()) {
                top = combo.offset().top - panel.outerHeight();
            }
            if (top < $(document).scrollTop()) {
                top = combo.offset().top + combo.outerHeight();
            }
            return top;
        }
        $(document).unbind(".combo").bind("mousedown.combo", function(e) {
            var toolbar = $("body>div.combo-p>div.mulbottomtoolbar");
            var comboPanel = $("body>div.panel>div.combo-panel");
            var p = $(e.target).closest("div.combo-panel", comboPanel);
            var b = $(e.target).closest("div.mulbottomtoolbar", toolbar);
            if (p.length || b.length) {
                return;
            }
            removeComboFocus();
            comboPanel.panel("close");
            opts.onHidePanel.call(target);
            $(document).unbind(".combo");
        });
    }
    function removeComboFocus() {
        var $combo = $("body .combo");
        if ($combo.hasClass("combo-focus")) {
            $combo.removeClass("combo-focus");
        }
    }
    function hidePanel(target) {
        var opts = $.data(target, "combo").options;
        var panel = $.data(target, "combo").panel;
        var combo = $.data(target, "combo").combo;
        combo.removeClass("combo-focus");
        panel.panel("close");
        opts.onHidePanel.call(target);
    }
    function validate(target, doit) {
        //解决表头数据筛选时，可手工输入关键字----by lims
        if (_.isEmpty($.data(target, "combo"))) {
            return false;
        }
        var opts = $.data(target, "combo").options;
        var input = $.data(target, "combo").combo.find("input.combo-text");
        var textbox = $(target);
        if (opts.required) {
            if (textbox.parent().prev().children().length < 1 && textbox.parent().prev().text()) {
                textbox.parent().prev().html('<label class="required-star"> <span>' + textbox.parent().prev().text() + "</span> </label>");
            }
        }
        input.validatebox(opts);
        if (doit) {
            input.validatebox("validate");
            input.trigger("mouseleave");
        }
    }
    function setDisabled(target, disabled) {
        disabled = String(disabled);
        var opts = $.data(target, "combo").options, combo = $.data(target, "combo").combo, comboText = $.data(target, "combo").comboText, comboArrow = $.data(target, "combo").comboArrow, comboValue = combo.find(".combo-value");
        if (disabled == "true" || disabled == "disabled") {
            opts.disabled = true;
            $(target).attr("disabled", true);
            comboValue.attr("disabled", true);
            combo.addClass("combo-disabled");
            comboArrow.addClass("combo-arrow-disabled");
            comboText.addClass("form-input-disabled").attr("disabled", true).removeClass("validatebox-invalid").removeClass("varlidataTextarea-invalid").validatebox("hideTip");
        } else {
            opts.disabled = false;
            $(target).removeAttr("disabled");
            comboValue.removeAttr("disabled");
            combo.removeClass("combo-disabled");
            comboArrow.removeClass("combo-arrow-disabled");
            comboText.removeClass("form-input-disabled").removeAttr("disabled");
        }
    }
    function clear(target) {
        var opts = $.data(target, "combo").options;
        var combo = $.data(target, "combo").combo;
        if (opts.multiple) {
            combo.find("input.combo-value").length ? combo.find("input.combo-value:eq(0)").val("").siblings("input.combo-value").remove() : $('<input type="hidden" class="combo-value" name="' + $(target).attr("comboname") + '" value="">').appendTo(combo);
            $.data(target, "combo").panel.find(".combobox-item-selected").removeClass("combobox-item-selected").find("input:first").each(function() {
                $(this).attr("checked", false);
            });
        } else {
            combo.find("input.combo-value").val("");
        }
        combo.find("input.combo-text").val("").removeAttr("title");
    }
    function getText(target) {
        var combo = $.data(target, "combo").combo;
        return combo.find("input.combo-text").val();
    }
    function setText(target, text) {
        if (text) text = $("<a>" + text + "</a>").text();
        var combo = $.data(target, "combo").combo;
        combo.find("input.combo-text").val(text);
        if (text) validate(target, true);
        $.data(target, "combo").previousValue = text;
        combo.find("input.combo-text").attr("title", text);
    }
    function setTexts(target, text) {}
    function getValues(target) {
        var values = [];
        var combo = $.data(target, "combo").combo;
        combo.find("input.combo-value").each(function() {
            if ($(this).val()) values.push($(this).val());
        });
        return values;
    }
    function setValues(target, values, remainText) {
        var opts = $.data(target, "combo").options;
        var oldValues = getValues(target);
        var combo = $.data(target, "combo").combo;
        combo.find("input.combo-value").remove();
        var name = $(target).attr("comboName");
        var flage = values[values.length - 1];
        var length = 0;
        if (flage == "combobox") {
            var text = values[values.length - 2];
            length = values.length - 2;
        } else {
            length = values.length;
        }
        if (length == 0) {
            var input = $('<input type="hidden" class="combo-value">').appendTo(combo);
            if (name) {
                input.attr("name", name);
            }
            input.val("");
        } else {
            for (var i = 0; i < length; i++) {
                values[i] = jQuery.trim(values[i]);
                var input = $('<input type="hidden" class="combo-value">').appendTo(combo);
                if (flage == "combobox") {
                    input.attr("text", text[i]);
                }
                if (name) {
                    input.attr("name", name);
                }
                input.val(values[i]);
            }
        }
        var tmp = [];
        for (var i = 0; i < oldValues.length; i++) {
            tmp[i] = jQuery.trim(oldValues[i]);
        }
        var aa = [];
        for (var i = 0; i < values.length; i++) {
            for (var j = 0; j < tmp.length; j++) {
                if (values[i] == tmp[j]) {
                    aa.push(values[i]);
                    tmp.splice(j, 1);
                    break;
                }
            }
        }
        if (aa.length != values.length || values.length != oldValues.length) {
            if (opts.multiple) {
                opts.onChange.call(target, values, oldValues);
            } else {
                opts.onChange.call(target, values[0], oldValues[0]);
            }
        }
        if (!remainText) {
            $(target).combo("setText", values[0]);
        }
    }
    function getValue(target) {
        var values = getValues(target);
        if (values.length == 0) {
            return "";
        }
        return values[0];
    }
    function getName(target) {
        var texts = getNames(target);
        return texts[0];
    }
    function getNames(target) {
        var texts = [];
        var combo = $.data(target, "combo").combo;
        combo.find("input.combo-value").each(function() {
            texts.push($(this).attr("text"));
        });
        return texts;
    }
    function setValue(target, value) {
        setValues(target, [ value ]);
    }
    function reset(target) {
        var opts = $.data(target, "combo").options;
        setValue(target, opts.defaultValue || opts.value);
        setText(target, opts.text);
    }
    //判断data是否是单字节的
    function isSingleByte(target) {
        var opts = $.data(target, "combo").options;
        var data = opts.data || [];
        var flag = true;
        $.each(data, function(i, v) {
            if (v[opts.valueField].length > 1) {
                return flag = false;
            }
        });
        return flag;
    }
    function select(target) {
        var opts = $.data(target, "combo").options;
        //注释：不懂下面代码意义，先注释掉
        //var fn = opts.onChange;
        //opts.onChange = function() {};
        var v = opts.value || opts.defaultValue || "";
        opts.multiple ? setValues(target, $.isArray(v) ? v : -1 === v.indexOf(",") && opts.singleByte ? v.split("") : v.split(opts.separator)) : setValue(target, v);
    }
    function showTip(target, msg, global) {
        $.data(target, "combo").combo.find("input.combo-text").validatebox("showTip", msg, global);
    }
});
