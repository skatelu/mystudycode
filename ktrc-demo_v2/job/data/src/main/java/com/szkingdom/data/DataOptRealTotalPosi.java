/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: OptRealTotalPosi
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
public class DataOptRealTotalPosi implements Serializable {
    private static final long serialVersionUID = 4883746721605823359L;
    private Character stkex;
    private String stkbd;
    private String optUndlCode;
    private String optUndlName;
    private Character lsFlag;
    private Character corpLmtAttr;
    private long actTotalPosi;
    private long realTotalPosi;
    private Character checkFlag;

    public DataOptRealTotalPosi(){

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

    public Character getCorpLmtAttr() {
        return corpLmtAttr;
    }

    public void setCorpLmtAttr(Character corpLmtAttr) {
        this.corpLmtAttr = corpLmtAttr;
    }

    public long getActTotalPosi() {
        return actTotalPosi;
    }

    public void setActTotalPosi(int actTotalPosi) {
        this.actTotalPosi = actTotalPosi;
    }

    public long getRealTotalPosi() {
        return realTotalPosi;
    }

    public void setRealTotalPosi(int realTotalPosi) {
        this.realTotalPosi = realTotalPosi;
    }

    public Character getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Character checkFlag) {
        this.checkFlag = checkFlag;
    }

    @Override
    public String toString() {
        return "DataOptRealTotalPosi{" +
                "stkex=" + stkex +
                ", stkbd='" + stkbd + '\'' +
                ", optUndlCode='" + optUndlCode + '\'' +
                ", optUndlName='" + optUndlName + '\'' +
                ", lsFlag=" + lsFlag +
                ", corpLmtAttr=" + corpLmtAttr +
                ", actTotalPosi=" + actTotalPosi +
                ", realTotalPosi=" + realTotalPosi +
                ", checkFlag=" + checkFlag +
                '}';
    }
}
