package com.szkingdom.dao;

import com.szkingdom.data.DataStkInfo;
import java.util.List;

/**
 * @author zhangch
 * @date 2018/7/7 15:26
 */
public class DaoStkInfo extends DaoBase {
    public List<DataStkInfo> selectRiskInfoAssetList() throws Exception {
        return (List<DataStkInfo>)selectList("COMMON_STK_INFO_SELECT", null);
    }

    public List<DataStkInfo> selectRiskInfoAssetList(DataStkInfo dataRiskInfoAsset) throws Exception {
        return (List<DataStkInfo>)selectList("COMMON_STK_INFO_SELECT", dataRiskInfoAsset);
    }
}
