/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationIndicatorThreshold
 * Author:   ZhangMaohua
 * Date:     2018/8/27
 * Description: 风险阈值表
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;


import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.dao.DaoIndicatorThreshold;
import com.szkingdom.data.DataIndicatorThreshold;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationIndicatorThreshold extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationIndicatorThreshold.class);

    /**
     * 按唯一索引查询风险阈值表数据
     * @param condDataIndicatorThreshold
     * @throws Exception
     */
    public static DataIndicatorThreshold getIndicatorThresholdByUnique(DataIndicatorThreshold condDataIndicatorThreshold) throws Exception {
        DaoIndicatorThreshold daoIndicatorThreshold = new DaoIndicatorThreshold();
        try {
            DataIndicatorThreshold resultDataIndicatorThreshold = daoIndicatorThreshold.selectUnique(condDataIndicatorThreshold);
            if (resultDataIndicatorThreshold != null) {
                return resultDataIndicatorThreshold;
            } else {
                logger.warn("查表IndicatorThreshold结果为空！ \n");
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 返回阈值等字段，该字段最终写入相应风险结果表
     * @param indicatorId
     * @param indicatorResult
     * @return 返回阈值信息的JSON串
     * @throws Exception
     */
    public static JSONObject getThresholdForRiskTable(String indicatorId, long indicatorResult) throws Exception{
        JSONObject jsonObject = new JSONObject();

        // 根据唯一索引组串key值。
        String keySearch = ConstantUtil.TABLE_INDICATOR_THRESHOLD + "_";
        keySearch += indicatorId;
        DataIndicatorThreshold mapDataIndicatorThreshold = (DataIndicatorThreshold) InitCache.read(keySearch);
        if(mapDataIndicatorThreshold != null){
            jsonObject = getThershold(jsonObject,mapDataIndicatorThreshold,indicatorResult);
            return jsonObject;
        }
        //map缓存中没有数据，则继续查数据库表
        DataIndicatorThreshold condDataIndicatorThreshold = new DataIndicatorThreshold();
        condDataIndicatorThreshold.setIndicatorId(indicatorId);
        try {
            DataIndicatorThreshold resultDataIndicatorThreshold = getIndicatorThresholdByUnique(condDataIndicatorThreshold);
            if(resultDataIndicatorThreshold != null){
                InitCache.write(keySearch, resultDataIndicatorThreshold);
                jsonObject = getThershold(jsonObject,resultDataIndicatorThreshold,indicatorResult);
                return jsonObject;
            }
            else{
                logger.warn("风险指标阈值表（INDICATOR_THRESHOLD）查无数据！");
                return null;
            }
        } catch (Exception e) {
            throw new Exception("风险指标阈值表（INDICATOR_THRESHOLD）获取阈值失败！", e);
        }
    }

    private static JSONObject getThershold(JSONObject jsonObject, DataIndicatorThreshold condDataIndicatorThreshold, long indicatorResult){
        String operForluma1 = condDataIndicatorThreshold.getThres1Oper();
        if(!operForluma1.equals("") && condDataIndicatorThreshold.getThres1Valid()=='1'){
            if(OperationRiskIndicators.getOperationResult(indicatorResult, operForluma1)){
                jsonObject.put("THRES_NO",condDataIndicatorThreshold.getThres1No());
                jsonObject.put("THRES_NAME",condDataIndicatorThreshold.getThres1Name());
                jsonObject.put("THRES_COLOR",condDataIndicatorThreshold.getThres1Color());
                jsonObject.put("THRES_CNT",condDataIndicatorThreshold.getThresCnt());
                return jsonObject;
            }
        }
        String operForluma2 = condDataIndicatorThreshold.getThres2Oper();
        if(!operForluma2.equals("") && condDataIndicatorThreshold.getThres2Valid()=='1'){
            if(OperationRiskIndicators.getOperationResult(indicatorResult, operForluma2)){
                jsonObject.put("THRES_NO",condDataIndicatorThreshold.getThres2No());
                jsonObject.put("THRES_NAME",condDataIndicatorThreshold.getThres2Name());
                jsonObject.put("THRES_COLOR",condDataIndicatorThreshold.getThres2Color());
                jsonObject.put("THRES_CNT",condDataIndicatorThreshold.getThresCnt());
                return jsonObject;
            }
        }
        String operForluma3 = condDataIndicatorThreshold.getThres3Oper();
        if(!operForluma3.equals("") && condDataIndicatorThreshold.getThres3Valid()=='1'){
            if(OperationRiskIndicators.getOperationResult(indicatorResult, operForluma3)){
                jsonObject.put("THRES_NO",condDataIndicatorThreshold.getThres3No());
                jsonObject.put("THRES_NAME",condDataIndicatorThreshold.getThres3Name());
                jsonObject.put("THRES_COLOR",condDataIndicatorThreshold.getThres3Color());
                jsonObject.put("THRES_CNT",condDataIndicatorThreshold.getThresCnt());
                return jsonObject;
            }
        }
        String operForluma4 = condDataIndicatorThreshold.getThres4Oper();
        if(!operForluma4.equals("") && condDataIndicatorThreshold.getThres4Valid()=='1'){
            if(OperationRiskIndicators.getOperationResult(indicatorResult, operForluma4)){
                jsonObject.put("THRES_NO",condDataIndicatorThreshold.getThres4No());
                jsonObject.put("THRES_NAME",condDataIndicatorThreshold.getThres4Name());
                jsonObject.put("THRES_COLOR",condDataIndicatorThreshold.getThres4Color());
                jsonObject.put("THRES_CNT",condDataIndicatorThreshold.getThresCnt());
                return jsonObject;
            }
        }
        return null;
    }
}
