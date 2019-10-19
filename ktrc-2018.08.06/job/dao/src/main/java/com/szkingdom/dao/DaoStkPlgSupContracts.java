/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkPlgSupContracts
 * Author:   yinhl
 * Date:     2018-08-20
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataStkPlgSupContracts;

public class DaoStkPlgSupContracts extends DaoBase {

	public DataStkPlgSupContracts selectUnique(DataStkPlgSupContracts dataStkPlgSupContracts) throws Exception {
		return (DataStkPlgSupContracts)selectOne("STK_PLG_SUP_CONTRACTS_SELECT",dataStkPlgSupContracts);
	}

	public DataStkPlgSupContracts selectLock(DataStkPlgSupContracts dataStkPlgSupContracts) throws Exception {
		return null;
	}

	public List<DataStkPlgSupContracts> selectList(DataStkPlgSupContracts dataStkPlgSupContracts) throws Exception {
		return (List<DataStkPlgSupContracts>)selectList("STK_PLG_SUP_CONTRACTS_SELECT",dataStkPlgSupContracts);
	}

	public int update(DataStkPlgSupContracts dataStkPlgSupContracts) throws Exception {
		return 0;
	}

	public int insert(DataStkPlgSupContracts dataStkPlgSupContracts) throws Exception {
		return 0;
	}

}