/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoOptClsPosiLmt
 * Author:   yinhl
 * Date:     2018-07-24
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;

import com.szkingdom.data.DataOptClsPosiLmt;

public class DaoOptClsPosiLmt extends DaoBase {

	public DataOptClsPosiLmt selectUnique(DataOptClsPosiLmt dataOptClsPosiLmt) throws Exception {
		return (DataOptClsPosiLmt)selectOne("OPT_CLS_POSI_LMT_SELECT", dataOptClsPosiLmt);
	}

	public DataOptClsPosiLmt selectLock(DataOptClsPosiLmt dataOptClsPosiLmt) throws Exception {
		return null;
	}

	public List<DataOptClsPosiLmt> selectList(DataOptClsPosiLmt dataOptClsPosiLmt) throws Exception {
		return (List<DataOptClsPosiLmt>)selectList("OPT_CLS_POSI_LMT_SELECT", dataOptClsPosiLmt);
	}

	public int update(DataOptClsPosiLmt dataOptClsPosiLmt) throws Exception {
		return update("OPT_CLS_POSI_LMT_UPDATE", dataOptClsPosiLmt);
	}

	public int insert(DataOptClsPosiLmt dataOptClsPosiLmt) throws Exception {
		return insert("OPT_CLS_POSI_LMT_INSERT", dataOptClsPosiLmt);
	}

}