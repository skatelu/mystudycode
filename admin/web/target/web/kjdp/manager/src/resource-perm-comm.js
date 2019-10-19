/**
 *
 * @author ck
 */
define(function (require, exports, module) {
    var EmpSetView = require("./emp-set-view"),
        urlParam = uri.getUrlParam(),
        menuService = require("../../common/service/menu-service"),
        treegridIdArr = ["10"],
        PERM_DATA = null,
        perDefAllMenuId = "@",
        perTypeObj = {
            PER_ADD: "1",
            PER_MODIFY: "2",
            PER_DELETE: "3",
            PER_IMPORT: "4",
            PER_EXPORT: "5",
            PER_PRINT: "6"
        },View,
        girdIdPre = "KJDP_PERMISSION";
        return View = Backbone.View.extend({
        defOpts: {
            empSetTitle: "",
            onAddButtonClick: function() {},
            onDeleteButtonClick: function() {},
            onInitSuccess: function() {},
            onBeforeCreateDialog: function() {}
        },
        initialize: function (opts) {
            var that = this;
            that.opts = $.extend({}, that.defOpts, opts);
            that.empSetView = EmpSetView.loadView({
                el: "#empInfoPublic",
                urlParam: urlParam,
                empSetTitle: that.opts.empSetTitle, //标题
                girdIdPre: girdIdPre, //datagrid的ID前缀
                treegridIdArr: treegridIdArr, //treeegrid的ID集合
                initShow: false //渲染模板后是否显示
            })
        },
        initView: function() {
            var that = this;
            return $.when(
                menuService.getAllMenuData(),
                that.empSetView.initTpl()
            ).then(function(menuData) {
                that.allMenuData = menuData;
                that.empSetView.$tpls.show();
                that.initOperPermView();
                $(window).resize(function(){
                    that.resize();
                });
                that.opts.onInitSuccess();
            })
        },
        initOperPermView: function() {
            exports
            var that = this,
                $operTree = $("#" + girdIdPre + "_" + View.OPER_PERM_RES_TYPE),
                $listPanel = $operTree.treegrid("getPanel"),
                forAllData = {
                    MENU_ID:"默认权限",
                    MENU_LINK:"default",
                    MENU_LVL:"0000",
                    MENU_NAME:"默认权限",
                    MENU_PUR:"1",
                    PAR_MENU:"0"
                },
                treeData = [forAllData].concat(that.allMenuData);
            _.each(treeData, function (item) {
                if (item.PAR_MENU != 0) {
                    item.pid = item.PAR_MENU;
                }
            })
            $("#" + girdIdPre + "_" + View.OPER_PERM_RES_TYPE).treegrid("loadData", {
                rows: treeData,
                total: treeData.length
            });
            // 绑定点击事件
            $listPanel.find(".op-per-label>input[class$='-all']").unbind("click").bind("click", function (v) {
                var $input = $(this),
                    className = $input.attr("class").replace("-all", "");
                if ($input.isChecked()) {
                    $listPanel.find("." + className).not($input).removeAttr("checked").hide();
                } else {
                    $listPanel.find("." + className).not($input).show();
                }
            });
        },
        //页面大小调整
        resize: function () {
            var $tableBox = $(".table-box"),
                $tabsBox = $(".tabs-box"),
                tabsWidth = $(window).width() - 12,
                tabsHeight = $(window).height() - $("#empInfoTpl").height() - 46;
            $tableBox.css({"width": tabsWidth + "px", "height": tabsHeight + "px"});
            $tabsBox.tabs("resize", {
                height: tabsHeight,
                width: tabsWidth
            });
        },
        commonDelete: function(e) {
            if(this.opts.onBeforeCreateDialog() === false) {
                return false;
            }
            var $grid = $(e.data.target),
                that = this,
                rows = $grid.datagrid("getSelections");
            if (!rows || !rows.length) {
                alert("请选择一行数据！");
                return;
            }
            that.opts.onDeleteButtonClick(rows, rows[0].RES_TYPE);
        },
        commonAdd: function(e) {
            if(this.opts.onBeforeCreateDialog() === false) {
                return false;
            }
            var $grid = $(e.data.target),
                that = this,
                obj = this.createCommonDialog({
                    title: "增加",
                    col: $grid.datagrid("getOriginalColumns"),
                    buttons: [{
                        text: "保存",
                        iconCls: "icon-save",
                        handler: function () {
                            if (obj.form.form("validate")) {
                                if(that.opts.onAddButtonClick(obj.form, obj.form.RES_TYPE) !== false) {
                                    obj.dialog.dialog("close");
                                };
                            }
                        }
                    }, {
                        text: "关闭",
                        iconCls: "icon-cancel",
                        handler: function () {
                            obj.dialog.dialog("close");
                        }
                    }]
                });
        },
        getPermData: function() {
            return PERM_DATA;
        },
        loadAllPermData: function(permData) {
            var that = this;
            that.cleanPermData();
            PERM_DATA = permData;
            _.each(that.empSetView.permissionData, function (permClsEmpObj) {
                var perCls = permClsEmpObj && permClsEmpObj.DICT_ITEM;
                if(perCls == View.OPER_PERM_RES_TYPE) {
                    var $listPanel = $("#" + girdIdPre + "_" + perCls).treegrid("getPanel"),
                        allData = _.filter(PERM_DATA, function (v) {
                            return v.RELATE_VALUE == perDefAllMenuId && v.RES_TYPE == perCls;
                        }),
                        unAllFlagObj = {};
                    //设置非默认全部标记
                    _.each(perTypeObj, function (val) {
                        unAllFlagObj[val] = !_.find(allData, function (v) {
                            return v.RES_VALUE == val;
                        })
                    });
                    // 只有不是默认全部权限的情况下才执行勾选
                    _.each(PERM_DATA, function (per) {
                        unAllFlagObj[per.RES_VALUE] && per.RELATE_VALUE != perDefAllMenuId
                        && $listPanel.find("#" + per.RELATE_VALUE + "_" + per.RES_VALUE).attr("checked", true);
                    });
                    // 触发勾选事件
                    _.each(unAllFlagObj, function (val, key) {
                        if(!val){
                            $listPanel.find("input.op-per-type-chk" + key + "-all").trigger("click");
                            $listPanel.find("input.op-per-type-chk" + key).hide();
                        }
                    })
                } else if(!_.indexOf(treegridIdArr, perCls) == -1){
                    $("#" + girdIdPre + "_" + perCls).datagrid("loadData", _.filter(permData, function (o) {
                        return o.RES_TYPE == perCls;
                    }));
                }
            });

        },
        loadPermData: function(permData, resType) {
            var that = this;
            if(_.contains(treegridIdArr, resType)) {
                that.treegridOnLoadSuccess.apply($("#" + girdIdPre + "_" + resType), []);
            } else {
                $("#" + girdIdPre + "_" + resType).datagrid("loadData", permData);
            }
            PERM_DATA = PERM_DATA ? _.filter(PERM_DATA, function(v) {
                return v.RES_TYPE != resType
            }).concat(permData) : permData;
        },
        cleanPermData: function() {
            var that = this;
            _.each(that.empSetView.permissionData, function (permClsEmpObj) {
                var perCls = permClsEmpObj && permClsEmpObj.DICT_ITEM || "0";
                if (_.isEmpty(that.empSetView.permissionData) || _.indexOf(treegridIdArr, permClsEmpObj.DICT_ITEM) == -1) {
                    $("#" + girdIdPre + "_" + perCls).datagrid("clean");
                } else {
                    $("#" + girdIdPre + "_" + perCls).treegrid("getPanel").find("input[type=checkbox]").removeAttr("checked").show();
                }
            });
            PERM_DATA = null;
        },
        createCommonDialog: function(opts, data) {
            var $dialog = $("<div><form></form></div>").dialog({
                width: opts.width || 570,
                height: opts.height || 300,
                modal: true,
                title: opts.title,
                buttons: opts.buttons,
                onClose: function () {
                    $dialog.dialog("destroy");
                }
            });
            var form = $dialog.find("form").form({
                modify: !!opts.modify,
                colNumbers: opts.colNumbers || 2,
                col: opts.col,
                onInitSuccess: function () {
                    _.isFunction(opts.onBeforeLoad) && opts.onBeforeLoad.call(form, data);
                    if (data) {
                        form.form("load", data);
                        _.isFunction(opts.onFormInitSuccess) && opts.onFormInitSuccess.call(form, data);
                    }
                }
            });
            return {
                dialog: $dialog,
                form: form
            };
        },
        getaDiffTreePermData: function(resType) {
            var $grid = $("#" + girdIdPre + "_" + resType),perChkDataArr,addPerData=[],delPerData=[],
            permData = _.filter(PERM_DATA, function(v) {
                return v.RES_TYPE == resType;
            });
            if(_.contains(treegridIdArr, resType)) {
                perChkDataArr = _.chain($grid.treegrid("getPanel").find("input[type=checkbox]:checked")).map(function (chk) {
                    var opPerCodeArr = $(chk).val().split("_");
                    return {
                        RELATE_VALUE: opPerCodeArr[0],
                        RES_VALUE: opPerCodeArr[1]
                    };
                }).value();
                _.each(permData, function(per) {
                    // 在新数据中匹配
                    var oldObj = _.find(perChkDataArr, function (v) {
                        return v.RELATE_VALUE == per.RELATE_VALUE && v.RES_VALUE == per.RES_VALUE;
                    });
                    // 在新数据中找不到，标记删除
                    if (!oldObj) {
                        delPerData.push(per);
                    }
                })
                _.each(perChkDataArr, function(v) {
                    // 在旧数据中匹配
                    var oldObj = _.find(permData, function (per) {
                        return v.RELATE_VALUE == per.RELATE_VALUE && v.RES_VALUE == per.RES_VALUE;
                    });
                    // 在旧数据中找不到，标记新增
                    if (!oldObj) {
                        addPerData.push(v);
                    }
                })
                return {
                    addPermData: addPerData,
                    delPermData: delPerData
                }
            };
        },
        opPerFormatter: function(val, row, index, col) {
            if (!_.isEmpty(row.MENU_LINK)) {
                var perType = perTypeObj[col.field],
                    menuId = row.MENU_ID != "默认权限" ? row.MENU_ID : perDefAllMenuId,
                    isAll = menuId != perDefAllMenuId ? "" : "all",
                    allName = menuId != perDefAllMenuId ? "" : "全部",
                    opPerCode = _.compact([menuId, perType, isAll]).join("_"),
                    opPerType = _.compact([perType, isAll]).join("-");
                return ["<label for='", opPerCode, "' class='op-per-label'>",
                    "<input id='", opPerCode, "' class='op-per-type-chk", opPerType,
                    "' type='checkbox' value='", opPerCode, "' style='cursor: pointer' />",
                    "<span>", allName, "</span></label>"].join("");
            }
        }
    }, {
            OPER_PERM_RES_TYPE: "10",   //操作权限
            ORG_PERM_RES_TYPE: "01"     //机构权限
        });

})
