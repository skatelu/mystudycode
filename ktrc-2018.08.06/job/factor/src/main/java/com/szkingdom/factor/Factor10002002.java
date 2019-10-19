/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10002002
 * Author:   yinhl
 * Date:     2018-07-24
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataOptAsset;
import com.szkingdom.operation.OperationOptAsset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 单个账户对同一合约品种的认购和认沽期权合约的已持有权利仓与买入开仓申报未成交数量（撤单需扣减）合计数量
 * 
 * @author yinhl
 */
public class Factor10002002 extends FactorBase {
	private static final long serialVersionUID = 8287789101795379841L;
	private Logger logger = LoggerFactory.getLogger(Factor10002002.class);
	private DataOptAsset dataOptAsset;

	private long sumOptPosiRltSingleLongOpen = 0L;

	public Factor10002002() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		this.dataOptAsset = new DataOptAsset();

		JSONObject data = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
		this.dataOptAsset.setStkbd(data.getString("STKBD"));
		this.dataOptAsset.setCustCode(Long.parseLong(data.getString("CUST_CODE")));
		this.dataOptAsset.setOptUndlCode(data.getString("OPT_UNDL_CODE"));
		this.dataOptAsset.setTrdacct(data.getString("TRDACCT"));
		this.dataOptAsset.setCuacctCode(Long.parseLong(data.getString("CUACCT_CODE")));
		this.dataOptAsset.setOptSide(ConstantUtil.OPT_LONG);
	}

	@Override
	public void handleFactor() throws Exception {
		try {
			List<DataOptAsset> listOptAsset = OperationOptAsset.listOptAssetDataByParam(dataOptAsset);
			if (listOptAsset.size() > 0){
				for (DataOptAsset dataOptAssetResult : listOptAsset) {
					sumOptPosiRltSingleLongOpen += dataOptAssetResult.getOptPosiRlt();
				}
			}
		}catch (Exception e){
			throw new Exception("查表OPT_ASSET获取权利仓异常", e);
		}
	}

	@Override
	public Object getResult() throws Exception {
		return sumOptPosiRltSingleLongOpen;
	}
}