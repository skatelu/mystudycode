/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptOrder
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class DataOptOrder extends DataBase {

	private static final long serialVersionUID = 6207330622703895621L;
	private int trdDate;
	private int orderDate;
	private Date orderTime;
	private String orderId;
	private Character orderStatus;
	private short intOrg;
	private long custCode;
	private String custName;
	private Character custType;
	private long cuacctCode;
	private Character currency;
	private String stkbd;
	private String stkpbu;
	private String trdacct;
	private String subacctCode;
	private Character optTrdacctLvl;
	private short stkBiz;
	private short stkBizAction;
	private String optNum;
	private String optName;
	private Character optType;
	private String combNum;
	private String combStraCode;
	private String leg1Num;
	private String leg2Num;
	private long orderPrice;
	private long orderQty;
	private long orderFrzAmt;
	private Character isWithdraw;
	private Character optUndlCls;
	private String optUndlCode;
	private String optUndlName;
	private String rawOrderId;

	public DataOptOrder() {
		init();
	}

	public DataOptOrder(JSONObject json) {
	}

	public void init() {
		this.trdDate = 0;
		this.orderDate = 0;
		this.orderTime = new Date();
		this.orderId = "";
		this.orderStatus = null;
		this.intOrg = 0;
		this.custCode = 0L;
		this.custName = "";
		this.custType = null;
		this.cuacctCode = 0L;
		this.currency = null;
		this.stkbd = "";
		this.stkpbu = "";
		this.trdacct = "";
		this.subacctCode = "";
		this.optTrdacctLvl = null;
		this.stkBiz = 0;
		this.stkBizAction = 0;
		this.optNum = "";
		this.optName = "";
		this.optType = null;
		this.combNum = "";
		this.combStraCode = "";
		this.leg1Num = "";
		this.leg2Num = "";
		this.orderPrice = 0L;
		this.orderQty = 0L;
		this.orderFrzAmt = 0L;
		this.isWithdraw = null;
		this.optUndlCls = null;
		this.optUndlCode = "";
		this.optUndlName = "";
		this.rawOrderId = "";
	}

	public void setTrdDate(int trdDate) {
		this.trdDate = trdDate;
	}

	public int getTrdDate() {
		return this.trdDate;
	}

	public void setOrderDate(int orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderDate() {
		return this.orderDate;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderStatus(Character orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Character getOrderStatus() {
		return this.orderStatus;
	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public short getIntOrg() {
		return this.intOrg;
	}

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustType(Character custType) {
		this.custType = custType;
	}

	public Character getCustType() {
		return this.custType;
	}

	public void setCuacctCode(long cuacctCode) {
		this.cuacctCode = cuacctCode;
	}

	public long getCuacctCode() {
		return this.cuacctCode;
	}

	public void setCurrency(Character currency) {
		this.currency = currency;
	}

	public Character getCurrency() {
		return this.currency;
	}

	public void setStkbd(String stkbd) {
		this.stkbd = stkbd;
	}

	public String getStkbd() {
		return this.stkbd;
	}

	public void setStkpbu(String stkpbu) {
		this.stkpbu = stkpbu;
	}

	public String getStkpbu() {
		return this.stkpbu;
	}

	public void setTrdacct(String trdacct) {
		this.trdacct = trdacct;
	}

	public String getTrdacct() {
		return this.trdacct;
	}

	public void setSubacctCode(String subacctCode) {
		this.subacctCode = subacctCode;
	}

	public String getSubacctCode() {
		return this.subacctCode;
	}

	public void setOptTrdacctLvl(Character optTrdacctLvl) {
		this.optTrdacctLvl = optTrdacctLvl;
	}

	public Character getOptTrdacctLvl() {
		return this.optTrdacctLvl;
	}

	public void setStkBiz(short stkBiz) {
		this.stkBiz = stkBiz;
	}

	public short getStkBiz() {
		return this.stkBiz;
	}

	public void setStkBizAction(short stkBizAction) {
		this.stkBizAction = stkBizAction;
	}

	public short getStkBizAction() {
		return this.stkBizAction;
	}

	public void setOptNum(String optNum) {
		this.optNum = optNum;
	}

	public String getOptNum() {
		return this.optNum;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getOptName() {
		return this.optName;
	}

	public void setOptType(Character optType) {
		this.optType = optType;
	}

	public Character getOptType() {
		return this.optType;
	}

	public void setCombNum(String combNum) {
		this.combNum = combNum;
	}

	public String getCombNum() {
		return this.combNum;
	}

	public void setCombStraCode(String combStraCode) {
		this.combStraCode = combStraCode;
	}

	public String getCombStraCode() {
		return this.combStraCode;
	}

	public void setLeg1Num(String leg1Num) {
		this.leg1Num = leg1Num;
	}

	public String getLeg1Num() {
		return this.leg1Num;
	}

	public void setLeg2Num(String leg2Num) {
		this.leg2Num = leg2Num;
	}

	public String getLeg2Num() {
		return this.leg2Num;
	}

	public void setOrderPrice(long orderPrice) {
		this.orderPrice = orderPrice;
	}

	public long getOrderPrice() {
		return this.orderPrice;
	}

	public void setOrderQty(long orderQty) {
		this.orderQty = orderQty;
	}

	public long getOrderQty() {
		return this.orderQty;
	}

	public void setOrderFrzAmt(long orderFrzAmt) {
		this.orderFrzAmt = orderFrzAmt;
	}

	public long getOrderFrzAmt() {
		return this.orderFrzAmt;
	}

	public void setIsWithdraw(Character isWithdraw) {
		this.isWithdraw = isWithdraw;
	}

	public Character getIsWithdraw() {
		return this.isWithdraw;
	}

	public void setOptUndlCls(Character optUndlCls) {
		this.optUndlCls = optUndlCls;
	}

	public Character getOptUndlCls() {
		return this.optUndlCls;
	}

	public void setOptUndlCode(String optUndlCode) {
		this.optUndlCode = optUndlCode;
	}

	public String getOptUndlCode() {
		return this.optUndlCode;
	}

	public void setOptUndlName(String optUndlName) {
		this.optUndlName = optUndlName;
	}

	public String getOptUndlName() {
		return this.optUndlName;
	}

	public void setRawOrderId(String rawOrderId) {
		this.rawOrderId = rawOrderId;
	}

	public String getRawOrderId() {
		return this.rawOrderId;
	}

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("CUST_NAME", custName);
		json.put("CUST_TYPE", custType);
		return json;
	}
}