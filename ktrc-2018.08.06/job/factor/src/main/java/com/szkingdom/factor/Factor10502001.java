/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10502001
 * Author:   ZhangMaohua
 * Date:     2018/9/10
 * Description: 单一证券账户标准券总量（10502001）
 * 概念描述： 单一证券账户质押入库的所有无司法瑕疵的质押券根据标准券折算率计算出来的总额度
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataStkBondInfo;
import com.szkingdom.data.DataStkImpawn;
import com.szkingdom.data.DataStkInfo;
import com.szkingdom.operation.OperationStkBondInfo;
import com.szkingdom.operation.OperationStkImpawn;
import com.szkingdom.operation.OperationStkInfo;

import java.util.List;

public class Factor10502001 extends FactorBase {
    private static final long serialVersionUID = -4642522069178085713L;
    private DataStkImpawn dataStkImpawn;
    private DataStkInfo dataStkInfo;
    private DataStkBondInfo dataStkBondInfo;
    private long standardBondAmt;

    public Factor10502001() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        JSONObject jsonData = (JSONObject)json.get(ConstantUtil.JSON_KTRC);
        String stkbd = jsonData.getString("STKBD");
        String trdacct = jsonData.getString("TRDACCT");

        dataStkImpawn = new DataStkImpawn();
        dataStkImpawn.setStkbd(stkbd);
        dataStkImpawn.setTrdacct(trdacct);

        dataStkInfo = new DataStkInfo();
        dataStkInfo.setStkbd(stkbd);

        dataStkBondInfo = new DataStkBondInfo();
        dataStkBondInfo.setStkbd(stkbd);
    }

    @Override
    public void handleFactor() throws Exception {
        List<DataStkImpawn> resultDataStkImpawnList = OperationStkImpawn.getStkImpawnDataList(dataStkImpawn);
        if (resultDataStkImpawnList != null && resultDataStkImpawnList.size() > 0) {
            for (DataStkImpawn dataStkImpawnEach : resultDataStkImpawnList) {
                dataStkInfo.setStkCode(dataStkImpawnEach.getStkCode());
                dataStkBondInfo.setBondCode(dataStkImpawnEach.getStkCode());
                DataStkInfo resultDataStkInfo = OperationStkInfo.getStkInfoDataByUnique(dataStkInfo);
                DataStkBondInfo resultDataStkBondInfo = OperationStkBondInfo.getStkBondInfoDataByUnique(dataStkBondInfo);
                if (resultDataStkInfo != null && resultDataStkBondInfo != null) {
                    standardBondAmt += dataStkImpawnEach.getStkAvl() * resultDataStkInfo.getStkFaceVal() * resultDataStkBondInfo.getBondCvtrate();
                }
            }
        }
    }

    @Override
    public Object getResult() throws Exception {
        return standardBondAmt;
    }
}
