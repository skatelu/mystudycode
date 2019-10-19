/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoStkPlgStkAsset
 * Author:   yinhl
 * Date:     2018-08-20
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import java.util.List;
import com.szkingdom.data.DataStkPlgStkAsset;

public class DaoStkPlgStkAsset extends DaoBase {

	public DataStkPlgStkAsset selectUnique(DataStkPlgStkAsset dataStkPlgStkAsset) throws Exception {
		return (DataStkPlgStkAsset)selectOne("STK_PLG_STK_ASSET_SELECT",dataStkPlgStkAsset);
	}

	public DataStkPlgStkAsset selectLock(DataStkPlgStkAsset dataStkPlgStkAsset) throws Exception {
		return null;
	}

	public List<DataStkPlgStkAsset> selectList(DataStkPlgStkAsset dataStkPlgStkAsset) throws Exception {
		return (List<DataStkPlgStkAsset>)selectList("STK_PLG_STK_ASSET_SELECT",dataStkPlgStkAsset);
	}

	public int update(DataStkPlgStkAsset dataStkPlgStkAsset) throws Exception {
		return 0;
	}

	public int insert(DataStkPlgStkAsset dataStkPlgStkAsset) throws Exception {
		return 0;
	}

}