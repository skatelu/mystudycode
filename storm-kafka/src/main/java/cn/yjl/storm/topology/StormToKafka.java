package cn.yjl.storm.topology;

import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class StormToKafka {

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        Fields fields = new Fields("key","message");
        FixedBatchSpout spout = new FixedBatchSpout(fields, 4,
                new Values("storm", "1"),
                new Values("trident", "1"),
                new Values("needs", "1"),
                new Values("javadoc", "1")
        );

        spout.setCycle(true);
        //builder.setSpout("spout", spout, 5);
    }


}
