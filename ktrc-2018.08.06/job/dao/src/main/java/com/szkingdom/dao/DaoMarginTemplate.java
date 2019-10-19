/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoMarginTemplate
 * Author:   yinhl
 * Date:     2018-08-10
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataMarginTemplate;
import org.apache.ibatis.session.SqlSession;

public class DaoMarginTemplate extends DaoBase {

    public DataMarginTemplate selectUnique (DataMarginTemplate dataMarginTemplate)throws Exception{
        return null;
    }

    public DataMarginTemplate selectLock (DataMarginTemplate dataMarginTemplate , SqlSession session)throws Exception{
        return null;
    }

    public List<DataMarginTemplate> selectList (DataMarginTemplate dataMarginTemplate)throws Exception{
        return (List<DataMarginTemplate>)selectList("MARGIN_TEMPLATE_SELECT", dataMarginTemplate);
    }

    public List<DataMarginTemplate> selectListForMaginRisk(DataMarginTemplate dataMarginTemplate) throws Exception {
        return (List<DataMarginTemplate>)selectList("MARGIN_TEMPLATE_SELECT_FOR_MAGIN_RISK", dataMarginTemplate);
    }

    public int update(DataMarginTemplate dataMarginTemplate)throws Exception{
        return 0;
    }

    public int update(DataMarginTemplate dataMarginTemplate , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataMarginTemplate dataMarginTemplate)throws Exception{
        return 0;
    }

    public int insert(DataMarginTemplate dataMarginTemplate , SqlSession session)throws Exception{
        return 0;
    }
}