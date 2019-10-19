/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationWriteRiskTables
 * Author:   ZhangMaohua
 * Date:     2018/8/24
 * Description: 结果写入风险表
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationWriteRiskTables {

    protected static Logger logger = LoggerFactory.getLogger(OperationWriteRiskTables.class);

    /**
     *
     * @param indicatorId
     * @return
     */
    public static String getRiskTableName(String indicatorId){
        String riskTableName = "";
        switch(indicatorId){
            //账户类风险信息表
            case "60001001":
                riskTableName = ConstantUtil.TABLE_RISK_INFO_ACCT;
                break;
            //资金类风险信息表
            case "60004001":
            case "60004002":
            case "60004003":
                riskTableName = ConstantUtil.TABLE_RISK_INFO_FUND;
                break;
            //持仓类风险信息表
            case "60002001":
            case "60003001":
            case "60003002":
            case "60003003":
            case "60003004":
            case "60005001":
                riskTableName = ConstantUtil.TABLE_RISK_INFO_ASSET;
                break;
            //行权资金类风险信息表
            case "60007003":
                riskTableName = ConstantUtil.TABLE_RISK_INFO_EXE_FUND;
                break;
            //行权证券类风险信息表
            case "60007001":
            case "60007002":
                riskTableName = ConstantUtil.TABLE_RISK_INFO_EXE_STK;
                break;
            //经纪业务类资金风险信息表
            case "60006001":
                riskTableName = ConstantUtil.TABLE_RISK_INFO_CORP_FUND;
                break;
            //债券质押业务风险信息表
            case "60501001":
            case "60501002":
            case "60501003":
            case "60502001":
            case "60503001":
                riskTableName = ConstantUtil.TABLE_RISK_INFO_BOND_PLEDGE;
                break;
            //其他
            default:
                riskTableName = "";
                break;
        }
        return riskTableName;
    }

    /**
     *
     * @param jsonObject
     * @param tableName
     * @throws Exception
     */
    public static void writeMsgToRiskTables(JSONObject jsonObject,String tableName)throws Exception{

        switch (tableName){
            //持仓类风险信息表
            case ConstantUtil.TABLE_RISK_INFO_ASSET:
                try {
                    OperationRiskInfoAsset.writeRiskTables(jsonObject);
                } catch (Exception e) {
                    throw new Exception(" 更新持仓类风险信息表RISK_INFO_ASSET时错误！ ", e);
                }
                break;

            //资金类风险信息表
            case ConstantUtil.TABLE_RISK_INFO_FUND:
                try {
                    OperationRiskInfoFund.writeRiskTables(jsonObject);
                } catch (Exception e) {
                    throw new Exception(" 更新资金类风险信息表RISK_INFO_FUND时错误！ ", e);
                }
                break;

            //账户类风险信息表
            case ConstantUtil.TABLE_RISK_INFO_ACCT:
                try {
                    OperationRiskInfoAcct.writeRiskTables(jsonObject);
                } catch (Exception e) {
                    throw new Exception(" 更新账户类风险信息表RISK_INFO_ACCT时错误！ ", e);
                }
                break;

            //行权资金类风险信息表
            case ConstantUtil.TABLE_RISK_INFO_EXE_FUND:
                try {
                    OperationRiskInfoExeFund.writeRiskTables(jsonObject);
                } catch (Exception e) {
                    throw new Exception(" 更新行权资金类风险信息表RISK_INFO_EXE_FUND时错误！ ", e);
                }
                break;

            //行权证券类风险信息表
            case ConstantUtil.TABLE_RISK_INFO_EXE_STK:
                try {
                    OperationRiskInfoExeStk.writeRiskTables(jsonObject);
                } catch (Exception e) {
                    throw new Exception(" 更新行权证券类风险信息表RISK_INFO_EXE_STK时错误！ ", e);
                }
                break;

            //经纪业务类资金风险信息表，先写日志流水再写结果表
            case ConstantUtil.TABLE_RISK_INFO_CORP_FUND:
                try {
                    OperationRiskLogCorpFund.writeRiskTables(jsonObject);
                } catch (Exception e) {
                    throw new Exception(" 流水写入经纪业务类资金风险日志表RISK_LOG_CORP_FUND时错误！ ", e);
                }
                try {
                    OperationRiskInfoCorpFund.writeRiskTables(jsonObject);
                } catch (Exception e) {
                    throw new Exception(" 更新经纪业务类资金风险信息表RISK_INFO_CORP_FUND时错误！ ", e);
                }
                break;

            //债券质押业务风险信息表
            case ConstantUtil.TABLE_RISK_INFO_BOND_PLEDGE:
                try {
                    OperationRiskInfoBondPledge.writeRiskTables(jsonObject);
                } catch (Exception e) {
                    throw new Exception(" 更新债券质押业务风险信息表RISK_INFO_BOND_PLEDGE时错误！ ", e);
                }
                break;

            //其他
            default:
                logger.error("未找到合适的风险表来存储风险结果！");
                break;
        }

    }

    /**
     * 查表indicator，获取相关风险表
     * @param jsonObject
     * @throws Exception
     */
    public static void riskMessageToTables(JSONObject jsonObject ) throws Exception{
        /**
         * 查indicator表,获取对应riskTableNames
         */
        String riskTableNames = "";

        /**
         * 拆分多个表名,分别写入
         */
        String[] riskTableName = riskTableNames.split(",");
        for(String singleTableName : riskTableName){

            try {
                writeMsgToRiskTables(jsonObject,singleTableName);
            } catch (Exception e) {
                throw new Exception("风险结果录入数据库错误！", e);
            }
        }

    }
}
