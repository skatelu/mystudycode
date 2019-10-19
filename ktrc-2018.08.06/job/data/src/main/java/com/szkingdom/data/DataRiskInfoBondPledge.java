/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataRiskInfoBondPledge
 * Author:   yinhl
 * Date:     2018-09-21
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import java.sql.Timestamp;
public class DataRiskInfoBondPledge extends DataBase {

    private static final long serialVersionUID = -7126601427522430036L;
    private String indicatorId;
    private String indicatorName;
    private int occDate;
    private int occTime;
    private long preThreshold;
    private Character preThresNo;
    private String preThresName;
    private String preThresColor;
    private long preRiskValue;
    private long threshold;
    private Character thresNo;
    private String thresName;
    private String thresColor;
    private long riskValue;
    private Character stkex;
    private String stkbd;
    private long custCode;
    private String custName;
    private long cuacctCode;
    private Character currency;
    private String trdacct;
    private short intOrg;
    private String stkCode;
    private String stkName;
    private long fiUndueAmt1;
    private long totalBondStd;
    private long fiUndueAmt2;
    private long trustBondAmt;
    private long entryBondQty;
    private long stkexTrustBondQty;
    private long dueBuybkAmt;
    private long fundAvl;
    private Character riskValidFlag;
    private Character noticeSentLevel;
    private String noticeSentLevelName;

    public DataRiskInfoBondPledge(){
        init();
    }

    public DataRiskInfoBondPledge(JSONObject json){
    }


    public void init(){
        this.indicatorId = "";
        this.indicatorName = "";
        this.occDate = 0;
        this.occTime = 0;
        this.preThreshold = 0L;
        this.preThresNo = null;
        this.preThresName = "";
        this.preThresColor = "";
        this.preRiskValue = 0L;
        this.threshold = 0L;
        this.thresNo = null;
        this.thresName = "";
        this.thresColor = "";
        this.riskValue = 0L;
        this.stkex = null;
        this.stkbd = "";
        this.custCode = 0L;
        this.custName = "";
        this.cuacctCode = 0L;
        this.currency = null;
        this.trdacct = "";
        this.intOrg = 0;
        this.stkCode = "";
        this.stkName = "";
        this.fiUndueAmt1 = 0L;
        this.totalBondStd = 0L;
        this.fiUndueAmt2 = 0L;
        this.trustBondAmt = 0L;
        this.entryBondQty = 0L;
        this.stkexTrustBondQty = 0L;
        this.dueBuybkAmt = 0L;
        this.fundAvl = 0L;
        this.riskValidFlag = null;
        this.noticeSentLevel = null;
        this.noticeSentLevelName = "";

    }

    public void setIndicatorId(String indicatorId){
        this.indicatorId = indicatorId;
    }

    public String  getIndicatorId(){
        return this.indicatorId;
    }

    public void setIndicatorName(String indicatorName){
        this.indicatorName = indicatorName;
    }

    public String  getIndicatorName(){
        return this.indicatorName;
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

    public void setPreThreshold(long preThreshold){
        this.preThreshold = preThreshold;
    }

    public long  getPreThreshold(){
        return this.preThreshold;
    }

    public void setPreThresNo(Character preThresNo){
        this.preThresNo = preThresNo;
    }

    public Character  getPreThresNo(){
        return this.preThresNo;
    }

    public void setPreThresName(String preThresName){
        this.preThresName = preThresName;
    }

    public String  getPreThresName(){
        return this.preThresName;
    }

    public void setPreThresColor(String preThresColor){
        this.preThresColor = preThresColor;
    }

    public String  getPreThresColor(){
        return this.preThresColor;
    }

    public void setPreRiskValue(long preRiskValue){
        this.preRiskValue = preRiskValue;
    }

    public long  getPreRiskValue(){
        return this.preRiskValue;
    }

    public void setThreshold(long threshold){
        this.threshold = threshold;
    }

    public long  getThreshold(){
        return this.threshold;
    }

    public void setThresNo(Character thresNo){
        this.thresNo = thresNo;
    }

    public Character  getThresNo(){
        return this.thresNo;
    }

    public void setThresName(String thresName){
        this.thresName = thresName;
    }

    public String  getThresName(){
        return this.thresName;
    }

    public void setThresColor(String thresColor){
        this.thresColor = thresColor;
    }

    public String  getThresColor(){
        return this.thresColor;
    }

    public void setRiskValue(long riskValue){
        this.riskValue = riskValue;
    }

    public long  getRiskValue(){
        return this.riskValue;
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

    public void setCurrency(Character currency){
        this.currency = currency;
    }

    public Character  getCurrency(){
        return this.currency;
    }

    public void setTrdacct(String trdacct){
        this.trdacct = trdacct;
    }

    public String  getTrdacct(){
        return this.trdacct;
    }

    public void setIntOrg(short intOrg){
        this.intOrg = intOrg;
    }

    public short  getIntOrg(){
        return this.intOrg;
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

    public void setFiUndueAmt1(long fiUndueAmt1){
        this.fiUndueAmt1 = fiUndueAmt1;
    }

    public long  getFiUndueAmt1(){
        return this.fiUndueAmt1;
    }

    public void setTotalBondStd(long totalBondStd){
        this.totalBondStd = totalBondStd;
    }

    public long  getTotalBondStd(){
        return this.totalBondStd;
    }

    public void setFiUndueAmt2(long fiUndueAmt2){
        this.fiUndueAmt2 = fiUndueAmt2;
    }

    public long  getFiUndueAmt2(){
        return this.fiUndueAmt2;
    }

    public void setTrustBondAmt(long trustBondAmt){
        this.trustBondAmt = trustBondAmt;
    }

    public long  getTrustBondAmt(){
        return this.trustBondAmt;
    }

    public void setEntryBondQty(long entryBondQty){
        this.entryBondQty = entryBondQty;
    }

    public long  getEntryBondQty(){
        return this.entryBondQty;
    }

    public void setStkexTrustBondQty(long stkexTrustBondQty){
        this.stkexTrustBondQty = stkexTrustBondQty;
    }

    public long  getStkexTrustBondQty(){
        return this.stkexTrustBondQty;
    }

    public void setDueBuybkAmt(long dueBuybkAmt){
        this.dueBuybkAmt = dueBuybkAmt;
    }

    public long  getDueBuybkAmt(){
        return this.dueBuybkAmt;
    }

    public void setFundAvl(long fundAvl){
        this.fundAvl = fundAvl;
    }

    public long  getFundAvl(){
        return this.fundAvl;
    }

    public void setRiskValidFlag(Character riskValidFlag){
        this.riskValidFlag = riskValidFlag;
    }

    public Character  getRiskValidFlag(){
        return this.riskValidFlag;
    }

    public void setNoticeSentLevel(Character noticeSentLevel){
        this.noticeSentLevel = noticeSentLevel;
    }

    public Character  getNoticeSentLevel(){
        return this.noticeSentLevel;
    }

    public void setNoticeSentLevelName(String noticeSentLevelName){
        this.noticeSentLevelName = noticeSentLevelName;
    }

    public String  getNoticeSentLevelName(){
        return this.noticeSentLevelName;
    }

}