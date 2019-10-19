/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationCheckParam
 * Author:   ZhangChangHong
 * Date:     2018/8/27
 * Description: 接口入参校验类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.NumConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.List;
// 后期合并
import com.szkingdom.common.ZtsConstantUtil;


public class OperationCheckParam extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationCheckParam.class);

    public static boolean checkParamValidity(JSONObject jsonObject, StringBuilder stringBuilder, List<Object> listObjectParam) throws Exception {
        try {
            JSONObject jsonData = jsonObject.getJSONObject(ConstantUtil.JSON_KTRC);

            if (jsonData == null) {
                logger.error("checkParamValidity() Json解析异常，无KTRC数据:{}", jsonObject.toString());
                return false;
            }

            String dataID = jsonData.getString("DATA_ID");
            if ("".equals(dataID.trim())) {
                logger.error("checkParamValidity() Json解析异常，无SYS_DATA_TYPE数据:{}", jsonObject.toString());
                return false;
            }
            stringBuilder.append(dataID);

            if (ConstantUtil.SPOT_QUOTATION.equals(dataID)) {
                return checkSpotQuotationParam(jsonData, listObjectParam);

            } else if (ConstantUtil.OPT_QUOTATION.equals(dataID)) {
                return checkOptQuotationParam(jsonData, listObjectParam);

            } else if (ConstantUtil.OPT_TRADE.equals(dataID)) {
                return checkOptTradeParam(jsonData, listObjectParam);

            } else if (ConstantUtil.OPT_MATCH.equals(dataID)) {
                return checkOptMatchParam(jsonData, listObjectParam);

            } else if (ConstantUtil.OPT_FUND.equals(dataID)) {
                return checkOptFundParam(jsonData, listObjectParam);

            } else if (ConstantUtil.OPT_ASSET.equals(dataID)) {
                return checkOptAssetParam(jsonData, listObjectParam);

            } else if (ConstantUtil.OPT_MARGIN_CORP.equals(dataID)) {
                return checkOptMarginCorpParam(jsonData, listObjectParam);

            } else if (ConstantUtil.STK_TRADE.equals(dataID)) {
                return checkStkTradeParam(jsonData, listObjectParam);

            } else if (ConstantUtil.STK_MATCH.equals(dataID)) {
                return checkStkMatchParam(jsonData, listObjectParam);
                // 基金资产
            }  else if (ZtsConstantUtil.STK_FUNDASSET.equals(dataID)) {
                return checkFundAssetParam(jsonData, listObjectParam);

                // 电子客户信息
            }  else if (ZtsConstantUtil.STK_ECCODESIGN.equals(dataID)) {
                return checkEccodesignParam(jsonData, listObjectParam);
            } else {
                logger.error("checkParamValidity() Json解析异常，SYS_DATA_TYPE匹配失败:{}", jsonObject.toString());
                return false;
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkOptTradeParam(JSONObject jsonData, List<Object> listObjectParam) throws Exception {
        try {
            // 入参校验
            OperationDataIdOptTrade operationDataIdOptTrade = new OperationDataIdOptTrade();
            String valueTmp = "";

            valueTmp = jsonData.getString("DATA_ID");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setDataId(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[DATA_ID]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TRD_DATE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setTrdDate(Integer.parseInt(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[TRD_DATE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_DATE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setOrderDate(Integer.parseInt(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[ORDER_DATE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_TIME");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
                operationDataIdOptTrade.setOrderTime(simpleDateFormat.parse(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[ORDER_TIME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUST_CODE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setCustCode(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[CUST_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUST_NAME");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setCustName(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[CUST_NAME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_CUST_TYPE");
            if (valueTmp != null && !valueTmp.isEmpty() && valueTmp.charAt(0) != ' ') {
                operationDataIdOptTrade.setOptCustType(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_CUST_TYPE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUST_TYPE");
            if (valueTmp != null && !valueTmp.isEmpty() && valueTmp.charAt(0) != ' ') {
                operationDataIdOptTrade.setCustType(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[CUST_TYPE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUACCT_CODE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setCuacctCode(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[CUACCT_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CURRENCY");
            if (valueTmp != null && !valueTmp.isEmpty() && valueTmp.charAt(0) != ' ') {
                operationDataIdOptTrade.setCurrency(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[CURRENCY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STKBD");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setStkbd(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[STKBD]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TRDACCT");
            if (valueTmp != null  && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setTrdacct(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[TRDACCT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SUBACCT_CODE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setSubacctCode(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[SUBACCT_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TRDACCT_EXCLS");
            if (valueTmp != null && !valueTmp.isEmpty() && valueTmp.charAt(0) != ' ') {
                operationDataIdOptTrade.setTrdacctExcls(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[TRDACCT_EXCLS]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_TRDACCT_LVL");
            if (valueTmp != null && !valueTmp.isEmpty() && valueTmp.charAt(0) != ' ') {
                operationDataIdOptTrade.setOptTrdacctLvl(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_TRDACCT_LVL]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STKPBU");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setStkpbu(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[STKPBU]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("INT_ORG");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setIntOrg(Short.parseShort(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[INT_ORG]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("IS_WITHDRAW");
            if (valueTmp != null && !valueTmp.isEmpty() && valueTmp.charAt(0) != ' ') {
                operationDataIdOptTrade.setIsWithdraw(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[IS_WITHDRAW]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_BIZ");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setStkBiz(Short.parseShort(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[STK_BIZ]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_BIZ_ACTION");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setStkBizAction(Short.parseShort(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[STK_BIZ_ACTION]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_ID");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setOrderId(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[ORDER_ID]为空:{}", jsonData.toString());
                return false;
            }

            if (operationDataIdOptTrade.getIsWithdraw().equals(ConstantUtil.WITHDRAW)) {
                valueTmp = jsonData.getString("RAW_ORDER_ID");
                if (valueTmp != null && !valueTmp.isEmpty()) {
                    operationDataIdOptTrade.setRawOrderId(valueTmp);
                } else {
                    logger.error("交易申报参数解析异常，字段[RAW_ORDER_ID]为空:{}", jsonData.toString());
                    return false;
                }
            }

            valueTmp = jsonData.getString("ORDER_STATUS");
            if (valueTmp != null && !valueTmp.isEmpty() && valueTmp.charAt(0) != ' ') {
                operationDataIdOptTrade.setOrderStatus(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[ORDER_STATUS]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_PRICE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                long orderPrice = NumConvertUtil.priceToLong(Double.parseDouble(valueTmp));
                if (orderPrice > 0) {
                    operationDataIdOptTrade.setOrderPrice(orderPrice);
                } else {
                    logger.error("交易申报参数解析异常，字段[ORDER_PRICE]值不正确:{}", jsonData.toString());
                    return false;
                }

            } else {
                logger.error("交易申报参数解析异常，字段[ORDER_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_QTY");
            if (valueTmp != null) {
                long orderQty = Long.parseLong(valueTmp);
                if (orderQty > 0) {
                    operationDataIdOptTrade.setOrderQty(orderQty);
                } else {
                    logger.error("交易申报参数解析异常，字段[ORDER_QTY]值不正确:{}", jsonData.toString());
                    return false;
                }
            } else {
                logger.error("交易申报参数解析异常，字段[ORDER_QTY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_FRZ_AMT");
            if (valueTmp != null) {
                operationDataIdOptTrade.setOrderFrzAmt(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[ORDER_FRZ_AMT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_NUM");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setOptNum(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_NUM]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_CODE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setOptCode(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_NAME");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setOptName(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_NAME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_TYPE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setOptType(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_TYPE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_UNIT");
            if (valueTmp != null) {
                long optUnit = Long.parseLong(valueTmp);
                if (optUnit > 0) {
                    operationDataIdOptTrade.setOptUnit(optUnit);
                } else {
                    logger.error("交易申报参数解析异常，字段[OPT_UNIT]值不正确:{}", jsonData.toString());
                    return false;
                }
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_UNIT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_UNDL_CODE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setOptUndlCode(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_UNDL_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_UNDL_NAME");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setOptUndlName(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_UNDL_NAME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_UNDL_CLS");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setOptUndlCls(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_UNDL_CLS]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("FUND_BLN_VARY");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setFundBlnVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[FUND_BLN_VARY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("FUND_AVL_VARY");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptTrade.setFundAvlVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[FUND_AVL_VARY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MARGIN_USED_VARY");
            if (valueTmp != null) {
                operationDataIdOptTrade.setMarginUsedVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[MARGIN_USED_VARY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MARGIN_INCL_RLT_VARY");
            if (valueTmp != null) {
                operationDataIdOptTrade.setMarginInclRltVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[MARGIN_INCL_RLT_VARY]为空:{}", jsonData.toString());
                return false;
            }

            listObjectParam.add(operationDataIdOptTrade);
            return true;

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkOptMatchParam(JSONObject jsonData, List<Object> listObjectParam) throws Exception {
        try {
            // 入参校验
            OperationDataIdOptMatch operationDataIdOptMatch = new OperationDataIdOptMatch();
            String valueTmp = "";

            valueTmp = jsonData.getString("DATA_ID");
            if (valueTmp != null) {
                operationDataIdOptMatch.setDataId(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[DATA_ID]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TRD_DATE");
            if (valueTmp != null) {
                operationDataIdOptMatch.setTrdDate(Integer.parseInt(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[TRD_DATE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MATCHED_TIME");
            if (valueTmp != null) {
                operationDataIdOptMatch.setMatchedTime(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[MATCHED_TIME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUST_CODE");
            if (valueTmp != null) {
                operationDataIdOptMatch.setCustCode(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[CUST_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUST_NAME");
            if (valueTmp != null) {
                operationDataIdOptMatch.setCustName(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[CUST_NAME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_CUST_TYPE");
            if (valueTmp != null) {
                operationDataIdOptMatch.setOptCustType(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[USER_TYPE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUACCT_CODE");
            if (valueTmp != null) {
                operationDataIdOptMatch.setCuacctCode(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[CUACCT_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CURRENCY");
            if (valueTmp != null) {
                operationDataIdOptMatch.setCurrency(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[CURRENCY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STKBD");
            if (valueTmp != null) {
                operationDataIdOptMatch.setStkbd(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[STKBD]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TRDACCT");
            if (valueTmp != null) {
                operationDataIdOptMatch.setTrdacct(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[TRDACCT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SUBACCT_CODE");
            if (valueTmp != null) {
                operationDataIdOptMatch.setSubacctCode(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[SUBACCT_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STKPBU");
            if (valueTmp != null) {
                operationDataIdOptMatch.setStkpbu(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[STKPBU]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("INT_ORG");
            if (valueTmp != null) {
                operationDataIdOptMatch.setIntOrg(Short.parseShort(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[INT_ORG]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MATCHED_SN");
            if (valueTmp != null) {
                operationDataIdOptMatch.setMatchedSn(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[MATCHED_SN]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_ID");
            if (valueTmp != null) {
                operationDataIdOptMatch.setOrderId(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[ORDER_ID]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MATCHED_QTY");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptMatch.setMatchedQty(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[MATCHED_QTY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MATCHED_PRICE");
            if (valueTmp != null) {
                long matchedPrice = NumConvertUtil.priceToLong(Double.parseDouble(valueTmp));
                if (matchedPrice > 0) {
                    operationDataIdOptMatch.setMatchedPrice(matchedPrice);
                } else {
                    logger.error("交易申报参数解析异常，字段[MATCHED_PRICE]值不正确:{}", jsonData.toString());
                    return false;
                }
            } else {
                logger.error("交易申报参数解析异常，字段[MATCHED_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("IS_WITHDRAW");
            if (valueTmp != null) {
                operationDataIdOptMatch.setIsWithdraw(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[IS_WITHDRAW]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_BIZ");
            if (valueTmp != null) {
                operationDataIdOptMatch.setStkBiz(Short.parseShort(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[STK_BIZ]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_BIZ_ACTION");
            if (valueTmp != null) {
                operationDataIdOptMatch.setStkBizAction(Short.parseShort(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[STK_BIZ_ACTION]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MATCHED_TYPE");
            if (valueTmp != null) {
                operationDataIdOptMatch.setMatchedType(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[MATCHED_TYPE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_NUM");
            if (valueTmp != null) {
                operationDataIdOptMatch.setOptNum(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_NUM]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_CODE");
            if (valueTmp != null) {
                operationDataIdOptMatch.setOptCode(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_NAME");
            if (valueTmp != null) {
                operationDataIdOptMatch.setOptName(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_NAME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_TYPE");
            if (valueTmp != null) {
                operationDataIdOptMatch.setOptType(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_TYPE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_UNIT");
            if (valueTmp != null) {
                operationDataIdOptMatch.setOptUnit(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_UNIT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_UNDL_CODE");
            if (valueTmp != null) {
                operationDataIdOptMatch.setOptUndlCode(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_UNDL_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_UNDL_NAME");
            if (valueTmp != null) {
                operationDataIdOptMatch.setOptUndlName(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_UNDL_NAME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_UNDL_CLS");
            if (valueTmp != null) {
                operationDataIdOptMatch.setOptUndlCls(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_UNDL_CLS]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("COMB_NUM");
            if (valueTmp != null) {
                operationDataIdOptMatch.setCombNum(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[COMB_NUM]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("COMB_STRA_CODE");
            if (valueTmp != null) {
                operationDataIdOptMatch.setCombStraCode(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[COMB_STRA_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("LEG1_NUM");
            if (valueTmp != null) {
                operationDataIdOptMatch.setLeg1Num(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[LEG1_NUM]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("LEG2_NUM");
            if (valueTmp != null) {
                operationDataIdOptMatch.setLeg2Num(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[LEG2_NUM]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("FUND_BLN_VARY");
            if (valueTmp != null  && !valueTmp.isEmpty()) {
                operationDataIdOptMatch.setFundBlnVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[FUND_BLN_VARY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("FUND_AVL_VARY");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptMatch.setFundAvlVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[FUND_AVL_VARY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MARGIN_USED_VARY");
            if (valueTmp != null  && !valueTmp.isEmpty()) {
                operationDataIdOptMatch.setMarginUsedVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[MARGIN_USED_VARY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MARGIN_INCL_RLT_VARY");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptMatch.setMarginInclRltVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[MARGIN_INCL_RLT_VARY]为空:{}", jsonData.toString());
                return false;
            }

            listObjectParam.add(operationDataIdOptMatch);
            return true;

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkOptFundParam(JSONObject jsonData, List<Object> listObjectParam) throws Exception {
        try {
            // 入参校验
            OperationDataIdOptFund operationDataIdOptFund= new OperationDataIdOptFund();
            String valueTmp = "";

            operationDataIdOptFund.setSubsysSn((short) 0);

            valueTmp = jsonData.getString("DATA_ID");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptFund.setDataId(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[DATA_ID]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SERIAL_NO");
            if (valueTmp != null) {
                operationDataIdOptFund.setSerialNo(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[SERIAL_NO]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OCC_DATE");
            if (valueTmp != null) {
                operationDataIdOptFund.setOccDate(Integer.parseInt(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[OCC_DATE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OCC_TIME");
            if (valueTmp != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
                operationDataIdOptFund.setOccTime(simpleDateFormat.parse(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[OCC_TIME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUST_CODE");
            if (valueTmp != null) {
                operationDataIdOptFund.setCustCode(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[CUST_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUACCT_CODE");
            if (valueTmp != null) {
                operationDataIdOptFund.setCuacctCode(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[CUACCT_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CURRENCY");
            if (valueTmp != null) {
                operationDataIdOptFund.setCurrency(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[CURRENCY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("INT_ORG");
            if (valueTmp != null) {
                operationDataIdOptFund.setIntOrg(Short.parseShort(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[INT_ORG]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BIZ_CODE");
            if (valueTmp != null) {
                operationDataIdOptFund.setBizCode(Integer.parseInt(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[BIZ_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("FUND_CHANGE_FLAG");
            if (valueTmp != null) {
                operationDataIdOptFund.setFundChangeFlag(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[FUND_CHANGE_FLAG]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("FUND_BLN");
            if (valueTmp != null) {
                operationDataIdOptFund.setFundBln(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[FUND_BLN]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("FUND_BLN_VARY");
            if (valueTmp != null) {
                operationDataIdOptFund.setFundBlnVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[FUND_BLN_VARY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("FUND_AVL_VARY");
            if (valueTmp != null) {
                operationDataIdOptFund.setFundAvlVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[FUND_AVL_VARY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("DAILY_IN_AMT");
            if (valueTmp != null) {
                operationDataIdOptFund.setDailyInAmt(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[DAILY_IN_AMT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("DAILY_OUT_AMT");
            if (valueTmp != null) {
                operationDataIdOptFund.setDailyOutAmt(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[DAILY_OUT_AMT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORIGINAL_SN");
            if (valueTmp != null) {
                operationDataIdOptFund.setOriginalSn(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[ORIGINAL_SN]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OP_USER");
            if (valueTmp != null) {
                operationDataIdOptFund.setOpUser(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[OP_USER]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OP_ROLE");
            if (valueTmp != null) {
                operationDataIdOptFund.setOpRole(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[OP_ROLE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OP_NAME");
            if (valueTmp != null) {
                operationDataIdOptFund.setOpName(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OP_NAME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OP_ORG");
            if (valueTmp != null) {
                operationDataIdOptFund.setOpOrg(Short.parseShort(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[OP_ORG]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OP_SITE");
            if (valueTmp != null) {
                operationDataIdOptFund.setOpSite(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OP_SITE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CHANNEL");
            if (valueTmp != null) {
                operationDataIdOptFund.setChannel(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[CHANNEL]为空:{}", jsonData.toString());
                return false;
            }

            listObjectParam.add(operationDataIdOptFund);
            return true;

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkOptAssetParam(JSONObject jsonData, List<Object> listObjectParam) throws Exception {
        try {
            // 入参校验
            OperationDataIdOptAsset operationDataIdOptAsset = new OperationDataIdOptAsset();
            String valueTmp = "";

            operationDataIdOptAsset.setSubsysSn((short) 0);

            valueTmp = jsonData.getString("DATA_ID");
            if (valueTmp != null) {
                operationDataIdOptAsset.setDataId(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[DATA_ID]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SERIAL_NO");
            if (valueTmp != null) {
                operationDataIdOptAsset.setSerialNo(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[SERIAL_NO]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OCC_DATE");
            if (valueTmp != null) {
                operationDataIdOptAsset.setOccDate(Integer.parseInt(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[OCC_DATE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OCC_TIME");
            if (valueTmp != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
                operationDataIdOptAsset.setOccTime(simpleDateFormat.parse(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[OCC_TIME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUST_CODE");
            if (valueTmp != null) {
                operationDataIdOptAsset.setCustCode(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[CUST_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUACCT_CODE");
            if (valueTmp != null) {
                operationDataIdOptAsset.setCuacctCode(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[CUACCT_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("INT_ORG");
            if (valueTmp != null) {
                operationDataIdOptAsset.setIntOrg(Short.parseShort(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[INT_ORG]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STKBD");
            if (valueTmp != null) {
                operationDataIdOptAsset.setStkbd(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[STKBD]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TRDACCT");
            if (valueTmp != null) {
                operationDataIdOptAsset.setTrdacct(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[TRDACCT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_NUM");
            if (valueTmp != null) {
                operationDataIdOptAsset.setOptNum(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_NUM]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STKPBU");
            if (valueTmp != null) {
                operationDataIdOptAsset.setStkpbu(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[STKPBU]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_SIDE");
            if (valueTmp != null) {
                operationDataIdOptAsset.setOptSide(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_SIDE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BIZ_CODE");
            if (valueTmp != null) {
                operationDataIdOptAsset.setBizCode(Integer.parseInt(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[BIZ_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_CHANGE_FLAG");
            if (valueTmp != null) {
                operationDataIdOptAsset.setOptChangeFlag(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_CHANGE_FLAG]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_BLN_VARY");
            if (valueTmp != null) {
                operationDataIdOptAsset.setOptBlnVary(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_BLN_VARY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_AVL_VARY");
            if (valueTmp != null) {
                operationDataIdOptAsset.setOptAvlVary(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_AVL_VARY]为空:{}", jsonData.toString());
                return false;
            }

            listObjectParam.add(operationDataIdOptAsset);
            return true;

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkOptMarginCorpParam(JSONObject jsonData, List<Object> listObjectParam) throws Exception {
        try {
            // 入参校验
            OperationDataIdOptMarginCorp operationDataIdOptMarginCorp= new OperationDataIdOptMarginCorp();
            String valueTmp = "";

            valueTmp = jsonData.getString("DATA_ID");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdOptMarginCorp.setDataId(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[DATA_ID]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STKBD");
            if (valueTmp != null) {
                operationDataIdOptMarginCorp.setStkbd(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[STKBD]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MARGIN_CORP_ACCT");
            if (valueTmp != null) {
                operationDataIdOptMarginCorp.setMarginCorpAcct(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[MARGIN_CORP_ACCT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CURRENCY");
            if (valueTmp != null) {
                operationDataIdOptMarginCorp.setCurrency(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[CURRENCY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MARGIN_USED");
            if (valueTmp != null) {
                operationDataIdOptMarginCorp.setMarginUsed(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[MARGIN_USED]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MARGIN_AVL");
            if (valueTmp != null) {
                operationDataIdOptMarginCorp.setMarginAvl(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[MARGIN_AVL]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MARGIN_TOTAL_AMT");
            if (valueTmp != null) {
                operationDataIdOptMarginCorp.setMarginTotalAmt(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[MARGIN_TOTAL_AMT]为空:{}", jsonData.toString());
                return false;
            }

            listObjectParam.add(operationDataIdOptMarginCorp);
            return true;

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkSpotQuotationParam(JSONObject jsonData, List<Object> listObjectParam) throws Exception {
        try {
            // 入参校验
            OperationDataIdStkMktinfo operationDataIdStkMktinfo = new OperationDataIdStkMktinfo();
            String valueTmp = "";

            valueTmp = jsonData.getString("STKEX");
            if (valueTmp != null) {
                operationDataIdStkMktinfo.setStkex(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[STKEX]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STKBD");
            if (valueTmp != null) {
                operationDataIdStkMktinfo.setStkbd(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[STKBD]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_CODE");
            if (valueTmp != null) {
                operationDataIdStkMktinfo.setStkCode(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[STK_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TRD_DATE");
            if (valueTmp != null) {
                operationDataIdStkMktinfo.setTrdDate(Integer.parseInt(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[TRD_DATE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CLOSING_PRICE");
            if (valueTmp != null) {
                operationDataIdStkMktinfo.setClosingPrice(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[CLOSING_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPENING_PRICE");
            if (valueTmp != null) {
                operationDataIdStkMktinfo.setOpeningPrice(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[OPENING_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CURRENT_PRICE");
            if (valueTmp != null) {
                operationDataIdStkMktinfo.setCurrentPrice(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[CURRENT_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BOND_INT");
            if (valueTmp != null) {
                operationDataIdStkMktinfo.setBondInt(NumConvertUtil.rateToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[BOND_INT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BOND_INT_DATE");
            if (valueTmp != null) {
                operationDataIdStkMktinfo.setBondIntDate(Integer.parseInt(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[BOND_INT_DATE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ETF_IOPV");
            if (valueTmp != null) {
                operationDataIdStkMktinfo.setEtfIopv(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[ETF_IOPV]为空:{}", jsonData.toString());
                return false;
            }

            listObjectParam.add(operationDataIdStkMktinfo);
            return true;

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkOptQuotationParam(JSONObject jsonData, List<Object> listObjectParam) throws Exception {
        try {
            // 入参校验
            OperationDataIdOptMktinfo operationDataIdOptMktinfo = new OperationDataIdOptMktinfo();
            String valueTmp = "";

            valueTmp = jsonData.getString("STKEX");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setStkex(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[STKEX]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STKBD");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setStkbd(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[STKBD]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_NUM");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setOptNum(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_NUM]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_CODE");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setOptCode(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TRD_DATE");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setTrdDate(Integer.parseInt(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[TRD_DATE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TOTAL_AMT");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setTotalAmt(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[TOTAL_AMT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TOTAL_VOLUME");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setTotalVolume(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[TOTAL_VOLUME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("PRE_CLOSE_PX");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setPreClosePx(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[PRE_CLOSE_PX]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("PRE_SETT_PRICE");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setPreSettPrice(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[PRE_SETT_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_OPEN_PRICE");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setOptOpenPrice(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_OPEN_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_CURR_PRICE");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setOptCurrPrice(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_CURR_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_HIGH_PRICE");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setOptHighPrice(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_HIGH_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_LOW_PRICE");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setOptLowPrice(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_LOW_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_TRD_PRICE");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setOptTrdPrice(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_TRD_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("LEAVES_QTY");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setLeavesQty(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[LEAVES_QTY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_AUCT_PRICE");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setOptAuctPrice(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_AUCT_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_AUCT_QTY");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setOptAuctQty(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_AUCT_QTY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("OPT_SETT_PRICE");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setOptSettPrice(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[OPT_SETT_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BUY_PRICE1");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setBuyPrice1(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[BUY_PRICE1]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BUY_VOLUME1");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setBuyVolume1(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[BUY_VOLUME1]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SELL_PRICE1");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setSellPrice1(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[SELL_PRICE1]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SELL_VOLUME1");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setSellVolume1(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[SELL_VOLUME1]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BUY_PRICE2");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setBuyPrice2(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[BUY_PRICE2]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BUY_VOLUME2");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setBuyVolume2(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[BUY_VOLUME2]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SELL_PRICE2");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setSellPrice2(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[SELL_PRICE2]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SELL_VOLUME2");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setSellVolume2(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[SELL_VOLUME2]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BUY_PRICE3");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setBuyPrice3(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[BUY_PRICE3]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BUY_VOLUME3");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setBuyVolume3(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[BUY_VOLUME3]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SELL_PRICE3");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setSellPrice3(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[SELL_PRICE3]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SELL_VOLUME3");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setSellVolume3(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[SELL_VOLUME3]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BUY_PRICE4");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setBuyPrice4(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[BUY_PRICE4]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BUY_VOLUME4");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setBuyVolume4(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[BUY_VOLUME4]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SELL_PRICE4");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setSellPrice4(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[SELL_PRICE4]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SELL_VOLUME4");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setSellVolume4(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[SELL_VOLUME4]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BUY_PRICE5");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setBuyPrice5(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[BUY_PRICE5]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("BUY_VOLUME5");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setBuyVolume5(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[BUY_VOLUME5]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SELL_PRICE5");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setSellPrice5(NumConvertUtil.priceToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("交易申报参数解析异常，字段[SELL_PRICE5]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("SELL_VOLUME5");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setSellVolume5(Long.parseLong(valueTmp));
            } else {
                logger.error("交易申报参数解析异常，字段[SELL_VOLUME5]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("RLT_PHASE");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setRltPhase(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[RLT_PHASE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("RLT_FLAG");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setRltFlag(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[RLT_FLAG]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("RLT_OPEN_FLAG");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setRltOpenFlag(valueTmp.charAt(0));
            } else {
                logger.error("交易申报参数解析异常，字段[RLT_OPEN_FLAG]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("LATEST_ENQRY_TIME");
            if (valueTmp != null) {
                operationDataIdOptMktinfo.setLatestEnqryTime(valueTmp);
            } else {
                logger.error("交易申报参数解析异常，字段[LATEST_ENQRY_TIME]为空:{}", jsonData.toString());
                return false;
            }

            listObjectParam.add(operationDataIdOptMktinfo);
            return true;

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkStkTradeParam(JSONObject jsonData, List<Object> listObjectParam) throws Exception {
        try {
            // 入参校验
            OperationDataIdStkTrade operationDataIdStkTrade = new OperationDataIdStkTrade();
            String valueTmp = "";

            valueTmp = jsonData.getString("DATA_ID");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setDataId(valueTmp);
            } else {
                logger.error("集中交易申报参数解析异常，字段[DATA_ID]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TRD_DATE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setTrdDate(Integer.parseInt(valueTmp));
            } else {
                logger.error("集中交易申报参数解析异常，字段[TRD_DATE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_DATE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setOrderDate(Integer.parseInt(valueTmp));
            } else {
                logger.error("集中交易申报参数解析异常，字段[ORDER_DATE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_TIME");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
                operationDataIdStkTrade.setOrderTime(simpleDateFormat.parse(valueTmp));
            } else {
                logger.error("集中交易申报参数解析异常，字段[ORDER_TIME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUST_CODE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setCustCode(Long.parseLong(valueTmp));
            } else {
                logger.error("集中交易申报参数解析异常，字段[CUST_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUACCT_CODE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setCuacctCode(Long.parseLong(valueTmp));
            } else {
                logger.error("集中交易申报参数解析异常，字段[CUACCT_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CURRENCY");
            if (valueTmp != null && !valueTmp.isEmpty() && valueTmp.charAt(0) != ' ') {
                operationDataIdStkTrade.setCurrency(valueTmp.charAt(0));
            } else {
                logger.error("集中交易申报参数解析异常，字段[CURRENCY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STKBD");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setStkbd(valueTmp);
            } else {
                logger.error("集中交易申报参数解析异常，字段[STKBD]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TRDACCT");
            if (valueTmp != null  && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setTrdacct(valueTmp);
            } else {
                logger.error("集中交易申报参数解析异常，字段[TRDACCT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STKPBU");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setStkpbu(valueTmp);
            } else {
                logger.error("集中交易申报参数解析异常，字段[STKPBU]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("INT_ORG");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setIntOrg(Short.parseShort(valueTmp));
            } else {
                logger.error("集中交易申报参数解析异常，字段[INT_ORG]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("IS_WITHDRAW");
            if (valueTmp != null && !valueTmp.isEmpty() && valueTmp.charAt(0) != ' ') {
                operationDataIdStkTrade.setIsWithdraw(valueTmp.charAt(0));
            } else {
                logger.error("集中交易申报参数解析异常，字段[IS_WITHDRAW]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_BIZ");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setStkBiz(Short.parseShort(valueTmp));
            } else {
                logger.error("集中交易申报参数解析异常，字段[STK_BIZ]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_BIZ_ACTION");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setStkBizAction(Short.parseShort(valueTmp));
            } else {
                logger.error("集中交易申报参数解析异常，字段[STK_BIZ_ACTION]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_ID");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setOrderId(valueTmp);
            } else {
                logger.error("集中交易申报参数解析异常，字段[ORDER_ID]为空:{}", jsonData.toString());
                return false;
            }

            if (operationDataIdStkTrade.getIsWithdraw().equals(ConstantUtil.WITHDRAW)) {
                valueTmp = jsonData.getString("RAW_ORDER_ID");
                if (valueTmp != null && !valueTmp.isEmpty()) {
                    operationDataIdStkTrade.setRawOrderId(valueTmp);
                } else {
                    logger.error("集中交易申报参数解析异常，字段[RAW_ORDER_ID]为空:{}", jsonData.toString());
                    return false;
                }
            }

            valueTmp = jsonData.getString("ORDER_STATUS");
            if (valueTmp != null && !valueTmp.isEmpty() && valueTmp.charAt(0) != ' ') {
                operationDataIdStkTrade.setOrderStatus(valueTmp.charAt(0));
            } else {
                logger.error("集中交易申报参数解析异常，字段[ORDER_STATUS]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_PRICE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                long orderPrice = NumConvertUtil.priceToLong(Double.parseDouble(valueTmp));
                if (orderPrice > 0) {
                    operationDataIdStkTrade.setOrderPrice(orderPrice);
                } else {
                    logger.error("集中交易申报参数解析异常，字段[ORDER_PRICE]值不正确:{}", jsonData.toString());
                    return false;
                }

            } else {
                logger.error("集中交易申报参数解析异常，字段[ORDER_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_QTY");
            if (valueTmp != null) {
                long orderQty = Long.parseLong(valueTmp);
                if (orderQty > 0) {
                    operationDataIdStkTrade.setOrderQty(orderQty);
                } else {
                    logger.error("集中交易申报参数解析异常，字段[ORDER_QTY]值不正确:{}", jsonData.toString());
                    return false;
                }
            } else {
                logger.error("集中交易申报参数解析异常，字段[ORDER_QTY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_FRZ_AMT");
            if (valueTmp != null) {
                operationDataIdStkTrade.setOrderFrzAmt(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("集中交易申报参数解析异常，字段[ORDER_FRZ_AMT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_CODE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setStkCode(valueTmp);
            } else {
                logger.error("集中交易申报参数解析异常，字段[STK_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_NAME");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setStkName(valueTmp);
            } else {
                logger.error("集中交易申报参数解析异常，字段[STK_NAME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_CLS");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setStkCls(valueTmp.charAt(0));
            } else {
                logger.error("集中交易申报参数解析异常，字段[STK_CLS]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("FUND_BLN_VARY");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setFundBlnVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("集中交易申报参数解析异常，字段[FUND_BLN_VARY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("FUND_AVL_VARY");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setFundAvlVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("集中交易申报参数解析异常，字段[FUND_AVL_VARY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STD_BOND_CODE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setStdBondCode(valueTmp);
            } else {
                logger.error("集中交易申报参数解析异常，字段[STD_BOND_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STD_BOND_QTY");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkTrade.setFundAvlVary(Long.parseLong(valueTmp));
            } else {
                logger.error("集中交易申报参数解析异常，字段[STD_BOND_QTY]为空:{}", jsonData.toString());
                return false;
            }

            listObjectParam.add(operationDataIdStkTrade);
            return true;

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkStkMatchParam(JSONObject jsonData, List<Object> listObjectParam) throws Exception {
        try {
            // 入参校验
            OperationDataIdStkMatch operationDataIdStkMatch = new OperationDataIdStkMatch();
            String valueTmp = "";

            valueTmp = jsonData.getString("DATA_ID");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkMatch.setDataId(valueTmp);
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[DATA_ID]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TRD_DATE");
            if (valueTmp != null) {
                operationDataIdStkMatch.setTrdDate(Integer.parseInt(valueTmp));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[TRD_DATE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MATCHED_TIME");
            if (valueTmp != null) {
                operationDataIdStkMatch.setMatchedTime(valueTmp);
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[MATCHED_TIME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUST_CODE");
            if (valueTmp != null) {
                operationDataIdStkMatch.setCustCode(Long.parseLong(valueTmp));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[CUST_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUST_NAME");
            if (valueTmp != null) {
                operationDataIdStkMatch.setCustName(valueTmp);
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[CUST_NAME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CURRENCY");
            if (valueTmp != null) {
                operationDataIdStkMatch.setCurrency(valueTmp.charAt(0));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[CURRENCY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("CUACCT_CODE");
            if (valueTmp != null) {
                operationDataIdStkMatch.setCuacctCode(Long.parseLong(valueTmp));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[CUACCT_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STKBD");
            if (valueTmp != null) {
                operationDataIdStkMatch.setStkbd(valueTmp);
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[STKBD]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("TRDACCT");
            if (valueTmp != null) {
                operationDataIdStkMatch.setTrdacct(valueTmp);
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[TRDACCT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STKPBU");
            if (valueTmp != null) {
                operationDataIdStkMatch.setStkpbu(valueTmp);
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[STKPBU]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("INT_ORG");
            if (valueTmp != null) {
                operationDataIdStkMatch.setIntOrg(Short.parseShort(valueTmp));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[INT_ORG]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MATCHED_SN");
            if (valueTmp != null) {
                operationDataIdStkMatch.setMatchedSn(valueTmp);
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[MATCHED_SN]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("ORDER_ID");
            if (valueTmp != null) {
                operationDataIdStkMatch.setOrderId(valueTmp);
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[ORDER_ID]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MATCHED_QTY");
            if (valueTmp != null) {
                long matchedQty = Long.parseLong(valueTmp);
                if (matchedQty > 0) {
                    operationDataIdStkMatch.setMatchedQty(matchedQty);
                } else {
                    logger.error("集中交易成交回报参数解析异常，字段[MATCHED_QTY]值不正确:{}", jsonData.toString());
                    return false;
                }
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[MATCHED_QTY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MATCHED_PRICE");
            if (valueTmp != null) {
                long matchedPrice = NumConvertUtil.priceToLong(Double.parseDouble(valueTmp));
                if (matchedPrice > 0) {
                    operationDataIdStkMatch.setMatchedPrice(matchedPrice);
                } else {
                    logger.error("集中交易成交回报参数解析异常，字段[MATCHED_PRICE]值不正确:{}", jsonData.toString());
                    return false;
                }
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[MATCHED_PRICE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MATCH_AMT");
            if (valueTmp != null) {
                operationDataIdStkMatch.setMatchAmt(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[MATCH_AMT]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("IS_WITHDRAW");
            if (valueTmp != null) {
                operationDataIdStkMatch.setIsWithdraw(valueTmp.charAt(0));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[IS_WITHDRAW]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_BIZ");
            if (valueTmp != null) {
                operationDataIdStkMatch.setStkBiz(Short.parseShort(valueTmp));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[STK_BIZ]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_BIZ_ACTION");
            if (valueTmp != null) {
                operationDataIdStkMatch.setStkBizAction(Short.parseShort(valueTmp));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[STK_BIZ_ACTION]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("MATCHED_TYPE");
            if (valueTmp != null) {
                operationDataIdStkMatch.setMatchedType(valueTmp.charAt(0));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[MATCHED_TYPE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_CODE");
            if (valueTmp != null) {
                operationDataIdStkMatch.setStkCode(valueTmp);
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[STK_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_NAME");
            if (valueTmp != null) {
                operationDataIdStkMatch.setStkName(valueTmp);
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[STK_NAME]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_CLS");
            if (valueTmp != null) {
                operationDataIdStkMatch.setStkCls(valueTmp.charAt(0));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[STK_CLS]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STK_UNDL_CODE");
            if (valueTmp != null) {
                operationDataIdStkMatch.setStkUndlCode(valueTmp);
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[STK_UNDL_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("FUND_BLN_VARY");
            if (valueTmp != null) {
                operationDataIdStkMatch.setFundBlnVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[FUND_BLN_VARY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("FUND_AVL_VARY");
            if (valueTmp != null) {
                operationDataIdStkMatch.setFundAvlVary(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[FUND_AVL_VARY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STD_BOND_CODE");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkMatch.setStdBondCode(valueTmp);
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[STD_BOND_CODE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("STD_BOND_QTY");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdStkMatch.setFundAvlVary(Long.parseLong(valueTmp));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[STD_BOND_QTY]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("REPCH_DATE");
            if (valueTmp != null) {
                operationDataIdStkMatch.setRepchDate(Integer.parseInt(valueTmp));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[REPCH_DATE]为空:{}", jsonData.toString());
                return false;
            }

            valueTmp = jsonData.getString("REPCH_AMT");
            if (valueTmp != null) {
                operationDataIdStkMatch.setRepchAmt(NumConvertUtil.moneyToLong(Double.parseDouble(valueTmp)));
            } else {
                logger.error("集中交易成交回报参数解析异常，字段[REPCH_AMT]为空:{}", jsonData.toString());
                return false;
            }

            listObjectParam.add(operationDataIdStkMatch);
            return true;

        } catch (Exception e) {
            throw e;
        }
    }
    public static boolean checkEccodesignParam(JSONObject jsonData, List<Object> listObjectParam) throws Exception {
        try {
            // 入参校验
            OperationDataIdEccodesign operationDataIdEccodesign = new OperationDataIdEccodesign();
            String valueTmp = "";

            valueTmp = jsonData.getString("DATA_ID");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setDataId(valueTmp);
            } else {
                logger.error("电子合同约定表参数解析异常，字段[DATA_ID]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("_ENDIAN");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setEndian(Long.parseLong(valueTmp));
            } else {
                logger.error("电子合同约定表参数解析异常，字段[_ENDIAN]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("resfundexpdate");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setResfundExpdate(Integer.parseInt(valueTmp));
            } else {
                logger.error("电子合同约定表参数解析异常，字段[resfundexpdate]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("g_operway");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setgOperWay(Integer.parseInt(valueTmp));
            } else {
                logger.error("电子合同约定表参数解析异常，字段[g_operway]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("reservfund");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setReservFund(Double.parseDouble(valueTmp));
            } else {
                logger.error("电子合同约定表参数解析异常，字段[reservfund]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("g_checksno");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setgCheckSno(valueTmp);
            } else {
                logger.error("电子合同约定表参数解析异常，字段[g_checksno]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("g_funcid");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setgFuncId(valueTmp);
            } else {
                logger.error("电子合同约定表参数解析异常，字段[g_funcid]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("g_operid");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setgOperId(valueTmp);
            } else {
                logger.error("电子合同约定表参数解析异常，字段[g_operid]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("serverid");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setServerId(valueTmp);
            } else {
                operationDataIdEccodesign.setServerId("3");
            }
            valueTmp = jsonData.getString("orgid");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setOrgId(valueTmp);
            } else {
                logger.error("电子合同约定表参数解析异常，字段[orgid]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("ofcode");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setOfCode(valueTmp);
            } else {
                logger.error("电子合同约定表参数解析异常，字段[ofcode]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("fundid");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setFundId(Long.parseLong(valueTmp));
            } else {
                logger.error("电子合同约定表参数解析异常，字段[fundid]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("g_serverid");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setgServerId(valueTmp);
            } else {
                logger.error("电子合同约定表参数解析异常，字段[g_serverid]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("multisettexp");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setMultisettExp(Integer.parseInt(valueTmp));
            } else {
                logger.error("电子合同约定表参数解析异常，字段[multisettexp]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("g_operpwd");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setgOperPwd(valueTmp);
            } else {
                logger.error("电子合同约定表参数解析异常，字段[g_operpwd]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("_CA");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setCa(valueTmp);
            } else {
                logger.error("电子合同约定表参数解析异常，字段[_CA]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("g_stationaddr");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setgStationAddr(valueTmp);
            } else {
                logger.error("电子合同约定表参数解析异常，字段[g_stationaddr]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("multisettstatus");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdEccodesign.setMultisettStatus(valueTmp);
            } else {
                logger.error("电子合同约定表参数解析异常，字段[multisettstatus]为空:{}", jsonData.toString());
                return false;
            }
            listObjectParam.add(operationDataIdEccodesign);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }
    public static boolean checkFundAssetParam(JSONObject jsonData, List<Object> listObjectParam) throws Exception {
        try {
            // 入参校验
            OperationDataIdFundAsset operationDataIdFundAsset = new OperationDataIdFundAsset();
            String valueTmp = "";

            valueTmp = jsonData.getString("DATA_ID");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdFundAsset.setDataId(valueTmp);
            } else {
                logger.error("基金资产参数解析异常，字段[DATA_ID]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("fundid");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdFundAsset.setFundId(Long.parseLong(valueTmp));
            } else {
                logger.error("基金资产参数解析异常，字段[fundid]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("custid");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdFundAsset.setCustId(Long.parseLong(valueTmp));
            } else {
                logger.error("基金资产参数解析异常，字段[custid]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("fundavl");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdFundAsset.setFundAvl(Double.parseDouble(valueTmp));
            } else {
                logger.error("基金资产参数解析异常，字段[fundavl]为空:{}", jsonData.toString());
                return false;
            }
            valueTmp = jsonData.getString("moneytype");
            if (valueTmp != null && !valueTmp.isEmpty()) {
                operationDataIdFundAsset.setMoneyType(valueTmp.charAt(0));
            } else {
                logger.error("基金资产参数解析异常，字段[moneytype]为空:{}", jsonData.toString());
                return false;
            }
            listObjectParam.add(operationDataIdFundAsset);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

}
