package com.enable.asynchronous.processing.controller;


import com.enable.asynchronous.processing.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {


    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/startAsync")
    public String startAsyncProcess() {
        transactionService.asyncMethod();
        return "Async process started";
    }
}
