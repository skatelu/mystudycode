/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoIndicatorThreshold
 * Author:   yinhl
 * Date:     2018-08-03
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataIndicatorThreshold;
public class DaoIndicatorThreshold extends DaoBase {

    public DataIndicatorThreshold selectUnique (DataIndicatorThreshold dataIndicatorThreshold)throws Exception{
        return (DataIndicatorThreshold)selectOne("INDICATOR_THRESHOLD_SELECT",dataIndicatorThreshold);
    }

    public DataIndicatorThreshold selectLock (DataIndicatorThreshold dataIndicatorThreshold)throws Exception{
        return null;
    }

    public List<DataIndicatorThreshold> selectList (DataIndicatorThreshold dataIndicatorThreshold)throws Exception{
        return null;
    }

    public int update(DataIndicatorThreshold dataIndicatorThreshold)throws Exception{
        return 0;
    }

    public int insert(DataIndicatorThreshold dataIndicatorThreshold)throws Exception{
        return 0;
    }

}