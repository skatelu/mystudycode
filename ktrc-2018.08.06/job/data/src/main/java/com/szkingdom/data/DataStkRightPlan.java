/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkRightPlan
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStkRightPlan extends DataBase {

	private static final long serialVersionUID = -3350010167124941355L;
	private int recvDate;
	private Character stkex;
	private String stkbd;
	private String stkCode;
	private int regDate;
	private int dividendDate;
	private int endDate;
	private long bonusAmt;
	private long stkQty;
	private long rightPrice;
	private long rightQty;
	private long floatChgRate;
	private int aheadNotifyDays;
	private long bonusRate;
	private long allotmentRate;
	private long conversionRate;
	private long shareConverRate;

	public DataStkRightPlan() {
	}

	public DataStkRightPlan(JSONObject json) {
	}

	public void init() {
		this.recvDate = 0;
		this.stkex = null;
		this.stkbd = "";
		this.stkCode = "";
		this.regDate = 0;
		this.dividendDate = 0;
		this.endDate = 0;
		this.bonusAmt = 0L;
		this.stkQty = 0L;
		this.rightPrice = 0L;
		this.rightQty = 0L;
		this.floatChgRate = 0L;
		this.aheadNotifyDays = 0;
		this.bonusRate = 0L;
		this.allotmentRate = 0L;
		this.conversionRate = 0L;
		this.shareConverRate = 0L;

	}

	public void setRecvDate(int recvDate) {
		this.recvDate = recvDate;
	}

	public int getRecvDate() {
		return this.recvDate;
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

	public void setRegDate(int regDate) {
		this.regDate = regDate;
	}

	public int getRegDate() {
		return this.regDate;
	}

	public void setDividendDate(int dividendDate) {
		this.dividendDate = dividendDate;
	}

	public int getDividendDate() {
		return this.dividendDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	public int getEndDate() {
		return this.endDate;
	}

	public void setBonusAmt(long bonusAmt) {
		this.bonusAmt = bonusAmt;
	}

	public long getBonusAmt() {
		return this.bonusAmt;
	}

	public void setStkQty(long stkQty) {
		this.stkQty = stkQty;
	}

	public long getStkQty() {
		return this.stkQty;
	}

	public void setRightPrice(long rightPrice) {
		this.rightPrice = rightPrice;
	}

	public long getRightPrice() {
		return this.rightPrice;
	}

	public void setRightQty(long rightQty) {
		this.rightQty = rightQty;
	}

	public long getRightQty() {
		return this.rightQty;
	}

	public void setFloatChgRate(long floatChgRate) {
		this.floatChgRate = floatChgRate;
	}

	public long getFloatChgRate() {
		return this.floatChgRate;
	}

	public void setAheadNotifyDays(int aheadNotifyDays) {
		this.aheadNotifyDays = aheadNotifyDays;
	}

	public int getAheadNotifyDays() {
		return this.aheadNotifyDays;
	}

	public void setBonusRate(long bonusRate) {
		this.bonusRate = bonusRate;
	}

	public long getBonusRate() {
		return this.bonusRate;
	}

	public void setAllotmentRate(long allotmentRate) {
		this.allotmentRate = allotmentRate;
	}

	public long getAllotmentRate() {
		return this.allotmentRate;
	}

	public void setConversionRate(long conversionRate) {
		this.conversionRate = conversionRate;
	}

	public long getConversionRate() {
		return this.conversionRate;
	}

	public void setShareConverRate(long shareConverRate) {
		this.shareConverRate = shareConverRate;
	}

	public long getShareConverRate() {
		return this.shareConverRate;
	}

}