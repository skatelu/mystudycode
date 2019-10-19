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
        $("#UnoticeMark").dblclick(dbUlclick);
		$("#AnoticeMark").dblclick(dbAlclick);
	}

	/**
	 * 修改相关变量参考双击
	 */
	function dbUlclick() {
		$("#USCHEMA_CONT").val($("#USCHEMA_CONT").val() + $("#UnoticeMark").val());
	}

	/**
	 * 新增相关变量参考双击
	 */
	function dbAlclick() {
		$("#ASCHEMA_CONT").val($("#ASCHEMA_CONT").val() + $("#AnoticeMark").val());
	}
	
	/**
	 * 重置页面数据
	 */
	function init(t){
		$("#"+t+"SCHEMA_NAME").textinput("setValue", "");
		$("#"+t+"BIZ_ATTR").combobox("setValue", "");
		$("#"+t+"NOTICE_USER_ROLE").combobox("setValue", "");
		$("#"+t+"NOTICE_CHNL").combobox("setValues", "");
		$("#"+t+"NOTICE_CLS").combobox("setValue", "");
		$("#"+t+"SCHEMA_BACK").combobox("setValue", "");
		$("#"+t+"SCHEMA_VALIDE").combobox("setValue", "");
		$("#"+t+"SCHEMA_CONT").val("");
		if(t == 'A'){
			$("#"+t+"INDICATOR_ID").combobox("setValue", "");
			$("#"+t+"THRES_NO").combobox("setValue", "");
		}else{
			$("#"+t+"INDICATOR_ID").textinput("setValue", "");
			$("#"+t+"THRES_NO").textinput("setValue", "");
			$("#"+t+"UINDICATOR_NAME").textinput("setValue", "");
			$("#"+t+"UTHRES_NAME").textinput("setValue", "");
		}
		$("#"+t+"noticeMark").empty();
	}
	
	/**
	 * 初始化修改页面
	 */
	function updateData() {
		init('U');
		var row =$("#RISK_NOTICE_SCHEMA_INFO").datagrid("getSelected");
        if(!row) {
        	alert("请选择一行数据！");
        	return;
        }
		
		$("#updateDialog").dialog('open');
		var reqParam = {
				service : 'N0000005',
				INDICATOR_ID : row.INDICATOR_ID,
				THRES_NO:row.THRES_NO,
				NOTICE_USER_ROLE:row.NOTICE_USER_ROLE
			};
			return ajax.post({
				req : reqParam
			}).then(function(data) {
				var t = 'U';
				var d = data[0][0];
				$("#"+t+"SCHEMA_NAME").textinput("setValue", d.SCHEMA_NAME);
				$("#"+t+"BIZ_ATTR").combobox("setValue", d.BIZ_ATTR);
				$("#"+t+"NOTICE_USER_ROLE").combobox("setValue", d.NOTICE_USER_ROLE);
				$("#"+t+"NOTICE_CHNL").combobox("setValues", d.NOTICE_CHNL);
				$("#"+t+"NOTICE_CLS").combobox("setValue", d.NOTICE_CLS);
				$("#"+t+"SCHEMA_BACK").combobox("setValue", d.SCHEMA_BACK);
				$("#"+t+"SCHEMA_VALIDE").combobox("setValue", d.SCHEMA_VALIDE);
				$("#"+t+"SCHEMA_CONT").val(d.SCHEMA_CONT);
				$("#"+t+"INDICATOR_ID").textinput("setValue", d.INDICATOR_ID);
				$("#"+t+"THRES_NO").textinput("setValue", d.THRES_NO);
				$("#"+t+"INDICATOR_NAME").textinput("setValue", d.INDICATOR_NAME);
				$("#"+t+"THRES_NAME").textinput("setValue", d.THRES_NAME);
				
				riskNoticeMark($("#UnoticeMark"),$("#"+t+"INDICATOR_ID").textinput("getValue"));
		});
	}
	
	/**
	 * 通过业务属性查询指标
	 */
	function loadDataByBizAttrA() {
		$("#A_INDICATOR_TYPE").combobox("setValue", "");
		$("#ATHRES_NO").combobox("setValue", "");
		var reqParam = {
			service : 'N0000006',
			BIZ_ATTR : $("#ABIZ_ATTR").combobox("getValue")
		};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			$("#AINDICATOR_ID").combobox("loadData", data[0]);
		});
	}
	
	/**
	 * 查询指标阈值
	 */
	function QueryRiskThreshold(){
		riskNoticeMark($("#AnoticeMark"),$("#AINDICATOR_ID").combobox("getValue"));
		
		$("#ATHRES_NO").combobox("setValue", "");
		var reqParam = {
			service : 'N0000007',
			INDICATOR_ID : $("#AINDICATOR_ID").combobox("getValue")
		};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			$("#ATHRES_NO").combobox("loadData", data[0]);
		});
	}
	
	/**
	 * 查询相关变量参考
	 */
	function riskNoticeMark(noticeMark,INDICATOR_ID){
		noticeMark.empty();
		var reqParam = {
			service : 'N0000008',
			INDICATOR_ID : INDICATOR_ID
		};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			for(var i = 0 ; i < data[0].length ; i ++){
				noticeMark.append("<option value='"+data[0][i].MARK_NAME+"'>"+data[0][i].MARK_NAME+"</option>");
			}
		});
	}
	
	function loadDataByBizAttrS() {
		
		var INDICATOR_CLS = $("input[comboname='INDICATOR_CLS']");
    	INDICATOR_CLS.combobox("setValue", "");
    	
    	var INDICATOR_NAME = $("input[name='INDICATOR_NAME']");
    	INDICATOR_NAME.val("");
    	
    	var BIZ_ATTR = $("input[comboname='BIZ_ATTR']");
		
		var biz = BIZ_ATTR.combobox("getValue");
		loadDataByBizAttr(biz,INDICATOR_CLS);
	}

	function loadDataByBizAttr(biz, INDICATOR_TYPE) {
		var reqParam = {
			service : 'N0000004',
			BIZ_ATTR : biz
		};
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			INDICATOR_TYPE.combobox("loadData", data[0]);
		});
	}
	
	function check(value,title){
		if(!(value != null && value != '')){
			alert(title);
			return false;
		}
		return true;
	}

	/**
	 * 新增通知保持操作
	 */
	function doASave(op) {
		//$("#addDialog").dialog('close');
		var t = 'A';
		var SCHEMA_NAME = $("#"+t+"SCHEMA_NAME").textinput("getValue");
		var BIZ_ATTR = $("#"+t+"BIZ_ATTR").combobox("getValue");
		var INDICATOR_ID = $("#"+t+"INDICATOR_ID").combobox("getValue");
		var THRES_NO = $("#"+t+"THRES_NO").combobox("getValue");
		var NOTICE_USER_ROLE = $("#"+t+"NOTICE_USER_ROLE").combobox("getValue");
		var NOTICE_CHNL_ARRAYS = $("#"+t+"NOTICE_CHNL").combobox("getValues");
		var NOTICE_CLS = $("#"+t+"NOTICE_CLS").combobox("getValue");
		var SCHEMA_BACK = $("#"+t+"SCHEMA_BACK").combobox("getValue");
		var SCHEMA_VALIDE = $("#"+t+"SCHEMA_VALIDE").combobox("getValue");
		var SCHEMA_CONT = $("#"+t+"SCHEMA_CONT").val();
		
		if(!(check(BIZ_ATTR,'所属业务必选')&&
				check(INDICATOR_ID,'风险指标必选')&&
				check(THRES_NO,'风险指标阈值必选')&&
				check(NOTICE_USER_ROLE,'通知范围必选')&&
				check(SCHEMA_NAME,'通知主题必填')&&
				check(NOTICE_CHNL_ARRAYS,'通知渠道必选')&&
				check(NOTICE_CLS,'通知类型必选')&&
				check(SCHEMA_BACK,'反馈标志必选')&&
				check(SCHEMA_VALIDE,'启用状态必选')&&
				check(SCHEMA_CONT,'通知内容必填'))){
			return;
		}
		
		var NOTICE_CHNL = "";
		for(var i = 0 ; i <NOTICE_CHNL_ARRAYS.length ; i ++ ){
			NOTICE_CHNL+=  NOTICE_CHNL_ARRAYS[i];
			if(i < NOTICE_CHNL_ARRAYS.length - 1){
				NOTICE_CHNL += ",";
			}
		}
		
		var reqDetailParam = {
				service : 'N0000005',
				INDICATOR_ID : INDICATOR_ID,
				THRES_NO:THRES_NO,
				NOTICE_USER_ROLE:NOTICE_USER_ROLE
			};
		var reqParam = {
				service : 'N0000002',
				SCHEMA_NAME : SCHEMA_NAME,
				BIZ_ATTR : BIZ_ATTR,
				INDICATOR_ID : INDICATOR_ID,
				THRES_NO : THRES_NO,
				NOTICE_USER_ROLE : NOTICE_USER_ROLE,
				NOTICE_CHNL : NOTICE_CHNL,
				NOTICE_CLS : NOTICE_CLS,
				SCHEMA_BACK : SCHEMA_BACK,
				SCHEMA_VALIDE : SCHEMA_VALIDE,
				SCHEMA_CONT : SCHEMA_CONT
			};
		
		return ajax.post({
			req : reqDetailParam
		}).then(function(data) {
			if((data[0].length) == 0){
				return ajax.post({
					req : reqParam
				}).then(function(data) {
					$("#addDialog").dialog('close');
					loadData();
				});
			}else{
				alert("当前通知模板已经存在。");
			}
		});
	}

	function doAClose(op) {
		$("#addDialog").dialog('close');
		$("#updateDialog").dialog('close');
	}

	/**
	 * 修改通知保存操作
	 */
	function doUSave(op) {
		$("#addDialog").dialog('close');
		
		var t = 'U';
		var SCHEMA_NAME = $("#"+t+"SCHEMA_NAME").textinput("getValue");
		var BIZ_ATTR = $("#"+t+"BIZ_ATTR").combobox("getValue");
		var INDICATOR_ID = $("#"+t+"INDICATOR_ID").textinput("getValue");
		var THRES_NO = $("#"+t+"THRES_NO").textinput("getValue");
		var NOTICE_USER_ROLE = $("#"+t+"NOTICE_USER_ROLE").combobox("getValue");
		var NOTICE_CHNL_ARRAYS = $("#"+t+"NOTICE_CHNL").combobox("getValues");
		var NOTICE_CLS = $("#"+t+"NOTICE_CLS").combobox("getValue");
		var SCHEMA_BACK = $("#"+t+"SCHEMA_BACK").combobox("getValue");
		var SCHEMA_VALIDE = $("#"+t+"SCHEMA_VALIDE").combobox("getValue");
		var SCHEMA_CONT = $("#"+t+"SCHEMA_CONT").val();
		
		if(!(check(BIZ_ATTR,'所属业务必选')&&
				check(INDICATOR_ID,'风险指标必选')&&
				check(THRES_NO,'风险指标阈值必选')&&
				check(NOTICE_USER_ROLE,'通知范围必选')&&
				check(SCHEMA_NAME,'通知主题必填')&&
				check(NOTICE_CHNL_ARRAYS,'通知渠道必选')&&
				check(NOTICE_CLS,'通知类型必选')&&
				check(SCHEMA_BACK,'反馈标志必选')&&
				check(SCHEMA_VALIDE,'启用状态必选')&&
				check(SCHEMA_CONT,'通知内容必填'))){
			return;
		}
		
		var NOTICE_CHNL = "";
		for(var i = 0 ; i <NOTICE_CHNL_ARRAYS.length ; i ++ ){
			NOTICE_CHNL+=  NOTICE_CHNL_ARRAYS[i];
			if(i < NOTICE_CHNL_ARRAYS.length - 1){
				NOTICE_CHNL += ",";
			}
		}
		
		var reqParam = {
				service : 'N0000003',
				SCHEMA_NAME : SCHEMA_NAME,
				BIZ_ATTR : BIZ_ATTR,
				INDICATOR_ID : INDICATOR_ID,
				THRES_NO : THRES_NO,
				NOTICE_USER_ROLE : NOTICE_USER_ROLE,
				NOTICE_CHNL : NOTICE_CHNL,
				NOTICE_CLS : NOTICE_CLS,
				SCHEMA_BACK : SCHEMA_BACK,
				SCHEMA_VALIDE : SCHEMA_VALIDE,
				SCHEMA_CONT : SCHEMA_CONT
			};
		
		return ajax.post({
			req : reqParam
		}).then(function(data) {
			$("#addDialog").dialog('close');
			$("#updateDialog").dialog('close');
			loadData();
		});
	}

	function doUClose(op) {
		$("#addDialog").dialog('close');
		$("#updateDialog").dialog('close');
	}

	function loadThreshold() {
		$("#THRESHOLD_ID").val("");
	}
	
    /**
     * 处理view配置中的回调，主要用于处理通用界面表格的回调
     * @method handleCallbacks
     * @param viewConf {object} view配置
     * @return undefined
     */

    function handleCallbacks(viewConf) {
        var commonBtnHandler = {

        	noticedAdd: function(e, $datagrid, btnConf) {
        		init('A');
        		$("#addDialog").dialog('open');
            },

            noticedModify: function(e, $datagrid, btnConf) {
            	updateData();
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
    
    return {
    	$ready : exports.$ready,
    	onInitSuccess:onInitSuccess,
    	loadDataByBizAttrS : loadDataByBizAttrS,
		loadDataByBizAttrA : loadDataByBizAttrA,
		QueryRiskThreshold : QueryRiskThreshold,
		loadThreshold : loadThreshold,
		doASave : doASave,
		doAClose : doAClose,
		doUSave : doUSave,
		doUClose : doUClose
    };

});