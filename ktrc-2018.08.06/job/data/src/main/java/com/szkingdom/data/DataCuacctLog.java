/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataCuacctLog
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

public class DataCuacctLog extends DataBase {

	private static final long serialVersionUID = -4716332829650342357L;
	private long serialNo;
	private int occurDate;
	private Date occurTime;
	private short intOrg;
	private long userCode;
	private long cuacctCode;
	private Character currency;
	private short extOrg;
	private Character extOrgType;
	private int bizCode;
	private long bizAmt;
	private long fundBln;
	private long opUser;
	private Character opRole;
	private String opName;
	private short opOrg;
	private String opSite;
	private Character channel;
	private Character cancelFlag;
	private long originalSn;
	private String extSerialNo;
	private short subsysSn;

	public DataCuacctLog() {
		init();
	}

	public DataCuacctLog(JSONObject json) {
	}

	public void init() {
		this.serialNo = 0L;
		this.occurDate = 0;
		this.occurTime = new Date();
		this.intOrg = 0;
		this.userCode = 0L;
		this.cuacctCode = 0L;
		this.currency = null;
		this.extOrg = 0;
		this.extOrgType = null;
		this.bizCode = 0;
		this.bizAmt = 0L;
		this.fundBln = 0L;
		this.opUser = 0L;
		this.opRole = null;
		this.opName = "";
		this.opOrg = 0;
		this.opSite = "";
		this.channel = null;
		this.cancelFlag = null;
		this.originalSn = 0L;
		this.extSerialNo = "";
		this.subsysSn = 0;
	}

	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}

	public long getSerialNo() {
		return this.serialNo;
	}

	public void setOccurDate(int occurDate) {
		this.occurDate = occurDate;
	}

	public int getOccurDate() {
		return this.occurDate;
	}

	public void setOccurTime(Date occurTime) {
		this.occurTime = occurTime;
	}

	public Date getOccurTime() {
		return this.occurTime;
	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public short getIntOrg() {
		return this.intOrg;
	}

	public void setUserCode(long userCode) {
		this.userCode = userCode;
	}

	public long getUserCode() {
		return this.userCode;
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

	public void setExtOrg(short extOrg) {
		this.extOrg = extOrg;
	}

	public short getExtOrg() {
		return this.extOrg;
	}

	public void setExtOrgType(Character extOrgType) {
		this.extOrgType = extOrgType;
	}

	public Character getExtOrgType() {
		return this.extOrgType;
	}

	public void setBizCode(int bizCode) {
		this.bizCode = bizCode;
	}

	public int getBizCode() {
		return this.bizCode;
	}

	public void setBizAmt(long bizAmt) {
		this.bizAmt = bizAmt;
	}

	public long getBizAmt() {
		return this.bizAmt;
	}

	public void setFundBln(long fundBln) {
		this.fundBln = fundBln;
	}

	public long getFundBln() {
		return this.fundBln;
	}

	public void setOpUser(long opUser) {
		this.opUser = opUser;
	}

	public long getOpUser() {
		return this.opUser;
	}

	public void setOpRole(Character opRole) {
		this.opRole = opRole;
	}

	public Character getOpRole() {
		return this.opRole;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getOpName() {
		return this.opName;
	}

	public void setOpOrg(short opOrg) {
		this.opOrg = opOrg;
	}

	public short getOpOrg() {
		return this.opOrg;
	}

	public void setOpSite(String opSite) {
		this.opSite = opSite;
	}

	public String getOpSite() {
		return this.opSite;
	}

	public void setChannel(Character channel) {
		this.channel = channel;
	}

	public Character getChannel() {
		return this.channel;
	}

	public void setCancelFlag(Character cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public Character getCancelFlag() {
		return this.cancelFlag;
	}

	public void setOriginalSn(long originalSn) {
		this.originalSn = originalSn;
	}

	public long getOriginalSn() {
		return this.originalSn;
	}

	public void setExtSerialNo(String extSerialNo) {
		this.extSerialNo = extSerialNo;
	}

	public String getExtSerialNo() {
		return this.extSerialNo;
	}

	public void setSubsysSn(short subsysSn) {
		this.subsysSn = subsysSn;
	}

	public short getSubsysSn() {
		return this.subsysSn;
	}
}