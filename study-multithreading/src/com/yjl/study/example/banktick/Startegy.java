package com.yjl.study.example.banktick;

/**
 * 策略模式
 *  定义抽象策略角色
 *         类似于Comparator接口
 */
public interface Startegy {
    /**
     * 定义了实现两个数可以计算
     * @return
     */
    public int calc(int num1,int num2);

}
