/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptCustPosiLmt
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataOptCustPosiLmt extends DataBase {

	private static final long serialVersionUID = -918657692323408431L;
	private Character stkex;
	private String stkbd;
	private long custCode;
	private String trdacct;
	private Character optUndlCls;
	private String optUndlCode;
	private Character lsFlag;
	private Character custLmtAttr;
	private long optPosiLqty;
	private int posiBgnDate;
	private int posiEndDate;

	public DataOptCustPosiLmt() {
	}

	public DataOptCustPosiLmt(JSONObject json) {
	}

	public void init() {
		this.stkex = null;
		this.stkbd = "";
		this.custCode = 0L;
		this.trdacct = "";
		this.optUndlCls = null;
		this.optUndlCode = "";
		this.lsFlag = null;
		this.custLmtAttr = null;
		this.optPosiLqty = 0L;
		this.posiBgnDate = 0;
		this.posiEndDate = 0;

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

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
	}

	public void setTrdacct(String trdacct) {
		this.trdacct = trdacct;
	}

	public String getTrdacct() {
		return this.trdacct;
	}

	public void setOptUndlCls(Character optUndlCls) {
		this.optUndlCls = optUndlCls;
	}

	public Character getOptUndlCls() {
		return this.optUndlCls;
	}

	public void setOptUndlCode(String optUndlCode) {
		this.optUndlCode = optUndlCode;
	}

	public String getOptUndlCode() {
		return this.optUndlCode;
	}

	public void setLsFlag(Character lsFlag) {
		this.lsFlag = lsFlag;
	}

	public Character getLsFlag() {
		return this.lsFlag;
	}

	public void setCustLmtAttr(Character custLmtAttr) {
		this.custLmtAttr = custLmtAttr;
	}

	public Character getCustLmtAttr() {
		return this.custLmtAttr;
	}

	public void setOptPosiLqty(long optPosiLqty) {
		this.optPosiLqty = optPosiLqty;
	}

	public long getOptPosiLqty() {
		return this.optPosiLqty;
	}

	public void setPosiBgnDate(int posiBgnDate) {
		this.posiBgnDate = posiBgnDate;
	}

	public int getPosiBgnDate() {
		return this.posiBgnDate;
	}

	public void setPosiEndDate(int posiEndDate) {
		this.posiEndDate = posiEndDate;
	}

	public int getPosiEndDate() {
		return this.posiEndDate;
	}

}