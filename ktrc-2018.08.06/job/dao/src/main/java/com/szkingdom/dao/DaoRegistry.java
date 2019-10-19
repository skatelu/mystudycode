/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRegistry
 * Author:   yinhl
 * Date:     2018-08-10
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataRegistry;
public class DaoRegistry extends DaoBase {

    public DataRegistry selectUnique (DataRegistry dataRegistry)throws Exception{
        return (DataRegistry)selectOne("REGISTRY_SELECT", dataRegistry);
    }

    public DataRegistry selectLock (DataRegistry dataRegistry)throws Exception{
        return null;
    }

    public List<DataRegistry> selectList (DataRegistry dataRegistry)throws Exception{
        return null;
    }

    public int update(DataRegistry dataRegistry)throws Exception{
        return 0;
    }

    public int insert(DataRegistry dataRegistry)throws Exception{
        return 0;
    }

}