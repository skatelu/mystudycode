/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskIndicators
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataRiskIndicators extends DataBase {

	private static final long serialVersionUID = -8144021947010463565L;
	private String indicatorId;
	private String indicatorName;
	private String bizAttr;
	private String indicatorCls;
	private String indicatorFormula;
	private Character indicatorResultType;
	private String indicatorFactors;
	private Character indicatorRunFlag;
	private Character isRealtime;
	private String indicatorRemark;

	public DataRiskIndicators() {
	}

	public DataRiskIndicators(JSONObject json) {
	}

	public void init() {
		this.indicatorId = "";
		this.indicatorName = "";
		this.bizAttr = "";
		this.indicatorCls = "";
		this.indicatorFormula = "";
		this.indicatorResultType = null;
		this.indicatorFactors = "";
		this.indicatorRunFlag = null;
		this.isRealtime = null;
		this.indicatorRemark = "";

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

	public void setIndicatorFormula(String indicatorFormula) {
		this.indicatorFormula = indicatorFormula;
	}

	public String getIndicatorFormula() {
		return this.indicatorFormula;
	}

	public void setIndicatorResultType(Character indicatorResultType) {
		this.indicatorResultType = indicatorResultType;
	}

	public Character getIndicatorResultType() {
		return this.indicatorResultType;
	}

	public void setIndicatorFactors(String indicatorFactors) {
		this.indicatorFactors = indicatorFactors;
	}

	public String getIndicatorFactors() {
		return this.indicatorFactors;
	}

	public void setIndicatorRunFlag(Character indicatorRunFlag) {
		this.indicatorRunFlag = indicatorRunFlag;
	}

	public Character getIndicatorRunFlag() {
		return this.indicatorRunFlag;
	}

	public void setIsRealtime(Character isRealtime) {
		this.isRealtime = isRealtime;
	}

	public Character getIsRealtime() {
		return this.isRealtime;
	}

	public void setIndicatorRemark(String indicatorRemark) {
		this.indicatorRemark = indicatorRemark;
	}

	public String getIndicatorRemark() {
		return this.indicatorRemark;
	}

}