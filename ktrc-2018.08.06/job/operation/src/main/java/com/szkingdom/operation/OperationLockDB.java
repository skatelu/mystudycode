/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationLockDB
 * Author:   ZhangChangHong
 * Date:     2018/7/26
 * Description: 数据库表行级锁操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.ZtsConstantUtil;
import com.szkingdom.data.*;
import com.szkingdom.datasource.DataSourceUtil;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationLockDB extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationLockDB.class);
    private SqlSession sqlSession = null;

    public void initSqlSession() throws Exception {
        try {
            // 连接数不够时，抛异常
            sqlSession = DataSourceUtil.getSqlSession(false);
        } catch (Exception e) {
            throw new Exception("获取数据库连接句柄异常", e);
        }
    }

    public void writeMaintainRealtimeData2DB(String dataId,
                                             Object objectParam,
                                             StringBuilder stringBuilder,
                                             Object objectTableData,
                                             JSONObject jsonObject) throws Exception {
        // 依据各系统主题类型，固定更新相应表数据
        try {
            if (ConstantUtil.SPOT_QUOTATION.equals(dataId)) {
                // 现货行情
                OperationStkMktInfo.writeStkMktinfoData2DB(objectParam);

            } else if (ConstantUtil.OPT_QUOTATION.equals(dataId)) {
                // 期权行情
                OperationOptMktinfo.writeOptMktinfoData2DB(objectParam);

            } else if (ConstantUtil.OPT_MARGIN_CORP.equals(dataId)) {
                // 不做表更新
            }  else {
                initSqlSession();

                // 根据stk_biz更新相应表
                try {
                    if (sqlSession != null) {
                        if (ConstantUtil.OPT_TRADE.equals(dataId)) {
                            // 更新合约账户表OPT_TRDACCT
                            OperationOptTrdacct.writeOptTrdacctData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 更新资金表
                            OperationCuacctFund.writeCuacctFundData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 更新持仓表
                            OperationOptAsset.writeOptAssetData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 更新股票期权客户标的实时持仓
                            OperationOptCustUndlPosi.writeOptCustUndlPosiData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 写去重表
                            OperationOptOrder.insertOptOrderData2DB((DataOptOrder) objectTableData, stringBuilder, sqlSession, jsonObject);

                        } else if (ConstantUtil.OPT_MATCH.equals(dataId)) {
                            // 更新合约账户表OPT_TRDACCT
                            OperationOptTrdacct.writeOptTrdacctData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 更新资金表
                            OperationCuacctFund.writeCuacctFundData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 更新持仓表
                            OperationOptAsset.writeOptAssetData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 更新股票期权客户标的实时持仓
                            OperationOptCustUndlPosi.writeOptCustUndlPosiData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 写去重表
                            OperationOptMatching.insertOptMatchingData2DB((DataOptMatching)objectTableData, stringBuilder, sqlSession);

                        } else if (ConstantUtil.OPT_FUND.equals(dataId)) {
                            // 更新资金表
                            OperationCuacctFund.writeCuacctFundData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 写去重表
                            OperationCuacctLog.insertCuacctLogData2DB((DataCuacctLog)objectTableData, stringBuilder, sqlSession);

                        } else if (ConstantUtil.OPT_ASSET.equals(dataId)) {
                            // 更新持仓表
                            OperationOptAsset.writeOptAssetData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 写去重表
                            OperationStkAssetLog.insertStkAssetLogData2DB((DataStkAssetLog)objectTableData, stringBuilder, sqlSession);
                        } else if (ConstantUtil.STK_TRADE.equals(dataId)) {
                            //更新质押股份表STK_IMPAWN
                            OperationStkImpawn.writeStkImpawnData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 更新持仓表
                            OperationStkAsset.writeStkAssetData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 更新资金表
                            OperationCuacctFund.writeCuacctFundData2DB(objectParam, dataId, sqlSession, jsonObject);

                            //写去重表
                            OperationStkOrder.insertStkOrderData2DB((DataStkOrder)objectTableData, stringBuilder, sqlSession);

                        } else if (ConstantUtil.STK_MATCH.equals(dataId)) {
                            //更新质押股份表STK_IMPAWN
                            OperationStkImpawn.writeStkImpawnData2DB(objectParam, dataId, sqlSession, jsonObject);

                            //更新证券回购合约表STK_REPCH_CONTRACT
                            OperationStkRepchContract.writeStkRepchContractData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 更新持仓表
                            OperationStkAsset.writeStkAssetData2DB(objectParam, dataId, sqlSession, jsonObject);

                            // 更新资金表
                            OperationCuacctFund.writeCuacctFundData2DB(objectParam, dataId, sqlSession, jsonObject);

                            //写去重表
                            OperationStkMatching.insertStkMatchingData2DB((DataStkMatching)objectTableData, stringBuilder, sqlSession);

                        }
                        //ETF差额扣款处理
                        else if(ZtsConstantUtil.STK_ECCODESIGN.equals(dataId)) {
                            OperationEtfCashcomponent.writeEtfCashcomponent2DBbyEccodesign(objectTableData);
                        }
                        //基金资产处理
                        else if(ZtsConstantUtil.STK_FUNDASSET.equals(dataId)) {
                            OperationEtfCashcomponent.writeEtfCashcomponent2DBbyFundAsset(objectTableData);
                        }
                    }

                    DataSourceUtil.commitSession(sqlSession);
                } catch (Exception e) {
                    DataSourceUtil.rollbackSession(sqlSession);
                    throw new Exception("依据各系统主题类型，更新相应表数据异常 ", e);

                } finally {
                    DataSourceUtil.closeSession(sqlSession);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
