/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataIndicatorIdsCfg
 * Author:   yinhl
 * Date:     2018-09-20
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataIndicatorIdsCfg extends DataBase {

    private static final long serialVersionUID = 5021504273665644029L;
    private String indicatorId;
    private Character indicatorIdType;
    private Character thresDirect;
    private Character riskTrend;
    private int riskInterval;
    private int riskChecktime;

    public DataIndicatorIdsCfg(){
        init();
    }

    public DataIndicatorIdsCfg(JSONObject json){
    }

    public void init(){
        this.indicatorId = "";
        this.indicatorIdType = null;
        this.thresDirect = null;
        this.riskTrend = null;
        this.riskInterval = 0;
        this.riskChecktime = 0;
    }

    public void setIndicatorId(String indicatorId){
        this.indicatorId = indicatorId;
    }

    public String  getIndicatorId(){
        return this.indicatorId;
    }

    public void setIndicatorIdType(Character indicatorIdType){
        this.indicatorIdType = indicatorIdType;
    }

    public Character  getIndicatorIdType(){
        return this.indicatorIdType;
    }

    public void setThresDirect(Character thresDirect){
        this.thresDirect = thresDirect;
    }

    public Character  getThresDirect(){
        return this.thresDirect;
    }

    public void setRiskTrend(Character riskTrend){
        this.riskTrend = riskTrend;
    }

    public Character  getRiskTrend(){
        return this.riskTrend;
    }

    public void setRiskInterval(int riskInterval){
        this.riskInterval = riskInterval;
    }

    public int  getRiskInterval(){
        return this.riskInterval;
    }

    public void setRiskChecktime(int riskChecktime){
        this.riskChecktime = riskChecktime;
    }

    public int  getRiskChecktime(){
        return this.riskChecktime;
    }

}