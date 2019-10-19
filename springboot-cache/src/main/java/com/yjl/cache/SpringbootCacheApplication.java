package com.yjl.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @MapperScan("com.yjl.cache.mapper")  mybatis 扫描mapper文件
 * @EnableCaching  开启基于缓存注释
 *
 * 搭建基本环境
 * 整合mybatis操作数据库
 *      1、配置数据源信息
 *      2、使用注解版MyBatis
 *          1、@MapperScan 指定扫描的mapper接口所在包
 * 二、快速体验缓存
 *      步骤：
 *          1、开启基于注解的缓存 @EnableCaching
 *          2、标注缓存注解即可
 *              @Cacheable
 *              @CacheEvict
 *              @CachePut
 *
 *
 * 默认使用的是 ConcurrentMapCacheManager == ConcurrentMapCache;将数据保存在createConcurrentMap<Object,Object>
 * 整合Redis
 *  1、安装redis；
 *  2、引入redis的start
 *  3、配置redis
 *  4、测试原理：
 *      原理：CacheManager === Cache 缓存组件来实际给缓存中存取数据
 *      1、引入redis的start，容器中保存的是 RedisCacheManager；
 *      2、RedisCacheManager 帮我们创建 RedisCache 来作为缓存组建
 *      3、默认保存数据 k-v 都是Object ； 利用序列化保存；如何保存JSON
 *          引入了Redis的starter，cacheManager变为 RedisCacheManager
 *
 */
@MapperScan("com.yjl.cache.mapper")
@SpringBootApplication
@EnableCaching
public class SpringbootCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootCacheApplication.class, args);
    }

}
