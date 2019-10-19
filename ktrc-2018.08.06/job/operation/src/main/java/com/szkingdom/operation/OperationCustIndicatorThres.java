/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationCustIndicatorThres
 * Author:   ZhangMaohua
 * Date:     2018/8/27
 * Description: 客户风险阈值表
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;


import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.dao.DaoCustIndicatorThres;
import com.szkingdom.data.DataCustIndicatorThres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationCustIndicatorThres extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationCustIndicatorThres.class);

    /**
     * 按唯一索引查询客户风险阈值表数据
     *
     * @param condDataCustIndicatorThres
     * @throws Exception
     */
    public static DataCustIndicatorThres getCustIndicatorThresByUnique(DataCustIndicatorThres condDataCustIndicatorThres) throws Exception {
        DaoCustIndicatorThres daoCustIndicatorThres = new DaoCustIndicatorThres();
        try {
            DataCustIndicatorThres resultDataCustIndicatorThres = daoCustIndicatorThres.selectUnique(condDataCustIndicatorThres);
            if (resultDataCustIndicatorThres != null) {
                return resultDataCustIndicatorThres;

            } else {
                logger.info("查表CustIndicatorThres结果为空！ \n");
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 返回阈值等字段，该字段最终写入相应风险结果表
     * @param custCode
     * @param indicatorId
     * @param indicatorResult
     * @return
     * @throws Exception
     */
    public static JSONObject getCustThresForRiskTable(long custCode, String indicatorId, long indicatorResult) throws Exception {

        JSONObject jsonObject = new JSONObject();

        //根据唯一索引组串key值。
        String keySearch = ConstantUtil.TABLE_CUST_INDICATOR_THRES + "_";
        keySearch += indicatorId;
        keySearch += custCode;
        DataCustIndicatorThres mapDataCustIndicatorThres = (DataCustIndicatorThres) InitCache.read(keySearch);
        if(mapDataCustIndicatorThres != null){
            jsonObject = getThershold(jsonObject,mapDataCustIndicatorThres,indicatorResult);
            return jsonObject;
        }
        //map缓存中没有数据，则继续查数据库表
        DataCustIndicatorThres condDataCustIndicatorThres = new DataCustIndicatorThres();
        condDataCustIndicatorThres.setIndicatorId(indicatorId);
        condDataCustIndicatorThres.setCustCode(custCode);
        try {
            DataCustIndicatorThres resultDataCustIndicatorThres = getCustIndicatorThresByUnique(condDataCustIndicatorThres);
            if(resultDataCustIndicatorThres != null){
                InitCache.write(keySearch, resultDataCustIndicatorThres);
                jsonObject = getThershold(jsonObject,resultDataCustIndicatorThres,indicatorResult);
                return jsonObject;
            }
            else {
                logger.info("客户风险指标阈值表（CUST_INDICATOR_THRES）查无数据！");
                return null;
            }
        } catch (Exception e) {
            throw new Exception("获取客户风险指标阈值表（CUST_INDICATOR_THRES）阈值失败！", e);
        }
    }

    private static JSONObject getThershold(JSONObject jsonObject, DataCustIndicatorThres condDataCustIndicatorThres, long indicatorResult){
        String operForluma1 = condDataCustIndicatorThres.getThres1Oper();
        if(!operForluma1.equals("") && condDataCustIndicatorThres.getThres1Valid()=='1'){
            if(OperationRiskIndicators.getOperationResult(indicatorResult, operForluma1)){
                jsonObject.put("THRES_NO",condDataCustIndicatorThres.getThres1No());
                jsonObject.put("THRES_NAME",condDataCustIndicatorThres.getThres1Name());
                jsonObject.put("THRES_COLOR",condDataCustIndicatorThres.getThres1Color());
                jsonObject.put("THRES_CNT",condDataCustIndicatorThres.getThresCnt());
                return jsonObject;
            }
        }
        String operForluma2 = condDataCustIndicatorThres.getThres2Oper();
        if(!operForluma2.equals("") && condDataCustIndicatorThres.getThres2Valid()=='1'){
            if(OperationRiskIndicators.getOperationResult(indicatorResult, operForluma2)){
                jsonObject.put("THRES_NO",condDataCustIndicatorThres.getThres2No());
                jsonObject.put("THRES_NAME",condDataCustIndicatorThres.getThres2Name());
                jsonObject.put("THRES_COLOR",condDataCustIndicatorThres.getThres2Color());
                jsonObject.put("THRES_CNT",condDataCustIndicatorThres.getThresCnt());
                return jsonObject;
            }
        }
        String operForluma3 = condDataCustIndicatorThres.getThres3Oper();
        if(!operForluma3.equals("") && condDataCustIndicatorThres.getThres3Valid()=='1'){
            if(OperationRiskIndicators.getOperationResult(indicatorResult, operForluma3)){
                jsonObject.put("THRES_NO",condDataCustIndicatorThres.getThres3No());
                jsonObject.put("THRES_NAME",condDataCustIndicatorThres.getThres3Name());
                jsonObject.put("THRES_COLOR",condDataCustIndicatorThres.getThres3Color());
                jsonObject.put("THRES_CNT",condDataCustIndicatorThres.getThresCnt());
                return jsonObject;
            }
        }
        String operForluma4 = condDataCustIndicatorThres.getThres4Oper();
        if(!operForluma4.equals("") && condDataCustIndicatorThres.getThres4Valid()=='1'){
            if(OperationRiskIndicators.getOperationResult(indicatorResult, operForluma4)){
                jsonObject.put("THRES_NO",condDataCustIndicatorThres.getThres4No());
                jsonObject.put("THRES_NAME",condDataCustIndicatorThres.getThres4Name());
                jsonObject.put("THRES_COLOR",condDataCustIndicatorThres.getThres4Color());
                jsonObject.put("THRES_CNT",condDataCustIndicatorThres.getThresCnt());
                return jsonObject;
            }
        }
        return null;
    }

}