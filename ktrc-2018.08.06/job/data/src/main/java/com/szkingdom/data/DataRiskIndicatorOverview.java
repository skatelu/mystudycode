/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskIndicatorOverview
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataRiskIndicatorOverview extends DataBase {

	private static final long serialVersionUID = 4307260613721577670L;
	private String indicatorId;
	private String indicatorName;
	private String bizAttr;
	private String indicatorCls;
	private String indicatorClsName;
	private int occDate;
	private int occTime;
	private long riskTriggerQty;
	private long toBeNoticedQty;
	private Character riskTriggerFlag;
	private Character toBeNoticedFlag;

	public DataRiskIndicatorOverview() {
	}

	public DataRiskIndicatorOverview(JSONObject json) {
	}

	public void init() {
		this.indicatorId = "";
		this.indicatorName = "";
		this.bizAttr = "";
		this.indicatorCls = "";
		this.indicatorClsName = "";
		this.occDate = 0;
		this.occTime = 0;
		this.riskTriggerQty = 0L;
		this.toBeNoticedQty = 0L;
		this.riskTriggerFlag = null;
		this.toBeNoticedFlag = null;

	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getIndicatorId() {
		return this.indicatorId;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public String getIndicatorName() {
		return this.indicatorName;
	}

	public void setBizAttr(String bizAttr) {
		this.bizAttr = bizAttr;
	}

	public String getBizAttr() {
		return this.bizAttr;
	}

	public void setIndicatorCls(String indicatorCls) {
		this.indicatorCls = indicatorCls;
	}

	public String getIndicatorCls() {
		return this.indicatorCls;
	}

	public void setIndicatorClsName(String indicatorClsName) {
		this.indicatorClsName = indicatorClsName;
	}

	public String getIndicatorClsName() {
		return this.indicatorClsName;
	}

	public void setOccDate(int occDate) {
		this.occDate = occDate;
	}

	public int getOccDate() {
		return this.occDate;
	}

	public void setOccTime(int occTime) {
		this.occTime = occTime;
	}

	public int getOccTime() {
		return this.occTime;
	}

	public void setRiskTriggerQty(long riskTriggerQty) {
		this.riskTriggerQty = riskTriggerQty;
	}

	public long getRiskTriggerQty() {
		return this.riskTriggerQty;
	}

	public void setToBeNoticedQty(long toBeNoticedQty) {
		this.toBeNoticedQty = toBeNoticedQty;
	}

	public long getToBeNoticedQty() {
		return this.toBeNoticedQty;
	}

	public void setRiskTriggerFlag(Character riskTriggerFlag) {
		this.riskTriggerFlag = riskTriggerFlag;
	}

	public Character getRiskTriggerFlag() {
		return this.riskTriggerFlag;
	}

	public void setToBeNoticedFlag(Character toBeNoticedFlag) {
		this.toBeNoticedFlag = toBeNoticedFlag;
	}

	public Character getToBeNoticedFlag() {
		return this.toBeNoticedFlag;
	}

}