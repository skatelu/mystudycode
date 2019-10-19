package com.yjl.mapreduce.inputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class WholeRecordReader extends RecordReader<NullWritable, BytesWritable> {

    BytesWritable value = new BytesWritable();
    boolean isProcess = false;
    FileSplit split = new FileSplit();
    Configuration configuration;

    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        //初始化
        this.split = (FileSplit) split;
        configuration = context.getConfiguration();
    }

    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {
        // 读取一个一个的文件
        if(!isProcess){

            //缓冲区
            byte[] buf = new byte[(int) split.getLength()];

            FileSystem fileSystem = null;
            FSDataInputStream fis = null;
            try {
                //获取文件系统
                Path path = split.getPath();
                fileSystem = path.getFileSystem(configuration);

                //2.打开文件输入流
                fis = fileSystem.open(path);

                //3.流的拷贝
                IOUtils.readFully(fis,buf,0,buf.length);

                //4.拷贝缓存区数据到最终输出
                value.set(buf,0,buf.length);
            }catch (Exception e){

            }finally {
                IOUtils.closeStream(fis);
                IOUtils.closeStream(fileSystem);
            }
            isProcess = true;

            return true;
        }
        return false;
    }

    @Override
    public NullWritable getCurrentKey() throws IOException, InterruptedException {

        return NullWritable.get();
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {

        return value;
    }

    /**
     * 获取当前进度
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public float getProgress() throws IOException, InterruptedException {
        return isProcess ? 1 : 0;
    }

    @Override
    public void close() throws IOException {

    }
}
