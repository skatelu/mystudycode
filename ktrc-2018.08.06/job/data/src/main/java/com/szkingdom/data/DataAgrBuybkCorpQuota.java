/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataAgrBuybkCorpQuota
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataAgrBuybkCorpQuota extends DataBase {

	private static final long serialVersionUID = -6150990896167617521L;
	private String stkbd;
	private String trdacct;
	private Character extCls;
	private String stkpbu;
	private short intOrg;
	private long maxBondAmt;
	private long lastContAmt;
	private long lastBuyAmt;
	private long dayBuyAmt;
	private long dayBuybkAmt;
	private long netCptl;

	public DataAgrBuybkCorpQuota() {
	}

	public DataAgrBuybkCorpQuota(JSONObject json) {
	}

	public void init() {
		this.stkbd = "";
		this.trdacct = "";
		this.extCls = null;
		this.stkpbu = "";
		this.intOrg = 0;
		this.maxBondAmt = 0L;
		this.lastContAmt = 0L;
		this.lastBuyAmt = 0L;
		this.dayBuyAmt = 0L;
		this.dayBuybkAmt = 0L;
		this.netCptl = 0L;

	}

	public void setStkbd(String stkbd) {
		this.stkbd = stkbd;
	}

	public String getStkbd() {
		return this.stkbd;
	}

	public void setTrdacct(String trdacct) {
		this.trdacct = trdacct;
	}

	public String getTrdacct() {
		return this.trdacct;
	}

	public void setExtCls(Character extCls) {
		this.extCls = extCls;
	}

	public Character getExtCls() {
		return this.extCls;
	}

	public void setStkpbu(String stkpbu) {
		this.stkpbu = stkpbu;
	}

	public String getStkpbu() {
		return this.stkpbu;
	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public short getIntOrg() {
		return this.intOrg;
	}

	public void setMaxBondAmt(long maxBondAmt) {
		this.maxBondAmt = maxBondAmt;
	}

	public long getMaxBondAmt() {
		return this.maxBondAmt;
	}

	public void setLastContAmt(long lastContAmt) {
		this.lastContAmt = lastContAmt;
	}

	public long getLastContAmt() {
		return this.lastContAmt;
	}

	public void setLastBuyAmt(long lastBuyAmt) {
		this.lastBuyAmt = lastBuyAmt;
	}

	public long getLastBuyAmt() {
		return this.lastBuyAmt;
	}

	public void setDayBuyAmt(long dayBuyAmt) {
		this.dayBuyAmt = dayBuyAmt;
	}

	public long getDayBuyAmt() {
		return this.dayBuyAmt;
	}

	public void setDayBuybkAmt(long dayBuybkAmt) {
		this.dayBuybkAmt = dayBuybkAmt;
	}

	public long getDayBuybkAmt() {
		return this.dayBuybkAmt;
	}

	public void setNetCptl(long netCptl) {
		this.netCptl = netCptl;
	}

	public long getNetCptl() {
		return this.netCptl;
	}

}