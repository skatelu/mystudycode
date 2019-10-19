/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationOptAsset
 * Author:   ZhangChangHong
 * Date:     2018/7/26
 * Description: OPT_ASSET操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.dao.DaoOptAsset;
import com.szkingdom.data.DataOptAsset;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class OperationOptAsset extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationOptAsset.class);

    /**
     * 组装持仓变动数据
     */
    public static DataOptAsset initOptAssetData(Object objectParam, String dataId) {
        DataOptAsset dataOptAsset = new DataOptAsset();
        if (ConstantUtil.OPT_TRADE.equals(dataId)) {

            OperationDataIdOptTrade operationDataIdOptTrade = (OperationDataIdOptTrade) objectParam;
            short stkBiz = operationDataIdOptTrade.getStkBiz();
            short stkBizAction = operationDataIdOptTrade.getStkBizAction();
            long orderQty = operationDataIdOptTrade.getOrderQty();

            // 设置唯一索引
            dataOptAsset.setCuacctCode(operationDataIdOptTrade.getCuacctCode());
            dataOptAsset.setOptNum(operationDataIdOptTrade.getOptNum());
            dataOptAsset.setStkbd(operationDataIdOptTrade.getStkbd());
            dataOptAsset.setStkpbu(operationDataIdOptTrade.getStkpbu());
            dataOptAsset.setTrdacct(operationDataIdOptTrade.getTrdacct());

            if (ConstantUtil.STK_BIZ_BUY_OPEN_POSITION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_LONG);
                dataOptAsset.setOptCvdFlag(ConstantUtil.NOT_CVD);
                dataOptAsset.setOptPosiRlt(orderQty);
            } else if (ConstantUtil.STK_BIZ_SELL_CLOSE_POSITION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_LONG);
                dataOptAsset.setOptCvdFlag(ConstantUtil.NOT_CVD);
                dataOptAsset.setOptAvl(-orderQty);
                dataOptAsset.setOptClsUnmatched(orderQty);
            } else if (ConstantUtil.STK_BIZ_SELL_OPEN_POSITION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_SHORT);
                dataOptAsset.setOptCvdFlag(ConstantUtil.NOT_CVD);
                dataOptAsset.setOptPosiRlt(orderQty);
            } else if (ConstantUtil.STK_BIZ_BUY_CLOSE_POSITION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_SHORT);
                dataOptAsset.setOptCvdFlag(ConstantUtil.NOT_CVD);
                dataOptAsset.setOptAvl(-orderQty);
                dataOptAsset.setOptClsUnmatched(orderQty);
            } else if (ConstantUtil.STK_BIZ_COVERED_CALL_POSITION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_COVERED);
                dataOptAsset.setOptCvdFlag(ConstantUtil.CVD);
                dataOptAsset.setOptPosiRlt(orderQty);
            } else if (ConstantUtil.STK_BIZ_COVERED_CLOSE_POSITION == stkBiz && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_COVERED);
                dataOptAsset.setOptCvdFlag(ConstantUtil.CVD);
                dataOptAsset.setOptAvl(-orderQty);
                dataOptAsset.setOptClsUnmatched(orderQty);
            } else if ((ConstantUtil.STK_BIZ_EXE_CALL_OPTION == stkBiz || ConstantUtil.STK_BIZ_EXE_PUT_OPTION == stkBiz)
                       && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_LONG);
                dataOptAsset.setOptCvdFlag(ConstantUtil.NOT_CVD);
                dataOptAsset.setOptAvl(-orderQty);
            } else {
                return null;
            }

            dataOptAsset.setCustCode(operationDataIdOptTrade.getCustCode());
            dataOptAsset.setCustType(operationDataIdOptTrade.getCustType());
            dataOptAsset.setCuacctCode(operationDataIdOptTrade.getCuacctCode());
            dataOptAsset.setIntOrg(operationDataIdOptTrade.getIntOrg());
            dataOptAsset.setStkbd(operationDataIdOptTrade.getStkbd());
            dataOptAsset.setStkpbu(operationDataIdOptTrade.getStkpbu());
            dataOptAsset.setTrdacct(operationDataIdOptTrade.getTrdacct());
            dataOptAsset.setSubacctCode(operationDataIdOptTrade.getSubacctCode());
            dataOptAsset.setCurrency(operationDataIdOptTrade.getCurrency());
            dataOptAsset.setOptNum(operationDataIdOptTrade.getOptNum());
            dataOptAsset.setOptName(operationDataIdOptTrade.getOptName());
            dataOptAsset.setOptUndlCls(operationDataIdOptTrade.getOptUndlCls());
            dataOptAsset.setOptUndlCode(operationDataIdOptTrade.getOptUndlCode());
            dataOptAsset.setStkex(operationDataIdOptTrade.getStkbd().charAt(0));
            dataOptAsset.setTrdacctExcls(operationDataIdOptTrade.getTrdacctExcls());
            dataOptAsset.setOptType(operationDataIdOptTrade.getOptType());
            dataOptAsset.setOptCode(operationDataIdOptTrade.getOptCode());

        } else if (ConstantUtil.OPT_MATCH.equals(dataId)) {
            OperationDataIdOptMatch operationDataIdOptMatch = (OperationDataIdOptMatch) objectParam;
            short stkBiz = operationDataIdOptMatch.getStkBiz();
            short stkBizAction = operationDataIdOptMatch.getStkBizAction();
            long matchedQty = operationDataIdOptMatch.getMatchedQty();

            // 设置唯一索引
            dataOptAsset.setCuacctCode(operationDataIdOptMatch.getCuacctCode());
            dataOptAsset.setOptNum(operationDataIdOptMatch.getOptNum());
            dataOptAsset.setStkbd(operationDataIdOptMatch.getStkbd());
            dataOptAsset.setStkpbu(operationDataIdOptMatch.getStkpbu());
            dataOptAsset.setTrdacct(operationDataIdOptMatch.getTrdacct());

            if (ConstantUtil.STK_BIZ_BUY_OPEN_POSITION == stkBiz) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_LONG);
                dataOptAsset.setOptCvdFlag(ConstantUtil.NOT_CVD);
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 买开撤单成交
                    dataOptAsset.setOptPosiRlt(matchedQty);
                } else {
                    // 买开正常成交
                    dataOptAsset.setOptAvl(matchedQty);
                    dataOptAsset.setOptTrdBln(matchedQty);
                    dataOptAsset.setOptDailyOpenRlt(matchedQty);
                }
            } else if (ConstantUtil.STK_BIZ_SELL_CLOSE_POSITION == stkBiz) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_LONG);
                dataOptAsset.setOptCvdFlag(ConstantUtil.NOT_CVD);
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 卖平撤单成交
                    dataOptAsset.setOptAvl(-matchedQty);
                    dataOptAsset.setOptClsUnmatched(matchedQty);
                } else {
                    // 卖平正常成交
                    dataOptAsset.setOptTrdBln(-matchedQty);
                    dataOptAsset.setOptPosiRlt(-matchedQty);
                    dataOptAsset.setOptClsUnmatched(-matchedQty);
                }
            } else if (ConstantUtil.STK_BIZ_SELL_OPEN_POSITION == stkBiz) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_SHORT);
                dataOptAsset.setOptCvdFlag(ConstantUtil.NOT_CVD);
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 卖开撤单成交
                    dataOptAsset.setOptPosiRlt(matchedQty);
                } else {
                    // 卖开正常成交
                    dataOptAsset.setOptAvl(matchedQty);
                    dataOptAsset.setOptTrdBln(matchedQty);
                    dataOptAsset.setOptDailyOpenRlt(matchedQty);
                }
            } else if (ConstantUtil.STK_BIZ_BUY_CLOSE_POSITION == stkBiz) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_SHORT);
                dataOptAsset.setOptCvdFlag(ConstantUtil.NOT_CVD);
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 买平撤单成交
                    dataOptAsset.setOptAvl(-matchedQty);
                    dataOptAsset.setOptClsUnmatched(matchedQty);
                } else {
                    // 买平正常成交
                    dataOptAsset.setOptTrdBln(-matchedQty);
                    dataOptAsset.setOptPosiRlt(-matchedQty);
                    dataOptAsset.setOptClsUnmatched(-matchedQty);
                }
            } else if (ConstantUtil.STK_BIZ_COVERED_CALL_POSITION == stkBiz) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_COVERED);
                dataOptAsset.setOptCvdFlag(ConstantUtil.CVD);
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 备开撤单成交
                    dataOptAsset.setOptPosiRlt(matchedQty);
                } else {
                    // 备开正常成交
                    dataOptAsset.setOptAvl(matchedQty);
                    dataOptAsset.setOptTrdBln(matchedQty);
                    dataOptAsset.setOptCvdAsset(matchedQty);
                    dataOptAsset.setOptDailyOpenRlt(matchedQty);
                }
            } else if (ConstantUtil.STK_BIZ_COVERED_CLOSE_POSITION == stkBiz) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_COVERED);
                dataOptAsset.setOptCvdFlag(ConstantUtil.CVD);
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 备平撤单成交
                    dataOptAsset.setOptAvl(-matchedQty);
                    dataOptAsset.setOptClsUnmatched(matchedQty);
                } else {
                    // 备平正常成交
                    dataOptAsset.setOptCvdAsset(-matchedQty);
                    dataOptAsset.setOptTrdBln(-matchedQty);
                    dataOptAsset.setOptPosiRlt(-matchedQty);
                    dataOptAsset.setOptClsUnmatched(-matchedQty);
                }
            } else if (ConstantUtil.STK_BIZ_EXE_CALL_OPTION == stkBiz) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_LONG);
                dataOptAsset.setOptCvdFlag(ConstantUtil.NOT_CVD);
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 认购行权撤单成交
                    dataOptAsset.setOptAvl(-matchedQty);
                } else {
                    // 认购行权正常成交
                    return null;
                }
            } else if (ConstantUtil.STK_BIZ_EXE_PUT_OPTION == stkBiz) {
                dataOptAsset.setOptSide(ConstantUtil.OPT_LONG);
                dataOptAsset.setOptCvdFlag(ConstantUtil.NOT_CVD);
                if (ConstantUtil.STK_BIZ_ACTION_CANCEL_MATCHING == stkBizAction) {
                    // 认沽行权撤单成交
                    dataOptAsset.setOptAvl(-matchedQty);
                } else {
                    // 认沽行权正常成交
                    return null;
                }
            } else {
                return null;
            }

            dataOptAsset.setCustCode(operationDataIdOptMatch.getCustCode());
            //dataOptAsset.setCustType(operationDataIdOptTrade.getUserType()); 字段取消
            dataOptAsset.setCuacctCode(operationDataIdOptMatch.getCuacctCode());
            dataOptAsset.setIntOrg(operationDataIdOptMatch.getIntOrg());
            dataOptAsset.setStkbd(operationDataIdOptMatch.getStkbd());
            dataOptAsset.setStkpbu(operationDataIdOptMatch.getStkpbu());
            dataOptAsset.setTrdacct(operationDataIdOptMatch.getTrdacct());
            dataOptAsset.setSubacctCode(operationDataIdOptMatch.getSubacctCode());
            dataOptAsset.setCurrency(operationDataIdOptMatch.getCurrency());
            dataOptAsset.setOptNum(operationDataIdOptMatch.getOptNum());
            dataOptAsset.setOptName(operationDataIdOptMatch.getOptName());
            dataOptAsset.setOptUndlCls(operationDataIdOptMatch.getOptUndlCls());
            dataOptAsset.setOptUndlCode(operationDataIdOptMatch.getOptUndlCode());
            dataOptAsset.setStkex(operationDataIdOptMatch.getStkbd().charAt(0));
            //dataOptAsset.setTrdacctExcls('0');
            dataOptAsset.setOptType(operationDataIdOptMatch.getOptType());
            dataOptAsset.setOptCode(operationDataIdOptMatch.getOptCode());

        } else if (ConstantUtil.OPT_ASSET.equals(dataId)) {
            OperationDataIdOptAsset operationDataIdOptAsset = (OperationDataIdOptAsset) objectParam;
            Character optChangeFlag = operationDataIdOptAsset.getOptChangeFlag();
            Character optSide = operationDataIdOptAsset.getOptSide();
            long optBln = operationDataIdOptAsset.getOptBlnVary();
            long optAvl = operationDataIdOptAsset.getOptAvlVary();

            // 设置唯一索引
            dataOptAsset.setCuacctCode(operationDataIdOptAsset.getCuacctCode());
            dataOptAsset.setOptNum(operationDataIdOptAsset.getOptNum());
            dataOptAsset.setStkbd(operationDataIdOptAsset.getStkbd());
            dataOptAsset.setStkpbu(operationDataIdOptAsset.getStkpbu());
            dataOptAsset.setTrdacct(operationDataIdOptAsset.getTrdacct());
            dataOptAsset.setOptSide(optSide);

            if (ConstantUtil.FRZ_FLAG.equals(optChangeFlag)) {
                // 合约冻结
                dataOptAsset.setOptAvl(-optAvl);
            } else if (ConstantUtil.UFZ_FLAG.equals(optChangeFlag)) {
                // 合约解冻
                dataOptAsset.setOptAvl(optAvl);
            } else if (ConstantUtil.RED_FLAG.equals(optChangeFlag)) {
                // 合约红冲
                dataOptAsset.setOptBln(-optAvl);
                dataOptAsset.setOptAvl(-optAvl);
                dataOptAsset.setOptCvdAsset(-optAvl);
                dataOptAsset.setOptPosiRlt(-optAvl);
            } else if (ConstantUtil.BLUE_FLAG.equals(optChangeFlag)) {
                // 合约蓝补
                dataOptAsset.setOptBln(optAvl);
                dataOptAsset.setOptAvl(optAvl);
                dataOptAsset.setOptCvdAsset(optAvl);
                dataOptAsset.setOptPosiRlt(optAvl);
            }
        }

        return dataOptAsset;
    }

    /**
     * 锁持仓表，更新数据
     * @param objectParam
     * @param dataId
     * @param sqlSession
     * @throws Exception
     */
    public static void writeOptAssetData2DB(Object objectParam, String dataId,
                                            SqlSession sqlSession, JSONObject jsonObject) throws Exception {
        DaoOptAsset daoOptAsset = new DaoOptAsset();
        DataOptAsset dataOptAsset = initOptAssetData(objectParam, dataId);
        if (dataOptAsset == null) {
            return;
        }

        short stkBiz = 0;
        short stkBizAction = 0;
        String log = "";

        try {
            DataOptAsset resultDataOptAsset = daoOptAsset.selectLock(dataOptAsset, sqlSession);
            if (resultDataOptAsset != null) {
                //内存数据库更新

                // update
                long resOptAvl = resultDataOptAsset.getOptAvl();
                long optAvlTmp = dataOptAsset.getOptAvl();

                if (ConstantUtil.OPT_TRADE.equals(dataId)) {
                    stkBiz = ((OperationDataIdOptTrade) objectParam).getStkBiz();
                    stkBizAction = ((OperationDataIdOptTrade) objectParam).getStkBizAction();

                    // 认购行权正常委托 减合约平仓未成交数量 （可用不足时）
                    if ((ConstantUtil.STK_BIZ_EXE_CALL_OPTION == stkBiz || ConstantUtil.STK_BIZ_EXE_PUT_OPTION == stkBiz)
                            && ConstantUtil.STK_BIZ_ACTION_CANCEL_OFFER != stkBizAction) {
                       long optAvl = resOptAvl + optAvlTmp;
                       if (optAvl < 0) {
                           optAvlTmp = -resOptAvl;
                           dataOptAsset.setOptClsUnmatched(optAvl);
                       }
                    }
                } else if (ConstantUtil.OPT_MATCH.equals(dataId)) {
                    // 合约平仓未成交数量 增（可用不足时）逻辑未加
                }

                resultDataOptAsset.setOptBln(resultDataOptAsset.getOptBln() + dataOptAsset.getOptBln());
                resultDataOptAsset.setOptAvl(resOptAvl + optAvlTmp);
                //resultDataOptAsset.setOptFrz(resultDataOptAsset.getOptFrz() + dataOptAsset.getOptFrz());
                //resultDataOptAsset.setOptUfz(resultDataOptAsset.getOptUfz() + dataOptAsset.getOptUfz());
                //resultDataOptAsset.setOptTrdFrz(resultDataOptAsset.getOptTrdFrz() + dataOptAsset.getOptTrdFrz());
                //resultDataOptAsset.setOptTrdUfz(resultDataOptAsset.getOptTrdUfz() + dataOptAsset.getOptTrdUfz());
                resultDataOptAsset.setOptTrdBln(resultDataOptAsset.getOptTrdBln() + dataOptAsset.getOptTrdBln());
                resultDataOptAsset.setOptCvdAsset(resultDataOptAsset.getOptCvdAsset() + dataOptAsset.getOptCvdAsset());
                resultDataOptAsset.setOptPosiRlt(resultDataOptAsset.getOptPosiRlt() + dataOptAsset.getOptPosiRlt());
                resultDataOptAsset.setOptClsUnmatched(resultDataOptAsset.getOptClsUnmatched() + dataOptAsset.getOptClsUnmatched());
                resultDataOptAsset.setOptDailyOpenRlt(resultDataOptAsset.getOptDailyOpenRlt() + dataOptAsset.getOptDailyOpenRlt());

                daoOptAsset.update(resultDataOptAsset, sqlSession);
            } else {
                // 401 403 405 平仓操作正确性判断，委托通过，成交不用检查
                if (ConstantUtil.OPT_TRADE.equals(dataId)) {
                    stkBiz = ((OperationDataIdOptTrade) objectParam).getStkBiz();
                    if ((ConstantUtil.STK_BIZ_SELL_CLOSE_POSITION == stkBiz)
                         || (ConstantUtil.STK_BIZ_BUY_CLOSE_POSITION == stkBiz)
                            || (ConstantUtil.STK_BIZ_COVERED_CLOSE_POSITION == stkBiz)) {
                        log = "writeOptAssetData2DB 平仓委托操作，查无数据, StkBiz:["
                                + stkBiz + "], CuacctCode:[" +  dataOptAsset.getCuacctCode() + "], OptNum:["
                                + dataOptAsset.getOptNum() + "], Stkbd:[" + dataOptAsset.getStkbd() + "], Stkpbu:["
                                + dataOptAsset.getStkpbu() + "], Trdacct:[" + dataOptAsset.getTrdacct()
                                + "], OptSide:[" + dataOptAsset.getOptSide() + "]";
                        throw new Exception(log);
                    }
                } else if (ConstantUtil.OPT_ASSET.equals(dataId)) {
                    log = "writeOptAssetData2DB 股份调整操作，查无数据,BizCode:["
                            + ((OperationDataIdOptAsset) objectParam).getBizCode()
                            + "], CuacctCode:[" +  dataOptAsset.getCuacctCode() + "], OptNum:["
                            + dataOptAsset.getOptNum() + "], Stkbd:[" + dataOptAsset.getStkbd() + "], Stkpbu:["
                            + dataOptAsset.getStkpbu() + "], Trdacct:[" + dataOptAsset.getTrdacct()
                            + "], OptSide:[" + dataOptAsset.getOptSide() + "]";
                    throw new Exception(log);
                }

                // 内存数据库写入

                // insert
                daoOptAsset.insert(dataOptAsset, sqlSession);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 获取表OPT_ASSET的备兑股份数量字段OPT_CVD_ASSET,并累加
     * @param condDataOptAsset
     * @return
     * @throws Exception
     */
    public static long getOptCvdAssetByOptAsset(DataOptAsset condDataOptAsset) throws Exception{
        long optCvdAsset = 0L;

        DaoOptAsset daoOptAsset = new DaoOptAsset();
        try{
            List <DataOptAsset> dataOptAssetList = daoOptAsset.selectList(condDataOptAsset);
            if(dataOptAssetList != null && dataOptAssetList.size() > 0) {
                //字段OPT_CVD_ASSET累加
                for(DataOptAsset dataOptAssetEach : dataOptAssetList) {
                    optCvdAsset += dataOptAssetEach.getOptCvdAsset();
                }
            }
            else{
                logger.warn("查表 TABLE_OPT_ASSET 返回 NULL !");
            }
        } catch (Exception e){
            throw new Exception("查表 TABLE_OPT_ASSET for OPT_CVD_ASSET 抛出异常！ ", e);
        }
        return optCvdAsset;
    }

    /**
     * 获取合约持仓数量，取单客户单标的证券的持仓数量累加的结果，且合并OPT_BLN与OPT_TRD_BLN的值
     * @param dataOptAssetPara
     * @return
     * @throws Exception
     */
    public static long getOptBln(DataOptAsset dataOptAssetPara) throws Exception {
        long OptBlnResult = 0L;

        DaoOptAsset daoOptAsset = new DaoOptAsset();
        try {
            List<DataOptAsset> resultDataOptAsset = daoOptAsset.selectList(dataOptAssetPara);

            if (resultDataOptAsset != null && resultDataOptAsset.size() > 0) {
                for (DataOptAsset dataOptAst : resultDataOptAsset) {
                    // 在此循环累加单客户，单标的持仓数量
                    OptBlnResult += dataOptAst.getOptBln();
                    OptBlnResult += dataOptAst.getOptTrdBln();
                }
            }
        } catch (Exception e) {
            throw new Exception("查表OPT_ASSET获取持仓数量异常", e);
        }

        return OptBlnResult;
    }

    /**
     * 持仓数据收集
     * @param condDataOptAsset
     * @return
     * @throws Exception
     */
    public static List<DataOptAsset> listOptAssetDataByParam(DataOptAsset condDataOptAsset) throws Exception {
        DaoOptAsset daoOptAsset = new DaoOptAsset();
        try {
             List<DataOptAsset> resListDataOptAsset = daoOptAsset.selectList(condDataOptAsset);
            if (resListDataOptAsset != null && !resListDataOptAsset.isEmpty()) {
                return resListDataOptAsset;
            } else {
                logger.error("listOptAssetDataByParam() 持仓收集查无数据, CuacctCode:{}, OptNum:{}, Stkbd:{}, Stkpbu:{}, Trdacct:{}, OptSide:{}",
                                                                                    condDataOptAsset.getCuacctCode(), condDataOptAsset.getOptNum(),
                                                                                    condDataOptAsset.getStkbd(), condDataOptAsset.getStkpbu(),
                                                                                    condDataOptAsset.getTrdacct(), condDataOptAsset.getOptSide());
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 持仓数据收集保证金专用
     * @param condDataOptAsset
     * @return
     * @throws Exception
     */
    public static List<DataOptAsset> listOptAssetDataForMaginRiskByParam(DataOptAsset condDataOptAsset, boolean hedged) throws Exception {
        DaoOptAsset daoOptAsset = new DaoOptAsset();
        try {
            List<DataOptAsset> resListDataOptAsset;
            if (hedged) {
                resListDataOptAsset = daoOptAsset.selectListHedgedForMaginRisk(condDataOptAsset);
            }
            else {
                resListDataOptAsset = daoOptAsset.selectListNotHedgedForMaginRisk(condDataOptAsset);
            }

            if (resListDataOptAsset != null && !resListDataOptAsset.isEmpty()) {
                return resListDataOptAsset;
            } else {
                logger.error("listOptAssetDataForMaginRiskByParam() 持仓收集保证金专用查无数据, CuacctCode:{}, OptNum:{}, Stkbd:{}, Stkpbu:{}, Trdacct:{}, OptSide:{}",
                        condDataOptAsset.getCuacctCode(), condDataOptAsset.getOptNum(),
                        condDataOptAsset.getStkbd(), condDataOptAsset.getStkpbu(),
                        condDataOptAsset.getTrdacct(), condDataOptAsset.getOptSide());
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 获取单客户单标的备兑所需数量值，必送入参STKBD,TRDACCT,OPT_UNDL_CODE
     * @param condDataOptAsset
     * @return
     * @throws Exception
     */
    public static long getCvtQtyNeedByOptAsset(DataOptAsset condDataOptAsset) throws Exception {
        long CvtQtyNeed = 0L;
        DaoOptAsset daoOptAsset = new DaoOptAsset();
        try{
            Long resultObj = daoOptAsset.selectUniqueForCvd(condDataOptAsset);
            if(resultObj != null) {
                CvtQtyNeed = resultObj.longValue();
                return CvtQtyNeed;
            }
            else{
                throw new Exception("查表OPT_ASSET获取备兑所需数量无结果");
            }
        } catch (Exception e){
            throw new Exception("查表OPT_ASSET获取备兑所需数量异常", e);
        }
    }

    /**
     * 计算客户持仓市值
     * @param condDataOptAsset
     * @return
     * @throws Exception
     */
    public static long calcCustPosiMktVal(DataOptAsset condDataOptAsset) throws Exception {
        DaoOptAsset daoOptAsset = new DaoOptAsset();
        try {
            Long resultCustPosiMktVal = daoOptAsset.selectDataForCustPosiMktVal(condDataOptAsset);
            if (resultCustPosiMktVal != null) {
                return resultCustPosiMktVal.longValue();
            } else {
                logger.error("calcCustPosiMktVal() 计算客户总市值查无数据, CustCode:{}, Currency:{}, OptSide:{}",
                        condDataOptAsset.getCustCode(), condDataOptAsset.getCurrency(), condDataOptAsset.getOptSide());
                return 0L;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 计算客户义务仓(含备兑)市值
     * @param condDataOptAsset
     * @return
     * @throws Exception
     */
    public static long calcCustOptShortAndCoveredMktVal(DataOptAsset condDataOptAsset) throws Exception {
        DaoOptAsset daoOptAsset = new DaoOptAsset();
        try {
            Long resultCustOptShortAndCoveredMktVal = daoOptAsset.selectDataForCustOptShortAndCoveredMktVal(condDataOptAsset);
            if (resultCustOptShortAndCoveredMktVal != null) {
                return resultCustOptShortAndCoveredMktVal.longValue();
            } else {
                logger.error("calcCustOptShortAndCoveredMktVal() 计算客户义务仓(含备兑)市值查无数据, CustCode:{}, Currency:{}, OptSide:{}",
                        condDataOptAsset.getCustCode(), condDataOptAsset.getCurrency(), condDataOptAsset.getOptSide());
                return 0L;
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
