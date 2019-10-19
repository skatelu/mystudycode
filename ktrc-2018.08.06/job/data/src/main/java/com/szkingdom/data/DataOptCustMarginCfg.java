/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptCustMarginCfg
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataOptCustMarginCfg extends DataBase {

	private static final long serialVersionUID = 7655996581090617969L;
	private short intOrg;
	private Character marginCfgType;
	private String marginCfgValue;
	private Character strategyTemplate;

	public DataOptCustMarginCfg() {
	}

	public DataOptCustMarginCfg(JSONObject json) {
	}

	public void init() {
		this.intOrg = 0;
		this.marginCfgType = null;
		this.marginCfgValue = "";
		this.strategyTemplate = null;

	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public short getIntOrg() {
		return this.intOrg;
	}

	public void setMarginCfgType(Character marginCfgType) {
		this.marginCfgType = marginCfgType;
	}

	public Character getMarginCfgType() {
		return this.marginCfgType;
	}

	public void setMarginCfgValue(String marginCfgValue) {
		this.marginCfgValue = marginCfgValue;
	}

	public String getMarginCfgValue() {
		return this.marginCfgValue;
	}

	public void setStrategyTemplate(Character strategyTemplate) {
		this.strategyTemplate = strategyTemplate;
	}

	public Character getStrategyTemplate() {
		return this.strategyTemplate;
	}

}