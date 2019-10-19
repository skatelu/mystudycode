/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: InitConfig
 * Author:   ZhangChangHong
 * Date:     2018/7/31
 * Description: 初始化配置类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class InitConfig {
    protected static Logger logger = LoggerFactory.getLogger(InitConfig.class);

    public static String KAFKA_BROKER;
    public static String INTERVAL_TIME;

    public static void Init() throws Exception {
        InputStream in = null;
        Properties prop = new Properties();

        try {
            //读取配置文件properties
            in = ConfigFileUtil.readPropFileOutJar(ConstantUtil.CONFIG);
            if (in != null){
                prop.load(in);

                // kafka broker连接地址
                KAFKA_BROKER = prop.getProperty("kafka_broker");
                // 定时Bolt定时时间
                INTERVAL_TIME  = prop.getProperty("interval_time");
            }
        } catch (Exception e) {
            logger.error("Exception InitConfig Init() : [ " + e + "] \n");
            throw e;
        }finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("Exception InitConfig in.close() IOException : [ " + e + "] \n");
                    throw e;
                }
            }
        }
    }
}
