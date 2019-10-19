/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10202002
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
import com.szkingdom.data.DataCustAuditQuota;
import com.szkingdom.operation.OperationCustAuditQuota;

public class Factor10202002 extends FactorBase {
    private static final long serialVersionUID = 4977924578592013946L;

    private DataCustAuditQuota dataCustAuditQuota;
    private long quotaUsed = 0L;
    public Factor10202002() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        dataCustAuditQuota = new DataCustAuditQuota();
        JSONObject jsonData = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
        dataCustAuditQuota.setCustCode(Long.valueOf(jsonData.getString("CUST_CODE")));
        dataCustAuditQuota.setAgmType(jsonData.getString("AGM_TYPE").charAt(0));

    }

    @Override
    public void handleFactor() throws Exception {
        DataCustAuditQuota dataCustAuditQuotaResult = OperationCustAuditQuota.getDataCustAuditQuotaByUnique(dataCustAuditQuota);
        if(dataCustAuditQuotaResult != null){
            quotaUsed = dataCustAuditQuotaResult.getQuotaUsed();
        }
    }

    @Override
    public Object getResult() throws Exception {
        return quotaUsed;
    }
}
