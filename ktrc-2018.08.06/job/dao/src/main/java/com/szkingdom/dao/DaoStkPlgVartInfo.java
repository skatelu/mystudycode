/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkPlgVartInfo
 * Author:   yinhl
 * Date:     2018-08-20
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataStkPlgVartInfo;

public class DaoStkPlgVartInfo extends DaoBase {

	public DataStkPlgVartInfo selectUnique(DataStkPlgVartInfo dataStkPlgVartInfo) throws Exception {
		return (DataStkPlgVartInfo)selectOne("STK_PLG_VART_INFO_SELECT",dataStkPlgVartInfo);
	}

	public DataStkPlgVartInfo selectLock(DataStkPlgVartInfo dataStkPlgVartInfo) throws Exception {
		return null;
	}

	public List<DataStkPlgVartInfo> selectList(DataStkPlgVartInfo dataStkPlgVartInfo) throws Exception {
		return (List<DataStkPlgVartInfo>)selectList("STK_PLG_VART_INFO_SELECT",dataStkPlgVartInfo);
	}

	public int update(DataStkPlgVartInfo dataStkPlgVartInfo) throws Exception {
		return 0;
	}

	public int insert(DataStkPlgVartInfo dataStkPlgVartInfo) throws Exception {
		return 0;
	}

}