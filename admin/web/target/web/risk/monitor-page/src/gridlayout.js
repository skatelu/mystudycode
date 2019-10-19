/**
 * gridlayout组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define(function(require, exports, module) {

  $.fn.gridlayout = function(options, param) {
    if (typeof options == "string") {
      var method = $.fn.gridlayout.methods[options];
      if (method) {
        return method(this, param);
      }
    }
    options = options || {};
    return this.each(function() {
      var state = $.data(this, 'gridlayout');
      if (state) {
        $.extend(state.options, options);
        // reload(this)
      } else {
        state = $.data(this, 'gridlayout', {
          options: $.extend({},
            $.fn.gridlayout.defaults,
            $.fn.gridlayout.parseOptions(this),
            options),
        });
        init(this);
      }
    });
  };
  $.fn.gridlayout.methods = {
    options: function(jq) {
      return $.data(jq[0], "gridlayout").options;
    },
  };
  $.fn.gridlayout.parseOptions = function(target) {
    return $.extend({},
      $.parser.parseOptions(target, [

      ]));
  };

  $.fn.gridlayout.defaults = $.extend({}, {
    colGap: 0, // 列间距
    rowGap: 0, // 行间距
  });

  function init(target) {
    bindEvents(target)
    var opts = $.data(target, 'gridlayout').options
    var isValid = checkValid(opts)
    if (!isValid) {
      console.log("gridlayout初始化失败")
      return
    }

    /**
     * gridlayout为了自适应，本身高度会设为100%；此时若其parent为body，而body和html标签都
     * 不设高度的话它们的高度就会都为0；layout组件中有把body和html标签高度设为100%的操作，
     * 既然gridlayout是平级的，也把这个逻辑抄过来。
     */
    if ($(target).parent()[0].tagName == "BODY") {
      $(target).parent().css("height", "100%");
      $("html").css("height", "100%");
    }

    buildDom(target, opts)
  }

  function bindEvents(target) {
    // dispatch kui框架内的"_resize"方法
    $(target).bind("_resize", function() {
      $.each($(target).children(), function(i, child) {
        $(child)
        .find("div.panel,div.accordion,div.tabs-container,div.layout")
        .triggerHandler("_resize")
      })
    })
  }

  function checkValid(opts) {
    //check和format opts
    var widthValid = checkWidth(opts)
    if (!widthValid) {
      return false
    }
    var heightValid = checkHeight(opts)
    if (!heightValid) {
      return false
    }
    var gridPosValid = checkGridPos(opts)
    if (!gridPosValid) {
      return false
    }
    return true

    function checkWidth(opts) {
      //检测有没有宽度属性
      if (!_.isArray(opts.colWidth) || !opts.colWidth.length) {
        // 没有设置opts.colWidth属性时，再考虑opts.colNum属性
        if (!_.isNumber(opts.colNum)) {
          // 这两个属性都没有时抛出错误
          console.log("gridlayout组件没有定义列宽度")
          return false
        } else {
          opts.colWidth = []
          for (var i = 0; i < opts.colNum; i++) {
            opts.colWidth.push(1 / opts.colNum)
          }
        }
      } else {
        //设置opts.colWidth属性时，以opts.colWidth为准
      }
      // opts.colWidth里是否全部是数字（某些类型的可以转为数字比如带px的字符串，这个后续再开发）
      var numberValid = true
      opts.colWidth.forEach(function(widthValue) {
        if (!_.isNumber(widthValue)) {
          numberValid = false
        }
      });
      if (!numberValid) {
        console.log("gridlayout组件列宽设置需为数字")
        return false
      }
      //宽度属性的值是否能转化成可计算宽度的数字比例
      var allUnderOne = true //若值全部小于1则为宽度百分比
      var allAboveOne = true //若值全部大于1则为宽度值

      opts.colWidth.forEach(function(widthValue) {
        if (widthValue > 1) {
          allUnderOne = false
        } else {
          allAboveOne = false
        }
      });
      if (!allAboveOne && !allUnderOne) {
        console.log("gridlayout组件列宽设置需为百分比或像素值")
        return false
      }
      //归一化设置为百分比宽度
      var totalWidth = 0
      opts.colWidth.forEach(function(widthValue) {
        totalWidth += widthValue
      })
      opts.colWidth = _.map(opts.colWidth, function(widthValue) {
        return widthValue / totalWidth
      })
      opts.colNum = opts.colWidth.length
      return true
    }

    function checkHeight(opts) {
      //检测有没有高度属性
      if (!_.isArray(opts.rowHeight) || !opts.rowHeight.length) {
        // 没有设置opts.rowHeight属性时，再考虑opts.rowNum属性
        if (!_.isNumber(opts.rowNum)) {
          // 这两个属性都没有时抛出错误
          console.log("gridlayout组件没有定义列宽度")
          return false
        } else {
          opts.rowHeight = []
          for (var i = 0; i < opts.rowNum; i++) {
            opts.rowHeight.push(1 / opts.rowNum)
          }
        }
      } else {
        //设置opts.rowHeight属性时，以opts.rowHeight为准
      }
      // opts.rowHeight里是否全部是数字（某些类型的可以转为数字比如带px的字符串，这个后续再开发）
      var numberValid = true
      opts.rowHeight.forEach(function(widthValue) {
        if (!_.isNumber(widthValue)) {
          numberValid = false
        }
      });
      if (!numberValid) {
        console.log("gridlayout组件行高设置需为数字")
        return false
      }
      //高度属性的值是否能转化成可计算高度的数字比例
      var allUnderOne = true //若值全部小于1则为高度百分比
      var allAboveOne = true //若值全部大于1则为高度值

      opts.rowHeight.forEach(function(widthValue) {
        if (widthValue > 1) {
          allUnderOne = false
        } else {
          allAboveOne = false
        }
      });
      if (!allAboveOne && !allUnderOne) {
        console.log("gridlayout组件行高设置需为百分比或像素值")
        return false
      }
      //归一化设置为百分比高度
      var totalWidth = 0
      opts.rowHeight.forEach(function(widthValue) {
        totalWidth += widthValue
      })
      opts.rowHeight = _.map(opts.rowHeight, function(widthValue) {
        return widthValue / totalWidth
      })
      opts.rowNum = opts.rowHeight.length
      return true
    }

    function checkGridPos(opts) {
      // 检测网格位置写法是否合法、网格是否重叠
      var gridPosArr = opts.gridPos
      if (!_.isArray(gridPosArr) || gridPosArr.length === 0) {
        return false
      }
      var colNum = opts.colNum
      var rowNum = opts.rowNum

      // 生成一个colNum*rowNum的二维数组
      var nullArrArr = _.map(Array(rowNum), function () {
        return Array(colNum).fill(null)
      })
      for (var i = 0; i < gridPosArr.length; i++) {
        if (gridPosArr[i].w === undefined || gridPosArr[i].w === null ||
          gridPosArr[i].h === undefined || gridPosArr[i].h === null ||
          gridPosArr[i].x === undefined || gridPosArr[i].x === null ||
          gridPosArr[i].y === undefined || gridPosArr[i].y === null) {
          console.log("gridPos中w/h/x/y属性不能为空")
          return false
        }
        for (var h = 0; h < gridPosArr[i].h; h++) {
          var theRow = nullArrArr[gridPosArr[i].y + h]
          if (theRow === undefined) {
            console.log("gridPos中第"+i+"个元素行设置超过行最大值")
            return false
          }
          for (var w = 0; w < gridPosArr[i].w; w++) {
            var theColOfRow = theRow[gridPosArr[i].x + w]
            if (theColOfRow === undefined) {
              console.log("gridPos中第"+i+"个元素列设置超过列最大值")
              return false
            }
            if (theColOfRow !== null) {
              console.log("gridPos中出现位置重叠: 第"+theColOfRow+"个元素和第"+i+"个元素占据同一个网格")
              return false
            } else {
              // 标识为一个非null的值代表其已有元素占据
              theRow[gridPosArr[i].x + w] = i
            }
          }
        }
      }
      return true
    }
  }

  function buildDom(target, opts) {
    $(target).addClass("gridlayout-wrapper")
    var children = $(target).children().remove()
    children = _.map(children, function (child) {
      var retHtml
      if (opts.hasShadow === true) {
        retHtml = `
        <div class="gridlayout-child gridchild-boxshadow" style="width:0;height:0;left:0;top:0;">
        </div>
        `
      } else {
        retHtml = `
        <div class="gridlayout-child" style="width:0;height:0;left:0;top:0;">
        </div>
        `
      }
      return $(retHtml).append(child)
    })
    setPercentWidthHeight(children, opts)
    $(target).append(children)

    function setPercentWidthHeight(children, opts) {
      var colGap = opts.colGap
      var rowGap = opts.rowGap
      var colNum = opts.colNum
      var rowNum = opts.rowNum
      var colWidth = opts.colWidth
      var rowHeight = opts.rowHeight
      var gridPosArr = opts.gridPos
      gridPosArr.forEach(function (gridPos, i) {
        var widthPercent = getWHPercent(gridPos.x, gridPos.w, colWidth)
        var heightPercent = getWHPercent(gridPos.y, gridPos.h, rowHeight)
        var leftPercent = getWHPercent(0, gridPos.x, colWidth)
        var topPercent = getWHPercent(0, gridPos.y, rowHeight)
        var totalWidth = "calc(100% - " + (colNum - 1) * colGap + "px)"
        var totalHeight = "calc(100% - " + (rowNum - 1) * rowGap + "px)"
        if (!children[i]) {
          return
        } else {
          children[i].css("width", "calc(" + totalWidth + "*" + widthPercent + " + " + (gridPos.w-1) * colGap + "px)")
          children[i].css("height", "calc(" + totalHeight + "*" + heightPercent + " + " + (gridPos.h-1) * rowGap + "px)")
          children[i].css("left", "calc(" + totalWidth + "*" + leftPercent + " + " + gridPos.x * colGap + "px)")
          children[i].css("top", "calc(" + totalHeight + "*" + topPercent + " + " + gridPos.y * rowGap + "px)")
        }
      })
    }

    function getWHPercent(i, count, whArr) {
      if (count === 0) {
        return 0
      } else {
        return _.reduce(whArr.slice(i, i + count), function(sum, i) {
          return sum + i
        })
      }
    }
  }

});