package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by liujin on 2018/9/29.
 */
public class DataEccodesign extends DataBase {
    private static final long serialVersionUID = 6153514380584262529L;
    private int serverId;
    private long custId;
    private long fundId;
    private String fundCode;
    private String taacc;
    private String transacc;
    private String ectype;
    private int sno;
    private String dbtId;
    private String fullName;
    private String accType;
    private String idType;
    private String idNo;
    private int orderDate;
    private int operTime;
    private String operWay;
    private String netAddr;
    private String operOrg;
    private String remark;
    private String ecSno;
    private int operId;
    private String orgId;
    private String dealFlag;
    private String status;
    private int reorderDate;
    private int reoperTime;
    private String expand1;
    private String expand2;
    private String expand3;
    private String expand4;
    private String riskMatch;
    private String riskLevel;
    private String telNo;
    private String mobil;
    private String email;
    private String postId;
    private String addr;
    private String notAssignFlag;
    private String redeemContract;
    private double reservFund;
    private double dyreservFund;
    private int resfundExpdate;
    private String multisettStatus;
    private int multisettExp;
    private int multisettUpdate;
    private double lastConvertvol;
    private int lastDivate;
    private String isNewsign;
    private double beforConvertvol;
    private int updateDate;
    private String specialCtrl;
    private double prereservFund;
    private int prereservFundDate;

    public DataEccodesign() {
        init();
    }
    public DataEccodesign(JSONObject json) {
    }

