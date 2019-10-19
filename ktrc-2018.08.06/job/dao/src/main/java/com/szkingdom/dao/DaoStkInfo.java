/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkInfo
 * Author:   yinhl
 * Date:     2018-07-24
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataStkInfo;

public class DaoStkInfo extends DaoBase {
	public DataStkInfo selectUnique(DataStkInfo dataStkInfo) throws Exception {
		return (DataStkInfo)selectOne("STK_INFO_SELECT", dataStkInfo);
	}

	public DataStkInfo selectLock(DataStkInfo dataStkInfo) throws Exception {
		return null;
	}

	public List<DataStkInfo> selectList(DataStkInfo dataStkInfo) throws Exception {
		return null;
	}

	public int update(DataStkInfo dataStkInfo) throws Exception {
		return 0;
	}

	public int insert(DataStkInfo dataStkInfo) throws Exception {
		return 0;
	}
}