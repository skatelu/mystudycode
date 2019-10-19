/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskFactorSources
 * Author:   yinhl
 * Date:     2018-09-06
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import java.sql.Timestamp;
public class DataRiskFactorSources extends DataBase {

    private static final long serialVersionUID = 4415636691635393010L;
    private String factorId;
    private short stkBiz;
    private short stkBizAction;
    private String dataId;

    public DataRiskFactorSources(){
    }

    public DataRiskFactorSources(JSONObject json){
    }


    public void init(){
        this.factorId = "";
        this.dataId = "";

    }

    public void setFactorId(String factorId){
        this.factorId = factorId;
    }

    public String  getFactorId(){
        return this.factorId;
    }

    public void setStkBiz(short stkBiz){
        this.stkBiz = stkBiz;
    }

    public short  getStkBiz(){
        return this.stkBiz;
    }

    public void setStkBizAction(short stkBizAction){
        this.stkBizAction = stkBizAction;
    }

    public short  getStkBizAction(){
        return this.stkBizAction;
    }

    public void setDataId(String dataId){
        this.dataId = dataId;
    }

    public String  getDataId(){
        return this.dataId;
    }

}