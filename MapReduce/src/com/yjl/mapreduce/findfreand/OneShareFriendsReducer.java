package com.yjl.mapreduce.findfreand;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class OneShareFriendsReducer extends Reducer<Text, Text,Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        StringBuffer sb = new StringBuffer();

        for (Text person : values) {
            sb.append(person).append(",");
        }

        //写出
        context.write(key,new Text(sb.toString()));
    }
}
