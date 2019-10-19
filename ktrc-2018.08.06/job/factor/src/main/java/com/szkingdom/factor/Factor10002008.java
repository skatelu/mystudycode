/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10002008
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
import com.szkingdom.data.DataOptCustUndlPosi;
import com.szkingdom.operation.OperationOptCustUndlPosi;

import java.util.List;

/**
 * 证券公司经纪业务持有同一合约品种的认购和认沽期权合约的已持有权利仓、义务仓、备兑仓与开仓申报未成交数量（撤单需扣减）合计数量
 * 
 * @author yinhl
 */
public class Factor10002008 extends FactorBase {
	private static final long serialVersionUID = 8255212684787073944L;
	private DataOptCustUndlPosi dataOptCustUndlPosi;

	private long sumMemberPosiTotal = 0L;

	public Factor10002008() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		this.dataOptCustUndlPosi = new DataOptCustUndlPosi();

		JSONObject data = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
		// 该指标获取单标的所有持仓，查表入参不包含客户级信息
		this.dataOptCustUndlPosi.setStkbd(data.getString("STKBD"));
		this.dataOptCustUndlPosi.setOptUndlCode(data.getString("OPT_UNDL_CODE"));
	}

	@Override
	public void handleFactor() throws Exception {
		try {
			List<DataOptCustUndlPosi> listOptCustUndlPosi = OperationOptCustUndlPosi.listOptCustUndlPosiDataByParam(dataOptCustUndlPosi);
			if (listOptCustUndlPosi.size() > 0){
				for (DataOptCustUndlPosi dataOptCustUndlPosi : listOptCustUndlPosi) {
					sumMemberPosiTotal += dataOptCustUndlPosi.getTotalPosi();
				}
			}
		}catch (Exception e){
			throw new Exception("查表OPT_CUST_UNDL_POSI获取单标的经济总持仓异常", e);
		}
	}

	@Override
	public Object getResult() throws Exception {
		return sumMemberPosiTotal;
	}
}