/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationDataIdStkMatch
 * Author:   ZhangMaohua
 * Date:     2018/9/7
 * Description: 对接集中交易成交接口类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import java.util.Date;

public class OperationDataIdStkMatch extends OperationBase {

    private String dataId;
    private int trdDate;
    private String matchedTime;
    private long custCode;
    private String custName;
    private Character currency;
    private long cuacctCode;
    private String stkbd;
    private String trdacct;
    private String stkpbu;
    private short intOrg;
    private String matchedSn;
    private String orderId;
    private long matchedQty;
    private long matchedPrice;
    private long matchAmt;
    private Character isWithdraw;
    private short stkBiz;
    private short stkBizAction;
    private Character matchedType;
    private String stkCode;
    private String stkName;
    private Character stkCls;
    private String stkUndlCode;
    private long fundBlnVary;
    private long fundAvlVary;
    private String stdBondCode;
    private long srdBondQty;
    private int repchDate;
    private long repchAmt;

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

    public Character getCurrency() {
        return currency;
    }

    public void setCurrency(Character currency) {
        this.currency = currency;
    }

    public long getCuacctCode() {
        return cuacctCode;
    }

    public void setCuacctCode(long cuacctCode) {
        this.cuacctCode = cuacctCode;
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

    public long getMatchAmt() {
        return matchAmt;
    }

    public void setMatchAmt(long matchAmt) {
        this.matchAmt = matchAmt;
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

    public String getStkUndlCode() {
        return stkUndlCode;
    }

    public void setStkUndlCode(String stkUndlCode) {
        this.stkUndlCode = stkUndlCode;
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

    public int getRepchDate() {
        return repchDate;
    }

    public void setRepchDate(int repchDate) {
        this.repchDate = repchDate;
    }

    public long getRepchAmt() {
        return repchAmt;
    }

    public void setRepchAmt(long repchAmt) {
        this.repchAmt = repchAmt;
    }
}
