/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptMatching
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataOptMatching extends DataBase {

	private static final long serialVersionUID = -6055100184403988731L;
	private int trdDate;
	private String matchedTime;
	private int orderDate;
	private String orderId;
	private short intOrg;
	private long custCode;
	private String custName;
	private long cuacctCode;
	private Character currency;
	private String stkbd;
	private String stkpbu;
	private String trdacct;
	private String subacctCode;
	private short stkBiz;
	private short stkBizAction;
	private String optNum;
	private String optName;
	private Character optType;
	private String combNum;
	private String combStraCode;
	private String leg1Num;
	private String leg2Num;
	private Character optUndlCls;
	private String optUndlCode;
	private String optUndlName;
	private Character isWithdraw;
	private Character matchedType;
	private String matchedSn;
	private long matchedPrice;
	private long matchedQty;

	public DataOptMatching() {
		init();
	}

	public DataOptMatching(JSONObject json) {
	}

	public void init() {
		this.trdDate = 0;
		this.matchedTime = "";
		this.orderDate = 0;
		this.orderId = "";
		this.intOrg = 0;
		this.custCode = 0L;
		this.custName = "";
		this.cuacctCode = 0L;
		this.currency = null;
		this.stkbd = "";
		this.stkpbu = "";
		this.trdacct = "";
		this.subacctCode = "";
		this.stkBiz = 0;
		this.stkBizAction = 0;
		this.optNum = "";
		this.optName = "";
		this.optType = null;
		this.combNum = "";
		this.combStraCode = "";
		this.leg1Num = "";
		this.leg2Num = "";
		this.optUndlCls = null;
		this.optUndlCode = "";
		this.optUndlName = "";
		this.isWithdraw = null;
		this.matchedType = null;
		this.matchedSn = "";
		this.matchedPrice = 0L;
		this.matchedQty = 0L;
	}

	public void setTrdDate(int trdDate) {
		this.trdDate = trdDate;
	}

	public int getTrdDate() {
		return this.trdDate;
	}

	public void setMatchedTime(String matchedTime) {
		this.matchedTime = matchedTime;
	}

	public String getMatchedTime() {
		return this.matchedTime;
	}

	public void setOrderDate(int orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderDate() {
		return this.orderDate;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return this.orderId;
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

	public void setIsWithdraw(Character isWithdraw) {
		this.isWithdraw = isWithdraw;
	}

	public Character getIsWithdraw() {
		return this.isWithdraw;
	}

	public void setMatchedType(Character matchedType) {
		this.matchedType = matchedType;
	}

	public Character getMatchedType() {
		return this.matchedType;
	}

	public void setMatchedSn(String matchedSn) {
		this.matchedSn = matchedSn;
	}

	public String getMatchedSn() {
		return this.matchedSn;
	}

	public void setMatchedPrice(long matchedPrice) {
		this.matchedPrice = matchedPrice;
	}

	public long getMatchedPrice() {
		return this.matchedPrice;
	}

	public void setMatchedQty(long matchedQty) {
		this.matchedQty = matchedQty;
	}

	public long getMatchedQty() {
		return this.matchedQty;
	}

}