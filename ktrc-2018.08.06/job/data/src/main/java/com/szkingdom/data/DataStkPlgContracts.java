/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkPlgContracts
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStkPlgContracts extends DataBase {

	private static final long serialVersionUID = -6510954596060903491L;
	private int trdDate;
	private String stkbd;
	private String constrSn;
	private String trdacctIn;
	private long custCodeIn;
	private long cuacctIn;
	private Character currencyIn;
	private String trdacctOut;
	private long custCodeOut;
	private long cuacctOut;
	private Character currencyOut;
	private Character lenderCls;
	private Character pawneeCls;
	private String orderId;
	private String stkCode;
	private String stkNature;
	private long orgContQty;
	private long hasBuybkQty;
	private long intQty;
	private long buybkQty;
	private long orgContAmt;
	private long fiRate;
	private long fiInterest;
	private long hasBuybkAmt;
	private long bonusAmt;
	private long buybkAmt;
	private int orgBuybkDate;
	private int buybkDate;
	private int fiDeadline;
	private int orgFiDeadline;
	private long orgFiRate;
	private long orgFiQuota;
	private Character contStatus;
	private Character contFlag;
	private int performDate;
	private Character performFlag;
	private long performAmt;
	private long omarketAmt;
	private int handleDate;
	private Character flowType;
	private String rightsCls;
	private String listingYear;
	private Character fiWay;
	private int merTrdDate;
	private String merConstrSn;
	private long cashAmt;
	private long hasContrAmt;
	private String investVart;
	private long otherGuaranteeVal;
	private int calIntDays;
	private Character intSubFlag;
	private long currIntAccr;
	private long currIntAmt;
	private int upDateFeeamtt;
	private int upDateFeeaccru;
	private Character riskRateMode;
	private Character guaranteeRiskMode;

	public DataStkPlgContracts() {
	}

	public DataStkPlgContracts(JSONObject json) {
	}

	public void init() {
		this.trdDate = 0;
		this.stkbd = "";
		this.constrSn = "";
		this.trdacctIn = "";
		this.custCodeIn = 0L;
		this.cuacctIn = 0L;
		this.currencyIn = null;
		this.trdacctOut = "";
		this.custCodeOut = 0L;
		this.cuacctOut = 0L;
		this.currencyOut = null;
		this.lenderCls = null;
		this.pawneeCls = null;
		this.orderId = "";
		this.stkCode = "";
		this.stkNature = "";
		this.orgContQty = 0L;
		this.hasBuybkQty = 0L;
		this.intQty = 0L;
		this.buybkQty = 0L;
		this.orgContAmt = 0L;
		this.fiRate = 0L;
		this.fiInterest = 0L;
		this.hasBuybkAmt = 0L;
		this.bonusAmt = 0L;
		this.buybkAmt = 0L;
		this.orgBuybkDate = 0;
		this.buybkDate = 0;
		this.fiDeadline = 0;
		this.orgFiDeadline = 0;
		this.orgFiRate = 0L;
		this.orgFiQuota = 0L;
		this.contStatus = null;
		this.contFlag = null;
		this.performDate = 0;
		this.performFlag = null;
		this.performAmt = 0L;
		this.omarketAmt = 0L;
		this.handleDate = 0;
		this.flowType = null;
		this.rightsCls = "";
		this.listingYear = "";
		this.fiWay = null;
		this.merTrdDate = 0;
		this.merConstrSn = "";
		this.cashAmt = 0L;
		this.hasContrAmt = 0L;
		this.investVart = "";
		this.otherGuaranteeVal = 0L;
		this.calIntDays = 0;
		this.intSubFlag = null;
		this.currIntAccr = 0L;
		this.currIntAmt = 0L;
		this.upDateFeeamtt = 0;
		this.upDateFeeaccru = 0;
		this.riskRateMode = null;
		this.guaranteeRiskMode = null;

	}

	public void setTrdDate(int trdDate) {
		this.trdDate = trdDate;
	}

	public int getTrdDate() {
		return this.trdDate;
	}

	public void setStkbd(String stkbd) {
		this.stkbd = stkbd;
	}

	public String getStkbd() {
		return this.stkbd;
	}

	public void setConstrSn(String constrSn) {
		this.constrSn = constrSn;
	}

	public String getConstrSn() {
		return this.constrSn;
	}

	public void setTrdacctIn(String trdacctIn) {
		this.trdacctIn = trdacctIn;
	}

	public String getTrdacctIn() {
		return this.trdacctIn;
	}

	public void setCustCodeIn(long custCodeIn) {
		this.custCodeIn = custCodeIn;
	}

	public long getCustCodeIn() {
		return this.custCodeIn;
	}

	public void setCuacctIn(long cuacctIn) {
		this.cuacctIn = cuacctIn;
	}

	public long getCuacctIn() {
		return this.cuacctIn;
	}

	public void setCurrencyIn(Character currencyIn) {
		this.currencyIn = currencyIn;
	}

	public Character getCurrencyIn() {
		return this.currencyIn;
	}

	public void setTrdacctOut(String trdacctOut) {
		this.trdacctOut = trdacctOut;
	}

	public String getTrdacctOut() {
		return this.trdacctOut;
	}

	public void setCustCodeOut(long custCodeOut) {
		this.custCodeOut = custCodeOut;
	}

	public long getCustCodeOut() {
		return this.custCodeOut;
	}

	public void setCuacctOut(long cuacctOut) {
		this.cuacctOut = cuacctOut;
	}

	public long getCuacctOut() {
		return this.cuacctOut;
	}

	public void setCurrencyOut(Character currencyOut) {
		this.currencyOut = currencyOut;
	}

	public Character getCurrencyOut() {
		return this.currencyOut;
	}

	public void setLenderCls(Character lenderCls) {
		this.lenderCls = lenderCls;
	}

	public Character getLenderCls() {
		return this.lenderCls;
	}

	public void setPawneeCls(Character pawneeCls) {
		this.pawneeCls = pawneeCls;
	}

	public Character getPawneeCls() {
		return this.pawneeCls;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setStkCode(String stkCode) {
		this.stkCode = stkCode;
	}

	public String getStkCode() {
		return this.stkCode;
	}

	public void setStkNature(String stkNature) {
		this.stkNature = stkNature;
	}

	public String getStkNature() {
		return this.stkNature;
	}

	public void setOrgContQty(long orgContQty) {
		this.orgContQty = orgContQty;
	}

	public long getOrgContQty() {
		return this.orgContQty;
	}

	public void setHasBuybkQty(long hasBuybkQty) {
		this.hasBuybkQty = hasBuybkQty;
	}

	public long getHasBuybkQty() {
		return this.hasBuybkQty;
	}

	public void setIntQty(long intQty) {
		this.intQty = intQty;
	}

	public long getIntQty() {
		return this.intQty;
	}

	public void setBuybkQty(long buybkQty) {
		this.buybkQty = buybkQty;
	}

	public long getBuybkQty() {
		return this.buybkQty;
	}

	public void setOrgContAmt(long orgContAmt) {
		this.orgContAmt = orgContAmt;
	}

	public long getOrgContAmt() {
		return this.orgContAmt;
	}

	public void setFiRate(long fiRate) {
		this.fiRate = fiRate;
	}

	public long getFiRate() {
		return this.fiRate;
	}

	public void setFiInterest(long fiInterest) {
		this.fiInterest = fiInterest;
	}

	public long getFiInterest() {
		return this.fiInterest;
	}

	public void setHasBuybkAmt(long hasBuybkAmt) {
		this.hasBuybkAmt = hasBuybkAmt;
	}

	public long getHasBuybkAmt() {
		return this.hasBuybkAmt;
	}

	public void setBonusAmt(long bonusAmt) {
		this.bonusAmt = bonusAmt;
	}

	public long getBonusAmt() {
		return this.bonusAmt;
	}

	public void setBuybkAmt(long buybkAmt) {
		this.buybkAmt = buybkAmt;
	}

	public long getBuybkAmt() {
		return this.buybkAmt;
	}

	public void setOrgBuybkDate(int orgBuybkDate) {
		this.orgBuybkDate = orgBuybkDate;
	}

	public int getOrgBuybkDate() {
		return this.orgBuybkDate;
	}

	public void setBuybkDate(int buybkDate) {
		this.buybkDate = buybkDate;
	}

	public int getBuybkDate() {
		return this.buybkDate;
	}

	public void setFiDeadline(int fiDeadline) {
		this.fiDeadline = fiDeadline;
	}

	public int getFiDeadline() {
		return this.fiDeadline;
	}

	public void setOrgFiDeadline(int orgFiDeadline) {
		this.orgFiDeadline = orgFiDeadline;
	}

	public int getOrgFiDeadline() {
		return this.orgFiDeadline;
	}

	public void setOrgFiRate(long orgFiRate) {
		this.orgFiRate = orgFiRate;
	}

	public long getOrgFiRate() {
		return this.orgFiRate;
	}

	public void setOrgFiQuota(long orgFiQuota) {
		this.orgFiQuota = orgFiQuota;
	}

	public long getOrgFiQuota() {
		return this.orgFiQuota;
	}

	public void setContStatus(Character contStatus) {
		this.contStatus = contStatus;
	}

	public Character getContStatus() {
		return this.contStatus;
	}

	public void setContFlag(Character contFlag) {
		this.contFlag = contFlag;
	}

	public Character getContFlag() {
		return this.contFlag;
	}

	public void setPerformDate(int performDate) {
		this.performDate = performDate;
	}

	public int getPerformDate() {
		return this.performDate;
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

	public void setOmarketAmt(long omarketAmt) {
		this.omarketAmt = omarketAmt;
	}

	public long getOmarketAmt() {
		return this.omarketAmt;
	}

	public void setHandleDate(int handleDate) {
		this.handleDate = handleDate;
	}

	public int getHandleDate() {
		return this.handleDate;
	}

	public void setFlowType(Character flowType) {
		this.flowType = flowType;
	}

	public Character getFlowType() {
		return this.flowType;
	}

	public void setRightsCls(String rightsCls) {
		this.rightsCls = rightsCls;
	}

	public String getRightsCls() {
		return this.rightsCls;
	}

	public void setListingYear(String listingYear) {
		this.listingYear = listingYear;
	}

	public String getListingYear() {
		return this.listingYear;
	}

	public void setFiWay(Character fiWay) {
		this.fiWay = fiWay;
	}

	public Character getFiWay() {
		return this.fiWay;
	}

	public void setMerTrdDate(int merTrdDate) {
		this.merTrdDate = merTrdDate;
	}

	public int getMerTrdDate() {
		return this.merTrdDate;
	}

	public void setMerConstrSn(String merConstrSn) {
		this.merConstrSn = merConstrSn;
	}

	public String getMerConstrSn() {
		return this.merConstrSn;
	}

	public void setCashAmt(long cashAmt) {
		this.cashAmt = cashAmt;
	}

	public long getCashAmt() {
		return this.cashAmt;
	}

	public void setHasContrAmt(long hasContrAmt) {
		this.hasContrAmt = hasContrAmt;
	}

	public long getHasContrAmt() {
		return this.hasContrAmt;
	}

	public void setInvestVart(String investVart) {
		this.investVart = investVart;
	}

	public String getInvestVart() {
		return this.investVart;
	}

	public void setOtherGuaranteeVal(long otherGuaranteeVal) {
		this.otherGuaranteeVal = otherGuaranteeVal;
	}

	public long getOtherGuaranteeVal() {
		return this.otherGuaranteeVal;
	}

	public void setCalIntDays(int calIntDays) {
		this.calIntDays = calIntDays;
	}

	public int getCalIntDays() {
		return this.calIntDays;
	}

	public void setIntSubFlag(Character intSubFlag) {
		this.intSubFlag = intSubFlag;
	}

	public Character getIntSubFlag() {
		return this.intSubFlag;
	}

	public void setCurrIntAccr(long currIntAccr) {
		this.currIntAccr = currIntAccr;
	}

	public long getCurrIntAccr() {
		return this.currIntAccr;
	}

	public void setCurrIntAmt(long currIntAmt) {
		this.currIntAmt = currIntAmt;
	}

	public long getCurrIntAmt() {
		return this.currIntAmt;
	}

	public void setUpDateFeeamtt(int upDateFeeamtt) {
		this.upDateFeeamtt = upDateFeeamtt;
	}

	public int getUpDateFeeamtt() {
		return this.upDateFeeamtt;
	}

	public void setUpDateFeeaccru(int upDateFeeaccru) {
		this.upDateFeeaccru = upDateFeeaccru;
	}

	public int getUpDateFeeaccru() {
		return this.upDateFeeaccru;
	}

	public void setRiskRateMode(Character riskRateMode) {
		this.riskRateMode = riskRateMode;
	}

	public Character getRiskRateMode() {
		return this.riskRateMode;
	}

	public void setGuaranteeRiskMode(Character guaranteeRiskMode) {
		this.guaranteeRiskMode = guaranteeRiskMode;
	}

	public Character getGuaranteeRiskMode() {
		return this.guaranteeRiskMode;
	}

}