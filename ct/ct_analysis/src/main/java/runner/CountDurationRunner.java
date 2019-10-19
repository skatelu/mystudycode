package runner;

import kv.key.ComDimension;
import kv.value.CountDurationValue;
import mapper.CountDurationMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import outputformat.MysqlOutputFormat;
import reducer.CountDurationReducer;

import java.io.IOException;

public class CountDurationRunner implements Tool {

    private Configuration conf;

    @Override
    public void setConf(Configuration conf) {
        this.conf = HBaseConfiguration.create(conf);
    }

    @Override
    public Configuration getConf() {
        return this.conf;
    }

    @Override
    public int run(String[] args) throws Exception {
        // 得到conf
        // 实例化job、
        Job job = Job.getInstance(conf);
        job.setJarByClass(CountDurationRunner.class);
        // 组装Mapper 直接组装InputFormat
        initHbaseInputConfig(job);
        // 组装Reducer  Outputformat
        initReducerOutputConfig(job);
        //job提交成功后返回true
        boolean result = job.waitForCompletion(true);
        return result ? 0 : 1;
    }

    private void initHbaseInputConfig(Job job) {
        Connection connection = null;
        Admin admin = null;
        try {
            String tableName = "ns_ct:calllog";
            connection = ConnectionFactory.createConnection(conf);
            admin = connection.getAdmin();
            if (!admin.tableExists(TableName.valueOf("ns_ct:calllog"))) {
                throw new RuntimeException("无法找到目标表");
            }
            Scan scan = new Scan();
            //可以优化
            //设置Mapper 最后的true 是将依赖的jar包导入到当前工作环境中   默认为true
            TableMapReduceUtil.initTableMapperJob(
                    tableName,scan,
                    CountDurationMapper.class,
                    ComDimension.class,
                    Text.class,job,
                    true);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(admin != null){
                    admin.close();
                }
                if(connection!=null && !connection.isClosed()){
                    connection.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initReducerOutputConfig(Job job) {

        job.setReducerClass(CountDurationReducer.class);
        job.setOutputKeyClass(ComDimension.class);
        job.setOutputValueClass(CountDurationValue.class);
        job.setOutputFormatClass(MysqlOutputFormat.class);

    }

    public static void main(String[] args) {
        try {
            int status = ToolRunner.run(new CountDurationRunner(), args);
            System.exit(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
