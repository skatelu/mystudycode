package com.szkingdom.data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;

/**
 * @author zhengmj
 * @date 2018/7/7 09:35
 */
public class DataOptAsset implements Serializable {
    private static final long serialVersionUID = -4190854791424304738L;
    private long custCode;
    private Character custType;
    private long cuacctCode;
    private Character cuacctAttr;
    private Character cuacctCls;
    private Character cuacctLvl;
    private Character cuacctGrp;
    private short intOrg;
    private Character stkex;
    private String stkbd;
    private String stkpbu;
    private String trdacct;
    private String subacctCode;
    private Character trdacctExcls;
    private Character currency;
    private String optNum;
    private String optCode;
    private String optName;
    private Character optType;
    private Character optSide;
    private Character optCvdFlag;
    private Character optUndlCls;
    private String optUndlCode;
    private long optPrebln;
    private long optBln;
    private long optAvl;
    private long optFrz;
    private long optUfz;
    private long optTrdFrz;
    private long optTrdUfz;
    private long optTrdOtd;
    private long optTrdBln;
    private long optClrFrz;
    private long optClrUfz;
    private long optClrOtd;
    private long optBcost;
    private long optBcostRlt;
    private long optPlamt;
    private long optPlamtRlt;
    private long optMktVal;
    private long optPremium;
    private long quotaValUsed;
    private long optMargin;
    private long optCvdAsset;
    private long optClsProfit;
    private long preClsProfit;
    private long optFloatProfit;
    private long optPosiRlt;
    private long optAutoExeQty;
    private long optClsUnmatched;
    private long optDailyOpenRlt;
    private long combedQty;
    private Date updTime;
    private String mac;

    public DataOptAsset(){

    }

    public DataOptAsset(long cuacctCode, String stkbd, String stkpbu, String trdacct,
                        String optNum,String stkBiz, long orderQty){
        this.setCuacctCode(cuacctCode);
        this.setStkbd(stkbd);
        this.setStkpbu(stkpbu);
        this.setTrdacct(trdacct);
        this.setOptNum(optNum);
        if (ConstantUtil.StkBiz.STK_BIZ_BUY_OPEN_POSITION.equals(stkBiz)){
            this.setOptSide('L');
        }else if (ConstantUtil.StkBiz.STK_BIZ_SELL_CLOSE_POSITION.equals(stkBiz)){
            this.setOptSide('L');
        }else if (ConstantUtil.StkBiz.STK_BIZ_SELL_OPEN_POSITION.equals(stkBiz)){
            this.setOptSide('S');
        }else if (ConstantUtil.StkBiz.STK_BIZ_COVERED_CALL_POSITION.equals(stkBiz)){
            this.setOptSide('C');
        }
        this.setOptPosiRlt(orderQty);
    }

