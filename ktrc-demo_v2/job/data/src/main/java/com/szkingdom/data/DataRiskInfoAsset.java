package com.szkingdom.data;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangch
 * @date 2018/7/6 18:07
 */
public class DataRiskInfoAsset implements Serializable {
    private static final long serialVersionUID = -1946304562871197727L;
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
    private long posiQty;
    private long posiLqty;
    private long quotaValUsed;
    private long quotaVal;
    private Character cvdShortType;
    private long stkQtyNeed;
    private long stkQtyLocked;
    private long stkQtyAct;
    private long cvtQtyLack;
    private short intOrg;
    private long custCode;
    private String custName;
    private Character custType;
    private Character custCls;
    private long cuacctCode;
    private Character currency;
    private String stkbd;
    private String trdacct;
    private String subacctCode;
    private Character trdacctExcls;
    private String optUndlCode;
    private String optUndlName;
    private Character optUndlCls;
    private Character lsFlag;
    private Character riskValidFlag;
    private Character riskInfoSrc;
    private Character noticeSentLevel;
    private String noticeSentLevelName;

    /**
     * 给新建数据行赋初始默认值
     */
    public void dataRiskInfoAssetInit() {
        if(this.indicatorId == null) {
            this.indicatorId = "";
        }
        if(this.indicatorName == null) {
            this.indicatorName = "";
        }
        if(this.occDate == 0) {
            this.occDate = 0;
        }
        if(this.occTime == 0) {
            this.occTime = 0;
        }
        if(this.preThreshold == 0) {
            this.preThreshold = 0L;
        }
        if(this.preThresNo == null) {
            this.preThresNo = '0';
        }
        if(this.preThresName == null) {
            this.preThresName = "";
        }
        if(this.preThresColor == null) {
            this.preThresColor = "";
        }
        if(this.preRiskValue == 0) {
            this.preRiskValue = 0L;
        }
        if(this.threshold == 0) {
            this.threshold = 0L;
        }
        if(this.thresNo == null) {
            this.thresNo = '0';
        }
        if(this.thresName == null) {
            this.thresName = "";
        }
        if(this.thresColor == null) {
            this.thresColor = "";
        }
        if(this.riskValue == 0) {
            this.riskValue = 0L;
        }
        if(this.posiQty == 0) {
            this.posiQty = 0L;
        }
        if(this.posiLqty == 0) {
            this.posiLqty = 0L;
        }
        if(this.quotaValUsed == 0) {
            this.quotaValUsed = 0L;
        }
        if(this.quotaVal == 0) {
            this.quotaVal = 0L;
        }
        if(this.cvdShortType == null) {
            this.cvdShortType = '0';
        }
        if(this.stkQtyNeed == 0) {
            this.stkQtyNeed = 0L;
        }
        if(this.stkQtyLocked == 0) {
            this.stkQtyLocked = 0L;
        }
        if(this.stkQtyAct == 0) {
            this.stkQtyAct = 0L;
        }
        if(this.cvtQtyLack == 0) {
            this.cvtQtyLack = 0L;
        }
        if(this.intOrg == 0) {
            this.intOrg = 0;
        }
        if(this.custCode == 0) {
            this.custCode = 0L;
        }
        if(this.custName == null) {
            this.custName = "";
        }
        if(this.custType == null) {
            this.custType = '0';
        }
        if(this.custCls == null) {
            this.custCls = '0';
        }
        if(this.cuacctCode == 0) {
            this.cuacctCode = 0L;
        }
        if(this.currency == null) {
            this.currency = '0';
        }
        if(this.stkbd == null) {
            this.stkbd = "";
        }
        if(this.trdacct == null) {
            this.trdacct = "";
        }
        if(this.subacctCode == null) {
            this.subacctCode = "";
        }
        if(this.trdacctExcls == null) {
            this.trdacctExcls = '0';
        }
        if(this.optUndlCode == null) {
            this.optUndlCode = "";
        }
        if(this.optUndlName == null) {
            this.optUndlName = "";
        }
        if(this.optUndlCls == null) {
            this.optUndlCls = '0';
        }
        if(this.lsFlag == null) {
            this.lsFlag = '0';
        }
        if(this.riskValidFlag == null) {
            this.riskValidFlag = '0';
        }
        if(this.riskInfoSrc == null) {
            this.riskInfoSrc = '0';
        }
        if(this.noticeSentLevel == null) {
            this.noticeSentLevel = '0';
        }
        if(this.noticeSentLevelName == null) {
            this.noticeSentLevelName = "";
        }
    }

