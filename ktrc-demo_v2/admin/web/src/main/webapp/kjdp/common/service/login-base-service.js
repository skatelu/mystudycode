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
        return ajax.login(userData).then(function(result) {
            var loginData = {
                OP_CODE: userData.OP_CODE,
                LOGIN_CODE: result.getMsgCode(),
                LOGIN_MSG: result.getMsgText()
            }
            if(isLogin(loginData)) {
                return getPostData(loginData).then(function(postData){
                    loginData.POST_DATA = postData;
                    return loginData;
                });
            }
            return loginData;
        });
    }

    //退出
    function logout() {
        ajax.logout();
    }

    function isLogin(data) {
        if(data && data.LOGIN_CODE) {
            return data.LOGIN_CODE === "0";
        } else {
            return ajax.isLogin();
        }
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
            OP_CODE: opInfo.OP_CODE || opInfo.F_OP_USER
        }).then(function(result) {
            return result.getData() && result.getData()[0] || [];
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