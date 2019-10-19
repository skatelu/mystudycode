package com.yjl.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * java堆溢出
 * OutOfMemoryError
 * VM args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class TestJVM {

      static class OOMObject{

      }

      //-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();

        while (true){
            list.add(new OOMObject());
        }

        /*for (int i = 0; i < 5; i++) {
            list.add(new OOMObject());
            System.out.println(i);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }*/
    }
}
