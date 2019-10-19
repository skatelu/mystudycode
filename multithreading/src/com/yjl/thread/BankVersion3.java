package com.yjl.thread;

import java.util.ArrayList;

public class BankVersion3 {

    public static void main(String[] args) {
        final SynchronizedRunnable synchronizedRunnable = new SynchronizedRunnable();

        Thread windowThread1 = new Thread(synchronizedRunnable, "一号窗口");
        Thread windowThread2 = new Thread(synchronizedRunnable, "二号窗口");
        Thread windowThread3 = new Thread(synchronizedRunnable, "三号窗口");
        windowThread1.start();
        windowThread2.start();
        windowThread3.start();

        new ArrayList<>();
    }
}
