/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkRepchContract
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
public class DataStkRepchContract extends DataBase {

    private static final long serialVersionUID = -8944399633090606887L;
    private int trdDate;
    private Character stkex;
    private String stkbd;
    private String stkpbu;
    private short stkBiz;
    private short stkBizAction;
    private short intOrg;
    private long custCode;
    private String custName;
    private long cuacctCode;
    private String trdacct;
    private String matchId;
    private long matchQty;
    private long matchPrice;
    private long matchAmt;
    private Character currency;
    private String stkCode;
    private Character stkCls;
    private Character stkFlag;
    private int repchDate;
    private long repchAmt;
    private Character repchStatus;
    private int matchTime;
    private long lastRate;
    private long callRate;
    private Character delayFlag;
    private int autoBuyDate;
    private short actualUsedDays;

    public DataStkRepchContract(){
        init();
    }

    public DataStkRepchContract(JSONObject json){
    }


    public void init(){
        this.trdDate = 0;
        this.stkex = null;
        this.stkbd = "";
        this.stkpbu = "";
        this.stkBiz = 0;
        this.stkBizAction = 0;
        this.intOrg = 0;
        this.custCode = 0L;
        this.custName = "";
        this.cuacctCode = 0L;
        this.trdacct = "";
        this.matchId = "";
        this.matchQty = 0L;
        this.matchPrice = 0L;
        this.matchAmt = 0L;
        this.currency = null;
        this.stkCode = "";
        this.stkCls = null;
        this.stkFlag = null;
        this.repchDate = 0;
        this.repchAmt = 0L;
        this.repchStatus = null;
        this.matchTime = 0;
        this.lastRate = 0L;
        this.callRate = 0L;
        this.delayFlag = null;
        this.autoBuyDate = 0;
        this.actualUsedDays = 0;

    }

    public void setTrdDate(int trdDate){
        this.trdDate = trdDate;
    }

    public int  getTrdDate(){
        return this.trdDate;
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

    public void setStkpbu(String stkpbu){
        this.stkpbu = stkpbu;
    }

    public String  getStkpbu(){
        return this.stkpbu;
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

    public void setIntOrg(short intOrg){
        this.intOrg = intOrg;
    }

    public short  getIntOrg(){
        return this.intOrg;
    }

    public void setCustCode(long custCode){
        this.custCode = custCode;
    }

    public long  getCustCode(){
        return this.custCode;
    }

    public void setCustName(String custName){
        this.custName = custName;
    }

    public String  getCustName(){
        return this.custName;
    }

    public void setCuacctCode(long cuacctCode){
        this.cuacctCode = cuacctCode;
    }

    public long  getCuacctCode(){
        return this.cuacctCode;
    }

    public void setTrdacct(String trdacct){
        this.trdacct = trdacct;
    }

    public String  getTrdacct(){
        return this.trdacct;
    }

    public void setMatchId(String matchId){
        this.matchId = matchId;
    }

    public String  getMatchId(){
        return this.matchId;
    }

    public void setMatchQty(long matchQty){
        this.matchQty = matchQty;
    }

    public long  getMatchQty(){
        return this.matchQty;
    }

    public void setMatchPrice(long matchPrice){
        this.matchPrice = matchPrice;
    }

    public long  getMatchPrice(){
        return this.matchPrice;
    }

    public void setMatchAmt(long matchAmt){
        this.matchAmt = matchAmt;
    }

    public long  getMatchAmt(){
        return this.matchAmt;
    }

    public void setCurrency(Character currency){
        this.currency = currency;
    }

    public Character  getCurrency(){
        return this.currency;
    }

    public void setStkCode(String stkCode){
        this.stkCode = stkCode;
    }

    public String  getStkCode(){
        return this.stkCode;
    }

    public void setStkCls(Character stkCls){
        this.stkCls = stkCls;
    }

    public Character  getStkCls(){
        return this.stkCls;
    }

    public void setStkFlag(Character stkFlag){
        this.stkFlag = stkFlag;
    }

    public Character  getStkFlag(){
        return this.stkFlag;
    }

    public void setRepchDate(int repchDate){
        this.repchDate = repchDate;
    }

    public int  getRepchDate(){
        return this.repchDate;
    }

    public void setRepchAmt(long repchAmt){
        this.repchAmt = repchAmt;
    }

    public long  getRepchAmt(){
        return this.repchAmt;
    }

    public void setRepchStatus(Character repchStatus){
        this.repchStatus = repchStatus;
    }

    public Character  getRepchStatus(){
        return this.repchStatus;
    }

    public void setMatchTime(int matchTime){
        this.matchTime = matchTime;
    }

    public int  getMatchTime(){
        return this.matchTime;
    }

    public void setLastRate(long lastRate){
        this.lastRate = lastRate;
    }

    public long  getLastRate(){
        return this.lastRate;
    }

    public void setCallRate(long callRate){
        this.callRate = callRate;
    }

    public long  getCallRate(){
        return this.callRate;
    }

    public void setDelayFlag(Character delayFlag){
        this.delayFlag = delayFlag;
    }

    public Character  getDelayFlag(){
        return this.delayFlag;
    }

    public void setAutoBuyDate(int autoBuyDate){
        this.autoBuyDate = autoBuyDate;
    }

    public int  getAutoBuyDate(){
        return this.autoBuyDate;
    }

    public void setActualUsedDays(short actualUsedDays){
        this.actualUsedDays = actualUsedDays;
    }

    public short  getActualUsedDays(){
        return this.actualUsedDays;
    }

}