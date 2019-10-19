package com.yjl.springboot.config;

import com.sun.org.apache.xml.internal.security.utils.HelperNodeList;
import com.yjl.springboot.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration 指明当前类是一个配置类：就是来代替之前的Spring配置文件
 *
 * 在配置文件中用<Bean></Bean> 标签添加组件
 */
@Configuration
public class MyAppConfig {

//    将方法的返回值添加到容器中：容器中这个组件默认的id就是方法名
    @Bean
    public HelloService helloService(){
        return new HelloService();
    }

}
