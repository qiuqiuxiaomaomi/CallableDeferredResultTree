package com.bonaparte.controller;


import com.bonaparte.service.BonaparteSimpleCallable;
import com.karakal.commons.constant.ControllerConstents;
import com.karakal.commons.util.ControllerUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping("/callable")
public class CallableController {

    @RequestMapping("/callable1")
    @ApiOperation(value = "异步调用",notes = "异步调用",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "")
    })
    public Object callable1() throws ExecutionException, InterruptedException {
        Map<String, Object> map = ControllerUtil.defaultSuccResult();
        System.out.println(Thread.currentThread().getName());
        ExecutorService executorService = Executors.newCachedThreadPool();
        BonaparteSimpleCallable bonaparteSimpleCallable = new BonaparteSimpleCallable();
        Future<Integer> future = executorService.submit(bonaparteSimpleCallable);

        /*阻塞*/
        System.out.println(future.get());
        executorService.shutdown();
        return map;
    }

    @RequestMapping("/callable2")
    @ApiOperation(value = "异步调用",notes = "异步调用",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "")
    })
    public Object callable2() throws ExecutionException, InterruptedException {
        Map<String, Object> map = ControllerUtil.defaultSuccResult();
        System.out.println(Thread.currentThread().getName());
        ExecutorService executorService = Executors.newCachedThreadPool();
        BonaparteSimpleCallable bonaparteSimpleCallable = new BonaparteSimpleCallable();
        FutureTask<Integer> futureTask = new FutureTask<>(bonaparteSimpleCallable);
        executorService.submit(futureTask);
        executorService.shutdown();

        /*阻塞*/
        System.out.println(futureTask.get());
        return map;
    }

    @RequestMapping("/callable3")
    @ApiOperation(value = "异步调用",notes = "异步调用",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "")
    })
    public Callable<Map> callable3() throws ExecutionException, InterruptedException {
        Map<String, Object> map = ControllerUtil.defaultSuccResult();
        System.out.println(Thread.currentThread().getName());
        Callable<Map> callable = new Callable<Map>() {
            @Override
            public Map call() throws Exception {
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName());
                map.put(ControllerConstents.BODY_KEY, "thank you");
                return map;
            }
        };
        return callable;
    }
}
