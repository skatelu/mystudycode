/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10003018
 * Author:   ZhengMingjie
 * Date:     2018-08-01
 * Description: 证券公司客户股票期权保证金账户的总额
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.NumConvertUtil;

public class Factor10003018 extends FactorBase {
	private static final long serialVersionUID = -2584069495111100635L;
	private double marginTotalAmt = 0.0;

	public Factor10003018() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		JSONObject data = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
		this.marginTotalAmt = Double.parseDouble(data.getString("MARGIN_TOTAL_AMT"));
	}

	@Override
	public void handleFactor() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public Object getResult() throws Exception {
		return this.marginTotalAmt;
	}
}