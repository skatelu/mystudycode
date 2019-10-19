/**
 * sform组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/sform", [], function(require, exports, module) {
    $.fn.sform = function(options, param) {
        if (typeof options == "string") {
            return $.fn.sform.methods[options](this, param);
        }
        options = options || {};
        return this.each(function() {
            var state = $.data(this, "sform");
            if (state) {
                $.extend(state.options, options);
            } else {
                state = $.data(this, "sform", {
                    options: $.extend({}, $.fn.sform.defaults, $.fn.sform.parseOptions(this), options)
                });
            }
            create(this);
        });
    };
    $.fn.sform.parseOptions = function(target) {
        var jq = $(target);
        return $.extend({}, $.parser.parseOptions(target, [ {
            colNumbers: "number"
        } ], {
            queryCols: jq.attr("queryCols") ? eval(jq.attr("queryCols")) : undefined,
            toolbar: jq.attr("toolbar") ? eval(jq.attr("toolbar")) : undefined,
            req: jq.attr("req") ? eval(jq.attr("req")) : undefined
        }));
    };
    $.fn.sform.methods = {
        load: function(jq, data) {
            return jq.each(function() {
                loadData(this, data);
            });
        },
        getData: function(jq) {
            return getData(jq[0]);
        },
        clear: function(jq) {
            return jq.each(function() {
                clear(this);
            });
        },
        reload: function(jq, param) {
            return jq.each(function() {
                reload(this, param);
            });
        },
        toForm: function(jq) {
            return jq.each(function() {
                toForm(this);
            });
        }
    };
    $.fn.sform.defaults = {
        queryCols: [],
        //展示列项
        /*
         * queryCols = [{
         *   cols : [{
         *       field : "",
         *       title : "",
         *       colspan : "",
         *       formatter : "",
         *       type : "",
         *       editor : ""
         *   }]
         * }]
         * */
        data: null,
        //数据源
        colNumbers: 3,
        //每行展示数据数
        required: false,
        // 初始化sform时是否通过req查询一次。
        url: undefined,
        //request url
        method: undefined,
        //post/get
        dataType: undefined,
        //json
        req: null,
        //请求参数
        //        maxLabel : 0,
        cellWidth: 248,
        textWidth: "",
        onInitSuccess: $.noop,
        onLoadSuccess: $.noop,
        onBeforeLoadData: $.noop,
        onBeforeRequest: $.noop,
        onRequestError: $.noop
    };
    /**
     * 初始化
     * @param target
     */
    function create(target) {
        $(target).addClass("kui-sform");
        var opts = $.data(target, "sform").options;
        var queryCols = opts.queryCols;
        if (!queryCols || queryCols.length == 0) {
            return;
        }
        $(target).empty();
        var domArr = [];
        $.each(queryCols, function(i, obj) {
            var cols = obj.cols;
            domArr.push('<ul class="sform-ul clearfix">');
            $.each(cols, function(j, col) {
                domArr.push('<li name="' + (col.field || "") + '"' + (!col.wrap ? "" : ' class="li-br"') + (col.hidden ? 'style="display:none"' : "") + ">" + '<span class="sform-text" title="' + (col.title || "") + '">' + (col.title || "") + "：</span>" + '<span class="sform-value"></span></li>');
            });
            domArr.push("</ul>");
            domArr.push('<div style="clear:both;"></div>');
        });
        $(target).html(domArr.join(""));
        setColNum(target);
        if (opts.required) {
            request(target);
        } else if (opts.data) {
            loadData(target, opts.data);
        }
        opts.onInitSuccess.call(target);
    }
    /**
     * 重置样式，每行显示几个元素项
     * @param target
     */
    function setColNum(target) {
        var $target = $(target), opts = $.data(target, "sform").options, colNum = opts.colNumbers;
        if (colNum && !isNaN(colNum)) {
            var conWidth = $target.outerWidth(true), itemWidth = Math.floor(conWidth / colNum);
            if (opts.cellWidth && !isNaN(opts.cellWidth) && Number(opts.cellWidth) > 0 && Number(opts.cellWidth) < itemWidth) {
                itemWidth = opts.cellWidth;
            }
            $target.find(".sform-ul").each(function(j) {
                var cols = opts.queryCols[j].cols, $ul = $(this), fixIdx = 1, idx = 0;
                $ul.find("li").each(function(i) {
                    var name = $(this).attr("name"), col = cols[i], colspan = col.colspan, liWidth = itemWidth, textWidth = Number(opts.textWidth) || Math.floor(liWidth * .4), valueWidth;
                    fixIdx += colNum;
                    //和form里面的colspan保持一致，一个输入项是2
                    if (colspan && !isNaN(colspan)) {
                        liWidth = itemWidth * Number(colspan / 2);
                        // idx += Math.floor(Number(colspan/2) - 1);
                        fixIdx += Math.floor(Number(colspan / 2));
                    }
                    liWidth = minusMargin($(this), liWidth);
                    valueWidth = liWidth - textWidth;
                    $(this).width(liWidth);
                    $(this).find(".sform-text").width(textWidth);
                    $(this).find(".sform-value").width(valueWidth);
                    // if( (idx+1) % colNum == 0){
                    if (idx + 1 == fixIdx) {
                        $('<div class="clearfix"></div>').insertAfter($(this));
                    }
                    idx++;
                });
            });
        }
        //设置宽度，减去margin
        function minusMargin($li, liWidth) {
            var marginLeft = Number($li.css("marginLeft").replace("px", "")), marginRight = Number($li.css("marginRight").replace("px", ""));
            return liWidth - marginLeft - marginRight;
        }
    }
    /**
     * 加载数据
     * @param target
     * @param data
     */
    function loadData(target, data) {
        var opts = $.data(target, "sform").options;
        $.when($.isFunction(opts.onBeforeLoadData) ? opts.onBeforeLoadData.call(target, opts.data) : true).then(function() {
            opts.data = data;
            $(target).find(".sform-ul").each(function(j) {
                var cols = opts.queryCols[j].cols, $ul = $(this);
                $ul.find("li").each(function(i) {
                    var name = $(this).attr("name"), value = data[name], col = cols[i], type = col.type;
                    if (value || value === 0) {
                        if (col.formatter) {
                            if (typeof col.formatter == "string") {
                                col.formatter = eval("(" + col.formatter + ")");
                            }
                            if ($.isFunction(col.formatter)) {
                                value = col.formatter.call(this, value, null, name, col);
                            }
                        }
                        if (_views[type]) {
                            value = _views[type](col.editor, value);
                        }
                        $(this).find(".sform-value").text(value).attr("title", value);
                    }
                });
            });
            opts.onLoadSuccess.call(target, data);
        });
    }
    /**
     * 请求数据
     * @param target
     * @param param
     */
    function request(target, param) {
        var opts = $.data(target, "sform").options, req = opts.req;
        param = param || {};
        req = $.extend(req, param);
        $.when($.isFunction(opts.onBeforeRequest) ? opts.onBeforeRequest.call(target, req) : true).then(function() {
            ajaxRequest({
                type: opts.method,
                url: opts.url,
                dataType: opts.dataType,
                req: req,
                func: function(data) {
                    if (data[0].data[0].length) {
                        loadData(target, data[0].data[0][0]);
                    }
                },
                error: function() {
                    opts.onRequestError.call(target);
                }
            });
        });
    }
    /**
     * 重新加载数据
     * @param target
     * @param param
     */
    function reload(target, param) {
        var opts = $.data(target, "sform").options;
        if (opts.req) {
            request(target, param);
        } else if (opts.data) {
            loadData(target, data);
        }
    }
    /**
     * 获取数据源
     * @param target
     * @returns {*}
     */
    function getData(target) {
        var opts = $.data(target, "sform").options;
        return opts.data;
    }
    /**
     * 清空值
     * @param target
     */
    function clear(target) {
        $(target).find(".sform-value").text("");
    }
    /**
     * 将sform转化为form
     * @param target
     */
    function toForm(target) {
        var opts = $.data(target, "sform").options;
        $(target).empty().form(opts);
    }
    //显示值需要转化，转化规则
    var _views = {
        // combobox组件，根据editor转化值
        combobox: function(editor, value) {
            var valueField = editor.options.valueField || "dict_val";
            var textField = editor.options.textField || "dict_des";
            if (editor && editor.options && editor.options.data) {
                var dicts = editor.options.data;
                if (editor.options.multiple) {
                    var values;
                    if (editor.options.singleByte) {
                        values = value.split("");
                    } else {
                        values = value.split(editor.options.separator || ",");
                    }
                    var returnValue = [];
                    for (var i = 0, len = values.length; i < len; i++) {
                        for (var j = 0, lenJ = dicts.length; j < lenJ; j++) {
                            if (values[i] == dicts[j][valueField]) {
                                var text = (textField.split(/,/g).length === 2 ? dicts[j][textField.split(/,/g)[0]] + "-" + dicts[j][textField.split(/,/g)[1]] : dicts[j][textField]) || "";
                                returnValue.push(text.trim());
                                break;
                            }
                        }
                    }
                    if (returnValue.length > 0) {
                        return returnValue.join(",");
                    }
                } else {
                    for (var i = 0, len = dicts.length; i < len; i++) {
                        if (dicts[i][valueField] == value) {
                            var text = (textField.split(/,/g).length === 2 ? dicts[i][textField.split(/,/g)[0]] + "-" + dicts[i][textField.split(/,/g)[1]] : dicts[i][textField]) || "";
                            return text.trim();
                        }
                    }
                }
            }
            return value;
        },
        //timespinner转化为标准时间
        timespinner: function(editor, value) {
            var returnValue = [];
            while (value = value.replace(/(.{2}$)|(^.$)/, function(val) {
                returnValue.unshift(val);
                return "";
            })) {}
            return returnValue.join(":");
        }
    };
    _views.combotree = _views.combobox;
});
