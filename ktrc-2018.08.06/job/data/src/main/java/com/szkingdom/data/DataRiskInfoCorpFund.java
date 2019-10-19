/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskInfoCorpFund
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
public class DataRiskInfoCorpFund extends DataBase {

    private static final long serialVersionUID = 1956196821970039945L;
    private String stkbd;
    private String marginCorpAcct;
    private Character currency;
    private Character acctType;
    private long marginUsed;
    private long marginAvl;
    private long marginTotalAmt;
    private long openAmtUseedRate;
    private int occDate;
    private int occTime;

    public DataRiskInfoCorpFund(){
    }

    public DataRiskInfoCorpFund(JSONObject json){
    }


    public void init(){
        this.stkbd = "";
        this.marginCorpAcct = "";
        this.currency = null;
        this.acctType = null;
        this.marginUsed = 0L;
        this.marginAvl = 0L;
        this.marginTotalAmt = 0L;
        this.openAmtUseedRate = 0L;
        this.occDate = 0;
        this.occTime = 0;
    }

    public void setStkbd(String stkbd){
        this.stkbd = stkbd;
    }

    public String  getStkbd(){
        return this.stkbd;
    }

    public void setMarginCorpAcct(String marginCorpAcct){
        this.marginCorpAcct = marginCorpAcct;
    }

    public String  getMarginCorpAcct(){
        return this.marginCorpAcct;
    }

    public void setCurrency(Character currency){
        this.currency = currency;
    }

    public Character  getCurrency(){
        return this.currency;
    }

    public void setAcctType(Character acctType){
        this.acctType = acctType;
    }

    public Character  getAcctType(){
        return this.acctType;
    }

    public void setMarginUsed(long marginUsed){
        this.marginUsed = marginUsed;
    }

    public long  getMarginUsed(){
        return this.marginUsed;
    }

    public void setMarginAvl(long marginAvl){
        this.marginAvl = marginAvl;
    }

    public long  getMarginAvl(){
        return this.marginAvl;
    }

    public void setMarginTotalAmt(long marginTotalAmt){
        this.marginTotalAmt = marginTotalAmt;
    }

    public long  getMarginTotalAmt(){
        return this.marginTotalAmt;
    }

    public void setOpenAmtUseedRate(long openAmtUseedRate){
        this.openAmtUseedRate = openAmtUseedRate;
    }

    public long  getOpenAmtUseedRate(){
        return this.openAmtUseedRate;
    }

    public void setOccDate(int occDate){
        this.occDate = occDate;
    }

    public int  getOccDate(){
        return this.occDate;
    }

    public void setOccTime(int occTime){
        this.occTime = occTime;
    }

    public int  getOccTime(){
        return this.occTime;
    }

}