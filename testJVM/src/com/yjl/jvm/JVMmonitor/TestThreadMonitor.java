package com.yjl.jvm.JVMmonitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * java线程监控：
 * 线程长时间停顿的主要原因有等待外部资源（数据库链接、网络资源、设备资源等）、死循环、锁等等（活锁或死锁）
 *
 */
public class TestThreadMonitor {

    /**
     * 线程死循环演示
     */
    public static void createBusyThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){

                }

            }
        },"testBusyThread");
        thread.start();
    }

    /**
     * 线程锁等待演示
     * @param lock
     */
    public static void createLockThread(final Object lock){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"testLockThread");
        thread.start();
    }

    public static void main(String[] args)throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        //死循环线程
        createBusyThread();
        br.readLine();
        Object obj = new Object();
        //线程锁
        createLockThread(obj);

    }
}
