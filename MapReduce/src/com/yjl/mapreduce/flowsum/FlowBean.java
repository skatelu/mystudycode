package com.yjl.mapreduce.flowsum;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FlowBean implements Writable {

    private long upFlow; //上行流量
    private long downFlow; //下行流量
    private long sumFlow; //总流量

    //必须要有空参构造函数，为了后续反射用
    public FlowBean() {
        super();
    }

    public FlowBean(long upFlow, long downFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = upFlow + downFlow;
    }

    public void set(long upFlow, long downFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = upFlow + downFlow;
    }

    //序列化方法
    @Override
    public void write(DataOutput output) throws IOException {
        output.writeLong(upFlow);
        output.writeLong(downFlow);
        output.writeLong(sumFlow);
    }

    //反序列化方法
    //注意：序列化顺序和反序列化顺序必须一致
    @Override
    public void readFields(DataInput input) throws IOException {
        this.upFlow = input.readLong();
        this.downFlow = input.readLong();
        this.sumFlow = input.readLong();
    }

    @Override
    public String toString() {
        return  upFlow + "\t" + downFlow + "\t" + sumFlow;
    }

    public long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }

    public long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(long downFlow) {
        this.downFlow = downFlow;
    }

    public long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }
}
