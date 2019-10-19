/*
package com.yjl.hbase_syllabus.mr1;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class ReadFruitMapper extends TableMapper<ImmutableBytesWritable, Put> {

    @Override
    protected void map(ImmutableBytesWritable key,
                       Result value,
                       Context context)
            throws IOException, InterruptedException {
        //读取数据
        Put put = new Put(key.get());
        //遍历Colume
        for (Cell cell : value.rawCells()) {
            //添加info列族数据  如果本次得到的数据是info列族下的数据，则导入到fruit_mr表中
            if("info".equals(Bytes.toString(CellUtil.cloneFamily(cell)))){
                if("name".equals(Bytes.toString(CellUtil.cloneQualifier(cell)))){
                    //存放想要的数据
                    put.add(cell);
                }
            }
        }

        context.write(key,put);
    }
}
*/
