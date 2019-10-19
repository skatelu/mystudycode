/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: OptCustPosiLmt
 * Author:   zmh
 * Date:     2018/7/6 19:15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szkingdom.data;

import java.io.Serializable;

/**
 * 〈〉
 * @author zmh
 * @create 2018/7/6
 * @since 1.0.0
 */
public class DataOptCustPosiLmt implements Serializable {
    private static final long serialVersionUID = -244646759302558923L;
    private Character stkex;
    private String stkbd;
    private long custCode;
    private String trdacct;
    private Character optUndlCls;
    private String optUndlCode;
    private Character lsFlag;
    private Character custLmtAttr;
    private long optPosiLqty;
    private int posiBgnDate;
    private int posiEndDate;

    public DataOptCustPosiLmt() {

    }

    public DataOptCustPosiLmt(String stkbd, long custCode, String trdacct,
                              Character optUndlCls, String optUndlCode, Character lsFlag){
        this.setStkbd(stkbd);
        this.setCustCode(custCode);
        this.setTrdacct(trdacct);
        this.setOptUndlCls(optUndlCls);
        this.setOptUndlCode(optUndlCode);
        this.setLsFlag(lsFlag);
    }

    public Character getStkex() {
        return stkex;
    }

    public void setStkex(Character stkex) {
        this.stkex = stkex;
    }

    public String getStkbd() {
        return stkbd;
    }

    public void setStkbd(String stkbd) {
        this.stkbd = stkbd;
    }

    public long getCustCode() {
        return custCode;
    }

    public void setCustCode(long custCode) {
        this.custCode = custCode;
    }

    public String getTrdacct() {
        return trdacct;
    }

    public void setTrdacct(String trdacct) {
        this.trdacct = trdacct;
    }

    public Character getOptUndlCls() {
        return optUndlCls;
    }

    public void setOptUndlCls(Character optUndlCls) {
        this.optUndlCls = optUndlCls;
    }

    public String getOptUndlCode() {
        return optUndlCode;
    }

    public void setOptUndlCode(String optUndlCode) {
        this.optUndlCode = optUndlCode;
    }

    public Character getLsFlag() {
        return lsFlag;
    }

    public void setLsFlag(Character lsFlag) {
        this.lsFlag = lsFlag;
    }

    public Character getCustLmtAttr() {
        return custLmtAttr;
    }

    public void setCustLmtAttr(Character custLmtAttr) {
        this.custLmtAttr = custLmtAttr;
    }

    public long getOptPosiLqty() {
        return optPosiLqty;
    }

    public void setOptPosiLqty(int optPosiLqty) {
        this.optPosiLqty = optPosiLqty;
    }

    public int getPosiBgnDate() {
        return posiBgnDate;
    }

    public void setPosiBgnDate(int posiBgnDate) {
        this.posiBgnDate = posiBgnDate;
    }

    public int getPosiEndDate() {
        return posiEndDate;
    }

    public void setPosiEndDate(int posiEndDate) {
        this.posiEndDate = posiEndDate;
    }

    @Override
    public String toString() {
        return "DataOptCustPosiLmt{" +
                "stkex=" + stkex +
                ", stkbd='" + stkbd + '\'' +
                ", custCode=" + custCode +
                ", trdacct='" + trdacct + '\'' +
                ", optUndlCls=" + optUndlCls +
                ", optUndlCode='" + optUndlCode + '\'' +
                ", lsFlag=" + lsFlag +
                ", custLmtAttr=" + custLmtAttr +
                ", optPosiLqty=" + optPosiLqty +
                ", posiBgnDate=" + posiBgnDate +
                ", posiEndDate=" + posiEndDate +
                '}';
    }
}
