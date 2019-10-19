/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkMktinfo
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStkMktinfo extends DataBase {

	private static final long serialVersionUID = -5931205680771715310L;
	private Character stkex;
	private String stkbd;
	private String stkCode;
	private int trdDate;
	private long closingPrice;
	private long openingPrice;
	private long currentPrice;
	private long bondInt;
	private int bondIntDate;
	private long etfIopv;

	public DataStkMktinfo() {
	}

	public DataStkMktinfo(JSONObject json) {
	}

	public void init() {
		this.stkex = null;
		this.stkbd = "";
		this.stkCode = "";
		this.trdDate = 0;
		this.closingPrice = 0L;
		this.openingPrice = 0L;
		this.currentPrice = 0L;
		this.bondInt = 0L;
		this.bondIntDate = 0;
		this.etfIopv = 0L;

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

	public void setStkCode(String stkCode) {
		this.stkCode = stkCode;
	}

	public String getStkCode() {
		return this.stkCode;
	}

	public void setTrdDate(int trdDate) {
		this.trdDate = trdDate;
	}

	public int getTrdDate() {
		return this.trdDate;
	}

	public void setClosingPrice(long closingPrice) {
		this.closingPrice = closingPrice;
	}

	public long getClosingPrice() {
		return this.closingPrice;
	}

	public void setOpeningPrice(long openingPrice) {
		this.openingPrice = openingPrice;
	}

	public long getOpeningPrice() {
		return this.openingPrice;
	}

	public void setCurrentPrice(long currentPrice) {
		this.currentPrice = currentPrice;
	}

	public long getCurrentPrice() {
		return this.currentPrice;
	}

	public void setBondInt(long bondInt) {
		this.bondInt = bondInt;
	}

	public long getBondInt() {
		return this.bondInt;
	}

	public void setBondIntDate(int bondIntDate) {
		this.bondIntDate = bondIntDate;
	}

	public int getBondIntDate() {
		return this.bondIntDate;
	}

	public void setEtfIopv(long etfIopv) {
		this.etfIopv = etfIopv;
	}

	public long getEtfIopv() {
		return this.etfIopv;
	}

}