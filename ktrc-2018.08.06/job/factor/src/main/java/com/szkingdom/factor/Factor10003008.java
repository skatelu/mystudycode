/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10003008
 * Author:   ZhangMaohua
 * Date:     2018-07-24
 * Description: 客户可用资金（10003008）
 * 概念描述: 客户股票期权保证金账户中用于即时交易、即时出金的资金可用值。
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataCuacctFund;
import com.szkingdom.operation.OperationCuacctFund;


public class Factor10003008 extends FactorBase {
	private static final long serialVersionUID = 1140656985096428769L;
	private DataCuacctFund dataCuacctFund;
	private long fundAvl = 0L;

	public Factor10003008() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		dataCuacctFund = new DataCuacctFund();

		JSONObject data = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
		dataCuacctFund.setCuacctCode(Long.parseLong(data.getString("CUACCT_CODE")));
		dataCuacctFund.setCurrency(data.getString("CURRENCY").charAt(0));

	}

	@Override
	public void handleFactor() throws Exception {
		DataCuacctFund resultDataCuacctFund = OperationCuacctFund.getCuacctFundDataByUnique(dataCuacctFund);
		if(resultDataCuacctFund != null){
			fundAvl = resultDataCuacctFund.getFundAvl();
		}
	}

	@Override
	public Object getResult() throws Exception {
		return fundAvl;
	}
}