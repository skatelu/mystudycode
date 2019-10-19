/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkRepchInfo
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
public class DataStkRepchInfo extends DataBase {

    private static final long serialVersionUID = 8653182226562184023L;
    private Character stkex;
    private String stkbd;
    private String repchCode;
    private Character repchCls;
    private short repchDays;
    private short repchIntDays;
    private Character repchMfundFlag;
    private Character repchMcertFlag;
    private Character repchFfundFlag;
    private Character repchFcertFlag;
    private long quoteYearRate;
    private long quoteBreakRate;
    private Character quoteAutoBuyFlag;
    private Character quoteTradeFlag;

    public DataStkRepchInfo(){
    }

    public DataStkRepchInfo(JSONObject json){
    }


    public void init(){
        this.stkex = null;
        this.stkbd = "";
        this.repchCode = "";
        this.repchCls = null;
        this.repchDays = 0;
        this.repchIntDays = 0;
        this.repchMfundFlag = null;
        this.repchMcertFlag = null;
        this.repchFfundFlag = null;
        this.repchFcertFlag = null;
        this.quoteYearRate = 0L;
        this.quoteBreakRate = 0L;
        this.quoteAutoBuyFlag = null;
        this.quoteTradeFlag = null;

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

    public void setRepchCode(String repchCode){
        this.repchCode = repchCode;
    }

    public String  getRepchCode(){
        return this.repchCode;
    }

    public void setRepchCls(Character repchCls){
        this.repchCls = repchCls;
    }

    public Character  getRepchCls(){
        return this.repchCls;
    }

    public void setRepchDays(short repchDays){
        this.repchDays = repchDays;
    }

    public short  getRepchDays(){
        return this.repchDays;
    }

    public void setRepchIntDays(short repchIntDays){
        this.repchIntDays = repchIntDays;
    }

    public short  getRepchIntDays(){
        return this.repchIntDays;
    }

    public void setRepchMfundFlag(Character repchMfundFlag){
        this.repchMfundFlag = repchMfundFlag;
    }

    public Character  getRepchMfundFlag(){
        return this.repchMfundFlag;
    }

    public void setRepchMcertFlag(Character repchMcertFlag){
        this.repchMcertFlag = repchMcertFlag;
    }

    public Character  getRepchMcertFlag(){
        return this.repchMcertFlag;
    }

    public void setRepchFfundFlag(Character repchFfundFlag){
        this.repchFfundFlag = repchFfundFlag;
    }

    public Character  getRepchFfundFlag(){
        return this.repchFfundFlag;
    }

    public void setRepchFcertFlag(Character repchFcertFlag){
        this.repchFcertFlag = repchFcertFlag;
    }

    public Character  getRepchFcertFlag(){
        return this.repchFcertFlag;
    }

    public void setQuoteYearRate(long quoteYearRate){
        this.quoteYearRate = quoteYearRate;
    }

    public long  getQuoteYearRate(){
        return this.quoteYearRate;
    }

    public void setQuoteBreakRate(long quoteBreakRate){
        this.quoteBreakRate = quoteBreakRate;
    }

    public long  getQuoteBreakRate(){
        return this.quoteBreakRate;
    }

    public void setQuoteAutoBuyFlag(Character quoteAutoBuyFlag){
        this.quoteAutoBuyFlag = quoteAutoBuyFlag;
    }

    public Character  getQuoteAutoBuyFlag(){
        return this.quoteAutoBuyFlag;
    }

    public void setQuoteTradeFlag(Character quoteTradeFlag){
        this.quoteTradeFlag = quoteTradeFlag;
    }

    public Character  getQuoteTradeFlag(){
        return this.quoteTradeFlag;
    }

}