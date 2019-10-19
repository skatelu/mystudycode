/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoOptCustPosiLmt
 * Author:   yinhl
 * Date:     2018-07-24
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataOptCustPosiLmt;

public class DaoOptCustPosiLmt extends DaoBase {

	public DataOptCustPosiLmt selectUnique(DataOptCustPosiLmt dataOptCustPosiLmt) throws Exception {
		return (DataOptCustPosiLmt)selectOne("OPT_CUST_POSI_LMT_SELECT", dataOptCustPosiLmt);
	}

	public DataOptCustPosiLmt selectLock(DataOptCustPosiLmt dataOptCustPosiLmt) throws Exception {
		return null;
	}

	public List<DataOptCustPosiLmt> selectList(DataOptCustPosiLmt dataOptCustPosiLmt) throws Exception {
		return (List<DataOptCustPosiLmt>)selectList("OPT_CUST_POSI_LMT_SELECT", dataOptCustPosiLmt);
	}

	public int update(DataOptCustPosiLmt dataOptCustPosiLmt) throws Exception {
		return update("OPT_CUST_POSI_LMT_UPDATE", dataOptCustPosiLmt);
	}

	public int insert(DataOptCustPosiLmt dataOptCustPosiLmt) throws Exception {
		return insert("OPT_CUST_POSI_LMT_INSERT", dataOptCustPosiLmt);
	}

}