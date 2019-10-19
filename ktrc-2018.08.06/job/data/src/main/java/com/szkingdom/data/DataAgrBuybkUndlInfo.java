/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataAgrBuybkUndlInfo
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataAgrBuybkUndlInfo extends DataBase {

	private static final long serialVersionUID = 4709658253826338625L;
	private String stkbd;
	private String stkCode;
	private String agrBuybkVastCode;
	private Character priceType;
	private long orgTrdPrice;
	private long convertRate;
	private long totalShareCapital;
	private long lastContAmt;
	private long lastBuyAmt;
	private long dayBuyAmt;

	public DataAgrBuybkUndlInfo() {
	}

	public DataAgrBuybkUndlInfo(JSONObject json) {
	}

	public void init() {
		this.stkbd = "";
		this.stkCode = "";
		this.agrBuybkVastCode = "";
		this.priceType = null;
		this.orgTrdPrice = 0L;
		this.convertRate = 0L;
		this.totalShareCapital = 0L;
		this.lastContAmt = 0L;
		this.lastBuyAmt = 0L;
		this.dayBuyAmt = 0L;

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

	public void setAgrBuybkVastCode(String agrBuybkVastCode) {
		this.agrBuybkVastCode = agrBuybkVastCode;
	}

	public String getAgrBuybkVastCode() {
		return this.agrBuybkVastCode;
	}

	public void setPriceType(Character priceType) {
		this.priceType = priceType;
	}

	public Character getPriceType() {
		return this.priceType;
	}

	public void setOrgTrdPrice(long orgTrdPrice) {
		this.orgTrdPrice = orgTrdPrice;
	}

	public long getOrgTrdPrice() {
		return this.orgTrdPrice;
	}

	public void setConvertRate(long convertRate) {
		this.convertRate = convertRate;
	}

	public long getConvertRate() {
		return this.convertRate;
	}

	public void setTotalShareCapital(long totalShareCapital) {
		this.totalShareCapital = totalShareCapital;
	}

	public long getTotalShareCapital() {
		return this.totalShareCapital;
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

}