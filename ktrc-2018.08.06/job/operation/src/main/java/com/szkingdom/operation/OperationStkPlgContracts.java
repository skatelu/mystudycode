/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkPlgContracts
 * Author:   ZhangMaohua
 * Date:     2018/8/21
 * Description:
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.dao.DaoStkPlgContracts;
import com.szkingdom.data.DataStkPlgContracts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationStkPlgContracts extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationStkPlgContracts.class);

    public static DataStkPlgContracts getDataStkPlgContractsByUnique(DataStkPlgContracts condDataStkPlgContracts) throws Exception{
        DaoStkPlgContracts daoStkPlgContracts = new DaoStkPlgContracts();
        try{
            DataStkPlgContracts resultDataStkPlgContracts = daoStkPlgContracts.selectUnique(condDataStkPlgContracts);
            if(resultDataStkPlgContracts != null){
                return resultDataStkPlgContracts;
            } else {
                logger.error("查表 StkPlgContracts 返回 null error ! ");
                return null;
            }
        } catch(Exception e){
            throw new Exception("按唯一索引查表StkPlgContracts异常！", e);
        }
    }

    public static List<DataStkPlgContracts> getStkPlgContractsList(DataStkPlgContracts condDataStkPlgContracts)throws Exception{
        DaoStkPlgContracts daoStkPlgContracts = new DaoStkPlgContracts();
        try{
            List<DataStkPlgContracts> resultDataStkPlgContractsList = daoStkPlgContracts.selectList(condDataStkPlgContracts);
            if(resultDataStkPlgContractsList != null && resultDataStkPlgContractsList.size() > 0){
                return resultDataStkPlgContractsList;
            }
            else {
                logger.error("查表 StkPlgContracts 返回 null error ! ");
                return null;
            }
        }catch(Exception e){
            throw new Exception("查表StkPlgContracts异常！", e);
        }

    }
}
