package com.yjl.study.example.banktick;

import java.util.Map;

/**
 * 银行叫号系统，使用runnable接口，奖逻辑数据与代码的执行单元从线程中分离开
 */
public class TicketWindowRunnable implements Runnable {

    private int index = 0;

    private final static int MAX = 50;

    @Override
    public void run() {
        while (index <= MAX){
            System.out.println(Thread.currentThread()+" 的号码是：" + (index++));
        }
    }
}
