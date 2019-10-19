/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationOptCustMarginCfg
 * Author:   ZhangChangHong
 * Date:     2018/8/10
 * Description: 期权客户保证金设置操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.dao.DaoOptCustMarginCfg;
import com.szkingdom.data.DataOptCustMarginCfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationOptCustMarginCfg extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationOptCustMarginCfg.class);

    /**
     * 按唯一索引查询期权客户保证金设置信息 zhangch
     * @param condDataOptCustMarginCfg
     * @throws Exception
     */
    public static DataOptCustMarginCfg getOptCustMarginCfgDataByUnique(DataOptCustMarginCfg condDataOptCustMarginCfg) throws Exception {
        DaoOptCustMarginCfg daoOptCustMarginCfg = new DaoOptCustMarginCfg();
        try {
            DataOptCustMarginCfg resultDataOptCustMarginCfg = daoOptCustMarginCfg.selectUnique(condDataOptCustMarginCfg);
            if (resultDataOptCustMarginCfg != null) {
                return resultDataOptCustMarginCfg;

            } else {
                logger.error("ERROR getOptCustMarginCfgDataByUnique() resultDataOptCustMarginCfg is null \n");
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
