package com.yjl.study.example.threadjvm;

public class ThreadJvm {

    private int i = 0;

    private byte[] bytes = new byte[1024];

    private static int counter = 0;
//    JVM will create a thread named  "main"
    public static void main(String[] args) {
//    create a JVM stack
        try {
            add(0);
        }catch (Error e){
            e.printStackTrace();
            System.out.println(counter);
        }
    }

//    java.lang.StackOverflowError  15986
    private static void add(int i){
        ++counter;
        add(i+1);
    }
}
