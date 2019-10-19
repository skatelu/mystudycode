package com.yjl.study.example.studylambda;

import com.google.gson.Gson;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * java8 Lambda 内置的四大核心函数式接口
 *
 *  Consumer<T>:消费性接口
 *          void accept(T t);
 *
 *  Supplier<T> : 供给型接口
 *          T get();
 *
 *   Function<T,R>:函数型接口
 *          R apply(T t)
 *
 *   Predicate<T>:断言型接口
 *          boolean test(T t)
 *
 *
 */
public class TestLambda3 {

    //Predicate<T> 断言型接口
    @Test
    public void test4(){
        List<String> lis = Arrays.asList("Hello", "World", "Thanks", "The", "Country");

        List<String> list = filter(lis, (c) -> {
            return c.length() > 3;
        });
        Gson gson = new Gson();
        String s = gson.toJson(list);
        System.out.println(s);
    }

    // 需求：将满足条件的字符串，放入到集合中
    public List<String> filter(List<String> list, Predicate<String> pre){
        List<String> stringList = new ArrayList<>();
        for (String s : list) {
            boolean test = pre.test(s);
            if(test){
                stringList.add(s);
            }
        }
        return stringList;
    }

    //Function(T,R):函数型接口:
    @Test
    public void test3(){
        String newStr = strHandler("\t\t\t helloWorld", (str) -> str.trim());
        System.out.println(newStr);

        String subStr = strHandler("helloWorld你好世界", (str) -> str.substring(10, 14));
        System.out.println(subStr);

    }
    // 用于处理字符串
    public String strHandler(String str, Function<String,String> fun){
        return fun.apply(str);
    }

    //supplier<T> 供给型接口
    @Test
    public void test2(){
        List<Integer> numList = getNumList(10, () -> (int)(Math.random() * 100));
        for (Integer integer : numList) {
            System.out.println(integer);
        }
    }

    //需求：产生一些整数，并放入集合中
    public List<Integer> getNumList(int num, Supplier<Integer> sup){
        List<Integer> list = new ArrayList<>();
        for (int i = 0;i<num;i++) {
            Integer n = sup.get();
            list.add(n);
        }
        return list;
    }

    //consumer<T>  消费型接口
    @Test
    public void test1(){
        happy(10000,(m) -> System.out.println("刚哥大宝剑，每次消费："+m+"元"));
    }

    public void happy(double money, Consumer<Double> con){
        con.accept(money);
    }
}
