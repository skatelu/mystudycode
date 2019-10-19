package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import utils.ConnectionInstance;
import utils.HBaseUtil;
import utils.PropertiesUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HBaseDAO {
    //分区数量
    private int regions;
    private String namespace;
    private String tableName;
    public static final Configuration conf;
    private HTable table;
    private Connection connection;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");

    private List<Put> cacheList = new ArrayList<Put>();

    static {
        conf = HBaseConfiguration.create();
    }

    public HBaseDAO() {
        try {
            regions = Integer.valueOf(PropertiesUtils.getProperty("hbase.calllog.regions"));
            namespace = PropertiesUtils.getProperty("hbase.calllog.namespace");
            tableName = PropertiesUtils.getProperty("hbase.calllog.tablename");

            if(!HBaseUtil.isExistTable(conf,tableName)){
                HBaseUtil.initNamespace(conf,namespace);
                HBaseUtil.createTable(conf,tableName,regions,"f1","f2");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  将数据放入HBase表中
     * @param ori
     */
    public void put(String ori){
        try {
            if(cacheList.size() == 0){
                connection = ConnectionInstance.getConnection(conf);
                //此处需要将table接口转换成具体的实现类，转换成HTable才会进行优化
                //否则不会有设定是否自动提交，与设置client客户端参数方法
                table = (HTable)connection.getTable(TableName.valueOf(tableName));
                //设置成false 表示拒绝自动提交
                table.setAutoFlushTo(false);
                //设置client客户端缓冲区的大小
                table.setWriteBufferSize(5 * 1024 * 1024);
            }
            String[] splitOri = ori.split(",");

            String caller = splitOri[0];
            String callee = splitOri[1];
            String buildTime = splitOri[2];
            String duration = splitOri[3];
            String regionCode = HBaseUtil.genRegionCode(caller, buildTime, regions);
            //格式化后的建立时间
            String buildTimeReplace = sdf1.format(sdf.parse(buildTime));
            //创建实践戳
            String buildTimeTs = String.valueOf(sdf.parse(buildTime).getTime());

            //生成RowKey
            String rowKey = HBaseUtil.genRowKey(regionCode, caller, buildTimeReplace, callee, "1", duration);

            System.out.println(rowKey +":" +caller + callee + buildTime + duration + regionCode + buildTimeReplace + buildTimeTs);
            //向表中写入数据
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("call1"),Bytes.toBytes(caller));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("call2"),Bytes.toBytes(callee));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("buildTime"),Bytes.toBytes(buildTime));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("buildTimeTs"),Bytes.toBytes(buildTimeTs));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("flag"),Bytes.toBytes("1"));
            put.addColumn(Bytes.toBytes("f1"),Bytes.toBytes("duration"),Bytes.toBytes(duration));
            //将put类型的数据放入数组中，批量提交
            cacheList.add(put);

            if(cacheList.size() >= 30){
                table.put(cacheList);
                //手动进行刷新缓存，提交client端数据
                table.flushCommits();

                table.close();
                cacheList.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
