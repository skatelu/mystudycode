/**
 *  员工开户
 * @author chenk
 */
define(function (require, exports, module) {
    /**
     * 证件类型变更，证件号码校验规则变更
     * @param o
     */
    function idTypeSelect(o){
        var $idCode = $("input[name='ID_CODE']");
        if(o.dict_val == "00"){
            $idCode.validatebox({validType:"cardno[true]"});
        }else{
            $idCode.validatebox({validType:"numchar[8,48]"});
        }
    }

    /**
     * 身份证号，获取出生日期和性别
     */
    function checkIdCode(){
        var idType = $("input[comboname='ID_TYPE']").combobox("getValue"),
            $idCode = $("input[name='ID_CODE']"),
            idCode = $idCode.textinput("getValue");
        if(idType == "00" && $idCode.validatebox("isValid")){
            var birthday,sex;
            if(idCode.length == 18){
                if(idCode.substr(16,1) % 2 == 0){
                    sex = "1";
                }else{
                    sex = "0"
                }
                birthday = idCode.substr(6,8);
            }else if(idCode.length == 15){
                if(idCode.substr(14,1) % 2 == 0){
                    sex = "1";
                }else{
                    sex = "0"
                }
                birthday = "19"+idCode.substr(6,6);
            }
            $("input[comboname='BIRTHDAY']").datebox("setValue",birthday);
            $("input[comboname='SEX']").combobox("setValue",sex);
        }
    }

    function empOpenFormInitSuccess() {
        $("input[name=ID_EXP_DATE]").parent().parent().addClass("relative").append('<label class="long-date-label"><input id="longCheck" type="checkbox"><span>长期</span></label>');
        $("input[name=OP_CODE]").parent().append($("<a id='createButton'>产生员工代码</a>").linkbutton({
            onClick: function() {
                ajax.post({
                    service: "P0000050"
                }).then(function(result) {
                    var data = result.getDataRow();
                    data && $("input[name=OP_CODE]").textinput("setValue", data.SEQ_VAL || "");
                })
            }
            }
        ))
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

    //开户
    function uemEmpOpen(){
        var empInfoForm = $("#KJDP_OPERATOR_BASE"),
            infoData = empInfoForm.form("getData");
        if (!empInfoForm.form("validate")) {
            return false;
        }
        var mask = loading("正在进行员工开户！");
        ajax.post($.extend({}, infoData, {
            service: "P0000042",
            OP_STATUS: "0"
        })).done(function(){
            alert("开户成功!");
            clearForm();
        }).always(function() {
            mask.destroy();
        })

    }

    /**
     * 清空
     */
    function clearForm(){
        var empInfoForm = $("#KJDP_OPERATOR_BASE");
        empInfoForm.form("reset");
        empInfoForm.find("#longCheck").prop("checked",false);
        empInfoForm.find("input[comboname='ID_EXP_DATE']").datebox("enable");
        empInfoForm.find("#createButton").linkbutton("enable");
    }

    return {
        idTypeSelect: idTypeSelect,
        checkIdCode: checkIdCode,
        submit: uemEmpOpen,
        clearForm: clearForm,
        empOpenFormInitSuccess: empOpenFormInitSuccess
    }

})
