package com.szkingdom.dao;

import com.szkingdom.data.DataOptPosiLmt;
import org.apache.ibatis.session.SqlSession;


import java.util.List;



public class DaoOptPosiLmt extends DaoBase {

	public List<DataOptPosiLmt> selectOptPosiLmtList() throws Exception {
		return (List<DataOptPosiLmt>)selectList("COMMON_OPT_POSI_LMT_SELECT", null);
	}

	public List<DataOptPosiLmt> selectOptPosiLmtList(DataOptPosiLmt dataOptPosiLmt) throws Exception {
		return (List<DataOptPosiLmt>)selectList("COMMON_OPT_POSI_LMT_SELECT", dataOptPosiLmt);
	}

}
