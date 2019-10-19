/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkOrder
 * Author:   ZhangMaohua
 * Date:     2018/9/7
 * Description: StkOrder操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.dao.DaoStkOrder;
import com.szkingdom.data.DataStkOrder;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationStkOrder extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationStkOrder.class);

    public static DataStkOrder initStkOrderData(OperationDataIdStkTrade condOperationDataIdStkTrade, List<Object> listObjectTableData) {
        DataStkOrder dataStkOrder = new DataStkOrder();

        //设置唯一索引字段
        dataStkOrder.setTrdDate(condOperationDataIdStkTrade.getTrdDate());
        dataStkOrder.setOrderId(condOperationDataIdStkTrade.getOrderId());
        dataStkOrder.setStkbd(condOperationDataIdStkTrade.getStkbd());
        dataStkOrder.setStkpbu(condOperationDataIdStkTrade.getStkpbu());
        dataStkOrder.setIsWithdraw(condOperationDataIdStkTrade.getIsWithdraw());

        dataStkOrder.setOrderDate(condOperationDataIdStkTrade.getOrderDate());
        dataStkOrder.setOrderTime(condOperationDataIdStkTrade.getOrderTime());
        dataStkOrder.setOrderType('0');
        dataStkOrder.setOrderStatus(condOperationDataIdStkTrade.getOrderStatus());
        dataStkOrder.setIntOrg(condOperationDataIdStkTrade.getIntOrg());
        dataStkOrder.setCustCode(condOperationDataIdStkTrade.getCustCode());
        //dataStkOrder.setCustName(condOperationDataIdStkTrade.);
        dataStkOrder.setCustType('0');
        dataStkOrder.setCuacctCode(condOperationDataIdStkTrade.getCuacctCode());
        dataStkOrder.setCurrency(condOperationDataIdStkTrade.getCurrency());
        dataStkOrder.setTrdacct(condOperationDataIdStkTrade.getTrdacct());
        dataStkOrder.setStkBiz(condOperationDataIdStkTrade.getStkBiz());
        dataStkOrder.setStkBizAction(condOperationDataIdStkTrade.getStkBizAction());
        dataStkOrder.setStkCode(condOperationDataIdStkTrade.getStkCode());
        dataStkOrder.setStkName(condOperationDataIdStkTrade.getStkName());
        dataStkOrder.setStkCls(condOperationDataIdStkTrade.getStkCls());
        dataStkOrder.setOrderPrice(condOperationDataIdStkTrade.getOrderPrice());
        dataStkOrder.setOrderQty(condOperationDataIdStkTrade.getOrderQty());
        dataStkOrder.setOrderFrzAmt(condOperationDataIdStkTrade.getOrderFrzAmt());
        if (condOperationDataIdStkTrade.getIsWithdraw().equals(ConstantUtil.WITHDRAW)) {
            dataStkOrder.setRawOrderId(condOperationDataIdStkTrade.getRawOrderId());
        } else {
            dataStkOrder.setRawOrderId("");
        }
        //dataStkOrder.setGpzysno();
        //dataStkOrder.setItemno();

        listObjectTableData.add(dataStkOrder);
        return dataStkOrder;
    }

    /**
     * 集中交易委托类去重数据写表，写缓存
     * @param condDataStkOrder
     * @param stringBuilder
     * @throws Exception
     */
    public static void insertStkOrderData2DB(DataStkOrder condDataStkOrder, StringBuilder stringBuilder, SqlSession sqlSession) throws Exception {
        DaoStkOrder daoStkOrder = new DaoStkOrder();
        try {
            daoStkOrder.insert(condDataStkOrder, sqlSession);
            InitCache.write(stringBuilder.toString(), ConstantUtil.CACHE_VALUES);

        } catch (Exception e) {
            throw e;
        }
    }
}
