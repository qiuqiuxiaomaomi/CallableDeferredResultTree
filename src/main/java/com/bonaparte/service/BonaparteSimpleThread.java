package com.bonaparte.service;

import com.bonaparte.constants.ConstantsProps;

public class BonaparteSimpleThread extends Thread{

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName());
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
