package com.yjl.mapreduce.findfreand;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class OneShareFriendsMapper extends Mapper<LongWritable, Text,Text,Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //获取一行数据
        String line = value.toString();

        //切割
        String[] fields = line.split(":");

        //获取好友person和好友
        String person = fields[0];
        String[] friends = fields[1].split(",");

        //写出
        for (String friend : friends) {
            //输出<好友，人>
            context.write(new Text(friend),new Text(person));
        }
    }
}
