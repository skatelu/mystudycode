/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoCuacctFund
 * Author:   yinhl
 * Date:     2018-07-26
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;
import java.util.List;
import com.szkingdom.data.DataCuacctFund;
import org.apache.ibatis.session.SqlSession;

public class DaoCuacctFund extends DaoBase {

    public DataCuacctFund selectUnique (DataCuacctFund dataCuacctFund)throws Exception{
        return (DataCuacctFund)selectOne("CUACCT_FUND_SELECT", dataCuacctFund);
    }

    public DataCuacctFund selectLock(DataCuacctFund dataCuacctFund, SqlSession sqlSession)throws Exception{
        return (DataCuacctFund)sqlSession.selectOne("CUACCT_FUND_SELECT_LOCK", dataCuacctFund);
    }

    public Long selectDataForCustTotalAmt(DataCuacctFund dataCuacctFund) throws Exception {
        return (Long)selectOne("CUACCT_FUND_SELECT_FOR_CUST_TOTAL_AMT", dataCuacctFund);
    }

    public List<DataCuacctFund> selectList (DataCuacctFund dataCuacctFund)throws Exception{
        return null;
    }

    public int update(DataCuacctFund dataCuacctFund)throws Exception{
        return update("CUACCT_FUND_UPDATE", dataCuacctFund);
    }

    public int updateLock(DataCuacctFund dataCuacctFund, SqlSession sqlSession)throws Exception{
        return sqlSession.update("CUACCT_FUND_UPDATE", dataCuacctFund);
    }

    public int insert(DataCuacctFund dataCuacctFund)throws Exception{
        return insert("CUACCT_FUND_INSERT", dataCuacctFund);
    }

    public int insertLock(DataCuacctFund dataCuacctFund, SqlSession sqlSession)throws Exception{
        return sqlSession.insert("CUACCT_FUND_INSERT", dataCuacctFund);
    }

}