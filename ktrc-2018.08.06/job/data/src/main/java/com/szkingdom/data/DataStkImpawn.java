/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataStkImpawn
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
public class DataStkImpawn extends DataBase {

    private static final long serialVersionUID = -943091237326247893L;
    private short intOrg;
    private long custCode;
    private long cuacctCode;
    private String trdacct;
    private Character stkex;
    private String stkbd;
    private String stkpbu;
    private String stkCode;
    private Character stkCls;
    private long stkPrebln;
    private long stkBln;
    private long stkAvl;
    private Character custCls;
    private Character cuacctCls;

    public DataStkImpawn(){
        init();
    }

    public DataStkImpawn(JSONObject json){
    }


    public void init(){
        this.intOrg = 0;
        this.custCode = 0L;
        this.cuacctCode = 0L;
        this.trdacct = "";
        this.stkex = null;
        this.stkbd = "";
        this.stkpbu = "";
        this.stkCode = "";
        this.stkCls = null;
        this.stkPrebln = 0L;
        this.stkBln = 0L;
        this.stkAvl = 0L;
        this.custCls = null;
        this.cuacctCls = null;

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

    public void setTrdacct(String trdacct){
        this.trdacct = trdacct;
    }

    public String  getTrdacct(){
        return this.trdacct;
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

    public void setStkPrebln(long stkPrebln){
        this.stkPrebln = stkPrebln;
    }

    public long  getStkPrebln(){
        return this.stkPrebln;
    }

    public void setStkBln(long stkBln){
        this.stkBln = stkBln;
    }

    public long  getStkBln(){
        return this.stkBln;
    }

    public void setStkAvl(long stkAvl){
        this.stkAvl = stkAvl;
    }

    public long  getStkAvl(){
        return this.stkAvl;
    }

    public void setCustCls(Character custCls){
        this.custCls = custCls;
    }

    public Character  getCustCls(){
        return this.custCls;
    }

    public void setCuacctCls(Character cuacctCls){
        this.cuacctCls = cuacctCls;
    }

    public Character  getCuacctCls(){
        return this.cuacctCls;
    }

}