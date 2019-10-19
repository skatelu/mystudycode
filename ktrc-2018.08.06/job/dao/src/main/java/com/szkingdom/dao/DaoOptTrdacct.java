/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoOptTrdacct
 * Author:   yinhl
 * Date:     2018-07-25
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;

import com.szkingdom.data.DataOptTrdacct;
import org.apache.ibatis.session.SqlSession;

public class DaoOptTrdacct extends DaoBase {
	public DataOptTrdacct selectUnique(DataOptTrdacct dataOptTrdacct) throws Exception {
		return (DataOptTrdacct)selectOne("OPT_TRDACCT_SELECT",dataOptTrdacct);
	}

	public DataOptTrdacct selectLock(DataOptTrdacct dataOptTrdacct,SqlSession sqlSession) throws Exception {
		return (DataOptTrdacct)sqlSession.selectOne("OPT_TRDACCT_SELECT_LOCK",dataOptTrdacct);
	}

	public List<DataOptTrdacct> selectList(DataOptTrdacct dataOptTrdacct) throws Exception {
		return (List<DataOptTrdacct>)selectList("OPT_TRDACCT_SELECT",dataOptTrdacct);
	}

	public int update(DataOptTrdacct dataOptTrdacct) throws Exception {
		return update("OPT_TRDACCT_UPDATE",dataOptTrdacct);
	}

	public int insert(DataOptTrdacct dataOptTrdacct) throws Exception {
		return insert("OPT_TRDACCT_INSERT",dataOptTrdacct);
	}

	public int update(DataOptTrdacct dataOptTrdacct, SqlSession sqlSession) throws Exception {
		return sqlSession.update("OPT_TRDACCT_UPDATE", dataOptTrdacct);
	}

	public int insert(DataOptTrdacct dataOptTrdacct, SqlSession sqlSession) throws Exception {
		return sqlSession.insert("OPT_TRDACCT_INSERT", dataOptTrdacct);
	}
}