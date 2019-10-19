package com.yjl.mapreduce.reducerjoin;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;

import static org.apache.commons.beanutils.BeanUtils.copyProperties;


public class TableReducer extends Reducer<Text,TableBean,TableBean, NullWritable> {


    @Override
    protected void reduce(Text key, Iterable<TableBean> values, Context context) throws IOException, InterruptedException {

        //准备集合
        ArrayList<TableBean> orderBeans = new ArrayList<>();
        TableBean pbBean = new TableBean();

        for (TableBean value : values) {

            if("0".equals(value.getFlag())){//订单表
                TableBean tableBean = new TableBean();

                try {
                    copyProperties(tableBean,value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                orderBeans.add(tableBean);
            }else {
                //产品表
                try {
                    copyProperties(pbBean,value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        for (TableBean orderBean : orderBeans) {
            orderBean.setpName(pbBean.getpName());
            context.write(orderBean,NullWritable.get());
        }
    }
}
