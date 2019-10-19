/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataCustIndicatorThres
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataCustIndicatorThres extends DataBase {

	private static final long serialVersionUID = -7623359359747715337L;
	private String indicatorId;
	private long custCode;
	private short thresCnt;
	private long threshold1;
	private Character thres1No;
	private String thres1Name;
	private String thres1Color;
	private String thres1Oper;
	private Character thres1Valid;
	private long threshold2;
	private Character thres2No;
	private String thres2Name;
	private String thres2Color;
	private String thres2Oper;
	private Character thres2Valid;
	private long threshold3;
	private Character thres3No;
	private String thres3Name;
	private String thres3Color;
	private String thres3Oper;
	private Character thres3Valid;
	private long threshold4;
	private Character thres4No;
	private String thres4Name;
	private String thres4Color;
	private String thres4Oper;
	private Character thres4Valid;

	public DataCustIndicatorThres() {
	}

	public DataCustIndicatorThres(JSONObject json) {
	}

	public void init() {
		this.indicatorId = "";
		this.custCode = 0L;
		this.thresCnt = 0;
		this.threshold1 = 0L;
		this.thres1No = null;
		this.thres1Name = "";
		this.thres1Color = "";
		this.thres1Oper = "";
		this.thres1Valid = null;
		this.threshold2 = 0L;
		this.thres2No = null;
		this.thres2Name = "";
		this.thres2Color = "";
		this.thres2Oper = "";
		this.thres2Valid = null;
		this.threshold3 = 0L;
		this.thres3No = null;
		this.thres3Name = "";
		this.thres3Color = "";
		this.thres3Oper = "";
		this.thres3Valid = null;
		this.threshold4 = 0L;
		this.thres4No = null;
		this.thres4Name = "";
		this.thres4Color = "";
		this.thres4Oper = "";
		this.thres4Valid = null;

	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getIndicatorId() {
		return this.indicatorId;
	}

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
	}

	public void setThresCnt(short thresCnt) {
		this.thresCnt = thresCnt;
	}

	public short getThresCnt() {
		return this.thresCnt;
	}

	public void setThreshold1(long threshold1) {
		this.threshold1 = threshold1;
	}

	public long getThreshold1() {
		return this.threshold1;
	}

	public void setThres1No(Character thres1No) {
		this.thres1No = thres1No;
	}

	public Character getThres1No() {
		return this.thres1No;
	}

	public void setThres1Name(String thres1Name) {
		this.thres1Name = thres1Name;
	}

	public String getThres1Name() {
		return this.thres1Name;
	}

	public void setThres1Color(String thres1Color) {
		this.thres1Color = thres1Color;
	}

	public String getThres1Color() {
		return this.thres1Color;
	}

	public void setThres1Oper(String thres1Oper) {
		this.thres1Oper = thres1Oper;
	}

	public String getThres1Oper() {
		return this.thres1Oper;
	}

	public void setThres1Valid(Character thres1Valid) {
		this.thres1Valid = thres1Valid;
	}

	public Character getThres1Valid() {
		return this.thres1Valid;
	}

	public void setThreshold2(long threshold2) {
		this.threshold2 = threshold2;
	}

	public long getThreshold2() {
		return this.threshold2;
	}

	public void setThres2No(Character thres2No) {
		this.thres2No = thres2No;
	}

	public Character getThres2No() {
		return this.thres2No;
	}

	public void setThres2Name(String thres2Name) {
		this.thres2Name = thres2Name;
	}

	public String getThres2Name() {
		return this.thres2Name;
	}

	public void setThres2Color(String thres2Color) {
		this.thres2Color = thres2Color;
	}

	public String getThres2Color() {
		return this.thres2Color;
	}

	public void setThres2Oper(String thres2Oper) {
		this.thres2Oper = thres2Oper;
	}

	public String getThres2Oper() {
		return this.thres2Oper;
	}

	public void setThres2Valid(Character thres2Valid) {
		this.thres2Valid = thres2Valid;
	}

	public Character getThres2Valid() {
		return this.thres2Valid;
	}

	public void setThreshold3(long threshold3) {
		this.threshold3 = threshold3;
	}

	public long getThreshold3() {
		return this.threshold3;
	}

	public void setThres3No(Character thres3No) {
		this.thres3No = thres3No;
	}

	public Character getThres3No() {
		return this.thres3No;
	}

	public void setThres3Name(String thres3Name) {
		this.thres3Name = thres3Name;
	}

	public String getThres3Name() {
		return this.thres3Name;
	}

	public void setThres3Color(String thres3Color) {
		this.thres3Color = thres3Color;
	}

	public String getThres3Color() {
		return this.thres3Color;
	}

	public void setThres3Oper(String thres3Oper) {
		this.thres3Oper = thres3Oper;
	}

	public String getThres3Oper() {
		return this.thres3Oper;
	}

	public void setThres3Valid(Character thres3Valid) {
		this.thres3Valid = thres3Valid;
	}

	public Character getThres3Valid() {
		return this.thres3Valid;
	}

	public void setThreshold4(long threshold4) {
		this.threshold4 = threshold4;
	}

	public long getThreshold4() {
		return this.threshold4;
	}

	public void setThres4No(Character thres4No) {
		this.thres4No = thres4No;
	}

	public Character getThres4No() {
		return this.thres4No;
	}

	public void setThres4Name(String thres4Name) {
		this.thres4Name = thres4Name;
	}

	public String getThres4Name() {
		return this.thres4Name;
	}

	public void setThres4Color(String thres4Color) {
		this.thres4Color = thres4Color;
	}

	public String getThres4Color() {
		return this.thres4Color;
	}

	public void setThres4Oper(String thres4Oper) {
		this.thres4Oper = thres4Oper;
	}

	public String getThres4Oper() {
		return this.thres4Oper;
	}

	public void setThres4Valid(Character thres4Valid) {
		this.thres4Valid = thres4Valid;
	}

	public Character getThres4Valid() {
		return this.thres4Valid;
	}

}