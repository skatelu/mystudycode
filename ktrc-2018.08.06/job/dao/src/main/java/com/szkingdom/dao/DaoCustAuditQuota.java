/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoCustAuditQuota
 * Author:   yinhl
 * Date:     2018-08-20
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataCustAuditQuota;

public class DaoCustAuditQuota extends DaoBase {

	public DataCustAuditQuota selectUnique(DataCustAuditQuota dataCustAuditQuota) throws Exception {
		return (DataCustAuditQuota) super.selectOne("CUST_AUDIT_QUOTA_SELECT", dataCustAuditQuota);
	}

	public DataCustAuditQuota selectLock(DataCustAuditQuota dataCustAuditQuota) throws Exception {
		return null;
	}

	public List<DataCustAuditQuota> selectList(DataCustAuditQuota dataCustAuditQuota) throws Exception {
		return (List<DataCustAuditQuota>)selectList("CUST_AUDIT_QUOTA_SELECT",dataCustAuditQuota);
	}

	public int update(DataCustAuditQuota dataCustAuditQuota) throws Exception {
		return 0;
	}

	public int insert(DataCustAuditQuota dataCustAuditQuota) throws Exception {
		return 0;
	}

}