/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoCustMarginCfg
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
import com.szkingdom.data.DataCustMarginCfg;
public class DaoCustMarginCfg extends DaoBase {

    public DataCustMarginCfg selectUnique (DataCustMarginCfg dataCustMarginCfg)throws Exception{
        return null;
    }

    public DataCustMarginCfg selectLock (DataCustMarginCfg dataCustMarginCfg , SqlSession session)throws Exception{
        return null;
    }

    public List<DataCustMarginCfg> selectList (DataCustMarginCfg dataCustMarginCfg)throws Exception{
        return null;
    }

    public int update(DataCustMarginCfg dataCustMarginCfg)throws Exception{
        return 0;
    }

    public int update(DataCustMarginCfg dataCustMarginCfg , SqlSession session)throws Exception{
        return 0;
    }

    public int insert(DataCustMarginCfg dataCustMarginCfg)throws Exception{
        return 0;
    }

    public int insert(DataCustMarginCfg dataCustMarginCfg , SqlSession session)throws Exception{
        return 0;
    }

}