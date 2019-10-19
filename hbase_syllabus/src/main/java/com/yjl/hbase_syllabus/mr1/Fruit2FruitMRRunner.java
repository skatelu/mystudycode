/*
package com.yjl.hbase_syllabus.mr1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

*/
/**
 * 从HBase表中读取数据后写入其它HBase表中
 *//*

public class Fruit2FruitMRRunner implements Tool {

    Configuration conf;
    public int run(String[] strings) throws Exception {
        //创建job
        Job job = Job.getInstance(conf);
        job.setJarByClass(Fruit2FruitMRRunner.class);
        //配置job
        Scan scan = new Scan();
        //设置mapper
        TableMapReduceUtil.initTableMapperJob("fruit",
                scan,
                ReadFruitMapper.class,
                ImmutableBytesWritable.class,
                Put.class,job);
        //设置Reducer
        TableMapReduceUtil.initTableReducerJob("fruit_mr",WriteFruitMRReducer.class,job);
        job.setNumReduceTasks(1);

        boolean result = job.waitForCompletion(true);
        return result ? 0 : 1;
    }

    public void setConf(Configuration configuration) {
        //设置Configuration
        this.conf = HBaseConfiguration.create(configuration);
    }

    public Configuration getConf() {
        return this.conf;
    }

    public static void main(String[] args) throws Exception{
        int status = ToolRunner.run(new Fruit2FruitMRRunner(), args);
        System.out.println(status);
    }
}
*/
