/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationRiskInfoCorpFund
 * Author:   ZhengMingjie
 * Date:     2018/8/17
 * Description: 公司资金头寸风险信息表（RISK_INFO_CORP_FUND）的操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.dao.DaoRiskInfoCorpFund;
import com.szkingdom.data.DataRiskInfoCorpFund;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationRiskInfoCorpFund {
    protected static Logger logger = LoggerFactory.getLogger(OperationRiskInfoCorpFund.class);

    /**
     * insert时，对需要字段赋值
     * @param indicatorInfo         Json串
     * @param condDataRiskInfoCorpFund 唯一索引
     * @return dataRiskCorpFundPara
     */
    private static DataRiskInfoCorpFund setFieldsValues(JSONObject indicatorInfo, DataRiskInfoCorpFund condDataRiskInfoCorpFund) throws Exception {

        return condDataRiskInfoCorpFund;
    }

    /**
     * update时，对需要字段修改
     * @param condDataRiskInfoCorpFund 修改部分字段后返回
     */
    private static DataRiskInfoCorpFund updateFieldsValues(DataRiskInfoCorpFund condDataRiskInfoCorpFund) throws Exception {

        return condDataRiskInfoCorpFund;
    }

    /**
     * 写入风险表，如该行存在则update，如不存在则insert
     * @param jsonObject  Json串
     */
    public static void writeRiskTables(JSONObject jsonObject) throws Exception{

        DataRiskInfoCorpFund dataRiskInfoCorpFundIndex = new DataRiskInfoCorpFund();
        DaoRiskInfoCorpFund daoRiskInfoCorpFund = new DaoRiskInfoCorpFund();
        /**
         * 从Json串中获取唯一索引字段值
         */
        JSONObject data = (JSONObject)jsonObject.get(ConstantUtil.JSON_KTRC);
        dataRiskInfoCorpFundIndex.setMarginCorpAcct(data.getString("MARGIN_CORP_ACCT"));
        dataRiskInfoCorpFundIndex.setStkbd(data.getString("STKBD"));
        dataRiskInfoCorpFundIndex.setCurrency(data.getString("CURRENCY").charAt(0));
        dataRiskInfoCorpFundIndex.setAcctType('0');
        dataRiskInfoCorpFundIndex.setMarginAvl(NumConvertUtil.moneyToLong(Double.parseDouble(data.getString("MARGIN_AVL"))));
        dataRiskInfoCorpFundIndex.setMarginTotalAmt(NumConvertUtil.moneyToLong(Double.parseDouble(data.getString("MARGIN_TOTAL_AMT"))));
        dataRiskInfoCorpFundIndex.setMarginUsed(NumConvertUtil.moneyToLong(Double.parseDouble(data.getString("MARGIN_USED"))));
        dataRiskInfoCorpFundIndex.setOccDate(DateUtils.getCurrDate());
        dataRiskInfoCorpFundIndex.setOccTime(DateUtils.getCurrTime());

        JSONObject indicatorInfo = (JSONObject)jsonObject.get(ConstantUtil.JSON_INDICATORINFO);
        dataRiskInfoCorpFundIndex.setOpenAmtUseedRate(NumConvertUtil.rateToLong(Double.parseDouble(indicatorInfo.getString("indicatorResult"))));

        /**
         * 根据唯一索引查表，如该行存在update，如不存在则insert
         */
        try {
            DataRiskInfoCorpFund condDataRiskInfoCorpFund = daoRiskInfoCorpFund.selectUnique(dataRiskInfoCorpFundIndex);
            if(condDataRiskInfoCorpFund != null){
                //更新字段
                DataRiskInfoCorpFund dataRiskInfoCorpFundResult = updateFieldsValues(dataRiskInfoCorpFundIndex);
                daoRiskInfoCorpFund.update(dataRiskInfoCorpFundResult);
            }
            else {
                //设置所有字段值，然后插入表中
                DataRiskInfoCorpFund dataRiskInfoCorpFundResult = setFieldsValues(indicatorInfo, dataRiskInfoCorpFundIndex);
                daoRiskInfoCorpFund.insert(dataRiskInfoCorpFundResult);
            }
        } catch (Exception e){
            throw new Exception("OperationRiskInfoCorpFund 更新表 RISK_INFO_CORP_FUND 错误 ", e);
        }
    }
}
