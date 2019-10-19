package com.yjl.study.example.lock.demo;

public class OtherService {

    private final Object ob = new Object();

    DeadLock deadLock = new DeadLock();

    public OtherService(DeadLock deadLock) {
        this.deadLock = deadLock;
    }

    public void s1(){
        synchronized (ob){
            System.out.println("正在执行S1任务");
            deadLock.m1();
        }
    }

    public void s2(){
        synchronized (ob){
            System.out.println("正在执行S2任务");
        }
    }
}
