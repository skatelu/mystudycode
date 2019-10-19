/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkPlgSupContracts
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStkPlgSupContracts extends DataBase {

	private static final long serialVersionUID = 5519756150084580007L;
	private String stkbd;
	private int trdDate;
	private String supConstrSn;
	private String trdacctIn;
	private int orgTrdDate;
	private String orgConstrSn;
	private String orderId;
	private String stkCode;
	private Character shareCls;
	private long contQty;
	private long contAvl;
	private long contBuybkFrz;
	private long hasBuybkQty;
	private long bonusAmt;
	private long intQty;
	private Character contStatus;
	private long buybkQty;
	private Character flowType;
	private String rightsCls;
	private String listingYear;
	private int actBuybkDate;

	public DataStkPlgSupContracts() {
	}

	public DataStkPlgSupContracts(JSONObject json) {
	}

	public void init() {
		this.stkbd = "";
		this.trdDate = 0;
		this.supConstrSn = "";
		this.trdacctIn = "";
		this.orgTrdDate = 0;
		this.orgConstrSn = "";
		this.orderId = "";
		this.stkCode = "";
		this.shareCls = null;
		this.contQty = 0L;
		this.contAvl = 0L;
		this.contBuybkFrz = 0L;
		this.hasBuybkQty = 0L;
		this.bonusAmt = 0L;
		this.intQty = 0L;
		this.contStatus = null;
		this.buybkQty = 0L;
		this.flowType = null;
		this.rightsCls = "";
		this.listingYear = "";
		this.actBuybkDate = 0;

	}

	public void setStkbd(String stkbd) {
		this.stkbd = stkbd;
	}

	public String getStkbd() {
		return this.stkbd;
	}

	public void setTrdDate(int trdDate) {
		this.trdDate = trdDate;
	}

	public int getTrdDate() {
		return this.trdDate;
	}

	public void setSupConstrSn(String supConstrSn) {
		this.supConstrSn = supConstrSn;
	}

	public String getSupConstrSn() {
		return this.supConstrSn;
	}

	public void setTrdacctIn(String trdacctIn) {
		this.trdacctIn = trdacctIn;
	}

	public String getTrdacctIn() {
		return this.trdacctIn;
	}

	public void setOrgTrdDate(int orgTrdDate) {
		this.orgTrdDate = orgTrdDate;
	}

	public int getOrgTrdDate() {
		return this.orgTrdDate;
	}

	public void setOrgConstrSn(String orgConstrSn) {
		this.orgConstrSn = orgConstrSn;
	}

	public String getOrgConstrSn() {
		return this.orgConstrSn;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setStkCode(String stkCode) {
		this.stkCode = stkCode;
	}

	public String getStkCode() {
		return this.stkCode;
	}

	public void setShareCls(Character shareCls) {
		this.shareCls = shareCls;
	}

	public Character getShareCls() {
		return this.shareCls;
	}

	public void setContQty(long contQty) {
		this.contQty = contQty;
	}

	public long getContQty() {
		return this.contQty;
	}

	public void setContAvl(long contAvl) {
		this.contAvl = contAvl;
	}

	public long getContAvl() {
		return this.contAvl;
	}

	public void setContBuybkFrz(long contBuybkFrz) {
		this.contBuybkFrz = contBuybkFrz;
	}

	public long getContBuybkFrz() {
		return this.contBuybkFrz;
	}

	public void setHasBuybkQty(long hasBuybkQty) {
		this.hasBuybkQty = hasBuybkQty;
	}

	public long getHasBuybkQty() {
		return this.hasBuybkQty;
	}

	public void setBonusAmt(long bonusAmt) {
		this.bonusAmt = bonusAmt;
	}

	public long getBonusAmt() {
		return this.bonusAmt;
	}

	public void setIntQty(long intQty) {
		this.intQty = intQty;
	}

	public long getIntQty() {
		return this.intQty;
	}

	public void setContStatus(Character contStatus) {
		this.contStatus = contStatus;
	}

	public Character getContStatus() {
		return this.contStatus;
	}

	public void setBuybkQty(long buybkQty) {
		this.buybkQty = buybkQty;
	}

	public long getBuybkQty() {
		return this.buybkQty;
	}

	public void setFlowType(Character flowType) {
		this.flowType = flowType;
	}

	public Character getFlowType() {
		return this.flowType;
	}

	public void setRightsCls(String rightsCls) {
		this.rightsCls = rightsCls;
	}

	public String getRightsCls() {
		return this.rightsCls;
	}

	public void setListingYear(String listingYear) {
		this.listingYear = listingYear;
	}

	public String getListingYear() {
		return this.listingYear;
	}

	public void setActBuybkDate(int actBuybkDate) {
		this.actBuybkDate = actBuybkDate;
	}

	public int getActBuybkDate() {
		return this.actBuybkDate;
	}

}