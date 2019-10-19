package com.yjl.jvm;

/**
 * 栈溢出，和请求深度超过栈的长度时
 * VM args : -Xss128k
 * @author E570
 */
public class JavaVMStackOOM {

    private int stackLength = 1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Exception{
        JavaVMStackOOM oom = new JavaVMStackOOM();

        try {
            oom.stackLeak();
        }catch (Exception e){
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }finally {
            System.out.println("stack length:" + oom.stackLength);
        }
    }
}
