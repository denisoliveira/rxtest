package com.denisoliveira;


import io.reactivex.Observable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;


@RestController
@RequestMapping("/sample")
public class SampleController {

    private static Logger logger = Logger.getLogger(SampleController.class.getName());

    @Autowired
    private SampleService service;

    @RequestMapping("/block")
    public SampleObject getSampleObjectBlocking() {
        logger.info("Starting Block");
        return service.getSampleObject().blockingFirst();
    }

    @RequestMapping("/async")
    public DeferredResult<SampleObject> getSampleObjectAsync() {
        logger.info("Starting Async");
        DeferredResult<SampleObject> deferred = new DeferredResult<>();
        Observable<SampleObject> observable = service.getSampleObject();
        observable.subscribe(deferred::setResult, deferred::setErrorResult);
        return deferred;
    }
}
