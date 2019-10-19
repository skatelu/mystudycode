define(function() {
    return template.runtime(function($data) {
        "use strict";
        var $utils = this, $each = ($utils.$helpers, $utils.$each), cycles = $data.cycles, $escape = ($data.cycle, 
        $data.index, $utils.$escape), $out = ($data.data, $data.i, "");
        return $out += '<style type="text/css"> #cycleTab input[type="radio"], #cycleTab label, .data-list>input[type="checkbox"] , .data-list>label{ cursor:pointer; } </style> <div class="scheduler-corn"> <div id="cycleTab" class="kui-tabs" border="true" style="height:320px;margin: 5px 10px 0;"> ', 
        $each(cycles, function(cycle, index) {
            $out += ' <div id="cycle', $out += $escape(cycle.val), $out += '" kui-options="title:\'', 
            $out += $escape(cycle.title), $out += '\'"> <input id="', $out += $escape(index + "_1"), 
            $out += '" type="radio" name="', $out += $escape(index), $out += '" value="1" checked="true"/><label for="', 
            $out += $escape(index + "_1"), $out += '">每', $out += $escape(cycle.title), $out += " (*)</label><br/> ", 
            (3 == index || 5 == index || 6 == index) && ($out += ' <input id="', $out += $escape(index + "_2"), 
            $out += '" type="radio" name="', $out += $escape(index), $out += '" value="2"/><label for="', 
            $out += $escape(index + "_2"), $out += '">不指定 ', 6 != index && ($out += " (?)"), 
            $out += "</label><br/> "), $out += ' <input id="', $out += $escape(index + "_3"), 
            $out += '" type="radio" name="', $out += $escape(index), $out += '" value="3"/><label for="', 
            $out += $escape(index + "_3"), $out += '">周期</label><br/> <span style="margin-left: 20px;">从</span> ', 
            5 == index && ($out += $escape(cycle.title)), $out += " ", 6 == index ? ($out += ' <input id="', 
            $out += $escape(index + "_3_1"), $out += '" class="kui-textinput form-input validatebox-text" kui-options="validType:\'int[4]\',invalidMessage:\'请输入正确的年份\'"/> 至 <input id="', 
            $out += $escape(index + "_3_2"), $out += '" class="kui-textinput form-input validatebox-text" kui-options="validType:\'int[4]\',invalidMessage:\'请输入正确的年份\'"/> ') : ($out += ' <input id="', 
            $out += $escape(index + "_3_1"), $out += '" class="kui-combobox" kui-options="autoFill:true,isClear:false,editable:false,valueField:\'dict_val\',textField:\'dict_des\'"/> 至 <input id="', 
            $out += $escape(index + "_3_2"), $out += '" class="kui-combobox" kui-options="autoFill:true,isClear:false,editable:false,valueField:\'dict_val\',textField:\'dict_des\'"/> '), 
            $out += " ", 5 != index && ($out += $escape(cycle.title)), $out += "<br/> ", 5 > index && ($out += ' <input id="', 
            $out += $escape(index + "_4"), $out += '" type="radio" name="', $out += $escape(index), 
            $out += '" value="4"/><label for="', $out += $escape(index + "_4"), $out += '">间隔</label><br/> <span style="margin-left: 20px;">从</span> <input id="', 
            $out += $escape(index + "_4_1"), $out += '" class="kui-combobox" kui-options="autoFill:true,isClear:false,editable:false,valueField:\'dict_val\',textField:\'dict_des\'"/> ', 
            $out += $escape(cycle.title), $out += '开始,每 <input id="', $out += $escape(index + "_4_2"), 
            $out += '" class="kui-combobox" kui-options="autoFill:true,isClear:false,editable:false,valueField:\'dict_val\',textField:\'dict_des\'"/> ', 
            $out += $escape(cycle.title), $out += "执行一次<br/> "), $out += " ", 3 == index && ($out += ' <input id="', 
            $out += $escape(index + "_5"), $out += '" type="radio" name="', $out += $escape(index), 
            $out += '" value="5"/><label for="', $out += $escape(index + "_5"), $out += '">本月最后一天</label><br/> '), 
            $out += " ", 5 == index && ($out += ' <input id="', $out += $escape(index + "_5"), 
            $out += '" type="radio" name="', $out += $escape(index), $out += '" value="5"/><label for="', 
            $out += $escape(index + "_5"), $out += '">本月最后一个周</label> <input id="', $out += $escape(index + "_5_1"), 
            $out += '" class="kui-combobox" kui-options="autoFill:true,isClear:false,editable:false,valueField:\'dict_val\',textField:\'dict_des\'"/><br/> '), 
            $out += " ", 6 != index && ($out += ' <input id="', $out += $escape(index + "_6"), 
            $out += '" type="radio" name="', $out += $escape(index), $out += '" value="6"/><label for="', 
            $out += $escape(index + "_6"), $out += '">指定</label><br/> <div class="data-list" style="margin-left: 20px;"> ', 
            $each(cycle.data, function(data, i) {
                $out += ' <input id="', $out += $escape(index + "_6_" + data.dict_val), $out += '" type="checkbox" name="', 
                $out += $escape(index), $out += '" value="', $out += $escape(data.dict_val), $out += '"><label for="', 
                $out += $escape(index + "_6_" + data.dict_val), $out += '">', $out += $escape(data.dict_des), 
                $out += "</label> ", 0 == (i + 1) % 10 && ($out += " <br/> "), $out += " ";
            }), $out += " </div> "), $out += " </div> ";
        }), $out += ' </div> <div class="form-box"> <div class="form-group-inline"> <span class="form-label">时间表达式：</span> <input name="CRON_EXPRESSION" class="kui-textinput form-input validatebox-text" disabled="disabled" style="width: 330px;"></input><a id="resetBtn" class="kui-linkbutton">重置</a> </div> <div class="form-group-inline"> <span class="form-label">汉化翻译：</span> <input name="CRON_EXPRESSION_TEXT" class="kui-textinput form-input validatebox-text" style="width: 400px;" disabled="disabled"></input> </div> </div> <span style="color:red;margin-left: 20px;">注：日和周必须有一个不指定</span> </div> ', 
        new String($out);
    });
});