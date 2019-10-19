package com.szkingdom.dao;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.szkingdom.data.DataEccodesign;

/**
 * Created by liujin on 2018/9/29.
 */
public class DaoEccodesign extends DaoBase {
    public DataEccodesign selectUnique (DataEccodesign dataEccodesign)throws Exception{
        return (DataEccodesign)selectOne("ETF_ECCODESIGN_SELECT", dataEccodesign);
    }
    public int update(DataEccodesign dataEccodesign)throws Exception{
        return update("ETF_ECCODESIGN_UPDATE", dataEccodesign);
    }

}
