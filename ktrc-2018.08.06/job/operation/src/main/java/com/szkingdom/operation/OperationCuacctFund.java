/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationCuacctFund
 * Author:   ZhangChangHong
 * Date:     2018/7/26
 * Description: CUACCT_FUND操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.dao.DaoCuacctFund;
import com.szkingdom.data.DataCuacctFund;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationCuacctFund extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationCuacctFund.class);

    public static DataCuacctFund initCuacctFundData(Object objectParam, String dataId) {
        DataCuacctFund dataCuacctFund = new DataCuacctFund();

        if (ConstantUtil.OPT_TRADE.equals(dataId)) {
            OperationDataIdOptTrade operationDataIdOptTrade = (OperationDataIdOptTrade) objectParam;
            short stkBiz = operationDataIdOptTrade.getStkBiz();
            short stkBizAction = operationDataIdOptTrade.getStkBizAction();

            // 设置唯一索引
            dataCuacctFund.setCuacctCode(operationDataIdOptTrade.getCuacctCode());
            dataCuacctFund.setCurrency(operationDataIdOptTrade.getCurrency());

            if (ConstantUtil.STK_BIZ_BUY_OPEN_POSITION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                // 买入开仓
                dataCuacctFund.setFundAvl(operationDataIdOptTrade.getFundAvlVary());

            } else if (ConstantUtil.STK_BIZ_SELL_OPEN_POSITION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                // 卖出开仓
                dataCuacctFund.setFundAvl(operationDataIdOptTrade.getFundAvlVary());
                dataCuacctFund.setMarginInclRlt(operationDataIdOptTrade.getMarginInclRltVary());

            } else if (ConstantUtil.STK_BIZ_COVERED_CLOSE_POSITION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                // 备兑平仓
                dataCuacctFund.setFundAvl(operationDataIdOptTrade.getFundAvlVary());

            } else if (ConstantUtil.STK_BIZ_EXE_CALL_OPTION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                // 认购行权
                dataCuacctFund.setFundAvl(operationDataIdOptTrade.getFundAvlVary());

            } else {
                return null;
            }
        } else if (ConstantUtil.OPT_MATCH.equals(dataId)) {
            OperationDataIdOptMatch operationDataIdOptMatch = (OperationDataIdOptMatch) objectParam;
            short stkBiz = operationDataIdOptMatch.getStkBiz();
            short stkBizAction = operationDataIdOptMatch.getStkBizAction();

            // 设置唯一索引
            dataCuacctFund.setCuacctCode(operationDataIdOptMatch.getCuacctCode());
            dataCuacctFund.setCurrency(operationDataIdOptMatch.getCurrency());

            if (ConstantUtil.STK_BIZ_BUY_OPEN_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 买开撤单成交
                    dataCuacctFund.setFundAvl(operationDataIdOptMatch.getFundAvlVary());
                } else {
                    // 买开正常成交
                    dataCuacctFund.setFundTrdBln(operationDataIdOptMatch.getFundAvlVary());
                }
            } else if (ConstantUtil.STK_BIZ_SELL_CLOSE_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING != stkBizAction) {
                    // 卖平正常成交
                    dataCuacctFund.setFundAvl(operationDataIdOptMatch.getFundAvlVary());
                    dataCuacctFund.setFundTrdBln(operationDataIdOptMatch.getFundAvlVary());
                }
            } else if (ConstantUtil.STK_BIZ_SELL_OPEN_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 卖开撤单成交
                    dataCuacctFund.setFundAvl(operationDataIdOptMatch.getFundAvlVary());
                    dataCuacctFund.setMarginInclRlt(operationDataIdOptMatch.getMarginInclRltVary());

                } else {
                    // 卖开正常成交
                    dataCuacctFund.setFundAvl(operationDataIdOptMatch.getFundAvlVary());
                    dataCuacctFund.setFundTrdBln(operationDataIdOptMatch.getFundAvlVary());
                    dataCuacctFund.setMarginUsed(operationDataIdOptMatch.getMarginUsedVary());
                }
            } else if (ConstantUtil.STK_BIZ_BUY_CLOSE_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING != stkBizAction) {
                    // 买平正常成交
                    dataCuacctFund.setFundAvl(operationDataIdOptMatch.getFundAvlVary());
                    dataCuacctFund.setFundTrdBln(operationDataIdOptMatch.getFundAvlVary());
                    dataCuacctFund.setMarginUsed(operationDataIdOptMatch.getMarginUsedVary());
                    dataCuacctFund.setMarginInclRlt(operationDataIdOptMatch.getMarginInclRltVary());
                }
            } else if (ConstantUtil.STK_BIZ_COVERED_CALL_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING != stkBizAction) {
                    // 备开正常成交
                    dataCuacctFund.setFundAvl(operationDataIdOptMatch.getFundAvlVary());
                    dataCuacctFund.setFundTrdBln(operationDataIdOptMatch.getFundAvlVary());
                }
            } else if (ConstantUtil.STK_BIZ_COVERED_CLOSE_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 备平撤单成交
                    dataCuacctFund.setFundAvl(operationDataIdOptMatch.getFundAvlVary());
                } else {
                    // 备平正常成交
                    dataCuacctFund.setFundTrdBln(operationDataIdOptMatch.getFundAvlVary());
                }
            } else if (ConstantUtil.STK_BIZ_EXE_CALL_OPTION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 认购行权撤单成交
                    dataCuacctFund.setFundAvl(operationDataIdOptMatch.getFundAvlVary());
                }
            } else {
                return null;
            }

        } else if (ConstantUtil.OPT_FUND.equals(dataId)) {
            OperationDataIdOptFund operationDataIdOptFund = (OperationDataIdOptFund) objectParam;
            // 设置唯一索引
            dataCuacctFund.setCuacctCode(operationDataIdOptFund.getCuacctCode());
            dataCuacctFund.setCurrency(operationDataIdOptFund.getCurrency());

            dataCuacctFund.setFundBln(operationDataIdOptFund.getFundBlnVary());
            dataCuacctFund.setFundAvl(operationDataIdOptFund.getFundAvlVary());
            dataCuacctFund.setDailyInAmt(operationDataIdOptFund.getDailyInAmt());
            dataCuacctFund.setDailyOutAmt(operationDataIdOptFund.getDailyOutAmt());
            dataCuacctFund.setPaylater(0);

            //dataCuacctFund.setMarginUsed();
            //dataCuacctFund.setMarginInclRlt();
        } else if (ConstantUtil.STK_TRADE.equals(dataId)) {
            OperationDataIdStkTrade operationDataIdStkTrade = (OperationDataIdStkTrade)objectParam;
            short stkBiz = operationDataIdStkTrade.getStkBiz();
            short stkBizAction = operationDataIdStkTrade.getStkBizAction();
            // 设置唯一索引
            dataCuacctFund.setCuacctCode(operationDataIdStkTrade.getCuacctCode());
            dataCuacctFund.setCurrency(operationDataIdStkTrade.getCurrency());

            //融券回购正常委托: FundAvl 减， FundTrdFrz 加 （FundAvlVary自带负号）
            if (ConstantUtil.STK_BIZ_BOND_PLEDGE_LENDING == stkBiz && ConstantUtil.STK_BIZ_ACTION_LIMIT_OFFER == stkBizAction) {
                dataCuacctFund.setFundAvl(operationDataIdStkTrade.getFundAvlVary());
                dataCuacctFund.setFundTrdFrz(-operationDataIdStkTrade.getFundAvlVary());
            }

        } else if (ConstantUtil.STK_MATCH.equals(dataId)) {
            OperationDataIdStkMatch operationDataIdStkMatch = (OperationDataIdStkMatch)objectParam;
            short stkBiz = operationDataIdStkMatch.getStkBiz();
            short stkBizAction = operationDataIdStkMatch.getStkBizAction();
            // 设置唯一索引
            dataCuacctFund.setCuacctCode(operationDataIdStkMatch.getCuacctCode());
            dataCuacctFund.setCurrency(operationDataIdStkMatch.getCurrency());
            //融资回购正常成交 ： FundAvl加、FundTrdBln加 （FundTrdUfz 和 FundTrdOtd 二选一赋值）加
            if (ConstantUtil.STK_BIZ_BOND_PLEDGE_FINANCING == stkBiz && ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction) {
                dataCuacctFund.setFundAvl(operationDataIdStkMatch.getFundAvlVary());
                dataCuacctFund.setFundTrdBln(operationDataIdStkMatch.getFundBlnVary());
                dataCuacctFund.setFundTrdUfz(operationDataIdStkMatch.getFundAvlVary());
                dataCuacctFund.setFundTrdOtd(operationDataIdStkMatch.getFundAvlVary());
            }
            //融券回购撤单成交： FundAvl 加， FundTrdFrz 减 （FundAvlVary自带负号）
            else if (ConstantUtil.STK_BIZ_BOND_PLEDGE_LENDING == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                dataCuacctFund.setFundAvl(-operationDataIdStkMatch.getFundAvlVary());
                dataCuacctFund.setFundTrdFrz(operationDataIdStkMatch.getFundAvlVary());
            }
            //融券回购正常成交: FundTrdBln减 （FundAvlVary自带负号）
            else if (ConstantUtil.STK_BIZ_BOND_PLEDGE_LENDING == stkBiz && ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction) {
                dataCuacctFund.setFundTrdBln(operationDataIdStkMatch.getFundBlnVary());
            }

        }

        return dataCuacctFund;
    }

    /**
     * 锁资金表，更新数据
     * @param objectParam
     * @param dataId
     * @param sqlSession
     * @throws Exception
     */
    public static void writeCuacctFundData2DB(Object objectParam, String dataId,
                                              SqlSession sqlSession, JSONObject jsonObject) throws Exception {
        DaoCuacctFund daoCuacctFund = new DaoCuacctFund();
        DataCuacctFund dataCuacctFund = initCuacctFundData(objectParam, dataId);
        if (dataCuacctFund == null) {
            return;
        }

        try {
            DataCuacctFund resultDataCuacctFund = daoCuacctFund.selectLock(dataCuacctFund, sqlSession);
            if (resultDataCuacctFund != null) {
                //内存数据库更新

                // update
                resultDataCuacctFund.setFundBln(resultDataCuacctFund.getFundBln() + dataCuacctFund.getFundBln());
                resultDataCuacctFund.setFundAvl(resultDataCuacctFund.getFundAvl() + dataCuacctFund.getFundAvl());
                resultDataCuacctFund.setFundTrdBln(resultDataCuacctFund.getFundTrdBln() + dataCuacctFund.getFundTrdBln());
                resultDataCuacctFund.setMarginUsed(resultDataCuacctFund.getMarginUsed() + dataCuacctFund.getMarginUsed());
                resultDataCuacctFund.setMarginInclRlt(resultDataCuacctFund.getMarginInclRlt() + dataCuacctFund.getMarginInclRlt());
                resultDataCuacctFund.setDailyInAmt(resultDataCuacctFund.getDailyInAmt() + dataCuacctFund.getDailyInAmt());
                resultDataCuacctFund.setDailyOutAmt(resultDataCuacctFund.getDailyOutAmt() + dataCuacctFund.getDailyOutAmt());

                resultDataCuacctFund.setFundTrdFrz(resultDataCuacctFund.getFundTrdFrz() + dataCuacctFund.getFundTrdFrz());
                //如何区分T+0可用、不可用
                //resultDataCuacctFund.setFundTrdUfz();
                //resultDataCuacctFund.setFundTrdOtd();

                daoCuacctFund.updateLock(resultDataCuacctFund, sqlSession);

                // 查表数据透传
                jsonObject.put(ConstantUtil.JSON_CUACCT_FUND, resultDataCuacctFund.toJson());

            } else {
                String log = "资金表查无数据,CuacctCode:[" + dataCuacctFund.getCuacctCode() + "], Currency:[" + dataCuacctFund.getCurrency() + "]";
                throw new Exception(log);
            }
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 按唯一索引查询客户资产数据 zhangch
     * @param condDataCuacctFund
     * @throws Exception
     */
    public static DataCuacctFund getCuacctFundDataByUnique(DataCuacctFund condDataCuacctFund) throws Exception {
        DaoCuacctFund daoCuacctFund = new DaoCuacctFund();
        try {
            DataCuacctFund resultDataCuacctFund = daoCuacctFund.selectUnique(condDataCuacctFund);
            if (resultDataCuacctFund != null) {
                return resultDataCuacctFund;

            } else {
                logger.warn("getCuacctFundDataByUnique() 客户资产查无数据, CuacctCode:{}, Currency:{}",
                            condDataCuacctFund.getCuacctCode(), condDataCuacctFund.getCurrency());
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 计算客户总资产
     * @param condDataCuacctFund
     * @return
     * @throws Exception
     */
    public static long calcCustTotalAmt(DataCuacctFund condDataCuacctFund) throws Exception {
        DaoCuacctFund daoCuacctFund = new DaoCuacctFund();
        try {
            Long resultCustTotalAmt = daoCuacctFund.selectDataForCustTotalAmt(condDataCuacctFund);
            if (resultCustTotalAmt != null) {
                return resultCustTotalAmt.longValue();
            } else {
                logger.error("calcCustTotalAmt() 计算客户总资产查无数据, CuacctCode:{}, Currency:{}, UserCode:{}",
                        condDataCuacctFund.getCuacctCode(), condDataCuacctFund.getCurrency(), condDataCuacctFund.getUserCode());
                return 0L;
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
