package utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;


public class HBaseUtil {

    /**
     * 判断表是否存在
     * @param conf
     * @param tableName
     * @return
     */
    public static boolean isExistTable(Configuration conf, String tableName) throws IOException {
        Connection connection = ConnectionFactory.createConnection(conf);
        Admin admin = connection.getAdmin();
        boolean result = admin.tableExists(TableName.valueOf(tableName));

        admin.close();
        connection.close();
        return result;
    }

    /**
     * 初始化命名空间
     * @param conf
     * @param namespace
     */
    public static void initNamespace(Configuration conf,String namespace) throws IOException {
        Connection connection = ConnectionFactory.createConnection();
        Admin admin = connection.getAdmin();

        NamespaceDescriptor nd = NamespaceDescriptor
                .create(namespace)
                .addConfiguration("CREATE_TIME",String.valueOf(System.currentTimeMillis()))
                .addConfiguration("AUTHOR","YJL")
                .build();

        admin.createNamespace(nd);

        admin.close();
        connection.close();
    }

    /**
     * 创建表:协处理器
     * @param conf
     * @param tableName
     * @param columnFamilys
     */
    public static void createTable(Configuration conf,String tableName,int regions,String... columnFamilys) throws IOException {
        Connection connection = ConnectionFactory.createConnection();
        Admin admin = connection.getAdmin();
        //判断表是否存在
        if(isExistTable(conf,tableName)){
            return;
        }

        //创建表描述器
        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf(tableName));

        for (String cf : columnFamilys) {
            //创建列族描述器
            htd.addFamily(new HColumnDescriptor(cf));
        }
        htd.addCoprocessor("hbase.CalleeWriteObserver");

        //添加预分区，防止数据倾斜
        admin.createTable(htd,genSplitKeys(regions));

        admin.close();
        connection.close();
    }

    /**
     * 设置分区号
     * @param regions
     * @return
     */
    private static byte[][] genSplitKeys(int regions){
        //定义一个存放分区键的数组
        String[] keys = new String[regions];
        //目前推算，region个数不会超过两位数，所以region格式化为两位数字 所代表的字符串
        DecimalFormat df= new DecimalFormat("00");
        for (int i = 0; i < regions; i++) {
            //为什么后面会跟着一个"|"，是因为在ASCII码中，"|"的值是124，大于所有的数字和字母等符号，当然也可以用“~”（ASCII-126）。
            // 分隔文件的第一行为第一个region的stopkey，每行依次类推，最后一行不仅是倒数第二个region的stopkey，
            // 同时也是最后一个region的startkey。
            keys[i] = df.format(i) + "|";

        }

        byte[][] splitKeys = new byte[regions][];
        //生成byte[][] 类型的分区键的时候，一定要保证分区键是有序的 用TreeSet对数组进行排序
        TreeSet<byte[]> treeSet = new TreeSet<byte[]>(Bytes.BYTES_COMPARATOR);
        //将数组放进treeSet
        for (int i = 0; i < regions; i++) {
            treeSet.add(Bytes.toBytes(keys[i]));
        }

        Iterator<byte[]> splitKeysIterator = treeSet.iterator();
        int index = 0;
        while(splitKeysIterator.hasNext()){
            byte[] b = splitKeysIterator.next();
            splitKeys[index ++] = b;
        }
        System.out.println(Arrays.toString(keys));
        return splitKeys;
    }

    /**
     * 生成RowKey
     * regionCode_call1_buildTime_call2_flag_duration
     * @return
     */
    public static String genRowKey(String regionCode,String call1,String buildTime,String call2,String flag,String duration){
        StringBuilder sb = new StringBuilder();
        sb.append(regionCode+"_")
                .append(call1+"_")
                .append(buildTime+"_")
                .append(call2+"_")
                .append(flag+"_")
                .append(duration);
        return sb.toString();
    }

    /**
     *
     * @param call1  手机号
     * @param buildTime
     * @param regions
     * @return
     */
    public static String genRegionCode(String call1,String buildTime,int regions){
        int len = call1.length();
        //取出后4位号码
        String lastPhone = call1.substring(len - 4);
        //取出年月
        String ym = buildTime.replaceAll("-", "")
                            .replaceAll(":", "")
                            .replaceAll(" ", "")
                            .substring(0, 6);
        //离散操作1
        Integer x = Integer.valueOf(lastPhone) ^ Integer.valueOf(ym);
        //离散操作2
        int y = x.hashCode();
        //生成分区号
        int regionCode = y % regions;
        //格式化分区号
        DecimalFormat df = new DecimalFormat("00");

        return df.format(regionCode);
    }
}