    public boolean updateJsonDataToChart(JSONObject object, boolean setUniqueIndex){
        if(setUniqueIndex) {
            if (object.getString("cuacctCode") != null) {
                this.setCuacctCode(Long.valueOf(object.getString("cuacctCode")));
            }
            if (object.getString("optNum") != null) {
                this.setOptNum(object.getString("optNum"));
            }
            if (object.getString("stkbd") != null) {
                this.setStkbd(object.getString("stkbd"));
            }
            if (object.getString("stkpbu") != null) {
                this.setStkpbu(object.getString("stkpbu"));
            }
            if (object.getString("trdacct") != null) {
                this.setTrdacct(object.getString("trdacct"));
            }
            if (object.getString("optSide") != null) {
                this.setOptSide(object.getString("optSide").charAt(0));
            }
            return true;
        }
        else{
            if (object.getString("custCode") != null) {
                this.setCustCode(Long.valueOf(object.getString("custCode")));
            }
            if (object.getString("custType") != null) {
                this.setCustType(object.getString("custType").charAt(0));
            }
            if (object.getString("cuacctCode") != null) {
                this.setCuacctCode(Long.valueOf(object.getString("cuacctCode")));
            }
            if (object.getString("cuacctAttr") != null) {
                this.setCuacctAttr(object.getString("cuacctAttr").charAt(0));
            }
            if (object.getString("cuacctCls") != null) {
                this.setCuacctCls(object.getString("cuacctCls").charAt(0));
            }
            if (object.getString("cuacctLvl") != null) {
                this.setCuacctLvl(object.getString("cuacctLvl").charAt(0));
            }
            if (object.getString("cuacctGrp") != null) {
                this.setCuacctGrp(object.getString("cuacctGrp").charAt(0));
            }
            if (object.getString("intOrg") != null) {
                this.setIntOrg(Short.valueOf(object.getString("intOrg")));
            }
            if (object.getString("stkex") != null) {
                this.setStkex(object.getString("stkex").charAt(0));
            }
            if (object.getString("stkbd") != null) {
                this.setStkbd(object.getString("stkbd"));
            }
            if (object.getString("stkpbu") != null) {
                this.setStkpbu(object.getString("stkpbu"));
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
            if (object.getString("currency") != null) {
                this.setCurrency(object.getString("currency").charAt(0));
            }
            if (object.getString("optNum") != null) {
                this.setOptNum(object.getString("optNum"));
            }
            if (object.getString("optCode") != null) {
                this.setOptCode(object.getString("optCode"));
            }
            if (object.getString("optName") != null) {
                this.setOptNum(object.getString("optName"));
            }
            if (object.getString("optType") != null) {
                this.setOptType(object.getString("optType").charAt(0));
            }
            if (object.getString("optSide") != null) {
                this.setOptSide(object.getString("optSide").charAt(0));
            }
            if (object.getString("optCvdFlag") != null) {
                this.setOptCvdFlag(object.getString("optCvdFlag").charAt(0));
            }
            if (object.getString("optUndlCls") != null) {
                this.setOptUndlCls(object.getString("optUndlCls").charAt(0));
            }
            if (object.getString("optUndlCode") != null) {
                this.setOptUndlCode(object.getString("optUndlCode"));
            }
            if (object.getString("optPrebln") != null) {
                this.setOptPrebln(Long.valueOf(object.getString("optPrebln")));
            }
            if (object.getString("optBln") != null) {
                this.setOptBln(Long.valueOf(object.getString("optBln")));
            }
            if (object.getString("optAvl") != null) {
                this.setOptAvl(Long.valueOf(object.getString("optAvl")));
            }
            if (object.getString("optFrz") != null) {
                this.setOptAvl(Long.valueOf(object.getString("optFrz")));
            }
            if (object.getString("optUfz") != null) {
                this.setOptUfz(Long.valueOf(object.getString("optUfz")));
            }
            if (object.getString("optTrdFrz") != null) {
                this.setOptTrdFrz(Long.valueOf(object.getString("optTrdFrz")));
            }
            if (object.getString("optTrdUfz") != null) {
                this.setOptTrdUfz(Long.valueOf(object.getString("optTrdUfz")));
            }
            if (object.getString("optTrdOtd") != null) {
                this.setOptTrdOtd(Long.valueOf(object.getString("optTrdOtd")));
            }
            if (object.getString("optTrdBln") != null) {
                this.setOptTrdBln(Long.valueOf(object.getString("optTrdBln")));
            }
            if (object.getString("optClrFrz") != null) {
                this.setOptClrFrz(Long.valueOf(object.getString("optClrFrz")));
            }
            if (object.getString("optClrUfz") != null) {
                this.setOptClrUfz(Long.valueOf(object.getString("optClrUfz")));
            }
            if (object.getString("optClrOtd") != null) {
                this.setOptClrOtd(Long.valueOf(object.getString("optClrOtd")));
            }
            if (object.getString("optBcost") != null) {
                this.setOptBcost(Long.valueOf(object.getString("optBcost")));
            }
            if (object.getString("optBcostRlt") != null) {
                this.setOptBcostRlt(Long.valueOf(object.getString("optBcostRlt")));
            }
            if (object.getString("optPlamt") != null) {
                this.setOptPlamt(Long.valueOf(object.getString("optPlamt")));
            }
            if (object.getString("optPlamtRlt") != null) {
                this.setOptPlamtRlt(Long.valueOf(object.getString("optPlamtRlt")));
            }
            if (object.getString("optMktVal") != null) {
                this.setOptMktVal(Long.valueOf(object.getString("optMktVal")));
            }
            if (object.getString("optPremium") != null) {
                this.setOptPremium(Long.valueOf(object.getString("optPremium")));
            }
            if (object.getString("quotaValUsed") != null) {
                this.setQuotaValUsed(Long.valueOf(object.getString("quotaValUsed")));
            }
            if (object.getString("optMargin") != null) {
                this.setOptMargin(Long.valueOf(object.getString("optMargin")));
            }
            if (object.getString("optCvdAsset") != null) {
                this.setOptCvdAsset(Long.valueOf(object.getString("optCvdAsset")));
            }
            if (object.getString("optClsProfit") != null) {
                this.setOptClsProfit(Long.valueOf(object.getString("optClsProfit")));
            }
            if (object.getString("preClsProfit") != null) {
                this.setPreClsProfit(Long.valueOf(object.getString("preClsProfit")));
            }
            if (object.getString("optFloatProfit") != null) {
                this.setOptFloatProfit(Long.valueOf(object.getString("optFloatProfit")));
            }
            if (object.getString("optPosiRlt") != null) {
                this.setOptPosiRlt(Long.valueOf(object.getString("optPosiRlt")));
            }
            if (object.getString("optAutoExeQty") != null) {
                this.setOptAutoExeQty(Long.valueOf(object.getString("optAutoExeQty")));
            }
            if (object.getString("optClsUnmatched") != null) {
                this.setOptClsUnmatched(Long.valueOf(object.getString("optClsUnmatched")));
            }
            if (object.getString("optDailyOpenRlt") != null) {
                this.setOptDailyOpenRlt(Long.valueOf(object.getString("optDailyOpenRlt")));
            }
            if (object.getString("combedQty") != null) {
                this.setCombedQty(Long.valueOf(object.getString("combedQty")));
            }
            if (object.getString("updTime") != null) {
                SimpleDateFormat sdf = new SimpleDateFormat();
                try {
                    this.setUpdTime(sdf.parse(object.getString("updTime")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (object.getString("mac") != null) {
                this.setMac(object.getString("mac"));
            }

            return true;
        }
    }
    /**
     * 赋默认值
     */
    public void dataOptAssetInit(){
        if(this.custCode == 0) {
            this.custCode = 0;
        }
        if(this.custType == null) {
            this.custType = '0';
        }
        if(this.cuacctCode == 0) {
            this.cuacctCode = 0;
        }
        if(this.cuacctAttr == null) {
            this.cuacctAttr = '0';
        }
        if(this.cuacctCls == null) {
            this.cuacctCls = '0';
        }
        if(this.cuacctLvl == null) {
            this.cuacctLvl = '0';
        }
        if(this.cuacctGrp == null) {
            this.cuacctGrp = '0';
        }
        if(this.intOrg == 0) {
            this.intOrg = 0;
        }
        if(this.stkex == null) {
            this.stkex = '0';
        }
        if(this.stkbd == null) {
            this.stkbd = "";
        }
        if(this.stkpbu == null) {
            this.stkpbu = "";
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
        if(this.currency == null) {
            this.currency = '0';
        }
        if(this.optNum == null) {
            this.optNum = "";
        }
        if(this.optCode == null) {
            this.optCode = "";
        }
        if(this.optName == null) {
            this.optName = "";
        }
        if(this.optType == null) {
            this.optType = '0';
        }
        if(this.optSide == null) {
            this.optSide = '0';
        }
        if(this.optCvdFlag == null) {
            this.optCvdFlag = '0';
        }
        if(this.optUndlCls == null) {
            this.optUndlCls = '0';
        }
        if(this.optUndlCode == null) {
            this.optUndlCode = "";
        }
        if(this.optPrebln == 0) {
            this.optPrebln = 0;
        }
        if(this.optBln == 0) {
            this.optBln = 0;
        }
        if(this.optAvl == 0) {
            this.optAvl = 0;
        }
        if(this.optFrz == 0) {
            this.optFrz = 0;
        }
        if(this.optUfz == 0) {
            this.optUfz = 0;
        }
        if(this.optTrdFrz == 0) {
            this.optTrdFrz = 0;
        }
        if(this.optTrdUfz == 0) {
            this.optTrdUfz = 0;
        }
        if(this.optTrdOtd == 0) {
            this.optTrdOtd = 0;
        }
        if(this.optTrdBln == 0) {
            this.optTrdBln = 0;
        }
        if(this.optClrFrz == 0) {
            this.optClrFrz = 0;
        }
        if(this.optClrUfz == 0) {
            this.optClrUfz = 0;
        }
        if(this.optClrOtd == 0) {
            this.optClrOtd = 0;
        }
        if(this.optBcost == 0) {
            this.optBcost = 0;
        }
        if(this.optBcostRlt == 0) {
            this.optBcostRlt = 0;
        }
        if(this.optPlamt == 0) {
            this.optPlamt = 0;
        }
        if(this.optPlamtRlt == 0) {
            this.optPlamtRlt = 0;
        }
        if(this.optMktVal == 0) {
            this.optMktVal = 0;
        }
        if(this.optPremium == 0) {
            this.optPremium = 0;
        }
        if(this.quotaValUsed == 0) {
            this.quotaValUsed = 0;
        }
        if(this.optMargin == 0) {
            this.optMargin = 0;
        }
        if(this.optCvdAsset == 0) {
            this.optCvdAsset = 0;
        }
        if(this.optClsProfit == 0) {
            this.optClsProfit = 0;
        }
        if(this.preClsProfit == 0) {
            this.preClsProfit = 0;
        }
        if(this.optFloatProfit == 0) {
            this.optFloatProfit = 0;
        }
        if(this.optPosiRlt == 0) {
            this.optPosiRlt = 0;
        }
        if(this.optAutoExeQty == 0) {
            this.optAutoExeQty = 0;
        }
        if(this.optClsUnmatched == 0) {
            this.optClsUnmatched = 0;
        }
        if(this.optDailyOpenRlt == 0) {
            this.optDailyOpenRlt = 0;
        }
        if(this.combedQty == 0) {
            this.combedQty = 0;
        }
        if(this.updTime == null) {
            this.updTime = new Date();
        }
        if(this.mac == null) {
            this.mac = "";
        }
    }

    public void savePreviousData(List<DataOptAsset> dataOptAssetList){
        if(this.custCode == 0) {
            this.custCode = dataOptAssetList.get(0).getCustCode();
        }
        if(this.custType == null) {
            this.custType = dataOptAssetList.get(0).getCustType();
        }
        if(this.cuacctCode == 0) {
            this.cuacctCode = dataOptAssetList.get(0).getCuacctCode();
        }
        if(this.cuacctAttr == null) {
            this.cuacctAttr = dataOptAssetList.get(0).getCuacctAttr();
        }
        if(this.cuacctCls == null) {
            this.cuacctCls = dataOptAssetList.get(0).getCuacctCls();
        }
        if(this.cuacctLvl == null) {
            this.cuacctLvl = dataOptAssetList.get(0).getCuacctLvl();
        }
        if(this.cuacctGrp == null) {
            this.cuacctGrp = dataOptAssetList.get(0).getCuacctGrp();
        }
        if(this.intOrg == 0) {
            this.intOrg = dataOptAssetList.get(0).getIntOrg();
        }
        if(this.stkex == null) {
            this.stkex = dataOptAssetList.get(0).getStkex();
        }
        if(this.stkbd == null) {
            this.stkbd = dataOptAssetList.get(0).getStkbd();
        }
        if(this.stkpbu == null) {
            this.stkpbu = dataOptAssetList.get(0).getStkpbu();
        }
        if(this.trdacct == null) {
            this.trdacct = dataOptAssetList.get(0).getTrdacct();
        }
        if(this.subacctCode == null) {
            this.subacctCode = dataOptAssetList.get(0).getSubacctCode();
        }
        if(this.trdacctExcls == null) {
            this.trdacctExcls = dataOptAssetList.get(0).getTrdacctExcls();
        }
        if(this.currency == null) {
            this.currency = dataOptAssetList.get(0).getCurrency();
        }
        if(this.optNum == null) {
            this.optNum = dataOptAssetList.get(0).getOptNum();
        }
        if(this.optCode == null) {
            this.optCode = dataOptAssetList.get(0).getOptCode();
        }
        if(this.optName == null) {
            this.optName = dataOptAssetList.get(0).getOptName();
        }
        if(this.optType == null) {
            this.optType = dataOptAssetList.get(0).getOptType();
        }
        if(this.optSide == null) {
            this.optSide = dataOptAssetList.get(0).getOptSide();
        }
        if(this.optCvdFlag == null) {
            this.optCvdFlag = dataOptAssetList.get(0).getOptCvdFlag();
        }
        if(this.optUndlCls == null) {
            this.optUndlCls = dataOptAssetList.get(0).getOptUndlCls();
        }
        if(this.optUndlCode == null) {
            this.optUndlCode = dataOptAssetList.get(0).getOptUndlCode();
        }
        if(this.optPrebln == 0) {
            this.optPrebln = dataOptAssetList.get(0).getOptPrebln();
        }
        if(this.optBln == 0) {
            this.optBln = dataOptAssetList.get(0).getOptBln();
        }
        if(this.optAvl == 0) {
            this.optAvl = dataOptAssetList.get(0).getOptAvl();
        }
        if(this.optFrz == 0) {
            this.optFrz = dataOptAssetList.get(0).getOptFrz();
        }
        if(this.optUfz == 0) {
            this.optUfz = dataOptAssetList.get(0).getOptUfz();
        }
        if(this.optTrdFrz == 0) {
            this.optTrdFrz = dataOptAssetList.get(0).getOptTrdFrz();
        }
        if(this.optTrdUfz == 0) {
            this.optTrdUfz = dataOptAssetList.get(0).getOptTrdUfz();
        }
        if(this.optTrdOtd == 0) {
            this.optTrdOtd = dataOptAssetList.get(0).getOptTrdOtd();
        }
        if(this.optTrdBln == 0) {
            this.optTrdBln = dataOptAssetList.get(0).getOptTrdBln();
        }
        if(this.optClrFrz == 0) {
            this.optClrFrz = dataOptAssetList.get(0).getOptClrFrz();
        }
        if(this.optClrUfz == 0) {
            this.optClrUfz = dataOptAssetList.get(0).getOptClrUfz();
        }
        if(this.optClrOtd == 0) {
            this.optClrOtd = dataOptAssetList.get(0).getOptClrOtd();
        }
        if(this.optBcost == 0) {
            this.optBcost = dataOptAssetList.get(0).getOptBcost();
        }
        if(this.optBcostRlt == 0) {
            this.optBcostRlt = dataOptAssetList.get(0).getOptBcostRlt();
        }
        if(this.optPlamt == 0) {
            this.optPlamt = dataOptAssetList.get(0).getOptPlamt();
        }
        if(this.optPlamtRlt == 0) {
            this.optPlamtRlt = dataOptAssetList.get(0).getOptPlamtRlt();
        }
        if(this.optMktVal == 0) {
            this.optMktVal = dataOptAssetList.get(0).getOptMktVal();
        }
        if(this.optPremium == 0) {
            this.optPremium = dataOptAssetList.get(0).getOptPremium();
        }
        if(this.quotaValUsed == 0) {
            this.quotaValUsed = dataOptAssetList.get(0).getQuotaValUsed();
        }
        if(this.optMargin == 0) {
            this.optMargin = dataOptAssetList.get(0).getOptMargin();
        }
        if(this.optCvdAsset == 0) {
            this.optCvdAsset = dataOptAssetList.get(0).getOptCvdAsset();
        }
        if(this.optClsProfit == 0) {
            this.optClsProfit = dataOptAssetList.get(0).getOptClsProfit();
        }
        if(this.preClsProfit == 0) {
            this.preClsProfit = dataOptAssetList.get(0).getPreClsProfit();
        }
        if(this.optFloatProfit == 0) {
            this.optFloatProfit = dataOptAssetList.get(0).getOptFloatProfit();
        }
        if(this.optPosiRlt == 0) {
            this.optPosiRlt = dataOptAssetList.get(0).getOptPosiRlt();
        }
        if(this.optAutoExeQty == 0) {
            this.optAutoExeQty = dataOptAssetList.get(0).getOptAutoExeQty();
        }
        if(this.optClsUnmatched == 0) {
            this.optClsUnmatched = dataOptAssetList.get(0).getOptClsUnmatched();
        }
        if(this.optDailyOpenRlt == 0) {
            this.optDailyOpenRlt = dataOptAssetList.get(0).getOptDailyOpenRlt();
        }
        if(this.combedQty == 0) {
            this.combedQty = dataOptAssetList.get(0).getCombedQty();
        }
        if(this.updTime == null) {
            this.updTime = dataOptAssetList.get(0).getUpdTime();
        }
        if(this.mac == null) {
            this.mac = dataOptAssetList.get(0).getMac();
        }
    }

    public long getCustCode() {
        return custCode;
    }

    public void setCustCode(long custCode) {
        this.custCode = custCode;
    }

    public Character getCustType() {
        return custType;
    }

    public void setCustType(Character custType) {
        this.custType = custType;
    }

    public long getCuacctCode() {
        return cuacctCode;
    }

    public void setCuacctCode(long cuacctCode) {
        this.cuacctCode = cuacctCode;
    }

    public Character getCuacctAttr() {
        return cuacctAttr;
    }

    public void setCuacctAttr(Character cuacctAttr) {
        this.cuacctAttr = cuacctAttr;
    }

    public Character getCuacctCls() {
        return cuacctCls;
    }

    public void setCuacctCls(Character cuacctCls) {
        this.cuacctCls = cuacctCls;
    }

    public Character getCuacctLvl() {
        return cuacctLvl;
    }

    public void setCuacctLvl(Character cuacctLvl) {
        this.cuacctLvl = cuacctLvl;
    }

    public Character getCuacctGrp() {
        return cuacctGrp;
    }

    public void setCuacctGrp(Character cuacctGrp) {
        this.cuacctGrp = cuacctGrp;
    }

    public short getIntOrg() {
        return intOrg;
    }

    public void setIntOrg(short intOrg) {
        this.intOrg = intOrg;
    }

    public Character getStkex() {
        return stkex;
    }

    public void setStkex(Character stkex) {
        this.stkex = stkex;
    }

    public String getStkbd() {
        return stkbd;
    }

    public void setStkbd(String stkbd) {
        this.stkbd = stkbd;
    }

    public String getStkpbu() {
        return stkpbu;
    }

    public void setStkpbu(String stkpbu) {
        this.stkpbu = stkpbu;
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

    public Character getCurrency() {
        return currency;
    }

    public void setCurrency(Character currency) {
        this.currency = currency;
    }

    public String getOptNum() {
        return optNum;
    }

    public void setOptNum(String optNum) {
        this.optNum = optNum;
    }

    public String getOptCode() {
        return optCode;
    }

    public void setOptCode(String optCode) {
        this.optCode = optCode;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    public Character getOptType() {
        return optType;
    }

    public void setOptType(Character optType) {
        this.optType = optType;
    }

    public Character getOptSide() {
        return optSide;
    }

    public void setOptSide(Character optSide) {
        this.optSide = optSide;
    }

    public Character getOptCvdFlag() {
        return optCvdFlag;
    }

    public void setOptCvdFlag(Character optCvdFlag) {
        this.optCvdFlag = optCvdFlag;
    }

    public Character getOptUndlCls() {
        return optUndlCls;
    }

    public void setOptUndlCls(Character optUndlCls) {
        this.optUndlCls = optUndlCls;
    }

    public String getOptUndlCode() {
        return optUndlCode;
    }

    public void setOptUndlCode(String optUndlCode) {
        this.optUndlCode = optUndlCode;
    }

    public long getOptPrebln() {
        return optPrebln;
    }

    public void setOptPrebln(long optPrebln) {
        this.optPrebln = optPrebln;
    }

    public long getOptBln() {
        return optBln;
    }

    public void setOptBln(long optBln) {
        this.optBln = optBln;
    }

    public long getOptAvl() {
        return optAvl;
    }

    public void setOptAvl(long optAvl) {
        this.optAvl = optAvl;
    }

    public long getOptFrz() {
        return optFrz;
    }

    public void setOptFrz(long optFrz) {
        this.optFrz = optFrz;
    }

    public long getOptUfz() {
        return optUfz;
    }

    public void setOptUfz(long optUfz) {
        this.optUfz = optUfz;
    }

    public long getOptTrdFrz() {
        return optTrdFrz;
    }

    public void setOptTrdFrz(long optTrdFrz) {
        this.optTrdFrz = optTrdFrz;
    }

    public long getOptTrdUfz() {
        return optTrdUfz;
    }

    public void setOptTrdUfz(long optTrdUfz) {
        this.optTrdUfz = optTrdUfz;
    }

    public long getOptTrdOtd() {
        return optTrdOtd;
    }

    public void setOptTrdOtd(long optTrdOtd) {
        this.optTrdOtd = optTrdOtd;
    }

    public long getOptTrdBln() {
        return optTrdBln;
    }

    public void setOptTrdBln(long optTrdBln) {
        this.optTrdBln = optTrdBln;
    }

    public long getOptClrFrz() {
        return optClrFrz;
    }

    public void setOptClrFrz(long optClrFrz) {
        this.optClrFrz = optClrFrz;
    }

    public long getOptClrUfz() {
        return optClrUfz;
    }

    public void setOptClrUfz(long optClrUfz) {
        this.optClrUfz = optClrUfz;
    }

    public long getOptClrOtd() {
        return optClrOtd;
    }

    public void setOptClrOtd(long optClrOtd) {
        this.optClrOtd = optClrOtd;
    }

    public long getOptBcost() {
        return optBcost;
    }

    public void setOptBcost(long optBcost) {
        this.optBcost = optBcost;
    }

    public long getOptBcostRlt() {
        return optBcostRlt;
    }

    public void setOptBcostRlt(long optBcostRlt) {
        this.optBcostRlt = optBcostRlt;
    }

    public long getOptPlamt() {
        return optPlamt;
    }

    public void setOptPlamt(long optPlamt) {
        this.optPlamt = optPlamt;
    }

    public long getOptPlamtRlt() {
        return optPlamtRlt;
    }

    public void setOptPlamtRlt(long optPlamtRlt) {
        this.optPlamtRlt = optPlamtRlt;
    }

    public long getOptMktVal() {
        return optMktVal;
    }

    public void setOptMktVal(long optMktVal) {
        this.optMktVal = optMktVal;
    }

    public long getOptPremium() {
        return optPremium;
    }

    public void setOptPremium(long optPremium) {
        this.optPremium = optPremium;
    }

    public long getQuotaValUsed() {
        return quotaValUsed;
    }

    public void setQuotaValUsed(long quotaValUsed) {
        this.quotaValUsed = quotaValUsed;
    }

    public long getOptMargin() {
        return optMargin;
    }

    public void setOptMargin(long optMargin) {
        this.optMargin = optMargin;
    }

    public long getOptCvdAsset() {
        return optCvdAsset;
    }

    public void setOptCvdAsset(long optCvdAsset) {
        this.optCvdAsset = optCvdAsset;
    }

    public long getOptClsProfit() {
        return optClsProfit;
    }

    public void setOptClsProfit(long optClsProfit) {
        this.optClsProfit = optClsProfit;
    }

    public long getPreClsProfit() {
        return preClsProfit;
    }

    public void setPreClsProfit(long preClsProfit) {
        this.preClsProfit = preClsProfit;
    }

    public long getOptFloatProfit() {
        return optFloatProfit;
    }

    public void setOptFloatProfit(long optFloatProfit) {
        this.optFloatProfit = optFloatProfit;
    }

    public long getOptPosiRlt() {
        return optPosiRlt;
    }

    public void setOptPosiRlt(long optPosiRlt) {
        this.optPosiRlt = optPosiRlt;
    }

    public long getOptAutoExeQty() {
        return optAutoExeQty;
    }

    public void setOptAutoExeQty(long optAutoExeQty) {
        this.optAutoExeQty = optAutoExeQty;
    }

    public long getOptClsUnmatched() {
        return optClsUnmatched;
    }

    public void setOptClsUnmatched(long optClsUnmatched) {
        this.optClsUnmatched = optClsUnmatched;
    }

    public long getOptDailyOpenRlt() {
        return optDailyOpenRlt;
    }

    public void setOptDailyOpenRlt(long optDailyOpenRlt) {
        this.optDailyOpenRlt = optDailyOpenRlt;
    }

    public long getCombedQty() {
        return combedQty;
    }

    public void setCombedQty(long combedQty) {
        this.combedQty = combedQty;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "DataOptAsset{" +
                "custCode=" + custCode +
                ", custType=" + custType +
                ", cuacctCode=" + cuacctCode +
                ", cuacctAttr=" + cuacctAttr +
                ", cuacctCls=" + cuacctCls +
                ", cuacctLvl=" + cuacctLvl +
                ", cuacctGrp=" + cuacctGrp +
                ", intOrg=" + intOrg +
                ", stkex=" + stkex +
                ", stkbd='" + stkbd + '\'' +
                ", stkpbu='" + stkpbu + '\'' +
                ", trdacct='" + trdacct + '\'' +
                ", subacctCode='" + subacctCode + '\'' +
                ", trdacctExcls=" + trdacctExcls +
                ", currency=" + currency +
                ", optNum='" + optNum + '\'' +
                ", optCode='" + optCode + '\'' +
                ", optName='" + optName + '\'' +
                ", optType=" + optType +
                ", optSide=" + optSide +
                ", optCvdFlag=" + optCvdFlag +
                ", optUndlCls=" + optUndlCls +
                ", optUndlCode='" + optUndlCode + '\'' +
                ", optPrebln=" + optPrebln +
                ", optBln=" + optBln +
                ", optAvl=" + optAvl +
                ", optFrz=" + optFrz +
                ", optUfz=" + optUfz +
                ", optTrdFrz=" + optTrdFrz +
                ", optTrdUfz=" + optTrdUfz +
                ", optTrdOtd=" + optTrdOtd +
                ", optTrdBln=" + optTrdBln +
                ", optClrFrz=" + optClrFrz +
                ", optClrUfz=" + optClrUfz +
                ", optClrOtd=" + optClrOtd +
                ", optBcost=" + optBcost +
                ", optBcostRlt=" + optBcostRlt +
                ", optPlamt=" + optPlamt +
                ", optPlamtRlt=" + optPlamtRlt +
                ", optMktVal=" + optMktVal +
                ", optPremium=" + optPremium +
                ", quotaValUsed=" + quotaValUsed +
                ", optMargin=" + optMargin +
                ", optCvdAsset=" + optCvdAsset +
                ", optClsProfit=" + optClsProfit +
                ", preClsProfit=" + preClsProfit +
                ", optFloatProfit=" + optFloatProfit +
                ", optPosiRlt=" + optPosiRlt +
                ", optAutoExeQty=" + optAutoExeQty +
                ", optClsUnmatched=" + optClsUnmatched +
                ", optDailyOpenRlt=" + optDailyOpenRlt +
                ", combedQty=" + combedQty +
                ", updTime=" + updTime +
                ", mac='" + mac + '\'' +
                '}';
    }
}
