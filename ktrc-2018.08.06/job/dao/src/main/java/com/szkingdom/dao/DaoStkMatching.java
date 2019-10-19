/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkMatching
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
import com.szkingdom.data.DataStkMatching;
public class DaoStkMatching extends DaoBase {

    public DataStkMatching selectUnique (DataStkMatching dataStkMatching)throws Exception{
        return (DataStkMatching)selectOne("STK_MATCHING_SELECT", dataStkMatching);
    }

    public DataStkMatching selectLock (DataStkMatching dataStkMatching , SqlSession session)throws Exception{
        return null;
    }

    public List<DataStkMatching> selectList (DataStkMatching dataStkMatching)throws Exception{
        return null;
    }

    public int update(DataStkMatching dataStkMatching)throws Exception{
        return update("STK_MATCHING_UPDATE", dataStkMatching);
    }

    public int update(DataStkMatching dataStkMatching , SqlSession session)throws Exception{
        return session.update("STK_MATCHING_UPDATE", dataStkMatching);
    }

    public int insert(DataStkMatching dataStkMatching)throws Exception{
        return insert("STK_MATCHING_INSERT", dataStkMatching);
    }

    public int insert(DataStkMatching dataStkMatching , SqlSession session)throws Exception{
        return session.insert("STK_MATCHING_INSERT", dataStkMatching);
    }

}