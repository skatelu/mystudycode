package com.yjl.multi_thread;

/**
 * 获取线程名称,父类Thread方法
 *  String getName（）
 */
public class NameThread extends Thread{

    public void run() {
        setName("getName");
        System.out.println(getName());//获取线程名称

    }
}
