package cn.jyl.kafka;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerDemo {

    public static void main(String[] args) throws Exception{

        Properties props = new Properties();
        props.put("bootstrap.servers","192.168.25.7:9092");
        props.put("retries",0);
        props.put("batch.size",16384);
        props.put("linger.ms",1);
        props.put("buffer.memory","33554432");
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //将设置的properties添加进kafka中
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        //发送业务消息
        //读取消息，读取内存数据库 读socket端口
        for (int i = 0; i < 10; i++) {
            Thread.sleep(500);
            producer.send(new ProducerRecord<>(
                    "mygrils","你好 "+i+"times"
            ));
            System.out.println(i);
        }

    }

}
