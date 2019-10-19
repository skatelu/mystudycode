/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkCalendar
 * Author:   yinhl
 * Date:     2018-08-15
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataStkCalendar;

public class DaoStkCalendar extends DaoBase {

    public DataStkCalendar selectUnique (DataStkCalendar dataStkCalendar)throws Exception{
        return null;
    }

    public DataStkCalendar selectLock (DataStkCalendar dataStkCalendar)throws Exception{
        return null;
    }

    public List<DataStkCalendar> selectList (DataStkCalendar dataStkCalendar)throws Exception{
        return null;
    }

    public List<DataStkCalendar> selectListNext (DataStkCalendar dataStkCalendar)throws Exception{
        return (List<DataStkCalendar> )selectList("STK_CALENDAR_NEXT_SELECT", dataStkCalendar);
    }

    public List<DataStkCalendar> selectListPre (DataStkCalendar dataStkCalendar)throws Exception{
        return (List<DataStkCalendar> )selectList("STK_CALENDAR_PRE_SELECT", dataStkCalendar);
    }

    public int update(DataStkCalendar dataStkCalendar)throws Exception{
        return 0;
    }

    public int insert(DataStkCalendar dataStkCalendar)throws Exception{
        return 0;
    }

}