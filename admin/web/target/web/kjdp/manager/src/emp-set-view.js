/**
 * 员工/岗位资源权限-权限设置模块
 * @author chenguiy
 */
define(function (require, exports, module) {
    var empSetTpls = require("../tpls/emp-set-tpl.js"),
        EmpSetView = Backbone.View.extend({
            initialize: function (opts) {
                var that = this;
                that.opts = $.extend({}, that.defOpts, opts);

                that.permissionData = _.filter(dict.getDictData("RES_TYPE") || [], function(v) {
                    return v.DICT_ITEM != "00";
                }); //权限对象数据字典（数组）
            },
            initTpl: function () {
                var that = this;

                that.$el.append(empSetTpls({
                    empSetTitle: this.opts.empSetTitle, //展示设置类型标题（员工/岗位）
                    girdIdPre: this.opts.girdIdPre, //datagrid的ID前缀
                    permissionData: that.permissionData //权限对象
                }));

                _.each(that.opts.treegridIdArr, function (treeId) {
                    that.$el.find("table#" + that.opts.girdIdPre + "_" + treeId).removeClass().addClass("kui-treegrid")
                });

                that.$tpls = that.$("#empSetTpl");
                that.$fieldset = that.$tpls.find("fieldset");
                return parser.parse(that.$el).then(function () {
                    that.$fieldset.fieldset({
                        closable: that.opts.closable,
                        closeCallback: function () {
                            that.closeCallback()
                        },
                        expendCallback: function () {
                            that.expendCallback()
                        }
                    });
                    that.opts.initShow && that.$tpls.show(); //渲染后立即显示
                    that.expendCallback(); //展开回调
                });
            },

            //默认属性、回调事件配置
            defOpts: {
                closable : false, //fieldset是否允许折叠
                initShow: true, //渲染后是否立即显示
                closeCallback: $.noop, //fieldset折叠回调事件
                expendCallback: $.noop //fieldset展开回调事件
            },

            //事件绑定
            events: {},

            //关闭回调函数
            closeCallback: function () {
                _.isFunction(this.opts.closeCallback) && this.opts.closeCallback.call();
            },
            //展开回调函数
            expendCallback: function () {
                _.isFunction(this.opts.expendCallback) && this.opts.expendCallback.call();
            }
        });

    function loadView(opts) {
        return new EmpSetView(opts);
    }

    module.exports = {
        loadView: loadView
    }
});