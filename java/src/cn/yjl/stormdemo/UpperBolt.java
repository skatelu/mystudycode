package cn.yjl.stormdemo;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * Bolts:拓扑的处理逻辑单元
 * 具体执行通过spout进入的数据的业务处理
 */
public class UpperBolt extends BaseBasicBolt {

    //业务处理逻辑
    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        //先取到上一个组建传递过来的数据，数据在tuple里面
        String godName = tuple.getString(0);
        //将商品名转换成大写
        String godName_upper = godName.toUpperCase();
        //将转换完成的商品名发送出去
        collector.emit(new Values(godName_upper));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("uppername"));
    }
}
