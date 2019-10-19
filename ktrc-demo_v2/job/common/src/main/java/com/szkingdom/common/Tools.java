package com.szkingdom.common;

import java.io.*;

/**
 * @author zhangch
 * @date 2018/7/13 14:49
 */
public class Tools {

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
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
