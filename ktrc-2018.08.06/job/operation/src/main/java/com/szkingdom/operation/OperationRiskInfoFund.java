/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationRiskInfoFund
 * Author:   ZhangMaohua
 * Date:     2018/7/24
 * Description: 资金类风险表操作
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.common.InitCache;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.dao.DaoRiskInfoFund;
import com.szkingdom.data.DataCuacctFund;
import com.szkingdom.data.DataCustomer;
import com.szkingdom.data.DataIndicatorIdsCfg;
import com.szkingdom.data.DataRiskInfoFund;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationRiskInfoFund {
    protected static Logger logger = LoggerFactory.getLogger(OperationRiskInfoFund.class);

    /**
     * 设置阈值字段
     * @param custCode
     * @param indicatorId
     * @param indicatorResult
     * @param condDataRiskInfoFund
     * @return
     * @throws Exception
     */
    private static DataRiskInfoFund setThreshold(long custCode, String indicatorId, long indicatorResult, DataRiskInfoFund condDataRiskInfoFund) throws Exception {
        //先查客户风险指标阈值表（CUST_INDICATOR_THRES）
        JSONObject jsonObject = OperationCustIndicatorThres.getCustThresForRiskTable(custCode, indicatorId, indicatorResult);
        if(jsonObject != null) {
            condDataRiskInfoFund.setThresNo(jsonObject.getString("THRES_NO").charAt(0));
            condDataRiskInfoFund.setThresName(jsonObject.getString("THRES_NAME"));
            condDataRiskInfoFund.setThresColor(jsonObject.getString("THRES_COLOR"));
        }
        else {
            //如果客户风险指标阈值表为空，查风险指标阈值表（INDICATOR_THRESHOLD）
            jsonObject = OperationIndicatorThreshold.getThresholdForRiskTable(indicatorId, indicatorResult);
            if(jsonObject != null) {
                condDataRiskInfoFund.setThresNo(jsonObject.getString("THRES_NO").charAt(0));
                condDataRiskInfoFund.setThresName(jsonObject.getString("THRES_NAME"));
                condDataRiskInfoFund.setThresColor(jsonObject.getString("THRES_COLOR"));
            }
            else {
                logger.error("客户风险指标阈值表CUST_INDICATOR_THRES/风险指标阈值表INDICATOR_THRESHOLD无合适数据!");
                condDataRiskInfoFund.setThresNo('0');
                condDataRiskInfoFund.setThresName("");
                condDataRiskInfoFund.setThresColor("");
            }
        }

        // 设置默认值
        condDataRiskInfoFund.setRiskValidFlag('0');

        if (condDataRiskInfoFund.getThresNo() != '0') {
            // 设置风险状态
            Character thresCnt = jsonObject.getString("THRES_CNT").charAt(0);
            DataIndicatorIdsCfg dataIndicatorIdsCfg = new DataIndicatorIdsCfg();
            dataIndicatorIdsCfg.setIndicatorId(indicatorId);

            List<DataIndicatorIdsCfg> resListDataIndicatorIdsCfg =
                    OperationIndicatorIdsCfg.listIndicatorIdsCfgDataByParam(dataIndicatorIdsCfg);

            if (resListDataIndicatorIdsCfg != null && !resListDataIndicatorIdsCfg.isEmpty()) {
                condDataRiskInfoFund.setRiskValidFlag('1');

                for (DataIndicatorIdsCfg resultDataIndicatorIdsCfg : resListDataIndicatorIdsCfg) {
                    if (resultDataIndicatorIdsCfg.getIndicatorIdType() == '0' && resListDataIndicatorIdsCfg.size() > 1) {
                        // 优先客户级阈值
                        continue;
                    } else {
                        if (resultDataIndicatorIdsCfg.getThresDirect() == '0'
                                && resultDataIndicatorIdsCfg.getRiskTrend() == '0') {
                            // 从小到大，越小越高
                            if (condDataRiskInfoFund.getThresNo().equals(thresCnt)) {
                                condDataRiskInfoFund.setRiskValidFlag('0');
                            }
                        } else if (resultDataIndicatorIdsCfg.getThresDirect() == '0'
                                && resultDataIndicatorIdsCfg.getRiskTrend() == '1') {
                            // 从小到大，越小越低
                            if (condDataRiskInfoFund.getThresNo() < '2') {
                                condDataRiskInfoFund.setRiskValidFlag('0');
                            }
                        } else if (resultDataIndicatorIdsCfg.getThresDirect() == '1'
                                && resultDataIndicatorIdsCfg.getRiskTrend() == '0') {
                            // 从大到小，越小越高
                            if (condDataRiskInfoFund.getThresNo() < '2') {
                                condDataRiskInfoFund.setRiskValidFlag('0');
                            }
                        } else if (resultDataIndicatorIdsCfg.getThresDirect() == '1'
                                && resultDataIndicatorIdsCfg.getRiskTrend() == '1') {
                            // 阈值越小，风险越高
                            if (condDataRiskInfoFund.getThresNo().equals(thresCnt)) {
                                condDataRiskInfoFund.setRiskValidFlag('0');
                            }
                        } else {
                            logger.error("指标配置无匹配项,默认无风险");
                            condDataRiskInfoFund.setRiskValidFlag('0');
                        }
                    }
                }

                resListDataIndicatorIdsCfg.clear();
            }
        }

        return condDataRiskInfoFund;
    }

    /**
     * insert时，对需要字段赋值
     * @param jsonObject         Json串
     * @param condDataRiskInfoFund 唯一索引
     * @return condDataRiskInfoFund
     */
    private static DataRiskInfoFund setFieldsValues(JSONObject jsonData, JSONObject jsonObject, DataRiskInfoFund condDataRiskInfoFund) throws Exception {
        try {
            // 设置指标标识，指标名称
            JSONObject jsonIndicatorInfo = jsonObject.getJSONObject(ConstantUtil.JSON_INDICATORINFO);
            String indicatorId = jsonIndicatorInfo.getString("indicatorId");
            condDataRiskInfoFund.setIndicatorId(indicatorId);
            condDataRiskInfoFund.setIndicatorName(jsonIndicatorInfo.getString("indicatorName"));

            // 设置日期时间
            condDataRiskInfoFund.setOccDate(DateUtils.getCurrDate());
            condDataRiskInfoFund.setOccTime(DateUtils.getCurrTime());

            // 昨日阈值使用默认值
            condDataRiskInfoFund.setPreThresNo('0');

            // 根据指标结果类型，决定数据是否需要作为比例或金额放大相应倍数
            char indicatorResultType = jsonIndicatorInfo.getString("indicatorResultType").charAt(0);
            double resultDouble = Double.parseDouble(jsonIndicatorInfo.getString("indicatorResult"));
            long indicatorResultLong = OperationRiskIndicators.magnifyByIndicatorResultType(resultDouble, indicatorResultType);
            condDataRiskInfoFund.setCustCode(Long.parseLong(jsonData.getString("CUST_CODE")));

            // 设置阈值字段：THRESHOLD、THRES_NO、THRES_NAME、THRES_COLOR
            setThreshold(condDataRiskInfoFund.getCustCode(), indicatorId, indicatorResultLong, condDataRiskInfoFund);

            // RISK_VALUE,RISK_VALUE_RLT,STKEX_RISK_VALUE,STKEX_RISK_VALUE_RLT根据因子编号更新字段，逻辑未加
            condDataRiskInfoFund.setRiskValueRlt(indicatorResultLong);

            JSONObject jsonFactorsValue = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
            condDataRiskInfoFund.setMarginUsedRlt(NumConvertUtil.moneyToLong(Double.parseDouble(jsonFactorsValue.getString(ConstantUtil.FACTOR_MARGIN_USED_RLT))));

            // 组资金相关字段
            JSONObject jsonCuacctFund = jsonObject.getJSONObject(ConstantUtil.JSON_CUACCT_FUND);
            if (jsonCuacctFund != null) {
                // 数据已透传，可直接使用
                condDataRiskInfoFund.setFundBln(Long.parseLong(jsonCuacctFund.getString("FUND_BLN")));
                condDataRiskInfoFund.setFundAvl(Long.parseLong(jsonCuacctFund.getString("FUND_AVL")));
                condDataRiskInfoFund.setFundRet(Long.parseLong(jsonCuacctFund.getString("FUND_RET")));
                condDataRiskInfoFund.setMarginUsed(Long.parseLong(jsonCuacctFund.getString("MARGIN_USED")));
                condDataRiskInfoFund.setDailyInAmt(Long.parseLong(jsonCuacctFund.getString("DAILY_IN_AMT")));
                condDataRiskInfoFund.setDailyOutAmt(Long.parseLong(jsonCuacctFund.getString("DAILY_OUT_AMT")));
                condDataRiskInfoFund.setIntOrg(Short.parseShort(jsonCuacctFund.getString("INT_ORG")));

            } else {
                // 未透传数据，查DB
                DataCuacctFund dataCuacctFund = new DataCuacctFund();
                dataCuacctFund.setCuacctCode(condDataRiskInfoFund.getCuacctCode());
                dataCuacctFund.setCurrency(condDataRiskInfoFund.getCurrency());

                dataCuacctFund = OperationCuacctFund.getCuacctFundDataByUnique(dataCuacctFund);
                if (dataCuacctFund != null) {
                    condDataRiskInfoFund.setFundBln(dataCuacctFund.getFundBln());
                    condDataRiskInfoFund.setFundAvl(dataCuacctFund.getFundAvl());
                    condDataRiskInfoFund.setFundRet(dataCuacctFund.getFundRet());
                    condDataRiskInfoFund.setMarginUsed(dataCuacctFund.getMarginUsed());
                    condDataRiskInfoFund.setDailyInAmt(dataCuacctFund.getDailyInAmt());
                    condDataRiskInfoFund.setDailyOutAmt(dataCuacctFund.getDailyOutAmt());
                    condDataRiskInfoFund.setIntOrg(dataCuacctFund.getIntOrg());
                }
            }

            // 组客户相关字段
            JSONObject jsonOptOrder = jsonObject.getJSONObject(ConstantUtil.JSON_OPT_ORDER);
            if (jsonOptOrder != null) {
                condDataRiskInfoFund.setCustName(jsonOptOrder.getString("CUST_NAME"));
                condDataRiskInfoFund.setCustType(jsonOptOrder.getString("CUST_TYPE").charAt(0));
            } else {
                DataCustomer dataCustomer = new DataCustomer();
                dataCustomer.setCustCode(condDataRiskInfoFund.getCustCode());

                dataCustomer = OperationCustomer.getCustomerDataByUnique(dataCustomer);
                if (dataCustomer != null) {
                    condDataRiskInfoFund.setCustName(dataCustomer.getCustName());
                    condDataRiskInfoFund.setCustType(dataCustomer.getCustType());
                }
            }

            // 临时语句，防止subacctCode为空导致赋值失败
            condDataRiskInfoFund.setCustType('0');
            //condDataRiskInfoFund.setRiskValidFlag('0');
            condDataRiskInfoFund.setNoticeSentLevel('0');
            condDataRiskInfoFund.setMarginCls('0');

            return condDataRiskInfoFund;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * update时，对需要字段修改
     * @param condDataRiskInfoFund 修改部分字段后返回
     * @return condDataRiskInfoFund
     */
    private static DataRiskInfoFund updateFieldsValues(JSONObject jsonData, JSONObject jsonObject,
                                                       DataRiskInfoFund condDataRiskInfoFund) throws Exception {
        try {
            JSONObject jsonIndicatorInfo = jsonObject.getJSONObject(ConstantUtil.JSON_INDICATORINFO);
            String indicatorId = jsonIndicatorInfo.getString("indicatorId");

            // 设置日期时间
            condDataRiskInfoFund.setOccDate(DateUtils.getCurrDate());
            condDataRiskInfoFund.setOccTime(DateUtils.getCurrTime());


            // 根据指标结果类型，决定数据是否需要作为比例或金额放大相应倍数
            char indicatorResultType = jsonIndicatorInfo.getString("indicatorResultType").charAt(0);
            double resultDouble = Double.parseDouble(jsonIndicatorInfo.getString("indicatorResult"));
            long indicatorResultLong = OperationRiskIndicators.magnifyByIndicatorResultType(resultDouble, indicatorResultType);
            condDataRiskInfoFund.setCustCode(Long.parseLong(jsonData.getString("CUST_CODE")));

            // 更新风险率字段RISK_VALUE、阈值字段：THRESHOLD、THRES_NO、THRES_NAME、THRES_COLOR
            setThreshold(condDataRiskInfoFund.getCustCode(), indicatorId, indicatorResultLong, condDataRiskInfoFund);

            // RISK_VALUE,RISK_VALUE_RLT,STKEX_RISK_VALUE,STKEX_RISK_VALUE_RLT根据因子编号更新字段，逻辑未加
            condDataRiskInfoFund.setRiskValueRlt(indicatorResultLong);

            JSONObject jsonFactorsValue = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
            condDataRiskInfoFund.setMarginUsedRlt(NumConvertUtil.moneyToLong(Double.parseDouble(jsonFactorsValue.getString(ConstantUtil.FACTOR_MARGIN_USED_RLT))));

            // 组资金相关字段
            JSONObject jsonCuacctFund = jsonObject.getJSONObject(ConstantUtil.JSON_CUACCT_FUND);
            if (jsonCuacctFund != null) {
                // 数据已透传，可直接使用
                condDataRiskInfoFund.setFundBln(Long.parseLong(jsonCuacctFund.getString("FUND_BLN")));
                condDataRiskInfoFund.setFundAvl(Long.parseLong(jsonCuacctFund.getString("FUND_AVL")));
                condDataRiskInfoFund.setFundRet(Long.parseLong(jsonCuacctFund.getString("FUND_RET")));
                condDataRiskInfoFund.setMarginUsed(Long.parseLong(jsonCuacctFund.getString("MARGIN_USED")));
                condDataRiskInfoFund.setDailyInAmt(Long.parseLong(jsonCuacctFund.getString("DAILY_IN_AMT")));
                condDataRiskInfoFund.setDailyOutAmt(Long.parseLong(jsonCuacctFund.getString("DAILY_OUT_AMT")));

            } else {
                // 未透传数据，查DB
                DataCuacctFund dataCuacctFund = new DataCuacctFund();
                dataCuacctFund.setCuacctCode(condDataRiskInfoFund.getCuacctCode());
                dataCuacctFund.setCurrency(condDataRiskInfoFund.getCurrency());

                dataCuacctFund = OperationCuacctFund.getCuacctFundDataByUnique(dataCuacctFund);
                if (dataCuacctFund != null) {
                    condDataRiskInfoFund.setFundBln(dataCuacctFund.getFundBln());
                    condDataRiskInfoFund.setFundAvl(dataCuacctFund.getFundAvl());
                    condDataRiskInfoFund.setFundRet(dataCuacctFund.getFundRet());
                    condDataRiskInfoFund.setMarginUsed(dataCuacctFund.getMarginUsed());
                    condDataRiskInfoFund.setDailyInAmt(dataCuacctFund.getDailyInAmt());
                    condDataRiskInfoFund.setDailyOutAmt(dataCuacctFund.getDailyOutAmt());
                }
            }

            // 临时语句，防止subacctCode为空导致赋值失败
            //condDataRiskInfoFund.setRiskValidFlag('0');
            condDataRiskInfoFund.setNoticeSentLevel('0');
            condDataRiskInfoFund.setMarginCls('0');

            return condDataRiskInfoFund;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 写入风险表，如该行存在则update，如不存在则insert
     * @param jsonObject  Json串
     */
    public static void writeRiskTables(JSONObject jsonObject) throws Exception{
        DataRiskInfoFund dataRiskInfoFundIndex = new DataRiskInfoFund();
        DaoRiskInfoFund daoRiskInfoFund = new DaoRiskInfoFund();
        DataRiskInfoFund resultDataRiskInfoFund;
        JSONObject jsonData = jsonObject.getJSONObject(ConstantUtil.JSON_KTRC);

        // RISK_INFO_FUND 唯一索引
        dataRiskInfoFundIndex.setCuacctCode(Long.parseLong(jsonData.getString("CUACCT_CODE")));
        dataRiskInfoFundIndex.setCurrency(jsonData.getString("CURRENCY").charAt(0));

        // 根据唯一索引查表，如该行存在update，如不存在则insert
        try{
            DataRiskInfoFund condDataRiskInfoFund = daoRiskInfoFund.selectUnique(dataRiskInfoFundIndex);
            if(condDataRiskInfoFund != null){
                resultDataRiskInfoFund = updateFieldsValues(jsonData, jsonObject, condDataRiskInfoFund);
                daoRiskInfoFund.update(resultDataRiskInfoFund);
            }
            else{
                resultDataRiskInfoFund = setFieldsValues(jsonData, jsonObject, dataRiskInfoFundIndex);
                daoRiskInfoFund.insert(resultDataRiskInfoFund);
            }
        } catch (Exception e) {
            throw new Exception("表 RISK_INFO_FUND 操作错误", e);
        }
    }

}
