package com.yjl.study.example.studylambda;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.Comparator;
import java.util.function.*;

/**
 * 构造器引用：
 *      格式：
 *          ClassName::new
 *       注意：
 *          需要调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致！
 *
 * 数组引用：
 *      type::new;
 */
public class TestMethodRef2 {

    //数组引用
    @Test
    public void test3(){
        Function<Integer, String[]> fun = (x) -> new String[x];
        String[] apply = fun.apply(10);

        Function<Integer, String[]> fun2 = String[]::new;
        String[] apply1 = fun2.apply(5);

        Gson gson = new Gson();
        String s = gson.toJson(apply1);
        System.out.println(s);
    }

    //构造器引用
    @Test
    public void test(){
        Supplier<Employee> sup = ()->new Employee();
        sup.get(); // 返回一个Employee对象

        //构造器引用方式  匹配的是无参的构造方法
        Supplier<Employee> sup2 = Employee::new;

    }

    @Test
    public void test2(){
//        Function<Integer, Employee> fun = (x) -> new Employee(x);
//
//        // 调用的是带有一个参数的构造方法
//        Function<Integer, Employee> fun2 = Employee::new;
//
//        BiFunction<Integer,Integer,Employee> fun3 = Employee::new;
    }
}
