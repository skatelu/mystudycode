package com.yjl.multi_thread;

public class SleepThread extends Thread{

    public void run() {

        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(i);
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
