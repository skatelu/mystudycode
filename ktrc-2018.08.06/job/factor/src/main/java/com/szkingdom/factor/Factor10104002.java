/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10104002
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
 * 证券公司经纪业务所有客户单一证券当日融资买入交易的成交量汇总
 * 
 * @author yinhl
 */
public class Factor10104002 extends FactorBase {
	private static final long serialVersionUID = -4566681178870621535L;

	public Factor10104002() {
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