/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkMktInfo
 * Author:   ZhangChangHong
 * Date:     2018/7/26
 * Description: STK_MKTINFO操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.dao.DaoStkMktinfo;
import com.szkingdom.data.DataStkMktinfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationStkMktInfo extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationStkMktInfo.class);

    public static DataStkMktinfo initStkMktinfoData(Object objectParam) {
        DataStkMktinfo dataStkMktinfo = new DataStkMktinfo();

        OperationDataIdStkMktinfo operationDataIdStkMktinfo = (OperationDataIdStkMktinfo) objectParam;
        dataStkMktinfo.setStkex(operationDataIdStkMktinfo.getStkex());
        dataStkMktinfo.setStkbd(operationDataIdStkMktinfo.getStkbd());
        dataStkMktinfo.setStkCode(operationDataIdStkMktinfo.getStkCode());
        dataStkMktinfo.setTrdDate(operationDataIdStkMktinfo.getTrdDate());
        dataStkMktinfo.setClosingPrice(operationDataIdStkMktinfo.getClosingPrice());
        dataStkMktinfo.setOpeningPrice(operationDataIdStkMktinfo.getOpeningPrice());
        dataStkMktinfo.setCurrentPrice(operationDataIdStkMktinfo.getCurrentPrice());
        dataStkMktinfo.setBondInt(operationDataIdStkMktinfo.getBondInt());
        dataStkMktinfo.setBondIntDate(operationDataIdStkMktinfo.getBondIntDate());
        dataStkMktinfo.setEtfIopv(operationDataIdStkMktinfo.getEtfIopv());

        return dataStkMktinfo;
    }

    public static void writeStkMktinfoData2DB(Object objectParam) throws Exception {
        DaoStkMktinfo daoStkMktinfo = new DaoStkMktinfo();
        DataStkMktinfo dataStkMktinfo = initStkMktinfoData(objectParam);

        try {
            DataStkMktinfo resultDataStkMktinfo = daoStkMktinfo.selectUnique(dataStkMktinfo);
            if (resultDataStkMktinfo != null) {
                //内存数据库更新

                // update
                daoStkMktinfo.update(dataStkMktinfo);
            } else {
                // 内存数据库写入

                // insert
                daoStkMktinfo.insert(dataStkMktinfo);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 获取标的证券收盘价
     * @param condDataStkMktinfo
     * @return
     * @throws Exception
     */
    public static double getClosingPrice(DataStkMktinfo condDataStkMktinfo) throws Exception {
        double closingPriceResult = 0.0;
        DaoStkMktinfo daoStkMktinfo = new DaoStkMktinfo();
        try {
            DataStkMktinfo resultDataStkMktinfo = daoStkMktinfo.selectUnique(condDataStkMktinfo);
            if (resultDataStkMktinfo != null) {
                closingPriceResult = NumConvertUtil.longToPrice(resultDataStkMktinfo.getClosingPrice());
            } else {
                // 提示查无结果
                logger.warn("查表获取标的证券收盘价无结果，返回结果为0.0 \n");
            }

        } catch (Exception e) {
            throw new Exception("查表STK_MKTINFO获取收盘价异常", e);
        }

        return closingPriceResult;
    }

    /**
     * 按唯一索引查询行情信息 zhangch
     * @param condDataStkMktinfo
     * @throws Exception
     */
    public static DataStkMktinfo getStkMktinfoDataByUnique(DataStkMktinfo condDataStkMktinfo) throws Exception {
        DaoStkMktinfo daoStkMktinfo = new DaoStkMktinfo();
        try {
            DataStkMktinfo resultDataStkMktinfo = daoStkMktinfo.selectUnique(condDataStkMktinfo);
            if (resultDataStkMktinfo != null) {
                return resultDataStkMktinfo;

            } else {
                logger.error("getStkMktinfoDataByUnique() 行情信息查无数据,StkCode:{},Stkbd:{}", condDataStkMktinfo.getStkCode(), condDataStkMktinfo.getStkbd());
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
