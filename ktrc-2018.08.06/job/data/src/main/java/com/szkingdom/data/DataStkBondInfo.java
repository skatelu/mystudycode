/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkBondInfo
 * Author:   yinhl
 * Date:     2018-09-10
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import java.sql.Timestamp;
public class DataStkBondInfo extends DataBase {

    private static final long serialVersionUID = 4646985474648261317L;
    private Character stkex;
    private String stkbd;
    private String bondCode;
    private long bondCvtrate;
    private int bgnDate;
    private int endDate;
    private int updDate;
    private long bondPara;
    private long intPara;
    private long bondIncometaxRate;
    private Character riskLevel;
    private Character bondSuitable;
    private long bondAdjustPara;
    private Character bondCls;
    private long bondTotalTrust;
    private Character creditLevel;

    public DataStkBondInfo(){
    }

    public DataStkBondInfo(JSONObject json){
    }


    public void init(){
        this.stkex = null;
        this.stkbd = "";
        this.bondCode = "";
        this.bondCvtrate = 0L;
        this.bgnDate = 0;
        this.endDate = 0;
        this.updDate = 0;
        this.bondPara = 0L;
        this.intPara = 0L;
        this.bondIncometaxRate = 0L;
        this.riskLevel = null;
        this.bondSuitable = null;
        this.bondAdjustPara = 0L;
        this.bondCls = null;
        this.bondTotalTrust = 0L;
        this.creditLevel = null;

    }

    public void setStkex(Character stkex){
        this.stkex = stkex;
    }

    public Character  getStkex(){
        return this.stkex;
    }

    public void setStkbd(String stkbd){
        this.stkbd = stkbd;
    }

    public String  getStkbd(){
        return this.stkbd;
    }

    public void setBondCode(String bondCode){
        this.bondCode = bondCode;
    }

    public String  getBondCode(){
        return this.bondCode;
    }

    public void setBondCvtrate(long bondCvtrate){
        this.bondCvtrate = bondCvtrate;
    }

    public long  getBondCvtrate(){
        return this.bondCvtrate;
    }

    public void setBgnDate(int bgnDate){
        this.bgnDate = bgnDate;
    }

    public int  getBgnDate(){
        return this.bgnDate;
    }

    public void setEndDate(int endDate){
        this.endDate = endDate;
    }

    public int  getEndDate(){
        return this.endDate;
    }

    public void setUpdDate(int updDate){
        this.updDate = updDate;
    }

    public int  getUpdDate(){
        return this.updDate;
    }

    public void setBondPara(long bondPara){
        this.bondPara = bondPara;
    }

    public long  getBondPara(){
        return this.bondPara;
    }

    public void setIntPara(long intPara){
        this.intPara = intPara;
    }

    public long  getIntPara(){
        return this.intPara;
    }

    public void setBondIncometaxRate(long bondIncometaxRate){
        this.bondIncometaxRate = bondIncometaxRate;
    }

    public long  getBondIncometaxRate(){
        return this.bondIncometaxRate;
    }

    public void setRiskLevel(Character riskLevel){
        this.riskLevel = riskLevel;
    }

    public Character  getRiskLevel(){
        return this.riskLevel;
    }

    public void setBondSuitable(Character bondSuitable){
        this.bondSuitable = bondSuitable;
    }

    public Character  getBondSuitable(){
        return this.bondSuitable;
    }

    public void setBondAdjustPara(long bondAdjustPara){
        this.bondAdjustPara = bondAdjustPara;
    }

    public long  getBondAdjustPara(){
        return this.bondAdjustPara;
    }

    public void setBondCls(Character bondCls){
        this.bondCls = bondCls;
    }

    public Character  getBondCls(){
        return this.bondCls;
    }

    public void setBondTotalTrust(long bondTotalTrust){
        this.bondTotalTrust = bondTotalTrust;
    }

    public long  getBondTotalTrust(){
        return this.bondTotalTrust;
    }

    public void setCreditLevel(Character creditLevel){
        this.creditLevel = creditLevel;
    }

    public Character  getCreditLevel(){
        return this.creditLevel;
    }

}