/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10003003
 * Author:   ZhangChangHong
 * Date:     2018-09-15
 * Description: 客户权利仓市值
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.data.DataOptAsset;
import com.szkingdom.operation.OperationOptAsset;

public class Factor10003003 extends FactorBase {
	private static final long serialVersionUID = -4649324573725999121L;
	private DataOptAsset dataOptAsset;
	private double custOptLongMktVal = 0.00;

	public Factor10003003() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		JSONObject jsonData = (JSONObject) json.get(ConstantUtil.JSON_KTRC);

		// 组持仓数据查询条件
		dataOptAsset = new DataOptAsset();
		dataOptAsset.setCustCode(Long.parseLong(jsonData.getString("CUST_CODE")));
		dataOptAsset.setCurrency(jsonData.getString("CURRENCY").charAt(0));
		dataOptAsset.setOptSide(ConstantUtil.OPT_LONG);
	}

	@Override
	public void handleFactor() throws Exception {
		try {
			custOptLongMktVal = NumConvertUtil.longToMoney(OperationOptAsset.calcCustPosiMktVal(dataOptAsset));
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Object getResult() throws Exception {
		return custOptLongMktVal;
	}
}