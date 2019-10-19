/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10002012
 * Author:   ZhangMaohua
 * Date:     2018-07-24
 * Description: 账户所持相关流通证券数量（含备兑持仓）（10002012）
 * 概念描述 :  合约行权交收日，单个账户持有行权交收应交付标的证券的数量。
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataStkAsset;
import com.szkingdom.operation.OperationStkAsset;
import com.szkingdom.operation.OperationStkInfo;


public class Factor10002012 extends FactorBase {
	private static final long serialVersionUID = -7220619796480872717L;
	private DataStkAsset dataStkAsset;
	private long stkBln = 0L;

	public Factor10002012() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception{
		// 参数解析
		JSONObject data = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
		String stkbd = data.getString("STKBD");

		this.dataStkAsset = new DataStkAsset();
		this.dataStkAsset.setStkbd(OperationStkInfo.convertStkbdFromOptToUndl(stkbd));
		this.dataStkAsset.setCuacctCode(Long.parseLong(data.getString("CUACCT_CODE")));
		this.dataStkAsset.setStkCode(data.getString("OPT_UNDL_CODE"));
		this.dataStkAsset.setStkpbu(data.getString("STKPBU"));
		this.dataStkAsset.setTrdacct(data.getString("TRDACCT"));
	}

	@Override
	public void handleFactor() throws Exception{
		DataStkAsset resultDataStkAsset = OperationStkAsset.getStkAssetUnique(dataStkAsset);
		if (resultDataStkAsset != null) {
			stkBln = resultDataStkAsset.getStkBln() + resultDataStkAsset.getStkTrdBln();
		}
	}

	@Override
	public Object getResult() throws Exception{
		return stkBln;
	}
}