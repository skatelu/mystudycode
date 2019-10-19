package com.szkingdom.storm.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.storm.tuple.Values;
import org.apache.storm.tuple.Fields;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.szkingdom.dao.*;
import com.szkingdom.data.*;
import com.szkingdom.common.*;

/**
 * @author zhangch
 * @date 2018/7/6 15:30
 */
public class OptSigAccPosiLmtIndicatorsBolt extends BaseRichBolt {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final long serialVersionUID = 295633099422158167L;
    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        //获得字节数组，再将其封装到string类中
        String bizType =  String.valueOf(input.getStringByField("BIZ_TYPE"));
        String message =  String.valueOf(input.getStringByField("MESSAGE"));
        if(message.length() <= 0){
            this.collector.fail(input);
            //logger.error("OptSigAccPosiLmtIndicatorsBolt message.length()<=0 ack fail ! \n");
            return;
        }

        // json 解析
        JSONObject object = JSON.parseObject(message);
        JSONObject data = (JSONObject) object.get("data");
        String stkBiz = data.getString("STK_BIZ");
        long custCode = Long.valueOf(data.getString("CUST_CODE"));
        long orderQty = Long.valueOf(data.getString("ORDER_QTY"));
        long cuacctCode = Long.valueOf(data.getString("CUACCT_CODE"));
        String optNum = data.getString("OPT_NUM");
        String stkbd = data.getString("STKBD");
        String stkpbu = data.getString("STKPBU");
        String trdacct = data.getString("TRDACCT");
        String optUndlCode = data.getString("OPT_UNDL_CODE");

        /**
         * @业务：  单账户单标的总持仓比例
         * @公式:   单账户单标的总持仓数量/单账户单标的总持仓限额
         * @remark: 个人、机构投资者的持仓限额需要分开设置
         * @步骤:    1.获取标的类别，客户表不存map，查询客户属性(机构or个人)
         *           2.总持仓数量 = 持仓表客户,单标的实时持仓OPT_POSI_RLT+当前开仓委托数量
         *           3.总持仓限额 = 客户持仓限额 > 0 ? 客户持仓限额 :(标的证券限额 > 0 ? 标的证券限额 : 标的类别限额)
         * @注意:    参数表先查map，再查DB，然后记录到map，无论认沽认购都要计算
         */

        // 按唯一索引查询 STK_INFO
        Character stkCls = null;
        String stkName = "";
        DataStkInfo dataStkInfo = new DataStkInfo();
        dataStkInfo.setStkCode(optUndlCode);
        if (stkbd.equals("15"))
        dataStkInfo.setStkbd("10");

        try {
            DaoStkInfo daoStkInfo = new DaoStkInfo();
            List<DataStkInfo> listStkInfo = daoStkInfo.selectRiskInfoAssetList(dataStkInfo);
            if (listStkInfo.size() > 0) {
                stkCls = listStkInfo.get(0).getStkCls();
                stkName = listStkInfo.get(0).getStkName();
            } else {
                logger.error("查无数据 标的代码：[" + optUndlCode + "]\n");
            }
        }catch (Exception e){
            logger.error("查询STK_INFO异常 ERROR [ " + e + "]\n");
        }

        // 查客户属性
        Character custType = null;
        String custName = "";
        DataCustomer dataCustomer = new DataCustomer();
        dataCustomer.setCustCode(custCode);

        try {
            DaoCustomer daoCustomer = new DaoCustomer();
            List<DataCustomer> listCustomer = daoCustomer.selectCustomerList(dataCustomer);
            if (listCustomer.size() > 0) {
                custType = listCustomer.get(0).getCustType();
                custName = listCustomer.get(0).getCustName();
            } else {
                logger.error("查无数据 客户号：[" + custCode + "]\n");
            }
        }catch (Exception e){
            logger.error("Exception daoCustomer.selectCustomerListByPara ERROR [ " + e + "]\n");
        }

