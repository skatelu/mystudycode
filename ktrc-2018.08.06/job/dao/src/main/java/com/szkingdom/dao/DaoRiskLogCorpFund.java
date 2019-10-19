/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRiskLogCorpFund
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
import com.szkingdom.data.DataRiskLogCorpFund;
public class DaoRiskLogCorpFund extends DaoBase {

    public DataRiskLogCorpFund selectUnique (DataRiskLogCorpFund dataRiskLogCorpFund)throws Exception{
        return null;
    }

    public DataRiskLogCorpFund selectLock (DataRiskLogCorpFund dataRiskLogCorpFund , SqlSession session)throws Exception{
        return null;
    }

    public List<DataRiskLogCorpFund> selectList (DataRiskLogCorpFund dataRiskLogCorpFund)throws Exception{
        return null;
    }

    public int update(DataRiskLogCorpFund dataRiskLogCorpFund)throws Exception{
        return 0;
    }

    public int update(DataRiskLogCorpFund dataRiskLogCorpFund , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataRiskLogCorpFund dataRiskLogCorpFund)throws Exception{
        return insert("RISK_LOG_CORP_FUND_INSERT", dataRiskLogCorpFund);
    }

    public int insert(DataRiskLogCorpFund dataRiskLogCorpFund , SqlSession session)throws Exception{
        return 0;
    }

}