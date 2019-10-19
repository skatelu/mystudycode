/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10003017
 * Author:   ZhengMingjie
 * Date:     2018-08-01
 * Description: 证券公司经纪业务所有客户所有标的合约品种义务仓已占用保证金总和
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.NumConvertUtil;

public class Factor10003017 extends FactorBase {
	private static final long serialVersionUID = 5835497365035459802L;
	private double marginUsed = 0.0;

	public Factor10003017() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		JSONObject data = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
		this.marginUsed = Double.parseDouble(data.getString("MARGIN_USED"));
	}

	@Override
	public void handleFactor() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public Object getResult() throws Exception {
		return this.marginUsed;
	}
}