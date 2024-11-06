package com.stream.payment.adapter.adapter;


import org.springframework.stereotype.Service;


// Adapter for PayPal, implementing PaymentProcessor to unify the interface
@Service
public class PayPalAdapter implements PaymentProcessor {
    private final PayPalPayment payPalPayment;

    public PayPalAdapter() {
        this.payPalPayment = new PayPalPayment();
    }

    @Override
    public void processPayment(double amount) {

        // Adapts PayPal's API to processPayment
        //Capture payment status, update status in PaymentLogRepository[success, failed]-->[ before calling payment api , first create payment processing object and store in DB PaymentLogRepository with status [ Initiated]
        //

         payPalPayment.payWithPayPal(amount);
    }
}