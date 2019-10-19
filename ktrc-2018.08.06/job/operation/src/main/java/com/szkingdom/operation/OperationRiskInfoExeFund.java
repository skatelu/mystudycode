/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationRiskInfoExeFund
 * Author:   ZhangMaohua
 * Date:     2018/7/24
 * Description: 行权资金类风险信息表操作
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.dao.DaoCustomer;
import com.szkingdom.dao.DaoRiskInfoExeFund;
import com.szkingdom.data.DataCuacctFund;
import com.szkingdom.data.DataCustomer;
import com.szkingdom.data.DataRiskInfoExeFund;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationRiskInfoExeFund {
    protected static Logger logger = LoggerFactory.getLogger(OperationRiskInfoExeFund.class);

    private static void updateRiskFields(JSONObject jsonObject, JSONObject jsonIndicatorInfo, DataRiskInfoExeFund condDataRiskInfoExeFund) throws Exception {
        try {
            // 组资金相关字段
            JSONObject jsonCuacctFund = jsonObject.getJSONObject(ConstantUtil.JSON_CUACCT_FUND);
            if (jsonCuacctFund != null) {
                // 数据已透传，可直接使用
                condDataRiskInfoExeFund.setFundBln(Long.parseLong(jsonCuacctFund.getString("FUND_BLN")));
                condDataRiskInfoExeFund.setFundAvl(Long.parseLong(jsonCuacctFund.getString("FUND_AVL")));
                condDataRiskInfoExeFund.setFundRet(Long.parseLong(jsonCuacctFund.getString("FUND_RET")));
                condDataRiskInfoExeFund.setFundExeMargin(Long.parseLong(jsonCuacctFund.getString("FUND_EXE_MARGIN")));
                condDataRiskInfoExeFund.setDlvyExeMargin(Long.parseLong(jsonCuacctFund.getString("FUND_FRZ")));
                condDataRiskInfoExeFund.setFundFeeFrz(Long.parseLong(jsonCuacctFund.getString("FUND_FEE_FRZ")));
                condDataRiskInfoExeFund.setIntOrg(Short.parseShort(jsonCuacctFund.getString("INT_ORG")));

            } else {
                // 未透传数据，查DB
                DataCuacctFund dataCuacctFund = new DataCuacctFund();
                dataCuacctFund.setCuacctCode(condDataRiskInfoExeFund.getCuacctCode());
                dataCuacctFund.setCurrency(condDataRiskInfoExeFund.getCurrency());

                dataCuacctFund = OperationCuacctFund.getCuacctFundDataByUnique(dataCuacctFund);
                if (dataCuacctFund != null) {
                    condDataRiskInfoExeFund.setFundBln(dataCuacctFund.getFundBln());
                    condDataRiskInfoExeFund.setFundAvl(dataCuacctFund.getFundAvl());
                    condDataRiskInfoExeFund.setFundRet(dataCuacctFund.getFundRet());
                    condDataRiskInfoExeFund.setFundExeMargin(dataCuacctFund.getFundExeMargin());
                    condDataRiskInfoExeFund.setDlvyExeMargin(dataCuacctFund.getFundFrz()); //
                    condDataRiskInfoExeFund.setFundFeeFrz(dataCuacctFund.getFundFeeFrz());
                    condDataRiskInfoExeFund.setIntOrg(dataCuacctFund.getIntOrg());
                }
            }

            /**
             * 设置字段FundNetDlvy、FundExeFrz、FundAvl、AppointedLackAmt、AppointedNeedAmt
             */
            long appointedLackAmt = 0L;
            long appointedNeedAmt = 0L;

            JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
            if (ConstantUtil.INDICATOR_EXE_FUND_RISK.equals(jsonIndicatorInfo.getString("indicatorId"))) {
                appointedNeedAmt = Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_CUST_SETT_AMT));
                condDataRiskInfoExeFund.setFundExeFrz(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_CUST_FUND_EXE_FRZ)));
                condDataRiskInfoExeFund.setFundAvl(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_CUST_FUND_AVL_OPT)));

                if (condDataRiskInfoExeFund.getFundRet() > 0) {
                    appointedLackAmt = condDataRiskInfoExeFund.getFundExeFrz() + condDataRiskInfoExeFund.getDlvyExeMargin()
                            + condDataRiskInfoExeFund.getFundExeMargin() + condDataRiskInfoExeFund.getFundAvl()
                            + condDataRiskInfoExeFund.getFundFeeFrz() - condDataRiskInfoExeFund.getFundRet()
                            - Math.abs(appointedNeedAmt);
                } else {
                    appointedLackAmt = condDataRiskInfoExeFund.getFundExeFrz() + condDataRiskInfoExeFund.getDlvyExeMargin()
                            + condDataRiskInfoExeFund.getFundExeMargin() + condDataRiskInfoExeFund.getFundAvl()
                            + condDataRiskInfoExeFund.getFundFeeFrz() - Math.abs(appointedNeedAmt);
                }

                if (appointedNeedAmt < 0) {
                    condDataRiskInfoExeFund.setAppointedNeedAmt(-appointedNeedAmt);
                } else {
                    condDataRiskInfoExeFund.setAppointedNeedAmt(0L);
                }
                if (appointedLackAmt < 0) {
                    condDataRiskInfoExeFund.setAppointedLackAmt(-appointedLackAmt);
                    condDataRiskInfoExeFund.setRiskValidFlag('1');
                } else {
                    condDataRiskInfoExeFund.setAppointedLackAmt(0L);
                }
            }
        } catch(Exception e){
            throw new Exception("更新表RISK_INFO_EXE_FUND 行权净收付资金异常 ", e);
        }
    }
        /**
         * insert时，对需要字段赋值
         * @param jsonData
         * @param jsonIndicatorInfo
         * @param condDataRiskInfoExeFund
         * @return
         * @throws Exception
         */
    private static DataRiskInfoExeFund setFieldsValues(JSONObject jsonObject, JSONObject jsonData, JSONObject jsonIndicatorInfo, DataRiskInfoExeFund condDataRiskInfoExeFund) throws Exception {
        /**
         * 测试
         */
        condDataRiskInfoExeFund.setCustType('0');
        condDataRiskInfoExeFund.setCustCls('0');
        //condDataRiskInfoExeFund.setCurrency('0');
        condDataRiskInfoExeFund.setRiskValidFlag('0');
        condDataRiskInfoExeFund.setNoticeSentLevel('0');

        // 根据指标结果类型，决定数据是否需要作为比例或金额放大相应倍数
        long indicatorResultLong = 0L;
        try {
            char indicatorResultType = jsonIndicatorInfo.getString("indicatorResultType").charAt(0);
            double resultDouble = Double.parseDouble(jsonIndicatorInfo.getString("indicatorResult"));
            indicatorResultLong = OperationRiskIndicators.magnifyByIndicatorResultType(resultDouble, indicatorResultType);
        } catch (NumberFormatException e) {
            logger.error("风险结果indicatorResult格式不对，可能计算公式中分母为0 ! ",e);
        }
        if (indicatorResultLong < 0) {
            condDataRiskInfoExeFund.setRiskValue(-indicatorResultLong);
        }

        /**
         * 从Json串中提取字段INDICATOR_NAME、RISK_VALUE 和设置 PRE_RISK_VALUE（3）
         */
        condDataRiskInfoExeFund.setCustCode(Long.parseLong(jsonData.getString("CUST_CODE")));
        condDataRiskInfoExeFund.setCustType(jsonData.getString("CUST_TYPE").charAt(0));
        condDataRiskInfoExeFund.setIndicatorName(jsonIndicatorInfo.getString("indicatorName"));
        condDataRiskInfoExeFund.setCuacctCode(Long.parseLong(jsonData.getString("CUACCT_CODE")));
        condDataRiskInfoExeFund.setDlvyDate(Integer.parseInt(jsonData.getString("DELIVERY_DATE")));

        /**
         * 设置日期时间字段OCC_DATE、OCC_TIME （2）
         */
        condDataRiskInfoExeFund.setOccDate(DateUtils.getCurrDate());
        condDataRiskInfoExeFund.setOccTime(DateUtils.getCurrTime());

        /**
         * 交收日期
         */
        condDataRiskInfoExeFund.setDlvyDate(DateUtils.getCurrDate());

        /**
         * 查CUSTOMER表，获取字段：CUST_NAME、CUST_TYPE、CUST_CLS、INT_ORG （4）
         */
        DataCustomer dataCustomer = new DataCustomer();
        dataCustomer.setCustCode(condDataRiskInfoExeFund.getCustCode());
        DaoCustomer daoCustomer = new DaoCustomer();
        try {
            dataCustomer = daoCustomer.selectUnique(dataCustomer);
            condDataRiskInfoExeFund.setCustName(dataCustomer.getCustName());
            condDataRiskInfoExeFund.setCustType(dataCustomer.getCustType());
            condDataRiskInfoExeFund.setCustCls(dataCustomer.getCustCls());
            condDataRiskInfoExeFund.setIntOrg(dataCustomer.getIntOrg());
        } catch(Exception e){
            e.printStackTrace();
            logger.error("OperationRiskInfoExeFund 查表CUSTOMER错误！ ");
        }

        //更新风险相关字段
        updateRiskFields(jsonObject, jsonIndicatorInfo ,condDataRiskInfoExeFund);

        //暂不来考虑现金结算
        condDataRiskInfoExeFund.setCashSettAmt(0L);

        return condDataRiskInfoExeFund;
    }

    /**
     * update时，对需要字段修改
     * @param jsonData
     * @param jsonIndicatorInfo
     * @param condDataRiskInfoExeFund
     * @return
     * @throws Exception
     */
    private static DataRiskInfoExeFund updateFieldsValues(JSONObject jsonObject, JSONObject jsonData, JSONObject jsonIndicatorInfo, DataRiskInfoExeFund condDataRiskInfoExeFund) throws Exception {

        /**
         * 设置日期时间字段OCC_DATE、OCC_TIME （2）
         */
        condDataRiskInfoExeFund.setOccDate(DateUtils.getCurrDate());
        condDataRiskInfoExeFund.setOccTime(DateUtils.getCurrTime());

        // 根据指标结果类型，决定数据是否需要作为比例或金额放大相应倍数
        long indicatorResultLong = 0L;
        try {
            char indicatorResultType = jsonIndicatorInfo.getString("indicatorResultType").charAt(0);
            double resultDouble = Double.parseDouble(jsonIndicatorInfo.getString("indicatorResult"));
            indicatorResultLong = OperationRiskIndicators.magnifyByIndicatorResultType(resultDouble, indicatorResultType);
        } catch (NumberFormatException e) {
              logger.error("风险结果indicatorResult格式不对，可能计算公式中分母为0 ! ",e);
        }
        if (indicatorResultLong < 0) {
            condDataRiskInfoExeFund.setRiskValue(-indicatorResultLong);
        }

        //更新风险相关字段
        updateRiskFields(jsonObject, jsonIndicatorInfo ,condDataRiskInfoExeFund);
        
        return condDataRiskInfoExeFund;
    }

    /**
     * 写入风险表，如该行存在则update，如不存在则insert
     * @param jsonObject  Json串
     */
    public static void writeRiskTables(JSONObject jsonObject) throws Exception{

        DataRiskInfoExeFund dataRiskInfoExeFundIndex = new DataRiskInfoExeFund();
        DaoRiskInfoExeFund daoRiskInfoExeFund = new DaoRiskInfoExeFund();
        /**
         * 从Json串中获取唯一索引字段值
         */
        JSONObject jsonData = (JSONObject) jsonObject.get(ConstantUtil.JSON_KTRC);
        dataRiskInfoExeFundIndex.setCuacctCode(Long.valueOf(jsonData.getString("CUACCT_CODE")));
        dataRiskInfoExeFundIndex.setCurrency(jsonData.getString("CURRENCY").charAt(0));

        JSONObject indicatorInfo = (JSONObject)jsonObject.get(ConstantUtil.JSON_INDICATORINFO);
        dataRiskInfoExeFundIndex.setIndicatorId(indicatorInfo.getString("indicatorId"));


        /**
         * 根据唯一索引查表，如该行存在update，如不存在则insert
         */
        try {
            DataRiskInfoExeFund condDataRiskInfoExeFund = daoRiskInfoExeFund.selectUnique(dataRiskInfoExeFundIndex);
            if(condDataRiskInfoExeFund != null){
                //更新字段
                DataRiskInfoExeFund resultDataRiskInfoExeFund = updateFieldsValues(jsonObject, jsonData, indicatorInfo, condDataRiskInfoExeFund);
                daoRiskInfoExeFund.update(resultDataRiskInfoExeFund);
            }
            else {
                //设置所有字段值，然后插入表中
                DataRiskInfoExeFund resultDataRiskInfoExeFund = setFieldsValues(jsonObject, jsonData, indicatorInfo, dataRiskInfoExeFundIndex);
                daoRiskInfoExeFund.insert(resultDataRiskInfoExeFund);
            }
        } catch (Exception e){
            logger.error("OperationRiskInfoExeFund 更新表 RISK_INFO_EXE_FUND 错误 ", e);
            throw e;
        }
    }
}
