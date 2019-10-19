/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkInfo
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStkInfo extends DataBase {

	private static final long serialVersionUID = -5078753518168965000L;
	private Character stkex;
	private String stkbd;
	private String stkCode;
	private String stkName;
	private String stkIsin;
	private Character stkCls;
	private Character stkSubCls;
	private Character stkStatus;
	private Character currency;
	private long stkUplmtRatio;
	private long stkLwlmtRatio;
	private long stkUplmtPrice;
	private long stkLwlmtPrice;
	private long stkUplmtQty;
	private long stkLwlmtQty;
	private long stkLotSize;
	private Character stkLotFlag;
	private long stkSpread;
	private long stkFaceVal;
	private Character stkCalMktval;
	private Character stkSuspended;
	private Character stkCustodyMode;
	private String stkUndlCode;
	private String stkBizes;
	private int updDate;
	private Character stkLevel;
	private int stkDeadline;
	private String remindMessage;

	public DataStkInfo() {
	}

	public DataStkInfo(JSONObject json) {
	}

	public void init() {
		this.stkex = null;
		this.stkbd = "";
		this.stkCode = "";
		this.stkName = "";
		this.stkIsin = "";
		this.stkCls = null;
		this.stkSubCls = null;
		this.stkStatus = null;
		this.currency = null;
		this.stkUplmtRatio = 0L;
		this.stkLwlmtRatio = 0L;
		this.stkUplmtPrice = 0L;
		this.stkLwlmtPrice = 0L;
		this.stkUplmtQty = 0L;
		this.stkLwlmtQty = 0L;
		this.stkLotSize = 0L;
		this.stkLotFlag = null;
		this.stkSpread = 0L;
		this.stkFaceVal = 0L;
		this.stkCalMktval = null;
		this.stkSuspended = null;
		this.stkCustodyMode = null;
		this.stkUndlCode = "";
		this.stkBizes = "";
		this.updDate = 0;
		this.stkLevel = null;
		this.stkDeadline = 0;
		this.remindMessage = "";

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

	public void setStkCode(String stkCode) {
		this.stkCode = stkCode;
	}

	public String getStkCode() {
		return this.stkCode;
	}

	public void setStkName(String stkName) {
		this.stkName = stkName;
	}

	public String getStkName() {
		return this.stkName;
	}

	public void setStkIsin(String stkIsin) {
		this.stkIsin = stkIsin;
	}

	public String getStkIsin() {
		return this.stkIsin;
	}

	public void setStkCls(Character stkCls) {
		this.stkCls = stkCls;
	}

	public Character getStkCls() {
		return this.stkCls;
	}

	public void setStkSubCls(Character stkSubCls) {
		this.stkSubCls = stkSubCls;
	}

	public Character getStkSubCls() {
		return this.stkSubCls;
	}

	public void setStkStatus(Character stkStatus) {
		this.stkStatus = stkStatus;
	}

	public Character getStkStatus() {
		return this.stkStatus;
	}

	public void setCurrency(Character currency) {
		this.currency = currency;
	}

	public Character getCurrency() {
		return this.currency;
	}

	public void setStkUplmtRatio(long stkUplmtRatio) {
		this.stkUplmtRatio = stkUplmtRatio;
	}

	public long getStkUplmtRatio() {
		return this.stkUplmtRatio;
	}

	public void setStkLwlmtRatio(long stkLwlmtRatio) {
		this.stkLwlmtRatio = stkLwlmtRatio;
	}

	public long getStkLwlmtRatio() {
		return this.stkLwlmtRatio;
	}

	public void setStkUplmtPrice(long stkUplmtPrice) {
		this.stkUplmtPrice = stkUplmtPrice;
	}

	public long getStkUplmtPrice() {
		return this.stkUplmtPrice;
	}

	public void setStkLwlmtPrice(long stkLwlmtPrice) {
		this.stkLwlmtPrice = stkLwlmtPrice;
	}

	public long getStkLwlmtPrice() {
		return this.stkLwlmtPrice;
	}

	public void setStkUplmtQty(long stkUplmtQty) {
		this.stkUplmtQty = stkUplmtQty;
	}

	public long getStkUplmtQty() {
		return this.stkUplmtQty;
	}

	public void setStkLwlmtQty(long stkLwlmtQty) {
		this.stkLwlmtQty = stkLwlmtQty;
	}

	public long getStkLwlmtQty() {
		return this.stkLwlmtQty;
	}

	public void setStkLotSize(long stkLotSize) {
		this.stkLotSize = stkLotSize;
	}

	public long getStkLotSize() {
		return this.stkLotSize;
	}

	public void setStkLotFlag(Character stkLotFlag) {
		this.stkLotFlag = stkLotFlag;
	}

	public Character getStkLotFlag() {
		return this.stkLotFlag;
	}

	public void setStkSpread(long stkSpread) {
		this.stkSpread = stkSpread;
	}

	public long getStkSpread() {
		return this.stkSpread;
	}

	public void setStkFaceVal(long stkFaceVal) {
		this.stkFaceVal = stkFaceVal;
	}

	public long getStkFaceVal() {
		return this.stkFaceVal;
	}

	public void setStkCalMktval(Character stkCalMktval) {
		this.stkCalMktval = stkCalMktval;
	}

	public Character getStkCalMktval() {
		return this.stkCalMktval;
	}

	public void setStkSuspended(Character stkSuspended) {
		this.stkSuspended = stkSuspended;
	}

	public Character getStkSuspended() {
		return this.stkSuspended;
	}

	public void setStkCustodyMode(Character stkCustodyMode) {
		this.stkCustodyMode = stkCustodyMode;
	}

	public Character getStkCustodyMode() {
		return this.stkCustodyMode;
	}

	public void setStkUndlCode(String stkUndlCode) {
		this.stkUndlCode = stkUndlCode;
	}

	public String getStkUndlCode() {
		return this.stkUndlCode;
	}

	public void setStkBizes(String stkBizes) {
		this.stkBizes = stkBizes;
	}

	public String getStkBizes() {
		return this.stkBizes;
	}

	public void setUpdDate(int updDate) {
		this.updDate = updDate;
	}

	public int getUpdDate() {
		return this.updDate;
	}

	public void setStkLevel(Character stkLevel) {
		this.stkLevel = stkLevel;
	}

	public Character getStkLevel() {
		return this.stkLevel;
	}

	public void setStkDeadline(int stkDeadline) {
		this.stkDeadline = stkDeadline;
	}

	public int getStkDeadline() {
		return this.stkDeadline;
	}

	public void setRemindMessage(String remindMessage) {
		this.remindMessage = remindMessage;
	}

	public String getRemindMessage() {
		return this.remindMessage;
	}

}