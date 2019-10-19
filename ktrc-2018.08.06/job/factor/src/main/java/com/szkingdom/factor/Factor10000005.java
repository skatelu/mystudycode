/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10000005
 * Author:   ZhangMaohua
 * Date:     2018-07-24
 * Description: 客户买入额度（10000005）
 * 概念描述 : 证券公司以合约账户为单位对个人投资者买入开仓资金规模的限定额度。
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.data.DataOptTrdacct;
import com.szkingdom.operation.OperationOptTrdacct;

public class Factor10000005 extends FactorBase {
	private static final long serialVersionUID = 5889195260643846981L;
	private DataOptTrdacct dataOptTrdacct;
	private long quotaVal = 0L;

	public Factor10000005() {
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
		if(resultDataOptTrdacct != null) {
			quotaVal = resultDataOptTrdacct.getQuotaVal();
		}
	}

	@Override
	public Object getResult() throws Exception {
		return quotaVal;
	}
}