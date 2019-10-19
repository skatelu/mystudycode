package com.yjl.mapreduce.mapjoin;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class DistributedCacheMapper extends Mapper<LongWritable, Text,Text, NullWritable> {

    private HashMap<String,String> pdMap = new HashMap<String,String>();
    Text k = new Text();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //读取缓存文件
        //BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("pd.txt"), "UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("pd.txt"),"UTF-8"));
        String line;
        //读取文件内容
        while(StringUtils.isNotEmpty(line = reader.readLine())){
            //切割
            String[] fields = line.split("\t");
            //封装到map中,即缓存到集合中
            pdMap.put(fields[0].trim(),fields[1]);
        }

        System.out.println(pdMap.toString());
        //关闭流
        reader.close();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取一行
        String line = value.toString();
        //切割
        String[] fields = line.split("\t");
        //获取pid
        String pid = fields[1];
        //根据pid获取map中pid的产品名称
        String pName = pdMap.get(pid);
        //拼接
        line = line + "\t" + pName;
        k.set(line);
        //输出
        context.write(k,NullWritable.get());
    }
}
