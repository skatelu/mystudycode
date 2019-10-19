$(function() {
    var tempalte = artTemplate("serviceTemplate");
    expendAllNode();
    setSize();
    bindEvent();

    function setSize() {
        //左侧导航条高度
        $(".service-nav").height($(window).height() - 80 - 70);
        //右侧最小高度
        $(".service-info").css("min-height", $(window).height() - 80 - 70);
    }

    function bindEvent() {
        $(document).scroll(function() {
            if($(".service-content-select").length) {
                $(document).scrollTop() - $(".service-content-select").offset().top > 0 ? $(".service-nav").addClass("fix-service-nav") : $(".service-nav").removeClass("fix-service-nav");
            }
        })
        $(document).on("click",".service-nav-title", function(e) {
            var $target = $(e.currentTarget),
                $node = $target.parent("[node-id]");
            var nodeId = $node.attr("node-id");
            selectNode(nodeId);
        });
    }

    /************ node ************/
    function getNode(id) {
        return $("li[node-id=" + id +"]");
    }

    function expendAllNode() {
        $("li[node-id]").each(function(k) {
            var nodeId = $(this).attr("node-id");
            expendNode(nodeId);
        })
    }

    function collapseAllNode() {
        $("li[node-id]").each(function(k) {
            var nodeId = $(this).attr("node-id");
            collapseNode(nodeId);
        })
    }

    function expendNode(id) {
        var $node = getNode(id);
        if($node.length && isNodeCanToggle($node) && !isNodeExpend($node)) {
            $node.children(".service-nav-title").children(".service-nav-title-toggle").removeClass("nav-arrow-icon-2").addClass("nav-arrow-icon-1");
            $node.children(".service-nav-body").show().slideUp(100);
        }
    }

    function collapseNode(id) {
        var $node = getNode(id);
        if($node.length && isNodeCanToggle($node) && isNodeExpend($node)) {
            $node.children(".service-nav-title").children(".service-nav-title-toggle").removeClass("nav-arrow-icon-1").addClass("nav-arrow-icon-2");
            $node.children(".service-nav-body").slideDown(100);
        }
    }

    function isNodeExpend(id) {
        var $node = id instanceof jQuery ? id : getNode(id);
        if($node.length) {
            return !$node.children(".service-nav-body").is(":visible");
        }
    }

    function isNodeCanToggle(id) {
        var $node = id instanceof jQuery ? id : getNode(id);
        if($node.length) {
            return $node.children(".service-nav-body").length;
        }
    }

    function selectNode(id) {
        var $node = getNode(id);
        if($node.length) {
            if(isNodeCanToggle($node)) {
                if(isNodeExpend($node)) {
                    collapseNode(id);
                } else {
                    expendNode(id);
                }
            } else {
                var serviceCode = $node.attr("service-code");
                if(serviceCode && serviceCode != $(".service-content-select").attr("service-code")) {
                    $(".service-nav").removeClass("fix-service-nav");
                    selectService(serviceCode);
                    $(".service-content.welcome").hide();
                    $node.addClass("service-nav-select");
                }
            }
        }
    }

    /************ service ************/
    function selectService(serviceCode) {
        unSelectService();
        addScript(serviceCode, "./services/" + serviceCode + ".js");
        //$(".service-content[service-code=" + serviceCode + "]").addClass("service-content-select").show();
    }

    function unSelectService() {
        $(".service-nav").removeClass("fix-service-nav");

        $(".service-nav-select").each(function() {
            if($(this).attr("service-code")) {
                $("script#" + $(this).attr("service-code")).remove();
            }
            $(this).removeClass("service-nav-select");
        })
        $(".service-content-select").remove();
    }

    /************ template ************/
    window.render = render;
    function render(data) {
        $(".service-info").append($(tempalte(data)).addClass("service-content-select"));
    }
    function addScript(id, scriptPath) {
        var script = document.createElement("script");
        script.type = "text/javascript";
        script.src = scriptPath;
        script.id = id;
        document.getElementsByTagName("head")[0].appendChild(script);
    }

})
