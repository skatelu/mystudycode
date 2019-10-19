/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkRightPlan
 * Author:   ZhengMingjie
 * Date:     2018/8/4
 * Description: 除权除息信息表STK_RIGHT_PLAN操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.dao.DaoStkRightPlan;
import com.szkingdom.data.DataStkRightPlan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationStkRightPlan {
    protected static Logger logger = LoggerFactory.getLogger(OperationStkRightPlan.class);

    /**
     * 获取证券的除权除息日
     * @param condDataStkRightPlan
     * @throws Exception
     */
    public static long getDateOfXrDr(DataStkRightPlan condDataStkRightPlan)  throws Exception{
        DaoStkRightPlan daoStkRightPlan = new DaoStkRightPlan();
        long dateOfXrDr = 0L;
        try {
            DataStkRightPlan resultDataStkRightPlan = daoStkRightPlan.selectUnique(condDataStkRightPlan);
            if (resultDataStkRightPlan != null) {
                dateOfXrDr = resultDataStkRightPlan.getDividendDate();
            } else {
                logger.warn("查询日期结果为空—— TABLE_STK_RIGHT_PLAN");
            }
        } catch (Exception e) {
            throw new Exception("查表STK_RIGHT_PLAN获取除权除息日异常", e);
        }
        return dateOfXrDr;
    }

    public static DataStkRightPlan getStkRightPlanDataWithCache(DataStkRightPlan condDataStkRightPlan) throws Exception {
        // 根据唯一索引组串key值。注：组串时TABLE常量名要追加下划线
        String keySearch = ConstantUtil.TABLE_STK_RIGHT_PLAN + "_";
        keySearch += condDataStkRightPlan.getStkbd();
        keySearch += condDataStkRightPlan.getStkCode();
        try {
            // 根据Key从MAP中获取对应数据结果
            DataStkRightPlan mapDataStkRightPlan = (DataStkRightPlan) InitCache.read(keySearch);

            if (mapDataStkRightPlan != null) {
                return mapDataStkRightPlan;
            }

            // MAP查无数据，查表。查询参数对象与入参一致
            DaoStkRightPlan daoStkRightPlan = new DaoStkRightPlan();

            DataStkRightPlan resultDataStkRightPlan = daoStkRightPlan.selectUnique(condDataStkRightPlan);
            if (resultDataStkRightPlan != null) {
                InitCache.write(keySearch, resultDataStkRightPlan);
                return resultDataStkRightPlan;
            }
        } catch (Exception e) {
            throw new Exception("获取除权除息日异常", e);
        }

        return null;
    }

    /**
     * 获取备兑合约数量调整系数(R)，根据不同市场与不同标的券类别进一步细分计算逻辑
     * @param condDataStkRightPlan,closingPrice,optUndlCls
     * @throws Exception
     */
    public static double getRegulationRatio(DataStkRightPlan condDataStkRightPlan, double closingPrice, String optUndlCls) throws Exception {

        double regulationRatio = 0.0;

        // 除权除息信息由于精度问题，每股红利金额、每股红股数量和每股配股数量这三个字段也同样采用RATE级别，在表中数据放大了10^8次，取用时需保持一致
        double bonusRate = 0.0;
        double allotmentRate = 0.0;
        double conversionRate = 0.0;
        double bonusAmt = 0.0;
        double rightPrice = 0.0;
        double shareConverRate = 0.0;
        double floatChgRate = 0.0;
        DaoStkRightPlan daoStkRightPlan = new DaoStkRightPlan();

        if (condDataStkRightPlan.getStkbd().equals(ConstantUtil.STKBD_SZAG)) {
            if (ConstantUtil.STK_CLS_STOCK.equals(optUndlCls.charAt(0))) {
                /*
		         * 深市个股期权调整系数计算公式：	调整系数（R）= [除权除息前一日标的股票收盘价 ×（1 + 送股率 + 配股率 + 转股率）] / [除权除息前一日标的股票收盘价 - 每股红利 + 配股价 × 配股率]
		         * [a.PRE_CLOSEING_PRICE  * (1 + (BONUS_RATE + ALLOTMENT_RATE + CONVERSION_RATE))] / [(a.PRE_CLOSEING_PRICE - BONUS_AMT + RIGHT_PRICE * ALLOTMENT_RATE)]	其中，a表示行情表STK_MKTINFO，b表示除权除息信息表STK_RIGHT_PLAN
		         */
                try {
                    DataStkRightPlan resultDataStkRightPlan= daoStkRightPlan.selectUnique(condDataStkRightPlan);
                    if (resultDataStkRightPlan != null) {
                        bonusRate = NumConvertUtil.longToRate(resultDataStkRightPlan.getBonusRate());
                        allotmentRate = NumConvertUtil.longToRate(resultDataStkRightPlan.getAllotmentRate());
                        conversionRate = NumConvertUtil.longToRate(resultDataStkRightPlan.getConversionRate());
                        bonusAmt = NumConvertUtil.longToRate(resultDataStkRightPlan.getBonusAmt());
                        rightPrice = NumConvertUtil.longToPrice(resultDataStkRightPlan.getRightPrice());

                    }
                    else {
                        // 查询结果为空；
                        logger.error("获取除权信息为空—— TABLE_STK_RIGHT_PLAN");
                    }

                    if ((closingPrice - bonusAmt + rightPrice * allotmentRate) >= 0.00001) {
                        regulationRatio = closingPrice  * (1 + (bonusRate + allotmentRate + conversionRate)) / (closingPrice - bonusAmt + rightPrice * allotmentRate);
                    }
                    else {
                        throw new Exception("计算调整系数（R）异常，分母为0—— TABLE_STK_RIGHT_PLAN");
                    }

                } catch (Exception e) {
                    throw new Exception("查表STK_RIGHT_PLAN获取计算调整系数（R）异常", e);
                }
            }

            if (ConstantUtil.STK_CLS_ETF.equals(optUndlCls.charAt(0))) {
                /*
		         * 深市ETF期权调整系数计算公式：	调整系数（R）= [份额折算比例 × 除权除息前一日标的ETF 收盘价] / [除权除息前一日标的ETF 收盘价 － 单位分红金额]
				 * (a.PRE_CLOSEING_PRIC * b.SHARE_CONVER_RATE ) / (a.PRE_CLOSEING_PRICE - b.BONUS_AMT)
		         */
                try {
                    DataStkRightPlan resultDataStkRightPlan= daoStkRightPlan.selectUnique(condDataStkRightPlan);
                    if (resultDataStkRightPlan != null) {
                        shareConverRate = NumConvertUtil.longToRate(resultDataStkRightPlan.getShareConverRate());
                        bonusAmt = NumConvertUtil.longToRate(resultDataStkRightPlan.getBonusAmt());
                    }
                    else {
                        // 查询结果为空；
                        logger.error("获取除权信息为空—— TABLE_STK_RIGHT_PLAN");
                    }

                    if ((closingPrice - bonusAmt) >= 0.00001) {
                        regulationRatio = (closingPrice  * shareConverRate) / (closingPrice - bonusAmt);
                    }
                    else {
                        throw new Exception("计算调整系数（R）异常，分母为0—— TABLE_STK_RIGHT_PLAN");
                    }

                } catch (Exception e) {
                    throw new Exception("查表STK_RIGHT_PLAN获取计算调整系数（R）异常", e);
                }
            }
        }

        if (condDataStkRightPlan.getStkbd().equals(ConstantUtil.STKBD_SHAG)) {
            /* 上海市场调整系数计算公式当前唯一
             * 调整系数（R）= [（1 + 流通股份变动比例）× 除权(息)前一日标的证券收盘价] / [（前一日标的收盘价格 - 现金红利）+ 配（新）股价格 × 流通股份变动比例]
             * (1 + b.FLOAT_CHG_RATE) * a.PRE_CLOSEING_PRICE) / ((a.PRE_CLOSEING_PRICE - b.BONUS_AMT) + b.RIGHT_PRICE * b.FLOAT_CHG_RATE) 其中，a表示行情表STK_MKTINFO，b表示除权除息信息表STK_RIGHT_PLAN
             */
            try {
                DataStkRightPlan resultDataStkRightPlan= daoStkRightPlan.selectUnique(condDataStkRightPlan);
                if (resultDataStkRightPlan != null) {
                    floatChgRate = NumConvertUtil.longToRate(resultDataStkRightPlan.getFloatChgRate());
                    bonusAmt = NumConvertUtil.longToRate(resultDataStkRightPlan.getBonusAmt());
                    rightPrice = NumConvertUtil.longToPrice(resultDataStkRightPlan.getRightPrice());
                }
                else {
                    // 查询结果为空；
                    logger.error("查表STK_RIGHT_PLAN获取除权信息为空");
                }

                if ((closingPrice - bonusAmt + rightPrice * floatChgRate) >= 0.00001) {
                    regulationRatio = closingPrice  * (1 + floatChgRate) / (closingPrice - bonusAmt + rightPrice * floatChgRate);
                }
                else {
                    // 价格计算异常
                    logger.error("查表STK_RIGHT_PLAN获取计算调整系数（R）异常，分母为0");
                }

            } catch (Exception e) {
                throw new Exception("查表STK_RIGHT_PLAN获取计算调整系数（R）异常", e);
            }
        }

        return regulationRatio;
    }
}
