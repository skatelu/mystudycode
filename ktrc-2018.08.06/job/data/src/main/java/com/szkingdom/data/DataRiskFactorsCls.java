/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskFactorsCls
 * Author:   yinhl
 * Date:     2018-09-19
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import java.sql.Timestamp;
public class DataRiskFactorsCls extends DataBase {

    private static final long serialVersionUID = 8281873227712694349L;
    private String bizAttr;
    private String factorsCls;
    private String factorsClsName;

    public DataRiskFactorsCls(){
    }

    public DataRiskFactorsCls(JSONObject json){
    }


    public void init(){
        this.bizAttr = "";
        this.factorsCls = "";
        this.factorsClsName = "";

    }

    public void setBizAttr(String bizAttr){
        this.bizAttr = bizAttr;
    }

    public String  getBizAttr(){
        return this.bizAttr;
    }

    public void setFactorsCls(String factorsCls){
        this.factorsCls = factorsCls;
    }

    public String  getFactorsCls(){
        return this.factorsCls;
    }

    public void setFactorsClsName(String factorsClsName){
        this.factorsClsName = factorsClsName;
    }

    public String  getFactorsClsName(){
        return this.factorsClsName;
    }

}