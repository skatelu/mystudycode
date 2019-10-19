/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptPerbizData
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataOptPerbizData extends DataBase {

	private static final long serialVersionUID = -226785702733604629L;
	private String itfId;
	private int itfCfgSn;
	private int recSn;
	private int trdDate;
	private int settDate;
	private short intOrg;
	private long custCode;
	private String custName;
	private long cuacctCode;
	private Character currency;
	private Character stkex;
	private String stkbd;
	private String trdacct;
	private String subacctCode;
	private int bizCode;
	private short stkBiz;
	private short stkBizAction;
	private long orderPrice;
	private long orderQty;
	private Character optUndlCls;
	private String optUndlCode;
	private String optUndlName;
	private long matchPrice;
	private long matchQty;
	private long matchAmt;
	private long settAmt;
	private int comeInDate;

	public DataOptPerbizData() {
	}

	public DataOptPerbizData(JSONObject json) {
	}

	public void init() {
		this.itfId = "";
		this.itfCfgSn = 0;
		this.recSn = 0;
		this.trdDate = 0;
		this.settDate = 0;
		this.intOrg = 0;
		this.custCode = 0L;
		this.custName = "";
		this.cuacctCode = 0L;
		this.currency = null;
		this.stkex = null;
		this.stkbd = "";
		this.trdacct = "";
		this.subacctCode = "";
		this.bizCode = 0;
		this.stkBiz = 0;
		this.stkBizAction = 0;
		this.orderPrice = 0L;
		this.orderQty = 0L;
		this.optUndlCls = null;
		this.optUndlCode = "";
		this.optUndlName = "";
		this.matchPrice = 0L;
		this.matchQty = 0L;
		this.matchAmt = 0L;
		this.settAmt = 0L;
		this.comeInDate = 0;

	}

	public void setItfId(String itfId) {
		this.itfId = itfId;
	}

	public String getItfId() {
		return this.itfId;
	}

	public void setItfCfgSn(int itfCfgSn) {
		this.itfCfgSn = itfCfgSn;
	}

	public int getItfCfgSn() {
		return this.itfCfgSn;
	}

	public void setRecSn(int recSn) {
		this.recSn = recSn;
	}

	public int getRecSn() {
		return this.recSn;
	}

	public void setTrdDate(int trdDate) {
		this.trdDate = trdDate;
	}

	public int getTrdDate() {
		return this.trdDate;
	}

	public void setSettDate(int settDate) {
		this.settDate = settDate;
	}

	public int getSettDate() {
		return this.settDate;
	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public short getIntOrg() {
		return this.intOrg;
	}

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustName() {
		return this.custName;
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

	public void setTrdacct(String trdacct) {
		this.trdacct = trdacct;
	}

	public String getTrdacct() {
		return this.trdacct;
	}

	public void setSubacctCode(String subacctCode) {
		this.subacctCode = subacctCode;
	}

	public String getSubacctCode() {
		return this.subacctCode;
	}

	public void setBizCode(int bizCode) {
		this.bizCode = bizCode;
	}

	public int getBizCode() {
		return this.bizCode;
	}

	public void setStkBiz(short stkBiz) {
		this.stkBiz = stkBiz;
	}

	public short getStkBiz() {
		return this.stkBiz;
	}

	public void setStkBizAction(short stkBizAction) {
		this.stkBizAction = stkBizAction;
	}

	public short getStkBizAction() {
		return this.stkBizAction;
	}

	public void setOrderPrice(long orderPrice) {
		this.orderPrice = orderPrice;
	}

	public long getOrderPrice() {
		return this.orderPrice;
	}

	public void setOrderQty(long orderQty) {
		this.orderQty = orderQty;
	}

	public long getOrderQty() {
		return this.orderQty;
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

	public void setMatchPrice(long matchPrice) {
		this.matchPrice = matchPrice;
	}

	public long getMatchPrice() {
		return this.matchPrice;
	}

	public void setMatchQty(long matchQty) {
		this.matchQty = matchQty;
	}

	public long getMatchQty() {
		return this.matchQty;
	}

	public void setMatchAmt(long matchAmt) {
		this.matchAmt = matchAmt;
	}

	public long getMatchAmt() {
		return this.matchAmt;
	}

	public void setSettAmt(long settAmt) {
		this.settAmt = settAmt;
	}

	public long getSettAmt() {
		return this.settAmt;
	}

	public void setComeInDate(int comeInDate) {
		this.comeInDate = comeInDate;
	}

	public int getComeInDate() {
		return this.comeInDate;
	}

}