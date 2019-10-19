package com.yjl.jvm.JVMmonitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 测试VisualVM的BTrace功能
 * Btrace：在不停止目标程序运行的前提下，通过HotSwap技术（代码热替换技术）动态的加入原本并不存在的测试代码
 */
public class BTraceTest {

    public int add(int a,int b){
        return a + b;
    }

    public static void main(String[] args)throws Exception {
        BTraceTest test = new BTraceTest();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < 10; i++) {
            reader.readLine();
            int a = (int) Math.round(Math.random() * 1000);
            int b = (int) Math.round(Math.random() * 1000);

            System.out.println(test.add(a,b));
        }
    }

}
