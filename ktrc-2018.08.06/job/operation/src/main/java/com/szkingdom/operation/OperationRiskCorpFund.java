/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationRiskCorpFund
 * Author:   ZhengMingjie
 * Date:     2018/8/17
 * Description: 公司资金头寸风险信息表（RISK_CORP_FUND）的操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.dao.DaoRiskCorpFund;
import com.szkingdom.data.DataRiskCorpFund;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationRiskCorpFund {
    protected static Logger logger = LoggerFactory.getLogger(OperationRiskCorpFund.class);

    /**
     * insert时，对需要字段赋值
     * @param indicatorInfo         Json串
     * @param condDataRiskCorpFund 唯一索引
     * @return dataRiskCorpFundPara
     */
    private static DataRiskCorpFund setFieldsValues(JSONObject indicatorInfo, DataRiskCorpFund condDataRiskCorpFund) throws Exception {

        return condDataRiskCorpFund;
    }

    /**
     * update时，对需要字段修改
     * @param condDataRiskCorpFund 修改部分字段后返回
     */
    private static DataRiskCorpFund updateFieldsValues(DataRiskCorpFund condDataRiskCorpFund) throws Exception {

        return condDataRiskCorpFund;
    }

    /**
     * 写入风险表，如该行存在则update，如不存在则insert
     * @param jsonObject  Json串
     */
    public static void writeRiskTables(JSONObject jsonObject) throws Exception{

        DataRiskCorpFund dataRiskCorpFundIndex = new DataRiskCorpFund();
        DaoRiskCorpFund daoRiskCorpFund = new DaoRiskCorpFund();
        /**
         * 从Json串中获取唯一索引字段值
         */
        JSONObject data = (JSONObject)jsonObject.get(ConstantUtil.JSON_KTRC);
        dataRiskCorpFundIndex.setMarginCorpAcct(data.getString("MARGIN_CORP_ACCT"));
        dataRiskCorpFundIndex.setStkbd(data.getString("STKBD"));
        dataRiskCorpFundIndex.setCurrency(data.getString("CURRENCY").charAt(0));
        dataRiskCorpFundIndex.setAcctType('0');
        dataRiskCorpFundIndex.setMarginAvl(NumConvertUtil.moneyToLong(Double.parseDouble(data.getString("MARGIN_AVL"))));
        dataRiskCorpFundIndex.setMarginTotalAmt(NumConvertUtil.moneyToLong(Double.parseDouble(data.getString("MARGIN_TOTAL_AMT"))));
        dataRiskCorpFundIndex.setMarginUsed(NumConvertUtil.moneyToLong(Double.parseDouble(data.getString("MARGIN_USED"))));
        dataRiskCorpFundIndex.setOccDate(DateUtils.getCurrDate());
        dataRiskCorpFundIndex.setOccTime(DateUtils.getCurrTime());

        JSONObject indicatorInfo = (JSONObject)jsonObject.get(ConstantUtil.JSON_INDICATORINFO);
        dataRiskCorpFundIndex.setOpenAmtUseedRate(NumConvertUtil.rateToLong(Double.parseDouble(indicatorInfo.getString("indicatorResult"))));


        /**
         * 根据唯一索引查表，如该行存在update，如不存在则insert
         */
        try {
            DataRiskCorpFund condDataRiskCorpFund = daoRiskCorpFund.selectUnique(dataRiskCorpFundIndex);
            if(condDataRiskCorpFund != null){
                //更新字段
                DataRiskCorpFund dataRiskCorpFundResult = updateFieldsValues(dataRiskCorpFundIndex);
                daoRiskCorpFund.update(dataRiskCorpFundResult);
            }
            else {
                //设置所有字段值，然后插入表中
                DataRiskCorpFund dataRiskCorpFundResult = setFieldsValues(indicatorInfo, dataRiskCorpFundIndex);
                daoRiskCorpFund.insert(dataRiskCorpFundResult);
            }
        } catch (Exception e){
            throw new Exception("OperationRiskCorpFund 更新表 RISK_CORP_FUND 错误 ", e);
        }
    }
}
