/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10202003
 * Author:   ZhangMaohua
 * Date:     2018/8/30
 * Description: 	单一证券交易金额（10202003）
 * 概念描述： 提交质押登记的单一质押券对应的所有合约待购回交易金额与当日已有效申报的初始交易成交金额的汇总。
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataStkPlgUndlInfo;
import com.szkingdom.operation.OperationStkPlgUndlInfo;

public class Factor10202003  extends FactorBase {
    private static final long serialVersionUID = -6750929570347328575L;
    private DataStkPlgUndlInfo dataStkPlgUndlInfo;
    long singleStkAmt = 0L;

    public Factor10202003() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        dataStkPlgUndlInfo = new DataStkPlgUndlInfo();

        JSONObject jsonData = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
        dataStkPlgUndlInfo.setStkCode(jsonData.getString("STK_CODE"));
        dataStkPlgUndlInfo.setStkbd(jsonData.getString("STKBD"));
    }

    @Override
    public void handleFactor() throws Exception {
        DataStkPlgUndlInfo resultDataStkPlgUndlInfo = OperationStkPlgUndlInfo.getDataStkPlgUndlInfoByUnique(dataStkPlgUndlInfo);
        if(resultDataStkPlgUndlInfo != null){
            singleStkAmt = resultDataStkPlgUndlInfo.getLastContAmt()
                            + resultDataStkPlgUndlInfo.getLastBuyAmt()
                            + resultDataStkPlgUndlInfo.getDayBuyAmt();
        }
    }

    @Override
    public Object getResult() throws Exception {
        return singleStkAmt;
    }
}
