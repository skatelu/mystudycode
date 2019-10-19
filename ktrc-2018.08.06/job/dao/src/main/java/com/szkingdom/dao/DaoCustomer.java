/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoCustomer
 * Author:   yinhl
 * Date:     2018-07-24
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataCustomer;
import org.apache.ibatis.session.SqlSession;

public class DaoCustomer extends DaoBase {
	public DataCustomer selectUnique(DataCustomer dataCustomer) throws Exception {
		return (DataCustomer)selectOne("CUSTOMER_SELECT", dataCustomer);
	}

    public DataCustomer selectLock (DataCustomer dataCustomer , SqlSession session)throws Exception{
		return null;
	}

	public List<DataCustomer> selectList(DataCustomer dataCustomer) throws Exception {
		return (List<DataCustomer>)selectList("CUSTOMER_SELECT", dataCustomer);
	}

	public int update(DataCustomer dataCustomer) throws Exception {
		return update("CUSTOMER_UPDATE", dataCustomer);
	}

    public int update(DataCustomer dataCustomer , SqlSession session)throws Exception{
        return 0;
    }

	public int insert(DataCustomer dataCustomer) throws Exception {
		return insert("CUSTOMER_INSERT", dataCustomer);

	}

    public int insert(DataCustomer dataCustomer , SqlSession session)throws Exception{
        return 0;
    }
}