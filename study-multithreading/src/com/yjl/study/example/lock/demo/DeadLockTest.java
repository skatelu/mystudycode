package com.yjl.study.example.lock.demo;

public class DeadLockTest {

    public static void main(String[] args) {
        DeadLock deadLock = new DeadLock();
        OtherService otherService = new OtherService(deadLock);
        deadLock.setOtherService(otherService);

        new Thread(()->{
            while(true){
                otherService.s1();
            }
        }).start();

        new Thread(()->{
            while (true){
                deadLock.m2();
            }
        }).start();
    }
}