    public DataRiskInfoAsset(){

    }

    public DataRiskInfoAsset(String indicatorId, String indicatorName, int occDate, int occTime,
                             long riskValue, long posiQty, long posiLQty, long custCode, Character custType,
                             long cuacctCode, String stkbd, String trdacct, String subacctCode,
                             String optUndlCode, Character optUndlCls, Character riskValidFlag){
        this.setIndicatorId(indicatorId);
        this.setIndicatorName(indicatorName);
        this.setOccDate(occDate);
        this.setOccTime(occTime);
        this.setRiskValue(riskValue);
        this.setPosiQty(posiQty);
        this.setPosiLqty(posiLQty);
        this.setCustCode(custCode);
        this.setCustType(custType);
        this.setCuacctCode(cuacctCode);
        this.setStkbd(stkbd);
        this.setTrdacct(trdacct);
        this.setSubacctCode(subacctCode);
        this.setOptUndlCode(optUndlCode);
        this.setOptUndlCls(optUndlCls);
        this.setRiskValidFlag(riskValidFlag);
    }

    public DataRiskInfoAsset(long custCode, String trdacct, String subacctCode,
                             String indicatorId, String optUndlCode, String stkbd){
        this.setCustCode(custCode);
        this.setTrdacct(trdacct);
        this.setSubacctCode(subacctCode);
        this.setIndicatorId(indicatorId);
        this.setOptUndlCode(optUndlCode);
        this.setStkbd(stkbd);
    }

