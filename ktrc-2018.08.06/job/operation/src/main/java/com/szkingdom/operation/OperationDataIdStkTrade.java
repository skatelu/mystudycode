/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationDataIdStkTrade
 * Author:   ZhangMaohua
 * Date:     2018/9/7
 * Description: 对接集中交易申报接口类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import java.util.Date;

public class OperationDataIdStkTrade extends OperationBase {

    private String dataId;
    private int trdDate;
    private int orderDate;
    private Date orderTime;
    private long custCode;
    private long cuacctCode;
    private Character currency;
    private String stkbd;
    private String trdacct;
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
    private String stkCode;
    private String stkName;
    private Character stkCls;
    private long fundBlnVary;
    private long fundAvlVary;
    private String stdBondCode;
    private long srdBondQty;

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

    public String getStkCode() {
        return stkCode;
    }

    public void setStkCode(String stkCode) {
        this.stkCode = stkCode;
    }

    public String getStkName() {
        return stkName;
    }

    public void setStkName(String stkName) {
        this.stkName = stkName;
    }

    public Character getStkCls() {
        return stkCls;
    }

    public void setStkCls(Character stkCls) {
        this.stkCls = stkCls;
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

    public String getStdBondCode() {
        return stdBondCode;
    }

    public void setStdBondCode(String stdBondCode) {
        this.stdBondCode = stdBondCode;
    }

    public long getSrdBondQty() {
        return srdBondQty;
    }

    public void setSrdBondQty(long srdBondQty) {
        this.srdBondQty = srdBondQty;
    }
}
