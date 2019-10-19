/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10501001
 * Author:   ZhangMaohua
 * Date:     2018/9/10
 * Description: 单一证券账户融资回购交易未到期金额1
 * 概念描述：单一证券账户进行债券质押式回购的所有融资回购交易未到期金额（不含回购利息）
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

public class Factor10501001 extends FactorBase {
    private static final long serialVersionUID = -4642522069178085713L;
    private DataStkRepchContract dataStkRepchContract;
    long matchAmtForStk = 0L;

    public Factor10501001() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        dataStkRepchContract = new DataStkRepchContract();
        JSONObject jsonData = (JSONObject)json.get(ConstantUtil.JSON_KTRC);

        //设置索引
        dataStkRepchContract.setStkbd(jsonData.getString("STKBD"));
        dataStkRepchContract.setTrdacct(jsonData.getString("TRDACCT"));
    }

    @Override
    public void handleFactor() throws Exception {
        List<DataStkRepchContract> resultDataStkRepchContract = OperationStkRepchContract.getStkRepchContractDataList(dataStkRepchContract);
        if (resultDataStkRepchContract != null && resultDataStkRepchContract.size() > 0) {
            for (DataStkRepchContract dataStkRepchContractEach : resultDataStkRepchContract) {
                matchAmtForStk += dataStkRepchContractEach.getMatchAmt();
            }
        }
    }

    @Override
    public Object getResult() throws Exception {
        return matchAmtForStk;
    }
}
