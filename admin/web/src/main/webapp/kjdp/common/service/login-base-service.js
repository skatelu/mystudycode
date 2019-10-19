/**
 * 登录模块
 * @author chenk
 */
define(function (require, exports, module) {

    function isNeedValidateCode() {
        return ajax.post({
            service: "P0000006"
        }).then(function(result) {
            return result.getDataRow() && result.getDataRow().MUST_VALIDATE_CODE === "1";
        })
    }

    //登录
    function login(userData) {
        return ajax.login(userData);
    }

    //退出
    function logout() {
        ajax.logout();
    }

    function isLogin() {
        return ajax.isLogin();
    }

    //获取会话
    function session() {
        var session = ajax.session();
        if(session) {
            return getPostData(session).then(function(postData) {
                session.POST_DATA = postData;
                return session;
            });
        }
        return $.Deferred().resolve(session);

    }

    //查询岗位数据
    function getPostData(opInfo){
        return ajax.post({
            service: "P0000007",
            OP_CODE: opInfo.P_OP_CODE
        }).then(function(result) {
            return result.getDataRows();
        })
    }

    return {
        logout : logout,
        session : session,
        login : login,
        isNeedValidateCode: isNeedValidateCode,
        isLogin: isLogin,
        getPostData : getPostData
    };


});