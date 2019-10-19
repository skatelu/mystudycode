/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: DaoOptTotalPosiLmt
 * Author:   zmh
 * Date:     2018/7/10 15:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szkingdom.dao;



import com.szkingdom.data.DataOptTotalPosiLmt;

import java.util.List;

/**
 * 〈〉
 * @author zmh
 * @create 2018/7/10
 * @since 1.0.0
 */
public class DaoOptTotalPosiLmt extends DaoBase{

    public List<DataOptTotalPosiLmt> selectOptTotalPosiLmtList() throws Exception {
        return (List<DataOptTotalPosiLmt>)selectList("COMMON_OPT_TOTAL_POSI_LMT_SELECT", null);
    }

    public List<DataOptTotalPosiLmt> selectOptTotalPosiLmtList(DataOptTotalPosiLmt dataOptTotalPosiLmt) throws Exception {
        return (List<DataOptTotalPosiLmt>)selectList("COMMON_OPT_TOTAL_POSI_LMT_SELECT", dataOptTotalPosiLmt);
    }

}
