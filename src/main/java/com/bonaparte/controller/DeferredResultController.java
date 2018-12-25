package com.bonaparte.controller;


import com.bonaparte.constants.ConstantsProps;
import com.bonaparte.constants.DeferredResultQueue;
import com.bonaparte.service.BonaparteComplexRun;
import com.bonaparte.service.BonaparteExecutorThreadPool;
import com.bonaparte.service.BonaparteSimpleRun;
import com.karakal.commons.util.ControllerUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

@RestController
@RequestMapping("/deferredResult")
public class DeferredResultController {
    public static final Log logger = LogFactory.getLog(DeferredResultController.class);

    @RequestMapping("/deferredResult1")
    @ApiOperation(value = "异步调用",notes = "异步调用",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "")
    })
    public DeferredResult<Map> deferredResult1(){
        System.out.println(Thread.currentThread().getName());
        DeferredResult<Map> mapDeferredResult = new DeferredResult<>();
        return mapDeferredResult;
    }

    @RequestMapping("/deferredResult2")
    @ApiOperation(value = "异步调用",notes = "异步调用",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "")
    })
    public DeferredResult<Map> deferredResult2(){
        System.out.println(Thread.currentThread().getName());
        Long timeSeconds = System.currentTimeMillis();
        BonaparteComplexRun bonaparteComplexRun = new BonaparteComplexRun(timeSeconds);
        BonaparteExecutorThreadPool.execute(bonaparteComplexRun);
        DeferredResult<Map> map = ConstantsProps.mapDeferredResults.get(timeSeconds);
        return map;
    }

    @RequestMapping("/deferredResult3")
    @ApiOperation(value = "异步调用",notes = "异步调用",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "")
    })
    public DeferredResult<Map> deferredResult3(){
        System.out.println(Thread.currentThread().getName());
        DeferredResult<Map> deferredResult = new DeferredResult<>(300000L, "time out");
        DeferredResultQueue.push(deferredResult);
        return deferredResult;
    }

    @RequestMapping("/deferredResult5")
    @ApiOperation(value = "异步调用",notes = "异步调用",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "")
    })
    public DeferredResult<Map> deferredResult5(){
        System.out.println(Thread.currentThread().getName());
        DeferredResult<Map> deferredResult = DeferredResultQueue.poll();
        Map<String, Object> map = ControllerUtil.defaultSuccResult();
        deferredResult.setResult(map);
        return deferredResult;
    }
}
