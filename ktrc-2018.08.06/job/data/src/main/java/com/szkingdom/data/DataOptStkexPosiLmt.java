/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptStkexPosiLmt
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataOptStkexPosiLmt extends DataBase {

	private static final long serialVersionUID = 8317853280433366499L;
	private Character stkex;
	private String stkbd;
	private Character lsFlag;
	private Character stkexLmtAttr;
	private long optPosiLqty;

	public DataOptStkexPosiLmt() {
	}

	public DataOptStkexPosiLmt(JSONObject json) {
	}

	public void init() {
		this.stkex = null;
		this.stkbd = "";
		this.lsFlag = null;
		this.stkexLmtAttr = null;
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

	public void setLsFlag(Character lsFlag) {
		this.lsFlag = lsFlag;
	}

	public Character getLsFlag() {
		return this.lsFlag;
	}

	public void setStkexLmtAttr(Character stkexLmtAttr) {
		this.stkexLmtAttr = stkexLmtAttr;
	}

	public Character getStkexLmtAttr() {
		return this.stkexLmtAttr;
	}

	public void setOptPosiLqty(long optPosiLqty) {
		this.optPosiLqty = optPosiLqty;
	}

	public long getOptPosiLqty() {
		return this.optPosiLqty;
	}

}