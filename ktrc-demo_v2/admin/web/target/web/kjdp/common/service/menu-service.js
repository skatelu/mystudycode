/**
 * 菜单服务
 * @author chenk
 */
define(function (require, exports, module) {

    var MENU_LOCAL_STORE_KEY = "OP_MENU_DATA",
        OP_CACHE_MENU_NUM = 15;

    function getMenuData(param) {
        return ajax.post($.extend({}, param || {}, {
            service: "P0000008"
        })).then(function(result) {
            return result.getData()[0] || [];
        });
    }

    function getAllMenuData(param) {
        return ajax.post($.extend({}, param || {}, {
            service: "P0000008"
        })).then(function(result) {
            return result.getData()[0] || [];
        });
    }

    function addMenu(menuData) {
        return ajax.post($.extend({}, menuData || {}, {
            service: "P0000029"
        })).then(function(result) {
            return result.getData()[0] || [];
        });
    }

    function updateMenuInfo(menuId, menuData) {
        return ajax.post($.extend({}, menuData || {}, {
            service: "P0000030",
            MENU_ID: menuId
        }));
    }

    function removeMenu(menuId) {
        return ajax.post({
            MENU_ID: menuId,
            service: "P0000031"
        });
    }

    function moveMenu(selfMenuId, parMenuId, desPreMenuId) {
        return $.Deferred().resolve([]);
    }

    //获得柜员常用菜单
    function getOpCommMenu(opCode, params) {
        return $.Deferred().resolve([]);
    }

    //移除柜员常用菜单
    function removeOpCommMenu(opCode, menuId) {
        return $.Deferred().resolve([]);
    }

    function addOpCommMenu(opCode, menuId) {
        return $.Deferred().resolve([]);
    }

    function saveMenuRecord(opCode, menuObj) {
        var allOpMenuData = getMenuRecord(),
            opMenuData = _.filter(allOpMenuData[opCode] || [], function(v) {
                return v.MENU_ID != menuObj.MENU_ID;
            });
        if(opMenuData.length > OP_CACHE_MENU_NUM) {
            opMenuData.pop();
        }
        opMenuData.unshift(menuObj);
        allOpMenuData[opCode] = opMenuData;
        cache.setLocalStorage(MENU_LOCAL_STORE_KEY, utils.stringify(allOpMenuData));
    }

    function getMenuRecord(opCode) {
        var allOpMenuData = cache.getLocalStorage(MENU_LOCAL_STORE_KEY) && utils.parse(cache.getLocalStorage(MENU_LOCAL_STORE_KEY)) || {};
        return opCode ? allOpMenuData[opCode] || {} : allOpMenuData;
    }

    function clearMenuRecord(opCode) {
        var allOpMenuData = getMenuRecord();
        delete allOpMenuData[opCode];
        cache.setLocalStorage(MENU_LOCAL_STORE_KEY, utils.stringify(allOpMenuData));
    }


    return {
        getOpCommMenu: getOpCommMenu,
        removeOpCommMenu: removeOpCommMenu,
        getMenuData: getMenuData,
        saveMenuRecord: saveMenuRecord,
        getMenuRecord: getMenuRecord,
        clearMenuRecord: clearMenuRecord,
        addOpCommMenu: addOpCommMenu,
        addMenu: addMenu,
        getAllMenuData: getAllMenuData,
        updateMenuInfo: updateMenuInfo,
        removeMenu: removeMenu,
        moveMenu: moveMenu
    }

})
