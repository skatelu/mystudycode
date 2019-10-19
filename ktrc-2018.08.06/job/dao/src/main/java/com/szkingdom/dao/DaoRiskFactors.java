/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRiskFactors
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.szkingdom.data.DataRiskFactors;
public class DaoRiskFactors extends DaoBase {

    public DataRiskFactors selectUnique (DataRiskFactors dataRiskFactors)throws Exception{
        return null;
    }

    public DataRiskFactors selectLock (DataRiskFactors dataRiskFactors , SqlSession session)throws Exception{
        return null;
    }

    public List<DataRiskFactors> selectList (DataRiskFactors dataRiskFactors)throws Exception{
        return null;
    }

    public int update(DataRiskFactors dataRiskFactors)throws Exception{
        return 0;
    }

    public int update(DataRiskFactors dataRiskFactors , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataRiskFactors dataRiskFactors)throws Exception{
        return 0;
    }

    public int insert(DataRiskFactors dataRiskFactors , SqlSession session)throws Exception{
        return 0;
    }

}