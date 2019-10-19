/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoOptMatching
 * Author:   yinhl
 * Date:     2018-07-25
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;

import com.szkingdom.data.DataOptMatching;
import org.apache.ibatis.session.SqlSession;

public class DaoOptMatching extends DaoBase {
	public DataOptMatching selectUnique(DataOptMatching dataOptMatching) throws Exception {
		return (DataOptMatching)selectOne("OPT_MATCHING_SELECT", dataOptMatching);
	}

    public DataOptMatching selectLock (DataOptMatching dataOptMatching , SqlSession session)throws Exception{
		return null;
	}

	public List<DataOptMatching> selectList(DataOptMatching dataOptMatching) throws Exception {
		return (List<DataOptMatching>)selectList("OPT_MATCHING_SELECT", dataOptMatching);
	}

	public int update(DataOptMatching dataOptMatching) throws Exception {
		return 0;
	}

    public int update(DataOptMatching dataOptMatching , SqlSession session)throws Exception{
        return 0;
    }

	public int insert(DataOptMatching dataOptMatching) throws Exception {
		return insert("OPT_MATCHING_INSERT", dataOptMatching);
	}

	public int insert(DataOptMatching dataOptMatching, SqlSession sqlSession) throws Exception {
		return sqlSession.insert("OPT_MATCHING_INSERT", dataOptMatching);
	}
}