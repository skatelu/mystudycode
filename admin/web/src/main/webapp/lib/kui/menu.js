/**
 * menu组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define("lib/kui/menu", [], function(require, exports, module) {
    /**
	 *  **menu组件初始化**
	 *
	 *
	 *  方法如下:
	 *  ```html
	 *		<div id="mm" class="kui-menu" style="width:120px;">
	 *			 <div onclick="javascript:alert('new')">New</div>
	 *			 <div>
	 *			 <span>Open</span>
	 *			 <div style="width:150px;">
	 *			 <div><b>Word</b></div>
	 *			 <div>Excel</div>
	 *			 <div>PowerPoint</div>
	 *			 </div>
	 *			 </div>
	 *			 <div kui-options="iconCls:'icon-save'">Save</div>
	 *			 <div kui-options="iconCls:'icon-print',disabled:true">Print</div>
	 *			 <div class="menu-sep"></div>
	 *			 <div>Exit</div>
	 *       </div>
	 *  ```
	 *  ```javascript
	 *    $(document).bind('contextmenu',function(e){
	 *		e.preventDefault();
	 *		$('#mm').menu('show', {
	 *			left: e.pageX,
	 *			top: e.pageY
	 *		});
	 *	});
	 *
	 *  ```
	 *  **menu属性及事件**
	 *
	 *  ***使用$.fn.menu.defaults重写默认值对象。***
	 * @Method menu
	 * @param {object} option  设置参数
	 * @param {number} option.zIndex=110000   菜单z-index样式，增加它的值。
	 * @param {number} option.left=0  菜单的左边距位置。
	 * @param {number} option.top=0  菜单的上边距位置。
	 * @param {number} option.minWidth=120   菜单的最小宽度。
	 *
	 * @Method menu
	 * @param {object} option  设置参数
	 * @param {function} option.onShow  在菜单显示之后触发。
	 * @param {function} option.onHide  在菜单隐藏之后触发。
	 * @param {function} option.onClick  在菜单项被点击的时候触发。
	 * @example
	 *  onClick例子：
	 *<div id="mm" class="kui-menu" kui-options="onClick:'menuHandler'" style="width:120px;">
	 *	  <div kui-options="name:'new'">New</div>
	 *	  <div kui-options="name:'exit'">Exit</div>
	 *</div>
	 *
	 * exports.menuHandler = function(item){
     *    alert(item.name);
     * }
	 * @returns {*} 返回jQuery组件对象
	 */
    $.fn.menu = function(options, param) {
        if (typeof options == "string") {
            return $.fn.menu.methods[options](this, param);
        }
        options = options || {};
        return this.each(function() {
            var state = $.data(this, "menu");
            if (state) {
                $.extend(state.options, options);
            } else {
                state = $.data(this, "menu", {
                    options: $.extend({}, $.fn.menu.defaults, $.fn.menu.parseOptions(this), options)
                });
                init(this);
            }
            $(this).css({
                left: state.options.left,
                top: state.options.top
            });
        });
    };
    $.fn.menu.methods = {
        /**
		 * **获取组件配置方法参数**
		 * @method options
		 * @returns {*} 返回属性对象。
		 */
        options: function(jq) {
            return $.data(jq[0], "menu").options;
        },
        /**
		 *  **显示菜单到指定的位置**
		 * @method show
		 * @param {object} pos 参数有2个属性：left：新的左边距位置。top：新的上边距位置。
		 * @returns {*}  返回组件jQuery对象
		 */
        show: function(jq, pos) {
            return jq.each(function() {
                showMenu(this, pos);
            });
        },
        /**
		 *
		 * **隐藏菜单**
		 *
		 * @method hide
		 * @returns {*}   返回组件jQuery对象
		 */
        hide: function(jq) {
            return jq.each(function() {
                hideAll(this);
            });
        },
        /**
		 * **销毁菜单**
		 * @method destroy
		 * @returns {*}    返回组件jQuery对象
		 */
        destroy: function(jq) {
            return jq.each(function() {
                destroyMenu(this);
            });
        },
        /**
		 *  **设置指定菜单项的文本**
		 *  ```javascript
		 *   var item = $('#mm').menu('findItem', '保存');
		 *		$('#mm').menu('setText', {
		 *		target: item.target,
		 *		text: '修改'
		 *	});
		 *  ```
		 *  @method  setText
		 *  @param {object} param  参数包含2个属性：target: DOM对象，要设置值的菜单项。text: 字符串，要设置的新文本值
		 *  @returns {*}    返回组件jQuery对象
		 */
        setText: function(jq, param) {
            return jq.each(function() {
                $(param.target).children("div.menu-text").html(param.text);
            });
        },
        /**
		 *   **设置指定菜单项图标**
		 *   ```javascript
		 *   $('#mm').menu('setIcon', {
		 *		target: $('#m-open')[0],
		 *		iconCls: 'icon-closed'
		 *	});
		 *   ```
		 *   @method setIcon
		 *   @param {object} param  参数包含2个属性：target: DOM对象，要设置的菜单项 iconCls: 新的图标CSS类ID
		 *   @returns {*}    返回组件jQuery对象
		 */
        setIcon: function(jq, param) {
            return jq.each(function() {
                var item = $(this).menu("getItem", param.target);
                if (item.iconCls) {
                    $(item.target).children("div.menu-icon").removeClass(item.iconCls).addClass(param.iconCls);
                } else {
                    $('<div class="menu-icon ui-tool-icons"></div>').addClass(param.iconCls).appendTo(param.target);
                }
            });
        },
        /**
		 *   **获取指定的菜单项,得到的是一个菜单项的DOM元素**
		 *
		 *   如何根据ID获取指定的项：
		 *   ```html
		 *    <div id="mm" class="easyui-menu" style="width:120px">
		 *		 <div>New</div>
		 *		 <div id="m-open">Open</div>
		 *		 <div>Save</div>
		 *    </div>
		 *   ```
		 *   ```javascript
		 *   var itemEl = $('#m-open')[0];  // 获取菜单项
		 *   var item = $('#mm').menu('getItem', itemEl);
		 *   console.log(item);
		 *   ```
		 *   @method getItem
		 *   @param {object} itemEl 参数
		 *   @returns {*}    返回组件jQuery对象
		 */
        /*
		 * 	target: DOM object, the menu item
		 *  id: the menu id
		 * 	text: the menu item text
		 * 	iconCls: the icon class
		 *  href: a remote address to redirect to
		 *  onclick: a function to be called when the item is clicked
		 *
		 */
        getItem: function(jq, itemEl) {
            var t = $(itemEl);
            var item = {
                target: itemEl,
                id: t.attr("id"),
                text: $.trim(t.children("div.menu-text").html()),
                disabled: t.hasClass("menu-item-disabled"),
                href: t.attr("href"),
                name: t.attr("name"),
                onclick: itemEl.onclick
            };
            var icon = t.children("div.menu-icon");
            if (icon.length) {
                var cc = [];
                var aa = icon.attr("class").split(" ");
                for (var i = 0; i < aa.length; i++) {
                    if (aa[i] != "menu-icon") {
                        cc.push(aa[i]);
                    }
                }
                item.iconCls = cc.join(" ");
            }
            return item;
        },
        /**
		 *  **查找的指定菜单项，返回的对象和getItem方法是一样的**
		 *  ```javascript
		 *  // 查找“打开”项并禁用它
	     *	var item = $('#mm').menu('findItem', '打开');
		 *	$('#mm').menu('disableItem', item.target);
		 *
		 * ```
		 * @method findItem
		 * @param {object} text 参数
		 * @returns {*} 返回组件jQuery对象
		 */
        findItem: function(jq, text) {
            return findItem(jq[0], text);
        },
        /**
		 *  **追加新的菜单项**
		 *  ```javascript
		 *   // 追加一个顶部菜单
		 *	 $('#mm').menu('appendItem', {
		 * 		text: '新菜单项',
		 *		iconCls: 'icon-ok',
		 *		onclick: function(){alert('提示：新菜单项！')}
		 *	});
		 *
		 *   // 追加一个子菜单项
		 *   var item = $('#mm').menu('findItem', '打开');  // 查找“打开”项
		 *   $('#mm').menu('appendItem', {
		 *		parent: item.target,  // 设置父菜单元素
		 *		text: '打开Excel文档',
		 *		iconCls: 'icon-excel',
		 *		onclick: function(){alert('提示：打开Excel文档！')}
		 *	});
		 *  ```
		 *  @method  appendItem
		 *  @param {object} param 参数代表新菜单项的属性
		 *  @return {*} 返回组件jQuery对象
		 */
        /*
		 * parent,id,text,iconCls,href,onclick
		 * when parent property is assigned, append menu item to it
		 */
        appendItem: function(jq, param) {
            return jq.each(function() {
                appendItem(this, param);
            });
        },
        /**
		 *  **移除指定的菜单项**
		 * @method removeItem
		 * @param {object} itemEl 参数
		 * @returns {*} 返回组件jQuery对象
		 */
        removeItem: function(jq, itemEl) {
            return jq.each(function() {
                removeItem(this, itemEl);
            });
        },
        /**
		 * **启用菜单项**
		 * @method enableItem
		 * @param {object} itemEl 参数
		 * @returns {*}  返回组件jQuery对象
		 */
        enableItem: function(jq, itemEl) {
            return jq.each(function() {
                setDisabled(this, itemEl, false);
            });
        },
        /**
		 * **禁用菜单项**
		 *
		 * @method disableItem
		 * @param {object} itemEl 参数
		 * @returns {*}  返回组件jQuery对象
		 */
        disableItem: function(jq, itemEl) {
            return jq.each(function() {
                setDisabled(this, itemEl, true);
            });
        }
    };
    $.fn.menu.parseOptions = function(target) {
        return $.extend({}, $.parser.parseOptions(target, [ "left", "top", {
            minWidth: "number"
        } ]));
    };
    $.fn.menu.defaults = {
        zIndex: 11e4,
        left: 0,
        top: 0,
        minWidth: 120,
        onShow: function() {},
        onHide: function() {},
        onClick: function(item) {}
    };
    /**
	 * initialize the target menu, the function can be invoked only once
	 */
    function init(target) {
        $(target).appendTo("body");
        $(target).addClass("menu-top");
        // the top menu
        $(document).unbind(".menu").bind("mousedown.menu", function(e) {
            var allMenu = $("body>div.menu:visible");
            var m = $(e.target).closest("div.menu", allMenu);
            if (m.length) {
                return;
            }
            $("body>div.menu-top:visible").menu("hide");
        });
        var menus = splitMenu($(target));
        for (var i = 0; i < menus.length; i++) {
            createMenu(menus[i]);
        }
        function splitMenu(menu) {
            var menus = [];
            menu.addClass("menu");
            if (!menu[0].style.width) {
                menu[0].autowidth = true;
            }
            menus.push(menu);
            if (!menu.hasClass("menu-content")) {
                menu.children("div").each(function() {
                    var submenu = $(this).children("div");
                    if (submenu.length) {
                        submenu.insertAfter(target);
                        this.submenu = submenu;
                        // point to the sub menu
                        var mm = splitMenu(submenu);
                        menus = menus.concat(mm);
                    }
                });
            }
            return menus;
        }
        function createMenu(menu) {
            if (!menu.hasClass("menu-content")) {
                menu.children("div").each(function() {
                    var item = $(this);
                    if (!item.hasClass("menu-sep")) {
                        var itemOpts = $.extend({}, $.parser.parseOptions(this, [ "name", "iconCls", "href" ]), {
                            disabled: item.attr("disabled") ? true : undefined
                        });
                        item.attr("name", itemOpts.name || "").attr("href", itemOpts.href || "");
                        var text = item.addClass("menu-item").html();
                        item.empty().append($('<div class="menu-text"></div>').html(text));
                        if (itemOpts.iconCls) {
                            $('<div class="menu-icon ui-tool-icons"></div>').addClass(itemOpts.iconCls).appendTo(item);
                        }
                        if (itemOpts.disabled) {
                            setDisabled(target, item[0], true);
                        }
                        if (item[0].submenu) {
                            $('<div class="menu-rightarrow"></div>').appendTo(item);
                        }
                        //item._outerHeight(22); @zhaozz 2015-12-02 
                        bindMenuItemEvent(target, item);
                    }
                });
                $('<div class="menu-line"></div>').prependTo(menu);
            }
            setMenuWidth(target, menu);
            menu.hide();
            bindMenuEvent(target, menu);
        }
    }
    function setMenuWidth(target, menu) {
        var opts = $.data(target, "menu").options;
        var d = menu.css("display");
        menu.css({
            display: "block",
            left: -1e4
        });
        var width = menu._outerWidth();
        var autoWidth = 0;
        menu.find("div.menu-text").each(function() {
            if (autoWidth < $(this)._outerWidth()) {
                autoWidth = $(this)._outerWidth();
            }
        });
        //autoWidth += 65;  @zhaozz 2015-12-02
        menu._outerWidth(Math.max(width, autoWidth, opts.minWidth));
        menu.css("display", d);
    }
    /**
	 * bind menu event
	 */
    function bindMenuEvent(target, menu) {
        var state = $.data(target, "menu");
        menu.unbind(".menu").bind("mouseenter.menu", function() {
            if (state.timer) {
                clearTimeout(state.timer);
                state.timer = null;
            }
        }).bind("mouseleave.menu", function() {
            state.timer = setTimeout(function() {
                hideAll(target);
            }, 100);
        });
    }
    /**
	 * bind menu item event
	 */
    function bindMenuItemEvent(target, item) {
        item.unbind(".menu");
        item.bind("click.menu", function() {
            if ($(this).hasClass("menu-item-disabled")) {
                return;
            }
            var item = $(target).menu("getItem", this);
            $.data(target, "menu").options.onClick.call(target, item);
            // only the sub menu clicked can hide all menus
            if (!this.submenu) {
                hideAll(target);
                var href = $(this).attr("href");
                if (href) {
                    location.href = href;
                }
            }
        }).bind("mouseenter.menu", function(e) {
            // hide other menu
            item.siblings().each(function() {
                if (this.submenu) {
                    hideMenu(this.submenu);
                }
                $(this).removeClass("menu-active");
            });
            // show this menu
            item.addClass("menu-active");
            if ($(this).hasClass("menu-item-disabled")) {
                item.addClass("menu-active-disabled");
                return;
            }
            var submenu = item[0].submenu;
            if (submenu) {
                $(target).menu("show", {
                    menu: submenu,
                    parent: item
                });
            }
        }).bind("mouseleave.menu", function(e) {
            item.removeClass("menu-active menu-active-disabled");
            var submenu = item[0].submenu;
            if (submenu) {
                if (e.pageX >= parseInt(submenu.css("left"))) {
                    item.addClass("menu-active");
                } else {
                    hideMenu(submenu);
                }
            } else {
                item.removeClass("menu-active");
            }
        });
    }
    /**
	 * hide top menu and it's all sub menus
	 */
    function hideAll(target) {
        var state = $.data(target, "menu");
        if (state) {
            if ($(target).is(":visible")) {
                hideMenu($(target));
                state.options.onHide.call(target);
            }
        }
        return false;
    }
    /**
	 * show the menu, the 'param' object has one or more properties:
	 * left: the left position to display
	 * top: the top position to display
	 * menu: the menu to display, if not defined, the 'target menu' is used
	 * parent: the parent menu item to align to
	 * alignTo: the element object to align to
	 */
    function showMenu(target, param) {
        var left, top;
        var menu = $(param.menu || target);
        if (menu.hasClass("menu-top")) {
            var opts = $.data(target, "menu").options;
            left = opts.left;
            top = opts.top;
            if (param.alignTo) {
                var at = $(param.alignTo);
                left = at.offset().left;
                top = at.offset().top + at._outerHeight();
            }
            if (param.left != undefined) {
                left = param.left;
            }
            if (param.top != undefined) {
                top = param.top;
            }
            if (left + menu.outerWidth() > $(window)._outerWidth() + $(document)._scrollLeft()) {
                left = $(window)._outerWidth() + $(document).scrollLeft() - menu.outerWidth() - 5;
            }
            if (top + menu.outerHeight() > $(window)._outerHeight() + $(document).scrollTop()) {
                top -= menu.outerHeight();
            }
        } else {
            var parent = param.parent;
            // the parent menu item
            left = parent.offset().left + parent.outerWidth() - 2;
            if (left + menu.outerWidth() + 5 > $(window)._outerWidth() + $(document).scrollLeft()) {
                left = parent.offset().left - menu.outerWidth() + 2;
            }
            var top = parent.offset().top - 3;
            if (top + menu.outerHeight() > $(window)._outerHeight() + $(document).scrollTop()) {
                top = $(window)._outerHeight() + $(document).scrollTop() - menu.outerHeight() - 5;
            }
        }
        menu.css({
            left: left,
            top: top
        });
        menu.show(0, function() {
            if (!menu[0].shadow) {
                menu[0].shadow = $('<div class="menu-shadow"></div>').insertAfter(menu);
            }
            menu[0].shadow.css({
                display: "block",
                zIndex: $.fn.menu.defaults.zIndex++,
                left: menu.css("left"),
                top: menu.css("top"),
                width: menu.outerWidth(),
                height: menu.outerHeight()
            });
            menu.css("z-index", $.fn.menu.defaults.zIndex++);
            if (menu.hasClass("menu-top")) {
                $.data(menu[0], "menu").options.onShow.call(menu[0]);
            }
        });
    }
    function hideMenu(menu) {
        if (!menu) return;
        hideit(menu);
        menu.find("div.menu-item").each(function() {
            if (this.submenu) {
                hideMenu(this.submenu);
            }
            $(this).removeClass("menu-active");
        });
        function hideit(m) {
            m.stop(true, true);
            if (m[0].shadow) {
                m[0].shadow.hide();
            }
            m.hide();
        }
    }
    function findItem(target, text) {
        var result = null;
        var tmp = $("<div></div>");
        function find(menu) {
            menu.children("div.menu-item").each(function() {
                var item = $(target).menu("getItem", this);
                var s = tmp.empty().html(item.text).text();
                if (text == $.trim(s)) {
                    result = item;
                } else if (this.submenu && !result) {
                    find(this.submenu);
                }
            });
        }
        find($(target));
        tmp.remove();
        return result;
    }
    function setDisabled(target, itemEl, disabled) {
        var t = $(itemEl);
        if (disabled) {
            t.addClass("menu-item-disabled");
            if (itemEl.onclick) {
                itemEl.onclick1 = itemEl.onclick;
                itemEl.onclick = null;
            }
        } else {
            t.removeClass("menu-item-disabled");
            if (itemEl.onclick1) {
                itemEl.onclick = itemEl.onclick1;
                itemEl.onclick1 = null;
            }
        }
    }
    function appendItem(target, param) {
        var menu = $(target);
        if (param.parent) {
            if (!param.parent.submenu) {
                var submenu = $('<div class="menu"><div class="menu-line"></div></div>').appendTo("body");
                submenu[0].autowidth = true;
                submenu.hide();
                param.parent.submenu = submenu;
                $('<div class="menu-rightarrow"></div>').appendTo(param.parent);
            }
            menu = param.parent.submenu;
        }
        var item = $('<div class="menu-item"></div>').appendTo(menu);
        $('<div class="menu-text"></div>').html(param.text).appendTo(item);
        if (param.iconCls) $('<div class="menu-icon ui-tool-icons"></div>').addClass(param.iconCls).appendTo(item);
        if (param.id) item.attr("id", param.id);
        if (param.href) item.attr("href", param.href);
        if (param.name) item.attr("name", param.name);
        if (param.onclick) {
            if (typeof param.onclick == "string") {
                item.attr("onclick", param.onclick);
            } else {
                item[0].onclick = eval(param.onclick);
            }
        }
        if (param.handler) item[0].onclick = eval(param.handler);
        bindMenuItemEvent(target, item);
        if (param.disabled) {
            setDisabled(target, item[0], true);
        }
        bindMenuEvent(target, menu);
        setMenuWidth(target, menu);
    }
    function removeItem(target, itemEl) {
        function removeit(el) {
            if (el.submenu) {
                el.submenu.children("div.menu-item").each(function() {
                    removeit(this);
                });
                var shadow = el.submenu[0].shadow;
                if (shadow) shadow.remove();
                el.submenu.remove();
            }
            $(el).remove();
        }
        removeit(itemEl);
    }
    function destroyMenu(target) {
        $(target).children("div.menu-item").each(function() {
            removeItem(target, this);
        });
        if (target.shadow) target.shadow.remove();
        $(target).remove();
    }
});
