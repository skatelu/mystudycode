/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRiskFactorSources
 * Author:   yinhl
 * Date:     2018-09-06
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.szkingdom.data.DataRiskFactorSources;
public class DaoRiskFactorSources extends DaoBase {

    public DataRiskFactorSources selectUnique (DataRiskFactorSources dataRiskFactorSources)throws Exception{
        return null;
    }

    public DataRiskFactorSources selectLock (DataRiskFactorSources dataRiskFactorSources , SqlSession session)throws Exception{
        return null;
    }

    public List<DataRiskFactorSources> selectList (DataRiskFactorSources dataRiskFactorSources)throws Exception{
        return null;
    }

    public int update(DataRiskFactorSources dataRiskFactorSources)throws Exception{
        return 0;
    }

    public int update(DataRiskFactorSources dataRiskFactorSources , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataRiskFactorSources dataRiskFactorSources)throws Exception{
        return 0;
    }

    public int insert(DataRiskFactorSources dataRiskFactorSources , SqlSession session)throws Exception{
        return 0;
    }

}