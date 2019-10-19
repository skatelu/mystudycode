/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkAsset
 * Author:   ZhangMaohua
 * Date:     2018/8/4
 * Description:
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.dao.DaoStkAsset;
import com.szkingdom.data.DataStkAsset;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationStkAsset extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationStkAsset.class);
    	
    public static DataStkAsset getStkAssetUnique(DataStkAsset condDataStkAsset) throws Exception {
        DaoStkAsset daoStkAsset = new DaoStkAsset();
        try {
            DataStkAsset resultDataStkAsset = daoStkAsset.selectUnique(condDataStkAsset);
            if (resultDataStkAsset != null) {
                return resultDataStkAsset;
            } else {
                logger.error("按唯一索引查表 TABLE_STK_ASSET 返回 NULL !");
                return null;
            }
        } catch (Exception e) {
            throw new Exception("按唯一索引查表 TABLE_STK_ASSET 抛出异常", e);
        }
    }

    public static List<DataStkAsset> getStkAssetDataList(DataStkAsset condDataStkAsset) throws Exception {
        DaoStkAsset daoStkAsset = new DaoStkAsset();
        try {
            List<DataStkAsset> resultDataStkAssetList = daoStkAsset.selectList(condDataStkAsset);
            if (resultDataStkAssetList != null && resultDataStkAssetList.size() > 0) {
                return resultDataStkAssetList;
            } else {
                logger.error("查表 TABLE_STK_ASSET 返回 NULL !");
                return null;
            }
        } catch (Exception e) {
            throw new Exception("查表 TABLE_STK_ASSET 抛出异常", e);
        }
    }

    public static DataStkAsset initStkAssetData(Object objectParam, String dataId) {
        DataStkAsset dataStkAsset = new DataStkAsset();
        boolean flag = false;

        if (ConstantUtil.STK_TRADE.equals(dataId)) {
            OperationDataIdStkTrade operationDataIdStkTrade = (OperationDataIdStkTrade) objectParam;
            short stkBiz = operationDataIdStkTrade.getStkBiz();
            short stkBizAction = operationDataIdStkTrade.getStkBizAction();

            //设置唯一索引
            dataStkAsset.setCuacctCode(operationDataIdStkTrade.getCuacctCode());
            dataStkAsset.setStkbd(operationDataIdStkTrade.getStkbd());
            dataStkAsset.setStkpbu(operationDataIdStkTrade.getStkpbu());
            dataStkAsset.setTrdacct(operationDataIdStkTrade.getTrdacct());

            dataStkAsset.setCustCode(operationDataIdStkTrade.getCustCode());
            dataStkAsset.setIntOrg(operationDataIdStkTrade.getIntOrg());
            dataStkAsset.setStkex(operationDataIdStkTrade.getStkbd().charAt(0));
            dataStkAsset.setCurrency(operationDataIdStkTrade.getCurrency());

            //质押入库正常委托: StkAvl减，StkTrdFrz加
            if (ConstantUtil.STK_BIZ_BOND_PLEDGE_IN == stkBiz && ConstantUtil.STK_BIZ_ACTION_LIMIT_OFFER == stkBizAction ) {
                dataStkAsset.setStkCode(operationDataIdStkTrade.getStkCode());
                dataStkAsset.setStkAvl(-operationDataIdStkTrade.getOrderQty());
                dataStkAsset.setStkTrdFrz(operationDataIdStkTrade.getOrderQty());
            }
            //质押出库正常委托, 标准券 ： StkAvl减，StkTrdFrz加
            else if (ConstantUtil.STK_BIZ_BOND_PLEDGE_OUT == stkBiz && ConstantUtil.STK_BIZ_ACTION_LIMIT_OFFER == stkBizAction){
                dataStkAsset.setStkCode(operationDataIdStkTrade.getStdBondCode());
                dataStkAsset.setStkAvl(-operationDataIdStkTrade.getSrdBondQty());
                dataStkAsset.setStkTrdFrz(operationDataIdStkTrade.getSrdBondQty());
            }
            //融资回购正常委托，标准券 : StkAvl减，StkTrdFrz加
            else if (ConstantUtil.STK_BIZ_BOND_PLEDGE_FINANCING == stkBiz && ConstantUtil.STK_BIZ_ACTION_LIMIT_OFFER == stkBizAction){
                dataStkAsset.setStkCode(operationDataIdStkTrade.getStdBondCode());
                dataStkAsset.setStkAvl(-operationDataIdStkTrade.getSrdBondQty());
                dataStkAsset.setStkTrdFrz(operationDataIdStkTrade.getSrdBondQty());
            }

        } else if (ConstantUtil.STK_MATCH.equals(dataId)) {
            OperationDataIdStkMatch operationDataIdStkMatch = (OperationDataIdStkMatch)objectParam;
            short stkBiz = operationDataIdStkMatch.getStkBiz();
            short stkBizAction = operationDataIdStkMatch.getStkBizAction();

            //设置唯一索引
            dataStkAsset.setCuacctCode(operationDataIdStkMatch.getCuacctCode());
            dataStkAsset.setStkbd(operationDataIdStkMatch.getStkbd());
            dataStkAsset.setStkpbu(operationDataIdStkMatch.getStkpbu());
            dataStkAsset.setTrdacct(operationDataIdStkMatch.getTrdacct());

            dataStkAsset.setCustCode(operationDataIdStkMatch.getCustCode());
            dataStkAsset.setIntOrg(operationDataIdStkMatch.getIntOrg());
            dataStkAsset.setStkex(operationDataIdStkMatch.getStkbd().charAt(0));
            dataStkAsset.setCurrency(operationDataIdStkMatch.getCurrency());

            //质押入库撤单成交: StkAvl加，StkTrdFrz减
            if (ConstantUtil.STK_BIZ_BOND_PLEDGE_IN == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                dataStkAsset.setStkCode(operationDataIdStkMatch.getStkCode());
                dataStkAsset.setStkAvl(operationDataIdStkMatch.getMatchedQty());
                dataStkAsset.setStkTrdFrz(-operationDataIdStkMatch.getMatchedQty());
            }
            //质押入库正常成交: StkTrdBln减（标准券是加），
            else if (ConstantUtil.STK_BIZ_BOND_PLEDGE_IN == stkBiz && ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction){
                dataStkAsset.setStkCode(operationDataIdStkMatch.getStkCode());
                dataStkAsset.setStkTrdBln(-operationDataIdStkMatch.getMatchedQty());

            }
            //质押出库撤单成交，标准券 ： StkAvl加，StkTrdFrz减
            else if (ConstantUtil.STK_BIZ_BOND_PLEDGE_OUT == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction){
                dataStkAsset.setStkCode(operationDataIdStkMatch.getStdBondCode());
                dataStkAsset.setStkAvl(operationDataIdStkMatch.getSrdBondQty());
                dataStkAsset.setStkTrdFrz(-operationDataIdStkMatch.getSrdBondQty());
            }
            //质押出库正常成交:
            else if (ConstantUtil.STK_BIZ_BOND_PLEDGE_OUT == stkBiz && ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction){
                dataStkAsset.setStkCode(operationDataIdStkMatch.getStkCode());
                long bondQty = operationDataIdStkMatch.getMatchedQty();
                dataStkAsset.setStkAvl(bondQty);
                dataStkAsset.setStkTrdUfz(bondQty);
                dataStkAsset.setStkTrdBln(bondQty);
            }
            //融资回购正常成交，标准券 : StkTrdBln减
            else if (ConstantUtil.STK_BIZ_BOND_PLEDGE_FINANCING == stkBiz && ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction){
                dataStkAsset.setStkCode(operationDataIdStkMatch.getStdBondCode());
                dataStkAsset.setStkTrdBln(-operationDataIdStkMatch.getSrdBondQty());
            }
            //融资回购撤单成交，标准券 : StkAvl加，StkTrdFrz减
            else if (ConstantUtil.STK_BIZ_BOND_PLEDGE_FINANCING == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction){
                dataStkAsset.setStkCode(operationDataIdStkMatch.getStdBondCode());
                dataStkAsset.setStkAvl(operationDataIdStkMatch.getSrdBondQty());
                dataStkAsset.setStkTrdFrz(-operationDataIdStkMatch.getSrdBondQty());
            }
        }
        return dataStkAsset;
    }

    public static void writeStkAssetData2DB(Object objectParam, String dataId,
                                            SqlSession sqlSession, JSONObject jsonObject) throws Exception {
        DaoStkAsset daoStkAsset = new DaoStkAsset();
        DataStkAsset dataStkAsset = initStkAssetData(objectParam, dataId);
        if (dataStkAsset == null) {
            return;
        }

        //质押入库正常成交、质押出库正常成交，STK_ASSET正股证券和标准券都要更新
        if (ConstantUtil.STK_MATCH.equals(dataId)) {
            short stkBiz = ((OperationDataIdStkMatch) objectParam).getStkBiz();
            short stkBizAction = ((OperationDataIdStkMatch) objectParam).getStkBizAction();

            //更新正股债券
            try {
                DataStkAsset resultDataStkAsset = daoStkAsset.selectLock(dataStkAsset, sqlSession);
                if (resultDataStkAsset != null) {
                    //update 证券可用数量/交易冻结数量/交易解冻数量/交易轧差数量
                    resultDataStkAsset.setStkAvl(resultDataStkAsset.getStkAvl() + dataStkAsset.getStkTrdBln());
                    resultDataStkAsset.setStkTrdBln(resultDataStkAsset.getStkTrdBln() + dataStkAsset.getStkTrdBln());
                    resultDataStkAsset.setStkTrdFrz(resultDataStkAsset.getStkTrdFrz() + dataStkAsset.getStkTrdFrz());
                    resultDataStkAsset.setStkTrdUfz(resultDataStkAsset.getStkUfz() + dataStkAsset.getStkTrdUfz());

                    daoStkAsset.update(resultDataStkAsset, sqlSession);
                } else {
                    //质押出库正常成交，查不到数据时需要insert
                    if (ConstantUtil.STK_BIZ_BOND_PLEDGE_OUT == stkBiz && ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction) {
                        daoStkAsset.insert(dataStkAsset,sqlSession);
                    } else {
                        logger.error("维护数据查表 STK_ASSET 返回 NULL ！ ");
                    }
                }
            } catch (Exception e) {
                throw e;
            }

            //质押入库正常成交、质押出库正常成交时，更新标准券
            if((ConstantUtil.STK_BIZ_BOND_PLEDGE_IN == stkBiz && ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction)
             || (ConstantUtil.STK_BIZ_BOND_PLEDGE_OUT == stkBiz && ConstantUtil.STK_BIZ_ACTION_ORDER_MATCHING == stkBizAction)) {
                dataStkAsset.setStkCode(((OperationDataIdStkMatch) objectParam).getStdBondCode());
                //质押入库正常成交：StkTrdBln加 ，质押出库正常成交：StkTrdBln减
                if (ConstantUtil.STK_BIZ_BOND_PLEDGE_IN == stkBiz ) {
                    long bondQty = ((OperationDataIdStkMatch) objectParam).getSrdBondQty();
                    dataStkAsset.setStkTrdBln(bondQty);
                    dataStkAsset.setStkAvl(bondQty);
                    dataStkAsset.setStkTrdUfz(bondQty);
                }
                if (ConstantUtil.STK_BIZ_BOND_PLEDGE_OUT == stkBiz ) {
                    dataStkAsset.setStkTrdBln(-((OperationDataIdStkMatch) objectParam).getSrdBondQty());
                    dataStkAsset.setStkAvl(0L);
                    dataStkAsset.setStkTrdUfz(0L);
                }
                try {
                    DataStkAsset resultDataStkAsset = daoStkAsset.selectLock(dataStkAsset, sqlSession);
                    if (resultDataStkAsset != null) {
                        //update
                        resultDataStkAsset.setStkTrdBln(resultDataStkAsset.getStkTrdBln() + dataStkAsset.getStkTrdBln());
                        resultDataStkAsset.setStkAvl(resultDataStkAsset.getStkAvl() + dataStkAsset.getStkAvl());
                        resultDataStkAsset.setStkTrdUfz(resultDataStkAsset.getStkUfz() + dataStkAsset.getStkTrdUfz());

                        daoStkAsset.update(resultDataStkAsset, sqlSession);
                    } else {
                        //质押入库正常成交，查不到时需要insert
                        if (ConstantUtil.STK_BIZ_BOND_PLEDGE_IN == stkBiz ) {
                            daoStkAsset.insert(dataStkAsset,sqlSession);
                        } else {
                            logger.error("维护数据查表 STK_ASSET 返回 NULL ！ ");
                        }
                    }
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }
}
