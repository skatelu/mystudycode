package com.yjl.mapreduce.mapjoin;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.net.URI;

public class DistributedCacheDriver {

    public static void main(String[] args) throws Exception{

        args = new String[] { "d:/output2", "d:/output4" };

        // 1 获取 job 信息
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        // 2 设置加载 jar 包路径
        job.setJarByClass(DistributedCacheDriver.class);

        // 3 关联 map
        job.setMapperClass(DistributedCacheMapper.class);

        // 4 设置最终输出数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        // 5 设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        // 6 加载缓存数据
        job.addCacheFile(new URI("file:///d:/output3/pd.txt"));

        // 7 map 端 join 的逻辑不需要 reduce 阶段，设置 reducetask 数量为 0
        job.setNumReduceTasks(0);
        // 8 提交
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
