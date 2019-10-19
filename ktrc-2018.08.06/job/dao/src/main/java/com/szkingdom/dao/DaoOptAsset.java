/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoOptAsset
 * Author:   yinhl
 * Date:     2018-07-24
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataOptAsset;
import org.apache.ibatis.session.SqlSession;

public class DaoOptAsset extends DaoBase {
	public DataOptAsset selectUnique(DataOptAsset dataOptAsset) throws Exception {
		return (DataOptAsset)selectOne("OPT_ASSET_SELECT", dataOptAsset);
	}

	public Long selectUniqueForCvd(DataOptAsset dataOptAsset) throws Exception {
		return (Long)selectOne("OPT_ASSET_SELECT_FOR_CVD", dataOptAsset);
	}

	public DataOptAsset selectLock(DataOptAsset dataOptAsset, SqlSession sqlSession) throws Exception {
		return (DataOptAsset)sqlSession.selectOne("OPT_ASSET_SELECT_LOCK", dataOptAsset);
	}

	public Long selectDataForCustPosiMktVal(DataOptAsset dataOptAsset) throws Exception {
		return (Long)selectOne("OPT_ASSET_SELECT_FOR_CUST_POSI_MKT_VAL", dataOptAsset);
	}

	public Long selectDataForCustOptShortAndCoveredMktVal(DataOptAsset dataOptAsset) throws Exception {
		return (Long)selectOne("OPT_ASSET_SELECT_FOR_CUST_OPT_SHORT_AND_COVERED_MKT_VAL", dataOptAsset);
	}

	public List<DataOptAsset> selectList(DataOptAsset dataOptAsset) throws Exception {
		return (List<DataOptAsset>)selectList("OPT_ASSET_SELECT", dataOptAsset);
	}

	public List<DataOptAsset> selectListHedgedForMaginRisk(DataOptAsset dataOptAsset) throws Exception {
		return (List<DataOptAsset>)selectList("OPT_ASSET_SELECT_HEDGED_FOR_MAGIN_RISK", dataOptAsset);
	}

	public List<DataOptAsset> selectListNotHedgedForMaginRisk(DataOptAsset dataOptAsset) throws Exception {
		return (List<DataOptAsset>)selectList("OPT_ASSET_SELECT_NOT_HEDGED_FOR_MAGIN_RISK", dataOptAsset);
	}

	public int update(DataOptAsset dataOptAsset) throws Exception {
		return update("OPT_ASSET_UPDATE", dataOptAsset);
	}

	public int update(DataOptAsset dataOptAsset, SqlSession sqlSession) throws Exception {
		return sqlSession.update("OPT_ASSET_UPDATE", dataOptAsset);
	}

	public int insert(DataOptAsset dataOptAsset) throws Exception {
		return insert("OPT_ASSET_INSERT", dataOptAsset);
	}

	public int insert(DataOptAsset dataOptAsset, SqlSession sqlSession) throws Exception {
		return sqlSession.insert("OPT_ASSET_INSERT", dataOptAsset);
	}
}