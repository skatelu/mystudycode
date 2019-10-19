package mapper;

import kv.key.ComDimension;
import kv.key.ContactDimension;
import kv.key.DateDimension;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 这个是直接从Hbase中获取数据，即封装好的输入文件
 */
public class CountDurationMapper extends TableMapper<ComDimension, Text> {

    private ComDimension comDimension = new ComDimension();
    private Text durationText = new Text();
    private Map<String,String> phoneNameMap;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
        phoneNameMap = new HashMap<String, String>();

        phoneNameMap.put("18742720904", "李雁");
        phoneNameMap.put("13811958264", "卫艺");
        phoneNameMap.put("19089970150", "仰莉");
        phoneNameMap.put("16868664637", "陶欣悦");
        phoneNameMap.put("16635892961", "施梅梅");
        phoneNameMap.put("19145349791", "金虹霖");
        phoneNameMap.put("18102448599", "魏明艳");
        phoneNameMap.put("19533829665", "华贞");
        phoneNameMap.put("13657847997", "华啟倩");
        phoneNameMap.put("18349718826", "仲采绿");
        phoneNameMap.put("15725671094", "卫丹");
        phoneNameMap.put("18336247427", "戚丽红");
        phoneNameMap.put("15695444496", "何翠柔");
        phoneNameMap.put("14546128631", "钱溶艳");
        phoneNameMap.put("15798274734", "钱琳");
        phoneNameMap.put("15659456362", "缪静欣");
        phoneNameMap.put("13357154130", "焦秋菊");
        phoneNameMap.put("14914062673", "吕访琴");
        phoneNameMap.put("13739624089", "沈丹");
        phoneNameMap.put("16771190044", "褚美丽");
        phoneNameMap.put("14537878197", "孙怡");
        phoneNameMap.put("17135383073", "许婵");
        phoneNameMap.put("17647825882", "曹红恋");
        phoneNameMap.put("18585892024", "吕柔");
        phoneNameMap.put("15225174483", "冯怜云");
    }

    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        String rowkey = Bytes.toString(key.get());
        String[] splits = rowkey.split("_");
        if(splits[4].equals("0")){
            return;
        }

        //以下数据全部都是主叫数据，但是也包含了被叫电话的数据
        String caller = splits[1];
        String callee = splits[3];
        String buildTime = splits[2];
        String duration = splits[5];
        durationText.set(duration);

        String year = buildTime.substring(0,4);
        String month = buildTime.substring(4, 6);
        String day = buildTime.substring(6, 8);

        // 组装comDimension
        // 组装DateDimension
        DateDimension yearDimension = new DateDimension(year,"-1","-1");
        DateDimension monthDimension = new DateDimension(year,month,"-1");
        DateDimension dayDimension = new DateDimension(year,month,day);

        //组装ContactDimension
        ContactDimension callerContactDimension = new ContactDimension(caller,phoneNameMap.get(caller));

        //开始聚合主叫数据
        comDimension.setContactDimension(callerContactDimension);
        //年
        comDimension.setDateDimension(yearDimension);
        context.write(comDimension,durationText);
        //月
        comDimension.setDateDimension(monthDimension);
        context.write(comDimension,durationText);
        //日
        comDimension.setDateDimension(dayDimension);
        context.write(comDimension,durationText);

        //开始聚合被叫数据
        ContactDimension calleeContactDimension = new ContactDimension(callee,phoneNameMap.get(caller));
        comDimension.setContactDimension(calleeContactDimension);
        //年
        comDimension.setDateDimension(yearDimension);
        context.write(comDimension,durationText);
        //月
        comDimension.setDateDimension(monthDimension);
        context.write(comDimension,durationText);
        //日
        comDimension.setDateDimension(dayDimension);
        context.write(comDimension,durationText);
    }
}
