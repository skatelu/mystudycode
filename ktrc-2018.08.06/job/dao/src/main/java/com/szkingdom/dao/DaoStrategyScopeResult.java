/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStrategyScopeResult
 * Author:   yinhl
 * Date:     2018-08-10
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.szkingdom.data.DataStrategyScopeResult;
public class DaoStrategyScopeResult extends DaoBase {

    public DataStrategyScopeResult selectUnique (DataStrategyScopeResult dataStrategyScopeResult)throws Exception{
        return (DataStrategyScopeResult)selectOne("STRATEGY_SCOPE_RESULT_SELECT", dataStrategyScopeResult);
    }

    public DataStrategyScopeResult selectLock (DataStrategyScopeResult dataStrategyScopeResult , SqlSession session)throws Exception{
        return null;
    }

    public List<DataStrategyScopeResult> selectList (DataStrategyScopeResult dataStrategyScopeResult)throws Exception{
        return null;
    }

    public int update(DataStrategyScopeResult dataStrategyScopeResult)throws Exception{
        return 0;
    }

    public int update(DataStrategyScopeResult dataStrategyScopeResult , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataStrategyScopeResult dataStrategyScopeResult)throws Exception{
        return 0;
    }

    public int insert(DataStrategyScopeResult dataStrategyScopeResult , SqlSession session)throws Exception{
        return 0;
    }
}