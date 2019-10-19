/**
 * 业务公共模块
 * @author chenk
 */
define(function (require, exports, module) {


    //扩展kjdp变量
    function gExpose(obj) {
        $.extend(top.kjdp, obj);
    }

    //扩展window
    function gExposeGlobal(obj) {
        $.extend(top, obj);
    }

    function initCommonCache(sysParams) {
        //加载公共参数缓存
        sysParams && gExpose(param.getSysParamInfoObj(sysParams));
        //加载公共数据缓存
        return ajax.post({
            service: "P0000009",
            SYS_STATUS: "0"
        }).then(function(result) {
            var data = result.getData()[0] || [];
            cache.setData("c_system", data);
        })
    }

    return {
        initCommonCache: initCommonCache,
        gExposeGlobal : gExposeGlobal,
        gExpose : gExpose
    }

});

