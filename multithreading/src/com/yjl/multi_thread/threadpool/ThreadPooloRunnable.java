package com.yjl.multi_thread.threadpool;

public class ThreadPooloRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("线程提交任务");
    }
}
