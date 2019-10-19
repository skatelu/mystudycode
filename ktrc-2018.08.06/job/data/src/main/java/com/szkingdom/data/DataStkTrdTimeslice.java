/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkTrdTimeslice
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStkTrdTimeslice extends DataBase {

	private static final long serialVersionUID = -4074303016454352920L;
	private Character timeslice;
	private String timesliceName;
	private int bgnTime;
	private int endTime;

	public DataStkTrdTimeslice() {
	}

	public DataStkTrdTimeslice(JSONObject json) {
	}

	public void init() {
		this.timeslice = null;
		this.timesliceName = "";
		this.bgnTime = 0;
		this.endTime = 0;

	}

	public void setTimeslice(Character timeslice) {
		this.timeslice = timeslice;
	}

	public Character getTimeslice() {
		return this.timeslice;
	}

	public void setTimesliceName(String timesliceName) {
		this.timesliceName = timesliceName;
	}

	public String getTimesliceName() {
		return this.timesliceName;
	}

	public void setBgnTime(int bgnTime) {
		this.bgnTime = bgnTime;
	}

	public int getBgnTime() {
		return this.bgnTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public int getEndTime() {
		return this.endTime;
	}

}