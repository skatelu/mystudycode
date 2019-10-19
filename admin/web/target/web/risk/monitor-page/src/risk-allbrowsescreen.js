define(function(require, exports, module) {
	var kuichart = require("./kuichart.js");
	var gridlayout = require("./gridlayout.js");

	exports.$ready = function() {
		initpage();
	};

	function initpage() {
		var gridlayout = $(".gridlayout-child");

		var chart3 = gridlayout[1];
		var left = chart3.style.left;
		left = left.substring(0, left.length - 6) + " )";
		chart3.style.left = left;

		var chart5 = gridlayout[3];
		var left = chart5.style.left;
		left = left.substring(0, left.length - 5) + " 6px)";
		chart5.style.left = left;

		var chart7 = gridlayout[5];
		var left = chart7.style.left;
		left = left.substring(0, left.length - 6) + " )";
		chart7.style.left = left;

		var chart9 = gridlayout[7];
		var left = chart9.style.left;
		left = left.substring(0, left.length - 5) + " 6px)";
		chart9.style.left = left;
		
		empOpenForm();
		setTimeSchedule();
		
		var kuichart_chart8 = $("#chart8").kuichart("getInstance");
		var option_chart8 = {
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
		kuichart_chart8.setOption(option_chart8);
	}

	function gaugeChart() {
		var orgCode = $("#ORG_CODE").combotree("getValue");
		var reqParam = {
			service : "RAWS00001",
			orgCode : orgCode
		};
		return ajax.post({
			req : reqParam
		}).done(function(data) {
			var value = {};
			var temp = data[0][0];
			value.value = temp.OPEN_AMT_USEED_RATE;
			$("#chart2").kuichart("loadData", value);
			//$("#allbrowerscreen_chart3").sform("load",data[0][0]);
			var sform = $("#sform-chart3");
			var str = "<ul>";
			str += '<div style="width: 248px;">保证金账户总额：'+temp.MARGIN_TOTAL_AMT+'<div>'
			str += '<div style="width: 248px;">已占用保证金：'+temp.MARGIN_USED+'<div>'
			str += '<div style="width: 248px;">可用保证金：'+temp.MARGIN_AVL+'<div>'
			str += '<div style="width: 248px;">当日可用最低额：'+temp.MARGIN_AVL_MIN+'<div>'
			str += "</ul>";
			sform.html(str);
		}).fail(function(data){
			
		});
	}

	function pieChart() {
		var orgCode = $("#ORG_CODE").combotree("getValue");
		var reqParam = {
			service : "RAWS00002",
			orgCode : orgCode
		};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			var temp0 = data[0][0];
			var temp1 = data[0][1];
			var values = [];
			values[0] = {value:temp0.RISK_HIGHT_COUNT,name:temp1.RISK_HIGHT_COUNT_NAME};
			values[1] = {value:temp0.RISK_WARN_COUNT,name:temp1.RISK_WARN_COUNT_NAME};
			values[2] = {value:temp0.RISK_LOW_COUNT,name:temp1.RISK_LOW_COUNT_NAME};
			var dataPie = {values:values}
			$("#chart4").kuichart("loadData", dataPie);
			//$("#allbrowerscreen_chart5").sform("load",data[0][0]);
			var sform = $("#sform-chart5");
			var str = "<ul>";
			str += '<div style="width: 248px;">'+temp1.RISK_HIGHT_COUNT_NAME+'：'+temp0.RISK_HIGHT_COUNT+'<div>'
			str += '<div style="width: 248px;">'+temp1.RISK_WARN_COUNT_NAME+'：'+temp0.RISK_WARN_COUNT+'<div>'
			str += '<div style="width: 248px;">'+temp1.RISK_LOW_COUNT_NAME+'：'+temp0.RISK_LOW_COUNT+'<div>'
			str += "</ul>";
			sform.html(str);
			
		});
	}

	function lineChart() {
		var orgCode = $("#ORG_CODE").combotree("getValue");
		var reqParam = {
			service : "RAWS00003",
			orgCode : orgCode
		};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			$("#chart6").kuichart("loadData",data[0][0]);
			var sform = $("#sform-chart7");
			var str = "<ul>";
			str += '<div style="width: 248px;">当前增速水平：4%<div>'
			str += '<div style="width: 248px;">昨日增速水平：5%<div>'
			str += '<div style="width: 248px;">最高增速水平：1%<div>'
			str += '<div style="width: 248px;">最低增速水平：10%<div>'
			str += '<div style="width: 248px;">过去一个月水平：3%<div>'
			str += '<div style="width: 248px;">过去一季水平：5%<div>'
			str += "</ul>";
			sform.html(str);
		});

	}

	function barChart() {
		var orgCode = $("#ORG_CODE").combotree("getValue");
		var reqParam = {
			service : "RAWS00004",
			orgCode : orgCode
		};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			var barData = {};
			var category = [];
			var values = [];
			var temp = data[0];
			for(var i = 0 ; i < temp.length ; i ++){
				category[i] = temp[i].ORG_NAME;
				values[i] = temp[i].TOTAL_POSI;
			}
			barData.category = category;
			barData.values = values;
			$("#chart8").kuichart("loadData",barData);
			var sform = $("#sform-chart9");
			var str = "<ul>";
			for(var i = 0 ; i < category.length ; i ++){
				str += '<div style="width: 248px;">'+category[i]+'：'+values[i]+'<div>'
			}
			str += "</ul>";
			sform.html(str);
		});

	}

	function tableChart() {
		var orgCode = $("#ORG_CODE").combotree("getValue");
		var reqParam = {
			service : "RAWS00005",
			orgCode : orgCode
		};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			$("#allbrowerscreen_margin").sform("load",data[0][0]);
			var li = $("#allbrowerscreen_margin").find("li");
			li.each(function(i) {
				li[i].style.width = "358px";
			});
		});
	}
	var intervalFlag;
	function clearTimeSchedule(){
		 clearInterval(intervalFlag);
	}
	
	function setTimeSchedule(){
		intervalFlag=setInterval(function(){
			empOpenForm();
        },5000);
	}
	
	function empOpenForm(){
		//获取当前点击时间
        var currentTime= formatDate(new Date(),"hh:mm:ss");
        $("#REFRESH_TIME").val(currentTime);
		gaugeChart();
		pieChart();
		lineChart();
		barChart();
		tableChart();
	}
	
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

	return {
		$ready:exports.$ready,
		empOpenForm: empOpenForm,
		clearTimeSchedule:clearTimeSchedule,
		setTimeSchedule:setTimeSchedule
	};
});