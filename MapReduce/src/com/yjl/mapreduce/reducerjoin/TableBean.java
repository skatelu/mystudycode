package com.yjl.mapreduce.reducerjoin;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TableBean implements Writable {

    private String order_id; //订单id
    private String pid; //产品id
    private int amount; //产品数量
    private String pName; //产品名称
    private String flag; //标记是订单表（0）还是产品表（1）

    public TableBean() {
        super();
    }

    public TableBean(String order_id, String pid, int amount, String pname, String flag) {
        this.order_id = order_id;
        this.pid = pid;
        this.amount = amount;
        this.pName = pName;
        this.flag = flag;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(order_id);
        dataOutput.writeUTF(pid);
        dataOutput.writeInt(amount);
        dataOutput.writeUTF(pName);
        dataOutput.writeUTF(flag);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.order_id = dataInput.readUTF();
        this.pid = dataInput.readUTF();
        this.amount = dataInput.readInt();
        this.pName = dataInput.readUTF();
        this.flag = dataInput.readUTF();
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return order_id + "\t" + pName + "\t"+ amount + "\t" ;
    }
}
