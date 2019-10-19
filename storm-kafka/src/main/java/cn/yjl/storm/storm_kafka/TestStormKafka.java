package cn.yjl.storm.storm_kafka;

import org.apache.storm.kafka.trident.TridentKafkaStateFactory;
import org.apache.storm.kafka.trident.mapper.FieldNameBasedTupleToKafkaMapper;
import org.apache.storm.kafka.trident.selector.DefaultTopicSelector;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Properties;

public class TestStormKafka {

    public void Test1(){

        Fields fields = new Fields("word","count");

        FixedBatchSpout spout = new FixedBatchSpout(fields,4,
                new Values("storm","1"),
                new Values("trident","1"),
                new Values("needs","1"),
                new Values("javadoc","1")
        );
        spout.setCycle(true);

        TridentTopology tridentTopology = new TridentTopology();
        Stream stream = tridentTopology.newStream("spout1",spout);

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "1");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        TridentKafkaStateFactory stateFactory = new TridentKafkaStateFactory()
                .withProducerProperties(props)
                .withKafkaTopicSelector(new DefaultTopicSelector("test"))
                .withTridentTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper("word", "count"));

        //stream.partitionPersist(stateFactory, fields, new TridentKafkaStateUpdater(), new Fields());
    }
}
