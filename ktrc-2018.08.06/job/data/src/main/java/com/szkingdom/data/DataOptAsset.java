/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptAsset
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;

public class DataOptAsset extends DataBase {

	private static final long serialVersionUID = 7525363426506223599L;
	private long custCode;
	private Character custType;
	private long cuacctCode;
	private short intOrg;
	private Character stkex;
	private String stkbd;
	private String stkpbu;
	private String trdacct;
	private String subacctCode;
	private Character trdacctExcls;
	private Character currency;
	private String optNum;
	private String optCode;
	private String optName;
	private Character optType;
	private Character optSide;
	private Character optCvdFlag;
	private Character optUndlCls;
	private String optUndlCode;
	private long optPrebln;
	private long optBln;
	private long optAvl;
	private long optFrz;
	private long optUfz;
	private long optTrdFrz;
	private long optTrdUfz;
	private long optTrdOtd;
	private long optTrdBln;
	private long optCvdAsset;
	private long optPosiRlt;
	private long optAutoExeQty;
	private long optClsUnmatched;
	private long optDailyOpenRlt;
	private long combedQty;
	private Date updTime;
	private String mac;

	public DataOptAsset() {
		init();
	}

	public DataOptAsset(JSONObject json) {
	}

	public void init() {
		this.custCode = 0L;
		this.custType = null;
		this.cuacctCode = 0L;
		this.intOrg = 0;
		this.stkex = null;
		this.stkbd = "";
		this.stkpbu = "";
		this.trdacct = "";
		this.subacctCode = "";
		this.trdacctExcls = null;
		this.currency = null;
		this.optNum = "";
		this.optCode = "";
		this.optName = "";
		this.optType = null;
		this.optSide = null;
		this.optCvdFlag = null;
		this.optUndlCls = null;
		this.optUndlCode = "";
		this.optPrebln = 0L;
		this.optBln = 0L;
		this.optAvl = 0L;
		this.optFrz = 0L;
		this.optUfz = 0L;
		this.optTrdFrz = 0L;
		this.optTrdUfz = 0L;
		this.optTrdOtd = 0L;
		this.optTrdBln = 0L;
		this.optCvdAsset = 0L;
		this.optPosiRlt = 0L;
		this.optAutoExeQty = 0L;
		this.optClsUnmatched = 0L;
		this.optDailyOpenRlt = 0L;
		this.combedQty = 0L;
		this.updTime = new Date();
		this.mac = "";
	}

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
	}

	public void setCustType(Character custType) {
		this.custType = custType;
	}

	public Character getCustType() {
		return this.custType;
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

	public void setStkpbu(String stkpbu) {
		this.stkpbu = stkpbu;
	}

	public String getStkpbu() {
		return this.stkpbu;
	}

	public void setTrdacct(String trdacct) {
		this.trdacct = trdacct;
	}

	public String getTrdacct() {
		return this.trdacct;
	}

	public void setSubacctCode(String subacctCode) {
		this.subacctCode = subacctCode;
	}

	public String getSubacctCode() {
		return this.subacctCode;
	}

	public void setTrdacctExcls(Character trdacctExcls) {
		this.trdacctExcls = trdacctExcls;
	}

	public Character getTrdacctExcls() {
		return this.trdacctExcls;
	}

	public void setCurrency(Character currency) {
		this.currency = currency;
	}

	public Character getCurrency() {
		return this.currency;
	}

	public void setOptNum(String optNum) {
		this.optNum = optNum;
	}

	public String getOptNum() {
		return this.optNum;
	}

	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}

	public String getOptCode() {
		return this.optCode;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String getOptName() {
		return this.optName;
	}

	public void setOptType(Character optType) {
		this.optType = optType;
	}

	public Character getOptType() {
		return this.optType;
	}

	public void setOptSide(Character optSide) {
		this.optSide = optSide;
	}

	public Character getOptSide() {
		return this.optSide;
	}

	public void setOptCvdFlag(Character optCvdFlag) {
		this.optCvdFlag = optCvdFlag;
	}

	public Character getOptCvdFlag() {
		return this.optCvdFlag;
	}

	public void setOptUndlCls(Character optUndlCls) {
		this.optUndlCls = optUndlCls;
	}

	public Character getOptUndlCls() {
		return this.optUndlCls;
	}

	public void setOptUndlCode(String optUndlCode) {
		this.optUndlCode = optUndlCode;
	}

	public String getOptUndlCode() {
		return this.optUndlCode;
	}

	public void setOptPrebln(long optPrebln) {
		this.optPrebln = optPrebln;
	}

	public long getOptPrebln() {
		return this.optPrebln;
	}

	public void setOptBln(long optBln) {
		this.optBln = optBln;
	}

	public long getOptBln() {
		return this.optBln;
	}

	public void setOptAvl(long optAvl) {
		this.optAvl = optAvl;
	}

	public long getOptAvl() {
		return this.optAvl;
	}

	public void setOptFrz(long optFrz) {
		this.optFrz = optFrz;
	}

	public long getOptFrz() {
		return this.optFrz;
	}

	public void setOptUfz(long optUfz) {
		this.optUfz = optUfz;
	}

	public long getOptUfz() {
		return this.optUfz;
	}

	public void setOptTrdFrz(long optTrdFrz) {
		this.optTrdFrz = optTrdFrz;
	}

	public long getOptTrdFrz() {
		return this.optTrdFrz;
	}

	public void setOptTrdUfz(long optTrdUfz) {
		this.optTrdUfz = optTrdUfz;
	}

	public long getOptTrdUfz() {
		return this.optTrdUfz;
	}

	public void setOptTrdOtd(long optTrdOtd) {
		this.optTrdOtd = optTrdOtd;
	}

	public long getOptTrdOtd() {
		return this.optTrdOtd;
	}

	public void setOptTrdBln(long optTrdBln) {
		this.optTrdBln = optTrdBln;
	}

	public long getOptTrdBln() {
		return this.optTrdBln;
	}

	public void setOptCvdAsset(long optCvdAsset) {
		this.optCvdAsset = optCvdAsset;
	}

	public long getOptCvdAsset() {
		return this.optCvdAsset;
	}

	public void setOptPosiRlt(long optPosiRlt) {
		this.optPosiRlt = optPosiRlt;
	}

	public long getOptPosiRlt() {
		return this.optPosiRlt;
	}

	public void setOptAutoExeQty(long optAutoExeQty) {
		this.optAutoExeQty = optAutoExeQty;
	}

	public long getOptAutoExeQty() {
		return this.optAutoExeQty;
	}

	public void setOptClsUnmatched(long optClsUnmatched) {
		this.optClsUnmatched = optClsUnmatched;
	}

	public long getOptClsUnmatched() {
		return this.optClsUnmatched;
	}

	public void setOptDailyOpenRlt(long optDailyOpenRlt) {
		this.optDailyOpenRlt = optDailyOpenRlt;
	}

	public long getOptDailyOpenRlt() {
		return this.optDailyOpenRlt;
	}

	public void setCombedQty(long combedQty) {
		this.combedQty = combedQty;
	}

	public long getCombedQty() {
		return this.combedQty;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public Date getUpdTime() {
		return this.updTime;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getMac() {
		return this.mac;
	}

	@Override
	public String toString() {
		return "DataOptAsset{" +
				"custCode=" + custCode +
				", custType=" + custType +
				", cuacctCode=" + cuacctCode +
				", intOrg=" + intOrg +
				", stkex=" + stkex +
				", stkbd='" + stkbd + '\'' +
				", stkpbu='" + stkpbu + '\'' +
				", trdacct='" + trdacct + '\'' +
				", subacctCode='" + subacctCode + '\'' +
				", trdacctExcls=" + trdacctExcls +
				", currency=" + currency +
				", optNum='" + optNum + '\'' +
				", optCode='" + optCode + '\'' +
				", optName='" + optName + '\'' +
				", optType=" + optType +
				", optSide=" + optSide +
				", optCvdFlag=" + optCvdFlag +
				", optUndlCls=" + optUndlCls +
				", optUndlCode='" + optUndlCode + '\'' +
				", optPrebln=" + optPrebln +
				", optBln=" + optBln +
				", optAvl=" + optAvl +
				", optFrz=" + optFrz +
				", optUfz=" + optUfz +
				", optTrdFrz=" + optTrdFrz +
				", optTrdUfz=" + optTrdUfz +
				", optTrdOtd=" + optTrdOtd +
				", optTrdBln=" + optTrdBln +
				", optCvdAsset=" + optCvdAsset +
				", optPosiRlt=" + optPosiRlt +
				", optAutoExeQty=" + optAutoExeQty +
				", optClsUnmatched=" + optClsUnmatched +
				", optDailyOpenRlt=" + optDailyOpenRlt +
				", combedQty=" + combedQty +
				", updTime=" + updTime +
				", mac='" + mac + '\'' +
				'}';
	}
}