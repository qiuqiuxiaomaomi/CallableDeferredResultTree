package com.bonaparte.service;

import com.bonaparte.constants.ConstantsProps;
import com.bonaparte.controller.DeferredResultController;
import com.karakal.commons.constant.ControllerConstents;
import com.karakal.commons.util.ControllerUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;

public class BonaparteComplexRun implements Runnable{
    public static final Log logger = LogFactory.getLog(DeferredResultController.class);

    private Long id;

    public BonaparteComplexRun(Long id){
        this.id = id;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName());
            DeferredResult<Map> map = new DeferredResult<Map>(5000L);
            map.onTimeout(() ->{
                logger.info("time out");
                Map<String,Object> map1 = ControllerUtil.defaultSuccResult();
                map1.put(ControllerConstents.BODY_KEY, "time out");
                map.setResult(map1);
            });

            map.onCompletion(() -> {
                Map<String, Object> map1 = ControllerUtil.defaultSuccResult();
                map1.put(ControllerConstents.BODY_KEY, "ok successful");
                map.setResult(map1);
                logger.info("successful");
            });

            Thread.sleep(10000);
            Map<String, Object> mapTemp = ControllerUtil.defaultSuccResult();
            mapTemp.put(ControllerConstents.BODY_KEY, "id:" + id);
            map.setResult(mapTemp);
            ConstantsProps.mapDeferredResults.put(id, map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
