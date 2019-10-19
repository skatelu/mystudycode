/**
 * Copyright (C), 2018, 深圳市金证科技股份有限公司
 * FileName: Tools
 * Author:   ZhangChangHong
 * Date:     2018/7/24
 * Description: 工具类
 * Version:  0.1.0
 * History:
 * <author>          <time>          <version>          <desc>
 */

package com.szkingdom.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;

public class Tools {
    protected static Logger logger = LoggerFactory.getLogger(Tools.class);

    /**
     * 深度复制
     * @param object
     * @return object
     */
    public static Object deepClone(Object object) {
        if (object == null) {
            return null;
        }

        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        ByteArrayOutputStream byteOutStrem = null;
        ByteArrayInputStream byteInputStrem = null;
        try {
            byteOutStrem = new ByteArrayOutputStream();
            out = new ObjectOutputStream(byteOutStrem);
            out.writeObject(object);
            byteInputStrem = new ByteArrayInputStream(byteOutStrem.toByteArray());
            in = new ObjectInputStream(byteInputStrem);
            return in.readObject();
        } catch (Exception e) {
            logger.error("深度复制异常", e);
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("深度复制IO异常", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("深度复制IO异常", e);
                }
            }
        }
    }
}
