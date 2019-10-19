/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkPlgUndlInfo
 * Author:   ZhangMaohua
 * Date:     2018/8/21
 * Description:
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.dao.DaoStkPlgUndlInfo;
import com.szkingdom.data.DataStkPlgUndlInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OperationStkPlgUndlInfo extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationStkPlgUndlInfo.class);

    /**
     *
     * @param dataStkPlgUndlInfoPara
     * @return
     */
    public static DataStkPlgUndlInfo getDataStkPlgUndlInfoByUnique(DataStkPlgUndlInfo dataStkPlgUndlInfoPara) throws Exception{
        DaoStkPlgUndlInfo daoStkPlgUndlInfo = new DaoStkPlgUndlInfo();
        try{
            DataStkPlgUndlInfo dataStkPlgUndlInfoResult = daoStkPlgUndlInfo.selectUnique(dataStkPlgUndlInfoPara);
            if(dataStkPlgUndlInfoResult != null){
                return dataStkPlgUndlInfoResult;
            }
            else {
                logger.error("select StkPlgUndlInfo return null error ! ");
                return null;
            }
        } catch (Exception e){
            logger.error("select StkPlgUndlInfo throw Exception error ! ");
            throw e;
        }

    }
}
