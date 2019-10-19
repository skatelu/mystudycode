package com.szkingdom.dao;
import com.szkingdom.data.DataEtfCashcomponent;

/**
 * Created by liujin on 2018/9/29.
 */
public class DaoEtfCashcomponent extends DaoBase {
    public DataEtfCashcomponent selectUnique (DataEtfCashcomponent dataEtfCashcomponent)throws Exception{
        return (DataEtfCashcomponent)selectOne("ETF_ETFCASHCOMPONENT_SELECT", dataEtfCashcomponent);
    }
    public int update(DataEtfCashcomponent dataEtfCashcomponent)throws Exception{
        return update("ETF_ETFCASHCOMPONENT_UPDATE", dataEtfCashcomponent);
    }

}
