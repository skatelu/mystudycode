/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10003001
 * Author:   ZhangChangHong
 * Date:     2018-08-07
 * Description: 客户股票期权保证金账户中用于衍生品交易的资金总额，根据出入金、成交等进行实时增减
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

public class Factor10003001 extends FactorBase {
	private static final long serialVersionUID = 5542830682004689661L;

	private DataCuacctFund dataCuacctFund;
    private double marginTotalAmt = 0.00;

	public Factor10003001() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		// json解析
		JSONObject jsonData = (JSONObject) json.get(ConstantUtil.JSON_KTRC);

		dataCuacctFund = new DataCuacctFund();
		dataCuacctFund.setCuacctCode(Long.parseLong(jsonData.getString("CUACCT_CODE")));
		dataCuacctFund.setCurrency(jsonData.getString("CURRENCY").charAt(0));
	}

	@Override
	public void handleFactor() throws Exception {
		// 获取客户保证金总额
		try {
            DataCuacctFund resultDataCuacctFund = OperationCuacctFund.getCuacctFundDataByUnique(this.dataCuacctFund);
            if (resultDataCuacctFund != null) {
                // 客户保证金总额（CUACCT_FUND.FUND_BLN + CUACCT_FUND.FUND_TRD_BLN）
                marginTotalAmt = NumConvertUtil.longToMoney(resultDataCuacctFund.getFundBln() + resultDataCuacctFund.getFundTrdBln());
            } else {
                marginTotalAmt = 0.00;
            }
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Object getResult() throws Exception {
		return marginTotalAmt;
	}
}