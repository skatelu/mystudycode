/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10102016
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
 * 证券公司经纪业务单一客户单一证券融券卖出交易尚未偿还证券数量的汇总
 * 
 * @author yinhl
 */
public class Factor10102016 extends FactorBase {
	private static final long serialVersionUID = 7216842945436612773L;

	public Factor10102016() {
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