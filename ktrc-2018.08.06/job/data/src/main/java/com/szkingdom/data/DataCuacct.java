/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataCuacct
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataCuacct extends DataBase {

	private static final long serialVersionUID = 1468229145994910591L;
	private long userCode;
	private long cuacctCode;
	private Character cuacctAttr;
	private Character cuacctCls;
	private Character cuacctLvl;
	private Character cuacctGrp;
	private Character cuacctStatus;
	private Character mainFlag;
	private short corpOrg;
	private short intOrg;
	private int openDate;
	private int printDate;
	private int closeDate;

	public DataCuacct() {
	}

	public DataCuacct(JSONObject json) {
	}

	public void init() {
		this.userCode = 0L;
		this.cuacctCode = 0L;
		this.cuacctAttr = null;
		this.cuacctCls = null;
		this.cuacctLvl = null;
		this.cuacctGrp = null;
		this.cuacctStatus = null;
		this.mainFlag = null;
		this.corpOrg = 0;
		this.intOrg = 0;
		this.openDate = 0;
		this.printDate = 0;
		this.closeDate = 0;

	}

	public void setUserCode(long userCode) {
		this.userCode = userCode;
	}

	public long getUserCode() {
		return this.userCode;
	}

	public void setCuacctCode(long cuacctCode) {
		this.cuacctCode = cuacctCode;
	}

	public long getCuacctCode() {
		return this.cuacctCode;
	}

	public void setCuacctAttr(Character cuacctAttr) {
		this.cuacctAttr = cuacctAttr;
	}

	public Character getCuacctAttr() {
		return this.cuacctAttr;
	}

	public void setCuacctCls(Character cuacctCls) {
		this.cuacctCls = cuacctCls;
	}

	public Character getCuacctCls() {
		return this.cuacctCls;
	}

	public void setCuacctLvl(Character cuacctLvl) {
		this.cuacctLvl = cuacctLvl;
	}

	public Character getCuacctLvl() {
		return this.cuacctLvl;
	}

	public void setCuacctGrp(Character cuacctGrp) {
		this.cuacctGrp = cuacctGrp;
	}

	public Character getCuacctGrp() {
		return this.cuacctGrp;
	}

	public void setCuacctStatus(Character cuacctStatus) {
		this.cuacctStatus = cuacctStatus;
	}

	public Character getCuacctStatus() {
		return this.cuacctStatus;
	}

	public void setMainFlag(Character mainFlag) {
		this.mainFlag = mainFlag;
	}

	public Character getMainFlag() {
		return this.mainFlag;
	}

	public void setCorpOrg(short corpOrg) {
		this.corpOrg = corpOrg;
	}

	public short getCorpOrg() {
		return this.corpOrg;
	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public short getIntOrg() {
		return this.intOrg;
	}

	public void setOpenDate(int openDate) {
		this.openDate = openDate;
	}

	public int getOpenDate() {
		return this.openDate;
	}

	public void setPrintDate(int printDate) {
		this.printDate = printDate;
	}

	public int getPrintDate() {
		return this.printDate;
	}

	public void setCloseDate(int closeDate) {
		this.closeDate = closeDate;
	}

	public int getCloseDate() {
		return this.closeDate;
	}

}