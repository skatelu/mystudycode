/**
 * pagination组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/pagination", [], function(require, exports, module) {
    $.fn.pagination = function(options, context) {
        if (typeof options == "string") {
            return $.fn.pagination.methods[options](this, context);
        }
        options = options || {};
        return this.each(function() {
            var opts;
            var state = $.data(this, "pagination");
            if (state) {
                opts = $.extend(state.options, options);
            } else {
                opts = $.extend({}, $.fn.pagination.defaults, $.fn.pagination.parseOptions(this), options);
                if (opts.pageSize > 10) {
                    pageSize = Number(opts.pageSize);
                    opts.pageList = opts.pageList ? eval(opts.pageList) : [ pageSize, pageSize + 10, pageSize + 20, pageSize + 30 ];
                }
                $.data(this, "pagination", {
                    options: opts
                });
            }
            buildToolbar(this);
            showInfo(this);
        });
    };
    $.fn.pagination.methods = {
        options: function(jq) {
            return $.data(jq[0], "pagination").options;
        },
        loading: function(jq) {
            return jq.each(function() {
                setLoadStatus(this, true);
            });
        },
        loaded: function(jq) {
            return jq.each(function() {
                setLoadStatus(this, false);
            });
        },
        refresh: function(jq, options) {
            return jq.each(function() {
                showInfo(this, options);
            });
        },
        select: function(jq, page) {
            return jq.each(function() {
                selectPage(this, page);
            });
        }
    };
    $.fn.pagination.parseOptions = function(target) {
        var jq = $(target);
        return $.extend({}, $.parser.parseOptions(target, [ {
            total: "number",
            pageSize: "number",
            pageNumber: "number"
        }, {
            loading: "boolean",
            showPageList: "boolean",
            showRefresh: "boolean"
        } ]), {
            pageList: jq.attr("pageList") ? eval(jq.attr("pageList")) : undefined
        });
    };
    $.fn.pagination.defaults = {
        total: 0,
        pageSize: 10,
        pageNumber: 1,
        pageList: [ 20, 50, 100, 200, 500, 1e3 ],
        loading: false,
        buttons: null,
        showPageList: true,
        showRefresh: true,
        pageNavigate: true,
        // 增加是页显示页数导航按钮
        onSelectPage: function(pageNumber, pageSize) {},
        onBeforeRefresh: function(pageNumber, pageSize) {},
        onRefresh: function(pageNumber, pageSize) {},
        onChangePageSize: function(pageSize) {},
        beforePageText: "Page",
        afterPageText: "/ {pages}",
        displayMsg: "Displaying {from} to {to} of {total} items",
        showTime: true,
        timeMsg: "Time {time}ms",
        fallPageInfoFinish: "Load completed, A total of {total} records",
        fallPageInfoLoading: "Currently loaded 0 records",
        fallPageInfoStart: "Currently loaded {total} records, And data is not loaded"
    };
    function buildToolbar(target) {
        var data = $.data(target, "pagination");
        var opts = data.options;
        var buttons = data.buttons = {};
        var pageButtons = {
            //用于生成最左边的菜单按钮
            menu: {
                iconCls: "icon-grid-menu",
                handler: function() {
                    if (opts.$menu) {
                        var offset = $(this).offset();
                        opts.$menu.menu("show", {
                            left: offset.left,
                            top: offset.top
                        });
                        return;
                    }
                    var $menu = $("<div></div>");
                    var gridMenu = opts.gridMenu;
                    var importTypes = opts.importTypes;
                    var exportTypes = opts.exportTypes;
                    if (!gridMenu) {
                        return;
                    }
                    if (!exportTypes || !$.isArray(exportTypes)) {
                        opts.gridMenu = gridMenu = gridMenu.replace(/(,fileExport)|(fileExport,)/, "");
                    }
                    var buildMenus = {
                        fileExport: function() {
                            var str = [ "<div kui-options=\"iconCls:'icon-export'\"><span>导出</span><div>" ];
                            for (var i = 0, len = exportTypes.length; i < len; i++) {
                                str.push("<div kui-options=\"iconCls:'icon-" + exportTypes[i] + "'\">" + exportTypes[i] + "</div>");
                            }
                            str.push("</div></div>");
                            $(str.join("")).appendTo($menu).find("div div").click(function() {
                                opts.onFileExport($(this).text());
                            });
                        },
                        fileImport: function() {
                            $("<div kui-options=\"iconCls:'icon-import'\">导入</div>").appendTo($menu).click(function() {
                                opts.onFileImport($(this).text());
                            });
                        },
                        freezeColumns: function() {
                            $("<div kui-options=\"iconCls:'icon-lock'\">锁定列</div>").appendTo($menu).click(function() {
                                opts.onFreezeColumns();
                            });
                        },
                        hideColumns: function() {
                            $("<div kui-options=\"iconCls:'icon-hidecol'\">隐藏列</div>").appendTo($menu).click(function() {
                                opts.onHideColumns();
                            });
                        },
                        hideCols: function() {
                            $("<div kui-options=\"iconCls:'icon-client-detail'\">隐藏查询条件</div>").appendTo($menu).click(function() {
                                opts.onHideCols();
                            });
                        },
                        restoreDefault: function() {
                            $("<div kui-options=\"iconCls:'icon-reload'\">恢复默认</div>").appendTo($menu).click(function() {
                                opts.onRestoreDefault();
                            });
                        },
                        printReport: function() {
                            $("<div kui-options=\"iconCls:'icon-print'\">打印报表</div>").appendTo($menu).click(function() {
                                opts.onFileExport("print");
                            });
                        }
                    };
                    //@zhaozz 2016-01-20 去掉字符串的单引号
                    //var gridMenus = gridMenu.split(',');
                    var gridMenus = gridMenu.replace(/\'|’|‘/g, "").split(",");
                    for (var i = 0, len = gridMenus.length; i < len; i++) {
                        if (buildMenus[gridMenus[i]]) {
                            buildMenus[gridMenus[i]]();
                        }
                    }
                    var offset = $(this).offset();
                    opts.$menu = $menu.menu().menu("show", {
                        left: offset.left,
                        top: offset.top
                    });
                }
            },
            //------------------------------------------------------------------------------
            first: {
                iconCls: "pagination-first",
                handler: function() {
                    if (opts.pageNumber > 1) {
                        selectPage(target, 1);
                    }
                }
            },
            prev: {
                iconCls: "pagination-prev",
                handler: function() {
                    if (opts.pageNumber > 1) {
                        selectPage(target, opts.pageNumber - 1);
                    }
                }
            },
            next: {
                iconCls: "pagination-next",
                handler: function() {
                    var pageNumber = Math.ceil(opts.total / opts.pageSize);
                    if (opts.pageNumber < pageNumber) {
                        selectPage(target, opts.pageNumber + 1);
                    }
                }
            },
            last: {
                iconCls: "pagination-last",
                handler: function() {
                    var pageNumber = Math.ceil(opts.total / opts.pageSize);
                    if (opts.pageNumber < pageNumber) {
                        selectPage(target, pageNumber);
                    }
                }
            },
            refresh: {
                iconCls: "pagination-load",
                handler: function() {
                    $(target).prev("div.datagrid-row-detail").hide();
                    if (opts.onBeforeRefresh.call(target, opts.pageNumber, opts.pageSize) != false) {
                        selectPage(target, opts.pageNumber);
                        opts.onRefresh.call(target, opts.pageNumber, opts.pageSize);
                    }
                }
            }
        };
        var table = $(target).addClass("pagination").html('<table cellspacing="0" cellpadding="0" border="0"><tr></tr></table>');
        var tr = table.find("tr");
        function createButton(buttonName) {
            var a = $('<a href="javascript:void(0)"></a>').appendTo(tr);
            a.wrap("<td></td>");
            a.linkbutton({
                iconCls: pageButtons[buttonName].iconCls,
                plain: true
            }).unbind(".pagination").bind("click.pagination", pageButtons[buttonName].handler);
            return a;
        }
        //-----------------------------------------------------------------------
        //利用gridMenu生成菜单和利用fallParas隐藏菜单
        if (opts.gridMenu) {
            createButton("menu");
        }
        if (opts.fallParas && opts.fallParas[0].enable) {
            opts.showPageList = false;
            opts.showRefresh = false;
            opts.pageNavigate = false;
            //增加瀑布分页时可选择一次加载的数目
            if (opts.fallParas[0].showPageList) {
                var ps = $('<select class="pagination-page-list"></select>');
                ps.bind("change", function() {
                    opts.fallParas[0]["cntNum"] = this.value;
                });
                var options = [], selectNum = opts.fallParas[0]["pageList"];
                for (var i = 0; i < selectNum.length; i++) {
                    if (selectNum[i] == opts.fallParas[0]["cntNum"]) {
                        options.push("<option selected=true>");
                    } else {
                        options.push("<option>");
                    }
                    options.push(selectNum[i]);
                    options.push("</option>");
                }
                $(options.join("")).appendTo(ps);
                $("<td></td>").append(ps).appendTo(tr);
            }
        }
        //-----------------------------------------------------------------------
        if (opts.showPageList) {
            var ps = $('<select class="pagination-page-list"></select>');
            ps.bind("change", function() {
                opts.pageSize = parseInt($(this).val());
                opts.onChangePageSize.call(target, opts.pageSize);
                selectPage(target, opts.pageNumber);
            });
            var options = [];
            for (var i = 0; i < opts.pageList.length; i++) {
                options.push("<option>");
                options.push(opts.pageList[i]);
                options.push("</option>");
            }
            $(options.join("")).appendTo(ps);
            $("<td></td>").append(ps).appendTo(tr);
            $('<td><div class="pagination-btn-separator"></div></td>').appendTo(tr);
        }
        if (opts.pageNavigate) {
            // 增加页数导航的隐藏性
            buttons.first = createButton("first");
            buttons.prev = createButton("prev");
            $('<td><div class="pagination-btn-separator"></div></td>').appendTo(tr);
            $('<span style="padding-left:6px;"></span>').html(opts.beforePageText).appendTo(tr).wrap("<td></td>");
            buttons.num = $('<input class="pagination-num" type="text" value="1" size="2">').appendTo(tr).wrap("<td></td>");
            buttons.num.unbind(".pagination").bind("keydown.pagination", function(e) {
                if (e.keyCode == 13) {
                    var page = parseInt($(this).val()) || 1;
                    selectPage(target, page);
                    return false;
                }
            });
            buttons.after = $('<span style="padding-right:6px;"></span>').appendTo(tr).wrap("<td></td>");
            $('<td><div class="pagination-btn-separator"></div></td>').appendTo(tr);
            buttons.next = createButton("next");
            buttons.last = createButton("last");
        }
        if (opts.showRefresh) {
            $('<td><div class="pagination-btn-separator"></div></td>').appendTo(tr);
            buttons.refresh = createButton("refresh");
        }
        if (opts.buttons) {
            var aButton = [];
            aButton.push('<td><div class="pagination-btn-separator"></div></td>');
            for (var i = 0; i < opts.buttons.length; i++) {
                var btn = opts.buttons[i];
                if (btn == "-") {
                    aButton.push('<td><div class="pagination-btn-separator"></div></td>');
                } else {
                    aButton.push('<td><a href="javascript:void(0)" _pagination_button_index_="' + i + '"></a></td>');
                }
            }
            var $btns = $(aButton.join(""));
            $btns.appendTo(tr);
            $("a", $btns).each(function() {
                var index = $(this).attr("_pagination_button_index_");
                var btn = opts.buttons[index];
                $(this).linkbutton($.extend(btn, {
                    plain: true
                })).bind("click", eval(btn.handler || function() {}));
            });
            var detailButton = $btns.find("input[name=datagrid_detail_button]")[0];
            if (detailButton) {
                detailButton.checked = opts.isShowDetail;
            }
        }
        $('<div class="pagination-info"></div>').appendTo(table);
        $('<div style="clear:both;"></div>').appendTo(table);
    }
    function selectPage(target, page) {
        var opts = $.data(target, "pagination").options;
        showInfo(target, {
            pageNumber: page
        });
        opts.onSelectPage.call(target, opts.pageNumber, opts.pageSize);
    }
    function showInfo(target, options) {
        var opts = $.data(target, "pagination").options;
        var timeMsg = opts.timeMsg;
        $.extend(opts, options || {});
        if (opts.fallParas && opts.fallParas[0].enable) {
            // 在瀑布模型中的提示信息
            var msg = opts.fallParas[0].last ? opts.fallPageInfoFinish : opts.total == 0 ? opts.fallPageInfoLoading : opts.fallPageInfoStart;
            msg = msg.replace(/{total}/, opts.total);
            if (opts.showTime) {
                timeMsg = timeMsg.replace(/{time}/, opts.time || 0);
                msg = timeMsg + " " + msg;
            }
            //20160506 linbk  对 opts.fallParas[0].last 进行判断，如果加载完成了，则隐藏下一页按钮
            $(target).find("span:contains('下一页')").css("display", opts.fallParas[0].last ? "none" : "inline-block");
            $(target).find("div.pagination-info").html(msg);
            return;
        }
        //-----------------------------------------------------------------
        var buttons = $.data(target, "pagination").buttons;
        var ps = $(target).find("select.pagination-page-list");
        if (ps.length) {
            ps.val(opts.pageSize + "");
            opts.pageSize = parseInt(ps.val());
        }
        var pageCount = Math.ceil(opts.total / opts.pageSize) || 1;
        if (opts.pageNumber < 1) {
            opts.pageNumber = 1;
        }
        if (opts.pageNumber > pageCount) {
            opts.pageNumber = pageCount;
        }
        if (buttons.num) {
            buttons.num.val(opts.pageNumber);
        }
        if (buttons.after) {
            buttons.after.html(opts.afterPageText.replace(/{pages}/, pageCount));
        }
        var pinfo = opts.displayMsg;
        pinfo = pinfo.replace(/{from}/, opts.total == 0 ? 0 : opts.pageSize * (opts.pageNumber - 1) + 1);
        pinfo = pinfo.replace(/{to}/, Math.min(opts.pageSize * opts.pageNumber, opts.total));
        pinfo = pinfo.replace(/{total}/, opts.total);
        if (opts.showTime) {
            timeMsg = timeMsg.replace(/{time}/, opts.time || 0);
            pinfo = timeMsg + " " + pinfo;
        }
        $(target).find("div.pagination-info").html(pinfo);
        if (buttons.first) {
            buttons.first.add(buttons.prev).linkbutton({
                disabled: opts.pageNumber == 1
            });
        }
        if (buttons.next) {
            buttons.next.add(buttons.last).linkbutton({
                disabled: opts.pageNumber == pageCount
            });
        }
        setLoadStatus(target, opts.loading);
    }
    function setLoadStatus(target, loading) {
        var opts = $.data(target, "pagination").options;
        var buttons = $.data(target, "pagination").buttons;
        opts.loading = loading;
        if (opts.showRefresh) {
            if (opts.loading) {
                buttons.refresh.linkbutton({
                    iconCls: "pagination-loading"
                });
            } else {
                buttons.refresh.linkbutton({
                    iconCls: "pagination-load"
                });
            }
        }
    }
});
