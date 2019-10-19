/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationRiskIndicators
 * Author:   ZhengMingjie
 * Date:     2018/8/24
 * Description: 风险指标表的操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.common.NumConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationRiskIndicators {
    protected static Logger logger = LoggerFactory.getLogger(OperationRiskIndicators.class);

    /**
     * 根据数据类型，选择放大倍数
     * @param indicatorResultValue
     * @param indicatorResultType
     * @return
     * @throws Exception
     */
    public static long magnifyByIndicatorResultType(double indicatorResultValue, char indicatorResultType) throws Exception{

        if ('0' == indicatorResultType) {
            return NumConvertUtil.rateToLong(indicatorResultValue);
        }
        else if ('2' == indicatorResultType) {
            return NumConvertUtil.moneyToLong(indicatorResultValue);
        }
        else if ('4' == indicatorResultType) {
            return NumConvertUtil.priceToLong(indicatorResultValue);
        }
        else {
            return Double.valueOf(indicatorResultValue).longValue();
        }
    }

    /**
     * 判断阈值：字符串"2,999900000000,999900000000,1"与风险结果比较
     * @param indicatorResult
     * @param operForluma
     * @return
     */
    public static boolean getOperationResult(long indicatorResult, String operForluma){
        String[] operForlumaElement = operForluma.split(",");
        /**
         * 解析字符串  比例的负无穷大，正无穷大分别用-9999，+9999
         */
        long prevData = Long.parseLong(operForlumaElement[1]);
        long lattData = Long.parseLong(operForlumaElement[2]);

        //(-∞，+∞)时，直接返回
        boolean allFields = ((prevData == 999900000000L) && (lattData == -999900000000L))
                            ||((prevData == -999900000000L) && (lattData == 999900000000L));
        if (allFields) {
            return true;
        }
        /**
         * 0:<  1:<=  2:>  3:>=
         */
        if("0".equals(operForlumaElement[0]) && "0".equals(operForlumaElement[3])){
            if(prevData == 999900000000L){
                return (indicatorResult < lattData);
            }
            else if(lattData == 999900000000L){
                return (indicatorResult < prevData);
            }
            else {
                return (indicatorResult < prevData && indicatorResult < lattData);
            }
        }
        else if("0".equals(operForlumaElement[0]) && "1".equals(operForlumaElement[3])){
            if(prevData == 999900000000L){
                return (indicatorResult <= lattData);
            }
            else {
                return (indicatorResult < prevData && indicatorResult <= lattData);
            }
        }
        else if("0".equals(operForlumaElement[0]) && "2".equals(operForlumaElement[3])){
            if(prevData == 999900000000L){
                return (indicatorResult > lattData);
            }
            else if(lattData == -999900000000L){
                return (indicatorResult < prevData);
            }
            else {
                return (indicatorResult < prevData && indicatorResult > lattData);
            }
        }
        else if("0".equals(operForlumaElement[0]) && "3".equals(operForlumaElement[3])){
            if(prevData == 999900000000L){
                return (indicatorResult >= lattData);
            }
            else {
                return (indicatorResult < prevData && indicatorResult >= lattData);
            }
        }
        else if("1".equals(operForlumaElement[0]) && "0".equals(operForlumaElement[3])){
            if(lattData == 999900000000L){
                return (indicatorResult <= prevData);
            }
            else {
                return (indicatorResult <= prevData && indicatorResult < lattData);
            }
        }
        else if("1".equals(operForlumaElement[0]) && "1".equals(operForlumaElement[3])){
            return (indicatorResult <= prevData && indicatorResult <= lattData);
        }
        else if("1".equals(operForlumaElement[0]) && "2".equals(operForlumaElement[3])){
            if(lattData == -999900000000L){
                return (indicatorResult <= prevData);
            }
            else {
                return (indicatorResult <= prevData && indicatorResult > lattData);
            }
        }
        else if("1".equals(operForlumaElement[0]) && "3".equals(operForlumaElement[3])){
            return (indicatorResult <= prevData && indicatorResult >= lattData);
        }
        else if("2".equals(operForlumaElement[0]) && "0".equals(operForlumaElement[3])){
            if(prevData == -999900000000L){
                return (indicatorResult < lattData);
            }
            else if(lattData == 999900000000L){
                return (indicatorResult > prevData);
            }
            else {
                return (indicatorResult > prevData && indicatorResult < lattData);
            }
        }
        else if("2".equals(operForlumaElement[0]) && "1".equals(operForlumaElement[3])){
            if(prevData == -999900000000L){
                return (indicatorResult <= lattData);
            }
            else {
                return (indicatorResult > prevData && indicatorResult <= lattData);
            }
        }
        else if("2".equals(operForlumaElement[0]) && "2".equals(operForlumaElement[3])){
            if(prevData == -999900000000L){
                return (indicatorResult > lattData);
            }
            else if(lattData == -999900000000L){
                return (indicatorResult > prevData);
            }
            else {
                return (indicatorResult > prevData && indicatorResult > lattData);
            }
        }
        else if("2".equals(operForlumaElement[0]) && "3".equals(operForlumaElement[3])){
            if(prevData == -999900000000L){
                return (indicatorResult >= lattData);
            }
            else {
                return (indicatorResult > prevData && indicatorResult >= lattData);
            }
        }
        else if("3".equals(operForlumaElement[0]) && "0".equals(operForlumaElement[3])){
            if(lattData == 999900000000L){
                return (indicatorResult >= prevData);
            }
            else {
                return (indicatorResult >= prevData && indicatorResult < lattData);
            }
        }
        else if("3".equals(operForlumaElement[0]) && "1".equals(operForlumaElement[3])){
            return (indicatorResult >= prevData && indicatorResult <= lattData);
        }
        else if("3".equals(operForlumaElement[0]) && "2".equals(operForlumaElement[3])){
            if(lattData == -999900000000L){
                return (indicatorResult >= prevData);
            }
            else {
                return (indicatorResult >= prevData && indicatorResult > lattData);
            }
        }
        else if("3".equals(operForlumaElement[0]) && "3".equals(operForlumaElement[3])){
            return (indicatorResult >= prevData && indicatorResult >= lattData);
        }
        else{
            return false;
        }
    }

}
