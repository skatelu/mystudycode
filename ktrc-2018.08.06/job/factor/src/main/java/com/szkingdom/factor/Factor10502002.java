/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10502002
 * Author:   ZhangMaohua
 * Date:     2018/9/10
 * Description: 单一客户债券托管量（10502002）
 * 概念描述：回购融资主体名下所有证券账户合并计算的全部债券类资产、以及符合中国结算质押券入库标准的债券型基金产品
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataStkAsset;
import com.szkingdom.data.DataStkBondInfo;
import com.szkingdom.data.DataStkImpawn;
import com.szkingdom.data.DataStkInfo;
import com.szkingdom.operation.OperationStkAsset;
import com.szkingdom.operation.OperationStkBondInfo;
import com.szkingdom.operation.OperationStkImpawn;
import com.szkingdom.operation.OperationStkInfo;

import java.util.List;

public class Factor10502002 extends FactorBase {
    private static final long serialVersionUID = -4642522069178085713L;
    private DataStkImpawn dataStkImpawn;
    private DataStkInfo dataStkInfo;
    private DataStkBondInfo dataStkBondInfo;
    private DataStkAsset dataStkAsset;
    private long custBondAmt = 0L;

    public Factor10502002() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception {
        JSONObject jsonData = (JSONObject)json.get(ConstantUtil.JSON_KTRC);
        String stkbd = jsonData.getString("STKBD");
        int custCode = Integer.parseInt(jsonData.getString("CUST_CODE"));
        //证券类别, stkbd要不要？ 不要的话,放到for循环里面

        dataStkAsset = new DataStkAsset();
        dataStkAsset.setStkbd(stkbd);
        dataStkAsset.setCustCode(custCode);

        dataStkInfo = new DataStkInfo();
        dataStkInfo.setStkbd(stkbd);

        dataStkBondInfo = new DataStkBondInfo();
        dataStkBondInfo.setStkbd(stkbd);

        dataStkImpawn = new DataStkImpawn();
        dataStkImpawn.setStkbd(stkbd);
        dataStkImpawn.setCustCode(custCode);
        //dataStkImpawn.setStkCode(jsonData.getString("STK_CLS"));
    }

    @Override
    public void handleFactor() throws Exception {
        long bondAvlAmt = 0L;
        long bondRepchAmt = 0L;
        List<DataStkAsset> resultDataStkAssetList = OperationStkAsset.getStkAssetDataList(dataStkAsset);
        if (resultDataStkAssetList != null && resultDataStkAssetList.size() > 0) {
            for(DataStkAsset dataStkAssetEach : resultDataStkAssetList) {
                String stkCode = dataStkAssetEach.getStkCode();
                dataStkInfo.setStkCode(stkCode);
                dataStkBondInfo.setBondCode(stkCode);
                DataStkInfo resultDataStkInfo = OperationStkInfo.getStkInfoDataByUnique(dataStkInfo);
                DataStkBondInfo resultDataStkBondInfo = OperationStkBondInfo.getStkBondInfoDataByUnique(dataStkBondInfo);
                if (resultDataStkInfo != null && resultDataStkBondInfo != null) {
                    bondAvlAmt += (dataStkAssetEach.getStkBln() + dataStkAssetEach.getStkTrdBln())
                            * resultDataStkInfo.getStkFaceVal() * resultDataStkBondInfo.getBondAdjustPara();
                }
            }
        }

        List<DataStkImpawn> resultDataStkImpawnList = OperationStkImpawn.getStkImpawnDataList(dataStkImpawn);
        if (resultDataStkImpawnList != null && resultDataStkImpawnList.size() > 0){
            for (DataStkImpawn dataStkImpawnEach : resultDataStkImpawnList) {
                String stkCode = dataStkImpawnEach.getStkCode();
                dataStkInfo.setStkCode(stkCode);
                dataStkBondInfo.setBondCode(stkCode);
                DataStkInfo resultDataStkInfo = OperationStkInfo.getStkInfoDataByUnique(dataStkInfo);
                DataStkBondInfo resultDataStkBondInfo = OperationStkBondInfo.getStkBondInfoDataByUnique(dataStkBondInfo);
                if (resultDataStkInfo != null && resultDataStkBondInfo != null) {
                    bondRepchAmt += dataStkImpawnEach.getStkAvl() * resultDataStkInfo.getStkFaceVal() * resultDataStkBondInfo.getBondAdjustPara();
                }
            }
        }

        custBondAmt = bondAvlAmt + bondRepchAmt;
    }

    @Override
    public Object getResult() throws Exception {
        return custBondAmt;
    }
}
