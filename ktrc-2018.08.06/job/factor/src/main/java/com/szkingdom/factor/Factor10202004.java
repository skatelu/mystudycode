/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10202004
 * Author:   ZhangMaohua
 * Date:     2018/8/21
 * Description: 证券公司股票质押业务接受单一质押券的数量（当日已有效申报）
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataStkPlgStkAsset;
import com.szkingdom.operation.OperationStkPlgStkAsset;

public class Factor10202004 extends FactorBase {
    private static final long serialVersionUID = 4977924578592013946L;

    private DataStkPlgStkAsset dataStkPlgStkAsset;
    private long stkAvl = 0L;
    public Factor10202004() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        dataStkPlgStkAsset = new DataStkPlgStkAsset();
        JSONObject jsonData = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
        dataStkPlgStkAsset.setStkCode(jsonData.getString("STK_CODE"));

    }

    @Override
    public void handleFactor() throws Exception {
        stkAvl = OperationStkPlgStkAsset.getStkAvlByDataStkPlgStkAsset(dataStkPlgStkAsset);
    }

    @Override
    public Object getResult() throws Exception {
        return stkAvl;
    }
}
