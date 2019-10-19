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
//TODO
public class OperationDataIdFundAsset extends OperationBase {

    private String dataId;
    private long fundId;
    private  long custId;
    private double fundAvl;
    private Character moneyType;


    public OperationDataIdFundAsset() {
        init();
    }

    public OperationDataIdFundAsset(JSONObject json) {
    }

    private void init() {
        this.dataId = "";
        this.fundId = 0L;
        this.custId = 0L;
        this.fundAvl = 0.0;
        this.moneyType = null;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
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

    public double getFundAvl() {
        return fundAvl;
    }

    public void setFundAvl(double fundAvl) {
        this.fundAvl = fundAvl;
    }

    public Character getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Character moneyType) {
        this.moneyType = moneyType;
    }
}

