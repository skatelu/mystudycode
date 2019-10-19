package com.yjl.multi_thread.threadfuzhi;

public class ThreadDemo {

    public static void main(String[] args) {
        Resource r = new Resource();
        input in = new input(r);
        Output out = new Output(r);

        Thread t0 = new Thread(in);
        Thread t1 = new Thread(out);

        t0.start();
        t1.start();
    }

}
