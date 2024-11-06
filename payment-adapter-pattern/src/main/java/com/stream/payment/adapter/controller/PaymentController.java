package com.stream.payment.adapter.controller;

import com.stream.payment.adapter.service.PaymentServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentServiceManager paymentServiceManager;

    @Autowired
    public PaymentController(PaymentServiceManager paymentServiceManager) {
        this.paymentServiceManager = paymentServiceManager;
    }

    @PostMapping("/process")
    public String processPayment(@RequestParam String paymentMethod, @RequestParam double amount) {

        //its also require inputs for customerId, orderId-->productId [or orderId-->orderLineItems]
        paymentServiceManager.processPayment(paymentMethod, amount);
        return "Payment processed through " + paymentMethod;
    }
}