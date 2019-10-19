/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptInfo
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataOptInfo extends DataBase {

	private static final long serialVersionUID = 3237711262884990549L;
	private Character stkex;
	private String stkbd;
	private String optNum;
	private String optCode;
	private String optName;
	private Character optType;
	private String optUndlCode;
	private String optUndlName;
	private Character optUndlCls;
	private Character optExeType;
	private long optUnit;
	private long exercisePrice;
	private int startDate;
	private int endDate;
	private int exerciseDate;
	private int deliveryDate;
	private int expireDate;
	private Character updVersion;
	private long leavesQty;
	private long preClosePx;
	private long preSettPrice;
	private long undlClsPrice;
	private Character priceLmtType;
	private long optUplmtPrice;
	private long optLwlmtPrice;
	private long marginUnit;
	private long marginRatio1;
	private long marginRatio2;
	private long optLotSize;
	private long optLbuplmtQty;
	private long optLsuplmtQty;
	private long optLlwlmtQty;
	private long optMbuplmtQty;
	private long optMsuplmtQty;
	private long optMlwlmtQty;
	private long tickSize;
	private Character openFlag;
	private Character suspendedFlag;
	private Character expireFlag;
	private Character adjustFlag;
	private Character optStatus;
	private Character combFlag;
	private int autoSplitDate;
	private int updDate;

    public DataOptInfo(){
        init();
    }

    public DataOptInfo(JSONObject json){
    }

	public void init() {
		this.stkex = null;
		this.stkbd = "";
		this.optNum = "";
		this.optCode = "";
		this.optName = "";
		this.optType = null;
		this.optUndlCode = "";
		this.optUndlName = "";
		this.optUndlCls = null;
		this.optExeType = null;
		this.optUnit = 0L;
		this.exercisePrice = 0L;
		this.startDate = 0;
		this.endDate = 0;
		this.exerciseDate = 0;
		this.deliveryDate = 0;
		this.expireDate = 0;
		this.updVersion = null;
		this.leavesQty = 0L;
		this.preClosePx = 0L;
		this.preSettPrice = 0L;
		this.undlClsPrice = 0L;
		this.priceLmtType = null;
		this.optUplmtPrice = 0L;
		this.optLwlmtPrice = 0L;
		this.marginUnit = 0L;
		this.marginRatio1 = 0L;
		this.marginRatio2 = 0L;
		this.optLotSize = 0L;
		this.optLbuplmtQty = 0L;
		this.optLsuplmtQty = 0L;
		this.optLlwlmtQty = 0L;
		this.optMbuplmtQty = 0L;
		this.optMsuplmtQty = 0L;
		this.optMlwlmtQty = 0L;
		this.tickSize = 0L;
		this.openFlag = null;
		this.suspendedFlag = null;
		this.expireFlag = null;
		this.adjustFlag = null;
		this.optStatus = null;
		this.combFlag = null;
		this.autoSplitDate = 0;
		this.updDate = 0;

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

	public void setOptNum(String optNum) {
		this.optNum = optNum;
	}

	public String getOptNum() {
		return this.optNum;
	}

	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}

	public String getOptCode() {
		return this.optCode;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getOptName() {
		return this.optName;
	}

	public void setOptType(Character optType) {
		this.optType = optType;
	}

	public Character getOptType() {
		return this.optType;
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

	public void setOptUndlCls(Character optUndlCls) {
		this.optUndlCls = optUndlCls;
	}

	public Character getOptUndlCls() {
		return this.optUndlCls;
	}

	public void setOptExeType(Character optExeType) {
		this.optExeType = optExeType;
	}

	public Character getOptExeType() {
		return this.optExeType;
	}

	public void setOptUnit(long optUnit) {
		this.optUnit = optUnit;
	}

	public long getOptUnit() {
		return this.optUnit;
	}

	public void setExercisePrice(long exercisePrice) {
		this.exercisePrice = exercisePrice;
	}

	public long getExercisePrice() {
		return this.exercisePrice;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public int getStartDate() {
		return this.startDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	public int getEndDate() {
		return this.endDate;
	}

	public void setExerciseDate(int exerciseDate) {
		this.exerciseDate = exerciseDate;
	}

	public int getExerciseDate() {
		return this.exerciseDate;
	}

	public void setDeliveryDate(int deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setExpireDate(int expireDate) {
		this.expireDate = expireDate;
	}

	public int getExpireDate() {
		return this.expireDate;
	}

	public void setUpdVersion(Character updVersion) {
		this.updVersion = updVersion;
	}

	public Character getUpdVersion() {
		return this.updVersion;
	}

	public void setLeavesQty(long leavesQty) {
		this.leavesQty = leavesQty;
	}

	public long getLeavesQty() {
		return this.leavesQty;
	}

	public void setPreClosePx(long preClosePx) {
		this.preClosePx = preClosePx;
	}

	public long getPreClosePx() {
		return this.preClosePx;
	}

	public void setPreSettPrice(long preSettPrice) {
		this.preSettPrice = preSettPrice;
	}

	public long getPreSettPrice() {
		return this.preSettPrice;
	}

	public void setUndlClsPrice(long undlClsPrice) {
		this.undlClsPrice = undlClsPrice;
	}

	public long getUndlClsPrice() {
		return this.undlClsPrice;
	}

	public void setPriceLmtType(Character priceLmtType) {
		this.priceLmtType = priceLmtType;
	}

	public Character getPriceLmtType() {
		return this.priceLmtType;
	}

	public void setOptUplmtPrice(long optUplmtPrice) {
		this.optUplmtPrice = optUplmtPrice;
	}

	public long getOptUplmtPrice() {
		return this.optUplmtPrice;
	}

	public void setOptLwlmtPrice(long optLwlmtPrice) {
		this.optLwlmtPrice = optLwlmtPrice;
	}

	public long getOptLwlmtPrice() {
		return this.optLwlmtPrice;
	}

	public void setMarginUnit(long marginUnit) {
		this.marginUnit = marginUnit;
	}

	public long getMarginUnit() {
		return this.marginUnit;
	}

	public void setMarginRatio1(long marginRatio1) {
		this.marginRatio1 = marginRatio1;
	}

	public long getMarginRatio1() {
		return this.marginRatio1;
	}

	public void setMarginRatio2(long marginRatio2) {
		this.marginRatio2 = marginRatio2;
	}

	public long getMarginRatio2() {
		return this.marginRatio2;
	}

	public void setOptLotSize(long optLotSize) {
		this.optLotSize = optLotSize;
	}

	public long getOptLotSize() {
		return this.optLotSize;
	}

	public void setOptLbuplmtQty(long optLbuplmtQty) {
		this.optLbuplmtQty = optLbuplmtQty;
	}

	public long getOptLbuplmtQty() {
		return this.optLbuplmtQty;
	}

	public void setOptLsuplmtQty(long optLsuplmtQty) {
		this.optLsuplmtQty = optLsuplmtQty;
	}

	public long getOptLsuplmtQty() {
		return this.optLsuplmtQty;
	}

	public void setOptLlwlmtQty(long optLlwlmtQty) {
		this.optLlwlmtQty = optLlwlmtQty;
	}

	public long getOptLlwlmtQty() {
		return this.optLlwlmtQty;
	}

	public void setOptMbuplmtQty(long optMbuplmtQty) {
		this.optMbuplmtQty = optMbuplmtQty;
	}

	public long getOptMbuplmtQty() {
		return this.optMbuplmtQty;
	}

	public void setOptMsuplmtQty(long optMsuplmtQty) {
		this.optMsuplmtQty = optMsuplmtQty;
	}

	public long getOptMsuplmtQty() {
		return this.optMsuplmtQty;
	}

	public void setOptMlwlmtQty(long optMlwlmtQty) {
		this.optMlwlmtQty = optMlwlmtQty;
	}

	public long getOptMlwlmtQty() {
		return this.optMlwlmtQty;
	}

	public void setTickSize(long tickSize) {
		this.tickSize = tickSize;
	}

	public long getTickSize() {
		return this.tickSize;
	}

	public void setOpenFlag(Character openFlag) {
		this.openFlag = openFlag;
	}

	public Character getOpenFlag() {
		return this.openFlag;
	}

	public void setSuspendedFlag(Character suspendedFlag) {
		this.suspendedFlag = suspendedFlag;
	}

	public Character getSuspendedFlag() {
		return this.suspendedFlag;
	}

	public void setExpireFlag(Character expireFlag) {
		this.expireFlag = expireFlag;
	}

	public Character getExpireFlag() {
		return this.expireFlag;
	}

	public void setAdjustFlag(Character adjustFlag) {
		this.adjustFlag = adjustFlag;
	}

	public Character getAdjustFlag() {
		return this.adjustFlag;
	}

	public void setOptStatus(Character optStatus) {
		this.optStatus = optStatus;
	}

	public Character getOptStatus() {
		return this.optStatus;
	}

	public void setCombFlag(Character combFlag) {
		this.combFlag = combFlag;
	}

	public Character getCombFlag() {
		return this.combFlag;
	}

	public void setAutoSplitDate(int autoSplitDate) {
		this.autoSplitDate = autoSplitDate;
	}

	public int getAutoSplitDate() {
		return this.autoSplitDate;
	}

	public void setUpdDate(int updDate) {
		this.updDate = updDate;
	}

	public int getUpdDate() {
		return this.updDate;
	}

}