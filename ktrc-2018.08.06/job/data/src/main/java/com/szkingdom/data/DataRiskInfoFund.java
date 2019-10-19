/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskInfoFund
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataRiskInfoFund extends DataBase {

	private static final long serialVersionUID = 1335106217174417083L;
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
	private long riskValueRlt;
	private long stkexRiskValue;
	private long stkexRiskValueRlt;
	private long closeFundRiskValue;
	private long equity;
	private long fundBln;
	private long fundAvl;
	private long fundRet;
	private long marginUsed;
	private long marginUsedRlt;
	private long stkexMagUsed;
	private long stkexMagUsedRlt;
	private long dueAddAmt;
	private long dailyInAmt;
	private long dailyOutAmt;
	private short intOrg;
	private long custCode;
	private String custName;
	private Character custType;
	private long cuacctCode;
	private Character currency;
	private Character riskValidFlag;
	private Character noticeSentLevel;
	private String noticeSentLevelName;
	private Character marginCls;

	public DataRiskInfoFund() {
        init();
	}

	public DataRiskInfoFund(JSONObject json) {
	}

	public void init() {
		this.indicatorId = "";
		this.indicatorName = "";
		this.occDate = 0;
		this.occTime = 0;
		this.preThreshold = 0L;
		this.preThresNo = null;
		this.preThresName = "";
		this.preThresColor = "";
		this.preRiskValue = 0L;
		this.threshold = 0L;
		this.thresNo = null;
		this.thresName = "";
		this.thresColor = "";
		this.riskValue = 0L;
		this.riskValueRlt = 0L;
		this.stkexRiskValue = 0L;
		this.stkexRiskValueRlt = 0L;
		this.closeFundRiskValue = 0L;
		this.equity = 0L;
		this.fundBln = 0L;
		this.fundAvl = 0L;
		this.fundRet = 0L;
		this.marginUsed = 0L;
		this.marginUsedRlt = 0L;
		this.stkexMagUsed = 0L;
		this.stkexMagUsedRlt = 0L;
		this.dueAddAmt = 0L;
		this.dailyInAmt = 0L;
		this.dailyOutAmt = 0L;
		this.intOrg = 0;
		this.custCode = 0L;
		this.custName = "";
		this.custType = null;
		this.cuacctCode = 0L;
		this.currency = null;
		this.riskValidFlag = null;
		this.noticeSentLevel = null;
		this.noticeSentLevelName = "";
		this.marginCls = null;

	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getIndicatorId() {
		return this.indicatorId;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public String getIndicatorName() {
		return this.indicatorName;
	}

	public void setOccDate(int occDate) {
		this.occDate = occDate;
	}

	public int getOccDate() {
		return this.occDate;
	}

	public void setOccTime(int occTime) {
		this.occTime = occTime;
	}

	public int getOccTime() {
		return this.occTime;
	}

	public void setPreThreshold(long preThreshold) {
		this.preThreshold = preThreshold;
	}

	public long getPreThreshold() {
		return this.preThreshold;
	}

	public void setPreThresNo(Character preThresNo) {
		this.preThresNo = preThresNo;
	}

	public Character getPreThresNo() {
		return this.preThresNo;
	}

	public void setPreThresName(String preThresName) {
		this.preThresName = preThresName;
	}

	public String getPreThresName() {
		return this.preThresName;
	}

	public void setPreThresColor(String preThresColor) {
		this.preThresColor = preThresColor;
	}

	public String getPreThresColor() {
		return this.preThresColor;
	}

	public void setPreRiskValue(long preRiskValue) {
		this.preRiskValue = preRiskValue;
	}

	public long getPreRiskValue() {
		return this.preRiskValue;
	}

	public void setThreshold(long threshold) {
		this.threshold = threshold;
	}

	public long getThreshold() {
		return this.threshold;
	}

	public void setThresNo(Character thresNo) {
		this.thresNo = thresNo;
	}

	public Character getThresNo() {
		return this.thresNo;
	}

	public void setThresName(String thresName) {
		this.thresName = thresName;
	}

	public String getThresName() {
		return this.thresName;
	}

	public void setThresColor(String thresColor) {
		this.thresColor = thresColor;
	}

	public String getThresColor() {
		return this.thresColor;
	}

	public void setRiskValue(long riskValue) {
		this.riskValue = riskValue;
	}

	public long getRiskValue() {
		return this.riskValue;
	}

	public void setRiskValueRlt(long riskValueRlt) {
		this.riskValueRlt = riskValueRlt;
	}

	public long getRiskValueRlt() {
		return this.riskValueRlt;
	}

	public void setStkexRiskValue(long stkexRiskValue) {
		this.stkexRiskValue = stkexRiskValue;
	}

	public long getStkexRiskValue() {
		return this.stkexRiskValue;
	}

	public void setStkexRiskValueRlt(long stkexRiskValueRlt) {
		this.stkexRiskValueRlt = stkexRiskValueRlt;
	}

	public long getStkexRiskValueRlt() {
		return this.stkexRiskValueRlt;
	}

	public void setCloseFundRiskValue(long closeFundRiskValue) {
		this.closeFundRiskValue = closeFundRiskValue;
	}

	public long getCloseFundRiskValue() {
		return this.closeFundRiskValue;
	}

	public void setEquity(long equity) {
		this.equity = equity;
	}

	public long getEquity() {
		return this.equity;
	}

	public void setFundBln(long fundBln) {
		this.fundBln = fundBln;
	}

	public long getFundBln() {
		return this.fundBln;
	}

	public void setFundAvl(long fundAvl) {
		this.fundAvl = fundAvl;
	}

	public long getFundAvl() {
		return this.fundAvl;
	}

	public void setFundRet(long fundRet) {
		this.fundRet = fundRet;
	}

	public long getFundRet() {
		return this.fundRet;
	}

	public void setMarginUsed(long marginUsed) {
		this.marginUsed = marginUsed;
	}

	public long getMarginUsed() {
		return this.marginUsed;
	}

	public void setMarginUsedRlt(long marginUsedRlt) {
		this.marginUsedRlt = marginUsedRlt;
	}

	public long getMarginUsedRlt() {
		return this.marginUsedRlt;
	}

	public void setStkexMagUsed(long stkexMagUsed) {
		this.stkexMagUsed = stkexMagUsed;
	}

	public long getStkexMagUsed() {
		return this.stkexMagUsed;
	}

	public void setStkexMagUsedRlt(long stkexMagUsedRlt) {
		this.stkexMagUsedRlt = stkexMagUsedRlt;
	}

	public long getStkexMagUsedRlt() {
		return this.stkexMagUsedRlt;
	}

	public void setDueAddAmt(long dueAddAmt) {
		this.dueAddAmt = dueAddAmt;
	}

	public long getDueAddAmt() {
		return this.dueAddAmt;
	}

	public void setDailyInAmt(long dailyInAmt) {
		this.dailyInAmt = dailyInAmt;
	}

	public long getDailyInAmt() {
		return this.dailyInAmt;
	}

	public void setDailyOutAmt(long dailyOutAmt) {
		this.dailyOutAmt = dailyOutAmt;
	}

	public long getDailyOutAmt() {
		return this.dailyOutAmt;
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

	public void setRiskValidFlag(Character riskValidFlag) {
		this.riskValidFlag = riskValidFlag;
	}

	public Character getRiskValidFlag() {
		return this.riskValidFlag;
	}

	public void setNoticeSentLevel(Character noticeSentLevel) {
		this.noticeSentLevel = noticeSentLevel;
	}

	public Character getNoticeSentLevel() {
		return this.noticeSentLevel;
	}

	public void setNoticeSentLevelName(String noticeSentLevelName) {
		this.noticeSentLevelName = noticeSentLevelName;
	}

	public String getNoticeSentLevelName() {
		return this.noticeSentLevelName;
	}

	public void setMarginCls(Character marginCls) {
		this.marginCls = marginCls;
	}

	public Character getMarginCls() {
		return this.marginCls;
	}

}