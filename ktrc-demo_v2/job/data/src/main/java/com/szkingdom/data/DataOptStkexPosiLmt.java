/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: OptStkexPosiLmt
 * Author:   zmh
 * Date:     2018/7/6 19:16
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
public class DataOptStkexPosiLmt implements Serializable {
    private static final long serialVersionUID = -1245629286923415259L;
    private Character stkex;
    private String stkbd;
    private Character lsFlag;
    private Character stkexLmtAttr;
    private long optPosiLqty;

    public DataOptStkexPosiLmt(){

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

    public Character getLsFlag() {
        return lsFlag;
    }

    public void setLsFlag(Character lsFlag) {
        this.lsFlag = lsFlag;
    }

    public Character getStkexLmtAttr() {
        return stkexLmtAttr;
    }

    public void setStkexLmtAttr(Character stkexLmtAttr) {
        this.stkexLmtAttr = stkexLmtAttr;
    }

    public long getOptPosiLqty() {
        return optPosiLqty;
    }

    public void setOptPosiLqty(int optPosiLqty) {
        this.optPosiLqty = optPosiLqty;
    }

    @Override
    public String toString() {
        return "DataOptStkexPosiLmt{" +
                "stkex=" + stkex +
                ", stkbd='" + stkbd + '\'' +
                ", lsFlag=" + lsFlag +
                ", stkexLmtAttr=" + stkexLmtAttr +
                ", optPosiLqty=" + optPosiLqty +
                '}';
    }
}
