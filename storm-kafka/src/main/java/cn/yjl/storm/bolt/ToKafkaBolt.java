package cn.yjl.storm.bolt;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.kafka.bolt.mapper.FieldNameBasedTupleToKafkaMapper;
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.util.Map;
import java.util.Properties;

public class ToKafkaBolt extends BaseBasicBolt {

    Properties props1 = null;
    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        //设置需要写入到kafka的配置信息
        Properties props1 = new Properties();
        props1.put("bootstrap.servers","192.168.25.3:9092,192.168.25.4:9092,192.168.25.5:9092");
        props1.put("acks","1");
        props1.put("session.timeout.ms",30000);
        props1.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props1.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props1.put(ProducerConfig.CLIENT_ID_CONFIG, "helloisword1");

    }
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {

        KafkaBolt bolt = new KafkaBolt()
                .withProducerProperties(props1)
                .withTopicSelector(new DefaultTopicSelector("helloisword1"))
                .withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper("key", "suffix_name"));

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

}
