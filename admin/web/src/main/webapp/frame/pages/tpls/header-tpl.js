define(function() {
    return template.runtime(function($data) {
        "use strict";
        var $utils = this, showAutoSearch = ($utils.$helpers, $data.showAutoSearch), $out = "";
        return $out += '<div class="container-wrap clearfix header-wrap header-wrap-1"> <div class="container header"> <div class="header-top"> <div class="logo logo-1 "></div> <div class="menu-tabs"></div> <div class="user-info-box pull-right"> <div class="pull-group"> ', 
        showAutoSearch && ($out += ' <div class="search-wrap menu-autosearch"></div> '), 
        $out += ' <div class="user-info pull-left clearfix"> <span class="user-name" id="USER_NAME"></span> <span class="user-code" id="USER_CODE"></span> <span class="post-name" id="POST_NAME"></span> </div> <div class="org-info pull-left clearfix"> <span class="post-name" id="ORG_NAME"></span> </div> </div> <div class="pull-group pull-group-back"> <div class="head-icon-wrap pull-right">  <a class="icon-common icon-logout" title="退出" id="logout"></a> </div> </div> </div> </div> </div> </div> ', 
        new String($out);
    });
});