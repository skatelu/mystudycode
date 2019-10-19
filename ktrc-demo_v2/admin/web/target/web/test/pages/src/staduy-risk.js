/**
 * 客户风险监控
 * 
 */
define(function (require, exports, module) {
    var floatbox=require("./floatbox.js");
        //页面数据模型
        //菜单树数据模型，表单构造数据模型。
    var MenuModel = Backbone.Model.extend({
            defaults: {
                menuData: [],
                rightData: [],
                leftAllData:[],
                riskData:[]
            },
            //获取菜单树状初始数据
            getMenuData: function(){
                var that = this;
                var reqParam=[{service:'P5000001'},{service:'P5000002'},{service:'P5000003'}];
                return ajax.post({
                    req:reqParam
                }).then(function(arg1,arg2){
                    that.set("menuData", arg2[0][0].concat(arg2[1][0]).concat(arg2[2][0]));
                });
            },
            //定时刷新左侧数据，并提示报警
            getMenuDataSetInterval:function(){
                var that=this;
                var showRiskData=that.attributes.riskData;
                //查询所有指标
                /*ajax.post({
                    req:{
                        service:'P6000000'
                    }
                }).done(function(arg1,arg2){
                    for(var i=0;i<arg1[0].length;i++){
                        if(arg1[0][i].RISK_TRIGGER_FLAG!=0){
                            //给有风险的指标加样式
                            $($('.tree').tree('find',arg1[0][i].INDICATOR_ID).target).find(".tree-indent").eq(2).css("background-color","red");
                        }else{
                            //去掉无风险的指标样式
                           $($('.tree').tree('find',arg1[0][i].INDICATOR_ID).target).find(".tree-indent").eq(2).css("background-color","");
                        }
                    }
                });*/
            },
            //定时刷新右边的数据
            getRightDataSetInterval1:function(){
                var that=this;

                var treeSelectId=$(".tree").find(".tree-node-selected").attr("node-id");
                var selectType=$('input:radio[name="FLAGDATA"]:checked').val();
                var reqParam={
                    //service:"R"+treeSelectId.substring(0,5)+"000",
                    service:"R5000",
                    INDICATOR_ID:treeSelectId,
                };
                if(selectType!=-1){
                    $.extend(reqParam,{RISK_VALID_FLAG:selectType});
                }

                return ajax.post({
                    req:reqParam
                }).then(function(data){
                    that.set("rightData",data[0]);
                    var refreshTime= formatDate(new Date(),"hh:mm:ss");
                    $(".datagrid-queryForm").find("input[name=REFRESH_TIME]").val(refreshTime);
                    for(var i=0;i<data[0].length;i++){
                        data[0][i].RISK_VALUE=data[0][i].POSI_QTY/data[0][i].POSI_LQTY
                    }
                    $(".kui-datagrid").datagrid("loadData",data[0]);
                });
            },
            //获取初始化界面数据
            getRiskData:function(){
                var that=this;
                return ajax.post({
                    req:{
                        service:'R5000',
                        // RISK_TRIGGER_QTY:0  //不小于0表示有风险
                        INDICATOR_ID:'5'
                    }
                }).then(function(arg1,arg2){
                    that.set("riskData",arg1[0]);
                });
            },
            //点击左侧树，查询出右侧数据
            getRightData:function(paramId){
                var that=this;
                var reqParam={
                    service:"R"+paramId+"000",
                    INDICATOR_ID:paramId,
                };
                if(paramId.RISK_VALID_FLAG!=undefined){
                    $.extend(reqParam,{RISK_VALID_FLAG:paramId.RISK_VALID_FLAG});
                }
                ajax.post({
                    req:reqParam
                }).then(function(data){
                    //console.log(data)
                    for(var i=0;i<data[0].length;i++){
                        data[0][i].RISK_VALUE=data[0][i].POSI_QTY/data[0][i].POSI_LQTY
                    }
                    that.set("rightData",data[0]);
                    $(".kui-datagrid").datagrid("loadData",data[0]);
                });
            },
            //获取菜单table的view
            getTableView:function(viewIdParam) {
                var that = this;
                var viewId="R"+viewIdParam+"000_VIEW";
                $("#selectType")&&$("#selectType").remove();
                $("#tellButton")&&$("#tellButton").remove();
                $(".kui-datagrid")&&$(".kui-datagrid").remove();
                $(".datagrid")&&$(".datagrid").remove();
                //加载表格
                var $tab=$("<table class='kui-datagrid' sytle='overflow:auto' id='"+viewId+"'></table>");
                var $table=$("#riskDatagrid").append($tab);
                parser.parse($table).then(function(conf){
                    //$(".datagrid-queryForm").find("a")[0].style.display="none";
                    //$(".panel-title")[0].innerHTML=viewIdParam.text;
                    $(".datagrid-queryForm form").append(
                        //"<div id='selectType' style='float:left'>"+
                        /*"<input type='radio' name='FLAGDATA' value='-1' style='margin-top:10px;margin-left:20px' checked/>"+
                        "<label style='margin-top:5px;font-weight: bold;margin-left:10px;'>全部1</label>"+
                        "<input type='radio' name='FLAGDATA' value='0' style='margin-top:10px;margin-left:20px;' />"+
                        "<label style='margin-top:10px;font-weight: bold;margin-left:5px;'>正常1</label>"+
                        "<input type='radio' name='FLAGDATA' value='1' style='margin-top:10px;margin-left:20px;' />"+
                        "<label style='margin-top:10px;font-weight: bold;margin-left:5px;'>风险1</label>"+
                        "&nbsp&nbsp&nbsp"+*/
                        //"<div id='tellButton' style='float:right'>"+
                        /*"<a id='afterConfirm' style='width:50px' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text'>显示详情</span></span></a>"*/
                        //"<a id='StrongConfirm' style='width:50px' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text'>强平通知</span></span></a></div>"
                        //modifyBtn = datagrid.datagrid("getToolbarButton", "button_commonModify_" + sViewConf.id)
                    );
                    //追保通知
/*                    $("#afterConfirm").click(function(viewConf){

                        alert("暂未实现");
                    });*/
                    //强平通知
                    /*$("#StrongConfirm").click(function(){
                        alert("暂未实现");
                    });*/
                }); 
            },
        });
        //菜单界面视图
    var MenuView = Backbone.View.extend({
            el: "body",
            initialize: function(args) {
                var that = this;
                that.$menuTree = that.$el.find("#menuTree");
                //创建左侧树图
                that.createMenuTree(that.model.get("menuData"));
                that.$menuTabs = that.$el.find("#menuTabs");
            },
            createMenuTree: function(menuData) {
                var that = this;
                var clickFlag=false;
                that.$menuTree.empty().removeData("tree").tree({
                    data: menuData,
                    dropMethod: function(src,dest,position,callback){
                    },
                    onClick: function(node) { //单击左侧树加载右侧表格
                        if(!node||node.id.length>4) {
                            clearInterval(intervalFlag);
                            clickFlag=true;
                            return;
                        }

                        if(node.id.length>1){
                            //更换右侧视图
                            that.model.getTableView(50004);
                            //获取当前点击时间
                            var currentTime= formatDate(new Date(),"hh:mm:ss");
                            $(".datagrid-queryForm").find("input[name=REFRESH_TIME]").val(currentTime);
                            //给作业名称赋值
                            $(".datagrid-queryForm").find("input[name=TRANSNAME]").val(node.text);
                            //查询右侧数据
                            that.model.getRightData(node.id);
                        }else {

                            //更换右侧视图
                            that.model.getTableView(50003);
                            //获取当前点击时间
                            var currentTime= formatDate(new Date(),"hh:mm:ss");
                            $(".datagrid-queryForm").find("input[name=REFRESH_TIME]").val(currentTime);
                            //给作业名称赋值
                            $(".datagrid-queryForm").find("input[name=JOBNAME]").val(node.text);
                            //查询右侧数据
                            that.model.getRightData(node.id);
                        }
                    }
                });
            },
            getMenuData: function(menuId) {
                var that = this;
                return _.find(that.model.get("menuData"), function(v) {
                    return menuId === v.MENU_ID;
                });
            }
        });                 
    var menuView;

    exports.ons = function(index,rowData){
            //alert(rowData.LOG_FIELD);
    };
    exports.$ready = function() {
        var menuModel = new MenuModel();
        $.when(
            menuModel.getRiskData(),
            menuModel.getMenuData()
        ).done(function(){  
            menuView = new MenuView({
                model: menuModel
            });
            //初始化页面时默认选中第一条有风险的
            //if(menuModel.attributes.riskData.length>0){
                /*var initSelectId={
                    id:menuModel.attributes.riskData[0].INDICATOR_ID,
                    text:menuModel.attributes.riskData[0].INDICATOR_NAME
                };*/
                /*menuModel.getTableView(initSelectId);*/
                menuModel.getTableView(50004);
                menuModel.getTableView(50003);
                /*var $selectTreeNode=$('.tree').tree('find',initSelectId.id);
                $(".tree").tree("select",$selectTreeNode.target);*/
            /*}else{
                return;
            }*/
            menuModel.getMenuDataSetInterval();
            menuModel.getRightDataSetInterval1();
            //设置循环查询
            /*intervalFlag=setInterval(function(){
                menuModel.getMenuDataSetInterval();
                menuModel.getRightDataSetInterval();
            },3000);*/
        });
    };
    //日期格式化方法
    function formatDate(dateTime, fmt){
        fmt = fmt || "yyyy-MM-dd hh:mm:ss";
        if(_.isString(dateTime)) {
            dateTime = this.parseDate(dateTime);
        }
        if(!_.isDate(dateTime)) {
            return dateTime;
        }
        var o = {
            "M+" : dateTime.getMonth() + 1,               //月份
            "d+" : dateTime.getDate(),                    //日
            "h+" : dateTime.getHours(),                   //小时
            "m+" : dateTime.getMinutes(),                 //分
            "s+" : dateTime.getSeconds(),                 //秒
            "q+" : Math.floor((dateTime.getMonth()+3)/3), //季度
            "S"  : dateTime.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (dateTime.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
            if(new RegExp("("+ k +")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    }

});
