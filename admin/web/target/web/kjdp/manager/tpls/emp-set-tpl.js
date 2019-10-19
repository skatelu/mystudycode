define(function() {
    return template.runtime(function($data) {
        "use strict";
        var $utils = this, $escape = ($utils.$helpers, $utils.$escape), empSetTitle = $data.empSetTitle, $each = $utils.$each, permissionData = $data.permissionData, girdIdPre = ($data.permission, 
        $data.p, $data.girdIdPre), $out = "";
        return $out += '<div id="empSetTpl" class="clearfix hide"> <fieldset class="kui-fieldset"> <legend>', 
        $out += $escape(empSetTitle), $out += "管理权限设置（白名单设置）（允许", $out += $escape(empSetTitle), 
        $out += '访问指定类型数据）</legend> <div class="clearfix table-box"> <div class="kui-tabs tabs-box" kui-options="border:false"> ', 
        $each(permissionData, function(permission) {
            $out += ' <div title="', $out += $escape(permission && permission.DICT_ITEM_NAME), 
            $out += '" style="width: auto; height: 200px;"> <table class="kui-datagrid" id="', 
            $out += $escape(girdIdPre), $out += "_", $out += $escape(permission && permission.DICT_ITEM), 
            $out += '" ></table> </div> ';
        }), $out += " </div> </div> </fieldset> </div>", new String($out);
    });
});