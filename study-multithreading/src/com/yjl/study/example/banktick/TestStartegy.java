package com.yjl.study.example.banktick;

import com.yjl.study.example.banktick.strategyImpl.AddStrategy;
import com.yjl.study.example.banktick.strategyImpl.Environment;
import com.yjl.study.example.banktick.strategyImpl.SubtractStrategy;

public class TestStartegy {

    public static void main(String[] args) {
        Environment environment = new Environment(new SubtractStrategy());
        int caculate = environment.caculate(20, 30);
        System.out.println(caculate);
    }
}
