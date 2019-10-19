/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRiskFactorsCls
 * Author:   yinhl
 * Date:     2018-09-19
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.szkingdom.data.DataRiskFactorsCls;
public class DaoRiskFactorsCls extends DaoBase {

    public DataRiskFactorsCls selectUnique (DataRiskFactorsCls dataRiskFactorsCls)throws Exception{
        return null;
    }

    public DataRiskFactorsCls selectLock (DataRiskFactorsCls dataRiskFactorsCls , SqlSession session)throws Exception{
        return null;
    }

    public List<DataRiskFactorsCls> selectList (DataRiskFactorsCls dataRiskFactorsCls)throws Exception{
        return null;
    }

    public int update(DataRiskFactorsCls dataRiskFactorsCls)throws Exception{
        return 0;
    }

    public int update(DataRiskFactorsCls dataRiskFactorsCls , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataRiskFactorsCls dataRiskFactorsCls)throws Exception{
        return 0;
    }

    public int insert(DataRiskFactorsCls dataRiskFactorsCls , SqlSession session)throws Exception{
        return 0;
    }

}