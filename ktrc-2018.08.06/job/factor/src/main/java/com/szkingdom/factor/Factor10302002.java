/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10302002
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
import com.szkingdom.dao.DaoAgrBuybkContracts;
import com.szkingdom.data.DataAgrBuybkContracts;

/**
 * 约定购回式证券交易到期时间
 * 
 * @author yinhl
 */
public class Factor10302002 extends FactorBase {
	private static final long serialVersionUID = 4963974460940703619L;
	
	private Integer buybkDate;
	private DataAgrBuybkContracts dataAgrBuybkContracts;

	public Factor10302002() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject jsonObject) throws Exception {
		
		JSONObject json = jsonObject.getJSONObject(ConstantUtil.JSON_KTRC);
		
		dataAgrBuybkContracts = new DataAgrBuybkContracts();
		dataAgrBuybkContracts.setTrdDate(json.getIntValue("TRD_DATE"));
		//dataAgrBuybkContracts.setCustCode(json.getString(""));
		dataAgrBuybkContracts.setTrdacct(json.getString("TRDACCT"));
		dataAgrBuybkContracts.setStkbd(json.getString("STKBD"));
		dataAgrBuybkContracts.setStkex(json.getString("STKEX").charAt(0));
		dataAgrBuybkContracts.setMatchSn(json.getString("MATCH_SN"));
		dataAgrBuybkContracts.setStkCode(json.getString("STK_CODE"));

	}

	@Override
	public void handleFactor() throws Exception {
		DaoAgrBuybkContracts dao = new DaoAgrBuybkContracts();
		DataAgrBuybkContracts retData = dao.selectTrdDate(dataAgrBuybkContracts);
		buybkDate = retData.getBuybkDate();
	}

	@Override
	public Object getResult() throws Exception {
		if(buybkDate == null) {
			throw new Exception("因子Factor10302002未能成功取到值");
		}
		return buybkDate;
	}
}