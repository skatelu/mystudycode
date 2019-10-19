package com.yjl.study.example.banktick;

/**
 * 执行runnable 实现的业务与逻辑数据分离功能
 */
public class BankVersion2 {

    private final static int MAX = 50;
    public static void main(String[] args) {

        final TicketWindowRunnable ticketWindowRunnable = new TicketWindowRunnable();

//        final Runnable ticketWindowRunnable = () ->{
//            int index = 1;
//            while (index <= MAX){
//                System.out.println(Thread.currentThread()+" 的号码是：" + (index++));
//            }
//        };

        Thread windowThread1 = new Thread(ticketWindowRunnable,"一号窗口");
        Thread windowThread2 = new Thread(ticketWindowRunnable,"二号窗口");
        Thread windowThread3 = new Thread(ticketWindowRunnable,"三号窗口");

        windowThread1.start();
        windowThread2.start();
        windowThread3.start();
    }
}
