/**
 * 管理平台
 * @author chenk
 * */
define(function (require, exports, module) {
    var	common = require("../../../kjdp/common/service/busi-base-service"),
        loginService = require("../service/login-service"),
        OpModel = require("../../../kjdp/common/modules/op-model"),
        menuService = require("../../../kjdp/common/service/menu-service"),
        Header = require("./header"),
        Content = require("./content"),
        menuModel = require("../../../kjdp/common/modules/menu-model"),

        TabsView = Backbone.View.extend({
            el: "body",
            initialize: function(args) {
                var that = this;

                that.$tabsWrap = $(that.render({
                    tabsData: args.tabsData
                }));

                that.$tabs = that.$tabsWrap.find(".nav-tabs");
                that.$el.find(".header-bottom").empty().append(that.$tabsWrap);
                that.$tabs.tabs({
                    scrollIncrement: 200,
                    scrollDuration: 100
                });

                that.$tabs.find("a.tabs-inner").click(function() {
                    var $li = $(this).parent();
                    _.isFunction(args.onClick) && args.onClick.call($li, $li.attr("id"));
                });
            },

            render: artTemplate.compile(
                "<div class='nav-tabs-wrap clearfix'>" +
                    "<div class='container'>" +
                        "<div class='nav-tabs'>" +
                            "{{each tabsData}}" +
                                "<div kui-options=\"title:'{{$value.MENU_NAME}}',closable:false,iconCls:'{{$value.MENU_ICO}}',id:'{{$value.MENU_ID}}'\"></div>" +
                            "{{/each}}" +
                        "</div>" +
                    "</div>" +
                "</div>"
            ),

            getTabHeight: function() {
                return this.$tabsWrap.outerHeight(true);
            },

            selectFirst: function() {
                this.$tabs.find("a.tabs-inner:first").click();
            },

            selectTab: function(menuId) {
                this.$tabs.find("li#" + menuId).find("a.tabs-inner").click();
            }
        }),

        HomeView = Backbone.View.extend({
            el: "body",

            initialize: function() {
                var that = this;
                that.events = {};
                menuModel = new menuModel();

                menuModel.fetchMenuData({MENU_STA: "1"}).then(function() {

                         that.header = new Header({
                             showAutoSearch: true,
                             showRightContent: true,
                             model: that.model
                         });
                        
                        that.tabsView = new TabsView({
                        tabsData: menuModel.getTopMenuData(),
                        onClick: function(menuId) {
                            var menuObj = menuModel.getMenuData(menuId),
                                childMenuArr = menuModel.getChildMenuData(menuId);

                            top.kjdp.expandWest();
                            that.content.setWestTitle(menuObj.MENU_NAME);
                            that.renderMenu(childMenuArr);
                        }
                    });

                    that.content = new Content({
                        showWest: true,
                        split: "false",
                        difHeight: -that.tabsView.getTabHeight(),
                        westTitle:"系统菜单"
                    });

                    that.content.setTabs({
                        title: "常用功能",
                        href: uri.buildUrl(resolve("frame/pages/common-biz.html#"))
                    });

                    setTimeout(function() {
                        that.tabsView.selectFirst()
                    }, 100);

                    that.regEvents();
                });
            },
            regEvents: function() {
                var that = this;
                $(window).bind("scroll", function() {
                    that.content.fixLayout();
                });
            },

            renderMenu: function(menuArr) {
                var that = this,
                    $menuWrap = $("<div class='left-menu-wrap'></div>"),
                    $tree = $("<ul class='kui-tree' kui-options='animate:true'></ul>").appendTo($menuWrap);

                that.$tree = $tree.tree({
                    conf: {
                        treeType: "1",
                        parNode: "PAR_MENU",
                        nodeId: "MENU_ID",
                        nodeName: "MENU_NAME",
                        lvlLength: "4",
                        lvlNode: "MENU_LVL",
                        iconCls: "MENU_ICO,frame-icon",
                        link:"MENU_LINK"
                    },
                    data: menuArr,
                    onClick: function(node) {
                        if(!node) {
                            return;
                        }

                        if ( (node.children && node.children.length) || ($(node.target).find(".tree-folder").length>0) ) {
                            $(this).tree('toggle', node.target);
                            return false;
                        }
                        var menuData = menuModel.getMenuData(node.id);
                        top.kjdp.addTab({
                            id: menuData.MENU_ID
                        });
                    }
                }).scrollbox({overflow: "auto"});

                that.content.$westPanel.empty().append($menuWrap);
                return $menuWrap;
            },

            expandMenu: function(menuId) {
                var that = this,
                    rootMenuObj;

                if(!that.tabsView || !that.$tree) {
                    return;
                }

                if(!(rootMenuObj = menuModel.getRootMenu(menuId))) {
                    return;
                }
                that.tabsView.selectTab(rootMenuObj.MENU_ID);
                that.$tree.tree("expandTo", that.$tree.find("div.tree-node[node-id='" + menuId + "']").addClass("tree-node-selected"));
            },

            regEvent: function(name, callback) {
                _.isString(name) && _.isFunction(callback) && (this.events[name] = callback);
            },

            triggerEvent: function(name) {
                return _.isString(name) && _.isFunction(this.events[name]) && this.events[name]();
            },

            getOpInfo: function() {
                return this.model.toJSON();
            }


        }),
        menuModel,
        index,
        gObj;

    exports.$ready = function() {
        if(!loginService.isLogin()) {
            loginService.openLoginPage();
            return;
        } else {
            loginService.session().done(function(data) {
                common.initCommonCache().then(function() {
                    var opModel = new OpModel();
                    opModel.setOpInfo(data);
                    index = new HomeView({
                        model: opModel
                    });

                    $(window).resize(function() {
                        index.content.resizeLayout();
                    });
                })
            });
        }
    };
	
    common.gExpose(gObj = {
        events: {},
        getOpInfo: function() {
            return index.getOpInfo();
        },
        addTab: function(opts, param) {
            var menuData;
            if(!opts.id){
                index.content.addTab(opts, $.extend({}, param));
                return ;
            }
            menuData = menuModel.getMenuData(opts.id);
            if(!menuData) {
                alert("菜单信息查询失败！");
                return;
            }
            if(!menuData.MENU_LINK) {
                alert("此功能暂未实现！");
                return;
            }
            if(menuData.MENU_STA && menuData.MENU_STA == "0") {
                alert("该菜单功能，菜单状态已关闭，不能访问！");
                return false;
            }
            opts = $.extend({
                id: menuData.MENU_ID,
                title: menuData.MENU_NAME,
                href: resolve(menuData.MENU_LINK + "#"),
                menuSta : menuData.MENU_STA
            },opts);

            //是否可以全屏的处理
            if(opts.href.indexOf("uniform-param.html") != -1){
                opts.fullScreenAble = false;
            }
            //记录最近打开的菜单
            menuService.saveMenuRecord(top.kjdp.getOpInfo().OP_CODE, {
                MENU_ID: opts.id,
                MENU_NAME: opts.title,
                MENU_ICON: opts.ico
            });
            index.content.addTab(opts, $.extend({}, param));
        },

        closeTab: function(arg) {
            index.content.closeTab(arg);
        },

        //获取当前选项卡的标题名
        getSelectedTabName: function(){
            var $tabs = index.content.getTabs(),
                tabIndex = $tabs.tabs("getTabIndex",index.content.getSelectedTab()),
                opts = $.data($tabs[0],"tabs");
            return opts.tabs[tabIndex].data().panel.options.title;   
        },
        //获取当前选项卡
        getSelectedTab: function(){
            return index.content.getSelectedTab();
        },

        expandMenu: function(menuId) {
            return index && index.expandMenu(menuId);
        },

        collapseWest: function() {
            index.content.collapseWest();
        },

        expandWest: function() {
            index.content.expandWest();
        },

        getMenuData: function(menuId) {
            return menuModel.getMenuData(menuId);
        },

        regGlobalEvent: function(name, callback) {
            _.isString(name) && _.isFunction(callback) && (gObj.events[name] = callback);
        },

        triggerGlobalEvent: function(name) {
            if(!_.isString(name) || !_.isFunction(gObj.events[name])) {
                return;
            }

            try {
                return gObj.events[name].apply(this, utils.slice(arguments, 1));
            } catch(ex) {}
        }
    });

});
