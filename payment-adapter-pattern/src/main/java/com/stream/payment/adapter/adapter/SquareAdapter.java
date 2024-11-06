package com.stream.payment.adapter.adapter;


import org.springframework.stereotype.Service;

// Adapter for Square Pay, implementing PaymentProcessor to unify the interface
@Service
public class SquareAdapter implements PaymentProcessor {
    private final SquarePayment squarePayment;

    public SquareAdapter() {
        this.squarePayment = new SquarePayment();
    }

    @Override
    public void processPayment(double amount) {
        squarePayment.processSquarePayment(amount); // Adapts Square's API to processPayment
    }
}