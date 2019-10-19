/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStrategyScopeResult
 * Author:   ZhangChangHong
 * Date:     2018/8/13
 * Description: 保证金策略取值结果操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.dao.DaoStrategyScopeResult;
import com.szkingdom.data.DataStrategyScopeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationStrategyScopeResult extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationStrategyScopeResult.class);

    /**
     * 按唯一索引查询保证金策略取值结果信息 zhangch
     * @param condDataStrategyScopeResult
     * @throws Exception
     */
    public static DataStrategyScopeResult getStrategyScopeResultDataByUnique(DataStrategyScopeResult condDataStrategyScopeResult) throws Exception {
        DaoStrategyScopeResult daoStrategyScopeResult = new DaoStrategyScopeResult();
        try {
            DataStrategyScopeResult resultDataStrategyScopeResult = daoStrategyScopeResult.selectUnique(condDataStrategyScopeResult);
            if (resultDataStrategyScopeResult != null) {
                return resultDataStrategyScopeResult;

            } else {
                logger.error("ERROR getStrategyScopeResultDataByUnique() resultDataStrategyScopeResult is null \n");
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
