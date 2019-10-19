package com.yjl.mapreduce.sortorder;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OrderBean implements WritableComparable<OrderBean> {

    private int order_id;//订单 id
    private double price; // 价格

    public OrderBean() {
        super();
    }

    public OrderBean(int order_id, double price) {
        this.order_id = order_id;
        this.price = price;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(order_id);
        dataOutput.writeDouble(price);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.order_id = dataInput.readInt();
        this.price = dataInput.readDouble();
    }

    @Override
    public String toString() {
        return order_id + "\t" + price;
    }

    public int getOrder_id() {
        return order_id;
    }

    public double getPrice() {
        return price;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int compareTo(OrderBean o) {
        //比较id
        //int result = order_id > o.getOrder_id() ? -1 : 1;
        int result;
        if(order_id > o.getOrder_id()){
            result = 1;
        }else if(order_id < o.getOrder_id()){
            result = -1;
        }else {
            result = price > o.getPrice() ? -1 : 1;
        }
        return result;
    }
}
