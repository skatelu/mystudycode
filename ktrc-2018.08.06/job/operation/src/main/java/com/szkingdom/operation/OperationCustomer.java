/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationCustomer
 * Author:   ZhangChangHong
 * Date:     2018/8/10
 * Description: 客户表操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.dao.DaoCustomer;
import com.szkingdom.data.DataCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationCustomer extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationCustomer.class);

    /**
     * 按唯一索引查询客户信息 zhangch
     * @param condDataCustomer
     * @throws Exception
     */
    public static DataCustomer getCustomerDataByUnique(DataCustomer condDataCustomer) throws Exception {
        DaoCustomer daoCustomer = new DaoCustomer();
        try {
            DataCustomer resultDataCustomer = daoCustomer.selectUnique(condDataCustomer);
            if (resultDataCustomer != null) {
                return resultDataCustomer;

            } else {
                logger.error("getCustomerDataByUnique() 客户表CUSTOMER查无数据,CUST_CODE:{}", condDataCustomer.getCustCode());
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static String getCustomerType(DataCustomer dataCustomerParam) throws Exception {
        DaoCustomer daoCustomer = new DaoCustomer();
        try {
            DataCustomer resultDataCustomer = daoCustomer.selectUnique(dataCustomerParam);
            if (resultDataCustomer != null) {
                return resultDataCustomer.getCustType().toString();

            } else {
                logger.error("查表CUSTOMER获取客户类别数据为空");
                return " ";
            }
        } catch (Exception e) {
            throw new Exception("查表CUSTOMER获取客户类别异常", e);
        }
    }
}