    public boolean updateJsonDataToChart(JSONObject object, boolean setUniqueIndex){
        if(setUniqueIndex) {
            if (object.getString("custCode") != null) {
                this.setCustCode(Long.valueOf(object.getString("custCode")));
            }
            if (object.getString("trdacct") != null) {
                this.setTrdacct(object.getString("trdacct"));
            }
            if (object.getString("subacctCode") != null) {
                this.setSubacctCode(object.getString("subacctCode"));
            }
            if (object.getString("indicatorId") != null) {
                this.setIndicatorId(object.getString("indicatorId"));
            }
            if (object.getString("optUndlCode") != null) {
                this.setOptUndlCode(object.getString("optUndlCode"));
            }
            if (object.getString("stkbd") != null) {
                this.setStkbd(object.getString("stkbd"));
            }
            return true;
        }
        else{
            if (object.getString("indicatorId") != null) {
                this.setIndicatorId(object.getString("indicatorId"));
            }
            if (object.getString("indicatorName") != null) {
                this.setIndicatorName(object.getString("indicatorName"));
            }
            if (object.getString("occDate") != null) {
                this.setOccDate(Integer.valueOf(object.getString("occDate")));
            }
            if (object.getString("occTime") != null) {
                this.setOccTime(Integer.valueOf(object.getString("occTime")));
            }
            if (object.getString("preThreshold") != null) {
                this.setPreThreshold(Long.valueOf(object.getString("preThreshold")));
            }
            if (object.getString("preThresNo") != null) {
                this.setPreThresNo(object.getString("preThresNo").charAt(0));
            }
            if (object.getString("preThresName") != null) {
                this.setPreThresName(object.getString("preThresName"));
            }
            if (object.getString("preThresColor") != null) {
                this.setPreThresColor(object.getString("preThresColor"));
            }
            if (object.getString("preRiskValue") != null) {
                this.setRiskValue(Long.valueOf(object.getString("preRiskValue")));
            }
            if (object.getString("threshold") != null) {
                this.setThreshold(Long.valueOf(object.getString("threshold")));
            }
            if (object.getString("thresNo") != null) {
                this.setThresNo(object.getString("thresNo").charAt(0));
            }
            if (object.getString("thresName") != null) {
                this.setThresName(object.getString("thresName"));
            }
            if (object.getString("thresColor") != null) {
                this.setThresColor(object.getString("thresColor"));
            }
            if (object.getString("riskValue") != null) {
                this.setRiskValue(Long.valueOf(object.getString("riskValue")));
            }
            if (object.getString("posiQty") != null) {
                this.setPosiQty(Long.valueOf(object.getString("posiQty")));
            }
            if (object.getString("posiLqty") != null) {
                this.setPosiLqty(Long.valueOf(object.getString("posiLqty")));
            }
            if (object.getString("quotaValUsed") != null) {
                this.setQuotaValUsed(Long.valueOf(object.getString("quotaValUsed")));
            }
            if (object.getString("quotaVal") != null) {
                this.setQuotaVal(Long.valueOf(object.getString("quotaVal")));
            }
            if (object.getString("cvdShortType") != null) {
                this.setCvdShortType(object.getString("cvdShortType").charAt(0));
            }
            if (object.getString("stkQtyNeed") != null) {
                this.setStkQtyNeed(Long.valueOf(object.getString("stkQtyNeed")));
            }
            if (object.getString("stkQtyLocked") != null) {
                this.setStkQtyLocked(Long.valueOf(object.getString("stkQtyLocked")));
            }
            if (object.getString("stkQtyAct") != null) {
                this.setStkQtyAct(Long.valueOf(object.getString("stkQtyAct")));
            }
            if (object.getString("cvtQtyLack") != null) {
                this.setCvtQtyLack(Long.valueOf(object.getString("cvtQtyLack")));
            }
            if (object.getString("intOrg") != null) {
                this.setIntOrg(Short.valueOf(object.getString("intOrg")));
            }
            if (object.getString("custCode") != null) {
                this.setCustCode(Long.valueOf(object.getString("custCode")));
            }
            if (object.getString("custName") != null) {
                this.setCustName(object.getString("custName"));
            }
            if (object.getString("custType") != null) {
                this.setCustType(object.getString("custType").charAt(0));
            }
            if (object.getString("custCls") != null) {
                this.setCustCls(object.getString("custCls").charAt(0));
            }
            if (object.getString("cuacctCode") != null) {
                this.setCuacctCode(Long.valueOf(object.getString("cuacctCode")));
            }
            if (object.getString("currency") != null) {
                this.setCurrency(object.getString("currency").charAt(0));
            }
            if (object.getString("stkbd") != null) {
                this.setStkbd(object.getString("stkbd"));
            }
            if (object.getString("trdacct") != null) {
                this.setTrdacct(object.getString("trdacct"));
            }
            if (object.getString("subacctCode") != null) {
                this.setSubacctCode(object.getString("subacctCode"));
            }
            if (object.getString("trdacctExcls") != null) {
                this.setTrdacctExcls(object.getString("trdacctExcls").charAt(0));
            }
            if (object.getString("optUndlCode") != null) {
                this.setOptUndlCode(object.getString("optUndlCode"));
            }
            if (object.getString("optUndlName") != null) {
                this.setOptUndlName(object.getString("optUndlName"));
            }
            if (object.getString("optUndlCls") != null) {
                this.setOptUndlCls(object.getString("optUndlCls").charAt(0));
            }
            if (object.getString("lsFlag") != null) {
                this.setLsFlag(object.getString("lsFlag").charAt(0));
            }
            if (object.getString("riskValidFlag") != null) {
                this.setRiskValidFlag(object.getString("riskValidFlag").charAt(0));
            }
            if (object.getString("riskInfoSrc") != null) {
                this.setRiskInfoSrc(object.getString("riskInfoSrc").charAt(0));
            }
            if (object.getString("noticeSentLevel") != null) {
                this.setNoticeSentLevel(object.getString("noticeSentLevel").charAt(0));
            }
            if (object.getString("noticeSentLevelName") != null) {
                this.setNoticeSentLevelName(object.getString("noticeSentLevelName"));
            }
            return true;
        }
    }

