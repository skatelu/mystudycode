/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoOptCustMarginCfg
 * Author:   yinhl
 * Date:     2018-08-10
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataOptCustMarginCfg;
import org.apache.ibatis.session.SqlSession;

public class DaoOptCustMarginCfg extends DaoBase {

    public DataOptCustMarginCfg selectUnique (DataOptCustMarginCfg dataOptCustMarginCfg)throws Exception{
        return (DataOptCustMarginCfg)selectOne("OPT_CUST_MARGIN_CFG_SELECT", dataOptCustMarginCfg);
    }

    public DataOptCustMarginCfg selectLock (DataOptCustMarginCfg dataOptCustMarginCfg , SqlSession session)throws Exception{
        return null;
    }

    public List<DataOptCustMarginCfg> selectList (DataOptCustMarginCfg dataOptCustMarginCfg)throws Exception{
        return null;
    }

    public int update(DataOptCustMarginCfg dataOptCustMarginCfg)throws Exception{
        return 0;
    }

    public int update(DataOptCustMarginCfg dataOptCustMarginCfg , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataOptCustMarginCfg dataOptCustMarginCfg)throws Exception{
        return 0;
    }

    public int insert(DataOptCustMarginCfg dataOptCustMarginCfg , SqlSession session)throws Exception{
        return 0;
    }
}