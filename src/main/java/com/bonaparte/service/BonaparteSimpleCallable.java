package com.bonaparte.service;

import java.util.concurrent.Callable;

public class BonaparteSimpleCallable implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(20000);
        Integer sum = 0;
        for (int i = 0; i < 100000000; i++){
            sum+=i;
        }
        return sum;
    }
}
