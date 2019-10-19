package test.storm.topology.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

public class RandomWordSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;

    //模拟一些数据
    String[] words = {"iphone","xiaomi","mate","sony","sumsung","moto","meizu"};

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("orignname"));
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        Random random = new Random();
        int index = random.nextInt(words.length);

        String name  = words[index];

        collector.emit(new Values(name));

        //每发送一个休息，休眠500ms
        Utils.sleep(1000);
    }
}
