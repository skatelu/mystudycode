/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationRiskLogCorpFund
 * Author:   ZhengMingjie
 * Date:     2018/8/17
 * Description: 公司资金头寸风险日志表（RISK_LOG_CORP_FUND）的操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.dao.DaoRiskLogCorpFund;
import com.szkingdom.data.DataRiskLogCorpFund;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationRiskLogCorpFund {
    protected static Logger logger = LoggerFactory.getLogger(OperationRiskLogCorpFund.class);

    /**
     * 写入风险表，如该行存在则update，如不存在则insert
     * @param jsonObject  Json串
     */
    public static void writeRiskTables(JSONObject jsonObject) throws Exception{

        DataRiskLogCorpFund dataRiskLogCorpFundIndex = new DataRiskLogCorpFund();
        DaoRiskLogCorpFund daoRiskLogCorpFund = new DaoRiskLogCorpFund();
        /**
         * 从Json串中获取唯一索引字段值
         */
        JSONObject data = (JSONObject)jsonObject.get(ConstantUtil.JSON_KTRC);
        dataRiskLogCorpFundIndex.setMarginCorpAcct(data.getString("MARGIN_CORP_ACCT"));
        dataRiskLogCorpFundIndex.setStkbd(data.getString("STKBD"));
        dataRiskLogCorpFundIndex.setCurrency(data.getString("CURRENCY").charAt(0));
        dataRiskLogCorpFundIndex.setAcctType('0');
        dataRiskLogCorpFundIndex.setMarginAvl(NumConvertUtil.moneyToLong(Double.parseDouble(data.getString("MARGIN_AVL"))));
        dataRiskLogCorpFundIndex.setMarginTotalAmt(NumConvertUtil.moneyToLong(Double.parseDouble(data.getString("MARGIN_TOTAL_AMT"))));
        dataRiskLogCorpFundIndex.setMarginUsed(NumConvertUtil.moneyToLong(Double.parseDouble(data.getString("MARGIN_USED"))));
        dataRiskLogCorpFundIndex.setOccDate(DateUtils.getCurrDate());
        dataRiskLogCorpFundIndex.setOccTime(DateUtils.getCurrTime());

        JSONObject indicatorInfo = (JSONObject)jsonObject.get(ConstantUtil.JSON_INDICATORINFO);
        dataRiskLogCorpFundIndex.setOpenAmtUseedRate(NumConvertUtil.rateToLong(Double.parseDouble(indicatorInfo.getString("indicatorResult"))));

        /**
         * RISK_LOG_SN字段是自增序列字段，故数据记录统一使用Insert
         */
        try {
            daoRiskLogCorpFund.insert(dataRiskLogCorpFundIndex);
        } catch (Exception e){
            throw new Exception("OperationRiskLogCorpFund 记录日志到表 RISK_LOG_CORP_FUND 错误 ", e);
        }
    }
}
