package com.yjl.mapreduce.sortorder;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class OrderDirver {

    public static void main(String[] args) throws Exception{
        // 1 获取配置信息
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2 设置 jar 包加载路径
        job.setJarByClass(OrderDirver.class);

        // 3 加载 map/reduce 类
        job.setMapperClass(OrderMapper .class);
        job.setReducerClass(OrderReducer .class);

        // 4 设置 map 输出数据 key 和 value 类型
        job.setMapOutputKeyClass(OrderBean .class);
        job.setMapOutputValueClass(NullWritable.class);

        // 5 设置最终输出数据的 key 和 value 类型
        job.setOutputKeyClass(OrderBean .class);
        job.setOutputValueClass(NullWritable .class);

        // 6 设置输入数据和输出数据路径
        FileInputFormat.setInputPaths(job,new Path("D:\\output\\test.txt"));
        FileOutputFormat.setOutputPath(job,new Path("D:/output3"));

        // 10 设置 reduce 端的分组  也可以叫辅助排序
        job.setGroupingComparatorClass(OrderGroupingComparator.class);
        // 7 设置分区
        job.setPartitionerClass(OrderPartitioner.class);
        // 8 设置 reduce 个数
        job.setNumReduceTasks(3);

        // 9 提交
        boolean result = job.waitForCompletion(true);
        System.exit(result ?0:1);
    }


}
