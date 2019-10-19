package com.yjl.kafka_syllabus.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CustomProducer1 {

    public static void main(String[] args) {
        //1.配置生产者属性
        Properties prop = new Properties();//其实就是一个String，String的Map集合
        //配置kafka集群节点的地址，可以是多个
        prop.put("bootstrap.servers","192.168.25.3:9092,192.168.25.4:9092,192.168.25.5:9092");
        //配置发送消息是否等待应答acsk机制
        prop.put("acks","all");
        //配置消息发送失败的重试,消息发送最大尝试次数
        prop.put("retries","0");
        //一批消息处理大小 16KB
        prop.put("batch.size","16384");
        //设置批量处理数据的延迟，单位：ms与batch.size配额使用，5毫秒，5毫秒就对数据批量处理，5毫秒之内的数据会缓存在batch。size中
        prop.put("linger.ms","5");
        //设置内存缓冲区的大小，这个是消息在写入.log的文件本地磁盘之前，存入内存中，默认32M
        prop.put("buffer.memory","33554432");
        //数据在发送之前，一定要序列化
        prop.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        //为Producer注册拦截器，需要知道的是拦截器是顺序执行的
        List<String> inList = new ArrayList<String>();
        //注意，这个list里面需要放interceptor的全类名
        inList.add("com.yjl.kafka_syllabus.interceptor.TimeInterceptor");
        inList.add("com.yjl.kafka_syllabus.interceptor.CounterInterceptor");

        prop.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,inList);

        //2.实例化kafkaProducer
        KafkaProducer<String,String> producer = new KafkaProducer<>(prop);



        for (int i = 0; i < 50; i++) {
        //3.调用Producer的send方法，进行消息推送,每条待发送的消息，都必须封装为一个record对象
            producer.send(new ProducerRecord<String,String>("mygrils","hello"+i));
        }
        //4.close以释放资源
        producer.close();
    }

}
