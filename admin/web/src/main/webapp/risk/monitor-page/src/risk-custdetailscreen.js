/**
 * 客户风险监控
 * 
 */
define(function (require, exports, module) {
	var kuichart = require("./kuichart.js");
	var gridlayout = require("./gridlayout.js");
	var urlParam = uri.getUrlParam();
	exports.$ready = function() {
		$("#history_risk_trigger").resize(function() {
			var that = $(this);
			var width = that.width();
			if(width == 0){
				return;
			}
			var datagrid = that.find(".datagrid-view")[0];
			var gridWidth = datagrid.style.width;
			gridWidth = gridWidth.replace("px","");
			console.log(gridWidth);
			var div = that.find("div")
			for(var i = 0 ; i < div.length ; i ++){
				var thisWidth = div[i].style.width;
				thisWidth = thisWidth.replace("px","");
				if(parseInt(gridWidth) + 2 == parseInt(thisWidth)){
					div[i].style.width = width + "px"
				}
			}
			
			datagrid.style.width = (width - 2) + "px";
		});
		
		doFresh();
		
		var li = $("#custdetialscreen_base_info").find("li");
		li.each(function(i) {
			var width = li[i].style.width;
			width = width.replace("px","");
			li[i].style.width = (width * 2) + "px";
		});
		
		var ulwidth = $("#custdetialscreen_asset_info").width() - 20;
		li = $("#custdetialscreen_asset_info").find("li");
		li.each(function(i) {
			li[i].style.width = (ulwidth / 2) + "px";
		});
		
    };
    
    function cust_info(){
    	
    }
    
	function base_info(){
		var rowData = JSON.parse(urlParam.rowData);
		var reqParam = {
			service : "RCDS00002",
			CUST_CODE : rowData.CUST_CODE
		};
		return ajax.post({
			req : reqParam
		}).done(function(data) {
			$("#custdetialscreen_base_info").sform("load",data[0][0]);
			var li = $("#custdetialscreen_base_info").find("li");
			li.each(function(i) {
				var width = li[i].style.width;
				width = width.replace("px","");
				li[i].style.width = (width * 2) + "px";
			});
		}).fail(function(data){
			
		});
	}
	
	function risk_indicator_detail(){
		
	}
	
	function history_risk(){
		history_risk_pic();
		history_risk_trigger();
	}
	
	function history_risk_pic(){
		
	}
	
	function history_risk_trigger(){
		
	}
    
    function onInitSuccess(){
    	
    }
    
    function doBack(){
		location.replace("../monitor-page/risk-markdetailscreen.html?rowData=" +encodeURI( urlParam.rowData));
    }
    
    function doFresh(){
    	cust_info();
    	base_info();
    	risk_indicator_detail();
    	history_risk();
    }
    
    function doSearch(){
    	history_risk();
    }
    
	return {
    	$ready : exports.$ready,
    	onInitSuccess:onInitSuccess,
    	doBack:doBack,
    	doFresh:doFresh,
    	doSearch:doSearch
    };
});
