package kafka;

import hbase.HBaseDAO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import utils.PropertiesUtils;

import java.util.Arrays;

public class HbaseConsumer {

    public static void main(String[] args) {
        HbaseConsumer hbaseConsumer = new HbaseConsumer();
        //kafak消费者代码
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(PropertiesUtils.properties);
        //设置消费
        kafkaConsumer.subscribe(Arrays.asList(PropertiesUtils.getProperty("kafka.topics")));

        HBaseDAO hd = new HBaseDAO();
        //while true 轮训方式保证一直不间断的从kafka上获取数据,获取消费数据
        while (true){
            //具体获取数据的方法
            ConsumerRecords<String, String> records = kafkaConsumer.poll(500);
            for (ConsumerRecord<String, String> record : records) {
                String oriValue = record.value();
                hd.put(oriValue);
            }
        }
    }


}
