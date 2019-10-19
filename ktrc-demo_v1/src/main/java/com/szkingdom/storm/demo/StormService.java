package com.szkingdom.storm.demo;

import kafka.api.OffsetRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Created by zhangch on 2018/4/20.
 */
public class StormService {
    public static void main(String[] args) {
        /**
         * init jedis pool
         */
        JedisUtil.initialPool();

        TopologyBuilder topologyBuilder = new TopologyBuilder();
        BrokerHosts hosts = new ZkHosts(ConfigAPI.KafkaProperties.ZK);
        String zkRoot = "/storm";
        String id = StormService.class.getSimpleName();
        SpoutConfig configHq = new SpoutConfig(hosts, ConfigAPI.KcxpProperties.HQ_TOPIC, "", id + "Hq");
        configHq.startOffsetTime = OffsetRequest.LatestTime();
        configHq.useStartOffsetTimeIfOffsetOutOfRange = true;

        SpoutConfig configOrder = new SpoutConfig(hosts, ConfigAPI.KcxpProperties.ORDER_TOPIC, "", id + "Order");
        configOrder.startOffsetTime = OffsetRequest.LatestTime();
        configOrder.useStartOffsetTimeIfOffsetOutOfRange = true;

        String dataBoltName = DataManagerBolt.class.getSimpleName();
        String maintBoltName = MaintRatioRiskBolt.class.getSimpleName();
        String freqBoltName = FreqOrdEsperBolt.class.getSimpleName();
        String convertBoltName = BoltConvert.class.getSimpleName();
        String storeBoltName = StoreToRedisBolt.class.getSimpleName();

        //取行情，委托数据
        topologyBuilder.setSpout("HQ_TOPIC", new KafkaSpout(configHq));
        topologyBuilder.setSpout("ORDER_TOPIC", new KafkaSpout(configOrder));

        // DataManagerBolt，订阅处理数据,行情随机混排，委托同一个bolt任务处理 6并行数
        topologyBuilder.setBolt(dataBoltName, new DataManagerBolt(), 1)
                .setNumTasks(1).shuffleGrouping("HQ_TOPIC")
                .shuffleGrouping("ORDER_TOPIC");

        // FreqOrdEsperBolt 订阅order_topic
        topologyBuilder.setBolt(convertBoltName, new BoltConvert(), 1).shuffleGrouping("ORDER_TOPIC");
        topologyBuilder.setBolt(freqBoltName, new FreqOrdEsperBolt(), 1).fieldsGrouping(convertBoltName,new Fields("CUST_CODE"));

        // MaintRatioRiskBolt，订阅处理DataManagerBolt数据
        topologyBuilder.setBolt(maintBoltName, new MaintRatioRiskBolt(), 12).setNumTasks(12).shuffleGrouping(dataBoltName);

        // StoreToRedisBolt，订阅处理maintBoltName,freqBoltName数据
        topologyBuilder.setBolt(storeBoltName, new StoreToRedisBolt(), 12)
                .setNumTasks(12).shuffleGrouping(maintBoltName)
                .shuffleGrouping(freqBoltName);

        String simpleName = StormService.class.getSimpleName();
        if (true/*args.length > 0*/) {
            try {
                Config stormConfig = new Config();
                stormConfig.setNumWorkers(4);//设置4个worker

                StormSubmitter.submitTopology(simpleName, stormConfig, topologyBuilder.createTopology());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology(simpleName, new Config(), topologyBuilder.createTopology());
        }
    }
}
