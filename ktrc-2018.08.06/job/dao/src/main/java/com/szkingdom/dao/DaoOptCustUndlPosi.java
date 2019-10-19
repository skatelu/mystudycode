/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoOptCustUndlPosi
 * Author:   yinhl
 * Date:     2018-09-04
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.szkingdom.data.DataOptCustUndlPosi;

public class DaoOptCustUndlPosi extends DaoBase {

    public DataOptCustUndlPosi selectUnique (DataOptCustUndlPosi dataOptCustUndlPosi)throws Exception{
        return null;
    }

    public DataOptCustUndlPosi selectLock (DataOptCustUndlPosi dataOptCustUndlPosi , SqlSession sqlSession)throws Exception{
        return (DataOptCustUndlPosi)sqlSession.selectOne("OPT_CUST_UNDL_POSI_SELECT_LOCK", dataOptCustUndlPosi);
    }

    public List<DataOptCustUndlPosi> selectList (DataOptCustUndlPosi dataOptCustUndlPosi)throws Exception{
        return (List<DataOptCustUndlPosi>)selectList("OPT_CUST_UNDL_POSI_SELECT", dataOptCustUndlPosi);
    }

    public int update(DataOptCustUndlPosi dataOptCustUndlPosi)throws Exception{
        return 0;
    }

    public int update(DataOptCustUndlPosi dataOptCustUndlPosi , SqlSession sqlSession)throws Exception{
        return sqlSession.update("OPT_CUST_UNDL_POSI_UPDATE", dataOptCustUndlPosi);
    }

    public int insert(DataOptCustUndlPosi dataOptCustUndlPosi)throws Exception{
        return 0;
    }

    public int insert(DataOptCustUndlPosi dataOptCustUndlPosi , SqlSession sqlSession)throws Exception{
        return sqlSession.insert("OPT_CUST_UNDL_POSI_INSERT", dataOptCustUndlPosi);
    }

}