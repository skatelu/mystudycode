package com.szkingdom.storm.monitor;


import com.szkingdom.common.ConstantUtil;
import com.szkingdom.dao.DaoOptClsPosiLmt;
import com.szkingdom.dao.DaoOptCustPosiLmt;
import com.szkingdom.dao.DaoOptPosiLmt;
import com.szkingdom.data.DataOptClsPosiLmt;
import com.szkingdom.data.DataOptCustPosiLmt;
import com.szkingdom.data.DataOptPosiLmt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GetPosiLqty {
	//日志logger，用于输出错误信息。
	protected static Logger logger = LoggerFactory.getLogger(GetPosiLqty.class);

	// 获取客户级限额
	public static long getLqtyByOptCustPosiLmt(DataOptCustPosiLmt dataOptCustPosiLmt) throws Exception{
		long resPosiLqty = 0;

		//根据唯一索引组串key值。注：组串时TABLE常量名要追加下划线
		String keySearch = ConstantUtil.TABLE_OPT_CUST_POSI_LMT+"_";
		keySearch += dataOptCustPosiLmt.getStkbd();
		keySearch += dataOptCustPosiLmt.getCustCode();
		keySearch += dataOptCustPosiLmt.getTrdacct();
		keySearch += dataOptCustPosiLmt.getOptUndlCls();
		keySearch += dataOptCustPosiLmt.getOptUndlCode();
		keySearch += dataOptCustPosiLmt.getLsFlag();
		keySearch += dataOptCustPosiLmt.getCustLmtAttr();

		//根据Key从MAP中获取对应数据结果
		DataOptCustPosiLmt mapDataOptCustPosiLmt = (DataOptCustPosiLmt) InitialCache.getMapOptPosiLmtDataByKey(keySearch);

		//获取该索引下的持仓限额数据LQTY
		if (mapDataOptCustPosiLmt != null)
		{
			resPosiLqty = mapDataOptCustPosiLmt.getOptPosiLqty();
			if (resPosiLqty > 0){
				return resPosiLqty;
			}
			resPosiLqty = 0;
		}

		//MAP查无数据，查询客户限额OptCustPosiLmt表。查询参数对象与入参一致
		//创建dao对象，并使用入参对象作para调用数据库查询语句
		DaoOptCustPosiLmt daoOptCustPosiLmt = new DaoOptCustPosiLmt();
		try {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat f=new SimpleDateFormat("yyyyMMdd");
			String strDate = f.format(date);
			long currDate = Long.valueOf(strDate);
			List<DataOptCustPosiLmt> resList = daoOptCustPosiLmt.selectOptCustPosiLmtList(dataOptCustPosiLmt);
			for (DataOptCustPosiLmt resDataOptCustPosiLmt : resList)
			{
				if (currDate <= resDataOptCustPosiLmt.getPosiEndDate() && currDate >= resDataOptCustPosiLmt.getPosiBgnDate())
				{
					resPosiLqty = resDataOptCustPosiLmt.getOptPosiLqty();
				}
			}

			//对持仓限额数据执行回写MAP与返回
			if (resPosiLqty > 0){
				//查询有结果，则在此处先执行往MAP中回写
				InitialCache.add2MapOptPosiLmt(keySearch,dataOptCustPosiLmt);

				//返回额度结果
				return resPosiLqty;
			}
			resPosiLqty = 0;
		} catch (Exception e) {
			logger.error("Exception TABLE_OPT_CUST_POSI_LMT for LQTY ERROR [ " + e + "]\n");
			throw e;
		}

		return resPosiLqty;
	}

	// 获取标的级限额
	public static long getLqtyByOptPosiLmt(DataOptPosiLmt dataOptPosiLmt) throws Exception{
		long resPosiLqty = 0;

		//从MAP中获取分母，根据唯一索引组串key值
		String keySearch = ConstantUtil.TABLE_OPT_POSI_LMT+"_";
		keySearch += dataOptPosiLmt.getStkbd();
		keySearch += dataOptPosiLmt.getOptUndlCode();
		keySearch += dataOptPosiLmt.getLsFlag();
		keySearch += dataOptPosiLmt.getPosiLmtAttr();

		//根据Key从MAP中获取对应数据结果
		DataOptPosiLmt mapDataOptPosiLmt = (DataOptPosiLmt) InitialCache.getMapOptPosiLmtDataByKey(keySearch);

		if (mapDataOptPosiLmt != null)
		{
			resPosiLqty = mapDataOptPosiLmt.getOptPosiLqty();
			if (resPosiLqty > 0){
				return resPosiLqty;
			}
			resPosiLqty = 0;
		}

		//MAP查无数据，查询标的限额OptPosiLmt表。查询参数对象与入参一致
		//创建dao对象，并使用入参对象作para调用数据库查询语句
		DaoOptPosiLmt daoOptPosiLmt = new DaoOptPosiLmt();
		try {
			List<DataOptPosiLmt> resList = daoOptPosiLmt.selectOptPosiLmtList(dataOptPosiLmt);
			for (DataOptPosiLmt resDataOptPosiLmt : resList)
			{
				resPosiLqty = resDataOptPosiLmt.getOptPosiLqty();
			}

			//对持仓限额数据执行回写MAP与返回
			if (resPosiLqty > 0){
				//查询有结果，则在此处先执行往MAP中写
				InitialCache.add2MapOptPosiLmt(keySearch,dataOptPosiLmt);

				//返回额度结果
				return resPosiLqty;
			}
		} catch (Exception e) {
			logger.error("Exception TABLE_OPT_POSI_LMT for LQTY ERROR [ " + e + "]\n");
			throw e;
		}

		return resPosiLqty;
	}

	// 获取标的类别级限额
	public static long getLqtyByOptClsPosiLmt(DataOptClsPosiLmt dataOptClsPosiLmt) throws Exception{
		long resPosiLqty = 0;

		//从MAP中获取分母，根据唯一索引组串key值
		String keySearch = ConstantUtil.TABLE_OPT_CLS_POSI_LMT+"_";;
		keySearch += dataOptClsPosiLmt.getStkbd();
		keySearch += dataOptClsPosiLmt.getOptUndlCls();
		keySearch += dataOptClsPosiLmt.getLsFlag();
		keySearch += dataOptClsPosiLmt.getPosiLmtAttr();

		//根据Key从MAP中获取对应数据结果
		DataOptClsPosiLmt mapDataOptClsPosiLmt = (DataOptClsPosiLmt) InitialCache.getMapOptPosiLmtDataByKey(keySearch);

		if (mapDataOptClsPosiLmt != null)
		{
			resPosiLqty = mapDataOptClsPosiLmt.getOptPosiLqty();
			if (resPosiLqty > 0){
				return resPosiLqty;
			}
			resPosiLqty = 0;
		}

		//MAP查无数据，从数据库中查找
		//创建dao对象，并使用入参对象作para调用数据库查询语句
		DaoOptClsPosiLmt daoOptClsPosiLmt = new DaoOptClsPosiLmt();
		try {
			List<DataOptClsPosiLmt> resList = daoOptClsPosiLmt.selectOptClsPosiLmtList(dataOptClsPosiLmt);
			for (DataOptClsPosiLmt resDataOptClsPosiLmt : resList)
			{
				resPosiLqty = resDataOptClsPosiLmt.getOptPosiLqty();
			}

			//对持仓限额数据执行回写MAP与返回
			if (resPosiLqty > 0){
				//查询有结果，则在此处先执行往MAP中写
				InitialCache.add2MapOptPosiLmt(keySearch,dataOptClsPosiLmt);

				//返回额度结果
				return resPosiLqty;
			}
		} catch (Exception e) {
			logger.error("Exception TABLE_OPT_CLS_POSI_LMT for LQTY ERROR [ " + e + "]\n");
			throw e;
		}

		return resPosiLqty;
	}

	//业务接口，获取单客户单标的持仓总限额
	public static long getLqtyForSingleAcctSingleUndlTotalPosi(DataOptCustPosiLmt dataOptCustPosiLmt, DataOptPosiLmt dataOptPosiLmt, DataOptClsPosiLmt dataOptClsPosiLmt, char custType) throws Exception{
		long resPosiLqty = 0;

		if (custType == '0'){
			dataOptCustPosiLmt.setCustLmtAttr('A');
			resPosiLqty = getLqtyByOptCustPosiLmt(dataOptCustPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}

			dataOptPosiLmt.setPosiLmtAttr('A');
			resPosiLqty = getLqtyByOptPosiLmt(dataOptPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}

			dataOptClsPosiLmt.setPosiLmtAttr('A');
			resPosiLqty = getLqtyByOptClsPosiLmt(dataOptClsPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}
		}
		else if (custType == '1'){
			dataOptCustPosiLmt.setCustLmtAttr('C');
			resPosiLqty = getLqtyByOptCustPosiLmt(dataOptCustPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}

			dataOptPosiLmt.setPosiLmtAttr('C');
			resPosiLqty = getLqtyByOptPosiLmt(dataOptPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}

			dataOptClsPosiLmt.setPosiLmtAttr('C');
			resPosiLqty = getLqtyByOptClsPosiLmt(dataOptClsPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}
		}

		// 最后仍然没有结果，报错额度信息不存在
		if (resPosiLqty == 0){
			logger.error("持仓限额信息取值为空，请先设置额度信息\n");
			Exception exception = new Exception("持仓限额信息取值为空");
			throw exception;
		}

		return resPosiLqty;
	}

	//业务接口，获取单客户单标的权利仓限额
	public static long getLqtyForSingleAcctSingleUndlLongPosi(DataOptCustPosiLmt dataOptCustPosiLmt, DataOptPosiLmt dataOptPosiLmt, DataOptClsPosiLmt dataOptClsPosiLmt, char custType) throws Exception{
		long resPosiLqty = 0;

		if (custType == '0'){
			dataOptCustPosiLmt.setCustLmtAttr('B');
			resPosiLqty = getLqtyByOptCustPosiLmt(dataOptCustPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}

			dataOptPosiLmt.setPosiLmtAttr('B');
			resPosiLqty = getLqtyByOptPosiLmt(dataOptPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}

			dataOptClsPosiLmt.setPosiLmtAttr('B');
			resPosiLqty = getLqtyByOptClsPosiLmt(dataOptClsPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}
		}
		else if (custType == '1'){
			dataOptCustPosiLmt.setCustLmtAttr('D');
			resPosiLqty = getLqtyByOptCustPosiLmt(dataOptCustPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}

			dataOptPosiLmt.setPosiLmtAttr('D');
			resPosiLqty = getLqtyByOptPosiLmt(dataOptPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}

			dataOptClsPosiLmt.setPosiLmtAttr('D');
			resPosiLqty = getLqtyByOptClsPosiLmt(dataOptClsPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}
		}

		// 最后仍然没有结果，报错额度信息不存在
		if (resPosiLqty == 0){
			logger.error("持仓限额信息取值为空，请先设置额度信息\n");
			Exception exception = new Exception("持仓限额信息取值为空");
			throw exception;
		}

		return resPosiLqty;
	}

	//业务接口，获取单客户单标的单日累计限额
	public static long getLqtyForSingleAcctSingleUndlDailyPosi(DataOptCustPosiLmt dataOptCustPosiLmt, DataOptPosiLmt dataOptPosiLmt, DataOptClsPosiLmt dataOptClsPosiLmt, char custType) throws Exception{
		long resPosiLqty = 0;

		if (custType == '0'){
			dataOptCustPosiLmt.setCustLmtAttr('K');
			resPosiLqty = getLqtyByOptCustPosiLmt(dataOptCustPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}

			dataOptPosiLmt.setPosiLmtAttr('K');
			resPosiLqty = getLqtyByOptPosiLmt(dataOptPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}

			dataOptClsPosiLmt.setPosiLmtAttr('K');
			resPosiLqty = getLqtyByOptClsPosiLmt(dataOptClsPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}
		}
		else if (custType == '1'){
			dataOptCustPosiLmt.setCustLmtAttr('L');
			resPosiLqty = getLqtyByOptCustPosiLmt(dataOptCustPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}

			dataOptPosiLmt.setPosiLmtAttr('L');
			resPosiLqty = getLqtyByOptPosiLmt(dataOptPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}

			dataOptClsPosiLmt.setPosiLmtAttr('L');
			resPosiLqty = getLqtyByOptClsPosiLmt(dataOptClsPosiLmt);
			if (resPosiLqty > 0){
				return resPosiLqty;
			}
		}

		// 最后仍然没有结果，报错额度信息不存在
		if (resPosiLqty == 0){
			logger.error("持仓限额信息取值为空，请先设置额度信息\n");
			Exception exception = new Exception("持仓限额信息取值为空");
			throw exception;
		}

		return resPosiLqty;
	}
}
