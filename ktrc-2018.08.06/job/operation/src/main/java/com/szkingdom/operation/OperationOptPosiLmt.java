/**  
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName:  OperationOptPosiLmt.java
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
import com.szkingdom.dao.DaoOptPosiLmt;
import com.szkingdom.data.DataOptPosiLmt;

/**
 * 标的证券持仓限额
 * 
 * @author yinhl
 *
 */
public class OperationOptPosiLmt extends OperationBase {

	private static Logger logger = LoggerFactory.getLogger(OperationOptPosiLmt.class);

	/**
	 * 标的证券持仓限额（OPT_POSI_LMT）
	 * 
	 * @param dataOptPosiLmt
	 * @return
	 * @throws Exception
	 */
	public static DataOptPosiLmt getOptPosiLmtDataWithCache(DataOptPosiLmt dataOptPosiLmt) throws Exception {
		// 根据唯一索引组串key值。注：组串时TABLE常量名要追加下划线
		String keySearch = ConstantUtil.TABLE_OPT_POSI_LMT + "_";
		keySearch += dataOptPosiLmt.getStkbd();
		keySearch += dataOptPosiLmt.getOptUndlCode();
		keySearch += dataOptPosiLmt.getLsFlag();
		keySearch += dataOptPosiLmt.getPosiLmtAttr();

		// 根据Key从MAP中获取对应数据结果
		DataOptPosiLmt mapDataOptPosiLmt = (DataOptPosiLmt) InitCache.read(keySearch);

		if (mapDataOptPosiLmt != null && mapDataOptPosiLmt.getOptPosiLqty() > 0) {
			return mapDataOptPosiLmt;
		}

		// MAP查无数据，查询DB
		DaoOptPosiLmt daoOptPosiLmt = new DaoOptPosiLmt();
		try {
			DataOptPosiLmt resultDataOptPosiLmt = daoOptPosiLmt.selectUnique(dataOptPosiLmt);
			if (resultDataOptPosiLmt != null) {
				InitCache.write(keySearch, dataOptPosiLmt);
				return resultDataOptPosiLmt;
			}
		} catch (Exception e) {
			throw new Exception("查表OPT_POSI_LMT获取标的级持仓限额异常", e);
		}

		return null;
	}
}