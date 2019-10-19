/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkPlgLenderQuota
 * Author:   yinhl
 * Date:     2018-08-20
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataStkPlgLenderQuota;

public class DaoStkPlgLenderQuota extends DaoBase {

	public DataStkPlgLenderQuota selectUnique(DataStkPlgLenderQuota dataStkPlgLenderQuota) throws Exception {
		return (DataStkPlgLenderQuota)selectOne("STK_PLG_LENDER_QUOTA_SELECT",dataStkPlgLenderQuota);
	}

	public DataStkPlgLenderQuota selectLock(DataStkPlgLenderQuota dataStkPlgLenderQuota) throws Exception {
		return null;
	}

	public List<DataStkPlgLenderQuota> selectList(DataStkPlgLenderQuota dataStkPlgLenderQuota) throws Exception {
		return (List<DataStkPlgLenderQuota>)selectList("STK_PLG_LENDER_QUOTA_SELECT",dataStkPlgLenderQuota);
	}

	public int update(DataStkPlgLenderQuota dataStkPlgLenderQuota) throws Exception {
		return 0;
	}

	public int insert(DataStkPlgLenderQuota dataStkPlgLenderQuota) throws Exception {
		return 0;
	}

}