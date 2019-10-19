/**
 *  员工岗位设置
 * @author chenk
 */
define(function (require, exports, module) {

    var EmpInfoView = require("./emp-info-view"),$post,
        $mainPost,
        empInfoView;

    function $ready(){
        empInfoView = new EmpInfoView({
            el : "#empInfoPublic",
            searchEmpInfoCallback : searchEmpInfoCallback,
            resetEmpInfoCallback : function(){
                $("#KJDP_EMP_POST_SET").form("clear");
            }
        });
    }

    //获取员工岗位信息并加载
    function searchEmpInfoCallback(empInfo){
        if(empInfo.postArr && $.isArray(empInfo.postArr)){
            var postArr = [], mainPostId= "";
            for(var i = 0; i < empInfo.postArr.length; i++){
                if(empInfo.postArr[i].MAIN_FLAG == "1") {
                    mainPostId = empInfo.postArr[i].POST_ID;
                }
                postArr.push(empInfo.postArr[i].POST_ID);
            }
            $("#KJDP_EMP_POST_SET").form("load",{POST_ID:postArr.join(",")});
            changeMainPostData();
            $mainPost.combobox("select", mainPostId);

        }
    }

    /**
     * 员工岗位设置
     */
    function empPostSetSubmit(){
        var empInfo = empInfoView.getEmpInfo(),
            mainPostValue = $mainPost.combobox("getValue");
        if(!empInfo || $.isEmptyObject(empInfo)){
            alert("请填写员工信息！");
            return;
        }
        var $form = $("#KJDP_EMP_POST_SET");
        if(!$form.form("validate")) {
            return;
        }
        var formData = $form.form("getData"),
            postArr = formData.POST_ID.split(","),
            oldPostArr = _.pluck(empInfoView.getPostArr(), "POST_ID"),
            oldMainPostObj=_.find(empInfoView.getPostArr(), function(v) {
                return v.MAIN_FLAG == "1";
            }),
            oldMainPostId = oldMainPostObj && oldMainPostObj.POST_ID,
            addPost = _.difference(postArr, oldPostArr),
            deletePost = _.difference(oldPostArr, postArr),
            req = _.map(addPost, function(v) {
                return {
                    service: "P0000055",
                    OP_CODE: empInfo.OP_CODE,
                    MAIN_FLAG: v.POST_ID == mainPostValue ? "1" : "0",
                    POST_ID: v
                }
            }).concat(_.map(deletePost, function(v) {
                return {
                    service: "P0000057",
                    OP_CODE: empInfo.OP_CODE,
                    POST_ID: v
                }
            }));
        if(oldMainPostId && oldMainPostId != mainPostValue && !_.contains(deletePost, oldMainPostId)) {
            req.push({
                service: "P0000056",
                OP_CODE: empInfo.OP_CODE,
                MAIN_FLAG: "0",
                POST_ID: oldMainPostId
            })
        }
        if(!oldMainPostId || (oldMainPostId != mainPostValue && !_.contains(addPost, mainPostValue))) {
            req.push({
                service: "P0000056",
                OP_CODE: empInfo.OP_CODE,
                MAIN_FLAG: "1",
                POST_ID: mainPostValue
            })
        }
        var mask = loading("岗位设置中……");
        ajax.post(req).then(function() {
            alert("岗位设置成功！");
            empInfoView.searchEmpInfo();
        }).always(function() {
            mask.destroy();
        })
    }

    /**
     * 清空页面数据
     */
    function empPostSetCancel() {
        empInfoView.resetEmpInfo();
    }

    function changeMainPostData() {
        var allData = $post.combobox("getData"),
            value = $post.combobox("getValues"), mainPostValue = $mainPost.combobox("getValue");
        $mainPost.combobox("loadData", _.filter(allData, function(data) {
            return _.contains(value, data.POST_ID);
        }));
        if(mainPostValue && !_.contains(value, mainPostValue)) {
            $mainPost.combobox("clear");
        }
    }

    function onInitSuccess() {
        $post = $(this).find("[comboname=POST_ID]").combobox({
            onSelect: changeMainPostData,
            onUnselect: changeMainPostData,
            onSelectAll: changeMainPostData,
            onUnselectAll: changeMainPostData,
            onClear: changeMainPostData
        });
        $mainPost = $(this).find("[comboname=MAIN_POST_ID]");
    }


    //暴露函数到外部。
    return {
        $ready : $ready,
        empPostSetSubmit:empPostSetSubmit,
        empPostSetCancel:empPostSetCancel,
        onInitSuccess:onInitSuccess
    };

})
