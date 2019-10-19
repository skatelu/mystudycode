/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: MaintainRealtimeDataBolt
 * Author:   ZhangChangHong
 * Date:     2018/7/24
 * Description: 实时数据维护
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataRiskIndicators;
import com.szkingdom.operation.*;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MaintainRealtimeDataBolt extends BaseRichBolt {
    protected Logger logger = LoggerFactory.getLogger(MaintainRealtimeDataBolt.class);

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String message = "";
        Object objMsg = input.getValue(4);
        if (objMsg != null) {
            message = objMsg.toString();
            if ("".equals(message.trim())) {
                this.collector.ack(input);
                logger.warn("Tuple数据为空，异常数据");
                return;
            }
        } else {
            this.collector.ack(input);
            logger.warn("Tuple数据为空，异常数据");
            return;
        }

        // 业务处理
        try {
            List<Object> listObjectParam = new ArrayList<Object>();
            StringBuilder stringBuilder = new StringBuilder();

            JSONObject jsonObject;
            try {
                jsonObject = JSON.parseObject(message);
            } catch (JSONException e) {
                logger.error("数据非json格式，无效数据:{}", message);
                return;
            }

            // 参数校验
            if (!OperationCheckParam.checkParamValidity(jsonObject, stringBuilder, listObjectParam)) {
                this.collector.ack(input);
                return;
            }

            if (listObjectParam == null || listObjectParam.isEmpty()) {
                logger.error("接口数据对象收集失败:{}", message);
                this.collector.ack(input);
                return;
            }

            String dataId = stringBuilder.toString();
            stringBuilder.delete(0, stringBuilder.length());

            // 去重数据或无效数据直接ack确认，不做emit处理
            List<Object> listObjectTableData = new ArrayList<Object>();
            if (OperationDistinctData.distinctExistsData(dataId, listObjectParam.get(0), stringBuilder, listObjectTableData)) {
                this.collector.ack(input);
                logger.error("数据为重复或无效数据:{}", message);

                return;
            }

            if ((!ConstantUtil.OPT_MARGIN_CORP.equals(dataId))) {
                if (listObjectTableData == null || listObjectTableData.isEmpty()) {
                    logger.error("去重写表数据对象收集失败:{}", message);
                    this.collector.ack(input);
                    return;
                }
            } else {
                listObjectTableData.add(new Object());
            }

            // 数据写入DB
            OperationLockDB operationLockDB = new OperationLockDB();
            operationLockDB.writeMaintainRealtimeData2DB(dataId, listObjectParam.get(0), stringBuilder, listObjectTableData.get(0), jsonObject);

            // 指标收集，分发, 完成后手动clean
            List<DataRiskIndicators> listRiskIndicators = new ArrayList<DataRiskIndicators>();
            OperationExhaustiveRiskIndicators.gatherRiskIndicators(dataId, listRiskIndicators, listObjectParam.get(0));

            if (listRiskIndicators != null && !listRiskIndicators.isEmpty()) {
                for (DataRiskIndicators dataRiskIndicators : listRiskIndicators) {
                    jsonObject.put(ConstantUtil.JSON_INDICATORINFO, dataRiskIndicators);

                    this.collector.emit(input, new Values(JSON.toJSONString(jsonObject)));
                }

                listRiskIndicators.clear();
            } else {
                logger.error("指标收集容器listRiskIndicators为空");
            }

            listObjectParam.clear();
            listObjectTableData.clear();

            this.collector.ack(input);

        } catch (Exception e) {
            // 数据库操作失败，sql or connect error 重发一次message
            this.collector.fail(input);
            logger.error("MaintainRealtimeDataBolt 处理异常:", e);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(ConstantUtil.MAINTAIN_REALTIME_BOLT_DECLARE_FIELDS));
    }
}
