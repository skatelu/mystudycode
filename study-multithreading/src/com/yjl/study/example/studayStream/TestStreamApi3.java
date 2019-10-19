package com.yjl.study.example.studayStream;

import com.yjl.study.example.studylambda.Employee;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import sun.net.www.content.audio.x_aiff;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 终止操作
 */
public class TestStreamApi3 {

    List<Employee> employees = Arrays.asList(
            new Employee("张三",18,9999.99,Employee.Status.Free),
            new Employee("李斯",58,5555.55,Employee.Status.Busy),
            new Employee("王五",26,3333.33,Employee.Status.VOCATION),
            new Employee("赵六",36,6666.66,Employee.Status.Free),
            new Employee("田七",12,8888.88,Employee.Status.VOCATION),
            new Employee("田七",12,8888.88,Employee.Status.Free),
            new Employee("田七",12,8888.88,Employee.Status.Busy),
            new Employee("田七",12,8888.88,Employee.Status.Busy)
    );

    /*
        收集：
            collect--将流转换为其他形式。接收一个Collector接口的实现，用于给Stream中元素做汇总
     */
//    分区
    @Test
    public void test8(){
        Map<Boolean, List<Employee>> collect = employees.stream()
                .collect(Collectors.partitioningBy((e) -> e.getScalay() > 8000));
        Collection<List<Employee>> values = collect.values();
        values.forEach(System.out::println);
    }

    //多级分组
    @Test
    public void test7(){
        Map<Employee.Status, Map<String, List<Employee>>> collect = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
                    if (((Employee) e).getAge() <= 35) {
                        return "青年";
                    } else if (((Employee) e).getAge() <= 50) {
                        return "中年";
                    } else {
                        return "老年";
                    }
                })));
        System.out.println(collect);
    }

    //分组
    @Test
    public void test6(){
        Map<Employee.Status, List<Employee>> collect = employees.stream()
                .collect(Collectors.groupingBy((e) -> e.getStatus()));
        System.out.println(collect);
    }
    @Test
    public void test5(){
//        总数
        Long count = employees.stream()
                .collect(Collectors.counting());
        System.out.println(count);

        System.out.println("=================================");

//        平均值
        Double avg = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getScalay));
        System.out.println(avg);

        System.out.println("=================================");

//        工资最高的员工信息
        Optional<Employee> max = employees.stream()
                .collect(Collectors.maxBy((x, y) -> Double.compare(x.getScalay(), y.getScalay())));
        System.out.println(max);

        System.out.println("=================================");

//        工资最少的员工信息
        Optional<Employee> min = employees.stream()
                .collect(Collectors.minBy((x, y) -> Double.compare(x.getScalay(), y.getScalay())));
        System.out.println(min);

    }
    @Test
    public void test4(){
//       放到ArrayList 中
        List<String> list = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println("=================================");
//        放到Set中
        Set<String> collect = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());

        collect.forEach(System.out::println);
    }


    /*
        规约：
            reduce(T identity,BinaryOperator) / reduce(BinaryOperator) -- 可以将流中的元素反复结合起来，得到一个值。

     */
    @Test
    public void test3(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(sum);

        System.out.println("---------------------------");

        Optional<Double> result = employees.stream()
                .map(Employee::getScalay)
                .reduce(Double::sum);

        System.out.println(result.get());
    }

    /*
        查找与匹配
        allMatch--检查是否匹配所有元素
        anyMatch--检查是否至少匹配一个元素
        noneMatch--检查是否没有匹配所有元素
        findFrist--返回第一个元素
        findAny--返回当前流中的任意元素
        count--返回流中元素的总个数
        max--返回流中最大值
        min--返回流中最小值
     */
    @Test
    public void test1(){
        boolean b = employees.stream()
                .allMatch((e) -> e.getStatus().equals(Employee.Status.Busy));
        System.out.println(b);

        boolean b1 = employees.stream()
                .anyMatch((e) -> e.getStatus().equals(Employee.Status.Busy));
        System.out.println(b1);

        // .parallelStream() 并行流，多线程一起执行
        Optional<Employee> op2 = employees.parallelStream()
                .filter((e) -> e.getStatus().equals(Employee.Status.Busy))
                .findAny();
        System.out.println(op2.get());

    }
}
