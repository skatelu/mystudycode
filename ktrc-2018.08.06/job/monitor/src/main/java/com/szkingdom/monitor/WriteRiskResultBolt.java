/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: WriteRiskResultBolt
 * Author:   ZhangMaohua
 * Date:     2018/7/23
 * Description: 数据处理结果写入数据库
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.dao.DaoRiskInfoAsset;
import com.szkingdom.data.DataRiskInfoAsset;
import com.szkingdom.operation.*;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.trident.operation.Operation;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class WriteRiskResultBolt extends BaseRichBolt {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private OutputCollector collector;

    @Override
    public void prepare(Map topoConf, TopologyContext context, OutputCollector collector){
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input){

        String message = input.getStringByField(ConstantUtil.CALC_RISK_INDICATORS_BOLT_DECLARE_FIELDS);
        if ("".equals(message)) {
            this.collector.ack(input);
            logger.warn("WARN" + this.getClass() + " message is null ! \n");
            return;
        }

        JSONObject jsonObject = JSON.parseObject(message);

        //Indicator表中增加匹配risk表字段，待修改
        JSONObject indicatorInfo = (JSONObject)jsonObject.get(ConstantUtil.JSON_INDICATORINFO);
        String tableName = OperationWriteRiskTables.getRiskTableName(indicatorInfo.getString("indicatorId"));
        try {
            OperationWriteRiskTables.writeMsgToRiskTables(jsonObject,tableName);
        } catch (Exception e) {
            logger.error("风险结果录入数据库错误！", e);
        }

        // Indicator表中增加匹配risk表字段后
//        try {
//            OperationWriteRiskTables.riskMessageToTables(jsonObject);
//        } catch (Exception e) {
//            logger.error("风险结果录入数据库错误！");
//        }
        this.collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer){

    }

}