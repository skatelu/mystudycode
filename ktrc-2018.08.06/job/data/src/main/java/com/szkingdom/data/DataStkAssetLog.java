/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkAssetLog
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class DataStkAssetLog extends DataBase {

	private static final long serialVersionUID = -6011182563382210046L;
	private long serialNo;
	private int occurDate;
	private Date occurTime;
	private long custCode;
	private long cuacctCode;
	private short intOrg;
	private Character currency;
	private String stkbd;
	private String stkpbu;
	private String trdacct;
	private String stkCode;
	private int bizCode;
	private String bizContext;
	private long bizSn;
	private long stkAvl;
	private long stkBlnEffect;
	private long stkAvlEffect;
	private Character reverseStatus;
	private short subsysSn;

	public DataStkAssetLog() {
		init();
	}

	public DataStkAssetLog(JSONObject json) {
	}

	public void init() {
		this.serialNo = 0L;
		this.occurDate = 0;
		this.occurTime = new Date();
		this.custCode = 0L;
		this.cuacctCode = 0L;
		this.intOrg = 0;
		this.currency = null;
		this.stkbd = "";
		this.stkpbu = "";
		this.trdacct = "";
		this.stkCode = "";
		this.bizCode = 0;
		this.bizContext = "";
		this.bizSn = 0L;
		this.stkAvl = 0L;
		this.stkBlnEffect = 0L;
		this.stkAvlEffect = 0L;
		this.reverseStatus = null;
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

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
	}

	public void setCuacctCode(long cuacctCode) {
		this.cuacctCode = cuacctCode;
	}

	public long getCuacctCode() {
		return this.cuacctCode;
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

	public void setStkbd(String stkbd) {
		this.stkbd = stkbd;
	}

	public String getStkbd() {
		return this.stkbd;
	}

	public void setStkpbu(String stkpbu) {
		this.stkpbu = stkpbu;
	}

	public String getStkpbu() {
		return this.stkpbu;
	}

	public void setTrdacct(String trdacct) {
		this.trdacct = trdacct;
	}

	public String getTrdacct() {
		return this.trdacct;
	}

	public void setStkCode(String stkCode) {
		this.stkCode = stkCode;
	}

	public String getStkCode() {
		return this.stkCode;
	}

	public void setBizCode(int bizCode) {
		this.bizCode = bizCode;
	}

	public int getBizCode() {
		return this.bizCode;
	}

	public void setBizContext(String bizContext) {
		this.bizContext = bizContext;
	}

	public String getBizContext() {
		return this.bizContext;
	}

	public void setBizSn(long bizSn) {
		this.bizSn = bizSn;
	}

	public long getBizSn() {
		return this.bizSn;
	}

	public void setStkAvl(long stkAvl) {
		this.stkAvl = stkAvl;
	}

	public long getStkAvl() {
		return this.stkAvl;
	}

	public void setStkBlnEffect(long stkBlnEffect) {
		this.stkBlnEffect = stkBlnEffect;
	}

	public long getStkBlnEffect() {
		return this.stkBlnEffect;
	}

	public void setStkAvlEffect(long stkAvlEffect) {
		this.stkAvlEffect = stkAvlEffect;
	}

	public long getStkAvlEffect() {
		return this.stkAvlEffect;
	}

	public void setReverseStatus(Character reverseStatus) {
		this.reverseStatus = reverseStatus;
	}

	public Character getReverseStatus() {
		return this.reverseStatus;
	}

	public void setSubsysSn(short subsysSn) {
		this.subsysSn = subsysSn;
	}

	public short getSubsysSn() {
		return this.subsysSn;
	}

}