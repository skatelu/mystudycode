package com.yjl.multi_thread.testrunnable;

public class ThreadDemoRunnable {

    public static void main(String[] args) {
        SubRunnable sr = new SubRunnable();
        new Thread(sr).start();

        for (int i = 0; i < 500; i++) {
            System.out.println("main"+i);
        }
    }

}
