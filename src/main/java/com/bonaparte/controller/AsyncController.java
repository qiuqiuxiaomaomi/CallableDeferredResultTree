package com.bonaparte.controller;

import com.bonaparte.service.BonaparteSimpleRun;
import com.bonaparte.service.BonaparteSimpleThread;
import com.karakal.commons.constant.ControllerConstents;
import com.karakal.commons.util.ControllerUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/async")
public class AsyncController {

    @RequestMapping("/async1")
    @ApiOperation(value = "异步调用",notes = "异步调用",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "")
    })
    @Async
    public Object async1() throws InterruptedException {
        Map<String, Object> map = ControllerUtil.defaultSuccResult();
        Thread.currentThread().sleep(5000);
        map.put(ControllerConstents.BODY_KEY, "async");
        return map;
    }

    @RequestMapping("/async2")
    @ApiOperation(value = "异步调用",notes = "异步调用",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "")
    })
    @Async
    public Object async2() throws InterruptedException {
        Map<String, Object> map = ControllerUtil.defaultSuccResult();
        System.out.println(Thread.currentThread().getName());
        BonaparteSimpleThread bonaparteSimpleThread = new BonaparteSimpleThread();
        bonaparteSimpleThread.start();
        return map;
    }

    @RequestMapping("/async3")
    @ApiOperation(value = "异步调用",notes = "异步调用",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "")
    })
    @Async
    public Object async3() throws InterruptedException {
        Map<String, Object> map = ControllerUtil.defaultSuccResult();
        System.out.println(Thread.currentThread().getName());
        BonaparteSimpleRun bonaparteSimpleRun = new BonaparteSimpleRun();
        Thread thread = new Thread(bonaparteSimpleRun);
        thread.start();
        return map;
    }
}
