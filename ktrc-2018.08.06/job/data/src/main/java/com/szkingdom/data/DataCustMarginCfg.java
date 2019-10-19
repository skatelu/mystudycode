/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataCustMarginCfg
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataCustMarginCfg extends DataBase {

	private static final long serialVersionUID = -671589512858548133L;
	private short intOrg;
	private Character cuacctGrp;
	private long cuacctCode;
	private Character strategyTemplate;

	public DataCustMarginCfg() {
	}

	public DataCustMarginCfg(JSONObject json) {
	}

	public void init() {
		this.intOrg = 0;
		this.cuacctGrp = null;
		this.cuacctCode = 0L;
		this.strategyTemplate = null;

	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public short getIntOrg() {
		return this.intOrg;
	}

	public void setCuacctGrp(Character cuacctGrp) {
		this.cuacctGrp = cuacctGrp;
	}

	public Character getCuacctGrp() {
		return this.cuacctGrp;
	}

	public void setCuacctCode(long cuacctCode) {
		this.cuacctCode = cuacctCode;
	}

	public long getCuacctCode() {
		return this.cuacctCode;
	}

	public void setStrategyTemplate(Character strategyTemplate) {
		this.strategyTemplate = strategyTemplate;
	}

	public Character getStrategyTemplate() {
		return this.strategyTemplate;
	}

}