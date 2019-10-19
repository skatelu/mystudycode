package cn.yjl.storm.topology;

import cn.yjl.storm.bolt.WordSpliter;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

import java.util.Properties;

public class KafkastormMain {



    public static void main(String[] args) {
        //创建topologyBuilder，用来创建storm的topology
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        String topic = "TOPIC_FUNDASSET";
        Properties props = new Properties();
        props.put("group.id",topic);//kafka的分组id
        //从kafka0.8x版本以后，storm链接kafka都需要用storm-kafka-client进行链接
        //storm从kafka中获取消息需要用到kafkaSpout
        KafkaSpoutConfig<String, String> kfkSpoutCfg = KafkaSpoutConfig
                .builder("192.168.25.3:9092,192.168.25.4:9092,192.168.25.5:9092", "TOPIC_FUNDASSET")//需要接收的broker信息，及对应的topic名称
                .setProp("session.timeout.ms",30000)//kafka与zookeeper的通信过期时间
                .setProp("key.deserializer", StringDeserializer.class.getCanonicalName())
                .setProp("value.deserializer", StringDeserializer.class.getCanonicalName())
                .setProp(props)
                .setProp("enable.auto.commit","true")
                .setProp("auto.commit.interval.ms",1000)
                .setOffsetCommitPeriodMs(1000)//控制spout多久向kafka提交一次offset
                .setMaxUncommittedOffsets(200)
                .setFirstPollOffsetStrategy(KafkaSpoutConfig.FirstPollOffsetStrategy.LATEST)//从最新的offset开始读取topic中的消息
                .build();

        /*ToKafkaBolt toKafkaBolt = new ToKafkaBolt();
        KafkaBolt kafkaBolt = toKafkaBolt.getKafkaBolt();*/
/*        KafkaSpoutConfig.Builder<String, String> kafkaBuilder = KafkaSpoutConfig.builder("192.168.25.3:9092,192.168.25.4:9092,192.168.25.5:9092", "my-test");
        kafkaBuilder.setGroupId("testgroup");
        KafkaSpoutConfig<String, String> build = kafkaBuilder.build();

        KafkaSpout<String, String> kafkaSpout = new KafkaSpout<>(build);*/


        topologyBuilder.setSpout("KafkaStormMain",new KafkaSpout<String, String>(kfkSpoutCfg),1);

        topologyBuilder.setBolt("word-spilter",new WordSpliter()).shuffleGrouping("KafkaStormMain");
        //设置需要写入到kafka的配置信息
        Properties props1 = new Properties();
        props1.put("bootstrap.servers","192.168.25.3:9092,192.168.25.4:9092,192.168.25.5:9092");
        props1.put("acks","1");
        props1.put("session.timeout.ms",30000);
        props1.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props1.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props1.put(ProducerConfig.CLIENT_ID_CONFIG, "test2");

        //将storm中的数据写入kafka 有两种方法，一种是用kafkaStorm
        KafkaBolt bolt = new KafkaBolt()
                .withProducerProperties(props1)
                .withTopicSelector(new DefaultTopicSelector("test2"))
                .withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper("key", "word"));
        /*KafkaBolt bolt = new KafkaBolt()
                .withProducerProperties(props1)
                .withTopicSelector(new FieldNameTopicSelector("hello","TOPIC_ECCODESIGN"))
                .withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper("key", "hello"));*/

        topologyBuilder.setBolt("forwardToKafka", bolt, 2).shuffleGrouping("word-spilter");
        //topologyBuilder.setBolt("writer",new WriterBolt(),4).shuffleGrouping("word-spilter");

        Config config = new Config();
        config.setDebug(false);
        config.setNumWorkers(4);
        config.setNumAckers(0);

        if(args.length>0){
            try {
                StormSubmitter.submitTopology("KafkaStormMain",config,topologyBuilder.createTopology());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("KafkaStormMain",config,topologyBuilder.createTopology());
        }

    }

}
