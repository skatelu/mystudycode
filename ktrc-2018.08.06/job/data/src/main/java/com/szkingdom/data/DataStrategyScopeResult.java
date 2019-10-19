/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStrategyScopeResult
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStrategyScopeResult extends DataBase {

	private static final long serialVersionUID = -7191387122552413770L;
	private int trdDate;
	private Character stkex;
	private String stkbd;
	private String optNum;
	private Character marginStrategy;
	private Character autoSplitDay;
	private Character scopeType;
	private long scopeResult;
	private int scopeNo;

	public DataStrategyScopeResult() {
        init();
	}

	public DataStrategyScopeResult(JSONObject json) {
	}

	public void init() {
		this.trdDate = 0;
		this.stkex = null;
		this.stkbd = "";
		this.optNum = "";
		this.marginStrategy = null;
		this.autoSplitDay = null;
		this.scopeType = null;
		this.scopeResult = 0L;
		this.scopeNo = 0;

	}

	public void setTrdDate(int trdDate) {
		this.trdDate = trdDate;
	}

	public int getTrdDate() {
		return this.trdDate;
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

	public void setMarginStrategy(Character marginStrategy) {
		this.marginStrategy = marginStrategy;
	}

	public Character getMarginStrategy() {
		return this.marginStrategy;
	}

	public void setAutoSplitDay(Character autoSplitDay) {
		this.autoSplitDay = autoSplitDay;
	}

	public Character getAutoSplitDay() {
		return this.autoSplitDay;
	}

	public void setScopeType(Character scopeType) {
		this.scopeType = scopeType;
	}

	public Character getScopeType() {
		return this.scopeType;
	}

	public void setScopeResult(long scopeResult) {
		this.scopeResult = scopeResult;
	}

	public long getScopeResult() {
		return this.scopeResult;
	}

	public void setScopeNo(int scopeNo) {
		this.scopeNo = scopeNo;
	}

	public int getScopeNo() {
		return this.scopeNo;
	}

}