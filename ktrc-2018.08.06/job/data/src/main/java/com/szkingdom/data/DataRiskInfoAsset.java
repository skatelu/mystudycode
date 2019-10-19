/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskInfoAsset
 * Author:   yinhl
 * Date:     2018-07-26
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataRiskInfoAsset extends DataBase {

	private static final long serialVersionUID = 9071132764285821321L;
	private String indicatorId;
	private String indicatorName;
	private int occDate;
	private int occTime;
	private long preThreshold;
	private Character preThresNo;
	private String preThresName;
	private String preThresColor;
	private long preRiskValue;
	private long threshold;
	private Character thresNo;
	private String thresName;
	private String thresColor;
	private long riskValue;
	private long posiQty;
	private long posiLqty;
	private long quotaValUsed;
	private long quotaVal;
	private Character cvdShortType;
	private long stkQtyNeed;
	private long stkQtyLocked;
	private long stkQtyAct;
	private long cvtQtyLack;
	private short intOrg;
	private long custCode;
	private String custName;
	private Character custType;
	private Character custCls;
	private long cuacctCode;
	private Character currency;
	private String stkbd;
	private String trdacct;
	private String subacctCode;
	private String optUndlCode;
	private String optUndlName;
	private Character optUndlCls;
	private Character lsFlag;
	private Character riskValidFlag;
	private Character riskInfoSrc;
	private Character noticeSentLevel;
	private String noticeSentLevelName;

	private String optNum;
	private long shortRltPosi;
	private long shortActPosi;
	private long optHedgedQty;
	private long secuMargin;                                // 非对冲含挂单保证金(券商)
	private long secuMarginRlt;                             // 非对冲含挂单保证金(券商实时)
	private long stkexMargin;                               // 非对冲含挂单保证金(交易所)
	private long stkexMarginRlt;                            // 非对冲含挂单保证金(交易所实时)
	private long hedgedSecuMargin;                          // 对冲后保证金(券商)
	private long hedgedSecuMarginRlt;                       // 对冲后保证金(券商实时)
	private long hedgedStkexMargin;                         // 对冲后保证金(交易所)
	private long hedgedStkexMarginRlt;                      // 对冲后保证金(交易所实时)
	private long shortPremiumRlt;                           // 义务仓平仓权利金

	public DataRiskInfoAsset() {
		init();
	}

	public DataRiskInfoAsset(JSONObject json) {
	}

	public void init(){
		indicatorId = "";
		indicatorName = "";
		occDate = 0;
		occTime = 0;
		preThreshold = 0L;
		preThresNo = null;
		preThresName = "";

		preThresColor = "";
		preRiskValue = 0L;
		threshold = 0L;
		thresNo = null;
		thresName = "";
		thresColor = "";
		riskValue = 0L;
		posiQty = 0L;
		posiLqty = 0L;
		quotaValUsed = 0L;
		quotaVal = 0L;
		cvdShortType = null;
		stkQtyNeed = 0L;
		stkQtyLocked = 0L;
		stkQtyAct = 0L;
		cvtQtyLack = 0L;
		intOrg = 0;
		custCode = 0L;
		custName = "";
		custType = null;
		custCls = null;
		cuacctCode = 0L;
		currency = null;
		stkbd = "";
		trdacct = "";
		subacctCode = "";
		optUndlCode = "";
		optUndlName = "";
		optUndlCls = null;
		lsFlag = null;
		riskValidFlag = null;
		riskInfoSrc = null;
		noticeSentLevel = null;
		noticeSentLevelName = "";
		optNum = "";
		shortRltPosi = 0L;
		shortActPosi = 0L;
		optHedgedQty = 0L;
		secuMargin = 0L;
		secuMarginRlt = 0L;
		stkexMargin = 0L;
		stkexMarginRlt = 0L;
		hedgedSecuMargin = 0L;
		hedgedSecuMarginRlt = 0L;
		hedgedStkexMargin = 0L;
		hedgedStkexMarginRlt = 0L;
		shortPremiumRlt = 0L;
	}

	public String getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getIndicatorName() {
		return indicatorName;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public int getOccDate() {
		return occDate;
	}

	public void setOccDate(int occDate) {
		this.occDate = occDate;
	}

	public int getOccTime() {
		return occTime;
	}

	public void setOccTime(int occTime) {
		this.occTime = occTime;
	}

	public long getPreThreshold() {
		return preThreshold;
	}

	public void setPreThreshold(long preThreshold) {
		this.preThreshold = preThreshold;
	}

	public Character getPreThresNo() {
		return preThresNo;
	}

	public void setPreThresNo(Character preThresNo) {
		this.preThresNo = preThresNo;
	}

	public String getPreThresName() {
		return preThresName;
	}

	public void setPreThresName(String preThresName) {
		this.preThresName = preThresName;
	}

	public String getPreThresColor() {
		return preThresColor;
	}

	public void setPreThresColor(String preThresColor) {
		this.preThresColor = preThresColor;
	}

	public long getPreRiskValue() {
		return preRiskValue;
	}

	public void setPreRiskValue(long preRiskValue) {
		this.preRiskValue = preRiskValue;
	}

	public long getThreshold() {
		return threshold;
	}

	public void setThreshold(long threshold) {
		this.threshold = threshold;
	}

	public Character getThresNo() {
		return thresNo;
	}

	public void setThresNo(Character thresNo) {
		this.thresNo = thresNo;
	}

	public String getThresName() {
		return thresName;
	}

	public void setThresName(String thresName) {
		this.thresName = thresName;
	}

	public String getThresColor() {
		return thresColor;
	}

	public void setThresColor(String thresColor) {
		this.thresColor = thresColor;
	}

	public long getRiskValue() {
		return riskValue;
	}

	public void setRiskValue(long riskValue) {
		this.riskValue = riskValue;
	}

	public long getPosiQty() {
		return posiQty;
	}

	public void setPosiQty(long posiQty) {
		this.posiQty = posiQty;
	}

	public long getPosiLqty() {
		return posiLqty;
	}

	public void setPosiLqty(long posiLqty) {
		this.posiLqty = posiLqty;
	}

	public long getQuotaValUsed() {
		return quotaValUsed;
	}

	public void setQuotaValUsed(long quotaValUsed) {
		this.quotaValUsed = quotaValUsed;
	}

	public long getQuotaVal() {
		return quotaVal;
	}

	public void setQuotaVal(long quotaVal) {
		this.quotaVal = quotaVal;
	}

	public Character getCvdShortType() {
		return cvdShortType;
	}

	public void setCvdShortType(Character cvdShortType) {
		this.cvdShortType = cvdShortType;
	}

	public long getStkQtyNeed() {
		return stkQtyNeed;
	}

	public void setStkQtyNeed(long stkQtyNeed) {
		this.stkQtyNeed = stkQtyNeed;
	}

	public long getStkQtyLocked() {
		return stkQtyLocked;
	}

	public void setStkQtyLocked(long stkQtyLocked) {
		this.stkQtyLocked = stkQtyLocked;
	}

	public long getStkQtyAct() {
		return stkQtyAct;
	}

	public void setStkQtyAct(long stkQtyAct) {
		this.stkQtyAct = stkQtyAct;
	}

	public long getCvtQtyLack() {
		return cvtQtyLack;
	}

	public void setCvtQtyLack(long cvtQtyLack) {
		this.cvtQtyLack = cvtQtyLack;
	}

	public short getIntOrg() {
		return intOrg;
	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public long getCustCode() {
		return custCode;
	}

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Character getCustType() {
		return custType;
	}

	public void setCustType(Character custType) {
		this.custType = custType;
	}

	public Character getCustCls() {
		return custCls;
	}

	public void setCustCls(Character custCls) {
		this.custCls = custCls;
	}

	public long getCuacctCode() {
		return cuacctCode;
	}

	public void setCuacctCode(long cuacctCode) {
		this.cuacctCode = cuacctCode;
	}

	public Character getCurrency() {
		return currency;
	}

	public void setCurrency(Character currency) {
		this.currency = currency;
	}

	public String getStkbd() {
		return stkbd;
	}

	public void setStkbd(String stkbd) {
		this.stkbd = stkbd;
	}

	public String getTrdacct() {
		return trdacct;
	}

	public void setTrdacct(String trdacct) {
		this.trdacct = trdacct;
	}

	public String getSubacctCode() {
		return subacctCode;
	}

	public void setSubacctCode(String subacctCode) {
		this.subacctCode = subacctCode;
	}

	public String getOptUndlCode() {
		return optUndlCode;
	}

	public void setOptUndlCode(String optUndlCode) {
		this.optUndlCode = optUndlCode;
	}

	public String getOptUndlName() {
		return optUndlName;
	}

	public void setOptUndlName(String optUndlName) {
		this.optUndlName = optUndlName;
	}

	public Character getOptUndlCls() {
		return optUndlCls;
	}

	public void setOptUndlCls(Character optUndlCls) {
		this.optUndlCls = optUndlCls;
	}

	public Character getLsFlag() {
		return lsFlag;
	}

	public void setLsFlag(Character lsFlag) {
		this.lsFlag = lsFlag;
	}

	public Character getRiskValidFlag() {
		return riskValidFlag;
	}

	public void setRiskValidFlag(Character riskValidFlag) {
		this.riskValidFlag = riskValidFlag;
	}

	public Character getRiskInfoSrc() {
		return riskInfoSrc;
	}

	public void setRiskInfoSrc(Character riskInfoSrc) {
		this.riskInfoSrc = riskInfoSrc;
	}

	public Character getNoticeSentLevel() {
		return noticeSentLevel;
	}

	public void setNoticeSentLevel(Character noticeSentLevel) {
		this.noticeSentLevel = noticeSentLevel;
	}

	public String getNoticeSentLevelName() {
		return noticeSentLevelName;
	}

	public void setNoticeSentLevelName(String noticeSentLevelName) {
		this.noticeSentLevelName = noticeSentLevelName;
	}

	public String getOptNum() {
		return optNum;
	}

	public void setOptNum(String optNum) {
		this.optNum = optNum;
	}

	public long getShortRltPosi() {
		return shortRltPosi;
	}

	public void setShortRltPosi(long shortRltPosi) {
		this.shortRltPosi = shortRltPosi;
	}

	public long getShortActPosi() {
		return shortActPosi;
	}

	public void setShortActPosi(long shortActPosi) {
		this.shortActPosi = shortActPosi;
	}

	public long getOptHedgedQty() {
		return optHedgedQty;
	}

	public void setOptHedgedQty(long optHedgedQty) {
		this.optHedgedQty = optHedgedQty;
	}

	public long getSecuMargin() {
		return secuMargin;
	}

	public void setSecuMargin(long secuMargin) {
		this.secuMargin = secuMargin;
	}

	public long getSecuMarginRlt() {
		return secuMarginRlt;
	}

	public void setSecuMarginRlt(long secuMarginRlt) {
		this.secuMarginRlt = secuMarginRlt;
	}

	public long getStkexMargin() {
		return stkexMargin;
	}

	public void setStkexMargin(long stkexMargin) {
		this.stkexMargin = stkexMargin;
	}

	public long getStkexMarginRlt() {
		return stkexMarginRlt;
	}

	public void setStkexMarginRlt(long stkexMarginRlt) {
		this.stkexMarginRlt = stkexMarginRlt;
	}

	public long getHedgedSecuMargin() {
		return hedgedSecuMargin;
	}

	public void setHedgedSecuMargin(long hedgedSecuMargin) {
		this.hedgedSecuMargin = hedgedSecuMargin;
	}

	public long getHedgedSecuMarginRlt() {
		return hedgedSecuMarginRlt;
	}

	public void setHedgedSecuMarginRlt(long hedgedSecuMarginRlt) {
		this.hedgedSecuMarginRlt = hedgedSecuMarginRlt;
	}

	public long getHedgedStkexMargin() {
		return hedgedStkexMargin;
	}

	public void setHedgedStkexMargin(long hedgedStkexMargin) {
		this.hedgedStkexMargin = hedgedStkexMargin;
	}

	public long getHedgedStkexMarginRlt() {
		return hedgedStkexMarginRlt;
	}

	public void setHedgedStkexMarginRlt(long hedgedStkexMarginRlt) {
		this.hedgedStkexMarginRlt = hedgedStkexMarginRlt;
	}

	public long getShortPremiumRlt() {
		return shortPremiumRlt;
	}

	public void setShortPremiumRlt(long shortPremiumRlt) {
		this.shortPremiumRlt = shortPremiumRlt;
	}

	@Override
	public String toString() {
		return "DataRiskInfoAsset{" +
				"indicatorId='" + indicatorId + '\'' +
				", indicatorName='" + indicatorName + '\'' +
				", occDate=" + occDate +
				", occTime=" + occTime +
				", preThreshold=" + preThreshold +
				", preThresNo=" + preThresNo +
				", preThresName='" + preThresName + '\'' +
				", preThresColor='" + preThresColor + '\'' +
				", preRiskValue=" + preRiskValue +
				", threshold=" + threshold +
				", thresNo=" + thresNo +
				", thresName='" + thresName + '\'' +
				", thresColor='" + thresColor + '\'' +
				", riskValue=" + riskValue +
				", posiQty=" + posiQty +
				", posiLqty=" + posiLqty +
				", quotaValUsed=" + quotaValUsed +
				", quotaVal=" + quotaVal +
				", cvdShortType=" + cvdShortType +
				", stkQtyNeed=" + stkQtyNeed +
				", stkQtyLocked=" + stkQtyLocked +
				", stkQtyAct=" + stkQtyAct +
				", cvtQtyLack=" + cvtQtyLack +
				", intOrg=" + intOrg +
				", custCode=" + custCode +
				", custName='" + custName + '\'' +
				", custType=" + custType +
				", custCls=" + custCls +
				", cuacctCode=" + cuacctCode +
				", currency=" + currency +
				", stkbd='" + stkbd + '\'' +
				", trdacct='" + trdacct + '\'' +
				", subacctCode='" + subacctCode + '\'' +
				", optUndlCode='" + optUndlCode + '\'' +
				", optUndlName='" + optUndlName + '\'' +
				", optUndlCls=" + optUndlCls +
				", lsFlag=" + lsFlag +
				", riskValidFlag=" + riskValidFlag +
				", riskInfoSrc=" + riskInfoSrc +
				", noticeSentLevel=" + noticeSentLevel +
				", noticeSentLevelName='" + noticeSentLevelName + '\'' +
				", optNum='" + optNum + '\'' +
				", shortRltPosi=" + shortRltPosi +
				", shortActPosi=" + shortActPosi +
				", optHedgedQty=" + optHedgedQty +
				", secuMargin=" + secuMargin +
				", secuMarginRlt=" + secuMarginRlt +
				", stkexMargin=" + stkexMargin +
				", stkexMarginRlt=" + stkexMarginRlt +
				", hedgedSecuMargin=" + hedgedSecuMargin +
				", hedgedSecuMarginRlt=" + hedgedSecuMarginRlt +
				", hedgedStkexMargin=" + hedgedStkexMargin +
				", hedgedStkexMarginRlt=" + hedgedStkexMarginRlt +
				", shortPremiumRlt=" + shortPremiumRlt +
				'}';
	}
}