/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10104006
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
 * 证券公司经纪业务单一客户普通账户与信用账户单一证券当日合计卖出的成交量汇总
 * 
 * @author yinhl
 */
public class Factor10104006 extends FactorBase {
	private static final long serialVersionUID = -4349083258673016028L;

	public Factor10104006() {
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