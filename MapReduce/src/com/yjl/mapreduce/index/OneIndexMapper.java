package com.yjl.mapreduce.index;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class OneIndexMapper extends Mapper<LongWritable, Text,Text, IntWritable> {

    String name;
    Text k = new Text();
    IntWritable v = new IntWritable(1);
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //获取文件名称
        FileSplit split = (FileSplit) context.getInputSplit();
        name = split.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取一行
        String line = value.toString();
        //切割
        String[] fields = line.split(" ");
        for (String field : fields) {
            //拼接
            k.set(field + "--" + name);

            //输出
            context.write(k,v);
        }
    }
}
