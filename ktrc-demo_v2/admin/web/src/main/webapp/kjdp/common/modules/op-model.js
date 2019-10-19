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
                that.set({
                    "OP_CODE": opInfo["F_OP_USER"] || "",
                    "OP_NAME": opInfo["F_OP_NAME"] || "",
                    "ORG_FULL_NAME": opInfo["F_OP_ORG_FNAME"] || "",
                    "ORG_NAME": opInfo["F_OP_ORG_NAME"] || "",
                    "ORG_CODE": opInfo["F_OP_ORG"] || "",
                    "POST_NAME": opInfo["POST_NAME"] || "",
                    "POST_DATA": opInfo["POST_DATA"] || []
                });
            }
        }
    });

})
