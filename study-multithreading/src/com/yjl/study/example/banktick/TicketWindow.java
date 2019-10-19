package com.yjl.study.example.banktick;

/**
 * 模拟银行叫号系统
 */
public class TicketWindow extends Thread{

    private final String name;
// 当命名为static静态资源的时候，表示这个程序用这一个变量
//    但是static 变量是放在JVM的方法常量区中的，容易造成jvm对内存过大
    private static final int MAX = 50;

    private static int index =  1;

    public TicketWindow(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (index <= MAX){
            System.out.println("柜台："+name+"当前的号码是："+ (index++));
        }
    }
}
