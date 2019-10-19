/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkImpawn
 * Author:   yinhl
 * Date:     2018-09-10
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.szkingdom.data.DataStkImpawn;
public class DaoStkImpawn extends DaoBase {

    public DataStkImpawn selectUnique (DataStkImpawn dataStkImpawn)throws Exception{
        return (DataStkImpawn)selectOne("STK_IMPAWN_SELECT", dataStkImpawn);
    }

    public DataStkImpawn selectLock (DataStkImpawn dataStkImpawn , SqlSession session)throws Exception{
        return (DataStkImpawn)session.selectOne("STK_IMPAWN_SELECT_LOCK", dataStkImpawn);
    }

    public List<DataStkImpawn> selectList (DataStkImpawn dataStkImpawn)throws Exception{
        return (List<DataStkImpawn>)selectList("STK_IMPAWN_SELECT", dataStkImpawn);
    }

    public int update(DataStkImpawn dataStkImpawn)throws Exception{
        return update("STK_IMPAWN_UPDATE", dataStkImpawn);
    }

    public int update(DataStkImpawn dataStkImpawn , SqlSession session)throws Exception{
        return session.update("STK_IMPAWN_UPDATE", dataStkImpawn);
    }

    public int insert(DataStkImpawn dataStkImpawn)throws Exception{
        return insert("STK_IMPAWN_INSERT", dataStkImpawn);
    }

    public int insert(DataStkImpawn dataStkImpawn , SqlSession session)throws Exception{
        return session.insert("STK_IMPAWN_INSERT", dataStkImpawn);
    }

}