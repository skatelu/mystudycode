/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: DaoRiskIndicators
 * Author:   zmh
 * Date:     2018/7/19 10:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szkingdom.dao;

import com.szkingdom.data.DataRiskIndicators;

import java.util.List;

/**
 * 〈〉
 * @author zmh
 * @create 2018/7/19
 * @since 1.0.0
 */
public class DaoRiskIndicators extends DaoBase {
    public List<DataRiskIndicators> selectOptRiskIndicatorsList() throws Exception {
        return (List<DataRiskIndicators>)selectList("COMMON_RISK_INDICATORS_SELECT", null);
    }

    public List<DataRiskIndicators> selectRiskIndicatorsList(DataRiskIndicators dataRiskIndicators) throws Exception {
        return (List<DataRiskIndicators>)selectList("COMMON_RISK_INDICATORS_SELECT", dataRiskIndicators);
    }

    public int updateRiskIndicators(DataRiskIndicators dataRiskIndicators) throws Exception {
        return update("COMMON_RISK_INDICATORS_UPDATE", dataRiskIndicators);
    }

    public int insertRiskIndicators(DataRiskIndicators dataRiskIndicators) throws Exception {
        return insert("COMMON_RISK_INDICATORS_INSERT", dataRiskIndicators);
    }
}
