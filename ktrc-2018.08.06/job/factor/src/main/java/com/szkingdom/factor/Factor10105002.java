/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10105002
 * Author:   yinhl
 * Date:     2018-07-24
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;

/**
 * 流通市值指在某特定时间可交易的流通股股数乘以当时股价得出的流通股票总价值
 * 
 * @author yinhl
 */
public class Factor10105002 extends FactorBase {
	private static final long serialVersionUID = 5159608593206769925L;

	public Factor10105002() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleFactor() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public Object getResult() throws Exception {
		// TODO Auto-generated method stub
		return new Double(0.0d);
	}
}