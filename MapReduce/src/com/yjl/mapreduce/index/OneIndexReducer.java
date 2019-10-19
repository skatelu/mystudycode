package com.yjl.mapreduce.index;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class OneIndexReducer extends Reducer<Text, IntWritable,Text,IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        //汇总
        int sum = 0;
        for (IntWritable value : values) {
            sum += value.get();
        }

        //写出
        context.write(key,new IntWritable(sum));
    }
}
