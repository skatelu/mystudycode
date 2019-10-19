package com.yjl.study.example.studayStream;

import com.yjl.study.example.studylambda.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 一、Stream 的三个操作步骤
 *      1、创建Stream
 *
 *      2、中间操作
 *
 *      3、终止操作（终端操作）
 */
public class TestStreamAPI1 {

    //创建Stream
    @Test
    public void test1(){
        //1、可以通过Collection 系列集合提供的Stream（）串行流 或 parallelStream（）并行流
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2、通过Arrays中的静态方法 Stream（）获取数组流
        Employee[] emps = new Employee[10];
        Stream<Employee> stream2 = Arrays.stream(emps);

        //3、通过Stream中的静态方法 of（）
        Stream<String> stream3 = Stream.of("aa", "bb", "cc");

        //4、创建无限流
        //迭代  在此处的含义：从0开始，无限次数+2
        Stream.iterate(0, (x) -> x + 2)
                .limit(10) //中间操作 只产生10个
                .forEach(System.out::println);
        //生成
        Stream.generate(() -> Math.random())
                .limit(100)
                .forEach(System.out::println);

    }

}
