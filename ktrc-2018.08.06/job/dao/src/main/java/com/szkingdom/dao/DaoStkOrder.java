/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkOrder
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
import com.szkingdom.data.DataStkOrder;
public class DaoStkOrder extends DaoBase {

    public DataStkOrder selectUnique (DataStkOrder dataStkOrder)throws Exception{
        return (DataStkOrder)selectOne("STK_ORDER_SELECT", dataStkOrder);
    }

    public DataStkOrder selectLock (DataStkOrder dataStkOrder , SqlSession session)throws Exception{
        return null;
    }

    public List<DataStkOrder> selectList (DataStkOrder dataStkOrder)throws Exception{
        return null;
    }

    public int update(DataStkOrder dataStkOrder)throws Exception{
        return 0;
    }

    public int update(DataStkOrder dataStkOrder , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataStkOrder dataStkOrder)throws Exception{
        return insert("STK_ORDER_INSERT", dataStkOrder);
    }

    public int insert(DataStkOrder dataStkOrder , SqlSession session)throws Exception{
        return session.insert("STK_ORDER_INSERT", dataStkOrder);
    }

}