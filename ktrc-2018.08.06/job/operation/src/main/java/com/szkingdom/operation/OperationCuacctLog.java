/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationCuacctLog
 * Author:   ZhangChangHong
 * Date:     2018/7/25
 * Description: CuacctLog操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.dao.DaoCuacctLog;
import com.szkingdom.data.DataCuacctLog;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationCuacctLog extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationCuacctLog.class);

    /**
     * 组装资金变动数据
     */
    public static DataCuacctLog initCuacctLogData(OperationDataIdOptFund condOperationDataIdOptFund, List<Object> listObjectTableData) {
        DataCuacctLog dataCuacctLog = new DataCuacctLog();

        // 设置唯一索引
        dataCuacctLog.setSerialNo(condOperationDataIdOptFund.getSerialNo());
        dataCuacctLog.setUserCode(condOperationDataIdOptFund.getCustCode());
        dataCuacctLog.setSubsysSn(condOperationDataIdOptFund.getSubsysSn());

        dataCuacctLog.setOccurDate(condOperationDataIdOptFund.getOccDate());
        dataCuacctLog.setOccurTime(condOperationDataIdOptFund.getOccTime());
        dataCuacctLog.setIntOrg(condOperationDataIdOptFund.getIntOrg());
        dataCuacctLog.setCuacctCode(condOperationDataIdOptFund.getCuacctCode());
        dataCuacctLog.setCurrency(condOperationDataIdOptFund.getCurrency());
        dataCuacctLog.setExtOrg((short) 0);
        dataCuacctLog.setExtOrgType('0');
        dataCuacctLog.setBizCode(condOperationDataIdOptFund.getBizCode());
        dataCuacctLog.setBizAmt(condOperationDataIdOptFund.getFundBlnVary());
        dataCuacctLog.setFundBln(condOperationDataIdOptFund.getFundBlnVary());
        dataCuacctLog.setOpUser(condOperationDataIdOptFund.getOpUser());
        dataCuacctLog.setOpRole(condOperationDataIdOptFund.getOpRole());
        dataCuacctLog.setOpName(condOperationDataIdOptFund.getOpName());
        dataCuacctLog.setOpOrg(condOperationDataIdOptFund.getOpOrg());
        dataCuacctLog.setOpSite(condOperationDataIdOptFund.getOpSite());
        dataCuacctLog.setChannel(condOperationDataIdOptFund.getChannel());
        dataCuacctLog.setCancelFlag(condOperationDataIdOptFund.getFundChangeFlag());
        dataCuacctLog.setOriginalSn(condOperationDataIdOptFund.getOriginalSn());
        dataCuacctLog.setExtSerialNo(condOperationDataIdOptFund.getOpName());

        listObjectTableData.add(dataCuacctLog);
        return dataCuacctLog;
    }

    /**
     * 资金类去重数据写表，写缓存
     * @param condDataCuacctLog
     * @param stringBuilder
     * @throws Exception
     */
    public static void insertCuacctLogData2DB(DataCuacctLog condDataCuacctLog, StringBuilder stringBuilder, SqlSession sqlSession) throws Exception {
        DaoCuacctLog daoCuacctLog = new DaoCuacctLog();
        try {
            daoCuacctLog.insert(condDataCuacctLog, sqlSession);
            InitCache.write(stringBuilder.toString(), ConstantUtil.CACHE_VALUES);

        } catch (Exception e) {
            throw e;
        }
    }
}
