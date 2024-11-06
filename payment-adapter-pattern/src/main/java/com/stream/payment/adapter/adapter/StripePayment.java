package com.stream.payment.adapter.adapter;

// A third-party Stripe Pay payment class with a different API
public class StripePayment {
    public void chargeWithStripe(double amount) {
        System.out.println("Processing Stripe payment of $" + amount);
    }
}