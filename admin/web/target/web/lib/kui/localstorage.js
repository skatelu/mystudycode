define("lib/kui/localstorage", [], function(require, exports, module) {
    var ls, supported = true, prefix = "kjdp_";
    if (typeof localStorage == "undefined" || typeof localStorage == "unknown") {
        supported = false;
    } else {
        ls = localStorage;
    }
    $.ls = $.fn.localstorage = function(tempKey, value) {
        if (!supported) {
            return false;
        }
        var href = /\/([^\/]*)$/.exec(location.href.replace(/&_=[\.0-9]*$/, ""))[1];
        var key = prefix + utils.md5(href + "_" + tempKey);
        if (typeof value == "undefined") {
            return JSON.parse(ls.getItem(key));
        } else if (value == null) {
            ls.removeItem(key);
        } else {
            ls.setItem(key, JSON.stringify(value));
        }
    };
    $.ls.clear = function() {
        if (!supported) {
            return false;
        }
        for (var key in localStorage) {
            if (key.indexOf(prefix) == 0) {
                ls.removeItem(key);
            }
        }
        return true;
    };
});
