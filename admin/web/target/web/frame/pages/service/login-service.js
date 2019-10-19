/**
 *  登录服务
 *  @author chenk
 */
define(function (require, exports, module) {
    var loginBaseService = require("kjdp/common/service/login-base-service");

    //打开登录页面
    function openLoginPage() {
        location.replace(resolve("frame/login.html#"));
    }
    //首页跳转
    function redirect() {
        //现在只有管理平台
        location.replace(uri.buildUrl(resolve("frame/pages/index.html#" + "")));
    }

    //退出
    function logout() {
        loginBaseService.logout();
        openLoginPage();
    }

    return {
        redirect : redirect,
        logout : logout,
        session : loginBaseService.session,
        login : loginBaseService.login,
        isNeedValidateCode: loginBaseService.isNeedValidateCode,
        isLogin: loginBaseService.isLogin,
        openLoginPage: openLoginPage,
        getPostData : loginBaseService.getPostData
    };
})
