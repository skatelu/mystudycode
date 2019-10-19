/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10303003
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
 * 证券公司开展约定购回式证券交易的所有待购回金额（先不算利息）的汇总
 * 
 * @author yinhl
 */
public class Factor10303003 extends FactorBase {
	private static final long serialVersionUID = 1325099697559140198L;
	private DataAgrBuybkCorpQuota dataAgrBuybkCorpQuota;
	private Long lastContAmt;
	private Long lastBuyAmt;
	private Long dayBuyAmt;

	public Factor10303003() {
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
		if(retData == null) {
			return;
		}
		lastContAmt = retData.getLastContAmt();
		lastBuyAmt = retData.getLastBuyAmt();
		dayBuyAmt = retData.getDayBuybkAmt();
	}

	@Override
	public Object getResult() throws Exception {
		if(lastContAmt == null || lastBuyAmt == null || dayBuyAmt == null) {
			throw new Exception("因子Factor10303003未能成功取到值");
		}
		return NumConvertUtil.longToMoney(lastContAmt) 
				+ NumConvertUtil.longToMoney(lastBuyAmt) 
				+ NumConvertUtil.longToMoney(dayBuyAmt);
	}
}