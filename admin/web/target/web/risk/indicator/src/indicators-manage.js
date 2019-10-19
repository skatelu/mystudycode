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
    }
    /**
     * 处理view配置中的回调，主要用于处理通用界面表格的回调
     * @method handleCallbacks
     * @param viewConf {object} view配置
     * @return undefined
     */

    function handleCallbacks(viewConf) {
        var commonBtnHandler = {

            commonAdd: function(e, $datagrid, btnConf) {
                var mainDatagridId = $datagrid.data("mainDatagrid"),
                    $mainDatagrid = mainDatagridId ? $("#" + mainDatagridId) : $(),
                    subDatagridId = $datagrid.data("subDatagrid"),
                    $subDatagrid = subDatagridId ? $("#" + subDatagridId) : $(),
                    mainRowObj;

                if($mainDatagrid.length && !(mainRowObj = $mainDatagrid.datagrid("getSelected"))) {
                    alert("请选择主表格的一行数据！", alert.ERROR);
                    return;
                }

                parser.createCommonDialog({
                    title: btnConf.title || "新增",
                    isConfirm: btnConf.isConfirm,
                    confirmMsg: btnConf.confirmMsg,
                    colNumbers: btnConf.colNumbers,
                    $datagrid: $datagrid,
                    width: btnConf.panelWidth,
                    height: btnConf.panelHeight,
                    record: mainRowObj,
                    req: btnConf.req && btnConf.req[0],
                    onBeforeOpen: btnConf.onBeforeOpen,
                    onFormInitSuccess: btnConf.onFormInitSuccess,
                    onBeforeLoad: btnConf.onBeforeLoad,
                    onBeforeSave: btnConf.onBeforeSave,
                    onSaveSuccess: function(formData) {
                        $datagrid.datagrid("reload", null, function(){
                            var index = $datagrid.datagrid("getRowIndex", formData);
                            index > -1 && $datagrid.datagrid("selectRow", index);

                            //如果查找到新增的数据行则选中
                            if(index > -1) {
                                $datagrid.datagrid("selectRow", index)
                            } else if($subDatagrid.length) {
                                $subDatagrid.datagrid("loadData", {});
                            }

                            _.isFunction(btnConf.onSaveSuccess) && btnConf.onSaveSuccess.call(this, formData);
                        });
                    }
                });
            },

           /* commonModify: function(e, $datagrid, btnConf) {
                parser.createCommonDialog({
                    modify: true,
                    title: btnConf.title || "修改",
                    isConfirm: btnConf.isConfirm,
                    confirmMsg: btnConf.confirmMsg,
                    colNumbers: btnConf.colNumbers,
                    $datagrid: $datagrid,
                    width: btnConf.panelWidth,
                    height: btnConf.panelHeight,
                    req: btnConf.req && btnConf.req[0],
                    onBeforeOpen: btnConf.onBeforeOpen,
                    onFormInitSuccess: btnConf.onFormInitSuccess,
                    onBeforeLoad: btnConf.onBeforeLoad,
                    onBeforeSave: btnConf.onBeforeSave,
                    onSaveSuccess: function(formData) {
                        //修改成功后前端更新行数据，不重新请求数据刷新表格
                        var index = $datagrid.datagrid("getRowIndex", $datagrid.datagrid("getSelected"));
                        $datagrid.datagrid("updateRow", index, formData);

                        _.isFunction(btnConf.onSaveSuccess) && btnConf.onSaveSuccess.call(this, formData);
                    }
                });
            },*/
            IndicatorModify: function(e, $datagrid, btnConf) {
            	updateInitData();
            },
            

            commonDelete: function(e, $datagrid, btnConf) {
                var row = $datagrid.datagrid("getSelected"),
                    subDatagridId = $datagrid.data("subDatagrid"),
                    $subDatagrid = subDatagridId ? $("#" + subDatagridId) : $();

                if(!row) {
                    alert("请选择一行数据！");
                    return;
                }

                if(_.isFunction(btnConf.onBeforeDelete) && false === btnConf.onBeforeDelete(row)) {
                    return;
                }

                confirm(btnConf.confirmMsg || "确定要删除选中的记录吗？", function(flag) {
                    if(flag) {
                        if(btnConf.req && btnConf.req.length) {
                            ajax.post({
                                req: $.extend({}, btnConf.req && btnConf.req[0], row)
                            }).done(function() {
                                alert("删除成功！");
                                $subDatagrid.length && $subDatagrid.datagrid("loadData", {});
                                $datagrid.datagrid("clearSelections");
                                _.isFunction(btnConf.onDeleteSuccess) && btnConf.onDeleteSuccess(row);
                            }).always(function() {
                                //qc2780：若是发布订阅，删除功能不会reload数据的缺陷
                                $datagrid.datagrid("reload");
                            });
                        }
                    }
                }, {
                    title: btnConf.title || "提示"
                });
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
                        if(btnConf.text === "管理" && btnConf.iconCls === "icon-edit"  && !_.isFunction(sViewConf.onDblClickRow)) {
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
		$("input[name='INDICATOR_NAME']").attr('placeholder','请输入指标名称或ID');
	}
	 /**
	 * 通过业务属性查询指标
	 */
	 function loadIndicatorTypes(){
	    	
	    	var INDICATOR_CLS = $("input[comboname='INDICATOR_CLS']");
	    	INDICATOR_CLS.combobox("setValue", "");
	    	
	    	var INDICATOR_NAME = $("input[name='INDICATOR_NAME']");
	    	INDICATOR_NAME.val("");
	    	
	    	var BIZ_ATTR = $("input[comboname='BIZ_ATTR']");
	    	
			var reqParam = {
					service : 'N0000004',
					BIZ_ATTR : BIZ_ATTR.combobox("getValue")
				};
			return ajax.post({
				req : reqParam
			}).then(function(data) {
				INDICATOR_CLS.combobox("loadData", data[0]);
			});
	    }
	 
	 /**
		 * 修改弹框通过业务属性查询指标
		 */
		 function loadDataIndicatorCls(){
		    	
		    	/*var INDICATOR_NAME = $("#INDICATOR_ID_A");
		    	INDICATOR_NAME.combobox("setValue", "");*/
		    	var INDICATOR_CLS = $("input[comboname='INDICATOR_CLS_A']");
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
					var row =$("#RISK_INDICATORS_MANAGE_INFO").datagrid("getSelected");
			        INDICATOR_CLS.combobox("setValue", row.INDICATOR_CLS);
				});
		    }
	//指标管理修改页面填充数据	 
	 function updateInitData(){
     	var row =$("#RISK_INDICATORS_MANAGE_INFO").datagrid("getSelected");
         if(row == null || row == undefined) {
         	alert("请选择一行数据！");
         	return;
         }
         $("#INDICATOR_ID_A").val(row.INDICATOR_ID);
         $("#INDICATOR_NAME_A").val(row.INDICATOR_NAME);
         $("#INDICATOR_REMARK_A").val(row.INDICATOR_REMARK);
         $("#INDICATOR_FORMULA_A").val(row.INDICATOR_FORMULA);
         var panel = $(".panel-title");
     	panel[1].innerHTML = "指标管理";
     	$("#ABIZ_ATTR_D").combobox("setValue", row.BIZ_ATTR);
     	loadDataIndicatorCls();
     	$("#indicatorDialog").dialog('open');
     }
	 
	 function doSave(){
	    	var INDICATOR_ID = $("#INDICATOR_ID_A").val();
	    	var BIZ_ATTR = $("#ABIZ_ATTR_D").combobox("getValue");
	    	var INDICATOR_CLS = $("#INDICATOR_CLS_A").combobox("getValue");
	    	var INDICATOR_NAME = $("#INDICATOR_NAME_A").val();
	    	var INDICATOR_REMARK = $("#INDICATOR_REMARK_A").val();
	    	var reqParam = {
					service : 'INDI00003',
					INDICATOR_ID : INDICATOR_ID,
					BIZ_ATTR:BIZ_ATTR,
					INDICATOR_CLS:INDICATOR_CLS,
					INDICATOR_NAME:INDICATOR_NAME,
					INDICATOR_REMARK:INDICATOR_REMARK
				};
			return ajax.post({
				req : reqParam
			}).then(function(data) {
				$("#indicatorDialog").dialog('close');
				$("#RISK_INDICATORS_MANAGE_INFO").datagrid("reload");
			});
	    }
	    
	    function doClose(){
	    	$("#indicatorDialog").dialog('close');
	    }
	// 暴露函数到外部。
	return {
		$ready : exports.$ready,
		onInitSuccess:onInitSuccess,
		loadDataIndicatorCls:loadDataIndicatorCls,
		doSave : doSave,
		doClose : doClose,
		loadIndicatorTypes:loadIndicatorTypes
	};
});