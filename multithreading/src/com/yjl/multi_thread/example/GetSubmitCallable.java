package com.yjl.multi_thread.example;

import java.util.concurrent.Callable;

public class GetSubmitCallable implements Callable<Integer> {

    private int a;
    public GetSubmitCallable(int a){
        this.a = a;
    }


    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i <= a ; i++) {
            sum = sum + i;
        }
        return sum;
    }
}
