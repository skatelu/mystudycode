/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoOptMktinfo
 * Author:   yinhl
 * Date:     2018-07-25
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.szkingdom.data.DataOptMktinfo;

public class DaoOptMktinfo extends DaoBase {
	public DataOptMktinfo selectUnique(DataOptMktinfo dataOptMktinfo) throws Exception {
		return (DataOptMktinfo)selectOne("OPT_MKTINFO_SELECT", dataOptMktinfo);
	}

    public DataOptMktinfo selectLock (DataOptMktinfo dataOptMktinfo , SqlSession session)throws Exception{
		return null;
	}

	public List<DataOptMktinfo> selectList(DataOptMktinfo dataOptMktinfo) throws Exception {
		return null;
	}

	public int update(DataOptMktinfo dataOptMktinfo) throws Exception {
		return update("OPT_MKTINFO_UPDATE", dataOptMktinfo);
	}

    public int update(DataOptMktinfo dataOptMktinfo , SqlSession session)throws Exception{
        return 0;
    }

	public int insert(DataOptMktinfo dataOptMktinfo) throws Exception {
		return insert("OPT_MKTINFO_INSERT", dataOptMktinfo);
	}

    public int insert(DataOptMktinfo dataOptMktinfo , SqlSession session)throws Exception{
        return 0;
    }
}