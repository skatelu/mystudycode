package com.yjl.multi_thread.theadanquan;

/**
 *  多线程并发访问同一个数据资源
 *  3个线程，对一个票资源，出售
 */
public class ThreadDemo {

    public static void main(String[] args) {
        //创建Runnable接口实现类对象
        Tickets1 t = new Tickets1();
        //创建三个Thread类对象，传递Runnable接口实现类
        Thread t0 = new Thread(t);
        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);

        t0.start();
        t1.start();
        t2.start();
    }
}
