package com.yjl.study.example.banktick.strategyImpl;

import com.yjl.study.example.banktick.Startegy;

public class Environment {

//    持有对策略类的引用
    private Startegy strategy;

//    类似于TreeSet
    public Environment(Startegy strategy){
        this.strategy = strategy;
    }

    public int caculate(int a,int b){
        return strategy.calc(a, b);
    }
}
