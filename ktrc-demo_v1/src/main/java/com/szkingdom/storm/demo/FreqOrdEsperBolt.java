package com.szkingdom.storm.demo;

import com.espertech.esper.client.*;
import org.apache.commons.lang.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import org.apache.storm.tuple.Values;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by epAdmin on 2018/4/14.
 */
public class FreqOrdEsperBolt extends BaseRichBolt implements UpdateListener {
    public EPServiceProvider epService;
    public EPRuntime cepRT;
    public EPAdministrator epAdmin;
    private transient OutputCollector collector;
    private Map<String,Integer> counts;
    private Tuple tupleTrans;

    // ESPER事件触发所使用的JAVA类，直接在内部定义声明
    public static class Orders {
    //属性定义（系统变量）
    String contractsSn;            //合约号
    int custCode;            //字符串测试
    char isWithdraw;         // 撤单标志

    //属性定义（未使用，暂时保留的其他变量）
    Double capital;        //资金资产
    Double secuVal;          //证券市值
    Double totalAssets;            //总资产
    long timestamp;


    //初始化构造函数
    public Orders(int t, String consn, char wd) {
        custCode = t;
        contractsSn = consn;
        isWithdraw = wd;
    }

    //属性get方法，用于外部接口调用获取对象属性值。
    public int getCustCode() {
        return custCode;
    }


    public double getCapital() {
        return capital;
    }

    public double getSecuVal() {
        return secuVal;
    }

    public double getTotalAssets() {
        return totalAssets;
    }

    public long getTimestamp() {return  timestamp;}

    public char getIsWithdraw() {return isWithdraw;}

    public String  getContractsSn() {return contractsSn;}

    //属性set方法，用于外部修改对象属性值。总资产参数随着资金或股份有变动而实时更新
    public void setCapital(Double cap) {
        this.capital = cap;
        this.totalAssets = this.capital + this.secuVal;
    }

    public void setSecuSum(Double secu) {
        this.secuVal = secu;
        this.totalAssets = this.capital + this.secuVal;
    }
    public void setTimestamp(long tim) {
        this.timestamp = tim;
    }
    //重写类对象的toString方法，将显示结果改为想要的格式
    @Override
    public String toString() {
        return "Assets: " + totalAssets.toString() +
                " Capi: " + capital.toString() +
                " Secu: " + secuVal.toString() ;
    }
}

    @Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector)
    {
        this.collector = collector;
        this.counts = new HashMap<String,Integer>();
        Configuration cepConfig = new Configuration();
        cepConfig.addEventType("Orders", Orders.class.getName());
        // 创建cepService服务
        epService = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
        cepRT = epService.getEPRuntime();
        epAdmin = epService.getEPAdministrator();

        // EPL语句0：创建根据Order事件的客户号属性进行内部分组的context语句
        //根据Orders  pojo类中的 custCode属性进行分组
        String eplStr0 = " create context esbtest partition by custCode from Orders  ";
        epAdmin.createEPL(eplStr0);//context 创建不需要绑定监听，直接通过EPAdmin对象执行EPL语句即可。

        // EPL语句1：监听同一客户内，是否每秒钟出现5笔委托的异常(每2秒清理一次)，满足触发条件的事件不会被重复监听
        String eplStr1 = "context esbtest select cast(a1.custCode,int) as abnormalCust, 1 as typeDef" +
                " from pattern " +
                " [every (a1 = Orders -> a2 = Orders -> a3 = Orders -> a4=Orders -> a5=Orders) where timer:within(1 seconds)].win:time(2) " +
                " ";
        EPStatement cepStatement = epAdmin.createEPL(eplStr1);
        cepStatement.addListener(this); //监听类型1，绑定this内监听

        // EPL语句2：监听同一客户内是否存在一秒内同一合同号的下单和撤单操作。和前一个EPL保持同样的结构。固定TYPE字段来区分输出结果
        String eplStr2 = "context esbtest select a.custCode as abnormalCust, a.contractsSn as constrSn, 2 as typeDef " +
                " from pattern " +
                " [every (a = Orders(isWithdraw = cast(\'0\',char)) -> b = Orders(b.contractsSn = a.contractsSn and b.isWithdraw != a.isWithdraw)) where timer:within(1 seconds)].win:time(5)  " +
                " ";

        EPStatement cepStatement2 = epAdmin.createEPL(eplStr2);
        cepStatement2.addListener(this); //需要绑定同一个类内监听器，否则类对象外监听，实现不了.collector的emit功能

    }

    // 获取到数据时的处理，默认传来的对象符合事件类，
    @Override
    public void execute(Tuple arg0) {
        //对传来的数据进行解析，手动封装为事件类。
        String strCustCode = String.valueOf(arg0.getValueByField("CUST_CODE"));
        int a=0;
        try {
             a = Integer.parseInt(strCustCode);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Orders ord=new Orders(a,"",'0'); //只取客户代码

        this.tupleTrans=arg0;
        cepRT.sendEvent(ord); //用类对象作esper的触发事件。
        this.collector.ack(arg0);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer arg0) {
        arg0.declare(new Fields("REDIS_KEY", "REDIS_VAL"));
    }

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        String szRedisKey = "";
        String szRedisVal = "";

        if (newEvents != null) {
            for (int i = 0; i < newEvents.length; i++) {
                // 输出组装
                int abnormalCust = (int)newEvents[i].get("abnormalCust") ;
                int typeNum = (int)newEvents[i].get("typeDef");
                // 判断类型，调整组串数据
                if (typeNum == 1)
                {
                    SimpleDateFormat DateFormat = new SimpleDateFormat
                            ("yyyyMMdd HHmmss");
                    Date timeStamp = new Date((System.currentTimeMillis()));
                    String typeStr = "frequent order over 5 in 1 second";
                    System.out.println("Event received1 " +i+ " type:[5 in 1sec]"+ " cusCode:"+abnormalCust);// + ", seccode="3
                    int count = 0;
                    if (this.counts.get(""+abnormalCust) == null)
                    {
                        count++;
                    }
                    else
                    {
                        count=this.counts.get(""+abnormalCust).intValue();
                        count++;
                    }

                    szRedisKey = "[" + "FREQ_ORDER:" + abnormalCust + "]";
                    szRedisVal = "[TYPE:" + typeStr + ",COUNT:" + count + ", TIMESTAMP:" +DateFormat.format(timeStamp) +"]";

                    //执行数据传递
                    this.counts.put(""+abnormalCust,count);
                    this.collector.emit(this.tupleTrans,new Values(szRedisKey, szRedisVal));
                    //输出两个string，前者为客户代码，后面是信息类型与计数的组串

                }
                else if (typeNum == 2) //单日撤单处理暂不启用
                {
//                    String abnormalOrder = newEvents[i].get("constrSn").toString() ;
//                    System.out.println("Event received1 " +i+ " type:[Order & Draw in 1sec]"+ " custCode:"+abnormalCust+ " constrSn:"+abnormalOrder);
//                    this.collector.emit(new Values(" " +i+ " code:"+abnormalCust+ " type:[Order & Draw in 1sec]"));
                }

            }
        }

    }

}