    public void savePreviousData(List<DataRiskInfoAsset> dataRiskInfoAssetList){
        if(this.indicatorId == null) {
            this.indicatorId = dataRiskInfoAssetList.get(0).getIndicatorId();
        }
        if(this.indicatorName == null) {
            this.indicatorName = dataRiskInfoAssetList.get(0).getIndicatorName();
        }
        if(this.occDate == 0) {
            this.occDate = dataRiskInfoAssetList.get(0).getOccDate();
        }
        if(this.occTime == 0) {
            this.occTime = dataRiskInfoAssetList.get(0).getOccTime();
        }
        if(this.preThreshold == 0) {
            this.preThreshold = dataRiskInfoAssetList.get(0).getPreThreshold();
        }
        if(this.preThresNo == null) {
            this.preThresNo = dataRiskInfoAssetList.get(0).getPreThresNo();
        }
        if(this.preThresName == null) {
            this.preThresName = dataRiskInfoAssetList.get(0).getPreThresName();
        }
        if(this.preThresColor == null) {
            this.preThresColor = dataRiskInfoAssetList.get(0).getPreThresColor();
        }
        if(this.preRiskValue == 0) {
            this.preRiskValue = dataRiskInfoAssetList.get(0).getPreRiskValue();
        }
        if(this.threshold == 0) {
            this.threshold = dataRiskInfoAssetList.get(0).getThreshold();
        }
        if(this.thresNo == null) {
            this.thresNo = dataRiskInfoAssetList.get(0).getThresNo();
        }
        if(this.thresName == null) {
            this.thresName = dataRiskInfoAssetList.get(0).getThresName();
        }
        if(this.thresColor == null) {
            this.thresColor = dataRiskInfoAssetList.get(0).getThresColor();
        }
        if(this.riskValue == 0) {
            this.riskValue = dataRiskInfoAssetList.get(0).getRiskValue();
        }
        if(this.posiQty == 0) {
            this.posiQty = dataRiskInfoAssetList.get(0).getPosiQty();
        }
        if(this.posiLqty == 0) {
            this.posiLqty = dataRiskInfoAssetList.get(0).getPosiLqty();
        }
        if(this.quotaValUsed == 0) {
            this.quotaValUsed = dataRiskInfoAssetList.get(0).getQuotaValUsed();
        }
        if(this.quotaVal == 0) {
            this.quotaVal = dataRiskInfoAssetList.get(0).getQuotaVal();
        }
        if(this.cvdShortType == null) {
            this.cvdShortType = dataRiskInfoAssetList.get(0).getCvdShortType();
        }
        if(this.stkQtyNeed == 0) {
            this.stkQtyNeed = dataRiskInfoAssetList.get(0).getStkQtyNeed();
        }
        if(this.stkQtyLocked == 0) {
            this.stkQtyLocked = dataRiskInfoAssetList.get(0).getStkQtyLocked();
        }
        if(this.stkQtyAct == 0) {
            this.stkQtyAct = dataRiskInfoAssetList.get(0).getStkQtyAct();
        }
        if(this.cvtQtyLack == 0) {
            this.cvtQtyLack = dataRiskInfoAssetList.get(0).getCvtQtyLack();
        }
        if(this.intOrg == 0) {
            this.intOrg = dataRiskInfoAssetList.get(0).getIntOrg();
        }
        if(this.custCode == 0) {
            this.custCode = dataRiskInfoAssetList.get(0).getCustCode();
        }
        if(this.custName == null) {
            this.custName = dataRiskInfoAssetList.get(0).getCustName();
        }
        if(this.custType == null) {
            this.custType = dataRiskInfoAssetList.get(0).getCustType();
        }
        if(this.custCls == null) {
            this.custCls = dataRiskInfoAssetList.get(0).getCustCls();
        }
        if(this.cuacctCode == 0) {
            this.cuacctCode = dataRiskInfoAssetList.get(0).getCuacctCode();
        }
        if(this.currency == null) {
            this.currency = dataRiskInfoAssetList.get(0).getCurrency();
        }
        if(this.stkbd == null) {
            this.stkbd = dataRiskInfoAssetList.get(0).getStkbd();
        }
        if(this.trdacct == null) {
            this.trdacct = dataRiskInfoAssetList.get(0).getTrdacct();
        }
        if(this.subacctCode == null) {
            this.subacctCode = dataRiskInfoAssetList.get(0).getSubacctCode();
        }
        if(this.trdacctExcls == null) {
            this.trdacctExcls = dataRiskInfoAssetList.get(0).getTrdacctExcls();
        }
        if(this.optUndlCode == null) {
            this.optUndlCode = dataRiskInfoAssetList.get(0).getOptUndlCode();
        }
        if(this.optUndlName == null) {
            this.optUndlName = dataRiskInfoAssetList.get(0).getOptUndlName();
        }
        if(this.optUndlCls == null) {
            this.optUndlCls = dataRiskInfoAssetList.get(0).getOptUndlCls();
        }
        if(this.lsFlag == null) {
            this.lsFlag = dataRiskInfoAssetList.get(0).getLsFlag();
        }
        if(this.riskValidFlag == null) {
            this.riskValidFlag = dataRiskInfoAssetList.get(0).getRiskValidFlag();
        }
        if(this.riskInfoSrc == null) {
            this.riskInfoSrc = dataRiskInfoAssetList.get(0).getRiskInfoSrc();
        }
        if(this.noticeSentLevel == null) {
            this.noticeSentLevel = dataRiskInfoAssetList.get(0).getNoticeSentLevel();
        }
        if(this.noticeSentLevelName == null) {
            this.noticeSentLevelName = dataRiskInfoAssetList.get(0).getNoticeSentLevelName();
        }
    }

