package com.yjl.hbase_syllabus;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;

/**
 * java程序对HBase表的基本操作，增删改查
 */
public class HbaseDemo {

    public static Configuration conf;
    static{
        conf = HBaseConfiguration.create();
    }


    //判断表是否存在
    public static boolean isExist(String tableName) throws Exception {

        //最新版本的api
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();
        //TableName tableName1 = TableName.valueOf("myTable");

        return admin.tableExists(TableName.valueOf(tableName));
    }

    //Hbase表的创建
    public static void createTbale(String tbaleName, String... columnFamily)throws Exception{
        //根据传入的配置创建链接
        Connection connection = ConnectionFactory.createConnection(conf);
        //创建Admin对象来对Hbase进行操作
        Admin admin = connection.getAdmin();
        //设置TableName的名称
        TableName tableName = TableName.valueOf(tbaleName);
        //将传入的列簇封装成对应的list
        List<ColumnFamilyDescriptor> familyDescriptors = new ArrayList<ColumnFamilyDescriptor>(columnFamily.length);

        //创建列簇设置
        for (String cf : columnFamily) {
            //根据传入的列簇名称，设置列簇信息
            ColumnFamilyDescriptor columnFamilyDescriptor = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(cf)).build();
            //创建好列簇设置类后封装到list中
            familyDescriptors.add(columnFamilyDescriptor);
        }

        //创建table表结构描述
        TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableName);
        //根据封装的列簇设置类，形成table表结构描述
        TableDescriptor tableDescriptor = tableDescriptorBuilder.setColumnFamilies(familyDescriptors).build();

        //校验要创建的表是否存在
        if(admin.tableExists(tableName)){
            System.out.println("创建的表已经存在");
            return;
        }
        //用admin类来创建对应的表
        admin.createTable(tableDescriptor);
        System.out.println("表创建成功");
    }

    //Hbase删除表
    public static void deleteTable(String tableName)throws Exception{

        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();

        //判断表是否存在
        if(admin.tableExists(TableName.valueOf(tableName))){
            //判断表是否卸载
            if(!admin.isTableDisabled(TableName.valueOf(tableName))){
                //若没有卸载
                admin.disableTable(TableName.valueOf(tableName));
            }
            admin.deleteTable(TableName.valueOf(tableName));
            System.out.println("表删除成功");
        }else {
            System.out.println("表不存在");
        }
    }

    //添加数据
    public static void addRow(String tableName,String rowKey,String cf,String column,String value)throws Exception{
        Connection connection = ConnectionFactory.createConnection(conf);

        //table:用于表中数据的操作
        Table table = connection.getTable(TableName.valueOf(tableName));
        //用于封装待存放的数据
        List<Put> puts = new ArrayList<Put>();
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(column),Bytes.toBytes(value));
        puts.add(put);
        table.put(puts);
    }

    //删除一行数据
    public static void deleteRow(String tableName,String rowKey,String cf)throws Exception{
        Connection connection = ConnectionFactory.createConnection(conf);

        //table:用于表中数据的操作
        Table table = connection.getTable(TableName.valueOf(tableName));

        Delete delete = new Delete(Bytes.toBytes(rowKey));

        table.delete(delete);
    }

    //删除多行数据
    public static void deleteMultiRow(String tableName,String... rowKeys)throws Exception{
        Connection connection = ConnectionFactory.createConnection(conf);
        //table:用于表中数据的操作
        Table table = connection.getTable(TableName.valueOf(tableName));

        List<Delete> list = new ArrayList<Delete>();
        for (String rowKey : rowKeys) {
            Delete delete = new Delete(Bytes.toBytes(rowKey));
            list.add(delete);
        }
        table.delete(list);
    }

    //扫描数据
    public static void getAllRows(String tableName)throws Exception{
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("habseFilter"));

        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {
//            System.out.println(Bytes.toString(result.getRow()));
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                System.out.println("行健："+ Bytes.toString(CellUtil.cloneRow(cell)));
                System.out.println("列族："+ Bytes.toString(CellUtil.cloneFamily(cell)));
                System.out.println("列："+ Bytes.toString(CellUtil.cloneQualifier(cell)));
                System.out.println("值："+ Bytes.toString(CellUtil.cloneValue(cell)));
            }
        }
    }

    //得到一个具体的数据
    public static void getRow(String tableName,String rowKey,String cf,String column)throws Exception{
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf(tableName));

        Get get = new Get(Bytes.toBytes(rowKey));
//        get.addFamily(Bytes.toBytes("info1"));
        get.addColumn(Bytes.toBytes(cf),Bytes.toBytes(column));

        Result result = table.get(get);
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            System.out.println("行健："+ Bytes.toString(CellUtil.cloneRow(cell)));
            System.out.println("列族："+ Bytes.toString(CellUtil.cloneFamily(cell)));
            System.out.println("列："+ Bytes.toString(CellUtil.cloneQualifier(cell)));
            System.out.println("值："+ Bytes.toString(CellUtil.cloneValue(cell)));
        }

    }


    //主函数中进行测试
    public static void main(String[] args)throws Exception {
        System.out.println(isExist("student"));

        //createTbale("staff","info1","info2");

        //deleteTable("student");

//        addRow("staff","1001","info1","sex","男");
//        addRow("staff","1002","info1","name","alice");
//        addRow("staff","1003","info1","name","kotlin");


//        deleteMultiRow("staff","1001","1002","1003");

//        getAllRows("habseFilter");

//        getRow("staff","1001","info1","sex");
    }

}
