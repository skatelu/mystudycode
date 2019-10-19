/**
 * 菜单功能关联设置
 * @author zhubc 20180419
 */
define(function (require, exports, module) {
    var MenuService = require("../../common/service/menu-service"),

        MenuCheckModel = Backbone.Model.extend({
            defaults : {
                MENU_PUR : "",
                menuArr : ""
            },
            getMenuArr : function(){
                var that = this;
                return MenuService.getAllMenuData().then(function(data) {
                    that.set("menuArr", data);
                })
            }
        }),
        MenuCheckView = Backbone.View.extend({
            el: "body",
            initialize : function(){
                var that = this;
                that.$menuTree = that.$el.find("#menuTree");
                that.$upmMenuAjax = that.$el.find("#KJDP_UPM_MENU_SERVICE");
                that.menuArr = that.model.get("menuArr");
                that.initPage();
            },
            initPage : function(){
                var that = this;
                that.$menuTree.tree({
                    data : that.menuArr,
                    conf: {nodeId:'MENU_ID',nodeName:'MENU_NAME',parNode:'PAR_MENU',treeType: '1'},
                    onSelect : function(menu){
                        that.menuSelect(menu);
                    }
                });
                $.when(
                    parser.director(that.$upmMenuAjax.addClass("kui-datagrid"))
                ).then(function(){
                    mask.destroy();
                });
            },
            menuSelect : function(menu){
                var that = this;
                that.$upmMenuAjax.datagrid("clean");
                if(that.$menuTree.tree("isLeaf",menu.target)){
                    that.loadMenuAjax(menu);
                }
            },
            loadMenuAjax : function(menu,callback){
                var that = this;
                that.$upmMenuAjax.datagrid("reload",{
                    MENU_ID : menu.id
                },function(){
                    _.isFunction(callback) && callback();
                });
            },
            checkMenu : function(){
                var that = this,
                    menu = that.$menuTree.tree("getSelected");
                if(!menu){
                    alert("请先选择菜单！");
                    return false;
                }
                if(!that.$menuTree.tree("isLeaf",menu.target)){
                    alert("请先选择菜单！");
                    return false;
                }
                return menu;
            },
            menuServiceAdd : function(){
                var that = this,
                    menu = that.checkMenu();
                if(menu == false){
                    return;
                }
                var $datagrid = that.$upmMenuAjax,
                    btnConf = _.find($datagrid.datagrid("options").toolbar, function(obj){ return obj.iconCls == "icon-add"; });
                parser.createCommonDialog({
                    title: btnConf.title || "增加",
                    $datagrid: $datagrid,
                    record: {MENU_ID:menu.id},
                    req: btnConf.req && btnConf.req[0],
                    onSaveSuccess: function(formData) {
                        that.loadMenuAjax(menu,function(){
                            var index = $datagrid.datagrid("getRowIndex", formData);
                            index > -1 && $datagrid.datagrid("selectRow", index);
                        });
                    }
                });
            },
            menuServiceDelete : function(){
                var that = this,
                    menu = that.checkMenu();
                if(menu == false){
                    return;
                }
                var $datagrid = that.$upmMenuAjax,
                    btnConf = _.find($datagrid.datagrid("options").toolbar, function(obj){ return obj.iconCls == "icon-remove"; }),
                    row = $datagrid.datagrid("getSelected");
                if(!row) {
                    alert("请选择一行数据！");
                    return;
                }
                confirm("确定要删除选中的记录吗？", function(flag) {
                    if(flag) {
                        if(btnConf.req && btnConf.req.length) {
                            ajax.post({
                                req: $.extend({}, btnConf.req && btnConf.req[0], row)
                            }).done(function() {
                                alert("删除成功！");
                                that.loadMenuAjax(menu);
                            });
                        }
                    }
                });
            }
        }),
        mask,
        menuCheckModel,
        menuCheckView;

    function $ready(){
        menuCheckModel = new MenuCheckModel();
        mask = loading("界面加载中，请稍后……");
        $.when(
            menuCheckModel.getMenuArr()
        ).then(function(){
                menuCheckView = new MenuCheckView({
                    model : menuCheckModel
                });
            })
    }

    return {
        menuServiceAdd : function() {
            menuCheckView && menuCheckView.menuServiceAdd();
        },
        menuServiceDelete : function() {
            menuCheckView && menuCheckView.menuServiceDelete();
        },
        $ready : $ready
    };
});