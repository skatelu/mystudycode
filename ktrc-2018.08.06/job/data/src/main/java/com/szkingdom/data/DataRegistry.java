/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRegistry
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataRegistry extends DataBase {

	private static final long serialVersionUID = -3834851355327056195L;
	private String regkeyId;
	private Character regkeyType;
	private String regkeyName;
	private Character regkeyStatus;
	private String regkeyParent;
	private String regkeyVal;
	private String dataType;
	private short validLen;
	private short validDec;
	private String maxVal;
	private String minVal;
	private String dftVal;
	private String validChar;
	private String ctrlId;
	private String ctrlData;
	private short subsys;

	public DataRegistry() {
	}

	public DataRegistry(JSONObject json) {
	}

	public void init() {
		this.regkeyId = "";
		this.regkeyType = null;
		this.regkeyName = "";
		this.regkeyStatus = null;
		this.regkeyParent = "";
		this.regkeyVal = "";
		this.dataType = "";
		this.validLen = 0;
		this.validDec = 0;
		this.maxVal = "";
		this.minVal = "";
		this.dftVal = "";
		this.validChar = "";
		this.ctrlId = "";
		this.ctrlData = "";
		this.subsys = 0;

	}

	public void setRegkeyId(String regkeyId) {
		this.regkeyId = regkeyId;
	}

	public String getRegkeyId() {
		return this.regkeyId;
	}

	public void setRegkeyType(Character regkeyType) {
		this.regkeyType = regkeyType;
	}

	public Character getRegkeyType() {
		return this.regkeyType;
	}

	public void setRegkeyName(String regkeyName) {
		this.regkeyName = regkeyName;
	}

	public String getRegkeyName() {
		return this.regkeyName;
	}

	public void setRegkeyStatus(Character regkeyStatus) {
		this.regkeyStatus = regkeyStatus;
	}

	public Character getRegkeyStatus() {
		return this.regkeyStatus;
	}

	public void setRegkeyParent(String regkeyParent) {
		this.regkeyParent = regkeyParent;
	}

	public String getRegkeyParent() {
		return this.regkeyParent;
	}

	public void setRegkeyVal(String regkeyVal) {
		this.regkeyVal = regkeyVal;
	}

	public String getRegkeyVal() {
		return this.regkeyVal;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setValidLen(short validLen) {
		this.validLen = validLen;
	}

	public short getValidLen() {
		return this.validLen;
	}

	public void setValidDec(short validDec) {
		this.validDec = validDec;
	}

	public short getValidDec() {
		return this.validDec;
	}

	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}

	public String getMaxVal() {
		return this.maxVal;
	}

	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}

	public String getMinVal() {
		return this.minVal;
	}

	public void setDftVal(String dftVal) {
		this.dftVal = dftVal;
	}

	public String getDftVal() {
		return this.dftVal;
	}

	public void setValidChar(String validChar) {
		this.validChar = validChar;
	}

	public String getValidChar() {
		return this.validChar;
	}

	public void setCtrlId(String ctrlId) {
		this.ctrlId = ctrlId;
	}

	public String getCtrlId() {
		return this.ctrlId;
	}

	public void setCtrlData(String ctrlData) {
		this.ctrlData = ctrlData;
	}

	public String getCtrlData() {
		return this.ctrlData;
	}

	public void setSubsys(short subsys) {
		this.subsys = subsys;
	}

	public short getSubsys() {
		return this.subsys;
	}

}