/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10300002
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
import com.szkingdom.dao.DaoAgrBuybkCorpQuota;
import com.szkingdom.data.DataAgrBuybkCorpQuota;

/**
 * 业务总额度为交易所批准的约定回购业务总额度
 * 
 * @author yinhl
 */
public class Factor10300002 extends FactorBase {
	private static final long serialVersionUID = -5869253142142278285L;
	
	private Long maxBondAmt;
	private DataAgrBuybkCorpQuota dataAgrBuybkCorpQuota;

	public Factor10300002() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject jsonObject) throws Exception {
		
		JSONObject json = jsonObject.getJSONObject(ConstantUtil.JSON_KTRC);
		
		dataAgrBuybkCorpQuota = new DataAgrBuybkCorpQuota();
		dataAgrBuybkCorpQuota.setStkbd(json.getString("STKBD"));
		dataAgrBuybkCorpQuota.setExtCls('0');
		dataAgrBuybkCorpQuota.setTrdacct(json.getString("TRDACCT"));
		dataAgrBuybkCorpQuota.setIntOrg(json.getString("INT_ORG") == null ? 0 : json.getShort("INT_ORG"));
		dataAgrBuybkCorpQuota.setStkpbu(json.getString("STKPBU"));
	}

	@Override
	public void handleFactor() throws Exception {
		DaoAgrBuybkCorpQuota dao = new DaoAgrBuybkCorpQuota();
		DataAgrBuybkCorpQuota retData = dao.selectUnique(dataAgrBuybkCorpQuota);
		maxBondAmt = retData.getMaxBondAmt();
	}

	@Override
	public Object getResult() throws Exception {
		if(maxBondAmt == null) {
			throw new Exception("因子Factor10300002未能成功取到值");
		}
		return NumConvertUtil.longToMoney(maxBondAmt);
	}
}