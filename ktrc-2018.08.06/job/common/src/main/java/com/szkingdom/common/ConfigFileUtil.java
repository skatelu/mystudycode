/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: ConfigFileUtil
 * Author:   ZhangMaohua
 * Date:     2018/7/23
 * Description: 读取Jar包内/包外配置文件方法封装
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.szkingdom.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ConfigFileUtil {
    
    /**
     * readPropFileOutJar: 读取properties配置文件，jar包外配置：linux下，配置文件放在相应目录下即可
     * @param filepath：配置文件及路径 如： filepath = "/config/druid.properties"
     * @return InputStream 或 null
     * @exception  throws 读取文件异常
     *
     */
    public static InputStream readPropFileOutJar(String filepath) throws Exception {
        InputStream inputStream = null;
        try {
            filepath = System.getProperty("user.dir") + filepath;
            File file = new File(filepath);
            inputStream = new BufferedInputStream(new FileInputStream(file));
            return inputStream;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * readPropFileInJar: 读取properties配置文件，jar包内配置
     * @param filepath: 配置文件 如：filepath = "druid.properties"
     * @return InputStream 或 null
     * @exception  throws 读取文件异常
     */
    public static InputStream readPropFileInJar(String filepath) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResource(filepath).openStream();
            return inputStream;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
