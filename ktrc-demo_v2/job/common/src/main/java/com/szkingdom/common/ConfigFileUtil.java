/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: ConfigFileUtil
 * Author:   zmh
 * Date:     2018/7/5 17:52
 * Description: 配置文件读取
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szkingdom.common;

import java.io.*;
import java.util.Properties;

/**
 * 〈配置文件读取〉
 * @author zmh
 * @create 2018/7/5
 * @since 1.0.0
 */
public class ConfigFileUtil {

    public static Properties properties = null;

    /**
     * 读取properties配置文件，jar包外配置：linux下，配置文件与jar包放在同一目录下即可
     * @param filepath 文件 如： filepath = "/druid.properties"
     * @return InputStream 或 null
     */
    public static InputStream readPropFileOutJar(String filepath) throws Exception {
        InputStream inputStream = null;
        try {
            //filepath = System.getProperty("user.dir") + filepath;
            File file = new File(filepath);
            inputStream = new BufferedInputStream(new FileInputStream(file));
            return inputStream;
        }catch(Exception e){
            //加log打印error信息
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 读取properties配置文件，jar包内配置
     * @param filepath 文件 如：filepath = "druid.properties"
     * @return InputStream 或 null
     */
    public static InputStream readPropFileInJar(String filepath) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResource(filepath).openStream();
            return inputStream;
        }catch(Exception e){
            //加log打印error信息
            e.printStackTrace();
            throw e;
        }
    }

}