        // 单表可查多个指标的，统一赋值
        long sumOptPosiRltSingleTotal = 0L;
        long sumOptPosiRltSingleLongOpen = 0L;
        long sumOptPosiRltSingleDailyOpen = 0L;

        // 在此查riskinfo，如果有数据，取riskinfo数据计算，否则取持仓表
        List<DataRiskInfoAsset> listRiskInfoAssetUnique = new ArrayList<DataRiskInfoAsset>();
        String subacctCode = "";
        boolean bFlag = false;

        DataOptAsset dataOptAssetUnique = new DataOptAsset(cuacctCode, stkbd, stkpbu,
                                                           trdacct, optNum, stkBiz, 0);

        try {
            DaoOptAsset daoOptAssetUnique = new DaoOptAsset();
            List<DataOptAsset> listOptAssetUnique = daoOptAssetUnique.selectOptAssetList(dataOptAssetUnique);
            if (listOptAssetUnique.size() > 0){
                // 取交易账户子编码
                subacctCode = listOptAssetUnique.get(0).getSubacctCode();
            }
        }catch (Exception e){
            logger.error("Exception daoOptAsset.selectOptAssetListByPara ERROR [ " + e + "]\n");
        }

        if (!"".equals(subacctCode)){
            DataRiskInfoAsset dataRiskInfoAssetUnique = new DataRiskInfoAsset(custCode, trdacct, subacctCode,
                                                                        "1", optUndlCode, stkbd);

            try {
                DaoRiskInfoAsset daoRiskInfoAssetUnique = new DaoRiskInfoAsset();
                listRiskInfoAssetUnique = daoRiskInfoAssetUnique.selectRiskInfoAssetList(dataRiskInfoAssetUnique);

                if (listRiskInfoAssetUnique.size() > 0){
                    // 取riskinfoAsset计算
                    sumOptPosiRltSingleTotal += listRiskInfoAssetUnique.get(0).getPosiQty();
                    sumOptPosiRltSingleLongOpen += listRiskInfoAssetUnique.get(0).getPosiQty();
                    sumOptPosiRltSingleDailyOpen += listRiskInfoAssetUnique.get(0).getPosiQty();

                    bFlag = true;
                }
                else{
                    // 取持仓数据计算
                    // 查单客户所有标的
                    List<DataOptAsset> listOptAsset = new ArrayList<DataOptAsset>();

                    DataOptAsset dataOptAsset = new DataOptAsset();
                    dataOptAsset.setCuacctCode(cuacctCode);
                    dataOptAsset.setTrdacct(trdacct);
                    dataOptAsset.setStkbd(stkbd);
                    try {
                        DaoOptAsset daoOptAsset = new DaoOptAsset();
                        listOptAsset = daoOptAsset.selectOptAssetList(dataOptAsset);
                    }catch (Exception e){
                        logger.error("Exception daoOptAsset.selectOptAssetListByPara ERROR [ " + e + "]\n");
                    }

                    if (listOptAsset.size() > 0){
                        // 取交易账户子编码
                        subacctCode = listOptAsset.get(0).getSubacctCode();

                        for (DataOptAsset dataOptAst : listOptAsset) {
                            // 在此循环单客户，单标的总持仓
                            if (optUndlCode.equals(dataOptAst.getOptUndlCode())){
                                sumOptPosiRltSingleTotal += dataOptAst.getOptPosiRlt();
                                if ('L' == dataOptAst.getOptSide())
                                {
                                    sumOptPosiRltSingleLongOpen += dataOptAst.getOptPosiRlt();
                                    sumOptPosiRltSingleDailyOpen += dataOptAst.getOptDailyOpenRlt();
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){
                logger.error("Exception daoOptAsset.selectOptAssetListByPara ERROR [ " + e + "]\n");
            }
        }

        // 委托数量计入持仓
        sumOptPosiRltSingleTotal += orderQty;
        if (stkBiz.equals("400")){
            sumOptPosiRltSingleLongOpen += orderQty;
            sumOptPosiRltSingleDailyOpen += orderQty;
        }

        /**
         * A	单标的单一个人账户总持仓限额
         * B	单标的单一个人账户权利仓持仓限额
         * C	单标的单一机构账户总持仓限额
         * D	单标的单一机构账户权利仓持仓限额
         * E	单标的单一自营账户总持仓限额
         * F	单标的单一自营账户权利仓持仓限额
         * G	单标的单一做市商账户总持仓限额
         * H	单标的单一做市商账户权利仓持仓限额
         * K	单标的单一个人账户当日累计买入开仓限额
         * L	单标的单一机构账户当日累计买入开仓限额
         * M	单标的单一自营账户当日累计买入开仓限额
         * N	单标的单一做市商账户当日累计买入开仓限额
         * @注意:     单账户单标的，加条件区分业务，且经济业务I,J另加bolt处理，此处posiLmtAttr可跟custLmtAttr相同
         */
        long optPosiLqtySingleTotal = 0L;
        long optPosiLqtySingleLongOpen = 0L;
        long optPosiLqtySingleDailyOpen = 0L;

        DataOptCustPosiLmt dataOptCustPosiLmt = new DataOptCustPosiLmt(stkbd, custCode, trdacct, stkCls, optUndlCode, '@');
        DataOptPosiLmt dataOptPosiLmt = new DataOptPosiLmt(stkbd, optUndlCode, '@', '0');
        DataOptClsPosiLmt dataOptClsPosiLmt = new DataOptClsPosiLmt(stkbd, stkCls, '@', '0');

        try {
            optPosiLqtySingleTotal = GetPosiLqty.getLqtyForSingleAcctSingleUndlTotalPosi(dataOptCustPosiLmt,
                    dataOptPosiLmt,
                    dataOptClsPosiLmt,
                    custType);
        }catch (Exception e){
            logger.error("OptSigAccPosiLmtIndicatorsBolt optPosiLqtySingleTotal[ " + e + "]\n");

            return;
        }

        try {
            optPosiLqtySingleLongOpen = GetPosiLqty.getLqtyForSingleAcctSingleUndlLongPosi(dataOptCustPosiLmt,
                    dataOptPosiLmt,
                    dataOptClsPosiLmt,
                    custType);
        }catch (Exception e){
            logger.error("OptSigAccPosiLmtIndicatorsBolt optPosiLqtySingleLongOpen[ " + e + "]\n");

            return;
        }

        try {
            optPosiLqtySingleDailyOpen = GetPosiLqty.getLqtyForSingleAcctSingleUndlDailyPosi(dataOptCustPosiLmt,
                    dataOptPosiLmt,
                    dataOptClsPosiLmt,
                    custType);
        }catch (Exception e){
            logger.error("OptSigAccPosiLmtIndicatorsBolt optPosiLqtySingleDailyOpen[ " + e + "]\n");
            return;
        }

        // 在此计算持仓比例，emit写入bolt
        long riskValueSingleTotal = 0L;
        long riskValueSingleLongOpen = 0L;
        long riskValueSingleDailyOpen = 0L;

        Character riskValidFlagSingleTotal = null;
        Character riskValidFlagSingleLongOpen = null;
        Character riskValidFlagSingleDailyOpen = null;

        double riskValueTmp = 0.00;

        if (optPosiLqtySingleTotal > 0){
            riskValueTmp = sumOptPosiRltSingleTotal / (double)optPosiLqtySingleTotal;
            if (riskValueTmp >= 0.8){
                riskValidFlagSingleTotal = '1';
            }else {
                riskValidFlagSingleTotal = '0';
            }

            riskValueSingleTotal  = NumConvertUtil.rateToLong(riskValueTmp);
        }

        if (optPosiLqtySingleLongOpen > 0){
            riskValueTmp = sumOptPosiRltSingleLongOpen / (double)optPosiLqtySingleLongOpen;
            if (riskValueTmp >= 0.8){
                riskValidFlagSingleLongOpen = '1';
            }else {
                riskValidFlagSingleLongOpen = '0';
            }
            riskValueSingleLongOpen  = NumConvertUtil.rateToLong(riskValueTmp);
        }

        if (optPosiLqtySingleDailyOpen > 0){
            riskValueTmp = sumOptPosiRltSingleDailyOpen / (double)optPosiLqtySingleDailyOpen;
            if (riskValueTmp >= 0.8){
                riskValidFlagSingleDailyOpen = '1';
            }else {
                riskValidFlagSingleDailyOpen = '0';
            }

            riskValueSingleDailyOpen  = NumConvertUtil.rateToLong(riskValueTmp);
        }

        // 更新OPT_ASSET
        DataOptAsset dataOptAsset = new DataOptAsset(cuacctCode, stkbd, stkpbu, trdacct, optNum, stkBiz, orderQty);
        // 除唯一索引外，需要把其他可写入的字段同样设置，否则会导致新数据写表与查询时结果异常。
        dataOptAsset.setCustCode(custCode);
        dataOptAsset.setCustType(custType);
        dataOptAsset.setOptDailyOpenRlt(orderQty);
        dataOptAsset.setOptUndlCode(optUndlCode);
        if ("".equals(subacctCode)){
            subacctCode = "888";
        }
        dataOptAsset.setSubacctCode(subacctCode);
        if (stkBiz.equals("400")){
            dataOptAsset.setOptSide('L');
        }
        this.collector.emit(input, new Values(ConstantUtil.TABLE_OPT_ASSET, JSON.toJSONString(dataOptAsset)));

        // 更新RISK_INFO_ASSET
        Date date = new Date(System.currentTimeMillis());
        DateFormat format = new SimpleDateFormat("yyyyMMdd,HHmmss");
        String time= format.format(date);
        String[] dateTime = time.split(",");

        DataRiskInfoAsset dataRiskInfoAssetSingleTotal = new DataRiskInfoAsset();
        DataRiskInfoAsset dataRiskInfoAssetSingleLongOpen = new DataRiskInfoAsset();
        DataRiskInfoAsset dataRiskInfoAssetSingleDailyOpen = new DataRiskInfoAsset();

        if (bFlag){
            dataRiskInfoAssetSingleTotal = listRiskInfoAssetUnique.get(0);
            dataRiskInfoAssetSingleTotal.setRiskValue(riskValueSingleTotal);
            dataRiskInfoAssetSingleTotal.setPosiQty(sumOptPosiRltSingleTotal);
            dataRiskInfoAssetSingleTotal.setPosiLqty(optPosiLqtySingleTotal);
            dataRiskInfoAssetSingleTotal.setRiskValidFlag(riskValidFlagSingleTotal);
            dataRiskInfoAssetSingleTotal.setCustName(custName);
            dataRiskInfoAssetSingleTotal.setOptUndlName(stkName);

            dataRiskInfoAssetSingleLongOpen = listRiskInfoAssetUnique.get(0);
            dataRiskInfoAssetSingleLongOpen.setRiskValue(riskValueSingleLongOpen);
            dataRiskInfoAssetSingleLongOpen.setPosiQty(sumOptPosiRltSingleLongOpen);
            dataRiskInfoAssetSingleLongOpen.setPosiLqty(optPosiLqtySingleLongOpen);
            dataRiskInfoAssetSingleLongOpen.setRiskValidFlag(riskValidFlagSingleLongOpen);
            dataRiskInfoAssetSingleLongOpen.setCustName(custName);
            dataRiskInfoAssetSingleLongOpen.setOptUndlName(stkName);

            dataRiskInfoAssetSingleDailyOpen = listRiskInfoAssetUnique.get(0);
            dataRiskInfoAssetSingleDailyOpen.setRiskValue(riskValueSingleDailyOpen);
            dataRiskInfoAssetSingleDailyOpen.setPosiQty(sumOptPosiRltSingleDailyOpen);
            dataRiskInfoAssetSingleDailyOpen.setPosiLqty(optPosiLqtySingleDailyOpen);
            dataRiskInfoAssetSingleDailyOpen.setRiskValidFlag(riskValidFlagSingleDailyOpen);
            dataRiskInfoAssetSingleDailyOpen.setCustName(custName);
            dataRiskInfoAssetSingleDailyOpen.setOptUndlName(stkName);

        }else{
             DataRiskInfoAsset dataRiskInfoAssetSingleLongOpenTmp = new DataRiskInfoAsset("60003001",
                    "单账户单标的权利持仓比例",
                    Integer.valueOf(dateTime[0]),
                    Integer.valueOf(dateTime[1]),
                    riskValueSingleLongOpen, sumOptPosiRltSingleLongOpen,
                    optPosiLqtySingleLongOpen, custCode,
                    custType, cuacctCode,
                    stkbd, trdacct, subacctCode,
                    optUndlCode, stkCls, riskValidFlagSingleLongOpen);
            dataRiskInfoAssetSingleLongOpenTmp.setOptUndlName(stkName);
            dataRiskInfoAssetSingleLongOpenTmp.setCustName(custName);
            dataRiskInfoAssetSingleLongOpen = dataRiskInfoAssetSingleLongOpenTmp;

            DataRiskInfoAsset dataRiskInfoAssetSingleTotalTmp = new DataRiskInfoAsset("60003002",
                    "单账户单标的总持仓比例",
                    Integer.valueOf(dateTime[0]),
                    Integer.valueOf(dateTime[1]),
                    riskValueSingleTotal, sumOptPosiRltSingleTotal,
                    optPosiLqtySingleTotal, custCode,
                    custType, cuacctCode,
                    stkbd, trdacct, subacctCode,
                    optUndlCode, stkCls, riskValidFlagSingleTotal);
            dataRiskInfoAssetSingleTotalTmp.setOptUndlName(stkName);
            dataRiskInfoAssetSingleTotalTmp.setCustName(custName);
            dataRiskInfoAssetSingleTotal = dataRiskInfoAssetSingleTotalTmp;

            DataRiskInfoAsset dataRiskInfoAssetSingleDailyOpenTmp = new DataRiskInfoAsset("60003003",
                    "单账户单标的当日累计持仓比例",
                    Integer.valueOf(dateTime[0]),
                    Integer.valueOf(dateTime[1]),
                    riskValueSingleDailyOpen, sumOptPosiRltSingleDailyOpen,
                    optPosiLqtySingleDailyOpen, custCode,
                    custType, cuacctCode,
                    stkbd, trdacct, subacctCode,
                    optUndlCode, stkCls, riskValidFlagSingleDailyOpen);
            dataRiskInfoAssetSingleDailyOpenTmp.setOptUndlName(stkName);
            dataRiskInfoAssetSingleDailyOpenTmp.setCustName(custName);
            dataRiskInfoAssetSingleDailyOpen = dataRiskInfoAssetSingleDailyOpenTmp;
        }

        this.collector.emit(input, new Values(ConstantUtil.TABLE_RISK_INFO_ASSET, JSON.toJSONString(dataRiskInfoAssetSingleTotal)));

        this.collector.emit(input, new Values(ConstantUtil.TABLE_RISK_INFO_ASSET, JSON.toJSONString(dataRiskInfoAssetSingleLongOpen)));

        this.collector.emit(input, new Values(ConstantUtil.TABLE_RISK_INFO_ASSET, JSON.toJSONString(dataRiskInfoAssetSingleDailyOpen)));

        this.collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("TABLE_NAME", "VALUE"));
    }
}
