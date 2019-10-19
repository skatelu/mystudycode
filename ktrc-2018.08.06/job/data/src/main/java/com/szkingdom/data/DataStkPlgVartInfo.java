/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkPlgVartInfo
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStkPlgVartInfo extends DataBase {

	private static final long serialVersionUID = 7785862160493756945L;
	private String stkbd;
	private String stkPlgVartCode;
	private String stkPlgVartName;
	private int stkPlgVartDays;
	private long buybkRate;
	private Character trdFlag;
	private int calIntDays;
	private int fstDays;
	private long fstRate;
	private int secdDays;
	private long secdRate;
	private int thrdDays;
	private long thrdRate;

	public DataStkPlgVartInfo() {
	}

	public DataStkPlgVartInfo(JSONObject json) {
	}

	public void init() {
		this.stkbd = "";
		this.stkPlgVartCode = "";
		this.stkPlgVartName = "";
		this.stkPlgVartDays = 0;
		this.buybkRate = 0L;
		this.trdFlag = null;
		this.calIntDays = 0;
		this.fstDays = 0;
		this.fstRate = 0L;
		this.secdDays = 0;
		this.secdRate = 0L;
		this.thrdDays = 0;
		this.thrdRate = 0L;

	}

	public void setStkbd(String stkbd) {
		this.stkbd = stkbd;
	}

	public String getStkbd() {
		return this.stkbd;
	}

	public void setStkPlgVartCode(String stkPlgVartCode) {
		this.stkPlgVartCode = stkPlgVartCode;
	}

	public String getStkPlgVartCode() {
		return this.stkPlgVartCode;
	}

	public void setStkPlgVartName(String stkPlgVartName) {
		this.stkPlgVartName = stkPlgVartName;
	}

	public String getStkPlgVartName() {
		return this.stkPlgVartName;
	}

	public void setStkPlgVartDays(int stkPlgVartDays) {
		this.stkPlgVartDays = stkPlgVartDays;
	}

	public int getStkPlgVartDays() {
		return this.stkPlgVartDays;
	}

	public void setBuybkRate(long buybkRate) {
		this.buybkRate = buybkRate;
	}

	public long getBuybkRate() {
		return this.buybkRate;
	}

	public void setTrdFlag(Character trdFlag) {
		this.trdFlag = trdFlag;
	}

	public Character getTrdFlag() {
		return this.trdFlag;
	}

	public void setCalIntDays(int calIntDays) {
		this.calIntDays = calIntDays;
	}

	public int getCalIntDays() {
		return this.calIntDays;
	}

	public void setFstDays(int fstDays) {
		this.fstDays = fstDays;
	}

	public int getFstDays() {
		return this.fstDays;
	}

	public void setFstRate(long fstRate) {
		this.fstRate = fstRate;
	}

	public long getFstRate() {
		return this.fstRate;
	}

	public void setSecdDays(int secdDays) {
		this.secdDays = secdDays;
	}

	public int getSecdDays() {
		return this.secdDays;
	}

	public void setSecdRate(long secdRate) {
		this.secdRate = secdRate;
	}

	public long getSecdRate() {
		return this.secdRate;
	}

	public void setThrdDays(int thrdDays) {
		this.thrdDays = thrdDays;
	}

	public int getThrdDays() {
		return this.thrdDays;
	}

	public void setThrdRate(long thrdRate) {
		this.thrdRate = thrdRate;
	}

	public long getThrdRate() {
		return this.thrdRate;
	}

}