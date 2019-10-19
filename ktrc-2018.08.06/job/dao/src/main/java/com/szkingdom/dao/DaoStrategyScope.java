/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStrategyScope
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
import com.szkingdom.data.DataStrategyScope;
public class DaoStrategyScope extends DaoBase {

    public DataStrategyScope selectUnique (DataStrategyScope dataStrategyScope)throws Exception{
        return null;
    }

    public DataStrategyScope selectLock (DataStrategyScope dataStrategyScope , SqlSession session)throws Exception{
        return null;
    }

    public List<DataStrategyScope> selectList (DataStrategyScope dataStrategyScope)throws Exception{
        return (List<DataStrategyScope>)selectList("STRATEGY_SCOPE_SELECT", dataStrategyScope);
    }

    public int update(DataStrategyScope dataStrategyScope)throws Exception{
        return 0;
    }

    public int update(DataStrategyScope dataStrategyScope , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataStrategyScope dataStrategyScope)throws Exception{
        return 0;
    }

    public int insert(DataStrategyScope dataStrategyScope , SqlSession session)throws Exception{
        return 0;
    }
}