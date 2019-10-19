package com.yjl.jvm.testjava;

public class TestJava {

        public static int staticVar = 0;
        public int instanceVar = 0;

        public TestJava() {
            staticVar++;
            instanceVar++;
            System.out.println("staticVar=" + staticVar + ",instanceVar=" + instanceVar);
        }


    public static void main(String[] args) {
        TestJava testJava = new TestJava();

        TestJava testJava1 = new TestJava();
        TestJava testJava2 = new TestJava();
        TestJava testJava3 = new TestJava();
        TestJava testJava4 = new TestJava();

    }
}
