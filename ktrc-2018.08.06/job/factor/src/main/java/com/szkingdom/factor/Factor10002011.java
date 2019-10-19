/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10002011
 * Author:   ZhangMaohua
 * Date:     2018-07-24
 * Description: 账户行权交收应交券数量（10002011）
 * 概念描述: 合约行权交收日，单个账户行权交收应交付标的证券数量。
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.data.DataOptPerbizData;
import com.szkingdom.operation.OperationOptPerbizData;

public class Factor10002011 extends FactorBase {
	private static final long serialVersionUID = 4725318863335343578L;
	private DataOptPerbizData dataOptPerbizData;
	private long stkNetDlvyQty = 0L;

	public Factor10002011(){
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		dataOptPerbizData = new DataOptPerbizData();

		JSONObject data = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
		dataOptPerbizData.setCustCode(Long.parseLong(data.getString("CUST_CODE")));
		dataOptPerbizData.setStkbd(data.getString("STKBD"));
		dataOptPerbizData.setTrdacct(data.getString("TRDACCT"));
		dataOptPerbizData.setOptUndlCode(data.getString("OPT_UNDL_CODE"));
		// STK_BIZ_ACTION IN ('210','211','212','213','214') （SQL语句里）
		// COME_IN_DATE >= currentDate
		dataOptPerbizData.setComeInDate(DateUtils.getCurrDate());
	}

	@Override
	public void handleFactor() throws Exception {
		stkNetDlvyQty = OperationOptPerbizData.getOrderQtyByOptPerbizData(dataOptPerbizData);
	}

	@Override
	public Object getResult() throws Exception {
		return stkNetDlvyQty;
	}
}