/**  
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName:  CalcRiskIndicatorsBolt.java    
 * Author: yinhl     
 * Date:   2018年7月23日  
 * Description: 风险指标计算
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 *
 */
package com.szkingdom.monitor;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.Jep;
import com.singularsys.jep.ParseException;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.factor.FactorBase;
import com.szkingdom.factor.classload.ClassLoadFactors;

/**
 * 风险指标计算<br>
 * 1. 根据风险指标加载该指标计算公式<br>
 * 2. 使用词法解析器解析计算公式<br>
 * 3. 加载组成该计算公式的风险因子<br>
 * 4. 根据各风险因子获取风险因子入口类<br>
 * 5. 计算出风险结果<br>
 * 
 * @author yinhl
 *
 */
public class CalcRiskIndicatorsBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(CalcRiskIndicatorsBolt.class);
	private static DecimalFormat decimalFormat_8 = new DecimalFormat("0.0#######");
	private static DecimalFormat decimalFormat_2 = new DecimalFormat("0.0#");

	private OutputCollector collector;

	/**
	 * 
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	/**
	 * 
	 */
	@Override
	public void execute(Tuple input) {

		String msg = input.getStringByField(ConstantUtil.MAINTAIN_REALTIME_BOLT_DECLARE_FIELDS);
		if ("".equals(msg.trim())) {
			this.collector.ack(input);
			logger.warn("WARN CalcRiskIndicatorsBolt message is null \n");
			return;
		}

		Map<String, Object> factorResultMap = new HashMap<>();

		JSONObject json = JSON.parseObject(msg);
		JSONObject indicatorJson = json.getJSONObject(ConstantUtil.JSON_INDICATORINFO);

		// 使用当前指标锁使用到的因子，用逗号隔开
		String riskFactor = indicatorJson.getString("indicatorFactors");
		List<String> factorList = getFactorList(riskFactor);
		for (String currFactor : factorList) {
			currFactor = currFactor.trim();
			// 如果解析过则跳过当前，解析下一个因子
			if (factorResultMap.containsKey(currFactor)) {
				continue;
			}

			FactorBase factorBase = null;
			try {
				factorBase = ClassLoadFactors.getFactorObj(currFactor);
				try {
					factorBase.resolveParameter(json);
					try {
						factorBase.handleFactor();
						try {
							Object ret = factorBase.getResult();
							factorResultMap.put(currFactor, ret);
						} catch (Exception e) {
							logger.error("拿取指标因子{}结果异常" + e.getMessage(), currFactor, e);
							this.collector.ack(input);
							return;
						}
					} catch (Exception e) {
						logger.error("处理指标因子{}异常" + e.getMessage(), currFactor, e);
						this.collector.ack(input);
						return;
					}
				} catch (Exception e) {
					logger.error("指标因子json对象{}解析出现异常" + e.getMessage(), msg, e);
					this.collector.ack(input);
					return;
				}
			} catch (Exception e) {
				logger.error("系统未定义当前因子{}", currFactor, e);
				this.collector.ack(input);
				return;
			}
		}

		// 指标公式
		String riskIndicator_dest = indicatorJson.getString("indicatorFormula");
		String riskIndicator = riskIndicator_dest;
		for (Map.Entry<String, Object> entry : factorResultMap.entrySet()) {
			// 把因子替换成该因子对应的结果值
			riskIndicator = riskIndicator.replace(entry.getKey(), entry.getValue().toString());
		}

		Jep jep = new Jep();
		try {
			jep.parse(riskIndicator);
			// 对原来的json对象增加指标结果
			indicatorJson.put("indicatorResult", getResult(jep.evaluate().toString(), indicatorJson));
			indicatorJson.put("indicatorFactorsValue", factorResultMap);
			json.put(ConstantUtil.JSON_INDICATORINFO, indicatorJson);
			this.collector.emit(new Values(json.toJSONString()));
		} catch (ParseException e) {
			logger.error("指标因子公式{}转换成运算公式{}异常", riskIndicator_dest, riskIndicator, e);
			this.collector.ack(input);
			return;
		} catch (EvaluationException e) {
			logger.error("指标因子公式{}转换成运算公式取结果值{}异常", riskIndicator_dest, riskIndicator, e);
			this.collector.ack(input);
			return;
		} catch (Exception e) {
			logger.error("使用Jep操作因子公式{}转换成运算公式取结果值{}异常", riskIndicator_dest, riskIndicator, e);
		}

		this.collector.ack(input);
	}

	/**
	 * 处理Jep表达式结果返回精度问题
	 * 
	 * @param evaluate
	 * @param indicatorJson
	 * @return
	 */
	private String getResult(String evaluate, JSONObject indicatorJson) {
		try {
			double d = Double.parseDouble(evaluate);
			evaluate = decimalFormat_8.format(d);
			char indicatorResultType = indicatorJson.getString("indicatorResultType").charAt(0);
			if ('2' == indicatorResultType) {
				evaluate = decimalFormat_2.format(d);
			}
		} catch (Exception e) {
		}

		return evaluate;
	}

	/**
	 * 截取指标中用到的因子
	 * 
	 * @param riskFactor
	 * @return
	 */
	private List<String> getFactorList(String riskFactor) {
		String[] arrs = riskFactor.split(",");
		return Arrays.asList(arrs);
	}

	/**
	 * 
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(ConstantUtil.CALC_RISK_INDICATORS_BOLT_DECLARE_FIELDS));
	}
}
