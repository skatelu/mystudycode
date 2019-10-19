/**
 * statistics组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/statistics", [], function(require, exports, module) {
    $.fn.statistics = function(options, context) {
        if (typeof options == "string") {
            return $.fn.statistics.methods[options](this, context);
        }
        options = options || {};
        return this.each(function() {
            var state = $.data(this, "statistics");
            if (state) {
                $.extend(state.options, options);
            } else {
                $.data(this, "statistics", {
                    options: $.extend({}, $.fn.statistics.defaults, $.fn.statistics.parseOptions(this), options)
                });
            }
            createPanel(this);
        });
    };
    $.fn.statistics.methods = {
        options: function(jq) {
            return $.data(jq[0], "statistics").options;
        },
        showPanel: function(jq) {
            return jq.each(function() {
                isShowPanel(this, true);
            });
        },
        hidePanel: function(jq) {
            return jq.each(function() {
                isShowPanel(this, false);
            });
        },
        refresh: function(jq) {
            return jq.each(function() {
                refresh(this);
            });
        }
    };
    $.fn.statistics.parseOptions = function(target) {
        return $.extend({}, $.parser.parseOptions(target));
    };
    $.fn.statistics.defaults = {
        columns: undefined,
        refreshed: true,
        rows: undefined,
        dc: undefined,
        onLoaded: function() {}
    };
    function isShowPanel(target, isShow) {
        var data = $.data(target, "statistics");
        if (!data) {
            alert("无数据统计！");
            return false;
        }
        var opts = data.options;
        var statistics = opts.dc.view.find(".datagrid-footer");
        isShow ? statistics.show() : statistics.hide();
        opts.onLoaded.call(target);
    }
    //刷新时重新加载数据
    function refresh(target) {
        var data = $.data(target, "statistics");
        var opts = data.options;
        opts.refreshed = false;
        createPanel(target);
    }
    //分析统计数据
    function getAnalyzeData(target, rows, columns) {
        // U版平安需求，统计数据不能只统计当前页面的，需要统计到所有查询到的数据
        // 技术方案，判断浏览器是否支持H5多线程Worker，如果支持则采用worker单独统计所有数据.
        var statisticsType = getStatisticsType(target), opts = $.data(target, "datagrid").options, deffer = $.Deferred(), //可用来判断是否是前端分页
        boolReportParamExist = opts.reportParams && undefined !== opts.reportParams.R_LAST_SN && undefined !== opts.reportParams.R_COUNT ? true : false, reportParams = boolReportParamExist ? opts.reportParams : {
            R_LAST_SN: 0,
            R_COUNT: opts.pageSize * opts.pageNumber
        }, numRcount = Number(reportParams.R_COUNT), numStart = Number(reportParams.R_LAST_SN), //reqParam = $.extend({R_LAST_SN:numStart,R_COUNT: numRcount}, opts.req[0], opts.queryParams,reportParams),
        arrStatRow = $.data(target, "statistics").options.arrStatRow || [], //参与统计的数据
        arrNewRows = boolReportParamExist ? $.extend([], $.data(target, "datagrid").appendRows) : $.extend([], $.data(target, "datagrid").options.originalRowsData), //新查到的数据
        webWork;
        if (typeof Worker !== "undefined" && opts.pagination) {} else {
            for (var j = 0; j < columns.length; j++) {
                var col = columns[j], //字段属性
                field = col["field"], //字段名
                val = {};
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    if ($.isNumeric(row[field])) {
                        for (var key in statisticsType) {
                            if (col[key]) {
                                var tmp = statisticsType[key].getValue(val, rows, row, field, i);
                                if (i == rows.length - 1) {
                                    statisticsType[key].item.push({
                                        field: col["field"],
                                        title: col["title"],
                                        formatter: col[key + "formatter"],
                                        value: isNaN(tmp) ? tmp + "" : Number(tmp).toFixed(col.fixNum || 2)
                                    });
                                }
                            }
                        }
                    }
                }
            }
            return $.when(statisticsType);
        }
        return deffer.promise();
    }
    //创建统计数据区域
    function createPanel(target) {
        var data = $.data(target, "statistics"), opts = data.options, columns = opts.columns, rows = opts.rows, dc = opts.dc, fields = opts.fields, statisField = [], $statistics = dc.view.nextAll(".datagrid-row-statistics"), dataFormatter = {};
        if ($statistics.length > 0) {
            if ($statistics.is(":hidden")) {
                if (!opts.refreshed) {
                    $statistics.remove();
                } else {
                    $statistics.show();
                    opts.onLoaded.call(target);
                    return false;
                }
            } else {
                $statistics.remove();
            }
        }
        //start --修复datagrid锁定列相邻统计列统计描述字段替换统计信息的bug  @chenguiy 2016-08-09
        var analyzePromise = getAnalyzeData(target, rows, columns), //四种所有的统计数据
        index = 0, statistics = [], statistics_title = [], gridOpts = $.data(target, "datagrid").options;
        analyzePromise.done(function(analyzeData) {
            statistics.push('<table class="datagrid-ftable" cellspacing="0" cellpadding="0" border="0"><tbody>');
            statistics_title.push('<table class="datagrid-ftable" cellspacing="0" cellpadding="0" border="0"><tbody>');
            for (var key in analyzeData) {
                if (analyzeData[key]["item"].length > 0) {
                    statistics.push('<tr class="datagrid-header-row" datagrid-row-index="' + index + '">');
                    statistics_title.push('<tr class="datagrid-header-row" datagrid-row-index="' + index + '">');
                    var row = {}, stsDescField, stsDescFieldIndex = opts.stsDescFieldIndex;
                    for (var i = 0; i < analyzeData[key]["item"].length; i++) {
                        var item = analyzeData[key]["item"][i], value = item.value, field = item.field, formatter = item["formatter"];
                        if (typeof formatter == "string") {
                            eval("formatter=" + formatter);
                        }
                        if ($.isFunction(formatter)) {
                            value = formatter.call(null, value);
                        }
                        row[field] = value;
                    }
                    if (stsDescFieldIndex >= fields.length) stsDescFieldIndex = fields.length - 1;
                    stsDescField = fields[stsDescFieldIndex];
                    stsDescField = opts.columns.length === fields.length ? opts.columns[stsDescFieldIndex].field : opts.columns[fields.length + stsDescFieldIndex].field;
                    //start --修复datagrid锁定统计列时统计信息缺失、统计栏checkbox列多余复选框导致全选失效的bug  @chenguiy 2016-08-18
                    if (!gridOpts.rownumbers && opts.columns.length === fields.length) {
                        row[stsDescField] = analyzeData[key]["name"];
                    } else if (gridOpts.rownumbers) {
                        statistics_title.push("<td><div class='datagrid-cell-rownumber datagrid-cell'>" + analyzeData[key]["name"] + "</div></td>");
                        $.each(gridOpts.frozenColumns[0] || [], function(i, frozenCol) {
                            if (frozenCol.type === "checkbox") {
                                statistics_title.push("<td title='' field='" + frozenCol.field + "'><div style='font-weight: bolder;' class='datagrid-cell-check'></div></td>");
                            } else {
                                statistics_title.push("<td title='" + (frozenCol[key] ? value : "") + "' field='" + frozenCol.field + "'><div style='text-align:" + (frozenCol.align ? frozenCol.align : "left") + ";font-weight:bolder;' class='datagrid-cell datagrid-cell-c1-" + frozenCol.field + "'>" + (frozenCol[key] ? value : "") + "</div></td>");
                            }
                        });
                    } else {
                        var firstField = 0, frozenColNum = gridOpts.frozenColumns[0].length - 1;
                        $.each(gridOpts.frozenColumns[0] || [], function(i, frozenCol) {
                            if (frozenCol.type === "checkbox") {
                                firstField < frozenColNum && firstField++;
                                statistics_title.push("<td title='' field='" + frozenCol.field + "'><div style='font-weight: bolder;' class='datagrid-cell-check'>" + (i === firstField ? analyzeData[key]["name"] : frozenCol[key] ? value : "") + "</div></td>");
                            } else {
                                statistics_title.push("<td title='" + (frozenCol[key] ? value : "") + "' field='" + frozenCol.field + "'><div style='text-align:" + (frozenCol.align ? frozenCol.align : "left") + ";font-weight:bolder;' class='datagrid-cell datagrid-cell-c1-" + frozenCol.field + "'>" + (i === firstField ? analyzeData[key]["name"] : frozenCol[key] ? value : "") + "</div></td>");
                            }
                        });
                    }
                    //start 当启动统计功能时，有些列不需要做统计时，而且在col中配置了formatter,会显示为NaN.需要把NaN显示为空----by lims
                    statisField = _.chain(fields).reject(function(fieldCK, i) {
                        var flagCK = false, col = $(target).datagrid("getColumnOption", fieldCK);
                        $.each(analyzeData[key]["item"], function(index, _item) {
                            if (fieldCK == _item.field) {
                                flagCK = true;
                            }
                        });
                        if (!flagCK) {
                            if (col.formatter) {
                                dataFormatter[fieldCK] = col.formatter;
                                col.formatter = "";
                                flagCK = false;
                            }
                        }
                        if (col.type === "checkbox") {
                            statistics.push("<td title='' field='" + fieldCK + "'><div style='font-weight: bolder;' class='datagrid-cell-check'>" + (fieldCK in row ? row[fieldCK] : "") + "</div></td>");
                        }
                        return col.type === "checkbox";
                    }).value();
                    //end 当启动统计功能时，有些列不需要做统计时，而且在col中配置了formatter,会显示为NaN.需要把NaN显示为空----by lims
                    statistics.push(opts.view.renderRow.call(this, target, statisField, false, index, row));
                    //statistics_title.push("<td><div class='datagrid-cell-rownumber datagrid-cell'>" + analyzeData[key]['name'] + "</div></td>");
                    //end --修复datagrid锁定统计列时统计信息缺失、统计栏checkbox列多余复选框导致全选失效的bug  @chenguiy 2016-08-18
                    statistics.push("</tr>");
                    statistics_title.push("</tr>");
                    index++;
                }
            }
            //把置空的formatter补回来
            if (!$.isEmptyObject(dataFormatter)) {
                $.each(dataFormatter, function(index, itemFormatter) {
                    $(target).datagrid("getColumnOption", index).formatter = itemFormatter;
                });
                dataFormatter = {};
            }
            statistics.push("</tbody></table>");
            statistics_title.push("</tbody></table>");
            var footer = dc.view.find(".datagrid-view2").find(".datagrid-footer-inner");
            var footer_title = dc.view.find(".datagrid-view1").find(".datagrid-footer-inner");
            footer_title.empty();
            footer.empty();
            $(statistics.join("")).appendTo(footer);
            $(statistics_title.join("")).appendTo(footer_title);
            footer.show();
            footer_title.show();
            //end 修复datagrid锁定列相邻统计列统计描述字段替换统计信息的bug  @chenguiy 2016-08-09
            opts.refreshed = true;
            opts.onLoaded.call(target);
        });
    }
    //获取统计类型
    function getStatisticsType(target) {
        var data = $.data(target, "statistics"), opts = data.options, statisticsTexts = opts.statisticsTexts || [];
        return {
            max: {
                name: statisticsTexts[0] || "最大值",
                item: [],
                getValue: function(val, rows, row, field, i) {
                    return getMax(val, rows, row, field, i);
                }
            },
            min: {
                name: statisticsTexts[1] || "最小值",
                item: [],
                getValue: function(val, rows, row, field, i) {
                    return getMin(val, rows, row, field, i);
                }
            },
            avg: {
                name: statisticsTexts[2] || "均值",
                item: [],
                getValue: function(val, rows, row, field, i) {
                    return getAvg(val, rows, row, field, i);
                }
            },
            sum: {
                name: statisticsTexts[3] || "合计",
                item: [],
                getValue: function(val, rows, row, field, i) {
                    return getSum(val, rows, row, field, i);
                }
            }
        };
    }
    //最大值
    function getMax(val, rows, row, field) {
        var key = "max" + field, fieldVal = Number(row[field]), n = val[key] || 0;
        if (n < fieldVal) {
            val[key] = fieldVal;
        }
        return val[key];
    }
    //最小值
    function getMin(val, rows, row, field) {
        var key = "min" + field, fieldVal = Number(row[field]), n = val[key] || row[field];
        if (n >= fieldVal) {
            val[key] = fieldVal;
        }
        return val[key];
    }
    //均值
    function getAvg(val, rows, row, field, i) {
        var key = "avg" + field, n = val[key] || 0;
        val[key] = n + row[field] * 1;
        if (rows.length - 1 == i) {
            val[key] = val[key] / rows.length;
        }
        return val[key];
    }
    //合计
    function getSum(val, rows, row, field) {
        var key = "sum" + field, n = val[key] || 0;
        val[key] = n + row[field] * 1;
        return val[key];
    }
    //根据字符计算所占的长度
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
});
