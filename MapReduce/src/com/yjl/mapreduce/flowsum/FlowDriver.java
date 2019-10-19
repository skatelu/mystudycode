package com.yjl.mapreduce.flowsum;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FlowDriver {

    public static void main(String[] args) throws Exception{

        //1.获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        //2.设置jar包路径
        job.setJarByClass(FlowDriver.class);

        //3.关联mapper 和 reducer实例
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);

        //4.设置mapper输出的K,V类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        //5.设置最终输出k，v类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        //设置分区
        job.setPartitionerClass(ProvincePartitioner.class);
        job.setNumReduceTasks(5);
        //6.设置输入路径
        FileInputFormat.setInputPaths(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));

        //7.输出
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
