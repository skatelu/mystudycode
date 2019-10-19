/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10202001
 * Author:   ZhangMaohua
 * Date:     2018/8/20
 * Description:
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataStkPlgLenderQuota;
import com.szkingdom.operation.OperationStkPlgLenderQuota;

/**
 * 已使用业务规模（10202001） 概念描述： 证券公司股票质押式回购未到期明细数据中的
 * 所有未到期初始交易金额与当日已有效申报的初始交易成交金额之和。
 */
public class Factor10202001 extends FactorBase {
    private static final long serialVersionUID = 4977924578592013946L;

    private DataStkPlgLenderQuota dataStkPlgLenderQuota;
    private long usedBondAmt = 0L;

    public Factor10202001() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        dataStkPlgLenderQuota = new DataStkPlgLenderQuota();
        JSONObject jsonData = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
        dataStkPlgLenderQuota.setStkbd(jsonData.getString("STKBD"));
        dataStkPlgLenderQuota.setTrdacct(jsonData.getString("TRDACCT"));

    }

    @Override
    public void handleFactor() throws Exception {
        DataStkPlgLenderQuota dataStkPlgLenderQuotaResult = OperationStkPlgLenderQuota.getStkPlgLenderQuotaDataByUnique(dataStkPlgLenderQuota);
        if(dataStkPlgLenderQuotaResult != null){
            usedBondAmt = dataStkPlgLenderQuotaResult.getLastContAmt() +
                            dataStkPlgLenderQuotaResult.getLastBuyAmt() +
                            dataStkPlgLenderQuotaResult.getDayBuyAmt();
        }
    }

    @Override
    public Object getResult() throws Exception {
        return usedBondAmt;
    }
}
