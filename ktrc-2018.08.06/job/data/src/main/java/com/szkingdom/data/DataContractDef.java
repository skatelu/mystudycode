/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataContractDef
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import java.sql.Timestamp;

import com.alibaba.fastjson.JSONObject;

public class DataContractDef extends DataBase {

	private static final long serialVersionUID = -6250439718703235951L;
	private float contractCode;
	private String contractType;
	private String contractName;
	private String contractLocation;
	private Character isSyncUas;
	private Timestamp createTime;
	private Timestamp updateTime;

	public DataContractDef() {
	}

	public DataContractDef(JSONObject json) {
	}

	public void init() {
		this.contractCode = 0.0F;
		this.contractType = "";
		this.contractName = "";
		this.contractLocation = "";
		this.isSyncUas = null;
		this.createTime = null;
		this.updateTime = null;

	}

	public void setContractCode(float contractCode) {
		this.contractCode = contractCode;
	}

	public float getContractCode() {
		return this.contractCode;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractType() {
		return this.contractType;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getContractName() {
		return this.contractName;
	}

	public void setContractLocation(String contractLocation) {
		this.contractLocation = contractLocation;
	}

	public String getContractLocation() {
		return this.contractLocation;
	}

	public void setIsSyncUas(Character isSyncUas) {
		this.isSyncUas = isSyncUas;
	}

	public Character getIsSyncUas() {
		return this.isSyncUas;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

}