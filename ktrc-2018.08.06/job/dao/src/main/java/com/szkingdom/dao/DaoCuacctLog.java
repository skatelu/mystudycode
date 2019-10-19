/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoCuacctLog
 * Author:   yinhl
 * Date:     2018-07-25
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;

import com.szkingdom.data.DataCuacctLog;
import org.apache.ibatis.session.SqlSession;

public class DaoCuacctLog extends DaoBase {
	public DataCuacctLog selectUnique(DataCuacctLog dataCuacctLog) throws Exception {
		return (DataCuacctLog)selectOne("CUACCT_LOG_SELECT", dataCuacctLog);
	}

    public DataCuacctLog selectLock (DataCuacctLog dataCuacctLog , SqlSession session)throws Exception{
		return null;
	}

	public List<DataCuacctLog> selectList(DataCuacctLog dataCuacctLog) throws Exception {
		return (List<DataCuacctLog>)selectList("CUACCT_LOG_SELECT", dataCuacctLog);
	}

	public int update(DataCuacctLog dataCuacctLog) throws Exception {
		return 0;
	}

    public int update(DataCuacctLog dataCuacctLog , SqlSession session)throws Exception{
        return 0;
    }

	public int insert(DataCuacctLog dataCuacctLog) throws Exception {
		return insert("CUACCT_LOG_INSERT", dataCuacctLog);
	}

	public int insert(DataCuacctLog dataCuacctLog, SqlSession sqlSession) throws Exception {
		return sqlSession.insert("CUACCT_LOG_INSERT", dataCuacctLog);
	}
}