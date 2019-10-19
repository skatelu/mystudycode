/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationMarginTemplate
 * Author:   ZhangChangHong
 * Date:     2018/8/13
 * Description: 保证金策略模板表操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.dao.DaoMarginTemplate;
import com.szkingdom.data.DataMarginTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class OperationMarginTemplate extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationMarginTemplate.class);

    /**
     * 保证金策略模板数据收集
     * @param condDataMarginTemplate
     * @return
     * @throws Exception
     */
    public static List<DataMarginTemplate> listMarginTemplateDataByParam(DataMarginTemplate condDataMarginTemplate) throws Exception {
        DaoMarginTemplate daoMarginTemplate = new DaoMarginTemplate();
        try {
            List<DataMarginTemplate> resListDataMarginTemplate = daoMarginTemplate.selectList(condDataMarginTemplate);
            if (resListDataMarginTemplate != null && !resListDataMarginTemplate.isEmpty()) {
                return resListDataMarginTemplate;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 保证金策略模板数据收集,查无数据用默认模板
     * @param condDataMarginTemplate
     * @return
     * @throws Exception
     */
    public static List<DataMarginTemplate> listMarginTemplateDataForMaginRisk(DataMarginTemplate condDataMarginTemplate) throws Exception {
        DaoMarginTemplate daoMarginTemplate = new DaoMarginTemplate();
        try {
            List<DataMarginTemplate> resultListDataMarginTemplate = daoMarginTemplate.selectListForMaginRisk(condDataMarginTemplate);
            if (resultListDataMarginTemplate != null && !resultListDataMarginTemplate.isEmpty()) {
                return resultListDataMarginTemplate;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
