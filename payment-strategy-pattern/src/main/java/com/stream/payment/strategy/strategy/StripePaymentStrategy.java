package com.stream.payment.strategy.strategy;

import org.springframework.stereotype.Component;

@Component("stripe")
public class StripePaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Processing Stripe payment of $" + amount);
        // Implement Stripe-specific payment logic here
    }
}