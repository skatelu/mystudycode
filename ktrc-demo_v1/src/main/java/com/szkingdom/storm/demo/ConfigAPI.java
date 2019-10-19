package com.szkingdom.storm.demo;

/**
 * Created by zhangch on 2018/4/20.
 */
public class ConfigAPI {
    public interface KafkaProperties {
        String ZK = "192.168.50.140:2181,192.168.50.141:2181,192.168.202.41:2181";
        String GROUP_ID = "test_group1";
        String BROKER_LIST = "192.168.50.140:9092,192.168.50.141:9092,192.168.202.41:9092";
        int BUFFER_SIZE = 64 * 1024;
        int TIMEOUT = 20000;
        int INTERVAL = 10000;
    }

    public interface KcxpProperties {
        String Ip = "192.168.50.18";
        int Port = 21000;
        String Username = "KCXP00";
        String Password = "888888";

        String Hq_Queue = "req_hq";
        String HQ_TOPIC = "REQ_HQ";

        String Order_Queue = "req_order";
        String ORDER_TOPIC = "REQ_ORDER";
    }

    public interface RedisProperties {
        /**
         * Redis服务器IP
         */
        String ADDR_ARRAY = "192.168.202.42,192.168.202.42";

        /**
         * Redis的端口号
         */
        int PORT = 6379;

        /**
         * 访问密码
         */
        String AUTH = "";

        /**
         * 可用连接实例的最大数目，默认值为8；
         * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
         */
        int MAX_ACTIVE = 8;

        /**
         * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8
         */
        int MAX_IDLE = 8;

        /**
         * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
         */
        int MAX_WAIT = 30000;

        /**
         * 超时时间
         */
        int TIMEOUT = 300000;

        /**
         * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
         */
        boolean TEST_ON_BORROW = false;

        /**
         * redis过期时间,以秒为单位
         */
        int EXRP_HOUR = 60 * 60;            //一小时
        int EXRP_DAY = 60 * 60 * 24;        //一天
        int EXRP_MONTH = 60 * 60 * 24 * 30; //一个月
    }
}
