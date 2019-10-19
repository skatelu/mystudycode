/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationOptMatching
 * Author:   ZhangChangHong
 * Date:     2018/7/25
 * Description: OptMatching操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.dao.DaoOptMatching;
import com.szkingdom.data.DataOptMatching;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationOptMatching extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationOptMatching.class);

    /**
     * 组装成交回报数据
     */
    public static DataOptMatching initOptMatchingData(OperationDataIdOptMatch condOperationDataIdOptMatch, List<Object> listObjectTableData) {
        DataOptMatching dataOptMatching = new DataOptMatching();

        // 设置唯一索引
        dataOptMatching.setOrderId(condOperationDataIdOptMatch.getOrderId());
        dataOptMatching.setMatchedSn(condOperationDataIdOptMatch.getMatchedSn());
        dataOptMatching.setStkbd(condOperationDataIdOptMatch.getStkbd());
        dataOptMatching.setStkpbu(condOperationDataIdOptMatch.getStkpbu());
        dataOptMatching.setTrdDate(condOperationDataIdOptMatch.getTrdDate());

        dataOptMatching.setMatchedTime(condOperationDataIdOptMatch.getMatchedTime());
        dataOptMatching.setOrderDate(condOperationDataIdOptMatch.getTrdDate());
        dataOptMatching.setIntOrg(condOperationDataIdOptMatch.getIntOrg());
        dataOptMatching.setCustCode(condOperationDataIdOptMatch.getCustCode());
        dataOptMatching.setCustName(condOperationDataIdOptMatch.getCustName());
        dataOptMatching.setCuacctCode(condOperationDataIdOptMatch.getCuacctCode());
        dataOptMatching.setCurrency(condOperationDataIdOptMatch.getCurrency());
        dataOptMatching.setTrdacct(condOperationDataIdOptMatch.getTrdacct());
        dataOptMatching.setSubacctCode(condOperationDataIdOptMatch.getSubacctCode());
        dataOptMatching.setStkBiz(condOperationDataIdOptMatch.getStkBiz());
        dataOptMatching.setStkBizAction(condOperationDataIdOptMatch.getStkBizAction());
        dataOptMatching.setOptNum(condOperationDataIdOptMatch.getOptNum());
        dataOptMatching.setOptName(condOperationDataIdOptMatch.getOptName());
        dataOptMatching.setOptType(condOperationDataIdOptMatch.getOptType());
        dataOptMatching.setCombNum(condOperationDataIdOptMatch.getCombNum());
        dataOptMatching.setCombStraCode(condOperationDataIdOptMatch.getCombStraCode());
        dataOptMatching.setLeg1Num(condOperationDataIdOptMatch.getLeg1Num());
        dataOptMatching.setLeg2Num(condOperationDataIdOptMatch.getLeg2Num());
        dataOptMatching.setOptUndlCls(condOperationDataIdOptMatch.getOptUndlCls());
        dataOptMatching.setOptUndlCode(condOperationDataIdOptMatch.getOptUndlCode());
        dataOptMatching.setOptUndlName(condOperationDataIdOptMatch.getOptUndlName());
        dataOptMatching.setIsWithdraw(condOperationDataIdOptMatch.getIsWithdraw());
        dataOptMatching.setMatchedType(condOperationDataIdOptMatch.getMatchedType());
        dataOptMatching.setMatchedPrice(condOperationDataIdOptMatch.getMatchedPrice());
        dataOptMatching.setMatchedQty(condOperationDataIdOptMatch.getMatchedQty());

        listObjectTableData.add(dataOptMatching);
        return dataOptMatching;
    }

    /**
     * 成交类去重数据写表，写缓存
     * @param condDataOptMatching
     * @param stringBuilder
     * @throws Exception
     */
    public static void insertOptMatchingData2DB(DataOptMatching condDataOptMatching,
                                                StringBuilder stringBuilder, SqlSession sqlSession) throws Exception {
        DaoOptMatching daoOptMatching = new DaoOptMatching();
        try {
            daoOptMatching.insert(condDataOptMatching, sqlSession);
            InitCache.write(stringBuilder.toString(), ConstantUtil.CACHE_VALUES);

        } catch (Exception e) {
            throw e;
        }
    }
}
