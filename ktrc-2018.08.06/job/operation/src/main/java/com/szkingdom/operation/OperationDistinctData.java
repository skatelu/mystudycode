/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationDistinctData
 * Author:   ZhangChangHong
 * Date:     2018/7/25
 * Description: 数据去重操作，各系统去重通用，方法内区分数据来源系统
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.dao.*;
import com.szkingdom.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// 后期合并
import com.szkingdom.common.ZtsConstantUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OperationDistinctData extends OperationBase {

    protected static Logger logger = LoggerFactory.getLogger(OperationDistinctData.class);

    public static boolean distinctExistsData(String dataId,
                                             Object objectParam,
                                             StringBuilder stringBuilder,
                                             List<Object> listObjectTableData) throws Exception {
        try {
            if (ConstantUtil.SPOT_QUOTATION.equals(dataId)) {

            } else if (ConstantUtil.OPT_QUOTATION.equals(dataId)) {

            } else if (ConstantUtil.OPT_TRADE.equals(dataId)) {
                return checkTradeDataExists(OperationOptOrder.initOptOrderData((OperationDataIdOptTrade) objectParam, listObjectTableData), stringBuilder);

            } else if (ConstantUtil.OPT_MATCH.equals(dataId)) {
                return checkMatchDataExists(OperationOptMatching.initOptMatchingData((OperationDataIdOptMatch) objectParam, listObjectTableData), stringBuilder);

            } else if (ConstantUtil.OPT_FUND.equals(dataId)) {
                return checkFundDataExists(OperationCuacctLog.initCuacctLogData((OperationDataIdOptFund) objectParam, listObjectTableData), stringBuilder);

            } else if (ConstantUtil.OPT_ASSET.equals(dataId)) {
                return checkAssetDataExists(OperationStkAssetLog.initStkAssetLogData((OperationDataIdOptAsset) objectParam, listObjectTableData), stringBuilder);

            } else if (ConstantUtil.OPT_MARGIN_CORP.equals(dataId)) {
                // 不做去重，由后面计算，落地，后期看性能调整
            } else if (ConstantUtil.STK_TRADE.equals(dataId)) {
                return checkStkTradeDataExists(OperationStkOrder.initStkOrderData((OperationDataIdStkTrade) objectParam, listObjectTableData), stringBuilder);

            } else if (ConstantUtil.STK_MATCH.equals(dataId)) {
                return checkStkMatchDataExists(OperationStkMatching.initStkMatchingData((OperationDataIdStkMatch) objectParam, listObjectTableData), stringBuilder);
            //电子客户信息表，不存缓存直接更新数据库
            }  else if (ZtsConstantUtil.STK_ECCODESIGN.equals(dataId)) {
            return checkEccodesignDataExists((OperationDataIdEccodesign) objectParam, listObjectTableData, stringBuilder);
             //客户资产表，不存缓存直接更新数据库
            }  else if (ZtsConstantUtil.STK_FUNDASSET.equals(dataId)) {
                return checkFundAssetDataExists((OperationDataIdFundAsset) objectParam, listObjectTableData, stringBuilder);
        }else {
                return true;
            }

            return false;

        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean checkTradeDataExists(DataOptOrder condDataOptOrder, StringBuilder stringBuilder) throws Exception {
        // 查询缓存，该条数据是否已存在
        String keySearch = ConstantUtil.TABLE_OPT_ORDER + "_";
        keySearch += condDataOptOrder.getOrderId();
        keySearch += condDataOptOrder.getStkpbu();
        keySearch += condDataOptOrder.getTrdDate();
        keySearch += condDataOptOrder.getIsWithdraw();

        stringBuilder.append(keySearch);

        // 数据已存在缓存中，返回做去重处理
        Object obj = InitCache.read(keySearch);
        if (obj != null) {
            return true;
        }

        // 数据不存在缓存中，查询DB
        DaoOptOrder daoOptOrder = new DaoOptOrder();
        try {
            DataOptOrder resultDataOptOrder = daoOptOrder.selectUnique(condDataOptOrder);

            if (resultDataOptOrder != null) {
                // DB中存在，回写缓存，返回做去重处理
                InitCache.write(keySearch, ConstantUtil.CACHE_VALUES);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception("TABLE_OPT_ORDER 去重操作异常", e);
        }
    }

    public static boolean checkMatchDataExists(DataOptMatching condDataOptMatching, StringBuilder stringBuilder) throws Exception {
        // 查询缓存，该条数据是否已存在
        String keySearch = ConstantUtil.TABLE_OPT_MATCHING + "_";
        keySearch += condDataOptMatching.getOrderId();
        keySearch += condDataOptMatching.getMatchedSn();
        keySearch += condDataOptMatching.getStkpbu();
        keySearch += condDataOptMatching.getStkbd();
        keySearch += condDataOptMatching.getTrdDate();

        stringBuilder.append(keySearch);

        // 数据已存在缓存中，返回做去重处理
        Object obj = InitCache.read(keySearch);
        if (obj != null) {
            return true;
        }

        // 数据不存在缓存中，查询DB
        DaoOptMatching daoOptMatching = new DaoOptMatching();
        try {
            DataOptMatching resultDataOptMatching = daoOptMatching.selectUnique(condDataOptMatching);

            if (resultDataOptMatching != null) {
                // DB中存在，回写缓存，返回做去重处理
                InitCache.write(keySearch, ConstantUtil.CACHE_VALUES);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception("TABLE_OPT_MATCHING 去重操作异常", e);
        }
    }

    public static boolean checkFundDataExists(DataCuacctLog condDataCuacctLog, StringBuilder stringBuilder) throws Exception {
        // 查询缓存，该条数据是否已存在
        String keySearch = ConstantUtil.TABLE_CUACCT_LOG + "_";
        keySearch += condDataCuacctLog.getSerialNo();

        stringBuilder.append(keySearch);

        // 数据已存在缓存中，返回做去重处理
        Object obj = InitCache.read(keySearch);
        if (obj != null) {
            return true;
        }

        // 数据不存在缓存中，查询DB
        DaoCuacctLog daoCuacctLog = new DaoCuacctLog();
        try {
            DataCuacctLog resultDataCuacctLog = daoCuacctLog.selectUnique(condDataCuacctLog);

            if (resultDataCuacctLog != null) {
                // DB中存在，回写缓存，返回做去重处理
                InitCache.write(keySearch, ConstantUtil.CACHE_VALUES);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception("TABLE_CUACCT_LOG 去重操作异常", e);
        }
    }

    public static boolean checkAssetDataExists(DataStkAssetLog condDataStkAssetLog, StringBuilder stringBuilder) throws Exception {
        // 查询缓存，该条数据是否已存在
        String keySearch = ConstantUtil.TABLE_STK_ASSET_LOG + "_";
        keySearch += condDataStkAssetLog.getSerialNo();
        keySearch += condDataStkAssetLog.getCustCode();

        stringBuilder.append(keySearch);

        // 数据已存在缓存中，返回做去重处理
        Object obj = InitCache.read(keySearch);
        if (obj != null) {
            return true;
        }

        // 数据不存在缓存中，查询DB
        DaoStkAssetLog daoStkAssetLog = new DaoStkAssetLog();
        try {
            DataStkAssetLog resultDataStkAssetLog = daoStkAssetLog.selectUnique(condDataStkAssetLog);

            if (resultDataStkAssetLog != null) {
                // DB中存在，回写缓存，返回做去重处理
                InitCache.write(keySearch, ConstantUtil.CACHE_VALUES);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception("TABLE_STK_ASSET_LOG 去重操作异常", e);
        }
    }

    public static boolean checkStkTradeDataExists(DataStkOrder condDataStkOrder, StringBuilder stringBuilder) throws Exception {
        // 查询缓存，该条数据是否已存在
        String keySearch = ConstantUtil.TABLE_STK_ORDER + "_";
        keySearch += condDataStkOrder.getOrderId();
        keySearch += condDataStkOrder.getStkpbu();
        keySearch += condDataStkOrder.getTrdDate();
        keySearch += condDataStkOrder.getIsWithdraw();

        stringBuilder.append(keySearch);

        // 数据已存在缓存中，返回做去重处理
        Object obj = InitCache.read(keySearch);
        if (obj != null) {
            return true;
        }

        // 数据不存在缓存中，查询DB
        DaoStkOrder daoStkOrder = new DaoStkOrder();
        try {
            DataStkOrder resultDataStkOrder = daoStkOrder.selectUnique(condDataStkOrder);

            if (resultDataStkOrder != null) {
                // DB中存在，回写缓存，返回做去重处理
                InitCache.write(keySearch, ConstantUtil.CACHE_VALUES);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception("TABLE_STK_ORDER 去重操作异常", e);
        }
    }

    public static boolean checkStkMatchDataExists(DataStkMatching condDataStkMatching, StringBuilder stringBuilder) throws Exception {
        // 查询缓存，该条数据是否已存在
        String keySearch = ConstantUtil.TABLE_STK_MATCHING + "_";
        keySearch += condDataStkMatching.getOrderId();
        keySearch += condDataStkMatching.getMatchedSn();
        keySearch += condDataStkMatching.getStkpbu();
        keySearch += condDataStkMatching.getStkbd();
        keySearch += condDataStkMatching.getTrdDate();

        stringBuilder.append(keySearch);

        // 数据已存在缓存中，返回做去重处理
        Object obj = InitCache.read(keySearch);
        if (obj != null) {
            return true;
        }

        // 数据不存在缓存中，查询DB
        DaoStkMatching daoStkMatching = new DaoStkMatching();
        try {
            DataStkMatching resultDataStkMatching = daoStkMatching.selectUnique(condDataStkMatching);

            if (resultDataStkMatching != null) {
                // DB中存在，回写缓存，返回做去重处理
                InitCache.write(keySearch, ConstantUtil.CACHE_VALUES);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new Exception("TABLE_STK_MATCHING 去重操作异常", e);
        }
    }
    public static boolean checkEccodesignDataExists(OperationDataIdEccodesign condDataEccodesign,List<Object> listObjectTableData, StringBuilder stringBuilder) throws Exception {
        //回写Eccodesign数据

        DataEccodesign dataEccodesign = new DataEccodesign();
        dataEccodesign.setServerId(Integer.parseInt(condDataEccodesign.getServerId()));
        dataEccodesign.setOrgId(condDataEccodesign.getOrgId());
        dataEccodesign.setFundCode(condDataEccodesign.getOfCode());
        dataEccodesign.setFundId(condDataEccodesign.getFundId());

        DaoEccodesign daoEccodesign = new DaoEccodesign();
        try {
            DataEccodesign resultDataEccodesign = daoEccodesign.selectUnique(dataEccodesign);

            if (resultDataEccodesign != null) {
             //回写数据
                resultDataEccodesign.setResfundExpdate(condDataEccodesign.getResfundExpdate());
                resultDataEccodesign.setMultisettExp(condDataEccodesign.getMultisettExp());
                resultDataEccodesign.setReservFund(condDataEccodesign.getReservFund());
                resultDataEccodesign.setMultisettStatus(condDataEccodesign.getMultisettStatus());

                SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
                resultDataEccodesign.setUpdateDate(Integer.parseInt(f.format(new Date())));
                daoEccodesign.update(resultDataEccodesign);
                listObjectTableData.add(resultDataEccodesign);
            }
            return false;
        } catch (Exception e) {
            throw new Exception("TABLE_ECCODESIGN 更新操作异常", e);
        }
    }
    public static boolean checkFundAssetDataExists(OperationDataIdFundAsset condDataFundAsset,List<Object> listObjectTableData, StringBuilder stringBuilder) throws Exception {
        //回写FundAsset数据

        DataFundAsset dataFundAsset = new DataFundAsset();
        dataFundAsset.setFundId(condDataFundAsset.getFundId());
        dataFundAsset.setCustId(condDataFundAsset.getCustId());
        dataFundAsset.setMoneyType(condDataFundAsset.getMoneyType());
        DaoFundAsset daoFundAsset = new DaoFundAsset();
        try {
            DataFundAsset resultDataFundAsset = daoFundAsset.selectUnique(dataFundAsset);

            if (resultDataFundAsset != null) {
                //回写数据
                resultDataFundAsset.setFundAvl(condDataFundAsset.getFundAvl());

                daoFundAsset.update(resultDataFundAsset);
                listObjectTableData.add(resultDataFundAsset);
            }
            return false;
        } catch (Exception e) {
            throw new Exception("TABLE_FundAsset 更新操作异常", e);
        }
    }
}
