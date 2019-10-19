/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoAgrBuybkContracts
 * Author:   yinhl
 * Date:     2018-08-20
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;

import com.szkingdom.data.DataAgrBuybkContracts;

public class DaoAgrBuybkContracts extends DaoBase {

	public DataAgrBuybkContracts selectUnique(DataAgrBuybkContracts dataAgrBuybkContracts) throws Exception {
		return (DataAgrBuybkContracts) super.selectOne("AGR_BUYBK_CONTRACTS_SELECT", dataAgrBuybkContracts);
	}
	
	public DataAgrBuybkContracts selectTrdDate(DataAgrBuybkContracts dataAgrBuybkContracts) throws Exception {
		return (DataAgrBuybkContracts) super.selectOne("AGR_BUYBK_CONTRACTS_TRD_DATE_SELECT", dataAgrBuybkContracts);
	}
	
	public DataAgrBuybkContracts selectBuybkAmt(DataAgrBuybkContracts dataAgrBuybkContracts) throws Exception {
		return (DataAgrBuybkContracts) super.selectOne("AGR_BUYBK_CONTRACTS_BUYBK_AMT_SELECT", dataAgrBuybkContracts);
	}
	
	public DataAgrBuybkContracts selectBuybkQty(DataAgrBuybkContracts dataAgrBuybkContracts) throws Exception {
		return (DataAgrBuybkContracts) super.selectOne("AGR_BUYBK_CONTRACTS_BUYBK_QTY_SELECT", dataAgrBuybkContracts);
	}

	public DataAgrBuybkContracts selectLock(DataAgrBuybkContracts dataAgrBuybkContracts) throws Exception {
		return null;
	}

	public List<DataAgrBuybkContracts> selectList(DataAgrBuybkContracts dataAgrBuybkContracts) throws Exception {
		return null;
	}

	public int update(DataAgrBuybkContracts dataAgrBuybkContracts) throws Exception {
		return 0;
	}

	public int insert(DataAgrBuybkContracts dataAgrBuybkContracts) throws Exception {
		return 0;
	}

}