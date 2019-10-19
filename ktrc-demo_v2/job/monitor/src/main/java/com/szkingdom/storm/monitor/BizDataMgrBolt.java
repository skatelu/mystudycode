package com.szkingdom.storm.monitor;

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

/**
 * @author zhangch
 * @date 2018/7/4 16:35
 */
public class BizDataMgrBolt extends BaseRichBolt {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final long serialVersionUID = 294633099452558992L;

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        //获得字节数组，再将其封装到string类中
        String strMsg = String.valueOf(input.getValue(4));
        if (strMsg.length() <= 0){
            this.collector.fail(input);
            //logger.error("BizDataMgrBolt strMsg.length()<=0 ack fail ! \n");
            return;
        }

        // 行情或者委托分别处理
        if (strMsg.contains("QUOTATION")){

        }
        else if (strMsg.contains("TRADE")){

            //发给下一个订阅的bolt
            this.collector.emit(input, new Values("TRADE", strMsg));
        }
        else {
            //logger.error("BizDataMgrBolt Unknown data：" + strMsg);
            this.collector.fail(input);
            return;
        }

        this.collector.ack(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("BIZ_TYPE", "MESSAGE"));
    }
}
