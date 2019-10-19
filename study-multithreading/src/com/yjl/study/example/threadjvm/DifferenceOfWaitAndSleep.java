package com.yjl.study.example.threadjvm;

import java.util.stream.Stream;

public class DifferenceOfWaitAndSleep {

    public final static Object LOCK = new Object();

    public static void main(String[] args) {
        Stream.of("T1","T2").forEach(name->{
            new Thread(()->{
//                m1();
                m2();
            },name).start();
        });
    }

    public static void m1(){
        synchronized (LOCK){
            try {
                System.out.println("The Thread " + Thread.currentThread().getName()+"  enter.");
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void m2(){
        synchronized (LOCK){  //wait 需要在synchronized中使用
            try {
                System.out.println("The Thread " + Thread.currentThread().getName()+"  enter.");
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
