/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkTrdacct
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStkTrdacct extends DataBase {

	private static final long serialVersionUID = -7048078849217588834L;
	private long custCode;
	private long cuacctCode;
	private short intOrg;
	private Character stkex;
	private String stkbd;
	private String trdacct;
	private short trdacctSn;
	private String trdacctExid;
	private Character trdacctType;
	private Character trdacctExcls;
	private String trdacctName;
	private Character trdacctStatus;
	private Character tregStatus;
	private Character bregStatus;
	private String stkpbu;
	private String firmid;
	private String idType;
	private String idCode;
	private String idIssAgcy;
	private int idExpDate;
	private int openDate;

	public DataStkTrdacct() {
	}

	public DataStkTrdacct(JSONObject json) {
	}

	public void init() {
		this.custCode = 0L;
		this.cuacctCode = 0L;
		this.intOrg = 0;
		this.stkex = null;
		this.stkbd = "";
		this.trdacct = "";
		this.trdacctSn = 0;
		this.trdacctExid = "";
		this.trdacctType = null;
		this.trdacctExcls = null;
		this.trdacctName = "";
		this.trdacctStatus = null;
		this.tregStatus = null;
		this.bregStatus = null;
		this.stkpbu = "";
		this.firmid = "";
		this.idType = "";
		this.idCode = "";
		this.idIssAgcy = "";
		this.idExpDate = 0;
		this.openDate = 0;

	}

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
	}

	public void setCuacctCode(long cuacctCode) {
		this.cuacctCode = cuacctCode;
	}

	public long getCuacctCode() {
		return this.cuacctCode;
	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public short getIntOrg() {
		return this.intOrg;
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

	public void setTrdacct(String trdacct) {
		this.trdacct = trdacct;
	}

	public String getTrdacct() {
		return this.trdacct;
	}

	public void setTrdacctSn(short trdacctSn) {
		this.trdacctSn = trdacctSn;
	}

	public short getTrdacctSn() {
		return this.trdacctSn;
	}

	public void setTrdacctExid(String trdacctExid) {
		this.trdacctExid = trdacctExid;
	}

	public String getTrdacctExid() {
		return this.trdacctExid;
	}

	public void setTrdacctType(Character trdacctType) {
		this.trdacctType = trdacctType;
	}

	public Character getTrdacctType() {
		return this.trdacctType;
	}

	public void setTrdacctExcls(Character trdacctExcls) {
		this.trdacctExcls = trdacctExcls;
	}

	public Character getTrdacctExcls() {
		return this.trdacctExcls;
	}

	public void setTrdacctName(String trdacctName) {
		this.trdacctName = trdacctName;
	}

	public String getTrdacctName() {
		return this.trdacctName;
	}

	public void setTrdacctStatus(Character trdacctStatus) {
		this.trdacctStatus = trdacctStatus;
	}

	public Character getTrdacctStatus() {
		return this.trdacctStatus;
	}

	public void setTregStatus(Character tregStatus) {
		this.tregStatus = tregStatus;
	}

	public Character getTregStatus() {
		return this.tregStatus;
	}

	public void setBregStatus(Character bregStatus) {
		this.bregStatus = bregStatus;
	}

	public Character getBregStatus() {
		return this.bregStatus;
	}

	public void setStkpbu(String stkpbu) {
		this.stkpbu = stkpbu;
	}

	public String getStkpbu() {
		return this.stkpbu;
	}

	public void setFirmid(String firmid) {
		this.firmid = firmid;
	}

	public String getFirmid() {
		return this.firmid;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdType() {
		return this.idType;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getIdCode() {
		return this.idCode;
	}

	public void setIdIssAgcy(String idIssAgcy) {
		this.idIssAgcy = idIssAgcy;
	}

	public String getIdIssAgcy() {
		return this.idIssAgcy;
	}

	public void setIdExpDate(int idExpDate) {
		this.idExpDate = idExpDate;
	}

	public int getIdExpDate() {
		return this.idExpDate;
	}

	public void setOpenDate(int openDate) {
		this.openDate = openDate;
	}

	public int getOpenDate() {
		return this.openDate;
	}

}