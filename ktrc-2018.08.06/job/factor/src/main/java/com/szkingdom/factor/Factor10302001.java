/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10302001
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
 * 约定购回式证券交易专用账户与会员自营证券账户持有的单一证券数量之和
 * 
 * @author yinhl
 */
public class Factor10302001 extends FactorBase {
	private static final long serialVersionUID = -6750929570347328575L;

	public Factor10302001() {
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