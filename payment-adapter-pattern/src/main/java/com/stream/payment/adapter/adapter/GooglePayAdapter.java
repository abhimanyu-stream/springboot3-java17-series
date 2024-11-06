package com.stream.payment.adapter.adapter;



import org.springframework.stereotype.Service;



// Adapter for Google Pay, implementing PaymentProcessor to unify the interface
@Service
public class GooglePayAdapter implements PaymentProcessor {
    private final GooglePayPayment googlePayPayment;

    public GooglePayAdapter() {
        this.googlePayPayment = new GooglePayPayment();
    }

    @Override
    public void processPayment(double amount) {
        googlePayPayment.googlePayCharge(amount); // Adapting googlePayCharge to processPayment
    }
}