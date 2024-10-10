package com.stream.online.payment.controller;


import com.stream.online.payment.dto.PaymentCreateRequest;
import com.stream.online.payment.dto.PaymentResponse;
import com.stream.online.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private int amount = 100;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;

    }

    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentCreateRequest paymentRequest) {
        PaymentResponse createdPayment = paymentService.createPayment(paymentRequest);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }



}
