/**
 * 菜单模块
 * @author chenk
 */
define(function (require, exports, module) {
    var menuService = require("../service/menu-service");

    return Backbone.Model.extend({
        defaults: {
            menuArr: []
        },
        fetchMenuData: function(param) {
            param = param || {}
            var that = this;
            return menuService.getMenuData(param).then(function(data) {
                that.set("menuArr", data);
            })
        },

        getTopMenuData: function() {
            return _.chain(this.get("menuArr")).filter(function(v) {
                return "0" == v.PAR_MENU
            }).value();
        },

        getMenuData: function(menuId) {
            var menuArr = this.get("menuArr");
            if(!menuId) {
                return menuArr;
            }

            return _.chain(menuArr).find(function(v) {
                return v.MENU_ID == menuId;
            }).value();
        },

        getChildMenuData: function(menuId) {
            var that = this,
                child = _.chain(that.get("menuArr")).filter(function(v) {
                    return v.PAR_MENU == menuId;
                }).value();

            if(!child.length) {
                return [];
            }

            return _.chain(
                child.concat(
                    _.chain(child).map(function(v) {
                        return that.getChildMenuData(v.MENU_ID);
                    }).value()
                )
            ).flatten().value();
        },

        getRootMenu: function(menuId) {
            var that = this,
                menuObj;
            if(!menuId || !(menuObj = that.getMenuData(menuId))) {
                return;
            }

            if(menuObj.PAR_MENU == "0") {
                return menuObj
            }

            return that.getRootMenu(menuObj.PAR_MENU);
        }
    })

});