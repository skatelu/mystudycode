package com.szkingdom.storm.monitor;

//import kafka.api.OffsetRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

import java.util.Properties;
import com.szkingdom.common.ConstantUtil;

/**
 * @author zhangch
 * @date 2018/7/3 11:11
 */

public class StormMonitorMain {
    public static void main(String[] args) {
        /**
         * init config
         */
        try {
            ConfigInfo.InitKafkaProperties();
        }catch (Exception e){
            return;
        }

        /**
         * init cache
         */
        try {
            InitialCache.initOptCache();
        }catch (Exception e){
            return;
        }

        /**
         * topology
         */
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        /**
         * init setSpout
         */
        if (!"".equals(ConstantUtil.TOPIC_OPT)){
            String[] arrOptTopic = ConstantUtil.TOPIC_OPT;
            for (int i = 0; i < arrOptTopic.length; ++i){
                String optTopicName = arrOptTopic[i];

                Properties props = new Properties();
                props.put("group.id", optTopicName);

                KafkaSpoutConfig<String, String> kfkSpoutCfg = KafkaSpoutConfig
                        .builder(ConfigInfo.BROKER_LIST, optTopicName)
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

                if (optTopicName.contains(ConstantUtil.TOPIC_OPTION_TRADE)){
                    // 订阅交易类数据
                    topologyBuilder.setSpout(ConstantUtil.TOPIC_OPTION_TRADE, new KafkaSpout<>(kfkSpoutCfg), 1);
                }else if (optTopicName.contains(ConstantUtil.TOPIC_OPTION_QUOTATION)){
                    //订阅行情类数据
                    topologyBuilder.setSpout(ConstantUtil.TOPIC_OPTION_QUOTATION, new KafkaSpout<>(kfkSpoutCfg), 1);
                }
            }
        }

        String bizDataMgrBoltName = BizDataMgrBolt.class.getSimpleName();
        String optSigAccPosiLmtIndicatorsBoltName = OptSigAccPosiLmtIndicatorsBolt.class.getSimpleName();
        String writeBoltName = WriteToDbBolt.class.getSimpleName();

        topologyBuilder.setBolt(bizDataMgrBoltName, new BizDataMgrBolt(), 1)
                .shuffleGrouping(ConstantUtil.TOPIC_OPTION_TRADE)
                .shuffleGrouping(ConstantUtil.TOPIC_OPTION_QUOTATION);

        topologyBuilder.setBolt(optSigAccPosiLmtIndicatorsBoltName,
                new OptSigAccPosiLmtIndicatorsBolt(), 1)
                .shuffleGrouping(bizDataMgrBoltName);

        topologyBuilder.setBolt(writeBoltName, new WriteToDbBolt(), 1)
                .shuffleGrouping(optSigAccPosiLmtIndicatorsBoltName);


        String simpleName = StormMonitorMain.class.getSimpleName();
        Config stormConfig = new Config();
        stormConfig.setDebug(true);
        //stormConfig.setMaxSpoutPending(20000);
        //stormConfig.setMessageTimeoutSecs(60*60*24);
        //stormConfig.setNumAckers(8);
        stormConfig.setNumWorkers(8);//设置worker
        //BasicConfigurator.configure();

        if (args.length > 0) {
            try {
                StormSubmitter.submitTopology(simpleName, stormConfig, topologyBuilder.createTopology());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology(simpleName, stormConfig, topologyBuilder.createTopology());
        }
    }
}
