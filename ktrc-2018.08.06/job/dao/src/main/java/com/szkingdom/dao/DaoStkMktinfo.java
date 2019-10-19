/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkMktinfo
 * Author:   yinhl
 * Date:     2018-07-26
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataStkMktinfo;
public class DaoStkMktinfo extends DaoBase {

    public DataStkMktinfo selectUnique (DataStkMktinfo dataStkMktinfo)throws Exception{
        return (DataStkMktinfo)selectOne("STK_MKTINFO_SELECT", dataStkMktinfo);
    }

    public DataStkMktinfo selectLock (DataStkMktinfo dataStkMktinfo)throws Exception{
        return null;
    }

    public List<DataStkMktinfo> selectList (DataStkMktinfo dataStkMktinfo)throws Exception{
        return null;
    }

    public int update(DataStkMktinfo dataStkMktinfo)throws Exception{
        return update("STK_MKTINFO_UPDATE", dataStkMktinfo);
    }

    public int insert(DataStkMktinfo dataStkMktinfo)throws Exception{
        return insert("STK_MKTINFO_INSERT", dataStkMktinfo);
    }

}