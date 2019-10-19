package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Lenovo on 2018/9/29.
 */
public class DataEtfCashcomponent extends DataBase  {
    private static final long serialVersionUID = 4867260380974262529L;
    private String date;
    private long fundId;
    private long custId;
    private String custName;
    private String orgName;
    private double ykkje;
    private double djje;
    private double ydbzje;
    private double kyye;
    private double qsje;
    private double yled;
    private int jzrq;

    public DataEtfCashcomponent() {
        init();
    }
    public DataEtfCashcomponent(JSONObject json) {
    }
    private void init() {
        this.date="";
        this.fundId=0;
        this.custId=0;
        this.custName="";
        this.orgName="";
        this.ykkje=0;
        this.djje=0;
        this.ydbzje=0;
        this.kyye=0;
        this.qsje=0;
        this.yled=0;
        this.jzrq=0;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getFundId() {
        return fundId;
    }

    public void setFundId(long fundId) {
        this.fundId = fundId;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public double getYkkje() {
        return ykkje;
    }

    public void setYkkje(double ykkje) {
        this.ykkje = ykkje;
    }

    public double getDjje() {
        return djje;
    }

    public void setDjje(double djje) {
        this.djje = djje;
    }

    public double getYdbzje() {
        return ydbzje;
    }

    public void setYdbzje(double ydbzje) {
        this.ydbzje = ydbzje;
    }

    public double getKyye() {
        return kyye;
    }

    public void setKyye(double kyye) {
        this.kyye = kyye;
    }

    public double getQsje() {
        return qsje;
    }

    public void setQsje(double qsje) {
        this.qsje = qsje;
    }

    public double getYled() {
        return yled;
    }

    public void setYled(double yled) {
        this.yled = yled;
    }

    public int getJzrq() {
        return jzrq;
    }

    public void setJzrq(int jzrq) {
        this.jzrq = jzrq;
    }
}
