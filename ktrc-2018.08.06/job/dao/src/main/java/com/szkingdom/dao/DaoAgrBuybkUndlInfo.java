/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoAgrBuybkUndlInfo
 * Author:   yinhl
 * Date:     2018-08-20
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataAgrBuybkUndlInfo;

public class DaoAgrBuybkUndlInfo extends DaoBase {

	public DataAgrBuybkUndlInfo selectUnique(DataAgrBuybkUndlInfo dataAgrBuybkUndlInfo) throws Exception {
		return null;
	}

	public DataAgrBuybkUndlInfo selectStkTotalAtm(DataAgrBuybkUndlInfo dataAgrBuybkUndlInfo) throws Exception {
		return (DataAgrBuybkUndlInfo) super.selectOne("AGR_BUYBK_UNDL_INFO_STK_TOTAL_ATM_SELECT", dataAgrBuybkUndlInfo);
	}
	
	public DataAgrBuybkUndlInfo selectLock(DataAgrBuybkUndlInfo dataAgrBuybkUndlInfo) throws Exception {
		return null;
	}

	public List<DataAgrBuybkUndlInfo> selectList(DataAgrBuybkUndlInfo dataAgrBuybkUndlInfo) throws Exception {
		return null;
	}

	public int update(DataAgrBuybkUndlInfo dataAgrBuybkUndlInfo) throws Exception {
		return 0;
	}

	public int insert(DataAgrBuybkUndlInfo dataAgrBuybkUndlInfo) throws Exception {
		return 0;
	}

}