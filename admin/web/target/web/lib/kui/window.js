/**
 * window组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/window", [], function(require, exports, module) {
    $.fn.window = function(options, param) {
        if (typeof options == "string") {
            var method = $.fn.window.methods[options];
            if (method) {
                return method(this, param);
            } else {
                return this.panel(options, param);
            }
        }
        options = options || {};
        //不可移出窗口外时以文档定位
        options.outWin === false && $.extend(options, {
            offsetParent: false
        });
        return this.each(function() {
            var state = $.data(this, "window");
            if (state) {
                $.extend(state.options, options);
            } else {
                state = $.data(this, "window", {
                    options: $.extend({}, $.fn.window.defaults, $.fn.window.parseOptions(this), options)
                });
                if (!state.options.inline) {
                    document.body.appendChild(this);
                }
            }
            create(this);
            setProperties(this);
        });
    };
    $.fn.window.methods = {
        options: function(jq) {
            var popts = jq.panel("options");
            var wopts = $.data(jq[0], "window").options;
            return $.extend(wopts, {
                closed: popts.closed,
                collapsed: popts.collapsed,
                minimized: popts.minimized,
                maximized: popts.maximized
            });
        },
        window: function(jq) {
            if ($.data(jq[0], "window")) {
                return $.data(jq[0], "window").window;
            } else {
                return null;
            }
        },
        resize: function(jq, param) {
            return jq.each(function() {
                setSize(this, param);
            });
        },
        move: function(jq, param) {
            return jq.each(function() {
                moveWindow(this, param);
            });
        },
        hcenter: function(jq) {
            return jq.each(function() {
                hcenter(this, true);
            });
        },
        vcenter: function(jq) {
            return jq.each(function() {
                vcenter(this, true);
            });
        },
        center: function(jq) {
            return jq.each(function() {
                hcenter(this);
                vcenter(this);
                moveWindow(this);
            });
        }
    };
    $.fn.window.parseOptions = function(target) {
        return $.extend({}, $.fn.panel.parseOptions(target), $.parser.parseOptions(target, [ {
            draggable: "boolean",
            resizable: "boolean",
            shadow: "boolean",
            modal: "boolean",
            inline: "boolean"
        } ]));
    };
    // Inherited from $.fn.panel.defaults
    $.fn.window.defaults = $.extend({}, $.fn.panel.defaults, {
        zIndex: 9e3,
        draggable: true,
        resizable: true,
        shadow: true,
        modal: false,
        inline: false,
        outWin: true,
        //是否可以出window外，onDrap控制（与drapout不同）
        dragout: false,
        // true to stay inside its parent, false to go on top of all elements
        // window's property which difference from panel
        title: "New Window",
        collapsible: true,
        minimizable: true,
        maximizable: true,
        closable: true,
        closed: false
    });
    function setSize(target, param) {
        var opts = $.data(target, "window").options;
        if (param) {
            if (param.width) opts.width = param.width;
            if (param.height) opts.height = param.height;
            if (param.left != null) opts.left = param.left;
            if (param.top != null) opts.top = param.top;
        }
        $(target).panel("resize", opts);
        addIframeLayer(target);
    }
    //添加iframe
    function addIframeLayer(target) {
        var panel = $.data(target, "panel").panel, outWin = $.data(target, "window").options.outWin, h = panel.height() + outWin === false ? 0 : 9, w = panel.width() + outWin === false ? 0 : 10, $ifm = $("<iframe class='panel-iframe-layer' src='about:blank' frameborder='0'" + " marginHeight='0' marginWidth='0'></iframe>"), ifmTarget = panel.find(".panel-iframe-layer");
        if (!ifmTarget.length) {
            ifmTarget = $ifm;
            panel.append(ifmTarget);
        }
        ifmTarget.width(w).height(h);
    }
    function moveWindow(target, param) {
        var state = $.data(target, "window");
        if (param) {
            if (param.left != null) state.options.left = param.left;
            if (param.top != null) state.options.top = param.top;
        }
        $(target).panel("move", state.options);
        if (state.shadow) {
            state.shadow.css({
                left: state.options.left,
                top: state.options.top
            });
        }
    }
    /**
	 *  center the window only horizontally
	 */
    function hcenter(target, tomove) {
        var state = $.data(target, "window");
        var opts = state.options;
        var width = opts.width;
        if (isNaN(width)) {
            width = state.window._outerWidth();
        }
        if (opts.inline) {
            var parent = state.window.parent();
            opts.left = (parent.width() - width) / 2 + parent.scrollLeft();
        } else {
            opts.left = ($(window)._outerWidth() - width) / 2 + $(document).scrollLeft();
        }
        if (tomove) {
            moveWindow(target);
        }
    }
    /**
	 * center the window only vertically
	 */
    function vcenter(target, tomove) {
        var state = $.data(target, "window");
        var opts = state.options;
        var height = opts.height;
        if (isNaN(height)) {
            height = state.window._outerHeight();
        }
        if (opts.inline) {
            var parent = state.window.parent();
            opts.top = (parent.height() - height) / 2 + parent.scrollTop();
        } else {
            opts.top = ($(window)._outerHeight() - height) / 2 + $(document).scrollTop();
        }
        if (tomove) {
            moveWindow(target);
        }
    }
    function create(target) {
        var state = $.data(target, "window");
        var zIndex = state ? state.options.zIndex : $.fn.window.defaults.zIndex;
        var win = $(target).panel($.extend({}, state.options, {
            border: false,
            doSize: true,
            // size the panel, the property undefined in window component
            closed: true,
            // close the panel
            cls: "window",
            headerCls: "window-header",
            bodyCls: "window-body " + (state.options.noheader ? "window-body-noheader" : ""),
            onBeforeDestroy: function() {
                if (state.options.onBeforeDestroy.call(target) == false) return false;
                if (state.shadow) state.shadow.remove();
                if (state.mask) state.mask.remove();
            },
            onClose: function() {
                if (state.shadow) state.shadow.hide();
                if (state.mask) state.mask.hide();
                $("body").find(".validatebox-tip").css("display", "none");
                state.options.onClose.call(target);
            },
            onOpen: function() {
                if (state.mask) {
                    state.mask.css({
                        display: "block",
                        zIndex: $.fn.window.defaults.zIndex++
                    });
                }
                if (state.shadow) {
                    state.shadow.css({
                        display: "block",
                        zIndex: $.fn.window.defaults.zIndex++,
                        left: state.options.left,
                        top: state.options.top,
                        width: state.window._outerWidth(),
                        height: state.window._outerHeight()
                    });
                }
                state.window.css("z-index", $.fn.window.defaults.zIndex++);
                state.options.onOpen.call(target);
            },
            onResize: function(width, height) {
                var opts = $(this).panel("options");
                $.extend(state.options, {
                    width: opts.width,
                    height: opts.height,
                    left: opts.left,
                    top: opts.top
                });
                if (state.shadow) {
                    state.shadow.css({
                        left: state.options.left,
                        top: state.options.top,
                        width: state.window._outerWidth(),
                        height: state.window._outerHeight()
                    });
                }
                state.options.onResize.call(target, width, height);
            },
            onMinimize: function() {
                if (state.shadow) state.shadow.hide();
                if (state.mask) state.mask.hide();
                state.options.onMinimize.call(target);
            },
            onBeforeCollapse: function() {
                if (state.options.onBeforeCollapse.call(target) == false) return false;
                if (state.shadow) state.shadow.hide();
            },
            onExpand: function() {
                if (state.shadow) state.shadow.show();
                state.options.onExpand.call(target);
            }
        }));
        state.window = win.panel("panel");
        // create mask
        if (state.mask) state.mask.remove();
        if (state.options.modal == true) {
            state.mask = $('<div class="window-mask"></div>').insertAfter(state.window);
            state.mask.css({
                width: state.options.inline ? state.mask.parent().width() : getPageArea().width,
                height: state.options.inline ? state.mask.parent().height() : getPageArea().height - 1,
                display: "none"
            });
        }
        // create shadow
        if (state.shadow) state.shadow.remove();
        if (state.options.shadow == true) {
            state.shadow = $('<div class="window-shadow"></div>').insertAfter(state.window);
            state.shadow.css({
                display: "none"
            });
        }
        // if require center the window
        if (state.options.left == null) {
            hcenter(target);
        }
        if (state.options.top == null) {
            vcenter(target);
        }
        moveWindow(target);
        if (state.options.closed == false) {
            win.window("open");
        }
        addIframeLayer(target);
    }
    /**
	 * set window drag and resize property
	 */
    function setProperties(target) {
        var state = $.data(target, "window");
        var zIndex = state ? state.options.zIndex : $.fn.window.defaults.zIndex;
        var $mask;
        state.window.draggable({
            handle: ">div.panel-header>div.panel-title",
            disabled: state.options.draggable == false,
            onStartDrag: function(e) {
                //if (state.mask) state.mask.css('z-index', $.fn.window.defaults.zIndex++);
                //if (state.shadow) state.shadow.css('z-index', $.fn.window.defaults.zIndex++);
                //state.window.css('z-index', zIndex++);
                if (state.options && typeof state.options.onStartDrag === "function") {
                    if (false === state.options.onStartDrag.call($(state.window).find(".panel-body")[0], e)) {
                        return false;
                    }
                }
                if (!state.proxy) {
                    state.proxy = $('<div class="window-proxy" style="cursor:move;"></div>').insertAfter(state.window);
                }
                if (!state.options.modal) {
                    $mask = $('<div class="window-mask transparent"></div>');
                    $mask.insertAfter(state.window);
                }
                state.proxy.css({
                    display: "none",
                    zIndex: $.fn.window.defaults.zIndex++,
                    left: e.data.left,
                    top: e.data.top
                });
                state.proxy._outerWidth(state.window._outerWidth());
                state.proxy._outerHeight(state.window._outerHeight());
                setTimeout(function() {
                    if (state.proxy) state.proxy.show();
                }, 500);
            },
            onDrag: function(e) {
                if (state.options.outWin === false) {
                    var d = e.data, $target = $(d.target), $win = $(window), outerWidth = $target.outerWidth(), outerHeight = $target.outerHeight(), winWidth = $win.width(), winHeight = $win.height();
                    d.left < 0 && (d.left = 0);
                    d.top < 0 && (d.top = 0);
                    d.left + outerWidth > winWidth && (d.left = winWidth - outerWidth);
                    d.top + outerHeight > winHeight && (d.top = winHeight - outerHeight);
                }
                state.proxy.css({
                    display: "block",
                    left: e.data.left,
                    top: e.data.top
                });
                return false;
            },
            onStopDrag: function(e) {
                if (state.options.dragout) {
                    state.options.left = e.data.left;
                    state.options.top = e.data.top;
                } else {
                    var minLeft = 120 - state.options.width;
                    var maxLeft = $(window).width() - 120;
                    var maxTop = $(window).height() - 30;
                    state.options.left = e.data.left < minLeft ? minLeft : e.data.left > maxLeft ? maxLeft : e.data.left;
                    state.options.top = e.data.top < 0 ? 0 : e.data.top > maxTop ? maxTop : e.data.top;
                }
                $(target).window("move");
                state.proxy.remove();
                $mask && $mask.remove();
                state.proxy = null;
            }
        });
        state.window.resizable({
            disabled: state.options.resizable == false,
            onStartResize: function(e) {
                state.pmask = $('<div class="window-proxy-mask"></div>').insertAfter(state.window);
                state.pmask.css({
                    zIndex: $.fn.window.defaults.zIndex++,
                    left: e.data.left,
                    top: e.data.top,
                    width: state.window._outerWidth(),
                    height: state.window._outerHeight()
                });
                if (!state.proxy) {
                    state.proxy = $('<div class="window-proxy"></div>').insertAfter(state.window);
                }
                state.proxy.css({
                    zIndex: $.fn.window.defaults.zIndex++,
                    left: e.data.left,
                    top: e.data.top
                });
                state.proxy._outerWidth(e.data.width);
                state.proxy._outerHeight(e.data.height);
            },
            onResize: function(e) {
                state.proxy.css({
                    left: e.data.left,
                    top: e.data.top
                });
                state.proxy._outerWidth(e.data.width);
                state.proxy._outerHeight(e.data.height);
                return false;
            },
            onStopResize: function(e) {
                $.extend(state.options, {
                    left: e.data.left,
                    top: e.data.top,
                    width: e.data.width,
                    height: e.data.height
                });
                setSize(target);
                state.pmask.remove();
                state.pmask = null;
                state.proxy.remove();
                state.proxy = null;
            }
        });
    }
    function getPageArea() {
        if (document.compatMode == "BackCompat") {
            return {
                width: Math.max(document.body.scrollWidth, document.body.clientWidth),
                height: Math.max(document.body.scrollHeight, document.body.clientHeight)
            };
        } else {
            return {
                width: Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth),
                height: Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight)
            };
        }
    }
    $(window).resize(function() {
        setTimeout(function() {
            $("body>div.window-mask").css({
                width: getPageArea().width,
                height: getPageArea().height - 1
            });
        }, 50);
    });
});
