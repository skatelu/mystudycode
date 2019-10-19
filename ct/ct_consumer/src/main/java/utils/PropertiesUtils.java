package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载配置文件
 */
public class PropertiesUtils {

    public static Properties properties = null;

    static {
        InputStream is = ClassLoader.getSystemResourceAsStream("hbase_consumer.properties");
        properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据传入的key 获得锁需要的属性
     * @param key
     * @return
     */
    public static String getProperty(String key){
        return properties.getProperty(key);
    }


}
