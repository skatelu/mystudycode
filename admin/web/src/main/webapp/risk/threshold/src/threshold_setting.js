/**
 * 通用页面
 * @author chenk
 */
define(function(require, exports, module) {
    var urlParam = uri.getUrlParam();

    exports.$ready = function() {
        if(!urlParam.VIEW_ID) {
            throw "没有获取到VIEW_ID参数！"
        }
        var viewArr = urlParam.VIEW_ID.split(",");
        parser.getViewConfig(viewArr).then(function(viewConf) {
            if(viewArr.length !== viewConf.size()) {
                alert("获取view配置异常，配置参数个数：" + viewArr.length + "，实际获取个数：" + viewConf.size() + "，请检查配置！", alert.ERROR)
                return;
            }
            $.when(
                use(viewConf.getScript()),
                viewConf.getCss() && parser.loadCss(uri.resolve(viewConf.getCss() + "#"))
            ).then(function(mainObj) {
                initGenPage(viewConf.getConfigObj());
                parser.setContextObj(mainObj);
                parser.handleObject(viewConf.getConfigObj());
                handleCallbacks(viewConf.getConfigObj());
                parser.director("body");
                
                loadIndicatorType();
            });
        }, function() {
            console.error("界面解析出错，获取view配置失败！");
        });
    }
    /**
     * 生成通用界面dom结构
     * @method setGenPage
     * @return undefined
     */
    function initGenPage(viewConf) {
        var viewIdArr = urlParam.VIEW_ID && urlParam.VIEW_ID.split(","),
            tempViewConf = [];

        if(!viewIdArr) {
            throw "没有获取到view编号，检查参数！";
            return;
        }
        //保证viewConf的顺序和URL上的顺序一致。
        _.each(viewIdArr, function(v) {
            var vConf = viewConf[v];
            if(vConf) {
                tempViewConf.push({
                    viewId: v,
                    render: vConf.render || "datagrid",
                    isForm: _.contains(["form", "sform"], vConf.render)
                })
            }
        });
        $("body").empty().append(template("genericTemplate")({
            viewConfArr: tempViewConf,
            isVertical: urlParam.isVertical,
            winWidth: window.frameElement ? $(window.frameElement).parent().width() : $(window).width(),
            winHeight: window.frameElement ? $(window.frameElement).parent().height() : $(window).height()
        }));

        parser.setContextObj({
            onBeforeResize: function() {
                var panels = $.data(this, "layout").panels;
                if(panels.west && panels.west.length) {
                    panels.west.panel("options").width =  $(window).width() / 2;
                } else if(panels.north && panels.north.length) {
                    panels.north.panel("options").height =  $(window).height() / 2;
                }
            }
        });
    }
    /**
     * 处理view配置中的回调，主要用于处理通用界面表格的回调
     * @method handleCallbacks
     * @param viewConf {object} view配置
     * @return undefined
     */

    function handleCallbacks(viewConf) {
        var commonBtnHandler = {

        	thresholdAdd: function(e, $datagrid, btnConf) {
        		addInitData();
            },

            thresholdModify: function(e, $datagrid, btnConf) {
            	updateInitData();
            }
        };

        $.each(viewConf, function(viewId) {
            var datagrid = $("#" + viewId),
                subGridId = datagrid.data("subDatagrid"),
                subGrid = subGridId && $("#" + subGridId),
                subSubGridId = subGrid && subGrid.data("subDatagrid"),
                subSubGrid = subSubGridId && $("#" + subSubGridId),
                sViewConf = viewConf[viewId],
                render = sViewConf.render;
            if(render != "datagrid") {
                return;
            }
            if(!_.isFunction(sViewConf.onQueryButtonClick)) {
                sViewConf.onQueryButtonClick = function(param) {
                    datagrid[render]('reload', param);
                    datagrid[render]('clearSelections');
                }
            }

            if(!_.isFunction(sViewConf.onLoadSuccess)) {
                sViewConf.onLoadSuccess = function() {
                    subGrid && subGrid[render]("clean");
                    subSubGrid && subSubGrid[render]("clean");
                }
            }

            if(!_.isFunction(sViewConf.onSelect)) {
                sViewConf.onSelect = function(idx, row) {
                    var maintainFlag = "MAINTAIN_FLAG",
                        subBtns,
                        modifyBtn = datagrid.datagrid("getToolbarButton", "button_commonModify_" + sViewConf.id),
                        delBtn = datagrid.datagrid("getToolbarButton", "button_commonDelete_" + sViewConf.id);

                    modifyBtn.linkbutton("enable");
                    delBtn.linkbutton("enable");

                    if("1" === row[maintainFlag]) {
                        delBtn.linkbutton("disable");
                    } else if("3" === row[maintainFlag]) {
                        modifyBtn.linkbutton("disable");
                    } else if("0" === row[maintainFlag]) {
                        delBtn.linkbutton("disable");
                        modifyBtn.linkbutton("disable");
                    }

                    if(subGrid) {
                        subBtns = subGrid.datagrid("getToolbarButton");
                        if(subBtns.length) {
                            subBtns.linkbutton("enable");
                        }
                        subGrid.datagrid("reload", row);
                    }

                    if(subSubGrid) {
                        subBtns = subSubGrid.datagrid("getToolbarButton");
                        if(subBtns.length) {
                            subBtns.linkbutton("enable");
                        }
                        subSubGrid.datagrid("clean");
                    }
                }
            }

            $.each(sViewConf, function(key, value) {
                if("toolbar" === key) {
                    $.each(value, function() {
                        var btnConf = this,
                            proxy;

                        if(!btnConf.handler) {
                            proxy = $.proxy($.noop);
                        } else if(_.isString(btnConf.handler) && commonBtnHandler[btnConf.handler]) {
                            btnConf.id = "button_" + btnConf.handler + "_" + viewId;
                            proxy = $.proxy(commonBtnHandler[btnConf.handler]);
                        } else if(_.isFunction(btnConf.handler)) {
                            proxy = $.proxy(btnConf.handler);
                        }

                        btnConf.handler = function(e) {
                            if(proxy) {
                                proxy.call(this, e, datagrid, btnConf);
                            } else {
                                throw "没有找到对应的处理器，请检查按钮的handler配置！"
                            }
                        };

                        //有修改按钮尝试绑定双击表格行事件
                        if(btnConf.text === "修改" && btnConf.iconCls === "icon-edit"  && !_.isFunction(sViewConf.onDblClickRow)) {
                            sViewConf.onDblClickRow = function(i, row, e) {
                                var $target = $(e.target),
                                    $modifyBtn;

                                if(!$target.closest(".datagrid-row").length) {
                                    return;
                                }

                                if(proxy) {
                                    //当修改按钮禁用时，双击不能启用修改事件。zhubc-20160510
                                    if(btnConf.id
                                        && ($modifyBtn = datagrid.datagrid("getToolbarButton", btnConf.id))
                                        && $modifyBtn.length
                                        && $modifyBtn.linkbutton("options").disabled){
                                        return;
                                    }
                                    proxy.call(this, window.event, datagrid, btnConf);
                                } else {
                                    throw "没有找到对应的处理器，请检查按钮的handler配置！"
                                }
                            }
                        }
                    });
                }
            });
        });
    }
    
    function onInitSuccess(){
    	
    }
    
    function loadIndicatorType(){
    	
    	var INDICATOR_NAME = $("input[name='INDICATOR_NAME']");
    	INDICATOR_NAME.val("");
    	
    	var BIZ_ATTR = $("input[comboname='BIZ_ATTR']");
    	var biz = '00';
    	if(BIZ_ATTR){
    		try{
    			biz = BIZ_ATTR.combobox("getValue");
    		}
    		catch(err){
    		}
    	}
    	
		var reqParam = {
				service : 'N0000004',
				BIZ_ATTR : biz
			};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			var INDICATOR_CLS = $("input[comboname='INDICATOR_CLS']");
	    	INDICATOR_CLS.combobox("setValue", "");
			INDICATOR_CLS.combobox("loadData", data[0]);
			var row =$("#RISK_MONITOR_SETTING_THRESHOLD").datagrid("getSelected");
	        if(row != null && row != undefined && opType=='U') {
	        	INDICATOR_CLS.combobox("setValue", row.INDICATOR_CLS);
	        }
		});
    }
    
    function loadDataIndicator(){
    	var INDICATOR_NAME = $("#INDICATOR_ID_A");
    	INDICATOR_NAME.combobox("setValue", "");
    	var INDICATOR_CLS = $("#INDICATOR_CLS_"+opType);
    	var BIZ_ATTR = $("#ABIZ_ATTR_D");
    	biz = BIZ_ATTR.combobox("getValue");
    	
		var reqParam = {
				service : 'N0000006',
				BIZ_ATTR : biz,
				INDICATOR_CLS:INDICATOR_CLS.combobox("getValue")
			};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			INDICATOR_NAME.combobox("loadData", data[0]);
		});
    }
    
    function loadDataIndicatorCls(){
    	
    	var INDICATOR_NAME = $("#INDICATOR_ID_A");
    	INDICATOR_NAME.combobox("setValue", "");
    	var INDICATOR_CLS = $("#INDICATOR_CLS_" +opType);
    	INDICATOR_CLS.combobox("setValue", "");
    	
    	var BIZ_ATTR = $("#ABIZ_ATTR_D");
    	biz = BIZ_ATTR.combobox("getValue");
    	
		var reqParam = {
				service : 'N0000004',
				BIZ_ATTR : biz
			};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			INDICATOR_CLS.combobox("loadData", data[0]);
			var row =$("#RISK_MONITOR_SETTING_THRESHOLD").datagrid("getSelected");
	        if(row != null && row != undefined && opType=='U') {
	        	INDICATOR_CLS.combobox("setValue", row.INDICATOR_CLS);
	        }
		});
    }
    
    function loadOneIndicator(){
    	var INDICATOR_NAME = $("#INDICATOR_ID_A");
    	var INDICATOR_CLS = $("#INDICATOR_CLS_"+opType);
    	var BIZ_ATTR = $("#ABIZ_ATTR_D");
    	biz = BIZ_ATTR.combobox("getValue");
    	
    	var INDICATOR_NAME = INDICATOR_NAME.combobox("getValue");
    	var row =$("#RISK_MONITOR_SETTING_THRESHOLD").datagrid("getSelected");
        if(row != null && row != undefined && opType =='U') {
        	INDICATOR_NAME = row.INDICATOR_ID;
        }
    	
		var reqParam = {
				service : 'N0000006',
				BIZ_ATTR : biz,
				INDICATOR_NAME:INDICATOR_NAME
			};
		ajax.post({
			req : reqParam
		}).then(function(data) {
			$("#INDICATOR_RESULT_TYPE_D").combobox("setValue", data[0][0].INDICATOR_RESULT_TYPE);
		});
		
		if(opType == 'U'){
			return;
		}
			
		reqParam = {
				service : 'THR00001',
				BIZ_ATTR : biz,
				INDICATOR_NAME:INDICATOR_NAME
			};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			clear('A');
			$("#tbody_1").show();
			for(var i = 0 ; i < data[0].length;i++){
				var temp = data[0][i];
				var THRES_NO = temp.THRES_NO;
				$("#tbody_" + THRES_NO).show();
				$("#THRES"+THRES_NO+"_COLOR").combobox("setValue", temp.THRES_COLOR);
				$("#THRES"+THRES_NO+"_NAME").val(temp.THRES_NAME);
				$("#THRES"+THRES_NO+"_OP").combobox("setValue", temp.THRES_OP);
				$("#THRESHOLD"+THRES_NO).val(temp.THRESHOLD);
				$("#THRES"+THRES_NO+"_OPER").combobox("setValue", temp.THRES_OPER);
			}
		});
    }
    
    function doAAdd(){
    	var tbody_4 = $("#tbody_4");
    	var tbody_3 = $("#tbody_3");
    	var tbody_2 = $("#tbody_2");
    	
    	if(tbody_2.is(':hidden')){
    		tbody_2.show();
    	}else if(tbody_3.is(':hidden')){
    		tbody_3.show();
    	}else if(tbody_4.is(':hidden')){
    		tbody_4.show();
    	}
    }
    
    function doADel(){
    	var tbody_4 = $("#tbody_4");
    	var tbody_3 = $("#tbody_3");
    	var tbody_2 = $("#tbody_2");
    	
    	var THRES_NO;
    	if(!tbody_4.is(':hidden')){
    		tbody_4.hide();
    		THRES_NO = 4;
    	}else if(!tbody_3.is(':hidden')){
    		tbody_3.hide();
    		THRES_NO = 3;
    	}else if(!tbody_2.is(':hidden')){
    		tbody_2.hide();
    		THRES_NO = 2;
    	}else{
    		return;
    	}
    	
    	$("#THRES"+THRES_NO+"_COLOR").combobox("setValue", '');
		$("#THRES"+THRES_NO+"_NAME").val('');
		$("#THRES"+THRES_NO+"_OPER").combobox("setValue", '');
		$("#THRESHOLD"+THRES_NO).val('');
		$("#THRES"+THRES_NO+"_OP").combobox("setValue", '');
    }
    
    function doSave(){
    	
    	var BIZ_ATTR = $("#ABIZ_ATTR_D").combobox("getValue");
    	var INDICATOR_CLS = $("#INDICATOR_CLS_"+opType).combobox("getValue");
    	var INDICATOR_ID = $("#INDICATOR_ID_A").combobox("getValue");
    	var INDICATOR_RESULT_TYPE = $("#INDICATOR_RESULT_TYPE_D").combobox("getValue");
    	var THRES1_OPER = $("#THRES1_OPER").combobox("getValue");
    	var THRESHOLD1 = $("#THRESHOLD1").val();
    	var THRES1_OP = $("#THRES1_OP").combobox("getValue");
    	var THRES1_COLOR = $("#THRES1_COLOR").combobox("getValue");
    	var THRES1_NAME = $("#THRES1_NAME").val();
    	var THRES2_OPER = $("#THRES2_OPER").combobox("getValue");
    	var THRESHOLD2 = $("#THRESHOLD2").val();
    	var THRES2_OP = $("#THRES2_OP").combobox("getValue");
    	var THRES2_COLOR = $("#THRES2_COLOR").combobox("getValue");
    	var THRES2_NAME = $("#THRES2_NAME").val();
    	var THRES3_OPER = $("#THRES3_OPER").combobox("getValue");
    	var THRESHOLD3 = $("#THRESHOLD3").val();
    	var THRES3_OP = $("#THRES3_OP").combobox("getValue");
    	var THRES3_COLOR = $("#THRES3_COLOR").combobox("getValue");
    	var THRES3_NAME = $("#THRES3_NAME").val();
    	var THRES4_OPER = $("#THRES4_OPER").combobox("getValue");
    	var THRESHOLD4 = $("#THRESHOLD4").val();
    	var THRES4_OP = $("#THRES4_OP").combobox("getValue");
    	var THRES4_COLOR = $("#THRES4_COLOR").combobox("getValue");
    	var THRES4_NAME = $("#THRES4_NAME").val();
    	
    	if(opType == 'U'){
    		var row =$("#RISK_MONITOR_SETTING_THRESHOLD").datagrid("getSelected");
            if(row != null && row != undefined) {
            	INDICATOR_ID = row.INDICATOR_ID;
            }
    	}
    	
    	var reqParam = {
				service : 'THR00002',
				BIZ_ATTR : BIZ_ATTR,
				INDICATOR_CLS:INDICATOR_CLS,
				INDICATOR_ID:INDICATOR_ID,
				INDICATOR_RESULT_TYPE:INDICATOR_RESULT_TYPE,
				THRES1_OPER:THRES1_OPER,
				THRES1_COLOR:THRES1_COLOR,
				THRES1_NAME:THRES1_NAME,
				THRES1_OP:THRES1_OP,
				THRESHOLD1:THRESHOLD1,
				THRES2_OPER:THRES2_OPER,
				THRES2_COLOR:THRES2_COLOR,
				THRES2_NAME:THRES2_NAME,
				THRES2_OP:THRES2_OP,
				THRESHOLD2:THRESHOLD2,
				THRES3_OPER:THRES3_OPER,
				THRES3_COLOR:THRES3_COLOR,
				THRES3_NAME:THRES3_NAME,
				THRES3_OP:THRES3_OP,
				THRESHOLD3:THRESHOLD3,
				THRES4_OPER:THRES4_OPER,
				THRES4_COLOR:THRES4_COLOR,
				THRES4_NAME:THRES4_NAME,
				THRES4_OP:THRES4_OP,
				THRESHOLD4:THRESHOLD4,
				opType:opType
			};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			$("#thresholdDialog").dialog('close');
			$("#RISK_MONITOR_SETTING_THRESHOLD").datagrid("reload");
		});
    }
    
    function doClose(){
    	$("#thresholdDialog").dialog('close');
    }
 
    function addInitData(){
    	opType = 'A';
		var panel = $(".panel-title");
		panel[1].innerHTML = "阈值设置-新增阈值";
		$("#thresholdDialog").dialog('open');
		clear();
		$("#doAAdd").show();
		$("#doADel").show();
		$("#tbody_1").show();
		$("#ABIZ_ATTR_D").combobox("enable");
    }
    
    function clear(isClear){
    	$("#tbody_1").hide();
		$("#tbody_4").hide();
    	$("#tbody_3").hide();
    	$("#tbody_2").hide();
    	//var INDICATOR_ID_A = $("#INDICATOR_ID_A");
    	$("#tr_INDICATOR_ID_A").hide();
    	$("#tr_INDICATOR_ID_U").hide();
    	$("#tr_INDICATOR_ID_"+opType).show();
    	
    	$("#tr_INDICATOR_CLS_A").hide();
    	$("#tr_INDICATOR_CLS_U").hide();
    	$("#tr_INDICATOR_CLS_"+opType).show();
    	
    	if(!isClear){
    		$("#INDICATOR_CLS_"+opType).combobox("options").disabled;
        	$("#ABIZ_ATTR_D").combobox("setValue", '');
        	$("#INDICATOR_CLS_"+opType).combobox("setValue", '');
        	$("#INDICATOR_RESULT_TYPE_D").combobox("setValue", '');
        	$("#INDICATOR_ID_U").val('');
        	$("#INDICATOR_ID_A").combobox("setValue", '');
    	}
    	
    	for(var THRES_NO = 1 ; THRES_NO <= 4 ; THRES_NO ++){
    		$("#THRES"+THRES_NO+"_COLOR").combobox("setValue", '');
    		$("#THRES"+THRES_NO+"_NAME").val('');
    		$("#THRES"+THRES_NO+"_OP").combobox("setValue", '');
			$("#THRESHOLD"+THRES_NO).val('');
			$("#THRES"+THRES_NO+"_OPER").combobox("setValue", '');
    	}
    }
    
    function updateInitData(){
    	var row =$("#RISK_MONITOR_SETTING_THRESHOLD").datagrid("getSelected");
        if(row == null || row == undefined) {
        	alert("请选择一行数据！");
        	return;
        }
        var panel = $(".panel-title");
    	panel[1].innerHTML = "阈值设置-修改阈值";
    	opType = 'U';
    	clear();
    	$("#doAAdd").hide();
		$("#doADel").hide();
    	
    	$("#ABIZ_ATTR_D").combobox("setValue", row.BIZ_ATTR);
    	$("#INDICATOR_RESULT_TYPE_D").combobox("setValue", row.INDICATOR_RESULT_TYPE);
    	$("#INDICATOR_ID_U").val(row.INDICATOR_NAME);
    	
    	$("#ABIZ_ATTR_D").combobox("disable");
    	
    	loadOneIndicator();
		loadDataIndicatorCls();
		
    	var reqParam = {
				service : 'THR00001',
				BIZ_ATTR:row.BIZ_ATTR,
				INDICATOR_NAME:row.INDICATOR_ID
			};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			for(var i = 0 ; i < data[0].length;i++){
				var temp = data[0][i];
				var THRES_NO = temp.THRES_NO;
				$("#tbody_" + THRES_NO).show();
				$("#THRES"+THRES_NO+"_COLOR").combobox("setValue", temp.THRES_COLOR);
				$("#THRES"+THRES_NO+"_NAME").val(temp.THRES_NAME);
				$("#THRES"+THRES_NO+"_OP").combobox("setValue", temp.THRES_OP);
				$("#THRESHOLD"+THRES_NO).val(temp.THRESHOLD);
				$("#THRES"+THRES_NO+"_OPER").combobox("setValue", temp.THRES_OPER);
			}
			$("#thresholdDialog").dialog('open');
		});
    }
    
    
    var opType;
    
    return {
    	$ready : exports.$ready,
    	onInitSuccess:onInitSuccess,
    	loadIndicatorType:loadIndicatorType,
    	loadDataIndicatorCls:loadDataIndicatorCls,
    	loadDataIndicator:loadDataIndicator,
    	loadOneIndicator:loadOneIndicator,
    	doSave : doSave,
		doClose : doClose,
		doAAdd : doAAdd,
		doADel : doADel
    };
});