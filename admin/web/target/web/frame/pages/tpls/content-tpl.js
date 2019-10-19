define(function() {
    return template.runtime(function($data) {
        "use strict";
        var $utils = this, $escape = ($utils.$helpers, $utils.$escape), height = $data.height, showWest = $data.showWest, split = $data.split, collapsable = $data.collapsable, border = $data.border, westTitle = $data.westTitle, westWidth = $data.westWidth, $out = "";
        return $out += '<div class="container-wrap clearfix"> <div class=\'container content\'> <div class="kui-layout clearfix" kui-options="fit:true,split:true" style="height:', 
        $out += $escape(height), $out += 'px;" data-default-height="', $out += $escape(height), 
        $out += '"> ', showWest && ($out += ' <div class="west-panel" kui-options="region:\'west\',split:', 
        $out += $escape(split), $out += ",collapsable:", $out += $escape(collapsable), $out += ",border:", 
        $out += $escape(border), $out += '" title="', $out += $escape(westTitle), $out += '" style="width:', 
        $out += $escape(westWidth), $out += 'px;"> </div> '), $out += ' <div class="center-panel" kui-options="region:\'center\'"> </div> </div> <div class="tabs-menu hide"> <div id="refresh" kui-options="iconCls:\'icon-reload\'">刷新</div> <div class="menu-sep"></div> <div id="close">关闭</div> <div id="closeAll">全部关闭</div> <div id="closeOther">关闭其他</div> <div class="menu-sep"></div> <div id="closeRight">关闭右侧</div> <div id="closeLeft">关闭左侧</div> </div> </div> <div class="container-lock hide"> <div class="lock-img"></div> </div> </div>', 
        new String($out);
    });
});