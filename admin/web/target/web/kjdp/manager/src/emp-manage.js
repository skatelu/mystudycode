/**
 * 统一参数界面模块
 * @author chenk
 */
define(function(require, exports, module) {

    exports.exitOp = function() {
        var row = $("#KJDP_P_OP_MANAGE").datagrid("getSelected");
        if(_.isEmpty(row)) {
            alert("请选择表格中的一行数据！");
            return;
        }
        return ajax.post({
            service: "P0000091",
            OP_CODE: row.OP_CODE
        }).then(function() {
            alert("下线成功");
            $("#KJDP_P_OP_MANAGE").datagrid("reload");
        })
    }

});