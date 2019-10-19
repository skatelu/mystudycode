package com.yjl.thread;

public class SynchronizedRunnable implements Runnable{
    private int index = 1;

    private final static int MAX=500;

    private final Object MONITOR = new Object();

    /**
     * 这个锁是this
     */
    @Override
    public void run() {
        while (true){
            if (ticket()){
                break;
            }
        }
    }

    private synchronized boolean ticket(){
        if(index > MAX){
            return true;
        }
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread()+" 的号码是：" + (index++));
        return false;
    }
}
