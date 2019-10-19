/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRiskCorpFund
 * Author:   yinhl
 * Date:     2018-08-16
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataRiskCorpFund;
public class DaoRiskCorpFund extends DaoBase {

    public DataRiskCorpFund selectUnique (DataRiskCorpFund dataRiskCorpFund)throws Exception{
        return (DataRiskCorpFund)selectOne("RISK_CORP_FUND_SELECT", dataRiskCorpFund);
    }

    public DataRiskCorpFund selectLock (DataRiskCorpFund dataRiskCorpFund)throws Exception{
        return null;
    }

    public List<DataRiskCorpFund> selectList (DataRiskCorpFund dataRiskCorpFund)throws Exception{
        return null;
    }

    public int update(DataRiskCorpFund dataRiskCorpFund)throws Exception{
        return update("RISK_CORP_FUND_UPDATE", dataRiskCorpFund);
    }

    public int insert(DataRiskCorpFund dataRiskCorpFund)throws Exception{
        return insert("RISK_CORP_FUND_INSERT", dataRiskCorpFund);
    }

}