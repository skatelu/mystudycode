package com.yjl.study.example.studayStream;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class TestForkJoin {

    /**
     * Fork/Join框架
     */
    @Test
    public void test1(){
        //java8 计算时间
        Instant start = Instant.now();

        // 首先 fork/jion 需要一个线程池的支持
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinCalculate(0, 1000000000L);

        Long sum = pool.invoke(task);
        System.out.println(sum);//29097006

        Instant end = Instant.now();

        System.out.println("消耗的时间为：" + Duration.between(start,end).toMillis());
    }

    @Test
    public void test2(){



        long sum = 0L;
        for (long i = 0; i < 1000000000L; i++) {

            sum += i;
        }
        System.out.println(sum); //499999999500000000
    }

    /**
     * java8 并行流
     * java8 中将并行进行了优化，我们可以很容的对数据进行并行操作。
     * Stream API 可以声明性地通过 parallel()【并行流】 与 sequential()[串行流] 在并行流与顺序流之间进行切换。
     */
    @Test
    public void test3(){
        Instant start = Instant.now();

        long reduce = LongStream.rangeClosed(0, 100000000000L)
                .parallel()
                .reduce(0, Long::sum);
        System.out.println(reduce);

        Instant end = Instant.now();

        System.out.println("消耗时间为："+Duration.between(start,end).toMillis());
    }
}
