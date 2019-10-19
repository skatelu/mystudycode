/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: DaoRiskInfoAsset
 * Author:   yinhl
 * Date:     2018-07-23
 * Description: 
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.dao;

import com.szkingdom.data.DataRiskInfoAsset;
import java.util.List;

public class DaoRiskInfoAsset extends DaoBase {

    public DataRiskInfoAsset selectUnique(DataRiskInfoAsset dataRiskInfoAsset) throws Exception {
        return (DataRiskInfoAsset)selectOne("RISK_INFO_ASSET_SELECT", dataRiskInfoAsset);
    }

    public DataRiskInfoAsset selectLock(DataRiskInfoAsset dataRiskInfoAsset) throws Exception {
        return null;
    }

    public List<DataRiskInfoAsset> selectList(DataRiskInfoAsset dataRiskInfoAsset) throws Exception {
        return (List<DataRiskInfoAsset>)selectList("RISK_INFO_ASSET_SELECT", dataRiskInfoAsset);
    }

    public int update(DataRiskInfoAsset dataRiskInfoAsset) throws Exception {
        return update("RISK_INFO_ASSET_UPDATE", dataRiskInfoAsset);
    }

    public int insert(DataRiskInfoAsset dataRiskInfoAsset) throws Exception {
        return insert("RISK_INFO_ASSET_INSERT", dataRiskInfoAsset);
    }

}