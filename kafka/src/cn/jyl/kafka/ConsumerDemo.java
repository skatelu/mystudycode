package cn.jyl.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemo {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers","192.168.25.7:9092");
        props.put("group.id","connect-test1");
        props.put("enable.auto.commit","false");
        props.put("auto.commit.intervals.ms","100");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer consumer = new KafkaConsumer(props);

        consumer.subscribe(Arrays.asList("connect-test1"));

        String a = null;
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(100);
            for(ConsumerRecord<String,String> record : records){
                System.out.println(record.offset()+"----"+"-----"+record.value());
                try {
                    //a= record.toString();
                    //a = CharMatcher.INVISIBLE.removeFrom(a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(a);
            }

        }

    }
}
