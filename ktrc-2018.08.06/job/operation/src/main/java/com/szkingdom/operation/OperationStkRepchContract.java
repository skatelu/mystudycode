/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkRepchContract
 * Author:   ZhangMaohua
 * Date:     2018/9/10
 * Description: 证券回购合约表操作
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.dao.DaoStkRepchContract;
import com.szkingdom.data.DataStkRepchContract;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class OperationStkRepchContract extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationStkRepchContract.class);

    public static DataStkRepchContract initStkRepchContractData(Object objectParam, String dataId) {
        DataStkRepchContract dataStkRepchContract = new DataStkRepchContract();

        if (ConstantUtil.STK_MATCH.equals(dataId)) {
            OperationDataIdStkMatch operationDataIdStkMatch = (OperationDataIdStkMatch)objectParam;
            short stkBiz = operationDataIdStkMatch.getStkBiz();
            short stkBizAction = operationDataIdStkMatch.getStkBizAction();

            //设置唯一索引
            dataStkRepchContract.setTrdDate(operationDataIdStkMatch.getTrdDate());
            dataStkRepchContract.setStkbd(operationDataIdStkMatch.getStkbd());
            dataStkRepchContract.setStkBiz(stkBiz);
            dataStkRepchContract.setCuacctCode(operationDataIdStkMatch.getCuacctCode());
            dataStkRepchContract.setMatchId(operationDataIdStkMatch.getMatchedSn());
            dataStkRepchContract.setStkCode(operationDataIdStkMatch.getStkCode());

            //融资回购正常成交
            boolean flag = ConstantUtil.STK_BIZ_BOND_PLEDGE_FINANCING == stkBiz && ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction;
            if (flag) {
                dataStkRepchContract.setStkex(operationDataIdStkMatch.getStkbd().charAt(0));
                dataStkRepchContract.setStkpbu(operationDataIdStkMatch.getStkpbu());
                dataStkRepchContract.setStkBizAction(stkBizAction);
                dataStkRepchContract.setIntOrg(operationDataIdStkMatch.getIntOrg());
                dataStkRepchContract.setCustCode(operationDataIdStkMatch.getCustCode());
                dataStkRepchContract.setCustName("");
                dataStkRepchContract.setTrdacct(operationDataIdStkMatch.getTrdacct());
                dataStkRepchContract.setMatchQty(operationDataIdStkMatch.getMatchedQty());
                dataStkRepchContract.setMatchPrice(operationDataIdStkMatch.getMatchedPrice());
                dataStkRepchContract.setMatchAmt(operationDataIdStkMatch.getMatchAmt());
                dataStkRepchContract.setCurrency(operationDataIdStkMatch.getCurrency());
                dataStkRepchContract.setStkCls(operationDataIdStkMatch.getStkCls());
                dataStkRepchContract.setStkFlag('0');//
                dataStkRepchContract.setRepchDate(operationDataIdStkMatch.getRepchDate());
                dataStkRepchContract.setRepchAmt(operationDataIdStkMatch.getRepchAmt());
                dataStkRepchContract.setRepchStatus('0');//
                dataStkRepchContract.setMatchTime(Integer.parseInt(operationDataIdStkMatch.getMatchedTime()));

                dataStkRepchContract.setDelayFlag('0');
            }
        }

        return dataStkRepchContract;
    }

    public static void writeStkRepchContractData2DB(Object objectParam, String dataId,
                                                    SqlSession sqlSession, JSONObject jsonObject) throws Exception {
        DaoStkRepchContract daoStkRepchContract = new DaoStkRepchContract();
        DataStkRepchContract dataStkRepchContract = initStkRepchContractData(objectParam, dataId);

        if (dataStkRepchContract == null) {
            return;
        }
        //融资回购正常成交情况时，才INSERT
        if (ConstantUtil.STK_MATCH.equals(dataId)) {
            short stkBiz = ((OperationDataIdStkMatch) objectParam).getStkBiz();
            short stkBizAction = ((OperationDataIdStkMatch) objectParam).getStkBizAction();
            if (ConstantUtil.STK_BIZ_BOND_PLEDGE_FINANCING == stkBiz && ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction) {
                try {
                    DataStkRepchContract resultDataStkRepchContract = daoStkRepchContract.selectLock(dataStkRepchContract, sqlSession);
                    if (resultDataStkRepchContract == null) {
                        //insert
                        daoStkRepchContract.insert(dataStkRepchContract, sqlSession);
                    } else {
                        //不需要update
                        logger.error("已存在该合约 ！" + dataStkRepchContract);
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }

    public static DataStkRepchContract getStkRepchContractDataByUnique(DataStkRepchContract condDataStkRepchContract) throws Exception {
        DaoStkRepchContract daoStkRepchContract = new DaoStkRepchContract();
        try {
            DataStkRepchContract resultDataStkRepchContract = daoStkRepchContract.selectUnique(condDataStkRepchContract);
            if (resultDataStkRepchContract != null) {
                return resultDataStkRepchContract;
            } else {
                logger.error("按唯一索引查表 STK_REPCH_CONTRACT 返回 null ! ");
                return null;
            }
        } catch (Exception e) {
            throw new Exception("按唯一索引查表 STK_REPCH_CONTRACT 抛出异常 ! ", e);
        }
    }

    public static List<DataStkRepchContract> getStkRepchContractDataList(DataStkRepchContract condDataStkRepchContract) throws Exception {
        DaoStkRepchContract daoStkRepchContract = new DaoStkRepchContract();
        try {
            List<DataStkRepchContract> dataStkRepchContractList = daoStkRepchContract.selectList(condDataStkRepchContract);
            if (dataStkRepchContractList != null && dataStkRepchContractList.size() > 0) {
                return dataStkRepchContractList;
            } else {
                logger.error("查表 STK_REPCH_CONTRACT 返回 null ! ");
                return null;
            }
        } catch (Exception e) {
            throw new Exception("查表 STK_REPCH_CONTRACT 抛出异常 ! ", e);
        }
    }
}
