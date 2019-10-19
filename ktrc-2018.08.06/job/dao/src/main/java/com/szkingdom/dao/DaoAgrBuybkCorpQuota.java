/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoAgrBuybkCorpQuota
 * Author:   yinhl
 * Date:     2018-08-20
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataAgrBuybkCorpQuota;

public class DaoAgrBuybkCorpQuota extends DaoBase {

	public DataAgrBuybkCorpQuota selectUnique(DataAgrBuybkCorpQuota dataAgrBuybkCorpQuota) throws Exception {
		return (DataAgrBuybkCorpQuota) super.selectOne("AGR_BUYBK_CORP_QUOTA_SELECT", dataAgrBuybkCorpQuota);
	}

	public DataAgrBuybkCorpQuota selectLock(DataAgrBuybkCorpQuota dataAgrBuybkCorpQuota) throws Exception {
		return null;
	}

	public List<DataAgrBuybkCorpQuota> selectList(DataAgrBuybkCorpQuota dataAgrBuybkCorpQuota) throws Exception {
		return null;
	}

	public int update(DataAgrBuybkCorpQuota dataAgrBuybkCorpQuota) throws Exception {
		return 0;
	}

	public int insert(DataAgrBuybkCorpQuota dataAgrBuybkCorpQuota) throws Exception {
		return 0;
	}

}