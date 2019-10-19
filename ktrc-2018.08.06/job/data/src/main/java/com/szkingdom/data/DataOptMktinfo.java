/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DataOptMktinfo
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

public class DataOptMktinfo extends DataBase {

	private static final long serialVersionUID = 3274457553294081910L;
	private Character stkex;
	private String stkbd;
	private String optNum;
	private String optCode;
	private int trdDate;
	private long totalAmt;
	private long totalVolume;
	private long preClosePx;
	private long preSettPrice;
	private long optOpenPrice;
	private long optCurrPrice;
	private long optHighPrice;
	private long optLowPrice;
	private long optTrdPrice;
	private long leavesQty;
	private long optAuctPrice;
	private long optAuctQty;
	private long optSettPrice;
	private long buyPrice1;
	private long buyVolume1;
	private long sellPrice1;
	private long sellVolume1;
	private long buyPrice2;
	private long buyVolume2;
	private long sellPrice2;
	private long sellVolume2;
	private long buyPrice3;
	private long buyVolume3;
	private long sellPrice3;
	private long sellVolume3;
	private long buyPrice4;
	private long buyVolume4;
	private long sellPrice4;
	private long sellVolume4;
	private long buyPrice5;
	private long buyVolume5;
	private long sellPrice5;
	private long sellVolume5;
	private Character rltPhase;
	private Character rltFlag;
	private Character rltOpenFlag;
	private String latestEnqryTime;

	public DataOptMktinfo() {
		init();
	}

	public DataOptMktinfo(JSONObject json) {
	}

	public void init() {
		this.stkex = null;
		this.stkbd = "";
		this.optNum = "";
		this.optCode = "";
		this.trdDate = 0;
		this.totalAmt = 0L;
		this.totalVolume = 0L;
		this.preClosePx = 0L;
		this.preSettPrice = 0L;
		this.optOpenPrice = 0L;
		this.optCurrPrice = 0L;
		this.optHighPrice = 0L;
		this.optLowPrice = 0L;
		this.optTrdPrice = 0L;
		this.leavesQty = 0L;
		this.optAuctPrice = 0L;
		this.optAuctQty = 0L;
		this.optSettPrice = 0L;
		this.buyPrice1 = 0L;
		this.buyVolume1 = 0L;
		this.sellPrice1 = 0L;
		this.sellVolume1 = 0L;
		this.buyPrice2 = 0L;
		this.buyVolume2 = 0L;
		this.sellPrice2 = 0L;
		this.sellVolume2 = 0L;
		this.buyPrice3 = 0L;
		this.buyVolume3 = 0L;
		this.sellPrice3 = 0L;
		this.sellVolume3 = 0L;
		this.buyPrice4 = 0L;
		this.buyVolume4 = 0L;
		this.sellPrice4 = 0L;
		this.sellVolume4 = 0L;
		this.buyPrice5 = 0L;
		this.buyVolume5 = 0L;
		this.sellPrice5 = 0L;
		this.sellVolume5 = 0L;
		this.rltPhase = null;
		this.rltFlag = null;
		this.rltOpenFlag = null;
		this.latestEnqryTime = "";
	}

	public void setStkex(Character stkex) {
		this.stkex = stkex;
	}

	public Character getStkex() {
		return this.stkex;
	}

	public void setStkbd(String stkbd) {
		this.stkbd = stkbd;
	}

	public String getStkbd() {
		return this.stkbd;
	}

	public void setOptNum(String optNum) {
		this.optNum = optNum;
	}

