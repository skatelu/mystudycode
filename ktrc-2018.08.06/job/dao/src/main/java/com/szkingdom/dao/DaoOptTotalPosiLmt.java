/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoOptTotalPosiLmt
 * Author:   yinhl
 * Date:     2018-07-24
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataOptTotalPosiLmt;

public class DaoOptTotalPosiLmt extends DaoBase {
	
	public DataOptTotalPosiLmt selectUnique(DataOptTotalPosiLmt dataOptTotalPosiLmt) throws Exception {
		return (DataOptTotalPosiLmt)selectList("OPT_TOTAL_POSI_LMT_SELECT", dataOptTotalPosiLmt);
	}

	public DataOptTotalPosiLmt selectLock(DataOptTotalPosiLmt dataOptTotalPosiLmt) throws Exception {
		return null;
	}

	public List<DataOptTotalPosiLmt> selectList(DataOptTotalPosiLmt dataOptTotalPosiLmt) throws Exception {
		return (List<DataOptTotalPosiLmt>)selectList("OPT_TOTAL_POSI_LMT_SELECT", dataOptTotalPosiLmt);
	}

	public int update(DataOptTotalPosiLmt dataOptTotalPosiLmt) throws Exception {
		return 0;
	}

	public int insert(DataOptTotalPosiLmt dataOptTotalPosiLmt) throws Exception {
		return 0;
	}
}