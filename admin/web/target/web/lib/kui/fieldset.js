/**
 * fieldset组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/fieldset", [], function(require, exports, module) {
    $.fn.fieldset = function(options, param) {
        if (typeof options == "string") {
            var method = $.fn.fieldset.methods[options];
            if (method) {
                return method.apply(this, [ this ].concat(Array.prototype.slice.call(arguments, 1)));
            }
        }
        return this.each(function() {
            options = options || {};
            var state = $.data(this, "fieldset");
            if (state) {
                $.extend(state.options, options);
            } else {
                $.data(this, "fieldset", {
                    options: $.extend(true, {}, $.fn.fieldset.defaults, $.fn.fieldset.parseOptions(this), options)
                });
                init(this);
            }
        });
    };
    $.fn.fieldset.methods = {
        //获取组件配置
        options: function(jq) {
            return $.data(jq[0], "fieldset").options;
        },
        //调整fieldset大小
        resize: function(jq, size) {
            return jq.each(function() {
                setSize(this, size);
            });
        },
        //隐藏内容
        hideField: function(jq) {
            return jq.each(function() {
                hideFiledContent(this);
            });
        },
        //显示内容
        showField: function(jq) {
            return jq.each(function() {
                showFiledContent(this);
            });
        }
    };
    $.fn.fieldset.parseOptions = function(target) {
        return $.parser.parseOptions(target);
    };
    $.fn.fieldset.defaults = {
        title: "",
        //标题
        fit: false,
        //是否只适应大小
        width: "",
        height: "",
        showTitle: true,
        //是否显示标题
        closable: false,
        //是否可关闭
        collapsed: false,
        //是否是关闭状态,
        animation: true,
        //展开关闭是否有动画
        speed: "fast",
        //动画速度
        closeCallback: $.noop,
        //关闭回调函数
        expendCallback: $.noop
    };
    //初始化组件
    function init(target) {
        var fieldset = $(target), opts = $.data(target, "fieldset").options, legend = fieldset.children("legend").length ? fieldset.children("legend") : $("<legend>" + opts.title + "</legend>").appendTo(fieldset);
        fieldset.addClass("fieldset");
        if (!opts.showTitle) {
            legend.hide();
        }
        if (opts.closable) {
            legend.addClass("collapse-enable").append("<span class='closable-span'></span>");
            bindEvents(target);
            if (opts.collapsed) {
                hideFiledContent(target);
            }
        }
        setSize(target);
    }
    //绑定事件
    function bindEvents(target) {
        var opts = $.data(target, "fieldset").options, fieldset = $(target), legend = fieldset.children("legend");
        legend.unbind("click.fieldset");
        if (opts.closable) {
            legend.bind("click.fieldset", function(e) {
                if (fieldset.hasClass("collapsed")) {
                    showFiledContent(target);
                } else {
                    hideFiledContent(target);
                }
            });
        }
    }
    //隐藏元素
    function hideFiledContent(target) {
        var opts = $.data(target, "fieldset").options, children = $(target).children(":not(legend)"), num = 1;
        for (var i = 0; i < children.length; i++) {
            var tag = children[i];
            if (opts.animation) {
                $(tag).slideUp(opts.speed, function() {
                    if (num == children.length) {
                        opts.closeCallback.call(target, target);
                    } else {
                        num++;
                    }
                });
            } else {
                $(tag).hide();
            }
        }
        opts.collapsed = true;
        $(target).addClass("collapsed");
    }
    //显示元素
    function showFiledContent(target) {
        var opts = $.data(target, "fieldset").options, children = $(target).children(":not(legend)"), num = 1;
        for (var i = 0; i < children.length; i++) {
            var tag = children[i];
            if (opts.animation) {
                $(tag).slideDown(opts.speed, function() {
                    if (num == children.length) {
                        opts.expendCallback.call(target, target);
                    } else {
                        num++;
                    }
                });
            } else {
                $(tag).show();
            }
        }
        opts.collapsed = false;
        $(target).removeClass("collapsed");
    }
    function setSize(target, size) {
        var opts = $.data(target, "fieldset").options;
        if (opts.fit == true) {
            var p = $(target).parent();
            opts.width = p.width();
            opts.height = p.height();
        }
        if (size) {
            if (size.width) {
                opts.width = size.width;
            }
            if (size.height) {
                opts.height = size.height;
            }
        }
        opts.width && $(target).width(opts.width);
        opts.height && $(target).height(opts.height - $(target).children("legend").height());
    }
});
