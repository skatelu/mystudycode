/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkCalendar
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStkCalendar extends DataBase {

	private static final long serialVersionUID = -9057379513067541542L;
	private Character stkex;
	private String stkbd;
	private int physicalDate;
	private Character trdDateFlag;
	private Character settDateFlag;
	// 扩展字段
    // 天数查询diffDays，用于“基于当前日期获取前（后）第几个交易日”
    private int diffDays;

	public DataStkCalendar() {
	}

	public DataStkCalendar(JSONObject json) {
	}

	public void init() {
		this.stkex = null;
		this.stkbd = "";
		this.physicalDate = 0;
		this.trdDateFlag = null;
		this.settDateFlag = null;

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

	public void setPhysicalDate(int physicalDate) {
		this.physicalDate = physicalDate;
	}

	public int getPhysicalDate() {
		return this.physicalDate;
	}

	public void setTrdDateFlag(Character trdDateFlag) {
		this.trdDateFlag = trdDateFlag;
	}

	public Character getTrdDateFlag() {
		return this.trdDateFlag;
	}

	public void setSettDateFlag(Character settDateFlag) {
		this.settDateFlag = settDateFlag;
	}

	public Character getSettDateFlag() {
		return this.settDateFlag;
	}
	
    public int getDiffDays() {
        return diffDays;
    }

    public void setDiffDays(int diffDays) {
        this.diffDays = diffDays;
    }

}