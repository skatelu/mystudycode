package com.szkingdom.dao;

import com.szkingdom.data.DataCustomer;
import com.szkingdom.data.DataOptAsset;
import com.szkingdom.data.DataRiskInfoAsset;

import java.util.List;

/**
 * @author zhangch
 * @date 2018/7/7 15:26
 */
public class DaoRiskInfoAsset extends DaoBase{

    public List<DataRiskInfoAsset> selectRiskInfoAssetList() throws Exception {
        return (List<DataRiskInfoAsset>)selectList("COMMON_RISK_INFO_ASSET_SELECT", null);
    }

    public List<DataRiskInfoAsset> selectRiskInfoAssetList(DataRiskInfoAsset dataRiskInfoAsset) throws Exception {
        return (List<DataRiskInfoAsset>)selectList("COMMON_RISK_INFO_ASSET_SELECT_BY_UNIQUE_INDEX", dataRiskInfoAsset);
    }

    public int updateRiskInfoAsset(DataRiskInfoAsset dataRiskInfoAsset) throws Exception {
        return update("COMMON_RISK_INFO_ASSET_UPDATE", dataRiskInfoAsset);
    }

    public int insertRiskInfoAsset(DataRiskInfoAsset dataRiskInfoAsset) throws Exception {
        return insert("COMMON_RISK_INFO_ASSET_INSERT", dataRiskInfoAsset);
    }
}
