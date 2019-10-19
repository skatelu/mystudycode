/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationStkCalendar
 * Author:   ZhengMingjie
 * Date:     2018/8/15
 * Description: 交易日历表STK_CALENDAR的操作方法类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.operation;

import com.szkingdom.dao.DaoStkCalendar;
import com.szkingdom.data.DataStkCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class OperationStkCalendar {
    private static Logger logger = LoggerFactory.getLogger(OperationStkCalendar.class);

    // 向后获取第N个交易日，入参必送板块信息stkbd、起始日期dateBegin、以及N的值diffDays
    public static int getNextTrdDate(String stkbd,int dateBegin,int diffDays) throws Exception {
        int dateResult = 0;

        // diffDays最小有效值为1，表示取包含当日的最近一个交易日
        if (diffDays <= 0) {
            diffDays = 1;
        }

        DaoStkCalendar daoStkCalendar = new DaoStkCalendar();
        DataStkCalendar dataStkCalendar = new DataStkCalendar();
        dataStkCalendar.setStkbd(stkbd);
        dataStkCalendar.setPhysicalDate(dateBegin);
        dataStkCalendar.setDiffDays(diffDays);

        try {
            List<DataStkCalendar> resultDataStkCalendar = daoStkCalendar.selectListNext(dataStkCalendar);
            if (resultDataStkCalendar != null && resultDataStkCalendar.size() > 0) {
                if (diffDays <= resultDataStkCalendar.size()) {
                    dateResult = resultDataStkCalendar.get(diffDays - 1).getPhysicalDate();
                }
                else {
                    logger.warn("查表获取交易日期的天数跨度入参超过查表返回条数，将返回最大允许范围内结果 \n");
                    dateResult = resultDataStkCalendar.get(resultDataStkCalendar.size() - 1).getPhysicalDate();
                }
            }
        } catch (Exception e) {
            throw new Exception("查表STK_CALENDAR获取NEXT_TRD_DATE异常", e);
        }
        return dateResult;
    }

    // 向前获取第N个交易日，入参必送板块信息stkbd、起始日期dateBegin、以及N的值diffDays
    public static int getPreTrdDate(String stkbd,int dateParam,int diffDays) throws Exception {
        int dateResult = 0;

        // diffDays最小有效值为1，表示取包含当日的最近一个交易日
        if (diffDays <= 0) {
            diffDays = 1;
        }

        DaoStkCalendar daoStkCalendar = new DaoStkCalendar();
        DataStkCalendar dataStkCalendar = new DataStkCalendar();
        dataStkCalendar.setStkbd(stkbd);
        dataStkCalendar.setPhysicalDate(dateParam);
        dataStkCalendar.setDiffDays(diffDays);

        try {
            List<DataStkCalendar> resultDataStkCalendar = daoStkCalendar.selectListPre(dataStkCalendar);
            if (resultDataStkCalendar != null && resultDataStkCalendar.size() > 0) {
                if (diffDays <= resultDataStkCalendar.size()) {
                    dateResult = resultDataStkCalendar.get(diffDays - 1).getPhysicalDate();
                }
                else {
                    logger.warn("查表获取交易日期的天数跨度入参超过查表返回条数，将返回最大允许范围内结果 \n");
                    dateResult = resultDataStkCalendar.get(resultDataStkCalendar.size() - 1).getPhysicalDate();
                }
            }
        } catch (Exception e) {
            throw new Exception("查表STK_CALENDAR获取PRE_TRD_DATE异常", e);
        }
        return dateResult;
    }

}
