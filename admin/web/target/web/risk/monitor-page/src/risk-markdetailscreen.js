/**
 * 客户风险监控
 * 
 */
define(function (require, exports, module) {
    var floatbox=require("./floatbox.js");
    var urlParam = uri.getUrlParam();
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
                var reqParam=[{service:'P6000001'},{service:'P6000002'},{service:'P6000003'}];
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
                ajax.post({
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
                }); 
            },
            //定时刷新右边的数据
            getRightDataSetInterval:function(){
                var that=this;
                
                var treeSelectId=$(".tree").find(".tree-node-selected").attr("node-id");
                var selectType=$('input:radio[name="FLAGDATA"]:checked').val();
                
                var bex = getIndicatorNode(treeSelectId);
            	if(bex == null){
            		return;
            	}
            	
                var reqParam={
                    service:"RMDS00001",
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
            //获取风险数据
            getRiskData:function(){
                var that=this;
                return ajax.post({
                    req:{
                        service:'P6000000',
                        // RISK_TRIGGER_QTY:0  //不小于0表示有风险
                        INDICATOR_ID:'60003'
                    }
                }).then(function(arg1,arg2){
                    that.set("riskData",arg1[0]);
                });
            },
            //点击左侧树，查询出右侧数据
            getRightData:function(paramId){
            	 var that=this;
            	var bex = getIndicatorNode(paramId);
            	if(bex == null){
            		return;
            	}
            	
                /*var reqParam={
                    service:"RMDS00001",
                    INDICATOR_ID:paramId,
                };
                if(paramId.RISK_VALID_FLAG!=undefined){
                    $.extend(reqParam,{RISK_VALID_FLAG:paramId.RISK_VALID_FLAG});
                }
                ajax.post({
                    req:reqParam
                }).then(function(data){
                    for(var i=0;i<data[0].length;i++){
                        data[0][i].RISK_VALUE=data[0][i].POSI_QTY/data[0][i].POSI_LQTY
                    }
                    that.set("rightData",data[0]);
                    $(".kui-datagrid").datagrid("loadData",data[0]);
                });*/
            },
            //获取菜单table的view
            getTableView:function(viewIdParam) {
            	var that = this;
            	var bex = getIndicatorNode(viewIdParam.id);
            	if(bex == null){
            		return;
            	}
                var viewId= bex+"_VIEW";
                $("#selectType")&&$("#selectType").remove();
                $("#tellButton")&&$("#tellButton").remove();
                $(".kui-datagrid")&&$(".kui-datagrid").remove();
                $(".datagrid")&&$(".datagrid").remove();
                //加载表格
                var $tab=$("<table class='kui-datagrid' sytle='overflow:auto' id='"+viewId+"'></table>");
                var $table=$("#riskDatagrid").append($tab);
                parser.parse($table).then(function(conf){
                    $(".datagrid-queryForm").find("a")[0].style.display="none";
                    $(".panel-title")[0].innerHTML=viewIdParam.text;
                    $(".datagrid-queryForm form").append(
                        "<div id='selectType' style='float:left;padding-bottom:10px;'>"+
                        "<input type='hidden' name='INDICATOR_ID' id = 'INDICATOR_ID' value = '"+viewIdParam.id+"'>" +
                        "<input type='radio' name='FLAGDATA' value='-1' style='margin-top:10px;margin-left:20px' checked = 'true'/>"+
                        "<label style='margin-top:5px;font-weight: bold;margin-left:10px;'>全部</label>"+
                        "<input type='radio' name='FLAGDATA' value='0' style='margin-top:10px;margin-left:20px;' />"+
                        "<label style='margin-top:10px;font-weight: bold;margin-left:5px;'>正常</label>"+
                        "<input type='radio' name='FLAGDATA' value='1' style='margin-top:10px;margin-left:20px;' />"+
                        "<label style='margin-top:10px;font-weight: bold;margin-left:5px;'>风险</label>"+
                        "&nbsp&nbsp&nbsp"+
                        "<div id='tellButton' style='float:right'>"+
                        "<a id='afterConfirm' style='width:50px' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text'>追保通知</span></span></a>"+
                        "<a id='StrongConfirm' style='width:50px' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text'>强平通知</span></span></a></div>");
                    //追保通知
                    $("#afterConfirm").click(function(){
                        //alert("暂未实现");
                    	$("#dlg").dialog('open')
                    });
                    //强平通知
                    $("#StrongConfirm").click(function(){
                        //alert("暂未实现");
                    	$("#dlg").dialog('open')
                    });
                    var FLAGDATA = $("input:radio[name='FLAGDATA']");
                    FLAGDATA.click(function(){
                    	that.getRightDataSetInterval();
                    	//var selectType=$('input:radio[name="FLAGDATA"]:checked').val();
                    	//alert(selectType);
                    });
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
                        if(!node||node.id.length<8) {
                            clearInterval(intervalFlagRightData);
                            clearInterval(intervalFlagMenuData);
                            clickFlag=true;
                            return;
                        }
                        if(clickFlag){
                            clickFlag=false;
                            //设置循环查询
                            intervalFlagRightData=setInterval(function(){
                            	that.model.getRightDataSetInterval();
                            },5 * 60 * 1000);
                        }
                        var bex = getIndicatorNode(node.id);
                        if(bex == null){
                            //alert("暂未实现！");
                        	$("#dlg").dialog('open')
                            $("#selectType")&&$("#selectType").remove();
                            $("#tellButton")&&$("#tellButton").remove();
                            $(".kui-datagrid")&&$(".kui-datagrid").remove();
                            $(".datagrid")&&$(".datagrid").remove();
                            clickFlag=true;
                            clearInterval(intervalFlagRightData);
                            return;
                        }
                        //更换右侧视图
                        that.model.getTableView(node);
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

    exports.$ready = function() {
    	
    	$("#dlg").dialog('close')
        var menuModel = new MenuModel();
        $.when(
            menuModel.getRiskData(),
            menuModel.getMenuData()
        ).done(function(){  
            menuView = new MenuView({
                model: menuModel
            });
            
            var rowData = urlParam.rowData;
            if(rowData != undefined && JSON.parse(rowData).INDICATOR_ID != undefined){
            	rowData = JSON.parse(rowData);
            	var initSelectId={
                     id:rowData.INDICATOR_ID,
                     text:rowData.INDICATOR_NAME
                 };
                 menuModel.getTableView(initSelectId);
                 var $selectTreeNode=$('.tree').tree('find',initSelectId.id);
                 $(".tree").tree("select",$selectTreeNode.target);
            }else if(menuModel.attributes.riskData.length>0){ //初始化页面时默认选中第一条有风险的
                var initSelectId={
                    id:menuModel.attributes.riskData[0].INDICATOR_ID,
                    text:menuModel.attributes.riskData[0].INDICATOR_NAME
                };
                menuModel.getTableView(initSelectId);
                var $selectTreeNode=$('.tree').tree('find',initSelectId.id);
                $(".tree").tree("select",$selectTreeNode.target);
            }else{
                return;
            }
            menuModel.getMenuDataSetInterval();
            menuModel.getRightDataSetInterval();
            //设置循环查询
            intervalFlagRightData=setInterval(function(){
            	 menuModel.getRightDataSetInterval();
            },5 * 60 * 1000);
            
            intervalFlagMenuData=setInterval(function(){
                menuModel.getMenuDataSetInterval();
            },3 * 100000);
            
           resize();
        });
    };
    
    var intervalFlagRightData;
    var intervalFlagMenuData;

    exports.changeColor=function(value, row, index, col){
        // console.log("a");
        if(value==1){
           return "<span style='background-color:red'>1-风险</span>";
        }else{
            return "0-正常";
        } 
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
    
    function getIndicatorNode(indicatorId){
    	var id = parseInt(indicatorId);
    	var bex = null;
    	switch (id) {
    	// 账户类风险信息表
		case 60001001:
			bex = "MarkDetailScreen_RISK_INFO_ACCT";
			break;
		// 资金类风险信息表
		 case 60004001:
         case 60004002:
         case 60004003:
			bex = "MarkDetailScreen_RISK_INFO_FUND";
			break;
		// 持仓类风险信息表
         case 60002001:
         case 60003001:
         case 60003002:
         case 60003003:
         case 60003004:
         case 60005001:
         case 60501001:
         case 60501002:
         case 60501003:
         case 60502001:
         case 60503001:
			bex = "MarkDetailScreen_RISK_INFO_ASSET";
			break;
		// 行权资金类风险信息表
         case 60007003:
			bex = "MarkDetailScreen_RISK_INFO_EXE_FUND";
			break;
		// 行权证券类风险信息表
         case 60007001:
         case 60007002:
			bex = "MarkDetailScreen_RISK_INFO_EXE_STK";
			break;
		// 经济业务类资金风险信息表
         case 60006001:
			bex = "MarkDetailScreen_RISK_INFO_CORP_FUND";
			break;
		// 其他
		default:
			break;
		}
    	
    	return bex;
    }
    
    function doClose(){
    	$("#dlg").dialog('close');
    }
    
    function onInitSuccess(){
    	 var menuModel = new MenuModel();
    	//获取当前点击时间
        var currentTime= formatDate(new Date(),"hh:mm:ss");
        $(".datagrid-queryForm").find("input[name=REFRESH_TIME]").val(currentTime);
        //查询右侧数据
        menuModel.getRightDataSetInterval(); 
    }
    
    /** 
	 * 把分页找出来，没有使用view组件，分页被挤走了
	 */
	function resize() {
		var datagrid_view_height = $(".datagrid-view").css("height");
		var noticeSchema_height = 45;
		if(datagrid_view_height){
			datagrid_view_height = parseInt(datagrid_view_height.substring(0,datagrid_view_height.length - 2))
			if (datagrid_view_height > 0 && datagrid_view_height != datagridHeight) {

				if (datagridHeight + noticeSchema_height < document.body.clientHeight) {
					$(".datagrid-view").css("height",parseInt(datagrid_view_height- noticeSchema_height - 2));
					datagridHeight = $(".datagrid-view").css("height");
					datagridHeight = parseInt(datagridHeight.substring(0,datagridHeight.length - 2))

					var datagrid_body_height = $(".datagrid-body").css("height");
					datagrid_body_height = parseInt(datagrid_body_height.substring(
							0, datagrid_body_height.length - 2))

					$(".datagrid-body").css("height",parseInt(datagrid_body_height - 25));
				}

				if (datagrid_view_height + noticeSchema_height != datagridHeight) {
					$(".datagrid-view").css("height",parseInt(datagrid_view_height- noticeSchema_height - 2));
					datagridHeight = $(".datagrid-view").css("height");
					datagridHeight = parseInt(datagridHeight.substring(0,datagridHeight.length - 2))
					var datagrid_body_height = $(".datagrid-body").css("height");
					datagrid_body_height = parseInt(datagrid_body_height.substring(
							0, datagrid_body_height.length - 2))
					$(".datagrid-body").css("height",parseInt(datagrid_body_height - 25));
				}
			}
		}
		window.setTimeout(resize, 100);
	}
	var datagridHeight = 0;
	
	function onDblClickRow(rowIndex, rowData){
		var data = {};
		data.CUST_CODE=rowData.CUST_CODE;
		data.INDICATOR_ID=rowData.INDICATOR_ID;
		data.INDICATOR_NAME=rowData.INDICATOR_NAME;
		data = encodeURI(JSON.stringify(data));
		location.replace("../monitor-page/risk-custdetailscreen.html?rowData=" + data);
	}

	return {
    	$ready : exports.$ready,
    	onInitSuccess:onInitSuccess,
    	doClose:doClose,
    	onDblClickRow:onDblClickRow
    };
});
