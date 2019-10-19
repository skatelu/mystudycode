/**
 * datagrid组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/datagrid", [], function(require, exports, module) {
    var datagridId = 0, g_user = {
        userName: top.kjdp.getOpInfo ? top.kjdp.getOpInfo().USER_NAME : "",
        orgName: top.kjdp.getOpInfo ? top.kjdp.getOpInfo().ORG_NAME : ""
    };
    $.fn.datagrid = function(options, context) {
        if (typeof options == "string") {
            var args = [ this ];
            return $.fn.datagrid.methods[options].apply(this, args.concat(Array.prototype.slice.call(arguments, 1)));
        }
        options = options || {};
        return this.each(function() {
            var target = this;
            var state = $.data(target, "datagrid");
            var opts;
            if (state) {
                opts = $.extend(state.options, options);
                state.options = opts;
            } else {
                opts = $.extend(true, {}, $.extend({}, $.fn.datagrid.defaults, {
                    queryParams: {}
                }), $.fn.datagrid.parseOptions(target), options);
                $(target).css("width", "").css("height", "").attr("datagrid", "datagrid");
                var wrapResult = wrapGrid(target, opts.rownumbers);
                if (!opts.columns) {
                    opts.columns = wrapResult.columns;
                }
                if (!opts.frozenColumns) {
                    opts.frozenColumns = wrapResult.frozenColumns;
                }
                $.data(target, "datagrid", {
                    options: opts,
                    panel: wrapResult.panel,
                    dc: wrapResult.dc,
                    selectedRows: [],
                    data: {
                        total: 0,
                        rows: []
                    },
                    originalRows: [],
                    updatedRows: [],
                    insertedRows: [],
                    deletedRows: []
                });
            }
            state = state || $.data(target, "datagrid");
            opts = opts || state.options;
            if (_.isArray(opts.pageList) && opts.pageList.length > 0 && !_.contains(opts.pageList, opts.pageSize)) {
                opts.pageSize = opts.pageList[0];
            }
            var hideArray = parseColumns(target);
            //解析列的参数，用于列拖拽，列隐藏和列锁定
            $.each(opts.columns[0] || [], function() {
                if (this["type"] === "checkbox") {
                    this["checkbox"] = true;
                }
            });
            $.when(opts.onBeforeInit.call(target)).then(function() {
                create(target);
                if (!opts.queryCols || opts.queryCols.length == 0) {
                    //有查询框时已在form的初始化完成回调中调用
                    if (!opts.required) request(target);
                }
                if (!state) {
                    var data = getData(target);
                    if (data.total > 0) {
                        loadData(target, data);
                        saveOriginalRows(target);
                    }
                }
                setSize(target);
                bindEvents(target);
                if (opts.isMoveColumns) {
                    addMoveColumn(target);
                }
                if (opts.isHideColumns && hideArray.length > 0) {
                    hideColumns(target, hideArray);
                }
                opts.onInitSuccess.call(target);
            });
        });
    };
    $.fn.datagrid.methods = {
        options: function(jq) {
            var options = $.data(jq[0], "datagrid").options;
            var panelOptions = $.data(jq[0], "datagrid").panel.panel("options");
            var opts = $.extend(options, {
                width: panelOptions.width,
                height: panelOptions.height,
                closed: panelOptions.closed,
                collapsed: panelOptions.collapsed,
                minimized: panelOptions.minimized,
                maximized: panelOptions.maximized
            });
            var pager = jq.datagrid("getPager");
            if (pager.length) {
                var pagerOptions = pager.pagination("options");
                $.extend(opts, {
                    pageNumber: pagerOptions.pageNumber,
                    pageSize: pagerOptions.pageSize
                });
            }
            return opts;
        },
        getPanel: function(jq) {
            return $.data(jq[0], "datagrid").panel;
        },
        getPager: function(jq) {
            return $.data(jq[0], "datagrid").panel.children("div.datagrid-pager");
        },
        getColumnFields: function(jq, frozen) {
            return getColumnFields(jq[0], frozen);
        },
        getColumnOption: function(jq, field) {
            return getColumnOption(jq[0], field);
        },
        getData: function(jq, row, cell) {
            return getData(jq[0], row, cell);
        },
        resize: function(jq, size) {
            return jq.each(function() {
                setSize(this, size);
            });
        },
        loading: function(jq) {
            return jq.each(function() {
                var opts = $.data(this, "datagrid").options;
                if (!opts.isShowLoading) {
                    return false;
                }
                if (opts.pagination) {
                    $(this).datagrid("getPager").pagination("loading");
                }
                if (opts.loadMsg) {
                    var panel = $(this).datagrid("getPanel");
                    $('<div class="datagrid-mask" style="display:block"></div>').appendTo(panel);
                    var msg = $('<div class="datagrid-mask-msg" style="display:block"></div>').html(opts.loadMsg).appendTo(panel);
                    msg.css("left", (panel.width() - msg.outerWidth()) / 2);
                }
            });
        },
        loaded: function(jq) {
            return jq.each(function() {
                var opts = $.data(this, "datagrid").options;
                if (opts.pagination) {
                    $(this).datagrid("getPager").pagination("loaded");
                }
                var panel = $(this).datagrid("getPanel");
                panel.children("div.datagrid-mask-msg").remove();
                panel.children("div.datagrid-mask").remove();
                opts.editing = undefined;
            });
        },
        loadData: function(jq, data, addition) {
            return jq.each(function() {
                loadData(this, data, addition);
            });
        },
        fitColumns: function(jq) {
            return jq.each(function() {
                fitColumns(this);
            });
        },
        fixColumnSize: function(jq, field) {
            return jq.each(function() {
                fixColumnSize(this, field);
            });
        },
        fixRowHeight: function(jq, index) {
            return jq.each(function() {
                fixRowHeight(this, index);
            });
        },
        autoSizeColumn: function(jq, field) {
            return jq.each(function() {
                autoSizeColumn(this, field);
            });
        },
        getRows: function(jq) {
            return $.data(jq[0], "datagrid").data.rows;
        },
        getRowIndex: function(jq, row) {
            return getRowIndex(jq[0], row);
        },
        getSelected: function(jq) {
            var rows = getSelections(jq[0]);
            return rows.length > 0 ? rows[0] : null;
        },
        getSelections: function(jq) {
            return getSelections(jq[0]);
        },
        clearSelections: function(jq) {
            return jq.each(function() {
                var selectedRows = $.data(this, "datagrid").selectedRows;
                selectedRows.splice(0, selectedRows.length);
                var state = $.data(this, "datagrid");
                var dc = state.dc;
                var header = dc.header1.add(dc.header2);
                var $headCheckbox = $("input[type=checkbox]", header);
                unselectAll(this);
                $headCheckbox.attr("checked", false);
            });
        },
        selectAll: function(jq) {
            return jq.each(function() {
                selectAll(this);
            });
        },
        unselectAll: function(jq) {
            return jq.each(function() {
                unselectAll(this);
            });
        },
        reload: function(jq, queryParams, callback) {
            return jq.each(function() {
                request(this, queryParams, undefined, callback);
            });
        },
        selectRow: function(jq, index) {
            return jq.each(function() {
                selectRow(this, index);
            });
        },
        selectRecord: function(jq, field) {
            return jq.each(function() {
                selectRecord(this, field);
            });
        },
        getSelectRows: function(jq) {
            return getSelectRows(jq[0]);
        },
        getCheckedRows: function(jq) {
            return getCheckedRows(jq[0]);
        },
        getUnCheckedRows: function(jq) {
            return getUnCheckedRows(jq[0]);
        },
        unselectRow: function(jq, index) {
            return jq.each(function() {
                unselectRow(this, index);
            });
        },
        /**
         * @param boolean bool 是否触发 onCheck回调
         */
        checkRow: function(jq, index, bool) {
            return jq.each(function() {
                checkRow(this, index, true, bool);
            });
        },
        /**
         * @param boolean bool 是否触发 onUnCheck回调
         */
        uncheckRow: function(jq, index, bool) {
            return jq.each(function() {
                uncheckRow(this, index, false, bool);
            });
        },
        getEditor: function(jq, opts) {
            return getEditor(jq[0], opts);
        },
        destroyEditor: function(jq, index) {
            return jq.each(function() {
                destroyEditor(this, index);
            });
        },
        beginEdit: function(jq, index) {
            return jq.each(function() {
                beginEdit(this, index);
            });
        },
        endEdit: function(jq, lastIndex, cancelEdit) {
            return jq.each(function() {
                endEdit(this, lastIndex, cancelEdit);
            });
        },
        refreshRow: function(jq, index) {
            return jq.each(function() {
                var opts = $.data(this, "datagrid").options;
                opts.view.refreshRow.call(opts.view, this, index);
            });
        },
        updateRow: function(jq, index, row) {
            return jq.each(function() {
                var opts = $.data(this, "datagrid").options;
                opts.view.updateRow.call(opts.view, this, index, row);
            });
        },
        validateRow: function(jq, index) {
            return validateRow(jq[0], index);
        },
        appendRow: function(jq, row) {
            return jq.each(function() {
                insertRow(this, {
                    row: row,
                    index: null
                });
            });
        },
        insertRow: function(jq, row) {
            return jq.each(function() {
                insertRow(this, row);
            });
        },
        deleteRow: function(jq, index, tag) {
            return jq.each(function() {
                deleteRow(this, index, tag);
            });
        },
        deleteAllSelections: function(jq, loadAgain) {
            return jq.each(function() {
                deleteAllSelections(this, loadAgain);
            });
        },
        getChanges: function(jq, operate) {
            return getChanges(jq[0], operate);
        },
        acceptChanges: function(jq) {
            return jq.each(function() {
                acceptChanges(this);
            });
        },
        rejectChanges: function(jq) {
            return jq.each(function() {
                rejectChanges(this);
            });
        },
        mergeCells: function(jq, span) {
            return jq.each(function() {
                mergeCells(this, span);
            });
        },
        showColumn: function(jq, field) {
            return jq.each(function() {
                var panel = $(this).datagrid("getPanel");
                if ($.isArray(field)) {
                    //支持传入的为数组
                    var that = this;
                    $.each(field, function() {
                        var tmp = this;
                        panel.find('td[field="' + tmp + '"]').show();
                        $(that).datagrid("getColumnOption", tmp).hidden = false;
                    });
                } else {
                    panel.find('td[field="' + field + '"]').show();
                    $(this).datagrid("getColumnOption", field).hidden = false;
                }
                $(this).datagrid("fitColumns");
            });
        },
        hideColumn: function(jq, field) {
            return jq.each(function() {
                var panel = $(this).datagrid("getPanel");
                if ($.isArray(field)) {
                    //支持传入的为数组
                    var that = this;
                    $.each(field, function() {
                        var tmp = this;
                        panel.find('td[field="' + tmp + '"]').hide();
                        $(that).datagrid("getColumnOption", tmp).hidden = true;
                    });
                } else {
                    panel.find('td[field="' + field + '"]').hide();
                    $(this).datagrid("getColumnOption", field).hidden = true;
                }
                $(this).datagrid("fitColumns");
            });
        },
        fileReport: function(jq, param) {
            onFileExport(jq[0], "pdf", param);
        },
        fileExport: function(jq, param) {
            onFileExport(jq[0], param.type, param, param.fileType, true);
        },
        showAllReport: function(jq, fileType) {
            // 导出全部可见列数据，fileType目前仅支持txt和pdf
            onFileExport(jq[0], "print", undefined, fileType, true);
        },
        onFileImport: function(jq, param) {
            onFileImport(jq[0], param);
        },
        clean: function(jq) {
            return jq.each(function() {
                clean(this);
            });
        },
        getOriginalColumns: function(jq, ignoreHidden) {
            return getOriginalColumns(jq[0], ignoreHidden);
        },
        getSubRow: function(jq, index) {
            return getSubRow(jq[0], index, 2);
        },
        fixSubRowHeight: function(jq, index) {
            return jq.each(function() {
                fixSubRowHeight(this, index);
            });
        },
        getToolbarButton: function(jq, arg) {
            return getToolbarButton(jq[0], arg);
        },
        getUIRowData: function(jq, arg) {
            return getUIRowData(jq[0], arg);
        },
        getOriginalRowsData: function(jq) {
            var opts = $.data(jq[0], "datagrid").options;
            return opts.originalRowsData || [];
        },
        checkAll: function(jq, arg) {
            return jq.each(function() {
                checkAll(this, arg);
            });
        },
        uncheckAll: function(jq, arg) {
            return jq.each(function() {
                uncheckAll(this, arg);
            });
        }
    };
    $.fn.datagrid.parseOptions = function(target) {
        var jq = $(target);
        return $.extend({}, $.fn.panel.parseOptions(target), $.parser.parseOptions(target, [ "url", "toolbar", "idField", "sortName", "sortOrder", "pagePosition", {
            fitColumns: "boolean",
            autoRowHeight: "boolean",
            striped: "boolean",
            nowrap: "boolean"
        }, {
            rownumbers: "boolean",
            subrow: "boolean",
            singleSelect: "boolean",
            checkOnSelect: "boolean",
            selectOnCheck: "boolean"
        }, {
            pagination: "boolean",
            remotePagination: "boolean",
            pageSize: "number",
            pageNumber: "number"
        }, {
            remoteSort: "boolean",
            showHeader: "boolean"
        }, {
            allowMove: "boolean"
        }, {
            scrollbarSize: "number"
        } ]), {
            pageList: jq.attr("pageList") ? eval(jq.attr("pageList")) : undefined,
            loadMsg: jq.attr("loadMsg") != undefined ? jq.attr("loadMsg") : undefined,
            rowStyler: jq.attr("rowStyler") ? eval(jq.attr("rowStyler")) : undefined
        });
    };
    var view = {
        render: function(target, body, isFrozen, appendRows) {
            var state = $.data(target, "datagrid");
            var opts = state.options;
            var rows = appendRows || state.data.rows;
            if (appendRows && appendRows.length === 0) {
                rows = state.data.rows;
            }
            var fields = $(target).datagrid("getColumnFields", isFrozen);
            if (isFrozen) {
                if (!(opts.rownumbers || opts.subrow || opts.frozenColumns && opts.frozenColumns.length)) {
                    return;
                }
            }
            var table = [];
            if (appendRows && appendRows.length) {
                var total = parseInt(state.data.total);
                var baseNum = total - rows.length;
                for (var i = baseNum; i < total; i++) {
                    var cls = i % 2 && opts.striped ? 'class="datagrid-row datagrid-row-alt"' : 'class="datagrid-row"';
                    var rowStyle = opts.rowStyler ? opts.rowStyler.call(target, i, rows[i - baseNum]) : "";
                    var style = rowStyle ? 'style="' + rowStyle + '"' : "";
                    var rowId = state.rowIdPrefix + "-" + (isFrozen ? 1 : 2) + "-" + i;
                    table.push('<tr id="' + rowId + '" datagrid-row-index="' + i + '" ' + cls + " " + style + ">");
                    table.push(this.renderRow.call(this, target, fields, isFrozen, i, rows[i - baseNum]));
                    table.push("</tr>");
                }
                $(body).find("tbody").append(table.join(""));
                if (rows.length > opts.maxRows) {
                    $(body).scrollTop(0);
                } else {
                    if (total > opts.maxRows) {
                        if (rows.length !== 0) {
                            $(body).scrollTop((opts.maxRows - rows.length) * opts.trHeight + $(body).parent().height());
                        }
                    } else {
                        if (rows.length !== 0) {
                            $(body).scrollTop(baseNum * opts.trHeight);
                        }
                    }
                }
            } else {
                table.push('<table class="datagrid-btable" cellspacing="0" cellpadding="0" border="0"><tbody>');
                for (var i = 0; i < rows.length; i++) {
                    var cls = i % 2 && opts.striped ? 'class="datagrid-row datagrid-row-alt"' : 'class="datagrid-row"';
                    var rowStyle = opts.rowStyler ? opts.rowStyler.call(target, i, rows[i]) : "";
                    var style = rowStyle ? 'style="' + rowStyle + '"' : "";
                    var rowId = state.rowIdPrefix + "-" + (isFrozen ? 1 : 2) + "-" + i;
                    table.push('<tr id="' + rowId + '" datagrid-row-index="' + i + '" ' + cls + " " + style + ">");
                    table.push(this.renderRow.call(this, target, fields, isFrozen, i, rows[i]));
                    table.push("</tr>");
                }
                table.push("</tbody></table>");
                var a = table.join("");
                $(body)[0].innerHTML = table.join("");
                if (!appendRows) {
                    $(body).scrollTop(0);
                }
            }
        },
        renderFooter: function(target, footer, frozen) {
            var opts = $.data(target, "datagrid").options;
            var rows = $.data(target, "datagrid").footer || [];
            var fields = $(target).datagrid("getColumnFields", frozen);
            var footerHtml = [ '<table class="datagrid-ftable" cellspacing="0" cellpadding="0" border="0"><tbody>' ];
            for (var i = 0; i < rows.length; i++) {
                footerHtml.push('<tr class="datagrid-row" datagrid-row-index="' + i + '">');
                footerHtml.push(this.renderRow.call(this, target, fields, frozen, i, rows[i]));
                footerHtml.push("</tr>");
            }
            footerHtml.push("</tbody></table>");
            $(footer).html(footerHtml.join(""));
        },
        renderRow: function(target, fields, frozen, index, row) {
            var opts = $.data(target, "datagrid").options;
            var rowHtml = [], contextObj = parser.getContextObj();
            if (frozen && (opts.rownumbers || opts.subrow)) {
                var rowNumber = index + 1;
                if (opts.pagination) {
                    rowNumber += (opts.pageNumber - 1) * opts.pageSize;
                }
                opts.rownumbers ? rowHtml.push('<td class="datagrid-td-rownumber"><div class="datagrid-cell-rownumber">' + rowNumber + "</div></td>") : rowHtml.push('<td class="datagrid-td-subrow"><div class="datagrid-cell-subrow"><span class="datagrid-row-expander datagrid-row-expand" style="display:inline-block;width:16px;height:16px;cursor:pointer;"></span></div></td>');
            }
            for (var i = 0; i < fields.length; i++) {
                var field = fields[i];
                row[field] = gridDataValueReplace(row[field]);
                var col = $(target).datagrid("getColumnOption", field);
                if (col && col.type != "notice") {
                    var styler = col.styler ? col.styler(row[field], row, index) || "" : "";
                    var colStyle = col.hidden ? 'style="display:none;' + styler + '"' : col.listHidden ? 'style="display:none;' + styler + '"' : styler ? 'style="' + styler + '"' : "";
                    //rowHtml.push("<td field=\"" + field + "\" " + colStyle + ">");
                    //rowHtml.push("<td title='"+row[field]+"' field=\"" + field + "\" " + colStyle + ">");
                    var colText = "";
                    if (col.formatter) {
                        var formatter = null, fFormatter = $.data(target, "formatter");
                        if (typeof col.formatter == "string") {
                            if (fFormatter && fFormatter[col.formatter]) {
                                formatter = fFormatter[col.formatter];
                            } else if (contextObj && contextObj[col.formatter]) {
                                formatter = contextObj[col.formatter];
                            } else {
                                eval("formatter=" + col.formatter);
                            }
                        } else {
                            formatter = col.formatter;
                        }
                        var val = formatter.call(target, row[field], row, index, col);
                        if (val && typeof val === "string" && val.indexOf("<") > -1) {
                            rowHtml.push("<td title='" + (val.indexOf("<") > -1 ? $(val).text() : val) + "' field=\"" + field + '" ' + colStyle + ">");
                        } else {
                            rowHtml.push("<td title='" + val + "' field=\"" + field + '" ' + colStyle + ">");
                        }
                    } else if (col.editor && col.editor.options && col.editor.options.data) {
                        col.editor.options.valueField = col.editor.options.valueField || "dict_val";
                        col.editor.options.textField = col.editor.options.textField || "dict_des";
                        rowHtml.push("<td title='" + getEdtDictText(col, row[field], row) + "' field=\"" + field + '" ' + colStyle + ">");
                    } else if (col.editor && col.editor.options && col.editor.options.dict) {
                        col.editor.options.valueField = "dict_val";
                        col.editor.options.textField = "dict_des";
                        rowHtml.push("<td title='" + getEdtDictText(col, row[field], row) + "' field=\"" + field + '" ' + colStyle + ">");
                    } else if (col.editor && col.editor.options && col.editor.options.cache) {
                        rowHtml.push("<td title='" + getEdtCacheText(col, row[field]) + "' field=\"" + field + '" ' + colStyle + ">");
                    } else if (col.textField) {
                        var textFields = col.textField.split(",");
                        colText = _.map(textFields, function(v) {
                            return row[v] || "";
                        }).join("-");
                        rowHtml.push("<td title='" + colText + "' field=\"" + field + '" ' + colStyle + ">");
                    } else {
                        rowHtml.push("<td title='" + row[field] + "' field=\"" + field + '" ' + colStyle + ">");
                    }
                    var style = "";
                    if (col.checkbox) {
                        style = "";
                    } else {
                        style += "text-align:" + (col.align || "left") + ";";
                        if (!opts.nowrap) {
                            style += "white-space:normal;height:auto;";
                        } else {
                            if (opts.autoRowHeight) {
                                style += "height:auto;";
                            }
                        }
                    }
                    rowHtml.push('<div style="' + style + '" ');
                    if (col.checkbox) {
                        rowHtml.push('class="datagrid-cell-check ');
                    } else {
                        rowHtml.push('class="datagrid-cell ' + col.cellClass);
                    }
                    rowHtml.push('">');
                    if (col.checkbox) {
                        rowHtml.push('<input type="checkbox" name="' + field + '" value="' + (row[field] != undefined ? row[field] : "") + '"/>');
                    } else {
                        if (col.formatter) {
                            var formatter = null;
                            if (typeof col.formatter == "string") {
                                if (fFormatter && fFormatter[col.formatter]) {
                                    formatter = fFormatter[col.formatter];
                                } else if (contextObj && contextObj[col.formatter]) {
                                    formatter = contextObj[col.formatter];
                                } else {
                                    eval("formatter=" + col.formatter);
                                }
                            } else {
                                formatter = col.formatter;
                            }
                            rowHtml.push(formatter.call(target, row[field], row, index, col));
                        } else if (col.editor && col.editor.options && col.editor.options.data) {
                            col.editor.options.valueField = col.editor.options.valueField || "dict_val";
                            col.editor.options.textField = col.editor.options.textField || "dict_des";
                            rowHtml.push(getEdtDictText(col, row[field], row));
                        } else if (col.editor && col.editor.options && col.editor.options.dict) {
                            col.editor.options.valueField = "dict_val";
                            col.editor.options.textField = "dict_des";
                            rowHtml.push(getEdtDictText(col, row[field], row));
                        } else if (col.editor && col.editor.options && col.editor.options.cache) {
                            rowHtml.push(getEdtCacheText(col, row[field]));
                        } else if (col.type == "numberbox") {
                            row[field] = $.type(row[field]) !== "number" ? row[field].indexOf(".") == 0 ? "0" + row[field] : row[field] : row[field];
                            var tempVal = row[field];
                            if ("" !== tempVal && undefined !== tempVal && !isNaN(tempVal) && col.fixNum) {
                                tempVal = Number(tempVal).toFixed(col.fixNum);
                            }
                            rowHtml.push(kui.formatNumber(tempVal));
                        } else if (colText) {
                            rowHtml.push(colText);
                        } else {
                            rowHtml.push(row[field]);
                        }
                    }
                    rowHtml.push("</div>");
                    rowHtml.push("</td>");
                }
            }
            return rowHtml.join("");
        },
        refreshRow: function(target, index) {
            var row = {};
            var fields = $(target).datagrid("getColumnFields", true).concat($(target).datagrid("getColumnFields", false));
            for (var i = 0; i < fields.length; i++) {
                row[fields[i]] = undefined;
            }
            var rows = $(target).datagrid("getRows");
            $.extend(row, rows[index]);
            this.updateRow.call(this, target, index, row);
        },
        updateRow: function(target, index, row) {
            var opts = $.data(target, "datagrid").options;
            var rows = $(target).datagrid("getRows");
            var tr = opts.finder.getTr(target, index);
            var fFormatter = $.data(target, "formatter");
            var contextObj = parser.getContextObj();
            for (var field in row) {
                rows[index][field] = row[field];
                var td = tr.children('td[field="' + field + '"]');
                var cell = td.find("div.datagrid-cell");
                var col = $(target).datagrid("getColumnOption", field);
                if (col) {
                    var style = col.styler ? col.styler(rows[index][field], rows[index], index) : "";
                    td.attr("style", style || "");
                    if (col.hidden || col.listHidden) {
                        td.hide();
                    }
                    if (col.formatter) {
                        var formatter = null;
                        if (typeof col.formatter == "string") {
                            if (fFormatter && fFormatter[col.formatter]) {
                                formatter = fFormatter[col.formatter];
                            } else if (contextObj && contextObj[col.formatter]) {
                                formatter = contextObj[col.formatter];
                            } else {
                                eval("formatter=" + col.formatter);
                            }
                        } else {
                            formatter = col.formatter;
                        }
                        cell.html(formatter.call(target, rows[index][field], rows[index], index, col));
                    } else if (col.editor && col.editor.options && col.editor.options.data) {
                        cell.html(getEdtDictText(col, row[field], row) + "");
                    } else if (col.editor && col.editor.options && col.editor.options.cache) {
                        cell.html(getEdtCacheText(col, row[field]) + "");
                    } else if (col.textField) {
                        var textFields = col.textField.split(","), colText = _.map(textFields, function(v) {
                            return row[v] || "";
                        }).join("-");
                        cell.html(colText);
                    } else {
                        typeof rows[index][field] == "object" && rows[index][field].length > 0 ? cell.html(rows[index][field].val()) : cell.html(rows[index][field]);
                    }
                    var str = cell.html() || "";
                    str = str == "" ? "" : str.replace(/<[^>]+>/g, "");
                    td.attr("title", str);
                }
            }
            var style = opts.rowStyler ? opts.rowStyler.call(target, index, rows[index]) : "";
            tr.attr("style", style || "");
            $(target).datagrid("fixRowHeight", index);
        },
        insertRow: function(target, index, row) {
            var obj = $.data(target, "datagrid");
            var opts = obj.options;
            var dc = obj.dc;
            var data = obj.data;
            if (data.rows == 0) {
                data.rows = [];
            }
            if (index == undefined || index == null) {
                index = data.rows.length;
            }
            if (index > data.rows.length) {
                index = data.rows.length;
            }
            function modifyRowNumber(flag) {
                var num = flag ? 1 : 2;
                for (var i = data.rows.length - 1; i >= index; i--) {
                    var tr = opts.finder.getTr(target, i, "body", num);
                    tr.attr("datagrid-row-index", i + 1);
                    tr.attr("id", obj.rowIdPrefix + "-" + num + "-" + (i + 1));
                    if (flag && opts.rownumbers) {
                        var num1 = i + 2;
                        if (opts.pagination) {
                            num1 += (opts.pageNumber - 1) * opts.pageSize;
                        }
                        tr.find("div.datagrid-cell-rownumber").html(num1);
                    }
                }
            }
            modifyRowNumber.call(this, true);
            modifyRowNumber.call(this, false);
            var frozenFields = $(target).datagrid("getColumnFields", true);
            var fields = $(target).datagrid("getColumnFields", false);
            var idStr = obj.rowIdPrefix + "-" + 1 + "-" + index;
            var tr1 = '<tr id="' + idStr + '" class="datagrid-row" datagrid-row-index="' + index + '">' + this.renderRow.call(this, target, frozenFields, true, index, row) + "</tr>";
            var tr2 = '<tr id="' + idStr + '" class="datagrid-row" datagrid-row-index="' + index + '">' + this.renderRow.call(this, target, fields, false, index, row) + "</tr>";
            if (index >= data.rows.length) {
                if (data.rows.length) {
                    opts.finder.getTr(target, "", "last", 1).after(tr1);
                    opts.finder.getTr(target, "", "last", 2).after(tr2);
                } else {
                    dc.body1.html('<table cellspacing="0" cellpadding="0" border="0"><tbody>' + tr1 + "</tbody></table>");
                    dc.body2.html('<table cellspacing="0" cellpadding="0" border="0"><tbody>' + tr2 + "</tbody></table>");
                }
            } else {
                opts.finder.getTr(target, index + 1, "body", 1).before(tr1);
                opts.finder.getTr(target, index + 1, "body", 2).before(tr2);
            }
            data.total += 1;
            data.rows.splice(index, 0, row);
            //begin @zhaozz 2016-01-18
            if (opts.originalRowsData && data.rows != opts.originalRowsData) {
                opts.originalRowsData.splice(index, 0, row);
            } else {
                opts.originalRowsData = [ row ];
            }
            //end @zhaozz 2016-01-18
            this.refreshRow.call(this, target, index);
        },
        insertSubRow: function(target, index, row) {
            var opts = $.data(target, "datagrid").options;
            var fields = $(target).datagrid("getColumnFields", false);
            var tr1 = '<tr class="datagird-row-sub-tr1" style="display: table-row;"><td colspan="3" style="border-right:0"><div class="datagrid-row-sub"></div></td></tr>';
            var tr2 = '<tr class="datagird-row-sub-tr2" style="display: table-row;"><td colspan="' + fields.length + '"><div class="datagrid-row-sub"></div></td></tr>';
            opts.finder.getTr(target, index + 1, "body", 1).before(tr1);
            opts.finder.getTr(target, index + 1, "body", 2).before(tr2);
        },
        onBeforeRender: function(target, rows) {
            var opts = $.data(target, "datagrid").options;
            _.isFunction(opts.onBeforeRender) && opts.onBeforeRender.call(target, rows);
        },
        onAfterRender: function(target) {
            var opts = $.data(target, "datagrid").options;
            _.isFunction(opts.onAfterRender) && opts.onAfterRender.call(target);
        }
    };
    $.fn.datagrid.defaults = $.extend({}, $.fn.panel.defaults, {
        frozenColumns: undefined,
        columns: undefined,
        isFormCollapsable: false,
        isFormCollapsed: false,
        colNumbers: 3,
        fitColumns: false,
        toolbar: null,
        toolbarAlign: "bottom",
        //新架构一柜通默认使用bottom
        method: "post",
        req: [],
        nowrap: true,
        striped: true,
        rownumbers: false,
        subrow: false,
        //子行扩展，通过onExpandRow可以把子行展示成datagrid、form等
        singleSelect: true,
        enableCheckAll: true,
        selectOnCheck: true,
        checkOnSelect: true,
        loadMsg: "Processing, please wait ...",
        //新增参数用于控制查询时是否丢弃选中项
        clearOnQuery: true,
        pagination: true,
        pagePosition: "bottom",
        pageSize: 100,
        pageNumber: 1,
        showTime: true,
        maxRows: 1e3,
        sort: true,
        sortName: null,
        sortOrder: "asc",
        remoteSort: false,
        remotePagination: false,
        showHeader: true,
        rowDetailHeight: 80,
        showRowDetail: false,
        isShowDetail: false,
        limitQueryLine: 3,
        //限制查询form的显示行数
        isShowLoading: true,
        //是否显示加载中
        showStatistics: false,
        //显示统计
        isShowStatistics: false,
        //是否显示统计开关
        stsDescFieldIndex: 0,
        //统计的描述字段放第几个列字段中
        statisticsTexts: [ "最大值", "最小值", "平均值", "合计" ],
        //显示的统计描述，统计是按索引做业务分析
        showDetailCols: "",
        isMoveColumns: true,
        // 是否添加功能“列拖拽” 
        gridMenu: false,
        queryBtnIco: "icon-search",
        queryBtnTxt: "查询",
        //'restoreDefault,freezeColumns,hideColumns,hideCols,fileExport,printReport', //表格的菜单需要添加的内容
        exportTypes: [ "xls", "dbf", "pdf", "csv" ],
        // 表格的菜单中如果有导出菜单，需要显示的导出类型有哪些
        importTypes: [ "导入" ],
        reportTitle: undefined,
        // 导出标题，如果没有设置该项，则会在name、title以及tabs的标题中去依次寻找导出标题，一般情况下不需要设置
        fallParas: [ {
            // 采用瀑布式展示数据的参数，以默认参数为例，发送到后台参数添加的两个值为 {PAGE_NUM:1,PAGE_SIZE:100}
            enable: false,
            // 是否开户瀑布模式，默认不开启，在view中配置时，将该项设置为true即可开启瀑布模式，其它值为默认
            recName: "PAGE_NUM",
            // 从第几页读取的参数的名字
            dbPagination: false,
            cntName: "PAGE_SIZE",
            // 每次读取多少条数据的参数的名字
            recNum: 1,
            // 从某一条开始读取
            cntNum: 100,
            // 每次读取多少条数据
            backAllBtn: false,
            // 是否有“全部返回”按钮
            nextPageBtn: true,
            //是否显示“下一页”按钮
            showPageList: true,
            //是否显示选择读取多少条数据
            pageList: [ 100, 500, 1e3 ]
        } ],
        autoColNumber: true,
        // 是否让form自动控制每行的条件个数
        //showFooter:false,
        scrollbarSize: 18,
        allowMove: false,
        editing: undefined,
        view: view,
        rowStyler: function(index, row) {},
        loader: function(params, onLoadSuccess, onLoadError) {
            var opts = $(this).datagrid("options");
            opts.pageNumber = params.PAGE_NUM || 1;
            //ray add
            var that = $(this);
            if (opts.req.length == 0 && !opts.url) {
                return false;
            }
            ajaxRequest({
                type: opts.method,
                url: opts.url,
                dataType: opts.dataType,
                req: [ params ],
                func: function(data, head) {
                    var retData = {
                        HEAD: head,
                        DATA: data
                    };
                    onLoadSuccess(retData);
                },
                error: function() {
                    onLoadError.apply(this, arguments);
                    setTimeout(function() {
                        clean(that[0]);
                        that.datagrid("loaded");
                    }, 0);
                }
            });
        },
        beforeFilter: function(data) {
            return data;
        },
        loadFilter: function(data, pageNumber) {
            var dg = $(this);
            var opts = $(this).datagrid("options");
            var pager = $(this).datagrid("getPager");
            opts.pageNumber = pageNumber || 1;
            //ray add
            if (typeof data == "object" && data["HEAD"] && data["DATA"]) {
                var tdata = [];
                var trow = "";
                if (opts.comp_code && opts.comp_code.length > 0) {
                    trow = data["DATA"][0][opts.comp_code].rows;
                    tdata = data["DATA"][0][opts.comp_code].data;
                } else if ($.isArray(data["DATA"][0])) {
                    trow = data["HEAD"]["DATA_ROWS"];
                    tdata = data["DATA"][0];
                } else {
                    trow = data["HEAD"]["BPM_ROWS"];
                    tdata = data["DATA"][0]["BPM_DATA"];
                }
                //@zhaozz 2015-11-28 解决表头数据过滤的问题（之前实现的是当前页过滤，现在针对所有数据过滤）
                data.total = undefined !== data.total && null !== data.total ? data.total : trow;
                data.rows = data.rows ? data.rows : tdata;
                //@zhaozz 2015-11-28
                opts.originalRowsData = tdata;
            } else if (typeof data.total == "number" && typeof data.rows.splice == "function") {
                opts.originalRowsData = data.rows;
                return data;
            } else if (Object.prototype.toString.call(data) === "[object Array]") {
                opts.originalRowsData = data;
                data = {
                    total: data.length,
                    rows: data,
                    originalRowsData: data
                };
            }
            //end@zhaozz  支持直接传数组参数 2015-12-07 ---------------------
            if (opts.remotePagination) {
                pager.pagination({
                    onSelectPage: function(pageNum, pageSize) {
                        opts.pageNumber = pageNum;
                        opts.pageSize = pageSize;
                        pager.pagination("refresh", {
                            pageNumber: pageNum,
                            pageSize: pageSize
                        });
                        dg.datagrid("reload", null, true);
                    }
                });
            }
            if (!opts.remotePagination && !opts.fallParas[0].enable) {
                pager.pagination({
                    onSelectPage: function(pageNum, pageSize) {
                        opts.pageNumber = pageNum;
                        opts.pageSize = pageSize;
                        pager.pagination("refresh", {
                            pageNumber: pageNum,
                            pageSize: pageSize
                        });
                        //dg.datagrid('reload', null, true);
                        if (!data.originalRows) {
                            data.originalRows = data.rows;
                        }
                        var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
                        var end = start + parseInt(opts.pageSize);
                        data.rows = data.originalRows.slice(start, end);
                        dg.datagrid("loadData", data);
                    },
                    onRefresh: function() {
                        dg.datagrid("reload", null, true);
                    },
                    //---------------------------------------------------------------------
                    //传给工具栏的回调函数和构造menu所需要的参数
                    gridMenu: opts.gridMenu,
                    //将菜单项传递给工具条用于生成相应的菜单
                    exportTypes: opts.exportTypes,
                    //工具条可以导出的文件类型
                    importTypes: opts.importTypes,
                    onHideColumns: function() {
                        onHideColumns(dg[0]);
                    },
                    onHideCols: function() {
                        onHideCols(dg[0]);
                    },
                    onFreezeColumns: function() {
                        onFreezeColumns(dg[0]);
                    },
                    onRestoreDefault: function() {
                        onRestoreDefault(dg[0]);
                    },
                    onFileExport: function(type) {
                        onFileExport(dg[0], type);
                    },
                    onFileImport: function() {
                        onFileImport(dg[0]);
                    },
                    fallParas: opts.fallParas,
                    showPageList: opts.pagerShowPageList,
                    showRefresh: opts.pagerShowRefresh,
                    pageNavigate: opts.pagerPageNavigate,
                    isShowDetail: opts.isShowDetail
                });
                if (!data.originalRows) {
                    data.originalRows = data.rows;
                }
                var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
                var end = start + parseInt(opts.pageSize);
                if (data.originalRows != undefined) {
                    data.rows = data.originalRows.slice(start, end);
                } else {
                    data.rows = 0;
                }
            }
            return data;
        },
        finder: {
            getTr: function(target, index, type, frozen) {
                type = type || "body";
                frozen = frozen || 0;
                var data = $.data(target, "datagrid");
                var dc = data.dc;
                var opts = data.options;
                if (frozen == 0) {
                    var tr1 = opts.finder.getTr(target, index, type, 1);
                    var tr2 = opts.finder.getTr(target, index, type, 2);
                    return tr1.add(tr2);
                } else {
                    if (type == "body") {
                        var tr = $("#" + data.rowIdPrefix + "-" + frozen + "-" + index);
                        if (!tr.length) {
                            tr = (frozen == 1 ? dc.body1 : dc.body2).find(">table>tbody>tr[datagrid-row-index=" + index + "]");
                        }
                        return tr;
                    } else {
                        if (type == "footer") {
                            return (frozen == 1 ? dc.footer1 : dc.footer2).find(">table>tbody>tr[datagrid-row-index=" + index + "]");
                        } else {
                            if (type == "selected") {
                                return (frozen == 1 ? dc.body1 : dc.body2).find(">table>tbody>tr.datagrid-row-selected");
                            } else {
                                if (type == "last") {
                                    return (frozen == 1 ? dc.body1 : dc.body2).find(">table>tbody>tr:last[datagrid-row-index]");
                                } else {
                                    if (type == "allbody") {
                                        return (frozen == 1 ? dc.body1 : dc.body2).find(">table>tbody>tr[datagrid-row-index]");
                                    } else {
                                        if (type == "allfooter") {
                                            return (frozen == 1 ? dc.footer1 : dc.footer2).find(">table>tbody>tr[datagrid-row-index]");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
            getRow: function(target, index) {
                return $.data(target, "datagrid").data.rows[index];
            }
        },
        toolbarHandler: {
            dft: {
                editHandler: function(e) {
                    e.data.callback(e);
                },
                clbHandler: function(e) {
                    e.data.callback(e);
                }
            },
            add: {
                editHandler: function(e) {
                    var inserted = $.data(e.data.target[0], "datagrid").insertedRows;
                    if (inserted.length == 0) e.data.target.datagrid("appendRow", {});
                },
                clbHandler: function(e) {
                    e.data.callback(e);
                }
            },
            edit: {
                editHandler: function(e) {
                    e.data.callback(e);
                },
                clbHandler: function(e) {
                    e.data.callback(e);
                }
            },
            remove: {
                editHandler: function(e) {
                    var opts = $.data(e.data.target[0], "datagrid").options;
                    e.data.target.datagrid("destroyEditor", opts.editing);
                    //.datagrid('deleteRow', opts.editing)
                    var selected = e.data.target.datagrid("getSelected");
                    var sltIndex = e.data.target.datagrid("getRowIndex", selected);
                    var $row = opts.finder.getTr(e.data.target[0], sltIndex);
                    $row.addClass("datagrid-row-del");
                    $.data(e.data.target[0], "datagrid").deletedRows.push(selected);
                },
                clbHandler: function(e) {
                    e.data.callback(e);
                }
            },
            save: {
                editHandler: function(e) {
                    var $data = $.data(e.data.target[0], "datagrid");
                    var opts = $data.options;
                    var cols = opts.columns;
                    var edit = false, opType = "";
                    if (e.data.target.datagrid("validateRow", opts.editing)) {
                        e.data.target.datagrid("endEdit", opts.editing);
                        edit = true;
                    }
                    if (edit) {
                        var primary = new Array();
                        if (cols && cols.length > 0) {
                            for (var i = cols[0].length - 1; i >= 0; i--) {
                                if (cols[0][i]["primary_key"] == "1") primary.push(cols[0][i].field);
                            }
                        }
                        var inserted = $data.insertedRows, deleted = $data.deletedRows, updated = $data.updatedRows;
                        var tools = opts.toolbar;
                        var req = new Array();
                        for (var i = tools.length - 1; i >= 0; i--) {
                            var op = tools[i].iconCls ? tools[i].iconCls.split("-")[1] : undefined;
                            if (op) {
                                var baseReq = typeof tools[i].req == "string" ? eval("(" + tools[i].req + ")") : tools[i].req;
                                if (baseReq && baseReq.length > 0 && !baseReq[0].service) baseReq[0].service = "P9999999";
                                switch (op) {
                                  case "add":
                                    if (inserted.length > 0) {
                                        var params = parseInput(inserted);
                                        req.push($.extend(params, baseReq[0]));
                                        opType = "add";
                                    }
                                    break;

                                  case "edit":
                                    if (updated.length > 0) {
                                        var params = parseInput(updated);
                                        req.push($.extend(params, baseReq[0]));
                                        opType = "edit";
                                    }
                                    break;

                                  case "remove":
                                    if (deleted.length > 0) {
                                        var params = parseInput(deleted);
                                        req.push($.extend(params, baseReq[0]));
                                        opType = "remove";
                                    }
                                    break;

                                  default:
                                    break;
                                }
                            }
                        }
                        if (req.length > 0) {
                            ajaxRequest({
                                type: opts.method,
                                url: opts.url,
                                dataType: opts.dataType,
                                req: req,
                                func: function(data) {
                                    if (data && data.length > 0) {
                                        if (data[0].service.flag === "0" || data[0].service.flag === "100") {
                                            e.data.target.datagrid("acceptChanges");
                                            e.data.target.datagrid("reload");
                                            switch (opType) {
                                              case "add":
                                                alert("增加成功！");
                                                break;

                                              case "edit":
                                                alert("修改成功！");
                                                break;

                                              case "remove":
                                                alert("删除成功！");
                                                break;

                                              default:
                                                break;
                                            }
                                        }
                                    }
                                },
                                error: function() {
                                    alert("操作失败", alert.ERROR);
                                }
                            });
                        }
                    }
                    function parseInput(param) {
                        var vParam = $.extend({}, param[0]);
                        if (vParam) {
                            for (var field in vParam) {
                                var col = e.data.target.datagrid("getColumnOption", field);
                                if (col) {
                                    if (col.type == "timespinner") {
                                        var value = vParam[field];
                                        if (value && value.indexOf(":") != -1) vParam[field] = value.replace(":", "") + "00";
                                    } else if (col.type == "combobox" && col.editor.options.multiple) {
                                        var value = vParam[field];
                                        if (Object.prototype.toString.apply(value) === "[object Array]") vParam[field] = value.join("");
                                        if (value && value.indexOf(",") != -1) vParam[field] = value.replace(",", "");
                                    }
                                }
                            }
                        }
                        return vParam;
                    }
                },
                clbHandler: function(e) {
                    e.data.callback(e);
                }
            }
        },
        editors: {},
        view: view,
        onBeforeLoad: function() {},
        onLoadSuccess: function() {},
        onLoadError: function() {},
        onClickRow: function(rowIndex, rowData) {},
        onDblClickRow: function(rowIndex, rowData) {},
        onClickCell: function(rowIndex, colIndex, rowData) {},
        onDblClickCell: function(rowIndex, colIndex, rowData) {},
        onSortColumn: function(sortName, sortOrder) {},
        onResizeColumn: function(field, width) {},
        onSelect: function(rowIndex, rowData) {},
        onUnselect: function(rowIndex, rowData) {},
        onSelectAll: function(rows) {},
        onUnselectAll: function(rows) {},
        onCheck: function(rowIndex, rowData) {},
        onUncheck: function(rowIndex, rowData) {},
        onCheckAll: function(rows) {},
        onUncheckAll: function(rows) {},
        onBeforeEdit: function(rowIndex, row) {},
        onBeforeEditCell: function(rowIndex, field, rowData, colData) {
            return true;
        },
        onAfterEdit: function(rowIndex, colIndex, rowData) {},
        onCancelEdit: $.noop,
        onDeleteRow: function(rowIndex, rowData) {},
        onHeaderContextMenu: function(e, field) {},
        onRowContextMenu: function(e, rowIndex, rowData) {},
        onBeforeInit: $.noop,
        onInitSuccess: $.noop,
        onExpandRow: function(rowIndex, rowData) {},
        onCollapseRow: function(rowIndex, rowData) {}
    });
    function contain(a, o) {
        for (var i = 0, len = a.length; i < len; i++) {
            //begin@zhaozz  2015-12-07 解决需求：datagrid新增row后，默认选中新增的那一行记录
            if ("[object Object]" === Object.prototype.toString.call(a[i])) {
                var boolEqual = true;
                for (var key in o) {
                    var tmpNewVal = o[key] + "", tmpGridVal = a[i][key] + "";
                    tmpNewVal = tmpNewVal && tmpNewVal.match(/^(-?\d+)(\.\d+)?$/) ? Number(tmpNewVal) : tmpNewVal;
                    tmpGridVal = tmpGridVal && tmpGridVal.match(/^(-?\d+)(\.\d+)?$/) ? Number(tmpGridVal) : tmpGridVal;
                    if (tmpNewVal && tmpNewVal != tmpGridVal) {
                        boolEqual = false;
                        break;
                    }
                }
                if (boolEqual) {
                    return i;
                }
            } else if (a[i] == o) {
                return i;
            }
        }
        return -1;
    }
    function remove(a, o, id) {
        if (typeof o == "string") {
            for (var i = 0, len = a.length; i < len; i++) {
                if (a[i] && a[i][o] == id) {
                    //ray 添加了a[i]判断
                    a.splice(i, 1);
                    return;
                }
            }
        } else {
            var index = contain(a, o);
            if (index != -1) {
                a.splice(index, 1);
            }
        }
    }
    function setSize(target, size) {
        var state = $.data(target, "datagrid");
        var dc = state.dc;
        var opts = $.data(target, "datagrid").options;
        var panel = $.data(target, "datagrid").panel;
        if (size) {
            if (size.width) {
                opts.width = size.width;
            }
            if (size.height) {
                opts.height = size.height;
            }
        }
        if (opts.fit == true) {
            var p = panel.panel("panel").parent();
            opts.width = p.width();
            opts.height = p.height();
        }
        panel.panel("resize", {
            width: opts.width,
            height: opts.height
        });
        dc.body2.triggerHandler("scroll");
    }
    function resize(target) {
        var opts = $.data(target, "datagrid").options;
        var dc = $.data(target, "datagrid").dc;
        var wrap = $.data(target, "datagrid").panel;
        var wrapWidth = wrap.width();
        var height = wrap.outerHeight();
        var view = dc.view;
        var view1 = dc.view1;
        var view2 = dc.view2;
        var header1 = view1.children("div.datagrid-header");
        var header2 = view2.children("div.datagrid-header");
        var table1 = header1.find("table");
        var table2 = header2.find("table");
        view.width(wrapWidth - 2);
        var inner = header1.children("div.datagrid-header-inner").show();
        view1.width(inner.find("table").width());
        if (!opts.showHeader) {
            inner.hide();
        }
        view2.width(wrapWidth - view1.outerWidth());
        view1.children("div.datagrid-header,div.datagrid-body,div.datagrid-footer,div.datagrid-toolbar").width(view1.width());
        view2.children("div.datagrid-header,div.datagrid-body,div.datagrid-footer,div.datagrid-toolbar").width(view2.width());
        header1.css("height", "");
        header2.css("height", "");
        table1.css("height", "");
        table2.css("height", "");
        var tableHeight = Math.max(table1.height(), table2.height());
        table1.height(tableHeight);
        table2.height(tableHeight);
        header1.add(header2)._outerHeight(tableHeight);
        var toolbar1 = view1.children("div.datagrid-toolbar");
        var toolbar2 = view2.children("div.datagrid-toolbar");
        if (toolbar2.children().length > 0) toolbar1.add(toolbar2)._outerHeight(tableHeight);
        var $footer = view2.children("div.datagrid-footer");
        var footerTable = $footer.find("div.datagrid-footer-inner").children();
        if (footerTable.length == 0) {
            //IE7兼容
            $footer.height(0);
        } else {
            $footer.height(footerTable.height());
        }
        if (opts.height != "auto") {
            var bodyHeight = height, headerHeight = view2.children("div.datagrid-header").outerHeight(true), footerHeight = view2.children("div.datagrid-footer").outerHeight(true), detailHeight = wrap.children("div.datagrid-row-detail:visible").outerHeight(true), //queryFormHeight = wrap.children("div.datagrid-queryForm").outerHeight(true); // 去掉该行代码改为下面那行代码，用于解决IE7下queryForm高度计算不正确的BUG
            queryFormHeight = wrap.find(">div.datagrid-queryForm").outerHeight(true);
            //var queryPanelHeight = wrap.find("div.datagrid-query").outerHeight(true);//解决当查询条件过多时，datagridBody算高度时去掉收拉条的高度的问题
            var $toolbar = wrap.children("div.datagrid-toolbar");
            toolbarHeight = $toolbar.outerHeight(true);
            if ($.isNumeric(headerHeight)) bodyHeight -= headerHeight;
            if ($.isNumeric(footerHeight)) bodyHeight -= footerHeight;
            if ($.isNumeric(toolbarHeight) && $toolbar.children().length > 0) bodyHeight -= toolbarHeight;
            if ($.isNumeric(queryFormHeight)) bodyHeight -= queryFormHeight;
            //if ($.isNumeric(queryPanelHeight)) bodyHeight -= queryPanelHeight;
            if ($.isNumeric(detailHeight)) bodyHeight -= detailHeight;
            wrap.children("div.datagrid-pager").each(function() {
                if ($.isNumeric($(this).outerHeight(true))) bodyHeight -= $(this).outerHeight(true);
            });
            view1.children("div.datagrid-body").height(bodyHeight);
            view2.children("div.datagrid-body").height(bodyHeight);
        }
        view.height(view2.height() - 1);
        view2.css("left", view1.outerWidth());
        var w = view2.children("div.datagrid-header").find(".datagrid-htable").width();
        var rows = dc.body1.find(">table>tbody>tr:visible");
        var $datagridbody = view2.children("div.datagrid-body");
        if (!rows.length && !$datagridbody.find(".fullWidth").length) {
            $datagridbody.append("<div class='fullWidth' style='width:" + w + "px;height:22px'></div>");
        }
        if (rows.length) {
            $datagridbody.find(".fullWidth").remove();
        }
    }
    function fixRowHeight(target, row, autoRowHeight) {
        var rows = $.data(target, "datagrid").data.rows;
        var opts = $.data(target, "datagrid").options;
        var dc = $.data(target, "datagrid").dc;
        if (!dc.body1.is(":empty") && (!opts.nowrap || opts.autoRowHeight || autoRowHeight)) {
            if (row != undefined) {
                var tr1 = opts.finder.getTr(target, row, "body", 1);
                var tr2 = opts.finder.getTr(target, row, "body", 2);
                setRowHeight(tr1, tr2);
            } else {
                var tr1 = opts.finder.getTr(target, 0, "allbody", 1);
                var tr2 = opts.finder.getTr(target, 0, "allbody", 2);
                setRowHeight(tr1, tr2);
            }
        }
        resize(target);
        if (opts.height == "auto") {
            var body1Parent = dc.body1.parent();
            var body2 = dc.body2;
            var height = 0;
            var width = 0;
            body2.children().each(function() {
                var c = $(this);
                if (c.is(":visible")) {
                    height += c.outerHeight();
                    if (width < c.outerWidth()) {
                        width = c.outerWidth();
                    }
                }
            });
            if (width > body2.width()) {
                height += 18;
            }
            body1Parent.height(height);
            body2.height(height);
            dc.view.height(dc.view2.height());
        }
        dc.body2.triggerHandler("scroll");
        function setRowHeight(trs1, trs2) {
            for (var i = 0; i < trs2.length; i++) {
                var tr1 = $(trs1[i]);
                var tr2 = $(trs2[i]);
                tr1.css("height", "");
                tr2.css("height", "");
                var height = Math.max(tr1.height(), tr2.height());
                tr1.css("height", height);
                tr2.css("height", height);
            }
        }
    }
    function autoSizeColumn(target, field) {
        var opts = $.data(target, "datagrid").options;
        var dc = $.data(target, "datagrid").dc;
        if (field) {
            setSize(field);
            if (opts.fitColumns) {
                resize(target);
                fitColumns(target);
            }
        } else {
            var auto = false;
            var fields = getColumnFields(target, true).concat(getColumnFields(target, false));
            for (var i = 0; i < fields.length; i++) {
                var field = fields[i];
                var col = getColumnOption(target, field);
                if (col && col.auto && !col.listHidden) {
                    setSize(field);
                    auto = true;
                }
            }
            if (auto && opts.fitColumns) {
                resize(target);
                fitColumns(target);
            }
        }
        function setSize(field) {
            var cell = dc.view.find('div.datagrid-header td[field="' + field + '"] div.datagrid-cell');
            cell.css("width", "");
            var col = $(target).datagrid("getColumnOption", field);
            col.width = undefined;
            col.boxWidth = undefined;
            col.auto = true;
            $(target).datagrid("fixColumnSize", field);
            var width = Math.max(cell.outerWidth(), getMaxColumnWidth("allbody"), getMaxColumnWidth("allfooter"));
            cell._outerWidth(width + 1);
            // 防止过滤图标隐藏
            col.width = width;
            col.boxWidth = parseInt(cell[0].style.width);
            $(target).datagrid("fixColumnSize", field);
            opts.onResizeColumn.call(target, field, col.width);
            function getMaxColumnWidth(type) {
                var maxWidth = 0;
                opts.finder.getTr(target, 0, type).find('td[field="' + field + '"] div.datagrid-cell').each(function() {
                    var outerWidth = $(this).outerWidth();
                    if (maxWidth < outerWidth) {
                        maxWidth = outerWidth;
                    }
                });
                return maxWidth;
            }
        }
    }
    function fixColumnSize(target, field) {
        var opts = $.data(target, "datagrid").options;
        var dc = $.data(target, "datagrid").dc;
        var table = dc.view.find("table.datagrid-btable,table.datagrid-ftable");
        table.css("table-layout", "fixed");
        if (field) {
            fix(field);
        } else {
            var fields = getColumnFields(target, true).concat(getColumnFields(target, false));
            for (var i = 0; i < fields.length; i++) {
                fix(fields[i]);
            }
        }
        table.css("table-layout", "auto");
        merge(target);
        setTimeout(function() {
            fixRowHeight(target);
            resizeEditor(target);
        }, 0);
        function fix(field) {
            var col = getColumnOption(target, field);
            if (!col || col.checkbox) {
                return;
            }
            var style = dc.view.children("style")[0];
            var sheet = style.styleSheet ? style.styleSheet : style.sheet || document.styleSheets[document.styleSheets.length - 1];
            var cssRules = sheet.cssRules || sheet.rules;
            //IE mozilla
            for (var i = 0, len = cssRules.length; i < len; i++) {
                var rule = cssRules[i];
                if (col.cellSelector && rule.selectorText && rule.selectorText.toLowerCase() == col.cellSelector.toLowerCase()) {
                    //                    rule.style["width"] = col.boxWidth ? (opts.rowEdit ?( col.boxWidth-8) : col.boxWidth) + "px": "auto";
                    rule.style["width"] = col.boxWidth ? col.boxWidth + "px" : "auto";
                    break;
                }
            }
        }
    }
    function merge(target) {
        var dc = $.data(target, "datagrid").dc;
        dc.body1.add(dc.body2).find("td.datagrid-td-merged").each(function() {
            var td = $(this);
            var colspan = td.attr("colspan") || 1;
            var width = getColumnOption(target, td.attr("field")).width;
            for (var i = 1; i < colspan; i++) {
                td = td.next();
                width += getColumnOption(target, td.attr("field")).width + 1;
            }
            $(this).children("div.datagrid-cell")._outerWidth(width);
        });
    }
    function fitColumns(target) {
        var opts = $.data(target, "datagrid").options;
        var dc = $.data(target, "datagrid").dc;
        if (!opts.fitColumns) {
            return;
        }
        var header = dc.view2.children("div.datagrid-header");
        var width = 0;
        var column;
        var fields = getColumnFields(target, false);
        for (var i = 0; i < fields.length; i++) {
            var col = getColumnOption(target, fields[i]);
            if (fixWidth(col)) {
                width += col.width;
                column = col;
            }
        }
        var headerInner = header.children("div.datagrid-header-inner").show();
        var headerWidth = header.width() - header.find("table").width() - opts.scrollbarSize;
        var rate = headerWidth / width;
        if (!opts.showHeader) {
            headerInner.hide();
        }
        for (var i = 0; i < fields.length; i++) {
            var col = getColumnOption(target, fields[i]);
            if (fixWidth(col)) {
                var colWidth = Math.floor(col.width * rate);
                setWidth(col, colWidth);
                headerWidth -= colWidth;
            }
        }
        if (headerWidth && column) {
            setWidth(column, headerWidth);
        }
        fixColumnSize(target);
        function setWidth(col, width) {
            col.width += width;
            col.boxWidth += width;
            header.find('td[field="' + col.field + '"] div.datagrid-cell').width(col.boxWidth);
        }
        function fixWidth(col) {
            if (!col.hidden && !col.checkbox && !col.auto) {
                return true;
            }
        }
    }
    function clean(target) {
        $(target).datagrid("loadData", {});
        var $data = $.data(target, "datagrid");
        $data.options.editing = undefined;
        $data.updatedRows = [];
        $data.insertedRows = [];
        $data.deletedRows = [];
    }
    function wrapGrid(target, rownumbers) {
        function getColumnOptions() {
            var frozenColumns = [];
            var columns = [];
            $(target).children("thead").each(function() {
                var opt = $.parser.parseOptions(this, [ {
                    frozen: "boolean"
                } ]);
                $(this).find("tr").each(function() {
                    var cols = [];
                    $(this).find("th").each(function() {
                        var th = $(this);
                        var col = $.extend({}, $.parser.parseOptions(this, [ "field", "align", {
                            sortable: "boolean",
                            checkbox: "boolean",
                            resizable: "boolean"
                        }, {
                            rowspan: "number",
                            colspan: "number",
                            width: "number"
                        } ]), {
                            title: th.html() || th.attr("title") || undefined,
                            hidden: th.attr("hidden") ? true : undefined,
                            formatter: th.attr("formatter") ? eval(th.attr("formatter")) : undefined,
                            styler: th.attr("styler") ? eval(th.attr("styler")) : undefined
                        });
                        if (!col.align) {
                            col.align = "left";
                        }
                        if (th.attr("editor")) {
                            var editor = $.trim(th.attr("editor"));
                            if (editor.substr(0, 1) == "{") {
                                col.editor = eval("(" + editor + ")");
                            } else {
                                col.editor = editor;
                            }
                        }
                        cols.push(col);
                    });
                    opt.frozen ? frozenColumns.push(cols) : columns.push(cols);
                });
            });
            return [ frozenColumns, columns ];
        }
        //create datagrid container
        var container = [ '<div class="datagrid-wrap">', '<div class="datagrid-queryForm"></div>', '<div class="datagrid-view">', '<div class="datagrid-view1">', '<div class="datagrid-header">', '<div class="datagrid-header-inner"></div>', "</div>", '<div class="datagrid-body">', '<div class="datagrid-body-inner"></div>', "</div>", '<div class="datagrid-footer">', '<div class="datagrid-footer-inner"></div>', "</div>", "</div>", '<div class="datagrid-view2">', '<div class="datagrid-header">', '<div class="datagrid-header-inner"></div>', "</div>", '<div class="datagrid-body"></div>', '<div class="datagrid-footer">', '<div class="datagrid-footer-inner"></div>', "</div>", "</div>", "</div>", "</div>" ];
        var grid = $(container.join("")).insertAfter(target);
        grid.panel({
            doSize: false
        });
        grid.panel("panel").addClass("datagrid").bind("_resize", function(e, _447) {
            var opts = $.data(target, "datagrid").options;
            if (opts.fit == true || _447) {
                setSize(target);
                setTimeout(function() {
                    if ($.data(target, "datagrid")) {
                        fixColumnSize(target);
                    }
                }, 0);
            }
            return false;
        });
        $(target).hide().appendTo(grid.children("div.datagrid-view"));
        var columns = getColumnOptions();
        // get kui-options config from dom
        var view = grid.children("div.datagrid-view");
        var queryForm = grid.children("div.datagrid-queryForm");
        var view1 = view.children("div.datagrid-view1");
        //装载固定列
        var view2 = view.children("div.datagrid-view2");
        //装载普通列
        return {
            panel: grid,
            frozenColumns: columns[0],
            columns: columns[1],
            dc: {
                view: view,
                view1: view1,
                view2: view2,
                queryForm: queryForm,
                header1: view1.children("div.datagrid-header").children("div.datagrid-header-inner"),
                header2: view2.children("div.datagrid-header").children("div.datagrid-header-inner"),
                body1: view1.children("div.datagrid-body").children("div.datagrid-body-inner"),
                body2: view2.children("div.datagrid-body"),
                footer1: view1.children("div.datagrid-footer").children("div.datagrid-footer-inner"),
                footer2: view2.children("div.datagrid-footer").children("div.datagrid-footer-inner")
            }
        };
    }
    function resizeEditor(target) {
        var dc = $.data(target, "datagrid").dc;
        var opts = $.data(target, "datagrid").options;
        dc.view.find("div.datagrid-editable").each(function() {
            var cell = $(this);
            var field = cell.parent().attr("field");
            var col = $(target).datagrid("getColumnOption", field);
            cell._outerWidth(col.boxWidth + 8);
            var ed = $.data(this, "datagrid.editor");
            if (ed.actions) {
                if (ed.actions.resize) ed.actions.resize(ed.target, cell.width());
            } else if (ed.type && ed.filter) {
                $(ed.filter, ed.target)[ed.type]("resize", cell.width());
            }
        });
    }
    function getColumnOption(target, field) {
        function find(columns) {
            if (columns) {
                for (var i = 0; i < columns.length; i++) {
                    var cols = columns[i];
                    for (var j = 0; j < cols.length; j++) {
                        var col = cols[j];
                        if (col.field == field) {
                            return col;
                        }
                    }
                }
            }
            return null;
        }
        var opts = $.data(target, "datagrid").options;
        var col = find(opts.columns);
        if (!col) {
            col = find(opts.frozenColumns);
        }
        return col;
    }
    function getData(target, row, cell) {
        var data = {
            total: 0,
            rows: []
        };
        var fields = getColumnFields(target, true).concat(getColumnFields(target, false));
        if (row) {
            var tr = $(target).find("tbody tr:eq(" + row + ")");
            if (cell) {
                $("td:eq(" + cell + ")", tr).html();
            } else {
                data = [];
                var col = {};
                for (var i = 0; i < fields.length; i++) {
                    col[fields[i]] = $("td:eq(" + i + ")", tr).html();
                }
                data.push(col);
            }
        } else {
            $(target).find("tbody tr").each(function() {
                data.total++;
                var col = {};
                for (var i = 0; i < fields.length; i++) {
                    col[fields[i]] = $("td:eq(" + i + ")", this).html();
                }
                data.rows.push(col);
            });
        }
        return data;
    }
    function create(target) {
        var state = $.data(target, "datagrid");
        var opts = state.options;
        var dc = state.dc;
        var panel = state.panel;
        panel.panel($.extend({}, opts, {
            id: null,
            doSize: true,
            onResize: function(_453, _454) {
                setTimeout(function() {
                    if ($.data(target, "datagrid")) {
                        resize(target);
                        opts.fitColumns && fitColumns(target);
                        opts.onResize.call(panel, _453, _454);
                    }
                }, 0);
            },
            onExpand: function() {
                fixRowHeight(target);
                opts.onExpand.call(panel);
            }
        }));
        if (opts.allowMove == true) {
            panel.panel("panel").draggable({
                handle: ".panel-header"
            });
        }
        state.rowIdPrefix = "datagrid-row-r" + ++datagridId;
        createColumnHeader(dc.header1, opts, true);
        createColumnHeader(dc.header2, opts, false);
        CreateCellClass();
        dc.header1.add(dc.header2).css("display", opts.showHeader ? "block" : "none");
        //dc.footer1.add(dc.footer2).css("display",opts.showFooter?"block":"none");
        if (opts.toolbar) {
            if (typeof opts.toolbar == "string") {
                $(opts.toolbar).addClass("datagrid-toolbar").insertAfter("bottom" === opts.toolbarAlign ? dc.view : dc.queryForm);
                $(opts.toolbar).show();
            } else {
                $("div.datagrid-toolbar", panel).remove();
                var tb = $('<div class="datagrid-toolbar"></div>').insertAfter("bottom" === opts.toolbarAlign ? dc.view : dc.queryForm);
                for (var i = 0; i < opts.toolbar.length; i++) {
                    var btn = opts.toolbar[i];
                    if (String(btn.hidden) != "true") {
                        if (btn == "-") {
                            $('<div class="datagrid-btn-separator"></div>').appendTo(tb);
                        } else {
                            var a = $("<a></a>");
                            var b = a.appendTo(tb).linkbutton($.extend({
                                plain: true
                            }, btn));
                            var iconCls = btn.iconCls;
                            var cls = undefined;
                            var event = undefined;
                            var handler = undefined;
                            if (iconCls) cls = opts.toolbarHandler[iconCls.split("-").length > 1 ? iconCls.split("-")[1] : "dft"];
                            if (opts.rowEdit && !btn.handler) {
                                event = cls ? cls.editHandler : opts.toolbarHandler["dft"].editHandler;
                            } else {
                                handler = eval(btn.handler);
                                event = cls ? cls.clbHandler : opts.toolbarHandler["dft"].clbHandler;
                            }
                            var hideDetail = function() {
                                var detail = dc.view.next(".datagrid-row-detail");
                                dc.view.height(dc.view[0].clientHeight + (detail.length ? detail[0].scrollHeight : 0));
                                detail.hide();
                                resize(target);
                            };
                            if (btn.float1) {
                                a.css("float", btn.float1);
                            }
                            b.bind("click", {
                                target: $(target),
                                req: btn.req,
                                title: btn.title,
                                handler: handler,
                                w: btn.width,
                                h: btn.height,
                                view: btn.view,
                                options: btn,
                                callback: function(e) {
                                    if ($(e.target).closest("a.l-btn").hasClass("l-btn-disabled")) //avoid to handle event when disabled
                                    return false; else e.data.handler(e);
                                }
                            }, event);
                        }
                    }
                }
            }
        }
        if (opts.queryCols && opts.queryCols.length > 0) {
            if (dc.queryForm.find("form").length >= 1) {
                return false;
            }
            var form = $("<form/>");
            panel.panel("panel").children().eq(0).before(form);
            dc.queryForm.append(form);
            var colNumbers = 2;
            if (opts.colNumbers) colNumbers = opts.colNumbers;
            form.form({
                onInitSuccess: function() {
                    //自动列数量的时候计算一下列的数量，否则取实际配置
                    opts.colNumbers = opts.autoColNumber && dc.queryForm.is(":visible") ? parseInt(dc.queryForm.width() / dc.queryForm.find(".form-group-inline:first").width()) : opts.colNumbers;
                    createQueryMoreBtn(target);
                    opts.isFormCollapsable && createFormCollapseBtn(target);
                    $.extend(opts.queryParams, form.form("getData"));
                    //@zhaozz 2016-6-6 解决查询统计statistics.js 获取 opts.queryParams 为空对象的问题
                    if ($.isFunction(opts.onQueryFormInitSuccess)) {
                        opts.onQueryFormInitSuccess.call(this);
                    }
                    if (!opts.required) {
                        request(target);
                    }
                    setSize(target);
                },
                queryCols: opts.queryCols,
                colNumbers: colNumbers,
                autoColNumber: opts.autoColNumber,
                // 是否让form自动控制每行的条件个数
                isHideCols: opts.isHideCols,
                // 创建form的时候增加该参数，表示是否有增加“隐藏搜索条件”的功能
                lsKey: opts.lsKey + "form"
            });
            var $form = form.find("div.form-box");
            var $formGrp = $("<div class='form-group-inline'></div>").appendTo($form);
            $formGrp.append($("<span></span>").attr("class", $formGrp.prev().find(".form-label").attr("class")));
            var $button = $("<a></a>").appendTo($formGrp.find(".form-label"));
            $button.linkbutton($.extend({}, {
                plain: true,
                iconCls: opts.queryBtnIco,
                text: opts.queryBtnTxt
            }));
            $button.click(function(e) {
                if ($button.hasClass("l-btn-disabled") || !form.form("validate")) {
                    return false;
                }
                opts.pageNumber = 1;
                var p = form.form("getData", true);
                //datagrid查询前执行
                if (opts.onClickQueryButtonBefore) {
                    $.extend(p, opts.onClickQueryButtonBefore.call(target, p, form));
                }
                if (opts.onQueryButtonClick) {
                    $.extend(opts.queryParams, p);
                    //@zhaozz 2016-6-6 解决查询统计statistics.js 获取 opts.queryParams 为空对象的问题
                    opts.onQueryButtonClick.call(target, p, form);
                } else {
                    $(target).datagrid("reload", p);
                    opts.clearOnQuery && $(target).datagrid("clearSelections");
                    if (opts.isTreeGrid) {
                        $(target).treegrid("reload", 0, p);
                    }
                }
                //                if (opts.onQueryButtonClick) {
                //                    opts.onQueryButtonClick.call(target, p, form);
                //                } else if (opts.onClickQueryButtonBefore) {
                //                    var rtnParam = opts.onClickQueryButtonBefore.call(target, p, form);
                //                    $(target).datagrid('reload', $.extend({},
                //                    p, rtnParam));
                //                } else {
                //                    $(target).datagrid('reload', p);
                //                    $(target).datagrid('clearSelections');
                //                    if (opts.isTreeGrid) {
                //                        $(target).treegrid('reload', 0, p);
                //                    }
                //                }
                e.preventDefault();
            });
        } else {
            dc.queryForm.hide();
            dc.queryForm.height(0);
        }
        $("div.datagrid-pager", panel).remove();
        if (opts.pagination) {
            var pager = $('<div class="datagrid-pager"></div>');
            if (opts.pagePosition == "bottom") {
                pager.appendTo(panel);
            } else {
                if (opts.pagePosition == "top") {
                    pager.addClass("datagrid-pager-top").prependTo(panel);
                } else {
                    var ptop = $('<div class="datagrid-pager datagrid-pager-top"></div>').prependTo(panel);
                    pager.appendTo(panel);
                    pager = pager.add(ptop);
                }
            }
            var dButtons = [];
            if (opts.showRowDetail) {
                dButtons = [ {
                    text: '<input id="' + opts.id + '_show_detail" name="datagrid_detail_button" type="checkbox" ' + (opts.isShowDetail ? 'checked="true"' : "") + ' style="vertical-align:middle;cursor:pointer;"/><label for="' + opts.id + '_show_detail" style="vertical-align:middle;cursor:pointer;padding: 1px 0 1px 1px;">显示详细</label>',
                    handler: function(e) {
                        var isCheck = $(this).find("input").attr("checked");
                        var detail = dc.view.nextAll(".datagrid-row-detail");
                        if (isCheck) {
                            opts.isShowDetail = true;
                            showDblClickRowDetail(target, dc.view2.find("tr.datagrid-row-selected"));
                        } else if (detail.length > 0) {
                            dc.view.height(dc.view[0].clientHeight + detail[0].scrollHeight);
                            detail.hide();
                            resize(target);
                            opts.isShowDetail = false;
                        }
                        e.stopPropagation();
                    }
                } ];
            }
            // 添加按钮
            if (opts.fallParas[0].dbPagination) {
                var prevButton = {
                    text: '<span id="dbpPrevBtn" style="cursor:pointer;display: none;">上一页</span>',
                    handler: function(e) {
                        var self = $(this);
                        if (!$(this).find("span.l-btn-left").is(":hidden")) {
                            $(this).hide();
                            dbpRequire(target, self);
                        }
                    }
                };
                var nextButton = {
                    text: '<span id="dbpNextBtn" style="cursor:pointer;display: inline-block;">下一页</span>',
                    handler: function(e) {
                        var self = $(this);
                        if (!$(this).find("span.l-btn-left").is(":hidden")) {
                            $(this).hide();
                            dbpRequire(target, self);
                        }
                    }
                };
                dButtons.push(prevButton);
                dButtons.push(nextButton);
            }
            if (opts.fallParas[0].enable && opts.fallParas[0].backAllBtn) {
                var backAllButton = {
                    text: '<span style="cursor:pointer;margin: 2px 0 0 0;display: inline-block;">查看全部</span>',
                    handler: function(e) {
                        onFileExport(target, "print", undefined, "txt", true);
                    }
                };
                dButtons.push(backAllButton);
            }
            if (opts.fallParas[0].enable && opts.fallParas[0].nextPageBtn) {
                var nextButton = {
                    text: '<span style="cursor:pointer;display: inline-block;">下一页</span>',
                    handler: function(e) {
                        var self = $(this);
                        if (!$(this).find("span.l-btn-left").is(":hidden")) {
                            $(this).hide();
                            request(target, null, true, function() {
                                self.show();
                            });
                        }
                    }
                };
                dButtons.push(nextButton);
            }
            if (opts.showStatistics) {
                var btnStatistics = {
                    text: '<input id="' + opts.id + '_show_statistics" name="datagrid_statistics_button" type="checkbox" ' + (opts.isShowStatistics ? 'checked="true"' : "") + ' style="vertical-align:middle;cursor:pointer;" /><label for="' + opts.id + '_show_statistics" style="vertical-align:middle;cursor:pointer;padding: 1px 0 1px 1px;">显示统计</label>',
                    handler: function(e) {
                        var isCheck = $(this).find("input").attr("checked");
                        if (e.target.tagName == "INPUT") {
                            if (isCheck) {
                                opts.isShowStatistics = true;
                                $(target).statistics("showPanel");
                            } else {
                                $(target).statistics("hidePanel");
                                opts.isShowStatistics = false;
                            }
                        }
                        e.stopPropagation();
                    }
                };
                dButtons.push(btnStatistics);
            }
            pager.pagination({
                pageNumber: opts.pageNumber,
                pageSize: opts.pageSize,
                pageList: opts.pageList,
                showTime: opts.showTime,
                buttons: dButtons.length > 0 ? dButtons : undefined,
                onSelectPage: function(pageNum, pageSize) {
                    opts.pageNumber = pageNum;
                    opts.pageSize = pageSize;
                    pager.pagination("refresh", {
                        pageNumber: pageNum,
                        pageSize: pageSize
                    });
                    request(target);
                },
                gridMenu: opts.gridMenu,
                exportTypes: opts.exportTypes,
                importTypes: opts.importTypes,
                onHideColumns: function() {
                    onHideColumns(target);
                },
                onHideCols: function() {
                    onHideCols(target);
                },
                onFreezeColumns: function() {
                    onFreezeColumns(target);
                },
                onRestoreDefault: function() {
                    onRestoreDefault(target);
                },
                onFileExport: function(type) {
                    onFileExport(target, type);
                },
                onFileImport: function() {
                    onFileImport(target);
                },
                fallParas: opts.fallParas,
                showPageList: opts.pagerShowPageList,
                showRefresh: opts.pagerShowRefresh,
                pageNavigate: opts.pagerPageNavigate,
                isShowDetail: opts.isShowDetail
            });
        }
        //创建固定列与非固定列都用此方法
        function createColumnHeader(header, opts, isFrozen) {
            var columns = "undifend";
            if (isFrozen) {
                columns = opts.frozenColumns;
            } else {
                columns = opts.columns;
            }
            if (!columns) {
                return;
            }
            $(header).show();
            $(header).empty();
            var hTable = new Array();
            hTable.push('<table class="datagrid-htable" border="0" cellspacing="0" cellpadding="0">');
            hTable.push("<tbody>");
            for (var i = 0; i < columns.length; i++) {
                hTable.push('<tr class="datagrid-header-row">');
                var cols = columns[i];
                for (var j = 0; j < cols.length; j++) {
                    var col = cols[j];
                    if (!cols || "notice" === col.type) {
                        continue;
                    }
                    if (null == col.grid_header_filter) {
                        col.grid_header_filter = false;
                    }
                    hTable.push("<td");
                    if (col.rowspan) {
                        hTable.push(' rowspan="' + col.rowspan + '"');
                    }
                    if (col.colspan) {
                        hTable.push(' colspan="' + col.colspan + '"');
                    }
                    if (col.hidden || col.listHidden) {
                        hTable.push(' style="display:none"');
                    }
                    if (col.checkbox) {
                        col.field = col.field || "checkbox" + $.expando;
                        hTable.push(' field="' + col.field + '">');
                        hTable.push('<div class="datagrid-header-check"><input type="checkbox"/' + (opts.enableCheckAll ? "" : "disabled='true'") + ' class="header-check-input"></div>');
                    } else {
                        if (col.field) {
                            col.width = Math.max(parseInt(col.width), titleLen(col.title));
                            hTable.push(' field="' + col.field + '">');
                            hTable.push('<div class="datagrid-cell" style="float:left;text-align:' + (col.align || "left") + ";" + (col.width ? "width:" + col.width + "px;" : "") + '"');
                            if (col.resizable == false) {
                                hTable.push(' resizable="false">');
                            } else {
                                hTable.push(">");
                            }
                            if (col.width) {
                                //col.boxWidth = parseInt(col.width);
                                col.boxWidth = col.width;
                            } else {
                                col.auto = true;
                            }
                            col.cellClass = "datagrid-cell-c" + datagridId + "-" + col.field.replace(".", "-");
                            col.cellSelector = "div." + col.cellClass;
                            hTable.push('<div class="datagrid-title">' + (col.title ? col.title : "") + "</div>");
                            if (opts.rowEdit) {
                                hTable.push('<div class="edit-nullalbe-div">');
                                if (col.edt_required) {
                                    hTable.push('<div class="nullable-div">*</div>');
                                } else {
                                    hTable.push('<div class="nullable-div"></div>');
                                }
                                if (col.edit_flag != "" && typeof col.edit_flag != "undefined" && col.edit_flag == "0") {
                                    hTable.push('<div class="edit-div edit-icon"></div>');
                                }
                                hTable.push("</div>");
                            }
                            if (col.sortable && col.sortable.toString() == "true") {
                                hTable.push('<span class="datagrid-sort-icon"></span>');
                            }
                            //@zhaozz 增加view.xml配置项 grid_header_filter=false 表头不提供过滤功能 2015-12-12
                            if ((col.type == "combobox" || col.type == "combotree") && col.editor && col.editor.options.data && false != col.grid_header_filter) {
                                hTable.push('<span class="datagrid-match-icon" title="选择"></span>');
                            } else if (false != col.grid_header_filter) {
                                hTable.push('<span class="datagrid-filter-icon" title="筛选"></span>');
                            }
                            hTable.push("</div>");
                        } else {
                            hTable.push(">");
                            hTable.push('<div class="datagrid-cell-group">' + col.title + "</div>");
                        }
                    }
                    hTable.push("</td>");
                }
                hTable.push("</tr>");
                hTable.push("</tbody></table>");
            }
            var $header = $(hTable.join(""));
            header.append($header);
            if (isFrozen && (opts.rownumbers || opts.subrow)) {
                var td = opts.rownumbers ? $('<td rowspan="' + opts.frozenColumns.length + '"><div class="datagrid-header-rownumber"></div></td>') : $('<td rowspan="1"><div class="datagrid-header-subrow"></div></td>');
                if ($("tr", $header).length == 0) {
                    td.wrap('<tr class="datagrid-header-row"></tr>').parent().appendTo($("tbody", $header));
                } else {
                    td.prependTo($("tr:first", $header));
                }
            }
        }
        function titleLen(str) {
            str = str || "";
            var strs = str.match(/[\x00-\xFF]/g);
            //            return (str.length * 13 - (strs ? strs.length * 7 : 0)) + 30;
            return str.length * 13 - (strs ? strs.length * 7 : 0);
        }
        function CreateCellClass() {
            dc.view.children("style").remove();
            var style = [ '<style type="text/css" rel="stylesheet">' ];
            var fields = getColumnFields(target, true).concat(getColumnFields(target));
            for (var i = 0; i < fields.length; i++) {
                var col = getColumnOption(target, fields[i]);
                if (col && !col.checkbox) {
                    col.boxWidth = $("td[field='" + col.field + "']", dc.header2).children().outerWidth() - 8;
                    if (col.boxWidth < 0) {
                        col.boxWidth = $("td[field='" + col.field + "']", dc.header1).children().outerWidth() - 8;
                    }
                    style.push(col.cellSelector + "{ width:" + col.boxWidth + "px;}");
                }
            }
            style.push("</style>");
            $(style.join("\n")).prependTo(dc.view);
        }
    }
    function createQueryMoreBtn(target) {
        var $target = $(target), state = $target.data("datagrid"), opts = state.options, dc = state.dc, $query = $("<div class='datagrid-query'>" + "<div title='更多查询条件' class='datagrid-queryMoreBtn datagrid-queryOpenBtn'></div>" + "</div>"), $queryMoreBtn = $query.find(".datagrid-queryMoreBtn"), queryColsLen = 0;
        $.each(opts.queryCols[0].cols, function(index, col) {
            !col.hidden && queryColsLen++;
        });
        //查询表单的实际行数小于`limitQueryLine`参数时不显示`更多查询条件`按钮
        if (Math.ceil(queryColsLen / opts.colNumbers) <= opts.limitQueryLine) {
            return;
        }
        $query.appendTo(dc.queryForm.find("div.form-box .form-group-inline:last .form-label").addClass("showMoreQueryButton"));
        var num = opts.limitQueryLine * opts.colNumbers - 2;
        dc.queryForm.find(".form-group-inline:gt(" + num + ")").filter(function() {
            return !$(this).find("a.l-btn").length;
        }).hide();
        $queryMoreBtn.click(function() {
            if ($queryMoreBtn.hasClass("datagrid-queryHideBtn")) {
                $queryMoreBtn.attr("title", "更多查询条件").removeClass("datagrid-queryHideBtn").addClass("datagrid-queryOpenBtn");
                var num = opts.limitQueryLine * opts.colNumbers - 2;
                dc.queryForm.find(".form-group-inline:gt(" + num + ")").filter(function() {
                    return !$(this).find("a.l-btn").length;
                }).hide();
            } else {
                dc.queryForm.find(".form-group-inline").show();
                $queryMoreBtn.attr("title", "收起查询条件").removeClass("datagrid-queryOpenBtn").addClass("datagrid-queryHideBtn");
            }
            resize(target);
        });
    }
    function createFormCollapseBtn(target) {
        var $target = $(target), state = $target.data("datagrid"), opts = state.options, dc = state.dc, $form = dc.queryForm.find("form"), $wrap = $("<div class='form-collapse-wrap'><div class='collapse-btn close' title='隐藏查询条件'></div></div>").appendTo(dc.queryForm), $btn = $wrap.find(".collapse-btn");
        $btn.click(function() {
            var isOpen = $btn.hasClass("close");
            //当前状态是否是打开的
            isOpen && $form.data("height", $form.outerHeight(true));
            //保存打开状态form的高度以便展开的时候恢复
            $form.animate({
                height: isOpen ? 0 : $form.data("height")
            }, 50, function() {
                isOpen ? $btn.removeClass("close").addClass("open").attr("title", "显示查询条件") : $btn.removeClass("open").addClass("close").attr("title", "隐藏查询条件");
                !isOpen && $form.css("height", "");
                resize(target);
            });
        });
        setTimeout(function() {
            opts.isFormCollapsed && $btn.click();
        });
        resize(target);
    }
    //控制上一页，下一页按钮的显示与隐藏 by lims
    function paginatonBtn(target, data) {
        var pager = $(target).datagrid("getPager");
        var arr = data.HEAD.MSG_TEXT.split(",");
        pager.pagination("options").dbpNum = parseInt(arr[1]);
        $("#dbpPrevBtn").parent().parent().parent().css({
            display: "inline-block"
        });
        $("#dbpPrevBtn").css({
            display: "inline-block"
        });
        $("#dbpNextBtn").parent().parent().parent().css({
            display: "inline-block"
        });
        if (pager.pagination("options").dbpNum == "1") {
            $("#dbpPrevBtn").parent().parent().parent().css({
                display: "none"
            });
        }
        if (arr[0] == "1") {
            $("#dbpNextBtn").parent().parent().parent().css({
                display: "none"
            });
        }
    }
    function getColumnFields(target, frozen) {
        var opts = $.data(target, "datagrid").options;
        var columns = frozen == true ? opts.frozenColumns || [ [] ] : opts.columns;
        if (columns.length == 0) {
            return [];
        }
        var fields = [];
        function getFields(rowIndex) {
            var columnFields = [];
            var colspan = 0;
            for (var i = 0; i < columns[rowIndex].length; i++) {
                var col = columns[rowIndex][i];
                if (col.field) {
                    columnFields.push([ colspan, col.field ]);
                }
                colspan += parseInt(col.colspan || "1");
            }
            for (var i = 0; i < columnFields.length; i++) {
                var columnField = columnFields[i];
                fields[columnField[0]] = columnField[1];
            }
        }
        for (var i = 0; i < columns.length; i++) {
            getFields(i);
        }
        return fields;
    }
    function saveOriginalRows(target) {
        var data = $.data(target, "datagrid").data;
        var rows = data.rows;
        var originalRows = [];
        for (var i = 0; i < rows.length; i++) {
            originalRows.push($.extend({}, rows[i]));
        }
        $.data(target, "datagrid").originalRows = originalRows;
        $.data(target, "datagrid").updatedRows = [];
        $.data(target, "datagrid").insertedRows = [];
        $.data(target, "datagrid").deletedRows = [];
    }
    //common method to add event
    function addEvent(target, event, callback) {
        if (!callback || !target || !event) return false;
        var jqEvent = event + ".datagrid";
        target.unbind(jqEvent).bind(jqEvent, callback);
        return target;
    }
    function getEventTargets(target, e) {
        var state = $.data(target, "datagrid");
        var opts = state.options;
        var tt = $(e.target);
        var tr = tt.closest("tr.datagrid-row");
        var value = tt.text();
        var index;
        if (tr.attr("datagrid-row-index")) {
            index = parseInt(tr.attr("datagrid-row-index"));
        } else {
            index = tr.attr("node-id");
        }
        var vRow = opts.finder.getRow(target, index);
        var td = tt.closest("td[field]", tr);
        var field = td.attr("field");
        return {
            cell: tt,
            tr: tr,
            row: $.extend({}, vRow),
            index: index,
            field: field,
            value: value
        };
    }
    function _bodyEventHandler(e, event, target) {
        var state = $.data(target, "datagrid");
        var opts = state.options;
        var dc = state.dc;
        var obj = getEventTargets(target, e);
        switch (event) {
          case "contextmenu":
            opts.onRowContextMenu.call(target, e, obj.index, obj.row);
            e.stopPropagation();
            break;

          case "click":
            if (!obj.index && obj.index != 0) {
                return;
            }
            if (opts.showRowDetail) {
                var wrap = state.panel;
                var isChecked = $("input[name='datagrid_detail_button']", wrap).attr("checked");
                if (opts.isShowDetail && isChecked) {
                    showDblClickRowDetail(target, obj.tr);
                }
            }
            if (obj.cell.parent().hasClass("datagrid-cell-check")) {
                if (opts.singleSelect && opts.selectOnCheck) {
                    if (!opts.checkOnSelect) {
                        uncheckAll(target, true);
                    }
                    checkRow(target, obj.index);
                } else {
                    if (obj.cell.is(":checked")) {
                        checkRow(target, obj.index);
                    } else {
                        uncheckRow(target, obj.index);
                    }
                }
            } else if (obj.cell.parent().hasClass("datagrid-cell-subrow")) {
                if (obj.cell.hasClass("datagrid-row-expand")) {
                    obj.cell.removeClass("datagrid-row-expand").addClass("datagrid-row-collapse");
                    var $tr1 = getSubRow(target, obj.index, 1);
                    var $tr2 = getSubRow(target, obj.index, 2);
                    if ($tr2 && $tr2.length > 0) {
                        $tr1.add($tr2).show();
                    } else {
                        var view = $.data(target, "datagrid").options.view;
                        view.insertSubRow.call(view, target, obj.index, obj.row);
                        opts.onExpandRow.call(target, obj.index, obj.row);
                    }
                } else {
                    obj.cell.removeClass("datagrid-row-collapse").addClass("datagrid-row-expand");
                    var $tr1 = getSubRow(target, obj.index, 1);
                    var $tr2 = getSubRow(target, obj.index, 2);
                    if ($tr2 && $tr2.length > 0) {
                        $tr1.add($tr2).hide();
                    }
                    opts.onCollapseRow.call(target, obj.index, obj.row);
                }
                selectRow(target, obj.index);
            } else {
                if (obj.field) {
                    opts.onClickCell.call(target, obj.index, obj.field, obj.row[obj.field]);
                }
                if (opts.singleSelect == true) {
                    selectRow(target, obj.index);
                } else {
                    if (obj.tr.hasClass("datagrid-row-selected")) {
                        unselectRow(target, obj.index);
                    } else {
                        selectRow(target, obj.index);
                    }
                }
                if (opts.rowEdit) {
                    //add row edit event
                    if (typeof opts.editing == "undefined") {
                        beginEdit(target, obj.index);
                        opts.editing = obj.index;
                    } else if (opts.editing != obj.index) {
                        if (validateRow(target, opts.editing)) {
                            endEdit(target, opts.editing);
                            beginEdit(target, obj.index);
                            opts.editing = obj.index;
                        }
                    }
                }
                opts.onClickRow.call(target, obj.index, obj.row, e);
            }
            e.stopPropagation();
            break;

          case "dblclick":
            if (obj.field) {
                opts.onDblClickCell.call(target, obj.index, obj.field, obj.row[obj.field]);
            }
            opts.onDblClickRow.call(target, obj.index, obj.row, e);
            e.stopPropagation();
            break;

          case "scroll":
            //    		dc.view1.children("div.datagrid-body").scrollTop($(dc.body1).scrollTop());
            dc.view1.children("div.datagrid-body").scrollTop(dc.body2.scrollTop());
            dc.view2.children("div.datagrid-header,div.datagrid-footer").scrollLeft($(dc.body2).scrollLeft());
            // datagrid中每行的高度是保存在opts.trHeight中，用opts.fallParas[0].cntNum乘以高度除以5，相当于在末尾五分之一处就开始加载新的数据并添加到末尾
            if (!opts.requesting && opts.fallParas[0].enable && !opts.fallParas[0].last && opts.reportParams) {
                // 增加opts.reportParams判断，必须进行过一次查询才会触发该事件
                if (!opts.trHeight) {
                    opts.trHeight = dc.body2.find("tr:first").height();
                }
                var delRows = 0;
                var scTop = dc.body2.scrollTop();
                if (state.data.total > opts.maxRows) delRows = state.data.total - opts.maxRows;
                if ((state.data.total - delRows) * opts.trHeight <= dc.body2.height() + scTop + 10) {
                    //if(delRows) dc.body2.scrollTop(scTop - 20);
                    //后台分页的时候 不是用瀑布流加载数据 @zhaozz 2016-06-23
                    if (opts.fallParas && opts.fallParas[0] && opts.fallParas[0].nextPageBtn) {
                        return;
                    }
                    //mybatis 分页时，也不用瀑布流加载数据  by lims -- 2016.9.24
                    if (opts.fallParas[0].dbPagination) {
                        return;
                    }
                    request(target, null, true);
                }
            }
            break;

          case "mouseover":
            if (!obj.tr) {
                return;
            }
            obj.tr.addClass("datagrid-row-over");
            e.stopPropagation();
            break;

          case "mouseout":
            if (!obj.tr) {
                return;
            }
            obj.tr.removeClass("datagrid-row-over");
            e.stopPropagation();
            break;

          default:
            break;
        }
    }
    //检测是否全部勾选，如果是则勾选全选checkbox，如果否则去掉全选的checkbox @zhaozz 20150901
    function _checkAllSelect(dc, index) {
        var arrChkbox = dc.view1.find("[type=checkbox]"), chkNum = 0;
        if (!arrChkbox.length) {
            arrChkbox = dc.view2.find("[type=checkbox]");
        }
        if (index >= 0 && $(arrChkbox[0]).hasClass("header-check-input")) {
            for (var i = 1, len = arrChkbox.length; i < len; i++) {
                $(arrChkbox[i]).prop("checked") && chkNum++;
            }
            $(arrChkbox[0]).prop("checked", chkNum === arrChkbox.length - 1);
        }
    }
    function _bindBodyEvent(target, bodys, event) {
        addEvent(bodys, event, function(e) {
            _bodyEventHandler(e, event, target);
        });
    }
    function bindEvents(target) {
        var state = $.data(target, "datagrid");
        var panel = state.panel;
        var opts = state.options;
        var dc = state.dc;
        var header = dc.header1.add(dc.header2);
        var $headCell = $("td:has(div.datagrid-cell)", header);
        addEvent($headCell, "mouseenter", function(e) {
            if (state.resizing) return;
            $(this).addClass("datagrid-header-over");
        });
        addEvent($headCell, "mouseleave", function(e) {
            $(this).removeClass("datagrid-header-over");
        });
        addEvent($headCell, "contextmenu", function(e) {
            var field = $(this).attr("field");
            opts.onHeaderContextMenu.call(target, e, field);
        });
        var $headCheckbox = $("input[type=checkbox]", header);
        addEvent($headCheckbox, "click", function(e) {
            if (opts.singleSelect && opts.selectOnCheck) {
                return false;
            }
            if ($(this).is(":checked")) {
                checkAll(target);
            } else {
                uncheckAll(target);
            }
            e.stopPropagation();
        });
        var $headerCell = $("div.datagrid-cell", header);
        addEvent($headerCell, "click", function(e) {
            if ($(e.target).hasClass("datagrid-match-icon")) {
                // 点中漏斗，用于过滤数据
                // -----点中漏斗之后自动生成下拉框并增加选中事件
                var field = $(this).parent().attr("field");
                var col = getColumnOption(target, field);
                //修复datagrid列筛选(datagrid-match)的bug
                //@chenguiy 2015-11-25 begin
                if (col.type == "combotree") {
                    var nodeNameArray = col.nodeName ? col.nodeName.split(",") : col.editor.options.nodeName.split(",");
                    var nodeName = nodeNameArray.length === 1 ? nodeNameArray[0] : nodeNameArray[1];
                } else if (col.editor.options.req) {
                    var textNameArray = col.editor.options.textField.split(",");
                    var textName = textNameArray.length === 1 ? textNameArray[0] : textNameArray[1];
                }
                var selectItem = col.type == "combotree" ? {
                    textField: col.nodeName || col.editor.options.nodeName,
                    valueField: col.nodeId || col.editor.options.nodeId
                } : col.editor.options.req ? {
                    textField: col.editor.options.textField,
                    valueField: col.editor.options.valueField
                } : {
                    textField: "dict_val,dict_des",
                    valueField: "dict_val"
                };
                var showField = col.type == "combotree" ? {
                    textField: nodeName,
                    valueField: col.nodeId || col.editor.options.nodeId
                } : col.editor.options.req ? {
                    textField: textName,
                    valueField: col.editor.options.valueField
                } : {
                    textField: "dict_des",
                    valueField: "dict_val"
                };
                var tempData = [];
                if (col.editor.options["orgFilter"] && typeof col.editor.options["orgFilter"] == "string") {
                    var f = eval(col.editor.options["orgFilter"]);
                    col.editor.options.data = f(col.editor.options.data);
                } else {
                    col.editor.options.data = $.grep(col.editor.options.data, function(item, i) {
                        //begin:修复bug：机构数据字典的重复项被过滤 2015-12-12 @zhaozz
                        var tempItem = {
                            textField: item[showField.valueField] + "-" + item[showField.textField],
                            valueField: item[showField.valueField],
                            dict_org: item.dict_org
                        };
                        var tempDataLength = tempData.length;
                        var i = 0;
                        for (;i < tempDataLength; i++) {
                            if (tempItem.textField == tempData[i].textField && tempItem.valueField == tempData[i].valueField && item.dict_org == tempData[i].dict_org) {
                                break;
                            }
                        }
                        //end:修复bug：机构数据字典的重复项被过滤 2015-12-12 @zhaozz
                        if (i == tempDataLength) {
                            tempData.push(tempItem);
                            return true;
                        }
                    });
                }
                var selectedText = $(this).closest("td").attr("filter");
                var selectedValue = $.grep(col.editor.options.data, function(item, index) {
                    return item[showField.valueField] + "-" + item[showField.textField] == selectedText;
                });
                if (selectedValue.length > 0) {
                    selectedValue = selectedValue[0][showField.valueField];
                } else {
                    selectedValue = "";
                }
                /*
                * col.filterable?col.filterable:false  当在view配置filterable=true时，可手工输入关键字
                * 解决表头数据筛选时，可手工输入关键字----by lims
                * */
                var options = $.extend({}, col.editor.options, {
                    editable: col.filterable ? col.filterable : false,
                    required: false,
                    multiple: false,
                    isClear: false,
                    showDictValue: false,
                    width: col.width,
                    panelHeight: col.editor.options.data.length > 9 ? 220 : undefined,
                    value: selectedValue,
                    onSelect: function(d) {
                        var filterStr = $(this).next().find(">input:first").val();
                        var index = filterStr.indexOf("-") + 1;
                        var filter = filterStr.substr(index);
                        $(this).next().find(">input:first").val(filter);
                        if (filter && filter != "请选择") {
                            $(this).closest("td").addClass("filtered").attr("filter", filter);
                        } else {
                            $(this).closest("td").removeClass("filtered").attr("filter", "");
                        }
                        headFilter(target);
                    }
                }, selectItem, {
                    extItems: ""
                });
                var needExtItem = true;
                //!options.extItems && options.data[0][showField.textField] != '请选择';
                if (needExtItem) {
                    var oItem = {};
                    oItem[showField.valueField] = "";
                    oItem[showField.textField] = "请选择";
                    options.data.unshift(oItem);
                }
                //@chenguiy 2015-11-25 end
                $("<div><input></div>").prependTo(this).click(function() {
                    return false;
                }).find("input").combobox($.extend({}, options, {
                    onHidePanel: function() {
                        var filter = $(this).next().find(">input:first").val();
                        if (filter && filter != "请选择") {
                            $(this).closest("td").addClass("filtered").attr("filter", filter);
                        } else {
                            $(this).closest("td").removeClass("filtered");
                        }
                        $(this).parent().remove();
                    }
                })).next().children("input").focus();
                if (needExtItem) {
                    options.data.splice(0, 1);
                }
            } else if ($(e.target).hasClass("datagrid-filter-icon")) {
                // -----点中表头匹配自动生成一个输入框，输入正则表达式用于过滤
                var field = $(this).parent().attr("field");
                var col = getColumnOption(target, field);
                var options = {
                    onBlur: function() {
                        var $this = $(this);
                        var text = $.trim($this.val());
                        if (text != "") {
                            $(this).closest("td").addClass("filtered").attr("filter", text);
                        } else {
                            $(this).closest("td").removeClass("filtered").attr("filter", "");
                        }
                        $this.parent().remove();
                        headFilter(target);
                    }
                };
                $('<div><input style="width:' + col.width + 'px;"></div>').prependTo(this).click(function(e) {
                    return false;
                }).find("input").focus().textinput(options).val($(e.target).closest("td").attr("filter")).keydown(function(e) {
                    e.stopPropagation();
                    if (e.which == 13) {
                        return false;
                    }
                });
            } else if (e.pageX < $(this).offset().left + $(this).outerWidth() - 5) {
                var field = $(this).parent().attr("field");
                var col = getColumnOption(target, field);
                if (!col.sortable || col.sortable.toString() == "false" || state.resizing) {
                    return;
                }
                opts.sortName = field;
                opts.sortOrder = "asc";
                var className = "datagrid-sort-asc";
                if ($(this).hasClass(className)) {
                    className = "datagrid-sort-desc";
                    opts.sortOrder = "desc";
                }
                $headerCell.removeClass("datagrid-sort-asc datagrid-sort-desc");
                $(this).addClass(className);
                if (opts.remoteSort) {
                    request(target);
                } else {
                    var data = $.data(target, "datagrid").data;
                    loadData(target, data, 0);
                }
                opts.onSortColumn.call(target, opts.sortName, opts.sortOrder);
            }
        });
        addEvent($headerCell, "dblclick", function(e) {
            if (e.pageX > $(this).offset().left + $(this).outerWidth() - 5) {
                var field = $(this).parent().attr("field");
                var col = getColumnOption(target, field);
                if (col.resizable == false) {
                    return;
                }
                $(target).datagrid("autoSizeColumn", field);
                col.auto = false;
            }
        });
        $headerCell.each(function() {
            $(this).resizable({
                handles: "e",
                disabled: $(this).attr("resizable") ? $(this).attr("resizable") == "false" : false,
                minWidth: 25,
                onStartResize: function(e) {
                    state.resizing = true;
                    header.css("cursor", "e-resize");
                    if (!state.proxy) {
                        state.proxy = $('<div class="datagrid-resize-proxy"></div>').appendTo(dc.view);
                    }
                    state.proxy.css({
                        left: e.pageX - $(panel).offset().left - 1,
                        display: "none"
                    });
                    setTimeout(function() {
                        if (state.proxy) {
                            state.proxy.show();
                        }
                    }, 500);
                },
                onResize: function(e) {
                    state.proxy.css({
                        left: e.pageX - $(panel).offset().left - 1,
                        display: "block"
                    });
                    return false;
                },
                onStopResize: function(e) {
                    header.css("cursor", "");
                    var field = $(this).parent().attr("field");
                    var col = getColumnOption(target, field);
                    col.width = $(this).outerWidth();
                    col.boxWidth = parseInt(this.style.width);
                    col.auto = undefined;
                    fixColumnSize(target, field);
                    dc.view2.children("div.datagrid-header").scrollLeft(dc.body2.scrollLeft());
                    state.proxy.remove();
                    state.proxy = null;
                    if ($(this).parents("div:first.datagrid-header").parent().hasClass("datagrid-view1")) {
                        resize(target);
                    }
                    fitColumns(target);
                    var widthLs = [];
                    var columns = getOriginalColumns(target);
                    for (var i = 0, len = columns.length; i < len; i++) {
                        widthLs.push(parseInt(columns[i].boxWidth));
                    }
                    $.ls(opts.lsKey + "_width", widthLs);
                    opts.onResizeColumn.call(target, field, col.width);
                    setTimeout(function() {
                        state.resizing = false;
                    }, 0);
                }
            });
        });
        var bodys = dc.body1.add(dc.body2);
        _bindBodyEvent(target, bodys, "mouseover");
        _bindBodyEvent(target, bodys, "mouseout");
        _bindBodyEvent(target, bodys, "click");
        _bindBodyEvent(target, bodys, "dblclick");
        _bindBodyEvent(target, bodys, "contextmenu");
        _bindBodyEvent(target, bodys, "scroll");
    }
    function showDblClickRowDetail(target, tr) {
        var obj = $.data(target, "datagrid");
        var opts = obj.options, dc = obj.dc;
        if (!opts.isShowDetail) return;
        // 有固定列时单击某行时，固定列中的值也需要显示在详细信息中
        if (tr[0]) {
            var trId = tr[0].id;
            trId = trId.replace(/\d-\d*$/, function(str) {
                return str.replace(/^\d/, function(str1) {
                    return str1 == "1" ? "2" : "1";
                });
            });
            var trs = tr.add("#" + trId);
            tr = trs;
        }
        var $detail = dc.view.nextAll(".datagrid-row-detail");
        if ($detail.length > 0) {
            if ($detail.is(":hidden")) {
                opts.detailTrsLen = 0;
            }
            $detail.remove();
        } else {
            opts.detailTrsLen = 0;
        }
        var cols = $.extend([], $(target).datagrid("getOriginalColumns", false));
        // 防止后面的splice更改grid配置
        for (var i = 0, len = cols.length; i < len; i++) {
            if (/ hide/.test(cols[i].cellClass) || cols[i].hidden || !!cols[i].listHidden) {
                cols.splice(i, 1);
                i--;
                len--;
            }
        }
        if (opts.showDetailCols && opts.showDetailCols.length > 0) {
            for (var i = 0; i < cols.length; i++) {
                if (opts.showDetailCols.indexOf(cols[i]["field"]) == -1) {
                    cols.splice(i, 1);
                    i--;
                }
            }
        }
        var labelWidth = 120, colsLength = cols.length, preWidth = 0, dWidth = parseInt(dc.view[0].style.width) - 16;
        // 显示详细信息长度过长的BUG。 函数dc.view.width()在数据量大的时候速度太慢，换成了较快速的实现方式。
        var detail = [ '<div class="datagrid-row-detail"><table width="95%"><tbody>' ];
        var trsLen = 0;
        for (var i = 0; i < colsLength; i++) {
            if (String(cols[i].hidden) == "true" || String(cols[i].title) == "操作" || cols[i].type == "notice") {
                continue;
            }
            if (preWidth == 0) {
                detail.push("<tr>");
                trsLen++;
            }
            var content = tr && tr.length > 0 ? tr.find("td[field=" + cols[i].field + "]").children().text() : "";
            content = content.replace(/(^\s*)|(\s*$)/g, "");
            var fLength = caculateCharLength(content);
            fLength = 120;
            content = content.replace(/\"/g, "&quot");
            content = content.replace(/</g, "&lt");
            content = content.replace(/>/g, "&gt");
            var colspan = 1;
            if (fLength > 120) {
                var floor = Math.round(fLength / 120);
                if (floor > 1 && floor % 2 == 0) {
                    colspan = floor - 1;
                } else {
                    colspan = floor;
                }
            } else {
                fLength = Math.max(120, fLength);
                colspan = 1;
            }
            var cLength = labelWidth + colspan * 120 + 5;
            if (dWidth - preWidth < cLength) {
                detail.push("</tr>");
                detail.push("<tr>");
                trsLen++;
                preWidth = cLength;
            } else {
                preWidth += cLength;
            }
            var _title = cols[i].title;
            //有多选框时选中一行点击详细消息的异常处理。
            if (_.isUndefined(_title)) {
                continue;
            }
            if (_title.length > 8) {
                _title = _title.substring(0, 8) + "...";
            } else {
                _title = cols[i].title;
            }
            detail.push("<td class='datagrid-detail-label'><div style=\"width:" + labelWidth + 'px;overflow:hidden;white-space:nowrap;word-wrap:normal;" title="' + cols[i].title + '">' + _title + "</div></td>");
            //" + (fLength > 100 ? fLength: 100) + "
            //去掉宽度的判断，始终用120，超出部分显示省略号
            detail.push('<td nowrap colspan="' + colspan + '"><div id="detail_' + cols[i].field + '" class=\'datagrid-detail-value\' style="width:120px;overflow:hidden;white-space:nowrap;text-overflow: ellipsis;word-wrap:normal;" title="' + content + '">' + content + "</div></td>");
        }
        detail.push("</tbody></table></div>");
        //$detail = $(detail.join("")).insertAfter(dc.view);
        var rowDetailHeight = opts.rowDetailHeight == "auto" ? 26 * trsLen + 2 : opts.rowDetailHeight;
        $detail = $(detail.join("")).height(rowDetailHeight);
        $detail = $(detail.join("")).height(opts.rowDetailHeight);
        var $statistics = dc.view.nextAll(".datagrid-row-statistics");
        if ($statistics.length > 0) {
            $detail.insertAfter($statistics);
        } else {
            $detail.insertAfter(dc.view);
        }
        if (trsLen != opts.detailTrsLen) {
            opts.detailTrsLen = trsLen;
            resize(target);
        }
    }
    function showStatisticsRow(target) {
        var obj = $.data(target, "datagrid"), rows = obj.data.rows, opts = obj.options;
        if (!opts.showStatistics) {
            return false;
        }
        var columns = opts.columns[0], frozenColumns = opts.frozenColumns[0], cols = columns.concat(frozenColumns);
        $(target).statistics({
            columns: cols,
            rows: rows,
            dc: obj.dc,
            stsDescFieldIndex: opts.stsDescFieldIndex,
            view: opts.view,
            statisticsTexts: opts.statisticsTexts,
            fields: $(target).datagrid("getColumnFields", false),
            onLoaded: function() {
                resize(target);
            }
        });
        if (!opts.isShowStatistics) {
            $(target).statistics("hidePanel");
        }
    }
    function caculateCharLength(cString) {
        cString = cString + "";
        // 确保是字符串类型
        var len = 0;
        if (!cString || cString == "") return len;
        for (i = 0; i < cString.length; i++) {
            if (cString.charCodeAt(i) > 256) {
                len += 2 + 12;
            } else if (cString.charCodeAt(i) < 65) {
                len += 8;
            } else {
                len += 9;
            }
        }
        return len;
    }
    //点击数据行时触发
    function selectRow(target, index, check, boolTrigger) {
        var state = $.data(target, "datagrid");
        var dc = state.dc;
        var opts = state.options;
        var data = state.data;
        var grid = $.data(target, "datagrid");
        //用于在singleSelect模式下保存被选中的那一列的序号
        var selectedRows = grid.selectedRows;
        if (opts.singleSelect) {
            //            unselectAll(target); // 在singleSelect模式下不需要unselectAll，只需要将以前选中的那行取消选中即可，行数过多导致unselectAll效率低下
            opts.selectOnCheck && uncheckRow(target, grid.singleSelectIndex, false);
            // 将以前选中的那行取消选中
            selectedRows.splice(0, selectedRows.length);
        }
        if (!check && opts.checkOnSelect) {
            checkRow(target, index, true, boolTrigger);
        }
        grid.singleSelectIndex = index;
        if (opts.idField) {
            var row = opts.finder.getRow(target, index);
            (function() {
                for (var i = 0; i < selectedRows.length; i++) {
                    //ray 添加了row 与 selectedRows[i]判断
                    if (row && selectedRows[i] && selectedRows[i][opts.idField] == row[opts.idField]) {
                        return;
                    }
                }
                selectedRows.push(row);
            })();
        }
        var tr = opts.finder.getTr(target, index).addClass("datagrid-row-selected");
        //ray 在treegrid深度级联选择时慢,
        //特加上$.data(target,****)缓存数据。
        if (tr.length) {
            var height = 0, tr_top = 0, body2 = dc.body2;
            if (!$.data(target, "datagrid-header-outerHeight")) {
                $.data(target, "datagrid-header-outerHeight", dc.view2.children("div.datagrid-header").outerHeight());
            }
            if (!$.data(target, "row-selected_top")) {
                $.data(target, "row-selected_top", tr.position().top);
            }
            height = $.data(target, "datagrid-header-outerHeight");
            tr_top = $.data(target, "row-selected_top");
        }
        !opts.singleSelect && !opts.checkOnSelect && _checkAllSelect(dc, index);
        if (typeof opts.onSelect == "string") opts.onSelect = eval(opts.onSelect);
        false !== boolTrigger && opts.onSelect.call(target, index, data.rows[index]);
    }
    //行数据可以进行多选的情况下,再取消选中一行时触发
    function unselectRow(target, index, check) {
        var state = $.data(target, "datagrid");
        var dc = state.dc;
        var opts = state.options;
        var data = state.data;
        var selectedRows = $.data(target, "datagrid").selectedRows;
        if (!check && opts.checkOnSelect) {
            uncheckRow(target, index, true);
        }
        opts.finder.getTr(target, index).removeClass("datagrid-row-selected");
        var row = opts.finder.getRow(target, index);
        if (opts.idField && row) {
            remove(selectedRows, opts.idField, row[opts.idField]);
        }
        !opts.singleSelect && !opts.checkOnSelect && _checkAllSelect(dc, index);
        opts.onUnselect.call(target, index, row);
    }
    //选中整页数据时触发
    function selectAll(target, check) {
        var state = $.data(target, "datagrid");
        var opts = state.options;
        var rows = state.data.rows;
        var selectRows = $.data(target, "datagrid").selectedRows;
        if (!check && opts.checkOnSelect) {
            checkAll(target, true);
        }
        opts.finder.getTr(target, "", "allbody").addClass("datagrid-row-selected");
        if (opts.idField) {
            for (var index = 0; index < rows.length; index++) {
                (function() {
                    var row = rows[index];
                    for (var i = 0; i < selectRows.length; i++) {
                        if (selectRows[i][opts.idField] == row[opts.idField]) {
                            return;
                        }
                    }
                    selectRows.push(row);
                })();
            }
        }
        opts.onSelectAll.call(target, rows);
    }
    //反选整页数据时触发
    function unselectAll(target, check) {
        var state = $.data(target, "datagrid");
        var opts = state.options;
        var rows = state.data.rows;
        var selectedRows = $.data(target, "datagrid").selectedRows;
        if (!check && opts.checkOnSelect) {
            uncheckAll(target, true);
        }
        opts.finder.getTr(target, "", "selected").removeClass("datagrid-row-selected");
        if (opts.idField) {
            for (var i = 0; i < rows.length; i++) {
                remove(selectedRows, opts.idField, rows[i][opts.idField]);
            }
        }
        opts.onUnselectAll.call(target, selectedRows);
    }
    function checkRow(target, index, check, boolTrigger) {
        var state = $.data(target, "datagrid");
        var dc = state.dc;
        var opts = state.options;
        var data = state.data;
        var rowData = data.rows[index] ? data.rows[index] : data.rows[index - (opts.pageNumber - 1) * opts.pageSize];
        if (!check && opts.selectOnCheck) {
            selectRow(target, index, true);
        }
        var ck = opts.finder.getTr(target, index).find("div.datagrid-cell-check input[type=checkbox]");
        $.fn.prop ? ck.prop("checked", true) : ck.attr("checked", true);
        !opts.singleSelect && _checkAllSelect(dc, index);
        false !== boolTrigger && opts.onCheck.call(target, index, rowData, check);
    }
    function uncheckRow(target, index, check, boolTrigger) {
        var state = $.data(target, "datagrid");
        var dc = state.dc;
        var opts = state.options;
        var data = state.data;
        var rowData = data.rows[index] ? data.rows[index] : data.rows[index - (opts.pageNumber - 1) * opts.pageSize];
        if (!check && opts.selectOnCheck) {
            unselectRow(target, index, true);
        }
        var ck = opts.finder.getTr(target, index).find("div.datagrid-cell-check input[type=checkbox]");
        $.fn.prop ? ck.prop("checked", false) : ck.attr("checked", false);
        !opts.singleSelect && _checkAllSelect(dc, index);
        false !== boolTrigger && opts.onUncheck.call(target, index, rowData, check);
    }
    function checkAll(target, check) {
        var state = $.data(target, "datagrid");
        var dc = state.dc;
        var opts = state.options;
        var data = state.data;
        if (!check && opts.selectOnCheck) {
            selectAll(target, true);
        }
        var checkInput = opts.finder.getTr(target, "", "allbody").find("div.datagrid-cell-check input[type=checkbox]");
        $.fn.prop ? checkInput.prop("checked", true) : checkInput.attr("checked", true);
        !opts.singleSelect && _checkAllSelect(dc, 0);
        opts.onCheckAll.call(target, data.rows, check);
    }
    function uncheckAll(target, check) {
        var state = $.data(target, "datagrid");
        var dc = state.dc;
        var opts = state.options;
        var data = state.data;
        if (!check && opts.selectOnCheck) {
            unselectAll(target, true);
        }
        var checkInput = opts.finder.getTr(target, "", "allbody").find("div.datagrid-cell-check input[type=checkbox]");
        $.fn.prop ? checkInput.prop("checked", false) : checkInput.attr("checked", false);
        !opts.singleSelect && _checkAllSelect(dc, 0);
        opts.onUncheckAll.call(target, data.rows, check);
    }
    function beginEdit(target, index) {
        var opts = $.data(target, "datagrid").options;
        var tr = opts.finder.getTr(target, index);
        var row = opts.finder.getRow(target, index);
        if (tr.hasClass("datagrid-row-editing")) {
            return;
        }
        if (opts.onBeforeEdit.call(target, index, row) == false) {
            return;
        }
        tr.addClass("datagrid-row-editing");
        createEditor(target, index);
    }
    function endEdit(target, lastIndex, cancelEdit) {
        var opts = $.data(target, "datagrid").options;
        var updatedRows = $.data(target, "datagrid").updatedRows;
        var insertedRows = $.data(target, "datagrid").insertedRows;
        var tr = opts.finder.getTr(target, lastIndex);
        var row = opts.finder.getRow(target, lastIndex);
        if (!tr.hasClass("datagrid-row-editing")) {
            return;
        }
        if (!cancelEdit) {
            if (!canEdit(target, lastIndex)) {
                return;
            }
            var change = false;
            var newValue = {};
            tr.find("div.datagrid-editable").each(function() {
                var field = $(this).parent().attr("field");
                var col = getColumnOption(target, field);
                var ed = $.data(this, "datagrid.editor");
                var edValue;
                if (ed.actions) {
                    edValue = ed.actions.getValue ? ed.actions.getValue(ed.target) : "";
                } else {
                    edValue = $(ed.filter, $(this)).data("combo") ? $(ed.filter, $(this))[ed.type]("getValues").join(",") : $(ed.filter, $(this))[ed.type]("getValue");
                }
                if (row[field] != edValue && edValue != undefined) {
                    row[field] = edValue;
                    change = true;
                    newValue[field] = edValue;
                }
            });
            if (change) {
                if (contain(insertedRows, row) == -1) {
                    if (contain(updatedRows, row) == -1) {
                        updatedRows.push(row);
                    }
                }
            }
        }
        tr.removeClass("datagrid-row-editing");
        destroyEditor(target, lastIndex);
        $(target).datagrid("refreshRow", lastIndex);
        if (!cancelEdit) {
            opts.onAfterEdit.call(target, lastIndex, row, newValue);
        } else {
            opts.onCancelEdit.call(target, lastIndex, row);
        }
    }
    function canEdit(target, index) {
        var tr = $.data(target, "datagrid").options.finder.getTr(target, index);
        if (!tr.hasClass("datagrid-row-editing")) {
            return true;
        }
        var vbox = tr.find(".validatebox-text");
        vbox.validatebox("validate");
        vbox.trigger("mouseleave");
        var ivbox = tr.find(".validatebox-invalid");
        return ivbox.length == 0;
    }
    //返回固定行固定列的editor
    function getEditor(target, options) {
        var ed = null;
        var opts = $.data(target, "datagrid").options;
        var tr = opts.finder.getTr(target, options.index);
        tr.find("div.datagrid-editable").each(function() {
            var field = $(this).parent().attr("field");
            if (field == options.field) {
                ed = $.data(this, "datagrid.editor");
            }
        });
        return ed;
    }
    function destroyEditor(target, index) {
        var opts = $.data(target, "datagrid").options;
        var tr = opts.finder.getTr(target, index);
        tr.children("td").each(function() {
            var cell = $(this).find("div.datagrid-editable");
            if (cell.length) {
                var ed = $.data(cell[0], "datagrid.editor");
                if (ed.actions) {
                    if (ed.actions.destroy) ed.actions.destroy(ed.target);
                } else if ($(ed.filter, cell)[ed.type]) {
                    $(ed.filter, cell)[ed.type]("destroy");
                }
                cell.html(ed.oldHtml);
                $.removeData(cell[0], "datagrid.editor");
                cell.removeClass("datagrid-editable");
                cell.css("width", "");
            }
        });
    }
    function destroyEditorCell(cell) {
        if (cell.length) {
            var ed = $.data(cell[0], "datagrid.editor");
            if (ed.actions.destroy) {
                ed.actions.destroy(ed.target);
            }
            cell.html(ed.oldHtml);
            $.removeData(cell[0], "datagrid.editor");
            cell.removeClass("datagrid-editable");
            cell.css("width", "");
        }
    }
    function deleteAllSelections(target, loadAgain) {
        var indexs = [];
        var opts = $.data(target, "datagrid").options;
        opts.finder.getTr(target, "", "selected", 2).each(function() {
            var index = parseInt($(this).attr("datagrid-row-index"));
            indexs.push(index);
        });
        indexs.sort(function(a, b) {
            return a < b ? 1 : -1;
        });
        $.each(indexs, function(index, item) {
            deleteRow(target, item);
        });
        if (loadAgain !== false) {
            loadData(target, $.data(target, "datagrid").data);
        }
    }
    function deleteRow(target, index, cancelEdit) {
        var opts = $.data(target, "datagrid").options;
        var updatedRows = $.data(target, "datagrid").updatedRows;
        var insertedRows = $.data(target, "datagrid").insertedRows;
        var tr = opts.finder.getTr(target, index);
        tr.remove();
        var row = opts.finder.getRow(target, index);
        if (!tr.hasClass("datagrid-row-editing")) {
            //ray 把grid里的数据也做删除 
            var data = $.data(target, "datagrid").data;
            data.rows.splice(index, 1);
            data.total -= 1;
            opts.originalRowsData.splice(index, 1);
            //@zhaozz 2016-03-28
            return;
        }
        if (!cancelEdit) {
            if (!validateRow(target, index)) {
                return;
            }
            var change = false;
            var values = {};
            tr.find("div.datagrid-editable").each(function() {
                var field = $(this).parent().attr("field");
                var editor = $.data(this, "datagrid.editor");
                var newValue = editor.actions.getValue(editor.target);
                if (row[field] != newValue) {
                    row[field] = newValue;
                    change = true;
                    values[field] = newValue;
                }
            });
            if (change) {
                if (contain(insertedRows, row) == -1) {
                    if (contain(updatedRows, row) == -1) {
                        updatedRows.push(row);
                    }
                }
            }
        }
        tr.removeClass("datagrid-row-editing");
        destroyEditor(target, index);
        $(target).datagrid("refreshRow", index);
        if (!cancelEdit) {
            opts.onAfterEdit.call(target, index, row, values);
        } else {
            opts.onDeleteRow.call(target, index, row);
        }
        //ray 把grid里的数据也做删除且重新排下index
        var data = $.data(target, "datagrid").data;
        var gd = $.data(target, "datagrid");
        for (var i = index + 1; i < data.rows.length; i++) {
            opts.finder.getTr(target, i, "body", 2).attr("datagrid-row-index", i - 1).attr("id", gd.rowIdPrefix + "-2-" + (i - 1));
            var tr1 = opts.finder.getTr(target, i, "body", 1).attr("datagrid-row-index", i - 1).attr("id", gd.rowIdPrefix + "-1-" + (i - 1));
            if (opts.rownumbers) {
                tr1.find("div.datagrid-cell-rownumber").html(i);
            }
        }
        data.rows.splice(index, 1);
        data.total -= 1;
    }
    function loadData(target, data, addition) {
        var state = $.data(target, "datagrid");
        var opts = state.options;
        var sortOrder = state.options.sortOrder;
        var dc = state.dc;
        var selectedRows = state.selectedRows;
        var head = data.HEAD;
        //回调控制分页的上一页，按钮 by lims 2016-9-24
        if ($.data(target, "datagrid").options.fallParas[0].dbPagination) {
            if (data.HEAD) {
                paginatonBtn(target, data);
            }
        }
        return $.when(//在数据加载之前对数据进行过滤。不改变结果集形式。
        $.isFunction(opts.beforeFilter) && opts.beforeFilter.call(target, data)).then(function(data) {
            data = opts.loadFilter.call(target, data, opts.pageNumber);
            //增加了参数opts.pageNumber页面数
            var appendRows;
            if (addition === true) {
                appendRows = data.rows;
                if (appendRows && appendRows.length) {
                    state.data.total = parseInt(state.data.total) + parseInt(data.total);
                    var delRow = state.data.rows.length + appendRows.length - opts.maxRows;
                    state.appendRows = $.extend([], appendRows);
                    //统计的时候使用
                    if (delRow > 0) {
                        if (sortOrder !== "desc") {
                            state.data.rows = state.data.rows.concat(appendRows);
                            state.data.rows = state.data.rows.slice(opts.maxRows);
                            dc.body2.find("tr:lt(" + delRow + ")").remove();
                        } else {
                            var resolveRow = opts.maxRows - appendRows.length;
                            state.data.rows = state.data.rows.slice(0, resolveRow);
                            dc.body2.find("tr:gt(" + resolveRow + ")").remove();
                            state.data.rows = state.data.rows.concat(appendRows);
                        }
                    } else {
                        state.data.rows = state.data.rows.concat(appendRows);
                    }
                    if (appendRows.length && appendRows.length != opts.fallParas[0].cntNum) {
                        opts.fallParas[0].last = true;
                    }
                } else {
                    opts.fallParas[0].last = true;
                }
            } else if (addition === false || addition === undefined) {
                state.appendRows = $.extend([], data.rows);
                //统计的时候使用
                state.data = data;
                opts.fallParas[0].last = false;
                if (!data.rows) {
                    data.rows = [];
                    data.total = 0;
                }
                if (opts.fallParas[0].enable && data.total != opts.fallParas[0].cntNum) {
                    opts.fallParas[0].last = true;
                }
            } else if (addition === 0) {
                // addition 传0专门用于排序时的刷新，排序时的刷新绝不能改变opts.fallParas[0].last的状态。                
                state.data = data;
                appendRows = [];
                if (!data.rows) {
                    data.rows = [];
                    data.total = 0;
                }
            }
            if (!opts.remoteSort) {
                var col = getColumnOption(target, opts.sortName);
                if (col) {
                    var sorter = col.sorter;
                    col.sortType = col.type === "numberbox" ? "number" : col.sortType;
                    col.sortType = col.type === "datebox" ? "date" : col.sortType;
                    if (!sorter) {
                        if (col.sortType == "number" || col.sortType == "date") {
                            sorter = function(_a, _b) {
                                //将 yyyy-MM-dd的日期类型转换为 yyyyMMdd @zhaozz 2016-06-14
                                var a = _a.replace(/-/g, ""), b = _b.replace(/-/g, "");
                                if (a && b) {
                                    return parseFloat(a) > parseFloat(b) ? 1 : -1;
                                } else if (!a) {
                                    return -1;
                                } else {
                                    return 1;
                                }
                            };
                        } else {
                            sorter = function(a, b) {
                                return a > b ? 1 : -1;
                            };
                        }
                    }
                    if (data.rows) {
                        data.rows.sort(function(row1, row2) {
                            return sorter(row1[opts.sortName], row2[opts.sortName]) * (opts.sortOrder == "asc" ? 1 : -1);
                        });
                    }
                }
            }
            var checkInput = dc.view2.find("div.datagrid-header-check input[type=checkbox]");
            checkInput && $.fn.prop ? checkInput.prop("checked", false) : checkInput.attr("checked", false);
            if (opts.view.onBeforeRender) {
                opts.view.onBeforeRender.call(opts.view, target, data.rows);
            }
            opts.view.render.call(opts.view, target, dc.body1, true, appendRows);
            opts.view.render.call(opts.view, target, dc.body2, false, appendRows);
            if (opts.view.onAfterRender) {
                opts.view.onAfterRender.call(opts.view, target);
            }
            if (appendRows && addition !== 0) {
                $.extend(state.data.HEAD, head);
                //刷新head
                return loadData(target, state.data, 0);
            }
            clearHeadFilter(target);
            // 将表头过滤清理掉
            opts.onLoadSuccess.call(target, data);
            var pager = $(target).datagrid("getPager");
            if (pager.length) {
                pager.pagination("refresh", {
                    time: head && head.RUN_TIMES ? head.RUN_TIMES : 0,
                    total: state.data.total,
                    pageNumber: opts.pageNumber
                });
                pager.prev("div.datagrid-row-detail").hide();
            }
            fixRowHeight(target);
            dc.body2.triggerHandler("scroll");
            selectRow();
            $(target).datagrid("autoSizeColumn");
            function selectRow() {
                if (opts.idField) {
                    for (var i = 0; i < data.rows.length; i++) {
                        var row = data.rows[i];
                        if (isSelected(row)) {
                            selectRecord(target, row[opts.idField]);
                        }
                    }
                }
                function isSelected(row) {
                    for (var i = 0; i < selectedRows.length; i++) {
                        if (selectedRows[i][opts.idField] == row[opts.idField]) {
                            selectedRows[i] = row;
                            return true;
                        }
                    }
                    return false;
                }
            }
            showStatisticsRow(target);
            return data;
        });
    }
    function getRowIndex(target, row) {
        var opts = $.data(target, "datagrid").options;
        var rows = $.data(target, "datagrid").data.rows;
        if (typeof row == "object") {
            return contain(rows, row);
        } else {
            for (var i = 0; i < rows.length; i++) {
                if (rows[i][opts.idField] == row) {
                    return i;
                }
            }
            return -1;
        }
    }
    function gridDataValueReplace(v) {
        if (null == v) {
            return "";
        }
        if (typeof v != "string") return v;
        v = v.replace(/</g, "&lt;");
        v = v.replace(/>/g, "&gt;");
        return v;
    }
    function gridDataValueReplaceReverse(v) {
        if (typeof v != "string") return v;
        v = v.replace(/&lt;/g, "<");
        v = v.replace(/&gt;/g, ">");
        return v;
    }
    function getSelections(target) {
        var opts = $.data(target, "datagrid").options;
        var data = $.data(target, "datagrid").data;
        if (opts.idField) {
            return $.data(target, "datagrid").selectedRows;
        } else {
            var rows = [];
            opts.finder.getTr(target, "", "selected", 2).each(function() {
                var index = parseInt($(this).attr("datagrid-row-index"));
                var rowData = data.rows[index];
                for (var i in rowData) {
                    rowData[i] = gridDataValueReplaceReverse(rowData[i]);
                }
                rows.push(rowData);
            });
            return rows;
        }
    }
    function getSelectRows(target) {
        var opts = $.data(target, "datagrid").options;
        var data = $.data(target, "datagrid").data;
        var rows = [];
        opts.finder.getTr(target, "", "selected", 2).each(function() {
            var index = parseInt($(this).attr("datagrid-row-index"));
            var rowData = data.rows[index];
            for (var i in rowData) {
                rowData[i] = gridDataValueReplaceReverse(rowData[i]);
            }
            rows.push(rowData);
        });
        return rows;
    }
    function getCheckedRows(target) {
        var d = $.data(target, "datagrid");
        var data = d.data;
        var dc = d.dc;
        var rows = [];
        dc.body2.find("input:checked").each(function() {
            var tr = $(this).closest("tr");
            var index = parseInt(tr.attr("datagrid-row-index"));
            var rowData = data.rows[index];
            for (var i in rowData) {
                rowData[i] = gridDataValueReplaceReverse(rowData[i]);
            }
            rows.push(rowData);
        });
        return rows;
    }
    function getUnCheckedRows(target) {
        var d = $.data(target, "datagrid");
        var data = d.data;
        var dc = d.dc;
        var rows = [];
        dc.body2.find("input").not("input:checked").each(function() {
            var tr = $(this).closest("tr");
            var index = parseInt(tr.attr("datagrid-row-index"));
            var rowData = data.rows[index];
            for (var i in rowData) {
                rowData[i] = gridDataValueReplaceReverse(rowData[i]);
            }
            rows.push(rowData);
        });
        return rows;
    }
    function selectRecord(target, field) {
        var opts = $.data(target, "datagrid").options;
        if (opts.idField) {
            var index = getRowIndex(target, field);
            if (index >= 0) {
                selectRow(target, index);
            }
        }
    }
    function getEdtDictText(col, fieldValue, row) {
        if (fieldValue && (fieldValue == "@" || fieldValue[0] == "@") && col.editor.options.atIsAll == true) {
            return "全部";
        }
        var orgCode = col.editor.options.dictOrg ? row[col.editor.options.dictOrg] : "";
        var dictData = col.editor.options.data;
        if (col.editor.options.extItems) {
            var extItems = $.isArray(col.editor.options.extItems) ? col.editor.options.extItems : eval("(" + col.editor.options.extItems + ")");
            dictData = extItems.concat(dictData);
        }
        fieldValue = $.trim(fieldValue);
        fieldValue = gridDataValueReplaceReverse(fieldValue);
        if (!dictData) return fieldValue;
        var dictValue = [];
        var valueField = col.editor.type == "combotree" ? col.editor.options.nodeId : col.editor.options.valueField;
        var textField = col.editor.type == "combotree" ? col.editor.options.nodeName : col.editor.options.textField;
        if (fieldValue != null && fieldValue != undefined && fieldValue != "") {
            var sep = ",";
            if (col.singleByte || col.editor && col.editor.options.singleByte) {
                sep = "";
            }
            var dictText = fieldValue.split(sep);
            dictText = $.grep(dictText, function(item) {
                if (item != " ") return true;
            });
            for (var m = 0; m < dictText.length; m++) {
                var index = -1;
                $.each(dictData, function(i, item) {
                    //conf 里面增加了 nodeDataType 配置项 如果为number ，则会将node里的id和pid先强制转换为number型，再转为string型
                    //@zhaozz 2015-11-18
                    if (col.editor && col.editor.options && col.editor.options.conf && "number" === col.editor.options.conf.nodeDataType) {
                        item[valueField] = Number(item[valueField]).toString();
                    }
                    if ($.trim(item[valueField]) == dictText[m] && (!orgCode || orgCode == item["dict_org"])) {
                        index = i;
                        return;
                    }
                });
                if (index == -1) {
                    dictValue.push(dictText[m]);
                } else {
                    var text = "";
                    if (textField.indexOf(",") != -1) {
                        var textSplit = textField.split(",");
                        text = dictData[index][textSplit[0]] + "-" + dictData[index][textSplit[1]];
                    } else {
                        text = dictData[index][textField];
                    }
                    dictValue.push(text);
                }
            }
        }
        return dictValue.length == 1 ? dictValue[0] : dictValue;
    }
    function getEdtCacheText(col, fieldValue) {
        var cacheData = cache.getData(col.editor.options.cache);
        fieldValue = $.trim(fieldValue);
        fieldValue = gridDataValueReplaceReverse(fieldValue);
        if (!cacheData) return fieldValue;
        var dictValue = [];
        var valueField = col.editor.type == "combotree" ? col.editor.options.nodeId : col.editor.options.valueField;
        var textField = col.editor.type == "combotree" ? col.editor.options.nodeName : col.editor.options.textField;
        if (fieldValue != null && fieldValue != undefined && fieldValue != "") {
            var sep = ",";
            if (col.singleByte || col.editor && col.editor.options.singleByte) {
                sep = "";
            }
            var dictText = fieldValue.split(sep);
            dictText = $.grep(dictText, function(item) {
                if (item != " ") return true;
            });
            for (var m = 0; m < dictText.length; m++) {
                var index = -1;
                $.each(cacheData, function(i, item) {
                    //conf 里面增加了 nodeDataType 配置项 如果为number ，则会将node里的id和pid先强制转换为number型，再转为string型
                    //@zhaozz 2015-11-18
                    if (col.editor && col.editor.options && col.editor.options.conf && "number" === col.editor.options.conf.nodeDataType) {
                        item[valueField] = Number(item[valueField]).toString();
                    }
                    if (item[valueField] == dictText[m]) {
                        index = i;
                        return;
                    }
                });
                if (index == -1) {
                    dictValue.push(dictText[m]);
                } else {
                    var text = "";
                    if (textField.indexOf(",") != -1) {
                        var textSplit = textField.split(",");
                        text = cacheData[index][textSplit[0]] + "-" + cacheData[index][textSplit[1]];
                    } else {
                        text = cacheData[index][textField];
                    }
                    dictValue.push(text);
                }
            }
        }
        return dictValue.length == 1 ? dictValue[0] : dictValue;
    }
    function validateDictText(col, arr) {
        var dictData = col.editor.options.data;
        var flag = false;
        if (!dictData) return flag;
        for (var n = 0; n < dictData.length; n++) {
            for (var m = 0; m < arr.length; m++) {
                if (dictData[n][col.editor.options.valueField] == arr[m]) {
                    flag = true;
                }
            }
        }
        return flag;
    }
    function getEdtDictValue(col, oldHtml) {
        var dictData = col.editor.options.data;
        var dictValue = [];
        var dictText = oldHtml.split(",");
        for (var n = 0; n < dictData.length; n++) {
            for (var m = 0; m < dictText.length; m++) {
                if (dictData[n][col.editor.options.textField] == dictText[m]) {
                    dictValue.push(dictData[n][col.editor.options.valueField]);
                }
            }
        }
        return dictValue.length == 1 ? dictValue[0] : dictValue;
    }
    function createEditor(target, index) {
        var opts = $.data(target, "datagrid").options;
        var tr = opts.finder.getTr(target, index);
        var selectRow = opts.finder.getRow(target, index);
        var insertFlag = false;
        //var selectRow = $(target).datagrid("getSelected");
        var insertedRows = $.data(target, "datagrid").insertedRows;
        for (var i = 0; i < insertedRows.length; i++) {
            if (insertedRows[i] == selectRow) {
                insertFlag = true;
            }
        }
        var uioptions = {};
        tr.children("td").each(function() {
            var cell = $(this).find("div.datagrid-cell");
            var field = $(this).attr("field");
            if (!field) {
                return;
            }
            var col = getColumnOption(target, field);
            var editFlag = col.edit_flag;
            if (insertFlag) {
                if (editFlag == "2") {
                    return;
                }
            } else {
                if (editFlag == "1" || editFlag == "2") {
                    return;
                }
            }
            if (col && col.editor && opts.onBeforeEditCell.call(target, index, field, selectRow, col)) {
                var type;
                var options = {
                    field: field
                };
                if (typeof col.editor == "string") {
                    type = col.editor;
                } else {
                    if (col.editor && col.editor.options) {
                        options = $.extend(options, col.editor.options);
                        if (options.req) {
                            options.req = eval(options.req);
                        }
                    }
                    if (col.editor && col.editor.type) {
                        type = col.editor.type === "text" ? "textinput" : col.editor.type;
                    }
                }
                if (selectRow && selectRow[field]) {
                    options.value = $.trim(selectRow[field]) || options.defaultValue;
                } else {
                    options.value = options.defaultValue;
                }
                var editor = opts.editors[type];
                if ("checkbox" != type) {
                    var oldHtml = cell.html();
                    if (col.edt_dict) {
                        oldHtml = getEdtDictValue(col, oldHtml);
                    }
                    var input = "";
                    if (!editor) {
                        uioptions[field] = options;
                        if (type == "textarea") {
                            input = "<textarea name='" + field + "' class='kui-" + type + " form-input'></textarea>";
                        } else {
                            input = "<input type=" + (type === "password" ? type : "text") + " name='" + field + "' class='kui-" + type + "'></input>";
                            type = type === "text" ? "textinput" : type;
                        }
                    }
                    var width = cell.outerWidth();
                    cell.addClass("datagrid-editable");
                    cell._outerWidth(width);
                    cell.html('<table border="0" cellspacing="0" cellpadding="1"><tr><td>' + input + "</td></tr></table>");
                    cell.children("table").attr("align", col.align);
                    cell.children("table").bind("click dblclick contextmenu", function(e) {
                        e.stopPropagation();
                    });
                    cell.find("input").width(width).val(oldHtml);
                    var editOptions = {
                        field: field,
                        type: type,
                        oldHtml: oldHtml,
                        filter: "combobox" === type || "combogrid" === type || "combotree" === type || "datebox" === type || "datetimebox" === type ? "input[comboname='" + field + "']" : "input[name='" + field + "']"
                    };
                    if (editor) {
                        editOptions.actions = editor;
                        editOptions.target = editor.init(cell.find("td"), options);
                    } else {
                        editOptions.target = cell.find("td");
                    }
                    $.data(cell[0], "datagrid.editor", editOptions);
                }
            }
        });
        $.data(tr[0], "uioptions", uioptions);
        $.parser.director(tr).done(function() {
            fixRowHeight(target, index, true);
            resizeEditor(target);
        });
    }
    function validateRow(target, index) {
        var tr = $.data(target, "datagrid").options.finder.getTr(target, index);
        if (!tr.hasClass("datagrid-row-editing")) {
            return true;
        }
        var vbox = tr.find(".validatebox-text");
        var money = tr.find(".easyui-moneybox");
        if (money) {
            money.val(money.next().val());
        }
        vbox.validatebox("validate");
        vbox.trigger("mouseleave");
        var validatebox = tr.find(".validatebox-invalid");
        return validatebox.length == 0;
    }
    function getChanges(target, operate) {
        var insertedRows = $.data(target, "datagrid").insertedRows;
        var deletedRows = $.data(target, "datagrid").deletedRows;
        var updatedRows = $.data(target, "datagrid").updatedRows;
        if (!operate) {
            var rows = [];
            rows = rows.concat(insertedRows);
            rows = rows.concat(deletedRows);
            rows = rows.concat(updatedRows);
            return rows;
        } else {
            if (operate == "inserted") {
                return insertedRows;
            } else {
                if (operate == "deleted") {
                    return deletedRows;
                } else {
                    if (operate == "updated") {
                        return updatedRows;
                    }
                }
            }
        }
        return [];
    }
    function insertRow(target, rows) {
        var index = rows.index;
        var row = rows.row;
        var view = $.data(target, "datagrid").options.view;
        var insertedRows = $.data(target, "datagrid").insertedRows;
        var opts = $.data(target, "datagrid").options;
        var dftValues = {};
        if (opts.columns.length > 0) {
            var cls = opts.columns[0].length;
            for (var i = 0; i < cls; i++) {
                var col = opts.columns[0][i];
                if (typeof col.dft_value != "undefined") {
                    if (col.dft_value.indexOf(",") != -1) {
                        col.dft_value = col.dft_value.split(",");
                    }
                    dftValues[col.field] = col.dft_value;
                } else {
                    dftValues[col.field] = "";
                }
            }
        }
        if (!$.isEmptyObject(row)) {
            for (var key in row) {
                for (var ky in dftValues) {
                    if (key == ky) {
                        dftValues[ky] = row[key];
                    }
                }
            }
        }
        row = $.extend({}, dftValues);
        view.insertRow.call(view, target, index, row);
        insertedRows.push(row);
    }
    function acceptChanges(target) {
        var data = $.data(target, "datagrid").data;
        var ok = true;
        for (var i = 0, len = data.rows.length; i < len; i++) {
            if (validateRow(target, i)) {} else {
                ok = false;
            }
        }
        if (ok) {
            saveOriginalRows(target);
        }
    }
    function rejectChanges(target) {
        var opts = $.data(target, "datagrid").options;
        var originalRows = $.data(target, "datagrid").originalRows;
        var insertedRows = $.data(target, "datagrid").insertedRows;
        var deletedRows = $.data(target, "datagrid").deletedRows;
        var selectedRows = $.data(target, "datagrid").selectedRows;
        var data = $.data(target, "datagrid").data;
        for (var i = 0; i < data.rows.length; i++) {
            deleteRow(target, i, true);
        }
        var fields = [];
        for (var i = 0; i < selectedRows.length; i++) {
            fields.push(selectedRows[i][opts.idField]);
        }
        selectedRows.splice(0, selectedRows.length);
        data.total = Number(data.total) + deletedRows.length - insertedRows.length;
        data.rows = originalRows;
        loadData(target, data);
        for (var i = 0; i < fields.length; i++) {
            selectRecord(target, fields[i]);
        }
        saveOriginalRows(target);
    }
    //请求数据
    function request(target, queryParams, addition, reloadCallback) {
        var grid = $.data(target, "datagrid");
        var opts = grid.options;
        // 每次请求前将页面设置为第一页，防止当前为第二页时再查询时还是显示第二页的BUG
        var pager = $(target).datagrid("getPager");
        if (pager.length) {
            var pagerOptions = pager.pagination("options");
            pagerOptions.pageNumber = 1;
        }
        // 防止重复不断提交请求
        if (opts.fallParas[0].enable && opts.requesting) {
            return;
        }
        opts.requesting = true;
        var dc = $.data(target, "datagrid").dc;
        if (queryParams) {
            opts.queryParams = $.extend(opts.queryParams, queryParams);
        }
        var params = $.extend({}, opts.queryParams);
        if (opts.pagination) {
            $.extend(params, {
                PAGE_NUM: opts.pageNumber,
                PAGE_SIZE: opts.pageSize
            });
        }
        if (opts.sortName) {
            $.extend(params, {
                sort: opts.sortName,
                order: opts.sortOrder
            });
        }
        //没有传查询条件的时候，才去重新获取form表单的值。20160524
        var form = dc.queryForm.find("form");
        if (!form.form("validate")) {
            opts.requesting = false;
            //避免queryParam校验不通过时，把下一页按钮隐藏掉了。 by liucx  2016 10 24
            $.isFunction(reloadCallback) && reloadCallback();
            return false;
        }
        var p = form.form("getData");
        params = $.extend({}, params, p);
        $.when($.isFunction(opts.onBeforeLoad) ? opts.onBeforeLoad.call(target, params) : true).always(function() {
            opts.requesting = false;
        }).then(function(flag) {
            if (flag === false) {
                return;
            }
            if (opts.req) {
                var req;
                if (typeof opts.req == "string") req = eval("(" + opts.req + ")"); else req = opts.req || {};
                for (var i = 0; i < req.length; i++) {
                    var servicesObj = req[i];
                    if (!req[i].service && req[i].bex_codes) {
                        servicesObj["service"] = "P9999999";
                    }
                    params = $.extend(params, servicesObj);
                }
            }
            var data = grid.data;
            if (addition && data.rows.length === 0) {
                addition = false;
            }
            var fallParas = opts.fallParas[0];
            if (fallParas.enable) {
                params[fallParas.recName] = fallParas.recNum + (addition ? 1 * data.total : 0);
                params[fallParas.cntName] = 1 * fallParas.cntNum;
                if (addition && fallParas.keyField) {
                    var keyArr = fallParas.keyField.split(","), valueArr = [];
                    if (fallParas.valueField) {
                        valueArr = fallParas.valueField.split(",");
                    } else {
                        for (var i = 0; i < keyArr.length; i++) {
                            valueArr.push("K_" + keyArr[i]);
                        }
                    }
                    if (keyArr.length != valueArr.length) {
                        alert("分页参数配置错误，请检查view配置！");
                        return;
                    }
                    var lastRow = grid.originalRows[grid.originalRows.length - 1];
                    if (lastRow) {
                        for (var i = 0; i < valueArr.length; i++) {
                            params[valueArr[i]] = lastRow[keyArr[i]];
                        }
                    }
                }
            }
            if (opts.pageParamete) {
                params[opts.pageParamete.recName] = params["PAGE_SIZE"];
                params[opts.pageParamete.cntName] = params["PAGE_NUM"];
            }
            load();
        });
        function load() {
            $(target).datagrid("loading");
            var result = false;
            if (opts.req.length > 0 || opts.url) {
                result = opts.loader.call(target, params, function(data) {
                    opts.reportParams = params;
                    // 用于报表导出
                    opts.searchParam = getSearchParam(target);
                    // 用于报表导出的查询条件
                    setTimeout(function() {
                        $(target).datagrid("loaded");
                    }, 0);
                    //增加addition参数
                    loadData(target, data, addition).then(function(d) {
                        //当分页后当前页只有一条数据将其删除时需后退一页。
                        if (d.rows.length == 0 && opts.pageNumber > 1) {
                            opts.pageNumber--;
                            opts.requesting = false;
                            $(target).datagrid("reload");
                            return false;
                        }
                        setTimeout(function() {
                            saveOriginalRows(target);
                            opts.requesting = false;
                        }, 0);
                        if (opts.backAllMode && !fallParas.last) {
                            opts.requesting = false;
                            request(target, null, true);
                            setTimeout(function() {
                                $(target).datagrid("loaded");
                            }, 0);
                        } else {
                            opts.backAllMode = false;
                        }
                        $.isFunction(reloadCallback) && reloadCallback();
                    });
                }, function() {
                    setTimeout(function() {
                        $(target).datagrid("loaded");
                    }, 0);
                    opts.onLoadError.apply(target, arguments);
                    opts.requesting = false;
                    // ajax请求完成之后设置为可以进行新的请求
                    opts.backAllMode = false;
                });
            }
            if (result == false) {
                $(target).datagrid("loaded");
            }
        }
    }
    function mergeCells(target, param) {
        var opts = $.data(target, "datagrid").options;
        var rows = $.data(target, "datagrid").data.rows;
        param.rowspan = param.rowspan || 1;
        param.colspan = param.colspan || 1;
        if (param.index < 0 || param.index >= rows.length) {
            return;
        }
        if (param.rowspan == 1 && param.colspan == 1) {
            return;
        }
        var colData = rows[param.index][param.field];
        var tr = opts.finder.getTr(target, param.index);
        var td = tr.find('td[field="' + param.field + '"]');
        td.attr("rowspan", param.rowspan).attr("colspan", param.colspan);
        td.addClass("datagrid-td-merged");
        for (var i = 1; i < param.colspan; i++) {
            td = td.next();
            td.hide();
            rows[param.index][td.attr("field")] = colData;
        }
        for (var i = 1; i < param.rowspan; i++) {
            tr = tr.next();
            var td = tr.find('td[field="' + param.field + '"]').hide();
            rows[param.index + i][td.attr("field")] = colData;
            for (var j = 1; j < param.colspan; j++) {
                td = td.next();
                td.hide();
                rows[param.index + i][td.attr("field")] = colData;
            }
        }
        merge(target);
    }
    /**
	 * 解析参数中的列的参数，用于列拖拽，列隐藏和列锁定。
	 */
    function parseColumns(target) {
        var opts = $.data(target, "datagrid").options;
        if (opts.fallParas && opts.fallParas[0].enable) {
            // opts.pageSize = 999999999;
            opts.fallParas[0] = $.extend({}, $.fn.datagrid.defaults.fallParas[0], opts.fallParas[0]);
        }
        //解析opts.gridMenu参数，交将解析出来的结果保存到opts中
        if (opts.gridMenu) {
            strs = [ "fileExport", "freezeColumns", "hideColumns", "hideCols" ];
            for (var i = 0, len = strs.length; i < len; i++) {
                var reg = new RegExp(strs[i]);
                if (reg.test(opts.gridMenu)) {
                    opts["is" + strs[i].replace(/^[a-z]/, function(tempStr) {
                        return tempStr.toUpperCase();
                    })] = true;
                }
            }
        }
        opts.frozenColumns = opts.frozenColumns || [ [] ];
        opts.frozenColumns[0] = opts.frozenColumns[0] || [];
        //保证lsKey的独一无二性非常重要，利用当前URL、操作员和datagrid的打印标题MD5之后做为KEY。
        opts.lsKey = getReportTitle(target) + (datagridId + 1);
        // 每次初始化时从localStorage中获取保存的列宽，然后再重新赋值。
        var columns = opts.columns[0];
        if (!columns) return [];
        // --------------------------------------------
        var widthLs = $.ls(opts.lsKey + "_width");
        if (widthLs) {
            if (!$.isArray(widthLs) || widthLs.length != columns.length) {
                $.ls(opts.lsKey, null);
            } else {
                for (var i = 0, len = widthLs.length; i < len; i++) {
                    columns[i].width = widthLs[i];
                }
            }
        }
        //-----------------------------------------------------------------------
        for (var i = 0, len = columns.length; i < len; i++) {
            columns[i].sortable = typeof columns[i].sortable == "undefined" ? true : columns[i].sortable;
        }
        // 是否有“列拖拽”、“隐藏列”或“冻结列”功能，并查看本地是否存储着列的顺序的信息，有则将保存着的列的顺序的信息还原
        var gridLs = $.ls(opts.lsKey);
        var hideArray = new Array();
        if ((opts.isMoveColumns || opts.isHideColumns || opts.isFreezeColumns) && opts.columns && opts.columns[0]) {
            var columns = opts.columns[0];
            if (gridLs && gridLs.length == columns.length) {
                var newColumns = new Array();
                var frozenColumns = new Array();
                for (var i = 0, len = gridLs.length; i < len; i++) {
                    if (gridLs[i] < 0) {
                        newColumns.push(columns[gridLs[i] + len]);
                        hideArray.push(i);
                    } else if (gridLs[i] >= len) {
                        frozenColumns.push(columns[gridLs[i] - len]);
                    } else {
                        newColumns.push(columns[gridLs[i]]);
                    }
                }
                opts.columns[0] = newColumns;
                opts.frozenColumns[0] = frozenColumns;
            } else {
                $.ls(opts.lsKey, null);
            }
        }
        //检查列中有哪是需要固定的列
        if (!gridLs && opts.columns && opts.columns[0]) {
            gridLs = [];
            var columns = opts.columns[0];
            var frozenColumns = new Array();
            var k = 0;
            // 如果在LocalStorage中保存着有固定列的信息，则忽略掉列的配置项
            for (var len = columns.length, i = len - 1; i >= 0; i--) {
                if (columns[i] && columns[i].frozen && columns[i].frozen + "" !== "false") {
                    frozenColumns.unshift(columns.splice(i, 1)[0]);
                    gridLs.splice(gridLs.length - k++, 0, i + len);
                } else {
                    gridLs.unshift(i);
                }
            }
            opts.frozenColumns[0] = frozenColumns;
        }
        return hideArray;
    }
    //给datagrid组件添加功能“列拖拽”
    function addMoveColumn(dom) {
        if (!dom) {
            return;
        }
        var dc = $.data(dom, "datagrid").dc;
        var $head1Tr = dc.header1.find("tr");
        var $head2Tr = dc.header2.find("tr");
        var $body2 = dc.body2;
        var $body = $("body");
        var onselectstart = $body[0].onselectstart;
        var $curDiv = null;
        var isDown = false;
        var from = 0;
        var oldX = 0;
        var left = 0;
        var tdLefts = [];
        $body.mousemove(function(e) {
            if (isDown) {
                $head2Tr.find(".floatTd").css("left", left + (e.pageX - oldX) + "px");
                var nowLeft = Math.max(e.pageX, tdLefts[0]);
                for (var i = 1, len = tdLefts.length; i < len; i++) {
                    if (nowLeft < tdLefts[i]) {
                        var $tempDiv;
                        if (nowLeft >= tdLefts[i] - (tdLefts[i] - tdLefts[i - 1]) / 2) {
                            $tempDiv = $head2Tr.find("td:eq(" + (i - 1) + ") div.datagrid-cell").removeClass("leftInsert").addClass("rightInsert");
                        } else {
                            $tempDiv = $head2Tr.find("td:eq(" + (i - 1) + ") div.datagrid-cell").removeClass("rightInsert").addClass("leftInsert");
                        }
                        if ($tempDiv[0] != $curDiv[0]) {
                            $curDiv.removeClass("leftInsert").removeClass("rightInsert");
                            $curDiv = $tempDiv;
                        }
                        break;
                    }
                }
            }
        }).bind("mouseup mouseleave", function(e) {
            if (isDown) {
                isDown = false;
                $body[0].onselectstart = onselectstart;
                $body.removeClass("noneSelect");
                $head2Tr.find(".floatTd").remove();
                $curDiv.removeClass("leftInsert").removeClass("rightInsert");
                var nowLeft = Math.max(e.pageX, tdLefts[0]);
                for (var i = 1, len = tdLefts.length; i < len; i++) {
                    if (nowLeft < tdLefts[i]) {
                        if (nowLeft >= tdLefts[i] - (tdLefts[i] - tdLefts[i - 1]) / 2) {
                            moveColumn(dom, from, i);
                        } else {
                            moveColumn(dom, from, i - 1);
                        }
                        return;
                    }
                }
                moveColumn(dom, from, len - 1);
            }
        });
        $head1Tr.find("td").add($head2Tr.find("td")).mousedown(function(e) {
            if ($(this).parents("div.datagrid-view2:first").length < 1) {
                return true;
            }
            if ($(this).find(".datagrid-cell:first").css("cursor") != "e-resize" && e.ctrlKey) {
                isDown = true;
                from = $(this).index();
                // 清除拖动时会默认选择
                $body[0].onselectstart = getFalse;
                // 适用于IE浏览器
                $body.addClass("noneSelect");
                oldX = e.pageX;
                left = $(this).offset().left;
                var baseLeft = $head2Tr.parent().offset().left;
                var scrollLeft = $body2.scrollLeft();
                left = left - baseLeft - scrollLeft;
                tdLefts = [];
                $(this).parent().find("td").each(function() {
                    tdLefts.push($(this).offset().left);
                }).filter(":last").each(function() {
                    tdLefts.push($(this).offset().left + $(this).width());
                });
                $curDiv = $(this).parent().append($(this).clone().attr("class", "floatTd").css("left", left + "px")).find("div.datagrid-cell");
            }
        });
        function getFalse() {
            return false;
        }
    }
    //将datagrid控件中的某一列（从0开始计算）移动到某一位置（从0开始计算）同，如果有隐藏列，不会对该函数造成影响
    function moveColumn(dom, from, to) {
        if (!dom) {
            return;
        }
        var dc = $.data(dom, "datagrid").dc;
        var $view2 = dc.view2;
        var len = $view2.find("tr:first td").length;
        if (from < 0 || from > len - 1 || to < 0 || to > len || from == to || from == to - 1) {
            return;
        }
        var $tr = $view2.find("tr");
        var func;
        var dest = to;
        if (to == len) {
            func = "insertAfter";
            dest = len - 1;
        } else {
            func = "insertBefore";
        }
        $tr.each(function(i, d) {
            var $from = $(this).find("td:eq(" + from + ")");
            var $to = $(this).find("td:eq(" + dest + ")");
            $from[func]($to);
        });
        // 以下代码改变列的顺序，即改变参数columns的顺序
        var opts = $.data(dom, "datagrid").options;
        var columns = opts.columns[0];
        var frozenColumns = opts.frozenColumns[0];
        columns.splice(to, 0, columns[from]);
        columns.splice(from > to ? from + 1 : from, 1);
        // 以下代码将列的顺序保存起来
        var gridLs = $.ls(opts.lsKey);
        if (!gridLs || !$.isArray(gridLs) || gridLs.length != columns.length + frozenColumns.length) {
            gridLs = [];
            for (var i = 0, len = columns.length + frozenColumns.length; i < len; i++) {
                gridLs[i] = i;
            }
        }
        gridLs.splice(to, 0, gridLs[from]);
        gridLs.splice(from > to ? from + 1 : from, 1);
        $.ls(opts.lsKey, gridLs);
    }
    //给datagrid对象添加功能“隐藏列”
    function addHideColumns(dom) {
        if (!dom || !$.data(dom, "datagrid")) {
            return;
        }
        var columns = $.data(dom, "datagrid").options.columns[0];
        var html = [];
        html.push('<div class="hide_columns"><div style="line-height:20px;height:20px;"><a name="selectAll" style="margin:0 5px;" href="javascript:void(0)">全选</a><a href="javascript:void(0)" style="margin:0 5px;">全不选</a><a href="javascript:void(0)" style="margin:0 5px;">反选</a></div><div style="height:160px;overflow:auto;margin-bottom:10px;"><ul style="list-style:none;margin:0;padding:0;">');
        for (var i = 0, len = columns.length; i < len; i++) {
            html.push("<li ");
            if (columns[i].hidden == "true" || columns[i].listHidden == "true") {
                // 对于在view中就配置了隐藏属性的列来说，不允许对其操作
                html.push(' style="display:none"');
            }
            html.push('><input type="checkbox"');
            if (/ hide/.test(columns[i].cellClass)) {
                html.push(' checked="checked"');
            }
            html.push("><span>" + columns[i].title + "</span></li>");
        }
        html.push('</ul></div><a name="confirm" style="margin:0 10px 0 10px;" href="javascript:void(0)">确定</a>');
        html.push('<a name="cancel" class="kui-linkbutton">取消</a>');
        html.push("</div>");
        var $window = $(html.join("")).window({
            title: "请选择要隐藏的列",
            width: 200,
            height: 250,
            modal: true,
            minimizable: false,
            maximizable: false,
            collapsible: false
        });
        $window.find("a[name=selectAll]").click(function() {
            $window.find("ul input").each(function() {
                this.checked = true;
            });
        }).next().click(function() {
            $window.find("ul input").each(function() {
                this.checked = false;
            });
        }).next().click(function() {
            $window.find("ul input").each(function() {
                this.checked = !this.checked;
            });
        });
        $window.find("a[name=confirm]").linkbutton({}).click(function() {
            var hideArray = [];
            $window.find("input:checked").each(function(i, d) {
                hideArray.push($(d).parent().index());
            });
            hideColumns(dom, hideArray);
            $window.window("close");
        });
        $window.find("a[name=cancel]").linkbutton({}).click(function() {
            $window.window("close");
        });
        $window.window("open");
    }
    //隐藏datagrid中的某几列，将这几列隐藏之后，还会将其它列都设为显示
    function hideColumns(dom, hideArray) {
        if (!dom || !$.isArray(hideArray)) {
            return;
        }
        var dc = $.data(dom, "datagrid").dc;
        var $h2 = dc.header2;
        var $body2 = dc.body2;
        var $htr = $h2.find("tr");
        var $btr = $body2.find("tr");
        var opts = $.data(dom, "datagrid").options;
        var columns = opts.columns[0];
        var frozenColumns = opts.frozenColumns[0];
        $htr.find("td>div").show();
        $btr.find("td>div[class*=hide]").removeClass("hide");
        for (var i = 0, len = columns.length; i < len; i++) {
            if (columns[i].checkbox) {
                continue;
            }
            columns[i].cellClass = columns[i].cellClass.replace(/ hide/g, "");
        }
        for (var i = 0, len = hideArray.length; i < len; i++) {
            var index = hideArray[i];
            columns[index].cellClass += " hide";
            $htr.find("td:eq(" + index + ")>div").hide();
            $btr.find("td:eq(" + index + ")>div").addClass("hide");
        }
        // 将隐藏的列的数据保存在本地存储中，需要隐藏的列，用列序号减去列的总长度用来表示该列是隐藏的
        var gridLs = $.ls(opts.lsKey);
        if (!gridLs || !$.isArray(gridLs) || gridLs.length != columns.length + frozenColumns.length) {
            gridLs = [];
            for (var i = 0, len = columns.length + frozenColumns.length; i < len; i++) {
                gridLs[i] = i;
            }
        }
        var columnsLen = gridLs.length;
        for (var i = 0, len = gridLs.length; i < len; i++) {
            if (gridLs[i] < 0) {
                gridLs[i] += columnsLen;
            }
        }
        for (var i = 0, len = hideArray.length; i < len; i++) {
            if (gridLs[hideArray[i]] >= 0) {
                gridLs[hideArray[i]] -= columnsLen;
            }
        }
        $.ls(opts.lsKey, gridLs);
    }
    //打开固定列的Window窗口
    function freezeColumnsWindow(dom) {
        if (!dom || !$.data(dom, "datagrid")) {
            return;
        }
        var opts = $.data(dom, "datagrid").options;
        var columns = opts.columns[0];
        var frozenColumns = opts.frozenColumns[0];
        var html = [];
        html.push('<div class="hide_columns"><div style="line-height:20px;height:20px;"><a name="selectAll" style="margin:0 5px;" href="javascript:void(0)">全选</a><a href="javascript:void(0)" style="margin:0 5px;">全不选</a><a href="javascript:void(0)" style="margin:0 5px;">反选</a></div><div style="height:160px;overflow:auto;margin-bottom:10px;"><ul style="list-style:none;margin:0;padding:0;">');
        for (var i = 0, len = frozenColumns.length; i < len; i++) {
            html.push("<li");
            if (frozenColumns[i].hidden) {
                // 对于在view中就配置了隐藏属性的列来说，不允许对其操作
                html.push(' style="display:none"');
            }
            html.push('><input type="checkbox" field="' + frozenColumns[i].field + '"');
            html.push(' checked="checked"');
            html.push("><span>" + frozenColumns[i].title + "</span></li>");
        }
        for (var i = 0, len = columns.length; i < len; i++) {
            if (!/ hide/.test(columns[i].cellClass)) {
                html.push("<li");
                if (columns[i].hidden == "true" || columns[i].listHidden == "true") {
                    // 对于在view中就配置了隐藏属性的列来说，不允许对其操作
                    html.push(' style="display:none"');
                }
                html.push('><input type="checkbox" field="' + columns[i].field + '"');
                html.push("><span>" + columns[i].title + "</span></li>");
            }
        }
        html.push('</ul></div><a name="confirm" style="margin:0 10px 0 10px;" href="javascript:void(0)">确定</a>');
        html.push('<a name="cancel" class="kui-linkbutton">取消</a>');
        html.push("</div>");
        var $window = $(html.join("")).window({
            title: "请选择要锁定的列",
            width: 200,
            height: 250,
            modal: true,
            minimizable: false,
            maximizable: false,
            collapsible: false
        });
        $window.find("a[name=selectAll]").click(function() {
            $window.find("ul input").each(function() {
                this.checked = true;
            });
        }).next().click(function() {
            $window.find("ul input").each(function() {
                this.checked = false;
            });
        }).next().click(function() {
            $window.find("ul input").each(function() {
                this.checked = !this.checked;
            });
        });
        $window.find("a[name=confirm]").linkbutton({}).click(function() {
            var freezeArray = [];
            $window.find("input:checked").each(function(i, d) {
                freezeArray.push($(d).attr("field"));
            });
            if (freezeColumns(dom, freezeArray)) {
                $window.window("close");
            }
        });
        $window.find("a[name=cancel]").linkbutton({}).click(function() {
            $window.window("close");
        });
        $window.window("open");
    }
    //冻结列
    function freezeColumns(dom, freezeArray) {
        if (!dom || !$.isArray(freezeArray)) {
            return;
        }
        var pager = $(dom).datagrid("getPager");
        var data = $.data(dom, "datagrid");
        var dc = data.dc;
        var opts = data.options;
        var originalRows = data.originalRows;
        var $h1 = dc.header1;
        var $h2 = dc.header2;
        var columns = opts.columns[0];
        var frozenColumns = opts.frozenColumns[0];
        var totalLen = columns.length + frozenColumns.length;
        var gridLs = $.ls(opts.lsKey);
        if (!gridLs || !$.isArray(gridLs) || gridLs.length != totalLen) {
            gridLs = [];
            for (var i = 0, len = columns.length; i < len; i++) {
                gridLs[i] = i;
            }
            for (var j = 0, len = frozenColumns.length; j < len; j++) {
                gridLs[i + j] = i + j + totalLen;
            }
        }
        var totalWidth = dc.view.width() * .6;
        // 所有固定列的宽度之和不能超过表格宽度的60%
        var originalColumns = frozenColumns.concat(columns);
        var totalWidthTemp = 0;
        for (var i = 0, len = freezeArray.length; i < len; i++) {
            for (var j = 0, lenJ = originalColumns.length; j < lenJ; j++) {
                if (freezeArray[i] == originalColumns[j].field) {
                    totalWidthTemp += parseInt(originalColumns[j].width);
                }
            }
        }
        if (totalWidthTemp > totalWidth) {
            alert("所有固定列宽度之和不能超过表格宽度的60%。");
            return false;
        }
        for (var len = frozenColumns.length, i = len - 1, k = 0; i >= 0; i--) {
            var field = frozenColumns[i].field;
            if (notInArray(field, freezeArray)) {
                columns.unshift(frozenColumns.splice(i, 1)[0]);
                gridLs.unshift(gridLs.splice(totalLen - (len - i - k++), 1)[0] - totalLen);
                $h1.find("tr td[field=" + field + "]").prependTo($h2.find("tr"));
            }
        }
        for (var i = 0, len = freezeArray.length; i < len; i++) {
            if (notInArrayObject(freezeArray[i], frozenColumns)) {
                for (var j = 0, lenJ = columns.length; j < lenJ; j++) {
                    if (columns[j].field == freezeArray[i]) {
                        frozenColumns.push(columns.splice(j, 1)[0]);
                        var value = gridLs.splice(j, 1)[0];
                        if (value < 0) {
                            value += 2 * totalLen;
                        } else if (value < totalLen) {
                            value += totalLen;
                        }
                        gridLs.push(value);
                        $h2.find("tr td[field=" + freezeArray[i] + "]").appendTo($h1.find("tr"));
                        break;
                    }
                }
            }
        }
        $.ls(opts.lsKey, gridLs);
        function notInArray(element, array) {
            for (var i = 0, len = array.length; i < len; i++) {
                if (element == array[i]) {
                    return false;
                }
            }
            return true;
        }
        function notInArrayObject(element, array) {
            for (var i = 0, len = array.length; i < len; i++) {
                if (element == array[i].field) {
                    return false;
                }
            }
            return true;
        }
        if (originalRows && originalRows.length > 0) {
            $(dom).datagrid("loadData", data.data);
        }
        resize(dom);
        return true;
    }
    //列的顺序或者隐藏属性或者固定属性改变之后，再获取到原始的列的顺序
    function getOriginalColumns(target, ignoreHidden) {
        var opts = $.data(target, "datagrid").options;
        var gridLs = $.ls(opts.lsKey);
        var columns = opts.columns[0];
        var frozenColumns = opts.frozenColumns[0] || [];
        var originalColumns;
        if (gridLs && $.isArray(gridLs) && columns && frozenColumns && gridLs.length == columns.length + frozenColumns.length) {
            originalColumns = [];
            var totalLen = gridLs.length;
            var columnsLen = columns.length;
            for (var i = 0; i < totalLen; i++) {
                if (gridLs[i] < 0) {
                    gridLs[i] += totalLen;
                }
            }
            for (var i = 0; i < totalLen; i++) {
                for (var j = 0; j < totalLen; j++) {
                    if (i == gridLs[j]) {
                        originalColumns.push(columns[j]);
                        break;
                    } else if (i == gridLs[j] - totalLen) {
                        originalColumns.push(frozenColumns[j - columnsLen]);
                        break;
                    }
                }
            }
        } else {
            originalColumns = columns.concat(frozenColumns);
        }
        if (ignoreHidden) {
            for (var i = 0, len = originalColumns.length; i < len; i++) {
                if (/ hide/.test(originalColumns[i].cellClass) || originalColumns[i].hidden || !!originalColumns[i].listHidden) {
                    originalColumns.splice(i, 1);
                    i--;
                    len--;
                }
            }
        }
        return originalColumns;
    }
    //用于打开“隐藏列”功能的对话框，传给工具栏的回调函数
    function onHideColumns(target) {
        addHideColumns(target);
    }
    //用于打开“隐藏搜索条件”功能的对话框，传给工具栏的回调函数
    function onHideCols(target) {
        var dc = $.data(target, "datagrid").dc;
        var $queryForm = dc.queryForm.find("form");
        $queryForm.form("hideColsWindow", function() {
            resize(target);
        });
    }
    //用于打开“固定列”功能的对话框，传给工具栏的回调函数
    function onFreezeColumns(target) {
        freezeColumnsWindow(target);
    }
    //用于工具栏菜单按钮点击“恢复默认”时的回调函数
    function onRestoreDefault(target) {
        var grid = $.data(target, "datagrid");
        $.ls(grid.options.lsKey, null);
        $.ls(grid.options.lsKey + "_width", null);
        var $queryForm = grid.dc.queryForm;
        var $form = $queryForm && /form/i.test($queryForm[0].tagName) ? $queryForm : $queryForm.find("form");
        var data = $form.data();
        if (data && data.form) {
            $form.form("restoreDefault");
        }
        window.location.reload(false);
    }
    //从参数中获取searchParam传给后台生成pdf文件的操作
    function getSearchParam(target) {
        var grid = $.data(target, "datagrid");
        var $queryForm = grid.dc.queryForm || $();
        var searchParam = {};
        $queryForm.find("input:visible").each(function(i, d) {
            var $this = $(this);
            var name, value = $.trim($this.val());
            if (value && value != "-请选择-" && $.data(this, "validatebox") && $.data(this, "validatebox").options.showWhenExport) {
                name = $this.closest("div").children(":first").text();
                if (name) {
                    name = name.replace(/[:：]$/, "");
                    searchParam[name] = value;
                }
            }
        });
        return searchParam;
    }
    //向服务器提交生成下载文件或导出的函数
    function submitReport(target, param) {
        var grid = $.data(target, "datagrid");
        if (grid.dc.reportDialog) {
            grid.dc.reportDialog.report(param);
        } else {
            grid.dc.reportDialog = $("<div></div>").report(param);
        }
    }
    function getReportTitle(target) {
        var data = $.data(target, "datagrid");
        var opts = data.options;
        var title = opts.reportTitle || opts.name || opts.title;
        var $tabs = data.dc.view.parents("div.kui-tabs:first");
        if ($tabs.length > 0) {
            if ($tabs.data("tabs")) {
                title = title || $tabs.tabs("getSelected").panel("options").title;
            } else {
                title = title || data.dc.view.parents("div.panel:first").parent().attr("title");
            }
        }
        return title;
    }
    //用于工具栏点击导出按钮时的回调函数
    function onFileExport(target, type, exportParam, fileType, showDialog) {
        var isPrint = type == "print";
        var data = $.data(target, "datagrid");
        var opts = data.options;
        var reportParams = opts.reportParams;
        if (exportParam && exportParam.Datas) {
            reportParams = {};
        }
        if (!reportParams) {
            alert("必须先进行一次查询！");
            return;
        }
        if (!data.data.rows) {
            alert("没有数据不必要进行该操作！");
            return;
        }
        var searchParam = opts.searchParam;
        var title = getReportTitle(target);
        var fallParas = opts.fallParas[0];
        var pageInfo;
        if (fallParas.enable) {
            var endRowNum = 0;
            if (fallParas.recNum == 0) {
                endRowNum = fallParas.recNum + data.data.rows.length;
            } else {
                endRowNum = fallParas.recNum + data.data.rows.length - 1;
            }
            pageInfo = {
                pageNum: 1,
                totalRowNum: 1e5,
                totalPageNum: 1e3,
                pageSize: fallParas.cntNum,
                startRowNum: fallParas.recNum,
                endRowNum: endRowNum < fallParas.cntNum ? fallParas.cntNum : endRowNum
            };
        } else if (opts.remotePagination) {
            var pager = $(target).reportgrid("getPager");
            var pagerOptions = pager.pagination("options");
            pageInfo = {
                pageNum: 1,
                totalRowNum: 1e5,
                totalPageNum: 1e3,
                noPagination: true,
                remotePagination: true,
                pageSize: pagerOptions.pageSize,
                pageParamete: opts.pageParamete,
                startRowNum: pagerOptions.pageSize * (pagerOptions.pageNumber - 1),
                endRowNum: pagerOptions.pageSize * pagerOptions.pageNumber
            };
        } else {
            var pager = $(target).datagrid("getPager");
            var pagerOptions = pager.pagination("options");
            pageInfo = {
                pageNum: 1,
                totalRowNum: 1e5,
                totalPageNum: 1e3,
                noPagination: true,
                // 无分页的LBM
                pageSize: pagerOptions.pageSize,
                startRowNum: pagerOptions.pageSize * (pagerOptions.pageNumber - 1),
                endRowNum: pagerOptions.pageSize * pagerOptions.pageNumber
            };
        }
        var tableView = {};
        tableView[opts.id] = "col";
        var vc = getVisibleFields(target);
        var param = {
            onBeforeExport: opts.onBeforeExport,
            onBeforePrint: opts.onBeforePrint,
            exportCheckAll: false !== opts.exportCheckAll,
            notShowDialog: showDialog ? true : false,
            showStatistics: opts.showStatistics,
            tableView: tableView,
            reqStr: {
                req: [ reportParams ]
            },
            lsKey: opts.lsKey,
            pageInfo: pageInfo,
            gridColumns: vc,
            exportType: isPrint ? fileType ? fileType : "pdf" : type,
            // 如果是打印且文件类型则显示全部filetype类型，否则为打印pdf或导出文件
            isPrint: isPrint,
            title: title,
            fileName: title,
            userName: g_user && g_user.userName ? g_user.userName : "",
            orgName: g_user && g_user.orgName ? g_user.orgName : "",
            searchParam: searchParam,
            fallParas: fallParas,
            footerInfo: opts.footerInfo,
            confirmInfo: opts.confirmInfo,
            companyName: opts.companyName
        };
        $.extend(param, exportParam);
        submitReport(target, param);
    }
    function onFileImport(target) {
        var toolbar = $.data(target, "datagrid").options.toolbar;
        var param = {
            toolbar: toolbar
        };
        submitImport(target, param);
    }
    function submitImport(target, param) {
        var grid = $.data(target, "datagrid");
        if (grid.dc.importDialog) {
            grid.dc.importDialog.imports(target, param);
        } else {
            grid.dc.importDialog = $("<div></div>").imports(target, param);
        }
    }
    function getSubRow(target, index, frozen) {
        var frozen = frozen || 2;
        var opts = $.data(target, "datagrid").options;
        var $tr = opts.finder.getTr(target, index, "body", frozen);
        return $tr.next("tr[class='datagird-row-sub-tr" + frozen + "']");
    }
    function fixSubRowHeight(target, index) {
        var $tr1 = getSubRow(target, index, 1);
        var $tr2 = getSubRow(target, index, 2);
        $tr1.css("height", "");
        $tr2.css("height", "");
        var height = Math.max($tr1.height(), $tr2.height());
        $tr1.css("height", height);
        $tr2.css("height", height);
    }
    function headFilter(target) {
        var grid = $.data(target, "datagrid");
        var options = grid.options;
        var dc = grid.dc;
        var $this, filterKey;
        var reg = /\d-\d+$/;
        var reg1 = /^\d/;
        //↓ begin @zhaozz 2015-11-28 解决表头数据过滤的问题（之前实现的是当前页过滤，现在针对所有数据过滤）
        var newFilterParam = [];
        $.each([ dc.header1, dc.header2 ], function(i, d) {
            var newParam = {};
            d.find("td.filtered").each(function() {
                $this = $(this);
                newParam = {
                    field: $(this).attr("field"),
                    value: $this.find(".combo-value").length > 0 ? $this.find(".combo-value").val() : $this.attr("filter"),
                    fuzzy: $this.find(".combo-value").length == 0 ? true : false
                };
            });
            !$.isEmptyObject(newParam) && newFilterParam.push(newParam);
            dc["body" + (i + 1)].find(">table>tbody>tr:hidden").show();
        });
        if (newFilterParam.length > 0) {
            var $trs, value, newRows = [];
            $.each(newFilterParam, function(index, p) {
                if (!$.isEmptyObject(p)) {
                    $.each(options.originalRowsData, function(index, row) {
                        //支持模糊匹配
                        if (p.fuzzy && row[p.field].indexOf(p.value) != -1) {
                            newRows.push(row);
                        } else if (!p.fuzzy && row[p.field] == p.value) {
                            newRows.push(row);
                        }
                    });
                }
            });
            $(target).datagrid("loadData", {
                DATA: [ options.originalRowsData ],
                HEAD: {
                    DATA_ROWS: options.originalRowsData.length
                },
                rows: newRows,
                total: newRows.length
            });
        } else {
            $(target).datagrid("loadData", {
                DATA: [ options.originalRowsData ],
                HEAD: {
                    DATA_ROWS: options.originalRowsData.length
                },
                rows: options.originalRowsData,
                total: options.originalRowsData.length
            });
        }
        resize(target);
    }
    //将表头过滤全部去掉
    function clearHeadFilter(target) {
        var grid = $.data(target, "datagrid");
        var dc = grid.dc;
        $.each([ dc.header1, dc.header2 ], function(i, d) {
            d.find("td.filtered").attr("filter", "").removeClass("filtered");
        });
    }
    function getToolbarButton(target, arg) {
        var $target = $.data(target, "datagrid"), btn;
        btns = $target.panel.find(".datagrid-toolbar a.l-btn");
        if (null == arg) {
            return btns;
        }
        if ("number" === $.type(arg)) {
            return $(btns[arg]);
        }
        arg = String(arg);
        btn = btns.filter(function() {
            return $(this).attr("id") === arg;
        });
        if (btn.length) {
            return btn;
        }
        return btns.filter(function() {
            return -1 != $(this).find(".l-btn-text").text().indexOf(arg);
        });
    }
    function getVisibleFields(target) {
        var dc = $.data(target, "datagrid").dc;
        var $head1Tr = dc.header1.find("tr");
        var $head2Tr = dc.header2.find("tr");
        var tds = $head2Tr.find("td").add($head1Tr.find("td")), td, field, fields = [];
        tds.each(function() {
            td = $(this);
            if (!td.is(":visible") || !td.find(">div").is(":visible")) {
                return true;
            }
            field = td.attr("field");
            if (field) {
                fields.push(field);
            }
        });
        var gridColumns = $(target).datagrid("getOriginalColumns");
        var vc = [];
        for (var v in fields) {
            for (var vv in gridColumns) {
                if (fields[v] == gridColumns[vv].field) {
                    vc.push(gridColumns[vv]);
                }
            }
        }
        return vc;
    }
    function getUIRowData(target, arg) {
        var t = $(target), opts = t.data("datagrid").options, trs = $(), ret = [];
        if ("number" === $.type(arg)) {
            trs = opts.finder.getTr(target, arg);
        } else if ("object" === $.type(arg)) {
            trs = opts.finder.getTr(target, t.datagrid("getRowIndex", arg));
        } else if ($.isArray(arg)) {
            $.each(arg, function() {
                trs.push(opts.finder.getTr(target, t.datagrid("getRowIndex", this)));
            });
        }
        trs.filter(function() {
            return !!$(this).html();
        }).each(function() {
            var tr = $(this), retRow = {}, td;
            tr.find("td").each(function() {
                td = $(this);
                if (td.attr("field")) {
                    retRow[td.attr("field")] = td.find(">div").text();
                }
            });
            ret.push(retRow);
        });
        return $.isArray(arg) ? ret : ret[0];
    }
});
