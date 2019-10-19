package com.yjl.cache;

import com.yjl.cache.bean.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootCacheApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate; //操作字符串

    @Autowired
    RedisTemplate redisTemplate; // 操作k、v 都是对象

    @Autowired
    RedisTemplate<Object,Employee> empRedisTemplate;

    /**
     * String(字符串)\ list(列表) | Set（集合）| Hash（散列） | ZSet(有序集合)
     *
     * stringRedisTemplate.opsForValue()[String(字符串)]
     * stringRedisTemplate.opsForList()[list(列表)]
     * stringRedisTemplate.opsForSet()[Set（集合）]
     */
    @Test
    public void test01(){
//        给redis中保存数据
        stringRedisTemplate.opsForValue().append("msg", "hello world");
    }

    @Test
    public void test02(){
//        测试保存对象
//        默认如果保存对象，使用jdk序列化机制，序列化后的数据保存到redis中
//        redisTemplate.opsForValue().set("emp-01",new Employee());
        // 1、将数据以JSON的形式
        // 2、redisTemplate默认的序列化规则；改变默认的序列化规则
        empRedisTemplate.opsForValue().set("emp-01",new Employee(1,"zhangsan","nihao",2,2));
    }
    @Test
    public void contextLoads() {



    }

}
