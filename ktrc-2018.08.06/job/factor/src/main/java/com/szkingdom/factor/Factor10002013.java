/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10002013
 * Author:   ZhangMaohua
 * Date:     2018-07-24
 * Description: 账户所持相关流通证券数量（不含备兑持仓）（10002013）
 * 概念描述: 合约行权交收日，扣除备兑持仓占用证券数量，单个账户持有行权交收应交付标的证券的数量。
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataOptAsset;
import com.szkingdom.data.DataStkAsset;
import com.szkingdom.operation.OperationOptAsset;
import com.szkingdom.operation.OperationStkAsset;
import com.szkingdom.operation.OperationStkInfo;


public class Factor10002013 extends FactorBase {
	private static final long serialVersionUID = -1789328465803963117L;
	private DataStkAsset dataStkAsset;
	private DataOptAsset dataOptAsset;
	private long stkBlnExpCvd = 0L;

	public Factor10002013() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception{
		JSONObject data = (JSONObject) json.get(ConstantUtil.JSON_KTRC);

		String stkbd = data.getString("STKBD");
		long cuacctCode = Long.parseLong(data.getString("CUACCT_CODE"));
		String stkCode = data.getString("OPT_UNDL_CODE");
		String stkpbu = data.getString("STKPBU");
		String trdacct = data.getString("TRDACCT");

		dataStkAsset = new DataStkAsset();
		dataStkAsset.setTrdacct(trdacct);
		dataStkAsset.setStkbd(OperationStkInfo.convertStkbdFromOptToUndl(stkbd));
		dataStkAsset.setStkpbu(stkpbu);
		dataStkAsset.setStkCode(stkCode);
		dataStkAsset.setCuacctCode(cuacctCode);

		dataOptAsset = new DataOptAsset();
		dataOptAsset.setStkbd(stkbd);
		dataOptAsset.setStkpbu(stkpbu);
		dataOptAsset.setTrdacct(trdacct);
		dataOptAsset.setOptUndlCode(stkCode);
		dataOptAsset.setCuacctCode(cuacctCode);
	}

	@Override
	public void handleFactor() throws Exception{
		DataStkAsset resultDataStkAsset = OperationStkAsset.getStkAssetUnique(dataStkAsset);
		long optCvdAsset = OperationOptAsset.getOptCvdAssetByOptAsset(dataOptAsset);
		if(resultDataStkAsset != null){
			stkBlnExpCvd = resultDataStkAsset.getStkBln() + resultDataStkAsset.getStkTrdBln() - optCvdAsset;
		}
	}

	@Override
	public Object getResult() throws Exception{
		return stkBlnExpCvd;
	}
}