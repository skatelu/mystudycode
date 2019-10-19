package com.yjl.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 上传文件到hdfs
 */
public class HdfsClient {

    //上传文件
    public static void main(String[] args) throws Exception {
        //1获取文件系统
        Configuration conf = new Configuration();
        //设置配置信息
//        这种发放需要在VM运行时修改权限 -DHADOOP_USER_NAME=root
//        conf.set("fs.defaultFS","hdfs://hadoop101:9000");
//        FileSystem fs = FileSystem.get(conf);

        //这种方式直接配置谁来执行
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), conf, "root");

        //2上传文件
        fs.copyFromLocalFile(new Path("D:/qingshu.txt"),new Path("/nihao.txt"));
        //关闭资源
        fs.close();

        System.out.println("OVER");
    }

    //获取文件系统
    @Test
    public void getFile() throws IOException {

        //1.获取文件系统
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        //2.打印文件系统到控制台
        System.out.println(fs.toString());

    }

    //文件上传
    @Test
    public void putFileToHdfs()throws Exception{

        //1获取文件系统
        Configuration conf = new Configuration();

        conf.set("dfs.replication","2");

        //这种方式直接配置谁来执行
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), conf, "root");

        //2上传文件
        fs.copyFromLocalFile(new Path("D:/qingshu.txt"),new Path("/nihao1.txt"));
        //关闭资源
        fs.close();

        System.out.println("OVER");
    }

    //下载文件
    @Test
    public void getFileFromHdfs() throws Exception {
        Configuration conf = new Configuration();

        //1获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), conf, "root");
        //2.执行下载操作
        fs.copyToLocalFile(false,new Path("/hello1.txt"),new Path("D:/hello1.txt"),true);

        // 关闭资源
        fs.close();

    }

    //创建目录
    @Test
    public void mkdirAtHdfs() throws Exception{
        //1.获取文件系统
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"),conf,"root");
        //2.创建目录
        fs.mkdirs(new Path("/1108/yjl/banzhang"));
        //3.关闭资源
        fs.close();
    }

    //删除文件夹
    @Test
    public void deleteAtHdfs() throws Exception{
        Configuration conf = new Configuration();
        //获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"),conf,"root");
        //删除文件
        //fs.delete(new Path("/1108"),true);
        fs.delete(new Path("/hello*"),true);
        //关闭资源
        fs.close();
    }

    //修改文件名称
    @Test
    public void renameAtHdfs() throws Exception{
        Configuration conf = new Configuration();
        //获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"),conf,"root");
        //修改文件名称
        fs.rename(new Path("/hello.txt"),new Path("/nihao12.txt"));

        fs.close();
    }

    //查看文件详情
    @Test
    public void readListFiles() throws Exception{
        Configuration conf = new Configuration();
        //获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"),conf,"root");

        //获取文件详情
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);

        while (listFiles.hasNext()){
            LocatedFileStatus status = listFiles.next();
            //输出详情
            System.out.println(status.getPath().getName());
            System.out.println(status.getLen());
            System.out.println(status.getPermission());
            System.out.println(status.getGroup());
            BlockLocation[] blockLocations = status.getBlockLocations();

            for (BlockLocation blockLocation:blockLocations) {
                String[] hosts = blockLocation.getHosts();
                for(String host : hosts){
                    System.out.println(host);
                }
            }
            System.out.println("-----------分割线------------------");
        }


        fs.close();
    }

    //HDFS判断是文件还是文件夹
    @Test
    public void findAtHdfs() throws Exception{
        Configuration conf = new Configuration();
        //获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"),conf,"root");

        //判断是否是文件还是文件夹
        FileStatus[] fileStatuses = fs.listStatus(new Path("/"));

        for(FileStatus fileStatus : fileStatuses){

            //如果是文件
            if(fileStatus.isDirectory()){
                System.out.println("这个是文件夹，名称是: " + fileStatus.getPath().getName());
            }else {
                System.out.println("这个是文件，名称是： " + fileStatus.getPath().getName());
            }
        }

        fs.close();
    }

}
