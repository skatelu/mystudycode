define(function(require, exports, module) {

  
  /**
   * jQuery本身的resize方法只是对window.onresize方法的简单封装，只提供对window的
   * resize事件的监听，此方法拓展到所有元素，使用requestAnimationFrame、setTimeout、
   * setInterval，每隔一段时间查询其width和height，如果和记录的width和height不一样，
   * 运行回调函数并更新记录中的width为height
   * 
   * jQuery resize event - v1.1 - 3/14/2010
   * http://benalman.com/projects/jquery-resize-plugin/
   * Copyright (c) 2010 "Cowboy" Ben Alman
   * Dual licensed under the MIT and GPL licenses.
   * http://benalman.com/about/license/
   */
  (function($, window, undefined) {
    var elems = $([]),
     jq_resize = $.resize = $.extend($.resize, {}),
     timeout_id,
     str_setTimeout = 'setTimeout',
     str_resize = 'resize',
     str_data = str_resize + '-special-event',
     str_delay = 'delay',
     str_throttle = 'throttleWindow';
    jq_resize[str_delay] = 250;
    jq_resize[str_throttle] = true;
    $.event.special[str_resize] = {
     setup: function() {
      if (!jq_resize[str_throttle] && this[str_setTimeout]) {
       return false;
      }
      var elem = $(this);
      elems = elems.add(elem);
      $.data(this, str_data, {
       w: elem.width(),
       h: elem.height()
      });
      if (elems.length === 1) {
       loopy();
      }
     },
     teardown: function() {
      if (!jq_resize[str_throttle] && this[str_setTimeout]) {
       return false;
      }
      var elem = $(this);
      elems = elems.not(elem);
      elem.removeData(str_data);
      if (!elems.length) {
       clearTimeout(timeout_id);
      }
     },
     add: function(handleObj) {
      if (!jq_resize[str_throttle] && this[str_setTimeout]) {
       return false;
      }
      var old_handler;
      function new_handler(e, w, h) {
       var elem = $(this),
        data = $.data(this, str_data);
      // 项目其他地方存在大量$(window).resize方法，这里判断一下
      // lzp 2018/6/4
      if (data === undefined && this === window) {
        return
      }
       data.w = w !== undefined ? w : elem.width();
       data.h = h !== undefined ? h : elem.height();
       old_handler.apply(this, arguments);
      }
      if ($.isFunction(handleObj)) {
       old_handler = handleObj;
       return new_handler;
      } else {
       old_handler = handleObj.handler;
       handleObj.handler = new_handler;
      }
     }
    };
    
    function loopy() {
     timeout_id = window[str_setTimeout](function() {
      elems.each(function() {
       var elem = $(this),
        width = elem.width(),
        height = elem.height(),
        data = $.data(this, str_data);
       if (width !== data.w || height !== data.h) {
        elem.trigger(str_resize, [data.w = width, data.h = height]);
       }
      });
      loopy();
     }, jq_resize[str_delay]);
    }
   })(jQuery, this);
})