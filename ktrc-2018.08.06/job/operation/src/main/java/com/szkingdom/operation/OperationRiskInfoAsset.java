/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationRiskInfoAsset
 * Author:   ZhangMaohua
 * Date:     2018/7/24
 * Description: 持仓类风险表操作
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.dao.DaoCustomer;
import com.szkingdom.dao.DaoRiskInfoAsset;
import com.szkingdom.data.DataCustomer;
import com.szkingdom.data.DataRiskInfoAsset;
import com.szkingdom.data.DataStkRightPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class OperationRiskInfoAsset {

    protected static Logger logger = LoggerFactory.getLogger(OperationRiskInfoAsset.class);

    /**
     * 设置阈值字段
     * @param custCode
     * @param indicatorId
     * @param indicatorResult
     * @param condDataRiskInfoAsset
     * @return
     * @throws Exception
     */
    private static DataRiskInfoAsset setThreshold(long custCode, String indicatorId, long indicatorResult, DataRiskInfoAsset condDataRiskInfoAsset) throws Exception{
        JSONObject jsonObject;
        //先查客户风险指标阈值表（CUST_INDICATOR_THRES）
        jsonObject = OperationCustIndicatorThres.getCustThresForRiskTable(custCode,indicatorId,indicatorResult);
        if(jsonObject != null){
            condDataRiskInfoAsset.setThresNo(jsonObject.getString("THRES_NO").charAt(0));
            condDataRiskInfoAsset.setThresName(jsonObject.getString("THRES_NAME"));
            condDataRiskInfoAsset.setThresColor(jsonObject.getString("THRES_COLOR"));
        }
        else {
            //如果客户风险指标阈值表为空，查风险指标阈值表（INDICATOR_THRESHOLD）
            jsonObject = OperationIndicatorThreshold.getThresholdForRiskTable(indicatorId,indicatorResult);
            if(jsonObject != null){
                condDataRiskInfoAsset.setThresNo(jsonObject.getString("THRES_NO").charAt(0));
                condDataRiskInfoAsset.setThresName(jsonObject.getString("THRES_NAME"));
                condDataRiskInfoAsset.setThresColor(jsonObject.getString("THRES_COLOR"));
            }
            else {
                logger.warn("客户风险指标阈值表CUST_INDICATOR_THRES/风险指标阈值表INDICATOR_THRESHOLD无合适数据!");
                condDataRiskInfoAsset.setThresNo('0');
                condDataRiskInfoAsset.setThresName("");
                condDataRiskInfoAsset.setThresColor("");
            }
        }
        return condDataRiskInfoAsset;
    }

    /**
     * insert时，对需要字段赋值
     * @param jsonData
     * @param jsonIndicatorInfo Json串
     * @param condDataRiskInfoAsset 唯一索引
     * @return condDataRiskInfoAsset
     */
    private static DataRiskInfoAsset setFieldsValues(JSONObject jsonData, JSONObject jsonIndicatorInfo, DataRiskInfoAsset condDataRiskInfoAsset) throws Exception{
        String indicatorId = jsonIndicatorInfo.getString("indicatorId");

        /**
         * 设置字符Character类型字段（测试用）
         */
        condDataRiskInfoAsset.setLsFlag('0');
        condDataRiskInfoAsset.setRiskValidFlag('0');
        condDataRiskInfoAsset.setRiskInfoSrc('0');
        condDataRiskInfoAsset.setNoticeSentLevel('0');

        /**
         * 从Json串中提取字段INDICATOR_NAME、CUACCT_CODE、CURRENCY、TRDACCT_EXCLS、OPT_UNDL_NAME、OPT_UNDL_CLS（6）
         */
        condDataRiskInfoAsset.setIndicatorName(jsonIndicatorInfo.getString("indicatorName"));
        condDataRiskInfoAsset.setCuacctCode(Long.parseLong(jsonData.getString("CUACCT_CODE")));
        condDataRiskInfoAsset.setCurrency(jsonData.getString("CURRENCY").charAt(0));
        condDataRiskInfoAsset.setOptUndlName(jsonData.getString("OPT_UNDL_NAME"));
        condDataRiskInfoAsset.setOptUndlCls(jsonData.getString("OPT_UNDL_CLS").charAt(0));

        /**
         * 更新风险率字段RISK_VALUE、阈值字段: THRESHOLD、THRES_NO、THRES_NAME、THRES_COLOR （5）
         */
        // 根据指标结果类型，决定数据是否需要作为比例或金额放大相应倍数
        long indicatorResultLong = 0L;
        try {
            char indicatorResultType = jsonIndicatorInfo.getString("indicatorResultType").charAt(0);
            double resultDouble = Double.parseDouble(jsonIndicatorInfo.getString("indicatorResult"));
            indicatorResultLong = OperationRiskIndicators.magnifyByIndicatorResultType(resultDouble, indicatorResultType);
        } catch (NumberFormatException e) {
            logger.error("风险结果indicatorResult格式不对，可能计算公式中分母为0 ! ",e);
        }
        condDataRiskInfoAsset.setRiskValue(indicatorResultLong);

        setThreshold(Long.parseLong(jsonData.getString("CUST_CODE")),indicatorId,indicatorResultLong,condDataRiskInfoAsset);

        /**
         * 查CUSTOMER表，获取字段：CUST_NAME、CUST_TYPE、CUST_CLS、INT_ORG （4）
         */
        DataCustomer dataCustomer = new DataCustomer();
        dataCustomer.setCustCode(condDataRiskInfoAsset.getCustCode());
        DaoCustomer daoCustomer = new DaoCustomer();
        try {
            dataCustomer = daoCustomer.selectUnique(dataCustomer);
            condDataRiskInfoAsset.setCustName(dataCustomer.getCustName());
            condDataRiskInfoAsset.setCustType(dataCustomer.getCustType());
            condDataRiskInfoAsset.setCustCls(dataCustomer.getCustCls());
            condDataRiskInfoAsset.setIntOrg(dataCustomer.getIntOrg());
        } catch(Exception e){
            e.printStackTrace();
            logger.error(" 查表CUSTOMER错误！ ");
        }

        /**
         * 设置日期时间字段OCC_DATE、OCC_TIME （2）
         */
        condDataRiskInfoAsset.setOccDate(DateUtils.getCurrDate());
        condDataRiskInfoAsset.setOccTime(DateUtils.getCurrTime());

        /**
         * 设置字段PRE_RISK_VALUE、PRE_THRESHOLD、PRE_THRES_NO、PRE_THRES_NAME、PRE_THRES_COLOR (5)
         */
        condDataRiskInfoAsset.setPreRiskValue(0L);
        condDataRiskInfoAsset.setPreThreshold(0L);
        condDataRiskInfoAsset.setPreThresColor("");
        condDataRiskInfoAsset.setPreThresName("");
        condDataRiskInfoAsset.setPreThresNo('0');

        /**
         * 设置字段QUOTA_VAL、QUOTA_VAL_USED （2）
         */
        if (indicatorId.equals(ConstantUtil.INDICATOR_QUOTA)) {
            try {
                JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
                condDataRiskInfoAsset.setQuotaVal(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_QUOTA_VAL)));
                condDataRiskInfoAsset.setQuotaValUsed(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_QUOTA_VAL_USED)));
            } catch(Exception e){
                throw new Exception("更新表RISK_INFO_ASSET买入额度字段异常 ", e);
            }
        }

        /**
         * 设置字段STK_QTY_NEED、STK_QTY_ACT、CVD_SHORT_TYPE（3）
         */
        // 判断备兑不足指标来写表备兑字段相关取值
        if (indicatorId.equals(ConstantUtil.INDICATOR_CVT)) {
            // 根据除权日期判断是预测备兑不足还是实际备兑不足
            try {
                if (indicatorResultLong > 0) {
                    DataStkRightPlan dataStkRightPlan = new DataStkRightPlan();
                    dataStkRightPlan.setStkCode(jsonData.getString("OPT_UNDL_CODE"));
                    dataStkRightPlan.setStkbd(OperationStkInfo.convertStkbdFromOptToUndl(jsonData.getString("STKBD")));
                    long dateOfXrDr = OperationStkRightPlan.getStkRightPlanDataWithCache(dataStkRightPlan).getDividendDate();
                    long dateCurrent = DateUtils.getCurrDate();
                    if (dateCurrent == dateOfXrDr && dateOfXrDr != 0) {
                        condDataRiskInfoAsset.setCvdShortType(ConstantUtil.CVD_SHORT_TYPE_LACK);
                        condDataRiskInfoAsset.setRiskValidFlag(ConstantUtil.RISK_VALID_FLAG_RISK);
                    }
                    else {
                        condDataRiskInfoAsset.setCvdShortType(ConstantUtil.CVD_SHORT_TYPE_PRE_LACK);
                        condDataRiskInfoAsset.setRiskValidFlag(ConstantUtil.RISK_VALID_FLAG_RISK);
                    }
                }
                else {
                    condDataRiskInfoAsset.setCvdShortType(ConstantUtil.CVD_SHORT_TYPE_NORMAL);
                    condDataRiskInfoAsset.setRiskValidFlag(ConstantUtil.RISK_VALID_FLAG_NORMAL);
                }
            } catch(Exception e){
                throw new Exception("更新表RISK_INFO_ASSET备兑相关字段，获取除权除息日期异常 ", e);
            }

            // 是备兑不足指标则更新所需标的字段与实际持有标的字段，由传参结果解析
            try {
                condDataRiskInfoAsset.setCvtQtyLack(indicatorResultLong);
                JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
                condDataRiskInfoAsset.setStkQtyNeed(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_CVT_QTY_NEED)));
                condDataRiskInfoAsset.setStkQtyAct(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_CVT_QTY_AVL)));
            } catch(Exception e){
                throw new Exception("更新表RISK_INFO_ASSET备兑相关字段异常 ", e);
            }
        }
        else {
            condDataRiskInfoAsset.setCvdShortType(ConstantUtil.CVD_SHORT_TYPE_NORMAL);
        }


        /**
         * 对持仓限额业务设置字段POSI_LQTY、POSI_QTY
         */
        try {
            if (indicatorId.equals(ConstantUtil.INDICATOR_POSI_LONG)) {
                JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
                condDataRiskInfoAsset.setPosiLqty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_POSI_LQTY_LONG)));
                condDataRiskInfoAsset.setPosiQty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_POSI_QTY_LONG)));
            }
            if (indicatorId.equals(ConstantUtil.INDICATOR_POSI_TOTAL)) {
                JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
                condDataRiskInfoAsset.setPosiLqty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_POSI_LQTY_TOTAL)));
                condDataRiskInfoAsset.setPosiQty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_POSI_QTY_TOTAL)));
            }
            if (indicatorId.equals(ConstantUtil.INDICATOR_POSI_DAILY)) {
                JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
                condDataRiskInfoAsset.setPosiLqty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_POSI_LQTY_DAILY)));
                condDataRiskInfoAsset.setPosiQty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_POSI_QTY_DAILY)));
            }
            if (indicatorId.equals(ConstantUtil.INDICATOR_MEMBER_POSI_TOTAL)) {
                JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
                condDataRiskInfoAsset.setPosiLqty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_MEMBER_POSI_LQTY_TOTAL)));
                condDataRiskInfoAsset.setPosiQty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_MEMBER_POSI_QTY_TOTAL)));
            }
        } catch(Exception e){
            throw new Exception("更新表RISK_INFO_ASSET持仓限额相关字段异常 ", e);
        }


        /**
         * 设置字段LS_FLAG、 RISK_VALID_FLAG、 RISK_INFO_SRC、NOTICE_SENT_LEVEL、NOTICE_SENT_LEVEL_NAME （4）
         */

        return condDataRiskInfoAsset;
    }
    /**
     * update时，对需要字段修改
     * @param condDataRiskInfoAsset 修改部分字段后返回
     */
    private static DataRiskInfoAsset updateFieldsValues(JSONObject jsonData, JSONObject jsonIndicatorInfo, DataRiskInfoAsset condDataRiskInfoAsset) throws Exception{
        String indicatorId = jsonIndicatorInfo.getString("indicatorId");
        /**
         * 更新日期时间字段OCC_DATE、OCC_TIME （2）
         */
        condDataRiskInfoAsset.setOccDate(DateUtils.getCurrDate());
        condDataRiskInfoAsset.setOccTime(DateUtils.getCurrTime());

        /**
         * 更新标的证券名称、类型，资产账户、币种
         */
        condDataRiskInfoAsset.setOptUndlName(jsonData.getString("OPT_UNDL_NAME"));
        condDataRiskInfoAsset.setOptUndlCls(jsonData.getString("OPT_UNDL_CLS").charAt(0));
        condDataRiskInfoAsset.setCuacctCode(Long.parseLong(jsonData.getString("CUACCT_CODE")));
        condDataRiskInfoAsset.setCurrency(jsonData.getString("CURRENCY").charAt(0));

        /**
         * 更新风险率字段RISK_VALUE、阈值字段：THRESHOLD、THRES_NO、THRES_NAME、THRES_COLOR
         */
        // 根据指标结果类型，决定数据是否需要作为比例或金额放大相应倍数
        long indicatorResultLong = 0L;
        try {
            char indicatorResultType = jsonIndicatorInfo.getString("indicatorResultType").charAt(0);
            double resultDouble = Double.parseDouble(jsonIndicatorInfo.getString("indicatorResult"));
            indicatorResultLong = OperationRiskIndicators.magnifyByIndicatorResultType(resultDouble, indicatorResultType);
        } catch (NumberFormatException e) {
            logger.error("风险结果indicatorResult格式不对，可能计算公式中分母为0 ! ",e);
        }
        condDataRiskInfoAsset.setRiskValue(indicatorResultLong);

        setThreshold(Long.parseLong(jsonData.getString("CUST_CODE")),indicatorId,indicatorResultLong,condDataRiskInfoAsset);

        if (indicatorId.equals(ConstantUtil.INDICATOR_QUOTA)) {
            condDataRiskInfoAsset.setOptUndlCls('0');
            condDataRiskInfoAsset.setOptUndlName("");
            try {
                JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
                condDataRiskInfoAsset.setQuotaVal(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_QUOTA_VAL)));
                condDataRiskInfoAsset.setQuotaValUsed(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_QUOTA_VAL_USED)));
            } catch(Exception e){
                throw new Exception("更新表RISK_INFO_ASSET买入额度字段异常 ", e);
            }
        }

        // 判断备兑不足指标来写表备兑字段相关取值
        if (indicatorId.equals(ConstantUtil.INDICATOR_CVT)) {
            // 根据除权日期判断是预测备兑不足还是实际备兑不足
            try {
                if (indicatorResultLong > 0) {
                    DataStkRightPlan dataStkRightPlan = new DataStkRightPlan();
                    dataStkRightPlan.setStkCode(jsonData.getString("OPT_UNDL_CODE"));
                    dataStkRightPlan.setStkbd(OperationStkInfo.convertStkbdFromOptToUndl(jsonData.getString("STKBD")));
                    long dateOfXrDr = OperationStkRightPlan.getStkRightPlanDataWithCache(dataStkRightPlan).getDividendDate();
                    long dateCurrent = DateUtils.getCurrDate();
                    if (dateCurrent == dateOfXrDr && dateOfXrDr != 0) {
                        condDataRiskInfoAsset.setCvdShortType(ConstantUtil.CVD_SHORT_TYPE_LACK);
                        condDataRiskInfoAsset.setRiskValidFlag(ConstantUtil.RISK_VALID_FLAG_RISK);
                    }
                    else {
                        condDataRiskInfoAsset.setCvdShortType(ConstantUtil.CVD_SHORT_TYPE_PRE_LACK);
                        condDataRiskInfoAsset.setRiskValidFlag(ConstantUtil.RISK_VALID_FLAG_RISK);
                    }
                }
                else {
                    condDataRiskInfoAsset.setCvdShortType(ConstantUtil.CVD_SHORT_TYPE_NORMAL);
                    condDataRiskInfoAsset.setRiskValidFlag(ConstantUtil.RISK_VALID_FLAG_NORMAL);
                }
            } catch(Exception e){
                throw new Exception("更新表RISK_INFO_ASSET备兑相关字段，获取除权除息日期异常 ", e);
            }

            // 是备兑不足指标则更新所需标的字段与实际持有标的字段，由传参结果解析
            try {
                condDataRiskInfoAsset.setCvtQtyLack(indicatorResultLong);
                JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
                condDataRiskInfoAsset.setStkQtyNeed(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_CVT_QTY_NEED)));
                condDataRiskInfoAsset.setStkQtyAct(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_CVT_QTY_AVL)));
            } catch(Exception e){
                throw new Exception("更新表RISK_INFO_ASSET备兑相关字段异常 ", e);
            }
        }
        else {
            condDataRiskInfoAsset.setCvdShortType(ConstantUtil.CVD_SHORT_TYPE_NORMAL);
        }

        /**
         * 对持仓限额业务设置字段POSI_LQTY、POSI_QTY
         */
        try {
            if (indicatorId.equals(ConstantUtil.INDICATOR_POSI_LONG)) {
                JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
                condDataRiskInfoAsset.setPosiLqty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_POSI_LQTY_LONG)));
                condDataRiskInfoAsset.setPosiQty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_POSI_QTY_LONG)));
            }
            if (indicatorId.equals(ConstantUtil.INDICATOR_POSI_TOTAL)) {
                JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
                condDataRiskInfoAsset.setPosiLqty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_POSI_LQTY_TOTAL)));
                condDataRiskInfoAsset.setPosiQty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_POSI_QTY_TOTAL)));
            }
            if (indicatorId.equals(ConstantUtil.INDICATOR_POSI_DAILY)) {
                JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
                condDataRiskInfoAsset.setPosiLqty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_POSI_LQTY_DAILY)));
                condDataRiskInfoAsset.setPosiQty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_POSI_QTY_DAILY)));
            }
            if (indicatorId.equals(ConstantUtil.INDICATOR_MEMBER_POSI_TOTAL)) {
                JSONObject factorsValueObject = jsonIndicatorInfo.getJSONObject("indicatorFactorsValue");
                condDataRiskInfoAsset.setPosiLqty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_MEMBER_POSI_LQTY_TOTAL)));
                condDataRiskInfoAsset.setPosiQty(Long.parseLong(factorsValueObject.getString(ConstantUtil.FACTOR_MEMBER_POSI_QTY_TOTAL)));
            }
        } catch(Exception e){
            throw new Exception("更新表RISK_INFO_ASSET持仓限额相关字段异常 ", e);
        }

        return condDataRiskInfoAsset;
    }

    /**
     * 写入风险表，如该行存在update，如不存在则insert
     * @param jsonObject  Json串
     */
    public static void writeRiskTables(JSONObject jsonObject) throws Exception{

        DataRiskInfoAsset dataRiskInfoAssetIndex = new DataRiskInfoAsset();
        DaoRiskInfoAsset daoRiskInfoAsset = new DaoRiskInfoAsset();
        /**
         * 从Json串中获取唯一索引字段值
         */
        JSONObject jsonData = (JSONObject) jsonObject.get(ConstantUtil.JSON_KTRC);
        dataRiskInfoAssetIndex.setCustCode(Long.parseLong(jsonData.getString("CUST_CODE")));
        dataRiskInfoAssetIndex.setStkbd(jsonData.getString("STKBD"));
        dataRiskInfoAssetIndex.setTrdacct(jsonData.getString("TRDACCT"));
        dataRiskInfoAssetIndex.setSubacctCode(jsonData.getString("SUBACCT_CODE"));
        JSONObject jsonIndicatorInfo = (JSONObject)jsonObject.get(ConstantUtil.JSON_INDICATORINFO);
        String indicatorId = jsonIndicatorInfo.getString("indicatorId");
        dataRiskInfoAssetIndex.setIndicatorId(indicatorId);

        //限购额度指标不需要唯一索引查表
        if(!indicatorId.equals(ConstantUtil.INDICATOR_QUOTA)){
            dataRiskInfoAssetIndex.setOptUndlCode(jsonData.getString("OPT_UNDL_CODE"));
        }

        /**
         * 根据唯一索引查表，如该行存在update，如不存在则insert
         */
        try {
            DataRiskInfoAsset condDataRiskInfoAsset = daoRiskInfoAsset.selectUnique(dataRiskInfoAssetIndex);
            if (condDataRiskInfoAsset != null) {
                DataRiskInfoAsset resultDataRiskInfoAsset  = updateFieldsValues(jsonData, jsonIndicatorInfo, condDataRiskInfoAsset);
                daoRiskInfoAsset.update(resultDataRiskInfoAsset);
            } else {
                DataRiskInfoAsset resultDataRiskInfoAsset = setFieldsValues(jsonData, jsonIndicatorInfo, dataRiskInfoAssetIndex);
                daoRiskInfoAsset.insert(resultDataRiskInfoAsset);
            }
        }catch (Exception e){
            throw new Exception("writeRiskTables 更新表RISK_INFO_ASSET错误 ", e);
        }
    }
}
