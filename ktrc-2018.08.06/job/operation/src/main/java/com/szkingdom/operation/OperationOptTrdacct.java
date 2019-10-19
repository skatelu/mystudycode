/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationOptTrdacct
 * Author:   ZhangMaohua
 * Date:     2018/8/6
 * Description:
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.dao.DaoOptTrdacct;
import com.szkingdom.data.DataOptTrdacct;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationOptTrdacct  extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationOptTrdacct.class);

    /**
     * 按唯一索引查 股票期权合约账户表OPT_TRDACCT
     * @param condDataOptTrdacct
     * @return
     * @throws Exception
     */
    public static DataOptTrdacct getOptTrdacctDataByUnique(DataOptTrdacct condDataOptTrdacct) throws Exception{
        DaoOptTrdacct daoOptTrdacct = new DaoOptTrdacct();
        try{
            DataOptTrdacct resultDataOptTrdacct = daoOptTrdacct.selectUnique(condDataOptTrdacct);
            if(resultDataOptTrdacct != null){
                return resultDataOptTrdacct;
            }
            else {
                logger.warn("查表 TABLE_OPT_TRDACCT 返回 NULL !");
                return null;
            }
        } catch (Exception e){
            throw new Exception("查表 TABLE_OPT_TRDACCT 抛出异常！", e);
        }
    }

    /**
     * 组装持仓变动数据
     */
    public static DataOptTrdacct initOptTrdacctData(Object objectParam, String dataId) {
        DataOptTrdacct dataOptTrdacct = new DataOptTrdacct();

        if (ConstantUtil.OPT_TRADE.equals(dataId)) {
            OperationDataIdOptTrade operationDataIdOptTrade = (OperationDataIdOptTrade) objectParam;
            if(!operationDataIdOptTrade.getOptCustType().equals(ConstantUtil.OPT_CUST_TYPE_PERSON)) {
                return null;
            }

            short stkBiz = operationDataIdOptTrade.getStkBiz();
            short stkBizAction = operationDataIdOptTrade.getStkBizAction();

            dataOptTrdacct.setCustCode(operationDataIdOptTrade.getCustCode());
            dataOptTrdacct.setStkbd(operationDataIdOptTrade.getStkbd());
            dataOptTrdacct.setTrdacct(operationDataIdOptTrade.getTrdacct());

            if (ConstantUtil.STK_BIZ_BUY_OPEN_POSITION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                //更新已使用额度
                double orderPrice = NumConvertUtil.longToPrice(operationDataIdOptTrade.getOrderPrice());
                double orderAmt = orderPrice * operationDataIdOptTrade.getOrderQty();
                dataOptTrdacct.setQuotaValUsed(NumConvertUtil.moneyToLong(orderAmt));
            } else {
                return null;
            }
        } else if (ConstantUtil.OPT_MATCH.equals(dataId)) {
            OperationDataIdOptMatch operationDataIdOptMatch = (OperationDataIdOptMatch) objectParam;
            if(!operationDataIdOptMatch.getOptCustType().equals(ConstantUtil.OPT_CUST_TYPE_PERSON)) {
                return null;
            }

            short stkBiz = operationDataIdOptMatch.getStkBiz();
            short stkBizAction = operationDataIdOptMatch.getStkBizAction();

            dataOptTrdacct.setCustCode(operationDataIdOptMatch.getCustCode());
            dataOptTrdacct.setStkbd(operationDataIdOptMatch.getStkbd());
            dataOptTrdacct.setTrdacct(operationDataIdOptMatch.getTrdacct());

            //更新已使用额度
            if (ConstantUtil.STK_BIZ_BUY_OPEN_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 买开撤单成交
                    double matchedPrice = NumConvertUtil.longToPrice(operationDataIdOptMatch.getMatchedPrice());
                    double matchedAmt = matchedPrice * operationDataIdOptMatch.getMatchedQty();
                    dataOptTrdacct.setQuotaValUsed(NumConvertUtil.moneyToLong(matchedAmt));
                } else {
                    return null;
                }
            } else if (ConstantUtil.STK_BIZ_SELL_CLOSE_POSITION == stkBiz) {
                if (ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction) {
                    // 卖平正常成交
                    double matchedPrice = NumConvertUtil.longToPrice(operationDataIdOptMatch.getMatchedPrice());
                    double matchedAmt = matchedPrice * operationDataIdOptMatch.getMatchedQty();
                    dataOptTrdacct.setQuotaValUsed(-NumConvertUtil.moneyToLong(matchedAmt));
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
        return dataOptTrdacct;
    }

    public static void writeOptTrdacctData2DB(Object objectParam, String dataId,
                                              SqlSession sqlSession, JSONObject jsonObject) throws Exception {
        DaoOptTrdacct daoOptTrdacct = new DaoOptTrdacct();
        DataOptTrdacct condDataOptTrdacct = initOptTrdacctData(objectParam, dataId);
        if(condDataOptTrdacct == null){
            return;
        }

        try {
            DataOptTrdacct resultDataOptTrdacct = daoOptTrdacct.selectLock(condDataOptTrdacct, sqlSession);
            if (resultDataOptTrdacct != null) {
                //内存数据库更新

                resultDataOptTrdacct.setQuotaValUsed(resultDataOptTrdacct.getQuotaValUsed() + condDataOptTrdacct.getQuotaValUsed());
                daoOptTrdacct.update(resultDataOptTrdacct, sqlSession);
            }else {
                String log = "OPT_TRDACCT查无数据,CustCode:["
                        + condDataOptTrdacct.getCustCode() + "], Stkbd:["
                        + condDataOptTrdacct.getStkbd() + "], Trdacct:["
                        + condDataOptTrdacct.getTrdacct() + "]";
                throw new Exception(log);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
