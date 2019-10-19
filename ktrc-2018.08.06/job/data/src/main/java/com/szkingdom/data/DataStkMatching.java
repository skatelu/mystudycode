/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkMatching
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
public class DataStkMatching extends DataBase {

    private static final long serialVersionUID = 5583514215584999529L;
    private int trdDate;
    private String matchedTime;
    private int orderDate;
    private String orderId;
    private Character orderType;
    private short intOrg;
    private long custCode;
    private long cuacctCode;
    private Character currency;
    private String stkbd;
    private String stkpbu;
    private String firmid;
    private String trdacct;
    private short stkBiz;
    private short stkBizAction;
    private String stkCode;
    private String stkName;
    private Character stkCls;
    private Character isWithdraw;
    private Character matchedType;
    private String matchedSn;
    private long matchedPrice;
    private long matchedQty;

    public DataStkMatching(){
        init();
    }

    public DataStkMatching(JSONObject json){
    }


    public void init(){
        this.trdDate = 0;
        this.matchedTime = "";
        this.orderDate = 0;
        this.orderId = "";
        this.orderType = null;
        this.intOrg = 0;
        this.custCode = 0L;
        this.cuacctCode = 0L;
        this.currency = null;
        this.stkbd = "";
        this.stkpbu = "";
        this.firmid = "";
        this.trdacct = "";
        this.stkBiz = 0;
        this.stkBizAction = 0;
        this.stkCode = "";
        this.stkName = "";
        this.stkCls = null;
        this.isWithdraw = null;
        this.matchedType = null;
        this.matchedSn = "";
        this.matchedPrice = 0L;
        this.matchedQty = 0L;

    }

    public void setTrdDate(int trdDate){
        this.trdDate = trdDate;
    }

    public int  getTrdDate(){
        return this.trdDate;
    }

    public void setMatchedTime(String matchedTime){
        this.matchedTime = matchedTime;
    }

    public String  getMatchedTime(){
        return this.matchedTime;
    }

    public void setOrderDate(int orderDate){
        this.orderDate = orderDate;
    }

    public int  getOrderDate(){
        return this.orderDate;
    }

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public String  getOrderId(){
        return this.orderId;
    }

    public void setOrderType(Character orderType){
        this.orderType = orderType;
    }

    public Character  getOrderType(){
        return this.orderType;
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

    public void setCuacctCode(long cuacctCode){
        this.cuacctCode = cuacctCode;
    }

    public long  getCuacctCode(){
        return this.cuacctCode;
    }

    public void setCurrency(Character currency){
        this.currency = currency;
    }

    public Character  getCurrency(){
        return this.currency;
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

    public void setFirmid(String firmid){
        this.firmid = firmid;
    }

    public String  getFirmid(){
        return this.firmid;
    }

    public void setTrdacct(String trdacct){
        this.trdacct = trdacct;
    }

    public String  getTrdacct(){
        return this.trdacct;
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

    public void setStkCode(String stkCode){
        this.stkCode = stkCode;
    }

    public String  getStkCode(){
        return this.stkCode;
    }

    public void setStkName(String stkName){
        this.stkName = stkName;
    }

    public String  getStkName(){
        return this.stkName;
    }

    public void setStkCls(Character stkCls){
        this.stkCls = stkCls;
    }

    public Character  getStkCls(){
        return this.stkCls;
    }

    public void setIsWithdraw(Character isWithdraw){
        this.isWithdraw = isWithdraw;
    }

    public Character  getIsWithdraw(){
        return this.isWithdraw;
    }

    public void setMatchedType(Character matchedType){
        this.matchedType = matchedType;
    }

    public Character  getMatchedType(){
        return this.matchedType;
    }

    public void setMatchedSn(String matchedSn){
        this.matchedSn = matchedSn;
    }

    public String  getMatchedSn(){
        return this.matchedSn;
    }

    public void setMatchedPrice(long matchedPrice){
        this.matchedPrice = matchedPrice;
    }

    public long  getMatchedPrice(){
        return this.matchedPrice;
    }

    public void setMatchedQty(long matchedQty){
        this.matchedQty = matchedQty;
    }

    public long  getMatchedQty(){
        return this.matchedQty;
    }

}