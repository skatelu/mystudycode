/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10003019
 * Author:   ZhangMaohua
 * Date:     2018-08-01
 * Description: 客户占用买入额度（10003019）
 * 概念描述 :  客户（仅针对个人投资者）用于股票期权买入开仓的已使用资金规模。
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.data.DataOptTrdacct;
import com.szkingdom.operation.OperationOptTrdacct;

public class Factor10003019 extends FactorBase {
	private static final long serialVersionUID = -2584069495111100635L;
	private DataOptTrdacct dataOptTrdacct;
	private long quotaValUsed = 0L;

	public Factor10003019() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		dataOptTrdacct = new DataOptTrdacct();

		JSONObject data = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
		dataOptTrdacct.setStkbd(data.getString("STKBD"));
		dataOptTrdacct.setTrdacct(data.getString("TRDACCT"));
		dataOptTrdacct.setCustCode(Long.parseLong(data.getString("CUST_CODE")));
	}

	@Override
	public void handleFactor() throws Exception {
		DataOptTrdacct resultDataOptTrdacct = OperationOptTrdacct.getOptTrdacctDataByUnique(dataOptTrdacct);
		if(resultDataOptTrdacct != null){
			quotaValUsed = resultDataOptTrdacct.getQuotaValUsed();
		}
	}

	@Override
	public Object getResult() throws Exception {
		return quotaValUsed;
	}
}