/**
 * combobox组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/combobox", [], function(require, exports, module) {
    $.fn.combobox = function(options, param) {
        if (typeof options == "string") {
            var method = $.fn.combobox.methods[options];
            if (method) {
                return method(this, param);
            } else {
                return this.combo(options, param);
            }
        }
        options = options || {};
        return this.each(function() {
            var state = $.data(this, "combobox");
            if (state) {
                $.extend(state.options, options);
                if ((state.options.dict || state.options.cache || state.options.req && state.options.req.length) && state.options.data.length) {
                    state.options.data = [];
                }
                create(this);
            } else {
                state = $.data(this, "combobox", {
                    options: $.extend({}, $.fn.combobox.defaults, $.fn.combobox.parseOptions(this), options)
                });
                create(this);
                bindEvents(this);
            }
            var transData = transformData(this);
            //装载数据
            if (transData.length) {
                loadData(this, transData);
            } else if (state.options.data.length) {
                loadData(this, state.options.data);
            } else if (state.options.dict) {
                setDict(this, state.options.dict);
            } else if (state.options.cache) {
                setCache(this, state.options.cache);
            } else {
                request(this);
            }
            state.options.text = $.data(this, "combo").options.text = $(this).combo("getText");
        });
    };
    $.fn.combobox.methods = {
        options: function(jq) {
            var opts = $.data(jq[0], "combobox").options;
            opts.originalValue = jq.combo("options").originalValue;
            return opts;
        },
        getData: function(jq) {
            return $.data(jq[0], "combobox").data;
        },
        setValues: function(jq, values) {
            return jq.each(function() {
                setValues(this, values);
            });
        },
        setValue: function(jq, value) {
            return jq.each(function() {
                setValues(this, [ value ]);
            });
        },
        reset: function(jq) {
            return jq.each(function() {
                var opts = $(this).combobox("options");
                if (opts.multiple) {
                    $(this).combobox("setValues", opts.originalValue);
                } else {
                    $(this).combobox("setValue", opts.originalValue);
                }
            });
        },
        loadData: function(jq, data) {
            return jq.each(function() {
                loadData(this, data);
            });
        },
        changeFilter: function(jq, data) {
            return jq.each(function() {
                changeFilter(this, data);
            });
        },
        reload: function(jq, url, param) {
            return jq.each(function() {
                request(this, url, param);
            });
        },
        select: function(jq, value) {
            return jq.each(function() {
                select(this, value);
            });
        },
        unselect: function(jq, value) {
            return jq.each(function() {
                unselect(this, value);
            });
        },
        setDict: function(jq, dictCode) {
            return jq.each(function() {
                setDict(this, dictCode);
            });
        },
        setCache: function(jq, cacheName) {
            return jq.each(function() {
                setCache(this, cacheName);
            });
        },
        selectAll: function(jq) {
            return jq.each(function() {
                selectAll(this);
            });
        },
        unSelectAll: function(jq) {
            return jq.each(function() {
                unSelectAll(this);
            });
        }
    };
    $.fn.combobox.parseOptions = function(target) {
        var t = $(target);
        return $.extend({}, $.fn.combo.parseOptions(target), $.parser.parseOptions(target, [ "valueField", "textField", "mode", "method", "url" ]));
    };
    $.fn.combobox.defaults = $.extend({}, $.fn.combo.defaults, {
        atIsAll: false,
        //对于combobox值，全选等于@，@等于全选
        autoSelectAll: true,
        showDictValue: true,
        width: "auto",
        panelWidth: null,
        panelHeight: "auto",
        valueField: "value",
        textField: "text",
        mode: "local",
        //local,remote
        method: "post",
        editable: true,
        enableQuery: true,
        matchCase: true,
        autoFill: false,
        sort: null,
        url: null,
        data: [],
        req: [],
        keyHandler: {
            up: function() {
                selectPrev(this);
            },
            down: function() {
                selectNext(this);
            },
            enter: function() {
                var panel = $(this).data("combo").panel, values = $(this).combobox("getValues"), $firstItem = panel.find("div.combobox-item:visible:first");
                values = values.length ? values : $firstItem.length ? [ $firstItem.attr("value") ] : [];
                values.length && select(this, values[0]);
                //$(this).combobox('setValues', values);
                $(this).combobox("hidePanel");
            },
            query: function(q) {
                var opts = $(this).combobox("options");
                if (opts.enableQuery) {
                    doQuery(this, q);
                }
            }
        },
        filter: function(q, row) {
            var opts = $(this).combobox("options"), sArr = opts.textField.split(",");
            function isMatch(src, dest, matchCase) {
                return opts.matchCase ? -1 !== src.indexOf(dest) : -1 !== src.toLowerCase().indexOf(dest.toLowerCase());
            }
            if (sArr.length === 1) {
                return isMatch(row[opts.textField], q) || isMatch(row[opts.valueField], q);
            }
            return isMatch(row[sArr[0]], q) || isMatch(row[sArr[1]], q) || isMatch(row[opts.valueField], q);
        },
        formatter: function(row) {
            var opts = $(this).combobox("options");
            if (opts.textField.indexOf(",") > -1) {
                var sArr = opts.textField.split(",");
                return row[sArr[0]] + (row[sArr[0]].toString() ? "-" : "") + row[sArr[1]];
            } else {
                return row[opts.textField];
            }
        },
        loader: function(param, success, error) {
            var opts = $(this).combobox("options");
            if (!opts.url && (!opts.req || opts.req.length == 0)) return false;
            if (opts.req && opts.req.length > 0) {
                if (!opts.req[0].service) opts.req[0]["service"] = "P9999999";
            }
            var resFunc = function(rtnData) {
                if (rtnData && rtnData[0] && $.isFunction(success)) {
                    success(rtnData[0]);
                }
            };
            var p = {
                url: opts.url,
                type: opts.method,
                dataType: "xml",
                async: true,
                responseType: "xml",
                func: resFunc,
                error: function() {
                    error.apply(this, arguments);
                },
                req: opts.req || []
            };
            ajaxRequest(p);
        },
        onBeforeLoad: function(param) {},
        onLoadSuccess: function() {},
        onLoadError: function() {},
        onSelect: function(record) {},
        onUnselect: function(record) {},
        onSelectAll: function(records) {},
        onUnselectAll: function(records) {},
        onClear: $.noop
    });
    function selectAll(target) {
        var opts = $.data(target, "combobox").options;
        if (opts.multiple === false) {
            return;
        }
        setValues(target, $.map(opts.data, function(obj) {
            return obj[opts.valueField];
        }));
        if (opts.onSelect) {
            for (var i = 0; i < opts.data.length; i++) {
                opts.onSelect.call(target, opts.data[i]);
            }
        }
    }
    function unSelectAll(target) {
        var opts = $.data(target, "combobox").options;
        var $target = $(target);
        var valArr = $target.combo("getValues");
        if (!valArr.length) {
            selectAll(target);
            return;
        }
        var tmpArr = [];
        $.each(opts.data, function(index, obj) {
            -1 === $.inArray(obj[opts.valueField] + "", valArr) && tmpArr.push(obj[opts.valueField]);
        });
        $target.combo("clear");
        setValues(target, tmpArr);
    }
    function scrollTo(target, value) {
        var panel = $(target).combo("panel");
        var item = panel.find('div.combobox-item[value="' + value + '"]');
        if (item.length) {
            if (item.position().top <= 0) {
                var h = panel.scrollTop() + item.position().top;
                panel.scrollTop(h);
            } else if (item.position().top + item.outerHeight() > panel.height()) {
                var h = panel.scrollTop() + item.position().top + item.outerHeight() - panel.height();
                panel.scrollTop(h);
            }
        }
    }
    function setSize(target, width) {
        $(target).combo("resize", width);
    }
    function selectPrev(target) {
        var panel = $(target).combo("panel"), values = $(target).combo("getValues"), item = panel.find('div.combobox-item[value="' + values.pop() + '"]').prevAll(":visible:first"), value;
        if (!item.length) {
            item = panel.find("div.combobox-item:visible:last");
        }
        value = item.attr("value");
        select(target, value);
        scrollTo(target, value);
    }
    function selectNext(target) {
        var panel = $(target).combo("panel"), values = $(target).combo("getValues"), item, value;
        if (isComboboxEmpty(target)) {
            //getValues的值为空
            item = getEmptyItem(target);
            if (item.length && isItemSelected(item)) {
                item = item.next(":visible");
            } else {
                item = panel.find("div.combobox-item:visible:first");
            }
        } else {
            item = panel.find('div.combobox-item[value="' + values.pop() + '"]').nextAll(":visible:first");
        }
        if (!item.length) {
            item = panel.find("div.combobox-item:visible:first");
        }
        value = item.attr("value");
        select(target, value);
        scrollTo(target, value);
    }
    function isComboboxEmpty(target) {
        var values = $(target).combo("getValues");
        return !values.length || 1 === values.length && "" === values[0];
    }
    function getEmptyItem(target) {
        return $(target).combo("panel").find("div.combobox-item[value='']");
    }
    function isItemSelected(item) {
        return item.hasClass("combobox-item-selected");
    }
    function select(target, value) {
        var opts = $.data(target, "combobox").options;
        var data = $.data(target, "combobox").data || [];
        if (opts.multiple) {
            var values = $(target).combo("getValues");
            for (var i = 0; i < values.length; i++) {
                if (values[i] == value) return;
            }
            values.push(value);
            setValues(target, values);
        } else {
            setValues(target, [ value ]);
        }
        for (var i = 0; i < data.length; i++) {
            if (data[i][opts.valueField] == value) {
                opts.onSelect.call(target, data[i]);
                break;
            }
        }
    }
    function unselect(target, value) {
        var opts = $.data(target, "combobox").options;
        var data = $.data(target, "combobox").data;
        var values = $(target).combo("getValues");
        for (var i = 0; i < values.length; i++) {
            if (values[i] == value) {
                values.splice(i, 1);
                setValues(target, values);
                break;
            }
        }
        for (var i = 0; i < data.length; i++) {
            if (data[i][opts.valueField] == value) {
                opts.onUnselect.call(target, data[i]);
                return;
            }
        }
    }
    //判断data是否是单字节的
    function isSingleByte(target) {
        var opts = $.data(target, "combobox").options;
        var data = $.data(target, "combobox").data || [];
        var flag = true;
        $.each(data, function(i, v) {
            if (v[opts.valueField].length > 1) {
                return flag = false;
            }
        });
        return flag;
    }
    function setValues(target, values, remainText) {
        var opts = $.data(target, "combobox").options, data = opts.data || [], panel = $(target).combo("panel");
        if (!opts.acceptInput && (!data || !data.length)) {
            $(target).combo("setText", "");
            return;
        }
        var vv = $.isArray(values) ? values : opts.multiple ? -1 === values.indexOf(",") && isSingleByte(target) ? values.split("") : values.split(",") : [ values ], validVal = [], ss = [];
        panel.find(".combobox-item").each(function() {
            var $item = $(this);
            $item.removeClass("combobox-item-selected");
            opts.multiple && $item.find("input").prop("checked", false);
        });
        for (var i = 0; i < vv.length; i++) {
            var v = vv[i], item;
            if (v === "\\") {
                v = "\\\\";
            }
            item = panel.find('.combobox-item[value="' + v + '"]');
            if (item.length) {
                ss.push(getDiaplayText(v) || v);
                validVal.push(v);
                item.addClass("combobox-item-selected");
                opts.multiple && item.find("input").prop("checked", true);
            }
        }
        if (validVal.length) {
            $(target).combo("setValues", validVal, remainText);
            if (!remainText) {
                $(target).combo("setText", ss.join(opts.separator));
            }
        } else {
            if (opts.acceptInput) {
                $(target).combo("setValues", values);
            } else {
                $(target).combo("setValues", []);
                $(target).combo("setText", "");
            }
        }
        function getDiaplayText(value) {
            var text;
            for (var j = 0; j < data.length; j++) {
                if (!data[j]) continue;
                if (data[j][opts.valueField] == value) {
                    if (opts.textField.indexOf(",") > -1) {
                        var sArr = opts.textField.split(",");
                        text = data[j][sArr[0]] + (data[j][sArr[0]].toString() ? "-" : "") + data[j][sArr[1]];
                    } else {
                        text = data[j][opts.textField];
                    }
                    break;
                }
            }
            return text;
        }
    }
    function transformData(target) {
        var opts = $.data(target, "combobox").options;
        var data = [];
        $(">option", target).each(function() {
            var item = {};
            item[opts.valueField] = $(this).attr("value") != undefined ? $(this).attr("value") : $(this).html();
            item[opts.textField] = $(this).html();
            item["selected"] = $(this).attr("selected");
            data.push(item);
        });
        return data;
    }
    function setDict(target, dictCode) {
        var opts = $.data(target, "combobox").options, dictDatas = dict.getDictData(dictCode), dictArr = dictDatas || [], data = [], data = data.concat(dictArr);
        if (opts && opts["default"]) {
            var defs = opts["default"].split(":");
            data.unshift({
                dict_val: defs[0],
                dict_des: defs[1]
            });
        }
        opts["valueField"] = "dict_val";
        opts["textField"] = "dict_des";
        loadData(target, data);
    }
    function setCache(target, cacheName) {
        var opts = $.data(target, "combobox").options;
        var cacheData = cache.getData(cacheName);
        var data = [];
        data = data.concat(cacheData);
        if (opts && opts["default"]) {
            var defs = opts["default"].split(":");
            var tmp = {};
            tmp[opts["valueField"]] = defs[0];
            if (opts["textField"].indexOf(",") > -1) {
                tmp[opts["textField"].split(",")[1]] = defs[1];
            } else {
                tmp[opts["textField"]] = defs[1];
            }
            data.unshift(tmp);
        }
        loadData(target, data);
    }
    function loadData(target, data, remainText) {
        var opts = $.data(target, "combobox").options;
        var panel = $(target).combo("panel");
        $.data(target, "combobox").data = $.extend([], data);
        var selected = $(target).combobox("getValues");
        panel.empty();
        if (typeof data == "undefined" || data.length == 0) return;
        var cData = $.extend([], data);
        if (opts.sort) {
            cData = opts.sort.call(target, cData);
        }
        var retData = [];
        if ("string" === $.type(opts.loadFilter)) {
            var inverse = opts.loadFilter.charAt("0") === "!", filterArr = opts.loadFilter.replace("!", "").split(/[,\|]/), condition = "-1 " + (inverse ? "===" : "!==") + " $.inArray(this[opts.valueField], filterArr)";
            $.each(cData, function() {
                eval(condition) && retData.push(this);
            });
            cData = retData;
        } else if ($.isFunction(opts.loadFilter)) {
            cData = opts.loadFilter.call(target, cData);
        }
        if (opts.extItems) {
            opts.extItems = $.isArray(opts.extItems) ? opts.extItems : eval("(" + opts.extItems + ")");
            opts.extItems.sort(function(prev, next) {
                //空值元素置前
                if ("" === next[opts.valueField]) {
                    return 1;
                }
                return 0;
            });
            var extItems = [];
            $.each(opts.extItems, function(i, item) {
                extItems.push(item);
            });
            $.data(target, "combobox").data = $.extend([], cData = extItems.concat(cData));
        }
        if (opts.dict && opts.showDictValue) {
            opts.valueField = "dict_val";
            opts.textField = "dict_val,dict_des";
        }
        //解释颜色配置项---by lims
        if (opts.col || opts.dataColor) {
            var objCol = opts.col ? opts.col.dataColor : opts.dataColor;
            var _color = [];
            if (objCol) {
                for (var key in objCol) {
                    var arr = objCol[key].split("|");
                    if (arr.length > 1) {
                        for (var a = 0; a < arr.length; a++) {
                            _color.push({
                                item: arr[a],
                                color: key
                            });
                        }
                    } else {
                        _color.push({
                            item: arr[0],
                            color: key
                        });
                    }
                }
            }
        }
        for (var i = 0; i < cData.length; i++) {
            if (typeof cData[i] == "undefined") {
                return;
            }
            var v = $.trim(cData[i][opts.valueField]);
            if ("number" === opts.valDataType) {
                var sArr = opts.textField.split(",");
                cData[i][sArr[0]] = Number(cData[i][sArr[0]]);
                cData[i][opts.valueField] = v = Number(v);
            }
            var s;
            if (opts.textField.indexOf(",") > -1) {
                var sArr = opts.textField.split(",");
                s = cData[i][sArr[0]] + (cData[i][sArr[0]].toString() ? "-" : "") + cData[i][sArr[1]];
            } else {
                s = cData[i][opts.textField];
            }
            //加多color这个变量，控制conbobox里字体的颜色---by lims
            var color = "";
            if (_color) {
                if (_color.length > 0) {
                    $.each(_color, function(a, b) {
                        if (b.item == cData[i].dict_val) {
                            color = b.color;
                            return;
                        }
                    });
                }
            }
            var item = $('<div class="combobox-item" style="color:' + color + '"></div>').appendTo(panel);
            item.attr("value", v);
            if (opts.formatter) {
                item.html(opts.multiple ? '<input type="checkbox" value="' + v + '"><span>' + opts.formatter.call(target, cData[i]) + "</span>" : opts.formatter.call(target, cData[i]));
            } else {
                item.html(opts.multiple ? '<input type="checkbox" value="' + v + '"><span>' + s + "</span>" : s);
            }
            if (cData[i]["selected"]) {
                (function() {
                    for (var i = 0; i < selected.length; i++) {
                        if (v == selected[i]) return;
                    }
                    selected.push(v);
                })();
            }
        }
        opts.data = cData;
        if (selected.length == 0 || selected[0] == "") {
            if (cData.length > 0 && opts.autoFill) {
                selected.push(cData[0][opts.valueField]);
                setTimeout(function() {
                    opts.onSelect.call(target, cData[0]);
                }, 100);
            }
            if (cData.length > 0 && opts.defaultValue !== "") {
                selected.push(opts.defaultValue);
                setValues(target, selected, remainText);
            }
        }
        if (selected.length > 0 && selected.length != 1 || selected[0] != "") {
            var oldValues = $(target).combobox("getValue");
            if (opts.multiple) {
                if (selected[0] == "@" && opts.autoSelectAll) {
                    var panel = $.data(target, "combo").panel;
                    var $select = panel.parent().find(".combo-selectAll");
                    $select.find("span:eq(0)").trigger("click");
                } else {
                    setValues(target, selected, remainText);
                }
            } else {
                if (selected.length) {
                    setValues(target, [ selected[selected.length - 1] ], remainText);
                } else if (cData.length && opts.autoFill) {
                    setValues(target, [ cData[0][opts.valueField] ], remainText);
                } else {
                    setValues(target, [], remainText);
                }
            }
        }
        opts.onLoadSuccess.call(target, cData);
        $(".combobox-item", panel).hover(function() {
            $(this).addClass("combobox-item-hover");
        }, function() {
            $(this).removeClass("combobox-item-hover");
        }).click(function(e) {
            var item = $(this);
            if (opts.multiple) {
                if (item.hasClass("combobox-item-selected")) {
                    item.find(":checkbox").attr("checked", false);
                    item.removeClass("combobox-item-selected");
                    unselect(target, item.attr("value"));
                } else {
                    item.find(":checkbox").attr("checked", true);
                    item.addClass("combobox-item-selected");
                    select(target, item.attr("value"));
                }
                /**
                 * 加入下面这段代码可以解决当combobox设置为可多选时，选择一个后，快捷键失效的问题----by lims
                 */
                //                $(target).combobox().next('span').find('input').eq(0).focus();
                /**
                 * 修改combobox选中一个后，没法全选反选的问题。上面代码每次都会初始化combobox，如果给combobox设置了默认值，会导致选择多选时无法清空。
                 * update zhubc 20160802
                 */
                $(target).parent().find("input.combo-text").focus();
            } else {
                select(target, item.attr("value"));
                $(target).combo("hidePanel");
            }
            e.stopPropagation();
        });
    }
    function changeFilter(target, filter) {
        var $data = $.data(target, "combobox");
        $data.options.loadFilter = filter;
        loadData(target, $data.data);
    }
    function bindEvents(target) {
        var panel = $.data(target, "combo").panel;
        var $select = panel.parent().find(".combo-selectAll");
        $select.find("span:eq(0)").unbind("click").bind("click", function() {
            selectAll(target);
        });
        $select.find("span:eq(1)").unbind("click").bind("click", function() {
            unSelectAll(target);
        });
    }
    function request(target, url, param, remainText) {
        var opts = $.data(target, "combobox").options;
        if (url) {
            opts.url = url;
        }
        param = param || {};
        if (opts.onBeforeLoad.call(target, param) == false) return;
        opts.loader.call(target, param, function(data) {
            loadData(target, data, remainText);
        }, function() {
            opts.onLoadError.apply(this, arguments);
        });
    }
    function doQuery(target, value) {
        var opts = $.data(target, "combobox").options, panel = $(target).combo("panel"), data = $.data(target, "combobox").data || [], vArr = [];
        clearValue(target);
        resetPanel(target);
        if (!value) {
            panel.find("div.combobox-item").show();
            return;
        }
        value = opts.multiple ? value.split(/[\,\，]/) : [ value ];
        panel.find("div.combobox-item").hide();
        $.each(data, function(i, sData) {
            $.each(value, function(j, sValue) {
                if (sValue && opts.filter.call(target, sValue, sData)) {
                    panel.find('div.combobox-item[value="' + sData[opts.valueField] + '"]').show();
                    if (-1 === $.inArray(sData[opts.valueField], vArr)) {
                        vArr.push(sData[opts.valueField] || "");
                    }
                }
            });
        });
        if (!panel.find("div.combobox-item:visible").length) {}
        if (vArr.length && !opts.multiple) {
            //多选情况，不自动选中赋值
            setValues(target, opts.multiple ? vArr : [ vArr[0] ], true);
        }
    }
    function clearValue(target) {
        var combo = $.data(target, "combo").combo;
        combo.find("input.combo-value").remove();
        $('<input type="hidden" class="combo-value" name="' + $(target).attr("comboname") + '">').appendTo(combo);
    }
    function resetPanel(target) {
        $(target).combo("panel").find("div.combobox-item-selected").removeClass("combobox-item-selected").find(":checkbox").attr("checked", false);
    }
    function create(target) {
        var state = $.data(target, "combobox");
        var opts = state.options;
        $(target).addClass("combobox-f");
        $(target).combo($.extend({}, opts, {
            onShowPanel: function() {
                var ph = "auto", data = $.data(target, "combobox").data;
                $(target).combo("panel").find("div.combobox-item").show();
                scrollTo(target, $(target).combobox("getValue"));
                opts.onShowPanel.call(target);
                //在没有设置高度情况下判断是否需要显示滚动条
                if (data && data.length > 10) {
                    ph = opts.panelHeight === ph ? 260 : opts.panelHeight;
                } else {
                    ph = opts.panelHeight === ph ? ph : opts.panelHeight;
                }
                $(target).combo("panel").css({
                    height: ph
                });
            },
            onClear: function() {
                resetPanel(this);
                opts.onClear.call(target);
            },
            onBlur: function(e) {
                var text = $.data(target, "combo").combo.find("input.combo-text"), sValues = $(target).combo("getValues");
                if (text.val()) {
                    if (isComboboxEmpty(target)) {
                        setValues(target, getEmptyItem(target).length ? [ "" ] : []);
                    } else {
                        setValues(target, sValues);
                    }
                }
                _.isFunction(opts.onBlur) && opts.onBlur.call(target, e);
            }
        }));
    }
});
