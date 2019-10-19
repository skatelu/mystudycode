package com.yjl.study.example.lock.demo;

public class DeadLock {

    private final Object ob = new Object();

    private OtherService otherService;
    public void m1(){
        synchronized (ob){
            System.out.println("执行M1任务完成");
        }
    }

    public void m2(){
        synchronized (ob){
            System.out.println("执行M2任务完成");
            otherService.s2();
        }
    }

    public void setOtherService(OtherService otherService) {
        this.otherService = otherService;
    }
}
