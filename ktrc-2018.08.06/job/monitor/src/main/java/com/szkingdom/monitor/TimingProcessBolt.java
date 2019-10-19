/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: TimingProcessBolt
 * Author:   ZhangMaohua
 * Date:     2018/8/16
 * Description: 定时处理
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitConfig;
import com.szkingdom.dao.DaoOptAsset;
import com.szkingdom.dao.DaoOptInfo;
import com.szkingdom.dao.DaoRiskIndicators;
import com.szkingdom.data.DataOptAsset;
import com.szkingdom.data.DataOptInfo;
import com.szkingdom.data.DataRiskIndicators;
import com.szkingdom.operation.OperationExhaustiveRiskIndicators;
import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.szkingdom.common.DateUtils;

import java.util.*;

public class TimingProcessBolt extends BaseRichBolt {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private OutputCollector collector;

    @Override
    public void prepare(Map topoConf, TopologyContext context, OutputCollector collector){
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input){

        //进入定时处理任务
        if (input.getSourceComponent().equalsIgnoreCase(Constants.SYSTEM_COMPONENT_ID) ) {

            JSONObject jsonObject = new JSONObject();
            int currentDate = DateUtils.getCurrDate();
            String[] indicatorIdArray = {"60007001","60007002","60007003"};

            //业务处理
            try {
                /**
                 * 股票期权行权风险指标定时处理
                 * 处理逻辑：对3个行权风险指标，获取相关信息和风险因子，发送到CalcRiskIndicatorsBolt进行处理.
                 * 步骤： 1. 获取股票期权基础信息（OPT_INFO）中相对期权行权日字段EXERCISE_DATE，当前日期为E+1和E+2日的所有行，进行处理；
                 *       2. 对每一条数据（标的证券）获取相应 股票期权基础信息（OPT_INFO）中数据；
                 *       3. 根据OPT_INFO中的标的证券代码OPT_UNDL_CODE 查询 股票期权合约资产（OPT_ASSET）表，
                 *          获得不同客户合约信息；对不同客户分别进行风险监控；
                 *       4. 遍历对3个行权风险指标，获取风险指标表（RISK_INDICATORS）中数据。
                 */
                JSONObject data = new JSONObject();
                //步骤1
                DataOptInfo dataOptInfoPara = new DataOptInfo();
                //dataOptInfoPara.setExerciseDate(currentDate);
                DaoOptInfo daoOptInfo = new DaoOptInfo();
                try{
                    List<DataOptInfo> dataOptInfoList = daoOptInfo.selectList(dataOptInfoPara);
                    if(dataOptInfoList.size() > 0){
                        //步骤2
                        for(DataOptInfo dataOptInfoEach : dataOptInfoList){
                            data.put("STKBD",dataOptInfoEach.getStkbd());
                            data.put("STKEX",dataOptInfoEach.getStkex());
                            data.put("OPT_UNDL_CODE",dataOptInfoEach.getOptUndlCode());
                            data.put("OPT_UNDL_NAME",dataOptInfoEach.getOptUndlName());
                            data.put("OPT_UNDL_CLS",dataOptInfoEach.getOptUndlCls());
                            data.put("DELIVERY_DATE",dataOptInfoEach.getDeliveryDate());

                            //步骤3
                            DataOptAsset dataOptAssetPara = new DataOptAsset();
                            DaoOptAsset daoOptAsset = new DaoOptAsset();
                            dataOptAssetPara.setOptUndlCode(dataOptInfoEach.getOptUndlCode());
                            dataOptAssetPara.setStkbd(dataOptInfoEach.getStkbd());
                            try{
                                List<DataOptAsset> dataOptAssetList = daoOptAsset.selectList(dataOptAssetPara);
                                if(dataOptAssetList.size() > 0){
                                    for(DataOptAsset dataOptAssetEach : dataOptAssetList){
                                        data.put("STKPBU",dataOptAssetEach.getStkpbu());
                                        data.put("CUST_CODE",dataOptAssetEach.getCustCode());
                                        data.put("CUACCT_CODE",dataOptAssetEach.getCuacctCode());
                                        data.put("CURRENCY",dataOptAssetEach.getCurrency());
                                        data.put("TRDACCT",dataOptAssetEach.getTrdacct());
                                        data.put("SUBACCT_CODE",dataOptAssetEach.getSubacctCode());
                                        data.put("TRDACCT_EXCLS",dataOptAssetEach.getTrdacctExcls());

                                        data.put("CUST_TYPE",dataOptAssetEach.getCustType());

                                        jsonObject.put(ConstantUtil.JSON_KTRC,data);

                                        //遍历3个风险指标
                                        DaoRiskIndicators daoRiskIndicators = new DaoRiskIndicators();
                                        for(String indicatorId : indicatorIdArray)
                                        {
                                            //步骤4
                                            DataRiskIndicators condDataRiskIndicators = new DataRiskIndicators();
                                            condDataRiskIndicators.setIndicatorId(indicatorId);
                                            DataRiskIndicators resultDataRiskIndicators = daoRiskIndicators.selectUnique(condDataRiskIndicators);
                                            if(resultDataRiskIndicators != null) {
                                                if (resultDataRiskIndicators.getIndicatorRunFlag() == '1') {
                                                    jsonObject.put(ConstantUtil.JSON_INDICATORINFO, resultDataRiskIndicators);
                                                    this.collector.emit(new Values(JSON.toJSONString(jsonObject)));
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e){
                                e.printStackTrace();
                                logger.error("SELECT TABLE_OPT_ASSET ERROR !");
                            }
                        }
                    } else {
                        logger.info("没有行权标的，今日非E+1、E+2日 ！");
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    logger.error("SELECT TABLE_OPT_INFO ERROR ! ");
                }
            } catch (Exception e){
                e.printStackTrace();
                logger.error("TimingProcessBolt process error !");
            }
        }
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> config = new HashMap<>();

        //设置定时时间
        long timeInterval = Long.parseLong(InitConfig.INTERVAL_TIME);
        config.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, timeInterval);
        return config;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer){
        declarer.declare(new Fields(ConstantUtil.MAINTAIN_REALTIME_BOLT_DECLARE_FIELDS));
    }

}