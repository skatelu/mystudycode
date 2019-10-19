/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10003007
 * Author:   ZhangChangHong
 * Date:     2018-09-15
 * Description: 客户总资产
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.data.DataCuacctFund;
import com.szkingdom.operation.OperationCuacctFund;

public class Factor10003007 extends FactorBase {
	private static final long serialVersionUID = 2582841269879821263L;
	private DataCuacctFund dataCuacctFund;
	private double custTotalAmt = 0.00;

	public Factor10003007() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		JSONObject jsonData = (JSONObject) json.get(ConstantUtil.JSON_KTRC);

		// 组查询条件
		dataCuacctFund = new DataCuacctFund();
		dataCuacctFund.setUserCode(Long.parseLong(jsonData.getString("CUST_CODE")));
		dataCuacctFund.setCuacctCode(Long.parseLong(jsonData.getString("CUACCT_CODE")));
		dataCuacctFund.setCurrency(jsonData.getString("CURRENCY").charAt(0));
	}

	@Override
	public void handleFactor() throws Exception {
		try {
			custTotalAmt = NumConvertUtil.longToMoney(OperationCuacctFund.calcCustTotalAmt(dataCuacctFund));
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Object getResult() throws Exception {
		return custTotalAmt;
	}
}