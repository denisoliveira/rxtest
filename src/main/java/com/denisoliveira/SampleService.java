package com.denisoliveira;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class SampleService {

    private static Logger logger = Logger.getLogger(SampleService.class.getName());
    private final ExecutorService executors = Executors.newCachedThreadPool();

    public Observable<SampleObject> getSampleObject() {
        return Observable.<SampleObject>create(emitter -> {
            logger.info("Start: Executing slow task in service");
            TimeUnit.SECONDS.sleep(10);
            emitter.onNext(new SampleObject("Test: made by thread id:" + Thread.currentThread().getId()));
            logger.info("Info: thread id: " + Thread.currentThread().getId());
            logger.info("End: Executing slow task in service");
            emitter.onComplete();
        }).subscribeOn(Schedulers.from(executors));
    }
}
