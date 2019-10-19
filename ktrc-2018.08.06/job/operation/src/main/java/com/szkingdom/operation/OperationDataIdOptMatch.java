/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationDataIdOptMatch
 * Author:   ZhengMingjie
 * Date:     2018/8/27
 * Description: 平台接口成交回报OPT_MATCH数据类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;

public class OperationDataIdOptMatch extends OperationBase {

    private String dataId;
    private int trdDate;
    private String matchedTime;
    private long custCode;
    private String custName;
    private Character optCustType;
    private long cuacctCode;
    private Character currency;
    private String stkbd;
    private String trdacct;
    private String subacctCode;
    private String stkpbu;
    private short intOrg;
    private String matchedSn;
    private String orderId;
    private long matchedQty;
    private long matchedPrice;
    private Character isWithdraw;
    private short stkBiz;
    private short stkBizAction;
    private Character matchedType;
    private String optNum;
    private String optCode;
    private String optName;
    private Character optType;
    private long optUnit;
    private String optUndlCode;
    private String optUndlName;
    private Character optUndlCls;
    private String combNum;
    private String combStraCode;
    private String leg1Num;
    private String leg2Num;
    private long fundBlnVary;
    private long fundAvlVary;
    private long marginUsedVary;
    private long marginInclRltVary;

    public OperationDataIdOptMatch() {
        init();
    }

    public OperationDataIdOptMatch(JSONObject json) {
    }

