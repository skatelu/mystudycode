/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptClsPosiLmt
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataOptClsPosiLmt extends DataBase {

	private static final long serialVersionUID = -9164043783273106591L;
	private Character stkex;
	private String stkbd;
	private Character optUndlCls;
	private Character lsFlag;
	private Character posiLmtAttr;
	private long optPosiLqty;

	public DataOptClsPosiLmt() {
	}

	public DataOptClsPosiLmt(JSONObject json) {
	}

	public void init() {
		this.stkex = null;
		this.stkbd = "";
		this.optUndlCls = null;
		this.lsFlag = null;
		this.posiLmtAttr = null;
		this.optPosiLqty = 0L;

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

	public void setOptUndlCls(Character optUndlCls) {
		this.optUndlCls = optUndlCls;
	}

	public Character getOptUndlCls() {
		return this.optUndlCls;
	}

	public void setLsFlag(Character lsFlag) {
		this.lsFlag = lsFlag;
	}

	public Character getLsFlag() {
		return this.lsFlag;
	}

	public void setPosiLmtAttr(Character posiLmtAttr) {
		this.posiLmtAttr = posiLmtAttr;
	}

	public Character getPosiLmtAttr() {
		return this.posiLmtAttr;
	}

	public void setOptPosiLqty(long optPosiLqty) {
		this.optPosiLqty = optPosiLqty;
	}

	public long getOptPosiLqty() {
		return this.optPosiLqty;
	}

}