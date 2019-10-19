/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataCuacctFund
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;

public class DataCuacctFund extends DataBase {

	private static final long serialVersionUID = -9213990292699097291L;
	private long userCode;
	private String userName;
	private long cuacctCode;
	private Character cuacctAttr;
	private Character cuacctCls;
	private Character cuacctLvl;
	private Character cuacctGrp;
	private Character cuacctDmf;
	private short intOrg;
	private Character currency;
	private long fundPrebln;
	private long fundBln;
	private long fundAvl;
	private long fundFrz;
	private long fundUfz;
	private long fundTrdFrz;
	private long fundTrdUfz;
	private long fundTrdOtd;
	private long fundTrdBln;
	private Date updTime;
	private Character fundStatus;
	private long paylater;
	private long preadvaPay;
	private long fundExeFrz;
	private long fundRet;
	private long fundExeMargin;
	private long fundFeeFrz;
	private long marginUsed;
	private long marginInclRlt;
	private long dailyInAmt;
	private long dailyOutAmt;
	private short subsysSn;

	public DataCuacctFund() {
		init();
	}

	public DataCuacctFund(JSONObject json) {
	}

	public void init() {
		this.userCode = 0L;
		this.userName = "";
		this.cuacctCode = 0L;
		this.cuacctAttr = null;
		this.cuacctCls = null;
		this.cuacctLvl = null;
		this.cuacctGrp = null;
		this.cuacctDmf = null;
		this.intOrg = 0;
		this.currency = null;
		this.fundPrebln = 0L;
		this.fundBln = 0L;
		this.fundAvl = 0L;
		this.fundFrz = 0L;
		this.fundUfz = 0L;
		this.fundTrdFrz = 0L;
		this.fundTrdUfz = 0L;
		this.fundTrdOtd = 0L;
		this.fundTrdBln = 0L;
		this.updTime = new Date();
		this.fundStatus = null;
		this.paylater = 0L;
		this.preadvaPay = 0L;
		this.fundExeFrz = 0L;
		this.fundRet = 0L;
		this.fundExeMargin = 0L;
		this.fundFeeFrz = 0L;
		this.marginUsed = 0L;
		this.marginInclRlt = 0L;
		this.dailyInAmt = 0L;
		this.dailyOutAmt = 0L;
		this.subsysSn = 0;
	}

	public void setUserCode(long userCode) {
		this.userCode = userCode;
	}

	public long getUserCode() {
		return this.userCode;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setCuacctCode(long cuacctCode) {
		this.cuacctCode = cuacctCode;
	}

	public long getCuacctCode() {
		return this.cuacctCode;
	}

	public void setCuacctAttr(Character cuacctAttr) {
		this.cuacctAttr = cuacctAttr;
	}

	public Character getCuacctAttr() {
		return this.cuacctAttr;
	}

	public void setCuacctCls(Character cuacctCls) {
		this.cuacctCls = cuacctCls;
	}

	public Character getCuacctCls() {
		return this.cuacctCls;
	}

	public void setCuacctLvl(Character cuacctLvl) {
		this.cuacctLvl = cuacctLvl;
	}

	public Character getCuacctLvl() {
		return this.cuacctLvl;
	}

	public void setCuacctGrp(Character cuacctGrp) {
		this.cuacctGrp = cuacctGrp;
	}

	public Character getCuacctGrp() {
		return this.cuacctGrp;
	}

	public void setCuacctDmf(Character cuacctDmf) {
		this.cuacctDmf = cuacctDmf;
	}

	public Character getCuacctDmf() {
		return this.cuacctDmf;
	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public short getIntOrg() {
		return this.intOrg;
	}

	public void setCurrency(Character currency) {
		this.currency = currency;
	}

	public Character getCurrency() {
		return this.currency;
	}

	public void setFundPrebln(long fundPrebln) {
		this.fundPrebln = fundPrebln;
	}

	public long getFundPrebln() {
		return this.fundPrebln;
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

	public void setFundFrz(long fundFrz) {
		this.fundFrz = fundFrz;
	}

	public long getFundFrz() {
		return this.fundFrz;
	}

	public void setFundUfz(long fundUfz) {
		this.fundUfz = fundUfz;
	}

	public long getFundUfz() {
		return this.fundUfz;
	}

	public void setFundTrdFrz(long fundTrdFrz) {
		this.fundTrdFrz = fundTrdFrz;
	}

	public long getFundTrdFrz() {
		return this.fundTrdFrz;
	}

	public void setFundTrdUfz(long fundTrdUfz) {
		this.fundTrdUfz = fundTrdUfz;
	}

	public long getFundTrdUfz() {
		return this.fundTrdUfz;
	}

	public void setFundTrdOtd(long fundTrdOtd) {
		this.fundTrdOtd = fundTrdOtd;
	}

	public long getFundTrdOtd() {
		return this.fundTrdOtd;
	}

	public void setFundTrdBln(long fundTrdBln) {
		this.fundTrdBln = fundTrdBln;
	}

	public long getFundTrdBln() {
		return this.fundTrdBln;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public Date getUpdTime() {
		return this.updTime;
	}

	public void setFundStatus(Character fundStatus) {
		this.fundStatus = fundStatus;
	}

	public Character getFundStatus() {
		return this.fundStatus;
	}

	public void setPaylater(long paylater) {
		this.paylater = paylater;
	}

	public long getPaylater() {
		return this.paylater;
	}

	public void setPreadvaPay(long preadvaPay) {
		this.preadvaPay = preadvaPay;
	}

	public long getPreadvaPay() {
		return this.preadvaPay;
	}

	public void setFundExeFrz(long fundExeFrz) {
		this.fundExeFrz = fundExeFrz;
	}

	public long getFundExeFrz() {
		return this.fundExeFrz;
	}

	public void setFundRet(long fundRet) {
		this.fundRet = fundRet;
	}

	public long getFundRet() {
		return this.fundRet;
	}

	public void setFundExeMargin(long fundExeMargin) {
		this.fundExeMargin = fundExeMargin;
	}

	public long getFundExeMargin() {
		return this.fundExeMargin;
	}

	public void setFundFeeFrz(long fundFeeFrz) {
		this.fundFeeFrz = fundFeeFrz;
	}

	public long getFundFeeFrz() {
		return this.fundFeeFrz;
	}

	public void setMarginUsed(long marginUsed) {
		this.marginUsed = marginUsed;
	}

	public long getMarginUsed() {
		return this.marginUsed;
	}

	public void setMarginInclRlt(long marginInclRlt) {
		this.marginInclRlt = marginInclRlt;
	}

	public long getMarginInclRlt() {
		return this.marginInclRlt;
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

	public void setSubsysSn(short subsysSn) {
		this.subsysSn = subsysSn;
	}

	public short getSubsysSn() {
		return this.subsysSn;
	}

	public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("FUND_BLN", fundBln);
        json.put("FUND_AVL", fundAvl);
        json.put("FUND_RET", fundRet);
		json.put("MARGIN_USED", marginUsed);
		json.put("MARGIN_USED_RLT", marginInclRlt);
		json.put("DAILY_IN_AMT", dailyInAmt);
		json.put("DAILY_OUT_AMT", dailyOutAmt);
		json.put("INT_ORG", intOrg);
		json.put("FUND_EXE_MARGIN", fundExeMargin);
		json.put("FUND_FRZ", fundFrz);
		json.put("FUND_FEE_FRZ", fundFeeFrz);

		return json;
	}
}