    public String getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public int getOccDate() {
        return occDate;
    }

    public void setOccDate(int occDate) {
        this.occDate = occDate;
    }

    public int getOccTime() {
        return occTime;
    }

    public void setOccTime(int occTime) {
        this.occTime = occTime;
    }

    public long getPreThreshold() {
        return preThreshold;
    }

    public void setPreThreshold(long preThreshold) {
        this.preThreshold = preThreshold;
    }

    public Character getPreThresNo() {
        return preThresNo;
    }

    public void setPreThresNo(Character preThresNo) {
        this.preThresNo = preThresNo;
    }

    public String getPreThresName() {
        return preThresName;
    }

    public void setPreThresName(String preThresName) {
        this.preThresName = preThresName;
    }

    public String getPreThresColor() {
        return preThresColor;
    }

    public void setPreThresColor(String preThresColor) {
        this.preThresColor = preThresColor;
    }

    public long getPreRiskValue() {
        return preRiskValue;
    }

    public void setPreRiskValue(long preRiskValue) {
        this.preRiskValue = preRiskValue;
    }

    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    public Character getThresNo() {
        return thresNo;
    }

    public void setThresNo(Character thresNo) {
        this.thresNo = thresNo;
    }

    public String getThresName() {
        return thresName;
    }

    public void setThresName(String thresName) {
        this.thresName = thresName;
    }

    public String getThresColor() {
        return thresColor;
    }

    public void setThresColor(String thresColor) {
        this.thresColor = thresColor;
    }

    public long getRiskValue() {
        return riskValue;
    }

    public void setRiskValue(long riskValue) {
        this.riskValue = riskValue;
    }

    public long getPosiQty() {
        return posiQty;
    }

    public void setPosiQty(long posiQty) {
        this.posiQty = posiQty;
    }

    public long getPosiLqty() {
        return posiLqty;
    }

    public void setPosiLqty(long posiLqty) {
        this.posiLqty = posiLqty;
    }

    public long getQuotaValUsed() {
        return quotaValUsed;
    }

    public void setQuotaValUsed(long quotaValUsed) {
        this.quotaValUsed = quotaValUsed;
    }

    public long getQuotaVal() {
        return quotaVal;
    }

    public void setQuotaVal(long quotaVal) {
        this.quotaVal = quotaVal;
    }

    public Character getCvdShortType() {
        return cvdShortType;
    }

    public void setCvdShortType(Character cvdShortType) {
        this.cvdShortType = cvdShortType;
    }

    public long getStkQtyNeed() {
        return stkQtyNeed;
    }

    public void setStkQtyNeed(long stkQtyNeed) {
        this.stkQtyNeed = stkQtyNeed;
    }

    public long getStkQtyLocked() {
        return stkQtyLocked;
    }

    public void setStkQtyLocked(long stkQtyLocked) {
        this.stkQtyLocked = stkQtyLocked;
    }

    public long getStkQtyAct() {
        return stkQtyAct;
    }

    public void setStkQtyAct(long stkQtyAct) {
        this.stkQtyAct = stkQtyAct;
    }

    public long getCvtQtyLack() {
        return cvtQtyLack;
    }

    public void setCvtQtyLack(long cvtQtyLack) {
        this.cvtQtyLack = cvtQtyLack;
    }

    public short getIntOrg() {
        return intOrg;
    }

    public void setIntOrg(short intOrg) {
        this.intOrg = intOrg;
    }

    public long getCustCode() {
        return custCode;
    }

