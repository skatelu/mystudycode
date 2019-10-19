/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkPlgUndlInfo
 * Author:   yinhl
 * Date:     2018-08-20
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataStkPlgUndlInfo;

public class DaoStkPlgUndlInfo extends DaoBase {

	public DataStkPlgUndlInfo selectUnique(DataStkPlgUndlInfo dataStkPlgUndlInfo) throws Exception {
		return (DataStkPlgUndlInfo)selectOne("STK_PLG_UNDL_INFO_SELECT",dataStkPlgUndlInfo);
	}

	public DataStkPlgUndlInfo selectLock(DataStkPlgUndlInfo dataStkPlgUndlInfo) throws Exception {
		return null;
	}

	public List<DataStkPlgUndlInfo> selectList(DataStkPlgUndlInfo dataStkPlgUndlInfo) throws Exception {
		return (List<DataStkPlgUndlInfo>)selectList("STK_PLG_UNDL_INFO_SELECT",dataStkPlgUndlInfo);
	}

	public int update(DataStkPlgUndlInfo dataStkPlgUndlInfo) throws Exception {
		return 0;
	}

	public int insert(DataStkPlgUndlInfo dataStkPlgUndlInfo) throws Exception {
		return 0;
	}

}