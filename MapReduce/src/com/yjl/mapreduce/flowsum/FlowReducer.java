package com.yjl.mapreduce.flowsum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowReducer extends Reducer<Text,FlowBean, Text,FlowBean> {
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {

        long sum_upFlow = 0;
        long sum_downFlow = 0;

      //1.累加求和
        for(FlowBean flowBean : values){
            sum_upFlow += flowBean.getUpFlow();
            sum_downFlow += flowBean.getUpFlow();
        }

        FlowBean flowBean = new FlowBean(sum_upFlow,sum_downFlow);

        //2.输出
        context.write(key,flowBean);
    }
}
