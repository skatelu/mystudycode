/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10300001
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
 * 证券公司净资本
 * 
 * @author yinhl
 */
public class Factor10300001 extends FactorBase {
	private static final long serialVersionUID = 2428769181942939167L;
	
	private Long netCptl;
	private DataAgrBuybkCorpQuota dataAgrBuybkCorpQuota;

	public Factor10300001() {
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
		netCptl = retData.getNetCptl();
	}

	@Override
	public Object getResult() throws Exception {
		if(netCptl == null) {
			throw new Exception("因子Factor10300001未能成功取到值");
		}
		
		return NumConvertUtil.longToMoney(netCptl);
	}
}