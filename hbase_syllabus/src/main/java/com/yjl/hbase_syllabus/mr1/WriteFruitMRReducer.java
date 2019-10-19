/*
package com.yjl.hbase_syllabus.mr1;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.NullWritable;

import java.io.IOException;

public class WriteFruitMRReducer extends TableReducer<ImmutableBytesWritable, Put, NullWritable> {

    @Override
    protected void reduce(ImmutableBytesWritable key,
                          Iterable<Put> values,
                          Context context)
            throws IOException, InterruptedException {

        //获取数据
        for (Put put : values) {
            context.write(NullWritable.get(),put);
        }
    }
}
*/
