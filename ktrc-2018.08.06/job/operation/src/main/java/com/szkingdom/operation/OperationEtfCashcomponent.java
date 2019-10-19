/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: OperationOptMktinfo
 * Author:   ZhangChangHong
 * Date:     2018/7/26
 * Description: OPT_MKTINFO操作类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.operation;

import com.szkingdom.dao.DaoEtfCashcomponent;
import com.szkingdom.data.DataEccodesign;
import com.szkingdom.data.DataEtfCashcomponent;
import com.szkingdom.data.DataFundAsset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OperationEtfCashcomponent extends OperationBase {
    protected static Logger logger = LoggerFactory.getLogger(OperationEtfCashcomponent.class);

    public static DataEtfCashcomponent initEtfCashcomponent(long fundId,long custId) throws Exception {
        DataEtfCashcomponent dataEtfCashcomponent = new DataEtfCashcomponent();

        dataEtfCashcomponent.setFundId(fundId);
        dataEtfCashcomponent.setCustId(custId);

        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
        dataEtfCashcomponent.setDate(f.format(new Date()));

        DaoEtfCashcomponent daoEtfCashcomponent = new DaoEtfCashcomponent();
        try {
            DataEtfCashcomponent resEtfCashcomponent = daoEtfCashcomponent.selectUnique(dataEtfCashcomponent);
            return resEtfCashcomponent;
        } catch (Exception e) {
            throw new Exception("TABLE_EtfCashcomponent 查询操作异常", e);
        }
    }

    public static void writeEtfCashcomponent2DBbyEccodesign(Object objectParam) throws Exception {
        DataEccodesign searchByEccodesign = (DataEccodesign) objectParam;
        //根据Eccodesign更新预留额度、截止日期

        DataEtfCashcomponent dataEtfCashcomponent = initEtfCashcomponent(searchByEccodesign.getFundId(),searchByEccodesign.getCustId());
        if (dataEtfCashcomponent != null){
            try {
                //etf_cashcomponent数据更新
                String multisettStatus = searchByEccodesign.getMultisettStatus();
                int resfundExpdate = searchByEccodesign.getResfundExpdate();
                double reservFund = searchByEccodesign.getReservFund();
                double lastConvertvol = searchByEccodesign.getLastConvertvol();

                double yled = 0;
                int jzrq = resfundExpdate;

                if ("0".equals(multisettStatus))
                    yled = reservFund;
                else if ("1".equals(multisettStatus))
                    yled = lastConvertvol;

                dataEtfCashcomponent.setYled(yled);
                dataEtfCashcomponent.setJzrq(jzrq);

                if(("0".equals(multisettStatus))||("1".equals(multisettStatus)))
                {
                    DaoEtfCashcomponent daoUpdateEtfCashcomponent = new DaoEtfCashcomponent();
                    daoUpdateEtfCashcomponent.update(dataEtfCashcomponent);
                }

            } catch (Exception e) {
                throw e;
            }
    }
    }
    public static void writeEtfCashcomponent2DBbyFundAsset(Object objectParam) throws Exception {
        DataFundAsset searchByFundAsset = (DataFundAsset) objectParam;
        //根据fundAsset更新可用余额、起始金额

        DataEtfCashcomponent dataEtfCashcomponent = initEtfCashcomponent(searchByFundAsset.getFundId(),searchByFundAsset.getCustId());
        if (dataEtfCashcomponent != null){
            try {
                //fundasset数据更新
                double fundavl = searchByFundAsset.getFundAvl();
                double kyye = dataEtfCashcomponent.getKyye();
                double ydbzje = dataEtfCashcomponent.getYdbzje();
                double qsje = dataEtfCashcomponent.getQsje();
                kyye = fundavl;
                qsje = fundavl-ydbzje;
                dataEtfCashcomponent.setKyye(kyye);
                dataEtfCashcomponent.setQsje(qsje);
                    DaoEtfCashcomponent daoUpdateEtfCashcomponent = new DaoEtfCashcomponent();
                    daoUpdateEtfCashcomponent.update(dataEtfCashcomponent);

            } catch (Exception e) {
                throw e;
            }
        }
    }
}
