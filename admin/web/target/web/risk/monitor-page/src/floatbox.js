/**
 * floatbox 组件
 * @author zhubc
 * 浮动的弹框 组件
 * web2风格
 */
define(function(require, exports, module) {

    $.fn.floatbox = function(options, param) {
        if (typeof options == "string") {
            var method = $.fn.floatbox.methods[options];
            if (method) {
                return method.apply(this, [this].concat(Array.prototype.slice.call(arguments, 1)));
            }else{
                alert("floatbox function["+method+"] is undefined!");
            }
        }
        return this.each(function() {
            options = options || {};
            var state = $.data(this, 'floatbox');
            if (state) {
                $.extend(state.options, options);
            } else {
                $.data(this, 'floatbox', {
                    options: $.extend({},$.fn.floatbox.defaults, $.fn.floatbox.parseOptions(this), options)
                });
                init(this);
            }
        });
    };

    $.fn.floatbox.methods = {
        options: function(jq, param) {
            return $.data(jq[0], 'floatbox').options;
        },
        enable: function(jq) {
            return jq.each(function() {
                setEnabled(this, false);
            });
        },
        disable: function(jq) {
            return jq.each(function() {
                setDisabled(this, true);
            });
        },
        show : function(jq){
            return jq.each(function() {
                showContainer(this);
            });
        },
        hide : function(jq){
            return jq.each(function() {
                hideContainer(this);
            });
        }
    };

    $.fn.floatbox.parseOptions = function(target) {
        var t = $(target);
        return $.extend({},$.parser.parseOptions(target));
    };

    $.fn.floatbox.defaults = {
        element : null,
        disable: false,     //触发元素可用性
        direction : "top",  //浮动框出现的方向 top，bottom，left，right
        isChangeDir : false,  //是否变更过浮动框出现的方向
        autoFit: false,    //是否每次显示的时候计算位置
        event: "hover",  //触发事件的方式  可选有hover click
        fadeInTime : 300,   //失去焦点后隔多少毫秒隐藏 click时无效
        onClick: $.noop()
    };
    function setDisabled(target) {
        var opts = $.data(target, 'floatbox').options;
        opts.disable = true;
        $(target).addClass("floatbox-disabled");
    }
    function setEnabled(target) {
        var opts = $.data(target, 'floatbox').options;
        opts.disable = false;
        $(target).removeClass("floatbox-disabled");
    }
    /**
     * 自动计算显示的位置
     * @param target
     */
    function fitCondition(target) {
        var opts = $.data(target, 'floatbox').options,
            $body = $("body"),
            bodyWidth = $body.width(),
            bodyHeight = $body.height(),
            winWidth = $(document).width(),
            winHeight = $(document).height(),
            pos = $(target).offset(),
            $arrow = opts.container.find(".floatbox-arrow"),
            arrowWidth = $arrow.outerWidth(),
            arrowHeight = $arrow.outerHeight(),
            width = $(target).outerWidth(),
            height = $(target).outerHeight(),
            conWidth = opts.container.outerWidth() - 2,
            conHeight = opts.container.outerHeight()-2,
            arrowLeft,
            arrowTop,
            left,
            top;
        switch (opts.direction){
            case "top":
                arrowLeft = conWidth/2 - arrowWidth/2;
                left = pos.left + width/2 - conWidth/2;
                top = pos.top - conHeight - arrowHeight;
                break;
            case "bottom":
                arrowLeft = conWidth/2 - arrowWidth/2;
                left = pos.left + width/2 - conWidth/2;
                top = pos.top + height + arrowHeight;
                break;
            case "left":
                arrowTop = conHeight/2 - arrowHeight/2;
                left = pos.left - conWidth - arrowWidth;
                top = pos.top + height/2 - conHeight/2;
                break;
            case "right":
                arrowTop = conHeight/2 - arrowHeight/2;
                left = pos.left + width + arrowWidth;
                top = pos.top + height/2 - conHeight/2;
                break;
        }
        //出现在屏幕外，重新定位方向
        var changFlag = false ;
        switch (opts.direction){
            case "top":
                //显示在顶部，出现在屏幕外了
                if(!opts.isChangeDir && top < 0){
                    changeDir(target,"bottom");
                    changFlag = true;
                }
                break;
            case "bottom":
                //显示在底部，出现在屏幕外了
                if(!opts.isChangeDir && (top + conHeight) > bodyHeight){
                    changeDir(target,"top");
                    changFlag = true;
                }
                break;
            case "left":
                //显示在左边，出现在屏幕外了
                if(!opts.isChangeDir && left < 0){
                    changeDir(target,"right");
                    changFlag = true;
                }
                break;
            case "right":
                //显示在右边，出现在屏幕外了
                if(!opts.isChangeDir && (left + conWidth) > bodyWidth){
                    changeDir(target,"left");
                    changFlag = true;
                }
                break;
        }
        if(changFlag){
            fitCondition(target);
            return;
        }
        switch (opts.direction){
            case "top":
            case "bottom":
                //上下浮动框对左边距进行修正
                if(left < 0){
                    arrowLeft += left - 5;
                    left = 5;
                }
                if((left + conWidth) > bodyWidth){
                    arrowLeft += left + conWidth - bodyWidth + 5;
                    left = bodyWidth - conWidth - 5;
                }
                break;
            case "left":
            case "right":
                //左右浮动框对上边距进行修正
                if(top < 0){
                    arrowTop += top - 5;
                    top = 5;
                }
                if((top + conHeight) > bodyHeight){
                    arrowTop += top + conHeight - bodyHeight + 5;
                    top = bodyHeight - conHeight - 5;
                }
                break;
        }
        if(arrowLeft){
            $arrow.css({
                left : arrowLeft + "px"
            });
        }
        if(arrowTop){
            $arrow.css({
                top : arrowTop + "px"
            });
        }
        if(left && top){
            opts.container.offset({
                left : left,
                top : top
            })
        }
    }

    function changeDir(target, direction){
        var opts = $.data(target, 'floatbox').options,
            oldDir = opts.direction;
        if(oldDir == direction){
            return;
        }
        opts.container.find(".floatbox-arrow").removeClass(oldDir).addClass(direction);
        opts.direction = direction;
        opts.isChangeDir = true;
    }

    /**
     * 初始化
     * @param target
     */
    function init(target) {
        var opts = $.data(target, 'floatbox').options;
        $(target).addClass("floatTarget");
        opts.container = $("<div class='floatbox'><div class='floatbox-arrow "+opts.direction+"'></div></div>").appendTo($("body"));
        if(opts.element){
            $(opts.element).appendTo(opts.container);
        }
        !opts.autoFit && fitCondition(target);
        opts.container.hide();
        opts.disable && setDisabled(target);
        bindEvent(target);
    }

    /**
     * 显示浮动框
     * @param target
     */
    function showContainer(target){
        var opts = $.data(target, 'floatbox').options;
        opts.conTimeout && clearTimeout(opts.conTimeout);
        opts.container.fadeIn(opts.fadeInTime);
        if(opts.autoFit){
            //每次重新计算之前，都将isChangeDir重置为false。保证每次都能重新计算。
            opts.isChangeDir = false;
            fitCondition(target);
        }
        $.isFunction(opts.onClick) && opts.onClick.call();
    }

    /**
     * 隐藏浮动框
     * @param target
     * @param noTimeOut 隐藏是否立即隐藏
     */
    function hideContainer(target, noTimeOut){
        var opts = $.data(target, 'floatbox').options;
        !noTimeOut ? (opts.conTimeout = setTimeout(function () {
            opts.container.hide();
        }, opts.event == "click" ? 0 : opts.fadeInTime)) : opts.container.hide();

    }

    /**
     * 绑定事件
     * @param target
     */
    function bindEvent(target){
        var opts = $.data(target, 'floatbox').options;
        if(opts.event == "click") {
            $(target).unbind('.floatbox').bind("click.floatbox",function(e){
                var showFlag = opts.container.is(":visible");
                if(opts.disable) {
                    return;
                }
                $(".floatTarget").each(function() {
                    hideContainer(this, true);
                });
                !showFlag && showContainer(target);
            });
            opts.container.unbind('.floatbox').bind("click.floatbox",function(e){
                var showFlag = opts.container.is(":visible");
                !showFlag && showContainer(target);
            });
            $(document).unbind('.floatbox').on("click.floatbox",function(e) {
                !$(e.target).hasClass("floatTarget") && $(e.target).closest(".floatbox").length < 1 && $(".floatTarget").each(function() {
                    hideContainer(this, true);
                });
            });
        } else {
            $(target).unbind('.floatbox').bind("mouseenter.floatbox",function() {
                if(opts.disable) {
                    return;
                }
                $(".floatTarget").each(function() {
                    !$(target).is($(this)) && hideContainer(this, true);
                });
            }).bind("mouseover.floatbox",function(){
                if(opts.disable) {
                    return;
                }
                showContainer(target);
            }).bind("mouseout.floatbox",function(){
                hideContainer(target)
            });
            opts.container.unbind('.floatbox').bind("mouseover.floatbox",function(){
                showContainer(target);
            }).bind("mouseout.floatbox",function(){
                hideContainer(target)
            });
        }

    }

});