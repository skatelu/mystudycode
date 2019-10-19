package cn.yjl.stormdemo;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

/**
 * 这个方法是Spout：拓扑的消息源，用来接收消息和发送消息的
 */
public class RandomWordSpout extends BaseRichSpout {
//这个方法是storm用来发送数据的
    private SpoutOutputCollector collector;

//    模拟一些数据
    String[] words={"iphone","xiaomi","mate","sony","sumsung","moto","meizu"};

    /**
     * 不断的往下一个组件发送tuple消息
     * 这里面是该spout组建的核心逻辑
     */
    @Override
    public void nextTuple() {
        //可以从kafka消息队列中拿到数据，简便起见，我们从words数组中随机挑选一个商品名发出去
        Random random = new Random();
        int index = random.nextInt(words.length);

        //通过随机数拿到一个商品名
        String godName = words[index];

        //将商品名封装成tuple，发送消息给下一个组件
        collector.emit(new Values(godName));

        //每法送一个消息，休眠500ms
        Utils.sleep(500);
    }

    /**
     * 初始化方法，在spout组件实例化时调用一次
     * @param map
     * @param topologyContext
     * @param spoutOutputCollector
     */
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = collector;
    }

    /**
     * 声明本spout组件发送出去的tuple中的数据的字段名
     * @param outputFieldsDeclarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("orignname"));
    }
}
