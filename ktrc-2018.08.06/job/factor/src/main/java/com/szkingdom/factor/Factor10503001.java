/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10503001
 * Author:   ZhangMaohua
 * Date:     2018/9/10
 * Description: 债券全市场托管量: 指该债券全部已发行尚未偿还的总量
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.dao.DaoStkBondInfo;
import com.szkingdom.data.DataStkBondInfo;
import com.szkingdom.operation.OperationStkBondInfo;

public class Factor10503001 extends FactorBase {
    private static final long serialVersionUID = -4642522069178085713L;
    private DataStkBondInfo dataStkBondInfo;
    private long bondTotalTrust = 0L;

    public Factor10503001() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        dataStkBondInfo = new DataStkBondInfo();

        JSONObject jsonData = (JSONObject)json.get(ConstantUtil.JSON_KTRC);
        dataStkBondInfo.setStkbd(jsonData.getString("STKBD"));
        dataStkBondInfo.setBondCode(jsonData.getString("STK_CODE"));
    }

    @Override
    public void handleFactor() throws Exception {
        DaoStkBondInfo daoStkBondInfo = new DaoStkBondInfo();
        DataStkBondInfo resultDataStkBondInfo = OperationStkBondInfo.getStkBondInfoDataByUnique(dataStkBondInfo);
        if (resultDataStkBondInfo != null) {
            bondTotalTrust = resultDataStkBondInfo.getBondTotalTrust();
        }
    }

    @Override
    public Object getResult() throws Exception {
        return bondTotalTrust;
    }
}
