/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkOrder
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
public class DataStkOrder extends DataBase {

    private static final long serialVersionUID = 3688320469362113136L;
    private int trdDate;
    private int orderDate;
    private Date orderTime;
    private String orderId;
    private Character orderType;
    private Character orderStatus;
    private short intOrg;
    private long custCode;
    private String custName;
    private Character custType;
    private long cuacctCode;
    private Character currency;
    private String stkbd;
    private String stkpbu;
    private String trdacct;
    private short stkBiz;
    private short stkBizAction;
    private String stkCode;
    private String stkName;
    private Character stkCls;
    private long orderPrice;
    private long orderQty;
    private long orderFrzAmt;
    private Character isWithdraw;
    private String rawOrderId;
    private String gpzysno;
    private long itemno;

    public DataStkOrder(){
        init();
    }

    public DataStkOrder(JSONObject json){
    }


    public void init(){
        this.trdDate = 0;
        this.orderDate = 0;
        this.orderTime = null;
        this.orderId = "";
        this.orderType = null;
        this.orderStatus = null;
        this.intOrg = 0;
        this.custCode = 0L;
        this.custName = "";
        this.custType = null;
        this.cuacctCode = 0L;
        this.currency = null;
        this.stkbd = "";
        this.stkpbu = "";
        this.trdacct = "";
        this.stkBiz = 0;
        this.stkBizAction = 0;
        this.stkCode = "";
        this.stkName = "";
        this.stkCls = null;
        this.orderPrice = 0L;
        this.orderQty = 0L;
        this.orderFrzAmt = 0L;
        this.isWithdraw = null;
        this.rawOrderId = "";
        this.gpzysno = "";
        this.itemno = 0L;

    }

    public void setTrdDate(int trdDate){
        this.trdDate = trdDate;
    }

    public int  getTrdDate(){
        return this.trdDate;
    }

    public void setOrderDate(int orderDate){
        this.orderDate = orderDate;
    }

    public int  getOrderDate(){
        return this.orderDate;
    }

    public void setOrderTime(Date orderTime){
        this.orderTime = orderTime;
    }

    public Date  getOrderTime(){
        return this.orderTime;
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

    public void setOrderStatus(Character orderStatus){
        this.orderStatus = orderStatus;
    }

    public Character  getOrderStatus(){
        return this.orderStatus;
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

    public void setCustType(Character custType){
        this.custType = custType;
    }

    public Character  getCustType(){
        return this.custType;
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

    public void setOrderPrice(long orderPrice){
        this.orderPrice = orderPrice;
    }

    public long  getOrderPrice(){
        return this.orderPrice;
    }

    public void setOrderQty(long orderQty){
        this.orderQty = orderQty;
    }

    public long  getOrderQty(){
        return this.orderQty;
    }

    public void setOrderFrzAmt(long orderFrzAmt){
        this.orderFrzAmt = orderFrzAmt;
    }

    public long  getOrderFrzAmt(){
        return this.orderFrzAmt;
    }

    public void setIsWithdraw(Character isWithdraw){
        this.isWithdraw = isWithdraw;
    }

    public Character  getIsWithdraw(){
        return this.isWithdraw;
    }

    public void setRawOrderId(String rawOrderId){
        this.rawOrderId = rawOrderId;
    }

    public String  getRawOrderId(){
        return this.rawOrderId;
    }

    public void setGpzysno(String gpzysno){
        this.gpzysno = gpzysno;
    }

    public String  getGpzysno(){
        return this.gpzysno;
    }

    public void setItemno(long itemno){
        this.itemno = itemno;
    }

    public long  getItemno(){
        return this.itemno;
    }

}