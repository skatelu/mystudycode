/**  
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName:  OperationOptCustPosiLmt.java    
 * Author: yinhl     
 * Date:   2018年7月24日  
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 *
 */
package com.szkingdom.operation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.InitCache;
import com.szkingdom.dao.DaoOptCustPosiLmt;
import com.szkingdom.data.DataOptCustPosiLmt;

/**
 * 按照客户持仓限额
 * 
 * @author yinhl
 *
 */
public class OperationOptCustPosiLmt extends OperationBase {

	private static Logger logger = LoggerFactory.getLogger(OperationOptCustPosiLmt.class);

	/**
	 * 按照客户持仓限额（OPT_CUST_POSI_LMT）
	 * 
	 * @param dataOptCustPosiLmt
	 * @return
	 * @throws Exception
	 */
	public static DataOptCustPosiLmt getOptCustPosiLmtDataWithCache(DataOptCustPosiLmt dataOptCustPosiLmt) throws Exception {
		// 根据唯一索引组串key值。注：组串时TABLE常量名要追加下划线
		String keySearch = ConstantUtil.TABLE_OPT_CUST_POSI_LMT + "_";
		keySearch += dataOptCustPosiLmt.getStkbd();
		keySearch += dataOptCustPosiLmt.getCustCode();
		keySearch += dataOptCustPosiLmt.getTrdacct();
		keySearch += dataOptCustPosiLmt.getOptUndlCls();
		keySearch += dataOptCustPosiLmt.getOptUndlCode();
		keySearch += dataOptCustPosiLmt.getLsFlag();
		keySearch += dataOptCustPosiLmt.getCustLmtAttr();

		// 根据Key从MAP中获取对应数据结果
		DataOptCustPosiLmt mapDataOptCustPosiLmt = (DataOptCustPosiLmt) InitCache.read(keySearch);

		if (mapDataOptCustPosiLmt != null && mapDataOptCustPosiLmt.getOptPosiLqty() > 0) {
			return mapDataOptCustPosiLmt;
		}

		// MAP查无数据，查询客户限额OptCustPosiLmt表。查询参数对象与入参一致
		DaoOptCustPosiLmt daoOptCustPosiLmt = new DaoOptCustPosiLmt();
		try {
			DataOptCustPosiLmt resultDataOptCustPosiLmt = getOptCustPosiLmtDataByUnique(dataOptCustPosiLmt);

			// 对持仓限额数据执行回写MAP与返回
			if (resultDataOptCustPosiLmt != null) {
				InitCache.write(keySearch, resultDataOptCustPosiLmt);
				return resultDataOptCustPosiLmt;
			}
		} catch (Exception e) {
			throw new Exception("查表OPT_CUST_POSI_LMT获取客户级持仓限额异常", e);
		}

		return null;
	}

	public static DataOptCustPosiLmt getOptCustPosiLmtDataByUnique(DataOptCustPosiLmt condDataOptCustPosiLmt) throws Exception {
		DaoOptCustPosiLmt daoOptCustPosiLmt = new DaoOptCustPosiLmt();
		try {
			DataOptCustPosiLmt resultDataOptCustPosiLmt = daoOptCustPosiLmt.selectUnique(condDataOptCustPosiLmt);
			if (resultDataOptCustPosiLmt != null) {
				return resultDataOptCustPosiLmt;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			throw new Exception("查表OPT_CUST_POSI_LMT获取客户级持仓限额异常", e);
		}
	}
}