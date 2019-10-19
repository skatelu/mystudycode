/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkBondInfo
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
import com.szkingdom.data.DataStkBondInfo;
public class DaoStkBondInfo extends DaoBase {

    public DataStkBondInfo selectUnique (DataStkBondInfo dataStkBondInfo)throws Exception{
        return (DataStkBondInfo)selectOne("STK_BOND_INFO_SELECT",dataStkBondInfo);
    }

    public DataStkBondInfo selectLock (DataStkBondInfo dataStkBondInfo , SqlSession session)throws Exception{
        return null;
    }

    public List<DataStkBondInfo> selectList (DataStkBondInfo dataStkBondInfo)throws Exception{
        return null;
    }

    public int update(DataStkBondInfo dataStkBondInfo)throws Exception{
        return 0;
    }

    public int update(DataStkBondInfo dataStkBondInfo , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataStkBondInfo dataStkBondInfo)throws Exception{
        return 0;
    }

    public int insert(DataStkBondInfo dataStkBondInfo , SqlSession session)throws Exception{
        return 0;
    }

}