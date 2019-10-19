/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataCustomer
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataCustomer extends DataBase {

	private static final long serialVersionUID = -889935928200020149L;
	private long custCode;
	private String custName;
	private Character custType;
	private Character custCls;
	private Character custStatus;
	private String cardId;
	private String criterion;
	private String riskFactor;
	private short intOrg;
	private int openDate;
	private String channels;
	private String remark;
	private Character creditLvl;
	private Character remoteProtocol;
	private Character custSource;
	private Character serviceLvl;
	private String custExtAttr;
	private int closeDate;
	private short subsysSn;
	private Character isVip;
	private short vipSubsysSn;
	private long comDiscount;
	private Character marginLvl;
	private Character investorType;
	private int profInvestorExpDate;
	private Character ratingLvl;
	private int ratingDate;
	private int ratingExpDate;
	private Character lowestRiskFlag;
	private String investPro;
	private Character investLmt;
	private Character expectIncome;
	private Character isSign;
	private short actSid;
	private short thdSubsysSn;
	private Character optCustType;

	public DataCustomer() {
		init();
	}

	public DataCustomer(JSONObject json) {
	}

	public void init() {
		this.custCode = 0L;
		this.custName = "";
		this.custType = null;
		this.custCls = null;
		this.custStatus = null;
		this.cardId = "";
		this.criterion = "";
		this.riskFactor = "";
		this.intOrg = 0;
		this.openDate = 0;
		this.channels = "";
		this.remark = "";
		this.creditLvl = null;
		this.remoteProtocol = null;
		this.custSource = null;
		this.serviceLvl = null;
		this.custExtAttr = "";
		this.closeDate = 0;
		this.subsysSn = 0;
		this.isVip = null;
		this.vipSubsysSn = 0;
		this.comDiscount = 0L;
		this.marginLvl = null;
		this.investorType = null;
		this.profInvestorExpDate = 0;
		this.ratingLvl = null;
		this.ratingDate = 0;
		this.ratingExpDate = 0;
		this.lowestRiskFlag = null;
		this.investPro = "";
		this.investLmt = null;
		this.expectIncome = null;
		this.isSign = null;
		this.actSid = 0;
		this.thdSubsysSn = 0;
		this.optCustType = null;
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

	public void setCustCls(Character custCls) {
		this.custCls = custCls;
	}

	public Character getCustCls() {
		return this.custCls;
	}

	public void setCustStatus(Character custStatus) {
		this.custStatus = custStatus;
	}

	public Character getCustStatus() {
		return this.custStatus;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardId() {
		return this.cardId;
	}

	public void setCriterion(String criterion) {
		this.criterion = criterion;
	}

	public String getCriterion() {
		return this.criterion;
	}

	public void setRiskFactor(String riskFactor) {
		this.riskFactor = riskFactor;
	}

	public String getRiskFactor() {
		return this.riskFactor;
	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public short getIntOrg() {
		return this.intOrg;
	}

	public void setOpenDate(int openDate) {
		this.openDate = openDate;
	}

	public int getOpenDate() {
		return this.openDate;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	public String getChannels() {
		return this.channels;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setCreditLvl(Character creditLvl) {
		this.creditLvl = creditLvl;
	}

	public Character getCreditLvl() {
		return this.creditLvl;
	}

	public void setRemoteProtocol(Character remoteProtocol) {
		this.remoteProtocol = remoteProtocol;
	}

	public Character getRemoteProtocol() {
		return this.remoteProtocol;
	}

	public void setCustSource(Character custSource) {
		this.custSource = custSource;
	}

	public Character getCustSource() {
		return this.custSource;
	}

	public void setServiceLvl(Character serviceLvl) {
		this.serviceLvl = serviceLvl;
	}

	public Character getServiceLvl() {
		return this.serviceLvl;
	}

	public void setCustExtAttr(String custExtAttr) {
		this.custExtAttr = custExtAttr;
	}

	public String getCustExtAttr() {
		return this.custExtAttr;
	}

	public void setCloseDate(int closeDate) {
		this.closeDate = closeDate;
	}

	public int getCloseDate() {
		return this.closeDate;
	}

	public void setSubsysSn(short subsysSn) {
		this.subsysSn = subsysSn;
	}

	public short getSubsysSn() {
		return this.subsysSn;
	}

	public void setIsVip(Character isVip) {
		this.isVip = isVip;
	}

	public Character getIsVip() {
		return this.isVip;
	}

	public void setVipSubsysSn(short vipSubsysSn) {
		this.vipSubsysSn = vipSubsysSn;
	}

	public short getVipSubsysSn() {
		return this.vipSubsysSn;
	}

	public void setComDiscount(long comDiscount) {
		this.comDiscount = comDiscount;
	}

	public long getComDiscount() {
		return this.comDiscount;
	}

	public void setMarginLvl(Character marginLvl) {
		this.marginLvl = marginLvl;
	}

	public Character getMarginLvl() {
		return this.marginLvl;
	}

	public void setInvestorType(Character investorType) {
		this.investorType = investorType;
	}

	public Character getInvestorType() {
		return this.investorType;
	}

	public void setProfInvestorExpDate(int profInvestorExpDate) {
		this.profInvestorExpDate = profInvestorExpDate;
	}

	public int getProfInvestorExpDate() {
		return this.profInvestorExpDate;
	}

	public void setRatingLvl(Character ratingLvl) {
		this.ratingLvl = ratingLvl;
	}

	public Character getRatingLvl() {
		return this.ratingLvl;
	}

	public void setRatingDate(int ratingDate) {
		this.ratingDate = ratingDate;
	}

	public int getRatingDate() {
		return this.ratingDate;
	}

	public void setRatingExpDate(int ratingExpDate) {
		this.ratingExpDate = ratingExpDate;
	}

	public int getRatingExpDate() {
		return this.ratingExpDate;
	}

	public void setLowestRiskFlag(Character lowestRiskFlag) {
		this.lowestRiskFlag = lowestRiskFlag;
	}

	public Character getLowestRiskFlag() {
		return this.lowestRiskFlag;
	}

	public void setInvestPro(String investPro) {
		this.investPro = investPro;
	}

	public String getInvestPro() {
		return this.investPro;
	}

	public void setInvestLmt(Character investLmt) {
		this.investLmt = investLmt;
	}

	public Character getInvestLmt() {
		return this.investLmt;
	}

	public void setExpectIncome(Character expectIncome) {
		this.expectIncome = expectIncome;
	}

	public Character getExpectIncome() {
		return this.expectIncome;
	}

	public void setIsSign(Character isSign) {
		this.isSign = isSign;
	}

	public Character getIsSign() {
		return this.isSign;
	}

	public void setActSid(short actSid) {
		this.actSid = actSid;
	}

	public short getActSid() {
		return this.actSid;
	}

	public void setThdSubsysSn(short thdSubsysSn) {
		this.thdSubsysSn = thdSubsysSn;
	}

	public short getThdSubsysSn() {
		return this.thdSubsysSn;
	}

	public void setOptCustType(Character optCustType) {
		this.optCustType = optCustType;
	}

	public Character getOptCustType() {
		return this.optCustType;
	}

}