    public void setCustCode(long custCode) {
        this.custCode = custCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Character getCustType() {
        return custType;
    }

    public void setCustType(Character custType) {
        this.custType = custType;
    }

    public Character getCustCls() {
        return custCls;
    }

    public void setCustCls(Character custCls) {
        this.custCls = custCls;
    }

    public long getCuacctCode() {
        return cuacctCode;
    }

    public void setCuacctCode(long cuacctCode) {
        this.cuacctCode = cuacctCode;
    }

    public Character getCurrency() {
        return currency;
    }

    public void setCurrency(Character currency) {
        this.currency = currency;
    }

    public String getStkbd() {
        return stkbd;
    }

    public void setStkbd(String stkbd) {
        this.stkbd = stkbd;
    }

    public String getTrdacct() {
        return trdacct;
    }

    public void setTrdacct(String trdacct) {
        this.trdacct = trdacct;
    }

    public String getSubacctCode() {
        return subacctCode;
    }

    public void setSubacctCode(String subacctCode) {
        this.subacctCode = subacctCode;
    }

    public Character getTrdacctExcls() {
        return trdacctExcls;
    }

    public void setTrdacctExcls(Character trdacctExcls) {
        this.trdacctExcls = trdacctExcls;
    }

    public String getOptUndlCode() {
        return optUndlCode;
    }

    public void setOptUndlCode(String optUndlCode) {
        this.optUndlCode = optUndlCode;
    }

    public String getOptUndlName() {
        return optUndlName;
    }

    public void setOptUndlName(String optUndlName) {
        this.optUndlName = optUndlName;
    }

    public Character getOptUndlCls() {
        return optUndlCls;
    }

    public void setOptUndlCls(Character optUndlCls) {
        this.optUndlCls = optUndlCls;
    }

    public Character getLsFlag() {
        return lsFlag;
    }

    public void setLsFlag(Character lsFlag) {
        this.lsFlag = lsFlag;
    }

    public Character getRiskValidFlag() {
        return riskValidFlag;
    }

    public void setRiskValidFlag(Character riskValidFlag) {
        this.riskValidFlag = riskValidFlag;
    }

    public Character getRiskInfoSrc() {
        return riskInfoSrc;
    }

    public void setRiskInfoSrc(Character riskInfoSrc) {
        this.riskInfoSrc = riskInfoSrc;
    }

    public Character getNoticeSentLevel() {
        return noticeSentLevel;
    }

    public void setNoticeSentLevel(Character noticeSentLevel) {
        this.noticeSentLevel = noticeSentLevel;
    }

    public String getNoticeSentLevelName() {
        return noticeSentLevelName;
    }

    public void setNoticeSentLevelName(String noticeSentLevelName) {
        this.noticeSentLevelName = noticeSentLevelName;
    }

    @Override
    public String toString() {
        return "DataRiskInfoAsset{" +
                "indicatorId='" + indicatorId + '\'' +
                ", indicatorName='" + indicatorName + '\'' +
                ", occDate=" + occDate +
                ", occTime=" + occTime +
                ", preThreshold=" + preThreshold +
                ", preThresNo=" + preThresNo +
                ", preThresName='" + preThresName + '\'' +
                ", preThresColor='" + preThresColor + '\'' +
                ", preRiskValue=" + preRiskValue +
                ", threshold=" + threshold +
                ", thresNo=" + thresNo +
                ", thresName='" + thresName + '\'' +
                ", thresColor='" + thresColor + '\'' +
                ", riskValue=" + riskValue +
                ", posiQty=" + posiQty +
                ", posiLqty=" + posiLqty +
                ", quotaValUsed=" + quotaValUsed +
                ", quotaVal=" + quotaVal +
                ", cvdShortType=" + cvdShortType +
                ", stkQtyNeed=" + stkQtyNeed +
                ", stkQtyLocked=" + stkQtyLocked +
                ", stkQtyAct=" + stkQtyAct +
                ", cvtQtyLack=" + cvtQtyLack +
                ", intOrg=" + intOrg +
                ", custCode=" + custCode +
                ", custName='" + custName + '\'' +
                ", custType=" + custType +
                ", custCls=" + custCls +
                ", cuacctCode=" + cuacctCode +
                ", currency=" + currency +
                ", stkbd='" + stkbd + '\'' +
                ", trdacct='" + trdacct + '\'' +
                ", subacctCode='" + subacctCode + '\'' +
                ", trdacctExcls=" + trdacctExcls +
                ", optUndlCode='" + optUndlCode + '\'' +
                ", optUndlName='" + optUndlName + '\'' +
                ", optUndlCls=" + optUndlCls +
                ", lsFlag=" + lsFlag +
                ", riskValidFlag=" + riskValidFlag +
                ", riskInfoSrc=" + riskInfoSrc +
                ", noticeSentLevel=" + noticeSentLevel +
                ", noticeSentLevelName='" + noticeSentLevelName + '\'' +
                '}';
    }
}
