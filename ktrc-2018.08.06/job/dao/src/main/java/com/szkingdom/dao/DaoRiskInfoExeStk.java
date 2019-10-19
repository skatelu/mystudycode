/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRiskInfoExeStk
 * Author:   yinhl
 * Date:     2018-08-16
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataRiskInfoExeStk;
public class DaoRiskInfoExeStk extends DaoBase {

    public DataRiskInfoExeStk selectUnique (DataRiskInfoExeStk dataRiskInfoExeStk)throws Exception{
        return (DataRiskInfoExeStk)selectOne("RISK_INFO_EXE_STK_SELECT",dataRiskInfoExeStk);
    }

    public DataRiskInfoExeStk selectLock (DataRiskInfoExeStk dataRiskInfoExeStk)throws Exception{
        return null;
    }

    public List<DataRiskInfoExeStk> selectList (DataRiskInfoExeStk dataRiskInfoExeStk)throws Exception{
        return (List<DataRiskInfoExeStk>)selectList("RISK_INFO_EXE_STK_SELECT",dataRiskInfoExeStk);
    }

    public int update(DataRiskInfoExeStk dataRiskInfoExeStk)throws Exception{
        return update("RISK_INFO_EXE_STK_UPDATE",dataRiskInfoExeStk);
    }

    public int insert(DataRiskInfoExeStk dataRiskInfoExeStk)throws Exception{
        return insert("RISK_INFO_EXE_STK_INSERT",dataRiskInfoExeStk);
    }

}