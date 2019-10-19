package com.yjl.mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

// 驱动主程序
public class WordcountDriver {

	public static void main(String[] args) throws Exception {

		// 1 获取job对象信息
		Configuration configuration = new Configuration();

		//开启map端输出压缩
//		configuration.setBoolean("mapreduce.map.output.compress",true);
		//设置map端输出压缩方式
//		configuration.setClass("mapreduce.map.output.compress.codec", BZip2Codec.class, CompressionCodec.class);
		Job job = Job.getInstance(configuration);

		// 2 设置加载jar位置
		job.setJarByClass(WordcountDriver.class);

		// 3 设置mapper和reducer的class类
		job.setMapperClass(WordcountMapper.class);
		job.setReducerClass(WordcountReducer.class);

		// 4 设置输出mapper的数据类型
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		// 5 设置最终数据输出的类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		//8 设置读取输入文件切片的类    CombineTextInputFormat 这个文件输入是针对hdfs上面的大量小文件的
//		job.setInputFormatClass(CombineTextInputFormat.class);
//		CombineTextInputFormat.setMaxInputSplitSize(job,4194304);
//		CombineTextInputFormat.setMinInputSplitSize(job,2097152);

		// combiner mapper之后 reducer之前进行局部汇总
//		job.setCombinerClass(WordcountCombiner.class);

		// 6 设置输入数据和输出数据路径
		FileInputFormat.setInputPaths(job, new Path("D:\\output"));
		FileOutputFormat.setOutputPath(job, new Path("D:/output5"));

		// 9 关联Combiner
//		job.setCombinerClass(WordCountCombiner.class);

		//设置reduce端输出压缩开启
		FileOutputFormat.setCompressOutput(job,true);
		//设置压缩方式
		FileOutputFormat.setOutputCompressorClass(job,BZip2Codec.class);
		// 7 提交
		boolean result = job.waitForCompletion(true);

		System.exit(result ? 0 : 1);
	}
}
