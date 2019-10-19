/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkAsset
 * Author:   yinhl
 * Date:     2018-08-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataStkAsset;
import org.apache.ibatis.session.SqlSession;

public class DaoStkAsset extends DaoBase {

    public DataStkAsset selectUnique (DataStkAsset dataStkAsset)throws Exception{
        return (DataStkAsset)selectOne("STK_ASSET_SELECT", dataStkAsset);
    }

    public DataStkAsset selectLock (DataStkAsset dataStkAsset, SqlSession sqlSession)throws Exception{
        return (DataStkAsset)sqlSession.selectOne("STK_ASSET_SELECT_LOCK",dataStkAsset);
    }

    public List<DataStkAsset> selectList (DataStkAsset dataStkAsset)throws Exception{
        return (List<DataStkAsset>)selectList("STK_ASSET_SELECT", dataStkAsset);
    }

    public int update(DataStkAsset dataStkAsset)throws Exception{
        return update("STK_ASSET_UPDATE", dataStkAsset);
    }

    public int update(DataStkAsset dataStkAsset, SqlSession sqlSession)throws Exception{
        return sqlSession.update("STK_ASSET_UPDATE", dataStkAsset);
    }

    public int insert(DataStkAsset dataStkAsset)throws Exception{
        return insert("STK_ASSET_INSERT", dataStkAsset);
    }

    public int insert(DataStkAsset dataStkAsset, SqlSession sqlSession)throws Exception{
        return sqlSession.insert("STK_ASSET_INSERT", dataStkAsset);
    }
}