/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkBondInfo
 * Author:   ZhangMaohua
 * Date:     2018/9/10
 * Description:
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.dao.DaoStkBondInfo;
import com.szkingdom.data.DataStkBondInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationStkBondInfo extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationStkBondInfo.class);

    public static DataStkBondInfo getStkBondInfoDataByUnique(DataStkBondInfo condDataStkBondInfo) throws Exception {
        DaoStkBondInfo daoStkBondInfo = new DaoStkBondInfo();
        try {
            DataStkBondInfo resultDataStkBondInfo = daoStkBondInfo.selectUnique(condDataStkBondInfo);
            if (resultDataStkBondInfo != null) {
                return resultDataStkBondInfo;
            } else {
                logger.error("查表 STK_BOND_INFO 返回 null ! ");
                return null;
            }
        } catch (Exception e) {
            throw new Exception("查表 STK_BOND_INFO 抛出异常 ! ", e);
        }
    }
}
