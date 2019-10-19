package com.yjl.study.example.lock;

/**
 * 死锁现象
 * @author yjl create 2019/04/25 14:05
 */
public class DeadLock {

    private OtherService otherService = new OtherService();

    public DeadLock(OtherService otherService){
        this.otherService = otherService;
    }

    private final Object lock = new Object();

    public void m1(){
        synchronized (lock){
            System.out.println("m1");
            otherService.s1();
        }
    }

    public void m2(){
        synchronized (lock){
            System.out.println("m2");
        }
    }
}
