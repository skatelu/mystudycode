package com.yjl.multi_thread;

/**
 * 获取线程名称
 *  每个线程都有自己的名字
 *  运行方法main线程，名字就是“main”
 *  其他新建线的线程也有名字，默认“Thread-0”,"Thread-1"
 *
 *  JVM开启线程，运行方法main，主线程也是线程，是线程必然就是Thread类对象
 *  在Thread类当中，定义了一个静态方法：
 *      (通用方式)static Thread currentThread（） 返回正在执行的线程对象
 */
public class ThreadDemo2 {

    public static void main(String[] args) {
        /*NameThread nt = new NameThread();
        nt.start();

        //拿到main线程的名称
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName());*/

        //线程的Sleep方法
        SleepThread sleepThread = new SleepThread();
        sleepThread.start();
    }

}
