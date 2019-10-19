/**
 * 菜单自动搜索
 * @author chenk
 */
define(function(require, exports, module) {
	var AutoComp = function($element, options) {
            var objAttrData = $element.data();

            this.$el = $element;
            this.options = options || {};
            this.init();
        },
		menuService = require("../service/menu-service"),
        autoComp;

	AutoComp.prototype = {
		constructor: AutoComp,
		init: function() {
			var self = this;
			this.$el.append('<input class="validatebox-text" id="search-input" type="text" maxlength="18" placeholder="菜单名称/简拼/编号"/><div class="search-result clearfix"></div>');
			this.arrMenu = [];
			this.queryParam = $.extend({},this.options.queryParam);
			this.getMenuData().done(function(){
				self.regEvents();
			});
		},
		getMenuData: function() {
			var self = this;
			return menuService.getMenuData().then(function(data) {
                self.arrMenu = _filter(data);
            });
		},
		clean: function(){
			this.$el.find("input").val("");
			this.$el.find(".search-result").empty();
			this.$el.find(".search-result").hide();
		},
		regEvents: function() {
			var $curLi,
				arrAllMenu = this.arrMenu,
				self = this;
			this.$el.find("input").keyup(function(e) {
				var selectDom,
					searchVal = $.trim(e.target.value);

				self.$el.find("li.selected").removeClass("selected");
				if (40 === e.keyCode) { //向下						
					$curLi = ($curLi && $curLi.length > 0) ?
						$curLi.next().addClass('selected') :
						self.$el.find("li:first").addClass('selected');
				} else if (38 === e.keyCode) { //向上	
					$curLi = ($curLi && $curLi.length > 0) ?
						$curLi.prev().addClass('selected') :
						self.$el.find("li:first").addClass('selected');
				} else {
					searchVal && _buildSeachResultDom(searchVal);
					$curLi = null;
				}
			}).keydown(function(e) {
				var selectDom, objMenuData;
				if (13 === e.keyCode) { //回车
					selectDom = self.$el.find(".search-result li.selected");
					if(selectDom.length ==0){
						selectDom = self.$el.find(".search-result li:first");
					}
					objMenuData = selectDom.data();
					self.clean();
					self.openMenuLink(objMenuData);
				}
			});

			self.$el.find(".search-result").mouseover(function(e) {
				self.$el.find("li.selected").removeClass("selected");
			});

			function _buildSeachResultDom(searchVal) {
				var arrDom = [];
				$.each(arrAllMenu, function(index, m) {
					if (arrDom.length > 8) {
						return false;
					}
                    var searchFlag = false,
                        searchStr = "";
                    if(!m.HAS_CHILD){
                        if(/^[a-zA-Z]+$/.test(searchVal) && m.MENU_SPELL.match(new RegExp(".*" + searchVal.toUpperCase() + ".*")) ){
                            //拼音
                            searchFlag = true;
                            var i = m.MENU_SPELL.match(new RegExp(searchVal.toUpperCase())).index,
                                l = searchVal.length,
                                str = m.MENU_NAME.substr(i,l);
                            searchStr = m.MENU_ID + " " + m.MENU_NAME.replace(new RegExp("("+str+")"), "<strong>$1</strong>");
                        }else if(/^[0-9]+$/.test(searchVal) && m.MENU_ID.match(new RegExp(".*" + searchVal + ".*"))  ){
                            //数字
                            searchFlag = true;
                            searchStr = m.MENU_ID.replace(new RegExp("("+searchVal+")"), "<strong>$1</strong>") + " " + m.MENU_NAME;
                        }else if(m.MENU_NAME.match(new RegExp(".*" + searchVal + ".*"))){
                            //名称
                            searchFlag = true;
                            searchStr = m.MENU_ID + " " + m.MENU_NAME.replace(new RegExp("("+searchVal+")"), "<strong>$1</strong>");
                        }
                    }
                    if(searchFlag){
                        arrDom.push('<li data-menu-id="' + m.MENU_ID + '">' + searchStr + '</li>');
                    }
				});
				arrDom.length > 0 ? self.$el.find(".search-result").show() : self.$el.find(".search-result").hide();
				self.$el.find(".search-result").html('<ul>' + arrDom.join("") + '</ul>');
			}
		},
		openMenuLink: function(objMenuData) {
            //实时查询链接，不需要保存链接
            if(objMenuData){
                $(document.body).click();
                _.isFunction(top.kjdp.expandWest) && top.kjdp.expandWest();
                _.isFunction(top.kjdp.expandMenu) && top.kjdp.expandMenu(objMenuData.menuId + "");
                _.isFunction(top.kjdp.addTab) && top.kjdp.addTab({
                    id: objMenuData.menuId
                },{
					MENU_ID: objMenuData.menuId
				});
            }
		}
	};

	function _filter(arrMenu) {
		var _arr = [];
		$.each(arrMenu, function(index, menu) {
            _hasParent(arrMenu,menu) && !_hasChild(arrMenu,menu) && _arr.push(menu);
		});
		return _arr;
	}

    function _hasChild(arrMenu, menu) {
        return (_.find(arrMenu, function(m) {
            return m.PAR_MENU === menu.MENU_ID;
        })) ? true : false;
    }

    function _hasParent(arrMenu, menu) {
        return menu.PAR_MENU == '0' || (_.find(arrMenu, function(m) {
            return m.MENU_ID === menu.PAR_MENU;
        })) ? true : false;
    }

    $(document.body).on("click.menu-autosearch", ".menu-autosearch", function(e){
        e.stopPropagation();
        e.preventDefault();
        $(this).find(".search-result").empty().hide();
    }).on("click.menu-autosearch", ".search-result>ul>li", function(e) {
        autoComp && autoComp.openMenuLink($(e.currentTarget).data());
    });
    $(document.body).on("click", function() {
        autoComp && autoComp.clean();
    });

	return function($element, option) {
		$element.each(function() {
			var $this = $(this),
				data = $this.data("menu-autosearch");
			if (!data) {
				data = new AutoComp($this, $this.data());
				$this.data("menu-autosearch", data);
			}
            autoComp = data;
			if (typeof option === 'string') {
				data[option].call(data);
			}
			if (typeof option === 'object') {
				for (var key in option) {
					_.isFunction(data[key]) && data[key].call(data, option[key]);
				}
			}
		});
	};
});