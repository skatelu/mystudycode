package com.yjl.study.example.studylambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 *  一、Lambda 表达式的基础语法
 *   java8 中引入了一个新的操作符 “->” 该操作符称为箭头操作或Lambda 操作符
 *   箭头操作符将 Lambda 表达式拆分成两部分：
 *   左侧：Lambda 表达式的参数列表
 *   右侧： Lambda 表达式中所需执行的功能，即 Lambda 体
 *   函数式接口：即lambda表达式中实现的函数，只有一个方法时，称为函数式接口
 *
 *   语法格式一：
 *      无参数，无返回值的方法
 *      () -> System.out.println("Hello Lambda");
 *
 *   语法格式二、
 *      有一个参数，并且无返回值
 *
 *   语法格式三、
 *      若只有一个参数，小括号可以省略不写
 *      x -> System.out.println(x)
 *
 *   语法格式四：
 *      有两个以上的参数，有返回值，并且 lambda 体中有多条语句  这个时候Lambda体需要使用大括号
 *        Comparator<Integer> com = (a,b) ->{
 *             System.out.println("函数式接口");
 *             return Integer.compare(a, b);
 *         };
 *
 *   语法格式五：
 *      若Lambda体中只有一条语句，return 和 大括号都可以省略不写
 *      Comparator<Integer> com = (a,b) -> Integer.compare(a,b);
 *
 *   语法格式六：
 *      Lambda 表达式的参数列表数据类型可以省略不写，因为JVM编译器，可以通过上下文推断出数据类型，即“类型推断”
 *
 *   二、Lambda 表达式需要“函数式接口”的支持
 *   函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。 可以使用注解 @FunctionalInterface 修饰
 *   可以检查是否是函数式接口
 */
public class TestLambda2 {

    @Test
    public void test1(){
        Runnable r1 = () -> System.out.println("Hello Lambda");
        r1.run();
    }

    @Test
    public void test2(){
        Consumer<String> con = x ->{
            System.out.println(x);
        };
        con.accept("Lambda 好神奇！");
    }

    @Test
    public void test4(){
        Comparator<Integer> com = (a,b) ->{
            System.out.println("函数式接口");
            return Integer.compare(a, b);
        };
    }

    @Test
    public void test5(){
        Comparator<Integer> com = (a,b) -> Integer.compare(a,b);
    }


    //需求：对一个数进行运算
    @Test
    public void test6(){
        Integer num = operation(100, n -> n * n);
        System.out.println(num);
    }

    public Integer operation(Integer num,MyFun myFun){
        return myFun.getValue(num);
    }

}
