package com.yjl.multi_thread.threadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *      使用jdk1.5 的接口Lock ，替换同步代码块，实现线程安全
 *      Lock接口方法：
 *          lock（）获取锁
 *          unlock（）释放锁
 *      locl接口的实现类 ReentranLock
*/
public class Tickets implements Runnable{

    //定义可以出售的票源
    private int ticket = 100;
    //在类的成员位置，创建Lock接口的实现类对象
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while(true){
            //调用Lock接口方法lock获取锁
            lock.lock();
                //对票数进行判断，大于0，可以出售，变量--操作
                if(ticket > 0){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"出售第"+ ticket--);
                }
            //释放锁，调用Lock接口方法unLock释放锁
            lock.unlock();
        }
    }
}
