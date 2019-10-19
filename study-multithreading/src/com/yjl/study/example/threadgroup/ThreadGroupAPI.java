package com.yjl.study.example.threadgroup;

import java.util.Arrays;

public class ThreadGroupAPI {

    public static void main(String[] args) {
        //use the name
        ThreadGroup tg1 = new ThreadGroup("TG1");
        Thread t1 = new Thread(tg1, "T1") {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();

        ThreadGroup tg2 = new ThreadGroup(tg1,"TG2");
        Thread t2 = new Thread(tg2,"T2"){
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(10_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t2.start();

        System.out.println(tg1.activeCount());
        System.out.println(tg1.activeGroupCount());

        t2.checkAccess();//检查是否有权限修改此线程

        System.out.println("======================================");
        Thread[] ts1 = new Thread[tg1.activeCount()];
        tg1.enumerate(ts1);//拷贝一些线程，放到其他thread 数组中
        Arrays.asList(ts1).forEach(System.out::println);

        System.out.println("======================================");
        tg1.enumerate(ts1, true);//true 表示使用递归来实现拷贝所有的线程组与线程
        Arrays.asList(ts1).forEach(System.out::println);
    }
}
