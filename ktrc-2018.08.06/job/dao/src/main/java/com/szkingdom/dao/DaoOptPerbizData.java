/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoOptPerbizData
 * Author:   yinhl
 * Date:     2018-08-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataOptPerbizData;
public class DaoOptPerbizData extends DaoBase {

    public DataOptPerbizData selectUnique (DataOptPerbizData dataOptPerbizData)throws Exception{
        return (DataOptPerbizData)selectOne("OPT_PERBIZ_DATA_SELECT", dataOptPerbizData);
    }

    public DataOptPerbizData selectLock (DataOptPerbizData dataOptPerbizData)throws Exception{
        return null;
    }

    public List<DataOptPerbizData> selectList (DataOptPerbizData dataOptPerbizData)throws Exception{
        return (List<DataOptPerbizData>)selectList("OPT_PERBIZ_DATA_SELECT_LIST", dataOptPerbizData);
    }

    public int update(DataOptPerbizData dataOptPerbizData)throws Exception{
        return update("OPT_PERBIZ_DATA_UPDATE", dataOptPerbizData);
    }

    public int insert(DataOptPerbizData dataOptPerbizData)throws Exception{
        return insert("OPT_PERBIZ_DATA_INSERT", dataOptPerbizData);
    }

}