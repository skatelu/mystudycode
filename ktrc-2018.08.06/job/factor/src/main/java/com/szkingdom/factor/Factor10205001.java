/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10205001
 * Author:   ZhangMaohua
 * Date:     2018/8/21
 * Description: 总股本指某一证券在二级市场可交易的流通股股数
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataStkPlgUndlInfo;
import com.szkingdom.operation.OperationStkPlgUndlInfo;

public class Factor10205001 extends FactorBase {
    private static final long serialVersionUID = 4977924578592013946L;

    private DataStkPlgUndlInfo dataStkPlgUndlInfo;
    long totalShareCapital = 0L;

    public Factor10205001() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        dataStkPlgUndlInfo = new DataStkPlgUndlInfo();
        JSONObject jsonData = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
        dataStkPlgUndlInfo.setStkCode(jsonData.getString("STK_CODE"));
        dataStkPlgUndlInfo.setStkbd(jsonData.getString("STKBD"));

    }

    @Override
    public void handleFactor() throws Exception {
        DataStkPlgUndlInfo dataStkPlgUndlInfoResult = OperationStkPlgUndlInfo.getDataStkPlgUndlInfoByUnique(dataStkPlgUndlInfo);
        if(dataStkPlgUndlInfoResult != null){
            totalShareCapital = dataStkPlgUndlInfoResult.getTotalShareCapital();
        }
    }

    @Override
    public Object getResult() throws Exception {
        return totalShareCapital;
    }
}
