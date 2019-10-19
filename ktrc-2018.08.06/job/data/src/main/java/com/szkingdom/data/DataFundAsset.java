package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by liujin on 2018/10/8.
 */
public class DataFundAsset  extends DataBase  {
    private static final long serialVersionUID = 1828512670584262529L;
    private int serverId;
    private String orgId;
    private int fundSeq;
    private long fundId;
    private Character moneyType;
    private  long custId;
    private double fundLastBal;
    private double fundBal;
    private double fundAvl;
    private double overDraw;
    private double fundBuy;
    private double fundSale;
    private double fundUncomeBuy;
    private double fundUncomeSale;
    private double fundFrz;
    private double fundUnFrz;
    private double fundTrdFrz;
    private double fundTrdUnFrz;
    private double fundNightFrz;
    private double fundLoan;
    private double creditBal;
    private double creditBuySale;
    private Character fundFlag;
    private double marketValue;
    private double fundStandby;
    private double fundBuySale;
    private double fundBrkBuy;
    private double fundRealBuy;
    private double fundBalPrefrz;
    private double fundCashPro;
    private double fundAvlPreFrz;

    public DataFundAsset(){ init();}
    public DataFundAsset(JSONObject json) {
    }

    private void init(){
        this.serverId = 0;
        this.orgId = "";
        this.fundSeq = 0;
        this.fundId = 0L;
        this.moneyType = null;
        this.custId = 0L;
        this.fundLastBal = 0.0;
        this.fundBal = 0.0;
        this.fundAvl = 0.0;
        this.overDraw = 0.0;
        this.fundBuy = 0.0;
        this.fundSale = 0.0;
        this.fundUncomeBuy = 0.0;
        this.fundUncomeSale = 0.0;
        this.fundFrz = 0.0;
        this.fundUnFrz = 0.0;
        this.fundTrdFrz = 0.0;
        this.fundTrdUnFrz = 0.0;
        this.fundNightFrz = 0.0;
        this.fundLoan = 0.0;
        this.creditBal = 0.0;
        this.creditBuySale = 0.0;
        this.fundFlag = null;
        this.marketValue = 0.0;
        this.fundStandby = 0.0;
        this.fundBuySale = 0.0;
        this.fundBrkBuy = 0.0;
        this.fundRealBuy = 0.0;
        this.fundBalPrefrz = 0.0;
        this.fundCashPro = 0.0;
        this.fundAvlPreFrz = 0.0;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public int getFundSeq() {
        return fundSeq;
    }

    public void setFundSeq(int fundSeq) {
        this.fundSeq = fundSeq;
    }

    public long getFundId() {
        return fundId;
    }

    public void setFundId(long fundId) {
        this.fundId = fundId;
    }

    public Character getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Character moneyType) {
        this.moneyType = moneyType;
    }

    public long getCustId() {
        return custId;
    }

    public void setCustId(long custId) {
        this.custId = custId;
    }

    public double getFundLastBal() {
        return fundLastBal;
    }

    public void setFundLastBal(double fundLastBal) {
        this.fundLastBal = fundLastBal;
    }

    public double getFundBal() {
        return fundBal;
    }

    public void setFundBal(double fundBal) {
        this.fundBal = fundBal;
    }

    public double getFundAvl() {
        return fundAvl;
    }

    public void setFundAvl(double fundAvl) {
        this.fundAvl = fundAvl;
    }

    public double getOverDraw() {
        return overDraw;
    }

    public void setOverDraw(double overDraw) {
        this.overDraw = overDraw;
    }

    public double getFundBuy() {
        return fundBuy;
    }

    public void setFundBuy(double fundBuy) {
        this.fundBuy = fundBuy;
    }

    public double getFundSale() {
        return fundSale;
    }

    public void setFundSale(double fundSale) {
        this.fundSale = fundSale;
    }

    public double getFundUncomeBuy() {
        return fundUncomeBuy;
    }

    public void setFundUncomeBuy(double fundUncomeBuy) {
        this.fundUncomeBuy = fundUncomeBuy;
    }

    public double getFundUncomeSale() {
        return fundUncomeSale;
    }

    public void setFundUncomeSale(double fundUncomeSale) {
        this.fundUncomeSale = fundUncomeSale;
    }

    public double getFundFrz() {
        return fundFrz;
    }

    public void setFundFrz(double fundFrz) {
        this.fundFrz = fundFrz;
    }

    public double getFundUnFrz() {
        return fundUnFrz;
    }

    public void setFundUnFrz(double fundUnFrz) {
        this.fundUnFrz = fundUnFrz;
    }

    public double getFundTrdFrz() {
        return fundTrdFrz;
    }

    public void setFundTrdFrz(double fundTrdFrz) {
        this.fundTrdFrz = fundTrdFrz;
    }

    public double getFundTrdUnFrz() {
        return fundTrdUnFrz;
    }

    public void setFundTrdUnFrz(double fundTrdUnFrz) {
        this.fundTrdUnFrz = fundTrdUnFrz;
    }

    public double getFundNightFrz() {
        return fundNightFrz;
    }

    public void setFundNightFrz(double fundNightFrz) {
        this.fundNightFrz = fundNightFrz;
    }

    public double getFundLoan() {
        return fundLoan;
    }

    public void setFundLoan(double fundLoan) {
        this.fundLoan = fundLoan;
    }

    public double getCreditBal() {
        return creditBal;
    }

    public void setCreditBal(double creditBal) {
        this.creditBal = creditBal;
    }

    public double getCreditBuySale() {
        return creditBuySale;
    }

    public void setCreditBuySale(double creditBuySale) {
        this.creditBuySale = creditBuySale;
    }

    public Character getFundFlag() {
        return fundFlag;
    }

    public void setFundFlag(Character fundFlag) {
        this.fundFlag = fundFlag;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public double getFundStandby() {
        return fundStandby;
    }

    public void setFundStandby(double fundStandby) {
        this.fundStandby = fundStandby;
    }

    public double getFundBuySale() {
        return fundBuySale;
    }

    public void setFundBuySale(double fundBuySale) {
        this.fundBuySale = fundBuySale;
    }

    public double getFundBrkBuy() {
        return fundBrkBuy;
    }

    public void setFundBrkBuy(double fundBrkBuy) {
        this.fundBrkBuy = fundBrkBuy;
    }

    public double getFundRealBuy() {
        return fundRealBuy;
    }

    public void setFundRealBuy(double fundRealBuy) {
        this.fundRealBuy = fundRealBuy;
    }

    public double getFundBalPrefrz() {
        return fundBalPrefrz;
    }

    public void setFundBalPrefrz(double fundBalPrefrz) {
        this.fundBalPrefrz = fundBalPrefrz;
    }

    public double getFundCashPro() {
        return fundCashPro;
    }

    public void setFundCashPro(double fundCashPro) {
        this.fundCashPro = fundCashPro;
    }

    public double getFundAvlPreFrz() {
        return fundAvlPreFrz;
    }

    public void setFundAvlPreFrz(double fundAvlPreFrz) {
        this.fundAvlPreFrz = fundAvlPreFrz;
    }
}
