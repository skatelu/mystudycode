package com.szkingdom.data;


import java.io.Serializable;

/**
 * @author zhengmj
 * @date 2018/7/7 09:35
 */
public class DataCustomer implements Serializable {
    private static final long serialVersionUID = 3987208478111431683L;
    private long custCode;
    private String custName;
    private Character custType;
    private Character custCls;
    private Character custStatus;
    private String cardId;
    private String criterion;
    private String riskFactor;
    private short intOrg;
    private int openDate;
    private String channels;
    private String remark;
    private Character creditLvl;
    private Character remoteProtocol;
    private Character custSource;
    private Character serviceLvl;
    private String custExtAttr;
    private int closeDate;
    private short subsysSn;
    private Character isVip;
    private short vipSubsysSn;
    private long comDiscount;
    private Character marginLvl;
    private Character investorType;
    private int profInvestorExpDate;
    private Character ratingLvl;
    private int ratingDate;
    private int ratingExpDate;
    private Character lowestRiskFlag;
    private String investPro;
    private Character investLmt;
    private Character investIncome;
    private Character isSign;

    public DataCustomer() {

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

    public Character getCustType() {
        return custType;
    }

    public void setCustType(Character custType) {
        this.custType = custType;
    }

    public Character getCustCls() {
        return custCls;
    }

    public void setCustCls(Character custCls) {
        this.custCls = custCls;
    }

    public Character getCustStatus() {
        return custStatus;
    }

    public void setCustStatus(Character custStatus) {
        this.custStatus = custStatus;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCriterion() {
        return criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    public String getRiskFactor() {
        return riskFactor;
    }

    public void setRiskFactor(String riskFactor) {
        this.riskFactor = riskFactor;
    }

    public short getIntOrg() {
        return intOrg;
    }

    public void setIntOrg(short intOrg) {
        this.intOrg = intOrg;
    }

    public int getOpenDate() {
        return openDate;
    }

    public void setOpenDate(int openDate) {
        this.openDate = openDate;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Character getCreditLvl() {
        return creditLvl;
    }

    public void setCreditLvl(Character creditLvl) {
        this.creditLvl = creditLvl;
    }

    public Character getRemoteProtocol() {
        return remoteProtocol;
    }

    public void setRemoteProtocol(Character remoteProtocol) {
        this.remoteProtocol = remoteProtocol;
    }

    public Character getCustSource() {
        return custSource;
    }

    public void setCustSource(Character custSource) {
        this.custSource = custSource;
    }

    public Character getServiceLvl() {
        return serviceLvl;
    }

    public void setServiceLvl(Character serviceLvl) {
        this.serviceLvl = serviceLvl;
    }

    public String getCustExtAttr() {
        return custExtAttr;
    }

    public void setCustExtAttr(String custExtAttr) {
        this.custExtAttr = custExtAttr;
    }

    public int getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(int closeDate) {
        this.closeDate = closeDate;
    }

    public short getSubsysSn() {
        return subsysSn;
    }

    public void setSubsysSn(short subsysSn) {
        this.subsysSn = subsysSn;
    }

    public Character getIsVip() {
        return isVip;
    }

    public void setIsVip(Character isVip) {
        this.isVip = isVip;
    }

    public short getVipSubsysSn() {
        return vipSubsysSn;
    }

    public void setVipSubsysSn(short vipSubsysSn) {
        this.vipSubsysSn = vipSubsysSn;
    }

    public long getComDiscount() {
        return comDiscount;
    }

    public void setComDiscount(long comDiscount) {
        this.comDiscount = comDiscount;
    }

    public Character getMarginLvl() {
        return marginLvl;
    }

    public void setMarginLvl(Character marginLvl) {
        this.marginLvl = marginLvl;
    }

    public Character getInvestorType() {
        return investorType;
    }

    public void setInvestorType(Character investorType) {
        this.investorType = investorType;
    }

    public int getProfInvestorExpDate() {
        return profInvestorExpDate;
    }

    public void setProfInvestorExpDate(int profInvestorExpDate) {
        this.profInvestorExpDate = profInvestorExpDate;
    }

    public Character getRatingLvl() {
        return ratingLvl;
    }

    public void setRatingLvl(Character ratingLvl) {
        this.ratingLvl = ratingLvl;
    }

    public int getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(int ratingDate) {
        this.ratingDate = ratingDate;
    }

    public int getRatingExpDate() {
        return ratingExpDate;
    }

    public void setRatingExpDate(int ratingExpDate) {
        this.ratingExpDate = ratingExpDate;
    }

    public Character getLowestRiskFlag() {
        return lowestRiskFlag;
    }

    public void setLowestRiskFlag(Character lowestRiskFlag) {
        this.lowestRiskFlag = lowestRiskFlag;
    }

    public String getInvestPro() {
        return investPro;
    }

    public void setInvestPro(String investPro) {
        this.investPro = investPro;
    }

    public Character getInvestLmt() {
        return investLmt;
    }

    public void setInvestLmt(Character investLmt) {
        this.investLmt = investLmt;
    }

    public Character getInvestIncome() {
        return investIncome;
    }

    public void setInvestIncome(Character investIncome) {
        this.investIncome = investIncome;
    }

    public Character getIsSign() {
        return isSign;
    }

    public void setIsSign(Character isSign) {
        this.isSign = isSign;
    }
}
