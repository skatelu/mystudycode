package com.yjl.mapreduce.compress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.CompressionInputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class TestCompress {

    public static void main(String[] args) throws Exception{
        //1测试压缩
//        compress("d:/hello.txt","org.apache.hadoop.io.compress.BZip2Codec");
//        compress("d:/hello.txt","org.apache.hadoop.io.compress.GzipCodec");
        //compress("d:/hello.txt","org.apache.hadoop.io.compress.DefaultCodec");

        decompress("d:/hello.txt.bz2");
    }

    /**
     * 解压测试
     * @param
     */
    private static void decompress(String fileName) throws Exception {

        // 校验是否能解压缩
        CompressionCodecFactory factory = new CompressionCodecFactory(new Configuration());
        CompressionCodec codec = factory.getCodec(new Path(fileName));
        if(codec == null){
            System.out.println("不支持该解码器" + fileName);
            return;
        }
        //获取输入流
        CompressionInputStream cis = codec.createInputStream(new FileInputStream(new File(fileName)));
        //获取输出流
        FileOutputStream fos = new FileOutputStream(new File(fileName + ".decode"));
        //流的对拷
        IOUtils.copyBytes(cis,fos,1024*1024*5,false);

        //关闭资源
        cis.close();
        fos.close();
    }

    //测试压缩
    private static void compress(String fileName, String method) throws Exception{

        //1获取输入流
        FileInputStream fis = new FileInputStream(new File(fileName));

        Class classname = Class.forName(method);
        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(classname, new Configuration());

        //2.获取输出流
        FileOutputStream fos = new FileOutputStream(new File(fileName + codec.getDefaultExtension()));
        CompressionOutputStream cos = codec.createOutputStream(fos);

        //3.流的对拷
        IOUtils.copyBytes(fis,cos,1024*1024*5,false);

        //4.关闭资源
        fis.close();
        cos.close();
        fos.close();
    }

}
