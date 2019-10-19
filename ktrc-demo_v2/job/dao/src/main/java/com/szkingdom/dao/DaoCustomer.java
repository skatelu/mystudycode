package com.szkingdom.dao;

import com.szkingdom.data.DataCustomer;

import java.util.List;
import java.util.Map;

/**
 * @author zhangch
 * @date 2018/7/10 20:06
 */
public class DaoCustomer extends DaoBase {

    public List<DataCustomer> selectCustomerList() throws Exception {
        return (List<DataCustomer>)selectList("COMMON_CUSTOMER_SELECT", null);
    }

    public List<DataCustomer> selectCustomerList(DataCustomer dataCustomer) throws Exception {
        return (List<DataCustomer>)selectList("COMMON_CUSTOMER_SELECT", dataCustomer);
    }
}
