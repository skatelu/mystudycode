/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataAgrBuybkContracts
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataAgrBuybkContracts extends DataBase {

	private static final long serialVersionUID = -6760413463046698684L;
	private int addDate;
	private int trdDate;
	private int buybkDate;
	private long custCode;
	private Character custCls;
	private long cuacctCode;
	private Character currency;
	private Character cuacctCls;
	private short intOrg;
	private String trdacctName;
	private String trdacct;
	private Character stkex;
	private String stkbd;
	private String orderId;
	private String stkpbu;
	private String matchSn;
	private String stkCode;
	private String stkName;
	private Character stkCls;
	private short stkBiz;
	private short stkBizAction;
	private long orderPrice;
	private long contAmt;
	private long buybkPrice;
	private long buybkQty;
	private long buybkAmt;
	private Character buybkStatus;
	private Character performFlag;
	private long performAmt;
	private long stkTrdFrz;
	private long trdFrzAmt;
	private String stkUndlCode;
	private int bizCode;
	private long pathNo;
	private String agrBuybkVastCode;
	private long buybkRate;
	private long undlConRate;
	private Character conFlag;
	private int delayBuybkCount;
	private int cptContTrdDate;
	private String cptContMatchSn;

	public DataAgrBuybkContracts() {
	}

	public DataAgrBuybkContracts(JSONObject json) {
	}

	public void init() {
		this.addDate = 0;
		this.trdDate = 0;
		this.buybkDate = 0;
		this.custCode = 0L;
		this.custCls = null;
		this.cuacctCode = 0L;
		this.currency = null;
		this.cuacctCls = null;
		this.intOrg = 0;
		this.trdacctName = "";
		this.trdacct = "";
		this.stkex = null;
		this.stkbd = "";
		this.orderId = "";
		this.stkpbu = "";
		this.matchSn = "";
		this.stkCode = "";
		this.stkName = "";
		this.stkCls = null;
		this.stkBiz = 0;
		this.stkBizAction = 0;
		this.orderPrice = 0L;
		this.contAmt = 0L;
		this.buybkPrice = 0L;
		this.buybkQty = 0L;
		this.buybkAmt = 0L;
		this.buybkStatus = null;
		this.performFlag = null;
		this.performAmt = 0L;
		this.stkTrdFrz = 0L;
		this.trdFrzAmt = 0L;
		this.stkUndlCode = "";
		this.bizCode = 0;
		this.pathNo = 0L;
		this.agrBuybkVastCode = "";
		this.buybkRate = 0L;
		this.undlConRate = 0L;
		this.conFlag = null;
		this.delayBuybkCount = 0;
		this.cptContTrdDate = 0;
		this.cptContMatchSn = "";

	}

	public void setAddDate(int addDate) {
		this.addDate = addDate;
	}

	public int getAddDate() {
		return this.addDate;
	}

	public void setTrdDate(int trdDate) {
		this.trdDate = trdDate;
	}

	public int getTrdDate() {
		return this.trdDate;
	}

	public void setBuybkDate(int buybkDate) {
		this.buybkDate = buybkDate;
	}

	public int getBuybkDate() {
		return this.buybkDate;
	}

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
	}

	public void setCustCls(Character custCls) {
		this.custCls = custCls;
	}

	public Character getCustCls() {
		return this.custCls;
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

	public void setCuacctCls(Character cuacctCls) {
		this.cuacctCls = cuacctCls;
	}

	public Character getCuacctCls() {
		return this.cuacctCls;
	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public short getIntOrg() {
		return this.intOrg;
	}

	public void setTrdacctName(String trdacctName) {
		this.trdacctName = trdacctName;
	}

	public String getTrdacctName() {
		return this.trdacctName;
	}

	public void setTrdacct(String trdacct) {
		this.trdacct = trdacct;
	}

	public String getTrdacct() {
		return this.trdacct;
	}

	public void setStkex(Character stkex) {
		this.stkex = stkex;
	}

	public Character getStkex() {
		return this.stkex;
	}

	public void setStkbd(String stkbd) {
		this.stkbd = stkbd;
	}

	public String getStkbd() {
		return this.stkbd;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setStkpbu(String stkpbu) {
		this.stkpbu = stkpbu;
	}

	public String getStkpbu() {
		return this.stkpbu;
	}

	public void setMatchSn(String matchSn) {
		this.matchSn = matchSn;
	}

	public String getMatchSn() {
		return this.matchSn;
	}

	public void setStkCode(String stkCode) {
		this.stkCode = stkCode;
	}

	public String getStkCode() {
		return this.stkCode;
	}

	public void setStkName(String stkName) {
		this.stkName = stkName;
	}

	public String getStkName() {
		return this.stkName;
	}

	public void setStkCls(Character stkCls) {
		this.stkCls = stkCls;
	}

	public Character getStkCls() {
		return this.stkCls;
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

	public void setOrderPrice(long orderPrice) {
		this.orderPrice = orderPrice;
	}

	public long getOrderPrice() {
		return this.orderPrice;
	}

	public void setContAmt(long contAmt) {
		this.contAmt = contAmt;
	}

	public long getContAmt() {
		return this.contAmt;
	}

	public void setBuybkPrice(long buybkPrice) {
		this.buybkPrice = buybkPrice;
	}

	public long getBuybkPrice() {
		return this.buybkPrice;
	}

	public void setBuybkQty(long buybkQty) {
		this.buybkQty = buybkQty;
	}

	public long getBuybkQty() {
		return this.buybkQty;
	}

	public void setBuybkAmt(long buybkAmt) {
		this.buybkAmt = buybkAmt;
	}

	public long getBuybkAmt() {
		return this.buybkAmt;
	}

	public void setBuybkStatus(Character buybkStatus) {
		this.buybkStatus = buybkStatus;
	}

	public Character getBuybkStatus() {
		return this.buybkStatus;
	}

	public void setPerformFlag(Character performFlag) {
		this.performFlag = performFlag;
	}

	public Character getPerformFlag() {
		return this.performFlag;
	}

	public void setPerformAmt(long performAmt) {
		this.performAmt = performAmt;
	}

	public long getPerformAmt() {
		return this.performAmt;
	}

	public void setStkTrdFrz(long stkTrdFrz) {
		this.stkTrdFrz = stkTrdFrz;
	}

	public long getStkTrdFrz() {
		return this.stkTrdFrz;
	}

	public void setTrdFrzAmt(long trdFrzAmt) {
		this.trdFrzAmt = trdFrzAmt;
	}

	public long getTrdFrzAmt() {
		return this.trdFrzAmt;
	}

	public void setStkUndlCode(String stkUndlCode) {
		this.stkUndlCode = stkUndlCode;
	}

	public String getStkUndlCode() {
		return this.stkUndlCode;
	}

	public void setBizCode(int bizCode) {
		this.bizCode = bizCode;
	}

	public int getBizCode() {
		return this.bizCode;
	}

	public void setPathNo(long pathNo) {
		this.pathNo = pathNo;
	}

	public long getPathNo() {
		return this.pathNo;
	}

	public void setAgrBuybkVastCode(String agrBuybkVastCode) {
		this.agrBuybkVastCode = agrBuybkVastCode;
	}

	public String getAgrBuybkVastCode() {
		return this.agrBuybkVastCode;
	}

	public void setBuybkRate(long buybkRate) {
		this.buybkRate = buybkRate;
	}

	public long getBuybkRate() {
		return this.buybkRate;
	}

	public void setUndlConRate(long undlConRate) {
		this.undlConRate = undlConRate;
	}

	public long getUndlConRate() {
		return this.undlConRate;
	}

	public void setConFlag(Character conFlag) {
		this.conFlag = conFlag;
	}

	public Character getConFlag() {
		return this.conFlag;
	}

	public void setDelayBuybkCount(int delayBuybkCount) {
		this.delayBuybkCount = delayBuybkCount;
	}

	public int getDelayBuybkCount() {
		return this.delayBuybkCount;
	}

	public void setCptContTrdDate(int cptContTrdDate) {
		this.cptContTrdDate = cptContTrdDate;
	}

	public int getCptContTrdDate() {
		return this.cptContTrdDate;
	}

	public void setCptContMatchSn(String cptContMatchSn) {
		this.cptContMatchSn = cptContMatchSn;
	}

	public String getCptContMatchSn() {
		return this.cptContMatchSn;
	}

}