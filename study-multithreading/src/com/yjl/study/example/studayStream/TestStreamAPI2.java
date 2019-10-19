package com.yjl.study.example.studayStream;

import com.yjl.study.example.studylambda.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 * Stream 中间操作
 */
public class TestStreamAPI2 {

    List<Employee> employees = Arrays.asList(
      new Employee("张三",18,9999.99),
      new Employee("李斯",58,5555.55),
      new Employee("王五",26,3333.33),
      new Employee("赵六",36,6666.66),
      new Employee("田七",12,8888.88),
      new Employee("田七",12,8888.88),
      new Employee("田七",12,8888.88),
      new Employee("田七",12,8888.88)
    );
    /*
        排序：
        sorted()--自然排序(comparable)
        sorted(Comparator com)--定制排序
     */
    @Test
    public void testSort(){
        List<String> list = Arrays.asList("aaa","bbb","ccc","ddd","eee");
        list.stream()
                .sorted()
                .forEach(System.out::println);

        System.out.println("------------------------------");

        employees.stream()
                .sorted((a,b)-> {
                   if(a.getAge().equals(b.getAge())){
                       return a.getName().compareTo(b.getName());
                   }else {
                       return a.getAge().compareTo(b.getAge());
                   }
                }).forEach(System.out::println);
    }

    /*
        映射：
        map--接收lambda，将元素换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新元素。
        flatMap--接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有的流连城一个流
        通俗点说：map是指把一个个流加入到当前流中，flatMap是把流中的一个个元素加到现在的流中
     */
    @Test
    public void testMap(){
        List<String> list = Arrays.asList("aaa","bbb","ccc","ddd","eee");

        list.stream()
                .map((s)->{
                    return s.toUpperCase();
                }).forEach(System.out::println);
        System.out.println("---------------------------------------");


        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
    }

    @Test
    public void testFlatMap(){
        List<String> list = Arrays.asList("aaa","bbb","ccc","ddd","eee");

        Stream<Stream<Character>> streamStream = list.stream()
                .map(TestStreamAPI2::filterCharacter);
        streamStream.forEach((sm)->{
            sm.forEach(System.out::println);
        });

        System.out.println("---------------------------------------");

        list.stream()
                .flatMap(TestStreamAPI2::filterCharacter)
                .forEach(System.out::println);
    }

    public static Stream<Character> filterCharacter(String str){
        List<Character> list = new ArrayList<>();
        for (char c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }

    /*
      筛选与切片
      filter--接收Lambda，从流中排除某些元素
      limit--截段流，使其元素不超过给定数量
      skip(n)-- 跳过元素，返回一个扔掉了前n各元素的流。若流中元素不足n个，则返回一个空流
      distinct--筛选，通过流所生成元素的hashCode() 和 equals() 去除重复数据  需要重写hashCode() 和 equals() 方法
     */

    @Test
    public void test5(){
        Test1 t1 = new Test1();
        employees.stream()
                .filter((e)->e.getScalay()>3000)
                .skip(2) //跳过前两个
                .distinct() // 筛选，去掉重复数据
                .forEach(System.out::println);
    }

    @Test
    public void test4(){
        Test1 t1 = new Test1();
        employees.stream()
                .filter((e)->e.getScalay()>3000)
                .skip(2) //跳过前两个
                .forEach(System.out::println);
    }

    @Test
    public void test3(){

        Test1 t1 = new Test1();
        employees.stream()
                .filter((e)->e.getScalay()>3000)
                .limit(2)
                .forEach(System.out::println);
    }

    //创建Stream  内部迭代：迭代操作用Stream API 完成
    @Test
    public void test1(){
        // 中间操作时：不会执行任何操作
        Stream<Employee> employeeStream = employees.stream()
                .filter((x) -> {
                    System.out.println("Stream API 的中间操作");
                    return x.getAge() > 35;});

        // 终止操作：一次性执行全部内容，即“惰性求值”
        employeeStream.forEach(System.out::println);
    }

    // 外部迭代
    @Test
    public void test2(){
        Iterator<Employee> it = employees.iterator();

        while (it.hasNext()){
            System.out.println(it.next());
        }
    }

}
