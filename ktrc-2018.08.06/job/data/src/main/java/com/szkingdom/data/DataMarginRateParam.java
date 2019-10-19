/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataMarginRateParam
 * Author:   yinhl
 * Date:     2018-08-10
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataMarginRateParam extends DataBase {

    private static final long serialVersionUID = 4858727239804678733L;
    private int paramNo;
    private String paramName;
    private Character marginMode;
    private long floatRate;
    private long marginRatio1;
    private long marginRatio2;
    private long secuMargin;

    private int paramNoRlt;
    private Character marginModeRlt;
    private long floatRateRlt;
    private long marginRatio1Rlt;
    private long marginRatio2Rlt;
    private long secuMarginRlt;

    private long extraMargin;
    private Character strategyTemplate;
    private Character marginStrategy;

    public DataMarginRateParam(){
        init();
    }

    public DataMarginRateParam(JSONObject json){
    }

    public void init() {
        paramNo = 0;
        paramName = "";
        marginMode = null;
        floatRate = 0L;
        marginRatio1 = 0L;
        marginRatio2 = 0L;
        secuMargin = 0L;

        paramNoRlt = 0;
        marginModeRlt = null;
        floatRateRlt = 0L;
        marginRatio1Rlt = 0L;
        marginRatio2Rlt = 0L;
        secuMarginRlt = 0L;

        extraMargin = 0L;
        strategyTemplate = null;
        marginStrategy = null;
    }

    public int getParamNo() {
        return paramNo;
    }

    public void setParamNo(int paramNo) {
        this.paramNo = paramNo;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Character getMarginMode() {
        return marginMode;
    }

    public void setMarginMode(Character marginMode) {
        this.marginMode = marginMode;
    }

    public long getFloatRate() {
        return floatRate;
    }

    public void setFloatRate(long floatRate) {
        this.floatRate = floatRate;
    }

    public long getMarginRatio1() {
        return marginRatio1;
    }

    public void setMarginRatio1(long marginRatio1) {
        this.marginRatio1 = marginRatio1;
    }

    public long getMarginRatio2() {
        return marginRatio2;
    }

    public void setMarginRatio2(long marginRatio2) {
        this.marginRatio2 = marginRatio2;
    }

    public long getSecuMargin() {
        return secuMargin;
    }

    public void setSecuMargin(long secuMargin) {
        this.secuMargin = secuMargin;
    }

    public int getParamNoRlt() {
        return paramNoRlt;
    }

    public void setParamNoRlt(int paramNoRlt) {
        this.paramNoRlt = paramNoRlt;
    }

    public Character getMarginModeRlt() {
        return marginModeRlt;
    }

    public void setMarginModeRlt(Character marginModeRlt) {
        this.marginModeRlt = marginModeRlt;
    }

    public long getFloatRateRlt() {
        return floatRateRlt;
    }

    public void setFloatRateRlt(long floatRateRlt) {
        this.floatRateRlt = floatRateRlt;
    }

    public long getMarginRatio1Rlt() {
        return marginRatio1Rlt;
    }

    public void setMarginRatio1Rlt(long marginRatio1Rlt) {
        this.marginRatio1Rlt = marginRatio1Rlt;
    }

    public long getMarginRatio2Rlt() {
        return marginRatio2Rlt;
    }

    public void setMarginRatio2Rlt(long marginRatio2Rlt) {
        this.marginRatio2Rlt = marginRatio2Rlt;
    }

    public long getSecuMarginRlt() {
        return secuMarginRlt;
    }

    public void setSecuMarginRlt(long secuMarginRlt) {
        this.secuMarginRlt = secuMarginRlt;
    }

    public long getExtraMargin() {
        return extraMargin;
    }

    public void setExtraMargin(long extraMargin) {
        this.extraMargin = extraMargin;
    }

    public Character getStrategyTemplate() {
        return strategyTemplate;
    }

    public void setStrategyTemplate(Character strategyTemplate) {
        this.strategyTemplate = strategyTemplate;
    }

    public Character getMarginStrategy() {
        return marginStrategy;
    }

    public void setMarginStrategy(Character marginStrategy) {
        this.marginStrategy = marginStrategy;
    }

    @Override
    public String toString() {
        return "DataMarginRateParam{" +
                "paramNo=" + paramNo +
                ", paramName='" + paramName + '\'' +
                ", marginMode=" + marginMode +
                ", floatRate=" + floatRate +
                ", marginRatio1=" + marginRatio1 +
                ", marginRatio2=" + marginRatio2 +
                ", secuMargin=" + secuMargin +
                ", paramNoRlt=" + paramNoRlt +
                ", marginModeRlt=" + marginModeRlt +
                ", floatRateRlt=" + floatRateRlt +
                ", marginRatio1Rlt=" + marginRatio1Rlt +
                ", marginRatio2Rlt=" + marginRatio2Rlt +
                ", secuMarginRlt=" + secuMarginRlt +
                ", extraMargin=" + extraMargin +
                ", strategyTemplate=" + strategyTemplate +
                ", marginStrategy=" + marginStrategy +
                '}';
    }
}