/**
 *  管理平台内容部分
 * @author chenk
 */
define(function (require, exports, module) {
    var urlParam = uri.getUrlParam(),

        contentRender = require("../tpls/content-tpl"),

        ContentView = Backbone.View.extend({
            el: "body",

            initialize: function(arg) {
                var that = this;

                this.headerHeight = $(".header-wrap").height();
                this.footerHeight = 0;

                this.minHeight = 500;
                this.marginHeight = 20;
                this.tabsHeight = 32;

                this.difHeight = arg.difHeight || 0;

                arg = $.extend({
                    height: this.getContentHeight(),
                    westWidth: "230",
                    collapsable: "true",
                    border:"true"
                }, arg);

                this.$content = $(contentRender(arg));
                this.$el.append(this.$content);
                this.$layout = this.$content.find(".kui-layout");
                this.$centerPanel = this.$content.find(".center-panel");
                this.$westPanel = this.$content.find(".west-panel");

                this.$layout.layout({
                    animateSpeed: 50,
                    height : arg.height,
                    width : that.$layout.parent().width(),
                    onBeforeResize : function() {
                        //layout绑定回调onBeforeResize事件，控制页面缩放。
                        that.$layout.css({
                            width: that.$layout.parent().width(),
                            height: that.getContentHeight()
                        });
                    }
                });

                arg.collapseWest && this.collapseWest();

                this.$menu = this.$content.find(".tabs-menu").menu({
                    onClick: function(obj) {
                        var menu = $(this),
                            index = menu.data("index"),
                            tab = menu.data("tab");
                        switch(obj.id) {
                            case "refresh":
                                that.refreshTab(index, tab);
                                break;
                            case "close":
                                that.closeTab(index);
                                break;
                            case "closeAll":
                                that.closeAllTabs();
                                break;
                            case "closeOther":
                                that.closeOtherTabs(index);
                                break;
                            case "closeRight":
                                that.closeRightTabs(index);
                                break;
                            case "closeLeft":
                                that.closeLeftTabs(index);
                                break;
                        }
                    },

                    onShow: function() {
                        var menu = $(this),
                            tab = menu.data("tab"),
                            opts = tab && tab.data("panel") && tab.panel("options");

                        if(opts && !opts.closable) {
                            menu.find(".menu-item").filter(function() {
                                return "close" === $(this).attr("id")
                            }).each(function() {
                                menu.menu("disableItem", this);
                            });
                        }
                    },

                    onHide: function() {
                        var menu = $(this);
                        menu.find(".menu-item").each(function() {
                            menu.menu("enableItem", this);
                        });
                    }
                });
            },
            tabsTemplate: artTemplate.compile(
                '<div class="kui-tabs" kui-options="fit:true">' +
                    '{{each tabsData}}' +
                    '<div kui-options="{{if $value.id}}id:\'{{$value.id}}\',{{/if}}title:\'{{$value.title}}\',closable:\'{{if $value.closable}}true{{else}}{{/if}}\',iconCls:\'{{$value.iconCls}}\',href:\'{{$value.href}}\'"></div>' +
                    '{{/each}}' +
                '</div>'
            ),
            fixLayout: function() {
                var that = this,
                    scrollTop = $(window).scrollTop(),
                    pos = that.$layout.position();

                if(scrollTop > pos.top) {
                    !that.$layout.hasClass("fixed") && that.$layout.addClass("fixed");
                } else {
                    that.$layout.removeClass("fixed");
                }
            },

            isVisible: function() {
                return this.$content.is(":visible");
            },

            show: function() {
                this.$content.show();
                //解决content切换的时候，由于未知原因导致的页面空白问题。
                var $container = this.$content.find(".container");
                if($container.height() == 0){
                    $container.height(this.getContentHeight());
                }
                $container.find("iframe").each(function () {
                    var iframe = $(this)[0];
                    if(iframe){
                        iframe.style.height = "99%";
                        iframe.scrollWidth;
                        iframe.style.height = "100%";
                    }
                });
                this.$layout.layout("resize");
            },

            hide: function() {
                this.$content.hide();
            },

            destroy: function(){
                this.$content.remove();
            },

            collapseWest: function() {
                this.$layout.layout("collapse", "west");
            },

            expandWest: function() {
                this.$layout.layout("expand", "west");
            },

            refreshTab: function(index, tab) {
                var that = this,
                    global = that.getWindow(index);

                this.$tabs.tabs("select", index);
                that.unloadPanel(global).then(function() {
                    _.isFunction(global.onunload) && global.onunload();
                    tab.panel("refresh");
                });
            },

            closeTab: function(index, forceClose) {
                var that = this,
                    tab = null == index ? that.$tabs.tabs("getSelected") : that.$tabs.tabs("getTab", index),
                    opts;
                if(tab.data("panel")){
                    opts = tab.panel("options");
                    if(forceClose || opts.closable) {
                        var global = that.getWindow(tab);
                        that.unloadPanel(global).then(function() {
                            _.isFunction(global.onunload) && global.onunload();
                            that.$tabs.tabs("close", null == index ? that.$tabs.tabs("getTabIndex", tab) : index);
                        });
                    }
                }
            },

            getWindow: function(arg) {
                var $tab = (_.isNumber(arg) && this.$tabs.tabs("getTab", arg)) || arg,
                    $iframe = $tab.find("iframe");
                return $iframe[0] && $iframe[0].contentWindow;
            },

            unloadPanel: function(windowObj) {
                if(!windowObj) {
                    return $.when(true);
                }

                var defObj = $.Deferred(),
                    beforeRet = _.isFunction(windowObj.onbeforeunload) && windowObj.onbeforeunload();

                if(_.isString(beforeRet) && beforeRet) {
                    top.confirm(beforeRet, function(flag) {
                        if(!flag) {
                            defObj.reject(false);
                            return;
                        }
                        defObj.resolve(true);
                    });
                    return defObj.promise();
                }

                return $.when(true);
            },

            closeAllTabs: function() {
                var len = this.$tabs.tabs("tabs").length;
                while((--len) >= 0) {
                    this.closeTab(len);
                }
            },

            closeOtherTabs: function(index) {
                var len = this.$tabs.tabs("tabs").length;
                while((--len) >= 0) {
                    if(len !== index) {
                        this.closeTab(len);
                    }
                }
                this.$tabs.tabs("select", 1);
            },

            closeRightTabs: function(index) {
                var len = this.$tabs.tabs("tabs").length;
                while((--len) > index) {
                    this.closeTab(len);
                }
                var len = this.$tabs.tabs("tabs").length;
                this.$tabs.tabs("select", len-1);
            },

            closeLeftTabs: function(index) {
                var len = this.$tabs.tabs("tabs").length;
                while((--index) >= 0) {
                    this.closeTab(index);
                }
                this.$tabs.tabs("select", 1);
            },

            setLayoutSize: function(width, height) {
                var currentHeight = this.$layout.data("height"),
                    currentWidth = this.$layout.data("width");

                //没有检测到高度变化不做任何处理
                if(width === currentWidth && height === currentHeight) {
                    return;
                }

                //设置新的高度
                this.$layout.data("height", height).data("width", width).width(width).height(height).layout("resize");
            },

            resizeLayout: function() {
                var that = this,
                    height = (function() {
                        var isTabPanel = !!that.$tabs,
                            panel = isTabPanel ? that.$tabs.tabs("getSelected") : that.$centerPanel,
                            $iframe = $(panel.find("iframe")),
                            difHeight = isTabPanel ? that.tabsHeight : 0,
                            contentHeight = that.getContentHeight(),
                            height;

                        if(!$iframe.length || !$iframe[0].contentDocument) {
                            return contentHeight;
                        }

                        height = $($iframe[0].contentDocument.body).height() + difHeight;

                        return height < contentHeight ? contentHeight : height;
                    })(),
                    width = that.$layout.parent().width();

                that.setLayoutSize(width, height);
            },

            getContentHeight: function() {
                var tmpHeight = $(window).height() - this.headerHeight - this.footerHeight - this.marginHeight/* + this.difHeight*/;
                return tmpHeight < this.minHeight ? this.minHeight : tmpHeight;
            },

            //销毁右侧panel
            destroyCenterPanel: function() {
                this.$layout.layout("remove", "center");
                this.$layout.layout("add", {region: "center"});
                this.$centerPanel = this.$layout.length && this.$layout.layout("panel", "center");
                this.$tabs = null;
            },

            //设置左侧标题
            setWestTitle: function(title) {
                var expandWest = this.$layout.layout("panel", "expandWest");
                this.$westPanel.panel("setTitle", title);
                if(expandWest && expandWest.length) {
                    expandWest.html((function(str) {
                        var spt = str.split(""),
                            ret = [];
                        $.each(spt, function() {
                            ret.push(this);
                            ret.push("<br>");
                        });

                        return ret.join("");
                    })(title));
                }
            },

            //设置右侧的tabs组件
            setTabs: function(tabsData) {
                var that = this,
                    fullScreenAble = tabsData.fullScreenAble || false;
                tabsData = _.map(_.isArray(tabsData) ? tabsData : [tabsData], function(data) {
                    if(data.href) {
                        data.href = uri.buildUrl(data.href, {
                            noscroll: "1" === top.kjdp.AUTO_SCROLL,
                            debug: urlParam.debug
                        });
                    }

                    return data;
                });

                that.destroyCenterPanel();
                that.$centerPanel.append(that.tabsTemplate({
                    tabsData: tabsData
                }));
                that.onCloseTab = $.isFunction(tabsData[0].onClose) && tabsData[0].onClose;
                var currIndex;
                that.$tabs = that.$centerPanel.find(".kui-tabs").tabs({
                    maxTitleWidth: 150,
                    onContextMenu: function(e, title, index) {
                        e.preventDefault();

                        var tab = that.$tabs.tabs("getTab", index),
                            opts;

                        if(tab.data("panel") && (opts = tab.panel("options"))) {
                            that.$menu.data("tab", tab).data("index", index).menu("show", {
                                left: e.pageX,
                                top: e.pageY
                            });
                        }
                    },
                    onSelect: function (title, index) {
                        if(currIndex === index){
                            return;
                        }
                        that.setLayoutSize(that.$layout.parent().width(), that.getContentHeight());
                        currIndex = index;
                    },
                    fullScreenAble : fullScreenAble,  //是否允许全屏
                    onClose: function(title, index){
                        _.isFunction(that.onCloseTab) && that.onCloseTab.call(this,title, index);
                    }
                });

                that.$tabs.find("ul.tabs").bind("dblclick", function(e) {
                    var isSpan = e.target.tagName.toLowerCase() === "span",
                        isA = e.target.tagName.toLowerCase() === "a",
                        $a = isSpan ? $(e.target).parent() : $(e.target),
                        $li = $a.parent();

                    if(!$a.hasClass("tabs-inner")) {
                        return;
                    }

                    if(!$a.next("a.tabs-close").length) {
                        return;
                    }

                    that.closeTab($li.prevAll("li").length);
                });
            },

            //增加一个tab页签
            addTab: function(opts, param) {
                var that = this;
                if(!that.$tabs || !that.$tabs.length) {
                    that.setTabs({});
                    that.$tabs.tabs("close",0);
                }

                if(!opts.title) {
                    $.error("请传入title属性！");
                }

                if(!opts.href) {
                    $.error("请传入href属性！");
                }

                var isId = !!opts.id,
                    tab = that.$tabs.tabs(isId ? "getTabById" : "getTab", isId ? opts.id : opts.title);


                param = $.extend({
                    noscroll: uri.getUrlParam(opts.href).noscroll || ("1" === top.kjdp.AUTO_SCROLL),
                    debug: urlParam.debug
                }, param);

                //20160524 linbk 修改了如果已存在tab但是传了refresh为true时，刷新tab页面
                if(tab && tab.length) {
                    that.$tabs.tabs("select", that.$tabs.tabs("getTabIndex", tab));
                    param.refresh && tab.panel("refresh", uri.buildUrl(opts.href, param));
                    return;
                }
                that.$tabs.tabs("add", $.extend({
                    closable: true,
                    fullScreenAble : top.kjdp.isSmallScreen   //是否允许全屏
                }, opts, {
                    href: uri.buildUrl(opts.href, param)
                }));
            },

            //获取选中的panel
            getSelectedTab: function(){
                return this.$tabs.tabs("getSelected");
            },

            // 刷新选中的panel
            refreshSelectedTab: function () {
                var curTab = this.$tabs.tabs("getSelected");
                return this.refreshTab(this.$tabs.tabs("getTabIndex", curTab), curTab);
            },

            //修改tab标题
            updateTabTitle: function(title, index){
                var tab = _.isUndefined(index) ? this.$tabs.tabs("getSelected") : this.$tabs.tabs("getTab", index);
                this.$tabs.tabs("update", {
                    "tab":tab,
                    "options":{
                        "title":title
                    }
                });
            },

            //获取tabs组件
            getTabs: function(){
                return this.$tabs;
            },

            getTabIndex: function(tabPanel) {
                return this.$tabs.tabs("getTabIndex", tabPanel);
            },

            selectTab: function(index) {
                return this.$tabs.tabs("select", index);
            },

            getTabLength: function() {
                var $tabs = this.$tabs.tabs("tabs") || $();
                return $tabs.length;
            }

        });

    return ContentView;
})
