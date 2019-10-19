/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: OptPosiLmt
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
public class DataOptPosiLmt implements Serializable {
    private static final long serialVersionUID = -516917080696852326L;
    private Character stkex;
    private String stkbd;
    private Character optUndlCls;
    private String optUndlCode;
    private String optUndlName;
    private Character lsFlag;
    private Character posiLmtAttr;
    private long optPosiLqty;

    public DataOptPosiLmt() {

    }

    public DataOptPosiLmt(String stkbd, String optUndlCode, Character lsFlag, Character posiLmtAttr){
        this.setStkbd(stkbd);
        this.setOptUndlCode(optUndlCode);
        this.setLsFlag(lsFlag);
        this.setPosiLmtAttr(posiLmtAttr);
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

    public String getOptUndlName() {
        return optUndlName;
    }

    public void setOptUndlName(String optUndlName) {
        this.optUndlName = optUndlName;
    }

    public Character getLsFlag() {
        return lsFlag;
    }

    public void setLsFlag(Character lsFlag) {
        this.lsFlag = lsFlag;
    }

    public Character getPosiLmtAttr() {
        return posiLmtAttr;
    }

    public void setPosiLmtAttr(Character posiLmtAttr) {
        this.posiLmtAttr = posiLmtAttr;
    }

    public long getOptPosiLqty() {
        return optPosiLqty;
    }

    public void setOptPosiLqty(int optPosiLqty) {
        this.optPosiLqty = optPosiLqty;
    }

    @Override
    public String toString() {
        return "DataOptPosiLmt{" +
                "stkex=" + stkex +
                ", stkbd='" + stkbd + '\'' +
                ", optUndlCls=" + optUndlCls +
                ", optUndlCode='" + optUndlCode + '\'' +
                ", optUndlName='" + optUndlName + '\'' +
                ", lsFlag=" + lsFlag +
                ", posiLmtAttr=" + posiLmtAttr +
                ", optPosiLqty=" + optPosiLqty +
                '}';
    }
}
