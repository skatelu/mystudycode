package pac;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * Created by admin on 2018/5/7.
 */
public class BoltConvert extends BaseRichBolt {
    private OutputCollector collector;

    public void prepare(Map conf, TopologyContext context, OutputCollector collector)
    {
        this.collector = collector;
    }
    public void execute(Tuple arg0) {
        String strMsg = new String(arg0.getBinaryByField("bytes"));
        String strCustCode = strMsg.substring(strMsg.indexOf("CUSTOMER") + 11, strMsg.indexOf("\"", strMsg.indexOf("CUSTOMER") + 11));
        int a=0;
        try {
            a = Integer.parseInt(strCustCode);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        //执行数据传递
        this.collector.emit(arg0, new Values(strCustCode));
    }

    public void declareOutputFields(OutputFieldsDeclarer arg0) {
        arg0.declare(new Fields("CUST_CODE"));
    }
}
