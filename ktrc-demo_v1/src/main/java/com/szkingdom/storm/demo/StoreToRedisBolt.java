/**
 * Copyright (C), 2018, 金证股份有限公司
 * FileName: StoreToRedisBolt
 * Author:   zmh
 * Date:     2018/4/21 18:50
 * Description: 处理完的数据保存到redis
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.szkingdom.storm.demo;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;

/**
 * 〈处理完的数据保存到redis〉
 * @author zmh
 * @create 2018/4/21
 * @since 1.0.0
 */
public class StoreToRedisBolt extends BaseRichBolt {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private OutputCollector collector;

    @Override
    public void prepare(Map topoConf, TopologyContext context, OutputCollector collector){

        this.collector = collector;

    }

    @Override
    public void execute(Tuple input){

        Map<String, String> map = new HashMap<String, String>();

        String redisKey = input.getStringByField("REDIS_KEY");
        String redisVal = input.getStringByField("REDIS_VAL");

        //redisey和redisVal按]分割
        String[] redisKeyArray = redisKey.split("]");
        String[] redisValArray = redisVal.split("]");
        boolean lengthCorrect = (redisKey.length()>0 && redisVal.length()>0);
        boolean lengthCorresponding = (redisKeyArray.length == redisValArray.length);
        if(lengthCorrect && lengthCorresponding) {
            collector.ack(input);

            for (int i = 0; i < redisKeyArray.length; ++i) {
                //redisKeyArray为：表名 + key；继续按：拆分
                String[] redisKeyArrayCell = redisKeyArray[i].split(":");
                String redisNameCell = redisKeyArrayCell[0].substring(1);
                String redisKeyCell = redisKeyArrayCell[1];

                String redisValCell = redisValArray[i].substring(1).replace("[", "");

                if (JedisUtil.setHashString(redisNameCell, redisKeyCell, redisValCell) == -1){
                    logger.error("setHashString error redisNameCell : " + redisNameCell + "\n" + "redisKeyCell : " + redisKeyCell + "\n redisValCell : " + redisValCell + "\n");
                }

                //map.put(redisKeyCell, redisValCell);

//                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
//
//                while (it.hasNext()) {
//
//                    Map.Entry<String, String> entry = it.next();
//
//                    System.out.println("name = " + redisNameCell + "\n" + "Key = " + entry.getKey() + "\n  Value = " + entry.getValue());
//
//                }

                //JedisUtil.hmSetHashString(redisNameCell, map);
            }
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer){

    }

}
