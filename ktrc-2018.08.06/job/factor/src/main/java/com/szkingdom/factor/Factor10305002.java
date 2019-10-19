/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10305002
 * Author:   yinhl
 * Date:     2018-08-14
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;

/**
 * 证券当日涨幅（跌幅为负）
 * 
 * @author yinhl
 */
public class Factor10305002 extends FactorBase {
	private static final long serialVersionUID = 6893691186415077957L;

	public Factor10305002() {
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