package com.yjl.hdfs;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;

/**
 * 利用io流上传文件到hdfs
 */
public class HDFSClientIo {

    // 文件上传
    @Test
    public void putFileToHDFS()throws Exception{
        //1.获取文件系统
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), conf, "root");
        //2.获取输入流
        FileInputStream fis = new FileInputStream(new File("D:/output/hello.txt"));
        //3.获取输出流
        FSDataOutputStream fos = fs.create(new Path("/user/input/hello.txt"));
        //4.流的拷贝
        IOUtils.copyBytes(fis,fos,conf);
        //5.关闭资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);

        fs.close();
    }

    //文件下载
    @Test
    public void getFileToHDFS() throws Exception{

        //创建链接 获取文件系统
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), conf, "root");

        //获取输入流
        FSDataInputStream fis = fs.open(new Path("/qingshu.txt"));

        //获取输出流
        FileOutputStream fos = new FileOutputStream("D://hello.txt");
        //流的对拷
        IOUtils.copyBytes(fis,fos,conf);

        //关闭资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);

        fs.close();
    }

    //定位文件读取
    //下载第一块
    @Test
    public void readFileSeek1() throws Exception{

        //1.获取文件系统
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), conf, "root");
        //2.获取输入流
        FSDataInputStream fis = fs.open(new Path("/user/yjl/hadoop-2.7.2.tar.gz"));

        //3.获取输出流
        FileOutputStream fos = new FileOutputStream(new File("D:/hadoop.tar.gz.part1"));

        //4.流的对拷
        byte[] buf = new byte[1024];

        for(int i = 0;i<1024 * 128;i++){
            fis.read(buf);
            fos.write(buf);
        }

        //5.关闭资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);

        fs.close();
    }

    //读取第二块文件
    @Test
    public void readFileSeek2() throws Exception{
        //1.获取文件系统
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), conf, "root");
        //2.获取输入流
        FSDataInputStream fis = fs.open(new Path("/user/yjl/hadoop-2.7.2.tar.gz"));
        
        //定位读取位置
        fis.seek(1024*1024*128);
        //获取输出流
        FileOutputStream fos = new FileOutputStream(new File("D:/hadoop.tar.gz.part2"));
        //流的对拷
        IOUtils.copyBytes(fis,fos,conf);
        //关闭资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);

        fs.close();
    }

    //一致性模型
    @Test
    public void putFile() throws Exception{

        //获取文件系统
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), conf, "root");
/*        //获取输入文件
        //        FileInputStream fis = new FileInputStream(new File("D:/qingshu.txt"));
        //        //获取输出文件
        //        FSDataOutputStream fos = fs.create(new Path("/qingshu2.txt"));
        //        //流的对拷
        //        IOUtils.copyBytes(fis,fos,conf);
        //        //刷新*/
        // 2 获取输出流
        FSDataOutputStream fos = fs.create(new Path("/qingshu2.txt"));
        // 3 写数据
        fos.write("hello".getBytes());
        // 4 一致性刷新
        fos.hflush();
        //关闭资源
        //IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);

        fs.close();
    }
}
