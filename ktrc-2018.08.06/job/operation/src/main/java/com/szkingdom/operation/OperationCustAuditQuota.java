/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationCustAuditQuota
 * Author:   ZhangMaohua
 * Date:     2018/8/20
 * Description:
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.dao.DaoCustAuditQuota;
import com.szkingdom.data.DataCustAuditQuota;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationCustAuditQuota extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationCustAuditQuota.class);

    public static DataCustAuditQuota getDataCustAuditQuotaByUnique(DataCustAuditQuota dataCustAuditQuotaPara) throws Exception{
        DaoCustAuditQuota daoCustAuditQuota = new DaoCustAuditQuota();
        try{
            DataCustAuditQuota dataCustAuditQuotaResult = daoCustAuditQuota.selectUnique(dataCustAuditQuotaPara);
            if(dataCustAuditQuotaResult != null){
                return dataCustAuditQuotaResult;
            }
            else{
                logger.error("select CustAuditQuota return null error ! ");
                return null;
            }

        }catch (Exception e){
            logger.error("select CustAuditQuota throw Exception error ! ");
            throw e;
        }
    }
}
