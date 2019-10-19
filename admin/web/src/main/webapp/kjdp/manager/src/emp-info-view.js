/**
 * 员工操作-员工信息模块
 * @author zhubc
 */
define(function (require, exports, module) {

    module.exports = Backbone.View.extend({
        initialize: function(opts) {
            this.opts = $.extend({},this.defOpts,opts);
            this.$el.prepend(this.template());
            this.$tpls = this.$("#empInfoTpl");
            parser.parse(this.$tpls);
            this.$userCode = this.$tpls.find("#OP_CODE");
            this.$fieldset = this.$tpls.find("fieldset");
            var that = this;
            this.userCode = "";
            this.empInfo = {};
            this.$fieldset.fieldset({
                closable : this.opts.closable,
                closeCallback : function(){that.closeCallback()},
                expendCallback : function(){that.expendCallback()}
            });
            this.initTpl();
        },
        template: artTemplate.compile('<div id="empInfoTpl" class="clearfix" style="min-height: 122px">' +
            '<fieldset>' +
                '<legend>员工信息</legend>' +
                '<div class="clearfix tpl-dl-form">' +
                    '<div class="tpl-dl">' +
                        '<span class="form-label op-code-input">员工代码：</span>' +
                        '<input id="OP_CODE" name="OP_CODE" class="kui-textinput" kui-options="focus2Next:false,required:true,width:\'120\',validType:\'num[1,8]\',maxLength:8"/>' +
                    '</div>' +
                    '<div class="tpl-dl">' +
                        '<a id="searchEmpInfo" class="kui-linkbutton" kui-options="iconCls:\'icon-search\'">查询</a>' +
                        '<a id="resetEmpInfo" class="kui-linkbutton" kui-options="iconCls:\'icon-cancel\'">重置</a>' +
                    '</div>' +
                '</div>' +
            '</fieldset>' +
            '</div>'),
        initTpl : function(){
            var that = this;
            return parser.getViewConfig(this.opts.empInfoTplId).done(function(res){
                that.tpl = res[that.opts.empInfoTplId];
                that.columns = that.tpl.columns;
                var tplColArr = [];
                for(var i = 0; i < that.columns.length; i++){
                    var col = that.columns[i];
                    tplColArr.push('<div class="tpl-dl">' +
                        '<label class="dl-label">'+col['title']+'：</label>' +
                        '<span class="label-value" name="'+col['field']+'" '+(col.editor.options.dict?'dict="'+col.editor.options.dict+'"':'')+'></span>' +
                        '</div>');
                    try{
                        if(col.editor.options.data){
                            var dictArr = col.editor.options.data,
                                temp = {};
                            for(var j = 0; j < dictArr.length; j++){
                                temp[dictArr[j].dict_val] = dictArr[j];
                            }
                            that.opts.dataCache[col.editor.options.dict] = temp;
                        }
                    }catch(e){}
                }
                that.$fieldset.append(tplColArr.join(""));
                that.expendCallback();
            });
        },
        //默认回调事件配置
        defOpts : {
            empInfoTplId : "KJDP_EMP_INFO",
            checkFreeze : true,//是否检查冻结状态的用户
            closable : false,
            closeCallback : $.noop,
            expendCallback : $.noop,
            searchEmpInfoCallback : $.noop,
            resetEmpInfoCallback : $.noop,
            dataCache : {}
        },
        //事件绑定
        events: {
            "keydown #OP_CODE" : "keydownUserCode",
            "click #searchEmpInfo" : "searchEmpInfo",
            "click #resetEmpInfo" : "resetEmpInfo"
        },
        //输入框keydown事件
        keydownUserCode : function(e){
            if(e.keyCode == 13 || e.keyCode == 9){
                this.searchEmpInfo();
            }
        },
        //查询员工信息
        searchEmpInfo : function(){
            this.beforeSearchEmpInfo();
            var userCode = this.$userCode.textinput("getValue");
            if(!this.$userCode.validatebox('isValid')){
                this.$userCode.focus();
                return;
            }
            var userObj = top.kjdp.getOpInfo();
            if(userObj.OP_CODE == userCode){
                var flag = true;
                for(var i = 0; i < userObj.POST_DATA.length; i++){
                    if(userObj.POST_DATA[i].POST_ID == "8888"){
                        flag = false;
                    }
                }
                if(flag){
                    alert("员工代码【"+userCode+"】不能对自己进行操作！");
                    this.resetEmpInfo();
                    return;
                }
            }
            var that = this;
            ajax.post([{
                service: "P0000041",
                OP_CODE: userCode
            }, {
                service: "P0000007",
                OP_CODE: userCode
            }]).then(function(result, results) {
                var data = results.get(0).getDataRow(),
                    postArr = results.get(1).getData()[0] || [];
                if(_.isEmpty(data)) {
                    alert("员工代码【"+userCode+"】尚未在系统中开户，或您没有员工代码【"+userCode+"】的管理权限");
                    that.resetEmpInfo();
                    return;
                }
                if(!data){
                    alert("员工代码【"+userCode+"】尚未在系统中开户，或您没有员工代码【"+userCode+"】的管理权限");
                    that.resetEmpInfo();
                    return;
                }
                if(data.OP_STATUS == "9"){
                    alert("员工代码【"+userCode+"】已被注销，不能进行操作！");
                    that.resetEmpInfo();
                    return;
                }
                if(data.OP_STATUS !== "0" && that.opts.checkFreeze){
                    alert("员工代码【"+userCode+"】员工状态不是正常状态，不能进行操作！");
                    that.resetEmpInfo();
                    return;
                }
                that.empInfo = data;
                that.empInfo.postArr = postArr;
                that.userCode = userCode;

                if(postArr && postArr.length > 0){
                    var postNameArr = [];
                    for(var i = 0; i < postArr.length; i++){
                        postNameArr.push(postArr[i].POST_NAME);
                    }
                    data["POST_NAME"] = postNameArr.join(",");
                }
                that.$(".label-value").each(function(){
                    var $this = $(this),
                        name = $this.attr("name"),
                        dictName = $this.attr("dict"),
                        val = $.trim(data[name]),
                        dataCache = that.opts.dataCache;
                    if(name && val){
                        if(dictName && dataCache[dictName] && dataCache[dictName][val]){
                            $this.text(dataCache[dictName][val].dict_des).attr("title",dataCache[dictName][val].dict_des);
                        }else{
                            $this.text(val).attr("title",val);
                        }
                    }
                });
                that.opts.searchEmpInfoCallback.call(that,data);

            }).fail(function() {
                that.resetEmpInfo();
            })
        },
        //重置员工信息
        resetEmpInfo : function(){
            this.$(".label-value").each(function(){
                $(this).text("").attr("title","");
            });
            this.$userCode.textinput("setValue","");
            this.userCode = "";
            this.empInfo = {};
            this.opts.resetEmpInfoCallback.call();
        },
        //查询员工信息之前清空页面
        beforeSearchEmpInfo : function(){
            this.$(".label-value").each(function(){
                $(this).text("");
            });
            this.opts.resetEmpInfoCallback.call();
        },
        //获取员工信息
        getEmpInfo : function(){
            return this.empInfo;
        },
        //获取员工机构代码
        getOrgCode : function(){
            return this.empInfo ? this.empInfo.ORG_CODE : "";
        },
        //获取员工代码
        getUserCode : function(){
            return this.userCode || "";
        },
        //获取岗位信息
        getPostArr : function(){
            return this.empInfo.postArr
        },
        //关闭回调函数
        closeCallback : function(){
            if(this.userCode){
                this.$fieldset.find("legend").append("<span class='user_code' style='color: #5B5B5B;'>员工代码："+this.userCode+"</span>");
            }
            this.opts.closeCallback.call();
        },
        //展开回调函数
        expendCallback : function(){
            this.$fieldset.find("legend .user_code").remove();
            this.opts.expendCallback.call();
        }
    });
});
