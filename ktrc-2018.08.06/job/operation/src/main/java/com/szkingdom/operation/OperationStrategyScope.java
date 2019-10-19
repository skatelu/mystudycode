/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStrategyScope
 * Author:   ZhangChangHong
 * Date:     2018/8/13
 * Description: 保证金策略取值范围操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.dao.DaoStrategyScope;
import com.szkingdom.data.DataStrategyScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationStrategyScope extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationStrategyScope.class);

    /**
     * 保证金策略取值范围数据收集
     * @param condDataStrategyScope
     * @return
     * @throws Exception
     */
    public static List<DataStrategyScope> listStrategyScopeDataByParam(DataStrategyScope condDataStrategyScope) throws Exception {
        DaoStrategyScope daoStrategyScope = new DaoStrategyScope();
        try {
            List<DataStrategyScope> resListDataStrategyScope = daoStrategyScope.selectList(condDataStrategyScope);
            if (resListDataStrategyScope != null && !resListDataStrategyScope.isEmpty()) {
                return resListDataStrategyScope;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
