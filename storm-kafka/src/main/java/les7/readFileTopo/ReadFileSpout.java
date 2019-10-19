package les7.readFileTopo;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;

import java.util.Map;

public class ReadFileSpout implements IRichSpout {
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        //初始化方法
    }

    @Override
    public void close() {
        //关闭topo
    }

    @Override
    public void activate() {
        //激活topo
    }

    @Override
    public void deactivate() {
        //停用topo
    }

    @Override
    public void nextTuple() {
        //接收外部数据内容
    }

    @Override
    public void ack(Object o) {
        //如果开启Acker成功执行Tuple后会回调该方法，告知storm框架，该tuple已经成功执行
    }

    @Override
    public void fail(Object o) {
        //如果开启Acker 当失败执行Tuple后会回调该方法，告知storm框架，该tuple已经执行失败
        //以便我们手工编码实现失败重发，并控制重发次数
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        //定义输出的列名
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        //可以在代码里设置一些属性。该方法基本是废弃不用的
        return null;
    }
}
