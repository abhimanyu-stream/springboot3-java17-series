package com.enable.asynchronous.processing.service;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionService {

    //private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Async
    public void asyncMethod() {
        log.info("Start async method");
        //logger.info("Start async method");

        try {
            // Simulate a long-running task
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Complete async method");
    }
}
