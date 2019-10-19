/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkImpawn
 * Author:   ZhangMaohua
 * Date:     2018/9/10
 * Description: 证券质押股份表
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.dao.DaoStkBondInfo;
import com.szkingdom.dao.DaoStkImpawn;
import com.szkingdom.data.DataStkImpawn;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationStkImpawn extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationStkImpawn.class);

    public static DataStkImpawn initStkImpawnData(Object objectParam, String dataId) {
        DataStkImpawn dataStkImpawn = new DataStkImpawn();
        boolean setStkAvlFlag = false;

        if (ConstantUtil.STK_TRADE.equals(dataId)) {
            OperationDataIdStkTrade operationDataIdStkTrade = (OperationDataIdStkTrade)objectParam;
            short stkBiz = operationDataIdStkTrade.getStkBiz();
            short stkBizAction = operationDataIdStkTrade.getStkBizAction();

            //设置唯一索引
            dataStkImpawn.setCuacctCode(operationDataIdStkTrade.getCuacctCode());
            dataStkImpawn.setTrdacct(operationDataIdStkTrade.getTrdacct());
            dataStkImpawn.setStkbd(operationDataIdStkTrade.getStkbd());
            dataStkImpawn.setStkpbu(operationDataIdStkTrade.getStkpbu());
            dataStkImpawn.setStkCode(operationDataIdStkTrade.getStkCode());

            dataStkImpawn.setIntOrg(operationDataIdStkTrade.getIntOrg());
            dataStkImpawn.setCustCode(operationDataIdStkTrade.getCustCode());
            dataStkImpawn.setStkex(operationDataIdStkTrade.getStkbd().charAt(0));
            dataStkImpawn.setStkCls(operationDataIdStkTrade.getStkCls());
            dataStkImpawn.setCustCls('0');
            dataStkImpawn.setCuacctCls('0');
            //dataStkImpawn.setStkPrebln();
            //dataStkImpawn.setStkBln();

            //质押出库委托申报
            setStkAvlFlag =  ConstantUtil.STK_BIZ_BOND_PLEDGE_OUT == stkBiz && ConstantUtil.STK_BIZ_ACTION_LIMIT_OFFER == stkBizAction;
            if (setStkAvlFlag) {
                dataStkImpawn.setStkAvl(-operationDataIdStkTrade.getOrderQty());
            }

        } else if (ConstantUtil.STK_MATCH.equals(dataId)) {
            OperationDataIdStkMatch operationDataIdStkMatch = (OperationDataIdStkMatch)objectParam;
            short stkBiz = operationDataIdStkMatch.getStkBiz();
            short stkBizAction = operationDataIdStkMatch.getStkBizAction();

            //设置唯一索引
            dataStkImpawn.setCuacctCode(operationDataIdStkMatch.getCuacctCode());
            dataStkImpawn.setTrdacct(operationDataIdStkMatch.getTrdacct());
            dataStkImpawn.setStkbd(operationDataIdStkMatch.getStkbd());
            dataStkImpawn.setStkpbu(operationDataIdStkMatch.getStkpbu());
            dataStkImpawn.setStkCode(operationDataIdStkMatch.getStkCode());

            dataStkImpawn.setIntOrg(operationDataIdStkMatch.getIntOrg());
            dataStkImpawn.setCustCode(operationDataIdStkMatch.getCustCode());
            dataStkImpawn.setStkex(operationDataIdStkMatch.getStkbd().charAt(0));
            dataStkImpawn.setStkCls(operationDataIdStkMatch.getStkCls());
            dataStkImpawn.setCustCls('0');
            dataStkImpawn.setCuacctCls('0');
            //dataStkImpawn.setStkPrebln();
            //dataStkImpawn.setStkBln();

            //质押入库正常成交 或 质押出库撤单成交
            setStkAvlFlag = (ConstantUtil.STK_BIZ_BOND_PLEDGE_IN == stkBiz && ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction)
                        || (ConstantUtil.STK_BIZ_BOND_PLEDGE_OUT == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) ;
            if (setStkAvlFlag) {
                dataStkImpawn.setStkAvl(operationDataIdStkMatch.getMatchedQty());
            }
        }

        return dataStkImpawn;
    }

    public static void writeStkImpawnData2DB(Object objectParam, String dataId,
                                             SqlSession sqlSession, JSONObject jsonObject) throws Exception {
        DaoStkImpawn daoStkImpawn = new DaoStkImpawn();
        DataStkImpawn dataStkImpawn = initStkImpawnData(objectParam, dataId);

        if (dataStkImpawn == null) {
            return;
        }
        if (ConstantUtil.STK_MATCH.equals(dataId)) {
            short stkBiz = ((OperationDataIdStkMatch) objectParam).getStkBiz();
            short stkBizAction = ((OperationDataIdStkMatch) objectParam).getStkBizAction();

            try {
                DataStkImpawn resultDataStkImpawn = daoStkImpawn.selectLock(dataStkImpawn, sqlSession);
                if (resultDataStkImpawn != null) {
                    //update
                    resultDataStkImpawn.setStkAvl(resultDataStkImpawn.getStkAvl() + dataStkImpawn.getStkAvl());

                    daoStkImpawn.update(resultDataStkImpawn, sqlSession);
                } else {
                    //insert   质押入库成交时， 如果没有数据，则插入
                    boolean flag = (ConstantUtil.STK_BIZ_BOND_PLEDGE_IN == stkBiz
                                    && ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction);
                    if (flag) {
                        daoStkImpawn.insert(dataStkImpawn, sqlSession);
                    } else {
                        logger.error("查表 STK_IMPAWN 返回 NULL ! ");
                    }
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public static List<DataStkImpawn> getStkImpawnDataList(DataStkImpawn condDataStkImpawn) throws Exception {
        DaoStkImpawn daoStkImpawn = new DaoStkImpawn();
        try {
            List<DataStkImpawn> dataStkImpawnList = daoStkImpawn.selectList(condDataStkImpawn);
            if (dataStkImpawnList != null && dataStkImpawnList.size() > 0) {
                return dataStkImpawnList;
            } else {
                logger.error("查表 STK_IMPAWN 返回 null ! ");
                return null;
            }
        } catch (Exception e) {
            throw new Exception("查表 STK_IMPAWN 抛出异常 ! ", e);
        }
    }
}
