/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationOptCustUndlPosi
 * Author:   ZhangChangHong
 * Date:     2018/9/4
 * Description: 期权客户标的实时持仓操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.dao.DaoOptCustUndlPosi;
import com.szkingdom.data.DataOptCustUndlPosi;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationOptCustUndlPosi extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationOptCustUndlPosi.class);

    public static DataOptCustUndlPosi initOptCustUndlPosiData(Object objectParam, String dataId) {
        DataOptCustUndlPosi dataOptCustUndlPosi = new DataOptCustUndlPosi();

        if (ConstantUtil.OPT_TRADE.equals(dataId)) {
            OperationDataIdOptTrade operationDataIdOptTrade = (OperationDataIdOptTrade) objectParam;
            short stkBiz = operationDataIdOptTrade.getStkBiz();
            short stkBizAction = operationDataIdOptTrade.getStkBizAction();
            long orderQty = operationDataIdOptTrade.getOrderQty();

            // 设置唯一索引
            dataOptCustUndlPosi.setCustCode(operationDataIdOptTrade.getCustCode());
            dataOptCustUndlPosi.setStkbd(operationDataIdOptTrade.getStkbd());
            dataOptCustUndlPosi.setTrdacct(operationDataIdOptTrade.getTrdacct());
            dataOptCustUndlPosi.setOptUndlCode(operationDataIdOptTrade.getOptUndlCode());

            if (ConstantUtil.STK_BIZ_BUY_OPEN_POSITION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                dataOptCustUndlPosi.setTotalPosi(orderQty);
                dataOptCustUndlPosi.setLongPosi(orderQty);
                dataOptCustUndlPosi.setDailyOpenPosi(orderQty);

            } else if (ConstantUtil.STK_BIZ_SELL_OPEN_POSITION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                dataOptCustUndlPosi.setTotalPosi(orderQty);

            } else if (ConstantUtil.STK_BIZ_COVERED_CALL_POSITION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                dataOptCustUndlPosi.setTotalPosi(orderQty);

            } else {
                return null;
            }

            dataOptCustUndlPosi.setCustCode(operationDataIdOptTrade.getCustCode());
            dataOptCustUndlPosi.setStkbd(operationDataIdOptTrade.getStkbd());
            dataOptCustUndlPosi.setTrdacct(operationDataIdOptTrade.getTrdacct());
            dataOptCustUndlPosi.setOptUndlCls(operationDataIdOptTrade.getOptUndlCls());
            dataOptCustUndlPosi.setOptUndlCode(operationDataIdOptTrade.getOptUndlCode());
            dataOptCustUndlPosi.setOptUndlName(operationDataIdOptTrade.getOptUndlName());
        } else if (ConstantUtil.OPT_MATCH.equals(dataId)) {
            OperationDataIdOptMatch operationDataIdOptMatch = (OperationDataIdOptMatch) objectParam;
            short stkBiz = operationDataIdOptMatch.getStkBiz();
            short stkBizAction = operationDataIdOptMatch.getStkBizAction();
            long matchedQty = operationDataIdOptMatch.getMatchedQty();

            // 设置唯一索引
            dataOptCustUndlPosi.setCustCode(operationDataIdOptMatch.getCustCode());
            dataOptCustUndlPosi.setStkbd(operationDataIdOptMatch.getStkbd());
            dataOptCustUndlPosi.setTrdacct(operationDataIdOptMatch.getTrdacct());
            dataOptCustUndlPosi.setOptUndlCode(operationDataIdOptMatch.getOptUndlCode());

            if (ConstantUtil.STK_BIZ_BUY_OPEN_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 买开撤单成交
                    dataOptCustUndlPosi.setTotalPosi(matchedQty);
                    dataOptCustUndlPosi.setLongPosi(matchedQty);
                    dataOptCustUndlPosi.setDailyOpenPosi(matchedQty);
                } else {
                    return null;
                }
            } else if (ConstantUtil.STK_BIZ_SELL_CLOSE_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction) {
                    // 卖平正常成交
                    dataOptCustUndlPosi.setTotalPosi(-matchedQty);
                    dataOptCustUndlPosi.setLongPosi(-matchedQty);
                } else {
                    return null;
                }
            } else if (ConstantUtil.STK_BIZ_SELL_OPEN_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 卖开撤单成交
                    dataOptCustUndlPosi.setTotalPosi(matchedQty);
                } else {
                    return null;
                }
            } else if (ConstantUtil.STK_BIZ_BUY_CLOSE_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction) {
                    // 买平正常成交
                    dataOptCustUndlPosi.setTotalPosi(-matchedQty);
                } else {
                    return null;
                }
            } else if (ConstantUtil.STK_BIZ_COVERED_CALL_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 备开撤单成交
                    dataOptCustUndlPosi.setTotalPosi(matchedQty);
                } else {
                    return null;
                }
            } else if (ConstantUtil.STK_BIZ_COVERED_CLOSE_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction) {
                    // 备平正常成交
                    dataOptCustUndlPosi.setTotalPosi(-matchedQty);
                } else {
                    return null;
                }
            } else {
                return null;
            }

            dataOptCustUndlPosi.setCustCode(operationDataIdOptMatch.getCustCode());
            dataOptCustUndlPosi.setStkbd(operationDataIdOptMatch.getStkbd());
            dataOptCustUndlPosi.setTrdacct(operationDataIdOptMatch.getTrdacct());
            dataOptCustUndlPosi.setOptUndlCls(operationDataIdOptMatch.getOptUndlCls());
            dataOptCustUndlPosi.setOptUndlCode(operationDataIdOptMatch.getOptUndlCode());
            dataOptCustUndlPosi.setOptUndlName(operationDataIdOptMatch.getOptUndlName());
        }

        return dataOptCustUndlPosi;
    }

    /**
     * 锁期权客户标的实时持仓表，更新数据
     * @param objectParam
     * @param dataId
     * @param sqlSession
     * @throws Exception
     */
    public static void writeOptCustUndlPosiData2DB(Object objectParam, String dataId,
                                                   SqlSession sqlSession, JSONObject jsonObject) throws Exception {
        DaoOptCustUndlPosi daoOptCustUndlPosi = new DaoOptCustUndlPosi();
        DataOptCustUndlPosi dataOptCustUndlPosi = initOptCustUndlPosiData(objectParam, dataId);
        if (dataOptCustUndlPosi == null) {
            return;
        }

        try {
            DataOptCustUndlPosi resultDataOptCustUndlPosi = daoOptCustUndlPosi.selectLock(dataOptCustUndlPosi, sqlSession);
            if (resultDataOptCustUndlPosi != null) {
                //内存数据库更新

                // update
                resultDataOptCustUndlPosi.setTotalPosi(resultDataOptCustUndlPosi.getTotalPosi() + dataOptCustUndlPosi.getTotalPosi());
                resultDataOptCustUndlPosi.setLongPosi(resultDataOptCustUndlPosi.getLongPosi() + dataOptCustUndlPosi.getLongPosi());
                resultDataOptCustUndlPosi.setDailyOpenPosi(resultDataOptCustUndlPosi.getDailyOpenPosi() + dataOptCustUndlPosi.getDailyOpenPosi());

                daoOptCustUndlPosi.update(resultDataOptCustUndlPosi, sqlSession);
            } else {
                // 内存数据库写入

                // insert
                daoOptCustUndlPosi.insert(dataOptCustUndlPosi, sqlSession);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static List<DataOptCustUndlPosi> listOptCustUndlPosiDataByParam(DataOptCustUndlPosi condDataOptCustUndlPosi) throws Exception {
        DaoOptCustUndlPosi daoOptCustUndlPosi = new DaoOptCustUndlPosi();
        try {
            List<DataOptCustUndlPosi> resListDataOptCustUndlPosi = daoOptCustUndlPosi.selectList(condDataOptCustUndlPosi);
            if (resListDataOptCustUndlPosi != null && !resListDataOptCustUndlPosi.isEmpty()) {
                return resListDataOptCustUndlPosi;
            } else {
                logger.error("listOptCustUndlPosiDataByParam() 经济业务单标的持仓收集查无数据");
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
