/**  
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName:  OperationOptClsPosiLmt.java    
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
import com.szkingdom.dao.DaoOptClsPosiLmt;
import com.szkingdom.data.DataOptClsPosiLmt;

/**
 * 标的类别持仓限额
 * 
 * @author yinhl
 *
 */
public class OperationOptClsPosiLmt extends OperationBase {

	private static Logger logger = LoggerFactory.getLogger(OperationOptClsPosiLmt.class);

	/**
	 * 标的类别持仓限额（OPT_CLS_POSI_LMT）
	 * 
	 * @param dataOptClsPosiLmt
	 * @return
	 * @throws Exception
	 */
	public static DataOptClsPosiLmt getOptPosiClsLmtDataWithCache(DataOptClsPosiLmt dataOptClsPosiLmt) throws Exception {
		// 根据唯一索引组串key值。注：组串时TABLE常量名要追加下划线
		String keySearch = ConstantUtil.TABLE_OPT_CLS_POSI_LMT + "_";
		keySearch += dataOptClsPosiLmt.getStkbd();
		keySearch += dataOptClsPosiLmt.getOptUndlCls();
		keySearch += dataOptClsPosiLmt.getLsFlag();
		keySearch += dataOptClsPosiLmt.getPosiLmtAttr();

		// 根据Key从MAP中获取对应数据结果
		DataOptClsPosiLmt mapDataOptClsPosiLmt = (DataOptClsPosiLmt) InitCache.read(keySearch);

		if (mapDataOptClsPosiLmt != null && mapDataOptClsPosiLmt.getOptPosiLqty() > 0) {
			return mapDataOptClsPosiLmt;
		}

		// MAP查无数据，查询DB
		DaoOptClsPosiLmt daoOptClsPosiLmt = new DaoOptClsPosiLmt();
		try {
			DataOptClsPosiLmt resultDataOptClsPosiLmt = daoOptClsPosiLmt.selectUnique(dataOptClsPosiLmt);
			if (resultDataOptClsPosiLmt != null) {
				InitCache.write(keySearch, dataOptClsPosiLmt);
				return resultDataOptClsPosiLmt;
			}
		} catch (Exception e) {
			throw new Exception("查表OPT_CLS_POSI_LMT获取证券类别级持仓限额异常", e);
		}

		return null;
	}
}