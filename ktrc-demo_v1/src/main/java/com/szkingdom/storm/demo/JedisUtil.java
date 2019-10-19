package com.szkingdom.storm.demo;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import com.szkingdom.storm.demo.ConfigAPI.RedisProperties;

/**
 * Created by zhangch on 2018/4/23.
 */
public class JedisUtil {
    protected static ReentrantLock lockPool = new ReentrantLock();
    protected static ReentrantLock lockJedis = new ReentrantLock();

    protected static Logger logger = Logger.getLogger(JedisUtil.class);

    private static JedisPool jedisPool = null;

    //初始化Redis连接池
    public static void initialPool() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(RedisProperties.MAX_ACTIVE);
            config.setMaxIdle(RedisProperties.MAX_IDLE);
            config.setMaxWaitMillis(RedisProperties.MAX_WAIT);
            config.setTestOnBorrow(RedisProperties.TEST_ON_BORROW);
            jedisPool = new JedisPool(config, RedisProperties.ADDR_ARRAY.split(",")[0],
                    RedisProperties.PORT, RedisProperties.TIMEOUT);
        } catch (Exception e) {
            logger.error("First create JedisPool error : " + e);
            try {
                //如果第一个IP异常，则访问第二个IP
                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxTotal(RedisProperties.MAX_ACTIVE);
                config.setMaxIdle(RedisProperties.MAX_IDLE);
                config.setMaxWaitMillis(RedisProperties.MAX_WAIT);
                config.setTestOnBorrow(RedisProperties.TEST_ON_BORROW);
                jedisPool = new JedisPool(config, RedisProperties.ADDR_ARRAY.split(",")[1],
                        RedisProperties.PORT, RedisProperties.TIMEOUT, RedisProperties.AUTH);
            } catch (Exception e2) {
                logger.error("Second create JedisPool error : " + e2);
            }
        }
    }

    /**
     * 在多线程环境同步初始化
     */
    private static void poolInit() {
        lockPool.lock();
        try {
            if (jedisPool == null) {
                initialPool();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lockPool.unlock();
        }
    }

    public static Jedis getJedis() {
        lockJedis.lock();
        if (jedisPool == null) {
            poolInit();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
            logger.error("Get jedis error : " + e);
            //jedisPool.returnBrokenResource(jedis);
        } finally {
            returnResource(jedis);
            lockJedis.unlock();
        }
        return jedis;
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null && jedisPool != null) {
            jedisPool.returnResource(jedis);
        }
    }

    /**
     * 设置 String
     *
     * @param key
     * @param value
     */
    public synchronized static boolean setString(String key, String value) {
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            getJedis().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("Set key error : " + e);
            return false;
        }
    }

    /**
     * 设置 过期时间
     *
     * @param key
     * @param seconds 以秒为单位
     * @param value
     */
    public synchronized static boolean setexString(String key, int seconds, String value) {
        try {
            value = StringUtils.isEmpty(value) ? "" : value;
            getJedis().setex(key, seconds, value);
            return true;
        } catch (Exception e) {
            logger.error("Set keyex error : " + e);
            return false;
        }
    }

    /**
     * 获取String值
     *
     * @param key
     * @return value
     */
    public synchronized static String getString(String key) {
        try {
            Jedis jedis = getJedis();
            if (jedis == null || !jedis.exists(key)) {
                logger.error("getString error");
                return "";
            }

            String value = jedis.get(key);
            if (value == null) {
                return "";
            } else {
                return value;
            }
        } catch (Exception e) {
            logger.error("getString error : " + e);
            return "";
        }
    }

    /**
     * 设置hash结构值
     *
     * @param key
     * @return value
     */
    public synchronized static long setHashString(String name, String key, String value) {
        try {
            name = StringUtils.isEmpty(name) ? "" : name;
            key  = StringUtils.isEmpty(key) ? "" : key;
            value = StringUtils.isEmpty(value) ? "" : value;

            Jedis jedis = getJedis();
            if (jedis == null) {
                logger.error("setHashString error");
                return -1;
            }

            return jedis.hset(name, key, value);
        } catch (Exception e) {
            logger.error("setHashString error : " + e);
            return -1;
        }
    }

    /**
     * 获取hash结构值
     *
     * @param key
     * @return value
     */
    public synchronized static String getHashString(String name, String key) {
        try {
            name = StringUtils.isEmpty(name) ? "" : name;

            Jedis jedis = getJedis();
            if (jedis == null) {
                logger.error("getHashString error");
                return "";
            }

            String value = jedis.hget(name, key);

            if (value == null || "OK".equals(value) || "ONG".equals(value) || "PONG".equals(value)){
                return "";
            }
            else{
                return value;
            }
        } catch (Exception e) {
            logger.error("getHashString error : " + e);
            return "";
        }
    }

    /**
     * 批量设置hash结构值
     *
     * @param hashMap
     * @return value
     */
    public synchronized static String hmSetHashString(String name, Map<String, String> hashMap) {
        try {
            name = StringUtils.isEmpty(name) ? "" : name;

            Jedis jedis = getJedis();
            if (jedis == null || hashMap.isEmpty()) {
                logger.error("hmSetHashString error");
                return "";
            }

            String value = jedis.hmset(name, hashMap);
            if (value == null){
                return "";
            }
            else{
                return value;
            }
        } catch (Exception e) {
            logger.error("hmSetHashString error : " + e);
            return "";
        }
    }
}
