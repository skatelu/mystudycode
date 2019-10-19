/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Factor10203001
 * Author:   ZhangMaohua
 * Date:     2018/8/29
 * Description: 合并管理的质押标的证券、相应孳息及其他担保物价值之和
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.factor;

import com.alibaba.fastjson.JSONObject;
import com.szkingdom.common.ConstantUtil;
import com.szkingdom.data.DataStkMktinfo;
import com.szkingdom.data.DataStkPlgContracts;
import com.szkingdom.data.DataStkPlgStkAsset;
import com.szkingdom.operation.OperationStkMktInfo;
import com.szkingdom.operation.OperationStkPlgContracts;
import com.szkingdom.operation.OperationStkPlgStkAsset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Factor10203001 extends FactorBase {
    private static final long serialVersionUID = -6750929570347328575L;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private DataStkPlgContracts dataStkPlgContracts;
    private double riskRate = 0.0;

    public Factor10203001() {
        super();
    }

    @Override
    public void resolveParameter(JSONObject jsonData) throws Exception {
        dataStkPlgContracts = new DataStkPlgContracts();

        //JSONObject jsonData = (JSONObject) json.get(ConstantUtil.JSON_KTRC);
        dataStkPlgContracts.setStkbd(jsonData.getString("STKBD"));
        dataStkPlgContracts.setStkCode(jsonData.getString("STK_CODE"));
        dataStkPlgContracts.setTrdDate(Integer.parseInt(jsonData.getString("TRD_DATE")));
        dataStkPlgContracts.setConstrSn(jsonData.getString("CONSTR_SN"));

    }

    @Override
    public void handleFactor() throws Exception {
        /**
         * 1.先查合约表 STK_PLG_CONTRACTS  left join 补充合约表 STK_PLG_SUP_CONTRACTS
         * 2.以唯一索引为条件查表 股票质押式回购质押股份表 STK_PLG_STK_ASSET和行情信息 STK_MKTINFO
         * 计算MKT_VAL
         * 3.计算待购回金额BUYBK_AMT
         * 4.计算履约保障比例分子ownAmt，分母oweAmt
         */
        long mktVal = 0;
        long buybkAmt = 0;
        long ownAmt = 0;
        long oweAmt = 0;

        DataStkPlgStkAsset condDataStkPlgStkAsset = new DataStkPlgStkAsset();
        DataStkMktinfo condDataStkMktinfo = new DataStkMktinfo();
        riskRate = 0.0;

        List<DataStkPlgContracts> dataStkPlgContractsList = OperationStkPlgContracts.getStkPlgContractsList(dataStkPlgContracts);
        if(dataStkPlgContractsList != null && dataStkPlgContractsList.size() > 0){
            for(DataStkPlgContracts dataStkPlgContractsEach : dataStkPlgContractsList){
                condDataStkPlgStkAsset.setStkbd(dataStkPlgContractsEach.getStkbd());
                condDataStkPlgStkAsset.setOrgTrdDate(dataStkPlgContractsEach.getTrdDate());
                condDataStkPlgStkAsset.setOrgConstrSn(dataStkPlgContractsEach.getConstrSn());

                List<DataStkPlgStkAsset> dataStkPlgStkAssetList = OperationStkPlgStkAsset.getDataStkPlgStkAssetList(condDataStkPlgStkAsset);
                if(dataStkPlgStkAssetList != null && dataStkPlgStkAssetList.size() > 0) {
                    //计算MKT_VAL
                    //Σ【证券可用数量(STK_AVL) * 市值 + 红利金额(BONUS_AMT)】
                    mktVal = 0;
                    buybkAmt = 0;
                    for (DataStkPlgStkAsset dataStkPlgStkAssetEach : dataStkPlgStkAssetList) {
                        condDataStkMktinfo.setStkbd(dataStkPlgStkAssetEach.getStkbd());
                        condDataStkMktinfo.setStkCode(dataStkPlgStkAssetEach.getStkCode());

                        DataStkMktinfo resultDataStkMktinfo = OperationStkMktInfo.getStkMktinfoDataByUnique(condDataStkMktinfo);
                        if(resultDataStkMktinfo != null){
                            if(resultDataStkMktinfo.getCurrentPrice() != 0L) {
                                mktVal += resultDataStkMktinfo.getCurrentPrice() * dataStkPlgStkAssetEach.getStkAvl() + dataStkPlgStkAssetEach.getBonusAmt();
                            }
                            else {
                                mktVal += resultDataStkMktinfo.getClosingPrice() * dataStkPlgStkAssetEach.getStkAvl() + dataStkPlgStkAssetEach.getBonusAmt();
                            }
                        }
                        else {
                            logger.error("查表 STK_MKTINFO 返回 NULL ！");
                            break;
                        }
                    }
                    // + 初始合约金额(ORG_CONT_AMT ) + 现金补充质押(CASH_AMT)
                    mktVal += dataStkPlgContractsEach.getOrgContAmt() + dataStkPlgContractsEach.getCashAmt();

                    //计算购回金额BUYBK_AMT,分段标志
                    if(dataStkPlgContractsEach.getIntSubFlag() == '2'){
                        buybkAmt = dataStkPlgContractsEach.getBuybkAmt();
                    }
                    else {
                        buybkAmt = dataStkPlgContractsEach.getOrgContAmt() + dataStkPlgContractsEach.getCurrIntAmt()
                                + dataStkPlgContractsEach.getCurrIntAccr()*dataStkPlgContractsEach.getFiRate()/100
                                - dataStkPlgContractsEach.getHasBuybkAmt();
                    }
                    //计算履约保障比例
                    //履约比例计算方式 0-质押市值/实时待购回金额,1-质押市值/未还本金,2-质押市值/待购回金额
                    if(dataStkPlgContractsEach.getRiskRateMode() == '1'){
                        oweAmt = dataStkPlgContractsEach.getOrgContAmt() - dataStkPlgContractsEach.getHasBuybkAmt();
                    }
                    else if(dataStkPlgContractsEach.getRiskRateMode() == '2'){
                        oweAmt = dataStkPlgContractsEach.getBuybkAmt();
                    }
                    else {
                        oweAmt = buybkAmt;
                    }
                    //担保物履约计算方式: 0-分子中累加, 1-分母中扣减
                    if(dataStkPlgContractsEach.getGuaranteeRiskMode() == '1'){
                        oweAmt = oweAmt - dataStkPlgContractsEach.getOtherGuaranteeVal();
                    }
                    else {
                        ownAmt = mktVal + dataStkPlgContractsEach.getOtherGuaranteeVal();
                    }
                    //履约保障比例
                    riskRate = ownAmt/((oweAmt > 1.0) ? oweAmt : 1.0);

                }
                else {
                    logger.error("查表 STK_PLG_STK_ASSET 返回 NULL ！");
                }
            }
        }
        else {
            throw new Exception("查表 STK_PLG_CONTRACTS 返回 NULL ！");
        }
    }

    @Override
    public Object getResult() throws Exception {
        return riskRate;
    }

    public static void main(String[] args) throws Exception {
        double factorResult = 0;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("STKBD","00");
        jsonObject.put("STK_CODE","000012");
        jsonObject.put("CONSTR_SN","88888020161028AT800462");
        jsonObject.put("TRD_DATE","20180831");

        Factor10203001 fact=new Factor10203001();
        fact.resolveParameter(jsonObject);
        fact.handleFactor();
        factorResult = (double)fact.getResult();

        System.out.println(factorResult);

    }
}