	public String getOptNum() {
		return this.optNum;
	}

	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}

	public String getOptCode() {
		return this.optCode;
	}

	public void setTrdDate(int trdDate) {
		this.trdDate = trdDate;
	}

	public int getTrdDate() {
		return this.trdDate;
	}

	public void setTotalAmt(long totalAmt) {
		this.totalAmt = totalAmt;
	}

	public long getTotalAmt() {
		return this.totalAmt;
	}

	public void setTotalVolume(long totalVolume) {
		this.totalVolume = totalVolume;
	}

	public long getTotalVolume() {
		return this.totalVolume;
	}

	public void setPreClosePx(long preClosePx) {
		this.preClosePx = preClosePx;
	}

	public long getPreClosePx() {
		return this.preClosePx;
	}

	public void setPreSettPrice(long preSettPrice) {
		this.preSettPrice = preSettPrice;
	}

	public long getPreSettPrice() {
		return this.preSettPrice;
	}

	public void setOptOpenPrice(long optOpenPrice) {
		this.optOpenPrice = optOpenPrice;
	}

	public long getOptOpenPrice() {
		return this.optOpenPrice;
	}

	public void setOptCurrPrice(long optCurrPrice) {
		this.optCurrPrice = optCurrPrice;
	}

	public long getOptCurrPrice() {
		return this.optCurrPrice;
	}

	public void setOptHighPrice(long optHighPrice) {
		this.optHighPrice = optHighPrice;
	}

	public long getOptHighPrice() {
		return this.optHighPrice;
	}

	public void setOptLowPrice(long optLowPrice) {
		this.optLowPrice = optLowPrice;
	}

	public long getOptLowPrice() {
		return this.optLowPrice;
	}

	public void setOptTrdPrice(long optTrdPrice) {
		this.optTrdPrice = optTrdPrice;
	}

	public long getOptTrdPrice() {
		return this.optTrdPrice;
	}

	public void setLeavesQty(long leavesQty) {
		this.leavesQty = leavesQty;
	}

	public long getLeavesQty() {
		return this.leavesQty;
	}

	public void setOptAuctPrice(long optAuctPrice) {
		this.optAuctPrice = optAuctPrice;
	}

	public long getOptAuctPrice() {
		return this.optAuctPrice;
	}

	public void setOptAuctQty(long optAuctQty) {
		this.optAuctQty = optAuctQty;
	}

	public long getOptAuctQty() {
		return this.optAuctQty;
	}

	public void setOptSettPrice(long optSettPrice) {
		this.optSettPrice = optSettPrice;
	}

	public long getOptSettPrice() {
		return this.optSettPrice;
	}

	public void setBuyPrice1(long buyPrice1) {
		this.buyPrice1 = buyPrice1;
	}

	public long getBuyPrice1() {
		return this.buyPrice1;
	}

	public void setBuyVolume1(long buyVolume1) {
		this.buyVolume1 = buyVolume1;
	}

	public long getBuyVolume1() {
		return this.buyVolume1;
	}

	public void setSellPrice1(long sellPrice1) {
		this.sellPrice1 = sellPrice1;
	}

	public long getSellPrice1() {
		return this.sellPrice1;
	}

	public void setSellVolume1(long sellVolume1) {
		this.sellVolume1 = sellVolume1;
	}

	public long getSellVolume1() {
		return this.sellVolume1;
	}

	public void setBuyPrice2(long buyPrice2) {
		this.buyPrice2 = buyPrice2;
	}

	public long getBuyPrice2() {
		return this.buyPrice2;
	}

	public void setBuyVolume2(long buyVolume2) {
		this.buyVolume2 = buyVolume2;
	}

	public long getBuyVolume2() {
		return this.buyVolume2;
	}

	public void setSellPrice2(long sellPrice2) {
		this.sellPrice2 = sellPrice2;
	}

	public long getSellPrice2() {
		return this.sellPrice2;
	}

	public void setSellVolume2(long sellVolume2) {
		this.sellVolume2 = sellVolume2;
	}

	public long getSellVolume2() {
		return this.sellVolume2;
	}

	public void setBuyPrice3(long buyPrice3) {
		this.buyPrice3 = buyPrice3;
	}

	public long getBuyPrice3() {
		return this.buyPrice3;
	}

	public void setBuyVolume3(long buyVolume3) {
		this.buyVolume3 = buyVolume3;
	}

	public long getBuyVolume3() {
		return this.buyVolume3;
	}

	public void setSellPrice3(long sellPrice3) {
		this.sellPrice3 = sellPrice3;
	}

	public long getSellPrice3() {
		return this.sellPrice3;
	}

	public void setSellVolume3(long sellVolume3) {
		this.sellVolume3 = sellVolume3;
	}

	public long getSellVolume3() {
		return this.sellVolume3;
	}

	public void setBuyPrice4(long buyPrice4) {
		this.buyPrice4 = buyPrice4;
	}

	public long getBuyPrice4() {
		return this.buyPrice4;
	}

	public void setBuyVolume4(long buyVolume4) {
		this.buyVolume4 = buyVolume4;
	}

	public long getBuyVolume4() {
		return this.buyVolume4;
	}

	public void setSellPrice4(long sellPrice4) {
		this.sellPrice4 = sellPrice4;
	}

	public long getSellPrice4() {
		return this.sellPrice4;
	}

	public void setSellVolume4(long sellVolume4) {
		this.sellVolume4 = sellVolume4;
	}

	public long getSellVolume4() {
		return this.sellVolume4;
	}

	public void setBuyPrice5(long buyPrice5) {
		this.buyPrice5 = buyPrice5;
	}

	public long getBuyPrice5() {
		return this.buyPrice5;
	}

	public void setBuyVolume5(long buyVolume5) {
		this.buyVolume5 = buyVolume5;
	}

	public long getBuyVolume5() {
		return this.buyVolume5;
	}

	public void setSellPrice5(long sellPrice5) {
		this.sellPrice5 = sellPrice5;
	}

	public long getSellPrice5() {
		return this.sellPrice5;
	}

	public void setSellVolume5(long sellVolume5) {
		this.sellVolume5 = sellVolume5;
	}

	public long getSellVolume5() {
		return this.sellVolume5;
	}

	public void setRltPhase(Character rltPhase) {
		this.rltPhase = rltPhase;
	}

	public Character getRltPhase() {
		return this.rltPhase;
	}

	public void setRltFlag(Character rltFlag) {
		this.rltFlag = rltFlag;
	}

	public Character getRltFlag() {
		return this.rltFlag;
	}

	public void setRltOpenFlag(Character rltOpenFlag) {
		this.rltOpenFlag = rltOpenFlag;
	}

	public Character getRltOpenFlag() {
		return this.rltOpenFlag;
	}

	public void setLatestEnqryTime(String latestEnqryTime) {
		this.latestEnqryTime = latestEnqryTime;
	}

	public String getLatestEnqryTime() {
		return this.latestEnqryTime;
	}

}