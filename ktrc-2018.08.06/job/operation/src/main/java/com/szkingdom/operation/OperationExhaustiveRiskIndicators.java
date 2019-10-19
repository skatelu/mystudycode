/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationExhaustiveRiskIndicators
 * Author:   ZhangChangHong
 * Date:     2018/7/30
 * Description: 指标穷举操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.dao.DaoRiskIndicators;
import com.szkingdom.data.DataRiskIndicators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationExhaustiveRiskIndicators extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationExhaustiveRiskIndicators.class);

    public static void gatherRiskIndicators(String factorSources, List<DataRiskIndicators> listRiskIndicators, Object objectParam) throws Exception {

        String keySearch = new String();

        try {
            if (ConstantUtil.SPOT_QUOTATION.equals(factorSources)) {
                keySearch = ConstantUtil.FACTOR_SOURCES_SPOT_QUOTATION;
                getRiskIndicatorsWithCache(keySearch, listRiskIndicators);

            } else if (ConstantUtil.OPT_QUOTATION.equals(factorSources)) {
                keySearch = ConstantUtil.FACTOR_SOURCES_OPT_QUOTATION;
                getRiskIndicatorsWithCache(keySearch, listRiskIndicators);

            } else if (ConstantUtil.OPT_TRADE.equals(factorSources)) {
                keySearch = ((OperationDataIdOptTrade) objectParam).getDataId();
                keySearch +=  String.valueOf(((OperationDataIdOptTrade) objectParam).getStkBiz());
                keySearch += ((OperationDataIdOptTrade) objectParam).getStkBizAction();
                getRiskIndicatorsWithCache(keySearch, listRiskIndicators);

            } else if (ConstantUtil.OPT_MATCH.equals(factorSources)) {
                keySearch = ((OperationDataIdOptMatch) objectParam).getDataId();
                keySearch +=  String.valueOf(((OperationDataIdOptMatch) objectParam).getStkBiz());
                keySearch += ((OperationDataIdOptMatch) objectParam).getStkBizAction();
                getRiskIndicatorsWithCache(keySearch, listRiskIndicators);

            } else if (ConstantUtil.OPT_FUND.equals(factorSources)) {
                keySearch = ((OperationDataIdOptFund) objectParam).getDataId();
                keySearch += ConstantUtil.FACTOR_SOURCES_OPT_FUND_BIZ + ConstantUtil.FACTOR_SOURCES_OPT_FUND_BIZ_ACTION;
                getRiskIndicatorsWithCache(keySearch, listRiskIndicators);

            } else if (ConstantUtil.OPT_ASSET.equals(factorSources)) {
                keySearch = ((OperationDataIdOptAsset) objectParam).getDataId();
                keySearch += ConstantUtil.FACTOR_SOURCES_OPT_ASSET_BIZ + ConstantUtil.FACTOR_SOURCES_OPT_ASSET_BIZ_ACTION;
                getRiskIndicatorsWithCache(keySearch, listRiskIndicators);

            } else if (ConstantUtil.OPT_MARGIN_CORP.equals(factorSources)) {
                keySearch = ((OperationDataIdOptMarginCorp) objectParam).getDataId();
                keySearch += ConstantUtil.FACTOR_SOURCES_OPT_MARGIN_CORP_BIZ + ConstantUtil.FACTOR_SOURCES_OPT_MARGIN_CORP_BIZ_ACTION;
                getRiskIndicatorsWithCache(keySearch, listRiskIndicators);

            } else if (ConstantUtil.STK_TRADE.equals(factorSources)) {
                keySearch = ((OperationDataIdStkTrade) objectParam).getDataId();
                keySearch += String.valueOf(((OperationDataIdStkTrade) objectParam).getStkBiz());
                keySearch += ((OperationDataIdStkTrade) objectParam).getStkBizAction();
                getRiskIndicatorsWithCache(keySearch, listRiskIndicators);

            } else if (ConstantUtil.STK_MATCH.equals(factorSources)) {
                keySearch = ((OperationDataIdStkMatch) objectParam).getDataId();
                keySearch += String.valueOf(((OperationDataIdStkMatch) objectParam).getStkBiz());
                keySearch += ((OperationDataIdStkMatch) objectParam).getStkBizAction();
                getRiskIndicatorsWithCache(keySearch, listRiskIndicators);

            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static void getRiskIndicatorsWithCache(String keySearch, List<DataRiskIndicators> listRiskIndicators) throws Exception {
        // 获取相关指标，记录到list
        try {
            // 缓存中有数据，取缓存数据
            List<DataRiskIndicators> resultListCacheRiskIndicators = (List<DataRiskIndicators>) InitCache.read(keySearch);
            if (resultListCacheRiskIndicators != null && !resultListCacheRiskIndicators.isEmpty()) {
                listRiskIndicators.addAll(resultListCacheRiskIndicators);
                return;

            } else {
                // 缓存中没有数据，查db，再记入缓存
                DaoRiskIndicators daoRiskIndicators = new DaoRiskIndicators();
                List<DataRiskIndicators> resListRiskIndicators = daoRiskIndicators.selectList(keySearch);
                if (resListRiskIndicators != null && !resListRiskIndicators.isEmpty()) {
                    InitCache.write(keySearch, resListRiskIndicators);
                    listRiskIndicators.addAll(resListRiskIndicators);
                    return;
                } else {
                    logger.error("getRiskIndicatorsWithCache() 指标收集查无数据 keySearch：{}", keySearch);
                }
            }
        } catch (Exception e) {
            throw new Exception("指标收集操作异常", e);
        }
    }
}
