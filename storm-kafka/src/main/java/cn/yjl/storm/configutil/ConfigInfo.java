package cn.yjl.storm.configutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class ConfigInfo {

    protected static Logger logger = LoggerFactory.getLogger(ConfigInfo.class);

    public static String BROKER_LIST;

    /**
     * 初始化Kafka需要的配置
     * @throws Exception
     */
    public static void InitKafkaProperties() throws Exception {
        //创建输入流
        InputStream in = null;
        //创建配置文件
        Properties prop = new Properties();
        //读取配置文件properties

        try {
            //读取配置文件，将配置文件转换成字符流
            in = ReadFile.readPropFile("D:\\IDEAWorkSpace\\storm-kafka\\src\\cn\\yjl\\storm\\config\\config.properties");
            if(in != null){
                //prop加载流
                prop.load(in);
                //获取流中的信息
                BROKER_LIST = prop.getProperty("broker_list");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                try {
                    in.close();
                } catch (Exception e) {
                    logger.error("IOException in.close() ERROR [ " + e + "]\n");
                    throw e;
                }
            }
        }


    }

}
