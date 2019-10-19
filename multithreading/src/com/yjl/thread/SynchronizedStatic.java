package com.yjl.thread;

public class SynchronizedStatic {

    public synchronized static void m1(){
        System.out.println("m1" + Thread.currentThread().getName());
        try {
            Thread.sleep(10_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void m2(){
        System.out.println("m2" + Thread.currentThread().getName());
        try {
            Thread.sleep(10_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
