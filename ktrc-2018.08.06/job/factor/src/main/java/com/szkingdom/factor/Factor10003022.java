/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10003022
 * Author:   ZhangMaohua
 * Date:     2018/8/1
 * Description: 客户行权交收应付资金（10003022）
 * 概念描述 : 客户行权交收应付的资金。
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.common.DateUtils;
import com.szkingdom.dao.DaoOptPerbizData;
import com.szkingdom.data.DataOptPerbizData;
import com.szkingdom.operation.OperationOptPerbizData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Factor10003022 extends FactorBase {

    private static final long serialVersionUID = 99496725854340L;
    private DataOptPerbizData dataOptPerbizData;
    private long fundNetDlvy = 0L;

    public Factor10003022() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject json) throws Exception{
        dataOptPerbizData = new DataOptPerbizData();

        JSONObject data = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
        dataOptPerbizData.setCuacctCode(Long.parseLong(data.getString("CUACCT_CODE")));
        dataOptPerbizData.setCurrency(data.getString("CURRENCY").charAt(0));
        dataOptPerbizData.setCustCode(Long.parseLong(data.getString("CUST_CODE")));
        // COME_IN_DATE >= currentDate
        dataOptPerbizData.setComeInDate(DateUtils.getCurrDate());
    }

    @Override
    public void handleFactor() throws Exception{
        fundNetDlvy = OperationOptPerbizData.getSettAmtByOptPerbizData(dataOptPerbizData);
    }

    @Override
    public Object getResult() throws Exception{
        return fundNetDlvy;
    }
}
