package com.yjl.kafka_syllabus.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

public class CustomConsumer1 {

    public static void main(String[] args) {

        //1.配置消费者属性
        Properties prop = new Properties();
        //定义kafka的服务器地址
        prop.put("bootstrap.servers","192.168.25.3:9092,192.168.25.4:9092,192.168.25.5:9092");
        //设置消费组
        prop.put("group.id","g1");
        //是否自动确认offset
        prop.put("enable.auto.commit","true");
         //key的饭序列化
        prop.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        //value的反序列化
        prop.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

        //2.创建消费者实例
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(prop);

        //4.守护线程，当虚拟机开始shuntDown的时候，会开启一个子线程执行此操作，释放资源,开启一个新的线程来解决
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if(consumer != null) consumer.close();
            }
        }));

        //订阅消息主题
        consumer.subscribe(Arrays.asList("mygrils"));
        //3.拉消息
        while (true){
            //消费者去paritition中拉取数据
            ConsumerRecords<String,String> records = consumer.poll(100);

            for ( ConsumerRecord record: records) {
                System.out.println(record.offset()+"--"+record.key()+"--"+record.value());
            }

        }

    }
}
