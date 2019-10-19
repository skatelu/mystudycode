/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkRightPlan
 * Author:   yinhl
 * Date:     2018-08-03
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataStkRightPlan;
public class DaoStkRightPlan extends DaoBase {

    public DataStkRightPlan selectUnique (DataStkRightPlan dataStkRightPlan)throws Exception{
        return (DataStkRightPlan)selectOne("STK_RIGHT_PLAN_SELECT", dataStkRightPlan);
    }

    public DataStkRightPlan selectLock (DataStkRightPlan dataStkRightPlan)throws Exception{
        return null;
    }

    public List<DataStkRightPlan> selectList (DataStkRightPlan dataStkRightPlan)throws Exception{
        return null;
    }

    public int update(DataStkRightPlan dataStkRightPlan)throws Exception{
        return 0;
    }

    public int insert(DataStkRightPlan dataStkRightPlan)throws Exception{
        return 0;
    }

}