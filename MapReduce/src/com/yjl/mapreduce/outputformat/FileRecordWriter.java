package com.yjl.mapreduce.outputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class FileRecordWriter extends RecordWriter<Text, NullWritable> {

    private Configuration conf;
    private FSDataOutputStream atguigufos = null;
    private FSDataOutputStream otherfos = null;

    public FileRecordWriter(TaskAttemptContext context) {
        conf = context.getConfiguration();

        //获取文件系统
        try {
            FileSystem fs = FileSystem.get(conf);

            //创建两个输出流
            atguigufos = fs.create(new Path("d:/output2/atguigu.log"));
            otherfos = fs.create(new Path("d:/output2/other.log"));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        //判断key中是否包含atguigu
        if(key.toString().contains("atguigu")){
            atguigufos.write(key.getBytes());
        }else {
            otherfos.write(key.getBytes());
        }
    }

    @Override
    public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        //必须关闭资源
        if(atguigufos != null){
            atguigufos.close();
        }
        if(otherfos != null){
            otherfos.close();
        }
    }
}
