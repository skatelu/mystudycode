package com.yjl.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行时常量池导致的内存溢出
 * Vm args: -XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class RuntimeConstantPoolOOM {

    /**
     * Java1.8会一直运行下去，貌似去掉永生代了
     * @param args
     */
    /*public static void main(String[] args) {
        //使用List保持着常量池引用，避免Full GC回收常量池行为
        List<String> list = new ArrayList<String>();
        //10MB的PermSize在integer 范围内足够产生OOM了
        int i = 0;
        while (true){
            //String.intern()是Native方法，他的作用是：如果字符串常量池中已经包含一个等于此String的对象的字符串
            //则返回代表池中这个字符串的String对象；否则，将此String对象包含的字符串添加到常量池中，并且返回此String对象的引用
            list.add(String.valueOf(i++).intern());
        }
    }*/
    public static void main(String[] args) {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

        String a = "计算机";
        String b = "计算机";
        System.out.println(a.intern()==b.intern());
    }



}
