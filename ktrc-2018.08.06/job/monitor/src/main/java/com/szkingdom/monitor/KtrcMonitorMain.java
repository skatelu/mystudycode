/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: KtrcMonitorMain
 * Author:   ZhangChangHong
 * Date:     2018/7/31
 * Description: monitor模块启动入口函数
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.monitor;
import com.szkingdom.common.InitConfig;
import com.szkingdom.common.ZtsConstantUtil;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

public class KtrcMonitorMain {
    protected static Logger logger = LoggerFactory.getLogger(KtrcMonitorMain.class);

    public static void main(String[] args) {
        /**
         * init config
         */
        try {
            InitConfig.Init();
        }catch (Exception e){
            return;
        }

        /**
         * topology
         */
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        /**
         * init setSpout

        if (!"".equals(ConstantUtil.TOPIC_OPT)){
            String[] arrOptTopic = ConstantUtil.TOPIC_OPT;
            for (int i = 0; i < arrOptTopic.length; ++i){
                String optTopicName = arrOptTopic[i];

                Properties props = new Properties();
                props.put("group.id", optTopicName);

                KafkaSpoutConfig<String, String> kfkSpoutCfg = KafkaSpoutConfig
                        .builder(InitConfig.KAFKA_BROKER, optTopicName)
                        .setProp(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,30000)
                        .setProp(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getCanonicalName())
                        .setProp(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getCanonicalName())
                        .setProp(props)
                        .setProp(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
                        .setProp(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,1000)
                        //.setProp(ConsumerConfig.GROUP_ID_CONFIG, ConfigAPI.KafkaProperties.HQ_GROUP)
                        .setOffsetCommitPeriodMs(1000)//控制spout多久向kafka提交一次offset
                        .setMaxUncommittedOffsets(200)
                        .setFirstPollOffsetStrategy(KafkaSpoutConfig.FirstPollOffsetStrategy.LATEST)
                        .build();

                if (ConstantUtil.TOPIC_OPTION_TRADE.equals(optTopicName)){
                    // 订阅交易类数据
                    topologyBuilder.setSpout(ConstantUtil.TOPIC_OPTION_TRADE, new KafkaSpout<>(kfkSpoutCfg), 1);
                }else if (ConstantUtil.TOPIC_OPTION_QUOTATION.equals(optTopicName)){
                    //订阅期权行情类数据
                    topologyBuilder.setSpout(ConstantUtil.TOPIC_OPTION_QUOTATION, new KafkaSpout<>(kfkSpoutCfg), 1);
                } else if (ConstantUtil.TOPIC_SPOT_QUOTATION.equals(optTopicName)){
                    //订阅现货行情类数据
                    topologyBuilder.setSpout(ConstantUtil.TOPIC_SPOT_QUOTATION, new KafkaSpout<>(kfkSpoutCfg), 1);
                }
                topologyBuilder.setSpout("TOPIC_FUNDASSET", new KafkaSpout<>(kfkSpoutCfg), 1);

            }
        }
        */
        /**
         * ETF差额扣款kafkaSpout设置
         */
        String[] arrOptTopic =  new String[]{"TOPIC_FUNDASSET","TOPIC_ECCODESIGN"};
        for (int i = 0; i < arrOptTopic.length; ++i) {
            String optTopicName = arrOptTopic[i];

            Properties props = new Properties();
            props.put("group.id", optTopicName);

            KafkaSpoutConfig<String, String> kfkSpoutCfg = KafkaSpoutConfig
                    .builder("192.168.25.7:9092", optTopicName)
                    .setProp(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000)
                    .setProp(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName())
                    .setProp(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName())
                    .setProp(props)
                    .setProp(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
                    .setProp(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000)
                    .setOffsetCommitPeriodMs(1000)//控制spout多久向kafka提交一次offset
                    .setMaxUncommittedOffsets(200)
                    .setFirstPollOffsetStrategy(KafkaSpoutConfig.FirstPollOffsetStrategy.LATEST)
                    .build();
            topologyBuilder.setSpout(optTopicName, new KafkaSpout<>(kfkSpoutCfg), 1);
        }
        /**
         * ETF差额扣款 拓扑设置
         * 只使用了维护bolt(maintain bolt)
         */
        String maintainRealtimeDataBoltName = MaintainRealtimeDataBolt.class.getSimpleName();
        topologyBuilder.setBolt(maintainRealtimeDataBoltName,
                new MaintainRealtimeDataBolt(), 1)
                .shuffleGrouping(ZtsConstantUtil.TOPIC_ECCODESIGN)
                .shuffleGrouping(ZtsConstantUtil.TOPIC_FUNDASSET);

        String simpleName = KtrcMonitorMain.class.getSimpleName();
        Config stormConfig = new Config();
        stormConfig.setNumWorkers(8);
        stormConfig.setDebug(true);

        if (args.length > 0) {
            try {
                StormSubmitter.submitTopology(simpleName, stormConfig, topologyBuilder.createTopology());
            } catch (Exception e) {
                logger.error("KtrcMonitorMain 提交拓扑失败", e);
                return;
            }
        } else {
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology(simpleName, stormConfig, topologyBuilder.createTopology());
        }
    }
}
