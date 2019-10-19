/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkPlgLenderQuota
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStkPlgLenderQuota extends DataBase {

	private static final long serialVersionUID = 671065737340399384L;
	private String stkbd;
	private String trdacct;
	private String stkpbu;
	private long custCode;
	private long cuacctCode;
	private Character currency;
	private Character lenderCls;
	private Character pawneeCls;
	private String pawneeName;
	private long maxBondAmt;
	private long lastContAmt;
	private long lastBuyAmt;
	private long dayBuyAmt;
	private long dayBuybkAmt;
	private long netCptl;
	private long netCptlRate;

	public DataStkPlgLenderQuota() {
	}

	public DataStkPlgLenderQuota(JSONObject json) {
	}

	public void init() {
		this.stkbd = "";
		this.trdacct = "";
		this.stkpbu = "";
		this.custCode = 0L;
		this.cuacctCode = 0L;
		this.currency = null;
		this.lenderCls = null;
		this.pawneeCls = null;
		this.pawneeName = "";
		this.maxBondAmt = 0L;
		this.lastContAmt = 0L;
		this.lastBuyAmt = 0L;
		this.dayBuyAmt = 0L;
		this.dayBuybkAmt = 0L;
		this.netCptl = 0L;
		this.netCptlRate = 0L;

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

	public void setStkpbu(String stkpbu) {
		this.stkpbu = stkpbu;
	}

	public String getStkpbu() {
		return this.stkpbu;
	}

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
	}

	public void setCuacctCode(long cuacctCode) {
		this.cuacctCode = cuacctCode;
	}

	public long getCuacctCode() {
		return this.cuacctCode;
	}

	public void setCurrency(Character currency) {
		this.currency = currency;
	}

	public Character getCurrency() {
		return this.currency;
	}

	public void setLenderCls(Character lenderCls) {
		this.lenderCls = lenderCls;
	}

	public Character getLenderCls() {
		return this.lenderCls;
	}

	public void setPawneeCls(Character pawneeCls) {
		this.pawneeCls = pawneeCls;
	}

	public Character getPawneeCls() {
		return this.pawneeCls;
	}

	public void setPawneeName(String pawneeName) {
		this.pawneeName = pawneeName;
	}

	public String getPawneeName() {
		return this.pawneeName;
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

	public void setNetCptlRate(long netCptlRate) {
		this.netCptlRate = netCptlRate;
	}

	public long getNetCptlRate() {
		return this.netCptlRate;
	}

}