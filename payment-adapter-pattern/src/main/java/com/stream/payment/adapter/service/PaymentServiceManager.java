package com.stream.payment.adapter.service;

import com.stream.payment.adapter.adapter.*;
import com.stream.payment.adapter.model.PaymentLog;
import com.stream.payment.adapter.repository.PaymentLogRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceManager {

    private final PaymentLogRepository paymentLogRepository;
    private final PayPalAdapter payPalAdapter;
    private final StripeAdapter stripeAdapter;
    private final SquareAdapter squareAdapter;
    private final GooglePayAdapter googlePayAdapter;

    @Autowired
    public PaymentServiceManager(PaymentLogRepository paymentLogRepository,
                                 PayPalAdapter payPalAdapter,
                                 StripeAdapter stripeAdapter,
                                 SquareAdapter squareAdapter, GooglePayAdapter googlePayAdapter) {
        this.paymentLogRepository = paymentLogRepository;
        this.payPalAdapter = payPalAdapter;
        this.stripeAdapter = stripeAdapter;
        this.squareAdapter = squareAdapter;
        this.googlePayAdapter = googlePayAdapter;
    }

    @Transactional
    public void processPayment(String paymentMethod, double amount) {
        PaymentProcessor paymentProcessor;
        // Creating adapters for each payment method
        switch (paymentMethod.toLowerCase()) {
            case "paypal" -> paymentProcessor = payPalAdapter;
            case "stripe" -> paymentProcessor = stripeAdapter;
            case "square" -> paymentProcessor = squareAdapter;
            case "gpay" -> paymentProcessor = googlePayAdapter;
            default -> throw new IllegalArgumentException("Unknown payment method: " + paymentMethod);
        }


        String paymentTransactionId = "someTransactionId";
        String status = "initiated";

        //store in db PaymentLogRepository with status [ initiated]
        logPayment(paymentMethod, amount, "initiated");


        //capture payment status and updated in db PaymentLogRepository only column status [ success or failed]
        paymentProcessor.processPayment(amount);


        //status = paymentService.processPayment(amount);
        updateStatusOfPaymentInPaymentLog(paymentTransactionId, status);


    }

    private void logPayment(String paymentMethod, double amount, String status) {
        PaymentLog log = new PaymentLog();
        log.setPaymentMethod(paymentMethod);
        log.setAmount(amount);
        log.setStatus(status);
        paymentLogRepository.save(log);
    }
    private void  updateStatusOfPaymentInPaymentLog(String paymentTransactionId,  String status){
        // get PaymentLog object by paymentTransactionId
        //updated status
        //save in to PaymentLogRepository

    }
}