define(function() {
    return template.runtime(function($data) {
        "use strict";
        var $utils = this, $each = ($utils.$helpers, $utils.$each), menuPurList = $data.menuPurList, $escape = ($data.$value, 
        $data.$index, $utils.$escape), cascadeCheck = $data.cascadeCheck, $out = "";
        return $out += '<div id="menuTreeTpl"> ', $each(menuPurList, function($value) {
            $out += ' <div title="', $out += $escape($value.DICT_ITEM_NAME), $out += '" class="menu-pur"> <div class="toolbar top-toolbar"> <a class="kui-linkbutton expandAll">全部展开</a> <a class="kui-linkbutton collapseAll">全部折叠</a> <a class="kui-linkbutton checkedAll">&nbsp;&nbsp;全选&nbsp;&nbsp;</a> <a class="kui-linkbutton unChecked">&nbsp;&nbsp;反选&nbsp;&nbsp;</a> <a class="kui-linkbutton unCheckedAll">&nbsp;&nbsp;全清&nbsp;&nbsp;</a> <a class="kui-linkbutton reset">&nbsp;&nbsp;重置&nbsp;&nbsp;</a> </div> <div id="menuPur', 
            $out += $escape($value.DICT_ITEM), $out += '" class="tree-box kui-tree" kui-options="cascadeCheck:', 
            $out += $escape(cascadeCheck), $out += ",checkbox:true,animate:true,conf:{treeType:'1',parNode:'PAR_MENU',nodeId:'MENU_ID',nodeName:'MENU_NAME'},animate:true,animateSpeed:300\"></div> </div> ";
        }), $out += ' <div class="toolbar bottom-toolbar"> <a class="kui-linkbutton submit" kui-options="iconCls:\'icon-save\'">提交</a> </div> </div>', 
        new String($out);
    });
});