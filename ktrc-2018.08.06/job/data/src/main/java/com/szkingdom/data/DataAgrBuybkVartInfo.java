/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataAgrBuybkVartInfo
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataAgrBuybkVartInfo extends DataBase {

	private static final long serialVersionUID = 1230934418422894326L;
	private int trdDate;
	private String stkbd;
	private String agrBuybkVastCode;
	private String agrBuybkVastName;
	private int agrBuybkVastDays;
	private long totalQuota;
	private long quotaUsed;
	private long buybkRate;
	private Character delayFlag;
	private Character trdFlag;
	private int aheadDelayDays;
	private long reconvertRate;

	public DataAgrBuybkVartInfo() {
	}

	public DataAgrBuybkVartInfo(JSONObject json) {
	}

	public void init() {
		this.trdDate = 0;
		this.stkbd = "";
		this.agrBuybkVastCode = "";
		this.agrBuybkVastName = "";
		this.agrBuybkVastDays = 0;
		this.totalQuota = 0L;
		this.quotaUsed = 0L;
		this.buybkRate = 0L;
		this.delayFlag = null;
		this.trdFlag = null;
		this.aheadDelayDays = 0;
		this.reconvertRate = 0L;

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

	public void setAgrBuybkVastCode(String agrBuybkVastCode) {
		this.agrBuybkVastCode = agrBuybkVastCode;
	}

	public String getAgrBuybkVastCode() {
		return this.agrBuybkVastCode;
	}

	public void setAgrBuybkVastName(String agrBuybkVastName) {
		this.agrBuybkVastName = agrBuybkVastName;
	}

	public String getAgrBuybkVastName() {
		return this.agrBuybkVastName;
	}

	public void setAgrBuybkVastDays(int agrBuybkVastDays) {
		this.agrBuybkVastDays = agrBuybkVastDays;
	}

	public int getAgrBuybkVastDays() {
		return this.agrBuybkVastDays;
	}

	public void setTotalQuota(long totalQuota) {
		this.totalQuota = totalQuota;
	}

	public long getTotalQuota() {
		return this.totalQuota;
	}

	public void setQuotaUsed(long quotaUsed) {
		this.quotaUsed = quotaUsed;
	}

	public long getQuotaUsed() {
		return this.quotaUsed;
	}

	public void setBuybkRate(long buybkRate) {
		this.buybkRate = buybkRate;
	}

	public long getBuybkRate() {
		return this.buybkRate;
	}

	public void setDelayFlag(Character delayFlag) {
		this.delayFlag = delayFlag;
	}

	public Character getDelayFlag() {
		return this.delayFlag;
	}

	public void setTrdFlag(Character trdFlag) {
		this.trdFlag = trdFlag;
	}

	public Character getTrdFlag() {
		return this.trdFlag;
	}

	public void setAheadDelayDays(int aheadDelayDays) {
		this.aheadDelayDays = aheadDelayDays;
	}

	public int getAheadDelayDays() {
		return this.aheadDelayDays;
	}

	public void setReconvertRate(long reconvertRate) {
		this.reconvertRate = reconvertRate;
	}

	public long getReconvertRate() {
		return this.reconvertRate;
	}

}