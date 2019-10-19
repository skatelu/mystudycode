/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkAssetLog
 * Author:   yinhl
 * Date:     2018-07-25
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;

import com.szkingdom.data.DataStkAssetLog;
import org.apache.ibatis.session.SqlSession;

public class DaoStkAssetLog extends DaoBase {
	public DataStkAssetLog selectUnique(DataStkAssetLog dataStkAssetLog) throws Exception {
		return (DataStkAssetLog)selectOne("STK_ASSET_LOG_SELECT", dataStkAssetLog);
	}

	public DataStkAssetLog selectLock(DataStkAssetLog dataStkAssetLog) throws Exception {
		return null;
	}

	public List<DataStkAssetLog> selectList(DataStkAssetLog dataStkAssetLog) throws Exception {
		return (List<DataStkAssetLog>)selectList("STK_ASSET_LOG_SELECT", dataStkAssetLog);
	}

	public int update(DataStkAssetLog dataStkAssetLog) throws Exception {
		return 0;
	}

	public int insert(DataStkAssetLog dataStkAssetLog) throws Exception {
		return insert("STK_ASSET_LOG_INSERT", dataStkAssetLog);
	}

	public int insert(DataStkAssetLog dataStkAssetLog, SqlSession sqlSession) throws Exception {
		return sqlSession.insert("STK_ASSET_LOG_INSERT", dataStkAssetLog);
	}
}