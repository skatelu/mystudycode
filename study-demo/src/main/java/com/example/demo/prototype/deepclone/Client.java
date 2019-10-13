package com.example.demo.prototype.deepclone;

public class Client {

    public static void main(String[] args) {
        DeepProtoType p = new DeepProtoType();

        p.name = "松江";
        p.deepCloneableTarget = new DeepCloneableTarget("大牛", "小牛");


        // 方式2 完成深拷贝
        DeepProtoType p2 = (DeepProtoType) p.deepClone();

        System.out.println("p.name = " + p.name + "p.deepCloneableTarget = " + p.deepCloneableTarget + p.deepCloneableTarget.hashCode());
        System.out.println("p2.name = " + p2.name + "p2.deepCloneableTarget = " + p2.deepCloneableTarget + p2.deepCloneableTarget.hashCode());

    }
}
