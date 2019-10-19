/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10303001
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
import com.szkingdom.dao.DaoStkMktinfo;
import com.szkingdom.data.DataAgrBuybkContracts;
import com.szkingdom.data.DataStkMktinfo;

/**
 * 单笔交易或合并管理的多笔交易的标的证券市值
 * 
 * @author yinhl
 */
public class Factor10303001 extends FactorBase {
	private static final long serialVersionUID = -5438489488947541136L;
	
	private DataAgrBuybkContracts dataAgrBuybkContracts;
	private DataStkMktinfo dataStkMktinfo;
	private Long buybkQty;
	private Long currentPrice;

	public Factor10303001() {
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

		dataStkMktinfo = new DataStkMktinfo();
		dataStkMktinfo.setStkbd(dataAgrBuybkContracts.getStkbd());
		dataStkMktinfo.setStkCode(dataAgrBuybkContracts.getStkCode());
		dataStkMktinfo.setStkex(dataAgrBuybkContracts.getStkex());
	}

	@Override
	public void handleFactor() throws Exception {
		// 取数量
		DaoAgrBuybkContracts daoContracts = new DaoAgrBuybkContracts();
		DataAgrBuybkContracts retData = daoContracts.selectBuybkQty(dataAgrBuybkContracts);
		buybkQty = retData.getBuybkQty();
		
		// 取实时价格
		DaoStkMktinfo daoMktInfo = new DaoStkMktinfo();
		DataStkMktinfo dataStkMktinfo = daoMktInfo.selectUnique(this.dataStkMktinfo);
		currentPrice = dataStkMktinfo.getCurrentPrice();
	}

	@Override
	public Object getResult() throws Exception {
		if(buybkQty == null || currentPrice == null) {
			throw new Exception("因子Factor10303001未能成功取到值");
		}
		return buybkQty * NumConvertUtil.longToPrice(currentPrice);
	}
}