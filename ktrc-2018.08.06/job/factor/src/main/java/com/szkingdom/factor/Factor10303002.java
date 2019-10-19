/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10303002
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
import com.szkingdom.dao.DaoAgrBuybkContracts;
import com.szkingdom.data.DataAgrBuybkContracts;

/**
 * 单笔交易或合并管理的多笔交易的待购回交易金额（不含利息）
 * 
 * @author yinhl
 */
public class Factor10303002 extends FactorBase {
	private static final long serialVersionUID = -7064659083832045727L;
	
	private DataAgrBuybkContracts dataAgrBuybkContracts;
	private Long buybkAmt;

	public Factor10303002() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject jsonObject) throws Exception {
		
		JSONObject json = jsonObject.getJSONObject(ConstantUtil.JSON_KTRC);
		
		dataAgrBuybkContracts = new DataAgrBuybkContracts();
		dataAgrBuybkContracts.setTrdDate(json.getIntValue("TRD_DATE"));
		dataAgrBuybkContracts.setTrdacct(json.getString("TRDACCT"));
		dataAgrBuybkContracts.setStkbd(json.getString("STKBD"));
		dataAgrBuybkContracts.setStkex(json.getString("STKEX").charAt(0));
		dataAgrBuybkContracts.setMatchSn(json.getString("MATCH_SN"));
		dataAgrBuybkContracts.setStkCode(json.getString("STK_CODE"));
	}

	@Override
	public void handleFactor() throws Exception {
		DaoAgrBuybkContracts daoContracts = new DaoAgrBuybkContracts();
		DataAgrBuybkContracts retData = daoContracts.selectBuybkAmt(dataAgrBuybkContracts);
		buybkAmt = retData.getBuybkAmt();
		
	}

	@Override
	public Object getResult() throws Exception {
		if(buybkAmt == null) {
			throw new Exception("因子Factor10303002未能成功取到值");
		}
		return NumConvertUtil.longToMoney(buybkAmt);
	}
}