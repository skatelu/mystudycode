/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10502004
 * Author:   ZhangMaohua
 * Date:     2018/9/10
 * Description: 客户可用资金: 客户资金账户中用于即时交易、即时出金的人民币资金可用值
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataCuacctFund;
import com.szkingdom.operation.OperationCuacctFund;

public class Factor10502004 extends FactorBase {
    private static final long serialVersionUID = -4642522069178085713L;
    private DataCuacctFund dataCuacctFund;
    private long fundAvl = 0L;

    public Factor10502004() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        dataCuacctFund = new DataCuacctFund();

        JSONObject jsonData = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
        dataCuacctFund.setCuacctCode(Long.parseLong(jsonData.getString("CUACCT_CODE")));
        dataCuacctFund.setCurrency(jsonData.getString("CURRENCY").charAt(0));
    }

    @Override
    public void handleFactor() throws Exception {
        DataCuacctFund resultDataCuacctFund = OperationCuacctFund.getCuacctFundDataByUnique(dataCuacctFund);
        if(resultDataCuacctFund != null){
            fundAvl = resultDataCuacctFund.getFundAvl();
        }
    }

    @Override
    public Object getResult() throws Exception {
        return fundAvl;
    }
}
