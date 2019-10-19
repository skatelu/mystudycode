package com.yjl.study.example.studayStream;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join 框架   递归的不断去拆分
 *
 * 实现时需要继承 RecursiveTask 或 RecursiveAction 抽象类
 *
 * RecursiveTask 有返回值
 * RecursiveAction:无返回值
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;

    private static final long THRESHOLD = 10000L;

    public ForkJoinCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;

        if (length <= THRESHOLD) {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }else {
            long middle = (start + end) / 2;

            ForkJoinCalculate left = new ForkJoinCalculate(start, middle);
            left.fork();//拆分成子任务，同时加入线程队列
            ForkJoinCalculate right = new ForkJoinCalculate(middle + 1, end);
            right.fork();

            return left.join() + right.join();
        }
    }
}
