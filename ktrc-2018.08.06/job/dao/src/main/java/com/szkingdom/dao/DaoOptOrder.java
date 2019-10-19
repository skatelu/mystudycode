/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoOptOrder
 * Author:   yinhl
 * Date:     2018-07-25
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;

import com.szkingdom.data.DataOptOrder;
import org.apache.ibatis.session.SqlSession;

public class DaoOptOrder extends DaoBase {
	public DataOptOrder selectUnique(DataOptOrder dataOptOrder) throws Exception {
		return (DataOptOrder)selectOne("OPT_ORDER_SELECT", dataOptOrder);
	}

    public DataOptOrder selectLock (DataOptOrder dataOptOrder , SqlSession session)throws Exception{
		return null;
	}

	public List<DataOptOrder> selectList(DataOptOrder dataOptOrder) throws Exception {
		return (List<DataOptOrder>)selectList("OPT_ORDER_SELECT", dataOptOrder);
	}

	public int update(DataOptOrder dataOptOrder) throws Exception {
		return 0;
	}

    public int update(DataOptOrder dataOptOrder , SqlSession session)throws Exception{
        return 0;
    }

	public int insert(DataOptOrder dataOptOrder) throws Exception {
		return insert("OPT_ORDER_INSERT", dataOptOrder);
	}

	public int insert(DataOptOrder dataOptOrder, SqlSession sqlSession) throws Exception {
		return sqlSession.insert("OPT_ORDER_INSERT", dataOptOrder);
	}
}