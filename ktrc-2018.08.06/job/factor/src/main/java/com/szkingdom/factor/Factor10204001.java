/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10204001
 * Author:   ZhangMaohua
 * Date:     2018/8/21
 * Description: 待购回交易金额是指单一回购合约尚未购回的初始交易金额及相关费用。
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataStkPlgContracts;
import com.szkingdom.operation.OperationStkPlgContracts;

public class Factor10204001 extends FactorBase {
    private static final long serialVersionUID = 4977924578592013946L;

    private DataStkPlgContracts dataStkPlgContracts;
    long orgContAmt = 0L;

    public Factor10204001() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        dataStkPlgContracts = new DataStkPlgContracts();
        JSONObject jsonData = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
        dataStkPlgContracts.setTrdDate(Integer.parseInt(jsonData.getString("STK_CODE")));
        dataStkPlgContracts.setStkbd(jsonData.getString("STKBD"));
        dataStkPlgContracts.setConstrSn(jsonData.getString("CONSTR_SN"));

    }

    @Override
    public void handleFactor() throws Exception {
        DataStkPlgContracts dataStkPlgContractsResult = OperationStkPlgContracts.getDataStkPlgContractsByUnique(dataStkPlgContracts);
        if(dataStkPlgContractsResult != null){
            orgContAmt = dataStkPlgContractsResult.getOrgContAmt();
        }
    }

    @Override
    public Object getResult() throws Exception {
        return orgContAmt;
    }
}
