package com.yjl.springboot;

import com.yjl.springboot.bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SpringBoot的单元测试
 * @SpringBootTest   单元测试标志
 * @RunWith(SpringRunner.class)  用Spring的环境进行单元测试
 *
 *
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot02ConfigApplicationTests {
    @Autowired
    Person person;

    @Autowired
    ApplicationContext ioc;
    @Test
    public void contextLoads() {
        System.out.println(person);
    }

    @Test
    public void textHelloService(){
        boolean b = ioc.containsBean("helloService");
        System.out.println(b);
    }
}
