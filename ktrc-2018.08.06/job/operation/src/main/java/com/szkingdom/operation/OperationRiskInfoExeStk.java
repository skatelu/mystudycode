/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationRiskInfoExeStk
 * Author:   ZhangMaohua
 * Date:     2018/7/24
 * Description: 行权证券类风险信息表操作
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.dao.DaoCustomer;
import com.szkingdom.dao.DaoRiskInfoExeStk;
import com.szkingdom.data.DataCustomer;
import com.szkingdom.data.DataRiskInfoExeStk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class OperationRiskInfoExeStk {
    protected static Logger logger = LoggerFactory.getLogger(OperationRiskInfoExeStk.class);

    private static void updateRiskFields(JSONObject jsonIndicatorInfo, DataRiskInfoExeStk condDataRiskInfoExeStk) throws Exception {
        /**
         * 上海市场的股票、etf，深圳市场的股票采用E+1日交收   深圳市场的ETF采用E+2日交收
         * 处理上海市场和深圳市场（A股 + ETF）在E+1日的缺口
         * 设置字段StkQtyAct、StkNetDlvyQty、StkQtyExelack1、StkQtyExelack2
         */
        long stkQtyExelack = 0L;
        try {
            JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
            if (ConstantUtil.INDICATOR_EXE_STK_RISK1.equals(jsonIndicatorInfo.getString("indicatorId"))) {
                condDataRiskInfoExeStk.setStkQtyAct(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_EXE_STK_BLN_CVD)));
                condDataRiskInfoExeStk.setStkNetDlvyQty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_EXE_NEED_STK)));
                stkQtyExelack = condDataRiskInfoExeStk.getStkQtyAct() - Math.abs(condDataRiskInfoExeStk.getStkNetDlvyQty());
                if (stkQtyExelack < 0 && condDataRiskInfoExeStk.getStkNetDlvyQty() < 0) {
                    condDataRiskInfoExeStk.setStkQtyExelack1(-stkQtyExelack);
                    condDataRiskInfoExeStk.setRiskValidFlag('1');
                } else {
                    condDataRiskInfoExeStk.setStkQtyExelack1(0L);
                }
            } else if (ConstantUtil.INDICATOR_EXE_STK_RISK2.equals(jsonIndicatorInfo.getString("indicatorId"))) {
                condDataRiskInfoExeStk.setStkQtyAct(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_EXE_STK_BLN)));
                condDataRiskInfoExeStk.setStkNetDlvyQty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_EXE_NEED_STK)));
                stkQtyExelack = condDataRiskInfoExeStk.getStkQtyAct() - Math.abs(condDataRiskInfoExeStk.getStkNetDlvyQty());
                if (stkQtyExelack < 0 && condDataRiskInfoExeStk.getStkNetDlvyQty() < 0) {
                    condDataRiskInfoExeStk.setStkQtyExelack2(-stkQtyExelack);
                    condDataRiskInfoExeStk.setRiskValidFlag('1');
                } else {
                    condDataRiskInfoExeStk.setStkQtyExelack2(0L);
                }
            }
        } catch(Exception e){
            throw new Exception("更新表RISK_INFO_EXE_STK 行权净收付数量异常 ", e);
        }
    }
    /**
     * insert时，对需要字段赋值
     * @param jsonIndicatorInfo
     * @param condDataRiskInfoExeStk 唯一索引
     * @return condDataRiskInfoExeStk
     */
    private static DataRiskInfoExeStk setFieldsValues(JSONObject jsonData, JSONObject jsonIndicatorInfo, DataRiskInfoExeStk condDataRiskInfoExeStk) throws Exception {
        /**
         * 测试
         */
        condDataRiskInfoExeStk.setCustType('0');
        condDataRiskInfoExeStk.setCustCls('0');
        condDataRiskInfoExeStk.setRiskValidFlag('0');
        condDataRiskInfoExeStk.setNoticeSentLevel('0');

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
            condDataRiskInfoExeStk.setRiskValue(-indicatorResultLong);
        }

        /**
         * 从Json串中提取字段
         */
        condDataRiskInfoExeStk.setIndicatorName(jsonIndicatorInfo.getString("indicatorName"));
        condDataRiskInfoExeStk.setCustType(jsonData.getString("CUST_TYPE").charAt(0));
        condDataRiskInfoExeStk.setTrdacctExcls(jsonData.getString("TRDACCT_EXCLS").charAt(0));
        condDataRiskInfoExeStk.setCurrency(jsonData.getString("CURRENCY").charAt(0));
        condDataRiskInfoExeStk.setOptUndlCls(jsonData.getString("OPT_UNDL_CLS").charAt(0));
        condDataRiskInfoExeStk.setCuacctCode(Long.parseLong(jsonData.getString("CUACCT_CODE")));
        condDataRiskInfoExeStk.setDlvyDate(Integer.parseInt(jsonData.getString("DELIVERY_DATE")));
        condDataRiskInfoExeStk.setOptUndlName(jsonData.getString("OPT_UNDL_NAME"));
        /**
         * 设置日期时间字段OCC_DATE、OCC_TIME （2）
         */
        condDataRiskInfoExeStk.setOccDate(DateUtils.getCurrDate());
        condDataRiskInfoExeStk.setOccTime(DateUtils.getCurrTime());

        /**
         * 查CUSTOMER表，获取字段：CUST_NAME、CUST_TYPE、CUST_CLS、INT_ORG （4）
         */
        DataCustomer dataCustomer = new DataCustomer();
        dataCustomer.setCustCode(condDataRiskInfoExeStk.getCustCode());
        DaoCustomer daoCustomer = new DaoCustomer();
        try {
            dataCustomer = daoCustomer.selectUnique(dataCustomer);
            condDataRiskInfoExeStk.setCustName(dataCustomer.getCustName());
            condDataRiskInfoExeStk.setCustType(dataCustomer.getCustType());
            condDataRiskInfoExeStk.setCustCls(dataCustomer.getCustCls());
            condDataRiskInfoExeStk.setIntOrg(dataCustomer.getIntOrg());
        } catch(Exception e){
            e.printStackTrace();
            logger.error("OperationRiskInfoExeStk 查表CUSTOMER错误！ ");
        }

        updateRiskFields(jsonIndicatorInfo, condDataRiskInfoExeStk);

        return condDataRiskInfoExeStk;
    }

    /**
     * update时，对需要字段修改
     * @param condDataRiskInfoExeStk 修改部分字段后返回
     * @return condDataRiskInfoExeStk
     */
    private static DataRiskInfoExeStk updateFieldsValues(JSONObject jsonData, JSONObject jsonIndicatorInfo, DataRiskInfoExeStk condDataRiskInfoExeStk) throws Exception {

        /**
         * 设置日期时间字段OCC_DATE、OCC_TIME （2）
         */
        condDataRiskInfoExeStk.setOccDate(DateUtils.getCurrDate());
        condDataRiskInfoExeStk.setOccTime(DateUtils.getCurrTime());

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
            condDataRiskInfoExeStk.setRiskValue(-indicatorResultLong);
        }

        int dlvyDate = Integer.parseInt(jsonData.getString("DELIVERY_DATE"));
        String stkbd = jsonData.getString("STKBD");
        char optUndlCls = jsonData.getString("OPT_UNDL_CLS").charAt(0);
        /**
         * 设置字段StkQtyAct、StkNetDlvyQty、StkQtyExelack1
         * 深圳ETF在e+2日，行权标的券缺口数据在e+1日交易结束后，就固定不变的，需保存深圳ETF在E+1交易结束后的缺口数据
         * 上海市场和深圳A股在E+2日不监控，字段值清0
         */
        if (((dlvyDate == DateUtils.getCurrDate()) && ("05".equals(stkbd)) && (optUndlCls=='D'))) {
            //深圳ETF在e+2日，字段值保持昨日交易结束，不需要操作

        } else if (dlvyDate == (DateUtils.getCurrDate() - 1)) {
            condDataRiskInfoExeStk.setStkQtyAct(0L);
            condDataRiskInfoExeStk.setStkNetDlvyQty(0L);
            condDataRiskInfoExeStk.setStkQtyExelack1(0L);
            condDataRiskInfoExeStk.setRiskValidFlag('0');
        } else {
            updateRiskFields(jsonIndicatorInfo, condDataRiskInfoExeStk);
        }

        return condDataRiskInfoExeStk;
    }

    /**
     * 写入风险表，如该行存在则update，如不存在则insert
     * @param jsonObject  Json串
     */
    public static void writeRiskTables(JSONObject jsonObject) throws Exception{
        DataRiskInfoExeStk dataRiskInfoExeStkIndex = new DataRiskInfoExeStk();
        DaoRiskInfoExeStk daoRiskInfoExeStk = new DaoRiskInfoExeStk();
        /**
         * 从Json串中获取唯一索引字段值
         */
        JSONObject jsonData = (JSONObject) jsonObject.get(ConstantUtil.JSON_KTRC);
        dataRiskInfoExeStkIndex.setOptUndlCode(jsonData.getString("OPT_UNDL_CODE"));
        dataRiskInfoExeStkIndex.setCustCode(Long.valueOf(jsonData.getString("CUST_CODE")));
        dataRiskInfoExeStkIndex.setStkbd(jsonData.getString("STKBD"));
        dataRiskInfoExeStkIndex.setTrdacct(jsonData.getString("TRDACCT"));
        dataRiskInfoExeStkIndex.setSubacctCode(jsonData.getString("SUBACCT_CODE"));

        JSONObject indicatorInfo = (JSONObject)jsonObject.get(ConstantUtil.JSON_INDICATORINFO);
        dataRiskInfoExeStkIndex.setIndicatorId(indicatorInfo.getString("indicatorId"));

        /**
         * 根据唯一索引查表，如该行存在update，如不存在则insert
         */
        try{
            DataRiskInfoExeStk condDataRiskInfoExeStk = daoRiskInfoExeStk.selectUnique(dataRiskInfoExeStkIndex);
            if(condDataRiskInfoExeStk != null){
                //更新部分数据
                DataRiskInfoExeStk resultDataRiskInfoExeStk = updateFieldsValues(jsonData, indicatorInfo, condDataRiskInfoExeStk);
                daoRiskInfoExeStk.update(resultDataRiskInfoExeStk);
            }
            else {
                //设置所有字段值，然后插入表中
                DataRiskInfoExeStk resultDataRiskInfoExeStk = setFieldsValues(jsonData, indicatorInfo, dataRiskInfoExeStkIndex);
                daoRiskInfoExeStk.insert(resultDataRiskInfoExeStk);
            }
        } catch (Exception e){
            logger.error("OperationRiskInfoExeStk 更新表 RISK_INFO_EXE_STK 错误", e);
            throw e;
        }
    }
}
