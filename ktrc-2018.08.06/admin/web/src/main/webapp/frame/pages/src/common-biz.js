/**
 * 常用功能界面
 * @author chenk
 */
define(function(require, exports, module) {
    var common = require("../service/login-service"),
        menuService = require("../../../kjdp/common/service/menu-service"),
        opInfo = top.kjdp.getOpInfo(),
        MenuModel = Backbone.Model.extend({
            defaults: {
                id: "",
                title: "",
                ico: ""
            }
        }),
        MenuCollection = Backbone.Collection.extend({
            model: MenuModel,
            pGetAllMenu: function() {
                var self = this;
                return menuService.getOpCommMenu(opInfo.OP_CODE, {
                    "MENU_STA": "1"
                }).then(function(data) {
                    var arrMenu = _filterLeaf(data);
                    self.reset();
                    $.each(arrMenu, function(index, m) {
                        self.add(new MenuModel({
                            id: m.MENU_ID,
                            title: m.MENU_NAME,
                            ico: m.MENU_ICON
                        }));
                    });
                    return self.toJSON();
                })
            },
            removeByMenuid: function(menuId) {
                var model;
                if (menuId) {
                    menuId = menuId + "";
                    model = this.findWhere({
                        id: menuId
                    });
                    model && this.remove(model);
                }
            }
        }),
        Container = Backbone.View.extend({
            el: ".container-menu",
            initialize: function() {
                var self = this;
                this.$rightMenu = this.$el.find("#right-menu");
                this.$rightMenu.menu({
                        onClick: function(item) {
                            self.rightMenuClick(item);
                        }
                    });
                this.resize();
                this.renderMenuPanel();
                this.bindEvents();
            },
            template: artTemplate.compile(
                '{{each arrMenu}}' +
                '<div class="menu-wrap clearfix" data-id="{{$value.MENU_ID}}" data-title="{{$value.MENU_NAME}}" title="{{$value.MENU_NAME}}">' +
                '<div class="menu-icon-leaf {{$value.MENU_ICON}}"></div>' +
                '<div class="menu-title" title="{{$value.MENU_NAME}}">{{$value.MENU_NAME}}</div>' +
                '</div>' +
                '{{/each}}'
            ),
            resize: function(){
                var southHeight = this.$el.find(".layout-panel.south")._outerHeight(),
                    northHeight = $(window).height() - southHeight - 6;
                this.$el.find(".layout-panel.north")._outerHeight(northHeight);
            },
            bindEvents: function() {
                var self = this;
                $(document).bind('contextmenu', function(e) {
                    return false;
                });
                this.$el.on("mousedown.menu-wrap", ".layout-panel.north .menu-wrap", function(e) {
                    e.stopPropagation();
                    e.preventDefault();
                    var curTarget = $(e.currentTarget);
                    self.selectMenuModel = new MenuModel($(e.currentTarget).data());
                    if (3 !== e.which && curTarget.hasClass("add-panel")) {
                        self.addMenu();
                    } else if (3 === e.which && !curTarget.hasClass("add-panel")) {
                        self.$rightMenu.menu("show", {
                            left: e.pageX + 12,
                            top: e.pageY + 12
                        });
                    } else if (3 !== e.which) { //普通单击
                        self.openMenu(self.selectMenuModel);
                    }
                });
                this.$el.on("click.menu-wrap", ".layout-panel.south .menu-wrap", function(e) {
                    self.openMenu(new MenuModel($(e.currentTarget).data()));
                });
                this.$el.on("click.clearRecently", ".clear-btn", function() {
                    confirm("是否清空【最近访问业务】？", function(flag) {
                        if (flag) {
                            self.renderRecentMenuPanel([]);
                            menuService.clearMenuRecord(top.kjdp.getOpInfo().OP_CODE);
                        }
                    })
                })
            },
            rightMenuClick: function(item) {
                var self = this;
                if ("delete" === item.name) {
                    if (confirm("是否要删除【" + self.selectMenuModel.get("title") + "】", function(flag) {
                            if (flag) {
                                menuService.removeOpCommMenu({
                                    OP_CODE: opInfo.OP_CODE,
                                    MENU_ID: self.selectMenuModel.get("id")
                                }).done(function() {
                                    self.collection.removeByMenuid(self.selectMenuModel.get("id"));
                                    self.renderMenuPanel();
                                });
                            }
                        }));

                }
            },
            renderMenuPanel: function() {
                var that = this,
                    collection = that.collection;
                collection.pGetAllMenu().done(function(arrMenu) {
                    that.$el.find(".layout-panel.north>.l-panel-body>.menu-panel")
                        .html(that.template({
                            arrMenu: arrMenu
                        }));
                });
                this.renderRecentMenuPanel();
            },
            //渲染
            renderRecentMenuPanel: function(arrNewMenu) {
                var that = this,
                    arrMenu = arrNewMenu || menuService.getMenuRecord(top.kjdp.getOpInfo().OP_CODE);
                that.$el.find(".layout-panel.south>.l-panel-body")
                    .html(that.template({
                        arrMenu: arrMenu
                    }));
            },
            //新增常用菜单
            addMenu: function() {
                var self = this;
                if (this.$addMenuDialog) {
                    this.$addMenuDialog.dialog("open");
                    onLoadSuccessMenuDialog();
                } else {
                    this.$addMenuDialog = $('<div><div class="kui-tree"></div></div>');
                    this.$addMenuDialog.dialog({
                        width: $(document.body).width() / 2,
                        height: $(document.body).height() - 80,
                        modal: true,
                        maximizable: true,
                        title: "新增常用业务",
                        buttons: [{
                            text: "确定",
                            iconCls: "icon-save",
                            handler: function() {
                                var nodeArr = self.$addMenuDialog.find(".kui-tree").tree("getChecked"),
                                    idArr = [],
                                    mask;
                                for (var i = 0; i < nodeArr.length; i++) {
                                    idArr.push(nodeArr[i].id);
                                }
                                if(idArr.length === 0){
                                    alert("请至少选中一个菜单。");
                                    return;
                                }                                
                                mask = loading("操作中，请稍后！");
                                menuService.addOpCommMenu(opInfo.OP_CODE, idArr.join(",")).done(function(){
                                    alert("新增常用业务菜单成功！");
                                    self.$addMenuDialog.dialog("close");
                                    self.renderMenuPanel();
                                }).always(function() {
                                    mask.destroy();
                                });
                            }
                        }]
                    });

                    menuService.getMenuData({
                        MENU_STA : "1"
                    }).done(function (data) {
                        self.$addMenuDialog.find(".kui-tree").tree({
                            conf: {
                                treeType: "1",
                                parNode: "PAR_MENU",
                                nodeId: "MENU_ID",
                                nodeName: "MENU_NAME",
                                nodeDataType : "number"
                            },
                            data: _filter(data),
                            checkbox : true,
                            onLoadSuccess : onLoadSuccessMenuDialog
                        });
                    });
                }
               
                //弹出框选中已添加的常用业务菜单
                function onLoadSuccessMenuDialog(){
                    var checkNode = self.collection.toJSON(),
                        $tree = self.$addMenuDialog.find(".kui-tree");
                    $tree.tree("unCheckedAll");
                    for(var i = 0; i < checkNode.length; i++){
                        var $check = $tree.find("div.tree-node[node-id='"+checkNode[i].id+"']");
                        $tree.tree("check",$check);
                    }
                }
            },
            //打开页面链接并记录link到cookie
            openMenu: function(menuModel) {
                top.kjdp.addTab({
                    id: "" + menuModel.get("id")
                },{
                    MENU_ID: "" + menuModel.get("id")
                });
            }
        }),
        container, menuCollection;

    function _filter(arrMenu) {
        var _arr = [];
        $.each(arrMenu, function(index, menu) {
            menu.PLAT_CODE = "" !== menu.PLAT_CODE ? menu.PLAT_CODE : "1";
            menu.HAS_PARENT =  _hasParent(_arr, menu);
            menu.HAS_PARENT && _arr.push(menu);
        });
        return _arr;
    }

    function _filterLeaf(arrMenu) {
        var _arr = [];
        $.each(arrMenu, function(index, menu) {
            menu.PLAT_CODE = "" !== menu.PLAT_CODE ? menu.PLAT_CODE : "1";
            menu.HAS_CHILD =  _hasChild(arrMenu, menu);
           !menu.HAS_CHILD && _arr.push(menu);
        });
        return _arr;
    }

    function _hasChild(arrMenu, menu) {
        return (_.find(arrMenu, function(m) {
            return m.PAR_MENU === menu.MENU_ID;
        })) ? true : false;
    }

    function _hasParent(arrMenu, menu) {
        return menu.PAR_MENU=='0' || (_.find(arrMenu, function(m) {
            return m.MENU_ID === menu.PAR_MENU;
        })) ? true : false;
    }

    if ($(".container-menu").length > 0) {
        menuCollection = new MenuCollection();
        container = new Container({
            collection: menuCollection
        });
        $(window).resize(function() {
            container.resize();
        });
    }

});