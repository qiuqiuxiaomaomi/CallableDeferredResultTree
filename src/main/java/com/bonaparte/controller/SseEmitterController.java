package com.bonaparte.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/sseEmitter")
public class SseEmitterController {

    private SseEmitter sseEmitter = new SseEmitter();

    @RequestMapping("/sseEmitter1")
    @ApiOperation(value = "异步调用",notes = "异步调用",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "")
    })
    public SseEmitter sseEmitter1(){
        return sseEmitter;
    }

    @RequestMapping("/sseEmitter2")
    @ApiOperation(value = "异步调用",notes = "异步调用",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "")
    })
    public String sseEmitter2(){
        try {
            sseEmitter.send("to to to");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "seccessful";
    }

    @RequestMapping("/sseEmitter3")
    @ApiOperation(value = "异步调用",notes = "异步调用",httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "")
    })
    public String sseEmitter3(){
        sseEmitter.complete();
        return "seccessful";
    }
}
