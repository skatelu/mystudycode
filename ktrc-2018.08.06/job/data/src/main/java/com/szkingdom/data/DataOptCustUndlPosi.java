/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptCustUndlPosi
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataOptCustUndlPosi extends DataBase {

	private static final long serialVersionUID = 4189306721288533976L;
	private long custCode;
	private String stkbd;
	private String trdacct;
	private Character optUndlCls;
	private String optUndlCode;
	private String optUndlName;
	private long totalPosi;
	private long longPosi;
	private long dailyOpenPosi;

	public DataOptCustUndlPosi() {
		init();
	}

	public DataOptCustUndlPosi(JSONObject json) {
	}

	public void init() {
		this.custCode = 0L;
		this.stkbd = "";
		this.trdacct = "";
		this.optUndlCls = null;
		this.optUndlCode = "";
		this.optUndlName = "";
		this.totalPosi = 0L;
		this.longPosi = 0L;
		this.dailyOpenPosi = 0L;

	}

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
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

	public void setTotalPosi(long totalPosi) {
		this.totalPosi = totalPosi;
	}

	public long getTotalPosi() {
		return this.totalPosi;
	}

	public void setLongPosi(long longPosi) {
		this.longPosi = longPosi;
	}

	public long getLongPosi() {
		return this.longPosi;
	}

	public void setDailyOpenPosi(long dailyOpenPosi) {
		this.dailyOpenPosi = dailyOpenPosi;
	}

	public long getDailyOpenPosi() {
		return this.dailyOpenPosi;
	}

}