package com.yjl.study.example.threadjvm;

import java.util.Optional;

public class DaemonThread {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(){
            @Override
            public void run() {
                try{
                    System.out.println(Thread.currentThread().getName() + "running");
                    Thread.sleep(100000);
                    System.out.println(Thread.currentThread().getName()+ "done.");
                }catch (Error | InterruptedException error){
                    error.printStackTrace();
                }
            }
        };
//        将这个线程设置为守护线程  当main线程结束的时候，此线程也随着结束  默认为false
        t.setDaemon(true);

        t.start();

        Thread.sleep(5_000);
        Optional.of(Thread.currentThread().getPriority()).ifPresent(System.out::println);
    }

}
