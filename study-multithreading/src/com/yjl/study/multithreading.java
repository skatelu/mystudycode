package com.yjl.study;

public class multithreading {

    public static void main(String[] args) {
        Thread thread = new Thread("READ-Thread") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("我已经执行");
            }
        };

        thread.start();
    }

}
