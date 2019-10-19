/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoOptInfo
 * Author:   yinhl
 * Date:     2018-08-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataOptInfo;
import org.apache.ibatis.session.SqlSession;

public class DaoOptInfo extends DaoBase {

    public DataOptInfo selectUnique (DataOptInfo dataOptInfo)throws Exception{
        return (DataOptInfo)selectOne("OPT_INFO_SELECT", dataOptInfo);
    }

    public DataOptInfo selectLock (DataOptInfo dataOptInfo , SqlSession session)throws Exception{
        return null;
    }

    public List<DataOptInfo> selectList (DataOptInfo dataOptInfo)throws Exception{
        return (List<DataOptInfo>)selectList("OPT_INFO_SELECT_LIST", dataOptInfo);
    }

    public int update(DataOptInfo dataOptInfo)throws Exception{
        return 0;
    }

    public int update(DataOptInfo dataOptInfo , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataOptInfo dataOptInfo)throws Exception{
        return 0;
    }

    public int insert(DataOptInfo dataOptInfo , SqlSession session)throws Exception{
        return 0;
    }
}