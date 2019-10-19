/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10002010
 * Author:   ZhengMingjie
 * Date:     2018-08-01
 * Description: 单账户标的证券持仓数量
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Factor10002010 extends FactorBase {
	private static final long serialVersionUID = -697665115470171033L;
	private Logger logger = LoggerFactory.getLogger(Factor10002010.class);
	private long cvtQtyAvl = 0L;
	private DataStkAsset dataStkAsset;

	public Factor10002010() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
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
	public void handleFactor() throws Exception {
		// 根据因子设计文档，该因子实际计算取值为该客户该标的证券的持仓数量
		DataStkAsset resultDataStkAsset = OperationStkAsset.getStkAssetUnique(dataStkAsset);
		if (resultDataStkAsset != null) {
			cvtQtyAvl = resultDataStkAsset.getStkBln() + resultDataStkAsset.getStkTrdBln();
		}
	}

	@Override
	public Object getResult() throws Exception {
		return cvtQtyAvl;
	}
}