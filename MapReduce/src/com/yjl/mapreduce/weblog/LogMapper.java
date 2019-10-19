package com.yjl.mapreduce.weblog;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class LogMapper extends Mapper<LongWritable, Text,Text, NullWritable> {

    Text k = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //获取一行
        String line = value.toString();

        //切割
        boolean result = parseLog(line,context);
        if(!result){
            return;
        }
        k.set(line);

        //输出
        context.write(k,NullWritable.get());
    }

    private boolean parseLog(String line, Context context) {

        //切割
        String[] fields = line.split(" ");

        //解析log日志
        if(fields.length > 11){
            //计数器
            context.getCounter("LogMapper","parseLog_true").increment(1);
            return true;
        }else {
            context.getCounter("LogMapper","parseLog_true").increment(1);
            return false;
        }
    }
}
