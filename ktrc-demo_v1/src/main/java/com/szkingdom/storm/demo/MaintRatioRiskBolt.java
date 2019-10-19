package com.szkingdom.storm.demo;

import java.text.NumberFormat;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;


/**
 * Created by zhangch on 2018/4/20.
 */
public class MaintRatioRiskBolt extends BaseRichBolt {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    //private static final long serialVersionUID = 294633099452558992L;

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;

    }

    @Override
    public void execute(Tuple input) {
        //获得字节数组，再将其封装到string类中
        String strCustCode = String.valueOf(input.getValueByField("CUST_CODE")); // new String(input.getBinaryByField("CUST_CODE"));
        String strMessage = String.valueOf(input.getValueByField("MESSAGE")); //new String(input.getBinaryByField("MESSAGE"));

        if (strCustCode.length() > 0 && strMessage.length() > 0){
            this.collector.ack(input);
        }else{
            return;
        }

        NumberFormat numFmt = NumberFormat.getInstance();

        // 不使用千分位，即展示为11672283.234，而不是11,672,283.234
        numFmt.setGroupingUsed(false);

        // 设置数的小数部分所允许的最小位数
        numFmt.setMinimumFractionDigits(3);

        // 设置数的小数部分所允许的最大位数
        numFmt.setMaximumFractionDigits(5);

        String strCustInfo;
        String strShares;
        String strContracts;
        String strstr;
        String szRedisKey = "";
        String szRedisVal = "";
        String strTemp = "";
        String strCustUpdate = "";

        double dAvailable = 0.00;
        double dTrdFrz = 0.00;
        double dOutstanding = 0.00;
        double dOtdAvl = 0.00;
        long   lShareAvl = 0;
        double dShareMktVal = 0.00;
        double dMktVal = 0.00;
        double dAssetAmt = 0.00;
        double dSLCntMktVal = 0.00;
        double dDebt = 0.00;
        double dPrice;
        double dSLAmt = 0.00;
        double dShareAmt = 0.00;
        boolean bIsNew = true;
        boolean bWriteRedis = false;
        boolean bAddCust = false;

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");
        String time = format.format(date);

        // 获取用户资金
        strCustInfo = JedisUtil.getHashString("CAPITAL", strCustCode);

        if (!"".equals(strCustInfo)) {
            String[] szArrCpt = strCustInfo.split("\\}");
            for (int i = 0; i < szArrCpt.length; ++i) {
                strstr = szArrCpt[i];

                dAvailable = Double.parseDouble(strstr.substring(strstr.indexOf("AVAILABLE") + 10, strstr.indexOf(",", strstr.indexOf("AVAILABLE") + 10)));
                dTrdFrz = Double.parseDouble(strstr.substring(strstr.indexOf("TRD_FRZ") + 8, strstr.indexOf(",", strstr.indexOf("TRD_FRZ") + 8)));
                dOutstanding = Double.parseDouble(strstr.substring(strstr.indexOf("OUTSTANDING") + 12, strstr.indexOf(",", strstr.indexOf("OUTSTANDING") + 12)));
                dOtdAvl = Double.parseDouble(strstr.substring(strstr.indexOf("OTD_AVL") + 8, strstr.indexOf(",", strstr.indexOf("OTD_AVL") + 8)));
            }
        }

        // 委托
        if (strMessage.contains("req_order")){
            // 解析当前委托 , req_order{"BOARD":"0","CUSTOMER":"2013259976","MARKET":"1","ORDER_TYPE":"01","SECU_ACC":"0603250001","SECU_CODE":"60054","TRD_ID":"0B"}
            String strMarket = strMessage.substring(strMessage.indexOf("MARKET") + 9, strMessage.indexOf("\"", strMessage.indexOf("MARKET") + 9));
            String strTrdId = strMessage.substring(strMessage.indexOf("TRD_ID") + 9, strMessage.indexOf("\"", strMessage.indexOf("TRD_ID") + 9));
            String strSecuCode = strMessage.substring(strMessage.indexOf("SECU_CODE") + 12, strMessage.indexOf("\"", strMessage.indexOf("SECU_CODE") + 12));
            String strOrderType = strMessage.substring(strMessage.indexOf("ORDER_TYPE") + 13, strMessage.indexOf("\"", strMessage.indexOf("ORDER_TYPE") + 13));
            long lQty = Long.parseLong(strMessage.substring(strMessage.indexOf("QTY") + 6, strMessage.indexOf("\"", strMessage.indexOf("QTY") + 6)));

            //获取用户股份
            strShares = JedisUtil.getHashString("SHARES", strCustCode);
            // 异常数据屏蔽
            if (strShares.contains("}}")
                    || strShares.contains(",,")
                    || strShares.contains("::")
                    || strShares.contains("{{")
                    || "".equals(strShares)){
                logger.error("REQ_ORDER SHARES Data Exception CUST_CODE:" + strCustCode + "\n");
                return;
            }

            if (!"".equals(strShares)){
                String[] szArrShare = strShares.split("\\}");
                for (int n = 0; n < szArrShare.length; ++n){
                    strstr = szArrShare[n] + "}";
                    szArrShare[n] += "}";

                    String strShareMarket = strstr.substring(strstr.indexOf("MARKET") + 7, strstr.indexOf(",", strstr.indexOf("MARKET") + 7));
                    String strShareSecuCode = strstr.substring(strstr.indexOf("SECU_CODE") + 10, strstr.indexOf(",", strstr.indexOf("SECU_CODE") + 10));
                    lShareAvl = Long.parseLong(strstr.substring(strstr.indexOf("SHARE_AVL") + 10, strstr.indexOf(",", strstr.indexOf("SHARE_AVL") + 10)));
                    dShareMktVal = Double.parseDouble(strstr.substring(strstr.indexOf("MKT_VAL") + 8, strstr.indexOf(",", strstr.indexOf("MKT_VAL") + 8)));

                    if (strMarket.equals(strShareMarket)
                            && strSecuCode.equals(strShareSecuCode)
                            && !"01".equals(strOrderType)
                            && !"04".equals(strOrderType)){
                        dPrice = 0.00;
                        strTemp = JedisUtil.getHashString("STK_MKT_INFO", strShareMarket + strShareSecuCode);
                        if (!"".equals(strTemp)){
                            dPrice = Double.parseDouble(strTemp);
                        }

                        if ("0B".equals(strTrdId)){
                            // 买入
                            lShareAvl += lQty;
                            dShareMktVal = lShareAvl * dPrice;

                            dShareAmt = -lQty * dPrice;
                        }else if ("0S".equals(strTrdId)){
                            // 卖出
                            lShareAvl -= lQty;
                            dShareMktVal = lShareAvl * dPrice;

                            dShareAmt = lQty * dPrice;
                        }

                        // 更新数量和市值
                        strstr = strstr.substring(0, strstr.indexOf("SHARE_AVL") + 10) + lShareAvl + strstr.substring(strstr.indexOf(",", strstr.indexOf("SHARE_AVL") + 10));
                        szArrShare[n] = strstr.substring(0, strstr.indexOf("MKT_VAL") + 8) + numFmt.format(dShareMktVal) + strstr.substring(strstr.indexOf(",", strstr.indexOf("MKT_VAL") + 8));
                        bIsNew = false;
                        bWriteRedis = true;
                    }

                    // 单客户市值累加
                    dMktVal += dShareMktVal;
                }

                // 数组转换为字符串
                String strArrShare = StringUtils.join(szArrShare);

                // 新增委托 {MARKET:0,SECU_CODE:161207,SHARE_AVL:1000000,MKT_VAL:1000000,}
                // 融资买入之后，客户资金不变，股份增加，客户总资产增加，融资负债增加，融资可用额度减少，融资业务头寸资金减少。
                if (bIsNew
                        && ("0B".equals(strTrdId)
                            || ("0S".equals(strTrdId)
                                && !"01".equals(strOrderType)
                                && !"04".equals(strOrderType) ))){

                    dPrice = 0.00;
                    strTemp = JedisUtil.getHashString("STK_MKT_INFO", strMarket + strSecuCode);
                    if (!"".equals(strTemp)){
                        dPrice = Double.parseDouble(strTemp);
                    }

                    if ("0B".equals(strTrdId)){
                        if ("01".equals(strOrderType)){
                            // 融资买入
                            dShareMktVal = lQty * dPrice;
                        }else{
                            // 普通买入
                            dShareMktVal = lQty * dPrice;
                            dShareAmt = -lQty * dPrice;
                        }
                    }else if ("0S".equals(strTrdId)){
                        // 卖出
                        dShareMktVal = -lQty * dPrice;
                        dShareAmt = lQty * dPrice;
                    }

                    dMktVal += dShareMktVal;

                    strArrShare += ",{MARKET:" + strMarket + ",SECU_CODE:" + strSecuCode + ",SHARE_AVL:" + lQty + ",MKT_VAL:" + numFmt.format(dShareMktVal) + ",}";

                    bWriteRedis = true;

                    // 新增委托，更新SECU_CUST_CODE
                    bAddCust = true;
                }

                // 组合数据redis数据
                if (bWriteRedis){
                    szRedisKey += "[" + "SHARES:" + strCustCode + "]";
                    szRedisVal += "[" + strArrShare + "]";
                }
            }

            // 普通买入，资金减少，市值增加，卖出，资金增加，市值减少
            if (dShareAmt != 0.00){
                dAvailable += dShareAmt;
                strCustInfo = strCustInfo.substring(0, strCustInfo.indexOf("AVAILABLE") + 10) + numFmt.format(dAvailable)
                        + strCustInfo.substring(strCustInfo.indexOf(",", strCustInfo.indexOf("AVAILABLE") + 10));
                szRedisKey += "[" + "CAPITAL:" + strCustCode + "]";
                szRedisVal += "[" + strCustInfo + "]";
            }

            // 总资产
            dAssetAmt = dAvailable + dTrdFrz + dOutstanding - dOtdAvl + dMktVal;

            bIsNew = true;
            bWriteRedis = false;

            //获取用户合约 {CONTRACT_TYPE:1,MARKET:0,SECU_CODE:000002,CONTRACT_MV:0,ORDER_QTY:2000,CONTRACT_AMT:18800.00,CONTRACT_INT:90.92,CONTRACT_FEE:39.60,}
            strContracts = JedisUtil.getHashString("CONTRACTS", strCustCode);

            // 异常数据屏蔽
            if (strContracts.contains("}}")
                    || strContracts.contains(",,")
                    || strContracts.contains("::")
                    || strContracts.contains("{{")
                    || "".equals(strContracts)){
                logger.error("REQ_ORDER CONTRACTS Data Exception CUST_CODE:" + strCustCode + "\n");
                return;
            }

            if (!"".equals(strContracts)){
                String[] szArrCnt = strContracts.split("\\}");
                for (int n = 0; n < szArrCnt.length; ++n){
                    strstr = szArrCnt[n] + "}";
                    szArrCnt[n] += "}";

                    char cCntType = strstr.substring(strstr.indexOf("CONTRACT_TYPE") + 14, strstr.indexOf(",", strstr.indexOf("CONTRACT_TYPE") + 14)).charAt(0);
                    String strCntMarket = strstr.substring(strstr.indexOf("MARKET") + 7, strstr.indexOf(",", strstr.indexOf("MARKET") + 7));
                    String strCntSecuCode = strstr.substring(strstr.indexOf("SECU_CODE") + 10, strstr.indexOf(",", strstr.indexOf("SECU_CODE") + 10));
                    //double dCntMv = Double.parseDouble(strstr.substring(strstr.indexOf("CONTRACT_MV") + 12, strstr.indexOf(",", strstr.indexOf("CONTRACT_MV") + 12));
                    double dCntAmt = Double.parseDouble(strstr.substring(strstr.indexOf("CONTRACT_AMT") + 13, strstr.indexOf(",", strstr.indexOf("CONTRACT_AMT") + 13)));
                    double dCntInt = Double.parseDouble(strstr.substring(strstr.indexOf("CONTRACT_INT") + 13, strstr.indexOf(",", strstr.indexOf("CONTRACT_INT") + 13)));
                    double dCntFee = Double.parseDouble(strstr.substring(strstr.indexOf("CONTRACT_FEE") + 13, strstr.indexOf(",", strstr.indexOf("CONTRACT_FEE") + 13)));
                    long lOdrQty = Long.parseLong(strstr.substring(strstr.indexOf("ORDER_QTY") + 10, strstr.indexOf(",", strstr.indexOf("ORDER_QTY") + 10)));

                    if (cCntType == '0'){
                        // 融资合约
                        // 单客户合约负债累加
                        dDebt += dCntAmt + dCntInt + dCntFee;
                    }else{
                        // 融券合约
                        dPrice = 0.00;
                        strTemp = JedisUtil.getHashString("STK_MKT_INFO", strCntMarket + strCntSecuCode);
                        if (!"".equals(strTemp)){
                            dPrice = Double.parseDouble(strTemp);
                        }

                        dSLCntMktVal = lOdrQty * dPrice;
                        if (strMarket.equals(strCntMarket)
                                && strSecuCode.equals(strCntSecuCode)
                                && "04".equals(strOrderType)){
                            // 融券合约累计数量
                            lOdrQty += lQty;
                            dSLCntMktVal = lOdrQty * dPrice;
                            dSLAmt = lQty * dPrice;

                            // 更新数量,融券不更新市值，每次需取最新行情计算
                            szArrCnt[n] = strstr.substring(0, strstr.indexOf("ORDER_QTY") + 10) + lOdrQty + strstr.substring(strstr.indexOf(",", strstr.indexOf("ORDER_QTY") + 10));
                            bIsNew = false;
                            bWriteRedis = true;
                        }
                        dDebt += dSLCntMktVal + dCntInt + dCntFee;
                    }
                }

                // 数组转换为字符串
                String strArrCnt = StringUtils.join(szArrCnt);

                // 融资合约每次做新增操作
                // {CONTRACT_TYPE:0,MARKET:0,SECU_CODE:000001,CONTRACT_MV:0,ORDER_QTY:2000,CONTRACT_AMT:18800.00,CONTRACT_INT:90.92,CONTRACT_FEE:39.60,},
                if ("01".equals(strOrderType)){
                    dPrice = 0.00;
                    strTemp = JedisUtil.getHashString("STK_MKT_INFO", strMarket + strSecuCode);
                    if (!"".equals(strTemp)){
                        dPrice = Double.parseDouble(strTemp);
                    }

                    dDebt += lQty * dPrice;
                    strArrCnt += ",{CONTRACT_TYPE:" + 0 + ",MARKET:" + strMarket + ",SECU_CODE:" + strSecuCode + ",CONTRACT_MV:" + 0 + ",ORDER_QTY:" + lQty + ",CONTRACT_AMT:" + numFmt.format(lQty * dPrice) +",CONTRACT_INT:0.00,CONTRACT_FEE:0.00,}";
                    bWriteRedis = true;
                    bAddCust = true;
                }

                if ("04".equals(strOrderType) && bIsNew){
                    dPrice = 0.00;
                    strTemp = JedisUtil.getHashString("STK_MKT_INFO", strMarket + strSecuCode);
                    if (!"".equals(strTemp)){
                        dPrice = Double.parseDouble(strTemp);
                    }

                    dDebt += lQty * dPrice;
                    dSLAmt = lQty * dPrice;
                    strArrCnt += ",{CONTRACT_TYPE:" + 1 + ",MARKET:" + strMarket + ",SECU_CODE:" + strSecuCode + ",CONTRACT_MV:" + 0 + ",ORDER_QTY:" + lQty + ",CONTRACT_AMT:" + numFmt.format(lQty * dPrice)  +",CONTRACT_INT:0.00,CONTRACT_FEE:0.00,}";
                    bWriteRedis = true;
                    bAddCust = true;
                }

                if (bAddCust){
                    // 新增委托，更新SECU_CUST_CODE
                    strCustUpdate = "";
                    strCustUpdate = JedisUtil.getHashString("SECU_CUST_CODE", strMarket + strSecuCode);
                    if (!"".equals(strCustUpdate)){
                        if (!strCustUpdate.contains(strCustCode)){
                            szRedisKey += "[" + "SECU_CUST_CODE:" + strMarket + strSecuCode + "]";
                            strCustUpdate += strCustCode;
                            szRedisVal += "[" + strCustUpdate + "," + "]";
                        }
                    }else{
//                        // 首次录入
//                        szRedisKey += "[" + "SECU_CUST_CODE:" + strMarket + strSecuCode + "]";
//                        strCustUpdate += strCustCode;
//                        szRedisVal += "[" + strCustUpdate + "," + "]";
                    }
                }

                if (bWriteRedis){
                    szRedisKey += "[" + "CONTRACTS:" + strCustCode + "]";
                    szRedisVal += "[" + strArrCnt + "]";
                }
            }

            // 融券卖出之后，客户资金增加，股份不变，总资产增加，融券负债增加，融券可用额度减少，融券业务头寸股份扣减
            if (dSLAmt != 0.00){
                dAvailable += dSLAmt;
                strCustInfo = strCustInfo.substring(0, strCustInfo.indexOf("AVAILABLE") + 10) + numFmt.format(dAvailable)
                                    + strCustInfo.substring(strCustInfo.indexOf(",", strCustInfo.indexOf("AVAILABLE") + 10));
                szRedisKey += "[" + "CAPITAL:" + strCustCode + "]";
                szRedisVal += "[" + strCustInfo + "]";

                dAssetAmt += dSLAmt;
            }

            // 计算维保
            double dMaintRatio = dDebt < 0.0001 ? -1.0000 : (dAssetAmt < 0.001 ? 0.0000 : (dAssetAmt / dDebt > 9999999.9999 ? 9999999.9999 : dAssetAmt / dDebt));
            String strType;
            if (dMaintRatio > 1.5) {
                szRedisKey += "[" + "MONITOR_NORMAL:" + strCustCode + "]";
                strType = "NORMAL";
            }else{
                szRedisKey += "[" + "MONITOR_RISK:" + strCustCode + "]";
                strType = dMaintRatio > 1.3 ? "ALERT" : "LQDT";
            }

            String strMaintRatio = String.format("%.2f", dMaintRatio);
            szRedisVal += "[" + "MAINT_RATIO:" + strMaintRatio + ",ASSET_AMT:" + numFmt.format(dAssetAmt) + ",DEBT:" + numFmt.format(dDebt) + ",ALERT_RATIO:" + "150%" + ",LQDT_RATIO:" + "130%" + ",TIME:" + time + ",RISK_TYPE:" + strType + "]";

            //发给下一个订阅的bolt
            this.collector.emit(new Values(szRedisKey, szRedisVal));
        }
        else if (strMessage.contains("req_hq")){
            // 行情
            dPrice = Double.parseDouble(strMessage.substring(strMessage.indexOf("CUR_PR") + 8, strMessage.indexOf("\"", strMessage.indexOf("CUR_PR") + 8)));
            String strMarket = strMessage.substring(strMessage.indexOf("MKT") + 5, strMessage.indexOf("\"", strMessage.indexOf("MKT") + 5));
            String strSecuCode = strMessage.substring(strMessage.indexOf("CODE") + 6, strMessage.indexOf("\"", strMessage.indexOf("CODE") + 6));

            //行情数据更新,发给下一个订阅的bolt，此处有延迟，最好另起bolt处理
            szRedisKey += "[" + "STK_MKT_INFO:" + strMarket + strSecuCode + "]";
            szRedisVal += "[" + dPrice + "]";
            this.collector.emit(new Values(szRedisKey, szRedisVal));
            szRedisKey = "";
            szRedisVal = "";

            bWriteRedis = false;

            //获取用户股份
            strShares = JedisUtil.getHashString("SHARES", strCustCode);

            // 异常数据屏蔽
            if (strShares.contains("}}")
                    || strShares.contains(",,")
                    || strShares.contains("::")
                    || strShares.contains("{{")
                    || "".equals(strShares)){
                logger.error("REQ_HQ SHARES Data Exception CUST_CODE:" + strCustCode + "\n");
                return;
            }

            if (!"".equals(strShares)) {
                String[] szArrShare = strShares.split("\\}");
                for (int n = 0; n < szArrShare.length; ++n) {
                    strstr = szArrShare[n] + "}";
                    szArrShare[n] += "}";

                    String strShareMarket = strstr.substring(strstr.indexOf("MARKET") + 7, strstr.indexOf(",", strstr.indexOf("MARKET") + 7));
                    String strShareSecuCode = strstr.substring(strstr.indexOf("SECU_CODE") + 10, strstr.indexOf(",", strstr.indexOf("SECU_CODE") + 10));
                    lShareAvl = Long.parseLong(strstr.substring(strstr.indexOf("SHARE_AVL") + 10, strstr.indexOf(",", strstr.indexOf("SHARE_AVL") + 10)));
                    dShareMktVal = Double.parseDouble(strstr.substring(strstr.indexOf("MKT_VAL") + 8, strstr.indexOf(",", strstr.indexOf("MKT_VAL") + 8)));

                    // 同一证券，用最新行情计算
                    if (strMarket.equals(strShareMarket) && strSecuCode.equals(strShareSecuCode)) {
                        dShareMktVal = lShareAvl * dPrice;

                        // 更新市值
                        szArrShare[n] = strstr.substring(0, strstr.indexOf("MKT_VAL") + 8) + numFmt.format(dShareMktVal) + strstr.substring(strstr.indexOf(",", strstr.indexOf("MKT_VAL") + 8));
                        bWriteRedis = true;
                    }

                    // 单客户市值累加
                    dMktVal += dShareMktVal;
                }

                // 组合数据redis数据
                if (bWriteRedis) {
                    szRedisKey += "[" + "SHARES:" + strCustCode + "]";
                    szRedisVal += "[" + StringUtils.join(szArrShare) + "]";
                }
            }

            // 总资产
            dAssetAmt = dAvailable + dTrdFrz + dOutstanding - dOtdAvl + dMktVal;

            //获取用户合约 {CONTRACT_TYPE:1,MARKET:0,SECU_CODE:000002,CONTRACT_MV:0,ORDER_QTY:2000,CONTRACT_AMT:18800.00,CONTRACT_INT:90.92,CONTRACT_FEE:39.60,}
            strContracts = JedisUtil.getHashString("CONTRACTS", strCustCode);

            // 异常数据屏蔽
            if (strContracts.contains("}}")
                    || strContracts.contains(",,")
                    || strContracts.contains("::")
                    || strContracts.contains("{{")
                    || "".equals(strContracts)){
                logger.error("REQ_HQ CONTRACTS Data Exception CUST_CODE:" + strCustCode + "\n");
                return;
            }

            if (!"".equals(strContracts)) {
                String[] szArrCnt = strContracts.split("\\}");
                for (int n = 0; n < szArrCnt.length; ++n) {
                    strstr = szArrCnt[n];

                    char cCntType = strstr.substring(strstr.indexOf("CONTRACT_TYPE") + 14, strstr.indexOf(",", strstr.indexOf("CONTRACT_TYPE") + 14)).charAt(0);
                    String strCntMarket = strstr.substring(strstr.indexOf("MARKET") + 7, strstr.indexOf(",", strstr.indexOf("MARKET") + 7));
                    String strCntSecuCode = strstr.substring(strstr.indexOf("SECU_CODE") + 10, strstr.indexOf(",", strstr.indexOf("SECU_CODE") + 10));
                    double dCntAmt = Double.parseDouble(strstr.substring(strstr.indexOf("CONTRACT_AMT") + 13, strstr.indexOf(",", strstr.indexOf("CONTRACT_AMT") + 13)));
                    double dCntInt = Double.parseDouble(strstr.substring(strstr.indexOf("CONTRACT_INT") + 13, strstr.indexOf(",", strstr.indexOf("CONTRACT_INT") + 13)));
                    double dCntFee = Double.parseDouble(strstr.substring(strstr.indexOf("CONTRACT_FEE") + 13, strstr.indexOf(",", strstr.indexOf("CONTRACT_FEE") + 13)));
                    long lOdrQty = Long.parseLong(strstr.substring(strstr.indexOf("ORDER_QTY") + 10, strstr.indexOf(",", strstr.indexOf("ORDER_QTY") + 10)));

                    if (cCntType == '0') {
                        // 融资合约
                        // 单客户合约负债累加
                        dDebt += dCntAmt + dCntInt + dCntFee;
                    } else {
                        // 融券合约
                        if (strMarket.equals(strCntMarket) && strSecuCode.equals(strCntSecuCode)) {
                            // 证券相同，用传入的行情价格
                            dSLCntMktVal = lOdrQty * dPrice;
                        } else {
                            // 证券不同查行情数据
                            double dPriceCnt = 0.00;
                            strTemp = JedisUtil.getHashString("STK_MKT_INFO", strCntMarket + strCntSecuCode);
                            if (!"".equals(strTemp)) {
                                dPriceCnt = Double.parseDouble(strTemp);
                            }

                            dSLCntMktVal = lOdrQty * dPriceCnt;
                        }
                        dDebt += dSLCntMktVal + dCntInt + dCntFee;
                    }
                }
            }

            // 计算维保
            double dMaintRatio = dDebt < 0.0001 ? -1.0000 : (dAssetAmt < 0.001 ? 0.0000 : (dAssetAmt / dDebt > 9999999.9999 ? 9999999.9999 : dAssetAmt / dDebt));
            String strType;
            if (dMaintRatio > 1.5) {
                szRedisKey += "[" + "MONITOR_NORMAL:" + strCustCode + "]";
                strType = "NORMAL";
            } else {
                szRedisKey += "[" + "MONITOR_RISK:" + strCustCode + "]";
                strType = dMaintRatio > 1.3 ? "ALERT" : "LQDT";
            }

            String strMaintRatio = String.format("%.2f", dMaintRatio);
            szRedisVal += "[" + "MAINT_RATIO:" + strMaintRatio + ",ASSET_AMT:" + numFmt.format(dAssetAmt) + ",DEBT:" + numFmt.format(dDebt) + ",ALERT_RATIO:" + "150%" + ",LQDT_RATIO:" + "130%" + ",TIME:" + time + ",RISK_TYPE:" + strType + "]";

            //发给下一个订阅的bolt
            this.collector.emit(new Values(szRedisKey, szRedisVal));
        }
        else{
            logger.error("Unknown data：" + strMessage);
            return;
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("REDIS_KEY", "REDIS_VAL"));
    }
}
