/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRiskInfoCorpFund
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
import com.szkingdom.data.DataRiskInfoCorpFund;
public class DaoRiskInfoCorpFund extends DaoBase {

    public DataRiskInfoCorpFund selectUnique (DataRiskInfoCorpFund dataRiskInfoCorpFund)throws Exception{
        return (DataRiskInfoCorpFund)selectOne("RISK_INFO_CORP_FUND_SELECT", dataRiskInfoCorpFund);
    }

    public DataRiskInfoCorpFund selectLock (DataRiskInfoCorpFund dataRiskInfoCorpFund , SqlSession session)throws Exception{
        return null;
    }

    public List<DataRiskInfoCorpFund> selectList (DataRiskInfoCorpFund dataRiskInfoCorpFund)throws Exception{
        return null;
    }

    public int update(DataRiskInfoCorpFund dataRiskInfoCorpFund)throws Exception{
        return update("RISK_INFO_CORP_FUND_UPDATE", dataRiskInfoCorpFund);
    }

    public int update(DataRiskInfoCorpFund dataRiskInfoCorpFund , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataRiskInfoCorpFund dataRiskInfoCorpFund)throws Exception{
        return insert("RISK_INFO_CORP_FUND_INSERT", dataRiskInfoCorpFund);
    }

    public int insert(DataRiskInfoCorpFund dataRiskInfoCorpFund , SqlSession session)throws Exception{
        return 0;
    }

}