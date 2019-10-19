package com.szkingdom.storm.demo;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.storm.tuple.Values;
import org.apache.storm.tuple.Fields;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by zhangch on 2018/4/21.
 */
public class DataManagerBolt extends BaseRichBolt {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    //private static final long serialVersionUID = 294633099452558992L;

    List<String> list;
    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        //list = new ArrayList<String>();
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {

        //获得字节数组，再将其封装到string类中
        String strMsg = new String(input.getBinaryByField("bytes"));

        //logger.info(strMsg);
        //list.add(strMsg);

        //tuple在传输的时候stom会通过ack fail机制来监控执行情况，如果tuple被bolt接收到了，需要给storm确认一下
        //如果出现异常，没执行成功，，数据会被重复发送并进行处理 .默认不用ack处理,数据会被bolt重复消费
        if (strMsg.length() > 0){
            this.collector.ack(input);
        }else{
            return;
        }

        String strSecuCode;
        String strCustCode;
        String strMessage = strMsg;
        int iMkt = 0;
        // 行情或者委托分别处理
        if (strMsg.contains("req_hq")){
            iMkt = Integer.parseInt(strMsg.substring(strMsg.indexOf("MKT") + 5, strMsg.indexOf("MKT") + 6));
            strSecuCode = strMsg.substring(strMsg.indexOf("CODE") + 6, strMsg.indexOf("CODE") + 12);
            strSecuCode = iMkt + strSecuCode;

            //get value from redis  strCustCode="1000,1001,1002,1003..." 首次没有数据，委托后才有
            strCustCode = JedisUtil.getHashString("SECU_CUST_CODE", strSecuCode);
            if (!"".equals(strCustCode)){
                String[] szArrCustCode = strCustCode.split("\\,");
                for (int i = 0; i < szArrCustCode.length; ++i){
                    String strCustCd = szArrCustCode[i];
                    //发给下一个订阅的bolt
                    this.collector.emit(new Values(strCustCd, strMessage));
                }
            }
        }
        else if (strMsg.contains("req_order")){
            strCustCode = strMsg.substring(strMsg.indexOf("CUSTOMER") + 11, strMsg.indexOf("\"", strMsg.indexOf("CUSTOMER") + 11));
            //发给下一个订阅的bolt
            this.collector.emit(new Values(strCustCode, strMessage));
        }
        else{
            logger.error("Unknown data：" + strMsg);
            return;
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("CUST_CODE", "MESSAGE"));
    }
}
