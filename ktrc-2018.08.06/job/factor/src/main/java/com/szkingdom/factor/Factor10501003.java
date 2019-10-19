/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10501003
 * Author:   ZhangMaohua
 * Date:     2018/9/10
 * Description:单一客户融资回购交易未到期金额1（10501003）
 * 概念描述：单一融资回购主体在单一证券公司处的全市场融资回购交易未到期金额（不含回购利息）
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataStkRepchContract;
import com.szkingdom.operation.OperationStkRepchContract;

import java.util.List;

public class Factor10501003 extends FactorBase {
    private static final long serialVersionUID = -4642522069178085713L;
    private DataStkRepchContract dataStkRepchContract;
    long matchAmtForCust = 0L;

    public Factor10501003() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        dataStkRepchContract = new DataStkRepchContract();
        JSONObject jsonData = (JSONObject)json.get(ConstantUtil.JSON_KTRC);

        dataStkRepchContract.setStkbd(jsonData.getString("STKBD"));
        dataStkRepchContract.setCustCode(Integer.parseInt(jsonData.getString("CUST_CODE")));
    }

    @Override
    public void handleFactor() throws Exception {
        List<DataStkRepchContract> resultDataStkRepchContract = OperationStkRepchContract.getStkRepchContractDataList(dataStkRepchContract);
        if (resultDataStkRepchContract != null && resultDataStkRepchContract.size() > 0) {
            for (DataStkRepchContract dataStkRepchContractEach : resultDataStkRepchContract) {
                matchAmtForCust += dataStkRepchContractEach.getMatchAmt();
            }
        }
    }

    @Override
    public Object getResult() throws Exception {
        return matchAmtForCust;
    }
}
