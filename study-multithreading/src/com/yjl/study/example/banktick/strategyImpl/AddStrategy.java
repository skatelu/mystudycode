package com.yjl.study.example.banktick.strategyImpl;

import com.yjl.study.example.banktick.Startegy;

public class AddStrategy implements Startegy {
    /**
     * 实现两个数的和
     * @param num1
     * @param num2
     * @return
     */
    @Override
    public int calc(int num1, int num2) {
        return num1 + num2;
    }
}
