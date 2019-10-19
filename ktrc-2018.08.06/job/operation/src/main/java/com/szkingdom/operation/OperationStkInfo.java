/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkInfo
 * Author:   ZhengMingjie
 * Date:     2018/8/18
 * Description: 证券信息表STK_INFO操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.dao.DaoStkInfo;
import com.szkingdom.data.DataStkInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationStkInfo {
    protected static Logger logger = LoggerFactory.getLogger(OperationStkInfo.class);

    public static DataStkInfo getStkInfoDataByUnique(DataStkInfo condDataStkInfo) throws Exception {
        DaoStkInfo daoStkInfo = new DaoStkInfo();
        try {
            DataStkInfo resultDataStkInfo = daoStkInfo.selectUnique(condDataStkInfo);
            if (resultDataStkInfo != null) {
                return resultDataStkInfo;
            } else {
                logger.error("查表 STK_INFO 返回 NULL ! ");
                return null;
            }
        } catch (Exception e) {
            throw new Exception("查表 STK_INFO 抛出异常 !", e);
        }
    }

    // 查询标的等场景的部分表查询，需要对板块从期权的05、15转换为标的证券的A股板块
    public static String convertStkbdFromOptToUndl(String stkbdFromOpt) throws Exception {
        String stkbdResult = "";
        if (stkbdFromOpt.equals(ConstantUtil.STKBD_SZQQ)) {
            stkbdResult = ConstantUtil.STKBD_SZAG;
        } else if (stkbdFromOpt.equals(ConstantUtil.STKBD_SHQQ)) {
            stkbdResult = ConstantUtil.STKBD_SHAG;
        } else {
            stkbdResult = stkbdFromOpt;
        }

        return stkbdResult;
    }
}
