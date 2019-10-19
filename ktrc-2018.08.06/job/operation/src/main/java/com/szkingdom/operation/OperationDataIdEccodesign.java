/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationDataIdOptFund
 * Author:   liujin
 * Date:     2018/8/27
 * Description: 平台接口客户理财计划电子合同约定Eccodesign数据类
 * Version:  0.1.0
 * History:
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class OperationDataIdEccodesign extends OperationBase {

    private String dataId;
    private long endian;
    private int resfundExpdate;
    private int gOperWay;
    private double reservFund;
    private String gCheckSno;
    private String gFuncId;
    private String gOperId;
    private String serverId;
    private String orgId;
    private String ofCode;
    private long fundId;
    private String gServerId;
    private int multisettExp;
    private String gOperPwd;
    private String ca;
    private String gStationAddr;
    private String multisettStatus;

    public OperationDataIdEccodesign() {
        init();
    }

    public OperationDataIdEccodesign(JSONObject json) {
    }

    private void init(){
        this.dataId = "";
        this.endian = 0L;
        this.resfundExpdate = 0;
        this.gOperWay = 0;
        this.reservFund = 0L;
        this.gCheckSno = "";
        this.gFuncId = "";
        this.gOperId = "";
        this.serverId = "";
        this.orgId = "";
        this.ofCode = "";
        this.fundId = 0;
        this.gServerId = "";
        this.multisettExp = 0;
        this.gOperPwd = "";
        this.ca = "";
        this.gStationAddr = "";
        this.multisettStatus = "";
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public long getEndian() {
        return endian;
    }

    public void setEndian(long endian) {
        this.endian = endian;
    }

    public int getResfundExpdate() {
        return resfundExpdate;
    }

    public void setResfundExpdate(int resfundExpdate) {
        this.resfundExpdate = resfundExpdate;
    }

    public int getgOperWay() {
        return gOperWay;
    }

    public void setgOperWay(int gOperWay) {
        this.gOperWay = gOperWay;
    }

    public double getReservFund() {
        return reservFund;
    }

    public void setReservFund(double reservFund) {
        this.reservFund = reservFund;
    }

    public String getgCheckSno() {
        return gCheckSno;
    }

    public void setgCheckSno(String gCheckSno) {
        this.gCheckSno = gCheckSno;
    }

    public String getgFuncId() {
        return gFuncId;
    }

    public void setgFuncId(String gFuncId) {
        this.gFuncId = gFuncId;
    }

    public String getgOperId() {
        return gOperId;
    }

    public void setgOperId(String gOperId) {
        this.gOperId = gOperId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOfCode() {
        return ofCode;
    }

    public void setOfCode(String ofCode) {
        this.ofCode = ofCode;
    }

    public long getFundId() {
        return fundId;
    }

    public void setFundId(long fundId) {
        this.fundId = fundId;
    }

    public String getgServerId() {
        return gServerId;
    }

    public void setgServerId(String gServerId) {
        this.gServerId = gServerId;
    }

    public int getMultisettExp() {
        return multisettExp;
    }

    public void setMultisettExp(int multisettExp) {
        this.multisettExp = multisettExp;
    }

    public String getgOperPwd() {
        return gOperPwd;
    }

    public void setgOperPwd(String gOperPwd) {
        this.gOperPwd = gOperPwd;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getgStationAddr() {
        return gStationAddr;
    }

    public void setgStationAddr(String gStationAddr) {
        this.gStationAddr = gStationAddr;
    }

    public String getMultisettStatus() {
        return multisettStatus;
    }

    public void setMultisettStatus(String multisettStatus) {
        this.multisettStatus = multisettStatus;
    }
}
