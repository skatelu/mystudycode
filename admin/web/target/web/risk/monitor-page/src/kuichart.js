/**
 * kuichart组件
 * Copyright (c) 2009-2016 www.szkingdom.com. All rights reserved.
 */
define(function(require, exports, module) {

  require("./echarts")
  require("./jqueryResize")
  $.fn.kuichart = function(options, param) {
    if (typeof options == "string") {
      var method = $.fn.kuichart.methods[options];
      if (method) {
        return method(this, param);
      }
    }
    options = options || {};
    return this.each(function() {
      var state = $.data(this, 'kuichart');
      if (state) {
        $.extend(state.options, options);
        // reload(this)
      } else {
        state = $.data(this, 'kuichart', {
          options: $.extend({},
            $.fn.kuichart.defaults,
            $.fn.kuichart.parseOptions(this),
            options),
        });
        init(this);
      }
    });
  };
  $.fn.kuichart.methods = {
    options: function(jq) {
      return $.data(jq[0], "kuichart").options;
    },
    loadData: function(jq, data) {
      return updateChart(jq[0], data);
    },
    getInstance: function(jq) {
      return getInstance(jq[0]);
    },
    updateConfig: function(jq, config) {
      return updateConfig(jq[0], config);
    },
    setTitle: function(jq, titleText) {
      return setTitle(jq[0], titleText);
    },
    getFloatLayer: function(jq) {
      return getFloatLayer(jq[0]);
    },
  };
  $.fn.kuichart.parseOptions = function(target) {
    return $.extend({},
      $.parser.parseOptions(target, [

      ]));
  };

  $.fn.kuichart.defaults = $.extend({}, {

  });

  var allRenderFunc = {
    gaugeChart: {
      init: function(jQueryDom, renderData, config) {
        var myChart = echarts.init(jQueryDom.get(0))
        if (!config) {
          config = {}
        }

        var noData = false
        if (!renderData || _.isEmpty(renderData)) {
          noData = true
        } else if (renderData.value === null || renderData.value === undefined) {
          noData = true
        }
        if (noData) {
          renderData = _.extend({}, renderData, { value: 0 })
          myChart.showLoading()
        }

        var option = {
          animation: config.animation === false ? false : true,
          title: {
            left: config.titlePos, //gaugeChart的标题位置，可设为'left'、'center'、'right'，或者20这样的数字、20%这样的百分比数字，这时代表离容器左侧的距离，若不设置config.titleName则无效
            text: config.titleName, //gaugeChart的标题名字
            subtext: config.titleSubtext, //副标题
          },
          backgroundColor: config.bgColor, //背景颜色
          color: ["#37A2DA", "#32C5E9", "#67E0E3"],
          tooltip: {
            show: config.tooltipShow || true, //显示提示框
            formatter: config.tooltipFormatter || "", //提示框显示格式
          },
          series: [{
            min: config.min || 0, //仪表盘最小刻度
            max: config.max || 100, //仪表盘最大刻度
            splitNumber: config.splitNum || 10, //仪表盘刻度分隔
            name: config.axisName, //仪表盘指针名，需配合tooltip及tooltip.formatter使用
            type: 'gauge',
            detail: {
              //数字显示格式,若直接定义formatter则用formatter，若没有定义则看config有没有percent字段，若为true则加上百分比符号
              formatter: config.detailFormatter || (config.percent === true ? '{value}%' : '{value}'),
              // rich: {
              //   width: config.detailWidth,
              // },
              fontSize: config.fontSize || 20,
              color: config.fontColor || "#000",
              offsetCenter: config.detailPos || [0, '40%'], //示数相对于仪表盘中心的偏移位置，default: [0, '40%']
              fontWeight: config.fontWeight || 'normal', //文字粗细
              rich: config.detailRich,
            },
            axisLine: {
              lineStyle: {
                width: config.axisWidth || 30, //仪表盘宽度
                shadowBlur: config.axisBlur || 0, //仪表盘阴影
                color: config.color || [ //仪表盘颜色及划分
                  [0.3, '#67e0e3'],
                  [0.7, '#37a2da'],
                  [1, '#fd666d']
                ]
              }
            },
            axisLabel: {
              fontSize: config.axisLabelFontSize || 12,
              fontWeight: config.axisLabelFontWeight || "normal",
              distance: config.axisLabelDst === undefined ? 5 : config.axisLabelDst, //默认仪表盘数字在内部显示，调整此为负值可让仪表盘数字在外部显示
              color: config.axisLabelColor || "#000", //刻度颜色
              formatter: config.axisLabelFormatter || (config.percent === true ? '{value}%' : '{value}'), //刻度格式
            },
            pointer: {
              width: config.pointerWidth || 8, //指针宽度
            },
            axisTick: {            // 坐标轴小标记
                length: config.axisTick || 8,        // 属性length控制线长
                lineStyle: {       // 属性lineStyle控制线条样式
                    color: config.axisTickColor || '#eee',
                }
            },
            splitLine: {           // 分隔线
                length: config.splitLine || 30,         // 属性length控制线长
                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                    color: config.splitLineColor || '#eee',
                }
            },
            title: {
              offsetCenter: config.titlePos || [0, '-40%'], //标题相对于仪表盘中心的偏移位置，default: [0, '-40%']
              fontWeight: config.titleWeight || 'normal', //文字粗细
            },
            data: [{
              value: renderData.value,
              name: renderData.name,
            }]

          }],
        };
        myChart.setOption(option)
        return myChart
      },
      update: function(echartsInstance, jQueryDom, updateData, config) {
        echartsInstance.setOption({
          series: [{
            data: [{
              value: updateData.value,
              name: updateData.name,
            }],
          }],
        })
        echartsInstance.hideLoading()
        return echartsInstance
      },
    },
    barChart: {
      init: function(jQueryDom, renderData, config) {
        var myChart = echarts.init(jQueryDom.get(0))
        if (!config) {
          config = {}
        }

        var noData = false
        if (!renderData || _.isEmpty(renderData)) {
          noData = true
        } else if (!_.isArray(renderData.values) || !renderData.values.length) {
          noData = true
        }
        if (noData) {
          renderData = _.extend({}, renderData, { values: [] })
          myChart.showLoading()
        }

        var option = {
          animation: config.animation === false ? false : true,
          color: config.color ? config.color : echartDefaultColors, //barChart颜色
          tooltip: {
            show: config.showTooltip === false ? false : true, //Echarts默认为true（只要有tooltip这个配置项），此处默认为false
            trigger: config.trigger || 'axis', //提示框触发方式，分'item'、'axis'、'none'三种
            axisPointer: { // 坐标轴指示器，当上面的tooltip.trigger为'axis'时有效
              type: config.axisTriggerTyp || 'shadow', // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: config.boundaryGap === false ? false : true, //坐标轴两边留白策略，默认为true，更深层的留白策略Echarts支持，暂不引入
            data: renderData.category, //传入数据中的目录项，与值项一一对应
            axisTick: {
              alignWithLabel: config.alignWithLabel //当boundaryGap为true时，刻度线和标签对齐
            },
          },
          grid: {
            left: config.gridLeft || '10%',
            bottom: config.gridBottom || 60,
            right: config.gridRight || '10%',
            top: config.gridTop || 60,
          },
          yAxis: {
            type: 'value',
            name: config.nameY,
          },
          title: {
            left: config.titlePos, //barChart的标题位置，可设为'left'、'center'、'right'，或者20这样的数字、20%这样的百分比数字，这时代表离容器左侧的距离，若不设置config.titleName则无效
            text: config.titleName, //barChart的标题名字
          },
          series: [{
            name: config.name, //鼠标移上去的文字说明
            data: renderData.values, //传入数据中的值项，与目录项一一对应
            type: 'bar',
            barWidth: config.barWidth, //bar占一栏的宽度或类目宽度的百分比
            barMaxWidth: config.barMaxWidth, //bar占一栏的最大宽度或类目宽度的百分比
            label: config.seriesLabel, //一个object，定义series的label
          }]
        }
        if (config.horizontal) {
          var values = option.yAxis
          var category = option.xAxis
          option.yAxis = category
          option.xAxis = values
        }
        myChart.setOption(option)
        return myChart
      },
      update: function(echartsInstance, jQueryDom, updateData, config) {
        if (config.horizontal) {
          echartsInstance.setOption({
            series: [{
              data: updateData.values,
            }],
            yAxis: {
              data: updateData.category,
            },
          })
        } else {
          echartsInstance.setOption({
            series: [{
              data: updateData.values,
            }],
            xAxis: {
              data: updateData.category,
            },
          })
        }
        echartsInstance.hideLoading()
        return echartsInstance
      },
    },
    lineChart: {
      init: function(jQueryDom, renderData, config) {
        var myChart = echarts.init(jQueryDom.get(0))
        if (!config) {
          config = {}
        }

        var noData = false
        if (!renderData || _.isEmpty(renderData)) {
          noData = true
        } else if (!_.isArray(renderData.values) || !renderData.values.length) {
          noData = true
        }
        if (noData) {
          renderData = _.extend({}, renderData, { values: [] })
          myChart.showLoading()
        }

        // 支持堆叠图
        var series
        if (!_.isArray(renderData.values[0])) {
          // 不是二维数组，即只有一条线
          series = [{
            data: renderData.values, //传入数据中的值项，与目录项一一对应
            type: 'line',
            smooth: config.smooth, //如果是 boolean 类型，则表示是否开启平滑处理。如果是 number 类型（取值范围 0 到 1），表示平滑程度，越小表示越接近折线段，反之则反。设为 true 时相当于设为 0.5
          }]
        } else {
          // 是二维数组
          var twoDimenSeries = []
          renderData.values.forEach(function(vArr, i) {
            var seriesObj = {
              data: vArr,
              type: 'line',
              smooth: config.smooth,
            }
            if (config.dataStack === true) {
              seriesObj.stack = 'lineChartIsStack'
            }
            if (_.isArray(config.lineNames)) {
              seriesObj.name = config.lineNames[i]
            }
            twoDimenSeries.push(seriesObj)
          })
          series = twoDimenSeries
        }

        var option = {
          animation: config.animation === false ? false : true,
          color: config.color ? config.color : echartDefaultColors, //lineChart颜色
          tooltip: {
            trigger: config.trigger === false ? 'none' : 'axis', //提示框触发方式，分'axis'、'none'两种
            formatter: config.formatter //当上面的tooltip.trigger为'axis'时有效，触发时显示文字的格式，具体参考Echarts文档，例如"{a} <br/>{b} : {c} ({d}%)"
          },
          legend: {
            show: config.legendShow === false ? false : true, //图例是否显示
            orient: config.legendOrient || 'horizontal', //图例列表的布局朝向，默认'horizontal'，可选：'vertical'、'horizontal'
            left: config.legendLeft || 'center', //可设为'left'、'center'、'right'，或者20这样的数字、20%这样的百分比数字
            top: config.legendTop || 'top', //可设为'top'、'center'、'bottom'，或者20这样的数字、20%这样的百分比数字
          },
          grid: {
              left: config.gridLeft || '10%',
              bottom: config.gridBottom || 60,
              right: config.gridRight || '10%',
              top: config.gridTop || 60,
            },
          xAxis: {
            type: 'category',
            boundaryGap: config.boundaryGap === false ? false : true, //坐标轴两边留白策略，默认为true，更深层的留白策略Echarts支持，暂不引入
            data: renderData.category, //传入数据中的目录项，与值项一一对应
            axisTick: {
              alignWithLabel: config.alignWithLabel //当boundaryGap为true时，刻度线和标签对齐
            },
          },
          yAxis: {
            type: 'value',
          },
          title: {
            left: config.titleX, //lineChart的标题X轴位置，可设为'left'、'center'、'right'，或者20这样的数字、20%这样的百分比字符串，这时代表离容器左侧的距离，若不设置config.titleName则无效
            top: config.titleY, //Y轴位置，可设为'top'、'middle'、'bottom'，或者数字、百分比字符串
            text: config.titleName, //lineChart的标题名字
            subtext: config.titleSubtext, //副标题
          },
          series: series,
        }

        myChart.setOption(option)
        return myChart
      },
      update: function(echartsInstance, jQueryDom, updateData, config) {
        if (!_.isArray(updateData.values)) {
          return
        }
        // 支持堆叠图
        var series
        if (!_.isArray(updateData.values[0])) {
          series = [{
            data: updateData.values,
          }]
        } else {
          series = _.map(updateData.values, function(vArr) {
            return {
              type: 'line',
              data: vArr,
            }
          })
        }
        echartsInstance.setOption({
          series: series,
          xAxis: {
            data: updateData.category,
          },
        })
        echartsInstance.hideLoading()
        return echartsInstance
      },
    },
    pieChart: {
      init: function(jQueryDom, renderData, config) {
        var myChart = echarts.init(jQueryDom.get(0))
        if (!config) {
          config = {}
        }

        var noData = false
        if (!renderData || _.isEmpty(renderData)) {
          noData = true
        } else if (!_.isArray(renderData.values) || !renderData.values.length) {
          noData = true
        }
        if (noData) {
          renderData = _.extend({}, renderData, { values: [{ value: 100 }] })
          myChart.showLoading()
        }

        // 有新需求：饼图数据从大到小排序
        if (config.dataSort === true) {
          var defualtSortFunc = function(v) {
            // 默认从大到小
            return -v.value
          }
          if (config.sortFunc) {
            defualtSortFunc = config.sortFunc
          }
          renderData.values = _.sortBy(renderData.values, defualtSortFunc)
        }

        var option = {
          animation: config.animation === false ? false : true,
          title: {
            text: config.titleText, //pieChart的标题
            subtext: config.titleSubtext, //pieChart的副标题
            x: config.titleX || 'center', //pieChart的标题位置
            top: config.titleY, //Y轴位置，可设为'top'、'middle'、'bottom'，或者数字、百分比字符串
          },
          tooltip: {
            trigger: config.trigger === false ? 'none' : 'item', //提示框触发方式，分'item'、'none'两种
            formatter: config.tooltipFormatter, //当上面的tooltip.trigger为'item'时有效，触发时显示文字的格式，具体参考Echarts文档，例如"{a} <br/>{b} : {c} ({d}%)"
          },
          legend: {
            show: config.legendShow === false ? false : true, //图例是否显示
            orient: config.legendOrient, //图例列表的布局朝向，默认'horizontal'，可选：'vertical'、'horizontal'
            left: config.legendPos, //可设为'left'、'center'、'right'，或者20这样的数字、20%这样的百分比数字
            data: config.legendData, //默认legend会把所有data里的name都列在此，可做自定义
          },
          grid: {
            left: config.gridLeft || '10%',
            bottom: config.gridBottom || 60,
            right: config.gridRight || '10%',
            top: config.gridTop || 60,
          },
          series: [{
            name: config.pieChartName, //图表名字
            type: 'pie',
            label: {
              formatter: config.labelFormatter,
              rich: config.labelRich || {},
              show: config.labelShow === false ? false : true, //图表标签是否显示
            },
            radius: config.radius || '75%', //图表大小所占比例，可以是一个百分比的String或者一个有两个String的Array，代表一个环，比如['50%', '70%']
            center: config.center || '50%', //图表中心所在位置，可以是一个百分比的String或者一个有两个String的Array
            data: renderData.values, //一个数组，格式是[{value:new Number(), name:new String()},...]
          }]
        }
        myChart.setOption(option)
        return myChart
      },
      update: function(echartsInstance, jQueryDom, updateData, config) {
        echartsInstance.setOption({
          series: [{
            data: updateData.values,
          }],
        })
        echartsInstance.hideLoading()
        return echartsInstance
      },
    },
    singleValue: {
      init: function(jQueryDom, renderData, config) {
        jQueryDom.css("display", "flex")
        jQueryDom.css("align-items", "center")
        var wrapDiv = $(`<div class="kuichart-single-value-wrap"></div>`).appendTo(jQueryDom)
        $(`
        <span class="kuichart-single-value-span"></span><span class="kuichart-single-text-span"></span>
        `).appendTo(wrapDiv)

        var noData = false
        if (!renderData || _.isEmpty(renderData)) {
          noData = true
        } else if (renderData.value === null || renderData.value === undefined) {
          noData = true
        }
        
        var valueSpan = wrapDiv.find(".kuichart-single-value-span")
        var textSpan = wrapDiv.find(".kuichart-single-text-span")
        if (noData) {
          showLoading()
        } else {
          valueSpan.text(renderData.value)
          textSpan.text(renderData.text)
        }

        if (config) {
          if (config.color) {
            valueSpan.css("color", config.color)
          }
          if (config.fontSize) {
            valueSpan.css("font-size", config.fontSize)
          }
        }
        return {
          el: jQueryDom,
          resize: function() {

          },
          showLoading: showLoading,
          hideLoading: hideLoading,
        }

        function showLoading() {

        }

        function hideLoading() {

        }
      },
      update: function(singleValueInstance, jQueryDom, updateData, config) {
        var noData = false
        if (!updateData || _.isEmpty(updateData)) {
          noData = true
        } else if (updateData.value === null || updateData.value === undefined) {
          noData = true
        }
        if (noData) {
          singleValueInstance.showLoading()
          return singleValueInstance
        } else {
          jQueryDom.find(".kuichart-single-value-span").text(updateData.value)
          if (updateData.text) {
            jQueryDom.find(".kuichart-single-text-span").text(updateData.text)
          }
          singleValueInstance.hideLoading()
          return singleValueInstance
        }
      },
    },
    customTable: {
      init: function(jQueryDom, renderData, config) {
        // jQueryDom.css("display", "flex")
        // jQueryDom.css("align-items", "center")
        jQueryDom.height("100%")

        var isRendered = true
        if (!renderData || _.isEmpty(renderData)) {
          isRendered = false
        } else if (!_.isArray(renderData.category) || !renderData.category.length) {
          isRendered = false
        } else if (!_.isArray(renderData.values) || !renderData.values.length) {
          isRendered = false
        }
        if (!isRendered) {
          showLoading()
        } else {

          var tableDiv = $(`<table class="kuichart-custom-table-wrap"></table>`).appendTo(jQueryDom)

          buildTableDom(tableDiv, renderData, config)
          renderTableData(tableDiv, renderData)
        }

        return {
          el: jQueryDom,
          isRendered: isRendered,
          resize: function() {

          },
          showLoading: showLoading,
          hideLoading: hideLoading,
          buildTableDom: buildTableDom,
          renderTableData: renderTableData,
        }

        function showLoading() {

        }

        function hideLoading() {

        }

        function buildTableDom(tableDom, renderData, config) {
          $(`
          <thead class="kuichart-custom-table-thead">
            <tr class="kuichart-custom-thead-tr">
            </tr>
          </thead>
          <tbody class="kuichart-custom-table-tbody"></tbody>
          `).appendTo(tableDom)
          var theadTr = tableDom.find(".kuichart-custom-thead-tr")
          var keyArr = renderData.category.map(function(ctgrItem) {
            return ctgrItem.key
          })
          tableDom.data("keys", keyArr)
          renderData.category.forEach(function(ctgrItem) {
            theadTr.append(theadThRender(ctgrItem.text))
          })
        }

        function renderTableData(tableDom, renderData) {
          var keyArr = tableDom.data("keys")
          var tbody = tableDom.find(".kuichart-custom-table-tbody")
          renderData.values.forEach(function(dataObj) {
            var thisTr = $(tbodyTrRender()).appendTo(tbody)
            keyArr.forEach(function(key) {
              var insertText = ""
              if (dataObj[key]) {
                insertText = dataObj[key]
              }
              thisTr.append(tbodyTdRender(insertText))
            })
          })
        }

        function theadThRender(text) {
          return $(`
          <th class="kuichart-custom-thead-th">` + text + `</th>
          `)
        }

        function tbodyTrRender() {
          return $(`
          <tr class="kuichart-custom-tbody-tr"></tr>
          `)
        }

        function tbodyTdRender(text) {
          return $(`
          <td class="kuichart-custom-tbody-td">` + text + `</td>
          `)
        }
      },
      update: function(customTableInstance, jQueryDom, updateData, config) {
        var renderAble = true
        if (!updateData || _.isEmpty(updateData)) {
          renderAble = false
        } else if (!_.isArray(updateData.category) || !updateData.category.length) {
          renderAble = false
        } else if (!_.isArray(updateData.values) || !updateData.values.length) {
          renderAble = false
        }
        jQueryDom.find(".kuichart-custom-table-wrap").remove()
        customTableInstance.isRendered = renderAble
        if (!renderAble) {
          customTableInstance.showLoading()
        } else {
          var tableDiv = $(`<table class="kuichart-custom-table-wrap"></table>`).appendTo(jQueryDom)

          customTableInstance.buildTableDom(tableDiv, updateData, config)
          customTableInstance.renderTableData(tableDiv, updateData)
          customTableInstance.hideLoading()
        }
        return customTableInstance
      },
    }
  }

  var echartDefaultColors = ['#c23531','#2f4554', '#61a0a8', '#d48265', '#91c7ae','#749f83',  '#ca8622', '#bda29a','#6e7074', '#546570', '#c4ccd3']

  function init(target) {
    // kuichart组件初始化需要的必需参数检验
    var readyInit = checkConfig(target)
    if (readyInit === false) {
      return
    }
    buildDom(target)
  }

  function checkConfig(target) {
    var isConfigLegal = true
    var opts = $.data(target, 'kuichart').options
    if (!opts.type) {
      console.log("kuichart初始化需要type属性")
      isConfigLegal = false
      return isConfigLegal
    }
    return isConfigLegal
  }

  function buildDom(target) {
    var opts = $.data(target, 'kuichart').options
    $(target).addClass("kuichart-wrapper")
    addChartLayer(target, opts)
    addFloatLayer(target)
    var renderSuccess = initRenderChart(target, opts)
    if (renderSuccess === false) {
      return
    }
    eventRegister(target)
    addTitle(target, opts)
    reqData(target, opts)

    function addChartLayer(target, opts) {
      if (opts.config.titlePaddingTop === true) {
        $(target).append(`
        <div class="kuichart-chart-layer chartlayer-title-paddingtop">
        </div>
        `)
      } else {
        $(target).append(`
        <div class="kuichart-chart-layer">
        </div>
        `)
      }
    }

    function addFloatLayer(target) {
      $(target).append(`
      <div class="kuichart-float-layer">
      </div>
      `)
    }

    function addTitle(target, opts) {
      var floatTitle = $(`
      <div class="kuichart-float-title">
      </div>
      `).appendTo($(target).find(".kuichart-float-layer"))

      if (opts.title) {
        floatTitle.text(opts.title)
      }
    }

    function eventRegister(target) {
      var initedChart = $(target).data("initedInstance")
      $(target).on("resize.kuichart", function(e) {
        if (!initedChart) {
          return
        }
        initedChart.resize()
        e.stopPropagation()
      })
    }
  }

  function initRenderChart(target, opts) {
    var chartType = opts.type
    var renderFunc = allRenderFunc[chartType]
    if (!renderFunc) {
      console.log("kuichart缺少 " + chartType + " 类型的渲染函数，渲染失败")
      return false
    } else if (!renderFunc.init) {
      console.log("kuichart缺少 " + chartType + " 类型的渲染init函数，渲染失败")
      return false
    } else {
      var initedChart = renderFunc.init.call(renderFunc, $(target).find(".kuichart-chart-layer"), opts.data, opts.config)
      $(target).data("initedInstance", initedChart)
    }
  }

  function reqData(target, opts) {
    var req = opts.req
    if (!req || _.isEmpty(req)) {
      return
    } else {
      ajaxRequest({
        req: req,
        error: function(err) {
          debugger
          console.log(err)
        },
        func: function(rawData) {
          var data = opts.dataHandler ? opts.dataHandler.call(target, rawData) : rawData
          updateChart(target, data)
        }
      })
    }
  }

  function updateChart(target, data) {
    var opts = $.data(target, 'kuichart').options
    var chartType = opts.type
    var renderFunc = allRenderFunc[chartType]
    if (!renderFunc.update) {
      console.log("kuichart缺少 " + chartType + " 类型的渲染update函数，渲染失败")
      return false
    } else {
      renderFunc.update.call(renderFunc, $(target).data("initedInstance"), $(target).find(".kuichart-chart-layer"), data, opts.config)
    }
  }

  function getInstance(target) {
    return $(target).data("initedInstance")
  }

  function updateConfig(target, config) {

  }

  function setTitle(target, titleText) {
    return $(target).find(".kuichart-float-layer .kuichart-float-title").text(titleText)
  }

  function getFloatLayer(target) {
    return $(target).find(".kuichart-float-layer")
  }

});