/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationIndicatorIdsCfg
 * Author:   ZhangChangHong
 * Date:     2018/9/20
 * Description: 指标标识配置表操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.dao.DaoIndicatorIdsCfg;
import com.szkingdom.data.DataIndicatorIdsCfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class OperationIndicatorIdsCfg extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationIndicatorIdsCfg.class);

    /**
     * 指标标识配置数据收集
     * @param condDataIndicatorIdsCfg
     * @return
     * @throws Exception
     */
    public static List<DataIndicatorIdsCfg> listIndicatorIdsCfgDataByParam(DataIndicatorIdsCfg condDataIndicatorIdsCfg) throws Exception {
        // 查缓存
        String keySearch = ConstantUtil.TABLE_INDICATOR_IDS_CFG + "_" + condDataIndicatorIdsCfg.getIndicatorId();
        List<DataIndicatorIdsCfg> resultListDataIndicatorIdsCfg = (List<DataIndicatorIdsCfg>)InitCache.read(keySearch);
        if (resultListDataIndicatorIdsCfg != null && !resultListDataIndicatorIdsCfg.isEmpty()) {
            return resultListDataIndicatorIdsCfg;
        }

        // 查DB
        DaoIndicatorIdsCfg daoIndicatorIdsCfg = new DaoIndicatorIdsCfg();
        try {
            List<DataIndicatorIdsCfg> resListDataIndicatorIdsCfg = daoIndicatorIdsCfg.selectList(condDataIndicatorIdsCfg);
            if (resListDataIndicatorIdsCfg != null && !resListDataIndicatorIdsCfg.isEmpty()) {
                InitCache.write(keySearch, resListDataIndicatorIdsCfg);
                return resListDataIndicatorIdsCfg;

            } else {
                logger.error("listIndicatorIdsCfgDataByParam() 指标标识配置收集查无数据, IndicatorId:{}, IndicatorIdType():{}",
                        condDataIndicatorIdsCfg.getIndicatorId(), condDataIndicatorIdsCfg.getIndicatorIdType());
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
