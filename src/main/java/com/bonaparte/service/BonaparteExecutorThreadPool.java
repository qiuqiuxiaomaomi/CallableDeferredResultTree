package com.bonaparte.service;

import com.google.common.collect.Lists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.concurrent.*;

public class BonaparteExecutorThreadPool {

    public static final Log logger = LogFactory.getLog(BonaparteExecutorThreadPool.class);

    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(8,
            16,
            60000,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>(10000),
            new ThreadPoolExecutor.CallerRunsPolicy()) {
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if (t != null) {
                logger.error(
                        "BonaparteExecutorThreadPool failure,command=" + r.getClass().getCanonicalName());
            }
        }
    };

    public static void execute(Runnable command) {
        if (command == null) {
            return;
        }
        threadPoolExecutor.execute(command);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return threadPoolExecutor.submit(task);
    }

    public static <T> List invokeAll(List<? extends Callable<T>> tasks) {
        List<T> res = Lists.newArrayList();
        try {
            List<Future<T>> futureList = threadPoolExecutor.invokeAll(tasks, 10, TimeUnit.SECONDS);
            for (Future f : futureList) {
                res.add((T) f.get(10, TimeUnit.SECONDS));
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("BonaparteExecutorThreadPool invokeAll failure," + e.getMessage(), e);
            return res;
        }
    }
}
