/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10105004
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
 * 单一证券当日的总成交数量
 * 
 * @author yinhl
 */
public class Factor10105004 extends FactorBase {
	private static final long serialVersionUID = 4977924578592013946L;

	public Factor10105004() {
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