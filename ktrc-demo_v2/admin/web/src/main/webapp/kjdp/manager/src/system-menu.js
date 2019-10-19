/**
 * 菜单管理模块
 * @author liuqing dengp
 */
define(function (require, exports, module) {
    var menuService = require("../../common/service/menu-service"),

        //页面数据模型
        //菜单树数据模型，表单构造数据模型。
        MenuModel = Backbone.Model.extend({
            defaults: {
                menuData: [],
                formConf: {}
            },
            //获取菜单树数据
            getMenuData: function(){
                var that = this;
                return menuService.getMenuData().then(function(data){
                    that.set("menuData", data);
                });
            },
            //获取菜单form的view配置
            getFormConf: function() {
                var that = this,
                    viewId = "KJDP_SYS_MENU";

                return parser.getViewConfig(viewId).then(function(conf){
                    that.set("formConf", conf[viewId]);
                });
            }
        }),

        //菜单界面视图
        MenuView = Backbone.View.extend({
            el: "body",
            initialize: function(args) {
                var that = this;

                that.$menuTree = that.$el.find("#menuTree");
                that.createMenuTree(that.model.get("menuData"));
                that.$menuTabs = that.$el.find("#menuTabs");

                that.menuInfoFormView = new FormView({
                    el: that.$el.find("#menuInfoPanel"),
                    parView: that,
                    formType: "1",  //菜单信息
                    formConf: that.model.get("formConf")
                });

                that.sibMenuFormView = new FormView({
                    el: that.$el.find("#sibMenuPanel"),
                    parView: that,
                    formType: "2",  //同级
                    formConf: that.model.get("formConf")
                });

                that.childMenuFormView = new FormView({
                    el: that.$el.find("#childMenuPanel"),
                    parView: that,
                    formType: "3",  //子级
                    formConf: that.model.get("formConf")
                });
            },
            getDestNodeId: function($tree, $dest, position) {
                if(position == "top" || position == "bottom") {
                   var destNode = $tree.tree("getNode", $dest),
                       parentNode = $tree.tree("getParent", $dest),
                       children = _.filter($tree.tree("getChildren", parentNode && parentNode.target || null), function(v) {
                           return parentNode ? v.pid == parentNode.id : v.pid == "0";
                       }),index = -1;
                   _.each(children, function(v, k) {
                       if(v.id == destNode.id) {
                           index = k;
                           return false;
                       }
                   });
                   if(position == "top") {
                       return --index > 0 ? children[index].id : "";
                   } else {
                       return children[index].id;
                   }
                } else {
                    return "";
                }
            },
            createMenuTree: function(menuData) {
                var that = this;
                that.$menuTree.empty().removeData("tree").tree({
                    data: menuData,
                    dropMethod: function(src,dest,position,callback){

                        var $tree = $(this),
                            destNode = $tree.tree("getNode",dest),
                            srcNode = $tree.tree("getNode",src),
                            parDesNodeId;

                        var msg = "确定要将["+$(src).text()+"]移动到["+$(dest).text()+"]的";
                        if(position=="top"){
                            msg += "前";
                            parDesNodeId = destNode.pid || "0";
                        }else if(position=="bottom"){
                            msg += "后";
                            parDesNodeId = destNode.pid || "0";
                        }else{
                            msg += "下";
                            parDesNodeId = destNode.id;
                        }
                        msg += "面吗？";

                        confirm(msg,function(flag){
                            if(flag){
                                var mask = loading("菜单移动中！");
                                menuService.moveMenu(srcNode.id, that.getDestNodeId($tree, dest, position), parDesNodeId).done(function(data) {
                                    alert("移动菜单操作成功！");
                                    menuView.resetData(srcNode);
                                }).always(function() {
                                    mask.destroy();
                                });
                            }
                        });
                    },
                    onClick: function(node) {
                        if(!node) {
                            return;
                        }

                        that.$menuTabs.tabs("enableTab", 1);
                        that.$menuTabs.tabs("enableTab", 2);

                        var menuData = that.getMenuData(node.id);

                        that.menuInfoFormView.setData(menuData,true);
                        that.sibMenuFormView.setData({
                            PAR_MENU: menuData.PAR_MENU
                        });
                        that.childMenuFormView.setData({
                            PAR_MENU: menuData.MENU_ID
                        });
                    }
                });
            },

            getMenuData: function(menuId) {
                var that = this;
                return _.find(that.model.get("menuData"), function(v) {
                    return menuId === v.MENU_ID;
                });
            },

            resetData: function(expandToNode) {
                var that = this;
                that.$menuTabs.tabs("select", 0);
                that.menuInfoFormView.resetForm();
                that.sibMenuFormView.resetForm();
                that.childMenuFormView.resetForm();
                that.reloadMenuTree(expandToNode);
            },

            reloadMenuTree: function(expandToNode) {
                var that = this,
                    sNode = expandToNode || that.$menuTree.tree("getSelected");

                that.model.getMenuData().done(function() {
                    that.createMenuTree(that.model.get("menuData"));
                    var node = sNode?that.getTreeNode(sNode.id):null;
                    sNode && node.length && that.$menuTree.tree("expandTo", node) && that.$menuTree.tree("select", node) && node.trigger("click");
                });
            },

            getTreeNode: function(menuId) {
                return this.$menuTree.find("div.tree-node[node-id='" + menuId + "']");
            }
        }),

        //form表单视图
        FormView = Backbone.View.extend({
            initialize: function(args){
                var that = this;
                that.parView = args.parView;
                that.formType = args.formType;
                that.formConf = args.formConf;
                that.initForm();
            },

            initForm: function() {
                var that = this;
                that.$form = $("<form></form>").appendTo(that.$el).form($.extend({}, that.formConf, {
                    onInitSuccess: function() {
                        that.$MENU_NAME = that.$form.find("input[name='MENU_NAME']");
                        that.$MENU_ICO = that.$form.find("input[name='MENU_ICON']");
                        that.$MENU_STA = that.$form.find("input[comboname='MENU_STA']");
                        that.$MENU_LINK = that.$form.find("input[name='MENU_LINK']");
                        that.$btnSave = that.$el.find("#btnSave");
                        that.$btnDelete = that.$el.find("#btnDelete");
                        that.disableForm();
                    }
                }));
                that.initButton();
            },

            initButton: function() {
                var that = this,
                    btnWrap = $("<div class='btn-wrap'></div>").appendTo(that.$el);

                $("<a id='btnSave'></a>").appendTo(btnWrap).linkbutton({
                    text: "保存",
                    iconCls: "icon-save",
                    onClick: function() {
                        if(!that.$form.form("validate")) {
                            return;
                        }

                        var menuObj = that.getData(),
                            mask = loading("保存菜单中！");
                        $.when("1" === that.formType ? menuService.updateMenuInfo(menuObj.MENU_ID, menuObj) :
                            menuService.addMenu(menuObj))
                            .done(function() {
                                alert("操作成功！");
                                that.parView.resetData();
                            }).always(function() {
                            mask.destroy();
                        });
                    }
                });

                if("1" === that.formType) {
                    $("<a id='btnDelete'></a>").appendTo(btnWrap).linkbutton({
                        text: "删除",
                        iconCls: "icon-cancel",
                        onClick: function() {
                            confirm("确定要删除选中的菜单吗？若菜单下有子菜单，子菜单也会级联删除！", function(flag) {
                                if(!flag) {
                                    return;
                                }

                                var mask = loading("删除菜单中！"),
                                    menuObj = that.getData();
                                menuService.removeMenu(menuObj.MENU_ID).done(function() {
                                    alert("操作成功！");
                                    that.parView.resetData();
                                }).always(function() {
                                    mask.destroy();
                                });
                            });
                        }
                    });
                }
            },

            getData: function() {
                var dataObj = this.$form.form("getData");
                dataObj.MENU_LINK = dataObj.MENU_LINK.replace(/\\/g, "/");
                return dataObj;
            },

            disableForm: function() {
                var that = this;
                that.$form.form("disable");
                that.$btnSave.linkbutton("disable");
                that.$btnDelete.linkbutton("disable");
            },

            enableForm: function(modify) {
                var that = this;
                that.$form.form("enable");
                that.$btnSave.linkbutton("enable");
                that.$btnDelete.linkbutton("enable");
                if(modify === true){
                    that.$form.form("disable",["MENU_ID"]);
                }
            },

            resetForm: function() {
                var that = this;
                that.disableForm();
            },

            setData: function(menuData,modify) {
                var that = this;
                that.$form.form("reset").form("load", menuData);
                that.enableForm(modify);
            }
        }),        				
        menuView;

    exports.$ready = function() {
        var menuModel = new MenuModel();
        $.when(
            menuModel.getMenuData(),
            menuModel.getFormConf()
        ).done(function(){
            menuView = new MenuView({
                model: menuModel
            });
        });
    };
});