    private void init(){
        this.dataId = "";
        this.trdDate = 0;
        this.matchedTime = "";
        this.custCode = 0L;
        this.custName = "";
        this.optCustType = null;
        this.cuacctCode = 0L;
        this.currency = null;
        this.stkbd = "";
        this.trdacct = "";
        this.subacctCode = "";
        this.stkpbu = "";
        this.intOrg = 0;
        this.matchedSn = "";
        this.orderId = "";
        this.matchedQty = 0L;
        this.matchedPrice = 0L;
        this.isWithdraw = null;
        this.stkBiz = 0;
        this.stkBizAction = 0;
        this.matchedType = null;
        this.optNum = "";
        this.optCode = "";
        this.optName = "";
        this.optType = null;
        this.optUnit = 0L;
        this.optUndlCode = "";
        this.optUndlName = "";
        this.optUndlCls = null;
        this.combNum = "";
        this.combStraCode = "";
        this.leg1Num = "";
        this.leg2Num = "";
        this.fundBlnVary = 0L;
        this.fundAvlVary = 0L;
        this.marginUsedVary = 0L;
        this.marginInclRltVary = 0L;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public int getTrdDate() {
        return trdDate;
    }

    public void setTrdDate(int trdDate) {
        this.trdDate = trdDate;
    }

    public String getMatchedTime() {
        return matchedTime;
    }

    public void setMatchedTime(String matchedTime) {
        this.matchedTime = matchedTime;
    }

    public long getCustCode() {
        return custCode;
    }

    public void setCustCode(long custCode) {
        this.custCode = custCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Character getOptCustType() {
        return optCustType;
    }

    public void setOptCustType(Character optCustType) {
        this.optCustType = optCustType;
    }

    public long getCuacctCode() {
        return cuacctCode;
    }

    public void setCuacctCode(long cuacctCode) {
        this.cuacctCode = cuacctCode;
    }

    public Character getCurrency() {
        return currency;
    }

    public void setCurrency(Character currency) {
        this.currency = currency;
    }

    public String getStkbd() {
        return stkbd;
    }

    public void setStkbd(String stkbd) {
        this.stkbd = stkbd;
    }

    public String getTrdacct() {
        return trdacct;
    }

    public void setTrdacct(String trdacct) {
        this.trdacct = trdacct;
    }

    public String getSubacctCode() {
        return subacctCode;
    }

    public void setSubacctCode(String subacctCode) {
        this.subacctCode = subacctCode;
    }

    public String getStkpbu() {
        return stkpbu;
    }

    public void setStkpbu(String stkpbu) {
        this.stkpbu = stkpbu;
    }

    public short getIntOrg() {
        return intOrg;
    }

    public void setIntOrg(short intOrg) {
        this.intOrg = intOrg;
    }

    public String getMatchedSn() {
        return matchedSn;
    }

    public void setMatchedSn(String matchedSn) {
        this.matchedSn = matchedSn;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getMatchedQty() {
        return matchedQty;
    }

    public void setMatchedQty(long matchedQty) {
        this.matchedQty = matchedQty;
    }

    public long getMatchedPrice() {
        return matchedPrice;
    }

    public void setMatchedPrice(long matchedPrice) {
        this.matchedPrice = matchedPrice;
    }

    public Character getIsWithdraw() {
        return isWithdraw;
    }

    public void setIsWithdraw(Character isWithdraw) {
        this.isWithdraw = isWithdraw;
    }

    public short getStkBiz() {
        return stkBiz;
    }

    public void setStkBiz(short stkBiz) {
        this.stkBiz = stkBiz;
    }

    public short getStkBizAction() {
        return stkBizAction;
    }

    public void setStkBizAction(short stkBizAction) {
        this.stkBizAction = stkBizAction;
    }

    public Character getMatchedType() {
        return matchedType;
    }

    public void setMatchedType(Character matchedType) {
        this.matchedType = matchedType;
    }

    public String getOptNum() {
        return optNum;
    }

    public void setOptNum(String optNum) {
        this.optNum = optNum;
    }

    public String getOptCode() {
        return optCode;
    }

    public void setOptCode(String optCode) {
        this.optCode = optCode;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    public Character getOptType() {
        return optType;
    }

    public void setOptType(Character optType) {
        this.optType = optType;
    }

    public long getOptUnit() {
        return optUnit;
    }

    public void setOptUnit(long optUnit) {
        this.optUnit = optUnit;
    }

    public String getOptUndlCode() {
        return optUndlCode;
    }

    public void setOptUndlCode(String optUndlCode) {
        this.optUndlCode = optUndlCode;
    }

    public String getOptUndlName() {
        return optUndlName;
    }

    public void setOptUndlName(String optUndlName) {
        this.optUndlName = optUndlName;
    }

    public Character getOptUndlCls() {
        return optUndlCls;
    }

    public void setOptUndlCls(Character optUndlCls) {
        this.optUndlCls = optUndlCls;
    }

    public String getCombNum() {
        return combNum;
    }

    public void setCombNum(String combNum) {
        this.combNum = combNum;
    }

    public String getCombStraCode() {
        return combStraCode;
    }

    public void setCombStraCode(String combStraCode) {
        this.combStraCode = combStraCode;
    }

    public String getLeg1Num() {
        return leg1Num;
    }

    public void setLeg1Num(String leg1Num) {
        this.leg1Num = leg1Num;
    }

    public String getLeg2Num() {
        return leg2Num;
    }

    public void setLeg2Num(String leg2Num) {
        this.leg2Num = leg2Num;
    }

    public long getFundBlnVary() {
        return fundBlnVary;
    }

    public void setFundBlnVary(long fundBlnVary) {
        this.fundBlnVary = fundBlnVary;
    }

    public long getFundAvlVary() {
        return fundAvlVary;
    }

    public void setFundAvlVary(long fundAvlVary) {
        this.fundAvlVary = fundAvlVary;
    }

    public long getMarginUsedVary() {
        return marginUsedVary;
    }

    public void setMarginUsedVary(long marginUsedVary) {
        this.marginUsedVary = marginUsedVary;
    }

    public long getMarginInclRltVary() {
        return marginInclRltVary;
    }

    public void setMarginInclRltVary(long marginInclRltVary) {
        this.marginInclRltVary = marginInclRltVary;
    }
}
