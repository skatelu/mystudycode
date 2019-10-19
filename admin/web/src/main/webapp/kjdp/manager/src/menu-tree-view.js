/**
 * 员工操作-员工信息模块
 * @author zhubc
 */
define(function (require, exports, module) {

    var menuTreeTpl = require("../tpls/menu-tree-info-tpl"),
        menuService = require("../../common/service/menu-service");

    module.exports = Backbone.View.extend({
        initialize: function(opts) {
            var that = this;

            that.opInfo = top.kjdp.getOpInfo();
            that.opts = $.extend({}, this.defOpts, opts);
            that.tabsFilter = that.opts.tabsFilter && that.opts.tabsFilter.split(",") || [];
            that.tabsOnSelect = that.opts.tabsOnSelect;
            that.resetTree = that.opts.resetTree;
            that.onCheck = that.opts.onCheck;
            that.callback = that.opts.callback;
            that.treeCheckObj = {};
            that.MENU_PUR = [{
                DICT_ITEM: "1",
                DICT_ITEM_NAME: "系统菜单"
            }];
            that.originalMenuArr = [];

            if(that.opts.menuPur){
                var menuPurArr = that.opts.menuPur.split(",");
                that.MENU_PUR = _.filter(that.MENU_PUR,function(o){return _.indexOf(menuPurArr,o.DICT_ITEM) != -1;});
            }
            // 综合处理需要
            if(that.tabsFilter.length){
                // 若需要过滤显示的tab
                that.MENU_PUR = _.filter(that.MENU_PUR, function (v) {
                    return _.indexOf(that.tabsFilter, v.DICT_ITEM) !== -1;
                });
            }
            that.$el.prepend(menuTreeTpl({
                menuPurList: that.MENU_PUR,
                cascadeCheck : opts.cascadeCheck || "true"
            }));
            that.$tpls = that.$("#menuTreeTpl");

            that.$treeBox = that.$tpls.find(".tree-box");
            that.$topBar = that.$tpls.find(".top-toolbar");
            that.$bottomBar = that.$tpls.find(".bottom-toolbar");

            if(that.opts.noCheckedAll == true){
                that.$topBar.find(".checkedAll").remove();
            }
            if(that.opts.noUnChecked == true){
                that.$topBar.find(".unChecked").remove();
            }
            if(that.opts.noUnCheckedAll == true){
                that.$topBar.find(".unCheckedAll").remove();
            }
            if(that.opts.noReset == true){
                that.$topBar.find(".reset").remove();
            }
            if(that.opts.showTopBar == false){
                that.$topBar.hide();
            }
            if(that.opts.showBottomBar == false){
                that.$bottomBar.hide();
            }
            var $mask = loading("正在加载数据！");
            parser.parse(that.$tpls).then(function() {
                $.when(
                    that.getMenuData()
                ).then(function () {
                    that.renderMenuTree();
                }).then(function () {
                    _.isFunction(that.callback) && that.callback();
                }).always(function () {
                    $mask.destroy();
                })
                that.initSize();
            });
        },
        //初始化大小
        initSize : function(){
            var that = this,
                width = that.$el.width(),
                height = that.$el.height(),
                topHeight = 0,
                bottomHeight = 0;
            that.$tpls.css({width:width+"px",height:height+"px"});
            if(that.opts.showBottomBar){
                bottomHeight = that.$topBar.outerHeight(true);
            }
            if(that.opts.showTopBar){
                topHeight = that.$bottomBar.outerHeight(true);
            }
            that.$treeBox.css({top:topHeight+"px",width:width+"px",height:(height-bottomHeight-topHeight)+"px","margin-top":"0"});
        },
        //获取菜单树的菜单数据
        getMenuData : function(){
            var that = this;
            return menuService.getAllMenuData({
                "MENU_STA": "1"
            }).then(function(menuArr) {
                if(!menuArr || menuArr.length == 0){
                    return;
                }
                if(!!that.opts.menuId){
                    var tmpArr = _.partition(menuArr, function (v) {
                        return v.MENU_ID == that.opts.menuId;
                    });
                    that.startMenuLvl = tmpArr[0][0] && tmpArr[0][0].MENU_LVL + "" || "";
                    menuArr = tmpArr[1];
                }
                var menuArrNew = [];
                _.each(menuArr,function(o){
                    if(o.PAR_MENU == "0" || o.PAR_MENU == that.opts.menuId || _.find(menuArr,function(obj){return obj.MENU_ID == o.PAR_MENU;})){
                        menuArrNew.push(o);
                    }
                });
                that.menuDataArr = menuArrNew;
            })
        },
        renderMenuTree: function () {
            var that = this;
            that.$treeBox.each(function(i) {
                var menuPur = $(this).attr("id").substr(-1);
                $(this).tree({
                    treeExpand: that.opts.treeExpand || "0",
                    onlyLeafCheck: that.opts.onlyLeafCheck || false,
                    onLoadSuccess: function () {
                        that.onTreeLoadSuccess(this, arguments[1]);
                    },
                    onCheck: function (node) {
                        _.isFunction(that.onCheck) && that.onCheck(this, node);
                    }
                }).tree("loadData", _.filter(that.menuDataArr, function (o) {
                    if(!that.opts.menuId){
                        return !o.MENU_PUR || o.MENU_PUR == menuPur;
                    }else{
                        return o.MENU_LVL.indexOf(that.startMenuLvl) == 0;
                    }
                }));
            });
        },
        //树加载完成，保存checkbox键值对，方便赋值。
        onTreeLoadSuccess : function(target,data){
            var that = this;
            _.each(data,function(o){
                that.treeCheckObj[o.id] = $(target).find(".tree-node[node-id='"+o.id+"']>.tree-checkbox");
            });
        },
        showTabsBox: function(menuPurArr) {
            var that = this,
                titleArr = [];

            _.chain(that.MENU_PUR).each(function(obj) {
                if(-1 !== _.indexOf(menuPurArr, obj.DICT_ITEM)) {
                    titleArr.push(obj.DICT_ITEM_NAME);
                }
            });

            if(!menuPurArr || !menuPurArr.length) {
                that.$treeBox.hide();
                that.setDisabled(".submit",true);
                return;
            }

            titleArr.length && that.$treeBox.show();

            that.setDisabled(".submit", false);
        },
        //全部展开
        expandAll : function(e){
            var $tree = $(e.currentTarget).closest(".toolbar").next();
            $tree.tree("expandAll");
        },
        //全部关闭
        collapseAll : function(e){
            var $tree = $(e.currentTarget).closest(".toolbar").next();
            $tree.tree("collapseAll");
        },
        //全选
        checkedAll : function(e){
            var $tree = $(e.currentTarget).closest(".toolbar").next();
            $tree.tree("checkedAll");
        },
        //反选
        unChecked : function(e){
            var $tree = $(e.currentTarget).closest(".toolbar").next();
            $tree.tree("unChecked");
        },
        //全清
        unCheckedAll : function(e){
            if(e){
                var $tree = $(e.currentTarget).closest(".toolbar").next();
                $tree.tree("unCheckedAll");
            }else{
                $.each(this.$treeBox,function(){
                    $(this).tree("unCheckedAll");
                });
            }
        },
        //关联父子节点
        setCascade : function(target,flag){
            $(target).tree("setCascade",flag);
        },
        //选中指定节点
        checkNode : function(data){
            this.originalData = data;
            var menuIdArr = [];
            for(var i = 0; i < this.originalData.length; i++){
                menuIdArr.push(this.originalData[i].RES_VALUE);
            }
            menuIdArr = _.uniq(menuIdArr);
            this.originalMenuArr = menuIdArr;
            for(var j = 0; j < menuIdArr.length; j++){
                this.treeCheckObj[menuIdArr[j]] && this.treeCheckObj[menuIdArr[j]].removeClass("tree-checkbox0 tree-checkbox1 tree-checkbox2").addClass("tree-checkbox1");
            }
        },

        //重置
        reset : function(e){
            var that = this;
            $.when(_.isFunction(that.resetTree) ? that.resetTree() : true).then(function (flag) {
                if(!flag){
                    return;
                }
                that.unCheckedAll(e);
                if(that.originalData){
                    that.checkNode(that.originalData);
                }
            })
        },
        //获取选中节点
        getCheckedNode : function(){
            var checkedNodeArr = [];
            $.each(this.$treeBox,function(){
                checkedNodeArr.push($(this).tree("getCascadeCheckNode"));
            });
            return _.pluck(_.flatten(checkedNodeArr), "id");
        },
        getAddNode : function() {
            return _.difference(this.getCheckedNode(), this.originalMenuArr || []) || [];
        },
        getDeleteNode: function() {
            return _.difference(this.originalMenuArr || [], this.getCheckedNode()) || [];
        },
        //清空所有选项
        clear : function(){
            $.each(this.$treeBox,function(){
                $(this).tree("unCheckedAll");
            });
            this.originalData = "";
            this.originalMenuArr = [];
        },
        //提交事件
        submit : function(){
            this.opts.submit.call(this,this.getAddNode(), this.getDeleteNode());
        },
        //禁用或启用指定按钮
        setDisabled : function(btnCls,flag){
            if(!btnCls){return;}
            if(_.isArray(btnCls)){
                for(var i = 0; i < btnCls.length; i++){
                    this.$tpls.find(btnCls[i]).linkbutton(flag ? "disable" : "enable");
                }
            }else{
                this.$tpls.find(btnCls).linkbutton(flag ? "disable" : "enable");
            }
        },
        //默认回调事件配置
        defOpts : {
            //提交回调事件
            submit : $.noop,
            onLoadSuccess : $.noop,
            showTopBar : true,
            showBottomBar : true
        },
        //事件绑定
        events: {
            "click .expandAll" : "expandAll",
            "click .collapseAll" : "collapseAll",
            "click .checkedAll" : "checkedAll",
            "click .unChecked" : "unChecked",
            "click .unCheckedAll" : "unCheckedAll",
            "click .reset" : "reset",
            "click .submit" : "submit"
        }
    });
});
