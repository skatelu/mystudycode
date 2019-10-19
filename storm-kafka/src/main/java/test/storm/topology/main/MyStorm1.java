package test.storm.topology.main;


import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector;
import org.apache.storm.topology.TopologyBuilder;
import test.storm.topology.bolt.SuffixBolt;
import test.storm.topology.bolt.UpperBolt;
import test.storm.topology.spout.RandomWordSpout;

import java.util.Properties;

/**
 * 组织各个处理组件形成一个完整的处理流程，就是所谓的topology
 * 并且将该topology提交给storm集群去运行
 */
public class MyStorm1 {

    public static void main(String[] args) {


        TopologyBuilder builder = new TopologyBuilder();
        //设置需要写入到kafka的配置信息
        Properties props1 = new Properties();
        props1.put("bootstrap.servers","192.168.25.3:9092,192.168.25.4:9092,192.168.25.5:9092");
        props1.put("acks","1");
        props1.put("session.timeout.ms",30000);
        props1.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props1.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props1.put(ProducerConfig.CLIENT_ID_CONFIG, "helloisword1");

        //将我们的spout组件设置到topology中去
        builder.setSpout("randomspout",new RandomWordSpout());

        builder.setBolt("upperBolt",new UpperBolt()).shuffleGrouping("randomspout");

        builder.setBolt("suffixbolt",new SuffixBolt()).shuffleGrouping("upperBolt");

        KafkaBolt bolt = new KafkaBolt()
                .withProducerProperties(props1)
                .withTopicSelector(new DefaultTopicSelector("helloisword1"))
                .withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper("key", "suffix_name"));


        builder.setBolt("tokafkaBolt",bolt).shuffleGrouping("suffixbolt");



        StormTopology topology = builder.createTopology();

        //配置一些topology在集中运行时的参数
        Config config = new Config();
        //这里设置的是整个demotop锁占用的槽位数，也就是work的数量
        config.setNumAckers(4);
        config.setDebug(false);
        config.setNumAckers(0);

        if(args.length>0){
            try {
                StormSubmitter.submitTopology("testStorm",config,topology);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("testStorm",config,topology);
        }
    }

}
