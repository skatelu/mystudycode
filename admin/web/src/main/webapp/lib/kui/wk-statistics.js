/**
 * 统计查询的webWork [HTML5 环境运行]
 */
onmessage = function(event) {
	postMessage(doStatist(event.data));
};


function doStatist(arg) {
	var columns = arg.columns,
		rows = arg.rows,
		statisticsType = getStatisticsType(arg.statisticsTexts);

	for (var j = 0; j < columns.length; j++) {
		var col = columns[j], //字段属性
			field = col["field"], //字段名
			val = {};

		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];

			//判断是数据是否以'.'开头，转化为数字类型----by lims
			if("string" === typeof row[field]){
				if(row[field].indexOf(".")==0){
					row[field] = parseFloat(row[field]);
				}

			}

			//if ($.isNumeric(row[field])) {
			if ("number" === typeof row[field] ||
				("string" === typeof row[field] && row[field].match(/^[-]?\d+[.]?\d*$/g))) {
				for (var key in statisticsType) {
					if (col[key]) {
						var tmp = statisticsType[key].getValue(val, rows, row, field, i);
						if (i == rows.length - 1) {
							statisticsType[key].item.push({
								"field": col["field"],
								"title": col["title"],
								"formatter": col[key + "formatter"],
								"value": isNaN(tmp) ? tmp + "" : Number(tmp).toFixed(col.fixNum || 2)
							});
						}
					}
				}
			}
		}
	}
	for (var _key in statisticsType) {
		delete statisticsType[_key].getValue;
	}
	return statisticsType;
}

//获取统计类型
function getStatisticsType(statisticsTexts) {
	return {
		max: {
			name: statisticsTexts[0] || "最大值",
			item: [],
			getValue: function(val, rows, row, field, i) {
				return getMax(val, rows, row, field, i);
			}
		},
		min: {
			name: statisticsTexts[1] || "最小值",
			item: [],
			getValue: function(val, rows, row, field, i) {
				return getMin(val, rows, row, field, i);
			}
		},
		avg: {
			name: statisticsTexts[2] || "均值",
			item: [],
			getValue: function(val, rows, row, field, i) {
				return getAvg(val, rows, row, field, i);
			}
		},
		sum: {
			name: statisticsTexts[3] || "合计",
			item: [],
			getValue: function(val, rows, row, field, i) {
				return getSum(val, rows, row, field, i);
			}
		}
	};
}
//最大值
function getMax(val, rows, row, field) {
	var key = "max" + field,
		fieldVal = Number(row[field]),
		n = val[key] || 0;
	if (n < fieldVal) {
		val[key] = fieldVal;
	}
	return val[key];
}

//最小值
function getMin(val, rows, row, field) {
	var key = "min" + field,
		fieldVal = Number(row[field]),
		n = val[key] || row[field];
	if (n >= fieldVal) {
		val[key] = fieldVal;
	}
	return val[key];
}

//均值
function getAvg(val, rows, row, field, i) {
	var key = "avg" + field,
		n = val[key] || 0;

	val[key] = n + row[field] * 1;
	if (rows.length - 1 == i) {
		val[key] = val[key] / rows.length;
	}
	return val[key];
}

//合计
function getSum(val, rows, row, field) {
	var key = "sum" + field,
		n = val[key] || 0;

	val[key] = n + row[field] * 1;
	return val[key];
}
//根据字符计算所占的长度
function caculateCharLength(cString) {
	cString = cString + ""; // 确保是字符串类型
	var len = 0;
	if (!cString || "" === cString ) {
		return len;
	}
	for (i = 0; i < cString.length; i++) {
		if (cString.charCodeAt(i) > 256) {
			len += 2 + 12;
		} else if (cString.charCodeAt(i) < 65) {
			len += 8; // 7
		} else {

			len += 9; //   
		}
	}
	return len;
}