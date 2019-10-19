package cn.yjl.storm.configutil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ReadFile {

    public static InputStream readPropFile(String path) throws Exception{
        BufferedInputStream inputstream = null;

        try {
            File file = new File(path);

            inputstream = new BufferedInputStream(new FileInputStream(file));
            return inputstream;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
