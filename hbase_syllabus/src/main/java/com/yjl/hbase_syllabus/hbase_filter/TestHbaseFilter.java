package com.yjl.hbase_syllabus.hbase_filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

public class TestHbaseFilter {

    public static Configuration conf;
    static {
        conf = HBaseConfiguration.create();
    }

    /**
     * 获取过滤后的数据
     * 获取 或 关系的数据
     */
    public static void getFilterRows()throws Exception{
        //创建链接
        Connection connection = ConnectionFactory.createConnection(conf);
        //获取表对象
        Table hbaseFilter = connection.getTable(TableName.valueOf("hbaseFilter"));

        //创建过滤器
/*        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        SingleColumnValueFilter filter1 = new SingleColumnValueFilter(
                Bytes.toBytes("f"),
                Bytes.toBytes("name"),
                CompareOperator.EQUAL,
                Bytes.toBytes("name2")
        );
        filterList.addFilter(filter1);
        SingleColumnValueFilter filter2 = new SingleColumnValueFilter(
                Bytes.toBytes("f"),
                Bytes.toBytes("name"),
                CompareOperator.EQUAL,
                Bytes.toBytes("name3")
        );
        filterList.addFilter(filter2);*/
        //创建scan对象
        Scan scan = new Scan();
//        scan.setFilter(filter1);

        //获取数据
        ResultScanner resultScanner = hbaseFilter.getScanner(scan);

        //遍历结果
        for (Result result : resultScanner) {
            Cell[] cells = result.rawCells();

            for (Cell cell : cells) {
                System.out.println("行健："+ Bytes.toString(CellUtil.cloneRow(cell)));
                System.out.println("列族："+ Bytes.toString(CellUtil.cloneFamily(cell)));
                System.out.println("列："+ Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.println("值："+ Bytes.toString(CellUtil.cloneValue(cell)));
            }
        }
    }

    public static void main(String[] args) {
        try {
            getFilterRows();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
