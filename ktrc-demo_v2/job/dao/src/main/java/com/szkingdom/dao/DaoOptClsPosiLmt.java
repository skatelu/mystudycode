/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: DaoOptClsPosiLmt
 * Author:   zmh
 * Date:     2018/7/9 16:09
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szkingdom.dao;

import com.szkingdom.data.DataOptClsPosiLmt;
import com.szkingdom.data.DataOptPosiLmt;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * 〈〉
 * @author zmh
 * @create 2018/7/9
 * @since 1.0.0
 */
public class DaoOptClsPosiLmt extends DaoBase{

    public List<DataOptClsPosiLmt> selectOptClsPosiLmtList() throws Exception {
        return (List<DataOptClsPosiLmt>)selectList("COMMON_OPT_CLS_POSI_LMT_SELECT", null);
    }

    public List<DataOptClsPosiLmt> selectOptClsPosiLmtList(DataOptClsPosiLmt dataOptClsPosiLmt) throws Exception {
        return (List<DataOptClsPosiLmt>)selectList("COMMON_OPT_CLS_POSI_LMT_SELECT", dataOptClsPosiLmt);
    }


}
