/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10000004
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
import com.szkingdom.data.DataOptClsPosiLmt;
import com.szkingdom.data.DataOptPosiLmt;
import com.szkingdom.operation.OperationOptClsPosiLmt;
import com.szkingdom.operation.OperationOptPosiLmt;

/**
 * 证券公司经纪业务持有同一合约品种的认购期权和认沽期权合约的权利仓、义务仓、备兑仓合计不超过的数量
 * 
 * @author ZhengMingjie
 */
public class Factor10000004 extends FactorBase {
	private static final long serialVersionUID = 9207441374758576228L;
	private DataOptPosiLmt dataOptPosiLmt;
	private DataOptClsPosiLmt dataOptClsPosiLmt;

	private long resultPosiLqty;

	public Factor10000004() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		dataOptPosiLmt = new DataOptPosiLmt();
		dataOptClsPosiLmt = new DataOptClsPosiLmt();

		// 公共入参变量
		JSONObject data = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
		String stkbd = data.getString("STKBD");
		String optUndlCode = data.getString("OPT_UNDL_CODE");
		Character optUndlCls = data.getString("OPT_UNDL_CLS").charAt(0);
		Character lsFlag = '@';

		dataOptPosiLmt.setStkbd(stkbd);
		dataOptPosiLmt.setOptUndlCode(optUndlCode);
		dataOptPosiLmt.setLsFlag(lsFlag);

		dataOptClsPosiLmt.setStkbd(stkbd);
		dataOptClsPosiLmt.setOptUndlCls(optUndlCls);
		dataOptClsPosiLmt.setLsFlag(lsFlag);

	}

	@Override
	public void handleFactor() throws Exception {
		try{
			// posiLmtAttr字段在该因子中统一设置为I	单标的会员经纪总持仓限额
			char posiLmtAttr = ConstantUtil.PLA_MEMBER_BROKER;

			dataOptPosiLmt.setPosiLmtAttr(posiLmtAttr);
			DataOptPosiLmt resultDataOptPosiLmt = OperationOptPosiLmt.getOptPosiLmtDataWithCache(dataOptPosiLmt);
			if (resultDataOptPosiLmt != null) {
				resultPosiLqty = resultDataOptPosiLmt.getOptPosiLqty();
				if (resultPosiLqty > 0) {
					return;
				}
			}

			dataOptClsPosiLmt.setPosiLmtAttr(posiLmtAttr);
			DataOptClsPosiLmt resultDataOptClsPosiLmt = OperationOptClsPosiLmt.getOptPosiClsLmtDataWithCache(dataOptClsPosiLmt);
			if (resultDataOptClsPosiLmt != null) {
				resultPosiLqty = resultDataOptClsPosiLmt.getOptPosiLqty();
				if (resultPosiLqty > 0) {
					return;
				}
			}

		}catch (Exception e){
			throw new Exception("获取经济业务持仓限额发生异常", e);
		}
	}

	@Override
	public Object getResult() throws Exception {
		if (resultPosiLqty > 0) {
			return resultPosiLqty;
		}
		else {
			throw new Exception("获取经济业务持仓限额数量取值为0，请检查额度设置");
		}
	}
}