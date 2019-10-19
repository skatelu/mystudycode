/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRiskIndicators
 * Author:   yinhl
 * Date:     2018-07-24
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.szkingdom.data.DataRiskIndicators;

public class DaoRiskIndicators extends DaoBase {
	public DataRiskIndicators selectUnique(DataRiskIndicators dataRiskIndicators) throws Exception {
		return (DataRiskIndicators)selectOne("RISK_INDICATORS_SELECT", dataRiskIndicators);
	}

    public DataRiskIndicators selectLock (DataRiskIndicators dataRiskIndicators , SqlSession session)throws Exception{
		return null;
	}

	public List<DataRiskIndicators> selectList(DataRiskIndicators dataRiskIndicators) throws Exception {
		return (List<DataRiskIndicators>)selectList("RISK_INDICATORS_SELECT", dataRiskIndicators);
	}

	public List<DataRiskIndicators> selectList(String keySearch) throws Exception {
		return (List<DataRiskIndicators>)selectList("RISK_INDICATORS_SELECT_LIST", keySearch);
	}

	public int update(DataRiskIndicators dataRiskIndicators) throws Exception {
		return 0;
	}

    public int update(DataRiskIndicators dataRiskIndicators , SqlSession session)throws Exception{
        return 0;
    }

	public int insert(DataRiskIndicators dataRiskIndicators) throws Exception {
		return 0;
	}
	
    public int insert(DataRiskIndicators dataRiskIndicators , SqlSession session)throws Exception{
        return 0;
    }

    public List<DataRiskIndicators> selectOptRiskIndicatorsList() throws Exception {
        return (List<DataRiskIndicators>)selectList("RISK_INDICATORS_SELECT", null);
    }
}