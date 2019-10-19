/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10303004
 * Author:   yinhl
 * Date:     2018-08-14
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.dao.DaoCustAuditQuota;
import com.szkingdom.data.DataCustAuditQuota;

/**
 * 单一客户所有约定购回式证券交易合约待购回交易金额（先不算利息）的汇总
 * 
 * @author yinhl
 */
public class Factor10303004 extends FactorBase {
	private static final long serialVersionUID = 8803190956830493395L;
	private DataCustAuditQuota dataCustAuditQuota;
	private Long quotaUsed;
	private Long appAmt;

	public Factor10303004() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject jsonObject) throws Exception {
		
		JSONObject json = jsonObject.getJSONObject(ConstantUtil.JSON_KTRC);
		
		dataCustAuditQuota = new DataCustAuditQuota();
		dataCustAuditQuota.setCustCode(json.getLongValue("CUST_CODE"));
		dataCustAuditQuota.setAgmType('Y');
	}

	@Override
	public void handleFactor() throws Exception {
		DaoCustAuditQuota daoCustAuditQuota = new DaoCustAuditQuota();
		DataCustAuditQuota retData = daoCustAuditQuota.selectUnique(dataCustAuditQuota);
		quotaUsed = retData.getQuotaUsed();
		appAmt = retData.getAppAmt();
	}

	@Override
	public Object getResult() throws Exception {
		if(quotaUsed == null || appAmt == null) {
			throw new Exception("因子Factor10303004未能成功取到值");
		}
		return NumConvertUtil.longToMoney(quotaUsed) + 
				NumConvertUtil.longToMoney(appAmt);
	}
}