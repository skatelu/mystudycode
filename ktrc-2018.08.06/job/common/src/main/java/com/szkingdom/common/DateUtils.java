/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DateUtils
 * Author:   ZhengMingjie
 * Date:     2018/8/4
 * Description: 日期工具类方法封装
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    // 输出int型当日日期值，格式yyyyMMdd
    public static int getCurrDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMdd");

        return Integer.valueOf(DateFormat.format(date));
    }

    // 输出int型当日日期值，格式HHmmss
    public static int getCurrTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat DateFormat = new SimpleDateFormat("HHmmss");

        return Integer.valueOf(DateFormat.format(date));
    }


}
