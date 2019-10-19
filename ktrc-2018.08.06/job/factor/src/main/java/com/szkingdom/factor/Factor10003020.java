/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10003020
 * Author:   ZhangChangHong
 * Date:     2018-08-08
 * Description: 客户行权待交收冻结资金
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;

public class Factor10003020 extends FactorBase {
	private static final long serialVersionUID = 2540051604299858048L;

	public Factor10003020() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {

	}

	@Override
	public void handleFactor() throws Exception {
	}

	@Override
	public Object getResult() throws Exception {
		return 0;
	}
}