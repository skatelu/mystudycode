package com.yjl.com.yjl.concurrency.threadapi;

/**
 * 在线程中设置一个sleep方法，当调用interrupt() 方法的时候，会报异常，
 * 运行break 打断当前进程，从而结束线程
 */
public class ThreadCloseGraceful2 {

    private static class Worker extends Thread{

        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

    }
    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        worker.interrupt();
    }

}
