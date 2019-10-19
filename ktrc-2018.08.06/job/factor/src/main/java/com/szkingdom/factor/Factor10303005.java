/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10303005
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
 * 单一证券充当担保券对应的所有约定购回式证券交易合约待购回交易金额（先不算利息）的汇总
 * 
 * @author yinhl
 */
public class Factor10303005 extends FactorBase {
	private static final long serialVersionUID = -2793908303601448037L;
	private DataAgrBuybkUndlInfo dataAgrBuybkUndlInfo;
	private Long lastBuyAmt;
	private Long lastContAmt;
	private Long dayBuyAmt;

	public Factor10303005() {
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
		DataAgrBuybkUndlInfo dataInfo = (DataAgrBuybkUndlInfo) daoAgrBuybkUndlInfo.selectStkTotalAtm(dataAgrBuybkUndlInfo);
		lastBuyAmt = dataInfo.getLastBuyAmt();
		lastContAmt = dataInfo.getLastContAmt();
		dayBuyAmt = dataInfo.getDayBuyAmt();
	}

	@Override
	public Object getResult() throws Exception {
		if(lastBuyAmt == null || lastContAmt == null || dayBuyAmt == null) {
			throw new Exception("因子Factor10303005未能成功取到值");
		}
		return NumConvertUtil.longToMoney(lastBuyAmt) + 
				NumConvertUtil.longToMoney(lastContAmt) + 
				NumConvertUtil.longToMoney(dayBuyAmt);
	}
}