package com.yjl.multi_thread.threadfuzhi;

/**
 *     定义资源类，有两个成员变量
 *     name,sex
 *     同时有2个线程，对资源中的变量操作
 *     1个对name,age赋值
 *     2个对name,age做变量的输出打印
 */
public class Resource {

    public String name;
    public String sex;
    public Boolean flag = false;
}
