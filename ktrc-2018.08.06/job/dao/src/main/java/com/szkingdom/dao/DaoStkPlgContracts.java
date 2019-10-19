/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkPlgContracts
 * Author:   yinhl
 * Date:     2018-08-20
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataStkPlgContracts;

public class DaoStkPlgContracts extends DaoBase {

	public DataStkPlgContracts selectUnique(DataStkPlgContracts dataStkPlgContracts) throws Exception {
		return (DataStkPlgContracts)selectOne("STK_PLG_CONTRACTS_SELECT",dataStkPlgContracts);
	}

	public DataStkPlgContracts selectLock(DataStkPlgContracts dataStkPlgContracts) throws Exception {
		return null;
	}

	public List<DataStkPlgContracts> selectList(DataStkPlgContracts dataStkPlgContracts) throws Exception {
		return (List<DataStkPlgContracts>)selectList("STK_PLG_CONTRACTS_SELECT_LIST",dataStkPlgContracts);
	}

	public int update(DataStkPlgContracts dataStkPlgContracts) throws Exception {
		return 0;
	}

	public int insert(DataStkPlgContracts dataStkPlgContracts) throws Exception {
		return 0;
	}

}