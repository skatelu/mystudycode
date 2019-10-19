package com.yjl.mapreduce.flowsum;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class FlowMapper extends Mapper<LongWritable, Text,Text,FlowBean> {

    FlowBean v = new FlowBean();
    Text k = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //1获取一行数据
        String line = value.toString();
        //2.切割
        String[] fileds = line.split("\t");
        //3.封装对象
        // 手机号
        String phoneNum = fileds[0];
        //上行流量
        long upFlow = Long.parseLong(fileds[fileds.length - 3]);
        //下行流量
        long downFlow = Long.parseLong(fileds[fileds.length-2]);

        v.set(upFlow,downFlow);
        k.set(phoneNum);

        //4.写出数据
        context.write(k,v);
    }
}
