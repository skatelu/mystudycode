/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskInfoExeFund
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataRiskInfoExeFund extends DataBase {

	private static final long serialVersionUID = -6798897190920568907L;
	private String indicatorId;
	private String indicatorName;
	private int occDate;
	private int occTime;
	private long riskValue;
	private long fundBln;
	private long fundAvl;
	private long fundRet;
	private long appointedNeedAmt;
	private long appointedLackAmt;
	private long fundNetDlvy;
	private long cashSettAmt;
	private long dlvyExeMargin;
	private long fundExeMargin;
	private long fundExeFrz;
	private long fundFeeFrz;
	private int dlvyDate;
	private short intOrg;
	private long custCode;
	private String custName;
	private Character custType;
	private Character custCls;
	private long cuacctCode;
	private Character currency;
	private Character riskValidFlag;
	private Character noticeSentLevel;
	private String noticeSentLevelName;

    public DataRiskInfoExeFund(){
        init();
    }

	public DataRiskInfoExeFund(JSONObject json) {
	}

	public void init() {
		this.indicatorId = "";
		this.indicatorName = "";
		this.occDate = 0;
		this.occTime = 0;
		this.riskValue = 0L;
		this.fundBln = 0L;
		this.fundAvl = 0L;
		this.fundRet = 0L;
		this.appointedNeedAmt = 0L;
		this.appointedLackAmt = 0L;
		this.fundNetDlvy = 0L;
		this.cashSettAmt = 0L;
		this.dlvyExeMargin = 0L;
		this.fundExeMargin = 0L;
		this.fundExeFrz = 0L;
		this.fundFeeFrz = 0L;
		this.dlvyDate = 0;
		this.intOrg = 0;
		this.custCode = 0L;
		this.custName = "";
		this.custType = null;
		this.custCls = null;
		this.cuacctCode = 0L;
		this.currency = null;
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

	public void setRiskValue(long riskValue) {
		this.riskValue = riskValue;
	}

	public long getRiskValue() {
		return this.riskValue;
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

	public void setAppointedNeedAmt(long appointedNeedAmt) {
		this.appointedNeedAmt = appointedNeedAmt;
	}

	public long getAppointedNeedAmt() {
		return this.appointedNeedAmt;
	}

	public void setAppointedLackAmt(long appointedLackAmt) {
		this.appointedLackAmt = appointedLackAmt;
	}

	public long getAppointedLackAmt() {
		return this.appointedLackAmt;
	}

	public void setFundNetDlvy(long fundNetDlvy) {
		this.fundNetDlvy = fundNetDlvy;
	}

	public long getFundNetDlvy() {
		return this.fundNetDlvy;
	}

	public void setCashSettAmt(long cashSettAmt) {
		this.cashSettAmt = cashSettAmt;
	}

	public long getCashSettAmt() {
		return this.cashSettAmt;
	}

	public void setDlvyExeMargin(long dlvyExeMargin) {
		this.dlvyExeMargin = dlvyExeMargin;
	}

	public long getDlvyExeMargin() {
		return this.dlvyExeMargin;
	}

	public void setFundExeMargin(long fundExeMargin) {
		this.fundExeMargin = fundExeMargin;
	}

	public long getFundExeMargin() {
		return this.fundExeMargin;
	}

	public void setFundExeFrz(long fundExeFrz) {
		this.fundExeFrz = fundExeFrz;
	}

	public long getFundExeFrz() {
		return this.fundExeFrz;
	}

	public void setFundFeeFrz(long fundFeeFrz) {
		this.fundFeeFrz = fundFeeFrz;
	}

	public long getFundFeeFrz() {
		return this.fundFeeFrz;
	}

	public void setDlvyDate(int dlvyDate) {
		this.dlvyDate = dlvyDate;
	}

	public int getDlvyDate() {
		return this.dlvyDate;
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