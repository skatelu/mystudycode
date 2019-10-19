define(function(require, exports, module) {
	
	var kuichart=  require("./kuichart.js");
	var gridlayout=require("./gridlayout.js");
	//var datagrid=require("./datagrid.js");
	/* exports.$ready = function() {
		 initpage();
	 }
	 function initpage(){
		 //barChart()
	 }*/
	 
	 function onInitSuccess(){
		 var reqParam = {
					service : 'TSRQ0001'
				};
				return ajax.post({
					req : reqParam
				}).then(function(data) {
					$("#SpecialCrowdScreen_Total").datagrid("loadData", data[0]);
					var rows=$('#SpecialCrowdScreen_Total').datagrid("getRows");
					if(rows.length > 0){
						$("#SpecialCrowdScreen_Total").datagrid('selectRow',0);//grid加载完成后自动选中第一行
						var row=$('#SpecialCrowdScreen_Total').datagrid("getSelections");//获取选中的数据
						barChart(row[0].CUST_CODE);
						history(row[0].CUST_CODE);
					} 
				});
				
	 }
	 
	 function showPicture(){
		 var row =$("#SpecialCrowdScreen_Total").datagrid("getSelected");
		 //刷新客户名称
		 $("#customName").text(row.CUST_NAME);
		 //刷新电话号码
		 $("#tel").text(row.TEL);
		 //刷新客户代码
		 $("#CUST_CODE").text(row.CUST_CODE);
		 barChart(row.CUST_CODE);
		 history(row.CUST_CODE);
	 }
	 
	 function barChart(CUST_CODE) {
			var reqParam = {
				service : "TSRQ0002",
				CUST_CODE:CUST_CODE
			};
			return ajax.post({
				req : reqParam
			}).then(function(data) {
				/*$("#chart2").kuichart("loadData",data[0][0]);*/
				var barData = {};
				var category = [];
				var values = [];
				var temp = data[0];
				for(var i = 0 ; i < temp.length ; i ++){
					category[i] = temp[i].INDICATOR_NAME;
					values[i] = temp[i].RISK_COUNT;
				}
				barData.category = category;
				barData.values = values;
				$("#chart2").kuichart("loadData",barData);
				if(temp.length>1){
					//当数据量超过一条时，对条形图的X轴坐标做倾斜处理（目的是为了能够显示更多的文字）
					var kuichart_chart2 = $("#chart2").kuichart("getInstance");
					var option_chart2 = {
							xAxis: {
			                    show:true,
			                    axisLabel:{
			                        interval:0,
			                        rotate:25,
			                        margin: 30,
			                        textStyle:{
			                          align: 'center'
			                        },
			                        axisTick:{
			                            alignWithLabel:true
			                        },
			                        data: this.appNameArr,
			               }
						}
					};
					kuichart_chart2.setOption(option_chart2);
				}
			});
			

		}
	 
	 function history(CUST_CODE){
		 var reqParam = {
					service : 'TSRQ0003',
					CUST_CODE:CUST_CODE
				};
				return ajax.post({
					req : reqParam
				}).then(function(data) {
					$("#SpecialCrowdScreen_Detail").datagrid("loadData", data[0]);
				});
	 }
	 return {
	    	/*$ready : exports.$ready,*/
	    	onInitSuccess:onInitSuccess,
	    	showPicture:showPicture
	  };
});