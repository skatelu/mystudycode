/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10200001
 * Author:   ZhangMaohua
 * Date:     2018/8/20
 * Description: 因子10200001:业务规模，为交易所批准证券公司开展股票质押式回购的业务规模
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
 * 因子10200001:业务规模，为交易所批准证券公司开展股票质押式回购的业务规模
 */
public class Factor10200001 extends FactorBase {
    private static final long serialVersionUID = 4977924578592013946L;

    private DataStkPlgLenderQuota dataStkPlgLenderQuota;
    private long maxBondAmt = 0L;
    public Factor10200001() {
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
            maxBondAmt = dataStkPlgLenderQuotaResult.getMaxBondAmt();
        }
    }

    @Override
    public Object getResult() throws Exception {
        return maxBondAmt;
    }
}
