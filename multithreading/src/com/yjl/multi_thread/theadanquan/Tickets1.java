package com.yjl.multi_thread.theadanquan;

/**
 *   采用同步方法形式，解决线程安全问题
 *   好处：代码简洁
 *   将线程共享数据，和同步，抽取到一个方法中
 *   载方法的声明上，加入同步关键字
 *   问题：
 *      同步方法有锁么？肯定有。同步方法中的对象锁，是本类对象的引用
 */
public class Tickets1 implements Runnable{

    //定义可以出售的票源
    private int ticket = 100;
    private Object obj = new Object();

    @Override
    public void run() {
        while (true){
            payTicket();
        }
    }

    //同步方法的写法  在方法声明上 写上同步关键字 synchronized
    public synchronized void payTicket() {
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
