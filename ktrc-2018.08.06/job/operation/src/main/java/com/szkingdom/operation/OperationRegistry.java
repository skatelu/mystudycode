/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationRegistry
 * Author:   ZhangChangHong
 * Date:     2018/8/10
 * Description: 系统注册表操作表
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.dao.DaoRegistry;
import com.szkingdom.data.DataRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationRegistry extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationRegistry.class);

    /**
     * 按唯一索引查询系统注册表信息 zhangch
     * @param condDataRegistry
     * @throws Exception
     */
    public static DataRegistry getRegistryDataByUnique(DataRegistry condDataRegistry) throws Exception {
        DaoRegistry daoRegistry = new DaoRegistry();
        try {
            DataRegistry resultDataRegistry = daoRegistry.selectUnique(condDataRegistry);
            if (resultDataRegistry != null) {
                return resultDataRegistry;

            } else {
                logger.error("ERROR getRegistryDataByUnique() resultDataRegistry is null \n");
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
