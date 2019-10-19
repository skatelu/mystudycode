package com.yjl.jvm;

/**
 * 借助CGLib 使方法区出现内存溢出异常
 * VM args: -XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class JavaMethodAreaOOM {

    public static void main(String[] args) {
        while(true){

        }
    }
}
