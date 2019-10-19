/**
 * corn表达式配置模块
 * @author guoj
 */
define(function(require, exports, module) {
    var getData = function(start,max){
            var data = [];
            for(var i=start;i<=max;i++ ){
                data.push({"dict_val":""+i,"dict_des":i>9?i:"0"+i})
            }
            return data;
        },
        weekDay = [" 日"," 一"," 二"," 三"," 四"," 五"," 六"],
        getWeekData = function(){
            var data = [];
            for(var i=1;i<=7;i++ ){
                data.push({"dict_val":""+i,"dict_des":weekDay[i-1]})
            }
            return data;
        },
        CornModel =  Backbone.Model.extend({
            defaults: {
                title:"编辑时间表达式",
                cycles:[
                    {'title':'秒','val':'0','data':getData(0,59)},
                    {'title':'分','val':'1','data':getData(0,59)},
                    {'title':'时','val':'2','data':getData(0,23)},
                    {'title':'日','val':'3','data':getData(1,31)},
                    {'title':'月','val':'4','data':getData(1,12)},
                    {'title':'周','val':'5','data':getWeekData()},
                    {'title':'年','val':'6'}
                ],
                cornValue: "* * * * * ?",
                cycleNo: "0",
                modeNos:{"0":"0","1":"0","2":"0","3":"0","4":"0","5":"0","6":"0"},
                data: {}
            },
            getData:function(){},
            initModelData:function(args){
                var that = this;
                if(args.cornValue)
                    that.set("cornValue",formatCorn(args.cornValue.trim()));
                if(args.title)
                    that.set("title",args.title);
            }
        }),
        cornModel,
        CornView = Backbone.View.extend({
            initialize:function(args){
                var that = this;
                that.render = require("../tpls/scheduler-corn-tpl.js");
                that.initDialog(cornModel.get("title"),args.saveCorn);
                parser.director(that.$render).then(function(){
                    that.$corn = that.$dialog.find("input[name='CRON_EXPRESSION']");
                    that.$cornText = that.$dialog.find("input[name='CRON_EXPRESSION_TEXT']");
                    that.$corn.textinput("setValue",that.model.get("cornValue"));
                    that.$cornText.textinput("setValue",formatToChiness(that.model.get("cornValue")));
                    that.$tabs =$("#cycleTab");
                    $.data(that.$tabs[0], 'tabs').options.onSelect = function(title,index){ //选择周期
                        that.model.set("cycleNo",index);
                    };
                    for(var i=0;i<6;i++){
                        $("#cycle"+i).find(".kui-combobox").each(function(){
                            var data = $.extend(true,[],that.model.get("cycles")[i].data),
                                id = this.id,
                                strs =id.split("_") ;
                            if(strs[2]==2){
                                if(strs[1]==3||(strs[1]==4&&strs[0]<3)){// 间隔值去掉0 周期中第二个输入框去掉第一项
                                    data.splice(0,1);
                                }
                            }
                            $(this).combobox("loadData",data);
                            $.data(this, 'combobox').options.onSelect = function(data){//选择下拉数据
                                that.setData(this,that,data.dict_val);
                            }
                        })
                    }
                    that.$tabs.on("click","input[type='radio']",function(e){//选择模式
                        that.selectRadio(this,that,e);
                    }).on("click",".data-list>input[type='checkbox']",function(e){//选择指定数据
                            that.selectCheck(this,that,e);
                    }).on("blur","#cycle6>input.kui-textinput",function(e){ //输入年份
                        if(e.target.value.trim())
                            that.setData(this,that, e.target.value);
                    });
                    //重置按钮
                    $("a#resetBtn").bind("click",function(){
                        var cornValue =  "* * * * * ?"
                        that.model.set("cornValue",cornValue);
                        that.$corn.textinput("setValue",that.model.get("cornValue"));
                        that.$cornText.textinput("setValue",formatToChiness(that.model.get("cornValue")));
                    });
                });
            },
            selectRadio:function(target,that,e){  //选择模式
                var cycleNo =e.target.name,
                    modeNo =e.target.value,
                    modeNos = that.model.get("modeNos");
                modeNos[cycleNo] = modeNo;
                that.model.set("modeNos",modeNos);
                var strValue = "",
                    data = that.model.get("data");
                switch (modeNo){
                    case '1':
                        strValue = '*';
                        break;
                    case '2':
                        if(cycleNo==6)
                            strValue = ' ';
                        else
                            strValue = '?';
                        break;
                    case '3':
                        var data1 = data[cycleNo+"_3_1"],
                            data2 = data[cycleNo+"_3_2"];
                        if(data1&&data2)//年可能为空
                            strValue = data1+'-'+data2;
                        break;
                    case '4':
                        var data1 =  data[cycleNo+"_4_1"]||"",
                            data2 =  data[cycleNo+"_4_2"]||"";
                        strValue = data1+'/'+data2;
                        break;
                    case '5':
                        if(cycleNo==3)
                            strValue = 'L';
                        else if(cycleNo==5)
                            var data1 = data[cycleNo+"_5_1"];
                            if(data1)
                                strValue = data1+'L';
                        break;
                    case '6':
                        strValue =  data[cycleNo+"_6"]||"";
                        break;
                }
                if(strValue)
                    that.refreshCorn(strValue,cycleNo);
            },
            setData:function(target,that,data){
                var id = target.id,
                    strs = id.split("_"),
                    str = "",
                    modeNos = that.model.get("modeNos"),
                    modeNo = modeNos[strs[0]],
                    corndata = that.model.get("data");
                corndata[id] = data;
                that.model.set("data",corndata);
                if(strs[1]==modeNo){
                    if(modeNo==5){
                        str = data+"L";
                    }else if(modeNo==3||modeNo==4){
                        var otherData = corndata[strs[0]+"_"+strs[1]+"_"+(strs[2]==1?2:1)];
                        if(otherData){
                            if(strs[2]==1)
                                str = data+(modeNo==3?"-":"/")+otherData;
                            else
                                str = otherData+(modeNo==3?"-":"/")+data;
                        }
                    }
                    if(str)
                        that.refreshCorn(str,strs[0]);
                }
            },
            selectCheck:function(target,that,e){//选择指定数据
                var strs = [],str = "",
                    cycleNo = e.target.name,
                    modeNos = that.model.get("modeNos"),
                    modeNo = modeNos[cycleNo],
                    corndata = that.model.get("data");

                $(e.target).parent().find("input:checked").each(function(){
                    strs.push(this.value)
                });
                str = strs.join(",")||"*";
                corndata[cycleNo+"_6"] = str;
                that.model.set("data",corndata);
                if(modeNo==6){
                    that.refreshCorn(str,cycleNo);
                }
            },

            refreshCorn: function(str,index){
                var that = this,
                    cornValue = that.model.get("cornValue")||"* * * * * ?",
                    cornStrs = cornValue.split(" "),
                    len = 0,
                    strs = [];
                for(var i= 0,l=cornStrs.length;i<l;i++){
                    var cornStr ="";
                    cornStr = cornStrs[i].trim();
                    if(cornStr){
                        if(len==index){
                            cornStr = str;
                        }else if((index==3&&len==5)||(index==5&&len==3)){
                            if(str!="?")
                                cornStr = "?";
                            else if(cornStr=="?")
                                cornStr = "*";
                        }
                        strs.push(cornStr);
                        len++;
                    }
                }
                //原有corn串位数不足，除非更新的值是年，否则默认只补充到周
                for(var m = index>5?index:5;len<=m;len++){
                    var cornStr = "*";
                    if(len==index){
                        cornStr = str;
                    }else if((len==5&&strs[3]!="?")||(len==3&&index==5&&str!="?")){
                        cornStr = "?";
                    }
                    strs.push(cornStr);
                }
                cornValue = strs.join(" ").trim();
                that.model.set("cornValue",cornValue);
                that.$corn.textinput("setValue",cornValue);
                that.$cornText.textinput("setValue",formatToChiness(cornValue));
            },
            initDialog : function(title,saveCorn){
                var that = this, dHeight = $(window).height();
                that.$render = $(that.render({
//                    cornValue:that.model.get("cornValue"),
//                    cornValueText:formatToChiness(that.model.get("cornValue")),
                    cycles : that.model.get("cycles")
                }));
                that.$dialog = $("<div></div>").append(that.$render).dialog({
                    title: title,
                    modal:true,
                    width:570,
                    height:dHeight > 500 ? 500 : dHeight,
                    buttons: [{
                        text: "保存",
                        iconCls: "icon-save",
                        handler: function() {
                            var cornValue = that.$corn[0].value;
                            if(!that.condValueValidate(cornValue)) {
                                return;
                            }
                            _.isFunction(saveCorn) && saveCorn(cornValue);
                            that.$dialog.dialog("destroy");
                        }
                    }, {
                        text: "关闭",
                        iconCls: "icon-cancel",
                        handler: function() {
                            that.$dialog.dialog("destroy");
                        }
                    }],
                    onClose: function() {
                        that.$dialog.dialog("destroy");
                    }
                });
            },
            condValueValidate:function(cornValue){
                return true
            }
        }),
        cornView;

    //格式化corn表达式
    function formatCorn(corn){
        if(!corn.trim())
            return "* * * * * ?";
        var cornStrs = corn.trim().split(" "),
            len = 0,
            strs = [];
        for(var i= 0,l=cornStrs.length;i<l;i++){
            var cornStr = cornStrs[i].trim();
            if(cornStr){
                if(len==5){
                    var str = strs[3];
                    if("*?".indexOf(cornStr)<0&&"*".indexOf(str)){
                        strs.splice(3,1,"?");
                    }else if(str!="?")
                        cornStr = "?";
                    else if(cornStr=="?")
                        cornStr = "*";
                }
                strs.push(cornStr);
                len++;
            }
        }
        for(;len<6;len++){//默认补位到周
            var cornStr = "*";
            if(len==5&&strs[3]!="?"){
                cornStr = "?";
            }
            strs.push(cornStr);
        }
        return strs.join(" ");
    }

    //corn表达式汉化翻译
    function formatToChiness(corn){
        if(!corn.trim()) return "";
        var cornStrs = corn.trim().split(" "),
            temp = cornStrs[4],
            chinessStr = "",
            cycle= ['秒','分','时','日','周','月','年'];
        cornStrs[4] = cornStrs[5];
        cornStrs[5] = temp;
        for(var len=cornStrs.length-1;len>-1;len--){
            var cornStr = cornStrs[len];
            if(cornStr.indexOf("-")>-1){
                var strs = cornStr.split("-");
                if(len==4)
                    chinessStr += "周"+weekDay[strs[0]-1]+"至"+weekDay[strs[1]-1]+" ";
                else
                    chinessStr += strs[0]+"至"+strs[1]+cycle[len]+" ";
            }else if(cornStr.indexOf("/")>-1){
                var strs = cornStr.split("/");
                if(len==4)
                    chinessStr += "从周"+weekDay[strs[0]-1]+"起每隔"+strs[1]+"天 ";
                else
                    chinessStr += "从"+strs[0]+cycle[len]+"起每隔"+strs[1]+cycle[len]+" ";
            }else if(cornStr=="*"){
                chinessStr += "每"+(len==4?"天":cycle[len])+" ";
            }else if(cornStr.indexOf("L")>-1){
                if(len==3)
                    chinessStr += "最后一天 ";
                else if(len==4)
                    chinessStr += "最后一个周"+weekDay[cornStr.substring(0,cornStr.length-1)-1]+" ";
            }else if(cornStr!="?"){
                if(len==4){
                    var strs = cornStr.split(","),
                        tempStr = [];
                    for(var k= 0,l=strs.length;k<l;k++){
                        tempStr.push(weekDay[strs[k]-1]);
                    }
                    chinessStr += "周"+tempStr.join(",")+" ";
                }else
                    chinessStr += cornStr+cycle[len]+" ";
            }
        }
        return chinessStr+="执行";
    }

    function init(args){
        cornModel = new CornModel();
        cornModel.initModelData(args);
        return  new CornView({
            model : cornModel,
            saveCorn : args.saveCorn
        });
    }

    module.exports = {
        init : init,
        formatToChiness : formatToChiness,
        formatCorn : formatCorn
    }

})