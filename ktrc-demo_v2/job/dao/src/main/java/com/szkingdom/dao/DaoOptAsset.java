package com.szkingdom.dao;

import com.szkingdom.data.DataOptAsset;
import com.szkingdom.data.DataRiskInfoAsset;

import java.util.List;

/**
 * @author zhangch
 * @date 2018/7/10 21:40
 */
public class DaoOptAsset extends DaoBase {
    public List<DataOptAsset> selectOptAssetList() throws Exception {
        return (List<DataOptAsset>)selectList("COMMON_OPT_ASSET_SELECT", null);
    }

    public List<DataOptAsset> selectOptAssetList(DataOptAsset dataOptAsset) throws Exception {
        return (List<DataOptAsset>)selectList("COMMON_OPT_ASSET_SELECT", dataOptAsset);
    }

    public int updateOptAsset(DataOptAsset dataOptAsset) throws Exception {
        return update("COMMON_OPT_ASSET_UPDATE", dataOptAsset);
    }

    public int insertOptAsset(DataOptAsset dataOptAsset) throws Exception {
        return insert("COMMON_OPT_ASSET_INSERT", dataOptAsset);
    }
}
