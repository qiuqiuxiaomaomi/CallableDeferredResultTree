package com.bonaparte.constants;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DeferredResultQueue {

    public static Queue<DeferredResult<Map>> deferredResultQueue = new ConcurrentLinkedDeque<>();

    public static void push(DeferredResult<Map> deferredResult){
        deferredResultQueue.add(deferredResult);
    }

    public static DeferredResult poll(){
        return deferredResultQueue.poll();
    }
}
