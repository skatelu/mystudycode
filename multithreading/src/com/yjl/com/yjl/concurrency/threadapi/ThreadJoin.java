package com.yjl.com.yjl.concurrency.threadapi;

import java.util.Optional;
import java.util.stream.IntStream;

/**
 * thread join方法
 */
public class ThreadJoin {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            IntStream.range(1,1000)
                    .forEach(i-> System.out.println(Thread.currentThread().getName() +"->"+i));
        });
        Thread t2 = new Thread(() -> {
            IntStream.range(1,1000)
                    .forEach(i-> System.out.println(Thread.currentThread().getName() +"->"+i));
        });
        t1.start();
        t2.start();
        t1.join();//当前线程执行完成后，执行下开启这个线程的线程
        t2.join();// 如果有参数 如 (100,10)表示100毫秒，10纳秒以后若没执行完成，则直接执行main线程
        Optional.of("All of task finaish done.").ifPresent(System.out::println);

        IntStream.range(1,1000)
                .forEach(i-> System.out.println(Thread.currentThread().getName() +"->"+i));
    }
}
