/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: OptTotalPosiLmt
 * Author:   zmh
 * Date:     2018/7/6 19:17
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
public class DataOptTotalPosiLmt implements Serializable {
    private static final long serialVersionUID = 3743343129749665639L;
    private Character stkex;
    private String stkbd;
    private String totalLmtAttr;
    private long optPosiLqty;

    public DataOptTotalPosiLmt(){

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

    public String getTotalLmtAttr() {
        return totalLmtAttr;
    }

    public void setTotalLmtAttr(String totalLmtAttr) {
        this.totalLmtAttr = totalLmtAttr;
    }

    public long getOptPosiLqty() {
        return optPosiLqty;
    }

    public void setOptPosiLqty(int optPosiLqty) {
        this.optPosiLqty = optPosiLqty;
    }

    @Override
    public String toString() {
        return "DataOptTotalPosiLmt{" +
                "stkex=" + stkex +
                ", stkbd='" + stkbd + '\'' +
                ", totalLmtAttr='" + totalLmtAttr + '\'' +
                ", optPosiLqty=" + optPosiLqty +
                '}';
    }
}
