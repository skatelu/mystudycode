/**
 * linkbutton组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/linkbutton", [], function(require, exports, module) {
    $.fn.linkbutton = function(options, param) {
        if (typeof options == "string") {
            var method = $.fn.linkbutton.methods[options];
            if (method) {
                return method.apply(this, [ this ].concat(Array.prototype.slice.call(arguments, 1)));
            }
        }
        return this.each(function() {
            options = options || {};
            var state = $.data(this, "linkbutton");
            if (state) {
                $.extend(state.options, options);
            } else {
                $.data(this, "linkbutton", {
                    options: $.extend({}, $.fn.linkbutton.defaults, $.fn.linkbutton.parseOptions(this), options)
                });
                $(this).removeAttr("disabled");
                createButton(this);
            }
        });
    };
    $.fn.linkbutton.methods = {
        options: function(jq, param) {
            return $.data(jq[0], "linkbutton").options;
        },
        enable: function(jq) {
            return jq.each(function() {
                setDisabled(this, false);
            });
        },
        disable: function(jq) {
            return jq.each(function() {
                setDisabled(this, true);
            });
        },
        show: function(jq) {
            return jq.each(function() {
                $(this).closest(".l-btn-wrap").show();
            });
        },
        hide: function(jq) {
            return jq.each(function() {
                $(this).closest(".l-btn-wrap").hide();
            });
        },
        setText: function(jq, text) {
            return jq.each(function() {
                setText(this, text);
            });
        },
        setIcon: function(jq, iconCls) {
            return jq.each(function() {
                setIcon(this, iconCls);
            });
        },
        //@deprecated 不建议使用，请使用setText或setIcon
        resetButtonStyle: function(jq, params) {
            return jq.each(function() {
                resetButtonStyle(this, params);
            });
        },
        destroy: function(jq) {
            return jq.each(function() {
                $(this).parent("div.l-btn-wrap").remove();
            });
        }
    };
    $.fn.linkbutton.parseOptions = function(target) {
        var t = $(target);
        return $.extend({}, $.parser.parseOptions(target, [ "id", "iconCls", "iconAlign", {
            plain: "boolean"
        } ]), {
            disabled: t.attr("disabled") ? true : undefined,
            text: $.trim(t.html()) || undefined,
            iconCls: t.attr("icon") || t.attr("iconCls") || undefined
        });
    };
    $.fn.linkbutton.defaults = {
        id: null,
        disabled: false,
        hidden: false,
        plain: false,
        text: "",
        iconCls: null,
        iconAlign: "left",
        onClick: function(target) {}
    };
    function createButton(target) {
        var opts = $.data(target, "linkbutton").options;
        $(target).empty();
        $(target).addClass("l-btn");
        if (opts.id) {
            $(target).attr("id", opts.id);
        } else {
            $(target).attr("id", "");
        }
        if (opts.plain) {
            $(target).addClass("l-btn-plain");
        } else {
            $(target).removeClass("l-btn-plain");
        }
        var sdom = [ '<span class="l-btn-left"><span class="l-btn-text">' ];
        if (opts.iconCls) {
            var iconStyle = "";
            if ("right" == opts.iconAlign) {
                iconStyle = 'style="float:right;"';
            }
            sdom.push('<span class="ui-tool-icons ' + opts.iconCls + '" ' + iconStyle + "></span>");
        }
        if (opts.text) {
            sdom.push(opts.text);
        }
        sdom.push("</span></span>");
        $(target).append(sdom.join(""));
        $(target).wrap("<div class='l-btn-wrap'>");
        var disableMask = $("<div class='l-btn-disable-mask'></div>").insertAfter(target);
        disableMask.attr("linkbutton-name", $(target).attr("name") || $(target).attr("id"));
        opts.disableMask = disableMask;
        opts.btnWrap = $(target).parent(".l-btn-wrap");
        opts.btnWrap.append(disableMask);
        $(target).unbind(".linkbutton").bind("focus.linkbutton", function() {
            if (!opts.disabled) {
                $(this).find("span.l-btn-text").addClass("l-btn-focus");
            }
        }).bind("blur.linkbutton", function() {
            $(this).find("span.l-btn-text").removeClass("l-btn-focus");
        }).bind("click.linkbutton", function() {
            opts.onClick.call(this);
        });
        setDisabled(target, opts.disabled);
        if (window && window.onbeforeunload) {
            $(target).unbind(".linkbutton").bind("click.linkbutton", function(e) {
                return false;
            });
        }
        if (opts.hidden) {
            $(target).parent().hide();
        }
    }
    function setDisabled(target, disabled) {
        var state = $.data(target, "linkbutton");
        if (disabled) {
            state.options.disabled = true;
            var href = $(target).attr("href");
            if (href) {
                state.href = href;
                $(target).attr("href", "javascript:void(0)");
            }
            if (target.onclick) {
                state.onclick = target.onclick;
                target.onclick = null;
            }
            $(target).addClass("l-btn-disabled");
            state.options.disableMask.show();
        } else {
            state.options.disabled = false;
            if (state.href) {
                $(target).attr("href", state.href);
            }
            if (state.onclick) {
                target.onclick = state.onclick;
            }
            $(target).removeClass("l-btn-disabled");
            state.options.disableMask.hide();
        }
    }
    function setText(target, text) {
        if (!text) {
            return;
        }
        var $button = $(target), $text = $button.find("span.l-btn-text"), $icon = $text.find("span.ui-tool-icons").clone();
        $text.text(text);
        $icon.length && $text.prepend($icon);
    }
    function setIcon(target, iconCls) {
        if (!iconCls) {
            return;
        }
        var $button = $(target), $icon = $button.find("span.ui-tool-icons");
        if ($icon.length) {
            $icon.removeClass().addClass("ui-tool-icons " + iconCls);
        } else {
            $button.find("span.l-btn-text").prepend('<span class="ui-tool-icons ' + iconCls + '"></span>');
        }
    }
    function resetButtonStyle(target, params) {
        var $button = $(target);
        for (var key in params) {
            switch (key) {
              case "text":
                var $text = $button.find("span.l-btn-text");
                var $icon = $text.find("span.ui-tool-icons").clone();
                $text.text(params[key]);
                if ($icon.length) $text.prepend($icon);
                break;

              case "iconCls":
                var $icon = $button.find("span.ui-tool-icons");
                if ($icon.length) $icon.removeClass().addClass("ui-tool-icons " + params[key]); else $button.find("span.l-btn-text").prepend('<span class="ui-tool-icons ' + params[key] + '"></span>');
                break;

              default:
                ;
            }
        }
    }
});
