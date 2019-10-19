/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkMatching
 * Author:   ZhangMaohua
 * Date:     2018/9/7
 * Description: StkMatching操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.dao.DaoStkMatching;
import com.szkingdom.data.DataStkMatching;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationStkMatching extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationStkMatching.class);

    public static DataStkMatching initStkMatchingData(OperationDataIdStkMatch condOperationDataIdStkMatch, List<Object> listObjectTableData) {
        DataStkMatching dataStkMatching = new DataStkMatching();

        //唯一索引字段
        dataStkMatching.setTrdDate(condOperationDataIdStkMatch.getTrdDate());
        dataStkMatching.setOrderId(condOperationDataIdStkMatch.getOrderId());
        dataStkMatching.setStkbd(condOperationDataIdStkMatch.getStkbd());
        dataStkMatching.setStkpbu(condOperationDataIdStkMatch.getStkpbu());
        dataStkMatching.setMatchedSn(condOperationDataIdStkMatch.getMatchedSn());

        dataStkMatching.setMatchedTime(condOperationDataIdStkMatch.getMatchedTime());
        dataStkMatching.setOrderDate(condOperationDataIdStkMatch.getRepchDate());
        dataStkMatching.setOrderType('0');
        dataStkMatching.setIntOrg(condOperationDataIdStkMatch.getIntOrg());
        dataStkMatching.setCustCode(condOperationDataIdStkMatch.getCustCode());
        dataStkMatching.setCuacctCode(condOperationDataIdStkMatch.getCuacctCode());
        dataStkMatching.setCurrency(condOperationDataIdStkMatch.getCurrency());
        //dataStkMatching.setFirmid();
        dataStkMatching.setTrdacct(condOperationDataIdStkMatch.getTrdacct());
        dataStkMatching.setStkBiz(condOperationDataIdStkMatch.getStkBiz());
        dataStkMatching.setStkBizAction(condOperationDataIdStkMatch.getStkBizAction());
        dataStkMatching.setStkCode(condOperationDataIdStkMatch.getStkCode());
        dataStkMatching.setStkName(condOperationDataIdStkMatch.getStkName());
        dataStkMatching.setStkCls(condOperationDataIdStkMatch.getStkCls());
        dataStkMatching.setIsWithdraw(condOperationDataIdStkMatch.getIsWithdraw());
        dataStkMatching.setMatchedType(condOperationDataIdStkMatch.getMatchedType());
        dataStkMatching.setMatchedPrice(condOperationDataIdStkMatch.getMatchedPrice());
        dataStkMatching.setMatchedQty(condOperationDataIdStkMatch.getMatchedQty());

        listObjectTableData.add(dataStkMatching);
        return dataStkMatching;
    }

    /**
     * 集中交易成交类去重数据写表，写缓存
     * @param condDataStkMatching
     * @param stringBuilder
     * @throws Exception
     */
    public static void insertStkMatchingData2DB(DataStkMatching condDataStkMatching, StringBuilder stringBuilder, SqlSession sqlSession) throws Exception {
        DaoStkMatching daoStkMatching = new DaoStkMatching();
        try {
            daoStkMatching.insert(condDataStkMatching, sqlSession);
            InitCache.write(stringBuilder.toString(), ConstantUtil.CACHE_VALUES);

        } catch (Exception e) {
            throw e;
        }
    }

}
