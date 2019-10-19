/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10000003
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
import com.szkingdom.data.*;
import com.szkingdom.operation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单个账户对同一合约品种单日进行认购期权和认沽期权的权利仓开仓合计不超过的数量
 * 
 * @author yinhl
 */
public class Factor10000003 extends FactorBase {
	private static final long serialVersionUID = 8589583758835391838L;
	private Logger logger = LoggerFactory.getLogger(Factor10000003.class);
	private DataOptCustPosiLmt dataOptCustPosiLmt;
	private DataOptPosiLmt dataOptPosiLmt;
	private DataOptClsPosiLmt dataOptClsPosiLmt;
	private Character custType;
	private char optCustType;

	private long resultPosiLqty;

	public Factor10000003() {
		super();
	}

	@Override
	public void resolveParameter(JSONObject json) throws Exception {
		dataOptCustPosiLmt = new DataOptCustPosiLmt();
		dataOptPosiLmt = new DataOptPosiLmt();
		dataOptClsPosiLmt = new DataOptClsPosiLmt();

		// 公共入参变量
		JSONObject data = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
		String stkbd = data.getString("STKBD");
		long custCode = Long.parseLong(data.getString("CUST_CODE"));
		String optUndlCode = data.getString("OPT_UNDL_CODE");
		Character optUndlCls = data.getString("OPT_UNDL_CLS").charAt(0);
		Character lsFlag = '@';
		optCustType = data.getString("OPT_CUST_TYPE").charAt(0);

		//成交触发不带CUST_CODE参数，需要自行查表获取
		custType = data.getString("CUST_TYPE").charAt(0);
		if (custType == null) {
			DataCustomer dataCustomer = new DataCustomer();
			dataCustomer.setCustCode(custCode);
			custType = OperationCustomer.getCustomerType(dataCustomer).charAt(0);
		}

		dataOptCustPosiLmt.setCustCode(custCode);
		dataOptCustPosiLmt.setTrdacct(data.getString("TRDACCT"));
		dataOptCustPosiLmt.setStkbd(stkbd);
		dataOptCustPosiLmt.setOptUndlCode(optUndlCode);
		dataOptCustPosiLmt.setOptUndlCls(optUndlCls);
		dataOptCustPosiLmt.setLsFlag(lsFlag);

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
			char custLmtAttr = '0';

			/**
			 * A 单标的单一个人账户总持仓限额 <br>
			 * B 单标的单一个人账户权利仓持仓限额<br>
			 * C 单标的单一机构账户总持仓限额<br>
			 * D 单标的单一机构账户权利仓持仓限额<br>
			 * E 单标的单一自营账户总持仓限额<br>
			 * F 单标的单一自营账户权利仓持仓限额 <br>
			 * G 单标的单一做市商账户总持仓限额<br>
			 * H 单标的单一做市商账户权利仓持仓限额 <br>
			 * K 单标的单一个人账户当日累计买入开仓限额 <br>
			 * L 单标的单一机构账户当日累计买入开仓限额 <br>
			 * M 单标的单一自营账户当日累计买入开仓限额 <br>
			 * N 单标的单一做市商账户当日累计买入开仓限额<br>
			 * 注：做市商类业务当前不作处理
			 *
			 * 客户类型：0-普通 1-自营 2-资管 3-QFII
			 * 用户类型：0-个人 1-机构 2=设备
			 */
			if (custType == ConstantUtil.CUST_TYPE_NORMAL) {
				if (optCustType == ConstantUtil.OPT_CUST_TYPE_PERSON) {
					custLmtAttr = ConstantUtil.PLA_SINGLE_PERSON_DAILY_BUY;
				}
				else if (optCustType == ConstantUtil.OPT_CUST_TYPE_ORG) {
					custLmtAttr = ConstantUtil.PLA_SINGLE_INST_DAILY_BUY;
				}
			}
			else if (custType == ConstantUtil.CUST_TYPE_SELF_BUSI) {
				custLmtAttr = ConstantUtil.PLA_SINGLE_SECU_DAILY_BUY;
			}

			if (custLmtAttr == '0') {
				throw new Exception("获取客户持仓限制属性CUST_LMT_ATTR字段异常");
			}

			dataOptCustPosiLmt.setCustLmtAttr(custLmtAttr);
			DataOptCustPosiLmt resultDataOptCustPosiLmt = OperationOptCustPosiLmt.getOptCustPosiLmtDataWithCache(dataOptCustPosiLmt);
			if (resultDataOptCustPosiLmt != null) {
				resultPosiLqty = resultDataOptCustPosiLmt.getOptPosiLqty();
				if (resultPosiLqty > 0) {
					return;
				}
			}

			dataOptPosiLmt.setPosiLmtAttr(custLmtAttr);
			DataOptPosiLmt resultDataOptPosiLmt = OperationOptPosiLmt.getOptPosiLmtDataWithCache(dataOptPosiLmt);
			if (resultDataOptPosiLmt != null) {
				resultPosiLqty = resultDataOptPosiLmt.getOptPosiLqty();
				if (resultPosiLqty > 0) {
					return;
				}
			}

			dataOptClsPosiLmt.setPosiLmtAttr(custLmtAttr);
			DataOptClsPosiLmt resultDataOptClsPosiLmt = OperationOptClsPosiLmt.getOptPosiClsLmtDataWithCache(dataOptClsPosiLmt);
			if (resultDataOptClsPosiLmt != null) {
				resultPosiLqty = resultDataOptClsPosiLmt.getOptPosiLqty();
				if (resultPosiLqty > 0) {
					return;
				}
			}

		}catch (Exception e){
			throw new Exception("获取持仓限额发生异常", e);
		}
	}

	@Override
	public Object getResult() throws Exception {
		if (resultPosiLqty > 0) {
			return resultPosiLqty;
		}
		else {
			throw new Exception("获取持仓限额数量取值为0，请检查额度设置");
		}
	}
}