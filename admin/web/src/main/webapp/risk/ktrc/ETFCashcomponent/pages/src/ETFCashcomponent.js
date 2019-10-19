/**
 * 客户风险监控
 *
 */
define(function (require, exports, module) {
    var floatbox = require("./floatbox.js");
    //页面数据模型
    var MenuModel = Backbone.Model.extend({
        defaults: {
            menuData: [],
            rightData: [],
            leftAllData: [],
            riskData: []
        },

        //定时刷新查询数据
        getCommissionData: function () {
            var that = this;
            ajax.post({
                req: {
                    service: 'P6000700'
                }
            }).then(function (data) {
                that.set("rightData", data[0]);
                var refreshTime = formatDate(new Date(), "hh:mm:ss");
                $(".datagrid-queryForm").find("input[name=REFRESH_TIME]").val(refreshTime);
                for (var i = 0; i < data[0].length; i++) {
                    data[0][i].RISK_VALUE = data[0][i].POSI_QTY / data[0][i].POSI_LQTY
                }
                $(".kui-datagrid").datagrid("loadData", data[0]);
            });
        },

        //获取菜单table的view
        getTableView: function () {
            var that = this;
            var viewId = "P6000700_VIEW";
            $("#selectType") && $("#selectType").remove();
            $("#tellButton") && $("#tellButton").remove();
            $(".kui-datagrid") && $(".kui-datagrid").remove();
            $(".datagrid") && $(".datagrid").remove();
            //加载表格
            var $tab = $("<table class='kui-datagrid' sytle='overflow:auto' id='" + viewId + "'></table>");
            debugger;
            var $table = $("#ETFCashcomponentQuery").append($tab);

            parser.parse($table).then(function (conf) {
                /*$(".datagrid-queryForm").find("a")[0].style.display = "none";
                $(".panel-title")[0].innerHTML = viewIdParam.text;*/
                /*$(".datagrid-queryForm form").append(
                    "<div id='selectType' style='float:left'>" +
                    "<input type='radio' name='FLAGDATA' value='-1' style='margin-top:10px;margin-left:20px' checked/>" +
                    "<label style='margin-top:5px;font-weight: bold;margin-left:10px;'>全部</label>" +
                    "<input type='radio' name='FLAGDATA' value='0' style='margin-top:10px;margin-left:20px;' />" +
                    "<label style='margin-top:10px;font-weight: bold;margin-left:5px;'>正常</label>" +
                    "<input type='radio' name='FLAGDATA' value='1' style='margin-top:10px;margin-left:20px;' />" +
                    "<label style='margin-top:10px;font-weight: bold;margin-left:5px;'>风险</label>" +
                    "&nbsp&nbsp&nbsp" +
                    "<div id='tellButton' style='float:right'>" +
                    "<a id='afterConfirm' style='width:50px' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text'>追保通知</span></span></a>" +
                    "<a id='StrongConfirm' style='width:50px' class='l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text'>强平通知</span></span></a></div>");*/
                
            });
        }
    });
    //菜单界面视图
    var MenuView = Backbone.View.extend({
        el: "body",
        initialize: function (args) {
            var that = this;
            clickFlag = true;
            intervalFlag = setInterval(function () {
            	var custname = $(".datagrid-queryForm").find("input[name=CUSTNAME]").val();
            	var fundid = $(".datagrid-queryForm").find("input[name=FUNDID]").val();
            	var custid = $(".datagrid-queryForm").find("input[name=CUSTID]").val();
            	if(custname !=''|| fundid !='' || custid !=''){
            		clickFlag = false;
            	}else{
            		clickFlag = true;
            	}
            	
                if(clickFlag){
                    that.model.getCommissionData();
                    var currentTime = formatDate(new Date(), "hh:mm:ss");
                    $(".datagrid-queryForm").find("input[name=REFRESH_TIME]").val(currentTime);
                    clickFlag = false;
                }
            }, 1000);
            //查询右侧数据
            that.model.getCommissionData();
        }
    })


    exports.$ready = function() {
        var menuModel = new MenuModel();
        $.when(
            menuModel.getCommissionData()
        ).done(function(){
            menuView = new MenuView({
                model: menuModel
            });
            debugger;
            menuModel.getTableView();
            //设置循环查询
            clickFlag = true;
            intervalFlag = setInterval(function () {
            	var custname = $(".datagrid-queryForm").find("input[name=CUSTNAME]").val();
            	var fundid = $(".datagrid-queryForm").find("input[name=FUNDID]").val();
            	var custid = $(".datagrid-queryForm").find("input[name=CUSTID]").val();
            	if(custname !=''|| fundid !='' || custid !=''){
            		clickFlag = false;
            	}else{
            		clickFlag = true;
            	}
                if(clickFlag){
                    menuModel.getCommissionData();
                    var currentTime = formatDate(new Date(), "hh:mm:ss");
                    $(".datagrid-queryForm").find("input[name=REFRESH_TIME]").val(currentTime);
                    clickFlag = false;
                }
            }, 1000);


        });
    };

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

});
