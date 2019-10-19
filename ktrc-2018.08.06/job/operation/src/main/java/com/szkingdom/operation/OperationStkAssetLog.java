/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkAssetLog
 * Author:   ZhangChangHong
 * Date:     2018/7/26
 * Description: STK_ASSET_LOG操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.common.Tools;
import com.szkingdom.dao.DaoStkAssetLog;
import com.szkingdom.data.DataStkAssetLog;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationStkAssetLog extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationStkAssetLog.class);

    /**
     * 组装合约变动数据
     */
    public static DataStkAssetLog initStkAssetLogData(OperationDataIdOptAsset condOperationDataIdOptAsset, List<Object> listObjectTableData) {
        DataStkAssetLog dataStkAssetLog = new DataStkAssetLog();

        // 设置唯一索引
        dataStkAssetLog.setSerialNo(condOperationDataIdOptAsset.getSerialNo());
        dataStkAssetLog.setCustCode(condOperationDataIdOptAsset.getCustCode());
        dataStkAssetLog.setSubsysSn(condOperationDataIdOptAsset.getSubsysSn());

        dataStkAssetLog.setCuacctCode(condOperationDataIdOptAsset.getCuacctCode());
        dataStkAssetLog.setIntOrg(condOperationDataIdOptAsset.getIntOrg());
        dataStkAssetLog.setStkbd(condOperationDataIdOptAsset.getStkbd());
        dataStkAssetLog.setTrdacct(condOperationDataIdOptAsset.getTrdacct());
        dataStkAssetLog.setStkpbu(condOperationDataIdOptAsset.getStkpbu());
        dataStkAssetLog.setReverseStatus('1');
        dataStkAssetLog.setOccurDate(condOperationDataIdOptAsset.getOccDate());
        dataStkAssetLog.setOccurTime(condOperationDataIdOptAsset.getOccTime());
        dataStkAssetLog.setCurrency('0');
        dataStkAssetLog.setStkCode(condOperationDataIdOptAsset.getOptNum());
        dataStkAssetLog.setBizCode(condOperationDataIdOptAsset.getBizCode());
        //dataStkAssetLog.setBizContext("");
        dataStkAssetLog.setBizSn(0);
        dataStkAssetLog.setStkAvl(condOperationDataIdOptAsset.getOptAvlVary());
        dataStkAssetLog.setStkBlnEffect(condOperationDataIdOptAsset.getOptBlnVary());
        dataStkAssetLog.setStkAvlEffect(condOperationDataIdOptAsset.getOptAvlVary());

        listObjectTableData.add(dataStkAssetLog);
        return dataStkAssetLog;
    }

    /**
     * 股份类去重数据写表，写缓存
     * @param condDataStkAssetLog
     * @param stringBuilder
     * @throws Exception
     */
    public static void insertStkAssetLogData2DB(DataStkAssetLog condDataStkAssetLog, StringBuilder stringBuilder, SqlSession sqlSession) throws Exception {
        DaoStkAssetLog daoStkAssetLog = new DaoStkAssetLog();
        try {
            daoStkAssetLog.insert(condDataStkAssetLog, sqlSession);
            InitCache.write(stringBuilder.toString(), ConstantUtil.CACHE_VALUES);

        } catch (Exception e) {
            throw e;
        }
    }
}
