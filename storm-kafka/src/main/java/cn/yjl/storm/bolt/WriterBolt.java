package cn.yjl.storm.bolt;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

import java.io.FileWriter;
import java.util.Map;
import java.util.UUID;

/**
 * 将数据写入文件
 * @author duanhaitao@itcast.cn
 *
 */
public class WriterBolt extends BaseBasicBolt {

	private static final long serialVersionUID = -6586283337287975719L;
	
	private FileWriter writer = null;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		try {
			writer = new FileWriter("D:\\test\\" + "wordcount"+ UUID.randomUUID().toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
	}
	
	
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String s = input.getString(0);
		try {
			writer.write(s);
			writer.write("\n");
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
