/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkPlgLenderQuota
 * Author:   ZhangMaohua
 * Date:     2018/8/20
 * Description:
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.dao.DaoStkPlgLenderQuota;
import com.szkingdom.data.DataStkPlgLenderQuota;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationStkPlgLenderQuota extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationStkPlgLenderQuota.class);

    /**
     *
     * @param dataStkPlgLenderQuotaPara
     * @return
     */
    public static DataStkPlgLenderQuota getStkPlgLenderQuotaDataByUnique(DataStkPlgLenderQuota dataStkPlgLenderQuotaPara) throws Exception{
        DaoStkPlgLenderQuota daoStkPlgLenderQuota = new DaoStkPlgLenderQuota();
        try{
            DataStkPlgLenderQuota dataStkPlgLenderQuotaResult = daoStkPlgLenderQuota.selectUnique(dataStkPlgLenderQuotaPara);
            if(dataStkPlgLenderQuotaResult != null){
                return dataStkPlgLenderQuotaResult;
            }
            else {
                logger.error("select StkPlgLenderQuota return null error ! ");
                return null;
            }
        } catch(Exception e){
            logger.error("select StkPlgLenderQuota throw Exception ! ");
            throw e;
        }


    }

}
