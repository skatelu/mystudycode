/**
 *  员工资料变更
 * @author chenk
 */
define(function (require, exports, module) {



    /**
     * 清空
     */
    function clearForm(){
        var empInfoForm = $("#KJDP_OPERATOR_BASE");
        empInfoForm.form("reset");
        empInfoForm.find("input[comboname='CHANNELS']").combobox("setValues","");
        empInfoForm.find("#longCheck").prop("checked",false);
        empInfoForm.find("input[comboname='ID_EXP_DATE']").datebox("enable");
        empInfoForm.find("#createButton").linkbutton("enable");
        $("#OP_CODE").textinput("setValue","").textinput("enable");
        $("#searchEmpInfo").linkbutton("enable");
    }

    function empOpenFormInitSuccess() {
        $(this).form("disable", ["OP_CODE", "ORG_CODE"]);
        $("input[name=ID_EXP_DATE]").parent().parent().addClass("relative").append('<label class="long-date-label"><input id="longCheck" type="checkbox"><span>长期</span></label>');
        //长期按钮
        $("#longCheck").bind("click",function(){
            var idExpDate = $("input[comboname='ID_EXP_DATE']");
            if($(this).prop("checked")){
                idExpDate.datebox("setValue","30001231").datebox("disable");
            }else{
                idExpDate.datebox("setValue","").datebox("enable");
            }
        });
    }

    /**
     * 加载员工信息
     */
    function loadDataByUserCode(){
        var empInfoForm = $("#KJDP_OPERATOR_BASE"),
            $userCode = $("#OP_CODE"),
            userCode = $userCode.textinput("getValue");
        if(!$userCode.validatebox('isValid')){
            $userCode.focus();
            return;
        }
        if(!userCode){
            alert("员工代码不能为空");
            return;
        }

        ajax.post({
            service: "P0000041",
            OP_CODE: userCode
        }).then(function(result) {
            var data = result.getDataRow();
            if(!data) {
                alert("指定的员工代码【"+userCode+"】在系统内尚未开户或没有该员工的管理权限！",function(){
                    $userCode.val("").focus();
                });
                return;
            }
            if(data.OP_STATUS != '0'){
                alert("员工代码【"+userCode+"】状态不是正常状态，不能进行资料变更！", function(){
                    $userCode.val("").focus();
                });
                return;
            }
            empInfoForm.form("load", data);
            $userCode.textinput("disable");
            $("#searchEmpInfo").linkbutton("disable");
            var $idCode = $("input[name='ID_CODE']");
            if(data.ID_TYPE == "00"){
                $idCode.validatebox({validType:"cardno[true]"});
            }else{
                $idCode.validatebox({validType:"numchar[8,48]"});
            }

        })
    }

    //资料修改
    function uemEmpChange(){
        var empInfoForm = $("#KJDP_OPERATOR_BASE"),
            infoData = empInfoForm.form("getData");
        if (!empInfoForm.form("validate")) {
            return false;
        }
        if(!infoData.OP_CODE) {
            alert("员工代码不能为空");
            return;
        }
        var mask = loading("正在进行员工资料修改！");
        ajax.post($.extend({}, infoData, {
            service: "P0000043"
        })).done(function(){
            alert("资料修改成功!");
            clearForm();
        }).always(function() {
            mask.destroy();
        })

    }

    return {
        submit: uemEmpChange,
        loadDataByUserCode:loadDataByUserCode,
        empOpenFormInitSuccess: empOpenFormInitSuccess,
        clearForm: clearForm
    }

})
