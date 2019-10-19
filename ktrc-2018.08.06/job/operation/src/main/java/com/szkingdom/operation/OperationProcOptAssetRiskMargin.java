/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationProcOptAssetRiskMargin
 * Author:   ZhangChangHong
 * Date:     2018/8/10
 * Description: 期权保证金计算
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.common.Tools;
import com.szkingdom.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class OperationProcOptAssetRiskMargin extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationProcOptAssetRiskMargin.class);

    public static void procOptAssetDataForMaginRisk(List<DataOptAsset> listDataOptAsset,
                                                    DataRiskInfoAsset dataRiskInfoAsset,
                                                    List<DataRiskInfoAsset> listDataRiskInfoAsset,
                                                    int CurrentDate,
                                                    int CurrentTime,
                                                    boolean entryOrders) throws Exception {
        try {
            if (listDataOptAsset != null) {
                for (DataOptAsset dataOptAsset : listDataOptAsset) {
                    if (dataOptAsset.getCurrency().equals(dataRiskInfoAsset.getCurrency())
                            && dataOptAsset.getStkbd().equals(dataRiskInfoAsset.getStkbd())
                            && dataOptAsset.getTrdacct().equals(dataRiskInfoAsset.getTrdacct())
                            && dataOptAsset.getOptNum().equals(dataRiskInfoAsset.getOptNum())) {
                        // 只累计义务仓, 用非挂单权利仓与义务仓进行持仓对冲
                        if (!dataOptAsset.getOptSide().equals(ConstantUtil.OPT_LONG)) {
                            if (entryOrders) {
                                dataRiskInfoAsset.setShortRltPosi(dataRiskInfoAsset.getShortRltPosi() + dataOptAsset.getOptPosiRlt() - dataOptAsset.getCombedQty());
                            } else {
                                dataRiskInfoAsset.setShortRltPosi(dataRiskInfoAsset.getShortRltPosi() + dataOptAsset.getOptBln() + dataOptAsset.getOptTrdBln() - dataOptAsset.getCombedQty());
                            }
                            dataRiskInfoAsset.setShortActPosi(dataRiskInfoAsset.getShortActPosi() + dataOptAsset.getOptBln() + dataOptAsset.getOptTrdBln());
                            dataRiskInfoAsset.setOptHedgedQty(dataRiskInfoAsset.getOptHedgedQty() + dataOptAsset.getOptPosiRlt() - dataOptAsset.getCombedQty());

                        } else {
                            dataRiskInfoAsset.setOptHedgedQty(dataRiskInfoAsset.getOptHedgedQty() - (dataOptAsset.getOptBln() + dataOptAsset.getOptTrdBln() - dataOptAsset.getCombedQty()));
                        }
                    } else {
                        if (dataRiskInfoAsset.getCustCode() > 0L) {
                            listDataRiskInfoAsset.add(dataRiskInfoAsset);
                        }

                        dataRiskInfoAsset.init();
                        dataRiskInfoAsset.setOccDate(CurrentDate);
                        dataRiskInfoAsset.setOccTime(CurrentTime);
                        dataRiskInfoAsset.setIntOrg(dataOptAsset.getIntOrg());
                        dataRiskInfoAsset.setCustCode(dataOptAsset.getCustCode());
                        dataRiskInfoAsset.setCuacctCode(dataOptAsset.getCuacctCode());
                        dataRiskInfoAsset.setCurrency(dataOptAsset.getCurrency());
                        dataRiskInfoAsset.setCustType(dataOptAsset.getCustType());
                        dataRiskInfoAsset.setStkbd(dataOptAsset.getStkbd());
                        dataRiskInfoAsset.setTrdacct(dataOptAsset.getTrdacct());
                        dataRiskInfoAsset.setOptNum(dataOptAsset.getOptNum());
                        if (!dataOptAsset.getOptSide().equals(ConstantUtil.OPT_LONG)) {
                            dataRiskInfoAsset.setShortRltPosi(dataOptAsset.getOptPosiRlt() - dataOptAsset.getCombedQty());
                            dataRiskInfoAsset.setShortActPosi(dataOptAsset.getOptBln() + dataOptAsset.getOptTrdBln());
                            dataRiskInfoAsset.setOptHedgedQty(dataOptAsset.getOptPosiRlt() - dataOptAsset.getCombedQty());
                        } else {
                            dataRiskInfoAsset.setOptHedgedQty(-(dataOptAsset.getOptBln() + dataOptAsset.getOptTrdBln() - dataOptAsset.getCombedQty()));
                        }
                    }
                }
            }

            if (dataRiskInfoAsset.getCustCode() > 0L) {
                listDataRiskInfoAsset.add(dataRiskInfoAsset);
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public static DataRiskInfoAsset calcOptMarginPrem(List<DataRiskInfoAsset> listDataRiskInfoAsset,
                                                                             DataOptInfo condDataOptInfo,
                                                                             DataOptMktinfo condDataOptMktinfo,
                                                                             DataStkMktinfo condDataStkMktinfo,
                                                                             long optQty,
                                                                             int CurrentDate) throws Exception {
        try {
            double execPrice = 0.00;
            double optPrice = 0.00;
            //long undlPrice = 0L;
            double optTrdPrice = 0.00;
            String UndlStkbd = "";
            double undlPriceRlt = 0.00;
            Character strategyTemplate = null;

            // 当前交易日期
            int trdDate = OperationStkCalendar.getNextTrdDate("15", CurrentDate, 0);
            DataRiskInfoAsset dataRiskInfoAssetTemp = new DataRiskInfoAsset();

            for (DataRiskInfoAsset dataRiskInfoAsset : listDataRiskInfoAsset) {
                double secuMargin = 0.00;
                double secuMarginRlt = 0.00;
                double stkexMargin = 0.00;
                double stkexMarginRlt = 0.00;
                double optPremiumRlt = 0.00;

                if (dataRiskInfoAsset.getOptHedgedQty() > 0L || dataRiskInfoAsset.getShortRltPosi() > 0L){
                    // 取合约前结算价和标的前收盘价
                    DataOptInfo resultDataOptInfo = OperationOptInfo.getOptInfoDataWithCache(condDataOptInfo);
                    if (resultDataOptInfo != null) {
                        execPrice = NumConvertUtil.longToMoney(resultDataOptInfo.getExercisePrice());
                        optPrice = NumConvertUtil.longToMoney(resultDataOptInfo.getPreSettPrice());
                        //undlPrice = resultDataOptInfo.getUndlClsPrice();
                    }

                    // 获取期权合约最新价格
                    DataOptMktinfo resultDataOptMktinfo = OperationOptMktinfo.getOptMktinfoDataByUnique(condDataOptMktinfo);
                    if (resultDataOptMktinfo != null) {
                        optTrdPrice = NumConvertUtil.longToMoney(resultDataOptMktinfo.getOptTrdPrice());
                    }

                    UndlStkbd = dataRiskInfoAsset.getStkbd().charAt(0) + "0";
                    condDataStkMktinfo.setStkbd(UndlStkbd);

                    //  获取现货行情价格
                    DataStkMktinfo resultDataStkMktinfo = OperationStkMktInfo.getStkMktinfoDataByUnique(condDataStkMktinfo);
                    if (resultDataStkMktinfo != null) {
                        undlPriceRlt = NumConvertUtil.longToMoney(resultDataStkMktinfo.getCurrentPrice());
                        undlPriceRlt = undlPriceRlt > 0 ? undlPriceRlt : NumConvertUtil.longToMoney(resultDataStkMktinfo.getClosingPrice());
                    }

                    // 准备保证金策略模板查询参数
                    DataMarginTemplate dataMarginTemplate = new DataMarginTemplate();
                    dataMarginTemplate.setStkbd(dataRiskInfoAsset.getStkbd());
                    dataMarginTemplate.setOptUndlCls(resultDataOptInfo.getOptUndlCls());
                    dataMarginTemplate.setOptUndlCode(resultDataOptInfo.getOptUndlCode());
                    dataMarginTemplate.setOptType(resultDataOptInfo.getOptType());
                    dataMarginTemplate.setOptNum(dataRiskInfoAsset.getOptNum());
                    dataMarginTemplate.setCombStraCode("@");

                    // 取客户保证金设置
                    strategyTemplate = getCustMarginCfg(dataRiskInfoAsset.getCuacctCode(), dataRiskInfoAsset.getCustCode());

                    dataMarginTemplate.setStrategyTemplate(strategyTemplate);

                    DataStrategyScopeResult resultDataStrategyScopeResult = null;
                    DataStrategyScopeResult dataStrategyScopeResult = new DataStrategyScopeResult();
                    dataStrategyScopeResult.setTrdDate(trdDate);
                    dataStrategyScopeResult.setStkbd(dataRiskInfoAsset.getStkbd());
                    dataStrategyScopeResult.setAutoSplitDay('@');

                    DataMarginRateParam[] dataMarginRateParam = new DataMarginRateParam[4];
                    for (int i = 0; i < ConstantUtil.MarginStrategy.length; ++i) {
                        dataMarginRateParam[i] = new DataMarginRateParam();
                        dataMarginRateParam[i].setStrategyTemplate(strategyTemplate);
                        dataMarginRateParam[i].setMarginStrategy(ConstantUtil.MarginStrategy[i]);
                        // 策略取值结果
                        if (ConstantUtil.MARGIN_STRATEGY_HOLLIDAY.equals(ConstantUtil.MarginStrategy[i])) {
                            dataStrategyScopeResult.setOptNum("@");
                            dataStrategyScopeResult.setMarginStrategy(ConstantUtil.MARGIN_STRATEGY_HOLLIDAY);
                            resultDataStrategyScopeResult = OperationStrategyScopeResult.getStrategyScopeResultDataByUnique(dataStrategyScopeResult);

                        } else if (ConstantUtil.MARGIN_STRATEGY_NONE.equals(ConstantUtil.MarginStrategy[i])) {
                            dataStrategyScopeResult.setOptNum(dataRiskInfoAsset.getOptNum());
                            dataStrategyScopeResult.setMarginStrategy(ConstantUtil.MarginStrategy[i]);
                            resultDataStrategyScopeResult = OperationStrategyScopeResult.getStrategyScopeResultDataByUnique(dataStrategyScopeResult);
                        }

                        if (resultDataStrategyScopeResult != null) {
                            dataMarginTemplate.setMarginStrategy(ConstantUtil.MarginStrategy[i]);
                            if (ConstantUtil.MarginStrategy[i].equals(ConstantUtil.MARGIN_STRATEGY_NONE)) {
                                dataMarginTemplate.setScopeNo(0);
                            } else {
                                dataMarginTemplate.setScopeNo(resultDataStrategyScopeResult.getScopeNo());
                            }

                            // 取保证金参数
                            getMarginParam(resultDataOptInfo, dataMarginTemplate, dataMarginRateParam[i]);
                        }

                        // 非默认模板未取到正常情况策略参数，则取默认模板正常情况策略参数
                        if (ConstantUtil.MARGIN_STRATEGY_NONE.equals(dataMarginRateParam[i].getMarginStrategy())
                                && dataMarginRateParam[i].getMarginMode() == null
                                && dataMarginRateParam[i].getStrategyTemplate() != '0') {
                            dataMarginRateParam[i].setStrategyTemplate('0');
                            dataMarginTemplate.setStrategyTemplate('0');
                            dataMarginTemplate.setMarginStrategy(ConstantUtil.MARGIN_STRATEGY_NONE);
                            dataMarginTemplate.setScopeNo(0);

                            // 取保证金参数
                            getMarginParam(resultDataOptInfo, dataMarginTemplate, dataMarginRateParam[i]);
                        }

                        if (dataMarginRateParam[i].getMarginMode() != null) {
                            break;
                        }
                    }

                    // 二次循环
                    double dMoneyness = 0.00;
                    DataStrategyScope dataStrategyScope = new DataStrategyScope();

                    for (int i = 0; i < ConstantUtil.MarginStrategy.length; ++i) {
                        if (dataMarginRateParam[i] != null &&
                                ConstantUtil.MARGIN_STRATEGY_MONEYNESS.equals(dataMarginRateParam[i].getMarginStrategy())) {
                            if (undlPriceRlt <= 0) {
                                continue;
                            }

                            if (ConstantUtil.OPT_CALL.equals(resultDataOptInfo.getOptType())) {
                                dMoneyness = 1 - execPrice / undlPriceRlt;
                            } else {
                                dMoneyness = execPrice / undlPriceRlt - 1;
                            }

                            int iScopeNo = Integer.MAX_VALUE;

                            // 取临近行权日策略取值结果
                            dataStrategyScopeResult.setOptNum(dataRiskInfoAsset.getOptNum());
                            dataStrategyScopeResult.setMarginStrategy(ConstantUtil.MARGIN_STRATEGY_EXERCISE);
                            resultDataStrategyScopeResult = OperationStrategyScopeResult.getStrategyScopeResultDataByUnique(dataStrategyScopeResult);
                            if (resultDataStrategyScopeResult != null) {
                                dataStrategyScope.setEdayScopeNo(resultDataStrategyScopeResult.getScopeNo());
                            } else {
                                dataStrategyScope.setEdayScopeNo(0);
                            }

                            dataStrategyScope.setMarginStrategy(ConstantUtil.MARGIN_STRATEGY_MONEYNESS);
                            List<DataStrategyScope> resultListDataStrategyScope = OperationStrategyScope.listStrategyScopeDataByParam(dataStrategyScope);
                            if (resultListDataStrategyScope != null) {
                                // 判断相对虚实值程度落在哪个范围之中
                                for (DataStrategyScope resDataStrategyScope : resultListDataStrategyScope) {
                                    long scopeMax = Math.max(resDataStrategyScope.getScopeBgn(), resDataStrategyScope.getScopeEnd());
                                    long scopeMin = Math.min(resDataStrategyScope.getScopeBgn(), resDataStrategyScope.getScopeEnd());

                                    char maxInclude = scopeMax == resDataStrategyScope.getScopeBgn() ? resDataStrategyScope.getBgnInclude() : resDataStrategyScope.getEndInclude();
                                    char minInclude = scopeMin == resDataStrategyScope.getScopeBgn() ? resDataStrategyScope.getBgnInclude() : resDataStrategyScope.getEndInclude();
                                    if ((maxInclude == '1' && NumConvertUtil.rateToLong(dMoneyness) <= scopeMax
                                            || maxInclude == '0' && NumConvertUtil.rateToLong(dMoneyness) < scopeMax)
                                            &&(minInclude == '1' && NumConvertUtil.rateToLong(dMoneyness) >= scopeMin
                                            || minInclude == '0' && NumConvertUtil.rateToLong(dMoneyness) > scopeMin))
                                    {
                                        iScopeNo = resDataStrategyScope.getScopeNo();
                                        break;
                                    }
                                }

                                resultListDataStrategyScope.clear();
                            }

                            // 取保证金策略模板
                            dataMarginTemplate.setStrategyTemplate(dataMarginRateParam[i].getStrategyTemplate());
                            dataMarginTemplate.setMarginStrategy(ConstantUtil.MARGIN_STRATEGY_MONEYNESS);
                            dataMarginTemplate.setScopeNo(iScopeNo);

                            List<DataMarginTemplate> resListDataMarginTemplate = OperationMarginTemplate.listMarginTemplateDataForMaginRisk(dataMarginTemplate);
                            if (resListDataMarginTemplate != null && !resListDataMarginTemplate.isEmpty()) {
                                dataMarginRateParam[i].setParamNoRlt(resListDataMarginTemplate.get(0).getParamNo());
                                resListDataMarginTemplate.clear();
                            }

                            // 取保证金比例参数
                            DataMarginRateParam condDataMarginRateParam = new DataMarginRateParam();
                            condDataMarginRateParam.setParamNoRlt(dataMarginRateParam[i].getParamNoRlt());
                            DataMarginRateParam resultDataMarginRateParam = OperationMarginRateParam.getMarginRateParamDataByUnique(condDataMarginRateParam);
                            if (resultDataMarginRateParam != null) {
                                dataMarginRateParam[i].setMarginModeRlt(resultDataMarginRateParam.getMarginMode());
                                dataMarginRateParam[i].setFloatRateRlt(resultDataMarginRateParam.getFloatRate());

                                if (dataMarginRateParam[i].getMarginModeRlt() == '0')
                                {
                                    dataMarginRateParam[i].setMarginRatio1Rlt(resultDataOptInfo.getMarginRatio1());
                                    dataMarginRateParam[i].setMarginRatio2Rlt(resultDataOptInfo.getMarginRatio2());
                                }
                                else
                                {
                                    dataMarginRateParam[i].setMarginRatio1Rlt(resultDataOptInfo.getMarginRatio1() + resultDataMarginRateParam.getMarginRatio1());
                                    dataMarginRateParam[i].setMarginRatio2Rlt(resultDataOptInfo.getMarginRatio2() + resultDataMarginRateParam.getMarginRatio2());
                                }
                            }
                        }
                    }

                    // 取合约结算价和标的前收盘价计算静态保证金，按当前价计算实时保证金
                    double floatRate = 0.00;         // 保证金浮动比例
                    double margin = NumConvertUtil.round(NumConvertUtil.longToMoney(resultDataOptInfo.getMarginUnit()), (short) 2);
                    stkexMargin = margin * optQty;

                    double optPriceRlt = optTrdPrice;
                    optPremiumRlt = optPriceRlt * resultDataOptInfo.getOptUnit() * optQty;
                    stkexMarginRlt = calcOptMargin(resultDataOptInfo.getOptType(),
                                                    optQty,
                                                    resultDataOptInfo.getOptUnit(),
                                                    optPriceRlt,
                                                    execPrice,
                                                    undlPriceRlt,
                                                    resultDataOptInfo.getMarginRatio1(),
                                                    resultDataOptInfo.getMarginRatio2());

                    for (int i = 0; i < ConstantUtil.MarginStrategy.length; ++i) {
                        if (dataMarginRateParam[i] != null) {
                            if (secuMargin == 0L && dataMarginRateParam[i].getMarginMode() != null) {
                                double secuMarginTmp = calcOptMargin(resultDataOptInfo.getOptType(),
                                        1,
                                        resultDataOptInfo.getOptUnit(),
                                        optPriceRlt,
                                        execPrice,
                                        undlPriceRlt,
                                        dataMarginRateParam[i].getMarginRatio1(),
                                        dataMarginRateParam[i].getMarginRatio2());

                                dataMarginRateParam[i].setSecuMargin(NumConvertUtil.moneyToLong(secuMarginTmp));
                            }

                            if (secuMarginRlt == 0L && dataMarginRateParam[i].getMarginModeRlt() != null) {
                                double secuMarginRltTmp = calcOptMargin(resultDataOptInfo.getOptType(),
                                        1,
                                        resultDataOptInfo.getOptUnit(),
                                        optPriceRlt,
                                        execPrice,
                                        undlPriceRlt,
                                        dataMarginRateParam[i].getMarginRatio1Rlt(),
                                        dataMarginRateParam[i].getMarginRatio2Rlt());

                                dataMarginRateParam[i].setSecuMarginRlt(NumConvertUtil.moneyToLong(secuMarginRltTmp));
                            }

                            // 再上浮
                            if (dataMarginRateParam[i].getMarginMode() != null) {
                                if (dataMarginRateParam[i].getMarginMode() == '0' || dataMarginRateParam[i].getMarginMode() == '2')
                                {
                                    floatRate = NumConvertUtil.longToRate(dataMarginRateParam[i].getFloatRate());
                                    floatRate++;
                                    margin = NumConvertUtil.round(NumConvertUtil.longToMoney(dataMarginRateParam[i].getSecuMargin()) * floatRate, (short) 2);
                                    dataMarginRateParam[i].setSecuMargin(NumConvertUtil.rateToLong(margin));
                                }
                            }

                            if (dataMarginRateParam[i].getMarginModeRlt() != null) {
                                if (dataMarginRateParam[i].getMarginModeRlt() == '0' || dataMarginRateParam[i].getMarginModeRlt() == '2')
                                {
                                    floatRate = NumConvertUtil.longToRate(dataMarginRateParam[i].getFloatRateRlt());
                                    floatRate++;
                                    margin = NumConvertUtil.round(NumConvertUtil.longToMoney(dataMarginRateParam[i].getSecuMarginRlt()) * floatRate, (short)2);
                                    dataMarginRateParam[i].setSecuMarginRlt(NumConvertUtil.rateToLong(margin));
                                }
                            }

                            // 先计算单张保证金（四舍五入，保留2位小数）再乘合约张数
                            dataMarginRateParam[i].setSecuMargin(dataMarginRateParam[i].getSecuMargin() * optQty);
                            dataMarginRateParam[i].setSecuMarginRlt(dataMarginRateParam[i].getSecuMarginRlt() * optQty);

                            secuMargin = secuMargin == 0L ? dataMarginRateParam[i].getSecuMargin() : secuMargin;
                            secuMarginRlt = secuMarginRlt == 0L ? dataMarginRateParam[i].getSecuMarginRlt() : secuMarginRlt;
                        }
                    }

                    secuMargin = Math.max(secuMargin, stkexMargin);
                    secuMarginRlt = Math.max(secuMarginRlt, stkexMarginRlt);
                }

                double tmpMoney = 0.00;
                long tempShortRltPosi = dataRiskInfoAsset.getShortRltPosi() < 0L ? 0L : dataRiskInfoAsset.getShortRltPosi();
                long tempOptHedgedQty = dataRiskInfoAsset.getOptHedgedQty() < 0L ? 0L : dataRiskInfoAsset.getOptHedgedQty();

                tmpMoney = NumConvertUtil.longToMoney(dataRiskInfoAssetTemp.getSecuMargin()) + secuMargin * tempShortRltPosi;
                dataRiskInfoAssetTemp.setSecuMargin(NumConvertUtil.moneyToLong(tmpMoney));

                tmpMoney = NumConvertUtil.longToMoney(dataRiskInfoAssetTemp.getSecuMarginRlt()) + secuMarginRlt * tempShortRltPosi;
                dataRiskInfoAssetTemp.setSecuMarginRlt(NumConvertUtil.moneyToLong(tmpMoney));

                tmpMoney = NumConvertUtil.longToMoney(dataRiskInfoAssetTemp.getStkexMargin()) + stkexMargin * tempShortRltPosi;
                dataRiskInfoAssetTemp.setStkexMargin(NumConvertUtil.moneyToLong(tmpMoney));

                tmpMoney = NumConvertUtil.longToMoney(dataRiskInfoAssetTemp.getStkexMarginRlt()) + stkexMarginRlt * tempShortRltPosi;
                dataRiskInfoAssetTemp.setStkexMarginRlt(NumConvertUtil.moneyToLong(tmpMoney));

                tmpMoney = NumConvertUtil.longToMoney(dataRiskInfoAssetTemp.getHedgedSecuMargin()) + secuMargin * tempOptHedgedQty;
                dataRiskInfoAssetTemp.setHedgedSecuMargin(NumConvertUtil.moneyToLong(tmpMoney));

                tmpMoney = NumConvertUtil.longToMoney(dataRiskInfoAssetTemp.getHedgedSecuMarginRlt()) + secuMarginRlt * tempOptHedgedQty;
                dataRiskInfoAssetTemp.setHedgedSecuMarginRlt(NumConvertUtil.moneyToLong(tmpMoney));

                tmpMoney = NumConvertUtil.longToMoney(dataRiskInfoAssetTemp.getHedgedStkexMargin()) + stkexMargin * tempOptHedgedQty;
                dataRiskInfoAssetTemp.setHedgedStkexMargin(NumConvertUtil.moneyToLong(tmpMoney));

                tmpMoney = NumConvertUtil.longToMoney(dataRiskInfoAssetTemp.getHedgedStkexMarginRlt()) + stkexMarginRlt * tempOptHedgedQty;
                dataRiskInfoAssetTemp.setHedgedStkexMarginRlt(NumConvertUtil.moneyToLong(tmpMoney));

                tmpMoney = NumConvertUtil.longToMoney(dataRiskInfoAssetTemp.getShortPremiumRlt()) + optPremiumRlt * (dataRiskInfoAsset.getShortActPosi() < 0L ? 0L : dataRiskInfoAsset.getShortActPosi());
                dataRiskInfoAssetTemp.setShortPremiumRlt(NumConvertUtil.moneyToLong(tmpMoney));
            }

            return dataRiskInfoAssetTemp;
        } catch (Exception e) {
            throw e;
        }
    }

    public static Character getCustMarginCfg(long cuacctCode, long custCode) throws Exception {
        try {
            // 无优化版本
            Character cuacctGrp = '-';
            Character marginLvl = '-';
            Character marginCfgType = '0';
            short intOrg = -1;
            short headOrgVal = -1;

            // 获取总部机构
            DataRegistry dataRegistry = new DataRegistry();
            dataRegistry.setRegkeyId("HEAD_QUARTERS_ORG");
            DataRegistry resultDataRegistry = OperationRegistry.getRegistryDataByUnique(dataRegistry);
            if (resultDataRegistry != null) {
                headOrgVal = Short.valueOf(resultDataRegistry.getRegkeyVal());
            }

            // 获取资产账户组别
            DataCuacct dataCuacct = new DataCuacct();
            dataCuacct.setCuacctCode(cuacctCode);
            DataCuacct resultDataCuacct = OperationCuacct.getCuacctDataByUnique(dataCuacct);
            if (resultDataCuacct != null) {
                cuacctGrp = resultDataCuacct.getCuacctGrp();
                intOrg = resultDataCuacct.getIntOrg();
            }

            // 获取客户保证金级别
            DataCustomer dataCustomer = new DataCustomer();
            dataCustomer.setCustCode(custCode);
            DataCustomer resultDataCustomer = OperationCustomer.getCustomerDataByUnique(dataCustomer);
            if (resultDataCustomer != null) {
                marginLvl = resultDataCustomer.getMarginLvl();
            }

            // 按资产账户匹配模板
            DataOptCustMarginCfg dataOptCustMarginCfg = new DataOptCustMarginCfg();
            dataOptCustMarginCfg.setIntOrg(intOrg);
            dataOptCustMarginCfg.setMarginCfgType(marginCfgType);
            dataOptCustMarginCfg.setMarginCfgValue(String.valueOf(cuacctCode));
            DataOptCustMarginCfg resultDataOptCustMarginCfg = OperationOptCustMarginCfg.getOptCustMarginCfgDataByUnique(dataOptCustMarginCfg);
            if (resultDataOptCustMarginCfg != null) {
                return resultDataOptCustMarginCfg.getStrategyTemplate();
            }

            // 查无数据，按保证金级别匹配模板
            marginCfgType = '1';
            dataOptCustMarginCfg.setIntOrg(headOrgVal);
            dataOptCustMarginCfg.setMarginCfgType(marginCfgType);
            dataOptCustMarginCfg.setMarginCfgValue(String.valueOf(marginLvl));
            resultDataOptCustMarginCfg = OperationOptCustMarginCfg.getOptCustMarginCfgDataByUnique(dataOptCustMarginCfg);
            if (resultDataOptCustMarginCfg != null) {
                return resultDataOptCustMarginCfg.getStrategyTemplate();
            }

            dataOptCustMarginCfg.setMarginCfgValue("@");
            resultDataOptCustMarginCfg = OperationOptCustMarginCfg.getOptCustMarginCfgDataByUnique(dataOptCustMarginCfg);
            if (resultDataOptCustMarginCfg != null) {
                return resultDataOptCustMarginCfg.getStrategyTemplate();
            }

            // 查无数据，按资产账户组别匹配模板
            marginCfgType = '2';
            dataOptCustMarginCfg.setIntOrg(intOrg);
            dataOptCustMarginCfg.setMarginCfgType(marginCfgType);
            dataOptCustMarginCfg.setMarginCfgValue(String.valueOf(cuacctGrp));
            resultDataOptCustMarginCfg = OperationOptCustMarginCfg.getOptCustMarginCfgDataByUnique(dataOptCustMarginCfg);
            if (resultDataOptCustMarginCfg != null) {
                return resultDataOptCustMarginCfg.getStrategyTemplate();
            }

            dataOptCustMarginCfg.setMarginCfgValue("@");
            resultDataOptCustMarginCfg = OperationOptCustMarginCfg.getOptCustMarginCfgDataByUnique(dataOptCustMarginCfg);
            if (resultDataOptCustMarginCfg != null) {
                return resultDataOptCustMarginCfg.getStrategyTemplate();
            }

            dataOptCustMarginCfg.setIntOrg(headOrgVal);
            dataOptCustMarginCfg.setMarginCfgValue(String.valueOf(cuacctGrp));
            resultDataOptCustMarginCfg = OperationOptCustMarginCfg.getOptCustMarginCfgDataByUnique(dataOptCustMarginCfg);
            if (resultDataOptCustMarginCfg != null) {
                return resultDataOptCustMarginCfg.getStrategyTemplate();
            }

            dataOptCustMarginCfg.setMarginCfgValue("@");
            resultDataOptCustMarginCfg = OperationOptCustMarginCfg.getOptCustMarginCfgDataByUnique(dataOptCustMarginCfg);
            if (resultDataOptCustMarginCfg != null) {
                return resultDataOptCustMarginCfg.getStrategyTemplate();
            }

        } catch (Exception e) {
            throw e;
        }

        return null;
    }

    public static void getMarginParam(DataOptInfo condDataOptInfo,
                                      DataMarginTemplate condDataMarginTemplate,
                                      DataMarginRateParam condDataMarginRateParam) throws Exception {
        try {
            List<DataMarginTemplate> resListDataMarginTemplate = OperationMarginTemplate.listMarginTemplateDataForMaginRisk(condDataMarginTemplate);
            if (resListDataMarginTemplate != null && !resListDataMarginTemplate.isEmpty()) {
                condDataMarginRateParam.setParamNo(resListDataMarginTemplate.get(0).getParamNo());
                resListDataMarginTemplate.clear();
            }

            DataMarginRateParam resultDataMarginRateParam = OperationMarginRateParam.getMarginRateParamDataByUnique(condDataMarginRateParam);
            if (resultDataMarginRateParam != null) {
                condDataMarginRateParam.setMarginMode(resultDataMarginRateParam.getMarginMode());
                condDataMarginRateParam.setFloatRate(resultDataMarginRateParam.getFloatRate());

                if ('0' == condDataMarginRateParam.getMarginMode()) {
                    condDataMarginRateParam.setMarginRatio1(condDataOptInfo.getMarginRatio1());
                    condDataMarginRateParam.setMarginRatio2(condDataOptInfo.getMarginRatio2());
                } else {
                    condDataMarginRateParam.setMarginRatio1(condDataOptInfo.getMarginRatio1() + resultDataMarginRateParam.getMarginRatio1());
                    condDataMarginRateParam.setMarginRatio2(condDataOptInfo.getMarginRatio2() + resultDataMarginRateParam.getMarginRatio2());
                }

                if (ConstantUtil.MARGIN_STRATEGY_MONEYNESS.equals(condDataMarginRateParam.getMarginStrategy())) {
                    condDataMarginRateParam.setMarginModeRlt(resultDataMarginRateParam.getMarginMode());
                    condDataMarginRateParam.setFloatRateRlt(resultDataMarginRateParam.getFloatRate());
                    if ('0' == condDataMarginRateParam.getMarginMode()) {
                        condDataMarginRateParam.setMarginRatio1Rlt(condDataOptInfo.getMarginRatio1());
                        condDataMarginRateParam.setMarginRatio2Rlt(condDataOptInfo.getMarginRatio2());
                    } else {
                        condDataMarginRateParam.setMarginRatio1Rlt(condDataOptInfo.getMarginRatio1() + resultDataMarginRateParam.getMarginRatio1());
                        condDataMarginRateParam.setMarginRatio2Rlt(condDataOptInfo.getMarginRatio2() + resultDataMarginRateParam.getMarginRatio2());
                    }
                }

                condDataMarginRateParam.setExtraMargin(resultDataMarginRateParam.getExtraMargin());
            }
        } catch (Exception e) {
            throw e;
        }
    }


    public static double calcOptMargin(Character optType, long optQty,
                                     long optUnit, double optPriceRlt,
                                     double execPrice, double undlPriceRlt,
                                     long MarginRatio1, long MarginRatio2) {
        double margin = 0.00;
        double exercisePrice = 0.00;  // 行权价格
        double undlPrice = 0.00;      // 标的价格
        double marginRatio1 = 0.00;   // 保证金计算比例参数一
        double marginRatio2 = 0.00;   // 保证金计算比例参数二

        double maxVal = 0.00;         // 中间变量 最大值
        double minVal = 0.00;         // 中间变量 最小值
        double price = 0.00;          // 中间变量 虚值

        exercisePrice = execPrice;
        undlPrice = undlPriceRlt;
        marginRatio1 = NumConvertUtil.longToRate(MarginRatio1);
        marginRatio2 = NumConvertUtil.longToRate(MarginRatio2);

        // 每张合约保证金计算如下（交易所交易系统使用）：
        // 认购期权保证金＝{合约价格+Max（25%×标的价格-认购期权虚值，10%×标的价格）}*合约单位;
        // 认沽期权保证金＝Min{合约价格+Max[25%×标的价格-认沽期权虚值，10%×行权价格]，行权价格}*合约单位;
        // 认购期权虚值=max（行权价格-标的价格，0）
        // 认沽期权虚值=max（标的价格-行权价格，0）

        if (ConstantUtil.OPT_CALL.equals(optType))
        {
            // 计算虚值：认购期权虚值=max（行权价格-标的价格，0）
            price = Math.max(exercisePrice - undlPrice,  0);

            // 计算中间值：Max（25%×标的价格-认购期权虚值，10%×标的价格）
            maxVal = Math.max(undlPrice * marginRatio1 - price, undlPrice * marginRatio2);

            // 认购期权保证金＝{合约价格+Max（25%×标的价格-认购期权虚值，10%×标的价格）}*合约单位;
            margin = NumConvertUtil.round((optPriceRlt + maxVal) * optUnit, (short)2);
        }
        else if (ConstantUtil.OPT_PUT.equals(optType))
        {
            // 计算虚值：认沽期权虚值=max（标的价格-行权价格，0）
            price = Math.max(undlPrice - exercisePrice, 0);

            // 计算中间值：Max[25%×标的价格-认沽期权虚值，10%×行权价格]
            maxVal = Math.max(undlPrice * marginRatio1 - price, exercisePrice * marginRatio2);

            // 计算中间值：Min{合约价格+Max[25%×标的价格-认沽期权虚值，10%×行权价格]，行权价格}
            minVal = Math.min(optPriceRlt + maxVal, exercisePrice);

            // 认沽期权保证金＝Min{合约价格+Max[25%×标的价格-认沽期权虚值，10%×行权价格]，行权价格}*合约单位;
            margin = NumConvertUtil.round(minVal * optUnit, (short)2);
        }

        return margin * optQty;
    }
}
