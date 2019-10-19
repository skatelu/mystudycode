/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: DaoRiskIndicatorOverview
 * Author:   zmh
 * Date:     2018/7/17 19:15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szkingdom.dao;

import com.szkingdom.data.DataOptAsset;
import com.szkingdom.data.DataRiskIndicatorOverview;

import java.util.List;

/**
 * 〈〉
 * @author zmh
 * @create 2018/7/17
 * @since 1.0.0
 */
public class DaoRiskIndicatorOverview extends DaoBase {
    public List<DataRiskIndicatorOverview> selectOptRiskIndicatorOverviewList() throws Exception {
        return (List<DataRiskIndicatorOverview>)selectList("COMMON_RISK_INDICATOR_OVERVIEW_SELECT", null);
    }

    public List<DataRiskIndicatorOverview> selectRiskIndicatorOverviewList(DataRiskIndicatorOverview dataRiskIndicatorOverview) throws Exception {
        return (List<DataRiskIndicatorOverview>)selectList("COMMON_RISK_INDICATOR_OVERVIEW_SELECT", dataRiskIndicatorOverview);
    }

    public int updateRiskIndicatorOverview(DataRiskIndicatorOverview dataRiskIndicatorOverview) throws Exception {
        return update("COMMON_RISK_INDICATOR_OVERVIEW_UPDATE", dataRiskIndicatorOverview);
    }

    public int insertRiskIndicatorOverview(DataRiskIndicatorOverview dataRiskIndicatorOverview) throws Exception {
        return insert("COMMON_RISK_INDICATOR_OVERVIEW_INSERT", dataRiskIndicatorOverview);
    }
}
