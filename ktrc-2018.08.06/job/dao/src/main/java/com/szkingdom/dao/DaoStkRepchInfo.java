/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkRepchInfo
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
import com.szkingdom.data.DataStkRepchInfo;
public class DaoStkRepchInfo extends DaoBase {

    public DataStkRepchInfo selectUnique (DataStkRepchInfo dataStkRepchInfo)throws Exception{
        return null;
    }

    public DataStkRepchInfo selectLock (DataStkRepchInfo dataStkRepchInfo , SqlSession session)throws Exception{
        return null;
    }

    public List<DataStkRepchInfo> selectList (DataStkRepchInfo dataStkRepchInfo)throws Exception{
        return null;
    }

    public int update(DataStkRepchInfo dataStkRepchInfo)throws Exception{
        return 0;
    }

    public int update(DataStkRepchInfo dataStkRepchInfo , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataStkRepchInfo dataStkRepchInfo)throws Exception{
        return 0;
    }

    public int insert(DataStkRepchInfo dataStkRepchInfo , SqlSession session)throws Exception{
        return 0;
    }

}