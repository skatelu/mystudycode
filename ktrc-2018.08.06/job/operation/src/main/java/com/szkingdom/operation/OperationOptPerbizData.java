/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationOptPerbizData
 * Author:   ZhangMaohua
 * Date:     2018/8/4
 * Description:
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.dao.DaoOptPerbizData;
import com.szkingdom.data.DataOptPerbizData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationOptPerbizData extends OperationBase {

    private static Logger logger = LoggerFactory.getLogger(OperationOptPerbizData.class);

    /**
     * 获取股票期权周期数据表（OPT_PERBIZ_DATA）中同一客户的ORDER_QTY，并累加
     * @param condDataOptPerbizData
     * @return
     * @throws Exception
     */
    public static long getOrderQtyByOptPerbizData(DataOptPerbizData condDataOptPerbizData) throws Exception{
        long orderQty = 0;

        DaoOptPerbizData daoOptPerbizData = new DaoOptPerbizData();
        try {
            //查表OPT_PERBIZ_DATA
            List<DataOptPerbizData> dataOptPerbizDataList = daoOptPerbizData.selectList(condDataOptPerbizData);
            if(dataOptPerbizDataList != null && dataOptPerbizDataList.size() > 0) {
                //委托数量ORDER_QTY累加
                for (DataOptPerbizData dataOptPerbizDataEach : dataOptPerbizDataList) {
                    orderQty += dataOptPerbizDataEach.getOrderQty();
                }
            }
            else{
                logger.warn("查表 TABLE_OPT_PERBIZ_DATA 返回 NULL !");
            }
        }catch(Exception e){
            throw new Exception("查表 TABLE_OPT_PERBIZ_DATA for ORDER_QTY 抛出异常! ", e);
        }
        return orderQty;
    }

    /**
     * 获取股票期权周期数据表（OPT_PERBIZ_DATA）中清算金额（SETT_AMT）,并累加
     * @param condDataOptPerbizData
     * @return
     * @throws Exception
     */
    public static long getSettAmtByOptPerbizData(DataOptPerbizData condDataOptPerbizData) throws Exception{
        long settAmt = 0;

        DaoOptPerbizData daoOptPerbizData = new DaoOptPerbizData();
        try {
            //查表OPT_PERBIZ_DATA
            List<DataOptPerbizData> dataOptPerbizDataList = daoOptPerbizData.selectList(condDataOptPerbizData);
            if(dataOptPerbizDataList != null && dataOptPerbizDataList.size() > 0) {
                //清算金额SETT_AMT累加
                for (DataOptPerbizData dataOptPerbizDataEach : dataOptPerbizDataList) {
                    settAmt += dataOptPerbizDataEach.getSettAmt();
                }
            }
            else {
                logger.warn("查表 TABLE_OPT_PERBIZ_DATA 返回 NULL !");
            }
        }catch(Exception e){
            throw new Exception("查表 TABLE_OPT_PERBIZ_DATA for SETT_AMT 抛出异常! ", e);
        }
        return settAmt;
    }
}
