/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10105001
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
 * 流通市值指在某特定时间可交易的流通股股数
 * 
 * @author yinhl
 */
public class Factor10105001 extends FactorBase {
	private static final long serialVersionUID = 6888900629181690491L;

	public Factor10105001() {
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