/**
 *  管理平台header
 * @author chenk
 */
define(function (require, exports, module) {
    var common = require("../../../kjdp/common/service/busi-base-service"),
        loginService = require("../service/login-service"),
        headerRender = require("../../../kjdp/pages/tpls/header-tpl"),
        autocomp = require("../../../kjdp/common/plugins/menu-autosearch");
    return Backbone.View.extend({
        el: "body",
        initialize: function (arg) {
            var that = this;
            $.extend(arg, {
                theme: top.kjdp.theme
            });
            that.$header = $(headerRender({
                showAutoSearch: arg.showAutoSearch,
                buttons: arg.buttons || []
            }));
            that.$el.append(this.$header);

            that.setLogoUrl();
            that.renderTopMenu(arg.buttons);
            that.renderOpInfo();
            that.renderPostName();
            if (arg.showAutoSearch) {
                that.renderMenuSearch();
            }
        },

        setLogoUrl: function () {
            this.$el.find(".logo").css({
                "background-image": "url(" + resolve("frame/pages/images/kjdp-logo-top.png#") + ")"
            });
        },

        renderTopMenu: function (buttons) {
            var that = this;
            this.$btnWrap = this.$el.find(".btn-wrap");
            $.each(that.$el.find(".header-btn"), function (index) {
                var $btn = $(this);
                $btn.bind("click", function (e) {
                    $btn.addClass("header-btn-selected").siblings().removeClass("header-btn-selected");
                    $.isFunction(buttons[index].handler) && buttons[index].handler.call(this, e);
                })
            });
        },
        renderPostName: function () {
            var that = this;
            that.$postName = that.$el.find("#POST_NAME");
            if (that.model) {
                that.postDataArr = that.model.get("POST_DATA");
                var platPost = _.find(that.postDataArr, function (post) {
                        return post.ORG_CODE == that.model.get("ORG_CODE");
                    }) || that.postDataArr[0];
                if (!platPost) {
                    alert("没有获取到用户的岗位信息！", alert.ERROR);
                    return;
                }
                this.$postName.text(platPost.POST_NAME);
            }
        },
        renderOpInfo: function () {
            var that = this;
            that.$el.find("#USER_NAME").text(that.model.get("OP_NAME"));
            that.$el.find("#USER_CODE").text(that.model.get("OP_CODE"));
            that.$el.find("#ORG_NAME").text(that.model.get("ORG_NAME"));
        },
        /**
         * 渲染菜单搜索组件
         * @author  zhaozz
         * @return {[type]} [description]
         */
        renderMenuSearch: function () {
            autocomp(this.$el.find(".search-wrap"));
        },

        triggerBtnClick: function (index) {
            if (typeof index == "number") {
                this.$btnWrap.find(".header-btn:eq(" + index + ")").trigger("click");
            } else {
                this.$btnWrap.find("#" + index).trigger("click");
            }
        },

        getBtn: function (index) {
            if (typeof index == "number") {
                return this.$btnWrap.find(".header-btn:eq(" + index + ")");
            } else {
                return this.$btnWrap.find("#" + index);
            }

        },
        lockTemplate: artTemplate.compile('<div style="text-align: center"> ' +
            '<span style="line-height: 60px;font-size: 18px;display: block;">系统已锁定,请输入密码解锁！</span> ' +
            '<input class="kui-textinput" id="lockPwd" type="password" placeholder="请输入密码" maxlength="15">' +
            '<span class="lock-msg" style="height: 25px;font-size: 14px;display: block;color: red;"></span> ' +
            '</div>'
        ),
        events: {

            "click #lock": function () {
                var that = this;
                confirm("确定要锁定系统吗？", function (flag) {
                    if (flag) {
                        if (that.$lockDialog && that.$lockDialog.length) {
                            ajax.logout().done(function () {
                                that.$lockDialog.find("#lockPwd").val("");
                                that.$lockDialog.dialog("open");
                                $(".container-lock").removeClass("hide");
                            });

                        } else {
                            parser.parse(that.lockTemplate()).then(function (dialog) {
                                var $dialog = $(dialog).appendTo("body"),
                                    buttons = [];
                                buttons.push({
                                    text: "解锁",
                                    iconCls: "icon-ok",
                                    handler: function () {
                                        var userData = {
                                            OP_CODE: that.model.get("USER_CODE"),
                                            OP_PASS: $dialog.find("#lockPwd").val()
                                        };
                                        loginService.login(userData).done(function (data) {
                                            if (loginService.isLogin(data)) {
                                                $(".container-lock").addClass("hide");
                                                $dialog.dialog("close");
                                            } else {
                                                $dialog.find(".lock-msg").text("您输入的密码不正确，请重新输入！");
                                            }
                                        }).fail(function () {
                                            $dialog.find(".lock-msg").text("解锁失败，服务器无响应或异常！");
                                        });
                                    }
                                });
                                ajax.logout();
                                $dialog.dialog({
                                    title: "解锁界面",
                                    width: 430,
                                    modal: true,
                                    closable: false,
                                    buttons: buttons
                                });
                                $(".container-lock").removeClass("hide");
                                that.$lockDialog = $dialog;
                            });
                        }
                    }
                });
            },

            "click #logout": function () {
                confirm("确定要退出系统吗？", function (flag) {
                    if (flag) {
                        loginService.logout();
                    }
                });
            },

        }

    });

})
