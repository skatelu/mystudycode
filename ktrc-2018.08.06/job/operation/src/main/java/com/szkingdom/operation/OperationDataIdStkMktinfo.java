/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationDataIdStkMktinfo
 * Author:   ZhengMingjie
 * Date:     2018/8/27
 * Description: 平台接口证券行情数据类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;

public class OperationDataIdStkMktinfo extends OperationBase {

    private Character stkex;
    private String stkbd;
    private String stkCode;
    private int trdDate;
    private long closingPrice;
    private long openingPrice;
    private long currentPrice;
    private long bondInt;
    private int bondIntDate;
    private long etfIopv;

    public OperationDataIdStkMktinfo() {
        init();
    }

    public OperationDataIdStkMktinfo(JSONObject json) {
    }

    public void init() {
        stkex = null;
        stkbd  = "";
        stkCode = "";
        trdDate = 0;
        closingPrice = 0L;
        openingPrice = 0L;
        currentPrice = 0L;
        bondInt      = 0L;
        bondIntDate = 0;
        etfIopv = 0L;
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

    public void setStkCode(String stkCode) {
        this.stkCode = stkCode;
    }

    public String getStkCode() {
        return this.stkCode;
    }

    public void setTrdDate(int trdDate) {
        this.trdDate = trdDate;
    }

    public int getTrdDate() {
        return this.trdDate;
    }

    public void setClosingPrice(long closingPrice) {
        this.closingPrice = closingPrice;
    }

    public long getClosingPrice() {
        return this.closingPrice;
    }

    public void setOpeningPrice(long openingPrice) {
        this.openingPrice = openingPrice;
    }

    public long getOpeningPrice() {
        return this.openingPrice;
    }

    public void setCurrentPrice(long currentPrice) {
        this.currentPrice = currentPrice;
    }

    public long getCurrentPrice() {
        return this.currentPrice;
    }

    public void setBondInt(long bondInt) {
        this.bondInt = bondInt;
    }

    public long getBondInt() {
        return this.bondInt;
    }

    public void setBondIntDate(int bondIntDate) {
        this.bondIntDate = bondIntDate;
    }

    public int getBondIntDate() {
        return this.bondIntDate;
    }

    public void setEtfIopv(long etfIopv) {
        this.etfIopv = etfIopv;
    }

    public long getEtfIopv() {
        return this.etfIopv;
    }

}
