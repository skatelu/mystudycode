/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoCustIndicatorThres
 * Author:   yinhl
 * Date:     2018-08-03
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataCustIndicatorThres;
public class DaoCustIndicatorThres extends DaoBase {

    public DataCustIndicatorThres selectUnique (DataCustIndicatorThres dataCustIndicatorThres)throws Exception{
        return (DataCustIndicatorThres)selectOne("CUST_INDICATOR_THRES_SELECT",dataCustIndicatorThres);
    }

    public DataCustIndicatorThres selectLock (DataCustIndicatorThres dataCustIndicatorThres)throws Exception{
        return null;
    }

    public List<DataCustIndicatorThres> selectList (DataCustIndicatorThres dataCustIndicatorThres)throws Exception{
        return null;
    }

    public int update(DataCustIndicatorThres dataCustIndicatorThres)throws Exception{
        return 0;
    }

    public int insert(DataCustIndicatorThres dataCustIndicatorThres)throws Exception{
        return 0;
    }

}