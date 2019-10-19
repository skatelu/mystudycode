/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskFactors
 * Author:   yinhl
 * Date:     2018-09-06
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataRiskFactors extends DataBase {

    private static final long serialVersionUID = -9077487483651464853L;
    private String factorId;
    private String factorName;
    private String bizAttr;
    private String factorCls;
    private String factorClass;
    private String factorRemark;

    public DataRiskFactors(){
    }

    public DataRiskFactors(JSONObject json){
    }


    public void init(){
        this.factorId = "";
        this.factorName = "";
        this.bizAttr = "";
        this.factorCls = "";
        this.factorClass = "";
        this.factorRemark = "";

    }

    public void setFactorId(String factorId){
        this.factorId = factorId;
    }

    public String  getFactorId(){
        return this.factorId;
    }

    public void setFactorName(String factorName){
        this.factorName = factorName;
    }

    public String  getFactorName(){
        return this.factorName;
    }

    public void setBizAttr(String bizAttr){
        this.bizAttr = bizAttr;
    }

    public String  getBizAttr(){
        return this.bizAttr;
    }

    public void setFactorCls(String factorCls){
        this.factorCls = factorCls;
    }

    public String  getFactorCls(){
        return this.factorCls;
    }

    public void setFactorClass(String factorClass){
        this.factorClass = factorClass;
    }

    public String  getFactorClass(){
        return this.factorClass;
    }

    public void setFactorRemark(String factorRemark){
        this.factorRemark = factorRemark;
    }

    public String  getFactorRemark(){
        return this.factorRemark;
    }

}