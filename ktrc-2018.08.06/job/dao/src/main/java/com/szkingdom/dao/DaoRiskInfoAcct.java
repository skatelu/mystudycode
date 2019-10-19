/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRiskInfoAcct
 * Author:   yinhl
 * Date:     2018-08-16
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataRiskInfoAcct;
public class DaoRiskInfoAcct extends DaoBase {

    public DataRiskInfoAcct selectUnique (DataRiskInfoAcct dataRiskInfoAcct)throws Exception{
        return (DataRiskInfoAcct)selectOne("RISK_INFO_ACCT_SELECT",dataRiskInfoAcct);
    }

    public DataRiskInfoAcct selectLock (DataRiskInfoAcct dataRiskInfoAcct)throws Exception{
        return null;
    }

    public List<DataRiskInfoAcct> selectList (DataRiskInfoAcct dataRiskInfoAcct)throws Exception{
        return (List<DataRiskInfoAcct>)selectList("RISK_INFO_ACCT_SELECT",dataRiskInfoAcct);
    }

    public int update(DataRiskInfoAcct dataRiskInfoAcct)throws Exception{
        return update("RISK_INFO_ACCT_UPDATE",dataRiskInfoAcct);
    }

    public int insert(DataRiskInfoAcct dataRiskInfoAcct)throws Exception{
        return insert("RISK_INFO_ACCT_INSERT",dataRiskInfoAcct);
    }

}