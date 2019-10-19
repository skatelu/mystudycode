/**
 * message组件
 * @author chenk
 */
define("lib/kui/message", [], function(require, exports, module) {
    var messageRender = artTemplate.compile('<div class="messager-body">' + "{{#content}}" + '<div class="messager-button">' + "{{each buttons as button key}}" + '<a class="win-btn-{{key}}">{{key}}</a>' + "{{/each}}" + "</div>" + "</div>"), contentRender = artTemplate.compile("<div class='messager-icon messager-{{type}}'></div><div class='messager-text' title='{{msg}}'>{{msg}}</div><br/><div style='clear:both;'/>" + '{{if showInput}}<div><input class="messager-input" type="text"/></div>{{/if}}');
    $.message = {
        defaultOkText: "确定",
        defaultCancelText: "取消",
        alert: $.extend(function(msg, typeOrOpts) {
            var title, type, fn, content, onCloseCallback;
            if ($.type(typeOrOpts) == "string") {
                type = typeOrOpts;
            } else if ($.type(typeOrOpts) == "object") {
                type = typeOrOpts.type;
                title = typeOrOpts.title;
                fn = typeOrOpts.onClose;
            } else if ($.type(typeOrOpts) == "function") {
                fn = typeOrOpts;
            }
            type = type || $.message.alert.INFO;
            title = title || type == $.message.alert.INFO ? "提示" : type == $.message.alert.WARN ? "警告" : type == $.message.alert.ERROR ? "错误" : "";
            content = contentRender({
                type: type,
                msg: msg
            });
            var buttons = {};
            buttons[typeOrOpts && typeOrOpts.okBtnTxt || $.message.defaultOkText] = function() {
                if (fn) {
                    fn();
                }
                win.window("destroy");
            };
            onCloseCallback = function() {
                if ($.type(fn) == "function") {
                    fn();
                }
            };
            var win = createDialog(title, content, buttons, onCloseCallback, $.extend({}, $.type(typeOrOpts) == "object" && typeOrOpts || {}, {
                showOne: true
            }));
        }, {
            INFO: "info",
            WARN: "warning",
            ERROR: "error"
        }),
        confirm: function(msg, fn, opts) {
            var buttons = {}, onCloseCallback, title, content = contentRender({
                type: "question",
                msg: msg
            });
            opts = opts || {};
            buttons[opts.okBtnText || $.message.defaultOkText] = function() {
                if (fn) {
                    fn(true);
                }
                win.window("destroy");
            };
            buttons[opts.cancelBtnText || $.message.defaultCancelText] = function() {
                if (fn) {
                    fn(false);
                }
                win.window("destroy");
            };
            onCloseCallback = opts.onClose || function() {
                if (fn) {
                    fn(false);
                }
            };
            title = opts.title || "确认";
            var win = createDialog(title, content, buttons, onCloseCallback, opts);
        },
        prompt: function(fn, opts) {
            var msg, content, $input, onCloseCallback, title, buttons = {};
            opts = opts || {};
            msg = $.type(opts.msg) == "undefined" ? "请输入" : opts.msg;
            content = contentRender({
                type: "info",
                showInput: true,
                msg: msg
            });
            buttons[opts && opts.okBtnTxt || $.message.defaultOkText] = function() {
                var value, $input = win.find("input.messager-input");
                if (opts.validType) {
                    if (!$input.validatebox("isValid")) {
                        return false;
                    }
                    value = $input.validatebox("getValue");
                } else {
                    value = $.trim($input.val());
                }
                if (value && false !== fn(value)) {
                    win.window("destroy");
                }
            };
            buttons[opts && opts.cancelBtnTxt || $.message.defaultCancelText] = function() {
                if (fn) {
                    fn(null);
                }
                win.window("destroy");
            };
            onCloseCallback = function() {
                if ($.type(opts.onClose) == "function") {
                    opts.onClose(null);
                }
            };
            title = opts.title || "确认";
            var win = createDialog(title, content, buttons, onCloseCallback);
            $input = win.find("input.messager-input");
            if (opts.validType) {
                $input.validatebox({
                    validType: opts.validType
                });
            }
            $input.focus();
            return win;
        },
        loading: function(msg, opts) {
            var win, content;
            msg = msg || "操作中,请稍候...";
            content = '<div class="progress-mask-msg">' + msg + "</div>";
            win = createDialog(false, content, null, null, $.extend({}, opts, {
                showOne: true
            }));
            win.addClass("messager-loading-body").closest("div.messager-window").addClass("message-loading").next("div.window-shadow").remove();
            return {
                destroy: function() {
                    win && win.window("destroy");
                },
                setText: function(msg) {
                    win && win.find(".messager-text,.progress-mask-msg").text(msg);
                }
            };
        }
    };
    var windowList = [];
    function createDialog(title, content, buttons, onCloseCallback, conf) {
        var win, parent, window;
        content = content || "";
        buttons = buttons || {};
        conf = conf || {};
        parent = $(conf.parent && $(conf.parent).length ? conf.parent : "body");
        win = $(messageRender({
            content: content,
            buttons: buttons
        })).appendTo(parent);
        $.each(buttons, function(label) {
            win.find(".win-btn-" + label).linkbutton({
                onClick: function() {
                    buttons[label]();
                }
            });
        });
        win.window({
            title: title,
            noheader: title ? false : true,
            width: conf.width || 400,
            height: "auto",
            modal: true,
            collapsible: false,
            minimizable: false,
            maximizable: false,
            resizable: false,
            outWin: false,
            onBeforeDestroy: function() {
                if (conf.showOne) {
                    var showFlag = true;
                    windowList = $.grep(windowList, function(value) {
                        if (win === value.win) {
                            if (value.main_flag == "1") {
                                showFlag = false;
                            }
                            return false;
                        } else if (!showFlag && value.parent.is(parent)) {
                            value.win.parent().show();
                            showFlag = true;
                        }
                        return true;
                    });
                }
            },
            onBeforeClose: function() {
                setTimeout(function() {
                    if (typeof onCloseCallback == "function") {
                        onCloseCallback();
                    }
                    win.window("destroy");
                }, 100);
            }
        });
        win.window("window").addClass("messager-window");
        if (conf.showOne) {
            $.each(windowList, function() {
                if (this.parent.is(parent)) {
                    this.win.parent().hide();
                    this.win.main_flag = "0";
                }
            });
            windowList.unshift({
                parent: parent,
                win: win,
                main_flag: "1"
            });
        }
        setTimeout(function() {
            win.children("div.messager-button").find("a.l-btn:first").focus();
        });
        return win;
    }
    return {
        alert: $.message.alert,
        confirm: $.message.confirm,
        prompt: $.message.prompt,
        loading: $.message.loading
    };
});
