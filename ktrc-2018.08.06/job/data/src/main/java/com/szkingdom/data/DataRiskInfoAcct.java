/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskInfoAcct
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataRiskInfoAcct extends DataBase {

	private static final long serialVersionUID = -5149401866908695203L;
	private String indicatorId;
	private String indicatorName;
	private int occDate;
	private int occTime;
	private Character openCondFlag;
	private String openCondReason;
	private Character riskRatingFlag;
	private String riskRatingReason;
	private Character trdCtrlFlag;
	private String trdCtrlReason;
	private Character skipLvlFlag;
	private String skipLvlReason;
	private Character forceUcloseFlag;
	private String forceUcloseReason;
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
	private Character trdacctExcls;
	private Character optTrdacctLvl;
	private Character riskValidFlag;
	private Character noticeSentLevel;
	private String noticeSentLevelName;

    public DataRiskInfoAcct(){
        init();
    }

	public DataRiskInfoAcct(JSONObject json) {
	}

	public void init() {
		this.indicatorId = "";
		this.indicatorName = "";
		this.occDate = 0;
		this.occTime = 0;
		this.openCondFlag = null;
		this.openCondReason = "";
		this.riskRatingFlag = null;
		this.riskRatingReason = "";
		this.trdCtrlFlag = null;
		this.trdCtrlReason = "";
		this.skipLvlFlag = null;
		this.skipLvlReason = "";
		this.forceUcloseFlag = null;
		this.forceUcloseReason = "";
		this.intOrg = 0;
		this.custCode = 0L;
		this.custName = "";
		this.custType = null;
		this.custCls = null;
		this.cuacctCode = 0L;
		this.currency = null;
		this.stkbd = "";
		this.trdacct = "";
		this.subacctCode = "";
		this.trdacctExcls = null;
		this.optTrdacctLvl = null;
		this.riskValidFlag = null;
		this.noticeSentLevel = null;
		this.noticeSentLevelName = "";

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

	public void setOpenCondFlag(Character openCondFlag) {
		this.openCondFlag = openCondFlag;
	}

	public Character getOpenCondFlag() {
		return this.openCondFlag;
	}

	public void setOpenCondReason(String openCondReason) {
		this.openCondReason = openCondReason;
	}

	public String getOpenCondReason() {
		return this.openCondReason;
	}

	public void setRiskRatingFlag(Character riskRatingFlag) {
		this.riskRatingFlag = riskRatingFlag;
	}

	public Character getRiskRatingFlag() {
		return this.riskRatingFlag;
	}

	public void setRiskRatingReason(String riskRatingReason) {
		this.riskRatingReason = riskRatingReason;
	}

	public String getRiskRatingReason() {
		return this.riskRatingReason;
	}

	public void setTrdCtrlFlag(Character trdCtrlFlag) {
		this.trdCtrlFlag = trdCtrlFlag;
	}

	public Character getTrdCtrlFlag() {
		return this.trdCtrlFlag;
	}

	public void setTrdCtrlReason(String trdCtrlReason) {
		this.trdCtrlReason = trdCtrlReason;
	}

	public String getTrdCtrlReason() {
		return this.trdCtrlReason;
	}

	public void setSkipLvlFlag(Character skipLvlFlag) {
		this.skipLvlFlag = skipLvlFlag;
	}

	public Character getSkipLvlFlag() {
		return this.skipLvlFlag;
	}

	public void setSkipLvlReason(String skipLvlReason) {
		this.skipLvlReason = skipLvlReason;
	}

	public String getSkipLvlReason() {
		return this.skipLvlReason;
	}

	public void setForceUcloseFlag(Character forceUcloseFlag) {
		this.forceUcloseFlag = forceUcloseFlag;
	}

	public Character getForceUcloseFlag() {
		return this.forceUcloseFlag;
	}

	public void setForceUcloseReason(String forceUcloseReason) {
		this.forceUcloseReason = forceUcloseReason;
	}

	public String getForceUcloseReason() {
		return this.forceUcloseReason;
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

	public void setStkbd(String stkbd) {
		this.stkbd = stkbd;
	}

	public String getStkbd() {
		return this.stkbd;
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

	public void setTrdacctExcls(Character trdacctExcls) {
		this.trdacctExcls = trdacctExcls;
	}

	public Character getTrdacctExcls() {
		return this.trdacctExcls;
	}

	public void setOptTrdacctLvl(Character optTrdacctLvl) {
		this.optTrdacctLvl = optTrdacctLvl;
	}

	public Character getOptTrdacctLvl() {
		return this.optTrdacctLvl;
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

}