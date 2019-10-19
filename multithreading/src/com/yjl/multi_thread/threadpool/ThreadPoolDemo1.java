package com.yjl.multi_thread.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 实现线程程序的第三个方式，实现Callable接口方式
 * 实现步骤
 *      工厂类Exectorors静态方法newFixedThreadPool方法，创建线程池对象
 *      线程池对象ExecutorService接口实现类，调用方法submit提交线程任务
 *      submit（Callable c）
 */
public class ThreadPoolDemo1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(2);
        //提交线程的方法submit方法返回Future接口实现类
        Future<String> f = es.submit(new ThreadPoolCallable());
        String s = f.get();
        System.out.println(s);
    }

}
