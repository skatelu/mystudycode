/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationRiskInfoBondPledge
 * Author:   ZhangMaohua
 * Date:     2018/9/20
 * Description: 债券质押风险结果表
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.dao.DaoCustomer;
import com.szkingdom.dao.DaoRiskInfoBondPledge;
import com.szkingdom.data.DataCustomer;
import com.szkingdom.data.DataRiskInfoBondPledge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationRiskInfoBondPledge extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationRiskInfoBondPledge.class);

    private static void setThreshold(long custCode, String indicatorId, long indicatorResult, DataRiskInfoBondPledge condDataRiskInfoBondPledge) throws Exception {
        JSONObject jsonObject;
        //先查客户风险指标阈值表（CUST_INDICATOR_THRES）
        jsonObject = OperationCustIndicatorThres.getCustThresForRiskTable(custCode, indicatorId, indicatorResult);
        if (jsonObject != null) {
            condDataRiskInfoBondPledge.setThresNo(jsonObject.getString("THRES_NO").charAt(0));
            condDataRiskInfoBondPledge.setThresName(jsonObject.getString("THRES_NAME"));
            condDataRiskInfoBondPledge.setThresColor(jsonObject.getString("THRES_COLOR"));
        } else {
            //如果客户风险指标阈值表为空，查风险指标阈值表（INDICATOR_THRESHOLD）
            jsonObject = OperationIndicatorThreshold.getThresholdForRiskTable(indicatorId, indicatorResult);
            if (jsonObject != null) {
                condDataRiskInfoBondPledge.setThresNo(jsonObject.getString("THRES_NO").charAt(0));
                condDataRiskInfoBondPledge.setThresName(jsonObject.getString("THRES_NAME"));
                condDataRiskInfoBondPledge.setThresColor(jsonObject.getString("THRES_COLOR"));
            } else {
                logger.warn("客户风险指标阈值表CUST_INDICATOR_THRES/风险指标阈值表INDICATOR_THRESHOLD无合适数据!");
                condDataRiskInfoBondPledge.setThresNo('0');
                condDataRiskInfoBondPledge.setThresName("");
                condDataRiskInfoBondPledge.setThresColor("");
            }
        }
    }

    private static void setValuesDependsIndicators(JSONObject jsonIndicatorInfo, DataRiskInfoBondPledge condDataRiskInfoBondPledge) {
        String indicatorId = jsonIndicatorInfo.getString("indicatorId");
        JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");

        if (indicatorId.equals(ConstantUtil.INDICATOR_STD_BOND_USED_RATE)) {
            condDataRiskInfoBondPledge.setFiUndueAmt1(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_BOND_MATCH_AMT)));
            condDataRiskInfoBondPledge.setTotalBondStd(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_STD_BOND_AMT)));

        } else if (indicatorId.equals(ConstantUtil.INDICATOR_BOND_AMT_RATE)) {
            condDataRiskInfoBondPledge.setFiUndueAmt2(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_CUST_REPCH_AMT)));
            condDataRiskInfoBondPledge.setTrustBondAmt(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_CUST_BOND_AMT)));

        } else if (indicatorId.equals(ConstantUtil.INDICATOR_CUST_BOND_AMT_RATE)) {
            condDataRiskInfoBondPledge.setEntryBondQty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_CUST_STK_AVL)));
            condDataRiskInfoBondPledge.setStkexTrustBondQty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_BOND_TOTAL_TRUST)));

        } else if (indicatorId.equals(ConstantUtil.INDICATOR_BOND_OWE_AMT)) {
            condDataRiskInfoBondPledge.setFiUndueAmt1(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_BOND_MATCH_AMT)));
            condDataRiskInfoBondPledge.setTotalBondStd(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_STD_BOND_AMT)));

        } else if (indicatorId.equals(ConstantUtil.INDICATOR_ACCT_OWE_AMT)) {
            condDataRiskInfoBondPledge.setFundAvl(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_CUST_FUND_AVL_STK)));
            condDataRiskInfoBondPledge.setDueBuybkAmt(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_CUST_BUYBK_REPCH_AMT)));

        }
    }

    private static DataRiskInfoBondPledge setFieldsValues(JSONObject jsonData, JSONObject jsonIndicatorInfo, DataRiskInfoBondPledge condDataRiskInfoBondPledge) throws Exception {
        try {
            String indicatorId = jsonIndicatorInfo.getString("indicatorId");

            condDataRiskInfoBondPledge.setRiskValidFlag('0');
            condDataRiskInfoBondPledge.setNoticeSentLevel('0');
            /**
             * 从Json串中提取字段INDICATOR_NAME、CUACCT_CODE、CURRENCY、TRDACCT_EXCLS、OPT_UNDL_NAME、OPT_UNDL_CLS（6）
             */
            condDataRiskInfoBondPledge.setIndicatorName(jsonIndicatorInfo.getString("indicatorName"));
            condDataRiskInfoBondPledge.setCuacctCode(Long.parseLong(jsonData.getString("CUACCT_CODE")));
            condDataRiskInfoBondPledge.setCurrency(jsonData.getString("CURRENCY").charAt(0));
            condDataRiskInfoBondPledge.setStkex(jsonData.getString("STKBD").charAt(0));

            /**
             * 更新风险率字段RISK_VALUE、阈值字段: THRESHOLD、THRES_NO、THRES_NAME、THRES_COLOR （5）
             */
            // 根据指标结果类型，决定数据是否需要作为比例或金额放大相应倍数
            long indicatorResultLong = 0L;
            try {
                char indicatorResultType = jsonIndicatorInfo.getString("indicatorResultType").charAt(0);
                double resultDouble = Double.parseDouble(jsonIndicatorInfo.getString("indicatorResult"));
                indicatorResultLong = OperationRiskIndicators.magnifyByIndicatorResultType(resultDouble, indicatorResultType);
            } catch (NumberFormatException e) {
                logger.error("风险结果indicatorResult格式不对，可能计算公式中分母为0 ! ", e);
            }
            condDataRiskInfoBondPledge.setRiskValue(indicatorResultLong);

            setThreshold(Long.parseLong(jsonData.getString("CUST_CODE")),indicatorId,indicatorResultLong,condDataRiskInfoBondPledge);

            /**
             * 查CUSTOMER表，获取字段：CUST_NAME、INT_ORG （2）
             */
            DataCustomer dataCustomer = new DataCustomer();
            dataCustomer.setCustCode(condDataRiskInfoBondPledge.getCustCode());
            DaoCustomer daoCustomer = new DaoCustomer();
            try {
                dataCustomer = daoCustomer.selectUnique(dataCustomer);
                if (dataCustomer != null) {
                    condDataRiskInfoBondPledge.setCustName(dataCustomer.getCustName());
                    condDataRiskInfoBondPledge.setIntOrg(dataCustomer.getIntOrg());
                }
            } catch(Exception e){
                logger.error(" 查表CUSTOMER错误！ ");
            }

            /**
             * 设置日期时间字段OCC_DATE、OCC_TIME （2）
             */
            condDataRiskInfoBondPledge.setOccDate(DateUtils.getCurrDate());
            condDataRiskInfoBondPledge.setOccTime(DateUtils.getCurrTime());

            /**
             * 设置字段PRE_RISK_VALUE、PRE_THRESHOLD、PRE_THRES_NO、PRE_THRES_NAME、PRE_THRES_COLOR (5)
             */
            condDataRiskInfoBondPledge.setPreRiskValue(0L);
            condDataRiskInfoBondPledge.setPreThreshold(0L);
            condDataRiskInfoBondPledge.setPreThresColor("");
            condDataRiskInfoBondPledge.setPreThresName("");
            condDataRiskInfoBondPledge.setPreThresNo('0');

            setValuesDependsIndicators(jsonIndicatorInfo, condDataRiskInfoBondPledge);

            return condDataRiskInfoBondPledge;
        } catch (Exception e) {
            throw new Exception("插入表 RISK_INFO_BOND_PLEDGE 时，数据错误", e);
        }
    }

    private static DataRiskInfoBondPledge updateFieldsValues(JSONObject jsonData, JSONObject jsonIndicatorInfo, DataRiskInfoBondPledge condDataRiskInfoBondPledge) throws Exception {
        try {
            String indicatorId = jsonIndicatorInfo.getString("indicatorId");
            /**
             * 更新风险率字段RISK_VALUE、阈值字段: THRESHOLD、THRES_NO、THRES_NAME、THRES_COLOR （5）
             */
            // 根据指标结果类型，决定数据是否需要作为比例或金额放大相应倍数
            long indicatorResultLong = 0L;
            try {
                char indicatorResultType = jsonIndicatorInfo.getString("indicatorResultType").charAt(0);
                double resultDouble = Double.parseDouble(jsonIndicatorInfo.getString("indicatorResult"));
                indicatorResultLong = OperationRiskIndicators.magnifyByIndicatorResultType(resultDouble, indicatorResultType);
            } catch (NumberFormatException e) {
                logger.error("风险结果indicatorResult格式不对，可能计算公式中分母为0 ! ", e);
            }
            condDataRiskInfoBondPledge.setRiskValue(indicatorResultLong);

            setThreshold(Long.parseLong(jsonData.getString("CUST_CODE")),indicatorId,indicatorResultLong,condDataRiskInfoBondPledge);

            /**
             * 设置日期时间字段OCC_DATE、OCC_TIME （2）
             */
            condDataRiskInfoBondPledge.setOccDate(DateUtils.getCurrDate());
            condDataRiskInfoBondPledge.setOccTime(DateUtils.getCurrTime());

            setValuesDependsIndicators(jsonIndicatorInfo, condDataRiskInfoBondPledge);

            return condDataRiskInfoBondPledge;
        } catch (Exception e) {
            throw new Exception("更新表 RISK_INFO_BOND_PLEDGE 时，数据错误", e);
        }
    }

    public static void writeRiskTables(JSONObject jsonObject) throws Exception {
        DataRiskInfoBondPledge dataRiskInfoBondPledgeIndex = new DataRiskInfoBondPledge();
        DaoRiskInfoBondPledge daoRiskInfoBondPledge = new DaoRiskInfoBondPledge();
        /**
         * 从Json串中获取唯一索引字段值
         */
        JSONObject jsonData = (JSONObject) jsonObject.get(ConstantUtil.JSON_KTRC);
        dataRiskInfoBondPledgeIndex.setStkbd(jsonData.getString("STKBD"));
        dataRiskInfoBondPledgeIndex.setTrdacct(jsonData.getString("TRDACCT"));

        JSONObject jsonIndicatorInfo = (JSONObject)jsonObject.get(ConstantUtil.JSON_INDICATORINFO);
        String indicatorId = jsonIndicatorInfo.getString("indicatorId");
        dataRiskInfoBondPledgeIndex.setIndicatorId(indicatorId);

        if(!indicatorId.equals(ConstantUtil.INDICATOR_STD_BOND_USED_RATE) || !indicatorId.equals(ConstantUtil.INDICATOR_BOND_OWE_AMT)){
            dataRiskInfoBondPledgeIndex.setStkCode(jsonData.getString("STK_CODE"));
            dataRiskInfoBondPledgeIndex.setCustCode(Long.parseLong(jsonData.getString("CUST_CODE")));
        }

        /**
         * 根据唯一索引查表，如该行存在update，如不存在则insert
         */
        try {
            DataRiskInfoBondPledge condDataRiskInfoBondPledge = daoRiskInfoBondPledge.selectUnique(dataRiskInfoBondPledgeIndex);
            if (condDataRiskInfoBondPledge != null) {
                DataRiskInfoBondPledge resultDataRiskInfoBondPledge  = updateFieldsValues(jsonData, jsonIndicatorInfo, condDataRiskInfoBondPledge);
                daoRiskInfoBondPledge.update(resultDataRiskInfoBondPledge);
            } else {
                DataRiskInfoBondPledge resultDataRiskInfoBondPledge = setFieldsValues(jsonData, jsonIndicatorInfo, dataRiskInfoBondPledgeIndex);
                daoRiskInfoBondPledge.insert(resultDataRiskInfoBondPledge);
            }
        }catch (Exception e){
            throw new Exception("writeRiskTables 更新表 RISK_INFO_BOND_PLEDGE 错误 ", e);
        }

    }


}