    private void init(){
        this.serverId=0;
        this.custId=0;
        this.fundId=0;
        this.fundCode="";
        this.taacc="";
        this.transacc="";
        this.ectype="";
        this.sno=0;
        this.dbtId="";
        this.fullName="";
        this.accType="";
        this.idType="";
        this.idNo="";
        this.orderDate=0;
        this.operTime=0;
        this.operWay="";
        this.netAddr="";
        this.operOrg="";
        this.remark="";
        this.ecSno="";
        this.operId=0;
        this.orgId="";
        this.dealFlag="";
        this.status="";
        this.reorderDate=0;
        this.reoperTime=0;
        this.expand1="";
        this.expand2="";
        this.expand3="";
        this.expand4="";
        this.riskMatch="";
        this.riskLevel="";
        this.telNo="";
        this.mobil="";
        this.email="";
        this.postId="";
        this.addr="";
        this.notAssignFlag="";
        this.redeemContract="";
        this.reservFund=0;
        this.dyreservFund=0;
        this.resfundExpdate=0;
        this.multisettStatus="";
        this.multisettExp=0;
        this.multisettUpdate=0;
        this.lastConvertvol=0;
        this.lastDivate=0;
        this.isNewsign="";
        this.beforConvertvol=0;
        this.updateDate=0;
        this.specialCtrl="";
        this.prereservFund=0;
        this.prereservFundDate=0;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public long getFundId() {
        return fundId;
    }

    public void setFundId(long fundId) {
        this.fundId = fundId;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public String getTaacc() {
        return taacc;
    }

    public void setTaacc(String taacc) {
        this.taacc = taacc;
    }

    public String getTransacc() {
        return transacc;
    }

    public void setTransacc(String transacc) {
        this.transacc = transacc;
    }

    public String getEctype() {
        return ectype;
    }

    public void setEctype(String ectype) {
        this.ectype = ectype;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getDbtId() {
        return dbtId;
    }

    public void setDbtId(String dbtId) {
        this.dbtId = dbtId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public int getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(int orderDate) {
        this.orderDate = orderDate;
    }

    public int getOperTime() {
        return operTime;
    }

    public void setOperTime(int operTime) {
        this.operTime = operTime;
    }

    public String getOperWay() {
        return operWay;
    }

    public void setOperWay(String operWay) {
        this.operWay = operWay;
    }

    public String getNetAddr() {
        return netAddr;
    }

    public void setNetAddr(String netAddr) {
        this.netAddr = netAddr;
    }

    public String getOperOrg() {
        return operOrg;
    }

    public void setOperOrg(String operOrg) {
        this.operOrg = operOrg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEcSno() {
        return ecSno;
    }

    public void setEcSno(String ecSno) {
        this.ecSno = ecSno;
    }

    public int getOperId() {
        return operId;
    }

    public void setOperId(int operId) {
        this.operId = operId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDealFlag() {
        return dealFlag;
    }

    public void setDealFlag(String dealFlag) {
        this.dealFlag = dealFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReorderDate() {
        return reorderDate;
    }

    public void setReorderDate(int reorderDate) {
        this.reorderDate = reorderDate;
    }

    public int getReoperTime() {
        return reoperTime;
    }

    public void setReoperTime(int reoperTime) {
        this.reoperTime = reoperTime;
    }

    public String getExpand1() {
        return expand1;
    }

    public void setExpand1(String expand1) {
        this.expand1 = expand1;
    }

    public String getExpand2() {
        return expand2;
    }

    public void setExpand2(String expand2) {
        this.expand2 = expand2;
    }

    public String getExpand3() {
        return expand3;
    }

    public void setExpand3(String expand3) {
        this.expand3 = expand3;
    }

    public String getExpand4() {
        return expand4;
    }

    public void setExpand4(String expand4) {
        this.expand4 = expand4;
    }

    public String getRiskMatch() {
        return riskMatch;
    }

    public void setRiskMatch(String riskMatch) {
        this.riskMatch = riskMatch;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getNotAssignFlag() {
        return notAssignFlag;
    }

    public void setNotAssignFlag(String notAssignFlag) {
        this.notAssignFlag = notAssignFlag;
    }

    public String getRedeemContract() {
        return redeemContract;
    }

    public void setRedeemContract(String redeemContract) {
        this.redeemContract = redeemContract;
    }

    public double getReservFund() {
        return reservFund;
    }

    public void setReservFund(double reservFund) {
        this.reservFund = reservFund;
    }

    public double getDyreservFund() {
        return dyreservFund;
    }

    public void setDyreservFund(double dyreservFund) {
        this.dyreservFund = dyreservFund;
    }

    public int getResfundExpdate() {
        return resfundExpdate;
    }

    public void setResfundExpdate(int resfundExpdate) {
        this.resfundExpdate = resfundExpdate;
    }

    public String getMultisettStatus() {
        return multisettStatus;
    }

    public void setMultisettStatus(String multisettStatus) {
        this.multisettStatus = multisettStatus;
    }

    public int getMultisettExp() {
        return multisettExp;
    }

    public void setMultisettExp(int multisettExp) {
        this.multisettExp = multisettExp;
    }

    public int getMultisettUpdate() {
        return multisettUpdate;
    }

    public void setMultisettUpdate(int multisettUpdate) {
        this.multisettUpdate = multisettUpdate;
    }

    public double getLastConvertvol() {
        return lastConvertvol;
    }

    public void setLastConvertvol(double lastConvertvol) {
        this.lastConvertvol = lastConvertvol;
    }

    public int getLastDivate() {
        return lastDivate;
    }

    public void setLastDivate(int lastDivate) {
        this.lastDivate = lastDivate;
    }

    public String getIsNewsign() {
        return isNewsign;
    }

    public void setIsNewsign(String isNewsign) {
        this.isNewsign = isNewsign;
    }

    public double getBeforConvertvol() {
        return beforConvertvol;
    }

    public void setBeforConvertvol(double beforConvertvol) {
        this.beforConvertvol = beforConvertvol;
    }

    public int getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(int updateDate) {
        this.updateDate = updateDate;
    }

    public String getSpecialCtrl() {
        return specialCtrl;
    }

    public void setSpecialCtrl(String specialCtrl) {
        this.specialCtrl = specialCtrl;
    }

    public double getPrereservFund() {
        return prereservFund;
    }

    public void setPrereservFund(double prereservFund) {
        this.prereservFund = prereservFund;
    }

    public int getPrereservFundDate() {
        return prereservFundDate;
    }

    public void setPrereservFundDate(int prereservFundDate) {
        this.prereservFundDate = prereservFundDate;
    }
}
