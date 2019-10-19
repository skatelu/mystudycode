/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationDataIdOptFund
 * Author:   ZhengMingjie
 * Date:     2018/8/27
 * Description: 平台接口资金变动OPT_FUND数据类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import java.util.Date;

public class OperationDataIdOptFund extends OperationBase {

    private String dataId;
    private long serialNo;
    private int occDate;
    private Date occTime;
    private long custCode;
    private long cuacctCode;
    private Character currency;
    private short intOrg;
    private int bizCode;
    private Character fundChangeFlag;
    private long fundBln;
    private long fundBlnVary;
    private long fundAvlVary;
    private long dailyInAmt;
    private long dailyOutAmt;
    private long originalSn;
    private long opUser;
    private Character opRole;
    private String opName;
    private short opOrg;
    private String opSite;
    private Character channel;
    private short subsysSn;

    public OperationDataIdOptFund() {
        init();
    }

    public OperationDataIdOptFund(JSONObject json) {
    }

    private void init(){
        this.dataId = "";
        this.serialNo = 0L;
        this.occDate = 0;
        this.occTime = new Date();
        this.custCode = 0L;
        this.cuacctCode = 0L;
        this.currency = null;
        this.intOrg = 0;
        this.bizCode = 0;
        this.fundChangeFlag = null;
        this.fundBln = 0L;
        this.fundBlnVary = 0L;
        this.fundAvlVary = 0L;
        this.dailyInAmt = 0L;
        this.dailyOutAmt = 0L;
        this.originalSn = 0L;
        this.opUser = 0L;
        this.opRole = null;
        this.opName = "";
        this.opOrg = 0;
        this.opSite = "";
        this.channel = null;
        this.subsysSn = 0;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
    }

    public int getOccDate() {
        return occDate;
    }

    public void setOccDate(int occDate) {
        this.occDate = occDate;
    }

    public Date getOccTime() {
        return occTime;
    }

    public void setOccTime(Date occTime) {
        this.occTime = occTime;
    }

    public long getCustCode() {
        return custCode;
    }

    public void setCustCode(long custCode) {
        this.custCode = custCode;
    }

    public long getCuacctCode() {
        return cuacctCode;
    }

    public void setCuacctCode(long cuacctCode) {
        this.cuacctCode = cuacctCode;
    }

    public Character getCurrency() {
        return currency;
    }

    public void setCurrency(Character currency) {
        this.currency = currency;
    }

    public short getIntOrg() {
        return intOrg;
    }

    public void setIntOrg(short intOrg) {
        this.intOrg = intOrg;
    }

    public int getBizCode() {
        return bizCode;
    }

    public void setBizCode(int bizCode) {
        this.bizCode = bizCode;
    }

    public Character getFundChangeFlag() {
        return fundChangeFlag;
    }

    public void setFundChangeFlag(Character fundChangeFlag) {
        this.fundChangeFlag = fundChangeFlag;
    }

    public long getFundBln() {
        return fundBln;
    }

    public void setFundBln(long fundBln) {
        this.fundBln = fundBln;
    }

    public long getFundBlnVary() {
        return fundBlnVary;
    }

    public void setFundBlnVary(long fundBlnVary) {
        this.fundBlnVary = fundBlnVary;
    }

    public long getFundAvlVary() {
        return fundAvlVary;
    }

    public void setFundAvlVary(long fundAvlVary) {
        this.fundAvlVary = fundAvlVary;
    }

    public long getDailyInAmt() {
        return dailyInAmt;
    }

    public void setDailyInAmt(long dailyInAmt) {
        this.dailyInAmt = dailyInAmt;
    }

    public long getDailyOutAmt() {
        return dailyOutAmt;
    }

    public void setDailyOutAmt(long dailyOutAmt) {
        this.dailyOutAmt = dailyOutAmt;
    }

    public long getOriginalSn() {
        return originalSn;
    }

    public void setOriginalSn(long originalSn) {
        this.originalSn = originalSn;
    }

    public long getOpUser() {
        return opUser;
    }

    public void setOpUser(long opUser) {
        this.opUser = opUser;
    }

    public Character getOpRole() {
        return opRole;
    }

    public void setOpRole(Character opRole) {
        this.opRole = opRole;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public short getOpOrg() {
        return opOrg;
    }

    public void setOpOrg(short opOrg) {
        this.opOrg = opOrg;
    }

    public String getOpSite() {
        return opSite;
    }

    public void setOpSite(String opSite) {
        this.opSite = opSite;
    }

    public Character getChannel() {
        return channel;
    }

    public void setChannel(Character channel) {
        this.channel = channel;
    }

    public short getSubsysSn() {
        return subsysSn;
    }

    public void setSubsysSn(short subsysSn) {
        this.subsysSn = subsysSn;
    }
}
