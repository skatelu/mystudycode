package hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;
import utils.HBaseUtil;
import utils.PropertiesUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * habase 协处理器，观察者，生成被叫数据
 */
public class CalleeWriteObserver extends BaseRegionObserver {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        super.postPut(e, put, edit, durability);
        //获取你想要操作的目标表的名称
        String targetTableName = PropertiesUtils.getProperty("hbase.calllog.tablename");
        //获取当前成功Put了数据的表（不一定我们当前业务想操作的表）
        String currentTableName = e.getEnvironment().getRegionInfo().getTable().getNameAsString();
        if(!targetTableName.equals(currentTableName)){
            return;
        }

        String oriRowKey = Bytes.toString(put.getRow());

        String[] splitOriRowKey = oriRowKey.split("_");

        String oldFlag = splitOriRowKey[4];
        //如果当前插入的是被叫数据，则直接返回（因为默认提供的数据全部为主叫数据）
        //如果不加上，会导致协处理器死循环，没法实现
        if(oldFlag.equals("0")){
            return;
        }

        int regions = Integer.parseInt(PropertiesUtils.getProperty("hbase.calllog.regions"));

        String caller = splitOriRowKey[1];
        String callee = splitOriRowKey[3];
        String buildTime = splitOriRowKey[2];
        String flag = "0";
        String duration = splitOriRowKey[5];
        String regionCode = HBaseUtil.genRegionCode(callee,buildTime,regions);

        String calleeRowKey = HBaseUtil.genRowKey(regionCode,callee,buildTime,caller,flag,duration);
        //生成时间戳
        String buildTimeTs = "";
        try {
            buildTimeTs = String.valueOf(sdf.parse(buildTime).getTime());
        } catch (ParseException error) {
            error.printStackTrace();
        }

        Put calleePut = new Put(Bytes.toBytes(calleeRowKey));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("call1"), Bytes.toBytes(callee));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("call2"), Bytes.toBytes(caller));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("buildTime"), Bytes.toBytes(buildTime));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("buildTimeTs"), Bytes.toBytes(buildTimeTs));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("flag"), Bytes.toBytes(flag));
        calleePut.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("duration"), Bytes.toBytes(duration));

        Table table = e.getEnvironment().getTable(TableName.valueOf(targetTableName));
        table.put(calleePut);
        table.close();
    }

}
