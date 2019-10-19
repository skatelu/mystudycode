package cn.yjl.stormdemo;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * bolt组件 用来处理输入的流式信息的业务逻辑
 */
public class SuffixBolt extends BaseBasicBolt {

    private FileWriter fileWriter = null;

    /**
     * 在bolt组件初始化时只会被调用一次
     * @param stormConf
     * @param context
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        try {
            new FileWriter("/usr/local/zookeeper/"+ UUID.randomUUID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        //先拿到上一个组件发送过来的商品名称
        String upper_name = tuple.getString(0);
        String suffix_name = upper_name + "_itisok";
        //为上一个组件发送过来的商品名称添加后缀
        try {
            fileWriter.write(suffix_name);
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 本bolt：拓扑的逻辑单元 已经不需要发送tuple消息到下一个组件，所以不需要声明tuple字段
     * @param declarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
