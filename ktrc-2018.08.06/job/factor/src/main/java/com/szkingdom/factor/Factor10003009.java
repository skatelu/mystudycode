/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10003009
 * Author:   ZhangChangHong
 * Date:     2018-08-08
 * Description: 客户实时价格保证金计算（公司）
 * Version:  0.1.0
 * History:
 * <author>          <time>                <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.common.NumConvertUtil;
import com.szkingdom.data.*;
import com.szkingdom.operation.OperationOptAsset;
import com.szkingdom.operation.OperationProcOptAssetRiskMargin;
import com.szkingdom.operation.OperationStkInfo;

import java.util.ArrayList;
import java.util.List;

public class Factor10003009 extends FactorBase {
	private static final long serialVersionUID = 503451862856341845L;

    private DataRiskInfoAsset dataRiskInfoAsset;
    private DataOptAsset dataOptAsset;
    private DataOptInfo dataOptInfo;
    private DataOptMktinfo dataOptMktinfo;
    private DataStkMktinfo dataStkMktinfo;
    private double hedgedSecuMarginRlt = 0.00;

	public Factor10003009() {
		super();
	}

	@Override
    public void resolveParameter(JSONObject json) throws Exception {
        JSONObject jsonData = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
        String optNum = jsonData.getString("OPT_NUM");
        String stkbd = jsonData.getString("STKBD");

        // 组持仓数据查询条件
        dataOptAsset = new DataOptAsset();
        dataOptAsset.setCuacctCode(Long.parseLong(jsonData.getString("CUACCT_CODE")));
        dataOptAsset.setCurrency(jsonData.getString("CURRENCY").charAt(0));

        // 组基础信息数据查询条件
        dataOptInfo = new DataOptInfo();
        dataOptInfo.setOptNum(optNum);
        dataOptInfo.setStkbd(stkbd);

        // 组期权行情数据查询条件
        dataOptMktinfo = new DataOptMktinfo();
        dataOptMktinfo.setOptNum(optNum);
        dataOptMktinfo.setStkbd(stkbd);

        // 组现货行情数据查询条件
        dataStkMktinfo = new DataStkMktinfo();
        dataStkMktinfo.setStkCode(jsonData.getString("OPT_UNDL_CODE"));
        dataStkMktinfo.setStkbd(OperationStkInfo.convertStkbdFromOptToUndl(stkbd));
    }

	@Override
	public void handleFactor() throws Exception {
        // 客户实时价格保证金计算
        try {
            dataRiskInfoAsset = new DataRiskInfoAsset();
            List<DataRiskInfoAsset> listDataRiskInfoAsset = new ArrayList<>();
            int CurrentDate = (int) DateUtils.getCurrDate();
            int CurrentTime = DateUtils.getCurrTime();

            // 获取持仓数据
            List<DataOptAsset> resListDataOptAsset = OperationOptAsset.listOptAssetDataForMaginRiskByParam(this.dataOptAsset, ConstantUtil.BOOLEAN_HEDGED);
            OperationProcOptAssetRiskMargin.procOptAssetDataForMaginRisk(resListDataOptAsset,
                                                                        dataRiskInfoAsset,
                                                                        listDataRiskInfoAsset,
                                                                        CurrentDate,
                                                                        CurrentTime,
                                                                        ConstantUtil.BOOLEAN_NOT_ENTRY_ORDERS);

            resListDataOptAsset.clear();

            if (!listDataRiskInfoAsset.isEmpty()) {
                // 计算单位保证金
                DataRiskInfoAsset resultDataRiskInfoAsset = OperationProcOptAssetRiskMargin.calcOptMarginPrem(listDataRiskInfoAsset,
                                                                                                              dataOptInfo,
                                                                                                              dataOptMktinfo,
                                                                                                              dataStkMktinfo,
                                                                                                      1L,
                                                                                                              CurrentDate);

                if (resultDataRiskInfoAsset != null) {
                    // 客户实时价格保证金（公司）对冲后
                    hedgedSecuMarginRlt = NumConvertUtil.longToMoney(resultDataRiskInfoAsset.getHedgedSecuMarginRlt());
                }
            } else {
                hedgedSecuMarginRlt = 0.00;
            }
        } catch (Exception e) {
            throw e;
        }
	}

	@Override
	public Object getResult() throws Exception {
		return hedgedSecuMarginRlt;
	}
}