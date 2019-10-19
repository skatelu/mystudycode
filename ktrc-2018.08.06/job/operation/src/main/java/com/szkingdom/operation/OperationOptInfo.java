/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationOptInfo
 * Author:   ZhengMingjie
 * Date:     2018/8/4
 * Description: OPT_INFO表操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.dao.DaoOptInfo;
import com.szkingdom.data.DataOptInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationOptInfo extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationOptInfo.class);

     /**
     * 获取单标的合约单位OPT_UNIT，同标的券合约单位相同
     * @param condDataOptInfo
     * @throws Exception
     */
    public static long getOptUnit(DataOptInfo condDataOptInfo) throws Exception {
        long optUnit = 0L;
        DaoOptInfo daoOptInfo = new DaoOptInfo();
        try {
            List<DataOptInfo> resultDataOptInfoList = daoOptInfo.selectList(condDataOptInfo);
            if (resultDataOptInfoList != null && resultDataOptInfoList.size() > 0) {
                DataOptInfo resultDataOptInfo = resultDataOptInfoList.get(0);
                optUnit = resultDataOptInfo.getOptUnit();
            }
            else {
                logger.warn("查表OPT_INFO，取合约单位无数据");
            }
        } catch (Exception e) {
            throw new Exception("查表OPT_INFO获取合约单位异常", e);
        }

        return optUnit;
    }

    /**
     * 按唯一索引查询期权基础信息 zhangch
     * @param condDataOptInfo
     * @throws Exception
     */
    public static DataOptInfo getOptInfoDataWithCache(DataOptInfo condDataOptInfo) throws Exception {
        try {
            String keySearch = ConstantUtil.TABLE_OPT_INFO + "_";
            keySearch += condDataOptInfo.getOptNum();
            keySearch += condDataOptInfo.getStkbd();

            // 查缓存
            DataOptInfo cacheDataOptInfo = (DataOptInfo) InitCache.read(keySearch);
            if (cacheDataOptInfo != null) {
                return cacheDataOptInfo;
            }

            // 缓存查无数据，查db
            DaoOptInfo daoOptInfo = new DaoOptInfo();
            DataOptInfo resultDataOptInfo = daoOptInfo.selectUnique(condDataOptInfo);
            if (resultDataOptInfo != null) {
                // cache
                InitCache.write(keySearch, resultDataOptInfo);
                return resultDataOptInfo;
            } else {
                logger.error("getOptInfoDataByUnique() 期权基础信息查无数据, OptName:{}, Stkbd:{}", condDataOptInfo.getOptName(), condDataOptInfo.getStkbd());
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
