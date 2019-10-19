/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: OptClsPosiLmt
 * Author:   zmh
 * Date:     2018/7/6 18:34
 * Description: 表OPT_CLS_POSI_LMT
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szkingdom.data;

import java.io.Serializable;

/**
 * 〈表OPT_CLS_POSI_LMT〉
 * @author zmh
 * @create 2018/7/6
 * @since 1.0.0
 */
public class DataOptClsPosiLmt implements Serializable {

    private static final long serialVersionUID = 6121828657951238779L;
    private Character stkex;
    private String stkbd;
    private Character optUndlCls;
    private Character lsFlag;
    private Character posiLmtAttr;
    private long optPosiLqty;

    public DataOptClsPosiLmt() {

    }

    public DataOptClsPosiLmt(String stkbd, Character optUndlCls, Character lsFlag, Character posiLmtAttr){
        this.setStkbd(stkbd);
        this.setOptUndlCls(optUndlCls);
        this.setLsFlag(lsFlag);
        this.setPosiLmtAttr(posiLmtAttr);
    }

    public void setStkex(Character stkex) {
        this.stkex = stkex;
    }

    public void setStkbd(String stkbd) {
        this.stkbd = stkbd;
    }

    public void setOptUndlCls(Character optUndlCls) {
        this.optUndlCls = optUndlCls;
    }

    public void setLsFlag(Character lsFlag) {
        this.lsFlag = lsFlag;
    }

    public void setPosiLmtAttr(Character posiLmtAttr) {
        this.posiLmtAttr = posiLmtAttr;
    }

    public void setOptPosiLqty(int optPosiLqty) {
        this.optPosiLqty = optPosiLqty;
    }

    public Character getStkex() {
        return stkex;
    }

    public String getStkbd() {
        return stkbd;
    }

    public Character getOptUndlCls() {
        return optUndlCls;
    }

    public Character getLsFlag() {
        return lsFlag;
    }

    public Character getPosiLmtAttr() {
        return posiLmtAttr;
    }

    public long getOptPosiLqty() {
        return optPosiLqty;
    }

    @Override
    public String toString() {
        return "DataOptClsPosiLmt{" +
                "stkex=" + stkex +
                ", stkbd='" + stkbd + '\'' +
                ", optUndlCls=" + optUndlCls +
                ", lsFlag=" + lsFlag +
                ", posiLmtAttr=" + posiLmtAttr +
                ", optPosiLqty=" + optPosiLqty +
                '}';
    }
}
