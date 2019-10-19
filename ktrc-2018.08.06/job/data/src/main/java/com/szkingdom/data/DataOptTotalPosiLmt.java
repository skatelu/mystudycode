/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptTotalPosiLmt
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataOptTotalPosiLmt extends DataBase {

	private static final long serialVersionUID = 7601602817295869817L;
	private Character stkex;
	private String stkbd;
	private String totalLmtAttr;
	private long optPosiLqty;

	public DataOptTotalPosiLmt() {
	}

	public DataOptTotalPosiLmt(JSONObject json) {
	}

	public void init() {
		this.stkex = null;
		this.stkbd = "";
		this.totalLmtAttr = "";
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

	public void setTotalLmtAttr(String totalLmtAttr) {
		this.totalLmtAttr = totalLmtAttr;
	}

	public String getTotalLmtAttr() {
		return this.totalLmtAttr;
	}

	public void setOptPosiLqty(long optPosiLqty) {
		this.optPosiLqty = optPosiLqty;
	}

	public long getOptPosiLqty() {
		return this.optPosiLqty;
	}

}