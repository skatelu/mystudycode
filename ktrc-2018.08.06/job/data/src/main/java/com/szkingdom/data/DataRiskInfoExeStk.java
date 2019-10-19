/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskInfoExeStk
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataRiskInfoExeStk extends DataBase {

	private static final long serialVersionUID = -2413855793977093707L;
	private String indicatorId;
	private String indicatorName;
	private int occDate;
	private int occTime;
	private long riskValue;
	private long defaultRiskValue1;
	private long defaultRiskValue2;
	private String optUndlCode;
	private String optUndlName;
	private Character optUndlCls;
	private long stkQtyAct;
	private long stkNetDlvyQty;
	private long stkQtyFar;
	private long cashSettAmt;
	private long stkQtyExelack1;
	private long stkQtyExelack2;
	private int dlvyDate;
	private short intOrg;
	private long custCode;
	private String custName;
	private Character custType;
	private Character custCls;
	private long cuacctCode;
	private Character currency;
	private String stkbd;
	private String trdacct;
	private String subacctCode;
	private Character trdacctExcls;
	private Character riskValidFlag;
	private Character noticeSentLevel;
	private String noticeSentLevelName;

    public DataRiskInfoExeStk(){
        init();
    }

	public DataRiskInfoExeStk(JSONObject json) {
	}

	public void init() {
		this.indicatorId = "";
		this.indicatorName = "";
		this.occDate = 0;
		this.occTime = 0;
		this.riskValue = 0L;
		this.defaultRiskValue1 = 0L;
		this.defaultRiskValue2 = 0L;
		this.optUndlCode = "";
		this.optUndlName = "";
		this.optUndlCls = null;
		this.stkQtyAct = 0L;
		this.stkNetDlvyQty = 0L;
		this.stkQtyFar = 0L;
		this.cashSettAmt = 0L;
		this.stkQtyExelack1 = 0L;
		this.stkQtyExelack2 = 0L;
		this.dlvyDate = 0;
		this.intOrg = 0;
		this.custCode = 0L;
		this.custName = "";
		this.custType = null;
		this.custCls = null;
		this.cuacctCode = 0L;
		this.currency = null;
		this.stkbd = "";
		this.trdacct = "";
		this.subacctCode = "";
		this.trdacctExcls = null;
		this.riskValidFlag = null;
		this.noticeSentLevel = null;
		this.noticeSentLevelName = "";

	}

	public void setIndicatorId(String indicatorId) {
		this.indicatorId = indicatorId;
	}

	public String getIndicatorId() {
		return this.indicatorId;
	}

	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public String getIndicatorName() {
		return this.indicatorName;
	}

	public void setOccDate(int occDate) {
		this.occDate = occDate;
	}

	public int getOccDate() {
		return this.occDate;
	}

	public void setOccTime(int occTime) {
		this.occTime = occTime;
	}

	public int getOccTime() {
		return this.occTime;
	}

	public void setRiskValue(long riskValue) {
		this.riskValue = riskValue;
	}

	public long getRiskValue() {
		return this.riskValue;
	}

	public void setDefaultRiskValue1(long defaultRiskValue1) {
		this.defaultRiskValue1 = defaultRiskValue1;
	}

	public long getDefaultRiskValue1() {
		return this.defaultRiskValue1;
	}

	public void setDefaultRiskValue2(long defaultRiskValue2) {
		this.defaultRiskValue2 = defaultRiskValue2;
	}

	public long getDefaultRiskValue2() {
		return this.defaultRiskValue2;
	}

	public void setOptUndlCode(String optUndlCode) {
		this.optUndlCode = optUndlCode;
	}

	public String getOptUndlCode() {
		return this.optUndlCode;
	}

	public void setOptUndlName(String optUndlName) {
		this.optUndlName = optUndlName;
	}

	public String getOptUndlName() {
		return this.optUndlName;
	}

	public void setOptUndlCls(Character optUndlCls) {
		this.optUndlCls = optUndlCls;
	}

	public Character getOptUndlCls() {
		return this.optUndlCls;
	}

	public void setStkQtyAct(long stkQtyAct) {
		this.stkQtyAct = stkQtyAct;
	}

	public long getStkQtyAct() {
		return this.stkQtyAct;
	}

	public void setStkNetDlvyQty(long stkNetDlvyQty) {
		this.stkNetDlvyQty = stkNetDlvyQty;
	}

	public long getStkNetDlvyQty() {
		return this.stkNetDlvyQty;
	}

	public void setStkQtyFar(long stkQtyFar) {
		this.stkQtyFar = stkQtyFar;
	}

	public long getStkQtyFar() {
		return this.stkQtyFar;
	}

	public void setCashSettAmt(long cashSettAmt) {
		this.cashSettAmt = cashSettAmt;
	}

	public long getCashSettAmt() {
		return this.cashSettAmt;
	}

	public void setStkQtyExelack1(long stkQtyExelack1) {
		this.stkQtyExelack1 = stkQtyExelack1;
	}

	public long getStkQtyExelack1() {
		return this.stkQtyExelack1;
	}

	public void setStkQtyExelack2(long stkQtyExelack2) {
		this.stkQtyExelack2 = stkQtyExelack2;
	}

	public long getStkQtyExelack2() {
		return this.stkQtyExelack2;
	}

	public void setDlvyDate(int dlvyDate) {
		this.dlvyDate = dlvyDate;
	}

	public int getDlvyDate() {
		return this.dlvyDate;
	}

	public void setIntOrg(short intOrg) {
		this.intOrg = intOrg;
	}

	public short getIntOrg() {
		return this.intOrg;
	}

	public void setCustCode(long custCode) {
		this.custCode = custCode;
	}

	public long getCustCode() {
		return this.custCode;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustType(Character custType) {
		this.custType = custType;
	}

	public Character getCustType() {
		return this.custType;
	}

	public void setCustCls(Character custCls) {
		this.custCls = custCls;
	}

	public Character getCustCls() {
		return this.custCls;
	}

	public void setCuacctCode(long cuacctCode) {
		this.cuacctCode = cuacctCode;
	}

	public long getCuacctCode() {
		return this.cuacctCode;
	}

	public void setCurrency(Character currency) {
		this.currency = currency;
	}

	public Character getCurrency() {
		return this.currency;
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

	public void setRiskValidFlag(Character riskValidFlag) {
		this.riskValidFlag = riskValidFlag;
	}

	public Character getRiskValidFlag() {
		return this.riskValidFlag;
	}

	public void setNoticeSentLevel(Character noticeSentLevel) {
		this.noticeSentLevel = noticeSentLevel;
	}

	public Character getNoticeSentLevel() {
		return this.noticeSentLevel;
	}

	public void setNoticeSentLevelName(String noticeSentLevelName) {
		this.noticeSentLevelName = noticeSentLevelName;
	}

	public String getNoticeSentLevelName() {
		return this.noticeSentLevelName;
	}

}