/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10502003
 * Author:   ZhangMaohua
 * Date:     2018/9/10
 * Description: 单一客户单只信用债券单市场入库量（10502003）
 * 概念描述： 回购融资主体名下所有证券账户合并计算的单只信用债券单市场入库量
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataStkImpawn;
import com.szkingdom.operation.OperationStkImpawn;

import java.util.List;

public class Factor10502003 extends FactorBase {
    private static final long serialVersionUID = -4642522069178085713L;
    private DataStkImpawn dataStkImpawn;
    private long stkAvl = 0L;

    public Factor10502003() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        dataStkImpawn = new DataStkImpawn();

        JSONObject jsonData = (JSONObject)json.get(ConstantUtil.JSON_KTRC);
        dataStkImpawn.setStkex(jsonData.getString("STKBD").charAt(0));
        dataStkImpawn.setStkCode(jsonData.getString("STK_CODE"));
        dataStkImpawn.setCustCode(Integer.parseInt(jsonData.getString("CUST_CODE")));
    }

    @Override
    public void handleFactor() throws Exception {
        List<DataStkImpawn> resultDataStkImpawn = OperationStkImpawn.getStkImpawnDataList(dataStkImpawn);
        if (resultDataStkImpawn != null && resultDataStkImpawn.size() > 0) {
            for (DataStkImpawn dataStkImpawnEach : resultDataStkImpawn) {
                stkAvl += dataStkImpawnEach.getStkAvl();
            }
        }
    }

    @Override
    public Object getResult() throws Exception {
        return stkAvl;
    }
}
