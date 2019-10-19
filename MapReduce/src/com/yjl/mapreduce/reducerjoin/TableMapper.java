package com.yjl.mapreduce.reducerjoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class TableMapper extends Mapper<LongWritable, Text,Text,TableBean> {

    Text k = new Text();
    TableBean v = new TableBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //区分两张表
        FileSplit split = (FileSplit) context.getInputSplit();
        String name = split.getPath().getName();

        //获取一行
        String line = value.toString();

        //订单表
        if(name.startsWith("order")){
            //切割
            String[] fields = line.split("\t");
            v.setOrder_id(fields[0]);
            v.setPid(fields[1]);
            v.setAmount(Integer.parseInt(fields[2]));
            v.setpName("");
            v.setFlag("0");

            k.set(fields[1]);
        }else {//产品表
            //切割
            String[] fields = line.split("\t");
            v.setOrder_id("");
            v.setPid(fields[0]);
            v.setAmount(0);
            v.setpName(fields[1]);
            v.setFlag("1");

            k.set(fields[0]);
        }

        context.write(k,v);
    }
}
