package com.yjl.mapreduce.index;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class TwoIndexReducer extends Reducer<Text,Text,Text,Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //获取values 拼接
        StringBuilder sb = new StringBuilder();
        for (Text value : values) {
            sb.append(value.toString().replace("\t","-->")+"\t");
    }

        //写出
        context.write(key,new Text(sb.toString()));
    }
}
