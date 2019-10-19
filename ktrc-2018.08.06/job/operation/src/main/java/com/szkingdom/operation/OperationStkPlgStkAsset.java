/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkPlgStkAsset
 * Author:   ZhangMaohua
 * Date:     2018/8/21
 * Description:
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.dao.DaoStkPlgStkAsset;
import com.szkingdom.data.DataStkPlgStkAsset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationStkPlgStkAsset extends OperationBase{
    protected static Logger logger = LoggerFactory.getLogger(OperationStkPlgUndlInfo.class);

    public static DataStkPlgStkAsset getDataStkPlgStkAssetByUnique(DataStkPlgStkAsset dataStkPlgStkAsset)throws Exception{
        DaoStkPlgStkAsset daoStkPlgStkAsset = new DaoStkPlgStkAsset();
        try{
            DataStkPlgStkAsset dataStkPlgStkAssetResult = daoStkPlgStkAsset.selectUnique(dataStkPlgStkAsset);
            if(dataStkPlgStkAssetResult != null){
                return dataStkPlgStkAssetResult;
            }
            else{
                logger.error("select StkPlgStkAsset return null error ! ");
                return null;
            }
        } catch(Exception e){
            throw new Exception("按唯一索引查数据 StkPlgStkAsset 异常 ! ", e);
        }
    }

    /**
     * 获取单一A股股票质押数量
     * @param dataStkPlgStkAsset
     * @return
     */
    public static long getStkAvlByDataStkPlgStkAsset(DataStkPlgStkAsset dataStkPlgStkAsset)throws Exception{
        DaoStkPlgStkAsset daoStkPlgStkAsset = new DaoStkPlgStkAsset();
        long stkAvl = 0L;
        try{
            List<DataStkPlgStkAsset> dataStkPlgStkAssetList = daoStkPlgStkAsset.selectList(dataStkPlgStkAsset);
            if(dataStkPlgStkAssetList != null && dataStkPlgStkAssetList.size() > 0){
                for(DataStkPlgStkAsset dataStkPlgStkAssetEach : dataStkPlgStkAssetList){
                    stkAvl += dataStkPlgStkAssetEach.getStkAvl();
                }
                return stkAvl;
            }
            else{
                logger.error("select StkPlgStkAsset return null error ! ");
                return 0L;
            }
        }catch (Exception e){
            throw new Exception("查询多条数据 StkPlgStkAsset 异常 ! ", e);
        }
    }

    /**
     * 唯一索引不设置STK_CODE，可查多条
     * @param condDataStkPlgStkAsset
     * @return
     * @throws Exception
     */
    public static List<DataStkPlgStkAsset> getDataStkPlgStkAssetList(DataStkPlgStkAsset condDataStkPlgStkAsset)throws Exception{
        DaoStkPlgStkAsset daoStkPlgStkAsset = new DaoStkPlgStkAsset();
        try{
            List<DataStkPlgStkAsset> resultDataStkPlgStkAssetList = daoStkPlgStkAsset.selectList(condDataStkPlgStkAsset);
            if(resultDataStkPlgStkAssetList != null && resultDataStkPlgStkAssetList.size() > 0){
                return resultDataStkPlgStkAssetList;
            }
            else{
                logger.error("查表 StkPlgStkAsset 返回 null error ! ");
                return null;
            }
        } catch(Exception e){
            throw new Exception("查询多条数据 StkPlgStkAsset 异常 ! ", e);
        }
    }
}
