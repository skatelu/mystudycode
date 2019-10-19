/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationOptOrder
 * Author:   ZhangChangHong
 * Date:     2018/7/25
 * Description: OptOrder操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.dao.DaoOptOrder;
import com.szkingdom.data.DataOptOrder;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationOptOrder extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationOptOrder.class);
    /**
     * 组装交易申报数据
     */
    public static DataOptOrder initOptOrderData(OperationDataIdOptTrade condOperationDataIdOptTrade, List<Object> listObjectTableData) {
        DataOptOrder dataOptOrder = new DataOptOrder();

        // 设置唯一索引
        dataOptOrder.setOrderId(condOperationDataIdOptTrade.getOrderId());
        dataOptOrder.setStkpbu(condOperationDataIdOptTrade.getStkpbu());
        dataOptOrder.setTrdDate(condOperationDataIdOptTrade.getTrdDate());
        dataOptOrder.setIsWithdraw(condOperationDataIdOptTrade.getIsWithdraw());

        dataOptOrder.setOrderDate(condOperationDataIdOptTrade.getOrderDate());
        dataOptOrder.setOrderTime(condOperationDataIdOptTrade.getOrderTime());
        dataOptOrder.setCustCode(condOperationDataIdOptTrade.getCustCode());
        dataOptOrder.setCustName(condOperationDataIdOptTrade.getCustName());
        dataOptOrder.setCustType(condOperationDataIdOptTrade.getCustType());
        dataOptOrder.setCuacctCode(condOperationDataIdOptTrade.getCuacctCode());
        dataOptOrder.setCurrency(condOperationDataIdOptTrade.getCurrency());
        dataOptOrder.setStkbd(condOperationDataIdOptTrade.getStkbd());
        dataOptOrder.setTrdacct(condOperationDataIdOptTrade.getTrdacct());
        dataOptOrder.setSubacctCode(condOperationDataIdOptTrade.getSubacctCode());
        dataOptOrder.setIntOrg(condOperationDataIdOptTrade.getIntOrg());
        dataOptOrder.setStkBiz(condOperationDataIdOptTrade.getStkBiz());
        dataOptOrder.setStkBizAction(condOperationDataIdOptTrade.getStkBizAction());
        dataOptOrder.setOrderStatus(condOperationDataIdOptTrade.getOrderStatus());
        dataOptOrder.setOptNum(condOperationDataIdOptTrade.getOptNum());
        dataOptOrder.setOptName(condOperationDataIdOptTrade.getOptName());
        dataOptOrder.setOptType(condOperationDataIdOptTrade.getOptType());
        dataOptOrder.setCombNum(condOperationDataIdOptTrade.getCombNum());
        dataOptOrder.setCombStraCode(condOperationDataIdOptTrade.getCombStraCode());
        dataOptOrder.setLeg1Num(condOperationDataIdOptTrade.getLeg1Num());
        dataOptOrder.setLeg2Num(condOperationDataIdOptTrade.getLeg2Num());
        dataOptOrder.setOrderPrice(condOperationDataIdOptTrade.getOrderPrice());
        dataOptOrder.setOrderQty(condOperationDataIdOptTrade.getOrderQty());
        dataOptOrder.setOrderFrzAmt(condOperationDataIdOptTrade.getOrderFrzAmt());
        dataOptOrder.setOptUndlCls(condOperationDataIdOptTrade.getOptUndlCls());
        dataOptOrder.setOptUndlCode(condOperationDataIdOptTrade.getOptUndlCode());
        dataOptOrder.setOptUndlName(condOperationDataIdOptTrade.getOptUndlName());
        dataOptOrder.setRawOrderId(condOperationDataIdOptTrade.getRawOrderId());
        dataOptOrder.setOptTrdacctLvl('0');

        listObjectTableData.add(dataOptOrder);
        return dataOptOrder;
    }

    /**
     * 委托类去重数据写表，写缓存
     * @param condDataOptOrder
     * @param stringBuilder
     * @throws Exception
     */
    public static void insertOptOrderData2DB(DataOptOrder condDataOptOrder, StringBuilder stringBuilder,
                                             SqlSession sqlSession, JSONObject jsonObject) throws Exception {
        DaoOptOrder daoOptOrder = new DaoOptOrder();
        try {
            daoOptOrder.insert(condDataOptOrder, sqlSession);
            InitCache.write(stringBuilder.toString(), ConstantUtil.CACHE_VALUES);

            // 查表数据透传
            jsonObject.put(ConstantUtil.JSON_OPT_ORDER, condDataOptOrder.toJson());
        } catch (Exception e) {
            throw e;
        }
    }
}
