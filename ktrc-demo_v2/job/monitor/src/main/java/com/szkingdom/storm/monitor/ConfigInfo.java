package com.szkingdom.storm.monitor;

import java.io.*;
import java.util.Properties;
import com.szkingdom.common.ConfigFileUtil;
import com.szkingdom.common.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangch
 * @date 2018/7/3 20:48
 */
public class ConfigInfo {

    protected static Logger logger = LoggerFactory.getLogger(ConfigInfo.class);

    public String ORDER_GROUP;
    public String HQ_GROUP;
    public static String BROKER_LIST;

    public static void InitKafkaProperties() throws Exception {
        InputStream in = null;

        Properties prop = new Properties();
        try {
            //读取配置文件properties
            in = ConfigFileUtil.readPropFileOutJar(ConstantUtil.CONFIG);
            if (in != null){
                prop.load(in);
                BROKER_LIST = prop.getProperty("broker_list");

            }
        } catch (Exception e) {
            logger.error("Exception ConfigFileUtil.readPropFileOutJar() ERROR [ " + e + "]\n");
            throw e;
        }finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("IOException in.close() ERROR [ " + e + "]\n");
                    throw e;
                }
            }
        }
    }
}
