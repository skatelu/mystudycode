/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationOptMktinfo
 * Author:   ZhangChangHong
 * Date:     2018/7/26
 * Description: OPT_MKTINFO操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.dao.DaoOptMktinfo;
import com.szkingdom.data.DataOptMktinfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationOptMktinfo extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationOptMktinfo.class);

    public static DataOptMktinfo initOptMktinfoData(Object objectParam) {
        DataOptMktinfo dataOptMktinfo = new DataOptMktinfo();

        OperationDataIdOptMktinfo operationDataIdOptMktinfo = (OperationDataIdOptMktinfo) objectParam;
        dataOptMktinfo.setStkex(operationDataIdOptMktinfo.getStkex());
        dataOptMktinfo.setStkbd(operationDataIdOptMktinfo.getStkbd());
        dataOptMktinfo.setOptNum(operationDataIdOptMktinfo.getOptNum());
        dataOptMktinfo.setOptCode(operationDataIdOptMktinfo.getOptCode());
        dataOptMktinfo.setTrdDate(operationDataIdOptMktinfo.getTrdDate());
        dataOptMktinfo.setTotalAmt(operationDataIdOptMktinfo.getTotalAmt());
        dataOptMktinfo.setTotalVolume(operationDataIdOptMktinfo.getTotalVolume());
        dataOptMktinfo.setPreClosePx(operationDataIdOptMktinfo.getPreClosePx());
        dataOptMktinfo.setPreSettPrice(operationDataIdOptMktinfo.getPreSettPrice());
        dataOptMktinfo.setOptOpenPrice(operationDataIdOptMktinfo.getOptOpenPrice());
        dataOptMktinfo.setOptCurrPrice(operationDataIdOptMktinfo.getOptCurrPrice());
        dataOptMktinfo.setOptHighPrice(operationDataIdOptMktinfo.getOptHighPrice());
        dataOptMktinfo.setOptLowPrice(operationDataIdOptMktinfo.getOptLowPrice());
        dataOptMktinfo.setOptTrdPrice(operationDataIdOptMktinfo.getOptTrdPrice());
        dataOptMktinfo.setLeavesQty(operationDataIdOptMktinfo.getLeavesQty());
        dataOptMktinfo.setOptAuctPrice(operationDataIdOptMktinfo.getOptAuctPrice());
        dataOptMktinfo.setOptAuctQty(operationDataIdOptMktinfo.getOptAuctQty());
        dataOptMktinfo.setOptSettPrice(operationDataIdOptMktinfo.getOptSettPrice());
        dataOptMktinfo.setBuyPrice1(operationDataIdOptMktinfo.getBuyPrice1());
        dataOptMktinfo.setBuyVolume1(operationDataIdOptMktinfo.getBuyVolume1());
        dataOptMktinfo.setSellPrice1(operationDataIdOptMktinfo.getSellPrice1());
        dataOptMktinfo.setSellVolume1(operationDataIdOptMktinfo.getSellVolume1());
        dataOptMktinfo.setBuyPrice2(operationDataIdOptMktinfo.getBuyPrice2());
        dataOptMktinfo.setBuyVolume2(operationDataIdOptMktinfo.getBuyVolume2());
        dataOptMktinfo.setSellPrice2(operationDataIdOptMktinfo.getSellPrice2());
        dataOptMktinfo.setSellVolume2(operationDataIdOptMktinfo.getSellVolume2());
        dataOptMktinfo.setBuyPrice3(operationDataIdOptMktinfo.getBuyPrice3());
        dataOptMktinfo.setBuyVolume3(operationDataIdOptMktinfo.getBuyVolume3());
        dataOptMktinfo.setSellPrice3(operationDataIdOptMktinfo.getSellPrice3());
        dataOptMktinfo.setSellVolume3(operationDataIdOptMktinfo.getSellVolume3());
        dataOptMktinfo.setBuyPrice4(operationDataIdOptMktinfo.getBuyPrice4());
        dataOptMktinfo.setBuyVolume4(operationDataIdOptMktinfo.getBuyVolume4());
        dataOptMktinfo.setSellPrice4(operationDataIdOptMktinfo.getSellPrice4());
        dataOptMktinfo.setSellVolume4(operationDataIdOptMktinfo.getSellVolume4());
        dataOptMktinfo.setBuyPrice5(operationDataIdOptMktinfo.getBuyPrice5());
        dataOptMktinfo.setBuyVolume5(operationDataIdOptMktinfo.getBuyVolume5());
        dataOptMktinfo.setSellPrice5(operationDataIdOptMktinfo.getSellPrice5());
        dataOptMktinfo.setSellVolume5(operationDataIdOptMktinfo.getSellVolume5());
        dataOptMktinfo.setRltPhase(operationDataIdOptMktinfo.getRltPhase());
        dataOptMktinfo.setRltFlag(operationDataIdOptMktinfo.getRltFlag());
        dataOptMktinfo.setRltOpenFlag(operationDataIdOptMktinfo.getRltOpenFlag());
        dataOptMktinfo.setLatestEnqryTime(operationDataIdOptMktinfo.getLatestEnqryTime());

        return dataOptMktinfo;
    }

    public static void writeOptMktinfoData2DB(Object objectParam) throws Exception {
        DaoOptMktinfo daoOptMktinfo = new DaoOptMktinfo();
        DataOptMktinfo dataOptMktinfo = initOptMktinfoData(objectParam);

        try {
            DataOptMktinfo resultDataOptMktinfo = daoOptMktinfo.selectUnique(dataOptMktinfo);
            if (resultDataOptMktinfo != null) {
                //内存数据库更新

                // update
                daoOptMktinfo.update(dataOptMktinfo);
            } else {
                // 内存数据库写入

                // insert
                daoOptMktinfo.insert(dataOptMktinfo);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 按唯一索引查询期权行情信息 zhangch
     * @param condDataOptMktinfo
     * @throws Exception
     */
    public static DataOptMktinfo getOptMktinfoDataByUnique(DataOptMktinfo condDataOptMktinfo) throws Exception {
        DaoOptMktinfo daoOptMktinfo = new DaoOptMktinfo();
        try {
            DataOptMktinfo resultDataOptMktinfo = daoOptMktinfo.selectUnique(condDataOptMktinfo);
            if (resultDataOptMktinfo != null) {
                return resultDataOptMktinfo;

            } else {
                logger.warn("getOptMktinfoDataByUnique() 期权行情信息查无数据, OptNum:{}, Stkbd:{}", condDataOptMktinfo.getOptNum(), condDataOptMktinfo.getStkbd());
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
