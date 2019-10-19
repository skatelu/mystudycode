/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkPlgStkAsset
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataStkPlgStkAsset extends DataBase {

	private static final long serialVersionUID = 8048077462353366495L;
	private int trdDate;
	private String stkbd;
	private long custCodeIn;
	private String trdacctIn;
	private String stkpbu;
	private String stkCode;
	private String constrSn;
	private String stkNature;
	private long stkBln;
	private long stkAvl;
	private long stkTrdFrz;
	private int orgTrdDate;
	private String orgConstrSn;
	private long bonusAmt;
	private long intQty;
	private Character plgStatus;
	private Character flowType;
	private String rightsCls;
	private String listingYear;
	private int merTrdDate;
	private String merConstrSn;

	public DataStkPlgStkAsset() {
	}

	public DataStkPlgStkAsset(JSONObject json) {
	}

	public void init() {
		this.trdDate = 0;
		this.stkbd = "";
		this.custCodeIn = 0L;
		this.trdacctIn = "";
		this.stkpbu = "";
		this.stkCode = "";
		this.constrSn = "";
		this.stkNature = "";
		this.stkBln = 0L;
		this.stkAvl = 0L;
		this.stkTrdFrz = 0L;
		this.orgTrdDate = 0;
		this.orgConstrSn = "";
		this.bonusAmt = 0L;
		this.intQty = 0L;
		this.plgStatus = null;
		this.flowType = null;
		this.rightsCls = "";
		this.listingYear = "";
		this.merTrdDate = 0;
		this.merConstrSn = "";

	}

	public void setTrdDate(int trdDate) {
		this.trdDate = trdDate;
	}

	public int getTrdDate() {
		return this.trdDate;
	}

	public void setStkbd(String stkbd) {
		this.stkbd = stkbd;
	}

	public String getStkbd() {
		return this.stkbd;
	}

	public void setCustCodeIn(long custCodeIn) {
		this.custCodeIn = custCodeIn;
	}

	public long getCustCodeIn() {
		return this.custCodeIn;
	}

	public void setTrdacctIn(String trdacctIn) {
		this.trdacctIn = trdacctIn;
	}

	public String getTrdacctIn() {
		return this.trdacctIn;
	}

	public void setStkpbu(String stkpbu) {
		this.stkpbu = stkpbu;
	}

	public String getStkpbu() {
		return this.stkpbu;
	}

	public void setStkCode(String stkCode) {
		this.stkCode = stkCode;
	}

	public String getStkCode() {
		return this.stkCode;
	}

	public void setConstrSn(String constrSn) {
		this.constrSn = constrSn;
	}

	public String getConstrSn() {
		return this.constrSn;
	}

	public void setStkNature(String stkNature) {
		this.stkNature = stkNature;
	}

	public String getStkNature() {
		return this.stkNature;
	}

	public void setStkBln(long stkBln) {
		this.stkBln = stkBln;
	}

	public long getStkBln() {
		return this.stkBln;
	}

	public void setStkAvl(long stkAvl) {
		this.stkAvl = stkAvl;
	}

	public long getStkAvl() {
		return this.stkAvl;
	}

	public void setStkTrdFrz(long stkTrdFrz) {
		this.stkTrdFrz = stkTrdFrz;
	}

	public long getStkTrdFrz() {
		return this.stkTrdFrz;
	}

	public void setOrgTrdDate(int orgTrdDate) {
		this.orgTrdDate = orgTrdDate;
	}

	public int getOrgTrdDate() {
		return this.orgTrdDate;
	}

	public void setOrgConstrSn(String orgConstrSn) {
		this.orgConstrSn = orgConstrSn;
	}

	public String getOrgConstrSn() {
		return this.orgConstrSn;
	}

	public void setBonusAmt(long bonusAmt) {
		this.bonusAmt = bonusAmt;
	}

	public long getBonusAmt() {
		return this.bonusAmt;
	}

	public void setIntQty(long intQty) {
		this.intQty = intQty;
	}

	public long getIntQty() {
		return this.intQty;
	}

	public void setPlgStatus(Character plgStatus) {
		this.plgStatus = plgStatus;
	}

	public Character getPlgStatus() {
		return this.plgStatus;
	}

	public void setFlowType(Character flowType) {
		this.flowType = flowType;
	}

	public Character getFlowType() {
		return this.flowType;
	}

	public void setRightsCls(String rightsCls) {
		this.rightsCls = rightsCls;
	}

	public String getRightsCls() {
		return this.rightsCls;
	}

	public void setListingYear(String listingYear) {
		this.listingYear = listingYear;
	}

	public String getListingYear() {
		return this.listingYear;
	}

	public void setMerTrdDate(int merTrdDate) {
		this.merTrdDate = merTrdDate;
	}

	public int getMerTrdDate() {
		return this.merTrdDate;
	}

	public void setMerConstrSn(String merConstrSn) {
		this.merConstrSn = merConstrSn;
	}

	public String getMerConstrSn() {
		return this.merConstrSn;
	}

}