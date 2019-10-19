/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptRealTotalPosi
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataOptRealTotalPosi extends DataBase {

	private static final long serialVersionUID = -626216691944385725L;
	private Character stkex;
	private String stkbd;
	private String optUndlCode;
	private String optUndlName;
	private Character lsFlag;
	private Character corpLmtAttr;
	private long actTotalPosi;
	private long realTotalPosi;
	private Character checkFlag;

	public DataOptRealTotalPosi() {
	}

	public DataOptRealTotalPosi(JSONObject json) {
	}

	public void init() {
		this.stkex = null;
		this.stkbd = "";
		this.optUndlCode = "";
		this.optUndlName = "";
		this.lsFlag = null;
		this.corpLmtAttr = null;
		this.actTotalPosi = 0L;
		this.realTotalPosi = 0L;
		this.checkFlag = null;

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

	public void setCorpLmtAttr(Character corpLmtAttr) {
		this.corpLmtAttr = corpLmtAttr;
	}

	public Character getCorpLmtAttr() {
		return this.corpLmtAttr;
	}

	public void setActTotalPosi(long actTotalPosi) {
		this.actTotalPosi = actTotalPosi;
	}

	public long getActTotalPosi() {
		return this.actTotalPosi;
	}

	public void setRealTotalPosi(long realTotalPosi) {
		this.realTotalPosi = realTotalPosi;
	}

	public long getRealTotalPosi() {
		return this.realTotalPosi;
	}

	public void setCheckFlag(Character checkFlag) {
		this.checkFlag = checkFlag;
	}

	public Character getCheckFlag() {
		return this.checkFlag;
	}

}