/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: WriteToDbBolt
 * Author:   zmh
 * Date:     2018/7/10 10:10
 * Description: 结果写入PostgreSQL数据库
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szkingdom.storm.monitor;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.dao.DaoOptAsset;
import com.szkingdom.dao.DaoRiskIndicatorOverview;
import com.szkingdom.dao.DaoRiskIndicators;
import com.szkingdom.dao.DaoRiskInfoAsset;
import com.szkingdom.data.DataOptAsset;
import com.szkingdom.data.DataRiskIndicatorOverview;
import com.szkingdom.data.DataRiskIndicators;
import com.szkingdom.data.DataRiskInfoAsset;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈结果写入PostgreSQL数据库〉
 * @author zmh
 * @create 2018/7/10
 * @since 1.0.0
 */
public class WriteToDbBolt extends BaseRichBolt implements Serializable {
    private static final long serialVersionUID = 8100472728473265280L;
    private static final boolean SET_UNIQUE_INDEX_DATA = true;
    private static final boolean SET_ALL_DATA = false;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private OutputCollector collector;    

    @Override
    public void prepare(Map topoConf, TopologyContext context, OutputCollector collector){
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input){

        // 确保不是系统发送的tuple，才使用我们的业务逻辑
        if (!input.getSourceComponent().equalsIgnoreCase(Constants.SYSTEM_COMPONENT_ID) ) {

            String tableName = input.getStringByField("TABLE_NAME");
            String tableMsg  = input.getStringByField("VALUE");
            if (tableName.length() <= 0 || tableMsg.length() <= 0 ){
                this.collector.fail(input);
                logger.error("WriteToDbBolt tableName.length/tableMsg.length <= 0 fail ! \n");
                return;
            }

            JSONObject object = JSON.parseObject(tableMsg);

            /**
             * 写入数据库RiskInfoAsset表
             */
            if (tableName.equals(ConstantUtil.TABLE_RISK_INFO_ASSET)) {
                // 创建类dataRiskInfoAsset，接收RiskInfoAsset表中，唯一索引字段
                DataRiskInfoAsset dataRiskInfoAsset = new DataRiskInfoAsset();
                dataRiskInfoAsset.updateJsonDataToChart(object, SET_UNIQUE_INDEX_DATA);

                DaoRiskInfoAsset daoRiskInfoAsset = new DaoRiskInfoAsset();
                try {
                    //写入RiskInfoAsset表: 先查，有数据则update，无数据则insert
                    List<DataRiskInfoAsset> listRiskInfoAsset = daoRiskInfoAsset.selectRiskInfoAssetList(dataRiskInfoAsset);
                    //update数据
                    if (listRiskInfoAsset.size() > 0) {
                        //查到的数据，先保存下来
                        dataRiskInfoAsset.savePreviousData(listRiskInfoAsset);
                        //在此写入要更新的数据
                        dataRiskInfoAsset.updateJsonDataToChart(object, SET_ALL_DATA);
                        daoRiskInfoAsset.updateRiskInfoAsset(dataRiskInfoAsset);
                    }
                    // insert数据
                    else {
                        //对空字段设置默认值
                        dataRiskInfoAsset.dataRiskInfoAssetInit();
                        //在此写入要插入的数据
                        dataRiskInfoAsset.updateJsonDataToChart(object, SET_ALL_DATA);
                        daoRiskInfoAsset.insertRiskInfoAsset(dataRiskInfoAsset);
                    }
                    this.collector.ack(input);
                    logger.info("WriteToDbBolt  Acked! \n");
                } catch (Exception e) {
                    e.printStackTrace();
                    this.collector.fail(input);
                    logger.error("WriteToDbBolt insert/update RISK_INFO_ASSET fail ! \n");
                    try {
                        throw e;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            } else if (tableName.equals(ConstantUtil.TABLE_OPT_ASSET)) {
                //写入数据库OptAsset表,唯一索引字段
                DataOptAsset dataOptAsset = new DataOptAsset();
                dataOptAsset.updateJsonDataToChart(object, SET_UNIQUE_INDEX_DATA);

                DaoOptAsset daoOptAsset = new DaoOptAsset();
                try {
                    //写入OptAsset表: 先查，有数据则update，无数据则insert
                    List<DataOptAsset> listOptAsset = daoOptAsset.selectOptAssetList(dataOptAsset);
                    if (listOptAsset.size() > 0) {
                        dataOptAsset.savePreviousData(listOptAsset);
                        dataOptAsset.updateJsonDataToChart(object, SET_ALL_DATA);
                        // 额度需要累加，包括总持仓和当日累计
                        dataOptAsset.setOptPosiRlt(dataOptAsset.getOptPosiRlt()+listOptAsset.get(0).getOptPosiRlt());
                        dataOptAsset.setOptDailyOpenRlt(dataOptAsset.getOptDailyOpenRlt()+listOptAsset.get(0).getOptDailyOpenRlt());
                        daoOptAsset.updateOptAsset(dataOptAsset);
                    } else {
                        dataOptAsset.dataOptAssetInit();
                        dataOptAsset.updateJsonDataToChart(object, SET_ALL_DATA);
                        daoOptAsset.insertOptAsset(dataOptAsset);
                    }
                    this.collector.ack(input);
                    logger.info("WriteToDbBolt  Acked! \n");
                } catch (Exception e) {
                    e.printStackTrace();
                    this.collector.fail(input);
                    logger.error("WriteToDbBolt insert/update OPT_ASSET fail ! \n");
                    try {
                        throw e;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

            } else {
                logger.info("Other message! \n");
            }
        }
        else {
            //局部定时任务 更新风险指标总览表
            DataRiskIndicators dataRiskIndicators = new DataRiskIndicators();
            dataRiskIndicators.setBizAttr("00");
            dataRiskIndicators.setIndicatorCls("03");
            DaoRiskIndicators daoRiskIndicators = new DaoRiskIndicators();
            try {
                List<DataRiskIndicators> listRiskIndicators = daoRiskIndicators.selectRiskIndicatorsList(dataRiskIndicators);

                for (DataRiskIndicators listRiskIndicator : listRiskIndicators) {
                    DataRiskIndicatorOverview dataRiskIndicatorOverview = new DataRiskIndicatorOverview();
                    dataRiskIndicatorOverview.setIndicatorId(listRiskIndicator.getIndicatorId());
                    dataRiskIndicatorOverview.setBizAttr("00");
                    dataRiskIndicatorOverview.setIndicatorCls("03");
                    try {
                        DaoRiskIndicatorOverview daoRiskIndicatorOverview = new DaoRiskIndicatorOverview();
                        daoRiskIndicatorOverview.updateRiskIndicatorOverview(dataRiskIndicatorOverview);
                    } catch (Exception e) {
                        logger.error("Exception daoCustomer.selectCustomerListByPara ERROR [ " + e + "]\n");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> config = new HashMap<>();

        //定时时间
        config.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 3);
        return config;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer){

    }
}
