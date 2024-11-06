package com.stream.payment.adapter.adapter;


import org.springframework.stereotype.Service;

// Adapter for Stripe Pay, implementing PaymentProcessor to unify the interface
@Service
public class StripeAdapter implements PaymentProcessor {
    private final StripePayment stripePayment;

    public StripeAdapter() {
        this.stripePayment = new StripePayment();
    }

    @Override
    public void processPayment(double amount) {
        stripePayment.chargeWithStripe(amount); // Adapts Stripe's API to processPayment
    }
}