/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoCuacct
 * Author:   yinhl
 * Date:     2018-08-10
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataCuacct;
public class DaoCuacct extends DaoBase {

    public DataCuacct selectUnique (DataCuacct dataCuacct)throws Exception{
        return (DataCuacct)selectOne("CUACCT_SELECT", dataCuacct);
    }

    public DataCuacct selectLock (DataCuacct dataCuacct)throws Exception{
        return null;
    }

    public List<DataCuacct> selectList (DataCuacct dataCuacct)throws Exception{
        return null;
    }

    public int update(DataCuacct dataCuacct)throws Exception{
        return 0;
    }

    public int insert(DataCuacct dataCuacct)throws Exception{
        return 0;
    }

}