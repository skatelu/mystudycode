/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRiskInfoExeFund
 * Author:   yinhl
 * Date:     2018-08-16
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataRiskInfoExeFund;
public class DaoRiskInfoExeFund extends DaoBase {

    public DataRiskInfoExeFund selectUnique (DataRiskInfoExeFund dataRiskInfoExeFund)throws Exception{
        return (DataRiskInfoExeFund)selectOne("RISK_INFO_EXE_FUND_SELECT",dataRiskInfoExeFund);
    }

    public DataRiskInfoExeFund selectLock (DataRiskInfoExeFund dataRiskInfoExeFund)throws Exception{
        return null;
    }

    public List<DataRiskInfoExeFund> selectList (DataRiskInfoExeFund dataRiskInfoExeFund)throws Exception{
        return (List<DataRiskInfoExeFund>)selectList("RISK_INFO_EXE_FUND_SELECT",dataRiskInfoExeFund);
    }

    public int update(DataRiskInfoExeFund dataRiskInfoExeFund)throws Exception{
        return update("RISK_INFO_EXE_FUND_UPDATE",dataRiskInfoExeFund);
    }

    public int insert(DataRiskInfoExeFund dataRiskInfoExeFund)throws Exception{
        return insert("RISK_INFO_EXE_FUND_INSERT",dataRiskInfoExeFund);
    }

}