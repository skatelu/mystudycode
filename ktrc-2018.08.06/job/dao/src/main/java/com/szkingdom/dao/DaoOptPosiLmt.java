/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoOptPosiLmt
 * Author:   yinhl
 * Date:     2018-07-24
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;

import com.szkingdom.data.DataOptPosiLmt;

public class DaoOptPosiLmt extends DaoBase {

	public DataOptPosiLmt selectUnique(DataOptPosiLmt dataOptPosiLmt) throws Exception {
		return (DataOptPosiLmt)selectOne("OPT_POSI_LMT_SELECT", dataOptPosiLmt);
	}

	public DataOptPosiLmt selectLock(DataOptPosiLmt dataOptPosiLmt) throws Exception {
		return null;
	}

	public List<DataOptPosiLmt> selectList(DataOptPosiLmt dataOptPosiLmt) throws Exception {
		return (List<DataOptPosiLmt>)selectList("OPT_POSI_LMT_SELECT", dataOptPosiLmt);
	}

	public int update(DataOptPosiLmt dataOptPosiLmt) throws Exception {
		return update("OPT_POSI_LMT_UPDATE", dataOptPosiLmt);
	}

	public int insert(DataOptPosiLmt dataOptPosiLmt) throws Exception {
		return insert("OPT_POSI_LMT_INSERT", dataOptPosiLmt);
	}
}