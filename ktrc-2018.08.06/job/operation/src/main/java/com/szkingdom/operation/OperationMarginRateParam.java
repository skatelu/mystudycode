/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationMarginRateParam
 * Author:   ZhangChangHong
 * Date:     2018/8/13
 * Description: 保证金比例参数操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.dao.DaoMarginRateParam;
import com.szkingdom.data.DataMarginRateParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationMarginRateParam extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationMarginRateParam.class);

    /**
     * 按唯一索引查询保证金比例参数信息 zhangch
     * @param condDataMarginRateParam
     * @throws Exception
     */
    public static DataMarginRateParam getMarginRateParamDataByUnique(DataMarginRateParam condDataMarginRateParam) throws Exception {
        DaoMarginRateParam daoMarginRateParam = new DaoMarginRateParam();
        try {
            DataMarginRateParam resultDataMarginRateParam = daoMarginRateParam.selectUnique(condDataMarginRateParam);
            if (resultDataMarginRateParam != null) {
                return resultDataMarginRateParam;

            } else {
                logger.error("ERROR getMarginRateParamDataByUnique() resultDataMarginRateParam is null \n");
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
