/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoIndicatorIdsCfg
 * Author:   yinhl
 * Date:     2018-09-20
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.szkingdom.data.DataIndicatorIdsCfg;
public class DaoIndicatorIdsCfg extends DaoBase {

    public DataIndicatorIdsCfg selectUnique (DataIndicatorIdsCfg dataIndicatorIdsCfg) throws Exception{
        return null;
    }

    public DataIndicatorIdsCfg selectLock (DataIndicatorIdsCfg dataIndicatorIdsCfg , SqlSession session) throws Exception{
        return null;
    }

    public List<DataIndicatorIdsCfg> selectList (DataIndicatorIdsCfg dataIndicatorIdsCfg) throws Exception{
        return (List<DataIndicatorIdsCfg>)selectList("INDICATOR_IDS_CFG_SELECT", dataIndicatorIdsCfg);
    }

    public int update(DataIndicatorIdsCfg dataIndicatorIdsCfg) throws Exception{
        return 0;
    }

    public int update(DataIndicatorIdsCfg dataIndicatorIdsCfg , SqlSession session) throws Exception{
        return 0;
    }

    public int insert(DataIndicatorIdsCfg dataIndicatorIdsCfg) throws Exception{
        return 0;
    }

    public int insert(DataIndicatorIdsCfg dataIndicatorIdsCfg , SqlSession session) throws Exception{
        return 0;
    }

}