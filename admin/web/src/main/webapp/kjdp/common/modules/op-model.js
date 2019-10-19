/**
 * 柜员信息模型
 * @author chenk
 */
define(function (require, exports, module) {
    //操作员模型
    return Backbone.Model.extend({
        defaults: {
            OP_CODE: "",
            OP_NAME: "",
            ORG_FULL_NAME: "",
            ORG_CODE: "",
            ORG_NAME: "",
            POST_NAME: "",
            POST_DATA: [],
        },
        setOpInfo: function(opInfo) {
            var that = this;
            if(opInfo) {
                var mainPostObj = opInfo["POST_DATA"] && opInfo["POST_DATA"].length > 0 && (_.find(opInfo["POST_DATA"], function(v) {
                    return v.MAIN_FLAG == "1"
                }) || opInfo["POST_DATA"][0]) || {};
                that.set({
                    "OP_CODE": opInfo["P_OP_CODE"] || "",
                    "OP_NAME": opInfo["P_OP_NAME"] || "",
                    "ORG_FULL_NAME": opInfo["P_OP_ORG_FNAME"] || "",
                    "ORG_NAME": opInfo["P_OP_ORG_NAME"] || "",
                    "ORG_CODE": opInfo["P_OP_ORG"] || "",
                    "POST_NAME": mainPostObj.POST_NAME || "",
                    "POST_DATA": opInfo["POST_DATA"] || []
                });
            }
        }
    });

})
