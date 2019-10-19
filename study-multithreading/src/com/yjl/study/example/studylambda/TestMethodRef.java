package com.yjl.study.example.studylambda;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 方法引用与构造器引用
 * 方法引用：若Lambda 体中的内容有方法已经实现，我们可以使用“方法引用”
 *          （可以理解为 方法引用是 Lambda 表达式的另外一种表现形式）
 *
 *  主要有三种语法格式：
 *      对象::实例方法名
 *      类::静态方法名
 *      类::实例方法名
 *
 *  注意 ：
 *      lambda 体中调用方法的参数列表和返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型一致！
 *      若 Lambda 参数列表中的第一参数是 实例方法的调用者，而第二个参数是实力方法的参数时，可以使用ClassName::method
 */
public class TestMethodRef {

//    类::实例方法名
    public void test3(){
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);

        BiPredicate<String, String> bp2 = String::contains;

    }


//    类::静态方法名
    @Test
    public void test2(){
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

        Comparator<Integer> com1 = Integer::compareTo;

        int compare = com1.compare(100,20);
        System.out.println(compare);
    }


    // 对象::实例方法名
    @Test
    public void test1(){
        Consumer<String> con = (x) -> System.out.println(x);

        Consumer<String> con1 = System.out::println;
    }

}
