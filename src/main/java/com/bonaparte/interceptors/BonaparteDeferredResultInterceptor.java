package com.bonaparte.interceptors;

import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.DeferredResultProcessingInterceptor;

public class BonaparteDeferredResultInterceptor implements DeferredResultProcessingInterceptor {

    @Override
    public <T> void beforeConcurrentHandling(NativeWebRequest nativeWebRequest, DeferredResult<T> deferredResult) throws Exception {
        System.out.println("DeferredResult before");
    }

    @Override
    public <T> void preProcess(NativeWebRequest nativeWebRequest, DeferredResult<T> deferredResult) throws Exception {
        System.out.println("DeferredResult pre");
    }

    @Override
    public <T> void postProcess(NativeWebRequest nativeWebRequest, DeferredResult<T> deferredResult, Object o) throws Exception {
        System.out.println("DeferredResult post");
    }

    @Override
    public <T> boolean handleTimeout(NativeWebRequest nativeWebRequest, DeferredResult<T> deferredResult) throws Exception {
        System.out.println("DeferredResult time out");
        return false;
    }

    @Override
    public <T> void afterCompletion(NativeWebRequest nativeWebRequest, DeferredResult<T> deferredResult) throws Exception {
        System.out.println("DeferredResult completion");
    }
}
