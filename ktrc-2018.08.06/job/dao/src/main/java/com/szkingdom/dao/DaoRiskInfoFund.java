/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRiskInfoFund
 * Author:   yinhl
 * Date:     2018-08-16
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataRiskInfoFund;
public class DaoRiskInfoFund extends DaoBase {

    public DataRiskInfoFund selectUnique (DataRiskInfoFund dataRiskInfoFund)throws Exception{
        return (DataRiskInfoFund)selectOne("RISK_INFO_FUND_SELECT",dataRiskInfoFund);
    }

    public DataRiskInfoFund selectLock (DataRiskInfoFund dataRiskInfoFund)throws Exception{
        return null;
    }

    public List<DataRiskInfoFund> selectList (DataRiskInfoFund dataRiskInfoFund)throws Exception{
        return (List<DataRiskInfoFund>)selectList("RISK_INFO_FUND_SELECT",dataRiskInfoFund);
    }

    public int update(DataRiskInfoFund dataRiskInfoFund)throws Exception{
        return update("RISK_INFO_FUND_UPDATE",dataRiskInfoFund);
    }

    public int insert(DataRiskInfoFund dataRiskInfoFund)throws Exception{
        return insert("RISK_INFO_FUND_INSERT",dataRiskInfoFund);
    }

}