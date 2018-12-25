package com.bonaparte.service;

public class BonaparteSimpleRun implements Runnable{

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
