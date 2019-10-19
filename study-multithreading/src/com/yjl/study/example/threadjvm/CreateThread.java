package com.yjl.study.example.threadjvm;

/**
 * 自定义栈大小
 */
public class CreateThread {

    private static int counter = 1;

    public static void main(String[] args) {
        Thread t1 = new Thread(null,new Runnable() {
            @Override
            public void run() {
                try {
                    add(1);
                }catch (Error e){
                    e.printStackTrace();
                    System.out.println(counter);
                }
            }

            private void add(int i){
                counter++ ;
                add(i + 1);
            }
        },"test",1 << 24);

        t1.start();
    }
}
