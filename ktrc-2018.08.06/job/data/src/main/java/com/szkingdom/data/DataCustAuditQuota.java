/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataCustAuditQuota
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataCustAuditQuota extends DataBase {

	private static final long serialVersionUID = 4681256450760232372L;
	private long custCode;
	private Character agmType;
	private long auditQuota;
	private String auditLvl;
	private long penaltyRate;
	private long quotaUsed;
	private long appAmt;
	private long callAmt;
	private long crdRate;

	public DataCustAuditQuota() {
	}

	public DataCustAuditQuota(JSONObject json) {
	}

	public void init() {
		this.custCode = 0L;
		this.agmType = null;
		this.auditQuota = 0L;
		this.auditLvl = "";
		this.penaltyRate = 0L;
		this.quotaUsed = 0L;
		this.appAmt = 0L;
		this.callAmt = 0L;
		this.crdRate = 0L;

	}

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
	}

	public void setAgmType(Character agmType) {
		this.agmType = agmType;
	}

	public Character getAgmType() {
		return this.agmType;
	}

	public void setAuditQuota(long auditQuota) {
		this.auditQuota = auditQuota;
	}

	public long getAuditQuota() {
		return this.auditQuota;
	}

	public void setAuditLvl(String auditLvl) {
		this.auditLvl = auditLvl;
	}

	public String getAuditLvl() {
		return this.auditLvl;
	}

	public void setPenaltyRate(long penaltyRate) {
		this.penaltyRate = penaltyRate;
	}

	public long getPenaltyRate() {
		return this.penaltyRate;
	}

	public void setQuotaUsed(long quotaUsed) {
		this.quotaUsed = quotaUsed;
	}

	public long getQuotaUsed() {
		return this.quotaUsed;
	}

	public void setAppAmt(long appAmt) {
		this.appAmt = appAmt;
	}

	public long getAppAmt() {
		return this.appAmt;
	}

	public void setCallAmt(long callAmt) {
		this.callAmt = callAmt;
	}

	public long getCallAmt() {
		return this.callAmt;
	}

	public void setCrdRate(long crdRate) {
		this.crdRate = crdRate;
	}

	public long getCrdRate() {
		return this.crdRate;
	}

}