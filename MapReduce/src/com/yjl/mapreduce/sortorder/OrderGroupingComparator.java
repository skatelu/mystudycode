package com.yjl.mapreduce.sortorder;

import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class OrderGroupingComparator extends WritableComparator {

    /**
     * 必须要添加空参构造方法，并且里面需要有super(OrderBean.class,true);
     */
    protected OrderGroupingComparator(){
        super(OrderBean.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {

        OrderBean aBean = (OrderBean) a;
        OrderBean bBean = (OrderBean) b;

        int result;

        // id相同即认为是同一个对象
        if(aBean.getOrder_id() > bBean.getOrder_id()){
            result = 1;
        }else if(aBean.getOrder_id() < bBean.getOrder_id()){
            result = -1;
        }else {
            result = 0;
        }

        return result;
    }
}
