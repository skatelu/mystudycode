/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10305001
 * Author:   yinhl
 * Date:     2018-08-14
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.dao.DaoAgrBuybkUndlInfo;
import com.szkingdom.data.DataAgrBuybkUndlInfo;

/**
 * 流通股本指某一证券在二级市场可交易的流通股股数
 * 
 * @author yinhl
 */
public class Factor10305001 extends FactorBase {
	private static final long serialVersionUID = -4688094493471056753L;
	private DataAgrBuybkUndlInfo dataAgrBuybkUndlInfo;
	// 总股本CMONEY
	private Long totalShareCapital;

	public Factor10305001() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject jsonObject) throws Exception {
		
		JSONObject json = jsonObject.getJSONObject(ConstantUtil.JSON_KTRC);
		
		dataAgrBuybkUndlInfo = new DataAgrBuybkUndlInfo();
		dataAgrBuybkUndlInfo.setStkbd(json.getString("STKBD"));
		dataAgrBuybkUndlInfo.setStkCode(json.getString("STK_CODE"));
	}

	@Override
	public void handleFactor() throws Exception {
		DaoAgrBuybkUndlInfo daoAgrBuybkUndlInfo = new DaoAgrBuybkUndlInfo();
		DataAgrBuybkUndlInfo dataInfo = (DataAgrBuybkUndlInfo) daoAgrBuybkUndlInfo.selectOne("", dataAgrBuybkUndlInfo);
		totalShareCapital = dataInfo.getTotalShareCapital();
	}

	@Override
	public Object getResult() throws Exception {
		if(totalShareCapital == null ) {
			throw new Exception("因子Factor10305001未能成功取到值");
		}
		return NumConvertUtil.longToMoney(totalShareCapital);
	}
}