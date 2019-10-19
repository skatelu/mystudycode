/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationDataIdOptAsset
 * Author:   ZhengMingjie
 * Date:     2018/8/27
 * Description: 平台接口合约变动OPT_ASSET数据类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;

import java.security.SecureRandom;
import java.util.Date;

public class OperationDataIdOptAsset extends OperationBase {

    private String dataId;
    private long serialNo;
    private int occDate;
    private Date occTime;
    private long custCode;
    private long cuacctCode;
    private short intOrg;
    private String stkbd;
    private String trdacct;
    private String optNum;
    private String stkpbu;
    private Character optSide;
    private int bizCode;
    private Character optChangeFlag;
    private long optBlnVary;
    private long optAvlVary;
    private short subsysSn;


    public OperationDataIdOptAsset() {
        init();
    }

    public OperationDataIdOptAsset(JSONObject json) {
    }

    private void init(){
        this.dataId = "";
        this.serialNo = 0L;
        this.occDate = 0;
        this.occTime = new Date();
        this.custCode = 0L;
        this.cuacctCode = 0L;
        this.intOrg = 0;
        this.stkbd = "";
        this.trdacct = "";
        this.optNum = "";
        this.stkpbu = "";
        this.optSide = null;
        this.bizCode = 0;
        this.optChangeFlag = null;
        this.optBlnVary = 0L;
        this.optAvlVary = 0L;
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

    public short getIntOrg() {
        return intOrg;
    }

    public void setIntOrg(short intOrg) {
        this.intOrg = intOrg;
    }

    public String getStkbd() {
        return stkbd;
    }

    public void setStkbd(String stkbd) {
        this.stkbd = stkbd;
    }

    public String getTrdacct() {
        return trdacct;
    }

    public void setTrdacct(String trdacct) {
        this.trdacct = trdacct;
    }

    public String getOptNum() {
        return optNum;
    }

    public void setOptNum(String optNum) {
        this.optNum = optNum;
    }

    public String getStkpbu() {
        return stkpbu;
    }

    public void setStkpbu(String stkpbu) {
        this.stkpbu = stkpbu;
    }

    public Character getOptSide() {
        return optSide;
    }

    public void setOptSide(Character optSide) {
        this.optSide = optSide;
    }

    public int getBizCode() {
        return bizCode;
    }

    public void setBizCode(int bizCode) {
        this.bizCode = bizCode;
    }

    public Character getOptChangeFlag() {
        return optChangeFlag;
    }

    public void setOptChangeFlag(Character optChangeFlag) {
        this.optChangeFlag = optChangeFlag;
    }

    public long getOptBlnVary() {
        return optBlnVary;
    }

    public void setOptBlnVary(long optBlnVary) {
        this.optBlnVary = optBlnVary;
    }

    public long getOptAvlVary() {
        return optAvlVary;
    }

    public void setOptAvlVary(long optAvlVary) {
        this.optAvlVary = optAvlVary;
    }

    public short getSubsysSn() {
        return subsysSn;
    }

    public void setSubsysSn(short subsysSn) {
        this.subsysSn = subsysSn;
    }
}
