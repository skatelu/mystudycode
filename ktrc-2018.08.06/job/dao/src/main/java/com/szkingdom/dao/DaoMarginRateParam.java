/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoMarginRateParam
 * Author:   yinhl
 * Date:     2018-08-10
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataMarginRateParam;
public class DaoMarginRateParam extends DaoBase {

    public DataMarginRateParam selectUnique (DataMarginRateParam dataMarginRateParam)throws Exception{
        return (DataMarginRateParam)selectOne("MARGIN_RATE_PARAM_SELECT", dataMarginRateParam);
    }

    public DataMarginRateParam selectLock (DataMarginRateParam dataMarginRateParam)throws Exception{
        return null;
    }

    public List<DataMarginRateParam> selectList (DataMarginRateParam dataMarginRateParam)throws Exception{
        return null;
    }

    public int update(DataMarginRateParam dataMarginRateParam)throws Exception{
        return 0;
    }

    public int insert(DataMarginRateParam dataMarginRateParam)throws Exception{
        return 0;
    }

}