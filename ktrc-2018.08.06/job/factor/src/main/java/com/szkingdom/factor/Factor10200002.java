/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10200002
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

public class Factor10200002 extends FactorBase {
    private static final long serialVersionUID = 4977924578592013946L;

    private DataStkPlgLenderQuota dataStkPlgLenderQuota;
    private long netCptl = 0L;
    public Factor10200002() {
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
            netCptl = dataStkPlgLenderQuotaResult.getNetCptl();
        }
    }

    @Override
    public Object getResult() throws Exception {
        return netCptl;
    }
}
