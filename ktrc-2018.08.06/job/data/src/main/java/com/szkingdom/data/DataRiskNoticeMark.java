/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskNoticeMark
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataRiskNoticeMark extends DataBase {

	private static final long serialVersionUID = 8976150615081480778L;
	private String indicatorId;
	private String markName;
	private String markId;
	private Character ddFlag;
	private String markDd;

	public DataRiskNoticeMark() {
	}

	public DataRiskNoticeMark(JSONObject json) {
	}

	public void init() {
		this.indicatorId = "";
		this.markName = "";
		this.markId = "";
		this.ddFlag = null;
		this.markDd = "";

	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getIndicatorId() {
		return this.indicatorId;
	}

	public void setMarkName(String markName) {
		this.markName = markName;
	}

	public String getMarkName() {
		return this.markName;
	}

	public void setMarkId(String markId) {
		this.markId = markId;
	}

	public String getMarkId() {
		return this.markId;
	}

	public void setDdFlag(Character ddFlag) {
		this.ddFlag = ddFlag;
	}

	public Character getDdFlag() {
		return this.ddFlag;
	}

	public void setMarkDd(String markDd) {
		this.markDd = markDd;
	}

	public String getMarkDd() {
		return this.markDd;
	}

}