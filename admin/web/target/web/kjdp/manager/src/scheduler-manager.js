/**
 * 自动任务管理
 * @author liuqing
 */
define(function(require, exports, module) {

    var opInfo = top.kjdp.getOpInfo(),
        $form,
        SchedulerCornView = require("./scheduler-corn.js") ;

    function onClickRow(rowIndex, rowData, e){
        if($(e.target).hasClass("runJobImmediately")) {
            runJobImmediately(rowData);
        } else if($(e.target).hasClass("pauseTrigger")) {
            pauseTrigger(this,rowData);
        } else if($(e.target).hasClass("resumeTrigger")) {
            resumeTrigger(this,rowData);
        } else if($(e.target).hasClass("deleteTrigger")){
            deleteTrigger(this,rowData);
        }
    }

    function jobOperatorFormatter(value, rowData){
         return '<a class="runJobImmediately">立即执行</a>';
    }

    function triggerOperatorFormatter(field, rowData, index){
        var dom = "";
        var state = rowData["TRIGGER_STATE"];
        if("2" == state) {
            dom = '&nbsp;<a href="javascript:void(0)" class="resumeTrigger">恢复</a>';
        } else {
            dom = '&nbsp;<a href="javascript:void(0)" class="pauseTrigger">挂起</a>';
        }
        return dom;
    }

    function runJobImmediately(rowData){
        ajax.post({
            req: {
                service: "P0000095",
                JOB_NAME: rowData.JOB_NAME,
                JOB_GROUP: rowData.JOB_GROUP
            }
        }).then(function(data){
            top.alert("任务执行成功.");
        });
    }

    function pauseTrigger(target, rowData){
        ajax.post({
            req: {
                service: "P0000102",
                TRIGGER_NAME: rowData.TRIGGER_NAME,
                TRIGGER_GROUP: rowData.TRIGGER_GROUP
            }
        }).then(function(data){
            top.alert("触发器【"  + rowData.DESCRIPTION + "】已挂起.");
            $(target).datagrid("reload");
        });
    }

    function resumeTrigger(target, rowData){
        ajax.post({
            req: {
                service: "P0000103",
                TRIGGER_NAME: rowData.TRIGGER_NAME,
                TRIGGER_GROUP: rowData.TRIGGER_GROUP
            }
        }).then(function(data){
            top.alert("触发器【"  + rowData.DESCRIPTION + "】已恢复.");
            $(target).datagrid("reload");
        });
    }

    function deleteTrigger(target, rowData){
        ajax.post({
            req: {
                service: "YG003487",
                TRIGGER_NAME: rowData.TRIGGER_NAME,
                TRIGGER_GROUP: rowData.TRIGGER_GROUP
            }
        }).then(function(data){
            top.alert("触发器已删除.");
            $(target).datagrid("reload");
        });
    }

    //选择触发的自动任务
    function jobSelect(obj) {
        $form.find("input[comboname=JOB_GROUP]").combobox("setValue", obj.JOB_GROUP);
    }

    //form表单初始化完成
    function onFormInitSuccess(rowObj){
        $form = $(this);
        var $condValue = $form.find("input[name='CRON_EXPRESSION']"),
            $btn = $("<a>编辑</a>").insertAfter($condValue);
        $btn.linkbutton({
            onClick : function(){
                SchedulerCornView.init({cornValue:$condValue[0].value,saveCorn:function(cornValue){
                    $condValue.textinput("setValue",cornValue);
                }});
            }
        });
    }

    return {
        jobOperFormatter : jobOperatorFormatter,
        triggerOperFormatter: triggerOperatorFormatter,
        onClickRow: onClickRow,
        onFormInitSuccess : onFormInitSuccess,
        jobSelect: jobSelect
    }

});