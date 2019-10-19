/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkRepchContract
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
import com.szkingdom.data.DataStkRepchContract;
public class DaoStkRepchContract extends DaoBase {

    public DataStkRepchContract selectUnique (DataStkRepchContract dataStkRepchContract)throws Exception{
        return (DataStkRepchContract)selectOne("STK_REPCH_CONTRACT_SELECT", dataStkRepchContract);
    }

    public DataStkRepchContract selectLock (DataStkRepchContract dataStkRepchContract , SqlSession session)throws Exception{
        return (DataStkRepchContract)session.selectOne("STK_REPCH_CONTRACT_SELECT_LOCK", dataStkRepchContract);
    }

    public List<DataStkRepchContract> selectList (DataStkRepchContract dataStkRepchContract)throws Exception{
        return (List<DataStkRepchContract>)selectList("STK_REPCH_CONTRACT_SELECT", dataStkRepchContract);
    }

    public int update(DataStkRepchContract dataStkRepchContract)throws Exception{
        return update("STK_REPCH_CONTRACT_UPDATE", dataStkRepchContract);
    }

    public int update(DataStkRepchContract dataStkRepchContract , SqlSession session)throws Exception{
        return session.update("STK_REPCH_CONTRACT_UPDATE", dataStkRepchContract);
    }

    public int insert(DataStkRepchContract dataStkRepchContract)throws Exception{
        return insert("STK_REPCH_CONTRACT_INSERT", dataStkRepchContract);
    }

    public int insert(DataStkRepchContract dataStkRepchContract , SqlSession session)throws Exception{
        return session.insert("STK_REPCH_CONTRACT_INSERT", dataStkRepchContract);
    }

}