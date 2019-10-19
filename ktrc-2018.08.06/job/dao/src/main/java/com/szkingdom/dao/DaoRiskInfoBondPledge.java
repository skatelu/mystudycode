/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRiskInfoBondPledge
 * Author:   yinhl
 * Date:     2018-09-21
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.szkingdom.data.DataRiskInfoBondPledge;
public class DaoRiskInfoBondPledge extends DaoBase {

    public DataRiskInfoBondPledge selectUnique (DataRiskInfoBondPledge dataRiskInfoBondPledge)throws Exception{
        return (DataRiskInfoBondPledge)selectOne("RISK_INFO_BOND_PLEDGE_SELECT",dataRiskInfoBondPledge);
    }

    public DataRiskInfoBondPledge selectLock (DataRiskInfoBondPledge dataRiskInfoBondPledge , SqlSession session)throws Exception{
        return null;
    }

    public List<DataRiskInfoBondPledge> selectList (DataRiskInfoBondPledge dataRiskInfoBondPledge)throws Exception{
        return (List<DataRiskInfoBondPledge>)selectList("RISK_INFO_BOND_PLEDGE_SELECT",dataRiskInfoBondPledge);
    }

    public int update(DataRiskInfoBondPledge dataRiskInfoBondPledge)throws Exception{
        return update("RISK_INFO_BOND_PLEDGE_UPDATE",dataRiskInfoBondPledge);
    }

    public int update(DataRiskInfoBondPledge dataRiskInfoBondPledge , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataRiskInfoBondPledge dataRiskInfoBondPledge)throws Exception{
        return insert("RISK_INFO_BOND_PLEDGE_INSERT",dataRiskInfoBondPledge);
    }

    public int insert(DataRiskInfoBondPledge dataRiskInfoBondPledge , SqlSession session)throws Exception{
        return 0;
    }

}