/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: DataRiskIndicatorOverview
 * Author:   zmh
 * Date:     2018/7/17 19:06
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.IntegerCodec;

import java.io.Serializable;

/**
 * 〈〉
 * @author zmh
 * @create 2018/7/17
 * @since 1.0.0
 */
public class DataRiskIndicatorOverview implements Serializable {

    private static final long serialVersionUID = 5539442805261739543L;
    private String indicatorId;
    private String indicatorName;
    private String bizAttr;
    private String indicatorCls;
    private String indicatorClsName;
    private int occDate;
    private int occTime;
    private long riskTriggerQty;
    private long toBeNoticedQty;
    private Character riskTriggerFlag;
    private Character toBeNoticedFlag;

    public DataRiskIndicatorOverview(){
        if(this.indicatorId == null){
            this.indicatorId = "";
        }
        if(this.indicatorName == null){
            this.indicatorName = "";
        }
        if(this.bizAttr == null){
            this.bizAttr = "";
        }
        if(this.indicatorCls == null){
            this.indicatorCls = "";
        }
        if(this.indicatorClsName == null){
            this.indicatorClsName = "";
        }
        if(this.riskTriggerFlag == null){
            this.riskTriggerFlag ='0';
        }
        if(this.toBeNoticedFlag == null){
            this.toBeNoticedFlag = '0';
        }
    }

    public boolean updateJsonDataToChart(JSONObject object, boolean setUniqueIndex) {
        if (setUniqueIndex) {
            if(object.getString("indicatorId") != null){
                this.indicatorId = object.getString("indicatorId");
            }
            return true;
        }
        else {
            if(object.getString("indicatorId") != null){
                setIndicatorId(object.getString("indicatorId"));
            }
            if(object.getString("indicatorName") != null){
                setIndicatorName(object.getString("indicatorName"));
            }
            if(object.getString("bizAttr") != null){
                setBizAttr(object.getString("bizAttr"));
            }
            if(object.getString("indicatorCls") != null){
                setIndicatorCls(object.getString("indicatorCls"));
            }
            if(object.getString("indicatorClsName") != null){
                setIndicatorClsName(object.getString("indicatorClsName"));
            }
            if(object.getString("occDate") != null){
                setOccDate(Integer.valueOf(object.getString("occDate")));
            }
            if(object.getString("occTime") != null){
                setOccTime(Integer.valueOf(object.getString("occTime")));
            }
            if(object.getString("riskTriggerQty") != null){
                setRiskTriggerQty(Long.valueOf(object.getString("riskTriggerQty")));
            }
            if(object.getString("toBeNoticedQty") != null){
               setToBeNoticedQty(Long.valueOf(object.getString("toBeNoticedQty")));
            }
            if(object.getString("riskTriggerFlag") != null){
                setRiskTriggerFlag(object.getString("riskTriggerFlag").charAt(0));
            }
            if(object.getString("toBeNoticedFlag") != null){
                setToBeNoticedFlag(object.getString("toBeNoticedFlag").charAt(0));
            }
            return true;
        }
    }

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getBizAttr() {
        return bizAttr;
    }

    public void setBizAttr(String bizAttr) {
        this.bizAttr = bizAttr;
    }

    public String getIndicatorCls() {
        return indicatorCls;
    }

    public void setIndicatorCls(String indicatorCls) {
        this.indicatorCls = indicatorCls;
    }

    public String getIndicatorClsName() {
        return indicatorClsName;
    }

    public void setIndicatorClsName(String indicatorClsName) {
        this.indicatorClsName = indicatorClsName;
    }

    public int getOccDate() {
        return occDate;
    }

    public void setOccDate(int occDate) {
        this.occDate = occDate;
    }

    public int getOccTime() {
        return occTime;
    }

    public void setOccTime(int occTime) {
        this.occTime = occTime;
    }

    public long getRiskTriggerQty() {
        return riskTriggerQty;
    }

    public void setRiskTriggerQty(long riskTriggerQty) {
        this.riskTriggerQty = riskTriggerQty;
    }

    public long getToBeNoticedQty() {
        return toBeNoticedQty;
    }

    public void setToBeNoticedQty(long toBeNoticedQty) {
        this.toBeNoticedQty = toBeNoticedQty;
    }

    public Character getRiskTriggerFlag() {
        return riskTriggerFlag;
    }

    public void setRiskTriggerFlag(Character riskTriggerFlag) {
        this.riskTriggerFlag = riskTriggerFlag;
    }

    public Character getToBeNoticedFlag() {
        return toBeNoticedFlag;
    }

    public void setToBeNoticedFlag(Character toBeNoticedFlag) {
        this.toBeNoticedFlag = toBeNoticedFlag;
    }

    @Override
    public String toString() {
        return "DataRiskIndicatorOverview{" +
                "indicatorId='" + indicatorId + '\'' +
                ", indicatorName='" + indicatorName + '\'' +
                ", bizAttr='" + bizAttr + '\'' +
                ", indicatorCls='" + indicatorCls + '\'' +
                ", indicatorClsName='" + indicatorClsName + '\'' +
                ", occDate=" + occDate +
                ", occTime=" + occTime +
                ", riskTriggerQty=" + riskTriggerQty +
                ", toBeNoticedQty=" + toBeNoticedQty +
                ", riskTriggerFlag=" + riskTriggerFlag +
                ", toBeNoticedFlag=" + toBeNoticedFlag +
                '}';
    }
}
