package com.szkingdom.dao;
import com.szkingdom.data.DataEccodesign;
import com.szkingdom.data.DataFundAsset;

/**
 * Created by liujin on 2018/9/29.
 */
public class DaoFundAsset extends DaoBase {
    public DataFundAsset selectUnique (DataFundAsset dataFundAsset)throws Exception{
        return (DataFundAsset)selectOne("ETF_FUNDASSET_SELECT", dataFundAsset);
    }
    public int update(DataFundAsset dataFundAsset)throws Exception{
        return update("ETF_FUNDASSET_UPDATE", dataFundAsset);
    }

}
