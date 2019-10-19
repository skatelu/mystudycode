/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationDataIdOptTrade
 * Author:   ZhangChangHong
 * Date:     2018/8/27
 * Description: 对接期权交易申报接口类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import java.util.Date;
import com.alibaba.fastjson.JSONObject;

public class OperationDataIdOptTrade extends OperationBase {

    private String dataId;
    private int trdDate;
    private int orderDate;
    private Date orderTime;
    private long custCode;
    private String custName;
    private Character optCustType;
    private Character custType;
    private long cuacctCode;
    private Character currency;
    private String stkbd;
    private String trdacct;
    private String subacctCode;
    private Character trdacctExcls;
    private Character optTrdacctLvl;
    private String stkpbu;
    private short intOrg;
    private Character isWithdraw;
    private short stkBiz;
    private short stkBizAction;
    private String orderId;
    private String rawOrderId;
    private Character orderStatus;
    private long orderPrice;
    private long orderQty;
    private long orderFrzAmt;
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

    public OperationDataIdOptTrade() {
        init();
    }

    public OperationDataIdOptTrade(JSONObject json) {
    }

    public void init() {
        dataId = "";
        trdDate = 0;
        orderDate = 0;
        orderTime = new Date();
        custCode = 0L;
        custName = "";
        optCustType = null;
        custType = null;
        cuacctCode = 0L;
        currency = null;
        stkbd = "";
        trdacct = "";
        subacctCode = "";
        trdacctExcls = null;
        optTrdacctLvl = null;
        stkpbu = "";
        intOrg = 0;
        isWithdraw = null;
        stkBiz = 0;
        stkBizAction = 0;
        orderId = "";
        rawOrderId = "";
        orderStatus = null;
        orderPrice = 0L;
        orderQty = 0L;
        orderFrzAmt = 0L;
        optNum = "";
        optCode = "";
        optName = "";
        optType = null;
        optUnit = 0L;
        optUndlCode = "";
        optUndlName = "";
        optUndlCls = null;
        combNum = "";
        combStraCode = "";
        leg1Num = "";
        leg2Num = "";
        fundBlnVary = 0L;
        fundAvlVary = 0L;
        marginUsedVary = 0L;
        marginInclRltVary = 0L;
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

    public int getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(int orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
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

    public Character getCustType() {
        return custType;
    }

    public void setCustType(Character custType) {
        this.custType = custType;
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

    public Character getTrdacctExcls() {
        return trdacctExcls;
    }

    public void setTrdacctExcls(Character trdacctExcls) {
        this.trdacctExcls = trdacctExcls;
    }

    public Character getOptTrdacctLvl() {
        return optTrdacctLvl;
    }

    public void setOptTrdacctLvl(Character optTrdacctLvl) {
        this.optTrdacctLvl = optTrdacctLvl;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRawOrderId() {
        return rawOrderId;
    }

    public void setRawOrderId(String rawOrderId) {
        this.rawOrderId = rawOrderId;
    }

    public Character getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Character orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public long getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(long orderQty) {
        this.orderQty = orderQty;
    }

    public long getOrderFrzAmt() {
        return orderFrzAmt;
    }

    public void setOrderFrzAmt(long orderFrzAmt) {
        this.orderFrzAmt = orderFrzAmt;
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

    @Override
    public String toString() {
        return "OperationDataIdOptTrade{" +
                "dataId='" + dataId + '\'' +
                ", trdDate=" + trdDate +
                ", orderDate=" + orderDate +
                ", orderTime=" + orderTime +
                ", custCode=" + custCode +
                ", custName='" + custName + '\'' +
                ", optCustType=" + optCustType +
                ", custType=" + custType +
                ", cuacctCode=" + cuacctCode +
                ", currency=" + currency +
                ", stkbd='" + stkbd + '\'' +
                ", trdacct='" + trdacct + '\'' +
                ", subacctCode='" + subacctCode + '\'' +
                ", trdacctExcls=" + trdacctExcls +
                ", optTrdacctLvl=" + optTrdacctLvl +
                ", stkpbu='" + stkpbu + '\'' +
                ", intOrg=" + intOrg +
                ", isWithdraw=" + isWithdraw +
                ", stkBiz=" + stkBiz +
                ", stkBizAction=" + stkBizAction +
                ", orderId='" + orderId + '\'' +
                ", rawOrderId='" + rawOrderId + '\'' +
                ", orderStatus=" + orderStatus +
                ", orderPrice=" + orderPrice +
                ", orderQty=" + orderQty +
                ", orderFrzAmt=" + orderFrzAmt +
                ", optNum='" + optNum + '\'' +
                ", optCode='" + optCode + '\'' +
                ", optName='" + optName + '\'' +
                ", optType=" + optType +
                ", optUnit=" + optUnit +
                ", optUndlCode='" + optUndlCode + '\'' +
                ", optUndlName='" + optUndlName + '\'' +
                ", optUndlCls=" + optUndlCls +
                ", combNum='" + combNum + '\'' +
                ", combStraCode='" + combStraCode + '\'' +
                ", leg1Num='" + leg1Num + '\'' +
                ", leg2Num='" + leg2Num + '\'' +
                ", fundBlnVary=" + fundBlnVary +
                ", fundAvlVary=" + fundAvlVary +
                ", marginUsedVary=" + marginUsedVary +
                ", marginInclRltVary=" + marginInclRltVary +
                '}';
    }
}
