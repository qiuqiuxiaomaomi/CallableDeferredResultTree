package com.bonaparte.interceptors;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;

import java.util.concurrent.Callable;

public class BonaparteCallableInterceptor implements CallableProcessingInterceptor {
    @Override
    public <T> void beforeConcurrentHandling(NativeWebRequest nativeWebRequest, Callable<T> callable) throws Exception {
        System.out.println("callable before");
    }

    @Override
    public <T> void preProcess(NativeWebRequest nativeWebRequest, Callable<T> callable) throws Exception {
        System.out.println("callable pre");
    }

    @Override
    public <T> void postProcess(NativeWebRequest nativeWebRequest, Callable<T> callable, Object o) throws Exception {
        System.out.println("callable post");
    }

    @Override
    public <T> Object handleTimeout(NativeWebRequest nativeWebRequest, Callable<T> callable) throws Exception {
        System.out.println("callable timeout");
        return null;
    }

    @Override
    public <T> void afterCompletion(NativeWebRequest nativeWebRequest, Callable<T> callable) throws Exception {
        System.out.println("callable completion");
    }
}
