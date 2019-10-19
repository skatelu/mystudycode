/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10003021
 * Author:   ZhangMaohua
 * Date:     2018-08-01
 * Description: 客户行权日行权交收已冻结资金（10003021）
 * 概念描述 :  客户行权日行权交收已冻结的资金。
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataCuacctFund;
import com.szkingdom.operation.OperationCuacctFund;


public class Factor10003021 extends FactorBase {
	private static final long serialVersionUID = 3829491932387168928L;
	private DataCuacctFund dataCuacctFund;
	private long fundExeFrz = 0L;

	public Factor10003021() {
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
			fundExeFrz = resultDataCuacctFund.getFundExeFrz();
		}
	}

	@Override
	public Object getResult() throws Exception {
		return fundExeFrz;
	}
}