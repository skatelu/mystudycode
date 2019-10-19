/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptTrdacct
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataOptTrdacct extends DataBase {

	private static final long serialVersionUID = -7985915339524002452L;
	private long custCode;
	private Character stkex;
	private String stkbd;
	private String trdacct;
	private String subacctCode;
	private Character subacctType;
	private Character trdacctExcls;
	private Character optTrdacctLvl;
	private int optMarginSn;
	private long quotaVal;
	private long quotaValUsed;
	private String trdacctEx;
	private String stkpbuEx;
	private long cuacctCodeEx;
	private Character opFlag;
	private short vipActSid;
	private long dailyEnqryCount;
	private String optTrdacctUsage;

	public DataOptTrdacct() {
	}

	public DataOptTrdacct(JSONObject json) {
	}

	public void init() {
		this.custCode = 0L;
		this.stkex = null;
		this.stkbd = "";
		this.trdacct = "";
		this.subacctCode = "";
		this.subacctType = null;
		this.trdacctExcls = null;
		this.optTrdacctLvl = null;
		this.optMarginSn = 0;
		this.quotaVal = 0L;
		this.quotaValUsed = 0L;
		this.trdacctEx = "";
		this.stkpbuEx = "";
		this.cuacctCodeEx = 0L;
		this.opFlag = null;
		this.vipActSid = 0;
		this.dailyEnqryCount = 0L;
		this.optTrdacctUsage = "";

	}

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
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

	public void setSubacctType(Character subacctType) {
		this.subacctType = subacctType;
	}

	public Character getSubacctType() {
		return this.subacctType;
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

	public void setOptMarginSn(int optMarginSn) {
		this.optMarginSn = optMarginSn;
	}

	public int getOptMarginSn() {
		return this.optMarginSn;
	}

	public void setQuotaVal(long quotaVal) {
		this.quotaVal = quotaVal;
	}

	public long getQuotaVal() {
		return this.quotaVal;
	}

	public void setQuotaValUsed(long quotaValUsed) {
		this.quotaValUsed = quotaValUsed;
	}

	public long getQuotaValUsed() {
		return this.quotaValUsed;
	}

	public void setTrdacctEx(String trdacctEx) {
		this.trdacctEx = trdacctEx;
	}

	public String getTrdacctEx() {
		return this.trdacctEx;
	}

	public void setStkpbuEx(String stkpbuEx) {
		this.stkpbuEx = stkpbuEx;
	}

	public String getStkpbuEx() {
		return this.stkpbuEx;
	}

	public void setCuacctCodeEx(long cuacctCodeEx) {
		this.cuacctCodeEx = cuacctCodeEx;
	}

	public long getCuacctCodeEx() {
		return this.cuacctCodeEx;
	}

	public void setOpFlag(Character opFlag) {
		this.opFlag = opFlag;
	}

	public Character getOpFlag() {
		return this.opFlag;
	}

	public void setVipActSid(short vipActSid) {
		this.vipActSid = vipActSid;
	}

	public short getVipActSid() {
		return this.vipActSid;
	}

	public void setDailyEnqryCount(long dailyEnqryCount) {
		this.dailyEnqryCount = dailyEnqryCount;
	}

	public long getDailyEnqryCount() {
		return this.dailyEnqryCount;
	}

	public void setOptTrdacctUsage(String optTrdacctUsage) {
		this.optTrdacctUsage = optTrdacctUsage;
	}

	public String getOptTrdacctUsage() {
		return this.optTrdacctUsage;
	}

}