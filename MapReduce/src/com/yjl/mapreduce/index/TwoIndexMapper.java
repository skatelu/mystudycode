package com.yjl.mapreduce.index;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class TwoIndexMapper extends Mapper<LongWritable, Text,Text,Text> {

    Text k = new Text();
    Text v = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取一行
        String line = value.toString();

        //切割
        String[] fields = line.split("--");

        k.set(fields[0]);
        v.set(fields[1]);

        //输出
        context.write(k,v);
    }
}
