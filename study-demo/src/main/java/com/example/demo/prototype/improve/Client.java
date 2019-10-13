package com.example.demo.prototype.improve;


/**
 * 原型模式实现克隆
 * 好处，当Sheep 中的属性发生改变时，此处不需要进行修改就能动态的创建
 */
public class Client {
    public static void main(String[] args) {
        Sheep sheep = new Sheep("tom", 1, "白色");
        Sheep sheep1 = (Sheep) sheep.clone();
        Sheep sheep2 = (Sheep) sheep.clone();
        Sheep sheep3 = (Sheep) sheep.clone();
        Sheep sheep4 = (Sheep) sheep.clone();
        Sheep sheep5 = (Sheep) sheep.clone();

    }
}
