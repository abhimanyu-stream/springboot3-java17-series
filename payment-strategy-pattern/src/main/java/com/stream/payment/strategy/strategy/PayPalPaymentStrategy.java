package com.stream.payment.strategy.strategy;

import org.springframework.stereotype.Component;

@Component("paypal")
public class PayPalPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
        // Implement PayPal-specific payment logic here
    }
}