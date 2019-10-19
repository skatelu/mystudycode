package com.szkingdom.data;

import java.io.Serializable;

/**
 * @author zhangch
 * @date 2018/7/6 18:07
 */
public class DataStkInfo implements Serializable {
    private static final long serialVersionUID = -7906995890235670636L;
    private Character stkex;
    private String stkbd;
    private String stkCode;
    private String stkName;
    private String stkIsin;
    private Character stkCls;
    private Character stkSubCls;
    private Character stkStatus;
    private Character currency;
    private int stkUplmtRatio;
    private int stkLwlmtRatio;
    private int stkUplmtPrice;
    private int stkLwlmtPrice;
    private int stkUplmtQty;
    private int stkLwlmtQty;
    private int stkLotSize;
    private Character stkLotFlag;
    private int stkSpread;
    private int stkFaceVal;
    private Character stkCalMktval;
    private Character stkSuspended;
    private Character stkCustodyMode;
    private String stkUndlCode;
    private String stkBizes;
    private int updDate;
    private Character stkLevel;
    private int stkDeadline;
    private String remindMessage;

    public DataStkInfo(){

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

    public String getStkIsin() {
        return stkIsin;
    }

    public void setStkIsin(String stkIsin) {
        this.stkIsin = stkIsin;
    }

    public Character getStkCls() {
        return stkCls;
    }

    public void setStkCls(Character stkCls) {
        this.stkCls = stkCls;
    }

    public Character getStkSubCls() {
        return stkSubCls;
    }

    public void setStkSubCls(Character stkSubCls) {
        this.stkSubCls = stkSubCls;
    }

    public Character getStkStatus() {
        return stkStatus;
    }

    public void setStkStatus(Character stkStatus) {
        this.stkStatus = stkStatus;
    }

    public Character getCurrency() {
        return currency;
    }

    public void setCurrency(Character currency) {
        this.currency = currency;
    }

    public int getStkUplmtRatio() {
        return stkUplmtRatio;
    }

    public void setStkUplmtRatio(int stkUplmtRatio) {
        this.stkUplmtRatio = stkUplmtRatio;
    }

    public int getStkLwlmtRatio() {
        return stkLwlmtRatio;
    }

    public void setStkLwlmtRatio(int stkLwlmtRatio) {
        this.stkLwlmtRatio = stkLwlmtRatio;
    }

    public int getStkUplmtPrice() {
        return stkUplmtPrice;
    }

    public void setStkUplmtPrice(int stkUplmtPrice) {
        this.stkUplmtPrice = stkUplmtPrice;
    }

    public int getStkLwlmtPrice() {
        return stkLwlmtPrice;
    }

    public void setStkLwlmtPrice(int stkLwlmtPrice) {
        this.stkLwlmtPrice = stkLwlmtPrice;
    }

    public int getStkUplmtQty() {
        return stkUplmtQty;
    }

    public void setStkUplmtQty(int stkUplmtQty) {
        this.stkUplmtQty = stkUplmtQty;
    }

    public int getStkLwlmtQty() {
        return stkLwlmtQty;
    }

    public void setStkLwlmtQty(int stkLwlmtQty) {
        this.stkLwlmtQty = stkLwlmtQty;
    }

    public int getStkLotSize() {
        return stkLotSize;
    }

    public void setStkLotSize(int stkLotSize) {
        this.stkLotSize = stkLotSize;
    }

    public Character getStkLotFlag() {
        return stkLotFlag;
    }

    public void setStkLotFlag(Character stkLotFlag) {
        this.stkLotFlag = stkLotFlag;
    }

    public int getStkSpread() {
        return stkSpread;
    }

    public void setStkSpread(int stkSpread) {
        this.stkSpread = stkSpread;
    }

    public int getStkFaceVal() {
        return stkFaceVal;
    }

    public void setStkFaceVal(int stkFaceVal) {
        this.stkFaceVal = stkFaceVal;
    }

    public Character getStkCalMktval() {
        return stkCalMktval;
    }

    public void setStkCalMktval(Character stkCalMktval) {
        this.stkCalMktval = stkCalMktval;
    }

    public Character getStkSuspended() {
        return stkSuspended;
    }

    public void setStkSuspended(Character stkSuspended) {
        this.stkSuspended = stkSuspended;
    }

    public Character getStkCustodyMode() {
        return stkCustodyMode;
    }

    public void setStkCustodyMode(Character stkCustodyMode) {
        this.stkCustodyMode = stkCustodyMode;
    }

    public String getStkUndlCode() {
        return stkUndlCode;
    }

    public void setStkUndlCode(String stkUndlCode) {
        this.stkUndlCode = stkUndlCode;
    }

    public String getStkBizes() {
        return stkBizes;
    }

    public void setStkBizes(String stkBizes) {
        this.stkBizes = stkBizes;
    }

    public int getUpdDate() {
        return updDate;
    }

    public void setUpdDate(int updDate) {
        this.updDate = updDate;
    }

    public Character getStkLevel() {
        return stkLevel;
    }

    public void setStkLevel(Character stkLevel) {
        this.stkLevel = stkLevel;
    }

    public int getStkDeadline() {
        return stkDeadline;
    }

    public void setStkDeadline(int stkDeadline) {
        this.stkDeadline = stkDeadline;
    }

    public String getRemindMessage() {
        return remindMessage;
    }

    public void setRemindMessage(String remindMessage) {
        this.remindMessage = remindMessage;
    }

    @Override
    public String toString() {
        return "DataStkInfo{" +
                "stkex=" + stkex +
                ", stkbd='" + stkbd + '\'' +
                ", stkCode='" + stkCode + '\'' +
                ", stkName='" + stkName + '\'' +
                ", stkIsin='" + stkIsin + '\'' +
                ", stkCls=" + stkCls +
                ", stkSubCls=" + stkSubCls +
                ", stkStatus=" + stkStatus +
                ", currency=" + currency +
                ", stkUplmtRatio=" + stkUplmtRatio +
                ", stkLwlmtRatio=" + stkLwlmtRatio +
                ", stkUplmtPrice=" + stkUplmtPrice +
                ", stkLwlmtPrice=" + stkLwlmtPrice +
                ", stkUplmtQty=" + stkUplmtQty +
                ", stkLwlmtQty=" + stkLwlmtQty +
                ", stkLotSize=" + stkLotSize +
                ", stkLotFlag=" + stkLotFlag +
                ", stkSpread=" + stkSpread +
                ", stkFaceVal=" + stkFaceVal +
                ", stkCalMktval=" + stkCalMktval +
                ", stkSuspended=" + stkSuspended +
                ", stkCustodyMode=" + stkCustodyMode +
                ", stkUndlCode='" + stkUndlCode + '\'' +
                ", stkBizes='" + stkBizes + '\'' +
                ", updDate=" + updDate +
                ", stkLevel=" + stkLevel +
                ", stkDeadline=" + stkDeadline +
                ", remindMessage='" + remindMessage + '\'' +
                '}';
    }
}
