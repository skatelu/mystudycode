/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskIndicatorsCls
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataRiskIndicatorsCls extends DataBase {

	private static final long serialVersionUID = 4585072652799757140L;
	private String bizAttr;
	private String indicatorCls;
	private String indicatorClsName;

	public DataRiskIndicatorsCls() {
	}

	public DataRiskIndicatorsCls(JSONObject json) {
	}

	public void init() {
		this.bizAttr = "";
		this.indicatorCls = "";
		this.indicatorClsName = "";

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

}