/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10501005
 * Author:   ZhangMaohua
 * Date:     2018/9/10
 * Description:客户当日到期购回金额（10501005）：客户当日融资回购到期所需购回金额（含回购利息）
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.data.DataStkRepchContract;
import com.szkingdom.operation.OperationStkRepchContract;

import java.util.List;

public class Factor10501005 extends FactorBase {
    private static final long serialVersionUID = -4642522069178085713L;

    private DataStkRepchContract dataStkRepchContract;
    long repchAmt = 0L;

    public Factor10501005() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        dataStkRepchContract = new DataStkRepchContract();
        JSONObject jsonData = (JSONObject)json.get(ConstantUtil.JSON_KTRC);

        //设置索引
        dataStkRepchContract.setStkbd(jsonData.getString("STKBD"));
        dataStkRepchContract.setCustCode(Integer.parseInt(jsonData.getString("CUST_CODE")));
        dataStkRepchContract.setRepchDate(DateUtils.getCurrDate());
    }

    @Override
    public void handleFactor() throws Exception {
        List<DataStkRepchContract> resultDataStkRepchContract = OperationStkRepchContract.getStkRepchContractDataList(dataStkRepchContract);
        if (resultDataStkRepchContract != null && resultDataStkRepchContract.size() > 0) {
            for (DataStkRepchContract dataStkRepchContractEach : resultDataStkRepchContract) {
                repchAmt += dataStkRepchContractEach.getMatchAmt();
            }
        }
    }

    @Override
    public Object getResult() throws Exception {
        return repchAmt;
    }
}
