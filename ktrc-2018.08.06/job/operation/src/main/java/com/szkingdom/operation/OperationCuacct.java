/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationCuacct
 * Author:   ZhangChangHong
 * Date:     2018/8/10
 * Description: 资产账户操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.dao.DaoCuacct;
import com.szkingdom.data.DataCuacct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationCuacct extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationCuacct.class);

    /**
     * 按唯一索引查询资产账户数据 zhangch
     * @param condDataCuacct
     * @throws Exception
     */
    public static DataCuacct getCuacctDataByUnique(DataCuacct condDataCuacct) throws Exception {
        DaoCuacct daoCuacct = new DaoCuacct();
        try {
            DataCuacct resultDataCuacct = daoCuacct.selectUnique(condDataCuacct);
            if (resultDataCuacct != null) {
                return resultDataCuacct;

            } else {
                logger.error("ERROR getCuacctDataByUnique() resultDataCuacct is null \n");
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
