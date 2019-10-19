package com.yjl.multi_thread.testrunnable;

/**
 * 实现线程的另外一个方式，实现runnable接口，重写run方法
 */
public class SubRunnable implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 500; i++) {
            System.out.println(i);
        }
    }
}
