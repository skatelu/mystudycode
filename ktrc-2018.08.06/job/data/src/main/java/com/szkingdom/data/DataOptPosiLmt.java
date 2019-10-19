/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptPosiLmt
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataOptPosiLmt extends DataBase {

	private static final long serialVersionUID = 259583667697467176L;
	private Character stkex;
	private String stkbd;
	private Character optUndlCls;
	private String optUndlCode;
	private String optUndlName;
	private Character lsFlag;
	private Character posiLmtAttr;
	private long optPosiLqty;

	public DataOptPosiLmt() {
	}

	public DataOptPosiLmt(JSONObject json) {
	}

	public void init() {
		this.stkex = null;
		this.stkbd = "";
		this.optUndlCls = null;
		this.optUndlCode = "";
		this.optUndlName = "";
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

	public void setOptUndlCode(String optUndlCode) {
		this.optUndlCode = optUndlCode;
	}

	public String getOptUndlCode() {
		return this.optUndlCode;
	}

	public void setOptUndlName(String optUndlName) {
		this.optUndlName = optUndlName;
	}

	public String getOptUndlName() {
		return this.optUndlName;
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