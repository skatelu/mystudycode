/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationRiskInfoAcct
 * Author:   ZhangMaohua
 * Date:     2018/7/24
 * Description: 账户类风险信息表操作
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.dao.DaoRiskInfoAcct;
import com.szkingdom.data.DataRiskInfoAcct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationRiskInfoAcct {

    protected static Logger logger = LoggerFactory.getLogger(OperationRiskInfoAcct.class);

    /**
     * insert时，对需要字段赋值
     * @param indicatorInfo         Json串
     * @param condDataRiskInfoAcct 唯一索引
     * @return condDataRiskInfoAcct
     */
    private static DataRiskInfoAcct setFieldsValues(JSONObject indicatorInfo, DataRiskInfoAcct condDataRiskInfoAcct) throws Exception {

        /**
         * 从Json串中提取字段INDICATOR_NAME、RISK_VALUE 和设置 PRE_RISK_VALUE（3）
         */
        condDataRiskInfoAcct.setIndicatorName(indicatorInfo.getString("indicatorName"));

        /**
         * 设置日期时间字段OCC_DATE、OCC_TIME （2）
         */
        condDataRiskInfoAcct.setOccDate(DateUtils.getCurrDate());
        condDataRiskInfoAcct.setOccTime(DateUtils.getCurrTime());

        /**
         * 测试
         */
        condDataRiskInfoAcct.setOpenCondFlag('0');
        condDataRiskInfoAcct.setRiskRatingFlag('0');
        condDataRiskInfoAcct.setRiskValidFlag('0');
        condDataRiskInfoAcct.setTrdCtrlFlag('0');
        condDataRiskInfoAcct.setTrdacctExcls('0');
        condDataRiskInfoAcct.setSkipLvlFlag('0');
        condDataRiskInfoAcct.setForceUcloseFlag('0');
        condDataRiskInfoAcct.setCustType('0');
        condDataRiskInfoAcct.setCustCls('0');
        condDataRiskInfoAcct.setOptTrdacctLvl('0');
        condDataRiskInfoAcct.setNoticeSentLevel('0');
        condDataRiskInfoAcct.setCurrency('0');
        return condDataRiskInfoAcct;
    }

    /**
     * update时，对需要字段修改
     * @param condDataRiskInfoAcct 修改部分字段后返回
     * @return condDataRiskInfoAcct
     */
    private static DataRiskInfoAcct updateFieldsValues(DataRiskInfoAcct condDataRiskInfoAcct) throws Exception {

        /**
         * 设置日期时间字段OCC_DATE、OCC_TIME （2）
         */
        condDataRiskInfoAcct.setOccDate(DateUtils.getCurrDate());
        condDataRiskInfoAcct.setOccTime(DateUtils.getCurrTime());

        return condDataRiskInfoAcct;
    }

    /**
     * 写入风险表，如该行存在则update，如不存在则insert
     * @param jsonObject  Json串
     */
    public static void writeRiskTables(JSONObject jsonObject) throws Exception{

        DataRiskInfoAcct dataRiskInfoAcctIndex = new DataRiskInfoAcct();
        DaoRiskInfoAcct daoRiskInfoAcct = new DaoRiskInfoAcct();
        /**
         * 从Json串中获取唯一索引字段值
         */
        JSONObject jsonData = (JSONObject)jsonObject.get(ConstantUtil.JSON_KTRC);
        dataRiskInfoAcctIndex.setCustCode(Long.parseLong(jsonData.getString("CUST_CODE")));
        dataRiskInfoAcctIndex.setCurrency(jsonData.getString("CURRENCY").charAt(0));
        dataRiskInfoAcctIndex.setStkbd(jsonData.getString("STKBD"));
        dataRiskInfoAcctIndex.setTrdacct(jsonData.getString("TRDACCT"));

        JSONObject indicatorInfo = (JSONObject)jsonObject.get(ConstantUtil.JSON_INDICATORINFO);
        dataRiskInfoAcctIndex.setIndicatorId(indicatorInfo.getString("indicatorId"));

        /**
         * 根据唯一索引查表，如该行存在update，如不存在则insert
         */
        try {
            DataRiskInfoAcct condDataRiskInfoAcct = daoRiskInfoAcct.selectUnique(dataRiskInfoAcctIndex);
            if(condDataRiskInfoAcct != null){
                //更新其他字段
                DataRiskInfoAcct resultDataRiskInfoAcct  = updateFieldsValues(condDataRiskInfoAcct);
                daoRiskInfoAcct.update(resultDataRiskInfoAcct);
            }
            else {
                //设置所有字段值，然后插入表中
                DataRiskInfoAcct resultDataRiskInfoAcct = setFieldsValues(indicatorInfo, dataRiskInfoAcctIndex);
                daoRiskInfoAcct.insert(resultDataRiskInfoAcct);
            }
        } catch (Exception e){
            throw new Exception("OperationRiskInfoAcct 更新表RISK_INFO_ACCT错误 ", e);
        }
    }

}
