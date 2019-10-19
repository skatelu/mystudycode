package com.yjl.study.example.studayStream;

import com.yjl.study.example.studylambda.Employee;

import java.util.function.Predicate;

public class Test1 implements Predicate {
    @Override
    public boolean test(Object o) {
        Employee c = (Employee) o;
        if(((Employee) o).getScalay()>5000){
            return true;
        }
        return false;
    }
}
