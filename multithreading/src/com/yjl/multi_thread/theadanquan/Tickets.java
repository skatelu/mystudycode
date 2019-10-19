package com.yjl.multi_thread.theadanquan;

/**
 *      通过线程休眠，出现安全问题
 *      解决安全问题，java程序，提供技术：同步技术
 *      公式：
 *          synchronized(任意的对象){
 *              线程要操作的共享数据
 *          }
 *      同步代码块
 *      同步安全性：没有锁的线程不能执行只能等待
 */
public class Tickets implements Runnable{

    //定义可以出售的票源
    private int ticket = 100;
    private Object obj = new Object();

    @Override
    public void run() {
        while(true){
            //线程共享数据，保证安全，加入同步代码块
            synchronized (obj){
                //对票数进行判断，大于0，可以出售，变量--操作
                if(ticket > 0){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"出售第"+ ticket--);
                }
            }
        }
    }
}
