/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: DataRiskIndicators
 * Author:   zmh
 * Date:     2018/7/19 10:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szkingdom.data;

import java.io.Serializable;

/**
 * 〈〉
 * @author zmh
 * @create 2018/7/19
 * @since 1.0.0
 */
public class DataRiskIndicators implements Serializable {

    private static final long serialVersionUID = 5539442805261739543L;
    private String indicatorId;
    private String indicatorName;
    private String bizAttr;
    private String indicatorCls;
    private String indicatorClsName;
    private String indicatorFormula;
    private Character indicatorRunFlag;
    private Character isRealtime;
    private String indicatorRemark;

    public DataRiskIndicators() {
        this.indicatorId = "";
        this.indicatorName = "";
        this.bizAttr = "";
        this.indicatorCls = "";
        this.indicatorClsName = "";
        this.indicatorFormula = "";
        this.indicatorRunFlag = '0';
        this.isRealtime = '0';
        this.indicatorRemark = "";
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

    public String getIndicatorFormula() {
        return indicatorFormula;
    }

    public void setIndicatorFormula(String indicatorFormula) {
        this.indicatorFormula = indicatorFormula;
    }

    public Character getIndicatorRunFlag() {
        return indicatorRunFlag;
    }

    public void setIndicatorRunFlag(Character indicatorRunFlag) {
        this.indicatorRunFlag = indicatorRunFlag;
    }

    public Character getIsRealtime() {
        return isRealtime;
    }

    public void setIsRealtime(Character isRealtime) {
        this.isRealtime = isRealtime;
    }

    public String getIndicatorRemark() {
        return indicatorRemark;
    }

    public void setIndicatorRemark(String indicatorRemark) {
        this.indicatorRemark = indicatorRemark;
    }

    @Override
    public String toString() {
        return "DataRiskIndicators{" +
                "indicatorId='" + indicatorId + '\'' +
                ", indicatorName='" + indicatorName + '\'' +
                ", bizAttr='" + bizAttr + '\'' +
                ", indicatorCls='" + indicatorCls + '\'' +
                ", indicatorClsName='" + indicatorClsName + '\'' +
                ", indicatorFormula='" + indicatorFormula + '\'' +
                ", indicatorRunFlag=" + indicatorRunFlag +
                ", isRealtime=" + isRealtime +
                ", indicatorRemark='" + indicatorRemark + '\'' +
                '}';
    }
}
